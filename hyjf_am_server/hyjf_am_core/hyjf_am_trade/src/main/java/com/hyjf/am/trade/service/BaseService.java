/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金服务: BaseService
 * 
 * @author liuyang
 * @version BaseService, v0.1 2018/6/27 9:32
 */
public interface BaseService {
	/**
	 * 根据标的编号检索标的借款信息
	 * 
	 * @param borrowNid
	 * @return
	 */
	Borrow getBorrowByNid(String borrowNid);

	/**
	 * 根据标的编号检索标的借款详情
	 * 
	 * @param borrowNid
	 * @return
	 */
	BorrowInfo getBorrowInfoByNid(String borrowNid);

	/**
	 * 根据标的编号检索标的借款信息
	 *
	 * @param borrowNid
	 * @return
	 */
	Borrow doGetBorrowByNid(String borrowNid);

	/**
	 * 根据标的编号检索标的借款详情
	 *
	 * @param borrowNid
	 * @return
	 */
	BorrowInfo doGetBorrowInfoByNid(String borrowNid);

	/**
	 * 获取用户的账户信息
	 * 
	 * @param userId
	 * @return
	 */
	Account getAccount(Integer userId);

	/**
	 * 取得本库冗余的用户信息
	 * 
	 * @param userId
	 * @return
	 */
	RUser getRUser(Integer userId);

	/**
	 * 取得本库冗余的用户信息
	 * 
	 * @param userName
	 * @return
	 */
	RUser getRUser(String userName);

	/**
	 * 取得本库冗余的推荐人信息
	 * 
	 * @param userId
	 * @return
	 */
	RUser getRefUser(Integer userId);

	/**
	 * 汇计划全部流程用更新用户的账户表
	 * 
	 * @param hjhProcessFlg
	 * @param userId
	 * @param amount
	 * @param interest
	 * @return
	 */
	public Boolean updateAccountForHjh(String hjhProcessFlg, Integer userId, BigDecimal amount, BigDecimal interest);

	/**
	 * 汇计划重算更新汇计划加入明细表
	 * 
	 * @param hjhProcessFlg
	 * @param id
	 * @param amount
	 * @param interest
	 * @return
	 */
	Boolean updateHjhAccedeForHjh(String hjhProcessFlg, Integer id, BigDecimal amount, BigDecimal interest,
			BigDecimal serviceFee);

	BorrowRepay getBorrowRepay(String borrowNid);

	List<BorrowRecover> getBorrowRecover(String borrowNid);

	List<BorrowRecoverPlan> getBorrowRecoverPlan(String borrowNid, int period);

	BorrowRepayPlan getRepayPlan(String borrowNid, int period);

	CreditTender getCreditTender(String assignNid);

	HjhDebtCreditTender getHjhDebtCreditTender(String assignNid);

	/**
	 * 获取系统配置
	 * 
	 * @param configCd
	 * @return
	 */
	String getBorrowConfig(String configCd);

	/**
	 * 根据电子账号查询用户在江西银行的可用余额
	 * 
	 * @param accountId
	 * @return
	 */
	BigDecimal getBankBalance(Integer userId, String accountId);

	/**
	 * 根据借款编号获取该机构的自动化流程配置
	 *
	 * @param borrowNid
	 * @return
	 */
	HjhAssetBorrowtype selectAssetBorrowType(String borrowNid);

	/**
	 * 根据资产类型获取自动化流程配置
	 * 
	 * @param instCode
	 * @param assetType
	 * @return
	 */
	HjhAssetBorrowtype selectAssetBorrowType(String instCode, Integer assetType);

	/**
	 * 判断是否属于线下充值类型.
	 * 
	 * @param tranType
	 * @return
	 * @Author : huanghui
	 */
	boolean getIsRechargeTransType(String tranType);

	/**
	 * 根据数据字典获取配置信息
	 *
	 * @param configCd
	 * @return
	 */
	String selectBorrowConfig(String configCd);

	/**
	 * 压缩zip文件包
	 *
	 * @param sb
	 * @param zipName
	 * @return
	 */
	boolean writeZip(StringBuffer sb, String zipName);

	/**
	 * 查询还款计划总表
	 *
	 * @param borrowNid
	 * @return
	 */
	BorrowRepay selectBorrowRepay(String borrowNid);

	/**
	 * 获取用户出借信息
	 *
	 * @param borrowNid
	 * @return
	 * @author PC-LIUSHOUYI
	 */
	List<BorrowTender> selectBorrowTenderListByBorrowNid(String borrowNid);

	/**
	 * 根据订单号获取用户放款信息
	 *
	 * @param nid
	 * @return
	 */
	BorrowRecover selectBorrowRecoverByNid(String nid);

	/**
	 * 根据订单编号获取互金合同信息
	 *
	 * @param nid
	 * @return
	 */
	NifaContractStatus selectNifaContractStatusByNid(String nid);

	/**
	 * 根据借款编号和还款期次查询放款信息总表
	 *
	 * @param borrowNid
	 * @param repayPeriod
	 * @return
	 */
	List<BorrowRecoverPlan> selectBorrowRecoverPlanList(String borrowNid, Integer repayPeriod);

	/**
	 * 根据借款编号和还款期次查询放款信息总表
	 *
	 * @param borrowNid
	 * @param repayPeriod
	 * @return
	 */
	List<BorrowRecover> selectBorrowRecoverList(String borrowNid, Integer repayPeriod);

	/**
	 * 获取保证金配置信息
	 *
	 * @param instCode
	 * @return
	 */
	HjhBailConfig getBailConfig(String instCode);

	/**
	 * 根据资产编号和机构获取资产
	 * 
	 * @param assetId
	 * @param instCode
	 * @return
	 */
	HjhPlanAsset selectPlanAsset(String assetId, String instCode);


	/**
	 * 根据标的号和机构编号获取资产
	 * @param borrowNid
	 * @param instCode
	 * @return
	 */
	HjhPlanAsset selectPlanAssetByBorrowNidAndInstCode(String borrowNid, String instCode);

	/**
     * 获取系统邮件收件地址， 测试生产不一致
     * @return
     */
    String getSystemEmailList();

	/**
	 * @Author liushouyi
	 * @Description  根据债转ID获取债转标签
	 * @Date  2019/4/16
	 * @Param credit
	 * @return
	 */
	HjhLabel getLabelIdCommon(HjhDebtCredit credit);
}
