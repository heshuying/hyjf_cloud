/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.common.util;

/**
 * @author sunpeikai
 * @version ReflectUtils, v0.1 2019/2/12 9:31
 */
public class ReflectUtils {

    /**
     * 通过堆栈信息获取上级调用的类名
     * 1:本类第19行调用，2：上级调用，3：上上级调用;以此类推
     * @auth sunpeikai
     * @param level 1:本类方法调用堆栈，2：上级调用，3：上上级调用;以此类推
     * @return
     */
    public static String getSuperiorClass(int level){
        StackTraceElement[] temp = Thread.currentThread().getStackTrace();
        StackTraceElement element = (StackTraceElement)temp[level];
        return "from --- >> " + element.getClassName() + " ------>>>>> " + element.getMethodName() + " ------>>>>>> " + element.getLineNumber();
    }
}
