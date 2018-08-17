package com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.impl;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.TenderCommissionRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.TenderCommission;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchCenterBorrowRecoverServiceImpl extends BaseServiceImpl implements BatchCenterBorrowRecoverService{

    /**
     * 获取列表数量
     * @param request
     * @return
     */
    @Override
    public Integer getListTotal(BatchBorrowRecoverRequest request) {

        Integer count = this.batchCenterCustomizeMapper.countBorrowBatchCenterListTotal(request);
        return count;
    }

    /**
     * 获取列表
     * @param request
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BatchBorrowRecoverVo> getList(BatchBorrowRecoverRequest request, int limitStart, int limitEnd) {

        request.setLimitStart(limitStart);
        request.setLimitEnd(limitEnd);
        List<BatchBorrowRecoverVo> recoverVoList = this.batchCenterCustomizeMapper.queryBatchCenterList(request);
        return recoverVoList;
    }

    /**
     * 获取批次中心列表求和
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverVo getListSum(BatchBorrowRecoverRequest request) {
        this.batchCenterCustomizeMapper.sumBatchCenter(request);
        return null;
    }

    /**
     * 根据ID获取批次任务数据
     * @param id
     * @return
     */
    @Override
    public BorrowApicron getRecoverApicronByID(String id) {

        BorrowApicron borrowApicron = this.borrowApicronMapper.selectByPrimaryKey(Integer.valueOf(id));
        return borrowApicron;
    }

    /**
     * 发提成处理- 计算提成,更新借款API表
     * @auth Zha Daojian
     * @param
     * @return
     */
    @Override
    public boolean updateBorrowApicronByPrimaryKeySelective(String apicornId) {
        int id = StringUtils.isEmpty(apicornId)?0:Integer.valueOf(apicornId);
        BorrowApicron borrowApicron = new BorrowApicron();
        borrowApicron.setId(id);
        borrowApicron.setWebStatus(1);
        return  borrowApicronMapper.updateByPrimaryKeySelective(borrowApicron)>0;
    }
}
