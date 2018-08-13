package com.hyjf.cs.user.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


/**
 * 
 * 请求的工具类
 * @author sunss
 * @version hyjf 1.0
 * @since hyjf 1.0 2018年2月2日
 */
@Component
public class RequestUtil{

    /**
     * 
     * 获取用户ID
     * @author sunss
     * @return
     */
    public Integer getRequestUserId(HttpServletRequest request){
        return (Integer)request.getAttribute("userId");
    }
    
    /**
     * 
     * 获取登录用户名
     * @author sunss
     * @param request
     * @return
     */
    public String getRequestUserIdStr(HttpServletRequest request){
        return getRequestUserId(request)+"";
    }
    
    /**
     * 
     * 获取sign值
     * @author sunss
     * @param request
     * @return
     */
    public String getRequestSign(HttpServletRequest request){
        return (String)request.getAttribute("sign");
    }
    

}
