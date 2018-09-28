/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.trade.dao.mapper.auto.DirectionalTransferAssociatedLogMapper;
import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedLog;
import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedLogExample;
import com.hyjf.am.trade.service.admin.finance.AssociatedLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BindLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedLogServiceImpl, v0.1 2018/7/5 15:45
 */
@Service
public class AssociatedLogServiceImpl extends BaseServiceImpl implements AssociatedLogService {

    @Autowired
    private DirectionalTransferAssociatedLogMapper directionalTransferAssociatedLogMapper;

    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer getBindLogCount(BindLogListRequest request) {
        DirectionalTransferAssociatedLogExample example = convertExample(request);
        return directionalTransferAssociatedLogMapper.countByExample(example);
    }

    /**
     * 根据筛选条件查询绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<BindLogVO> searchBindLogList(BindLogListRequest request) {
        DirectionalTransferAssociatedLogExample example = convertExample(request);
        List<DirectionalTransferAssociatedLog> directionalTransferAssociatedLogList = directionalTransferAssociatedLogMapper.selectByExample(example);
        List<BindLogVO> bindLogVOList = CommonUtils.convertBeanList(directionalTransferAssociatedLogList,BindLogVO.class);
        return bindLogVOList;
    }

    /**
     * BindLogListRequest转example参数
     * @auth sunpeikai
     * @param
     * @return
     */
    private DirectionalTransferAssociatedLogExample convertExample(BindLogListRequest request){
        DirectionalTransferAssociatedLogExample example = new DirectionalTransferAssociatedLogExample();
        DirectionalTransferAssociatedLogExample.Criteria criteria = example.createCriteria();
        // 转出账户
        if (StringUtils.isNotEmpty(request.getTurnOutUsername())) {
            criteria.andTurnOutUsernameLike("%" + request.getTurnOutUsername() + "%");
        }
        // 转出账户手机
        if (StringUtils.isNotEmpty(request.getTurnOutMobile())) {
            criteria.andTurnOutMobileLike("%" + request.getTurnOutMobile() + "%");
        }
        // 关联状态
        if (StringUtils.isNotEmpty(request.getStatusSearch())) {
            criteria.andAssociatedStateEqualTo(Integer.parseInt(request.getStatusSearch()));
        }
        // 转入账户
        if (StringUtils.isNotEmpty(request.getShiftToUsername())) {
            criteria.andShiftToUsernameLike("%" + request.getShiftToUsername() + "%");
        }
        // 转入账户手机
        if (StringUtils.isNotEmpty(request.getShiftToMobile())) {
            criteria.andShiftToMobileLike("%" + request.getShiftToMobile() + "%");
        }
        // 关联时间开始
        if (StringUtils.isNotEmpty(request.getStartDate())) {
            criteria.andAssociatedTimeGreaterThanOrEqualTo(GetDate.stringToDate(request.getStartDate() + " 00:00:00"));
        }
        // 关联时间结束
        if (StringUtils.isNotEmpty(request.getEndDate())) {
            criteria.andAssociatedTimeLessThanOrEqualTo(GetDate.stringToDate(request.getEndDate() + " 23:59:59"));
        }
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return example;
    }
}
