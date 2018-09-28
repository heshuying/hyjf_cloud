package com.hyjf.am.trade.controller.front.repay;

import com.hyjf.am.response.trade.ApiBorrowRepaymentInfoResponse;
import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.ApiBorrowRepaymentInfoCustomize;
import com.hyjf.am.trade.service.front.repay.ApiBorrowRepaymentInfoService;
import com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version ApiBorrowRepaymentInfoController, v0.1 2018/8/28 14:14
 * @Author: Zha Daojian
 */
@Api(value = "api端-第三方还款明细查询")
@RestController
@RequestMapping("/am-trade/apiBorrowRepaymentInfo")
public class ApiBorrowRepaymentInfoController extends BaseController {

    @Autowired
    private ApiBorrowRepaymentInfoService borrowRepaymentInfoService;

    /**
     * 第三方还款明细查询
    * @author Zha Daojian
    * @date 2018/8/28 14:44
    * @param request
    * @return com.hyjf.am.response.trade.HjhAssetBorrowTypeResponse
    **/
    @PostMapping("/selectBorrowRepaymentInfoList")
    public ApiBorrowRepaymentInfoResponse selectAssetBorrowType(@RequestBody ApiBorrowRepaymentInfoRequest request) {
        ApiBorrowRepaymentInfoResponse response = new ApiBorrowRepaymentInfoResponse();
        List<ApiBorrowRepaymentInfoCustomize> list = borrowRepaymentInfoService.selectBorrowRepaymentInfoList(request);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,ApiBorrowRepaymentInfoCustomizeVO.class));
        }
        return response;
    }
}
