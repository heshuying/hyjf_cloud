package com.hyjf.am.trade.controller.admin.productcenter.batchcenter.log;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.log.BatchCenterBorrowRecoverLogService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台批次还款记录
 * @Auther:hesy
 */
@Api(value = "批次中心-批次还款记录")
@RestController
@RequestMapping("/am-admin/adminBatchBorrowRecoverLog")
public class AdminBatchBorrowRecoverLogController extends BaseController {

    @Autowired
    private BatchCenterBorrowRecoverLogService batchBorrowRecoverService;

    /**
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "放款列表查询总件数")
    @PostMapping("/getListTotal")
    public Integer getListTotal(@RequestBody BatchBorrowRecoverRequest request) {
        Integer count = batchBorrowRecoverService.getListTotal(request);
        return count;
    }

    @ApiOperation(value = "放款列表查询")
    @PostMapping("/getList")
    public BatchBorrowRecoverReponse getList(@RequestBody BatchBorrowRecoverRequest request){

        logger.info("BatchBorrowRecoverRequest:::::::[{}]", JSON.toJSONString(request));
        BatchBorrowRecoverReponse reponse = new BatchBorrowRecoverReponse();
        Integer total = getListTotal(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getLimitStart() != null && request.getLimitStart() == -1){
            limitStart = -1;
            limitEnd = -1;
        }

        List<BatchBorrowRecoverVo> list =  batchBorrowRecoverService.getList(request,limitStart,limitEnd);
        reponse.setRecordTotal(total);
        reponse.setResultList(list);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }


}
