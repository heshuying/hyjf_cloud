/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.Response;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.service.front.hjh.HjhPlanBorrowTmpService;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自动投资（原子层）
 * @author liubin
 * @version AutoTenderController, v0.1 2018/6/28 19:08
 */
@RestController
@RequestMapping("/am-trade/hjhPlanBorrowTmpController")
public class HjhPlanBorrowTmpController extends BaseController {

    @Autowired
    private HjhPlanBorrowTmpService hjhPlanBorrowTmpService;

    @RequestMapping("/insertHjhPlanBorrowTmp")
    public Response<Integer> insertHjhPlanBorrowTmp(@RequestBody HjhDebtCreditVO hjhDebtCreditVO) {
        HjhPlanBorrowTmp hjhPlanBorrowTmp = new HjhPlanBorrowTmp();
        BeanUtils.copyProperties(hjhDebtCreditVO,hjhPlanBorrowTmp);
        return new Response(this.hjhPlanBorrowTmpService.insertHjhPlanBorrowTmp(hjhPlanBorrowTmp));
    }

    @RequestMapping("/deleteHjhPlanBorrowTmp")
    public Response<Integer> deleteHjhPlanBorrowTmp(@RequestBody HjhDebtCreditVO hjhDebtCreditVO) {
        HjhPlanBorrowTmp hjhPlanBorrowTmp = new HjhPlanBorrowTmp();
        BeanUtils.copyProperties(hjhDebtCreditVO,hjhPlanBorrowTmp);
        return new Response(this.hjhPlanBorrowTmpService.deleteHjhPlanBorrowTmp(hjhPlanBorrowTmp));
    }

    @RequestMapping("/updateHjhPlanBorrowTmpByPK")
    public Response<Integer> updateHjhPlanBorrowTmpByPK(@RequestBody HjhDebtCreditVO hjhDebtCreditVO) {
        HjhPlanBorrowTmp hjhPlanBorrowTmp = new HjhPlanBorrowTmp();
        BeanUtils.copyProperties(hjhDebtCreditVO,hjhPlanBorrowTmp);
        return new Response(this.hjhPlanBorrowTmpService.updateHjhPlanBorrowTmpByPK(hjhPlanBorrowTmp));
    }
}
