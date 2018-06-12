/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.client.impl;

import com.hyjf.am.assetpush.InfoBean;
import com.hyjf.am.response.borrow.BorrowProjectRepayReponse;
import com.hyjf.am.response.borrow.HjhAssetBorrowTypeResponse;
import com.hyjf.am.response.borrow.STZHWhiteListResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.assetpush.STZHWhiteListVO;
import com.hyjf.am.vo.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.borrow.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.borrow.client.ApiAssetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fuqiang
 * @version ApiAssetClientImpl, v0.1 2018/6/11 18:12
 */
@Repository
public class ApiAssetClientImpl implements ApiAssetClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, Integer assetType) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectAssetBorrowType/" + instCode + "/" + assetType;
        HjhAssetBorrowTypeResponse response = restTemplate.getForEntity(url, HjhAssetBorrowTypeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectProjectRepay/" + borrowcCd;
        BorrowProjectRepayReponse response = restTemplate.getForEntity(url, BorrowProjectRepayReponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public UserInfoVO selectUserInfoByNameAndCard(String truename, String idcard) {
        String url = "http://AM-USER/am-user/userInfo/selectUserInfoByNameAndCard/" + truename + "/" + idcard;
        UserInfoResponse response = restTemplate.getForEntity(url, UserInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO selectBankAccountById(Integer userId) {
        String url = "http://AM-USER/am-user/bankopen/selectById/" + userId;
        BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserVO selectUsersById(Integer userId) {
        String url = "http://AM-USER/am-user/user/findById/" + userId;
        UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectStzfWhiteList/" + instCode + "/" + entrustedAccountId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int insertAssert(HjhPlanAssetVO record) {
        String url = "http://AM-BORROW/am-borrow/assetPush/insertAssert";
        Integer result = restTemplate.postForEntity(url, record, Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public void insertRiskInfo(List<InfoBean> riskInfo) {
        String url = "http://AM-BORROW/am-borrow/assetPush/insertRiskInfo";
        restTemplate.postForEntity(url, riskInfo, Integer.class).getBody();
    }
}
