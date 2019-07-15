package com.hyjf.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description: 参数转换工具类
 */
public  class ConvertUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

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
                if(mapParam.get(f.getName()) != null){
                    method.invoke(obj,mapParam.get(f.getName()));
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
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
                mapParam.put(f.getName(),f.get(bean)==null?"":f.get(bean));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return mapParam;
    }

    public static List<Object> getAllFiledList(Class T){
        Field[] fs = T.getDeclaredFields();
        ArrayList fileList = new ArrayList(Arrays.asList(fs));
        Class tempClass = T;
        while (tempClass != null){
            Class superClass = tempClass.getSuperclass();
            if(superClass != null){
                Field[] declaredFields = superClass.getDeclaredFields();
                ArrayList fList = new ArrayList(Arrays.asList(declaredFields));
                fileList.addAll(fList);
            }
            tempClass = superClass;
        }
        return fileList;
    }

    /**
     * 将实体类转换成对应的基本类型的jsonMap
     * 增加对参数类中包含其他类的处理
     * add by cwyang
     * @param bean
     * @return
     */
    public static Map convertObjectToJsonMap(Object bean){

        Map<String, Object> mapParam = new HashMap<String, Object>();
        try {
            Class beanClass = bean.getClass();
            Field[] fs = beanClass.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                try{
                    Field f = fs[i];
                    f.setAccessible(true); //设置些属性是可以访问的
                    Class<?> fType = f.getType();
                    boolean flag = isNotSurperType(fType);
                    if(flag){//处理不支持的类型
                        Map map = convertObjectToJsonMap(f.get(bean));
                        mapParam.put(f.getName(),map);
                    }else{
                        if(String.class.isAssignableFrom(fType)){
                            mapParam.put(f.getName(),f.get(bean)==null?"":f.get(bean));
                        }else if(Number.class.isAssignableFrom(fType)){
                            mapParam.put(f.getName(),f.get(bean)==null?0:f.get(bean));
                        }else{
                            mapParam.put(f.getName(),f.get(bean));
                        }
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return mapParam;
    }

    /**
     * 判断是否为JsonUtils不支持的类型
     * @param fType
     * @return
     */
    private static boolean isNotSurperType(Class fType) {
        if(String.class.isAssignableFrom(fType) || Number.class.isAssignableFrom(fType) || Boolean.class.isAssignableFrom(fType)
                || Date.class.isAssignableFrom(fType)|| Collection.class.isAssignableFrom(fType) || Throwable.class.isAssignableFrom(fType)
                || int[].class.isAssignableFrom(fType) || long[].class.isAssignableFrom(fType) || TabularData.class.isAssignableFrom(fType)
                || CompositeData.class.isAssignableFrom(fType) || Map.class.isAssignableFrom(fType)){
            return false;
        }
        return true;
    }

    /**
     * 只转换非空字段
     * @param bean
     * @return
     */
    public static Map convertNonNullObjectToJsonMap(Object bean){

        Map<String, Object> mapParam = new HashMap<String, Object>();
        try {
            List<Object> fileList = getAllFiledList(bean.getClass());
            for(int i = 0 ; i < fileList.size(); i++){
                try{
                    Field f = (Field) fileList.get(i);
                    f.setAccessible(true); //设置些属性是可以访问的
                    Class<?> fType = f.getType();
                    boolean flag = isNotSurperType(fType);
                    if(flag){//处理不支持的类型
                        Map map = convertObjectToJsonMap(f.get(bean));
                        mapParam.put(f.getName(),map);
                    }else{
                        if (f.get(bean) != null){
                            mapParam.put(f.getName(),f.get(bean));
                        }
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return mapParam;
    }

    /**
     * 将实体类转换成对应Object，类属性名称需要一致
     * apache 工具类在转换时报错，自己写着用，仅转换参数类和目标类字段相同的部分 yangchangwei
     * @param bean 需要转换的实体类
     * @param T 转换结果的类型
     * @return 返回转换后的实体
     */
    public static Object convertBeanToObject(Object bean,Class T){
        Object obj = null;
        try {
            obj = T.newInstance();
            Field[] fs = T.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                String mtdName = "set"+ f.getName().substring(0,1).toUpperCase() + f.getName().substring(1);
                Method method;
                try{
                    method = T.getMethod(mtdName, f.getType());
                }catch (Exception e){
                    continue;
                }
                Class beanClass = bean.getClass();
                Field[] fs2 = beanClass.getDeclaredFields();
                for(int i2 = 0 ; i2 < fs2.length; i2++){
                    Field f2 = fs2[i2];
                    f2.setAccessible(true); //设置些属性是可以访问的
                    if(f2.getName().equals(f.getName())){
                        method.invoke(obj,f2.get(bean));
                    }
                }

            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return obj;
    }

    /**
     * 把Map<String, Object>转成Map<String, (String)Object>
     * @param mapParam
     * @return
     */
    public static Map<String, Object> convertToMapValString(Map<String, Object> mapParam) {
        if (mapParam == null) {
            return null;
        }
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            if (entry.getValue() == null) {
                newMap.put(entry.getKey(), null);
            } else {
                newMap.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return newMap;
    }
}
