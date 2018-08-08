package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.BankConfigResponse;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.FeeConfigResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.vo.config.FeeConfigVO;
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
				.postForEntity("http://AM-CONFIG/am-config/config/selectBankConfigByBankName" , bankName, AdminBankConfigResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
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
	public BanksConfigVO getBankNameByBankId(String bankId) {
		BanksConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<BanksConfigVO> getRechargeQuotaLimit() {
		BanksConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getRechargeQuotaLimit" , BanksConfigResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}


}
