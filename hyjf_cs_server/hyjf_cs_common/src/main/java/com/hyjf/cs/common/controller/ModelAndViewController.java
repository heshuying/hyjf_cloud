package com.hyjf.cs.common.controller;


import com.hyjf.cs.common.util.ErrorViewConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/redirectView")
public class ModelAndViewController extends BaseController {

    private static final String CALL_BACK_ACTION = "callBackAction";
    private static final String HIDE_SUBMIT_JSP_ERROR = "/api/api_error_send.html";


    /**
     * 隐式提交controller
     * ①post到第三方页面
     * ②在本项目直接显示错误信息
     * @author zhangyk
     * @date 2018/11/1 14:18
     */
    @RequestMapping("/callBackForm/error")
    public ModelAndView callbackErrorForm(@ModelAttribute(ErrorViewConstant.ERROR_FORM_BEAN)ModelAndView errorView){
        ModelAndView modelAndView = new ModelAndView(HIDE_SUBMIT_JSP_ERROR);  // 默认隐式提交的页面
        String callBackAction = "";
        Map<String,Object> param = new HashMap<>();
        if (errorView != null){
            param = errorView.getModel();
            Set<String> keySet = param.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                if (key.equals(CALL_BACK_ACTION)){
                    callBackAction = (String) param.get(key);
                    break;
                }
            }
            param.remove(CALL_BACK_ACTION);
        }
        //callBackAction = "http://10.10.2.72:8082/hyjf-web/assetmanage/test111";
        // 如果modelAndView中不包含回调地址，则认为要跳转本项目其他页面，此处不做处理。
        if (StringUtils.isBlank(callBackAction)){
            return errorView;
        }
        modelAndView.addObject(CALL_BACK_ACTION,callBackAction);
        modelAndView.addObject("params",param);
        return modelAndView;
    }
}
