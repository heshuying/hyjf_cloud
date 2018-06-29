/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.trade.BatchUserPortraitQueryResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.batch.UserPortraitBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchServiceImpl, v0.1 2018/6/28 18:43
 * 用户画像service
 */
@Service
public class UserPortraitBatchServiceImpl extends BaseServiceImpl implements UserPortraitBatchService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
    @Override
    public void userPortraitBatch() {
        logger.info("用户画像(每日)定时任务开始....");

        List<UserInfoVO> userInfoVOList = amUserClient.searchUserInfo();
        logger.info(JSON.toJSONString(userInfoVOList));
        String userId = "";
        if(!CollectionUtils.isEmpty(userInfoVOList)){
            for(UserInfoVO userInfoVO:userInfoVOList){
                userId += userInfoVO.getUserId() + ",";
            }
            userId = userId.substring(0,userId.lastIndexOf(","));
            logger.info("组装的userid为::::::{}",userId);
        }

        List<BatchUserPortraitQueryVO> batchUserPortraitQueryVOList = amTradeClient.searchInfoForUserPortrait(userId);
        logger.info(JSON.toJSONString(batchUserPortraitQueryVOList));
    }
}
