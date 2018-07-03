/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
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
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<BorrowRegistCustomize> borrowRegistCustomizeList = borrowRegistExceptionService.selectBorrowRegistList(borrowRegistListRequest);
        if (!CollectionUtils.isEmpty(borrowRegistCustomizeList)) {
            List<BorrowRegistCustomizeVO> voList = CommonUtils.convertBeanList(borrowRegistCustomizeList, BorrowRegistCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
