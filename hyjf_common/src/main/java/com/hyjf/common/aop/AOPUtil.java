/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月14日 上午9:52:58
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.common.aop;

import org.aspectj.lang.JoinPoint;

/**
 * @author lb
 */

public class AOPUtil {
    // 获取方法参数字符串
	protected static String getMethodArgs(JoinPoint jp) {
		Object[] arguments = jp.getArgs();
	    // 遍历获取参数类型，以及参数值
	    StringBuffer sb = new StringBuffer();
	    if (arguments != null && arguments.length > 0) {
	        for (Object argument : arguments) {
	            if (argument != null
	            	&& !argument.getClass().getSimpleName().equals("RequestFacade") 
	        		&& !argument.getClass().getSimpleName().equals("ResponseFacade") ) {
	                sb.append("参数类型(" + argument.getClass().getSimpleName() + ")");
	                sb.append("参数值(" + argument.toString() + ")");
	            }
	        }
	    }
	    return sb.toString();
	}
}

	