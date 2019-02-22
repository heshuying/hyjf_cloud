/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.tendercancelexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSIONS_BIDCANCEL;
import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

/**
 * @author: sunpeikai
 * @version: TenderCancelException, v0.1 2018/7/11 9:43
 */
@RestController
@RequestMapping("/hyjf-admin/exception/tendercancelexception")
@Api(value = "异常中心-银行出借撤销异常",tags = "异常中心-银行出借撤销异常")
public class TenderCancelRepairController extends BaseController {

    @Autowired
    private TenderCancelExceptionService tenderCancelExceptionService;
    private static final String PERMISSIONS = "bidcancelexception";

    /**
     * 查询银行出借撤销异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "查询银行出借撤销异常list", notes = "查询银行出借撤销异常list")
    @PostMapping(value = "/searchlist")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<BorrowTenderTmpVO>> searchList(@RequestBody TenderCancelExceptionRequest request){
        // 数据总数
        Integer count = tenderCancelExceptionService.getTenderCancelExceptionCount(request);
        // 异常列表list
        List<BorrowTenderTmpVO> borrowTenderTmpVOList = tenderCancelExceptionService.searchTenderCancelExceptionList(request);
        return new AdminResult<>(ListResult.build(borrowTenderTmpVOList,count));
    }

    /**
     * 查询标的状态
     * @auth sunpeikai
     * @param request 参数
     * @return
     */
    @ApiOperation(value = "查询标的状态", notes = "查询标的状态")
    @PostMapping(value = "/queryborrowstatus")
    public AdminResult queryBorrowStatus(@RequestBody TenderCancelExceptionRequest request) {
        Map<String,Object> ret = new HashMap<>();
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            return new AdminResult(FAIL,"参数错误，请稍后再试");
        }
        String borrowNid = request.getBorrowNid();
        BorrowAndInfoVO borrow = tenderCancelExceptionService.getBorrowByBorrowNid(borrowNid);
        Integer status = borrow.getStatus();
        if (status!=null && status == 2) {//出借中标的
            return new AdminResult(SUCCESS,"出借中标的,可撤销！");
        }else{
            return new AdminResult(FAIL,"该标的已经不是出借中的数据,请谨慎操作！是否继续撤销?");
        }
    }

    /**
     * 出借撤销异常处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "出借撤销异常处理", notes = "出借撤销异常处理")
    @PostMapping(value = "/handletendercancelexception")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSIONS_BIDCANCEL)
    public AdminResult handleTenderCancelException(HttpServletRequest request, @RequestBody TenderCancelExceptionRequest tenderCancelExceptionRequest){
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(tenderCancelExceptionRequest.getOrderId())) {
            return new AdminResult<>(FAIL,"参数错误，请稍后再试！");
        }else{
            jsonObject = tenderCancelExceptionService.handleTenderCancelException(tenderCancelExceptionRequest,loginUserId);
        }
        if("success".equals(jsonObject.getString("status"))){
            return new AdminResult(SUCCESS,jsonObject.getString("result"));
        }else{
            return new AdminResult(FAIL,jsonObject.getString("result"));
        }
    }
}
