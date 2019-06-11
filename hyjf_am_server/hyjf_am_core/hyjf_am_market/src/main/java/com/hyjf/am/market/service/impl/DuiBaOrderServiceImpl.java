/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.DuibaOrdersMapper;
import com.hyjf.am.market.dao.mapper.auto.DuibaPointsMapper;
import com.hyjf.am.market.dao.model.auto.DuibaOrders;
import com.hyjf.am.market.dao.model.auto.DuibaOrdersExample;
import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.market.service.DuiBaOrderService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import com.hyjf.pay.lib.duiba.util.DuiBaCallConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


/**
 * @author wangjun
 * @version DuiBaServiceImpl, v0.1 2019/6/10 10:55
 */
@Service
public class DuiBaOrderServiceImpl implements DuiBaOrderService {
    @Autowired
    DuibaOrdersMapper duibaOrdersMapper;
    @Autowired
    DuibaPointsMapper duibaPointsMapper;
    @Override
    public int countDuiBaOrder(String duiBaOrderId) {
        DuibaOrdersExample duibaOrdersExample = new DuibaOrdersExample();
        duibaOrdersExample.createCriteria().andDuibaOrderIdEqualTo(duiBaOrderId);
        return duibaOrdersMapper.countByExample(duibaOrdersExample);
    }

    @Override
    public void insertDuiBaOrder(CreditConsumeParams consumeParams) {
        // 插入兑吧订单
        DuibaOrders duibaOrders = new DuibaOrders();
        // 兑吧订单号
        duibaOrders.setDuibaOrderId(consumeParams.getOrderNum());
        // 汇盈订单号
        duibaOrders.setHyOrderId(consumeParams.getBizId());
        // 用户名
        duibaOrders.setUserName(consumeParams.getUserName());
        // 真实姓名
        duibaOrders.setTrueName(consumeParams.getTrueName());
        // 用户ID
        duibaOrders.setUserId(Integer.valueOf(consumeParams.getUid()));
        // 兑换内容
        duibaOrders.setExchangeContent(consumeParams.getDescription());
        // 商品类型
        String type = consumeParams.getType();
        if(DuiBaCallConstant.TYPE_OBJECT.equals(type)){
            duibaOrders.setProductType("0");
        } else if(DuiBaCallConstant.TYPE_COUPON.equals(type)){
            duibaOrders.setProductType("1");
        } else if(DuiBaCallConstant.TYPE_VIRTUAL.equals(type)){
            duibaOrders.setProductType("2");
        }
        // 售价
        BigDecimal duiBaRate = new BigDecimal(RedisUtils.get(RedisConstants.DUIBA_EXCHANGE_RATE));
        duibaOrders.setSellingPrice(new BigDecimal(consumeParams.getCredits()).divide(duiBaRate,2, BigDecimal.ROUND_DOWN));
        // 划线价 返回单位为分，转换一下
        if(null != consumeParams.getFacePrice()){
            duibaOrders.setMarkingPrice(new BigDecimal(consumeParams.getFacePrice()).divide(new BigDecimal("0.01"),2, BigDecimal.ROUND_DOWN));
        }
        // 成本 返回单位为分，转换一下
        if(null != consumeParams.getActualPrice()){
            duibaOrders.setCost(new BigDecimal(consumeParams.getActualPrice()).divide(new BigDecimal("0.01"),2, BigDecimal.ROUND_DOWN));
        }
        // 订单状态
        duibaOrders.setOrderStatus(2);
        // 下单时间
        duibaOrders.setOrderTime(GetDate.getNowTime10());
        // 发货状态，收货信息，只有实物的时候存初始值
        if(DuiBaCallConstant.TYPE_OBJECT.equals(type)){
            duibaOrders.setDeliveryStatus(0);
            duibaOrders.setReceivingInformation(consumeParams.getParams());
        }
        // 商品编码，只有虚拟充值时设置，为汇盈优惠券编码
        if(DuiBaCallConstant.TYPE_VIRTUAL.equals(type)){
            duibaOrders.setCommodityCode(consumeParams.getParams());
        }
        duibaOrders.setExchangeRate(duiBaRate);
        // 订单扣减积分
        duibaOrders.setIntegralPrice(consumeParams.getCredits().intValue());
        // 订单有效状态
        duibaOrders.setActivationType(false);
        duibaOrdersMapper.insertSelective(duibaOrders);

        // 插入积分明细
        DuibaPoints duibaPoints = new DuibaPoints();
        // 用户id
        duibaPoints.setUserId(Integer.valueOf(consumeParams.getUid()));
        // 用户名
        duibaPoints.setUserName(consumeParams.getUserName());
        // 用户真实姓名
        duibaPoints.setTrueName(consumeParams.getTrueName());
        // 操作积分数
        duibaPoints.setPoints(consumeParams.getCredits().intValue());
        // 用户操作后总积分
        duibaPoints.setTotal(consumeParams.getCreditsCurrent());
        // 积分类型 0:获取，1:使用
        duibaPoints.setType(1);
        // 积分业务类型  0出借 1商品兑换 2订单取消
        duibaPoints.setBusinessName(1);
        // 兑吧订单号
        duibaPoints.setDuibaOrderId(consumeParams.getOrderNum());
        // 汇盈订单号
        duibaPoints.setHyOrderId(consumeParams.getBizId());
        duibaPointsMapper.insertSelective(duibaPoints);
    }
}
