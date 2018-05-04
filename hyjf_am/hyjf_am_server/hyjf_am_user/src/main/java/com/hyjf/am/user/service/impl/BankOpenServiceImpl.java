/**
 * 开户
 */
package com.hyjf.am.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hyjf.am.user.dao.model.auto.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.user.dao.mapper.auto.AppChannelStatisticsDetailMapper;
import com.hyjf.am.user.dao.mapper.auto.BankOpenAccountLogMapper;
import com.hyjf.am.user.dao.mapper.auto.BankOpenAccountMapper;
import com.hyjf.am.user.dao.mapper.auto.UsersInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UsersMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmRegMapper;
import com.hyjf.am.user.service.BankOpenService;
import com.hyjf.am.user.utils.IdCard15To18;

@Service
public class BankOpenServiceImpl implements BankOpenService {	

	@Autowired
	private BankOpenAccountLogMapper bankOpenAccountLogMapper;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UsersInfoMapper usersInfoMapper;

	@Autowired
	private BankOpenAccountMapper bankOpenAccountMapper;

	@Autowired
	private UtmRegMapper utmRegMapper;

	@Autowired
	private AppChannelStatisticsDetailMapper appChannelStatisticsDetailMapper;
	

	Logger _log = LoggerFactory.getLogger(BankOpenServiceImpl.class);

	@Override
	public boolean updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc,String name,String idno,String cardNo) {
		Date date = new Date();
		BankOpenAccountLogExample example = new BankOpenAccountLogExample();
		example.createCriteria().andUserIdEqualTo(userId).andOrderIdEqualTo(logOrderId);
		List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
		if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
			BankOpenAccountLog openAccountLog = bankOpenAccountLogs.get(0);
			openAccountLog.setMobile(mobile);
			openAccountLog.setStatus(0);
			openAccountLog.setUpdateTime(date);
			openAccountLog.setUpdateUserId(userId);
			openAccountLog.setUpdateUserName(openAccountLog.getCreateUserName());
			boolean updateFlag = this.bankOpenAccountLogMapper.updateByPrimaryKeySelective(openAccountLog) > 0 ? true
					: false;
			if (updateFlag) {
				return true;
			} else {
				return false;
			}
		} else {
			BankOpenAccountLog bankOpenAccountLog = new BankOpenAccountLog();
			bankOpenAccountLog.setUserId(userId);
			bankOpenAccountLog.setUserName(userName);
			bankOpenAccountLog.setMobile(mobile);
			bankOpenAccountLog.setStatus(0);
			bankOpenAccountLog.setOrderId(logOrderId);
			bankOpenAccountLog.setCreateTime(date);
			bankOpenAccountLog.setCreateUserId(userId);
			bankOpenAccountLog.setCreateUserName(userName);
			bankOpenAccountLog.setName(name);
			bankOpenAccountLog.setIdNo(idno);
			bankOpenAccountLog.setCardNo(cardNo);
			bankOpenAccountLog.setClient(Integer.parseInt(clientPc));
			boolean flag = this.bankOpenAccountLogMapper.insertSelective(bankOpenAccountLog) > 0 ? true : false;
			return flag;
		}
	}
    
    /**
     * 更新开户日志表
     *
     * @param userId
     * @param logOrderId
     * @param status
     */
    @Override
    public void updateUserAccountLog(Integer userId, String logOrderId, int status) {
        Date date = new Date();
        BankOpenAccountLogExample example = new BankOpenAccountLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andOrderIdEqualTo(logOrderId);
        List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
        if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
            BankOpenAccountLog openAccountLog = bankOpenAccountLogs.get(0);
            openAccountLog.setStatus(status); // 更新开户状态
            openAccountLog.setUpdateTime(date);
            openAccountLog.setUpdateUserId(userId);
            openAccountLog.setUpdateUserName(openAccountLog.getCreateUserName());
            this.bankOpenAccountLogMapper.updateByPrimaryKeySelective(openAccountLog);
        }

    }

    @Override
    public boolean updateUserAccount(Integer userId,String trueName,  String orderId, String accountId, String idNo,Integer bankAccountEsb,String mobile) {
        _log.info("开户成功后,更新用户账户信息");
        // 需要调用查询接口查询用户的银行卡号  手机号  绑卡关系查询接口&&&&&&&&&&&&&&&&&&&&&&&&&&&
        // 当前日期
        Date nowDate = new Date();
        
        BankOpenAccountLogExample accountLogExample = new BankOpenAccountLogExample();
        accountLogExample.createCriteria().andUserIdEqualTo(userId);
        boolean deleteLogFlag = this.bankOpenAccountLogMapper.deleteByExample(accountLogExample) > 0 ? true : false;
        if (!deleteLogFlag) {
            throw new RuntimeException("删除用户开户日志表失败，用户开户订单号：" + orderId + ",用户userId:" + userId);
        }
        // 获取用户信息
        Users user = this.getUsers(userId);
        // 用户名
        String userName = user.getUsername();
        // 身份证号
//        String idNo = openAccoutLog.getIdNo();
        _log.info("用户ID:" + userId + "],用户名:[" + userName + "],用户身份证号:[" + idNo + "]");
        // 根据身份证号获取用户相关信息
        if (idNo != null && idNo.length() < 18) {
            try {
                idNo = IdCard15To18.getEighteenIDCard(idNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int sexInt = Integer.parseInt(idNo.substring(16, 17));// 性别
        if (sexInt % 2 == 0) {
            sexInt = 2;
        } else {
            sexInt = 1;
        }
        String birthDayTemp = idNo.substring(6, 14);// 出生日期
        String birthDay = StringUtils.substring(birthDayTemp, 0, 4) + "-" + StringUtils.substring(birthDayTemp, 4, 6) + "-" + StringUtils.substring(birthDayTemp, 6, 8);
        user.setBankOpenAccount(1);// 银行是否开户
        user.setBankAccountEsb(bankAccountEsb);// 开户平台
        user.setRechargeSms(0);
        user.setWithdrawSms(0);
        user.setUserType(0);
        user.setMobile(mobile);
        user.setVersion(user.getVersion().add(BigDecimal.ONE));
        // 更新相应的用户表
        boolean usersFlag = usersMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
        if (!usersFlag) {
            _log.info("开户成功后,更新用户信息失败,用户ID:[" + userId + "]");
            throw new RuntimeException("更新用户表失败！");
        }
        // 根据用户ID查询用户信息表
        UsersInfo userInfo = this.getUsersInfoByUserId(userId);
        if (userInfo == null) {
            _log.info("获取用户详情表失败,用户ID:[" + userId + "]");
            throw new RuntimeException("根据用户ID,查询用户详情失败");
        }
        userInfo.setTruename(trueName);// 姓名
        userInfo.setIdcard(idNo);// 身份证号
        userInfo.setSex(sexInt);// 性别
        userInfo.setBirthday(birthDay); // 出生年月日
        userInfo.setTruenameIsapprove(1);
        userInfo.setMobileIsapprove(1);
        // 更新用户详细信息表
        boolean userInfoFlag = usersInfoMapper.updateByPrimaryKeySelective(userInfo) > 0 ? true : false;
        if (!userInfoFlag) {
            _log.info("更新用户详细信息表失败,用户ID:[" + userId + "]");
            throw new RuntimeException("更新用户详情表失败！");
        }
        // 插入银行账户关联表
        BankOpenAccount openAccount = new BankOpenAccount();
        openAccount.setUserId(userId);
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(accountId);
        openAccount.setCreateTime(nowDate);
        openAccount.setCreateUserId(userId);
        openAccount.setCreateUserName(userName);
        boolean openAccountFlag = this.bankOpenAccountMapper.insertSelective(openAccount) > 0 ? true : false;
        if (!openAccountFlag) {
            _log.info("开户成功后,插入用户银行账户关联表失败,用户ID:[" + userId + "]");
            throw new RuntimeException("插入用户银行账户关联表失败！");
        }
        
        // PC渠道统计表更新开户
        UtmReg utmReg = this.selectUtmRegByUserId(userId);
        if (utmReg != null) {
            utmReg.setBindCard(1);
            utmReg.setOpenAccount(1);
            this.utmRegMapper.updateByPrimaryKeySelective(utmReg);
        }
        // APP渠道统计明细更新
        AppChannelStatisticsDetail appChannelStatisticsDetail = this.selectAppChannelByUserId(userId);
        if (appChannelStatisticsDetail != null) {
            appChannelStatisticsDetail.setOpenAccountTime(new Date());
            this.appChannelStatisticsDetailMapper.updateByPrimaryKeySelective(appChannelStatisticsDetail);
        }

        return openAccountFlag;
    }
    

	public Users getUsers(Integer userId) {
		return usersMapper.selectByPrimaryKey(userId);
	}

	/**
	 * 根据用户ID取得用户信息
	 *
	 * @param userId
	 * @return
	 */
	public UsersInfo getUsersInfoByUserId(Integer userId) {
		if (userId != null) {
			UsersInfoExample example = new UsersInfoExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<UsersInfo> usersInfoList = this.usersInfoMapper.selectByExample(example);
			if (usersInfoList != null && usersInfoList.size() > 0) {
				return usersInfoList.get(0);
			}
		}
		return null;
	}
    
    /**
     * 根据用户ID检索PC渠道统计明细
     *
     * @param userId
     * @return
     */
    private UtmReg selectUtmRegByUserId(Integer userId) {
        UtmRegExample example = new UtmRegExample();
        UtmRegExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<UtmReg> list = this.utmRegMapper.selectByExample(example);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据用户ID检索APP渠道统计明细
     *
     * @param userId
     * @return
     */
    private AppChannelStatisticsDetail selectAppChannelByUserId(Integer userId) {
        AppChannelStatisticsDetailExample example = new AppChannelStatisticsDetailExample();
        AppChannelStatisticsDetailExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<AppChannelStatisticsDetail> list = this.appChannelStatisticsDetailMapper.selectByExample(example);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }


	@Override
	public UsersInfo findUserInfoByCradId(String cardNo) {
		UsersInfoExample example = new UsersInfoExample();
		UsersInfoExample.Criteria cra = example.createCriteria();
		cra.andIdcardEqualTo(cardNo);

		List<UsersInfo> list = usersInfoMapper.selectByExample(example);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}

		return null;
	}

    @Override
    public BankOpenAccount selectByExample(BankOpenAccountExample example) {
        List<BankOpenAccount> bankOpenAccountList = bankOpenAccountMapper.selectByExample(example);
        if (bankOpenAccountList != null && bankOpenAccountList.size() == 1) {
            return bankOpenAccountList.get(0);
        }
        return null;
    }

}
