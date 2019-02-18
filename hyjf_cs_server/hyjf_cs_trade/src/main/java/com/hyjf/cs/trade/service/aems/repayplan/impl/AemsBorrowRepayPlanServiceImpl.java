/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.aems.repayplan.impl;

import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.cs.trade.bean.AemsBorrowRepayPlanRequestBean;
import com.hyjf.cs.trade.service.aems.repayplan.AemsBorrowRepayPlanService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AEMS系统:还款计划查询Service实现类
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanServiceImpl, v0.1 2018/12/12 14:01
 */
@Service
public class AemsBorrowRepayPlanServiceImpl extends BaseTradeServiceImpl implements AemsBorrowRepayPlanService {

    /**
     * 根据机构编号,查询还款计划数量
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectBorrowRepayPlanCountsByInstCode(AemsBorrowRepayPlanRequestBean requestBean) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(requestBean.getInstCode())) {
            param.put("instCode", requestBean.getInstCode());
        }
        // 最后还款日开始
        if (StringUtils.isNotBlank(requestBean.getEndDate())) {
            param.put("startDate", requestBean.getStartDate() + " 00:00:00");
        }
        // 最后还款日结束
        if (StringUtils.isNotBlank(requestBean.getStartDate())) {
            param.put("endDate", requestBean.getEndDate() + " 23:59:59");
        }
        if (StringUtils.isNotBlank(requestBean.getIsMonth())) {
            param.put("isMonth", requestBean.getIsMonth());
        }
        if (StringUtils.isNotBlank(requestBean.getProductId())) {
            param.put("productId", requestBean.getProductId());
        }
        if (StringUtils.isNotBlank(requestBean.getRepayType())) {
            param.put("repayType", requestBean.getRepayType());
        }
        return this.amTradeClient.selectBorrowRepayPlanCountsByInstCode(param);
    }

    /**
     * 根据机构编号,查询还款计划
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<AemsBorrowRepayPlanCustomizeVO> selectBorrowRepayPlanList(AemsBorrowRepayPlanRequestBean requestBean) {

        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(requestBean.getInstCode())) {
            param.put("instCode", requestBean.getInstCode());
        }
        // 最后还款日开始
        if (StringUtils.isNotBlank(requestBean.getEndDate())) {
            param.put("startDate", requestBean.getStartDate() + " 00:00:00");
        }
        // 最后还款日结束
        if (StringUtils.isNotBlank(requestBean.getStartDate())) {
            param.put("endDate", requestBean.getEndDate() + " 23:59:59");
        }
        if (requestBean.getLimitStart() >= 0) {
            param.put("limitStart", requestBean.getLimitStart());
        }
        if (requestBean.getLimitEnd() > 0) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }
        if (StringUtils.isNotBlank(requestBean.getIsMonth())) {
            param.put("isMonth", requestBean.getIsMonth());
        }
        if (StringUtils.isNotBlank(requestBean.getProductId())) {
            param.put("productId", requestBean.getProductId());
        }
        if (StringUtils.isNotBlank(requestBean.getRepayType())) {
            param.put("repayType", requestBean.getRepayType());
        }
        return this.amTradeClient.selectBorrowRepayPlanList(param);
    }

    /**
     * 根据标的编号查询资产推送表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public HjhPlanAssetVO selectHjhPlanAssetByBorrowNid(String borrowNid) {
        return this.amTradeClient.selectHjhPlanAssetByBorrowNid(borrowNid);
    }

    /**
     * 根据标的编号查询还款计划
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> selectBorrowRepayPlanByBorrowNid(String borrowNid) {
        return this.amTradeClient.getBorrowRepayPlansByBorrowNid(borrowNid);
    }
    /**
     * 判断是否逾期 逾期或延期时返回false 逾期或延期时不计算提前还款提前还款减息
     * @param borrow
     * @return
     */
    @Override
    public Boolean getFlag(RightBorrowVO borrow){
        return this.amTradeClient.getOverDueFlag(borrow);
    }
}
