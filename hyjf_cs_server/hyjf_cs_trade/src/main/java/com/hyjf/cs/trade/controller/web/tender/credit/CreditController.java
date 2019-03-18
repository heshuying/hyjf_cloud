/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.credit;

import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.credit.MyCreditListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @Description  债转
 * @Author sss
 * @Date 2018/6/29 13:59
 */
@Api(tags = "web端-债转出借")
@RestController
@RequestMapping("/hyjf-web/credit")
public class CreditController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(CreditController.class);
    @Autowired
    private MyCreditListService creditListService;
    @Autowired
    private CommonProducer commonProducer;
    /**
     * 首页 > 账户中心 > 资产管理 > 我要转让列表
     * @param
     * @return
     */
    @ApiOperation(value = "我要债转列表页 获取参数", notes = "首页 > 账户中心 > 资产管理 > 可转让列表")
    @PostMapping(value = "/creditListInit", produces = "application/json; charset=utf-8")
    public WebResult getCreditList(MyCreditListRequest request,
            @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.getCreditListData(request,userId);
        return result;
    }

    /**
     * 首页 > 账户中心 > 资产管理 > 我要转让列表
     * @param
     * @return
     */
    @ApiOperation(value = "我要债转列表页分页查询", notes = "首页 > 账户中心 > 资产管理 > 可转让列表")
    @PostMapping(value = "/creditListData", produces = "application/json; charset=utf-8")
    public WebResult creditListData(@RequestBody MyCreditListQueryRequest request,
                                    @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.getCreditList(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心查询出借可债转详情", notes = "点击列表的转让按钮")
    @PostMapping(value = "/tenderToCreditDetail", produces = "application/json; charset=utf-8")
    public WebResult tenderToCreditDetail(@RequestBody CreditDetailsRequestBean request,
                                    @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.tenderToCreditDetail(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心验证出借人当天是否可以债转 每天三次", notes = "用户中心验证出借人当天是否可以债转")
    @PostMapping(value = "/tenderAbleToCredit", produces = "application/json; charset=utf-8")
    public WebResult tenderAbleToCredit(@RequestBody CreditDetailsRequestBean request,
                                          @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.tenderAbleToCredit(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心检查是否可以债转", notes = "用户中心检查是否可以债转")
    @PostMapping(value = "/checkCanCredit", produces = "application/json; charset=utf-8")
    public WebResult checkCanCredit(@RequestBody CreditDetailsRequestBean request,
                                        @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.checkCanCredit(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心债转提交保存", notes = "用户中心债转提交保存")
    @PostMapping(value = "/saveTenderToCredit", produces = "application/json; charset=utf-8")
    public WebResult saveTenderToCredit(@RequestBody TenderBorrowCreditCustomize request,
                                    @RequestHeader(value = "userId",required = true) Integer userId,HttpServletRequest httpServletRequest){
        request.setPlatform(Integer.parseInt(CommonConstant.CLIENT_PC));
        WebResult result =  creditListService.saveTenderToCredit(request,userId);
        //保存用户日志mq
        BorrowAndInfoVO borrow = creditListService.getBorrowByNid(request.getBorrowNid());
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())|| CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle());

        String dayOrMonth ="";
        String lockPeriod = String.valueOf(borrow.getBorrowPeriod());
        if(isMonth){//月标
            dayOrMonth = lockPeriod + "个月散标";
        }else{
            dayOrMonth = lockPeriod + "天散标";
        }
        UserVO userVO = creditListService.getUserByUserId(userId);
        UserInfoVO userInfoVO = creditListService.getUsersInfoByUserId(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE5);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(httpServletRequest));
        userOperationLogEntity.setPlatform(0);
        userOperationLogEntity.setRemark(dayOrMonth);
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(userVO.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        return result;
    }

    @ApiOperation(value = " 用户中心查询 债转详细预计服务费计算", notes = " 用户中心查询 债转详细预计服务费计算")
    @PostMapping(value = "/exceptCreditFee", produces = "application/json; charset=utf-8")
    public WebResult getExpectCreditFee(@RequestBody TenderBorrowCreditCustomize request,
                                        @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result = creditListService.getExpectCreditFee(request,userId);
        return result;
    }

    @ApiOperation(value = "发送短信验证码（ajax请求） 短信验证码数据保存", notes = "发送短信验证码（ajax请求） 短信验证码数据保存")
    @PostMapping(value = "/sendCode", produces = "application/json; charset=utf-8")
    public WebResult sendCode(@RequestBody TenderBorrowCreditCustomize request,
                                        @RequestHeader(value = "userId",required = false) Integer userId,HttpServletRequest httpRequest){
        request.setIp(GetCilentIP.getIpAddr(httpRequest));
        request.setPlatform(Integer.parseInt(CommonConstant.CLIENT_PC));
        WebResult result = creditListService.sendCreditCode(request,userId);
        return result;
    }

    @ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
    @PostMapping(value = "/checkCode", produces = "application/json; charset=utf-8")
    public WebResult checkCode(@RequestBody TenderBorrowCreditCustomize request,
                              @RequestHeader(value = "userId",required = false) Integer userId){
        request.setPlatform(Integer.parseInt(CommonConstant.CLIENT_PC));
        WebResult result = creditListService.checkCode(request,userId);
        return result;
    }


}
