package com.hyjf.cs.message.service.access.impl;

import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.AppAccesStatistics;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.ic.AppAccesStatisticsDao;
import com.hyjf.cs.message.mq.base.CommonProducer;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.service.access.AppChannelStatisticsService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author：yinhui
 * @Date: 2018/10/22  16:39
 */
@Service
public class AppChannelStatisticsServiceImpl extends BaseServiceImpl implements AppChannelStatisticsService {

    @Autowired
    private AppAccesStatisticsDao appAccesStatisticsDao;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private CommonProducer producer;

    /**
     * 根据开始时间、结束时间和来源查询数据
     * @param request
     * @return
     */
    @Override
    public List<AppAccesStatisticsVO> getAppAccesStatisticsVO(AppChannelStatisticsRequest request){
        String timeStartSrch = request.getTimeStartSrch();
        String timeEndSrch = request.getTimeEndSrch();
        String sourceId = request.getSourceId();

        DBObject obj = new BasicDBObject();
        DBObject object = new BasicDBObject();

        try{
            //开始时间查询
            if(StringUtils.isNotBlank(timeStartSrch)){
                object.put("$gte", GetDate.stringToDate(timeStartSrch));
                obj.put("accessTime",object);
            }

            //结束时间查询
            if(StringUtils.isNotBlank(timeEndSrch)){
                object.put("$lte",GetDate.stringToDate(timeEndSrch));
                obj.put("accessTime",object);
            }

            if(StringUtils.isNotBlank(sourceId)){
                obj.put("sourceId",Integer.valueOf(sourceId));
            }

            Query query = new BasicQuery(obj.toString());
            List<AppAccesStatistics> list = appAccesStatisticsDao.getAppAccesStatistics(query);
            List<AppAccesStatisticsVO> appAccesStatisticsVO = CommonUtils.convertBeanList(list, AppAccesStatisticsVO.class);

            return appAccesStatisticsVO;

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void insertStatistics() {
        logger.info("----------------APP渠道统计定时任务Start-------------");

        AppChannelStatisticsRequest request = new AppChannelStatisticsRequest();
//		String nowDate = "2018-08-21";
        String nowDate = GetDate.date2Str(GetDate.date_sdf);
        request.setTimeStartSrch(GetDate.getDayStart(nowDate));
        request.setTimeEndSrch(GetDate.getDayEnd(nowDate));

        // 查询所有app渠道
        List<UtmVO> voList = amUserClient.selectUtmPlatList("app");
        if (!CollectionUtils.isEmpty(voList)) {
            for (UtmVO vo : voList) {
                Integer sourceId = vo.getSourceId();
                request.setSourceId(String.valueOf(sourceId));
                // 访问数
                int accessNumber = getAccessNumber(request);
                // 注册数
                int registNumber = getRegistNumber(request);
                // 开户数
                int openaccountnumber = getOpenAccountNumber(request);
                // 查询app渠道相关统计信息
                AppUtmRegResponse response = amUserClient.getAppUtmRegResponse(request);
                // 查询相应的app渠道无主单开户数
                int openAccountAttrCount = getOpenAccountAttrCount(request, response.getOpenAccountTimeList());
                // 查询相应的app渠道出借无主单用户数
                int investAttrNumber = getTenderNumber(request,"all", response.getHxfTenderPriceList());
                // 累计充值
                BigDecimal cumulativeRecharge = getCumulativeRecharge(request, response.getResultList());
                // 汇直投出借金额
                BigDecimal hztTenderPrice = getHztTenderPrice(request, response.getHztTenderPriceList());
                // 汇消费出借金额
                BigDecimal hxfTenderPrice = getHxfTenderPrice(request, response.getHxfTenderPriceList());
                // 汇天利出借金额
                BigDecimal htlTenderPrice = BigDecimal.ZERO;
                // 汇添金出借金额
                BigDecimal htjTenderPrice = BigDecimal.ZERO;
                // 汇金理财出借金额
                BigDecimal rtbTenderPrice = BigDecimal.ZERO;
                // 汇转让出借金额
                BigDecimal hzrTenderPrice = getHzrTenderPrice(request, response.getResultList());
                // app渠道主单注册数
                BigDecimal registerAttrCount = getRegisterAttrCount(request, response.getResultList());
                // 查询相应的app渠道Ios开户数
                int accountNumberIos = getAccountNumber(request,"ios", response.getOpenAccountTimeList());
                // 查询相应的app渠道PC开户数
                int accountNumberPc =getAccountNumber(request,"pc", response.getOpenAccountTimeList());
                // 查询相应的app渠道安卓开户数
                int accountNumberAndroid = getAccountNumber(request,"android", response.getOpenAccountTimeList());
                // 查询相应的app渠道微信开户数
                int accountNumberWechat =  getAccountNumber(request,"wechat", response.getOpenAccountTimeList());
                // 查询相应的app渠道用户Android出借数
                int tenderNumberAndroid = getTenderNumber(request,"android", response.getHxfTenderPriceList());
                // 查询相应的app渠道用户IOS出借数
                int tenderNumberIos = getTenderNumber(request,"ios", response.getHxfTenderPriceList());
                // 查询相应的app渠道用户PC出借数
                int tenderNumberPc = getTenderNumber(request,"pc", response.getHxfTenderPriceList());
                // 查询相应的app渠道用户微信出借数
                int tenderNumberWechat = getTenderNumber(request,"wechat", response.getHxfTenderPriceList());
                // 出借用户数
                int investNumber = getTenderNumber(request,"all", response.getHxfTenderPriceList());
                // 查询相应的app渠道无主单用户充值数
                BigDecimal cumulativeAttrCharge = getCumulativeAttrCharge(request, response.getHxfTenderPriceList());
                // 查询相应的app渠道无主单用户出借总额
                BigDecimal cumulativeAttrInvest = hztTenderPrice.add(hxfTenderPrice).add(hzrTenderPrice);
                // 查询相应的app渠道累计出借
                BigDecimal cumulativeInvest = hztTenderPrice.add(hxfTenderPrice).add(hzrTenderPrice);
                AppChannelStatisticsVO statisticsVO = new AppChannelStatisticsVO(sourceId, vo.getSourceName(),
                        accessNumber, registNumber, openaccountnumber, investNumber, cumulativeRecharge, hztTenderPrice,
                        hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date(),
                        registerAttrCount, accountNumberIos, accountNumberPc, accountNumberAndroid, accountNumberWechat,
                        tenderNumberAndroid, tenderNumberIos, tenderNumberPc, tenderNumberWechat, cumulativeAttrCharge,
                        openAccountAttrCount, investAttrNumber, cumulativeAttrInvest,cumulativeInvest);
                try {
                    producer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_TOPIC,
                            System.currentTimeMillis() + "", statisticsVO));
                } catch (MQException e) {
                    logger.error("APP渠道统计数据定时任务出错......", e);
                }
            }
        }
    }

    private BigDecimal getCumulativeAttrCharge(AppChannelStatisticsRequest request, List<AppUtmRegVO> list ) {
        if(CollectionUtils.isEmpty(list)){
            return BigDecimal.ZERO;
        }

        List<Integer> integerList = amUserClient.getUsersInfoList();
        Set<Integer> setUnUserId = new HashSet<>();
        Set<Integer> setUserId = new HashSet<>();
        for(AppUtmRegVO appVo : list){
            setUnUserId.add(appVo.getUserId());
        }

        for(Integer ids : integerList){

            if(setUnUserId.contains(ids)){

                setUserId.add(ids);
            }
        }

        List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amTradeClient.getAccountRechargeByAddtime(request);

        BigDecimal money = BigDecimal.ZERO;
        for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

            if(setUserId.contains(vo.getUserId())){

                money = money.add(vo.getAccount());
            }
        }

        return money;
    }

    private int getAccountNumber(AppChannelStatisticsRequest request, String source, List<AppUtmRegVO> list) {
       if (CollectionUtils.isEmpty(list)) {
           return 0;
       }

        List<Integer> listUserId = amUserClient.getUsersList(source);
        if(listUserId == null || listUserId.size() == 0){
            return 0;
        }

        int i = 0;
        for(AppUtmRegVO vo : list){
            boolean flag = listUserId.contains(vo.getUserId());
            if(flag){
                i++;
            }
        }

        return i;
    }

    private BigDecimal getRegisterAttrCount(AppChannelStatisticsRequest request, List<AppUtmRegVO> list) {
        if(CollectionUtils.isEmpty(list)){
            return BigDecimal.ZERO;
        }

        Set<Integer> setUserId = new HashSet<>();
        for(AppUtmRegVO appVo : list){

            setUserId.add(appVo.getUserId());
        }

        List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amTradeClient.getAccountRechargeByAddtime(request);

        BigDecimal money = BigDecimal.ZERO;
        for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

            if(setUserId.contains(vo.getUserId())){

                money = money.add(vo.getAccount());
            }
        }

        return money;
    }

    private BigDecimal getHzrTenderPrice(AppChannelStatisticsRequest request, List<AppUtmRegVO> list) {
        if(CollectionUtils.isEmpty(list)){
            return BigDecimal.ZERO;
        }

        Set<Integer> setUserId = new HashSet<>();
        for(AppUtmRegVO appVo : list){

            setUserId.add(appVo.getUserId());
        }

        List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amTradeClient.getCreditTenderByAddtime(request);

        BigDecimal money = BigDecimal.ZERO;
        for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

            if(setUserId.contains(vo.getUserId())){

                money = money.add(vo.getAccount());
            }
        }

        return money;
    }

    private BigDecimal getHxfTenderPrice(AppChannelStatisticsRequest request, List<AppUtmRegVO> list) {
        if(CollectionUtils.isEmpty(list)){
            return BigDecimal.ZERO;
        }

        Set<Integer> setUserId = new HashSet<>();
        for(AppUtmRegVO appVo : list){

            setUserId.add(appVo.getUserId());
        }

        List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amTradeClient.getBorrowTenderByAddtime(request);

        BigDecimal money = BigDecimal.ZERO;
        for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

            if(setUserId.contains(vo.getUserId())){

                money = money.add(vo.getAccount());
            }
        }

        return money;
    }

    private BigDecimal getHztTenderPrice(AppChannelStatisticsRequest request, List<AppUtmRegVO> list) {
        if(CollectionUtils.isEmpty(list)){
            return BigDecimal.ZERO;
        }

        Set<Integer> setUserId = new HashSet<>();
        for(AppUtmRegVO appVo : list){

            setUserId.add(appVo.getUserId());
        }

        List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amTradeClient.getBorrowTenderByAddtime(request);

        BigDecimal money = BigDecimal.ZERO;
        for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

            if(setUserId.contains(vo.getUserId())){

                money = money.add(vo.getAccount());
            }
        }

        return money;
    }

    private BigDecimal getCumulativeRecharge(AppChannelStatisticsRequest request, List<AppUtmRegVO> list) {
        if(CollectionUtils.isEmpty(list)){
            return BigDecimal.ZERO;
        }

        Set<Integer> setUserId = new HashSet<>();
        for(AppUtmRegVO appVo : list){

            setUserId.add(appVo.getUserId());
        }

        List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amTradeClient.getAccountRechargeByAddtime(request);

        BigDecimal money = BigDecimal.ZERO;
        for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

            if(setUserId.contains(vo.getUserId())){

                money = money.add(vo.getAccount());
            }
        }

        return money;
    }

    /**
     * app渠道Ios、pc、wechat、android开户数
     * @param request
     * @param source
     * @param list
     * @return
     */
    private int getTenderNumber(AppChannelStatisticsRequest request, String source, List<AppUtmRegVO> list) {
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }

        int i = 0;
        //1、
        List<WrbTenderNotifyCustomizeVO> list1 = amTradeClient.getBorrowTenderByClient(request);
        if(list1.size() > 0){

            for(AppUtmRegVO vo : list){

                for(WrbTenderNotifyCustomizeVO tender : list1 ){
                    if(vo.getUserId().equals(tender.getUserId())){
                        i++;
                        break;
                    }
                }

            }
        }

        //2、
        List<WrbTenderNotifyCustomizeVO> list2 = amTradeClient.getProductListByClient(request);
        if(list2.size() > 0){

            for(AppUtmRegVO vo : list){

                for(WrbTenderNotifyCustomizeVO tender : list2 ){
                    if(vo.getUserId().equals(tender.getUserId())){
                        i++;
                        break;
                    }
                }

            }
        }

        //3、
        List<WrbTenderNotifyCustomizeVO> list3 = amTradeClient.getDebtPlanAccedeByClient(request);
        if(list3.size() > 0){

            for(AppUtmRegVO vo : list){

                for(WrbTenderNotifyCustomizeVO tender : list3 ){
                    if(vo.getUserId().equals(tender.getUserId())){
                        i++;
                        break;
                    }
                }

            }
        }

        //4、
        List<WrbTenderNotifyCustomizeVO> list4 = amTradeClient.getCreditTenderByClient(request);
        if(list4.size() > 0){

            for(AppUtmRegVO vo : list){

                for(WrbTenderNotifyCustomizeVO tender : list4 ){
                    if(vo.getUserId().equals(tender.getUserId())){
                        i++;
                        break;
                    }
                }

            }
        }

        return i;
    }

    /**
     * 查询相应的app渠道无主单开户数
     * @param request
     * @return
     */
    private int getOpenAccountAttrCount(AppChannelStatisticsRequest request, List<AppUtmRegVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return amUserClient.getOpenAccountAttrCount(request);
    }

    /**
     * 获取开户数
     * @param request
     * @return
     */
    private int getOpenAccountNumber(AppChannelStatisticsRequest request) {
        request.setSourceIdSrch("openAccountTime");
        return amUserClient.getAppChannelStatisticsDetailVO(request);
    }

    /**
     * 获取注册数
     * @param request
     * @return
     */
    private int getRegistNumber(AppChannelStatisticsRequest request) {
        request.setSourceIdSrch("registerTime");
        return amUserClient.getAppChannelStatisticsDetailVO(request);
    }

    /**
     * 获取访问数
     * @param request
     * @return
     */
    private int getAccessNumber(AppChannelStatisticsRequest request) {
        String timeStartSrch = request.getTimeStartSrch();
        String timeEndSrch = request.getTimeEndSrch();
        String sourceId = request.getSourceId();

        DBObject obj = new BasicDBObject();
        DBObject object = new BasicDBObject();

        try{
            //开始时间查询
            if(StringUtils.isNotBlank(timeStartSrch)){
                object.put("$gte", GetDate.stringToDate(timeStartSrch));
                obj.put("accessTime",object);
            }

            //结束时间查询
            if(StringUtils.isNotBlank(timeEndSrch)){
                object.put("$lte",GetDate.stringToDate(timeEndSrch));
                obj.put("accessTime",object);
            }

            if(StringUtils.isNotBlank(sourceId)){
                obj.put("sourceId",Integer.valueOf(sourceId));
            }

            Query query = new BasicQuery(obj.toString());
            long count = appAccesStatisticsDao.count(query);
            return Math.toIntExact(count);

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return 0;
    }

}
