package com.hyjf.cs.trade.client;

import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.admin.CertLogRequestBean;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
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

    List<JxBankConfigVO> getRechargeQuotaLimit();

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
	/*JxBankConfigVO getBanksConfigByBankId(String bankId);*/
	Integer getBankInterfaceFlagByType(String type);

	/**
	 * 查询债转折让率配置列表
	 * @author zhangyk
	 * @date 2018/9/27 14:32
	 */
	List<DebtConfigVO> getDebtConfigList();

    String getBankRetMsg(String retCode);

	/**
	 * 获取短信配置表-最大量，有效时间等 配置只有一条
	 * @return
	 */
	SmsConfigVO findSmsConfig();

	void holidays();

	/**
	 * 获取待处理的异常
	 * @return
	 */
    List<CertErrLogVO> getCertErrLogs();

	/**
	 * 应急中心 查找上报记录
	 * add by nxl
	 * @return
	 */
	List<CertLogVO> selectCertReportLogList();

	/**
	 * 应急中心 根据id查找报送日志
	 * @param logId
	 * add by nxl
	 * @return
	 */
	CertLogVO selectCertReportLogById(int logId);

	/**
	 * 应急中心 更新操作日志
	 * @param request
	 * add by nxl
	 * @return
	 */
	int updateCertLog(CertLogRequestBean request);

	/**
	 * 插入发送记录表
	 * @param certLog
	 * @return
	 */
	boolean insertCertLog(CertLogVO certLog);

	/**
	 * 插入错误日志表
	 * @param errLog
	 */
	boolean insertCertErrorLog(CertErrLogVO errLog);

	/**
	 * 根据订单号删除错误日志
	 * @param oldLogOrdId
	 */
	boolean deleteCertErrByLogOrdId(String oldLogOrdId);

	/**
	 * 修改错误次数加1
	 */
	boolean updateErrorLogCount(CertErrLogVO logVO);
	/**
	 * 应急中心 查询待异步查询的日志数量
	 * add by nxl
	 * @return
	 */
	int selectCertLogLength();

	/**
	 * 获取分享信息
	 * @author wgx
	 * @date 2019/05/09
	 */
	ShareNewsBeanVO queryShareNews();
}
