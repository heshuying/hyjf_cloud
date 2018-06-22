/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.Utils;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.am.response.config.AdminSystemResponse;

/**
 * @author DongZeShan
 * @version SessionUtil.java, v0.1 2018年6月21日 下午5:40:20
 */
public class SessionUtil {
	public AdminSystemResponse getSession(HttpServletRequest request) {
		AdminSystemResponse ar=null;
		ar=(AdminSystemResponse) request.getSession().getAttribute("user");
		if(ar==null) {
			ar=new AdminSystemResponse();
			ar.setMessage("未登录或已超时请请重新登陆");
		}
		return ar;
		
	}

}
