/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.trade.dao.mapper.auto.DirectionalTransferAssociatedRecordsMapper;
import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedRecords;
import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedRecordsExample;
import com.hyjf.am.trade.service.admin.finance.AssociatedRecordsService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordsServiceImpl, v0.1 2018/7/5 14:50
 */
@Service
public class AssociatedRecordsServiceImpl extends BaseServiceImpl implements AssociatedRecordsService {

    @Autowired
    private DirectionalTransferAssociatedRecordsMapper directionalTransferAssociatedRecordsMapper;

    /**
     * 根据筛选条件查询关联记录count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getAssociatedRecordsCount(AssociatedRecordListRequest request) {
        DirectionalTransferAssociatedRecordsExample example = convertExample(request);
        return directionalTransferAssociatedRecordsMapper.countByExample(example);
    }

    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<AssociatedRecordListVo> searchAssociatedRecordList(AssociatedRecordListRequest request) {
        DirectionalTransferAssociatedRecordsExample example = convertExample(request);
        List<DirectionalTransferAssociatedRecords> directionalTransferAssociatedRecordsList = directionalTransferAssociatedRecordsMapper.selectByExample(example);
        List<AssociatedRecordListVo> associatedRecordListVoList = CommonUtils.convertBeanList(directionalTransferAssociatedRecordsList,AssociatedRecordListVo.class);
        return associatedRecordListVoList;
    }

    /**
     * 筛选条件转example
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    private DirectionalTransferAssociatedRecordsExample convertExample(AssociatedRecordListRequest request){
        DirectionalTransferAssociatedRecordsExample example = new DirectionalTransferAssociatedRecordsExample();
        DirectionalTransferAssociatedRecordsExample.Criteria criteria = example.createCriteria();
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
