/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.message;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.SmsCountCustomizeResponse;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;

/**
 * @author fq
 * @version SmsCountController, v0.1 2018/8/20 16:22
 */
@RestController
@RequestMapping("/am-user/sms_count")
public class SmsCountController extends BaseController {
    @Autowired
    private SmsCountService smsCountService;

    /**
     * 询短信统计，通过分公司显示
     * @param request
     * @return
     */
    @RequestMapping("/query_sms_count_list")
    public SmsCountCustomizeResponse querySmsCountLlist(@RequestBody SmsCountRequest request) {
        SmsCountCustomizeResponse response = new SmsCountCustomizeResponse();
        List<SmsCountCustomize> list = smsCountService.querySmsCountLlist(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<SmsCountCustomizeVO> voList = CommonUtils.convertBeanList(list, SmsCountCustomizeVO.class);
            response.setResultList(voList);
        }
        // 查询总条数
        int count = smsCountService.selectCount(request);
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
        if (StringUtils.isEmpty(configMoney)) {
            configMoney = "0.042";//短信单价（0.042元/条）
        }
        response.setCount(count);
        response.setSumMoney(decimalFormat.format(new BigDecimal(configMoney).multiply(new BigDecimal(count))));

        // 查询短信总条数
        int smsCount = smsCountService.querySmsCountNumberTotal(request);
        response.setSumCount(smsCount);
        return response;
    }

    /**
     * 查询询短信总条数+总费用
     * @param request
     * @return
     */
    @RequestMapping("/query_sms_count_number_total")
    public Integer querySmsCountNumberTotal(@RequestBody SmsCountRequest request) {
        return smsCountService.querySmsCountNumberTotal(request);
    }

    /**
     * 查询部门信息
     * @return
     */
    @RequestMapping("/query_department_info")
    public SmsCountCustomizeResponse queryDepartmentInfo() {
        SmsCountCustomizeResponse response = new SmsCountCustomizeResponse();
        List<OADepartmentCustomize> list = smsCountService.queryDepartmentInfo();
        if (!CollectionUtils.isEmpty(list)) {
            List<OADepartmentCustomizeVO> voList = CommonUtils.convertBeanList(list, OADepartmentCustomizeVO.class);
            response.setList(voList);
        }
        return response;
    }
}
