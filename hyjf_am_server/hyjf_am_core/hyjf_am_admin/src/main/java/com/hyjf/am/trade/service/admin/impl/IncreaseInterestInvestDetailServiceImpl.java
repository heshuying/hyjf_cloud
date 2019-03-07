package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;
import com.hyjf.am.trade.dao.mapper.auto.IncreaseInterestInvestMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.IncreaseInterestInvestCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvestExample;
import com.hyjf.am.trade.service.admin.IncreaseInterestInvestDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.IncreaseInterestInvestVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestInvestDetailServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class IncreaseInterestInvestDetailServiceImpl extends BaseServiceImpl implements IncreaseInterestInvestDetailService {

    @Autowired
    protected IncreaseInterestInvestMapper increaseInterestInvestMapper;

    @Autowired
    protected IncreaseInterestInvestCustomizeMapper increaseInterestInvestCustomizeMapper;

    @Override
    public int getIncreaseInterestInvestDetaiCount(IncreaseInterestInvestDetailRequest request) {
        IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
        IncreaseInterestInvestExample.Criteria cra = example.createCriteria();
        // 用户名
        if (StringUtils.isNotEmpty(request.getUserNameSrch())) {
            cra.andInvestUserNameLike("%" + request.getUserNameSrch() + "%");
        }
        // 项目编号
        if (StringUtils.isNotEmpty(request.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(request.getBorrowNidSrch());
        }
        // 出借时间
        //if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {
        //    cra.andCreateTimeBetween(GetDate.str2Timestamp(request.getTimeStartSrch()), GetDate.str2Timestamp(request.getTimeEndSrch()));
        //}

        // 出借时间结束时间加一秒
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())&&StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date timeStart = null;
            Date timeEnd = null;
            try {
                timeStart = (Date)formatter.parseObject(request.getTimeStartSrch() + " 00:00:00");
                timeEnd = StringUtil.delOneSecond((Date)formatter.parseObject(request.getTimeEndSrch() + " 00:00:00"));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
            //cra.andCreateTimeBetween(timeStart,timeEnd);
            cra.andCreateTimeLessThanOrEqualTo(timeEnd);
            cra.andCreateTimeGreaterThanOrEqualTo(timeStart);
        }

        return increaseInterestInvestMapper.countByExample(example);
    }

    @Override
    public List<IncreaseInterestInvestVO> getIncreaseInterestInvestDetaiList(IncreaseInterestInvestDetailRequest request) {
        IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
        IncreaseInterestInvestExample.Criteria cra = example.createCriteria();
        List<IncreaseInterestInvestVO> reslutList = new ArrayList<IncreaseInterestInvestVO>();
        // 用户名
        if (StringUtils.isNotEmpty(request.getUserNameSrch())) {
            cra.andInvestUserNameLike("%" + request.getUserNameSrch() + "%");
        }
        // 项目编号
        if (StringUtils.isNotEmpty(request.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(request.getBorrowNidSrch());
        }
        // 出借时间
        //if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {
        //    cra.andCreateTimeBetween(GetDate.str2Timestamp(request.getTimeStartSrch()), GetDate.str2Timestamp(request.getTimeEndSrch()));
        //}
        // 出借时间结束时间加一秒
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())&&StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date timeStart = null;
            Date timeEnd = null;
            try {
                timeStart = (Date)formatter.parseObject(request.getTimeStartSrch() + " 00:00:00");
                timeEnd = StringUtil.delOneSecond((Date)formatter.parseObject(request.getTimeEndSrch() + " 00:00:00"));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
            //cra.andCreateTimeBetween(timeStart,timeEnd);
            cra.andCreateTimeLessThanOrEqualTo(timeEnd);
            cra.andCreateTimeGreaterThanOrEqualTo(timeStart);
        }

        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        example.setOrderByClause(" create_time desc");
        List<IncreaseInterestInvest> list = increaseInterestInvestMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            reslutList = CommonUtils.convertBeanList(list,IncreaseInterestInvestVO.class);
        }
        return reslutList;
    }

    /**
     * 执行前每个方法前需要添加BusinessDesc描述
     * @param form
     * @return
     * @author PC-LIUSHOUYI
     */

    @Override
    public String sumAccount(IncreaseInterestInvestDetailRequest form) {

        IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
        IncreaseInterestInvestExample.Criteria cra = example.createCriteria();
        // 用户名
        if (StringUtils.isNotEmpty(form.getUserNameSrch())) {
            cra.andInvestUserNameLike("%" + form.getUserNameSrch() + "%");
        }
        // 项目编号
        if (StringUtils.isNotEmpty(form.getBorrowNidSrch())) {
            cra.andBorrowNidEqualTo(form.getBorrowNidSrch());
        }
        // 出借时间
        //if (StringUtils.isNotEmpty(form.getTimeStartSrch())) {
        //    cra.andCreateTimeBetween(GetDate.str2Timestamp(form.getTimeStartSrch()), GetDate.str2Timestamp(form.getTimeEndSrch()));
        //}

        // 出借时间结束时间加一秒
        if (StringUtils.isNotEmpty(form.getTimeStartSrch())&&StringUtils.isNotEmpty(form.getTimeEndSrch())) {
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date timeStart = null;
            Date timeEnd = null;
            try {
                timeStart = (Date)formatter.parseObject(form.getTimeStartSrch() + " 00:00:00");
                timeEnd = StringUtil.delOneSecond((Date)formatter.parseObject(form.getTimeEndSrch() + " 00:00:00"));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
            //cra.andCreateTimeBetween(timeStart,timeEnd);
            cra.andCreateTimeLessThanOrEqualTo(timeEnd);
            cra.andCreateTimeGreaterThanOrEqualTo(timeStart);
        }
        return this.increaseInterestInvestCustomizeMapper.sumAccount(example);
    }
}
