/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
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
    /**
     * 用户画像定时任务
     * */
    @Override
    public void userPortraitBatch() {
        logger.info("用户画像(每日)定时任务开始....");
        // 查询出需要更新用户画像的用户信息
        List<UserInfoVO> userInfoVOList = amUserClient.searchUserInfo();
        String userId = "";
        // list不为空就进行更新
        if(!CollectionUtils.isEmpty(userInfoVOList)){
            // 组装userId，去am-trade查询用户画像的相关信息
            for(UserInfoVO userInfoVO:userInfoVOList){
                userId += userInfoVO.getUserId() + ",";
            }
            // 去掉最后的,
            userId = userId.substring(0,userId.lastIndexOf(","));
            // 去am-trade查询用户画像的相关信息
            List<BatchUserPortraitQueryVO> batchUserPortraitQueryVOList = amTradeClient.searchInfoForUserPortrait(userId);
            // 如果信息不为空，就去am-user更新用户画像
            if(!CollectionUtils.isEmpty(batchUserPortraitQueryVOList)){
                BatchUserPortraitQueryRequest request = new BatchUserPortraitQueryRequest();
                request.setBatchUserPortraitQueryVOList(batchUserPortraitQueryVOList);
                // 保存用户画像
                amUserClient.saveUserPortrait(request);
            }
        }
    }
}
