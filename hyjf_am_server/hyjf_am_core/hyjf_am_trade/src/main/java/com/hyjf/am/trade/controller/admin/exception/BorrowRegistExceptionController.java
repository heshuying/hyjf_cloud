/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.exception.BorrowRegistExceptionService;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionController, v0.1 2018/7/3 15:05
 */
@RestController
@RequestMapping("/am-trade/borrow_regist_exception")
public class BorrowRegistExceptionController extends BaseController {

    @Autowired
    private BorrowRegistExceptionService borrowRegistExceptionService;

    /**
     * 获取项目类型
     * @return
     */
    @RequestMapping("/select_borrow_project")
    public BorrowProjectTypeResponse selectBorrowProjectList() {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        List<BorrowProjectType> borrowProjectTypeList = borrowRegistExceptionService.selectBorrowProjectList();
        if (!CollectionUtils.isEmpty(borrowProjectTypeList)) {
            List<BorrowProjectTypeVO> voList = CommonUtils.convertBeanList(borrowProjectTypeList, BorrowProjectTypeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取还款方式
     * @return
     */
    @RequestMapping("/select_borrow_style")
    public BorrowStyleResponse selectBorrowStyleList() {
        BorrowStyleResponse response = new BorrowStyleResponse();
        List<BorrowStyle> borrowStyleList = borrowRegistExceptionService.selectBorrowStyleList();
        if (!CollectionUtils.isEmpty(borrowStyleList)) {
            List<BorrowStyleVO> voList = CommonUtils.convertBeanList(borrowStyleList, BorrowStyleVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取标的列表count
     * @return
     */
    @RequestMapping("/get_regist_count")
    public Integer getRegistCount(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistExceptionService.getRegistCount(borrowRegistListRequest);
    }

    /**
     * 获取标的列表
     * @return
     */
    @RequestMapping("/select_borrow_regist_list")
    public BorrowRegistCustomizeResponse selectBorrowRegistList(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        Integer recordTotal = borrowRegistExceptionService.getRegistCount(borrowRegistListRequest);
        logger.info("selectBorrowRegistList::::::::::recordTotal=[{}]",recordTotal);
        Paginator paginator = new Paginator(borrowRegistListRequest.getCurrPage(), recordTotal);
        borrowRegistListRequest.setLimitStart(paginator.getOffset());
        borrowRegistListRequest.setLimitEnd(paginator.getLimit());
        logger.info("selectBorrowRegistList::::::::::limitStart=[{}],limitEnd=[{}]",borrowRegistListRequest.getLimitStart(),borrowRegistListRequest.getLimitEnd());

        List<BorrowRegistCustomize> borrowRegistCustomizeList = borrowRegistExceptionService.selectBorrowRegistList(borrowRegistListRequest);
        if (!CollectionUtils.isEmpty(borrowRegistCustomizeList)) {
            List<BorrowRegistCustomizeVO> voList = CommonUtils.convertBeanList(borrowRegistCustomizeList, BorrowRegistCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @GetMapping("/search_borrow_by_borrownid/{borrowNid}")
    public BorrowResponse searchBorrowByBorrowNid(@PathVariable String borrowNid){
        logger.info("handleBorrowRegistException::::::::::borrowNid=[{}]",borrowNid);
        BorrowResponse borrowResponse = new BorrowResponse();
        BorrowVO borrowVO = borrowRegistExceptionService.searchBorrowByBorrowNid(borrowNid);
        if(borrowVO != null){
            borrowResponse.setResult(borrowVO);
        }
        return borrowResponse;
    }
    /**
     * 标的备案异常处理
     * @auth 孙沛凯
     * @param entrustUserId 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常处理")
    @GetMapping(value = "/get_staccountid_by_entrusteduserid/{entrusteduserid}")
    public String getStAccountIdByEntrustedUserId(@PathVariable Integer entrustUserId){
        return  borrowRegistExceptionService.getStAccountIdByEntrustedUserId(entrustUserId);
    }
    /**
     * 标的备案异常处理
     * @auth 孙沛凯
     * @param type 类型
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常处理")
    @PostMapping(value = "/update_borrowregist_by_type/{type}")
    public Boolean updateBorrowRegistByType(@RequestBody BorrowVO borrowVO,@PathVariable Integer type){
        return  borrowRegistExceptionService.updateBorrowRegistByType(borrowVO,type);
    }
    /**
     * 标的备案异常处理
     * @auth 孙沛凯
     * @param status 状态  受托支付传4，非受托支付传5
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常处理")
    @PostMapping(value = "/update_borrowasset/{status}")
    public Boolean updateBorrowAsset(@RequestBody BorrowVO borrowVO,@PathVariable Integer status){
        return  borrowRegistExceptionService.updateBorrowAsset(borrowVO,status);
    }


}
