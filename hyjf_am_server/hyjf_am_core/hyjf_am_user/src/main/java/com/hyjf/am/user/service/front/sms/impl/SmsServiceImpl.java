package com.hyjf.am.user.service.front.sms.impl;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.dao.mapper.customize.SmsCountCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.SmsCode;
import com.hyjf.am.user.dao.model.auto.SmsCodeExample;
import com.hyjf.am.user.service.front.sms.SmsService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiasq
 * @version SmsServiceImpl, v0.1 2018/4/12 17:32
 */
@Service
public class SmsServiceImpl extends BaseServiceImpl implements SmsService {

    @Autowired
    private SmsCountCustomizeMapper smsCountCustomizeMapper;

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

	@Override
	public int saveSmscode(String mobile, String verificationCode, String verificationType, Integer status, String platform) {
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
		smsCode.setPosttime((int) (System.currentTimeMillis()/1000));
		smsCode.setCreateTime(new Date());
		smsCode.setStatus(status);
		smsCode.setUserId(0);
		return smsCodeMapper.insertSelective(smsCode);
	}

	/**
	 * 检查短信验证码
	 * @param mobile
	 * @param verificationCode
	 * @param verificationType
	 * @param platform
	 * @param searchStatus
	 * @param updateStatus
	 * @return
	 */
	@Override
	public int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
									 Integer searchStatus, Integer updateStatus,boolean isUpdate) {
		int time = (int) (System.currentTimeMillis()/1000);
		// 15分钟有效 900
		int timeAfter = time - 900;
		SmsCodeExample example = new SmsCodeExample();
		SmsCodeExample.Criteria cra = example.createCriteria();
		cra.andPosttimeGreaterThanOrEqualTo(timeAfter);
		cra.andPosttimeLessThanOrEqualTo(time);
		cra.andMobileEqualTo(mobile);
		cra.andCheckcodeEqualTo(verificationCode);
		List<Integer> status = new ArrayList<Integer>();
		status.add(CommonConstant.CKCODE_NEW);
		status.add(searchStatus);
		cra.andStatusIn(status);
		List<SmsCode> codeList = smsCodeMapper.selectByExample(example);
		if (codeList != null && codeList.size() > 0) {
			for (SmsCode smsCode : codeList) {
				if (smsCode.getCheckfor().equals(MD5.toMD5Code(mobile + verificationCode + verificationType + platform))) {
					// 已验8或已读9
					smsCode.setStatus(updateStatus);
					if(isUpdate) {
						smsCodeMapper.updateByPrimaryKey(smsCode);
					}
					return 1;
				}
			}
			return 0;
		} else {
			return 0;
		}
	}

	/**
	 * 只检查短信验证码对不对
	 *
	 * @param mobile
	 * @param verificationCode
	 * @param verificationType
	 * @param platform
	 * @param status
	 * @param updateStatus
	 * @return
	 */
	@Override
	public int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform, Integer status, Integer updateStatus) {
		int time = (int) (System.currentTimeMillis()/1000);
		// 15分钟有效 900
		int timeAfter = time - 900;
		SmsCodeExample example = new SmsCodeExample();
		SmsCodeExample.Criteria cra = example.createCriteria();
		cra.andPosttimeGreaterThanOrEqualTo(timeAfter);
		cra.andPosttimeLessThanOrEqualTo(time);
		cra.andMobileEqualTo(mobile);
		cra.andCheckcodeEqualTo(verificationCode);
		List<Integer> statusAll = new ArrayList<Integer>();
		statusAll.add(CommonConstant.CKCODE_NEW);
		statusAll.add(status);
		cra.andStatusIn(statusAll);
		List<SmsCode> codeList = smsCodeMapper.selectByExample(example);
		if (codeList != null && codeList.size() > 0) {
			for (SmsCode smsCode : codeList) {
				if (smsCode.getCheckfor().equals(MD5.toMD5Code(mobile + verificationCode + verificationType + platform))) {
					return 1;
				}
			}
			return 0;
		} else {
			return 0;
		}
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
	public List<String> queryUser(SmsCodeUserRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (request.getOpen_account() != null && request.getOpen_account() != 3) {
            params.put("open_account", request.getOpen_account());
        }
        if (StringUtils.isNotBlank(request.getRe_time_begin())) {
            params.put("re_time_begin", GetDate.getDayStart(request.getRe_time_begin()));
        }
        if (StringUtils.isNotBlank(request.getRe_time_end())) {
            params.put("re_time_end", GetDate.getDayEnd(request.getRe_time_end()));
        }
        return smsCountCustomizeMapper.queryUser(params);
	}

	/**
	 * 根据用户生日查询手机号码
	 * @param request
	 * @return
	 */
	@Override
	public List<String> queryUserByBirthday(SmsCodeUserRequest request) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(request.getBirthday()) ) {
			params.put("birthday", "%"+request.getBirthday());
		}
		return smsCountCustomizeMapper.queryUserByBirthday(params);
	}

}
