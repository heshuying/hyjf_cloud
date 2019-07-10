/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.user.CreditConsumeResultResponse;
import com.hyjf.am.user.service.front.user.DuiBaService;
import com.hyjf.am.vo.user.CreditConsumeResultVO;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version DuiBaController, v0.1 2019/6/6 10:20
 */
@RestController
@RequestMapping("/am-user/pointsshop/duiba")
public class DuiBaController {
    @Autowired
    DuiBaService duiBaService;

    /**
     * 兑吧扣积分回调相关业务
     * @param consumeParams
     * @return
     */
    @RequestMapping("/deductpoints")
    public CreditConsumeResultResponse deductPoints(@RequestBody CreditConsumeParams consumeParams){
        CreditConsumeResultResponse response = new CreditConsumeResultResponse();
        CreditConsumeResultVO resultVO = new CreditConsumeResultVO();
        // 扣积分前进行校验
        String checkResult = duiBaService.getCheckResult(consumeParams);
        if(StringUtils.isNotBlank(checkResult)){
            resultVO.setSuccess(false);
            resultVO.setErrorMessage(checkResult);
            response.setResult(resultVO);
        } else {
            // 扣减用户积分并返回结果
            try {
                resultVO = duiBaService.updateUserPoints(consumeParams);
            } catch (Exception e){
                resultVO.setSuccess(false);
                resultVO.setErrorMessage("兑换失败，请联系相关人员查看。");
                response.setResult(resultVO);
            }
            response.setResult(resultVO);
        }
        return response;
    }

}
