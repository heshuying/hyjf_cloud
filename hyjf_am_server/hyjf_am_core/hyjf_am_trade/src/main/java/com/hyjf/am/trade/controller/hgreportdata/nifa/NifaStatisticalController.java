/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.hgreportdata.nifa;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.trade.service.hgreportdata.nifa.NifaStatisticalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalController, v0.1 2019/1/19 16:58
 */
@Api(value = "互金统计监测二期")
@RestController
@RequestMapping("/am-trade/nifa_statistical")
public class NifaStatisticalController {

    @Autowired
    NifaStatisticalService nifaStatisticalService;

    /**
     * 查询用户借款笔数(企业)
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/select_borrow_users_count/{username}")
    public IntegerResponse selectBorrowUsersCount(@PathVariable String username) {
        Integer count = nifaStatisticalService.selectBorrowUsersCount(username);
        return new IntegerResponse(count);
    }

    /**
     * 查询用户借款笔数(个人)
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/select_borrow_man_info_count/{username}")
    public IntegerResponse selectBorrowManInfoCount(@PathVariable String username) {
        Integer count = nifaStatisticalService.selectBorrowManInfoCount(username);
        return new IntegerResponse(count);
    }
}
