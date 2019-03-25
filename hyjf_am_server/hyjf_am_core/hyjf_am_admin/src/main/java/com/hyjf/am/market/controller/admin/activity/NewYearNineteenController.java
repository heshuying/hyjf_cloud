package com.hyjf.am.market.controller.admin.activity;

import com.hyjf.am.market.service.NewYearNineteenService;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiehuili on 2019/3/25.
 */
@Controller("/am-admin/newYearNineteen")
public class NewYearNineteenController {

    @Autowired
    private NewYearNineteenService newYearNineteenService;

    /**
     * 累计年化出借金额 画面初始化
     * @param newYearNineteenRequestBean
     * @return
     */
    @RequestMapping("/init")
    public NewYearActivityResponse init(@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) {
        return null;
    }
}