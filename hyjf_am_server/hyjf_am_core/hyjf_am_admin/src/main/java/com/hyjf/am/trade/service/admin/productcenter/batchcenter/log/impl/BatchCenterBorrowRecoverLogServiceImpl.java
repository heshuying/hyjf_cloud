package com.hyjf.am.trade.service.admin.productcenter.batchcenter.log.impl;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.log.BatchCenterBorrowRecoverLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BatchBorrowRecoverLogVo;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchCenterBorrowRecoverLogServiceImpl extends BaseServiceImpl implements BatchCenterBorrowRecoverLogService {

    /**
     * 获取列表数量
     * @param request
     * @return
     */
    @Override
    public Integer getListTotal(BatchBorrowRecoverRequest request) {

        Integer count = this.batchCenterLogCustomizeMapper.countBorrowBatchCenterListTotal(request);
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
    public List<BatchBorrowRecoverLogVo> getList(BatchBorrowRecoverRequest request, int limitStart, int limitEnd) {

        request.setLimitStart(limitStart);
        request.setLimitEnd(limitEnd);
        List<BatchBorrowRecoverLogVo> recoverVoList = this.batchCenterLogCustomizeMapper.queryBatchCenterList(request);
        return recoverVoList;
    }

}
