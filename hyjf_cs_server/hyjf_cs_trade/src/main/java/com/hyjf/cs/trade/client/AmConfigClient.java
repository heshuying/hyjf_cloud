package com.hyjf.cs.trade.client;

import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
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

    BankConfigVO selectBankConfigByCode(String code);

	/**
	 * 债转配置初始化查询
	 * @auth
	 * @param
	 * @return
	 */
	DebtConfigResponse getDebtConfig();

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
	JxBankConfigVO getBankNameByBankId(String bankId);

    List<BanksConfigVO> getRechargeQuotaLimit();

	/**
	 * 判断某天是否是节假日
	 * @param somdate
	 * @return
	 */
	boolean checkSomedayIsWorkDate(Date somdate);

	/**
	 * 取从某天开始推后的第一个工作日开始时间
	 * @param somdate
	 * @return
	 */
	Date getFirstWorkdateAfterSomedate(Date somdate);

	List<ContentArticleVO> searchContentArticleList(ContentArticleRequest request);

	/**
	 * 获取安卓最新的版本信息
	 * @author zhangyk
	 * @date 2018/9/5 11:46
	 */
	VersionVO  getLastestVersion();

	/**
	 * 获取公司信息
	 *
	 * @return
	 */
    SiteSettingsVO selectSiteSetting();
	BanksConfigVO getBanksConfigByBankId(String bankId);
	Integer getBankInterfaceFlagByType(String type);

	/**
	 * 查询债转折让率配置列表
	 * @author zhangyk
	 * @date 2018/9/27 14:32
	 */
	List<DebtConfigVO> getDebtConfigList();
}
