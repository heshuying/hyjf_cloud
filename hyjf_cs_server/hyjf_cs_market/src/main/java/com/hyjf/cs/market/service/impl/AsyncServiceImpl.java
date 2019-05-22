package com.hyjf.cs.market.service.impl;

import com.hyjf.cs.market.service.Activity518Service;
import com.hyjf.cs.market.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author walter.limeng
 * @Description //AsyncServiceImpl$
 * @Date $ $
 **/
@Service
public class AsyncServiceImpl implements AsyncService {
    private Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Resource
    private Activity518Service activity518Service;


    @Override
    @Async("asyncServiceExecutor")
    public void saveUserDraw(int luckNum, Integer userId, Integer activityId, String couponCodes) {
        logger.info("线程启动，开始保存用户：{}的抽奖信息，奖品：{}",userId,luckNum);
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            logger.error("保存信息异常",e);
        }
        activity518Service.saveUserDraw(luckNum, userId,activityId,couponCodes);
    }
}
