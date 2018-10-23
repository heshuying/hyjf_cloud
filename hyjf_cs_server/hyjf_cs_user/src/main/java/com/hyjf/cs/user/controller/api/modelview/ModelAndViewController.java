package com.hyjf.cs.user.controller.api.modelview;


import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.constants.ErrorViewConstant;
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

    private static final String MODEL_AND_VIEW = "modelAndView";
    private static final String CALL_BACK_ACTION = "callBackAction";
    private static final String HIDE_SUBMIT_JSP_ERROR = "/api/api_error_send.html";


    @RequestMapping("/callBackForm/error")
    public ModelAndView callbackErrorForm(@ModelAttribute(ErrorViewConstant.ERROR_FORM_BEAN) Map<String,Object> map){
        Object modelObject = map.get(MODEL_AND_VIEW);
        String callBackAction = "";
        Map<String,Object> param = new HashMap<>();
        if (modelObject != null){
            ModelAndView modelAndView = (ModelAndView) modelObject;
            param = modelAndView.getModel();
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
        ModelAndView modelAndView = new ModelAndView(HIDE_SUBMIT_JSP_ERROR);
        //callBackAction = "http://10.10.2.72:8082/hyjf-web/assetmanage/test111";
        modelAndView.addObject(CALL_BACK_ACTION,StringUtils.isBlank(callBackAction) ? "/" : callBackAction);
        modelAndView.addObject("params",param);
        return modelAndView;
    }
}
