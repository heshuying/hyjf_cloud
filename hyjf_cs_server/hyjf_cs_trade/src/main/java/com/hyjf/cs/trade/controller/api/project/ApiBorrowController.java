package com.hyjf.cs.trade.controller.api.project;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.projectlist.ApiProjectListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * api端：标的接口
 * @author zhangyk
 * @date 2018/8/27 9:50
 */
@Api(value = "api端-标的信息接口",tags = "api端-标的信息接口")
@RestController
@RequestMapping("/hyjf-api/server/borrow")
public class ApiBorrowController extends BaseTradeController {



    @Autowired
    private ApiProjectListService apiProjectListService;

    /**
     * 查询标的列表
     * @author zhangyk
     * 原接口：com.hyjf.api.server.borrow.borrowlist.BorrowListServer.borrowList()
     * @date 2018/8/27 10:02
     */
    @ApiOperation(value = "查询标的列表", notes = "查询标的列表")
    @PostMapping("/borrowList/borrowList.do")
    public ApiResult getBorrowList(@RequestBody ApiBorrowReqBean reqBean){
        ApiResult apiResult = new ApiResult();
        apiResult = apiProjectListService.getBorrowList(reqBean);
        return apiResult;
    }


    /**
     * 查询标的详情
     * @author zhanyk
     * 原接口：com.hyjf.api.server.borrowDetail.BorrowDetailServer.getProjectDetail()
     * @date 2018/8/29 13:46
     */
    @ApiOperation(value = "标的详情" , notes = "标的详情")
    @PostMapping("/getBorrowDetail.do")
    public ApiResult getBorrowDetail(@RequestBody ApiBorrowReqBean reqBean){
        ApiResult apiResult = new ApiResult();
        apiResult = apiProjectListService.getBorrowDetail(reqBean);
        return apiResult;
    }
}
