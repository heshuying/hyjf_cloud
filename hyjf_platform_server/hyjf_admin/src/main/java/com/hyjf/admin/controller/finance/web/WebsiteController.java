/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.web;

import com.hyjf.admin.beans.request.WebBean;
import com.hyjf.admin.beans.response.WebsiteResponse;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.WebsiteService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangqingqing
 * @version WebsiteController, v0.1 2018/7/6 9:43
 */
@Api(value = "网站收支")
@RestController
@RequestMapping("/hyjf-admin/finance/web")
public class WebsiteController extends BaseController {

    @Autowired
    WebsiteService websiteService;
    /**
     * 网站收支 列表
     * @param
     * @param form
     * @return
     */
    @ApiOperation(value = "网站收支列表",notes = "网站收支列表")
    @PostMapping(value = "/weblist")
    //View
    public AdminResult init(@RequestBody WebBean form) {
        AccountWebListVO accountWebList = new AccountWebListVO();
        BeanUtils.copyProperties(form, accountWebList);
        WebsiteResponse websiteResponse = new WebsiteResponse();
        //交易类型列表
        List<AccountTradeVO> trades= this.websiteService.selectTradeTypes();
        AccountWebListResponse response = websiteService.queryAccountWebList(accountWebList);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        //交易金额总计
        String sumAccount = this.websiteService.selectBorrowInvestAccount(accountWebList);
        if(response == null||response.getRecordTotal()==0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        websiteResponse.setTradeList(trades);
        websiteResponse.setSumAccount(sumAccount);
        websiteResponse.setAccountWebList(response.getResultList());
        return new AdminResult(websiteResponse);
    }


}
