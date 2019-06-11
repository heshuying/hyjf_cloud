package com.hyjf.cs.user.controller.web.unbindcard;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.DeleteCardPageBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.bindcard.WebBindCardPageController;
import com.hyjf.cs.user.service.unbindcard.UnBindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 合规四期-解绑银行卡
 * @author nxl
 * @version WebUnBindCardPageController, v0.1 2018/10/15 14:26
 */
@Api(value = "web端-用户解绑卡接口(页面调用)",tags = "web端-用户解绑卡接口(页面调用)")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user/deleteCardPage")
public class WebUnBindCardPageController extends BaseUserController{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WebBindCardPageController.class);

    @Autowired
    UnBindCardService unBindCardService;

    /**
     * 绑卡接口
     */
    @ApiOperation(value = "解绑银行卡接口页面", notes = "解绑银行卡接口页面")
    @PostMapping(value = "/deleteCardPage", produces = "application/json; charset=utf-8")
    public WebResult<Object> bindCardPage(@RequestHeader(value = "userId") int userId,
                                          @RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                          @RequestParam(value = "cardId") String cardId, HttpServletRequest request) {
        logger.info("=======解绑银行卡开始,参数为:user Id = "+userId+"& cardId = "+cardId+"===============");
        // 银行卡id
//        String cardId = param.get("cardId");
        WebResult<Object> result = new WebResult<>();
        WebViewUserVO user = unBindCardService.getUserFromCache(userId);
        // 取得用户在汇付天下的客户号
        BankOpenAccountVO accountChinapnrTender = unBindCardService.getBankOpenAccount(userId);
        // 根据用户id查找账户信息管理
        AccountVO accountVO = unBindCardService.getAccountByUserId(userId);
        // 根据银行卡Id获取用户的银行卡信息
        BankCardVO bankCardVO = unBindCardService.getBankCardByUserAndId(userId,cardId);
        // 条件校验
        unBindCardService.checkParamUnBindCardPage(user,accountChinapnrTender,accountVO,bankCardVO);
        //获取用户info信息
        UserInfoVO userInfoVO = unBindCardService.getUserInfo(user.getUserId());
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-web/user/deleteCardPage/bgReturn?userId=" + user.getUserId();

        DeleteCardPageBean deleteCardPageBean = new DeleteCardPageBean();
        deleteCardPageBean.setUserId(user.getUserId());
        deleteCardPageBean.setAccountId(accountChinapnrTender.getAccount());
        deleteCardPageBean.setName(userInfoVO.getTruename());
        deleteCardPageBean.setIdNo(userInfoVO.getIdcard());
        deleteCardPageBean.setCardNo(bankCardVO.getCardNo());// 银行卡号
        deleteCardPageBean.setNotifyUrl(bgRetUrl);
        deleteCardPageBean.setMobile(user.getMobile());
        deleteCardPageBean.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));

        //调用解绑银行卡接口
        Map<String,Object> data = unBindCardService.callUnBindCardPage(deleteCardPageBean,BankCallConstant.CHANNEL_PC,null,request,wjtClient);
        result.setData(data);
        return result;
    }

    /**
     * 绑卡异步回调
     */
    @ApiOperation(value = "绑卡接口回调", notes = "绑卡接口回调")
    @PostMapping(value = "/bgReturn")
    @ResponseBody
    public BankCallResult bindCardBgReturn(@RequestBody BankCallBean bean, HttpServletRequest request) {

        BankCallResult result = new BankCallResult();
        logger.info("页面解卡异步回调start");
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        // 绑卡后处理
        try {
            if(BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())){
                logger.info("删除银行卡成功");
                // 删除银行卡信息
                unBindCardService.updateAfterUnBindCard(bean, userId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("页面解卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
        result.setStatus(true);
        return result;
    }

     /**
     * @Description 调用银行失败原因
     * @Author
     */
    @ApiOperation(value = "调用银行失败原因", notes = "查询调用银行失败原因")
    @PostMapping("/searchFiledMess")
    @ApiImplicitParam(name = "param",value = "{logOrdId:String}",dataType = "Map")
    @ResponseBody
    public WebResult<Object> searchFiledMess(@RequestBody String logOrdId) {
        logger.info("解绑银行卡调用银行失败原因start,logOrdId:{}", logOrdId);
        WebResult<Object> result = new WebResult<Object>();
        String retMsg = unBindCardService.getFailedMess(logOrdId);
        Map<String,String> map = new HashMap<>();
        map.put("error",retMsg);
        result.setData(map);
        return result;
    }


}
