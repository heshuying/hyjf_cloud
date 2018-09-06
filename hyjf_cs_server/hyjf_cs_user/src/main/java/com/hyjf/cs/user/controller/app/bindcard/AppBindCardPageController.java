package com.hyjf.cs.user.controller.app.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jdk.management.resource.internal.ApproverGroup;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * App端绑卡
 * @author hesy
 * @version AppBindCardController, v0.1 2018/7/19 9:34
 */
@Api(value = "app端-绑卡(页面)",tags = "app端-绑卡（页面）")
@RestController
@RequestMapping("/hyjf-app/bank/user/bindCardPage")
public class AppBindCardPageController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 页面请求绑卡
     * @param request
     */
    @PostMapping("/bindCardPage")
    @ApiOperation(value = "绑卡", notes = "绑卡")
    public AppResult<Object> bindCardPage(HttpServletRequest request, @RequestHeader(value = "userId") Integer userId) {

        AppResult<Object> result = new AppResult<Object>();
        String sign = request.getParameter("sign");
        String token = request.getParameter("token");
        WebViewUserVO webViewUserVO = bindCardService.getWebViewUserByUserId(userId);
        // 检查参数
        String checkResult = bindCardService.checkParamBindCardPageAPP(webViewUserVO);

        if (StringUtils.isNotBlank(checkResult)) {
            logger.info("checkResult is:{}", checkResult);
            result.setStatusInfo(AppResult.FAIL, checkResult);
            return result;
        }

        // 请求银行接口
        try {
            BindCardPageBean bean = new BindCardPageBean();
            bean.setTxCode(BankCallConstant.TXCODE_BIND_CARD_PAGE);
            bean.setChannel(BankCallConstant.CHANNEL_APP);
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(webViewUserVO.getIdcard());
            bean.setName(webViewUserVO.getTruename());
            bean.setAccountId(webViewUserVO.getBankAccount());
            bean.setUserIP(GetCilentIP.getIpAddr(request));
            bean.setUserId(userId);
            // 微官网 1
            bean.setPlatform("1");
            Map<String,Object> map = bindCardService.getCallbankMap(bean,sign,token);
            result.setData(map);
            result.setStatus(BaseResult.SUCCESS);
            logger.info("绑卡调用页面end");

            return result;
        } catch (Exception e) {
            logger.error("调用银行接口失败", e);
            result.setStatusInfo(AppResult.ERROR, "调用银行接口失败");
            return result;
        }

    }

    /**
     * 页面绑卡异步回调
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/notifyReturn")
    @ApiOperation(value = "绑卡异步回调", notes = "绑卡异步回调")
    public BankCallResult bgreturn(HttpServletRequest request,
                                   @ModelAttribute BankCallBean bean) {
        // 上送的异步地址里面有
        BankCallResult result = new BankCallResult();
        String phone = request.getParameter("phone");
        logger.info("页面绑卡异步回调start");
        bean.setMobile(phone);
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());

        // 绑卡后处理
        try {
            boolean checkTender = RedisUtils.tranactionSet("bindCard" + bean.getLogOrderId(), 600);
            if(checkTender){
                // 保存银行卡信息
                bindCardService.updateAfterBindCard(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("页面绑卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
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
    public AppResult<Object> searchFiledMess(@RequestBody Map<String,String> param) {
        logger.info("调用银行失败原因start,logOrdId:{}", param);
        AppResult<Object> result = new AppResult<Object>();
        String retMsg = bindCardService.getFailedMess(param.get("logOrdId"));
        Map<String,String> map = new HashedMap();
        map.put("error",retMsg);
        result.setData(map);
        return result;
    }
}
