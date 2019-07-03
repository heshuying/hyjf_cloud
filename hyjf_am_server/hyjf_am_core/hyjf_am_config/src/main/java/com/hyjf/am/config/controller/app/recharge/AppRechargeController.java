package com.hyjf.am.config.controller.app.recharge;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.JxBankConfigService;
import com.hyjf.am.config.service.app.AppRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppRechargeLimitResponse;
import com.hyjf.am.response.app.AppRechargeRuleResponse;
import com.hyjf.am.vo.app.recharge.AppRechargeLimitVO;
import com.hyjf.am.vo.app.recharge.AppRechargeRuleVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wgx
 * @date 2019/4/17
 */
@RestController
@RequestMapping("/am-config/recharge")
public class AppRechargeController {

    @Autowired
    private AppRechargeService appRechargeService;

    @Autowired
    private JxBankConfigService jxBankConfigService;

    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    /**
     * 获取充值规则
     * @return
     */
    @PostMapping("/getRechargeRule")
    public AppRechargeRuleResponse getRechargeRule() {
        AppRechargeRuleResponse response = new AppRechargeRuleResponse();
        response.setResultList(appRechargeService.getRechargeRule());
        return response;
    }

    /**
     * 获取充值限额说明
     * @return
     */
    @PostMapping("/getRechargeLimit")
    public AppRechargeLimitResponse getRechargeLimit() {
        AppRechargeLimitResponse response = new AppRechargeLimitResponse();
        List<JxBankConfig> recordList = jxBankConfigService.getQuickPaymentJxBankConfig();
        if (CollectionUtils.isNotEmpty(recordList)) {
            List<AppRechargeLimitVO> appRechargeLimitList = new ArrayList<>();
            recordList.stream().forEach(record -> {
                AppRechargeLimitVO appRechargeLimitVO = new AppRechargeLimitVO();
                appRechargeLimitVO.setBankName(record.getBankName());// 银行名称
                appRechargeLimitVO.setBankIcon(DOMAIN_URL + record.getBankIcon());// 银行logo
                //单月单卡限额
                appRechargeLimitVO.setMonthly(record.getMonthCardQuota().compareTo(BigDecimal.ZERO) == 0 ?
                        "不限" : record.getMonthCardQuota().divide(new BigDecimal(10000)) + "万元");
                //单卡单日限额
                appRechargeLimitVO.setDaily(record.getSingleCardQuota().compareTo(BigDecimal.ZERO) == 0 ?
                        "不限" : record.getSingleCardQuota().divide(new BigDecimal(10000)) + "万元");
                //单笔限额
                appRechargeLimitVO.setSingle(record.getSingleQuota().compareTo(BigDecimal.ZERO) == 0 ?
                        "不限" : record.getSingleQuota().divide(new BigDecimal(10000)) + "万元");
                appRechargeLimitList.add(appRechargeLimitVO);
            });
            response.setResultList(appRechargeLimitList);
        } else {
            response.setRtn(Response.FAIL);
        }
        return response;
    }
}
