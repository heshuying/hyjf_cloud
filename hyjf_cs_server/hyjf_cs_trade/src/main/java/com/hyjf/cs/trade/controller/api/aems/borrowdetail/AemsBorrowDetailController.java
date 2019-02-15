package com.hyjf.cs.trade.controller.api.aems.borrowdetail;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.AemsBorrowDetailRequestBean;
import com.hyjf.cs.trade.bean.AemsBorrowListRequestBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.borrow.AemsBorrowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AEMS标的详情信息接口
 * @author Zha Daojian
 * @date 2018/12/17 9:34
 * @param
 * @return
 **/
@Api(value = "api端-AEMS标的详情信息接口",tags = "api端-AEMS标的详情信息接口")
@RestController
@RequestMapping("/hyjf-api/aems/borrowDetail")
public class AemsBorrowDetailController extends BaseTradeController {



    @Autowired
    private AemsBorrowService aemsBorrowService;

    /**
     * 查询标的详情
     * @author Zha Daojian
     * @date 2018/12/17 9:35
     * @param reqBean
     * @return com.hyjf.cs.common.bean.result.ApiResult
     **/
    @ApiOperation(value = "标的详情" , notes = "标的详情")
    @PostMapping("/getBorrowDetail")
    public ApiResult getBorrowDetail(@RequestBody AemsBorrowDetailRequestBean reqBean){
        ApiResult apiResult = new ApiResult();
        apiResult = aemsBorrowService.getBorrowDetail(reqBean);
        return apiResult;
    }
}
