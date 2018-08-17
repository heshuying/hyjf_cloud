/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.borrowregistexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BorrowRegistExceptionService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionController, v0.1 2018/7/3 10:55
 * 银行标的备案掉单
 */
@RestController
@RequestMapping("/hyjf-admin/exception/borrow_regist_exception")
@Api(value = "异常中心-标的备案掉单",tags = "异常中心-标的备案掉单")
public class BorrowRegistExceptionController extends BaseController {

    @Autowired
    private BorrowRegistExceptionService borrowRegistExceptionService;
    /**
     * 查询银行标的备案异常list
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常list查询")
    @PostMapping(value = "/searchlist")
    public AdminResult<ListResult<BorrowRegistCustomizeVO>> searchList(@RequestBody BorrowRegistListRequest request){
        Map<String,Object> map = new HashMap<>();
        // 数据总数
        Integer count = borrowRegistExceptionService.getRegistCount(request);
        // 异常列表list
        List<BorrowRegistCustomizeVO> borrowRegistCustomizeVOList = borrowRegistExceptionService.selectBorrowRegistList(request);
        return new AdminResult<>(ListResult.build(borrowRegistCustomizeVOList,count));
    }

    /**
     * 共通筛选条件下拉列表-项目类型
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "共通筛选条件下拉列表-项目类型",notes = "共通筛选条件下拉列表-项目类型")
    @GetMapping(value = "/searchprojecttypelist")
    public AdminResult<ListResult<BorrowProjectTypeVO>> searchProjectTypeList(){
        // 筛选条件 项目类型list
        List<BorrowProjectTypeVO> borrowProjectTypeVOList = borrowRegistExceptionService.selectBorrowProjectList();
        return new AdminResult<>(ListResult.build(borrowProjectTypeVOList,borrowProjectTypeVOList.size()));
    }

    /**
     * 共通筛选条件下拉列表-还款方式
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "共通筛选条件下拉列表-还款方式",notes = "共通筛选条件下拉列表-还款方式")
    @GetMapping(value = "/searchborrowstylelist")
    public AdminResult<ListResult<BorrowStyleVO>> searchBorrowStyleList(){
        // 筛选条件 还款方式 list
        List<BorrowStyleVO> borrowStyleVOList = borrowRegistExceptionService.selectBorrowStyleList();
        return new AdminResult<>(ListResult.build(borrowStyleVOList,borrowStyleVOList.size()));
    }
    /**
     * 标的备案异常处理
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @param userId admin项目-当前登录用户id
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常处理")
    @PostMapping(value = "/borrowregisthandleexception")
    public AdminResult borrowRegistHandleException(@RequestHeader(value = "userId")Integer userId, @RequestBody BorrowRegistListRequest request){
        JSONObject jsonObject = new JSONObject();
        String borrowNid = request.getBorrowNidSrch();
        logger.info("borrowRegistHandleException::::::::::userId=[{}],borrowNid=[{}]",userId,borrowNid);
        if(StringUtils.isBlank(borrowNid)){
            return new AdminResult(FAIL,"项目编号为空");
        }else{
            jsonObject = borrowRegistExceptionService.handleBorrowRegistException(borrowNid,userId);
        }
        if("0".equals(jsonObject.getString("success"))){
            return new AdminResult(SUCCESS,jsonObject.getString("msg"));
        }else{
            return new AdminResult(FAIL,jsonObject.getString("msg"));
        }
    }
}
