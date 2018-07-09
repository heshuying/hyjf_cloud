package com.hyjf.admin.controller.productcenter.borrow.batchcenter.batchborrowrecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 批次中心-批次放款
 */
@Api(value = "批次中心-批次放款")
@RestController
@RequestMapping("/hyjf-admin/batchBorrowRecover")
public class BatchBorrowRecoverController extends BaseController{

    @Autowired
    private BatchBorrowRecoverService batchBorrowRecoverService;

    @ApiOperation(value = "批次中心-批次放款页面初始化", notes = "页面初始化")
    @PostMapping(value = "/batchBorrowRecoverInit")
    @ResponseBody
    public JSONObject batchBorrowRecoverInit() {
        JSONObject jsonObject = null;
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次放款页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/querybatchBorrowRecoverList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject querybatchBorrowRecoverList(@RequestBody BatchBorrowRecoverRequest request) {
        JSONObject jsonObject = null;
        BatchBorrowRecoverReponse BatchBorrowRecovertReponse = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);
        List<BatchBorrowRecoverVo> batchBorrowRecoverVoList = new ArrayList();
        if (null != BatchBorrowRecovertReponse) {
            List<BatchBorrowRecoverVo> listAccountDetail = BatchBorrowRecovertReponse.getResultList();
            Integer recordCount = BatchBorrowRecovertReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                batchBorrowRecoverVoList.addAll(listAccountDetail);
            }
            if (null != batchBorrowRecoverVoList) {
                jsonObject = this.success(String.valueOf(recordCount), batchBorrowRecoverVoList);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
        }
        return jsonObject;
    }


}
