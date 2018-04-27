/**
 * Description: 汇盈金服调用支付等API接口
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 下午4:20:22
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib;


import com.hyjf.pay.lib.chinapnr.ChinapnrBean;

/**
 * @author GOGTZ-T
 */
public interface PnrApi {

	/**
	 * 调用汇付天下API接口
	 *
	 * @param bean
	 * @return
	 */
	public String callChinaPnrApi(PnrApiBean bean);

	/**
	 * 用户开户
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean userRegister(PnrApiBean bean);

	/**
	 * 用户绑卡接口
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean userBindCard(PnrApiBean bean);

	/**
	 * 用户登录接口
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean userLogin(PnrApiBean bean);

	/**
	 * 删除银行卡接口
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean delCard(PnrApiBean bean);

	/**
	 * 充值
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean netSave(PnrApiBean bean);

	/**
	 * 资金（货款）冻结
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean usrFreezeBg(PnrApiBean bean);

	/**
	 * 资金（货款）解冻
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean usrUnFreeze(PnrApiBean bean);

	/**
	 * 主动投标
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean initiativeTender(PnrApiBean bean);

	/**
	 * 投标撤销
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean tenderCancle(PnrApiBean bean);

	/**
	 * 自动扣款（放款）
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean loans(PnrApiBean bean);

	/**
	 * 自动扣款（还款）
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean repayment(PnrApiBean bean);

	/**
	 * 自动扣款转账（商户用）
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean transfer(PnrApiBean bean);

	/**
	 * 取现（页面）
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean cash(PnrApiBean bean);

	/**
	 * 用户账户支付
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean usrAcctPay(PnrApiBean bean);

	/**
	 * 债权转让接口
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean creditAssign(PnrApiBean bean);
	
	/**
	 * 自动债权转让接口
	 *
	 * @param bean
	 * @return
	 */
	PnrApiBean autoCreditAssign(PnrApiBean bean);

	/**
	 * 生利宝交易接口
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean fssTrans(PnrApiBean bean);

	/**
	 * 余额查询（页面）
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean queryBalance(PnrApiBean bean);

	/**
	 * 余额查询（后台）
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean queryBalanceBg(PnrApiBean bean);

	/**
	 * 交易状态查询
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean queryTransStat(PnrApiBean bean);

	/**
	 * 放还款对账
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean reconciliation(PnrApiBean bean);

	/**
	 * 商户扣款对账
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean trfReconciliation(PnrApiBean bean);

	/**
	 * 取现对账
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean cashReconciliation(PnrApiBean bean);

	/**
	 * 充值对账
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean saveReconciliation(PnrApiBean bean);

	/**
	 * 银行卡查询接口
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean queryCardInfo(PnrApiBean bean);

	/**
	 * 用户信息查询
	 *
	 * @param bean
	 * @return
	 */
	public PnrApiBean queryUsrInfo(PnrApiBean bean);

	/**
	 * 标的信息录入接口
	 * 
	 * @param bean
	 * @return
	 */
	public PnrApiBean addBidInfo(PnrApiBean bean);

	/**
	 * 标的信息补录输入接口
	 * 
	 * @param bean
	 * @return
	 */
	public PnrApiBean addBidAttachInfo(PnrApiBean bean);

	/**
	 * 标的审核状态查询接口
	 * 
	 * @param bean
	 * @return
	 */
	public PnrApiBean queryBidInfo(PnrApiBean bean);

	/**
	 * 验证汇付天下签名
	 *
	 * @param bean
	 * @return
	 */
	public ChinapnrBean verifyChinaPnr(PnrApiBean bean);
    
    /**
     * 
     * 企业用户注册
     * @author renxingchen
     * @param bean
     * @return
     */
    public PnrApiBean corpRegister(PnrApiBean bean);
    
    /**
     * 
     * 企业用户绑定用户
     * @author renxingchen
     * @param bean
     * @return
     */
    public PnrApiBean direcTrfAuth(PnrApiBean bean);
     
    /**
     * 
     * 定向转账
     * @author renxingchen
     * @param bean
     * @return
     */
    public PnrApiBean direcTrf(PnrApiBean bean);
/**
 * 
 * @method: autoTender
 * @description: 	预约自动投标		
 *  @param bean
 *  @return 
 * @return: PnrApiBean
* @mender: zhouxiaoshuai
 * @date:   2016年8月22日 上午10:59:48
 */
    public	PnrApiBean autoTender(PnrApiBean bean);
    
    
    /**
     * 
     * 快捷充值限额信息查询
     * @author renxingchen
     * @param bean
     * @return
     */
    public PnrApiBean queryPayQuota(PnrApiBean bean);

}
