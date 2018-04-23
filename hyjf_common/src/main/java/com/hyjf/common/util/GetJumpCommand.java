package com.hyjf.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class GetJumpCommand {
    
    public static String getLinkJumpPrefix(HttpServletRequest request, String version) {
        
        if(StringUtils.isEmpty(version)){
            version = request.getParameter("version");
            if(StringUtils.isEmpty(version)){
                version = request.getAttribute("version")==null? "" : (String)request.getAttribute("version");
            }
        }
        
        if(StringUtils.isEmpty(version)){
            return "hyjf";
        }
        
        String pcode = "";
        String vers[] = version.split("\\."); // 取渠道号
        if(vers != null && vers.length > 3){
            pcode = vers[3] ;
        }
        
        if(StringUtils.isEmpty(pcode)){
            return "hyjf";
        }
        
        
        if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
            return "hyjf";
            
        } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
            return "hyjfYXB";
            
        } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
            return "hyjfZNB";
            
        } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
            return "hyjfZYB";
            
        } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
            return "hyjfZZB";
            
        } else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
            return "hyjfTEST";
            
        } else {
            return "hyjf";
        }
        
    }

}
