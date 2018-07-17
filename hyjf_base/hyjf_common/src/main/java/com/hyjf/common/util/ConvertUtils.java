package com.hyjf.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description: 参数转换工具类
 */
public  class ConvertUtils {

    /**
     * 将MAP参数转换成实体类
     * @param mapParam  map参数，key值要和实体类的属性一致
     * @param T  转换后的class类
     * @return  转换后的带内容的实体类
     */
    public static Object convertMapToObject(Map<String, Object> mapParam, Class T){
        Object obj = null;
        try {
            obj = T.newInstance();
            Field[] fs = T.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                String mtdName = "set"+ f.getName().substring(0,1).toUpperCase() + f.getName().substring(1);
                Method method = T.getMethod(mtdName, f.getType());
                method.invoke(obj,mapParam.get(f.getName()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将实体类转换成Map
     * @param bean 需要转换的实体类
     * @return  转换后的带内容的MAp key为实体类的属性名
     */
    public static Map convertObjectToMap(Object bean){
        Map<String, Object> mapParam = new HashMap<String, Object>();
        try {
            Class beanClass = bean.getClass();
            Field[] fs = beanClass.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                mapParam.put(f.getName(),f.get(bean));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapParam;
    }
}
