/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message.impl;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.dao.mapper.auto.SmsCodeMapper;
import com.hyjf.am.user.dao.mapper.customize.SmsCodeCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.SmsCode;
import com.hyjf.am.user.dao.model.auto.SmsCodeExample;
import com.hyjf.am.user.dao.model.customize.SmsCodeCustomize;
import com.hyjf.am.user.service.admin.message.SmsCodeService;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author fq
 * @version SmsCodeServiceImpl, v0.1 2018/8/20 20:30
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Autowired
    private SmsCodeCustomizeMapper smsCodeCustomizeMapper;

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Override
    public List<SmsCodeCustomize> queryUser(SmsCodeUserRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (request.getOpen_account() != null) {
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

    /**
     * 校验千乐验证码
     * @param phone
     * @param code
     * @return
     */
    @Override
    public int checkQianleMobileCode(String phone, String code) {
        int time = GetDate.getNowTime10();
        int timeAfter = time - 180;
        SmsCodeExample example = new SmsCodeExample();
        SmsCodeExample.Criteria cra = example.createCriteria();
        cra.andPosttimeGreaterThanOrEqualTo(timeAfter);
        cra.andPosttimeLessThanOrEqualTo(time);
        cra.andMobileEqualTo(phone);
        cra.andCheckcodeEqualTo(code);
        return smsCodeMapper.countByExample(example);
    }


    @Override
    public int save(String mobile, String verificationType, String verificationCode, String platform, Integer status) {
        // 删除其他验证码
        SmsCodeExample example = new SmsCodeExample();
        SmsCodeExample.Criteria cra = example.createCriteria();
        cra.andMobileEqualTo(mobile);
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(CommonConstant.CKCODE_NEW);
        statusList.add(CommonConstant.CKCODE_YIYAN);
        cra.andStatusIn(statusList);
        List<SmsCode> codeList = smsCodeMapper.selectByExample(example);
        if (codeList != null && codeList.size() > 0) {
            for (SmsCode smsCode : codeList) {
                // 失效7
                smsCode.setStatus(CommonConstant.CKCODE_FAILED);
                smsCodeMapper.updateByPrimaryKey(smsCode);
            }
        }
        // 保存新验证码到数据库
        SmsCode smsCode = new SmsCode();
        smsCode.setCheckfor(MD5.toMD5Code(mobile + verificationCode + verificationType + platform));
        smsCode.setMobile(mobile);
        smsCode.setCheckcode(verificationCode);
        smsCode.setPosttime((int) (System.currentTimeMillis()/1000));
        smsCode.setCreateTime(new Date());
        smsCode.setStatus(status);
        smsCode.setUserId(0);
        return smsCodeMapper.insertSelective(smsCode);
    }
}
