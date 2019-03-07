/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.exception.TenderCancelRepairService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.FreezeHistoryVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TenderCancelRepairServiceImpl, v0.1 2018/7/11 10:42
 */
@Service(value = "tradeTenderCancelRepairServiceImpl")
public class TenderCancelRepairServiceImpl extends BaseServiceImpl implements TenderCancelRepairService {

    /**
     * 根据筛选条件查询银行出借撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request) {
        BorrowTenderTmpExample example = convertExample(request);
        return borrowTenderTmpMapper.countByExample(example);
    }

    /**
     * 查询银行出借撤销异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<BorrowTenderTmp> searchTenderCancelExceptionList(TenderCancelExceptionRequest request) {
        BorrowTenderTmpExample example = convertExample(request);
        return borrowTenderTmpMapper.selectByExample(example);
    }

    /**
     * 根据orderId查询BorrowTender
     * @auth sunpeikai
     * @param orderId 订单号
     * @return List<BorrowTender>
     */
    @Override
    public List<BorrowTender> searchBorrowTenderByOrderId(String orderId) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        criteria.andNidEqualTo(orderId);
        return borrowTenderMapper.selectByExample(example);
    }

    /**
     * 根据borrowNid查询BorrowTender
     * @auth zdj
     * @param borrowNid 订单号
     * @return List<BorrowTender> searchBorrowTenderByBorrowNid(String borrowNid);
     */
    @Override
    public List<BorrowTender> searchBorrowTenderByBorrowNid(String borrowNid) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        return borrowTenderMapper.selectByExample(example);
    }

    /**
     * 根据orderId查询BorrowTenderTmp
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @Override
    public List<BorrowTenderTmp> searchBorrowTenderTmpByOrderId(String orderId) {
        BorrowTenderTmpExample example = new BorrowTenderTmpExample();
        BorrowTenderTmpExample.Criteria criteria = example.createCriteria();
        criteria.andNidEqualTo(orderId);
        return borrowTenderTmpMapper.selectByExample(example);
    }

    /**
     * 根据id删除BorrowTenderTmp
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBorrowTenderTmpById(Integer id) {
        return borrowTenderTmpMapper.deleteByPrimaryKey(id);
    }

    /**
     * 插入数据
     * @auth sunpeikai
     * @param freezeHistoryVO 冻结历史
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertFreezeHistory(FreezeHistoryVO freezeHistoryVO) {
        FreezeHistory freezeHistory = CommonUtils.convertBean(freezeHistoryVO,FreezeHistory.class);
        return freezeHistoryMapper.insertSelective(freezeHistory);
    }

    private BorrowTenderTmpExample convertExample(TenderCancelExceptionRequest request){
        BorrowTenderTmpExample example = new BorrowTenderTmpExample();
        BorrowTenderTmpExample.Criteria criteria = example.createCriteria();
        // 5分钟之前
        criteria.andCreateTimeLessThan(GetDate.getMinutesAfter(GetDate.getNowTime(),-5));

        // 用户名
        if(StringUtils.isNotEmpty(request.getUserName())){
            criteria.andUserNameLike("%"+request.getUserName()+"%");
        }
        criteria.andIsBankTenderEqualTo(1);
        // 标的号
        if (StringUtils.isNotBlank(request.getBorrowNid())) {
            criteria.andBorrowNidLike("%"+request.getBorrowNid()+"%");
        }
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        example.setOrderByClause("id");
        return example;
    }
}
