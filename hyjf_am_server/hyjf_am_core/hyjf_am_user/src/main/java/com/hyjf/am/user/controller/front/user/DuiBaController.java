/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.vo.user.CreditConsumeResultVO;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version DuiBaController, v0.1 2019/6/6 10:20
 */
@RestController
@RequestMapping("/am-user/pointsshop/duiba")
public class DuiBaController {
    @RequestMapping("/deductpoints")
    public CreditConsumeResultVO deductPoints(CreditConsumeParams consumeParams){
        return null;
    }

}
