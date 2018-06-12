/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.assetpush.STZHWhiteListVO;
import com.hyjf.am.vo.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.borrow.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.borrow.client.ApiAssetClient;
import com.hyjf.cs.borrow.mq.AssetPushProducer;
import com.hyjf.cs.borrow.mq.Producer;
import com.hyjf.cs.borrow.service.ApiAssetPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version ApiAssetPushServcieImpl, v0.1 2018/6/11 18:06
 */
@Service
public class ApiAssetPushServcieImpl implements ApiAssetPushService {

    private Logger logger = LoggerFactory.getLogger(ApiAssetPushServcieImpl.class);

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Autowired
    private AssetPushProducer assetPushProducer;

    @Override
    public HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, Integer assetType) {
        return apiAssetClient.selectAssetBorrowType(instCode, assetType);
    }

    @Override
    public List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd) {
        return apiAssetClient.selectProjectRepay(borrowcCd);
    }

    @Override
    public UserInfoVO selectUserInfoByNameAndCard(String truename, String idcard) {
        return apiAssetClient.selectUserInfoByNameAndCard(truename, idcard);
    }

    @Override
    public BankOpenAccountVO selectBankAccountById(Integer userId) {
        return apiAssetClient.selectBankAccountById(userId);
    }

    @Override
    public UserVO selectUsersById(Integer userId) {
        return apiAssetClient.selectUsersById(userId);
    }

    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        return apiAssetClient.selectStzfWhiteList(instCode, entrustedAccountId);
    }

    @Override
    public int insertAssert(HjhPlanAssetVO record) {
        return apiAssetClient.insertAssert(record);
    }

    @Override
    public void sendToMQ(HjhPlanAssetVO hjhPlanAsset) {
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("assetId", hjhPlanAsset.getAssetId());
        params.put("instCode", hjhPlanAsset.getInstCode());
        try {
            assetPushProducer.messageSend(new Producer.MassageContent(MQConstant.ASSET_PUST_TOPIC, params));
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("资产推送发送消息失败...");
        }
    }
}
