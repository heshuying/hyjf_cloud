/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.tender;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author libin
 * @version TenderService.java, v0.1 2018年8月24日 上午10:41:37
 */
public interface TenderService extends BaseTradeService{
	
	/**
	 * 校验自动投资参数
	 * 
	 * @param borrowNid
	 * @param account
	 * @param userId
	 * @param platform
	 * @param couponGrantId
	 * @return
	 */
	public JSONObject checkAutoTenderParam(String borrowNid, String account, String bizAccount, String platform, String couponGrantId);
	
	/**
	 * 调用汇付天下接口前操作
	 * 
	 * @param borrowId
	 *            借款id
	 * @param userId
	 *            用户id
	 * @param account
	 *            投资金额
	 * @param ip
	 *            投资人ip
	 * @param couponGrantId 
	 * @param tenderUserName 
	 * @return 操作是否成功
	 * @throws Exception 
	 */
	public boolean updateTenderLog(AutoTenderComboRequest autoTenderComboRequest);
	
	/**
	 * 投资失败后,投标申请撤销
	 * 
	 * @param borrowUserId
	 * @param investUserId
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public boolean bidCancel(int investUserId, String productId, String orgOrderId, String txAmount) throws Exception;
	
	/**
	 * 删除投资记录临时表
	 * 
	 * @param userId
	 * @param ordId
	 * @return
	 * @author Administrator
	 */
	public boolean deleteBorrowTenderTmpByParam(int userId, String borrowNid, String orderId);
	
	/**
	 * 保存用戶的自动投資数据
	 * @param borrow
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	public JSONObject userAutoTender(BorrowAndInfoVO borrow, BankCallBean bean,String couponGrantId) throws Exception;
}
