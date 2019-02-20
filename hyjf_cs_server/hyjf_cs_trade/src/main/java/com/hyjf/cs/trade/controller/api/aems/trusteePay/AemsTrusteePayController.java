package com.hyjf.cs.trade.controller.api.aems.trusteePay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.AemsTrusteePayRequestBean;
import com.hyjf.cs.trade.bean.AemsTrusteePayResultBean;
import com.hyjf.cs.trade.bean.BaseDefine;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.aems.trusteePay.AemsTrusteePayService;
import com.hyjf.cs.trade.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(value = "api端-AEMS借款人受托支付申请",tags = "api端-AEMS借款人受托支付申请")
@RestController
@RequestMapping("/hyjf-api/aems/trusteePay")
public class AemsTrusteePayController extends BaseController {

    /**
     * 请求失败页面路径 /bank/user/trusteePay/error
     */
    public static final String PATH_TRUSTEE_PAY_ERROR = "/bank/user/trusteePay/error";

    /**标的状态7 */
    public static final Integer BORROW_STATUS_WITE_AUTHORIZATION = 7;

    @Autowired
    private AemsTrusteePayService trusteePayService;

    @Autowired
    private SystemConfig systemConfig;
    
    /***
    * @author Zha Daojian
    * @date 2018/9/13 13:44
    * @param request, payRequestBean
    * @return org.springframework.web.servlet.ModelAndView
    **/
    @ApiOperation(value = "AEMS借款人受托支付申请", notes = "AEMS借款人受托支付申请")
    @PostMapping(value = "/pay", produces = "application/json; charset=utf-8")
    public ModelAndView trusteePay(HttpServletRequest request, @RequestBody AemsTrusteePayRequestBean payRequestBean){
        ModelAndView modelAndView = new ModelAndView(PATH_TRUSTEE_PAY_ERROR);
        logger.info("请求参数" + JSONObject.toJSONString(payRequestBean, true) + "]");
        // 检查参数是否为空
        if(payRequestBean.checkParmIsNull()){
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_CE000001);
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 验签 借款人受托支付申请
       if (!SignUtil.AEMSVerifyRequestSign(payRequestBean, "/aems/trusteePay/pay")) {
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_CE000002);
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000002, "验签失败"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = this.trusteePayService.selectBankOpenAccountByAccountId(payRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000004,"没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }

        // 检查用户是否存在
        UserVO user = trusteePayService.getUsers(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000007,"用户不存在汇盈金服账户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_CE000007);
            return modelAndView;
        }

        Integer userId = user.getUserId();
        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            logger.info("-------------------用户未开户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000006,"用户未开户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_CE000006);
            return modelAndView;
        }

        // 检查收款人电子帐户是否存在
        BankOpenAccountVO receiptAccount = this.trusteePayService.selectBankOpenAccountByAccountId(payRequestBean.getReceiptAccountId());
        if(receiptAccount == null){
            logger.info("-------------------没有根据电子银行卡找到收款人"+payRequestBean.getReceiptAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000004,"没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }

        // 检查标的是否存在
        BorrowAndInfoVO borrow = this.trusteePayService.selectBorrowByProductId(payRequestBean.getProductId());
        if(borrow==null){
            logger.info("-------------------标的不存在！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_HK000001,"标的不存在！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_HK000001);
            return modelAndView;
        }

        // 检查标的状态 // 待授权状态才可以
        if(!borrow.getStatus().equals(BORROW_STATUS_WITE_AUTHORIZATION)){
            logger.info("-------------------标的状态错误！"+payRequestBean.getAccountId()+"！--------------------status"+borrow.getStatus());
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_TR000001,"标的状态错误！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_TR000001);
            return modelAndView;
        }

        // 查询是否在白名单里面
        STZHWhiteListVO whiteList = trusteePayService.getSTZHWhiteListByCode(payRequestBean.getInstCode(),payRequestBean.getReceiptAccountId());
        if(whiteList==null){
            logger.info("-------------------不在授权白名单里！"+payRequestBean.getAccountId()+"！--------------------status"+borrow.getStatus());
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_TR000002,"不在授权白名单里！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_TR000002);
            return modelAndView;
        }
        // 状态为禁用
        if(whiteList.getState()-0==0){
            logger.info("-------------------状态为禁用！"+payRequestBean.getAccountId()+"！--------------------status"+borrow.getStatus());
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_TR000002,"不在授权白名单里！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_TR000002);
            return modelAndView;
        }

        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {// 未设置交易密码
            logger.info("-------------------未设置交易密码！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_TP000002,"未设置交易密码！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_TP000002);
            return modelAndView;
        }

        UserInfoVO usersInfo = trusteePayService.getUsersInfoByUserId(userId);

        // 证件类型  证件号
        String idType = "";
        String idNo = "";
        // 取用户类型 如果企业用户 上送不同
        if (user.getUserType() == 1) { // 企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = trusteePayService.getCorpOpenAccountRecord(userId);
            idType = record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE;// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            idNo = record.getBusiCode();
        }else{
            idType = BankCallConstant.ID_TYPE_IDCARD;
            idNo = usersInfo.getIdcard();
        }

        // 检查用户身份证或者证件是否为空
        if (Validator.isNull(idNo)) {
            logger.info("-------------------用户证件为空！用户类型："+user.getUserType()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_TR000003,"证件号不能为空！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_TR000003);
            return modelAndView;
        }

        // 检查证件号是否一致
        if(!payRequestBean.getIdNo().equals(idNo)){
            logger.info("-------------------证件号校验错误！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_ZC000021,"身份证号校验错误！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView,AemsErrorCodeConstant.STATUS_ZC000021);
            return modelAndView;
        }

        // 同步调用路径
        String retUrl =systemConfig.getAppFrontHost() + request.getContextPath()
                +  "/aems/trusteePay/trusteePayReturn?acqRes="
                + payRequestBean.getAcqRes()
                + StringPool.AMPERSAND + BankCallConstant.PARAM_PRODUCTID + StringPool.EQUAL + payRequestBean.getProductId()
                + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = "http://CS-TRADE/hyjf-api/aems/trusteePay/trusteePayBgreturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        // 调用受托支付授权接口
        BankCallBean bean = new BankCallBean();
        // 设置共同参数
        setCommonCall(bean);

        bean.setTxCode(BankCallConstant.TXCODE_TRUSTEE_PAY);// 消息类型(用户开户)
        bean.setChannel(payRequestBean.getChannel());
        bean.setIdType(idType);
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
        bean.setProductId(payRequestBean.getProductId()); //标的编号
        bean.setReceiptAccountId(payRequestBean.getReceiptAccountId()); // 收款人电子帐户
        bean.setIdNo(idNo);
        bean.setForgotPwdUrl(payRequestBean.getForgotPwdUrl()); // 忘记密码连接

        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_TRUSTEE_PAY);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogRemark("借款人受托支付申请");
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());

        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("借款人受托支付申请end");
        return modelAndView;
    }

    private ModelAndView getErrorMV(AemsTrusteePayRequestBean payRequestBean, ModelAndView modelAndView, String status) {
        AemsTrusteePayResultBean repwdResult = new AemsTrusteePayResultBean();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        repwdResult.setCallBackAction(payRequestBean.getRetUrl());
        repwdResult.set("chkValue", resultBean.getChkValue());
        repwdResult.set("status", resultBean.getStatus());
        repwdResult.set("acqRes",payRequestBean.getAcqRes());
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;
    }

    // 同步回调
    @RequestMapping("/trusteePayReturn")
    public ModelAndView passwordReturn(HttpServletRequest request, HttpServletResponse response,
                                       BankCallBean bean) {
        logger.info("借款人受托支付申请同步回调start,请求参数为：【"+JSONObject.toJSONString(bean, true)+"】");
        ModelAndView modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRUSTEEPAY_VIEW);
        AemsTrusteePayResultBean repwdResult = new AemsTrusteePayResultBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        
        int userId = Integer.parseInt(bean.getLogUserId());
        String productId = request.getParameter("productId");
        BankOpenAccountVO bankOpenAccount = this.trusteePayService.getBankOpenAccount(userId);
        bean.convert();
        // 调用查询接口 查询是否成功授权
        BankCallBean selectbean = trusteePayService.queryTrusteePayState(bankOpenAccount.getAccount(),productId,bean.getLogUserId() );
        // 调用接口
        bean = BankCallUtils.callApiBg(selectbean);
        
        logger.info("借款人受托支付申请同步回调请求查询接口,返回参数为：【"+JSONObject.toJSONString(bean, true)+"】");
        
        if (bean != null && bean!=null && ((BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(bean.getState())) || "JX900703".equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
            // 成功
            modelAndView.addObject("statusDesc", "借款人受托支付申请成功！");
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        } else {
            // 失败
            modelAndView.addObject("statusDesc", "借款人受托支付申请失败,失败原因：" + trusteePayService.getBankRetMsg(bean.getRetCode()));
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        
        //------------------------------------------------
        repwdResult.set("acqRes",request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("借款人受托支付申请同步回调end");
        return modelAndView;
    }

    /**
     * 
     * 设置共通参数
     * @author sss
     * @param selectbean
     */
    private void setCommonCall(BankCallBean selectbean) {
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        selectbean.setBankCode(systemConfig.getBankBankcode());// 银行代码
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
        selectbean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
    }

    // 异步回调
    @ResponseBody
    @RequestMapping(value = "/trusteePayBgReturn",method = RequestMethod.POST)
    public BankCallResult passwordBgreturn(HttpServletRequest request, HttpServletResponse response,
                                           @ModelAttribute BankCallBean bean) {
        logger.info("借款人受托支付申请异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        String state = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = this.trusteePayService.getUsers(userId);
        state = bean==null?"":bean.getState();
        // JX900703 受托支付已受理
        if (user != null && bean!=null && ((BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(bean.getState())) || "JX900703".equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
            try {
                // 修改对应数据
                // 修改huiyingdai_borrow表状态  根据返回值productId对应表的id   status改为2
                // 修改hyjf_hjh_plan_asset status改为
                boolean ok = trusteePayService.updateTrusteePaySuccess(bean);
                if (ok) {
                    message = "借款人受托支付申请成功";
                    state = "1";
                    status = AemsErrorCodeConstant.SUCCESS;
                } else {
                    message = "借款人受托支付申请失败";
                    state = "0";
                    status = AemsErrorCodeConstant.STATUS_CE999999;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("借款人受托支付申请出错,标的编号:【"+bean.getProductId()+"】错误原因："+e.getMessage());
                message = "借款人受托支付申请失败";
                state = "0";
                status = AemsErrorCodeConstant.STATUS_CE999999;
            }
            
        }else{
         // 失败
            message = "借款人受托支付申请失败,失败原因：" + trusteePayService.getBankRetMsg(bean.getRetCode());
            state = "0";
            status = AemsErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        
        params.put("accountId", bean.getAccountId());
        params.put("productId", bean.getProductId());
        params.put("status", status);
        params.put("state", state);
        params.put("statusDesc",message);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        
        logger.info("借款人受托支付申请异步回调end");
        result.setMessage("借款人受托支付申请成功");
        result.setStatus(true);
        // 返回值  9-22修改 end
        logger.info("借款人受托支付申请异步回调end");
        return result;
    }
    

    @ApiOperation(value = "AEMS借款人受托支付申请,查询接口", notes = "AEMS借款人受托支付申请，查询接口")
    @PostMapping(value = "/trusteePayQuery", produces = "application/json; charset=utf-8")
    public JSONObject trusteePayQuery(HttpServletRequest request, @RequestBody AemsTrusteePayRequestBean payRequestBean , HttpServletResponse response) {
        logger.info("受托支付申请查询接口 请求参数：" + JSONObject.toJSONString(payRequestBean, true) + "]");
        JSONObject result = new JSONObject();
        // 检查参数是否为空
        if(payRequestBean.checkParmQueryIsNull()){
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            result = payRequestBean.getErrorJson(AemsErrorCodeConstant.STATUS_CE000001, "请求参数异常");
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            return result;
        }

        // 验签
        if (!SignUtil.AEMSVerifyRequestSign(payRequestBean, "/aems/trusteePay/trusteePayQuery")) {
            logger.info("验签失败" + JSONObject.toJSONString(payRequestBean, true) + "]");
            result = payRequestBean.getErrorJson(AemsErrorCodeConstant.STATUS_CE000002, "验签失败");
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000002, "验签失败"));
            return result;
        }

        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = this.trusteePayService.selectBankOpenAccountByAccountId(payRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+payRequestBean.getAccountId()+"！--------------------");
            result = payRequestBean.getErrorJson(AemsErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户"));
            return result;
        }
        
        // 检查用户是否存在
        UserVO user = trusteePayService.getUsers(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！"+payRequestBean.getAccountId()+"！--------------------");
            result = payRequestBean.getErrorJson(AemsErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户！");
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户！"));
            return result;
        }
        
        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            logger.info("-------------------用户未开户！"+payRequestBean.getAccountId()+"！--------------------");
            result = payRequestBean.getErrorJson(AemsErrorCodeConstant.STATUS_CE000006, "用户未开户！");
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_CE000006, "用户未开户！"));
            return result;
        }
        
        // 检查标的是否存在
        BorrowAndInfoVO borrow = this.trusteePayService.selectBorrowByProductId(payRequestBean.getProductId());
        if(borrow==null){
            logger.info("-------------------标的不存在！"+payRequestBean.getAccountId()+"！--------------------");
            result = payRequestBean.getErrorJson(AemsErrorCodeConstant.STATUS_HK000001, "标的不存在！");
            payRequestBean.doNotify(payRequestBean.getErrorMap(AemsErrorCodeConstant.STATUS_HK000001, "标的不存在！"));
            return result;
        }
        
        // 调用查询接口 查询是否成功授权
        BankCallBean selectbean = trusteePayService.queryTrusteePayState(payRequestBean.getAccountId(),payRequestBean.getProductId(),String.valueOf(bankOpenAccount.getUserId()) );
        
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        
        if (retBean != null && ((BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(retBean.getState())) || "JX900703".equals(retBean.get(BankCallConstant.PARAM_RETCODE)))) {
            // 成功
        	payRequestBean.setState("1");
            result = payRequestBean.getSuccessJson("查询成功！");
        } else {
            // 失败
        	payRequestBean.setState("0");
            result = payRequestBean.getErrorJson(retBean.getRetCode(), "查询失败！");
            //payRequestBean.doNotify(payRequestBean.getErrorMap(AEMSErrorCodeConstant.STATUS_CE999999, "查询失败！"));
        }
        logger.info("受托支付申请查询接口 end,银行返回应答码retCode="+retBean.getRetCode());
        return result;
    }
}
