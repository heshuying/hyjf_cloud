/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.crm;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.api.crm.CrmTenderRepairService;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CRM 投资同步修复
 *
 * @author liuyang
 * @version CrmTenderRepairController, v0.1 2019/3/12 9:49
 */
@RestController
@RequestMapping("/am-trade/crm")
public class CrmTenderRepairController extends BaseController {
    @Autowired
    private CrmTenderRepairService crmTenderRepairService;

    @ApiOperation(value = "查询散标投资列表")
    @GetMapping("/selectCrmBorrowTenderList")
    public BorrowTenderResponse selectCrmBorrowTenderList() {
        List<BorrowTender> borrowTenderList = this.crmTenderRepairService.selectCrmBorrowTenderList();
        BorrowTenderResponse response = new BorrowTenderResponse();
        if (borrowTenderList != null && borrowTenderList.size() > 0) {
            List<BorrowTenderVO> borrowTenderVOList = CommonUtils.convertBeanList(borrowTenderList, BorrowTenderVO.class);
            response.setResultList(borrowTenderVOList);
            return response;
        }
        return null;
    }



    @ApiOperation(value = "查询智投服务列表")
    @GetMapping("/selectCrmHjhAccedeList")
    public HjhAccedeResponse selectCrmHjhAccedeList() {
        List<HjhAccede> borrowTenderList = this.crmTenderRepairService.selectCrmHjhAccedeList();
        HjhAccedeResponse response = new HjhAccedeResponse();
        if (borrowTenderList != null && borrowTenderList.size() > 0) {
            List<HjhAccedeVO> borrowTenderVOList = CommonUtils.convertBeanList(borrowTenderList, HjhAccedeVO.class);
            response.setResultList(borrowTenderVOList);
            return response;
        }
        return null;
    }
}
