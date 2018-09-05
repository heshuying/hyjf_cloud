package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayInfoListRequest;
import com.hyjf.am.trade.dao.mapper.auto.AdminIncreaseInterestRepayCustomizeMapper;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayInfoListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenxin
 * @version IncreaseInterestRepayInfoListServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class IncreaseInterestRepayInfoListServiceImpl extends BaseServiceImpl implements IncreaseInterestRepayInfoListService {

    @Autowired
    protected AdminIncreaseInterestRepayCustomizeMapper adminIncreaseInterestRepayCustomizeMapper;

    /**
     * 还款明细详情列表件数
     *
     * @Title countBorrowRepaymentInfoList
     * @param form
     * @return
     */
    @Override
    public int getIncreaseInterestRepayInfoListCount(IncreaseInterestRepayInfoListRequest form) {
        Map<String, Object> param = new HashMap<String, Object>();
        // 项目编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            param.put("borrowNidSrch", form.getBorrowNidSrch());
        }
        // 用户名
        if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
            param.put("userNameSrch", form.getUserNameSrch());
        }
        // 投资Id
        if (StringUtils.isNotEmpty(form.getInvestIdSrch())) {
            param.put("investIdSrch", form.getInvestIdSrch());
        }
        // 还款期数
        if (StringUtils.isNotEmpty(form.getRepayPeriodSrch())) {
            param.put("repayPeriodSrch", form.getRepayPeriodSrch());
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            param.put("timeStartSrch", form.getTimeStartSrch());
            param.put("timeEndSrch", form.getTimeEndSrch());
        }
        return this.adminIncreaseInterestRepayCustomizeMapper.countBorrowRepaymentInfoList(param);
    }

    /**
     * 还款明细详情列表
     *
     * @Title selectBorrowRepaymentInfoListList
     * @param form
     * @return
     */
    @Override
    public List<AdminIncreaseInterestRepayCustomizeVO> getIncreaseInterestRepayInfoListList(IncreaseInterestRepayInfoListRequest form) {
        Map<String, Object> param = new HashMap<String, Object>();
        // 项目编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            param.put("borrowNidSrch", form.getBorrowNidSrch());
        }
        // 用户名
        if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
            param.put("userNameSrch", form.getUserNameSrch());
        }
        // 投资Id
        if (StringUtils.isNotEmpty(form.getInvestIdSrch())) {
            param.put("investIdSrch", form.getInvestIdSrch());
        }
        // 还款期数
        if (StringUtils.isNotEmpty(form.getRepayPeriodSrch())) {
            param.put("repayPeriodSrch", form.getRepayPeriodSrch());
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            param.put("timeStartSrch", form.getTimeStartSrch());
            param.put("timeEndSrch", form.getTimeEndSrch());
        }
        // 还款期数
        if (StringUtils.isNotEmpty(form.getRepayPeriodSrch())) {
            param.put("repayPeriodSrch", form.getRepayPeriodSrch());
        }
        if (form.getLimitStart() != -1) {
            param.put("limitStart", form.getLimitStart());
            param.put("limitEnd", form.getLimitEnd());
        }
        return this.adminIncreaseInterestRepayCustomizeMapper.selectBorrowRepaymentInfoListList(param);
    }

    @Override
    public AdminIncreaseInterestRepayCustomizeVO sumBorrowLoanmentInfo(IncreaseInterestRepayInfoListRequest form) {

        Map<String, Object> param = new HashMap<String, Object>();
        // 项目编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            param.put("borrowNidSrch", form.getBorrowNidSrch());
        }
        // 用户名
        if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
            param.put("userNameSrch", form.getUserNameSrch());
        }
        // 投资Id
        if (StringUtils.isNotEmpty(form.getInvestIdSrch())) {
            param.put("investIdSrch", form.getInvestIdSrch());
        }
        // 还款期数
        if (StringUtils.isNotEmpty(form.getRepayPeriodSrch())) {
            param.put("repayPeriodSrch", form.getRepayPeriodSrch());
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            param.put("timeStartSrch", form.getTimeStartSrch());
            param.put("timeEndSrch", form.getTimeEndSrch());
        }
        return this.adminIncreaseInterestRepayCustomizeMapper.sumBorrowLoanmentInfo(param);
    }
}
