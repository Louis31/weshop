package com.i5le.framwork.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 优先加载 -test 文件
 * @Description: TODO
 * @ClassName: PropertiesUtil 
 * modify by huangzy@gzjp.cn
 * @date 2014年8月8日 下午1:36:29
 */
public final class PropertiesUtil {
	
	private PropertiesUtil() {}

	private Properties p;

	public Properties getP() {
		return p;
	}

	public void setP(Properties p) {
		this.p = p;
	}

	private static Map<String, PropertiesUtil> propertiesCache = new ConcurrentHashMap<String, PropertiesUtil>();
	private static Map<String,Long> lastModifyMap = new ConcurrentHashMap<String, Long>();
	private static int sleepSec = 30;	//扫描间隔

	/**
	 * 若存在propath-test.properties 则优先加载.不存在,则加载propath.properties
	 * @param propath
	 * @return
	 */
	public static PropertiesUtil getProperties(String propath) {
		String testPropath = propath.substring(0,propath.lastIndexOf('.'))+"-test"+propath.substring(propath.lastIndexOf('.'));
		String path = propath;
		try {
			ResourceUtil.parseAbsolutePath(PropertiesUtil.class, testPropath);
			path = testPropath;
		} catch (Exception e) {}
		return getProperties0(path);
	}
	
	private static PropertiesUtil getProperties0(String propath){
		if (propertiesCache.containsKey(propath)) {
			return propertiesCache.get(propath);
		} else {
			return cacheProperties(propath);
		}
	}
	
	
	public static void reloadProperties(String propath){
		cacheProperties(propath);
	}
	
	private static PropertiesUtil cacheProperties(String propath){
		PropertiesUtil pu = new PropertiesUtil();
		Properties p = new Properties();
		InputStreamReader isr = null;
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getResourceAsStream(propath);
			isr = new InputStreamReader(is,"utf8");
			p.load(isr);
			
			File file = new File(ResourceUtil.parseAbsolutePath(PropertiesUtil.class, propath));
			lastModifyMap.put(propath, file.lastModified());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			CloseUtil.close(is);
			CloseUtil.close(isr);
		}
		pu.setP(p);
		propertiesCache.put(propath, pu);
		
		return pu;
	}
	
	public String getString(String key ,String deValue){
		if (null != key && !key.trim().equals(""))
			return p.getProperty(key, deValue);
		return "";
	}
	public String getString(String key){
		if (null != key && !key.trim().equals(""))
			return p.getProperty(key);
		return "";
	}

	public Object getValue(String key, Object deObject) {
		if (null != key && !key.trim().equals(""))
			return null == p.get(key) ? deObject : p.get(key);
		return deObject;

	}

	public Object getValue(String key) {
		if (key == null) {
			return null;
		}

		return p.get(key);
	}
	
	static{
		new UpProperties();
	}
	//监控properties文件更新
	static class UpProperties{
		UpProperties(){
			init();
		}
		
		private void init(){
				Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						//System.out.println(Thread.currentThread().getId()+",扫描配制文件是否更新.....");
						//每5分钟扫描一下配制文件
						try {
							Thread.sleep(sleepSec*1000);
						} catch (InterruptedException e) {}
						
						if(lastModifyMap==null||lastModifyMap.size()==0) continue;
						for(Map.Entry<String, Long> entity: lastModifyMap.entrySet()){
							try {
								ResourceUtil.parseAbsolutePath(PropertiesUtil.class, entity.getKey());
								File f = new File(ResourceUtil.parseAbsolutePath(PropertiesUtil.class, entity.getKey()));
								
								//System.out.println("文件="+f.getAbsolutePath()+","+f.lastModified()+","+entity.getValue());
								
								if(f.lastModified()>entity.getValue()){
									reloadProperties(entity.getKey());
									System.out.println("线程id="+Thread.currentThread().getId()+",需要更新配制文件:"+f.getAbsolutePath());
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
								
								System.out.println("线程id="+Thread.currentThread().getId()+","+entity.getKey()+" 文件已被删除!从缓存中移除.....");
								lastModifyMap.remove(entity.getKey());
								propertiesCache.remove(entity.getKey());
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						
					}
				}
			});
			t.start();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		/*String propath = "/aa.properties";
		String testPropath = propath.substring(0,propath.lastIndexOf('.'))+"-test"+propath.substring(propath.lastIndexOf('.'));
		System.out.println(testPropath);*/
		/*while(true){
			PropertiesUtil pu = PropertiesUtil.getProperties("/test.properties");
			System.out.println(pu.getString("test"));
			
			PropertiesUtil.getProperties("/ReFineOrder.properties");
			
			Thread.sleep(10*1000);
		}*/
	}
}
