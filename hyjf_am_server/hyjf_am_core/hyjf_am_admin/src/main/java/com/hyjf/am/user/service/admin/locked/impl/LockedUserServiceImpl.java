/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.locked.impl;

import com.google.common.base.Preconditions;
import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.user.controller.admin.locked.LockedConfigManager;
import com.hyjf.am.user.dao.mapper.auto.LockedUserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.LockedUserInfoCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.dao.model.auto.LockedUserInfoExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.admin.locked.LockedUserService;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cui
 * @version LockedUserServiceImpl, v0.1 2018/9/25 11:08
 */
@Service
public class LockedUserServiceImpl implements LockedUserService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LockedUserInfoMapper lockedUserInfoMapper;

	@Autowired
    private LockedUserInfoCustomizeMapper lockedUserInfoCustomizeMapper;

	@Override
	public int countRecordTotal(Map<String, Object> parameterMap) {
        return lockedUserInfoCustomizeMapper.countRecordTotal(parameterMap);
	}

	@Override
	public List<LockedUserInfo> getRecordList(Map<String, Object> parameterMap) {
		return lockedUserInfoCustomizeMapper.getRecordList(parameterMap);
	}

	@Override
	public Integer getLockedUserId(String username) {
		UserExample example1 = new UserExample();
		UserExample example2 = new UserExample();
		example1.createCriteria().andUsernameEqualTo(username);
		UserExample.Criteria c2 = example2.createCriteria().andMobileEqualTo(username);
		example1.or(c2);
		List<User> lstUser = userMapper.selectByExample(example1);
		Preconditions.checkArgument(lstUser.size() == 1);
		return lstUser.get(0).getUserId();
	}

	@Override
	public boolean unlock(LockedUserInfoVO infoVO, boolean isFront) {
		LockedUserInfo user = lockedUserInfoMapper.selectByPrimaryKey(infoVO.getId());

		if (user == null) {
			logger.warn("不存在ID=【{}】的锁定用户", infoVO.getId());
			return false;
		}

		String key = user.getUsername();

		if (isFront) {
			key = String.valueOf(getLockedUserId(user.getUsername()));
		}

		String redisKeyPrefix = isFront ? RedisConstants.PASSWORD_ERR_COUNT_ALL : RedisConstants.PASSWORD_ERR_COUNT_ADMIN;

		RedisUtils.del(redisKeyPrefix + key);

		user.setUnlocked(1);
		user.setUnlockTime(new Date());
		user.setOperator(infoVO.getOperator());

		LockedUserInfoExample example = new LockedUserInfoExample();

		example.or().andIdEqualTo(infoVO.getId());

		int result = lockedUserInfoMapper.updateByExample(user, example);

		String logInfo = isFront ? "解除前台锁定用户【{}】成功！更新记录个数【{}】" : "解除后台锁定用户【{}】成功！更新记录个数【{}】";
		logger.info(logInfo, user.getUsername(), result);

		return result>0;
	}

	/**
	 *@author koudeli
	 * @param userName 用户名，手机号
	 * @param loginPassword 登录密码
	 * @return
	 */
	public Map<String, String> insertErrorPassword(String userName, String loginPassword,Admin admin){
		Map<String, String> r=new HashMap<>();
		//1.获取该用户密码错误次数
		String passwordErrorNum=RedisUtils.get(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + userName);
		//2.获取用户允许输入的最大错误次数
		Integer maxLoginErrorNum=LockedConfigManager.getInstance().getAdminConfig().getMaxLoginErrorNum();
		//3.redis配置的超限有效时间
		long retTime  = RedisUtils.ttl(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + userName);
		//查询到的密码
		String passwordDb = admin.getPassword();
		// 页面传来的密码
		String password = MD5.toMD5Code(loginPassword);
		logger.info("passwordDB:[{}],password:[{}],相等:[{}]",passwordDb,password,password.equals(passwordDb));
        // 是否禁用
        if (admin.getState() == "1") {
            r.put("info","该用户已被禁用");
            return  r;
        }
		//判断密码错误次数是否超限
		if (!StringUtils.isEmpty(passwordErrorNum)&&Integer.parseInt(passwordErrorNum)>=maxLoginErrorNum) {
//			CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_TOO_MAX,DateUtils.SToHMSStr(retTime));
			r.put("info","您的登录失败次数超限，请"+DateUtils.SToHMSStr(retTime)+"之后重试!");
			return r;
		}
		if (!password.equals(passwordDb)) {
			long value = this.insertPassWordCount(RedisConstants.PASSWORD_ERR_COUNT_ADMIN+ userName);//以用户手机号为key
			for (int i=1;i<4;i++){
				if (maxLoginErrorNum-value == i){
//					CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_MAX,i);
					r.put("info","登录失败,您的登录机会还剩"+i+"次!");
				}
			}
			if (maxLoginErrorNum - value == 0){
				Integer	loginLockTime=LockedConfigManager.getInstance().getAdminConfig().getLockLong();//获取Redis配置的登录错误次数有效时间
				// 同步输错密码超限锁定用户信息接口
				LockedUserInfo lockedUserInfoVO=new LockedUserInfo();
				lockedUserInfoVO.setUserid(admin.getId());
				lockedUserInfoVO.setUsername(admin.getUsername());
				lockedUserInfoVO.setMobile(admin.getMobile());
				lockedUserInfoVO.setLockTime(new Date());
				lockedUserInfoVO.setUnlockTime(DateUtils.nowDateAddDate(loginLockTime));
				lockedUserInfoVO.setFront(0);
				lockedUserInfoVO.setUnlocked(0);
				int insert=lockedUserInfoMapper.insert(lockedUserInfoVO);
				r.put("info","您的登录失败次数超限，请"+DateUtils.SToHMSStr(retTime)+"之后重试!");
			}
		}
		return  r;
	}
	/**@author koudeli
	 * redis增加
	 * @param key
	 */
	private long insertPassWordCount(String key) {
		long retValue  = RedisUtils.incr(key);
//		RedisUtils.expire(key,RedisUtils.getRemainMiao());//给key设置过期时间
		Integer	loginErrorConfigManager=LockedConfigManager.getInstance().getAdminConfig().getLockLong();
        //.获取用户允许输入的最大错误次数
        Integer maxLoginErrorNum=LockedConfigManager.getInstance().getWebConfig().getMaxLoginErrorNum();
        if(retValue<=maxLoginErrorNum){
            RedisUtils.expire(key,loginErrorConfigManager*3600);//给key设置过期时间
        }
		return retValue;
	}
}
