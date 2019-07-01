/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankMobileModifyResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.BankMobileModify;
import com.hyjf.am.user.service.front.user.BankMobileModifyService;
import com.hyjf.am.vo.user.BankMobileModifyVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 修改银行预留手机号日志表
 *
 * @author PC-LIUSHOUYI
 * @version BankMobileModifyController, v0.1 2019/5/9 15:28
 */
@RestController
@RequestMapping("/am-user/bankMobileModify")
public class BankMobileModifyController extends BaseController {

    @Autowired
    private BankMobileModifyService bankMobileModifyService;

    /**
     * 查询记录
     *
     * @return
     */
    @RequestMapping("/selectBankMobileModify")
    public BankMobileModifyResponse selectBankMobileModify(@RequestBody BankMobileModifyVO vo) {
        BankMobileModifyResponse response = new BankMobileModifyResponse();
        List<BankMobileModify> list = this.bankMobileModifyService.selectBankMobileModify(vo);
        if (!CollectionUtils.isEmpty(list)) {
            List<BankMobileModifyVO> voList = CommonUtils.convertBeanList(list, BankMobileModifyVO.class);
            response.setResultList(voList);
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return response;
    }

    /**
     * 插入记录
     *
     * @return
     */
    @RequestMapping("/insertBankMobileModify")
    public BooleanResponse insertBankMobileModify(@RequestBody BankMobileModifyVO vo) {
        BooleanResponse response = new BooleanResponse();
        BankMobileModify bankMobileModify = new BankMobileModify();
        BeanUtils.copyProperties(vo, bankMobileModify);
        boolean result = this.bankMobileModifyService.insertBankMobileModify(bankMobileModify) > 0 ? true : false;
        response.setResultBoolean(result);
        return response;
    }

    /**
     * 更新记录
     *
     * @param vo
     * @return
     */
    @RequestMapping("/updateBankMobileModify")
    public BooleanResponse updateBankMobileModify(@RequestBody BankMobileModifyVO vo) {
        BooleanResponse response = new BooleanResponse();
        BankMobileModify bankMobileModify = new BankMobileModify();
        BeanUtils.copyProperties(vo, bankMobileModify);
        boolean result = this.bankMobileModifyService.updateBankMoblieModify(bankMobileModify) > 0 ? true:false;
        response.setResultBoolean(result);
        return response;
    }

    /**
     * 修改用户银行预留手机号
     * @param userId
     * @param newBankMobile
     * @return
     */
    @RequestMapping("/updateBankMobileByUserId/{userId}/{newBankMobile}")
    public BooleanResponse updateBankMobileByUserId(@PathVariable Integer userId,
                                                    @PathVariable String newBankMobile){
        BooleanResponse response = new BooleanResponse();
        response.setResultBoolean(bankMobileModifyService.updateBankMobileByUserId(userId, newBankMobile) > 0 ? true:false);
        return response;
    }
}
