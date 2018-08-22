/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message.impl;

import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.dao.mapper.auto.SmsCountMapper;
import com.hyjf.am.user.dao.mapper.customize.SmsCountCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.SmsCountExample;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

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
    public int selectCount() {
        return smsCountMapper.countByExample(new SmsCountExample());
    }
}
