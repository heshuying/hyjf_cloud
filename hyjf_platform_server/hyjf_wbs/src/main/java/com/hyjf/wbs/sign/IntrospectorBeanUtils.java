package com.hyjf.wbs.sign;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用Introspector进行转换 
 * getPropertyDescriptors()根据方法来得到属性信息，所有符合javabean的get、set方法都会被获取到
 * 需要自己过滤不是属性的方法
 * 耗时与reflect相比：100：1
 */ 
public class IntrospectorBeanUtils {
 
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null || map.size()<=0)   
            return null;    
    
        Object obj = beanClass.newInstance();  
    
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName())); 
            }  
        }  
    
        return obj;  
    }    
        
    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
    
        Map<String, Object> map = new HashMap<String, Object>();
    
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
        return map;  
    }
}