/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.HjhCreditTenderClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;

/**
 * @author libin
 * @version HjhCreditTenderClientImpl.java, v0.1 2018年7月11日 下午3:05:32
 */
@Service
public class HjhCreditTenderClientImpl implements HjhCreditTenderClient{
	
    @Autowired
    private RestTemplate restTemplate;

	@Override
	public HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form) {
		HjhCreditTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhcredittender/getHjhCreditTenderListByParam", form, HjhCreditTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest form) {
		HjhCreditTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhcredittender/getHjhCreditTenderListByParamWithOutPage", form, HjhCreditTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
		return null;
	}

}
