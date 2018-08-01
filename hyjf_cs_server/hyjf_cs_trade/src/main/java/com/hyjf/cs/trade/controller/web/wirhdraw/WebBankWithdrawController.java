package com.hyjf.cs.trade.controller.web.wirhdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.BankWithdrawService;
import com.hyjf.cs.trade.vo.BankWithdrawVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 */
@Api(tags = "web端用户提现接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/withdraw")
public class WebBankWithdrawController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(WebBankWithdrawController.class);
    @Autowired
    private BankWithdrawService bankWithdrawService;


    /**
     * @Description 跳转到提现页面
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "web端获取用户银行提现", notes = "用户提现")
    @PostMapping("/toWithdraw")
    public WebResult<Object> toWithdraw(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        WebViewUserVO user=bankWithdrawService.getUsersByToken(token);
        if(null==user||0==user.getIsSetPassword()||!user.isBankOpenAccount()){
            return new WebResult<>();
        }
        WebResult<Object> objectWebResult=bankWithdrawService.toWithdraw(user);
        return objectWebResult;
    }


    /**
     * 用户银行提现
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date  用户提现调用银行页面
     */
    @ApiOperation(value = "web端用户银行提现", notes = "用户提现")
    @PostMapping("/userBankWithdraw")
    public WebResult<Object>  userBankWithdraw(@RequestHeader(value = "token", required = true) String token,
                                               @RequestBody @Valid BankWithdrawVO bankWithdrawVO , HttpServletRequest request) {
        logger.info("web端提现接口, token is :{}", JSONObject.toJSONString(token));
        WebResult<Object> result = new WebResult<Object>();
        WebViewUserVO user=bankWithdrawService.getUsersByToken(token);
        UserVO userVO=bankWithdrawService.getUserByUserId(user.getUserId());
        if(null==userVO||0==userVO.getIsSetPassword()||0==userVO.getOpenAccount()||0==userVO.getBankOpenAccount()){
            return result;
        }
        logger.info("user is :{}", JSONObject.toJSONString(user));
        String ip=CustomUtil.getIpAddr(request);
        BankCallBean bean = bankWithdrawService.getUserBankWithdrawView(userVO,bankWithdrawVO.getWithdrawmoney(),
                bankWithdrawVO.getWidCard(),bankWithdrawVO.getPayAllianceCode(),CommonConstant.CLIENT_PC,BankCallConstant.CHANNEL_PC,ip);

        try {
            Map<String,Object> data =  BankCallUtils.callApiMap(bean);
            result.setData(data);
        } catch (Exception e) {
            logger.info("web端提现失败");
            e.printStackTrace();
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        return result;
    }

    /**
     * 用户银行提现异步回调
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "用户银行提现异步回调", notes = "用户银行提现异步回调")
    @PostMapping("/userBankWithdrawBgreturn")
    public String userBankWithdrawBgreturn(HttpServletRequest request,BankCallBean bean) {
        logger.info("[web用户银行提现异步回调开始]");
        logger.info("web端提现银行返回参数, bean is :{}", JSONObject.toJSONString(bean));
        BankCallResult result = new BankCallResult();
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("ip", CustomUtil.getIpAddr(request));
        // 执行提现后处理
        this.bankWithdrawService.handlerAfterCash(bean, params);
        logger.info( "成功");
        result.setStatus(true);
        logger.info("[web用户银行提现异步回调结束]");
        return JSONObject.toJSONString(result, true);
    }

    /**
     * @Description web端查询提现失败原因
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "web端查询提现失败原因", notes = "web端查询提现失败原因")
    @RequestMapping("/seachFiledMess")
    @ResponseBody
    public WebResult<Object> seachUserBankWithdrawErrorMessgae(@RequestParam("logOrdId") String logOrdId) {
        logger.info("查询提现失败原因start,logOrdId:{}", logOrdId);
        WebResult<Object> result = bankWithdrawService.seachUserBankWithdrawErrorMessgae(logOrdId);
        return result;
    }
}
