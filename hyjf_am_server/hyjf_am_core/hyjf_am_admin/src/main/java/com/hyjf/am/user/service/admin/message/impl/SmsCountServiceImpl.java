/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message.impl;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.dao.mapper.auto.SmsCountMapper;
import com.hyjf.am.user.dao.mapper.customize.SmsCountCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version SmsCountServiceImpl, v0.1 2018/8/20 16:28
 */
@Service
public class SmsCountServiceImpl implements SmsCountService {
    @Autowired
    private SmsCountCustomizeMapper smsCountCustomizeMapper;
    @Autowired
    private SmsCountMapper smsCountMapper;

    @Override
    public List<SmsCountCustomize> querySmsCountLlist(SmsCountRequest request) {
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            request.setLimitStart((request.getCurrPage() - 1) * request.getPageSize());
            request.setLimitEnd(request.getPageSize());
        }
        List<SmsCountCustomize> list = smsCountCustomizeMapper.querySmsCountLlist(request);
        String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
        if (StringUtils.isEmpty(configMoney)) {
            configMoney = "0.042";//短信单价（0.042元/条）
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        if (!CollectionUtils.isEmpty(list)) {
            for (SmsCountCustomize sms: list) {
                sms.setSmsMoney(decimalFormat.format(new BigDecimal(configMoney).multiply(new BigDecimal(sms.getSmsNumber()))));
            }
            return list;
        }
        return null;
    }

    @Override
    public Integer querySmsCountNumberTotal(SmsCountRequest request) {
        return smsCountCustomizeMapper.querySmsCountNumberTotal(request);
    }

    @Override
    public List<OADepartmentCustomize> queryDepartmentInfo() {
        return smsCountCustomizeMapper.queryDepartmentInfo();
    }

    @Override
    public int selectCount(SmsCountRequest request) {
        return smsCountCustomizeMapper.selectCount(request);
    }

    @Override
    public List<String> queryUser(SmsCodeUserRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (request.getOpen_account() != null && request.getOpen_account() != 3) {
            params.put("open_account", request.getOpen_account());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getRe_time_begin())) {
            params.put("re_time_begin", GetDate.dateString2Timestamp(GetDate.getDayStart(request.getRe_time_begin())));
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getRe_time_end())) {
            params.put("re_time_end", GetDate.dateString2Timestamp(GetDate.getDayEnd(request.getRe_time_end())));
        }
        return smsCountCustomizeMapper.queryUser(params);
    }
}
