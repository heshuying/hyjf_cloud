package com.hyjf.cs.trade.controller.api.inveset;


import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.cs.trade.bean.api.ApiInvestListReqBean;
import com.hyjf.cs.trade.service.invest.BorrowListService;

/**
 * api端：标的接口
 * @author wenxin
 * @date 2018/8/27 13:48
 */
@Api(value = "api端-投资信息接口",tags = "api端-投资信息接口")
@RestController
@RequestMapping("/server/invest")
public class ApiInvestController extends BaseTradeController {

    @Autowired
    private BorrowListService investBorrowService;

    /**
     * 获取投资信息
     * @author wenxin
     * 原接口：com.hyjf.cs.trade.controller.api.inveset.investList()
     * @date 2018/8/27 13:52
     */
    @ApiOperation(value = "查询投资信息的列表", notes = "查询投资信息的列表")
    @PostMapping("/investList.do")
    public ApiResult investList(@RequestBody ApiInvestListReqBean bean) {
        ApiResult result = new ApiResult();
        // 返回查询结果
        result = investBorrowService.getInvestList(bean);
        return result;
    }
}
