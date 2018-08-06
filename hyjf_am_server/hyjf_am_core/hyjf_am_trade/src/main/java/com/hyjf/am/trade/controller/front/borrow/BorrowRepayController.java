/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.response.trade.BorrowRepayResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.service.front.borrow.BorrowRepayService;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayController, v0.1 2018/6/23 11:32
 */
@Api(value = "还款记录总表")
@RestController
@RequestMapping("/am-trade/borrowRepay")
public class BorrowRepayController extends BaseController {

    @Autowired
    BorrowRepayService borrowRepayService;

    /**
     * 检索正在还款中的标的
     *
     * @Author liushouyi
     * @return
     */
    @GetMapping("/selectBorrowRepayList/{borrowNid}/{repaySmsReminder}")
    public BorrowRepayResponse selectBorrowRepayList(@PathVariable String borrowNid,@PathVariable Integer repaySmsReminder) {
        BorrowRepayResponse response = new BorrowRepayResponse();
        List<BorrowRepay> borrowRepays = borrowRepayService.selectBorrowRepayList(borrowNid ,repaySmsReminder);
        if(borrowRepays != null){
            List<BorrowRepayVO> borrowRepayVO = CommonUtils.convertBeanList(borrowRepays,BorrowRepayVO.class);
            response.setResultList(borrowRepayVO);
        }
        return response;
    }

    @RequestMapping("/updateBorrowRepay")
    public Integer updateBorrowRepay(@RequestBody @Valid BorrowRepayVO borrowRepayVO) {
        return this.borrowRepayService.updateBorrowRepay(borrowRepayVO);
    }


    @GetMapping("getBorrowRepayListByBorrowNid/{borrowNid}")
    public BorrowRepayResponse getBorrowRepayListByBorrowNid(@PathVariable String borrowNid) {
        BorrowRepayResponse response = new BorrowRepayResponse();
        List<BorrowRepay> borrowRepays = borrowRepayService.getBorrowRepayList(borrowNid);
        if (CollectionUtils.isNotEmpty(borrowRepays)) {
            List<BorrowRepayVO> borrowRepayVOS = CommonUtils.convertBeanList(borrowRepays, BorrowRepayVO.class);
            response.setResultList(borrowRepayVOS);
        }
        return response;
    }
}
