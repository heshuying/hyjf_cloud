package com.hyjf.am.trade.controller.admin.productcenter.batchcenter.log;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BatchBorrowRecoverLogReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.log.BatchCenterBorrowRecoverLogService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverLogVo;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/getListCount")
    public IntegerResponse getListCount(@RequestBody BatchBorrowRecoverRequest request) {
        Integer count = batchBorrowRecoverService.getListTotal(request);
        return new IntegerResponse(count);
    }

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
    public BatchBorrowRecoverLogReponse getList(@RequestBody BatchBorrowRecoverRequest request){

        logger.info("还款请求记录列表查询请求参数，{}", JSON.toJSONString(request));
        BatchBorrowRecoverLogReponse reponse = new BatchBorrowRecoverLogReponse();
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

        List<BatchBorrowRecoverLogVo> list =  batchBorrowRecoverService.getList(request,limitStart,limitEnd);
        reponse.setRecordTotal(total);
        reponse.setResultList(list);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }


}
