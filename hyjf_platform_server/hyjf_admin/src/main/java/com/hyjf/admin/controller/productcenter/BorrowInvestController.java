/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.request.BorrowInvestRequestBean;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowInvestController, v0.1 2018/7/10 9:06
 */
@Api(value = "汇直投-投资明细接口")
@RestController
@RequestMapping("/hyjf-admin/borrow_full")
public class BorrowInvestController extends BaseController {
    @Autowired
    BorrowInvestService borrowInvestService;

    @Autowired
    AdminCommonService adminCommonService;

    private static final String PARAM_NAME = "hyjf_param_name:";

    @ApiOperation(value = "投资明细初始化", notes = "投资明细初始化")
    @PostMapping("/init")
    @ResponseBody
    public AdminResult init(@RequestBody BorrowInvestRequestBean borrowInvestRequestBean) {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        //还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        responseBean.setBorrowStyleList(borrowStyleList);
        //操作平台
        Map<String, String> clientList = adminCommonService.getParamNameMap(PARAM_NAME + "CLIENT");
        responseBean.setClientList(clientList);
        //投资方式
        Map<String, String> investTypeList = adminCommonService.getParamNameMap(PARAM_NAME + "INVEST_TYPE");
        responseBean.setInvestTypeList(investTypeList);
        return new AdminResult(responseBean);
    }
}
