package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.BankOpenAccountMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthLogMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.AdminUserAuthCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserRePayAuthCustomize;
import com.hyjf.am.user.service.UserauthService;
import com.hyjf.common.util.GetDate;

import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserauthServiceImpl extends BaseServiceImpl  implements UserauthService {
	@Autowired
	private AdminUserAuthCustomizeMapper adminUserAuthCustomizeMapper;
	@Autowired
	private HjhUserAuthMapper hjhUserAuthMapper;
	@Autowired
	private HjhUserAuthLogMapper hjhUserAuthLogMapper;
	@Autowired
	private UserMapper usersMapper;
	@Autowired
	private BankOpenAccountMapper bankOpenAccountMapper;

	@Value("${hyjf.bank.instcode}")
	private String instCode;
	@Value("${hyjf.bank.bankcode}")
	private String bankCode;
	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	@Override
    public List<AdminUserAuthListCustomize> getRecordList(Map<String, Object> authUser, int limitStart, int limitEnd) {

		if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminUserAuthListCustomize> users = adminUserAuthCustomizeMapper.selectUserAuthList(authUser);
		return users;
	}

	/**
	 * 调银行api拿用户数据(同步用户授权状态用)
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @param type 1自动投资授权  2债转授权
	 * @return
	 */
	@Override
	public BankCallBean getUserAuthQuery(Integer userId, String type) {
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andUserIdEqualTo(userId);
		BankOpenAccount bankOpenAccount = selectByExample(accountExample);
		// 调用查询投资人签约状态查询
		BankCallBean selectbean = new BankCallBean();
		// 接口版本号
		selectbean.setVersion(BankCallConstant.VERSION_10);
		selectbean.setTxCode(BankCallConstant.TXCODE_CREDIT_AUTH_QUERY);
		// 机构代码
		selectbean.setInstCode(instCode);
		selectbean.setBankCode(bankCode);
		selectbean.setTxDate(GetOrderIdUtils.getTxDate());
		selectbean.setTxTime(GetOrderIdUtils.getTxTime());
		selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		selectbean.setChannel(BankCallConstant.CHANNEL_PC);
		selectbean.setType(type);
		// 电子账号
		selectbean.setAccountId(bankOpenAccount.getAccount());

		// 操作者ID
		selectbean.setLogUserId(String.valueOf(userId));
		selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		//根据银行查询投资人签约状态
		if(BankCallConstant.QUERY_TYPE_1.equals(type)){
			selectbean.setLogRemark("用户授权自动投资");
		}else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
			selectbean.setLogRemark("用户授权自动债转");
		}
		selectbean.setLogClient(0);
		// 返回参数
		BankCallBean retBean = null;
		// 调用接口
		retBean = BankCallUtils.callApiBg(selectbean);
		return retBean;
	}
	/**
	 * 同步用户授权状态 -> 更新数据库
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public void updateUserAuthState(Integer userId, BankCallBean retBean) {
		HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
			// 自动投资授权
			if("1".equals(retBean.getType())){
				hjhUserAuth.setAutoInvesStatus(Integer.parseInt(retBean.getState()));
				hjhUserAuth.setAutoOrderId(retBean.getOrderId());
				hjhUserAuth.setAutoBidEndTime(retBean.getBidDeadline());
			}else{
				// 自动债转授权
				hjhUserAuth.setAutoCreditStatus(Integer.parseInt(retBean.getState()));
				hjhUserAuth.setAutoCreditOrderId(retBean.getOrderId());
			}
			hjhUserAuthMapper.updateByPrimaryKeySelective(hjhUserAuth);
		}

	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * 
	 * @param authUser
	 * @return
	 * @author Administrator
	 */

	@Override
	public int countRecordTotal(Map<String, Object> authUser) {
		// 查询用户列表
		int countTotal = adminUserAuthCustomizeMapper.countRecordTotal(authUser);
		return countTotal;

	}


    @Override
    public int countRecordTotalLog(Map<String, Object> authUser) {
        // 查询用户列表
        int countTotal = adminUserAuthCustomizeMapper.countRecordTotalLog(authUser);
        return countTotal;
    }

    @Override
    public List<AdminUserAuthLogListCustomize> getRecordListLog(Map<String, Object> authUser, int limitStart, int limitEnd) {
        if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
        }
        // 查询用户列表
        List<AdminUserAuthLogListCustomize> users = adminUserAuthCustomizeMapper.selectUserAuthLogList(authUser);
        return users;
    }

	@Override
	public int countRecordTotalPay(Map<String, Object> authUser) {
		
		int countTotal = adminUserAuthCustomizeMapper.countRecordTotalPay(authUser);
		return countTotal;
	}

	@Override
	public List<AdminUserPayAuthCustomize> getRecordListPay(Map<String, Object> authUser, int limitStart,
			int limitEnd) {
		if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminUserPayAuthCustomize> users = adminUserAuthCustomizeMapper.selectUserPayAuthList(authUser);
		return users;
	}

	@Override
	public void updatePayAuthRecord(int id, String signEndDate, int authtype) {
		adminUserAuthCustomizeMapper.updatePayAuthRecord(id,signEndDate,authtype);
	}
	

	@Override
	public int countRecordTotalRePay(Map<String, Object> authUser) {
		int countTotal = adminUserAuthCustomizeMapper.countRecordTotalRePay(authUser);
		return countTotal;
	}

	@Override
	public List<AdminUserRePayAuthCustomize> getRecordListRePay(Map<String, Object> authUser, int limitStart,
			int limitEnd) {
		if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminUserRePayAuthCustomize> users = adminUserAuthCustomizeMapper.selectUserRePayAuthList(authUser);
		return users;
	}

	@Override
	public void updateRePayAuthRecord(int id, String signEndDate, int authtype) {
		adminUserAuthCustomizeMapper.updateRePayAuthRecord(id,signEndDate,authtype);
	}

	@Override
	public int isDismissPay(int userid) {
//		return adminUserAuthCustomizeMapper.selectCanDismissPay(userid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userid);
        params.put("roleId", 2);
        params.put("status", 0);
	//	return webUserRepayListCustomizeMapper.countUserPayProjectRecordTotal(params);
        return 0;
	}

	@Override
	public int isDismissRePay(int userid) {
		return adminUserAuthCustomizeMapper.selectCanDismissRePay(userid);
	}

	@Override
	public void updateCancelInvestAuth(int userId) {
		HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		hjhUserAuth.setAutoInvesStatus(0);
		hjhUserAuth.setInvesCancelTime(GetDate.date2Str(new Date(),GetDate.yyyyMMdd));
		hjhUserAuthMapper.updateByPrimaryKeySelective(hjhUserAuth);
	}
    /**
     * 
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */

	public HjhUserAuth getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthExample example = new HjhUserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public void insertUserAuthLog2(int userId, String orderId, String authType) {
       
        User user=usersMapper.selectByPrimaryKey(userId);
        HjhUserAuthLog hjhUserAuthLog=new HjhUserAuthLog();
        hjhUserAuthLog.setUserId(user.getUserId());
        hjhUserAuthLog.setUserName(user.getUsername());
        hjhUserAuthLog.setOrderId(orderId);
        hjhUserAuthLog.setOrderStatus(1);
        hjhUserAuthLog.setAuthType(Integer.parseInt(authType));
        hjhUserAuthLog.setOperateEsb(0);
        hjhUserAuthLog.setCreateUserId(user.getUserId());
        hjhUserAuthLog.setCreateTime(new Date());
        hjhUserAuthLog.setUpdateTime(new Date());
        hjhUserAuthLog.setUpdateUserId(userId);
        hjhUserAuthLog.setDelFlag(0);
        hjhUserAuthLogMapper.insertSelective(hjhUserAuthLog);
		
	}

	@Override
	public void updateCancelCreditAuth(int userId) {
		HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		hjhUserAuth.setAutoCreditStatus(0);
		hjhUserAuth.setCreditCancelTime(GetDate.date2Str(new Date(),GetDate.yyyyMMdd));
		hjhUserAuthMapper.updateByPrimaryKeySelective(hjhUserAuth);
		
	}


	
	@Override
	public BankCallBean cancelInvestAuth(int userId, String channel) {
		BankCallBean selectbean = new BankCallBean();
		selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		selectbean.setTxCode(BankCallConstant.TXCODE_AUTOBID_AUTH_CANCEL);
		selectbean.setInstCode(instCode);// 机构代码
		selectbean.setBankCode(bankCode);
		selectbean.setTxDate(GetOrderIdUtils.getTxDate());
		selectbean.setTxTime(GetOrderIdUtils.getTxTime());
		selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		selectbean.setChannel(channel);
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andUserIdEqualTo(userId);
		BankOpenAccount bankOpenAccount = selectByExample(accountExample);
		if (bankOpenAccount != null) {
			selectbean.setAccountId(bankOpenAccount.getAccount());// 电子账号
		}
		selectbean.setOrderId(GetOrderIdUtils.getUsrId(userId));// 订单号

		HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		if (hjhUserAuth != null) {
			selectbean.setOrgOrderId(hjhUserAuth.getAutoOrderId());// 原订单号
		}

		// 操作者ID
		selectbean.setLogUserId(String.valueOf(userId));
		selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		selectbean.setLogClient(0);
		// 调用接口
		BankCallBean retBean = BankCallUtils.callApiBg(selectbean);
		return retBean;
	}

	@Override
	public BankCallBean cancelCreditAuth(int userId, String channel) {
		BankCallBean selectbean = new BankCallBean();
		selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		selectbean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_CANCEL);
		selectbean.setInstCode(instCode);// 机构代码
		selectbean.setBankCode(bankCode);
		selectbean.setTxDate(GetOrderIdUtils.getTxDate());
		selectbean.setTxTime(GetOrderIdUtils.getTxTime());
		selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		selectbean.setChannel(channel);
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andUserIdEqualTo(userId);
		BankOpenAccount bankOpenAccount = selectByExample(accountExample);
		if (bankOpenAccount != null) {
			selectbean.setAccountId(bankOpenAccount.getAccount());// 电子账号
		}
		selectbean.setOrderId(GetOrderIdUtils.getUsrId(userId));// 订单号

		HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		if (hjhUserAuth != null) {
			selectbean.setOrgOrderId(hjhUserAuth.getAutoCreditOrderId());// 原订单号
		}
		// 操作者ID
		selectbean.setLogUserId(String.valueOf(userId));
		selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		selectbean.setLogClient(0);
		// 调用接口
		BankCallBean retBean = BankCallUtils.callApiBg(selectbean);
		return retBean;
	}

}
