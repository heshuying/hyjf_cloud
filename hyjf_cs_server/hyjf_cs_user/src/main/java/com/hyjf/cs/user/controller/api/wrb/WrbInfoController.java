package com.hyjf.cs.user.controller.api.wrb;

import com.hyjf.am.response.WrbResponse;
import com.hyjf.am.response.trade.WrbBorrowInvestResponse;
import com.hyjf.am.response.trade.WrbInvestResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderSumCustomizeVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.WrbParseParamUtil;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.bean.WrbNoticeinfoResponse.NoticeinfoDetail;
import com.hyjf.cs.user.service.wrb.WrbInfoServcie;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 *
 * @author lisheng
 * @version WrbNoticeinfoController, v0.1 2018/9/20 14:32
 */
@RestController
@Api(tags = "风车理财-平台公告查询接口")
@RequestMapping("/wrb_interface")
public class WrbInfoController {
    private Logger logger = LoggerFactory.getLogger(WrbInfoController.class);
    public static final String datetimeFormat_key = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private WrbInfoServcie wrbInvestServcie;

    @RequestMapping("/notice_info")
    public WrbNoticeinfoResponse getNoticeinfoDetail(@RequestParam String param,
                                                     @RequestParam(value = "sign", required = false) String sign){
        logger.info("获取平台的公告信息, param is :{}, sign is :{}", param, sign);
        WrbNoticeinfoResponse response=new WrbNoticeinfoResponse();

        WrbNoticeinfoRequest request= null;
        try{
            request = WrbParseParamUtil.mapToBean(WrbParseParamUtil.parseParam(param), WrbNoticeinfoRequest.class);
        }catch(Exception e){
            logger.error("参数解析失败....", e);
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }finally {
            if (request == null) {
                response.setRetcode(WrbResponse.FAIL_RETCODE);
                response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
                return response;
            }
        }
        Integer limit = request.getLimit();
        Integer page = request.getPage();
        if(null == limit || null  == page ){
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }
        MsgPushTemplateRequest msgPushTemplateRequest = new MsgPushTemplateRequest();
        msgPushTemplateRequest.setCurrPage(limit);
        msgPushTemplateRequest.setPageSize(page);
        //只查打开微信的
        msgPushTemplateRequest.setTemplateAction(3);
        //启用的
        msgPushTemplateRequest.setStatus(1);
        //查询平台的公告信息-新
        List<MessagePushTemplateVO> noticeinfoDetailList = wrbInvestServcie.getNoticeinfoDetailNew(msgPushTemplateRequest);
        WrbNoticeinfoResponse wrbNoticeinfoResponse=new WrbNoticeinfoResponse();
        List<NoticeinfoDetail> detailList = new ArrayList<NoticeinfoDetail>();
        for (MessagePushTemplateVO messagePushTemplate:noticeinfoDetailList){
            WrbNoticeinfoResponse.NoticeinfoDetail noticeinfoDetail = new NoticeinfoDetail();
            noticeinfoDetail.setId(String.valueOf(messagePushTemplate.getId()));
            noticeinfoDetail.setContent(messagePushTemplate.getTemplateContent());
            noticeinfoDetail.setTitle(messagePushTemplate.getTemplateTitle());
            noticeinfoDetail.setUrl(messagePushTemplate.getTemplateActionUrl());
            if(messagePushTemplate.getUpdateTime()!=null){
                noticeinfoDetail.setRelease_time(GetDate.dateToString2(messagePushTemplate.getUpdateTime(),datetimeFormat_key));
            }else{
                noticeinfoDetail.setRelease_time("");
            }
            detailList.add(noticeinfoDetail);
        }
        wrbNoticeinfoResponse.setAll_notices(detailList);
        return wrbNoticeinfoResponse;

    }

    /**
     * 标的查询接口
     * @param param
     * @param sign
     * @return
     */
    @RequestMapping("/borrow_list")
    public WrbBorrowReturnBean getBorrowList(@RequestParam String param,
                                               @RequestParam(value = "sign", required = false) String sign) {
        logger.info("标的查询接口, param is :{}, sign is :{}", param, sign);

        WrbBorrowReturnBean response = new WrbBorrowReturnBean();
        Map<String, String> request = WrbParseParamUtil.parseParam(param);
        try {
            // 标的id
            String borrowNid = request.get("invest_id");

            // 1. 查询投资明细
            List<WrbBorrowListCustomizeVO> investList = wrbInvestServcie.searchBorrowListByNid(borrowNid);
            response.setInvest_list(investList);
        } catch (Exception e) {
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
        }


        return response;
    }

    /**
     * 查询标的投资情况
     *
     * @param param
     * @param sign
     */
    @RequestMapping("/bid_invest_list")
    public WrbBorrowInvestResponse getBorrowInvest(@RequestParam String param,
                                                   @RequestParam(value = "sign", required = false) String sign) {
        logger.info("查询标的投资情况, param is :{}, sign is :{}", param, sign);
        WrbBorrowInvestResponse response = new WrbBorrowInvestResponse();

        WrbBorrowInvestRequest request = null;
        try {
            request = WrbParseParamUtil.mapToBean(WrbParseParamUtil.parseParam(param), WrbBorrowInvestRequest.class);
        } catch (Exception e) {
            logger.error("参数解析失败....", e);
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        } finally {
            if (request == null) {
                response.setRetcode(WrbResponse.FAIL_RETCODE);
                response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
                return response;
            }
        }

        // 标的id
        String borrowNid;
        // 字符串时间转换为时间
        Date investTime;
        borrowNid = request.getId();
        // 投资开始时间
        String startTime = request.getStart_time();
        if (isBlank(borrowNid)) {
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }
        try {
            if (startTime == null) {
                BorrowVO borrow = wrbInvestServcie.selectBorrowByBorrowNid(borrowNid);
                if (borrow == null) {
                    response.setRetcode(WrbResponse.FAIL_RETCODE);
                    response.setRetmsg("标的不存在");
                    return response;
                }

                investTime = borrow.getCreateTime();
            } else {
                investTime = GetDate.stringToDate(startTime);
            }
        } catch (Exception e) {
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg("参数有误");
            return response;
        }

        // 1. 查询投资明细
        List<WrbBorrowTenderCustomizeVO> investList = wrbInvestServcie.searchBorrowTenderByNidAndTime(borrowNid,
                investTime);
        response.setInvest_list(this.copyBorrowTenderToResponse(investList) == null ? new ArrayList<WrbBorrowInvestResponse.InvestInfo>() : copyBorrowTenderToResponse(investList));

        // 2. 查询投资汇总数据
        WrbBorrowTenderSumCustomizeVO sumCustomize = wrbInvestServcie.searchBorrowTenderSumByNidAndTime(borrowNid,
                investTime);
        if (sumCustomize != null) {
            response.setAll_investors(sumCustomize.getAllInvestors() == null ? "" : sumCustomize.getAllInvestors());
            response.setInvest_all_money(sumCustomize.getInvestAllMoney() == null ? BigDecimal.ZERO : sumCustomize.getInvestAllMoney());
            response.setFirst_invest_time(sumCustomize.getFirstInvestTime() == null ? "" : sumCustomize.getFirstInvestTime());
            response.setLast_invest_time(sumCustomize.getLastInvestTime() == null ? "" : sumCustomize.getFirstInvestTime());
            response.setBorrow_id(sumCustomize.getBorrowId());

        }

        return response;
    }

    /**
     * 获取某天投资情况
     * @return
     */
    @RequestMapping("/day_invest_list")
    public WrbInvestResponse getInvestDetail(@RequestParam String param,
                                                   @RequestParam(value = "sign", required = false) String sign) {

        logger.info("获取某天投资情况, param is :{}, sign is :{}", param, sign);

        WrbInvestResponse response = new WrbInvestResponse();

        WrbInvestRequest request = null;
        try {
            request = WrbParseParamUtil.mapToBean(WrbParseParamUtil.parseParam(param), WrbInvestRequest.class);
        } catch (Exception e) {
            logger.error("参数解析失败....", e);
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        } finally {
            if (request == null) {
                response.setRetcode(WrbResponse.FAIL_RETCODE);
                response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
                return response;
            }
        }

        Date invest_date = null;
        try {
            invest_date = GetDate.stringToDate2(request.getInvest_date());
        } catch (Exception e) {
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }
        if(invest_date == null){
            logger.error("日期参数有误");
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }
        Integer limit = request.getLimit();
        Integer page = request.getPage();

        if (limit == null || page == null) {
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }

        // 查询投资情况
        List<BorrowTenderVO> borrowTenderList = wrbInvestServcie.getInvestDetail(invest_date, limit, page);

        WrbInvestResponse wrbInvestResponse = new WrbInvestResponse();
        List<WrbInvestResponse.InvestDetail> detailList = new ArrayList();
        if (!CollectionUtils.isEmpty(borrowTenderList)) {
            for (BorrowTenderVO borrowTender : borrowTenderList) {
                WrbInvestResponse.InvestDetail investDetail = new WrbInvestResponse.InvestDetail();
                investDetail.setIndex(borrowTender.getNid());// 订单号
                String borrowNid = borrowTender.getBorrowNid();
                BorrowVO borrow = wrbInvestServcie.selectBorrowByBorrowNid(borrowNid);
                if (borrow != null) {
                    investDetail.setBorrow_id(String.valueOf(borrow.getUserId()));// 借款人ID
                }
                String userName = borrowTender.getUserName();
                if (userName != null) {
                    userName = userName.substring(0, 1).concat("**");
                }
                investDetail.setInvest_user(userName);
                Integer addtime = borrowTender.getAddTime();
                investDetail.setInvest_time(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(addtime));
                investDetail.setInvest_money(borrowTender.getAccount());
                investDetail.setBid_id(borrowNid);
                detailList.add(investDetail);
            }
            wrbInvestResponse.setInvest_list(detailList);
        }
        return wrbInvestResponse;
    }



    /**
     * 格式化优惠券信息
     * @param couponUser
     * @return
     */
    private StringBuffer convertCouponInfo(CouponUserVO couponUser) {
        StringBuffer sbf = new StringBuffer();
        // 优惠券编号
        String couponCode = couponUser.getCouponCode();
        CouponConfigVO couponConfig = wrbInvestServcie.getCouponByCouponCode(couponCode);
        if(couponConfig != null){
            Integer couponType = couponConfig.getCouponType();
            if (1 == couponType) {
                sbf.append("体验金-");
                sbf.append(couponConfig.getCouponQuota()).append("-元-");
            } else if (2 == couponType) {
                sbf.append("加息券-");
                sbf.append(couponConfig.getCouponQuota()).append("%-无-");
            } else {
                sbf.append("代金券-");
                sbf.append(couponConfig.getCouponQuota()).append("-元-");
            }

            // 获取优惠券开始时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Integer add = (int)(couponConfig.getCreateTime().getTime() / 1000L);
            String addTime = "无";//开始和生效时间
            if(add != null){
                long addLong = add.longValue() * 1000;
                Date addTimedate = new Date(addLong);
                addTime = format.format(addTimedate);
            }

            // 获取优惠券结束时间
            Integer end = couponConfig.getExpirationDate();
            String endTime = "无";//结束时间
            if (end != null) {
                long endLong = end.longValue() * 1000;
                Date expirationDate = new Date(endLong);
                endTime = format.format(expirationDate);
            }
            sbf.append(addTime).append("-").append(addTime).append("-").append(endTime);//开始时间与生效时间一样
        }
        return sbf;
    }

    private List<WrbBorrowInvestResponse.InvestInfo> copyBorrowTenderToResponse(List<WrbBorrowTenderCustomizeVO> list) {
        List<WrbBorrowInvestResponse.InvestInfo> investInfoList = null;
        if (!CollectionUtils.isEmpty(list)) {
            investInfoList = new ArrayList<>();
            WrbBorrowInvestResponse.InvestInfo investInfo = null;
            for (WrbBorrowTenderCustomizeVO customize : list) {
                investInfo = new WrbBorrowInvestResponse.InvestInfo();
                investInfo.setIndex(customize.getNid());
                investInfo.setInvest_money(customize.getAccount());
                investInfo.setInvest_time(customize.getInvestTime());
                investInfo.setInvest_user(customize.getUsername());
                investInfoList.add(investInfo);
            }
        }
        return investInfoList;
    }
}
