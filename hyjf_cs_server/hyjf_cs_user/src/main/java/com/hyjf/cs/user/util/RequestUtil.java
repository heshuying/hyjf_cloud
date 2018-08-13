package com.hyjf.cs.user.util;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import org.apache.commons.lang3.StringUtils;
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
        String userId=request.getHeader("userId");
        if(StringUtils.isBlank(userId)){
            throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        return Integer.valueOf(request.getHeader("userId"));
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
