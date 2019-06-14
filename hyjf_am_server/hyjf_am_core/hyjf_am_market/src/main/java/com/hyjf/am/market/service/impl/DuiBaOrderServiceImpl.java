/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.calculate.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.market.dao.mapper.auto.DuibaOrdersMapper;
import com.hyjf.am.market.dao.mapper.auto.DuibaPointsMapper;
import com.hyjf.am.market.dao.mapper.customize.market.DuiBaOrderCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.DuibaOrders;
import com.hyjf.am.market.dao.model.auto.DuibaOrdersExample;
import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.market.service.DuiBaOrderService;
import com.hyjf.am.response.market.DuiBaPointsDetailResponse;
import com.hyjf.am.resquest.market.DuiBaPointsDetailRequest;
import com.hyjf.am.vo.market.DuiBaPointsDetailVO;
import com.hyjf.am.vo.market.DuiBaPointsListDetailVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import com.hyjf.pay.lib.duiba.util.DuiBaCallConstant;


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
    @Autowired
    DuiBaOrderCustomizeMapper duiBaOrderCustomizeMapper;
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
        duibaOrders.setActivationType(1);
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

    @Override
    public DuiBaPointsDetailResponse getPointsDetail(DuiBaPointsDetailRequest request) {
        // 原始请求类型，后面用来区分，获取不同类型的数据
        int requestType = request.getType();
        DuiBaPointsDetailResponse response = new DuiBaPointsDetailResponse();
        // 请求年月
        String year = request.getYear();
        String month = request.getMonth();
        // 防止没查到数据的情况，直接给前端格式化
        DuiBaPointsDetailVO duiBaPointsDetailVO = new DuiBaPointsDetailVO();
        duiBaPointsDetailVO.setList(new ArrayList<>());
        // 查询获取总积分
        request.setType(0);
        Integer pointsGetTotal = duiBaOrderCustomizeMapper.selectPointsTotal(request);
        duiBaPointsDetailVO.setCreditsGot(pointsGetTotal.toString());

        // 查询使用总积分
        request.setType(1);
        Integer pointsUseTotal = duiBaOrderCustomizeMapper.selectPointsTotal(request);
        duiBaPointsDetailVO.setCreditsUsed(pointsUseTotal.toString());
        // 条数>0 做业务处理，否则就直接返回
        // 设置查询时间范围 如果month=0，那么是全年查询，否则是单月查询
        if(Integer.valueOf(month) == 0){
            request.setStartTime(DateUtils.getMinYearDate(year.concat("-01-01")));
            request.setEndTime(DateUtils.getMaxYearDate(year.concat("-01-01")));
        } else {
            request.setStartTime(DateUtils.getMinMonthDate(year.concat("-").concat(month).concat("-01")));
            request.setEndTime(DateUtils.getMaxMonthDate(year.concat("-").concat(month).concat("-01")));
        }
        // 还原原始请求类型，并获取明细条数
        request.setType(requestType);
        Integer count = duiBaOrderCustomizeMapper.countPointsDetail(request);
        if(count > 0){
            List<DuiBaPointsListDetailVO> resultList = new ArrayList<>();

            // 设置分页
            Paginator paginator = new Paginator(request.getCurrentPage(), count, request.getPageSize());
            // 是否最后一页
            duiBaPointsDetailVO.setEnd(paginator.isLastPage());
            // 返回当前页数，请求原样返回
            duiBaPointsDetailVO.setCurrentPage(request.getCurrentPage());
            // 查询的limit
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());

            // 获取积分明细
            List<DuiBaPointsListDetailVO> list = duiBaOrderCustomizeMapper.selectPointsDetail(request);
            // 前一条数据月份
            int preMonth = 0;
            // 循环处理积分明细，将月度总计放到月度最前面
            DuiBaPointsListDetailVO monthTotalVO = null;
            for (DuiBaPointsListDetailVO vo : list) {
                // 如果是第一条，或者与前一条月份不同，那么需要重新获取月统计数据，并放入该月份所有数据的前面
                if(preMonth == 0 || (preMonth != Integer.valueOf(vo.getMonth()))){
                    // 将当前月份作为前月份
                    preMonth = Integer.valueOf(vo.getMonth());
                    monthTotalVO = new DuiBaPointsListDetailVO();
                    // 月统计标识
                    monthTotalVO.setIsTotal(1);
                    // 为了不混淆，总计数据类型为-1
                    monthTotalVO.setType(-1);
                    // 前端显示时间，如果是当月，则显示'本月'，否则显示'xxxx年xx月'
                    if(Integer.valueOf(vo.getYear()) == LocalDate.now().getYear()
                            && Integer.valueOf(vo.getMonth()) == LocalDate.now().getMonthValue()){
                        monthTotalVO.setTime("本月");
                    } else {
                        monthTotalVO.setTime(vo.getYear().concat("年").concat(vo.getMonth()).concat("月"));
                    }

                    // 处理月度积分
                    // 时间条件为当前月份
                    request.setStartTime(DateUtils.getMinMonthDate(vo.getYear().concat("-").concat(vo.getMonth()).concat("-01")));
                    request.setEndTime(DateUtils.getMaxMonthDate(vo.getYear().concat("-").concat(vo.getMonth()).concat("-01")));
                    // 查询月度获取积分
                    request.setType(0);
                    Integer monthPointsGetTotal = duiBaOrderCustomizeMapper.selectPointsTotal(request);
                    monthTotalVO.setPointsGetTotal(monthPointsGetTotal.toString());
                    // 查询月度使用积分
                    request.setType(1);
                    Integer monthPointsUseTotal = duiBaOrderCustomizeMapper.selectPointsTotal(request);
                    monthTotalVO.setPointsUseTotal(monthPointsUseTotal.toString());

                    resultList.add(monthTotalVO);
                }
                vo.setBusinessName(CacheUtil.getParamName(CustomConstants.INTEGRAL_BUSINESS_NAME, vo.getBusinessName()));
                resultList.add(vo);
            }
            // 列表数据
            duiBaPointsDetailVO.setList(resultList);
        }
        response.setResult(duiBaPointsDetailVO);

        return response;
    }
}
