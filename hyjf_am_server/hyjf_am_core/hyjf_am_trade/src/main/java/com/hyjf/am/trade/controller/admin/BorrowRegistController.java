/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.BorrowRegistService;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistController, v0.1 2018/6/29 16:41
 */
@RestController
@RequestMapping("/am-trade/borrow_regist")
public class BorrowRegistController {
    @Autowired
    BorrowRegistService borrowRegistService;

    /**
     * 获取项目类型
     *
     * @return
     */
    @RequestMapping("/select_borrow_project")
    public BorrowProjectTypeResponse selectBorrowProjectList() {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        List<BorrowProjectType> borrowProjectTypeList = borrowRegistService.selectBorrowProjectList();
        if (!CollectionUtils.isEmpty(borrowProjectTypeList)) {
            List<BorrowProjectTypeVO> voList = CommonUtils.convertBeanList(borrowProjectTypeList, BorrowProjectTypeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取还款方式
     *
     * @return
     */
    @RequestMapping("/select_borrow_style")
    public BorrowStyleResponse selectBorrowStyleList() {
        BorrowStyleResponse response = new BorrowStyleResponse();
        List<BorrowStyle> borrowStyleList = borrowRegistService.selectBorrowStyleList();
        if (!CollectionUtils.isEmpty(borrowStyleList)) {
            List<BorrowStyleVO> voList = CommonUtils.convertBeanList(borrowStyleList, BorrowStyleVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取标的列表count
     *
     * @return
     */
    @RequestMapping("/get_regist_count")
    public BorrowRegistCustomizeResponse getRegistCount(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        int count = borrowRegistService.getRegistCount(borrowRegistListRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 获取标的列表
     *
     * @return
     */
    @RequestMapping("/select_borrow_regist_list")
    public BorrowRegistCustomizeResponse selectBorrowRegistList(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        List<BorrowRegistCustomize> borrowRegistCustomizeList = borrowRegistService.selectBorrowRegistList(borrowRegistListRequest);
        if (!CollectionUtils.isEmpty(borrowRegistCustomizeList)) {
            List<BorrowRegistCustomizeVO> voList = CommonUtils.convertBeanList(borrowRegistCustomizeList, BorrowRegistCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 统计页面值总和
     *
     * @return
     */
    @RequestMapping("/sum_borrow_regist_account")
    public String sumBorrowRegistAccount(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistService.sumBorrowRegistAccount(borrowRegistListRequest);
    }

    /**
     * 获取受托支付电子账户列表
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    @GetMapping("/select_stzf_white_list/{instCode}/{entrustedAccountId}")
    public STZHWhiteListResponse selectStzfWhiteList(@PathVariable String instCode, @PathVariable String entrustedAccountId) {
        STZHWhiteListResponse response = new STZHWhiteListResponse();
        StzhWhiteList stzhWhiteList = borrowRegistService.selectStzfWhiteList(instCode, entrustedAccountId);
        if (stzhWhiteList != null) {
            STZHWhiteListVO stzhWhiteListVO = new STZHWhiteListVO();
            BeanUtils.copyProperties(stzhWhiteList, stzhWhiteListVO);
            response.setResult(stzhWhiteListVO);
        }
        return response;
    }

    /**
     * 更新标的信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/update_borrow_regist")
    public BorrowRegistCustomizeResponse updateBorrowRegist(@RequestBody BorrowRegistRequest request) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        int updateCount = borrowRegistService.updateBorrowRegistFromList(request);
        response.setTotal(updateCount);
        return response;
    }

    /**
     * 更新标的信息(受托支付备案)
     *
     * @param request
     * @return
     */
    @RequestMapping("/update_entrusted_borrow_regist")
    public BorrowRegistCustomizeResponse updateEntrustedBorrowRegist(@RequestBody BorrowRegistRequest request) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        int updateCount = borrowRegistService.updateEntrustedBorrowRegist(request);
        response.setTotal(updateCount);
        return response;
    }
}
