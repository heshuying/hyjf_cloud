package com.hyjf.cs.trade.client.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.CertReportLogResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.admin.CertLogRequestBean;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.AmConfigClient;

/**
 * 配置中心请求
 */
@Cilent
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
        BankReturnCodeConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/getBankReturnCodeConfig/"+retCode,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


	/**
	 * 提现费率
	 */
	@Override
	public List<FeeConfigVO> getFeeConfig(String bankCode) {
		String url = "http://AM-CONFIG/am-config/feeConfig/getFeeConfig/"+bankCode;
		FeeConfigResponse response = restTemplate.getForEntity(url, FeeConfigResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	/**
	 * 根据银行名称查询银行配置
	 * @return
	 */
	@Override
	public List<BankConfigVO> getBankConfigRecordList(String bankName){
		AdminBankConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/selectBankConfigByBankName/"+bankName, AdminBankConfigResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

    /**
     * 根据银行Code查询银行配置
     * @return
     */
    @Override
    public BankConfigVO selectBankConfigByCode(String code){
		BankConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/selectBankConfigByCode/"+ code, BankConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
	@Override
	public DebtConfigResponse getDebtConfig(){
		String url = "http://AM-CONFIG/am-config/debtconfig/init" ;
		DebtConfigResponse response = restTemplate.getForEntity(url, DebtConfigResponse.class).getBody();
		return response;
	}


    /**
	 * 查询江西银行配置（快捷支付）
	 * @return
	 */
	@Override
	public List<JxBankConfigVO> getQuickPaymentJxBankConfig(){
		String url = "http://AM-CONFIG/am-config/config/getQuickPaymentJxBankConfig";
		JxBankConfigResponse response = restTemplate.getForEntity(url,JxBankConfigResponse.class).getBody();
		if(response != null){
			return response.getResultList();
		}
		return null;
	}
	/**
	 * @param bankId
	 * @Description 根据bankId查询所属银行
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/5 15:13
	 */
	@Override
	public JxBankConfigVO getBankNameByBankId(String bankId) {
		JxBankConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getJxBankConfigByBankId/" + bankId, JxBankConfigResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<JxBankConfigVO> getRechargeQuotaLimit() {
		JxBankConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getbanklist" , JxBankConfigResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}


	/**
	 * 判断某天是否是节假日
	 * 
	 * @param somdate
	 * @return
	 */
	@Override
	public boolean checkSomedayIsWorkDate(Date somdate) {
		HolidaysConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/holidaysConfig/checkSomedayIsWorkDate/" + somdate,
						HolidaysConfigResponse.class)
				.getBody();
		if (response != null) {
			return response.isWorkDateFlag();
		}
		throw new RuntimeException("查询节假日配置错误...");
	}

	/**
	 * 取从某天开始推后的第一个工作日开始时间
	 * @param somdate
	 * @return
	 */
	@Override
	public Date getFirstWorkdateAfterSomedate(Date somdate) {
		HolidaysConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/holidaysConfig/getFirstWorkdateAfterSomedate/" + somdate,
						HolidaysConfigResponse.class)
				.getBody();
		if (response != null) {
			return response.getSomedate();
		}
		throw new RuntimeException("查询节假日配置错误...");
	}


	@Override
	@Cached(name="webHomeCompanyCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
	public List<ContentArticleVO> searchContentArticleList(ContentArticleRequest request) {
		ContentArticleResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/article/contentArticleList",request,ContentArticleResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 获取安卓最新的版本信息
	 * @author zhangyk
	 * @date 2018/9/5 11:47
	 */
	@Override
	public VersionVO getLastestVersion() {
		String url = "http://AM-CONFIG/am-config/config/versionconfig/getLastestVersion";
		VersionConfigBeanResponse response = restTemplate.getForEntity(url,VersionConfigBeanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 通过网站设置获取公司信息
	 *
	 * @return
	 */
	@Override
	public SiteSettingsVO selectSiteSetting() {
		String url = "http://AM-CONFIG/am-config/site_settings/select_site_setting";
		SiteSettingsResponse response = restTemplate.getForEntity(url,SiteSettingsResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

    @Override
    public Integer getBankInterfaceFlagByType(String type) {
        BankInterfaceResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/bankInterface/getBankInterfaceFlagByType/" + type, BankInterfaceResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return null;
    }


    /**
     * 查询债转折让率配置列表
     * @author zhangyk
     * @date 2018/9/27 14:33
     */
	@Override
	public List<DebtConfigVO> getDebtConfigList() {
		String url = "http://AM-CONFIG/am-config/debtconfig/getDebtConfigList";
		DebtConfigResponse response = restTemplate.getForEntity(url,DebtConfigResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	@Override
	public String getBankRetMsg(String retCode) {
		BankReturnCodeConfigVO vo = this.getBankReturnCodeConfig(retCode);
		if (vo == null) {
			return Response.ERROR_MSG;
		}
		return StringUtils.isNotBlank(vo.getRetMsg()) ? vo.getRetMsg() : Response.ERROR_MSG;
	}

	@Override
	public SmsConfigVO findSmsConfig() {
		SmsConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/smsConfig/findOne", SmsConfigResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public void holidays() {
		restTemplate.getForEntity("http://AM-CONFIG/am-config/holidays/save", String.class);
	}

	/**
	 * 获取待处理的异常
	 *
	 * @return
	 */
	@Override
	public List<CertErrLogVO> getCertErrLogs() {
		String url = "http://AM-CONFIG/am-config/certError/getCertErrLogs";
		CertErrLogResponse response = restTemplate.getForEntity(url,CertErrLogResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 应急中心 查找上报记录
	 * add by nxl
	 * @return
	 */
	@Override
	public List<CertLogVO> selectCertReportLogList(){
		CertReportLogResponse reportLogResponse =
				restTemplate.getForEntity("http://AM-CONFIG/am-config/certLog/selectCertReportLogList", CertReportLogResponse.class).getBody();
		if (reportLogResponse != null && Response.SUCCESS.equals(reportLogResponse.getRtn())) {
			return reportLogResponse.getResultList();
		}
		return null;
	}

	/**
	 * 应急中心 根据id查找报送日志
	 * @param logId
	 * add by nxl
	 * @return
	 */
	@Override
	public CertLogVO selectCertReportLogById(int logId){
		CertReportLogResponse reportLogResponse =
				restTemplate.getForEntity("http://AM-CONFIG/am-config/certLog/selectCertReportLogById/"+logId, CertReportLogResponse.class).getBody();
		if (reportLogResponse != null && Response.SUCCESS.equals(reportLogResponse.getRtn())) {
			return reportLogResponse.getResult();
		}
		return null;
	}

	/**
	 * 应急中心 更新操作日志
	 * @param request
	 * add by nxl
	 * @return
	 */
	@Override
	public int updateCertLog(CertLogRequestBean request){
		IntegerResponse response =
				restTemplate.postForEntity("http://AM-CONFIG/am-config/certLog/updateCertLog", request,IntegerResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultInt().intValue();
		}
		return 0;
	}


	/**
	 * 插入发送记录表
	 *
	 * @param certLog
	 * @return
	 */
	@Override
	public boolean insertCertLog(CertLogVO certLog) {
		String url = "http://AM-CONFIG/am-config/certLog/insertCertLog";
		BooleanResponse response = restTemplate.postForEntity(url,certLog,BooleanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultBoolean();
		}
		return false;
	}

	/**
	 * 插入错误日志表
	 *
	 * @param errLog
	 */
	@Override
	public boolean insertCertErrorLog(CertErrLogVO errLog) {
		String url = "http://AM-CONFIG/am-config/certError/insertCertErrorLog";
		BooleanResponse response = restTemplate.postForEntity(url,errLog,BooleanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultBoolean();
		}
		return false;
	}

	/**
	 * 根据订单号删除错误日志
	 *
	 * @param oldLogOrdId
	 */
	@Override
	public boolean deleteCertErrByLogOrdId(String oldLogOrdId) {
		String url = "http://AM-CONFIG/am-config/certError/deleteCertErrByLogOrdId/"+oldLogOrdId;
		BooleanResponse response = restTemplate.getForEntity(url,BooleanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultBoolean();
		}
		return false;
	}

	/**
	 * 修改错误次数加1
	 *
	 */
	@Override
	public boolean updateErrorLogCount(CertErrLogVO logVO) {
		String url = "http://AM-CONFIG/am-config/certError/updateErrorLogCount";
		BooleanResponse response = restTemplate.postForEntity(url,logVO,BooleanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultBoolean();
		}
		return false;
	}

	/**
	 * 应急中心 查询待异步查询的日志数量
	 * add by nxl
	 * @return
	 */
	@Override
	public int selectCertLogLength(){
		IntegerResponse reportLogResponse =
				restTemplate.getForEntity("http://AM-CONFIG/am-config/certLog/selectCertLogLength", IntegerResponse.class).getBody();
		if (reportLogResponse != null && Response.SUCCESS.equals(reportLogResponse.getRtn())) {
			return reportLogResponse.getResultInt();
		}
		return 0;
	}

	/**
	 * 查询某一天是否是工作日
	 * @param somedate
	 * @return
	 */
	@Override
	public boolean checkSomedayIsWorkDateForWithdraw(Date somedate) {
		WithdrawTimeConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/withdrawTimeConfig/checkSomedayIsWorkDateForWithdraw/" + somedate,
						WithdrawTimeConfigResponse.class)
				.getBody();
		if (response != null) {
			return response.isWorkDateFlag();
		}
		return false;
	}
}
