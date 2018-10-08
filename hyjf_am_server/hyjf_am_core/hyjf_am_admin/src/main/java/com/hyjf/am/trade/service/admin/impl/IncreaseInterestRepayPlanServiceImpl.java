package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.trade.dao.mapper.auto.IncreaseInterestRepayDetailMapper;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayDetail;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayDetailExample;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayPlanService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayPlanServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class IncreaseInterestRepayPlanServiceImpl extends BaseServiceImpl implements IncreaseInterestRepayPlanService {

    @Autowired
    protected IncreaseInterestRepayDetailMapper increaseInterestRepayDetailMapper;

    /**
     * 融通宝加息还款计划信息检索件数
     *
     * @Title countRecordList
     * @param form
     * @return
     */
    @Override
    public int getIncreaseInterestRepayPlanCount(IncreaseInterestRepayPlanRequest form) {
        IncreaseInterestRepayDetailExample example = new IncreaseInterestRepayDetailExample();
        IncreaseInterestRepayDetailExample.Criteria cra = example.createCriteria();
        // 借款编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(form.getBorrowNidSrch());
        }
        // 还款状态
        if (StringUtils.isNotEmpty(form.getRepayStatusSrch())) {
            cra.andRepayStatusEqualTo(Integer.parseInt(form.getRepayStatusSrch()));
        }
        // 应还时间
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
            cra.andRepayTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayStart(form.getTimeStartSrch())));
            cra.andRepayTimeLessThanOrEqualTo( GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayEnd(form.getTimeEndSrch())));
        }
        return this.increaseInterestRepayDetailMapper.countByExample(example);
    }

    /**
     * 融通宝加息还款计划检索列表
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public List<IncreaseInterestRepayDetailVO> getIncreaseInterestRepayPlanList(IncreaseInterestRepayPlanRequest form) {
        IncreaseInterestRepayDetailExample example = new IncreaseInterestRepayDetailExample();
        IncreaseInterestRepayDetailExample.Criteria cra = example.createCriteria();
        List<IncreaseInterestRepayDetailVO> reslutList = new ArrayList<IncreaseInterestRepayDetailVO>();
        // 借款编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(form.getBorrowNidSrch());
        }
        // 还款状态
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
        example.setOrderByClause(" create_time desc");
        List<IncreaseInterestRepayDetail> list = increaseInterestRepayDetailMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            reslutList = CommonUtils.convertBeanList(list,IncreaseInterestRepayDetailVO.class);
        }
        return reslutList;
    }
}
