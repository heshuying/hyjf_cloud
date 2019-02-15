/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.dao.mapper.auto.InviterReturnDetailMapper;
import com.hyjf.am.market.dao.mapper.auto.PerformanceReturnDetailMapper;
import com.hyjf.am.market.dao.mapper.customize.market.ActivityMidauInfoCustomizeMapper;
import com.hyjf.am.market.dao.mapper.customize.market.InviterReturnCashCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.*;
import com.hyjf.am.market.dao.model.customize.ActivityMidauInfo;
import com.hyjf.am.market.service.ReturnCashActivityService;
import com.hyjf.am.resquest.market.InviterReturnCashCustomize;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tyy
 * @version ReturnCashActivityServiceImpl, v0.1 2018/11/9 15:48
 */
@Service
public class ReturnCashActivityServiceImpl implements ReturnCashActivityService {
    private Logger _log = LoggerFactory.getLogger(ReturnCashActivityServiceImpl.class);
    @Autowired
    InviterReturnCashCustomizeMapper inviterReturnCashCustomizeMapper;
    @Autowired
    PerformanceReturnDetailMapper performanceReturnDetailMapper;
    @Autowired
    InviterReturnDetailMapper inviterReturnDetailMapper;

    @Autowired
    ActivityMidauInfoCustomizeMapper activityMidauInfoCustomizeMapper;
    @Override
    public Map<String,Object> selectReturnCash(Integer userId, String orderId, Integer productType,
                                BigDecimal investMoney, InviterReturnCashCustomize inviterReturnCashCustomize, List<NmUser> nmUserList){
        Map<String,Object> map = new HashMap<>();
        List<ActivityMidauInfo> tenderList = null;
        ActivityMidauInfo activityMidauInfo = null;
        PerformanceReturnDetail performanceReturnDetail = new PerformanceReturnDetail();
        //InviterReturnCashCustomize inviterReturnCashCustomize = inviterReturnCashCustomizeMapper.selectReturnCashList(userId);
        _log.info("开始保存投资记录");
        //1=新手标，2=散标，3=汇计划
        if(productType == 3){
            performanceReturnDetail.setProductType("智投服务");
            //查询加入汇计划的投资信息
            tenderList = activityMidauInfoCustomizeMapper.queryPlanList(orderId,userId);
            if(CollectionUtils.isEmpty(tenderList)){
                _log.info("【纳觅返现活动】用户加入汇计划信息为空，orderId:"+orderId+",userId:"+userId);
                return null;
            }
            activityMidauInfo = tenderList.get(0);

        }else if(productType==1){
            //查询加投资散标的投资放款时间
            performanceReturnDetail.setProductType("新手标");
            tenderList = activityMidauInfoCustomizeMapper.queryTenderRecoverList(orderId,userId);
            if(CollectionUtils.isEmpty(tenderList)){
                _log.info("【纳觅返现活动】用户投资的散标信息为空，orderId:"+orderId+",userId:"+userId);
                return null;
            }

            activityMidauInfo = tenderList.get(0);
        }else {
            //查询加投资散标的投资放款时间
            performanceReturnDetail.setProductType("汇直投");
            tenderList = activityMidauInfoCustomizeMapper.queryTenderRecoverList(orderId,userId);
            if(CollectionUtils.isEmpty(tenderList)){
                _log.info("【纳觅返现活动】用户投资的散标信息为空，orderId:"+orderId+",userId:"+userId);
                return null;
            }

            activityMidauInfo = tenderList.get(0);
        }
        _log.info("activityMidauInfo==="+ JSONObject.toJSONString(activityMidauInfo));
        BigDecimal yearAmount = BigDecimal.ZERO;
        //累计年化投资金额=SUM（m1*n1/12+m2*n2/12+...），m为单笔投资金额，n为单笔投资期限。
        //月=投资金额*几个月/12
        if(activityMidauInfo.getProductStyle().contains("个月")){
            String number = StringUtils.substringBefore(activityMidauInfo.getProductStyle(),"个月");
            if(StringUtils.isEmpty(number)){
                return null;
            }
            yearAmount = investMoney.multiply(new BigDecimal(number)).divide(new BigDecimal(12),2,BigDecimal.ROUND_DOWN);
        }
        //天=投资金额*天数/360
        else if(activityMidauInfo.getProductStyle().contains("天")){
            String number = StringUtils.substringBefore(activityMidauInfo.getProductStyle(),"天");
            if(StringUtils.isEmpty(number)){
                return null;
            }
            yearAmount = investMoney.multiply(new BigDecimal(number)).divide(new BigDecimal(360),2,BigDecimal.ROUND_DOWN);
        }

        if(yearAmount.compareTo(BigDecimal.ZERO) <= 0){
            _log.info("用户年化投资金额小于或等于0 orderId:"+orderId+",userId:"+userId+",yearAmount="+yearAmount);
            return null;
        }

        List<String> userNames = new ArrayList<>();
        int count =0;
        for(NmUser nmUser:nmUserList){
            userNames.add(nmUser.getUserName());
        }
        //邀请人获得的投资返现金额
        int level =  getLevel(userId,count,userNames);
        _log.info("level为1是A,2是B,3是C,4是D,0不计入返现=="+level);
        map.put("level",level);
        // 查询项目信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        performanceReturnDetail.setUserName(inviterReturnCashCustomize.getUserName());
        performanceReturnDetail.setTrueName(inviterReturnCashCustomize.getTrueName());
        //邀请人账户名
        performanceReturnDetail.setRefferName(inviterReturnCashCustomize.getRefferName());
        //投资订单号
        performanceReturnDetail.setTenderNo(orderId);
        //单笔投资金额
        performanceReturnDetail.setTenderAmount(investMoney);
        //投资期限
        performanceReturnDetail.setTerm(activityMidauInfo.getProductStyle());
        //单笔返现金额
        if(level==1){
            performanceReturnDetail.setReturnAmount(yearAmount.multiply(new BigDecimal(0.005)).setScale(2,BigDecimal.ROUND_DOWN));
        }else if(level==2){
            performanceReturnDetail.setReturnAmount(yearAmount.multiply(new BigDecimal(0.005)).setScale(2,BigDecimal.ROUND_DOWN));
        }else if(level==3){
            performanceReturnDetail.setReturnAmount(yearAmount.multiply(new BigDecimal(0.005)).setScale(2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(2)));
        }else if(level==4){
            performanceReturnDetail.setReturnAmount(yearAmount.multiply(new BigDecimal(0.005)).setScale(2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(3)));
        }
        //单笔当月产生的业绩
        performanceReturnDetail.setReturnPerformance(yearAmount);
        performanceReturnDetail.setProductNo(activityMidauInfo.getBorrowNid());
        if(activityMidauInfo.getInvestTime()!=null) {
            performanceReturnDetail.setJoinTime(sdf.format(activityMidauInfo.getInvestTime()));
        }
        performanceReturnDetail.setCreateTime(new Date());
        if(level>0) {
            map.put("performanceReturnDetail",performanceReturnDetail);
           // this.savePerformanceReturnDetail(performanceReturnDetail);
        }
        //假如为纳觅员工逻辑比较特殊自己邀请自己
        if(level == 1){
            InviterReturnDetail inviterReturnDetail = new InviterReturnDetail();
            BeanUtils.copyProperties(performanceReturnDetail,inviterReturnDetail);
            inviterReturnDetail.setDebtId(inviterReturnCashCustomize.getDebtId());
            inviterReturnDetail.setDebtName(inviterReturnCashCustomize.getDebtName());
            //投资人
            inviterReturnDetail.setTenderName(inviterReturnCashCustomize.getUserName());
            //邀请人
            inviterReturnDetail.setUserName(inviterReturnCashCustomize.getUserName());
            inviterReturnDetail.setTrueName(inviterReturnCashCustomize.getTrueName());
            inviterReturnDetail.setReturnAmount(yearAmount.multiply(new BigDecimal(0.005)).setScale(2,BigDecimal.ROUND_DOWN));
            map.put("inviterReturnDetail",inviterReturnDetail);
           // this.saveInviterReturnDetail(inviterReturnDetail);
            return map;
        }
        //邀请人返现金额计算
        Integer refferId = inviterReturnCashCustomize.getRefferId();
        for(int i =1;i<level;i++){
            InviterReturnDetail inviterReturnDetail = new InviterReturnDetail();
            BeanUtils.copyProperties(performanceReturnDetail,inviterReturnDetail);
            //inviterReturnDetail 计算金额和performanceReturnDetail值不一样故下面重新赋值
            InviterReturnCashCustomize inviterReturnCash = inviterReturnCashCustomizeMapper.selectReturnCashList(refferId);
            inviterReturnDetail.setDebtId(inviterReturnCash.getDebtId());
            inviterReturnDetail.setDebtName(inviterReturnCash.getDebtName());
            //投资人
            inviterReturnDetail.setTenderName(inviterReturnCashCustomize.getUserName());
            //邀请人
            inviterReturnDetail.setUserName(inviterReturnCash.getUserName());
            inviterReturnDetail.setTrueName(inviterReturnCash.getTrueName());
            _log.info("返现金额打印=="+yearAmount+"=="+yearAmount.multiply(new BigDecimal(0.005)));
            inviterReturnDetail.setReturnAmount(yearAmount.multiply(new BigDecimal(0.005)).setScale(2,BigDecimal.ROUND_DOWN));
            map.put("inviterReturnDetail"+i,inviterReturnDetail);
           // this.saveInviterReturnDetail(inviterReturnDetail);
            refferId = inviterReturnCash.getRefferId();
        }
        return map;
    }



    private int getLevel(Integer userId,int count,List<String> userNames){
        //计数器
        count++;
        InviterReturnCashCustomize inviterReturnCash = inviterReturnCashCustomizeMapper.selectReturnCashList(userId);
        if(inviterReturnCash==null){
            return 0;
        }
        if(userNames.contains(inviterReturnCash.getUserName())){
            return count;
        }
        userId = inviterReturnCash.getRefferId();
        if(count>3){
            if(!userNames.contains(inviterReturnCash.getUserName())){
                return 0;
            }
            return count;
        }
        return getLevel(userId,count,userNames);

    }
    @Override
    public  InviterReturnCashCustomize selectReturnCashList(Integer userId){
        InviterReturnCashCustomize inviterReturnCashCustomize = inviterReturnCashCustomizeMapper.selectReturnCashList(userId);
        return inviterReturnCashCustomize;
    }
    @Override
    public List<InviterReturnDetail> selectInviterReturnDetailList(String borrowNid){
        InviterReturnDetailExample example = new InviterReturnDetailExample();
        example.createCriteria().andProductNoEqualTo(borrowNid);
        List<InviterReturnDetail> inviterReturnDetailList = inviterReturnDetailMapper.selectByExample(example);
        return inviterReturnDetailList;
    }
    @Override
    public List<PerformanceReturnDetail> selectPerformanceReturnDetailList(String borrowNid){
        PerformanceReturnDetailExample performanceReturnDetailExample = new PerformanceReturnDetailExample();
        performanceReturnDetailExample.createCriteria().andProductNoEqualTo(borrowNid);
        List<PerformanceReturnDetail> performanceReturnDetailList =  performanceReturnDetailMapper.selectByExample(performanceReturnDetailExample);
        return performanceReturnDetailList;
    }
}
