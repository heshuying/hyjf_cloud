/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.client.impl;

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
        return restTemplate.getForEntity(url, HjhAssetBorrowTypeVO.class).getBody();
    }

    @Override
    public List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectAssetBorrowType/" + borrowcCd;
        return restTemplate.getForEntity(url, List.class).getBody();
    }

    @Override
    public UserInfoVO selectUserInfoByNameAndCard(String truename, String idcard) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectUserInfoByNameAndCard/" + truename + "/" + idcard;
        return restTemplate.getForEntity(url, UserInfoVO.class).getBody();
    }

    @Override
    public BankOpenAccountVO selectBankAccountById(Integer userId) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectUserInfoByNameAndCard/" + userId;
        return restTemplate.getForEntity(url, BankOpenAccountVO.class).getBody();
    }

    @Override
    public UserVO selectUsersById(Integer userId) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectUsersById/" + userId;
        return restTemplate.getForEntity(url, UserVO.class).getBody();
    }

    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        String url = "http://AM-BORROW/am-borrow/assetPush/selectStzfWhiteList/" + instCode + "/" + entrustedAccountId;
        return restTemplate.getForEntity(url, STZHWhiteListVO.class).getBody();
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
}
