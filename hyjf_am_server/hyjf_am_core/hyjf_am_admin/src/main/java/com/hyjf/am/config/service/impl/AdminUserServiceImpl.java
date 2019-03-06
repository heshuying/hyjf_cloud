package com.hyjf.am.config.service.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.config.dao.mapper.auto.AdminAndRoleMapper;
import com.hyjf.am.config.dao.mapper.auto.AdminMapper;
import com.hyjf.am.config.dao.mapper.auto.AdminRoleMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.dao.model.auto.AdminExample.Criteria;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;
import com.hyjf.am.config.service.AdminUserService;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class AdminUserServiceImpl implements AdminUserService {

	private static final Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Autowired
    AdminCustomizeMapper adminCustomizeMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AdminAndRoleMapper adminAndRoleMapper;
    @Autowired
    AdminRoleMapper adminRoleMapper;
    
    /**
     * 短信mq生产端
     */
    @Autowired
	private CommonProducer commonProducer;
	/**
	 * 获取账户列表
	 *
	 * @return
	 */
	public List<AdminCustomize> getRecordList(AdminCustomize adminCustomize) {
		return adminCustomizeMapper.selectAdminList(adminCustomize);
	}

	/**
	 * 获取单个账户
	 *
	 * @return
	 */
	public AdminCustomize getRecord(Integer id) {
		AdminCustomize adminCustomize = new AdminCustomize();
		adminCustomize.setId(id);
		List<AdminCustomize> adminList = adminCustomizeMapper.selectAdminList(adminCustomize);
		if (adminList != null && adminList.size() > 0) {
			return adminList.get(0);
		}
		return new AdminCustomize();
	}

	/**
	 * 根据主键判断账户中数据是否存在
	 *
	 * @return
	 */
	public boolean isExistsRecord(Integer id) {
		if (Validator.isNull(id)) {
			return false;
		}
		AdminExample example = new AdminExample();
		AdminExample.Criteria cra = example.createCriteria();
		cra.andIdEqualTo(id);
		int cnt = adminMapper.countByExample(example);
		return cnt > 0;
	}
	@Value("${hyjf.admin.login.url}")
	private String adminUrl;
	/**
	 * 账户插入
	 *
	 * @param form
	 */
	public void insertRecord(AdminRequest form) {
		Admin record = new Admin();
		BeanUtils.copyProperties(form, record);

		// 插入用户表
		String password = GetCode.getRandomPassword(8);
		record.setPassword(MD5.toMD5Code(password));
		record.setCreateTime(new Date());
		record.setCreateUserId( form.getAdminId());
		record.setUpdateTime(new Date());
		record.setUpdateUserId(form.getAdminId());

		int cnt = adminMapper.insertSelective(record);

		// 插入用户权限关联表
		if (Validator.isNotNull(form.getRoleId()) && cnt > 0) {
			AdminAndRole adminAndRole = new AdminAndRole();
			adminAndRole.setUserId(record.getId());
			adminAndRole.setRoleId(GetterUtil.getInteger(form.getRoleId()));
			adminAndRoleMapper.insertSelective(adminAndRole);
//			if (userId.equals(String.valueOf(record.getId()))) {
//				// 更新权限
//				ShiroUtil.updateAuth();
//			}
		}

		// 发送短信
		Map<String, String> replaceStrs = new HashMap<String, String>();
		replaceStrs.put("var_yonghuming", record.getUsername());
		replaceStrs.put("var_htmm", password);
		replaceStrs.put("var_htdizhi", adminUrl);
//		SmsMessage smsMessage = new SmsMessage(null, replaceStrs, record.getMobile(), null, MessageDefine.SMSSENDFORMOBILE, null, CustomConstants.PARAM_TPL_GLYMM, CustomConstants.CHANNEL_TYPE_NORMAL);
//		smsProcesser.gather(smsMessage);
		
        SmsMessage smsMessage = new SmsMessage(null, replaceStrs,  record.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
                CustomConstants.PARAM_TPL_GLYMM, CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
			commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                    smsMessage));
        } catch (MQException e) {
            logger.error(e.getMessage());
        }
	}

	/**
	 * 账户更新
	 *
	 * @param form
	 */
	public void updateRecord(AdminRequest form) {
//		String nowTime = GetDate.getServerDateTime(9, new Date());
//		String userId = ShiroUtil.getLoginUserId();
		Admin record = new Admin();
		BeanUtils.copyProperties(form, record);

		record.setUpdateTime(new Date());
		record.setUpdateUserId(form.getAdminId());
		adminMapper.updateByPrimaryKeySelective(record);

		// 插入用户权限关联表
		if (Validator.isNotNull(form.getRoleId())) {
			AdminAndRoleExample example = new AdminAndRoleExample();
			example.createCriteria().andUserIdEqualTo(record.getId());
			int cnt = adminAndRoleMapper.countByExample(example);
			AdminAndRole adminAndRole = new AdminAndRole();
			adminAndRole.setUserId(record.getId());
			adminAndRole.setRoleId(GetterUtil.getInteger(form.getRoleId()));
			if (cnt == 0) {
				adminAndRoleMapper.insertSelective(adminAndRole);
			} else {
				adminAndRoleMapper.updateByExampleSelective(adminAndRole, example);
			}
			if (form.getAdminId()==record.getId()) {
				// 更新权限
//				ShiroUtil.updateAuth();
			}
		}
	}

	/**
	 * 账户删除
	 *
	 * @param ids
	 */
	public void deleteRecord(List<Integer> ids,int adminId) {
//		String nowTime = GetDate.getServerDateTime(9, new Date());
//		String userId = ShiroUtil.getLoginUserId();
		Admin record = new Admin();
		record.setState("1");
		record.setDelFlag(1);
		record.setUpdateTime(new Date());
		record.setUpdateUserId(adminId);

		AdminExample example = new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		adminMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 检查手机号码唯一性
	 *
	 * @param id
	 * @param mobile
	 */
	public int countAdminByMobile(Integer id, String mobile) {
		AdminExample example = new AdminExample();
		AdminExample.Criteria criteria = example.createCriteria();
		if (Validator.isNotNull(id)) {
			criteria.andIdNotEqualTo(id);
		}
		criteria.andMobileEqualTo(mobile);
		criteria.andDelFlagEqualTo(0);
		int cnt = adminMapper.countByExample(example);
		return cnt;
	}

	/**
	 * 检查用户名唯一性
	 *
	 * @param id
	 * @param username
	 */
	public int countAdminByUsername(Integer id, String username) {
		AdminExample example = new AdminExample();
		AdminExample.Criteria criteria = example.createCriteria();
		if (Validator.isNotNull(id)) {
			criteria.andIdNotEqualTo(id);
		}
		criteria.andDelFlagEqualTo(0);
		criteria.andUsernameEqualTo(username);
		//criteria.andDelFlagEqualTo("0");// 未删除
		int cnt = adminMapper.countByExample(example);
		return cnt;
	}

	/**
	 * 检查邮箱唯一性
	 *
	 * @param id
	 * @param email
	 */
	public int countAdminByEmail(Integer id, String email) {
		AdminExample example = new AdminExample();
		AdminExample.Criteria criteria = example.createCriteria();
		if (Validator.isNotNull(id)) {
			criteria.andIdNotEqualTo(id);
		}
		criteria.andDelFlagEqualTo(0);
		criteria.andEmailEqualTo(email);
		int cnt = adminMapper.countByExample(example);
		return cnt;
	}

	@Override
	public void resetPwdAction(List<Integer> ids,int adminId) {

//		String nowTime = GetDate.getServerDateTime(9, new Date());
//		String userId = ShiroUtil.getLoginUserId();
		String password = GetCode.getRandomPassword(8);

		Admin record = new Admin();

		record.setPassword(MD5.toMD5Code(password));
		record.setUpdateTime(new Date());
		record.setUpdateUserId(adminId);

		AdminExample example = new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		adminMapper.updateByExampleSelective(record, example);

		List<Admin> list = adminMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			for (Admin admin : list) {
				// 发送短信
				Map<String, String> replaceStrs = new HashMap<String, String>();
				replaceStrs.put("var_name", admin.getTruename());
				replaceStrs.put("var_yonghuming", admin.getUsername());
				replaceStrs.put("var_htmm", password);
//				SmsMessage smsMessage = new SmsMessage(null, replaceStrs, admin.getMobile(), null, MessageDefine.SMSSENDFORMOBILE, null, CustomConstants.PARAM_TPL_RESETPWD, CustomConstants.CHANNEL_TYPE_NORMAL);
//				smsProcesser.gather(smsMessage);
		        SmsMessage smsMessage = new SmsMessage(null, replaceStrs,  admin.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
		                CustomConstants.PARAM_TPL_RESETPWD, CustomConstants.CHANNEL_TYPE_NORMAL);
		        try {
					commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
		                    smsMessage));
		        } catch (MQException e) {
		            logger.error(e.getMessage());
		        }
			}
		}
	}


	@Override
	public List<AdminRole> getAdminRoleList() {
		AdminRoleExample example = new AdminRoleExample();
		com.hyjf.am.config.dao.model.auto.AdminRoleExample.Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(0);
			criteria.andDelFlagEqualTo(0);
		example.setOrderByClause(" sort ");
		return adminRoleMapper.selectByExample(example);
	}

    @Override
    public Admin getAdminByName(String auditUser) {
		AdminExample example = new AdminExample();
		example.createCriteria().andUsernameEqualTo(auditUser);
		List<Admin> admins = adminMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(admins)) {
			return admins.get(0);
		}
		return null;
    }
}
