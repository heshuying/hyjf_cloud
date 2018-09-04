/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.tender.hjh;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description wechat端-加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(tags = "weChat端-加入计划")
@RestController
@RequestMapping("/hyjf-wechat/wx/plantender")
public class WechatjhPlanController extends BaseTradeController {

    @Autowired
    private HjhTenderService hjhTenderService;

    @ApiOperation(value = "加入计划", notes = "加入计划")
    @PostMapping(value = "/joinPlan", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WeChatResult<Map<String, Object>> joinPlan(@RequestHeader(value = "userId") Integer userId, TenderRequest tender, HttpServletRequest request) {
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        WebResult result = new WebResult();
        WeChatResult weChatResult = new WeChatResult();
        try {
            result = hjhTenderService.joinPlan(tender);
            weChatResult.setObject(result.getData());
        } catch (CheckException e) {
            throw e;
        } finally {
            RedisUtils.del(RedisConstants.HJH_TENDER_REPEAT + tender.getUser().getUserId());
        }
        return weChatResult;
    }

    @ApiOperation(value = "获取计划投资信息", notes = "获取计划投资信息")
    @GetMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
    public WeChatResult<TenderInfoResult> getInvestInfo(@RequestHeader(value = "userId") Integer userId,TenderRequest tender) {
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        AppInvestInfoResultVO resultBean = hjhTenderService.getInvestInfoApp(tender);
        WeChatResult result = new WeChatResult();
        result.setObject(resultBean);
        return  result;
    }
}
