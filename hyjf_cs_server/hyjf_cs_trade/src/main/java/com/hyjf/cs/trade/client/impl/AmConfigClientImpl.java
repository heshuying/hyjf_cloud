package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.trade.client.AmConfigClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * 配置中心请求
 */
@Service
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
				.postForEntity("http://AM-ADMIN/am-config/config/selectBankConfigByBankName" , bankName, AdminBankConfigResponse.class).getBody();
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
	public List<BanksConfigVO> getRechargeQuotaLimit() {
		BanksConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getbanklist" , BanksConfigResponse.class).getBody();
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
		String url = "http://AM-ADMIN/am-admin/siteSettings/select_site_setting";
		SiteSettingsResponse response = restTemplate.getForEntity(url,SiteSettingsResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}
	@Override
	public BanksConfigVO getBanksConfigByBankId(String bankId) {
		BanksConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
    @Override
    public Integer getBankInterfaceFlagByType(String type) {
        BankInterfaceResponse response = restTemplate
                .getForEntity("http://AM-ADMIN/am-admin/bankInterface/getBankInterfaceFlagByType/" + type, BankInterfaceResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return null;
    }
}
