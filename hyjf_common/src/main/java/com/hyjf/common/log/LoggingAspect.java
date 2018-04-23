/**
 * Description:公共Log切面文件
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: tianweihong
 * @version: 1.0.0
 * Created at: 2015年11月16日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */
package com.hyjf.common.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
public class LoggingAspect {

    /**
     * 创建一个切面，此处定义切面对象为针对service
     */
    @Pointcut("execution(* com.hyjf..*Service.*(..))")
    public void pointcut() {
    }

    /**
     * 在方法执行前进行，输出类名，方法名，参数类型，及参数名
     * 
     * @param jp
     */
    @Before("pointcut()")
    public void beforeMethod(JoinPoint jp) {
        // 获取类名
        String className = jp.getTarget().getClass().toString();
        // 获取方法名
        String methodName = jp.getSignature().getName();
        // 获取方法参数
        Object[] arguments = jp.getArgs();
        // 遍历获取参数类型，以及参数值
        if (arguments != null && arguments.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (Object argument : arguments) {
                if (argument != null) {
                    // 获取参数类型
                    sb.append("参数类型(" + argument.getClass().getName() + ")");
                    // 获取参数值
                    sb.append("参数值(" + argument.toString() + ")");
                }
            }
            // 输出相应的log信息
            LogUtil.startLog(className, methodName, sb.toString());
        } else {
            LogUtil.startLog(className, methodName);
        }

    }

    /**
     * 方法执行完后执行，输出类名，方法名
     * 
     * @param jp
     */
    @After("pointcut()")
    public void afterMethod(JoinPoint jp) {
        // 获取类名
        String className = jp.getTarget().getClass().toString();
        // 获取方法名
        String methodName = jp.getSignature().getName();
        LogUtil.endLog(className, methodName);
    }

    /**
     * 系统出现异常后执行 输出类名，方法名，参数类型，及参数名，异常信息
     * 
     * @param jp
     * @param ex
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint jp, Exception ex) {
        // 获取类名
        String className = jp.getTarget().getClass().toString();
        // 获取方法名
        String methodName = jp.getSignature().getName();
        // 获取方法参数
        Object[] arguments = jp.getArgs();
        // 遍历获取参数类型，以及参数值
        if (arguments != null && arguments.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (Object argument : arguments) {
                if (argument != null) {
                    sb.append("参数类型(" + argument.getClass().getName() + ")");
                    sb.append("参数值(" + argument.toString() + ")");
                }
            }
            LogUtil.errorLog(className, methodName, sb.toString(), ex);
        } else {
            LogUtil.errorLog(className, methodName, ex);
        }
    }
}
