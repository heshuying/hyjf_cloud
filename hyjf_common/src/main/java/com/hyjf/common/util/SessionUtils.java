/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月23日 上午9:57:11
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author Administrator
 */

public class SessionUtils {

	/**
	 * Session中放置数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void setSession(String key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(key, value);
	}

	/**
	 * 从Session中获取数据
	 * 
	 * @param key
	 * @param value
	 */
	public static Object getSession(String key) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return (Object) session.getAttribute(key);
	}
}
