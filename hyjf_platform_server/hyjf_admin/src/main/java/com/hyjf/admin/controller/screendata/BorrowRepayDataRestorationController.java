package com.hyjf.admin.controller.screendata;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.screendata.BorrowRepayDataService;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * @Author walter.limeng
 * @Description //用户画像投屏数据修复
 * @Date 15:13 2019-04-10
 **/

@ApiIgnore
@RestController
@RequestMapping(value = "/batch/borrow/repayrestoration")
public class BorrowRepayDataRestorationController extends BaseController {
    private static int MAX_COUNT=100;

    @Autowired
    BorrowRepayDataService borrowRepayDataService;
    @Resource
    private CommonProducer commonProducer;

    @GetMapping("/datarestoration")
    public void dealData() {
        logger.info("用户画像投屏数据修复开始");
        //获取redis缓存的查询开始数值
        String redisIndex = RedisUtils.get("SCREEN_DATA_RESTORATION_INDEX");
        Integer startIndex = 0;
        Integer endIndex = 200;
        if(null != redisIndex){
            startIndex = Integer.parseInt(redisIndex);
        }
        logger.info("datarestoration startIndex:{}",startIndex);

        logger.info("用户画像投屏数据承接数据修复开始");
        List<ScreenDataBean> creditTenderList = borrowRepayDataService.getCreditTenderList(startIndex,endIndex);
        if(!CollectionUtils.isEmpty(creditTenderList)){
            for(ScreenDataBean screenDataBean:creditTenderList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "承接已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据承接数据修复开始修复异常",e);
                }
            }
        }
        logger.info("用户画像投屏数据计划退出回款数据修复开始");
        List<ScreenDataBean> planRepayList = borrowRepayDataService.getPlanRepayList(startIndex,endIndex);
        if(!CollectionUtils.isEmpty(planRepayList)){
            for(ScreenDataBean screenDataBean:planRepayList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "计划退出回款已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据计划退出回款数据修复开始修复异常",e);
                }
            }
        }
        logger.info("用户画像投屏数据计划投资数据修复开始");
        List<ScreenDataBean> planTenderList = borrowRepayDataService.getPlanTenderList(startIndex,endIndex);
        logger.info("用户画像投屏数据计划投资数据打印:"+ JSONObject.toJSONString(planTenderList));
        if(!CollectionUtils.isEmpty(planTenderList)){
            for(ScreenDataBean screenDataBean:planTenderList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "计划投资数据已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据计划投资数据修复开始修复异常",e);
                }
            }
        }

        logger.info("用户画像投屏数据充值数据修复开始");
        List<ScreenDataBean> rechargeList = borrowRepayDataService.getRechargeList(startIndex,endIndex);
        if(!CollectionUtils.isEmpty(rechargeList)){
            for(ScreenDataBean screenDataBean:rechargeList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "充值数据已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据充值数据修复异常",e);
                }
            }
        }
        logger.info("用户画像投屏数据充值数据修复结束");

        logger.info("用户画像投屏数据提现数据修复开始");
        List<ScreenDataBean> withdrawList = borrowRepayDataService.getWithdrawList(startIndex,endIndex);
        if(!CollectionUtils.isEmpty(withdrawList)){
            for(ScreenDataBean screenDataBean:withdrawList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "提现数据已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据提现数据修复异常",e);
                }
            }
        }
        logger.info("用户画像投屏数据提现数据修复结束");

        logger.info("用户画像投屏数据还款成功数据修复开始");
        List<ScreenDataBean> borrowRecoverList = borrowRepayDataService.getBorrowRecoverList(startIndex,endIndex);
        if(!CollectionUtils.isEmpty(borrowRecoverList)){
            for(ScreenDataBean screenDataBean:borrowRecoverList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "还款成功数据已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据还款成功数据修复异常",e);
                }
            }
        }
        logger.info("用户画像投屏数据还款成功数据修复结束");

        logger.info("用户画像投屏数据散标投资数据修复开始");
        List<ScreenDataBean> borrowTenderList = borrowRepayDataService.getBorrowTenderList(startIndex,endIndex);
        if(!CollectionUtils.isEmpty(borrowTenderList)){
            for(ScreenDataBean screenDataBean:borrowTenderList){
                try{
                    this.sendScreenDataMQ(screenDataBean);
                    logger.debug("用户：{"+screenDataBean.getUserId()+"}" + "散标投资数据已修复");
                }catch (MQException e){
                    logger.error("用户画像投屏数据散标投资数据修复异常",e);
                }
            }
        }
        logger.info("用户画像投屏数据散标投资数据修复结束");

        Integer index = startIndex + endIndex;
        RedisUtils.set("SCREEN_DATA_RESTORATION_INDEX",index.toString(),600);
    }

    /**
     * 充值成功后,发送大屏数据统计MQ
     *
     * @param screenDataBean
     */
    private void sendScreenDataMQ(ScreenDataBean screenDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SCREEN_DATA_TOPIC, UUID.randomUUID().toString(),screenDataBean), 2);
    }


}
