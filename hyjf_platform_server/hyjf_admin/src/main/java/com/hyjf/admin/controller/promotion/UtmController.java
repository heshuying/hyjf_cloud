package com.hyjf.admin.controller.promotion;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.promotion.UtmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Api(value = "渠道管理列表")
@RestController
@RequestMapping("/hyjf-admin/promotion/utm")
public class UtmController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UtmController.class);

    @Autowired
    private UtmService utmService;

    @ApiOperation(value = "页面初始化", notes = "活动列表")
    @PostMapping("/init")
    public JSONObject activityListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject = utmService.getByPageList(map,Integer.parseInt(getCurrPage(map)),Integer.parseInt(getPageSize(map)));
        return jsonObject;
    }
}
