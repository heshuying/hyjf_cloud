package com.hyjf.cs.market.controller.app.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.market.service.MsgMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author lisheng
 * @version MsgMailController, v0.1 2018/8/13 17:51
 */
@Api( tags = "app端-修改短信与邮件是否开启")
@RestController
@RequestMapping(value = "/hyjf-app/user")
public class MsgMailController {
    private Logger logger = LoggerFactory.getLogger(MsgMailController.class);
    @Autowired
    private MsgMailService msgMailService;
    /** REQUEST_MAPPING */
    public static final String REQUEST_MAPPING = "/user/setmsgmail";
    /** 修改短信、邮件状态 */
    public static final String SET_STATUS = "/setStatus";

    @ResponseBody
    @ApiOperation(value = "修改短信与邮件是否开启",  notes = "修改短信与邮件是否开启")
    @PostMapping(value = "/setmsgmail/setStatus")
    public JSONObject setStatus(@RequestHeader(value = "userId" )Integer userId,  @RequestParam("smsOpenStatus") String smsOpenStatus, @RequestParam("emailOpenStatus") String emailOpenStatus) {
        JSONObject ret = new JSONObject();
        ret.put("request", REQUEST_MAPPING + SET_STATUS);
        logger.info("短信开启状态:{}, 邮件开启状态:{}", smsOpenStatus, emailOpenStatus);
        try {
            msgMailService.updateStatusByUserId(userId, smsOpenStatus, emailOpenStatus);
            ret.put("status", "0");
            ret.put("statusDesc", "设置成功");
            return ret;
        } catch (Exception e) {
            logger.error("失败...", e);
            ret.put("status", "2");
            ret.put("statusDesc", "设置失败");
            return ret;
        }
    }
}
