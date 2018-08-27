/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.app.find;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.AppFindSecurityCustomizeVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.security.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author fq
 * @version AppFindController, v0.1 2018/7/20 14:44
 */
@Api(tags = "app端-安全保障数据")
@RestController
@RequestMapping("/hyjf-app/find")
public class AppSecurityController extends BaseController {
    /** 平台上线时间 */
    private static final String PUT_ONLINE_TIME = "2013-12";
    @Autowired
    private SecurityService securityService;

    @ApiOperation(value = "安全保障数据", notes = "安全保障数据")
    @GetMapping("/security")
    public JSONObject getSecurityMessage() {
        JSONObject ret = new JSONObject();
        ret.put("status", "000");
        ret.put("statusDesc", "请求成功");
        try {
            AppFindSecurityCustomizeVO appFindSecurityCustomize = new AppFindSecurityCustomizeVO();
            BigDecimal bigDecimal = securityService.selectTotalInvest();
            String TotalCount = bigDecimal.divide(new BigDecimal(100000000), 0, BigDecimal.ROUND_DOWN).toString();
            BigDecimal bigDecimal1 = securityService.selectTotalInterest();
            String IotalInterest = bigDecimal1.divide(new BigDecimal(100000000), 0, BigDecimal.ROUND_DOWN).toString();

            String yearFromDate = String.valueOf(GetDate.getYearFromDate(PUT_ONLINE_TIME));
            //平台累计投资笔数
            int tenderCount = securityService.selectTotalTradeSum();
            BigDecimal total = new BigDecimal(tenderCount);
            String totalTradeVolume = total.divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_DOWN).toString();
            // 平台累计投资者
            appFindSecurityCustomize.setTotalInvester(totalTradeVolume + "万");
            // 累计收益
            appFindSecurityCustomize.setTotalUserIncome(IotalInterest + "亿");
            // 成立年份
            appFindSecurityCustomize.setStartYear("2013");
            // 运营多少年
            appFindSecurityCustomize.setOperateYear(yearFromDate);
            // 企业评级
            appFindSecurityCustomize.setCompanyGrade("AAA");
            // 平台累计投资
            appFindSecurityCustomize.setTotalTradeVolume(TotalCount + "亿");
            ret.put("info", appFindSecurityCustomize);
        } catch (Exception e) {
            ret.put("status", "999");
            ret.put("statusDesc", "系统异常请稍后再试");
            ret.put("info", new AppFindSecurityCustomizeVO());
        }
        return ret;
    }
}
