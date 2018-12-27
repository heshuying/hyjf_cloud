package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.resquest.admin.BorrowApicronRequest;
import com.hyjf.am.trade.service.agreement.FddPushService;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/12/26
 * @Description: 法大大协议推送
 */
@Api(value = "法大大协议推送")
@RestController
@RequestMapping("am-trade/batch/fddpush")
public class FddPushController {

    @Autowired
    private FddPushService fddPushService;

    @GetMapping("/getfddpushborrowlist")
    public BorrowApicronResponse getFddPushBorrowList(){

        List<BorrowApicronVO> list =  fddPushService.getFddPushBorrowList();
        BorrowApicronResponse response = new BorrowApicronResponse();
        response.setResultList(list);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @GetMapping("/updateFddPush")
    public void updateFddPush(@RequestBody BorrowApicronRequest request){

        fddPushService.updateFddPush(request);
    }

    @GetMapping("/getBorrowRecoverPlanByNidandPeriod")
    public BorrowRecoverPlanResponse getBorrowRecoverPlanByNidandPeriod(@RequestBody BorrowRecoverPlanVO planinfo){

        BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
        BorrowRecoverPlanVO info = fddPushService.getBorrowRecoverPlanByNidandPeriod(planinfo);
        response.setResult(info);
        return response;
    }
}
