/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.borrow;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRegistCancelConfirmCustomizeResponse;
import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowRegistService;
import com.hyjf.am.vo.admin.BorrowRegistCancelConfirmCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
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
public class BorrowRegistController extends BaseController {
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
    public BorrowRegistCustomizeResponse sumBorrowRegistAccount(@RequestBody @Valid BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistCustomizeResponse response = new BorrowRegistCustomizeResponse();
        String sumAccount =  borrowRegistService.getSumBorrowRegistAccount(borrowRegistListRequest);
        response.setSumAccount(sumAccount);
        return response;
    }

    /**
     * 标的备案
     * @param request
     * @return
     */
    @PostMapping("/update_borrow_regist")
    public Response updateBorrowRegist(@RequestBody @Valid BorrowRegistUpdateRequest request) {
        return borrowRegistService.updateBorrowRegist(request);
    }

    /**
     * 备案撤销成功后更新数据库
     */
    @RequestMapping("/regist_cancel")
    public Response borrowCancel(@RequestBody @Valid BorrowRegistUpdateRequest request){
        return borrowRegistService.updateForRegistCancel(request);
    }

    /**
     * 获取备案撤销确认页面数据
     *
     * @return
     */
    @GetMapping("/registcancel_confirm/{borrowNid}")
    public BorrowRegistCancelConfirmCustomizeResponse selectBorrowRegistCancelConfirm(@PathVariable String borrowNid) {
        BorrowRegistCancelConfirmCustomizeResponse response = new BorrowRegistCancelConfirmCustomizeResponse();
        BorrowRegistCancelConfirmCustomizeVO borrowRegistCancelConfirmCustomizeVO = borrowRegistService.selectRegistCancelConfirm(borrowNid);
        response.setResult(borrowRegistCancelConfirmCustomizeVO);
        return response;
    }
}
