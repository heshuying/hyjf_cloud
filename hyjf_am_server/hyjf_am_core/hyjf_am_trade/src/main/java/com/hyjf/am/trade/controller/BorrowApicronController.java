/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.BorrowApicronService;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVo;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ${yaoy}
 * @version BorrowApicronController, v0.1 2018/6/14 16:44
 */
@RestController
@RequestMapping("/am-trade/borrowApicron")
public class BorrowApicronController {

    @Autowired
    private BorrowApicronService borrowApicronService;

    @GetMapping("/getBorrowApicronList/{status}/{apiType}")
    public BorrowApicronResponse getBorrowApicronList(Integer status, Integer apiType) {
        BorrowApicronResponse response = new BorrowApicronResponse();
        List<BorrowApicron> borrowApicronList = borrowApicronService.getBorrowApicronList(status,apiType);
        if (!CollectionUtils.isEmpty(borrowApicronList)) {
            List<BorrowApicronVo> borrowApicronVoList = CommonUtils.convertBeanList(borrowApicronList,BorrowApicronVo.class);
            response.setResultList(borrowApicronVoList);
        }
        return response;
    }

    @PostMapping("/updateBorrowApicron")
    public int updateBorrowApicron(@RequestBody Integer id, Integer status, String data) {
        int updateBorrowApicronFlag = borrowApicronService.updateBorrowApicron(id, status, data);
        return updateBorrowApicronFlag;
    }

    @PostMapping("/updateBorrowApicron2")
    public int updateBorrowApicron2(@RequestBody Integer id, Integer status) {
        int updateBorrowApicronFlag = borrowApicronService.updateBorrowApicron2(id, status);
        return updateBorrowApicronFlag;
    }

    @GetMapping("/getBorrowApicronListWithRepayStatus/{status}/{apiType}")
    public BorrowApicronResponse getBorrowApicronListWithRepayStatus(Integer status, Integer apiType) {
        BorrowApicronResponse response = new BorrowApicronResponse();
        List<BorrowApicron> borrowApicronList = borrowApicronService.getBorrowApicronListWithRepayStatus(status,apiType);
        if (!CollectionUtils.isEmpty(borrowApicronList)) {
            List<BorrowApicronVo> borrowApicronVoList = CommonUtils.convertBeanList(borrowApicronList,BorrowApicronVo.class);
            response.setResultList(borrowApicronVoList);
        }
        return response;
    }

    @PostMapping("/updateBorrowApicronOfRepayStatus")
    public int updateBorrowApicronOfRepayStatus(@RequestBody Integer id, Integer status) {
        int updateBorrowApicronFlag = borrowApicronService.updateBorrowApicronOfRepayStatus(id,status);
        return updateBorrowApicronFlag;
    }
}
