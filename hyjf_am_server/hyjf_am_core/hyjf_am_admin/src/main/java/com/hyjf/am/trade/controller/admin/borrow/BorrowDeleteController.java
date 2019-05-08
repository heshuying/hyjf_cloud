/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.borrow;

import com.hyjf.am.response.admin.BorrowDeleteConfirmCustomizeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.borrow.BorrowDeleteService;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
