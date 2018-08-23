package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;

import java.util.Date;
import java.util.List;

/**
 * 配置中心请求
 */
public interface AmConfigClient {
	/**
	 * 获取银行返回码
	 * @param retCode
	 * @return
	 */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);
	/**
	 * 提现费率
	 * @param bankCode
	 * @return
	 */
	List<FeeConfigVO> getFeeConfig(String bankCode);
	/**
	 * 根据银行名称查询银行配置
	 * @return
	 */
	List<BankConfigVO> getBankConfigRecordList(String bankName);

	/**
	 * 查询江西银行配置（快捷支付）
	 * @return
	 */
	List<JxBankConfigVO> getQuickPaymentJxBankConfig();

	/**
	 * @Description 根据bankId查询所属银行
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/5 15:13
	 */
	BanksConfigVO getBankNameByBankId(String bankId);

    List<BanksConfigVO> getRechargeQuotaLimit();

	/**
	 * 判断某天是否是节假日
	 * @param somdate
	 * @return
	 */
	boolean checkSomedayIsWorkDate(Date somdate);

	/**
	 * 取从某天开始推后的第一个工作日开始时间
	 * @param somedate
	 * @return
	 */
	Date getFirstWorkdateAfterSomedate(Date somdate);
}
