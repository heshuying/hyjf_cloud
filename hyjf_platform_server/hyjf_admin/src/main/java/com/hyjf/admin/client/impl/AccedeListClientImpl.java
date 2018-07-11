/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.hyjf.admin.client.AccedeListClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccedeListResponse;
import com.hyjf.am.response.admin.HjhAccedeSumResponse;
import com.hyjf.am.response.admin.UserHjhInvistDetailResponse;
import com.hyjf.am.response.trade.TenderAgreementResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
/*import org.springframework.web.client.RestTemplate;*/
/**
 * @author libin
 * @version AccedeListClientImpl.java, v0.1 2018年7月7日 下午3:29:45
 */
@Service
public class AccedeListClientImpl implements AccedeListClient{
	
    @Autowired
    private RestTemplate restTemplate;
	
	/**
	 * 检索加入明细列表
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	@Override
	public AccedeListResponse getAccedeListByParam(AccedeListRequest form) {
		AccedeListResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/getAccedeListByParam", form, AccedeListResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form) {
		AccedeListResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/getAccedeListByParamWithoutPage", form, AccedeListResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
		return null;
	}

	@Override
	public HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form) {
		HjhAccedeSumResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/getCalcSumByParam", form,HjhAccedeSumResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
		return null;
	}

	@Override
	public UserVO getUserByUserId(int userId) {
        UserResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectUserByUserId/" + userId, UserResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
	}

	@Override
	public List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/selectTenderAgreementByNid/"+planOrderId;
        TenderAgreementResponse response = restTemplate.getForEntity(url,TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
		return null;
	}

	@Override
	public UserInfoVO selectUsersInfoByUserId(int userid) {
        UserInfoResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userInfo/findById/" + userid, UserInfoResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
		return null;
	}

	@Override
	public int updateSendStatusByParam(AccedeListRequest request) {
		String url = "http://AM-TRADE/am-trade/accedeList/updateSendStatusByParam";
		Integer Flag = restTemplate.postForEntity(url,request,Integer.class).getBody();
		return Flag;
	}

	@Override
	public UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request) {
		UserHjhInvistDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/selectUserHjhInvistDetail", request,UserHjhInvistDetailResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
		return null;
	}
}
