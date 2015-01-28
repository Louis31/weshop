package com.i5le.framwork.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;



public class JPBeanUtil {
	private static DozerBeanMapper mapper = new DozerBeanMapper();
	
	public static void setVal(List entityList,String[] props,String[] vals) throws IllegalAccessException, InvocationTargetException{
		if(props.length!=vals.length){
			throw new IllegalArgumentException("参数错误!属性名props与属性值大小必需相等.");
		}
		if(entityList==null||entityList.size()==0) return;
		for(Object e : entityList){
			for(int i=0,k=props.length;i<k;i++){
				setVal(e, props[i], vals[i]);
			}
		}
	}
	
	public static void setVal(Object obj,String[] props,String[] vals) throws IllegalAccessException, InvocationTargetException{
		if(props.length!=vals.length){
			throw new IllegalArgumentException("参数错误!属性名props与属性值大小必需相等.");
		}
		for(int i=0,k=props.length;i<k;i++){
			setVal(obj, props[i], vals[i]);
		}
	}
	
	public static void setVal(Object obj,String name,Object val) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.setProperty(obj,name,val);
	}
	
	public static void setVal(Object bean,String propPath) throws 	Exception{
		PropertiesUtil p = PropertiesUtil.getProperties(propPath);
		Map map = p.getP();
		setVal(bean,map,false,false);
	}
	
	/**
	 * 将map key中的值设置到bean。当flag为true时bean对应的key属性不为空也设置值.
	 * @param bean
	 * @param propPath
	 * @param flag
	 * @param throwErr
	 * @throws Exception 
	 */
	public static void setVal(Object bean,Map map,boolean flag,boolean throwErr) throws Exception{
		Set keySet = map.keySet();
		Iterator iter = keySet.iterator();
		Class clazz = bean.getClass();
		String key;
		Method method;
		Object valObj;
		while(iter.hasNext()){
			key = String.valueOf(iter.next());
			try {
				method = findGetMethod(clazz, key);
			} catch (Exception e) {
				if(throwErr) throw e;
				continue;
			}
			//空值才设置值
			valObj = method.invoke(bean, null);
			if(flag||(valObj==null||StringUtils.isBlank(valObj.toString()))){
				setVal(bean, key, map.get(key));
			}
		}
	}
	
	public static Method findGetMethod(Class clazz,String fieldName) throws SecurityException, NoSuchMethodException{
		String get = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		return clazz.getMethod(get, null);
	}
	
	public static Method findSetMethod(Class clazz,String fieldName) throws SecurityException, NoSuchMethodException, NoSuchFieldException{
		Field field = clazz.getDeclaredField(fieldName);
		Class arg0 = field.getType();
		String set = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		return clazz.getMethod(set, arg0);
	}
	
	public static void setObject(Object source,Object target){
		mapper.map(source, target);
	}
	/**
	 * 将一个对象转为另一个对象
	 * @param source
	 * @param destinationClass
	 * @return
	 */
	public static <T> T getObject(Object source,Class<T> destinationClass){
		return mapper.map(source, destinationClass);
	}
	
	@SuppressWarnings("unchecked")  
    public static void copyPropertiesExclude(Object from, Object to, String ...excludsArray) throws Exception {  
        List<String> excludesList = null;  
        if(excludsArray != null && excludsArray.length > 0) {  
            excludesList = Arrays.asList(excludsArray); //构造列表对象  
        }  
        Method[] fromMethods = from.getClass().getDeclaredMethods();  
        Method[] toMethods = to.getClass().getDeclaredMethods();  
        Method fromMethod = null, toMethod = null;  
        String fromMethodName = null, toMethodName = null;  
        for (int i = 0; i < fromMethods.length; i++) {  
            fromMethod = fromMethods[i];  
            fromMethodName = fromMethod.getName();  
            if (!fromMethodName.contains("get"))  
                continue;  
            //排除列表检测  
            if(excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {  
                continue;  
            }  
            toMethodName = "set" + fromMethodName.substring(3);  
            toMethod = findMethodByName(toMethods, toMethodName);  
            if (toMethod == null)  
                continue;  
            Object value = fromMethod.invoke(from, new Object[0]);  
            if(value == null)  
                continue;  
            //集合类判空处理  
            if(value instanceof Collection) {  
                Collection newValue = (Collection)value;  
                if(newValue.size() <= 0)  
                    continue;  
            }  
            toMethod.invoke(to, new Object[] {value});  
        }  
    }
	
	@SuppressWarnings("unchecked")  
    public static void copyPropertiesInclude(Object from, Object to, String[] includsArray) throws Exception {  
        List<String> includesList = null;  
        if(includsArray != null && includsArray.length > 0) {  
            includesList = Arrays.asList(includsArray); //构造列表对象  
        } else {  
            return;  
        }  
        Method[] fromMethods = from.getClass().getDeclaredMethods();  
        Method[] toMethods = to.getClass().getDeclaredMethods();  
        Method fromMethod = null, toMethod = null;  
        String fromMethodName = null, toMethodName = null;  
        for (int i = 0; i < fromMethods.length; i++) {  
            fromMethod = fromMethods[i];  
            fromMethodName = fromMethod.getName();  
            if (!fromMethodName.contains("get"))  
                continue;  
            //排除列表检测  
            String str = fromMethodName.substring(3);  
            if(!includesList.contains(str.substring(0,1).toLowerCase() + str.substring(1))) {  
                continue;  
            }  
            toMethodName = "set" + fromMethodName.substring(3);  
            toMethod = findMethodByName(toMethods, toMethodName);  
            if (toMethod == null)  
                continue;  
            Object value = fromMethod.invoke(from, new Object[0]);  
            if(value == null)  
                continue;  
            //集合类判空处理  
            if(value instanceof Collection) {  
                Collection newValue = (Collection)value;  
                if(newValue.size() <= 0)  
                    continue;  
            }  
            toMethod.invoke(to, new Object[] {value});  
        }  
    } 
	
	public static Method findMethodByName(Method[] methods, String name) {  
        for (int j = 0; j < methods.length; j++) {  
            if (methods[j].getName().equals(name))  
                return methods[j];  
        }  
        return null;  
    }
	
	public static Object getValue(Object obj,String fieldName){
		try {
			return BeanUtils.getProperty(obj, fieldName);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	


}
