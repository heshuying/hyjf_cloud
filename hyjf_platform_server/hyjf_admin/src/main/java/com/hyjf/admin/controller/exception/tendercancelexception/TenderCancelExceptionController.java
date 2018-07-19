/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.tendercancelexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TenderCancelException, v0.1 2018/7/11 9:43
 */
@RestController
@RequestMapping("/hyjf-admin/tendercancelexception")
@Api(value = "异常中心-银行投资撤销异常",description = "异常中心-银行投资撤销异常")
public class TenderCancelExceptionController {

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
    public JSONObject searchList(@RequestBody TenderCancelExceptionRequest request){
        JSONObject jsonObject = new JSONObject();
        // 数据总数
        Integer count = tenderCancelExceptionService.getTenderCancelExceptionCount(request);
        jsonObject.put("count",count);
        // 异常列表list
        List<BorrowTenderTmpVO> borrowTenderTmpVOList = tenderCancelExceptionService.searchTenderCancelExceptionList(request);
        jsonObject.put("borrowTenderTmpVOList",borrowTenderTmpVOList);
        return jsonObject;
    }

    /**
     * 查询标的状态
     * @auth sunpeikai
     * @param request 参数
     * @return
     */
    @ApiOperation(value = "查询标的状态", notes = "查询标的状态")
    @PostMapping(value = "/queryborrowstatus")
    public JSONObject queryBorrowStatus(@RequestBody TenderCancelExceptionRequest request) {
        JSONObject ret = new JSONObject();
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            ret.put("status","error");
            ret.put("result", "参数错误，请稍后再试！");
            return ret;
        }
        String borrowNid = request.getBorrowNid();
        BorrowVO borrow = tenderCancelExceptionService.getBorrowByBorrowNid(borrowNid);
        Integer status = borrow.getStatus();
        if (status!=null && status == 2) {//投资中标的
            ret.put("status","success");
            ret.put("result","投资中标的,可撤销！");
            return ret;
        }else{
            ret.put("status","error");
            ret.put("result","该标的已经不是投资中的数据,请谨慎操作！是否继续撤销?");
            return ret;
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
    public JSONObject handleTenderCancelException(@RequestHeader(value = "userId")Integer loginUserId,TenderCancelExceptionRequest request){
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(request.getOrderId())) {
            jsonObject.put("status","error");
            jsonObject.put("result","参数错误，请稍后再试！");
            return jsonObject;
        }else{
            jsonObject = tenderCancelExceptionService.handleTenderCancelException(request,loginUserId);
        }
        return jsonObject;
    }
}
