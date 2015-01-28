package com.i5le.framwork.core.utils;

import java.io.FileNotFoundException;
import java.net.URL;


public final class ResourceUtil {
	public static String parseAbsolutePath(Class clazz,String propath) throws FileNotFoundException{
		ClassLoader cl = clazz.getClassLoader();
		String filepath = resolveName(clazz, propath);
		URL url = cl.getResource(filepath);
		if(url==null){
			throw new FileNotFoundException("path1="+propath+",path2="+filepath);
		}
		return url.getFile();
	}
	
	private static String resolveName(Class clazz,String propath) {
        if (propath == null) {
            return propath;
        }
        if (!propath.startsWith("/")) {
            Class c = clazz;
            while (c.isArray()) {
                c = c.getComponentType();
            }
            String baseName = c.getName();
            int index = baseName.lastIndexOf('.');
            if (index != -1) {
            	propath = baseName.substring(0, index).replace('.', '/')
                    +"/"+propath;
            }
        } else {
        	propath = propath.substring(1);
        }
        return propath;
    }
	
	/*public static void main(String[] args) throws FileNotFoundException {
		File f = new File(parseAbsolutePath(ResourceUtil.class, "context.properties"));
		System.out.println(f.exists()+","+f.lastModified());
	}*/
}
