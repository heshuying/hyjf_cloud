/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.response.trade.BorrowProjectRepayReponse;
import com.hyjf.am.response.trade.HjhAssetBorrowTypeResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.client.ApiAssetClient;
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

    /**
     * 查看机构表是否存在
     *
     * @param instCode
     * @param assetType
     * @return
     */
    @Override
    public HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, Integer assetType) {
        String url = "http://AM-TRADE/am-trade/assetPush/selectAssetBorrowType/" + instCode + "/" + assetType;
        HjhAssetBorrowTypeResponse response = restTemplate.getForEntity(url, HjhAssetBorrowTypeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取还款方式,项目类型
     *
     * @param borrowcCd
     * @return
     */
    @Override
    public List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd) {
        String url = "http://AM-TRADE/am-trade/assetPush/selectProjectRepay/" + borrowcCd;
        BorrowProjectRepayReponse response = restTemplate.getForEntity(url, BorrowProjectRepayReponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询用户信息（user表）
     *
     * @param truename
     * @param idcard
     * @return
     */
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

    /**
     * 查询用户信息（userinfo表）
     *
     * @param userId
     * @return
     */
    @Override
    public UserVO selectUsersById(Integer userId) {
        String url = "http://AM-USER/am-user/user/findById/" + userId;
        UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询用户受托支付电子账户是否授权
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        String url = "http://AM-TRADE/am-trade/assetPush/selectStzfWhiteList/" + instCode + "/" + entrustedAccountId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 推送资产
     *
     * @param record
     * @return
     */
    @Override
    public int insertAssert(HjhPlanAssetVO record) {
        String url = "http://AM-TRADE/am-trade/assetPush/insertAssert";
        Integer result = restTemplate.postForEntity(url, record, Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 推送商家信息
     *
     * @param riskInfo
     */
    @Override
    public void insertRiskInfo(List<InfoBean> riskInfo) {
        String url = "http://AM-TRADE/am-trade/assetPush/insertRiskInfo";
        restTemplate.postForEntity(url, riskInfo, Integer.class).getBody();
    }
}
