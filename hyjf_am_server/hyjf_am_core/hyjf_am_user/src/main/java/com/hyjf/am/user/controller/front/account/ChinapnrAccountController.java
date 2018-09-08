/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.account;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.user.service.front.account.ChinapnrAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version ChinapnrController, v0.1 2018/9/8 10:08
 */
@RestController
@RequestMapping("/am-user/chinapnr")
public class ChinapnrAccountController {

    @Autowired
    ChinapnrAccountService chinapnrAccountService;

    /**
     * 根据汇付账户查询user_id
     * @param chinapnrUsrcustid
     * @return
     */
    @GetMapping("/selectUserIdByUsrcustid/{chinapnrUsrcustid}")
    public IntegerResponse selectUserIdByUsrcustid(@PathVariable Long chinapnrUsrcustid) {
        int cnt = chinapnrAccountService.selectUserIdByUsrcustid(chinapnrUsrcustid);
        return new IntegerResponse(cnt);
    }
}
