/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.finance;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.user.AccountChinapnrResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AccountChinapnr;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.admin.finance.CustomerTransferService;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferController, v0.1 2018/7/6 9:27
 */
@Api(value = "资金中心-转账管理-用户转账",tags ="资金中心-转账管理-用户转账")
@RestController(value = "userCustomerTransferController")
@RequestMapping(value = "/am-user/customertransfer")
public class CustomerTransferController extends BaseController {

    @Autowired
    private CustomerTransferService customerTransferService;

    /**
     * 根据userName查询用户信息list
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据userName查询用户信息list",notes = "根据userName查询用户信息list")
    @GetMapping(value = "/searchuserbyusername/{userName}")
    public UserResponse searchUserByUsername(@PathVariable String userName){
        UserResponse response = new UserResponse();
        List<User> userList = customerTransferService.searchUserByUsername(userName);
        logger.debug("userList=[{}]", JSON.toJSONString(userList));
        if(!CollectionUtils.isEmpty(userList)){
            logger.info("am-user searchUserByUserName 查询出来的userList.size=[{}]",userList.size());
            List<UserVO> userVOList = CommonUtils.convertBeanList(userList,UserVO.class);
            response.setResultList(userVOList);
            response.setRtn("0");
            response.setMessage("查询成功");
        }
        return response;
    }

    /**
     * 根据userId查询AccountChinapnr开户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据userId查询AccountChinapnr开户信息",notes = "根据userId查询AccountChinapnr开户信息")
    @GetMapping(value = "/searchaccountchinapnrbyuserid/{userId}")
    public AccountChinapnrResponse searchAccountChinapnrByUserId(@PathVariable Integer userId){
        AccountChinapnrResponse response = new AccountChinapnrResponse();
        List<AccountChinapnr> accountChinapnrList = customerTransferService.searchAccountChinapnrByUserId(userId);
        if(!CollectionUtils.isEmpty(accountChinapnrList)){
            List<AccountChinapnrVO> accountChinapnrVOList = CommonUtils.convertBeanList(accountChinapnrList,AccountChinapnrVO.class);
            response.setResultList(accountChinapnrVOList);
            response.setRtn("0");
            response.setMessage("查询成功");
        }
        return response;
    }
    /**
     * 根据userId查询User用户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据userId查询User用户信息",notes = "根据userId查询User用户信息")
    @GetMapping(value = "/searchuserbyuserid/{userId}")
    public UserResponse searchUserByUserId(@PathVariable Integer userId){
        UserResponse response = new UserResponse();
        User user = customerTransferService.searchUserByUserId(userId);
        if(user != null){
            UserVO userVO = CommonUtils.convertBean(user,UserVO.class);
            response.setResult(userVO);
            response.setRtn("0");
            response.setMessage("查询成功");
        }
        return response;
    }

}
