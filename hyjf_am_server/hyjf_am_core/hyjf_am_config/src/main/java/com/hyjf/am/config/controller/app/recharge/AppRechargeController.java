package com.hyjf.am.config.controller.app.recharge;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.BankSettingService;
import com.hyjf.am.config.service.app.AppRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private BankSettingService bankSettingService;

    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    /**
     * 获取充值规则
     * @return
     */
    @PostMapping("/getRechargeRule")
    public Response getRechargeRule() {
        Response response = new Response();
        response.setResultList(appRechargeService.getRechargeRule());
        return response;
    }

    /**
     * 获取充值限额说明
     * @return
     */
    @PostMapping("/getRechargeLimit")
    public AdminBankSettingResponse getRechargeLimit() {
        AdminBankSettingResponse response = new AdminBankSettingResponse();
        List<JxBankConfig> recordList = bankSettingService.getRecordList(new JxBankConfig(), -1, -1);
        if (CollectionUtils.isNotEmpty(recordList)) {
            recordList.stream().forEach(p -> {
                p.setBankIcon(DOMAIN_URL + p.getBankIcon());
                p.setBankLogo(DOMAIN_URL + p.getBankLogo());
            });
            List<JxBankConfigVO> jxBankConfigList = CommonUtils.convertBeanList(recordList, JxBankConfigVO.class);
            response.setResultList(jxBankConfigList);
        } else {
            response.setRtn(Response.FAIL);
        }
        return response;
    }
}
