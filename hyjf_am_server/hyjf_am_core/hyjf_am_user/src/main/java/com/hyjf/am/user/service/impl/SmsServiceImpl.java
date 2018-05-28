package com.hyjf.am.user.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.user.dao.mapper.auto.SmsCodeMapper;
import com.hyjf.am.user.dao.model.auto.SmsCode;
import com.hyjf.am.user.dao.model.auto.SmsCodeExample;
import com.hyjf.am.user.service.SmsService;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.MD5;

/**
 * @author xiasq
 * @version SmsServiceImpl, v0.1 2018/4/12 17:32
 */
@Service
public class SmsServiceImpl implements SmsService {
	@Autowired
	private SmsCodeMapper smsCodeMapper;

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
		smsCode.setCreateTime(new Date());
		smsCode.setStatus(status);
		smsCode.setUserId(0);
		return smsCodeMapper.insertSelective(smsCode);
	}

	@Override
	public int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform, Integer status, Integer updateStatus) {

		SmsCodeExample example = new SmsCodeExample();
		SmsCodeExample.Criteria cra = example.createCriteria();

		Calendar can = Calendar.getInstance();
		cra.andCreateTimeLessThanOrEqualTo(can.getTime());
		can.add(Calendar.MINUTE, -15);
		can.getTime();
		cra.andCreateTimeGreaterThanOrEqualTo(can.getTime());

		cra.andMobileEqualTo(mobile);
		cra.andCheckcodeEqualTo(verificationCode);
		List<Integer> list = new ArrayList<Integer>();
		//短信验证码状态,新验证码
		list.add(0);
		list.add(status);
		cra.andStatusIn(list);
		List<SmsCode> codeList = smsCodeMapper.selectByExample(example);
		if (codeList != null && codeList.size() > 0) {
			for (SmsCode smsCode : codeList) {
				if (smsCode.getCheckfor().equals(MD5.toMD5Code(mobile + verificationCode + verificationType + platform))) {
					// 已验8或已读9
					smsCode.setStatus(updateStatus);
					smsCodeMapper.updateByPrimaryKey(smsCode);
					return 1;
				}
			}
			return 0;
		} else {
			return 0;
		}
	}

	@Override
	public int saveSmsCode(String mobile, String verificationCode, String verificationType, Integer status, String platform) {
		// 使之前的验证码无效
		SmsCodeExample example = new SmsCodeExample();
		SmsCodeExample.Criteria cra = example.createCriteria();
		cra.andMobileEqualTo(mobile);
		List<Integer> statusList = new ArrayList<Integer>();
		//短信验证码状态,新验证码
		statusList.add(0);
		//短信验证码状态,已验
		statusList.add(8);
		cra.andStatusIn(statusList);
		List<SmsCode> codeList = smsCodeMapper.selectByExample(example);
		if (codeList != null && codeList.size() > 0) {
			for (SmsCode smsCode : codeList) {
				// 失效7
				smsCode.setStatus(7);
				smsCodeMapper.updateByPrimaryKey(smsCode);
			}
		}
		// 保存新验证码到数据库
		SmsCode smsCode = new SmsCode();
		smsCode.setCheckfor(MD5.toMD5Code(mobile + verificationCode + verificationType + platform));
		smsCode.setMobile(mobile);
		smsCode.setCheckcode(verificationCode);
		smsCode.setCreateTime(new Date());
		smsCode.setStatus(status);
		smsCode.setUserId(0);
		return smsCodeMapper.insertSelective(smsCode);
	}
}
