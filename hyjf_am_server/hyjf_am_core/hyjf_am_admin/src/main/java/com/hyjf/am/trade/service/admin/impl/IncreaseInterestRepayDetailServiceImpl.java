package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayDetailRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminIncreaseInterestRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.auto.IncreaseInterestRepayMapper;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepay;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayExample;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenxin
 * @version IncreaseInterestRepayDetailServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class IncreaseInterestRepayDetailServiceImpl extends BaseServiceImpl implements IncreaseInterestRepayDetailService {

    @Autowired
    protected IncreaseInterestRepayMapper increaseInterestRepayMapper;

    @Autowired
    protected AdminIncreaseInterestRepayCustomizeMapper adminIncreaseInterestRepayCustomizeMapper;
    /**
     * 融通宝加息还款信息检索件数
     *
     * @Title countRecordList
     * @param form
     * @return
     */
    @Override
    public int getIncreaseInterestRepayDetailCount(IncreaseInterestRepayDetailRequest form) {
        IncreaseInterestRepayExample example = new IncreaseInterestRepayExample();
        IncreaseInterestRepayExample.Criteria cra = example.createCriteria();
        // 项目编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(form.getBorrowNidSrch());
        }
        // 项目状态
        if (StringUtils.isNotEmpty(form.getRepayStatusSrch())) {
            cra.andRepayStatusEqualTo(Integer.parseInt(form.getRepayStatusSrch()));
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            cra.andRepayTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayStart(form.getTimeStartSrch())));
            cra.andRepayTimeLessThanOrEqualTo( GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayEnd(form.getTimeEndSrch())));
        }
        return this.increaseInterestRepayMapper.countByExample(example);
    }

    /**
     * 融通宝加息还款信息检索列表
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public List<AdminIncreaseInterestRepayCustomizeVO> getIncreaseInterestRepayDetailList(IncreaseInterestRepayDetailRequest form) {
        IncreaseInterestRepayExample example = new IncreaseInterestRepayExample();
        IncreaseInterestRepayExample.Criteria cra = example.createCriteria();
        List<AdminIncreaseInterestRepayCustomizeVO> reslutList = new ArrayList<AdminIncreaseInterestRepayCustomizeVO>();
        // 项目编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(form.getBorrowNidSrch());
        }
        // 项目状态
        if (StringUtils.isNotEmpty(form.getRepayStatusSrch())) {
            cra.andRepayStatusEqualTo(Integer.parseInt(form.getRepayStatusSrch()));
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            cra.andRepayTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayStart(form.getTimeStartSrch())));
            cra.andRepayTimeLessThanOrEqualTo( GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayEnd(form.getTimeEndSrch())));
        }
        if (form.getLimitStart() != -1) {
            example.setLimitStart(form.getLimitStart());
            example.setLimitEnd(form.getLimitEnd());
        }
        example.setOrderByClause(" repay_time ASC");
        List<IncreaseInterestRepay> list = increaseInterestRepayMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            reslutList = CommonUtils.convertBeanList(list,AdminIncreaseInterestRepayCustomizeVO.class);
        }
        return reslutList;
    }

    /**
     * 应还本金、应还加息收益合计
     * @param form
     * @return
     * @author PC-LIUSHOUYI
     */

    @Override
    public AdminIncreaseInterestRepayCustomizeVO sumBorrowRepaymentInfo(IncreaseInterestRepayDetailRequest form) {
        Map<String, Object> param = new HashMap<String, Object>();
        // 借款编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            param.put("borrowNidSrch", form.getBorrowNidSrch());
        }
        // 用户名
        if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
            param.put("userNameSrch", form.getUserNameSrch());
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            param.put("timeStartSrch", form.getTimeStartSrch());
            param.put("timeEndSrch", form.getTimeEndSrch());
        }
        //转账状态
        if (StringUtils.isNotEmpty(form.getRepayStatusSrch())) {
            param.put("repayStatus", form.getRepayStatusSrch());
        }
        return this.adminIncreaseInterestRepayCustomizeMapper.sumBorrowRepaymentInfo(param);

    }
}
