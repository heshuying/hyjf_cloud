package com.hyjf.cs.user.service.trusteepay.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.TrusteePayRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.trusteepay.TrusteePayService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class TrusteePayServiceImpl extends BaseUserServiceImpl implements TrusteePayService {

    @Autowired
    SystemConfig systemConfig;

    private static final Logger logger = LoggerFactory.getLogger(TrusteePayServiceImpl.class);

    /**
     * 借款人受托支付申请
     *
     * @param request
     * @param payRequestBean
     * @return
     */
    @Override
    public ModelAndView trusteePayApply(HttpServletRequest request, TrusteePayRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView("api/api_error_send.html");
        logger.info("请求参数" + JSONObject.toJSONString(payRequestBean, true) + "]");
        // 检查参数是否为空
        if (payRequestBean.checkParmIsNull(modelAndView)) {
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 验签
        if (!SignUtil.verifyRequestSign(payRequestBean, BaseDefine.METHOD_SERVER_TRUSTEE_PAY)) {
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(payRequestBean.getAccountId());
        if (bankOpenAccount == null) {
            logger.info("-------------------没有根据电子银行卡找到用户" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }

        // 检查用户是否存在
        UserVO user = amUserClient.findUserById(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000007);
            return modelAndView;
        }

        Integer userId = user.getUserId();
        // 未开户
        if (user.getBankOpenAccount().intValue() != 1) {
            logger.info("-------------------用户未开户！" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006, "用户未开户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000006);
            return modelAndView;
        }

        // 检查收款人电子帐户是否存在
        BankOpenAccountVO receiptAccount = amUserClient.selectBankOpenAccountByAccountId(payRequestBean.getReceiptAccountId());
        if (receiptAccount == null) {
            logger.info("-------------------没有根据电子银行卡找到收款人" + payRequestBean.getReceiptAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }

        // 检查标的是否存在
        RightBorrowVO borrow = amTradeClient.getRightBorrowByNid(payRequestBean.getProductId());
        if (borrow == null) {
            logger.info("-------------------标的不存在！" + payRequestBean.getProductId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_HK000001, "标的不存在！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_HK000001);
            return modelAndView;
        }

        // 检查标的状态 // 待授权状态才可以
        if (!borrow.getStatus().equals(7)) {
            logger.info("-------------------标的状态错误！" + payRequestBean.getProductId() + "！--------------------status" + borrow.getStatus());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TR000001, "标的状态错误！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TR000001);
            return modelAndView;
        }

        // 查询是否在白名单里面
        STZHWhiteListVO whiteList = amTradeClient.getSTZHWhiteList(payRequestBean.getInstCode(), payRequestBean.getReceiptAccountId());
        if (whiteList == null) {
            logger.info("-------------------不在授权白名单里！" + payRequestBean.getAccountId() + "！--------------------status" + borrow.getStatus());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TR000002, "不在授权白名单里！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TR000002);
            return modelAndView;
        }
        // 状态为禁用
        if (whiteList.getState() - 0 == 0) {
            logger.info("-------------------状态为禁用！" + payRequestBean.getAccountId() + "！--------------------status" + borrow.getStatus());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TR000002, "不在授权白名单里！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TR000002);
            return modelAndView;
        }

        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {// 未设置交易密码
            logger.info("-------------------未设置交易密码！" + payRequestBean.getAccountId() + "！--------------------status" + user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TP000002, "未设置交易密码！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TP000002);
            return modelAndView;
        }

        UserInfoVO usersInfo = this.getUserInfo(userId);

        // 证件类型  证件号
        String idType = "";
        String idNo = "";
        // 取用户类型 如果企业用户 上送不同
        if (user.getUserType() == 1) { // 企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = amUserClient.getCorpOpenAccountRecord(userId);
            idType = record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE;// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            idNo = record.getBusiCode();
        } else {
            idType = BankCallConstant.ID_TYPE_IDCARD;
            idNo = usersInfo.getIdcard();
        }

        // 检查用户身份证或者证件是否为空
        if (Validator.isNull(idNo)) {
            logger.info("-------------------用户证件为空！用户类型：" + user.getUserType() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TR000003, "证件号不能为空！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TR000003);
            return modelAndView;
        }

        // 检查证件号是否一致
        if (!payRequestBean.getIdNo().equals(idNo)) {
            logger.info("-------------------证件号校验错误！" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_ZC000021, "身份证号校验错误！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_ZC000021);
            return modelAndView;
        }

        // 同步调用路径 返回的银行类里面没有userId 所以加了一个
        String retUrl = systemConfig.getServerHost()
                + "/hyjf-api/server/trusteePay/trusteePayReturn?acqRes="
                + payRequestBean.getAcqRes()
                + StringPool.AMPERSAND + BankCallConstant.PARAM_PRODUCTID + StringPool.EQUAL + payRequestBean.getProductId()
                + "&userId=" + userId
                + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");

        String successUrl = systemConfig.getServerHost()
                + "/hyjf-api/server/trusteePay/trusteePayReturn?acqRes="
                + payRequestBean.getAcqRes()
                + StringPool.AMPERSAND + BankCallConstant.PARAM_PRODUCTID + StringPool.EQUAL + payRequestBean.getProductId()
                + "&userId=" + userId
                + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-api/server/trusteePay/trusteePayBgreturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");

        // 调用受托支付授权接口
        BankCallBean bean = new BankCallBean();
        // 设置共同参数  共同参数不需要设置 删除
//        setCommonCall(bean);

        bean.setTxCode(BankCallConstant.TXCODE_TRUSTEE_PAY);// 消息类型(用户开户)
        bean.setChannel(payRequestBean.getChannel());
        bean.setIdType(idType);
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
        bean.setProductId(payRequestBean.getProductId()); //标的编号
        bean.setReceiptAccountId(payRequestBean.getReceiptAccountId()); // 收款人电子帐户
        bean.setIdNo(idNo);
        bean.setForgotPwdUrl(payRequestBean.getForgotPwdUrl()); // 忘记密码连接
        /*bean.setAcqRes(bean.getProductId());*/
        /*bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());*/

        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setSuccessfulUrl(successUrl);
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
            logger.error(e.getMessage());
        }
        logger.info("借款人受托支付申请end");
        return modelAndView;
    }

    private ModelAndView getErrorMV(TrusteePayRequestBean payRequestBean, ModelAndView modelAndView, String status) {
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        //为了重定向
        modelAndView.addObject("callBackAction",payRequestBean.getRetUrl());
        modelAndView.addObject("error","true");
        modelAndView.addObject("chkValue", resultBean.getChkValue());
        modelAndView.addObject("status", resultBean.getStatus());
        modelAndView.addObject("acqRes", payRequestBean.getAcqRes());
        return modelAndView;
    }

    /**
     * 借款人受托支付申请同步回调
     *
     * @param request
     * @param bean
     * @return
     */
    @Override
    public Map<String, String> trusteePayReturn(HttpServletRequest request, BankCallBean bean){
        logger.info("借款人受托支付申请同步回调start,请求参数为：【" + JSONObject.toJSONString(bean, true) + "】");
        Map<String, String> resultMap = new HashMap<>();
        String url = request.getParameter("callback").replace("*-*-*", "#");

        String userId = request.getParameter("userId");
        bean.setLogUserId(userId);
        String productId = request.getParameter("productId");
        BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(Integer.parseInt((userId)));
        bean.convert();
        // 调用查询接口 查询是否成功授权
        BankCallBean selectbean = this.queryTrusteePayState(bankOpenAccount.getAccount(), productId, userId);
        // 调用接口
        bean = BankCallUtils.callApiBg(selectbean);

        logger.info("借款人受托支付申请同步回调请求查询接口,返回参数为：【" + JSONObject.toJSONString(bean, true) + "】");

        if (bean != null && ((BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(bean.getState())) || "JX900703".equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
            // 成功
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultMap.put("status", resultBean.getStatus());
            resultMap.put("chkValue", resultBean.getChkValue());
            resultMap.put("statusDesc", "借款人受托支付申请成功");
        } else {
            // 失败
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            resultMap.put("status", resultBean.getStatus());
            resultMap.put("chkValue", resultBean.getChkValue());
            resultMap.put("statusDesc", "借款人受托支付申请失败,失败原因：" + bean != null? this.getBankRetMsg(bean.getRetCode()) : "调用接口返回值为空");
        }

        //------------------------------------------------
        resultMap.put("callBackAction", url);
        resultMap.put("acqRes", request.getParameter("acqRes"));
        logger.info("借款人受托支付申请同步回调end");
        return resultMap;
    }

    /**
     * 借款人受托支付申请异步回调
     *
     * @param request
     * @param bean
     * @return
     */
    @Override
    public BankCallResult trusteePayBgreturn(HttpServletRequest request, BankCallBean bean){
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
        UserVO user = this.getUsersById(userId);
        state = bean == null ? "" : bean.getState();
        // JX900703 受托支付已受理
        if (user != null && bean != null && ((BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(bean.getState())) || "JX900703".equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
            // 修改对应数据
            boolean ok = amTradeClient.updateTrusteePaySuccess(bean.getProductId());
            if (ok) {
                message = "借款人受托支付申请成功";
                state = "1";
                status = ErrorCodeConstant.SUCCESS;
            } else {
                message = "借款人受托支付申请失败";
                state = "0";
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        } else {
            // 失败
            message = "借款人受托支付申请失败,失败原因：" + this.getBankRetMsg(bean.getRetCode());
            state = "0";
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        params.put("accountId", bean.getAccountId());
        params.put("productId", bean.getProductId());
        params.put("status", status);
        params.put("state", state);
        params.put("statusDesc", message);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes", request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);

        result.setMessage("借款人受托支付申请成功");
        result.setStatus(true);
        logger.info("借款人受托支付申请异步回调end");
        return result;
    }

    /**
     * 受托支付申请状态查询
     *
     * @param payRequestBean
     * @return
     */
    @Override
    public JSONObject trusteePayQuery(TrusteePayRequestBean payRequestBean){
        logger.info("受托支付申请查询接口 请求参数：" + JSONObject.toJSONString(payRequestBean, true) + "]");
        JSONObject result = new JSONObject();
        // 检查参数是否为空
        if (payRequestBean.checkParmQueryIsNull()) {
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_CE000001, "请求参数异常");
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            return result;
        }

        // 验签
        if (!SignUtil.verifyRequestSign(payRequestBean, "/server/trusteePay/trusteePayQuery")) {
            logger.info("验签失败" + JSONObject.toJSONString(payRequestBean, true) + "]");
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_CE000002, "验签失败");
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            return result;
        }

        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(payRequestBean.getAccountId());
        if (bankOpenAccount == null) {
            logger.info("-------------------没有根据电子银行卡找到用户" + payRequestBean.getAccountId() + "！--------------------");
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户"));
            return result;
        }

        // 检查用户是否存在
        UserVO user = this.getUsersById(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！" + payRequestBean.getAccountId() + "！--------------------");
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户！");
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户！"));
            return result;
        }

        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            logger.info("-------------------用户未开户！" + payRequestBean.getAccountId() + "！--------------------");
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_CE000006, "用户未开户！");
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006, "用户未开户！"));
            return result;
        }

        // 检查标的是否存在
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByBorrowNid(payRequestBean.getProductId());
        if (borrow == null) {
            logger.info("-------------------标的不存在！" + payRequestBean.getAccountId() + "！--------------------");
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_HK000001, "标的不存在！");
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_HK000001, "标的不存在！"));
            return result;
        }

        // 调用查询接口 查询是否成功授权
        BankCallBean selectbean = this.queryTrusteePayState(payRequestBean.getAccountId(), payRequestBean.getProductId(), String.valueOf(bankOpenAccount.getUserId()));

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
            result = payRequestBean.getErrorJson(ErrorCodeConstant.STATUS_CE999999, "查询失败！");
        }
        logger.info("受托支付申请查询接口 end");
        return result;
    }

    /**
     * 查询是否成功授权
     * @param accountId
     * @param productId
     * @param userid
     * @return
     */
    private BankCallBean queryTrusteePayState(String accountId, String productId, String userid) {
        BankCallBean selectbean = new BankCallBean();
        // 设置共通参数
//        setCommonCall(selectbean);
        selectbean.setTxCode(BankCallConstant.TXCODE_TRUSTEE_PAY_QUERY);
        selectbean.setAccountId(accountId);// 电子账号
        selectbean.setProductId(productId); // 标的编号

        // 操作者ID
        selectbean.setLogUserId(userid);
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userid)));
        selectbean.setLogClient(0);
        selectbean.setLogRemark("受托支付申请查询");
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        return selectbean;
    }
}
