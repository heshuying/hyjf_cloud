/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.message.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.SmsCodeCustomizeMapper;
import com.hyjf.am.trade.service.admin.message.SmsCodeService;
import com.hyjf.common.util.GetDate;

/**
 * @author fq
 * @version SmsCodeServiceImpl, v0.1 2018/8/20 20:30
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private SmsCodeCustomizeMapper smsCodeCustomizeMapper;

    @Override
    public List<SmsCodeCustomize> queryUser(SmsCodeUserRequest request) {
        Map<String, Object> params = new HashMap<>();
		if (request.getOpen_account() != null && request.getOpen_account() != 3) {
            params.put("open_account", request.getOpen_account());
        }
        if (StringUtils.isNotBlank(request.getRe_time_begin())) {
            params.put("re_time_begin", GetDate.dateString2Timestamp(request.getRe_time_begin()));
            params.put("re_time_begin", GetDate.dateString2Timestamp(GetDate.getDayStart(request.getRe_time_begin())));
        }
        if (StringUtils.isNotBlank(request.getRe_time_end())) {
            params.put("re_time_end", GetDate.dateString2Timestamp(GetDate.getDayEnd(request.getRe_time_end())));
        }
        if (StringUtils.isNotBlank(request.getAdd_time_begin())) {
            params.put("add_time_begin", GetDate.dateString2Timestamp(GetDate.getDayStart(request.getAdd_time_begin())));
        }
        if (StringUtils.isNotBlank(request.getAdd_time_end())) {
            params.put("add_time_end", GetDate.dateString2Timestamp(GetDate.getDayEnd(request.getAdd_time_end())));
        }
        String addMoneyCount = request.getAdd_money_count();
		if (StringUtils.isNotBlank(addMoneyCount)) {
			params.put("add_money_count", new BigDecimal(addMoneyCount));
		}
        return smsCodeCustomizeMapper.queryUser(params);
    }
}
