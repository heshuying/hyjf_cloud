package com.hyjf.pay.base;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import com.hyjf.common.util.DateEditor;
import com.hyjf.common.util.HtmlCleanEditor;

/**
 * <p>
 * BaseController
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class BaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new HtmlCleanEditor(true, true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }

    @ExceptionHandler({ Exception.class })
    public ModelAndView exception(Exception e) {
        ModelAndView modelAndView = new ModelAndView("/error/systemerror");
        modelAndView.addObject("errorhtml", e.getMessage());

        return modelAndView;
    }
}
