/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.borrow;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowDeleteConfirmCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.borrow.BorrowDeleteService;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 标的删除
 * @author hesy
 */
@RestController
@RequestMapping("/am-trade/borrow_delete")
public class BorrowDeleteController extends BaseController {
    @Autowired
    BorrowDeleteService borrowDeleteService;


    /**
     * 标的删除更新数据库
     */
//    @RequestMapping("/delete")
//    public Response borrowCancel(@RequestBody @Valid BorrowRegistUpdateRequest request){
//        return borrowRegistService.updateForRegistCancel(request);
//    }

    /**
     * 标的删除确认页面数据
     *
     * @return
     */
    @GetMapping("/delete_confirm/{borrowNid}")
    public BorrowDeleteConfirmCustomizeResponse selectBorrowRegistCancelConfirm(@PathVariable String borrowNid) {
        BorrowDeleteConfirmCustomizeResponse response = new BorrowDeleteConfirmCustomizeResponse();
        BorrowDeleteConfirmCustomizeVO borrowDeleteConfirmCustomizeVO = borrowDeleteService.selectDeleteConfirm(borrowNid);
        response.setResult(borrowDeleteConfirmCustomizeVO);
        return response;
    }

    /**
     * 标的撤销成功后删除对应标的数据
     */
    @RequestMapping("/delete")
    public Response borrowCancel(@RequestBody @Valid BorrowRegistUpdateRequest requestBean){
        return borrowDeleteService.deleteBorrow(requestBean);
    }
}
