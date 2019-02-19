package com.hyjf.am.trade.service.api.borrowRecover.impl;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.api.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *api端-第三方-批次放款
 * @author Zha Daojian
 * @date 2019/2/14 9:15
 * @param 
 * @return 
 **/
@Service
public class BatchCenterBorrowRecoverServiceImpl extends BaseServiceImpl implements BatchCenterBorrowRecoverService {

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
        BatchBorrowRecoverVo vo = this.batchCenterCustomizeMapper.sumBatchCenter(request);
        return vo;
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
