package com.hyjf.admin.controller.productcenter.borrow.batchcenter.batchrepaylog;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BatchBorrowRecoverLogService;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BatchBorrowRepayBankInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Auther:yangchangwei
 * @Date:2018/7/12
 * @Description:
 */
@Api(value = "产品中心-批次中心-批次还款记录",tags ="产品中心-批次中心-批次还款记录")
@RestController
@RequestMapping("/hyjf-admin/batchBorrowRepayLog")
public class BatchBorrowRepayLogController extends BaseController{

    private static final String NAME_CLASS = "REPAY_STATUS";

    private static final String PERMISSIONS = "batchborrowrepay";

    @Autowired
    private BatchBorrowRecoverLogService batchBorrowRecoverService;

    @ApiOperation(value = "批次中心-批次还款页面初始化", notes = "页面初始化")
    @PostMapping(value = "/batchBorrowRepayInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public JSONObject batchBorrowRepayInit() {
        JSONObject jsonObject = batchBorrowRecoverService.initPage(NAME_CLASS);
        BatchBorrowRecoverRequest request = new BatchBorrowRecoverRequest();
        JSONObject borrowRepayList = querybatchBorrowRepayList(request);
        if(borrowRepayList != null){
            List<BatchBorrowRecoverVo> listAccountDetail = (List<BatchBorrowRecoverVo>) borrowRepayList.get(LIST);
            if(listAccountDetail != null && listAccountDetail.size() > 0){
                jsonObject.put("批次还款列表","listAccountDetail");
                jsonObject.put("listAccountDetail",listAccountDetail);
                jsonObject.put("hjhDebtCreditVoListTotal",borrowRepayList.get(TRCORD));
            }
        }
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次还款页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/querybatchBorrowRepayList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public JSONObject querybatchBorrowRepayList(@RequestBody BatchBorrowRecoverRequest request) {
        request.setApiType(1);
        request.setNameClass(NAME_CLASS);
        JSONObject jsonObject = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);
        return jsonObject;
    }

}
