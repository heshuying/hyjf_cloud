/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.exception.BorrowRegistRepairService;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistRepairController, v0.1 2018/7/3 15:05
 * 异常中心-标的备案掉单
 */
@Api(value = "异常中心-标的备案掉单",tags ="异常中心-标的备案掉单")
@RestController(value = "tradeBorrowRegistRepairController")
@RequestMapping("/am-trade/borrow_regist_repair")
public class BorrowRegistRepairController extends BaseController {

    @Autowired
    private BorrowRegistRepairService borrowRegistRepairService;

    /**
     * 获取项目类型,筛选条件展示
     * @auth sunpeikai
     * @param
     * @return 项目类型list封装
     */
    @ApiOperation(value = "获取项目类型", notes = "获取项目类型,筛选条件展示")
    @RequestMapping("/select_borrow_project")
    public BorrowProjectTypeResponse selectBorrowProjectList() {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        List<BorrowProjectType> borrowProjectTypeList = borrowRegistRepairService.selectBorrowProjectList();
        if (!CollectionUtils.isEmpty(borrowProjectTypeList)) {
            List<BorrowProjectTypeVO> voList = CommonUtils.convertBeanList(borrowProjectTypeList, BorrowProjectTypeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取还款方式,筛选条件展示
     * @auth sunpeikai
     * @param
     * @return 还款方式list封装
     */
    @ApiOperation(value = "获取还款方式", notes = "获取还款方式,筛选条件展示")
    @RequestMapping("/select_borrow_style")
    public BorrowStyleResponse selectBorrowStyleList() {
        BorrowStyleResponse response = new BorrowStyleResponse();
        List<BorrowStyle> borrowStyleList = borrowRegistRepairService.selectBorrowStyleList();
        if (!CollectionUtils.isEmpty(borrowStyleList)) {
            List<BorrowStyleVO> voList = CommonUtils.convertBeanList(borrowStyleList, BorrowStyleVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取标的列表count,用于前端分页展示
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    @ApiOperation(value = "获取标的列表count", notes = "获取标的列表count,用于前端分页展示")
    @RequestMapping("/get_regist_count")
    public Integer getRegistCount(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistRepairService.getRegistCount(borrowRegistListRequest);
    }

    /**
     * 获取标的列表
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return 异常标list封装
     */
    @ApiOperation(value = "获取标的列表", notes = "获取标的列表")
    @RequestMapping("/select_borrow_regist_list")
    public BorrowRegistCustomizeResponse selectBorrowRegistList(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        Integer recordTotal = borrowRegistRepairService.getRegistCount(borrowRegistListRequest);
        logger.info("selectBorrowRegistList::::::::::recordTotal=[{}]",recordTotal);
        Paginator paginator = new Paginator(borrowRegistListRequest.getCurrPage(), recordTotal,borrowRegistListRequest.getPageSize());
        borrowRegistListRequest.setLimitStart(paginator.getOffset());
        borrowRegistListRequest.setLimitEnd(paginator.getLimit());
        logger.info("selectBorrowRegistList::::::::::limitStart=[{}],limitEnd=[{}]",borrowRegistListRequest.getLimitStart(),borrowRegistListRequest.getLimitEnd());

        List<BorrowRegistCustomize> borrowRegistCustomizeList = borrowRegistRepairService.selectBorrowRegistList(borrowRegistListRequest);
        if (!CollectionUtils.isEmpty(borrowRegistCustomizeList)) {
            List<BorrowRegistCustomizeVO> voList = CommonUtils.convertBeanList(borrowRegistCustomizeList, BorrowRegistCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 根据borrowNid查询borrow信息
     * @auth sunpeikai
     * @param borrowNid 项目编号
     * @return
     */
    @ApiOperation(value = "根据borrowNid查询borrow信息", notes = "根据borrowNid查询borrow信息")
    @GetMapping("/search_borrow_by_borrownid/{borrowNid}")
    public BorrowResponse searchBorrowByBorrowNid(@PathVariable String borrowNid){
        logger.info("handleBorrowRegistException::::::::::borrowNid=[{}]",borrowNid);
        BorrowResponse borrowResponse = new BorrowResponse();
        BorrowAndInfoVO borrowVO = borrowRegistRepairService.searchBorrowByBorrowNid(borrowNid);
        if(borrowVO != null){
            borrowResponse.setResult(borrowVO);
            borrowResponse.setRtn(Response.SUCCESS);
        }
        return borrowResponse;
    }

    /**
     * 根据受托支付userId查询stAccountId
     * @auth sunpeikai
     * @param entrustUserId 受托支付userId
     * @return
     */
    @ApiOperation(value = "根据受托支付userId查询stAccountId", notes = "根据受托支付userId查询stAccountId")
    @GetMapping(value = "/get_staccountid_by_entrusteduserid/{entrusteduserid}")
    public String getStAccountIdByEntrustedUserId(@PathVariable Integer entrustUserId){
        return  borrowRegistRepairService.getStAccountIdByEntrustedUserId(entrustUserId);
    }

    /**
     * 更新标
     * @auth sunpeikai
     * @param registUpdateRequest 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    @ApiOperation(value = "更新标", notes = "更新标")
    @PostMapping(value = "/update_borrowregist_by_type")
    public Boolean updateBorrowRegistByType(@RequestBody BorrowRegistUpdateRequest registUpdateRequest){
        logger.debug(JSON.toJSONString(registUpdateRequest));
        return borrowRegistRepairService.updateBorrowRegistByType(registUpdateRequest);
    }

    /**
     * 更新标的资产信息如果关联计划的话
     * @auth sunpeikai
     * @param status 状态  受托支付传4，非受托支付传5
     * @return
     */
    @ApiOperation(value = "更新标的资产信息如果关联计划的话", notes = "更新标的资产信息如果关联计划的话")
    @PostMapping(value = "/update_borrowasset/{status}")
    public Boolean updateBorrowAsset(@RequestBody BorrowAndInfoVO borrowVO,@PathVariable Integer status){
        return  borrowRegistRepairService.updateBorrowAsset(borrowVO,status);
    }


}
