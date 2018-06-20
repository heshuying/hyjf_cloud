package com.hyjf.cs.user.controller.wechat.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.DateEditor;
import com.hyjf.common.util.HtmlCleanEditor;
import com.hyjf.common.validator.Validator;
import com.hyjf.wechat.util.RequestUtil;

/**
 * <p>
 * BaseController
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class BaseController extends MultiActionController {

    @Autowired
    public RequestUtil requestUtil;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new HtmlCleanEditor(true, true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }

    @ExceptionHandler({ Exception.class })
    public ModelAndView exception(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/500");
        e.printStackTrace();
        return modelAndView;
    }

    
    /**
     * 组成返回信息
     *
     * @param message
     * @param status
     * @return
     */
    public JSONObject jsonMessage(String message, String status) {
        JSONObject jo = null;
        if (Validator.isNotNull(message)) {
            jo = new JSONObject();
            jo.put("message", message);
            jo.put("error", status);
        }
        return jo;
    }

    /**
     * 
     * 特殊字符编码
     * @param str
     * @return
     * @throws Exception
     */
    public String strEncode(String str) {
        try {
        	if(str!=null){
        		str = URLEncoder.encode(str, "utf-8");
        	}else{
        		str = "";
        	}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
