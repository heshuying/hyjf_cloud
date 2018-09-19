package com.hyjf.am.trade.controller.admin.productcenter.batchcenter.borrowrecover;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.paginator.Paginator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 后台列表-批次放款
 */
@Api(value = "批次中心-批次放款")
@RestController
@RequestMapping("/am-admin/adminBatchBorrowRecover")
public class AdminBatchBorrowRecoverController extends BaseController {

    @Autowired
    private BatchCenterBorrowRecoverService batchBorrowRecoverService;

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

    @ApiOperation(value = "获取列表求和用于显示")
    @PostMapping("/getListSum")
    public BatchBorrowRecoverReponse getListSum(@RequestBody BatchBorrowRecoverRequest request){

        BatchBorrowRecoverReponse reponse = new BatchBorrowRecoverReponse();
        BatchBorrowRecoverVo result =  batchBorrowRecoverService.getListSum(request);
        logger.info("====================开始调用列表求和------");
        reponse.setResult(result);
        return reponse;
    }

    @ApiOperation(value = "根据id获取放款任务")
    @GetMapping("/getRecoverApicronByID/{id}")
    public BorrowApicronResponse getRecoverApicronByID(@PathVariable String id){

        BorrowApicronResponse reponse = new BorrowApicronResponse();
        BorrowApicron apicron = batchBorrowRecoverService.getRecoverApicronByID(id);
        BorrowApicronVO result = CommonUtils.convertBean(apicron, BorrowApicronVO.class);
        reponse.setResult(result);
        reponse.setRtn(Response.SUCCESS);
        return reponse;
    }

    @ApiOperation(value = "根据id更新借款API表")
    @PostMapping("/updateBorrowApicronByPrimaryKeySelective")
    public boolean updateBorrowApicronByPrimaryKeySelective(@RequestBody String id){
        return batchBorrowRecoverService.updateBorrowApicronByPrimaryKeySelective(id);
    }

}
