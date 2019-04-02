/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.trade.dao.customize.CustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 资金服务:BaseService实现类
 *
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2018/6/27 9:33
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${hyjf.env.test}")
	private Boolean env_test;

	@Value("${hyjf.alerm.email.test}")
	private String emailList1;

	@Value("${hyjf.alerm.email}")
	private String emailList2;

	/**
	 * 根据标的编号检索标的信息
	 *
	 * @param borrowNid
	 * @return
	 */
	@Override
	public Borrow getBorrowByNid(String borrowNid) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<Borrow> list = this.borrowMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据标的编号检索标的借款详情
	 * 
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowInfo getBorrowInfoByNid(String borrowNid) {
		BorrowInfoExample example = new BorrowInfoExample();
		BorrowInfoExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		List<BorrowInfo> list = this.borrowInfoMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据标的编号检索标的信息(主库)
	 *
	 * @param borrowNid
	 * @return
	 */
	@Override
	public Borrow doGetBorrowByNid(String borrowNid) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<Borrow> list = this.borrowMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据标的编号检索标的借款详情(主库)
	 *
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowInfo doGetBorrowInfoByNid(String borrowNid) {
		BorrowInfoExample example = new BorrowInfoExample();
		BorrowInfoExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		List<BorrowInfo> list = this.borrowInfoMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 获取用户的账户信息
	 *
	 * @param userId
	 * @return 获取用户的账户信息
	 */
	@Override
	public Account getAccount(Integer userId) {
		AccountExample example = new AccountExample();
		AccountExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<Account> list = this.accountMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 取得本库冗余的用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public RUser getRUser(Integer userId) {
		RUserExample example = new RUserExample();
		RUserExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);
		List<RUser> list = this.rUserMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 取得本库冗余的用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public RUser getRUser(String userName) {
		RUserExample example = new RUserExample();
		RUserExample.Criteria cra = example.createCriteria();
		cra.andUsernameEqualTo(userName);
		List<RUser> list = this.rUserMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 取得本库冗余的推荐人信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public RUser getRefUser(Integer userId) {
		return rUserCustomizeMapper.selectRefUserInfoByUserId(userId);
	}

	/**
	 * 汇计划全部流程用更新用户的账户表
	 * 
	 * @param hjhProcessFlg
	 * @param userId
	 * @param amount
	 * @param interest
	 * @return
	 */
	@Override
	public Boolean updateAccountForHjh(String hjhProcessFlg, Integer userId, BigDecimal amount, BigDecimal interest) {
		// 更新用户的账户表
		Account accountBean = new Account();
		accountBean.setUserId(userId);
		switch (hjhProcessFlg) {
		case CustomConstants.HJH_PROCESS_B:
			// 计划订单-自动投标
		case CustomConstants.HJH_PROCESS_BF:
			// 计划订单-自动投标/复投
			// amount=自动投标金额=b
			accountBean.setPlanBalance(amount.negate()); // 汇计划可用余额 -b
			accountBean.setPlanFrost(amount); // 汇计划冻结金额 +b
			break;

		case CustomConstants.HJH_PROCESS_D:
			// 计划订单-自动承接(出借)
		case CustomConstants.HJH_PROCESS_DF:
			// 计划订单-自动承接(复投)
			// amount=自动投标金额=d
			accountBean.setPlanBalance(amount.negate()); // 汇计划可用余额 -d
			break;

		case CustomConstants.HJH_PROCESS_F:
			// 计划订单锁定期-债权回款（承接和还款，要复投）
			// amount=回款总额=f
			accountBean.setPlanBalance(amount); // 汇计划可用余额 +f
			break;
		case CustomConstants.HJH_PROCESS_H:
			// 汇计划清算-债权回款（承接和还款，不复投）
			// amount=回款总额=h
			accountBean.setPlanFrost(amount); // 汇计划冻结金额 +h
			break;
		default:
			break;
		}

		Boolean accountFlag = this.adminAccountCustomizeMapper.updateAccountForHjhProcess(accountBean) > 0 ? true
				: false;
		if (!accountFlag) {
			throw new RuntimeException("用户账户信息表更新失败");
		}

		return accountFlag;
	}

	/**
	 * 汇计划重算更新汇计划加入明细表
	 * 
	 * @param hjhProcessFlg
	 * @param id
	 * @param amount
	 * @param interest
	 * @return
	 */
	@Override
	public Boolean updateHjhAccedeForHjh(String hjhProcessFlg, Integer id, BigDecimal amount, BigDecimal interest,
			BigDecimal serviceFee) {
		// 更新用户的账户表
		HjhAccede hjhAccede = new HjhAccede();

		// 获取当前时间
		hjhAccede.setUpdateTime(GetDate.getDate());
		hjhAccede.setUpdateUser(1);
		hjhAccede.setId(id);
		switch (hjhProcessFlg) {
		case CustomConstants.HJH_PROCESS_B:
			// 计划订单-自动投标
			// amount=自动投标金额=b
			hjhAccede.setAlreadyInvest(amount);// 计划订单已出借金额 +b
		case CustomConstants.HJH_PROCESS_BF:
			// 计划订单-自动复投
			// amount=自动投标金额=b
			hjhAccede.setAvailableInvestAccount(amount.negate()); // 计划订单可用余额 -b
			hjhAccede.setFrostAccount(amount); // 计划订单冻结金额 +b
			// add 汇计划三期 计划订单出借笔数累加 liubin 20180515 start
			hjhAccede.setInvestCounts(1);// 出借笔数 +1
			// add 汇计划三期 计划订单出借笔数累加 liubin 20180515 end
			break;

		case CustomConstants.HJH_PROCESS_D:
			// 计划订单-自动承接(出借)
			// amount=自动投标金额=d
			hjhAccede.setAlreadyInvest(amount);// 计划订单已出借金额 +d
		case CustomConstants.HJH_PROCESS_DF:
			// 计划订单-自动承接(出借/复投)
			// amount=自动投标金额=d
			hjhAccede.setAvailableInvestAccount(amount.negate()); // 计划订单可用余额 -d
			// add 汇计划三期 计划订单出借笔数累加 liubin 20180515 start
			hjhAccede.setInvestCounts(1); // 出借笔数 +1
			// add 汇计划三期 计划订单出借笔数累加 liubin 20180515 end
			break;
		case CustomConstants.HJH_PROCESS_F:
			// 计划订单锁定期-债权回款（承接和还款，要复投）
			// amount=回款总额=f
			hjhAccede.setAvailableInvestAccount(amount); // 计划订单可用余额 +f
			// add 汇计划三期 汇计划自动出借(收债转服务费) liubin 20180515 start
			hjhAccede.setLqdServiceFee(serviceFee); // 债转服务费累计
			// add 汇计划三期 汇计划自动出借(收债转服务费) liubin 20180515 end
			break;
		case CustomConstants.HJH_PROCESS_H:
			// 汇计划清算-债权回款（承接和还款，不复投）
			// amount=回款总额=h
			hjhAccede.setFrostAccount(amount); // 计划订单冻结金额 +h
			// add 汇计划三期 汇计划自动出借(收债转服务费) liubin 20180515 start
			hjhAccede.setLqdServiceFee(serviceFee); // 债转服务费累计
			// add 汇计划三期 汇计划自动出借(收债转服务费) liubin 20180515 end
			break;
		default:
			break;
		}

		Boolean accountFlag = this.hjhPlanCustomizeMapper.updateHjhAccedeForHjhProcess(hjhAccede) > 0 ? true : false;
		if (!accountFlag) {
			throw new RuntimeException("用户账户信息表更新失败");
		}

		return accountFlag;
	}

	/**
	 * 获取借款人总的还款表数据
	 * 
	 * @auther: hesy
	 * @date: 2018/7/9
	 */
	@Override
	public BorrowRepay getBorrowRepay(String borrowNid) {
		BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
		BorrowRepayExample.Criteria borrowRepayCrt = borrowRepayExample.createCriteria();
		borrowRepayCrt.andBorrowNidEqualTo(borrowNid);
		List<BorrowRepay> list = borrowRepayMapper.selectByExample(borrowRepayExample);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public List<BorrowRecover> getBorrowRecover(String borrowNid) {
		BorrowRecoverExample example = new BorrowRecoverExample();
		BorrowRecoverExample.Criteria crt = example.createCriteria();
		crt.andBorrowNidEqualTo(borrowNid);
		List<BorrowRecover> borrowRecovers = borrowRecoverMapper.selectByExample(example);
		return borrowRecovers;
	}

	@Override
	public List<BorrowRecoverPlan> getBorrowRecoverPlan(String borrowNid, int period) {
		BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
		BorrowRecoverPlanExample.Criteria crt = example.createCriteria();
		crt.andBorrowNidEqualTo(borrowNid);
		crt.andRecoverPeriodEqualTo(period);
		List<BorrowRecoverPlan> borrowRecovers = borrowRecoverPlanMapper.selectByExample(example);
		return borrowRecovers;
	}

	/**
	 * 获取某一期的还款计划
	 * 
	 * @auther: hesy
	 * @date: 2018/7/9
	 */
	@Override
	public BorrowRepayPlan getRepayPlan(String borrowNid, int period) {
		BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
		BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
		borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
		borrowRepayPlanCrt.andRepayPeriodEqualTo(period);
		List<BorrowRepayPlan> list = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 债转出借记录获取
	 * 
	 * @auther: hesy
	 * @date: 2018/8/7
	 */
	@Override
	public CreditTender getCreditTender(String assignNid) {
		CreditTenderExample example = new CreditTenderExample();
		CreditTenderExample.Criteria crt = example.createCriteria();
		crt.andAssignNidEqualTo(assignNid);
		List<CreditTender> list = this.creditTenderMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 计划类债转 根据承接订单号获取承接记录
	 * 
	 * @param assignNid
	 * @return
	 */
	@Override
	public HjhDebtCreditTender getHjhDebtCreditTender(String assignNid) {
		HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
		HjhDebtCreditTenderExample.Criteria crt = example.createCriteria();
		crt.andAssignOrderIdEqualTo(assignNid);
		List<HjhDebtCreditTender> list = this.hjhDebtCreditTenderMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 获取系统配置
	 * 
	 * @param configCd
	 * @return
	 */
	@Override
	public String getBorrowConfig(String configCd) {
		BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
		return borrowConfig.getConfigValue();
	}

	/**
	 * 根据电子账号查询用户在江西银行的可用余额
	 *
	 * @param accountId
	 * @return
	 */
	@Override
	public BigDecimal getBankBalance(Integer userId, String accountId) {
		// 账户可用余额
		BigDecimal balance = BigDecimal.ZERO;
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(accountId);// 电子账号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("电子账户余额查询");
		bean.setLogClient(0);// 平台
		try {
			BankCallBean resultBean = BankCallUtils.callApiBg(bean);
			if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
				balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return balance;
	}

	/**
	 * 根据借款编号获取该机构的审核配置
	 *
	 * @param borrowNid
	 * @return
	 */
	@Override
	public HjhAssetBorrowtype selectAssetBorrowType(String borrowNid) {
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		return selectAssetBorrowType(borrowInfo.getInstCode(), borrowInfo.getAssetType());
	}

	@Override
	public HjhAssetBorrowtype selectAssetBorrowType(String instCode, Integer assetType) {
		HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
		example.createCriteria().andInstCodeEqualTo(instCode).andAssetTypeEqualTo(assetType).andIsOpenEqualTo(1);
		List<HjhAssetBorrowtype> list = this.hjhAssetBorrowtypeMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 判断是否属于线下充值类型. 优先从Redis中取数据,当Redis中的数据为空时,从数据表中读取数据
	 * 
	 * @param tranType
	 * @return
	 * @Author : huanghui
	 */
	@Override
	public boolean getIsRechargeTransType(String tranType) {
		// 从Redis获取线下充值类型List
		String codeStringList = RedisUtils.get(RedisConstants.UNDER_LINE_RECHARGE_TYPE);
		JSONArray redisCodeList = JSONArray.parseArray(codeStringList);

		if (StringUtils.isBlank(codeStringList) || redisCodeList.size() <= 0) {
			logger.info(this.getClass().getName(),
					"---------------------------线下充值类型Redis为空!-------------------------");

			UnderLineRechargeRequest request = new UnderLineRechargeRequest();
			UnderLineRechargeExample example = new UnderLineRechargeExample();
			UnderLineRechargeExample.Criteria criteria = example.createCriteria();

			// 启用状态的
			criteria.andStatusEqualTo(0);

			List<UnderLineRecharge> codeList = this.underLineRechargeMapper.selectByExample(example);
			if (codeList.isEmpty()) {
				logger.info(this.getClass().getName(),
						"---------------------------线下充值类型数据库未配置!-------------------------");
				return false;
			} else {
				for (UnderLineRecharge code : codeList) {
					if (code.getCode().equals(tranType)) {
						return true;
					} else {
						continue;
					}
				}
			}
		} else {

			for (Object code : redisCodeList) {
				if (code.equals(tranType)) {
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}

	/**
	 * 根据数据字典获取配置信息
	 *
	 * @param configCd
	 * @return
	 */
	@Override
	public String selectBorrowConfig(String configCd) {
		BorrowConfigExample example = new BorrowConfigExample();
		example.createCriteria().andConfigCdEqualTo(configCd);
		List<BorrowConfig> list = this.borrowConfigMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0).getConfigValue();
	}

	/**
	 * 压缩zip文件包
	 *
	 * @param sb
	 * @param zipName
	 * @return
	 */
	@Override
	public boolean writeZip(StringBuffer sb, String zipName) {
		try {
			String[] files = sb.toString().split(",");
			OutputStream os = new BufferedOutputStream(new FileOutputStream(zipName + ".zip"));
			ZipOutputStream zos = new ZipOutputStream(os);
			byte[] buf = new byte[8192];
			int len;
			for (int i = 0; i < files.length; i++) {
				File file = new File(files[i]);
				if (!file.isFile()) {
					continue;
				}
				ZipEntry ze = new ZipEntry(file.getName());
				zos.putNextEntry(ze);
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				while ((len = bis.read(buf)) > 0) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
			}
			zos.closeEntry();
			zos.close();
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 获取还款信息详情
	 *
	 * @param borrowNid
	 * @return
	 * @author PC-LIUSHOUYI
	 */
	@Override
	public BorrowRepay selectBorrowRepay(String borrowNid) {
		BorrowRepayExample example = new BorrowRepayExample();
		BorrowRepayExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<BorrowRepay> list = this.borrowRepayMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 获取用户出借信息
	 *
	 * @param borrowNid
	 * @return
	 * @author PC-LIUSHOUYI
	 */
	@Override
	public List<BorrowTender> selectBorrowTenderListByBorrowNid(String borrowNid) {
		BorrowTenderExample example = new BorrowTenderExample();
		BorrowTenderExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		return this.borrowTenderMapper.selectByExample(example);
	}

	/**
	 * 根据订单号获取用户放款信息
	 *
	 * @param nid
	 * @return
	 */
	@Override
	public BorrowRecover selectBorrowRecoverByNid(String nid) {
		// 获取用户放款信息
		BorrowRecoverExample example = new BorrowRecoverExample();
		example.createCriteria().andNidEqualTo(nid);
		List<BorrowRecover> list = this.borrowRecoverMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据订单编号获取互金合同信息
	 *
	 * @param nid
	 * @return
	 */
	@Override
	public NifaContractStatus selectNifaContractStatusByNid(String nid) {
		NifaContractStatusExample example = new NifaContractStatusExample();
		example.createCriteria().andContractNoEqualTo(nid);
		List<NifaContractStatus> list = this.nifaContractStatusMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据借款编号和还款期次查询放款信息总表
	 *
	 * @param borrowNid
	 * @param repayPeriod
	 * @return
	 */
	@Override
	public List<BorrowRecoverPlan> selectBorrowRecoverPlanList(String borrowNid, Integer repayPeriod) {
		BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
		BorrowRecoverPlanExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRecoverPeriodEqualTo(repayPeriod);
		return this.borrowRecoverPlanMapper.selectByExample(example);
	}

	/**
	 * 根据借款编号和还款期次查询放款信息总表
	 *
	 * @param borrowNid
	 * @param repayPeriod
	 * @return
	 */
	@Override
	public List<BorrowRecover> selectBorrowRecoverList(String borrowNid, Integer repayPeriod) {
		BorrowRecoverExample example = new BorrowRecoverExample();
		BorrowRecoverExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRecoverPeriodEqualTo(repayPeriod);
		List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);
		return CollectionUtils.isEmpty(borrowRecoverList) ? null : borrowRecoverList;
	}

	/**
	 * 获取保证金配置
	 * 
	 * @param instCode
	 * @return
	 */
	@Override
	public HjhBailConfig getBailConfig(String instCode) {
		HjhBailConfigExample example = new HjhBailConfigExample();
		example.createCriteria().andInstCodeEqualTo(instCode).andDelFlgEqualTo(0);

		List<HjhBailConfig> list = hjhBailConfigMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public HjhPlanAsset selectPlanAsset(String assetId, String instCode) {
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		HjhPlanAssetExample.Criteria crt = example.createCriteria();
		if (StringUtils.isNotBlank(assetId)) {
			crt.andAssetIdEqualTo(assetId);
		}
		if (StringUtils.isNotBlank(instCode)) {
			crt.andInstCodeEqualTo(instCode);
		}
		List<HjhPlanAsset> list = hjhPlanAssetMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public HjhPlanAsset selectPlanAssetByBorrowNidAndInstCode(String borrowNid, String instCode) {
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		HjhPlanAssetExample.Criteria crt = example.createCriteria();
		if (StringUtils.isNotBlank(borrowNid)) {
			crt.andBorrowNidEqualTo(borrowNid);
		}
		if (StringUtils.isNotBlank(instCode)) {
			crt.andInstCodeEqualTo(instCode);
		}
		List<HjhPlanAsset> list = hjhPlanAssetMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public String getSystemEmailList() {
		return env_test ? emailList1 : emailList2;
	}

	@Override
	public boolean updateBorrowApicronLog(BorrowApicron apicron, int status) {
		BorrowApicronLogExample logExample = new BorrowApicronLogExample();
		logExample.createCriteria().andBankSeqNoEqualTo(apicron.getBankSeqNo());
		List<BorrowApicronLog> borrowApicronLogList = borrowApicronLogMapper.selectByExample(logExample);
		if(borrowApicronLogList == null || borrowApicronLogList.size() == 0){
			logger.error("未查询到对应的还款日志！借款编号：{},银行流水号：{}", apicron.getBorrowNid(), apicron.getBankSeqNo());
			return false;
		}
		for(BorrowApicronLog borrowApicronLog: borrowApicronLogList){
			BorrowApicronLog newBorrowApicronLog = new BorrowApicronLog();
			newBorrowApicronLog.setId(borrowApicronLog.getId());
			newBorrowApicronLog.setStatus(status);
			borrowApicronLogMapper.updateByPrimaryKeySelective(newBorrowApicronLog);
		}
		return true;
	}
}
