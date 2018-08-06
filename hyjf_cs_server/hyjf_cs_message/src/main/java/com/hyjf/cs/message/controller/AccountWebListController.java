package com.hyjf.cs.message.controller;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.cs.message.service.account.AccountWebListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: walter.limeng
 * AccountWebList
 * @Date: 2018/8/1 13:59
 * @Description: AccountWebListController
 */
@RestController
@RequestMapping("/cs-message/accountweblist")
public class AccountWebListController {

    @Autowired
    private AccountWebListService accountWebListService;
    /**
     * @Author walter.limeng
     * @Description  根据nid和trade查询
     * @Date 11:33 2018/7/18
     * @Param nid
     * @Param trade
     * @return
     */
    @GetMapping("/countaccountweblist/{nid}/{trade}")
    public AccountWebListResponse countAccountWebList(@PathVariable String nid, @PathVariable String trade) {
        AccountWebListResponse response = new AccountWebListResponse();
        Integer count = accountWebListService.countAccountWebList(nid,trade);
        response.setRecordTotal(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  新增accountWebList
     * @Date 14:08 2018/7/18
     * @Param
     * @return
     */
    @PostMapping("/insertaccountweblist")
    public AccountWebListResponse insertAccountWebList(@RequestBody AccountWebListVO accountWebList) {
        AccountWebListResponse response = new AccountWebListResponse();
        Integer count = accountWebListService.insertAccountWebList(accountWebList);
        response.setRecordTotal(count);
        return response;
    }
}
