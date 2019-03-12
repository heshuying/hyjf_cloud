/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.crm;

import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.crm.CrmTenderRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CRM投资同步修复
 *
 * @author liuyang
 * @version CrmTenderRepairController, v0.1 2019/3/12 9:32
 */
@Api(value = "api端-获取回款记录接口", tags = "api端-获取回款记录接口")
@RestController
@RequestMapping("/hyjf-api/crm")
public class CrmTenderRepairController extends BaseTradeController {

    @Autowired
    CrmTenderRepairService crmTenderRepairService;

    /**
     * CRM投资同步修复
     *
     * @param
     * @return
     * @auth liuyang
     */
    @ApiOperation(value = "CRM投资数据同步修复", notes = "CRM投资数据同步修复")
    @ResponseBody
    @GetMapping(value = "/tenderRepair")
    public String repair() {
        List<BorrowTenderVO> borrowTenderList = this.crmTenderRepairService.selectCrmBorrowTenderList();
        if (borrowTenderList != null && borrowTenderList.size() > 0) {
            logger.info("散标出借共:[" + borrowTenderList.size() + "]条数据待修复");
            for (int i = 0; i < borrowTenderList.size(); i++) {
                BorrowTenderVO borrowTenderVO = borrowTenderList.get(i);
                this.crmTenderRepairService.sendCrmTenderInfoMQ(borrowTenderVO);
            }
        }

        List<HjhAccedeVO> hjhAccedeVOList = this.crmTenderRepairService.selectCrmHjhAccedeList();

        if (hjhAccedeVOList != null && hjhAccedeVOList.size() > 0) {
            logger.info("智投出借共:[" + hjhAccedeVOList.size() + "]条数据待修复");
            for (int i = 0; i < hjhAccedeVOList.size(); i++) {
                HjhAccedeVO hjhAccedeVO = hjhAccedeVOList.get(i);
                this.crmTenderRepairService.sendCrmHjhInfoMQ(hjhAccedeVO);
            }
        }
        return "success";
    }
}
