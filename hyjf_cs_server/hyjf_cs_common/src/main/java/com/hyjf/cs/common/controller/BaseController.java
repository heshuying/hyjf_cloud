/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.common.controller;

import com.hyjf.cs.common.util.ApiSignUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 组合层共用Controller基类
 *
 * @author liubin
 * @version BaseController, v0.1 2018/6/15 19:18
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CALL_BACK_ACTION = "callBackAction";
    private static final String HIDE_SUBMIT_JSP_ERROR = "api/api_error_send.html";
    private static final String CHECK_SIGN_STR = "status";
    private static final String CHECK_VALUE = "chkValue";

    // 联调阶段方便前端用错误信息，调试完成后要删除。
    private String getMessageForTest(Exception ex) {
        return "---《" + ex.getMessage() + "》";
    }


    /**
     * 特殊字符编码
     *
     * @return
     * @throws Exception
     * @author pangchengchao
     */
    public String strEncode(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return str;
    }

    /**
     * 重定向失败页面
     *
     * @author zhangyk
     * @date 2018/11/1 14:55
     */
    public ModelAndView callbackErrorView(ModelAndView errorView) {
        ModelAndView modelAndView = new ModelAndView(HIDE_SUBMIT_JSP_ERROR);  // 默认隐式提交的页面
        String callBackAction = "";
        Map<String, Object> param = new HashMap<>();
        if (errorView != null) {
            param = errorView.getModel();
            Set<String> keySet = param.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.equals(CALL_BACK_ACTION)) {
                    callBackAction = (String) param.get(key);
                    break;
                }
            }
        }
        //callBackAction = "http://10.10.2.72:8082/hyjf-web/assetmanage/test111";
        // 如果modelAndView中不包含回调地址，或者参数中包含status(加签使用)字段，则此处不做post第三方处理。
        if (StringUtils.isBlank(callBackAction) || param.get(CHECK_SIGN_STR) == null) {
            return errorView;
        }
        modelAndView.addObject(CALL_BACK_ACTION, callBackAction);
        // 重新加签，无论业务中是否加签,此处屏蔽业务内加签
        param.put(CHECK_VALUE, ApiSignUtil.encryptByRSA(String.valueOf(param.get(CHECK_SIGN_STR))));
        modelAndView.addObject("params", param);
        return modelAndView;
    }



    /**
     * 重载一下
     * @param param
     * @return
     */
    public ModelAndView callbackErrorView(Map<String, String> param) {
        ModelAndView modelAndView = new ModelAndView(HIDE_SUBMIT_JSP_ERROR);
        String callBackAction = "" ;
        if (param != null) {
            Set<String> keySet = param.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.equals(CALL_BACK_ACTION)) {
                    callBackAction = (String) param.get(key);
                    break;
                }
            }
            param.put(CHECK_VALUE, ApiSignUtil.encryptByRSA(String.valueOf(param.get(CHECK_SIGN_STR))));
        }
        modelAndView.addObject(CALL_BACK_ACTION, callBackAction);
        // 重新加签，无论业务中是否加签,此处屏蔽业务内加签
        modelAndView.addObject("params", param);
        return modelAndView;
    }

    /**
     * 重载一下
     * @param param
     * @return
     */
    public ModelAndView callbackErrorViewForMap(Map<String, Object> param) {
        ModelAndView modelAndView = new ModelAndView(HIDE_SUBMIT_JSP_ERROR);
        String callBackAction = "" ;
        if (param != null) {
            Set<String> keySet = param.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.equals(CALL_BACK_ACTION)) {
                    callBackAction = (String) param.get(key);
                    break;
                }
            }
        }
        modelAndView.addObject(CALL_BACK_ACTION, callBackAction);
        // 重新加签，无论业务中是否加签,此处屏蔽业务内加签
        param.put(CHECK_VALUE, ApiSignUtil.encryptByRSA(String.valueOf(param.get(CHECK_SIGN_STR))));
        modelAndView.addObject("params", param);
        return modelAndView;
    }


}
