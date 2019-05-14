/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.tender.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.invest.BorrowTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * wechat端-散标出借
 */
@Api(tags = "weChat端-散标出借")
@RestController
@RequestMapping("/hyjf-wechat/wx/bank/wechat/borrow")
public class WechatBorrowTenderController extends BaseTradeController {

    @Autowired
    private BorrowTenderService borrowTenderService;
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private HjhTenderService hjhTenderService;

    @ApiOperation(value = "散标出借", notes = "散标出借")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WeChatResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId") Integer userId,  @RequestHeader(value = "wjtClient",required = false) String wjtClient,@RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("wechat端-请求出借接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        tender.setUserId(userId);
        tender.setSign(request.getParameter("sign"));
        tender.setWjtClient(wjtClient);
        logger.info("sign:{}",request.getParameter("sign"));
        WebResult<Map<String,Object>> result = null;
        WeChatResult weChatResult = new WeChatResult();
        try{
            //如果是风车理财出借就将wrb插入到该表的tender_from中
            Cookie cookie = CookieUtils.getCookieByName(request,CustomConstants.TENDER_FROM_TAG);
            if (cookie != null && cookie.getValue() != null){
                String cookieValue = cookie.getValue();
                logger.info("出借来源为 ：{}", cookieValue);
                tender.setTenderFrom(cookieValue);
            }
            result =  borrowTenderService.borrowTender(tender);
            BorrowAndInfoVO borrow = borrowTenderService.getBorrowByNid(tender.getBorrowNid());
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())|| CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle());

            String dayOrMonth ="";
            String lockPeriod = String.valueOf(borrow.getBorrowPeriod());
            if(isMonth){//月标
                dayOrMonth = lockPeriod + "个月散标";
            }else{
                dayOrMonth = lockPeriod + "天散标";
            }
            UserVO userVO = borrowTenderService.getUsers(userId);
            UserInfoVO usersInfo = borrowTenderService.getUsersInfoByUserId(userId);
            UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE4);
            userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
            userOperationLogEntity.setPlatform(1);
            userOperationLogEntity.setRemark(dayOrMonth);
            userOperationLogEntity.setOperationTime(new Date());
            userOperationLogEntity.setUserName(userVO.getUsername());
            userOperationLogEntity.setUserRole(String.valueOf(usersInfo.getRoleId()));
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
            } catch (MQException e) {
                logger.error("保存用户日志失败", e);
            }
            weChatResult.setStatus(result.getStatus());
            //用户测评校验状态转换
            if(result.getData()!=null){
                if(result.getData().get("riskTested") != null && result.getData().get("riskTested") != ""){
                    //if(!CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL.equals(result.getData().get("riskTested"))){
                        weChatResult.setStatus((String) result.getData().get("riskTested"));
                        weChatResult.setStatusDesc((String) result.getData().get("message"));
                    //}
                }
            }
            weChatResult.setData(result.getData());
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + tender.getUser().getUserId());
        }
        return weChatResult;
    }

    /**
     * wechat端-散标异步处理
     * @param bean
     * @param couponGrantId
     * @return
     */
    @ApiIgnore
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowTenderBgReturn(BankCallBean bean , @RequestParam("couponGrantId") String couponGrantId) {
        logger.info("wechat端-散标出借异步处理start,userId:{}", bean.getLogUserId());
        BankCallResult result ;
        try{
            bean.setLogClient(ClientConstants.WECHAT_CLIENT);
            result = borrowTenderService.borrowTenderBgReturn(bean,couponGrantId);
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + bean.getLogUserId());
        }
        return result;
    }

    @ApiOperation(value = "散标出借获取出借结果", notes = "散标出借获取出借结果")
    @PostMapping(value = "/getBorrowTenderResult", produces = "application/json; charset=utf-8")
    public WeChatResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId") Integer userId,
                                                               @RequestParam String logOrdId,
                                                               @RequestParam String borrowNid) {
        logger.info("wechat端-请求获取出借结果接口，logOrdId{}",logOrdId);
        WebResult<Map<String,Object>> result = borrowTenderService.getBorrowTenderResult(userId,logOrdId,borrowNid);
        WeChatResult weChatResult = new WeChatResult();
        Map<String,Object>  resultMap = result.getData();
        if(resultMap!=null&&resultMap.containsKey("appIncome")){
            // 如果是代金券 并且是app
            resultMap.remove("income");
            resultMap.put("income",resultMap.get("appIncome"));
        }
        weChatResult.setObject(resultMap);
        return  weChatResult;
    }

    @ApiOperation(value = "散标出借获取投标成功结果", notes = "散标出借获取投标成功结果")
    @PostMapping(value = "/getBorrowTenderResultSuccess", produces = "application/json; charset=utf-8")
    public WeChatResult<Map<String, Object>> getBorrowTenderResultSuccess(@RequestHeader(value = "userId") Integer userId,
                                                                       @RequestParam String logOrdId,
                                                                       @RequestParam Integer couponGrantId,
                                                                       @RequestParam String borrowNid,
                                                                       @RequestParam String isPrincipal,
                                                                       @RequestParam String account) {
        logger.info("wechat端-散标出借获取投标成功结果，logOrdId{}", logOrdId);
        WebResult<Map<String,Object>> result = borrowTenderService.getBorrowTenderResultSuccess(userId, logOrdId, borrowNid, couponGrantId,isPrincipal,account);
        WeChatResult weChatResult = new WeChatResult();
        weChatResult.setObject(result.getData());
        return  weChatResult;
    }

    @ApiOperation(value = "获取出借信息", notes = "获取出借信息")
    @GetMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
    public WeChatResult getInvestInfo(@RequestHeader(value = "userId") Integer userId, TenderRequest tender, HttpServletRequest request) {
        logger.info("wechat端-获取出借信息 userid:{}" , userId);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        WeChatResult result = new WeChatResult();
        if(tender.getCouponId()!=null&&!"".equals(tender.getCouponId())){
            tender.setCouponGrantId(Integer.parseInt(tender.getCouponId()));
        }
        result.setData(borrowTenderService.getInvestInfoWeChat(tender));
        return result;
    }

}
