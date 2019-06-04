/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.order;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.market.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.service.front.coupon.CouponConfigService;
import com.hyjf.am.trade.service.front.coupon.CouponUserService;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.service.admin.promotion.ChannelService;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallBean;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallResultBean;
import com.hyjf.pay.lib.duiba.util.DuiBaCallConstant;
import com.hyjf.pay.lib.duiba.util.DuiBaCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 兑吧订单（订单查询,订单发货,异常订单）列表
 * @author wenxin
 * @version DuibaOrderListController, v0.1 2019/5/29 9:46
 */

@RestController
@RequestMapping("/am-market/pointsshop/duiba/order")
public class DuibaOrderListController {
    private static final Logger logger = LoggerFactory.getLogger(DuibaOrderListController.class);

    @Autowired
    private DuibaOrderListService duibaOrderListService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private CouponConfigService couponConfigService;

    @Autowired
    private CouponUserService couponUserService;

    @Autowired
    private CommonProducer commonProducer;

    @PostMapping("/findOrderList")
    public DuibaOrderResponse findOrderList(@RequestBody DuibaOrderRequest request){
        DuibaOrderResponse response = new DuibaOrderResponse();
        Integer count = duibaOrderListService.selectOrderListCount(request);
        if (count > 0) {
            List<DuibaOrderVO> list = this.duibaOrderListService.selectOrderList(request);
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setRecordTotal(count);
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }

    @GetMapping ("/synchronization/{orderId}")
    public String synchronization(@PathVariable Integer orderId){
        // 同步接口开始 DuiBaCallUtils.duiBaCall()
        logger.info("订单同步接口开始同步，orderId  = {orderId}");
        // 查询需要同步的订单信息（兑吧订单表）
        DuibaOrderVO duibaOrderVO = duibaOrderListService.findOneOrder(orderId);
        DuiBaCallBean duiBaCallBean = new DuiBaCallBean();
        duiBaCallBean.setOrderNum(duibaOrderVO.getDuibaOrderId());
        duiBaCallBean.setUserId(duibaOrderVO.getUserId());
        duiBaCallBean.setBizId(duibaOrderVO.getHyOrderId());
        duiBaCallBean.setMsgType(DuiBaCallConstant.TYPE_ORDER_QUERY);
        DuiBaCallResultBean duiBaCallResultBean = DuiBaCallUtils.duiBaCall(duiBaCallBean);
        // 根据兑吧回传的结果进行数据处理及赋值操作
        if(duiBaCallResultBean.isSuccess()){
            // 返回成功更新订单状态（success为true的时候返回，分为：create 创建订单后的初始状态、process 处理中、success 兑换成功、fail 兑换失败）
            if("process".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setOrderStatus(2);
            }else if("success".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setOrderStatus(0);
            }else if("fail".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setOrderStatus(1);
            }
            // 更新订单发货状态（订单子状态，success为true的时候返回，分为：create 创建订单后的初始状态、process 处理中、waitAudit 待审核、waitSend 待发货、success 兑换成功、fail 兑换失败）
            if("waitSend".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setDeliveryStatus(0);
            }else if("success".equals(duiBaCallResultBean.getStatus())){
                duibaOrderVO.setDeliveryStatus(1);
            }
            // 执行更新
            duibaOrderListService.updateOneOrderByPrimaryKey(duibaOrderVO);

        }else{
            return duiBaCallResultBean.getErrorMessage();
        }
        return "订单同步成功！";
    }


    /**
     * 根据兑吧订单的兑吧订单号查询用户订单信息并发放优惠卷
     *
     * @param orderNum
     * @return
     */
    @RequestMapping("/releaseCoupons/{orderNum}")
    public String selectCouponUserById(@PathVariable String orderNum) {
        if(StringUtils.isNotEmpty(orderNum)){
            // 根据兑吧订单号查询订单信息
            DuibaOrderVO duibaOrderVO = duibaOrderListService.selectOrderByOrderId(orderNum);
            // 发放优惠卷 需要的参数 优惠券编码  couponCode 用户id  userId 备注  content
            if(duibaOrderVO != null && duibaOrderVO.getUserId() != null) {
                // 根据用户id获取用户详情信息
                UserInfo userInfo = userInfoService.findUserInfoById(duibaOrderVO.getUserId());
                // 根据用户id获取注册时渠道名
                String channelName = channelService.selectChannelName(duibaOrderVO.getUserId());
                // 根据优惠券编码查询优惠券
                CouponConfigVO configVO = couponConfigService.getCouponConfigByOrderId(duibaOrderVO.getCommodityCode());
                CouponUserRequest couponUserRequest = new CouponUserRequest();
                // 截止日
                if (configVO.getExpirationType() == 1) {
                    couponUserRequest.setEndTime(configVO.getExpirationDate());
                } else if (configVO.getExpirationType() == 2) {
                    Date endDate = GetDate.countDate(GetDate.getDate(), 2, configVO.getExpirationLength());
                    couponUserRequest.setEndTime((int) (endDate.getTime() / 1000));
                } else if (configVO.getExpirationType() == 3) {
                    Date endDate = GetDate.countDate(GetDate.getDate(), 5, configVO.getExpirationLengthDay());
                    couponUserRequest.setEndTime((int) (endDate.getTime() / 1000));
                }
                couponUserRequest.setUserId(duibaOrderVO.getUserId());
                couponUserRequest.setCouponCode(configVO.getCouponCode());
                couponUserRequest.setContent("兑吧积分兑换优惠卷");
                couponUserRequest.setCouponUserCode(GetCode.getCouponUserCode(configVO.getCouponType()));
                // 优惠卷 的发放信息创建人是用户自己本身记录用户的userid
                couponUserRequest.setCreateUserId(duibaOrderVO.getUserId());
                couponUserRequest.setCreateTime(GetDate.getDate());
                // 优惠卷 的发放信息修改人是用户自己本身记录用户的userid
                couponUserRequest.setUpdateUserId(duibaOrderVO.getUserId());
                couponUserRequest.setUpdateTime(GetDate.getDate());
                couponUserRequest.setDelFlag(CustomConstants.FALG_NOR);
                couponUserRequest.setUsedFlag(CustomConstants.USER_COUPON_STATUS_WAITING_PUBLISH);
                couponUserRequest.setReadFlag(CustomConstants.USER_COUPON_READ_FLAG_NO);
                couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_INTEGRAL);
                couponUserRequest.setAttribute(userInfo.getAttribute());
                couponUserRequest.setChannel(channelName);
                int remain = couponConfigService.checkCouponSendExcess(duibaOrderVO.getCommodityCode());
                boolean countType = remain > 0 ? true : false;
                if (!countType) {
                    logger.info("优惠券发行数量超出上限，不再发放！");
                    return "error";
                }
                try {
                    // 插入审核状态并确认优惠卷状态
                    couponUserRequest.setAuditContent("兑吧积分兑换优惠卷");
                    couponUserRequest.setUsedFlag(CustomConstants.USER_COUPON_STATUS_UNUSED);
                    // 推送通知消息
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("val_number", String.valueOf(1));
                    param.put("val_coupon_type", configVO.getCouponType() == 1 ? "体验金" : configVO.getCouponType() == 2 ? "加息券" : configVO.getCouponType() == 3 ? "代金券" : "");
                    AppMsMessage appMsMessage = new AppMsMessage(couponUserRequest.getUserId(), param, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_COUPON_SUCCESS);
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(couponUserRequest.getUserId()),
                                appMsMessage));
                    } catch (MQException e) {
                        logger.error(e.getMessage());
                    }
                    // 插入优惠卷信息
                    CouponUser couponUser = couponUserService.insertByDuibaOrder(couponUserRequest);
                    if (couponUser.getId() != null) {
                        DuibaOrderVO duibaOrderVO1 = new DuibaOrderVO();
                        // 设置更新主键
                        duibaOrderVO1.setId(duibaOrderVO.getId());
                        // 设置更新用户订单表的优惠卷用户表id
                        duibaOrderVO1.setCouponUserId(couponUser.getId());
                        // 更新订单表信息插入优惠卷用户表主键id
                        int uocount = duibaOrderListService.updateOneOrderByPrimaryKey(duibaOrderVO1);
                        // 有更新数据返回成功
                        if(uocount > 0){
                            // 优惠卷发放成功
                            return "success";
                        }else{
                            logger.error("优惠券发放失败！更新“订单表”信息插入“优惠卷用户表主键”失败：duibaOrder 表主键 ID：" + duibaOrderVO.getId());
                            return "error";
                        }
                    }else{
                        logger.error("优惠插入失败！，返回优惠卷用户表的主键id为空：" + couponUser.getId());
                        return "error";
                    }
                } catch (Exception e) {
                    logger.error("优惠券发放失败！异常如下：" + e.getMessage());
                    return "error";
                }
            }else{
                logger.info("根据兑吧订单的兑吧订单号，查询用户订单信息并发放优惠卷，订单信息为空！ duibaOrderVO:" + duibaOrderVO.toString());
                return "error";
            }
        }else{
            logger.info("根据兑吧订单的兑吧订单号查询用户订单信息并发放优惠卷，兑吧订单号为空！ orderNum:" + orderNum);
            return "error";
        }
    }


    /**
     * 兑吧兑换结果通知接口（失败时设置订单无效）
     *
     * @param orderNum
     * @return
     */
    @RequestMapping("/activation/{orderNum}")
    public String activation(@PathVariable String orderNum) {
        int res;
        // 2.将发放的优惠卷设置成无效, 3.兑换失败将对应的"订单"设置成无效并给出失败信息
        DuibaOrderVO duibaOrderVO = new DuibaOrderVO();
        // 根据兑吧订单号查询订单信息
        DuibaOrderVO duibaOrderVOStr = duibaOrderListService.selectOrderByOrderId(orderNum);
        if(duibaOrderVOStr!=null){
            CouponUserBeanRequest couponUserBeanRequest = new CouponUserBeanRequest();
            // 设置主键
            couponUserBeanRequest.setId(duibaOrderVOStr.getCouponUserId());
            // 设置无效描述
            couponUserBeanRequest.setContent("兑吧兑换结果通知接口返回失败，回滚该笔数据！");
            // 设置优惠卷用户表无效
            couponUserService.deleteCouponUserById(couponUserBeanRequest);
            // - - - - - - - - - - - - - - - - - - - - - - - - - -
            duibaOrderVO.setId(duibaOrderVOStr.getId());
            // 设置订单无效
            duibaOrderVO.setActivationType(1);
            // 设置更新日期
            duibaOrderVO.setUpdateTime(new Date());
            // 设置订单状态为失败
            duibaOrderVO.setOrderStatus(1);
            // 设置失败原因
            duibaOrderVO.setRemark("兑吧兑换结果通知接口返回失败，回滚该笔数据！");
            // 根据订单信息更新订单状态
            res = duibaOrderListService.updateOneOrderByPrimaryKey(duibaOrderVO);
            if(res > 0){
                return "success";
            }
        }
        return "error";
    }
}
