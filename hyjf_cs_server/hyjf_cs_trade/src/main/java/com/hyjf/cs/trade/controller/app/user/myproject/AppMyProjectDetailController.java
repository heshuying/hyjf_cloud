package com.hyjf.cs.trade.controller.app.user.myproject;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaContractEssenceMessageService;
import com.hyjf.cs.trade.service.myproject.AppMyProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * 我的散标详情
 * @author zhangyk
 * @date 2018/8/1 10:05
 */
@Api(value = "app端-用户我的散标详情",tags = "app端-用户我的散标详情")
@RestController
@RequestMapping("/hyjf-app/user/borrow")
public class AppMyProjectDetailController extends BaseTradeController {

    @Autowired
    private AppMyProjectService appMyProjectService;
    @Autowired
    NifaContractEssenceMessageService nifaContractEssenceMessageService;
    @Autowired
    private CommonProducer commonProducer;
    /**
     * 用户中心我的散标详情
     * @date 2018/7/2 16:27
     * 原接口：com.hyjf.app.user.credit.AppTenderCreditBorrowController.searchTenderCreditDetail()
     */
    @ApiOperation(value = "App端:获取我的散标详情", notes = "App端:获取我的散标详情")
    @GetMapping(value = "/{borrowId}", produces = "application/json; charset=utf-8")
    public Object MyProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader(value = "userId") String userId){
       JSONObject result = appMyProjectService.getMyProjectDetail(borrowId,request,userId);
       return result;
    }
    
    /**
     * @author libin
     * App端:发送短信验证码(ajax请求)短信验证码数据保存(取自web)
     */
    @ApiOperation(value = "App端:发送短信验证码(ajax请求)短信验证码数据保存", notes = "App端:发送短信验证码(ajax请求)短信验证码数据保存")
    @PostMapping(value = "/sendcode", produces = "application/json; charset=utf-8")
    public AppResult sendCode(HttpServletRequest request,@RequestHeader(value = "userId",required = false) Integer userId){
    	AppResult result = appMyProjectService.sendCreditCode(request,userId);
    	return result;
    }
    
    /**
     * @author libin
     * App端:用户中心债转提交保存(取自web)
     */
    @ApiOperation(value = "App端:用户中心债转提交保存", notes = "App端:用户中心债转提交保存")
    @PostMapping(value = "/saveTenderToCredit", produces = "application/json; charset=utf-8")
    public JSONObject saveTenderToCredit(@ModelAttribute TenderBorrowCreditCustomize request,@RequestHeader(value = "userId",required = true) Integer userId,HttpServletRequest httpServletRequest){
    	String platform = httpServletRequest.getParameter("platform");
        JSONObject  result =  appMyProjectService.saveTenderToCredit(request,userId);
        //保存用户日志mq
        BorrowAndInfoVO borrow = this.nifaContractEssenceMessageService.selectBorrowByBorrowNid(request.getBorrowNid());
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())|| CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle());

        String dayOrMonth ="";
        String lockPeriod = String.valueOf(borrow.getBorrowPeriod());
        if(isMonth){//月标
            dayOrMonth = lockPeriod + "个月散标";
        }else{
            dayOrMonth = lockPeriod + "天散标";
        }
        UserVO userVO = appMyProjectService.getUserByUserId(userId);
        UserInfoVO userInfoVO = appMyProjectService.getUsersInfoByUserId(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE5);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(httpServletRequest));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
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

    /**
     * 已持有债权列表去转让接口
     * 原接口：com.hyjf.app.user.credit.AppTenderCreditBorrowController.searchTenderToCreditDetail()
     * @author zhangyk
     * @date 2018/9/12 13:53
     */
    @ApiOperation(value = "App端：已持有列表前往债转接口" , notes = "App端：已持有列表前往债转接口")
    @GetMapping(value = "/transfer/setting", produces = "application/json; charset=utf-8")
    public Object tenderToCreditDetail(HttpServletRequest request, @RequestHeader(value = "userId") Integer userId){
        JSONObject result = appMyProjectService.tenderToCreditDetail( request, userId);
        return result;
    }
}
