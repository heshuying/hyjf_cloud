package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;
import com.hyjf.am.trade.dao.mapper.auto.IncreaseInterestRepayMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.IncreaseInterestRepayCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepay;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayExample;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;
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
 * @version IncreaseInterestRepayServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class IncreaseInterestRepayServiceImpl extends BaseServiceImpl implements IncreaseInterestRepayService {

    @Autowired
    protected IncreaseInterestRepayMapper increaseInterestRepayMapper;

    @Autowired
    protected IncreaseInterestRepayCustomizeMapper increaseInterestRepayCustomizeMapper;
    /**
     * 融通宝加息还款信息检索件数
     *
     * @Title countRecordList
     * @param form
     * @return
     */
    @Override
    public int getIncreaseInterestRepayCount(IncreaseInterestRepayRequest form) {
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
    public List<IncreaseInterestRepayVO> getIncreaseInterestRepayList(IncreaseInterestRepayRequest form) {
        IncreaseInterestRepayExample example = new IncreaseInterestRepayExample();
        IncreaseInterestRepayExample.Criteria cra = example.createCriteria();
        List<IncreaseInterestRepayVO> reslutList = new ArrayList<IncreaseInterestRepayVO>();
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
            reslutList = CommonUtils.convertBeanList(list,IncreaseInterestRepayVO.class);
        }
        return reslutList;
    }

    /**
     * 应还加息收益合计取得
     * @Title sumAccount
     * @param form
     * @return
     * @author PC-LIUSHOUYI
     */

    @Override
    public String sumAccount(IncreaseInterestRepayRequest form) {
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
        return this.increaseInterestRepayCustomizeMapper.sumAccount(example);
    }
}
