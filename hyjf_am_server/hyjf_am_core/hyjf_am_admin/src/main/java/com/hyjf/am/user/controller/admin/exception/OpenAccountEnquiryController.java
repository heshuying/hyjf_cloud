package com.hyjf.am.user.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankOpenAccountLogResponse;
import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountLog;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.admin.exception.BankOpenAccountLogService;
import com.hyjf.am.vo.admin.BankOpenAccountLogVO;
import com.hyjf.am.vo.admin.OpenAccountEnquiryCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @version OpenAccountEnquiryController, v0.1 2018/8/21 14:11
 * @Author: Zha Daojian
 */
@RestController
@RequestMapping("/am-user/borrowOpenaccountenquiryException")
@Api(value = "异常中心-开户掉单",tags ="异常中心-开户掉单")
public class OpenAccountEnquiryController extends BaseController {

    @Autowired
    private BankOpenAccountLogService bankOpenAccountLogSrvice;

    /**
     * 通过手机号和身份证查询掉单信息
    * @author Zha Daojian
    * @date 2018/8/21 15:29
    * @param reqest
    * @return com.hyjf.am.response.user.BankOpenAccountLogResponse
    **/
    @ApiOperation(value = "通过手机号和身份证查询掉单信息", notes = "通过手机号和身份证查询掉单信息")
    @PostMapping(value = "/bankOpenAccountLogSelect")
    public BankOpenAccountLogResponse bankOpenAccountLogSelect(@RequestBody BankOpenAccountLogRequest reqest){
        String mobile = reqest.getMobile();
        String idcard = reqest.getIdcard();
        BankOpenAccountLogResponse response = new BankOpenAccountLogResponse();
        List<BankOpenAccountLog> bankOpenAccountLogList = bankOpenAccountLogSrvice.bankOpenAccountLogSelect(mobile,idcard);
        if(!CollectionUtils.isEmpty(bankOpenAccountLogList)){
            List<BankOpenAccountLogVO> bankCardExceptionCustomizeVOList = CommonUtils.convertBeanList(bankOpenAccountLogList,BankOpenAccountLogVO.class);
            response.setResultList(bankCardExceptionCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据订单号查询用户的开户记录
     * @author Zha Daojian
     * @date 2018/8/21 15:29
     * @param orderId
     * @return com.hyjf.am.response.user.BankOpenAccountLogResponse
     **/
    @ApiOperation(value = "根据订单号查询用户的开户记录", notes = "根据订单号查询用户的开户记录")
    @GetMapping(value = "/selectBankOpenAccountLogByOrderId/{orderId}")
    public BankOpenAccountLogResponse selectBankOpenAccountLogByOrderId(@PathVariable String orderId){
        BankOpenAccountLogResponse response = new BankOpenAccountLogResponse();
        BankOpenAccountLog bankOpenAccountLog = bankOpenAccountLogSrvice.selectBankOpenAccountLogByOrderId(orderId);
        BankOpenAccountLogVO bankCardExceptionCustomizeVO = CommonUtils.convertBean(bankOpenAccountLog,BankOpenAccountLogVO.class);
            response.setResult(bankCardExceptionCustomizeVO);
            response.setRtn(Response.SUCCESS);
        return response;
    }
    /**
     * 通过手机号和身份证查询用户信息
     * @author Zha Daojian
     * @date 2018/8/21 15:29
     * @param reqest
     * @return com.hyjf.am.response.user.BankOpenAccountLogResponse
     **/
    @ApiOperation(value = "通过手机号和身份证查询用户信息", notes = "通过手机号和身份证查询用户信息")
    @PostMapping(value = "/searchAccountEnquiry")
    public OpenAccountEnquiryResponse searchAccountEnquiry(@RequestBody BankOpenAccountLogRequest reqest){
        OpenAccountEnquiryResponse response = new OpenAccountEnquiryResponse();
        OpenAccountEnquiryCustomize openAccountEnquiryCustomize = bankOpenAccountLogSrvice.accountEnquiry(reqest);
        if(openAccountEnquiryCustomize!=null) {
            OpenAccountEnquiryCustomizeVO openAccountEnquiryCustomizeVO = CommonUtils.convertBean(openAccountEnquiryCustomize,OpenAccountEnquiryCustomizeVO.class);
            response.setResult(openAccountEnquiryCustomizeVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 查询返回的电子账号是否已开户
     * @author Zha Daojian
     * @date 2018/8/21 15:29
     * @param accountId
     * @return com.hyjf.am.response.user.BankOpenAccountLogResponse
     **/
    @ApiOperation(value = "查询返回的电子账号是否已开户", notes = "查询返回的电子账号是否已开户")
    @GetMapping(value = "/checkAccountByAccountId/{accountId}")
    public BankOpenAccountLogResponse checkAccountByAccountId(@PathVariable String accountId){
        BankOpenAccountLogResponse response = new BankOpenAccountLogResponse();
        Boolean checkAccount = bankOpenAccountLogSrvice.checkAccountByAccountId(accountId);
        response.setBankOpenFlag(checkAccount);
        return response;
    }

    /**
     * 根据userId删除开户日志
     * @author Zha Daojian
     * @date 2018/8/21 15:29
     * @param userId
     * @return com.hyjf.am.response.user.BankOpenAccountLogResponse
     **/
    @ApiOperation(value = "查询返回的电子账号是否已开户", notes = "查询返回的电子账号是否已开户")
    @GetMapping(value = "/deleteBankOpenAccountLogByUserId/{userId}")
    public BankOpenAccountLogResponse deleteBankOpenAccountLogByUserId(@PathVariable Integer userId){
        BankOpenAccountLogResponse response = new BankOpenAccountLogResponse();
        Boolean checkAccount = bankOpenAccountLogSrvice.deleteBankOpenAccountLogByUserId(userId);
        response.setBankOpenFlag(checkAccount);
        return response;
    }

    @ApiOperation(value = "通过手机号和身份证查询掉单信息", notes = "通过手机号和身份证查询掉单信息")
    @GetMapping(value = "/selectBankOpenAccountLogByUserId/{userId}")
    public BankOpenAccountLogResponse selectBankOpenAccountLogByUserId(@PathVariable Integer userId){
        BankOpenAccountLogResponse response = new BankOpenAccountLogResponse();
        List<BankOpenAccountLog> bankOpenAccountLogList = bankOpenAccountLogSrvice.selectBankOpenAccountLogByUserId(userId);
        if(!CollectionUtils.isEmpty(bankOpenAccountLogList)){
            List<BankOpenAccountLogVO> bankCardExceptionCustomizeVOList = CommonUtils.convertBeanList(bankOpenAccountLogList,BankOpenAccountLogVO.class);
            response.setResultList(bankCardExceptionCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @ApiOperation(value = "开户掉单，同步保存开户(user)数据", notes = "开户掉单，同步保存开户数据")
    @PostMapping(value = "/updateUser")
    public OpenAccountEnquiryResponse updateUser(@RequestBody OpenAccountEnquiryDefineRequest request){
        OpenAccountEnquiryResponse response = bankOpenAccountLogSrvice.updateUser(request);
        return response;
    }


    /**
     * 保存用户绑定的银行卡
     *
     * @author Zha Daojian
     * @date 2019/3/15 10:09
     * @param bankCardRequest
     * @return int
     **/
    @RequestMapping(value = "/insertUserCard", method = RequestMethod.POST)
    public int insertUserCard(@RequestBody @Valid BankCardRequest bankCardRequest) {
        BankCard bankCard = new BankCard();
        BeanUtils.copyProperties(bankCardRequest, bankCard);
        int cnt =bankOpenAccountLogSrvice.insertUserCard(bankCard);
        return cnt;
    }
}
