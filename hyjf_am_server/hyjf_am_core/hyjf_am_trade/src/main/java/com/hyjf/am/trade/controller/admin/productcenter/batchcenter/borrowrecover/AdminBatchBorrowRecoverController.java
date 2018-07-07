package com.hyjf.am.trade.controller.admin.productcenter.batchcenter.borrowrecover;

import com.hyjf.am.common.ConvertUtils;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.admin.AdminHjhDebtCreditCustomize;
import com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover.BatchCenterBorrowRecoverService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 后台列表-批次放款
 */
@Api(value = "批次中心-批次放款")
@RestController
@RequestMapping("/am-trade/adminBatchBorrowRecover")
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
    public Integer getListTotal(BatchBorrowRecoverRequest request) {
        Integer count = batchBorrowRecoverService.getListTotal(request);
        return count;
    }

    @ApiOperation(value = "放款列表查询")
    @PostMapping("/getList")
    public BatchBorrowRecoverReponse getList(BatchBorrowRecoverRequest request){

        BatchBorrowRecoverReponse reponse = new BatchBorrowRecoverReponse();
        Integer total = getListTotal(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        List<BatchBorrowRecoverVo> list =  batchBorrowRecoverService.getList(request,limitStart,limitEnd);
        //TODO 获取列表后循环列表设置状态显示描述
        reponse.setRecordTotal(total);
        reponse.setResultList(list);
        return reponse;
    }

    //TODO 获取列表求和用于显示

}
