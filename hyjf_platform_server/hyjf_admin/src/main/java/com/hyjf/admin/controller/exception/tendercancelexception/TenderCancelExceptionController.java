/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.tendercancelexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: TenderCancelException, v0.1 2018/7/11 9:43
 */
@RestController
@RequestMapping("/hyjf-admin/tendercancelexception")
@Api(value = "异常中心-银行投资撤销异常",description = "异常中心-银行投资撤销异常")
public class TenderCancelExceptionController extends BaseController {

    @Autowired
    private TenderCancelExceptionService tenderCancelExceptionService;


    /**
     * 查询银行投资撤销异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "查询银行投资撤销异常list", notes = "查询银行投资撤销异常list")
    @PostMapping(value = "/searchlist")
    public AdminResult searchList(@RequestBody TenderCancelExceptionRequest request){
        Map<String,Object> map = new HashMap<>();
        // 数据总数
        Integer count = tenderCancelExceptionService.getTenderCancelExceptionCount(request);
        map.put("count",count);
        // 异常列表list
        List<BorrowTenderTmpVO> borrowTenderTmpVOList = tenderCancelExceptionService.searchTenderCancelExceptionList(request);
        map.put("borrowTenderTmpVOList",borrowTenderTmpVOList);
        return new AdminResult(map);
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
        BorrowVO borrow = tenderCancelExceptionService.getBorrowByBorrowNid(borrowNid);
        Integer status = borrow.getStatus();
        if (status!=null && status == 2) {//投资中标的
            return new AdminResult(SUCCESS,"投资中标的,可撤销！");
        }else{
            return new AdminResult(FAIL,"该标的已经不是投资中的数据,请谨慎操作！是否继续撤销?");
        }
    }

    /**
     * 投资撤销异常处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "投资撤销异常处理", notes = "投资撤销异常处理")
    @PostMapping(value = "/handletendercancelexception")
    public AdminResult handleTenderCancelException(@RequestHeader(value = "userId")Integer loginUserId,TenderCancelExceptionRequest request){
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(request.getOrderId())) {
            return new AdminResult<>(FAIL,"参数错误，请稍后再试！");
        }else{
            jsonObject = tenderCancelExceptionService.handleTenderCancelException(request,loginUserId);
        }
        if("success".equals(jsonObject.getString("status"))){
            return new AdminResult(SUCCESS,jsonObject.getString("result"));
        }else{
            return new AdminResult(FAIL,jsonObject.getString("result"));
        }
    }
}
