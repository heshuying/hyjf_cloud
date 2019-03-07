package com.hyjf.cs.user.controller.api.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 绑卡相关接口
 * @author hesy
 * @version ApiBindCardController, v0.1 2018/8/27 15:39
 */
@Api(value = "api端-绑卡(接口)", tags = "api端-绑卡（接口）")
@RestController
@RequestMapping("/hyjf-api/server/user/bankcard")
public class ApiBindCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户绑卡增强发送验证码接口
     * @param bankCardRequestBean
     * @return
     */
    @PostMapping("/sendPlusCode.do")
    public BaseResultBean sendPlusCode(@RequestBody ThirdPartyBankCardRequestBean bankCardRequestBean) {
        logger.info(bankCardRequestBean.getMobile()+"用户绑卡增强发送验证码接口-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(bankCardRequestBean));
        ThirdPartyBankCardPlusResultBean ret = new ThirdPartyBankCardPlusResultBean();

        if (Validator.isNull(bankCardRequestBean.getAccountId())||
                Validator.isNull(bankCardRequestBean.getMobile())||
                Validator.isNull(bankCardRequestBean.getCardNo())||
                Validator.isNull(bankCardRequestBean.getInstCode())) {

            logger.info("-------------------请求参数非法--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000001);
            ret.setStatusDesc("请求参数非法");
            return ret;
        }

        //验签
        if(!bindCardService.verifyRequestSign(bankCardRequestBean, "/server/user/bankcard/sendPlusCode")){
            logger.info("-------------------验签失败！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000002);
            ret.setStatusDesc("验签失败");
            return ret;
        }


        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = bindCardService.getBankOpenAccountByAccount(bankCardRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+bankCardRequestBean.getAccountId()+"！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000004);
            ret.setStatusDesc("没有根据电子银行卡找到用户");
            return ret;
        }
        UserVO user = bindCardService.getUsersById(bankOpenAccount.getUserId());
        if(user == null){
            logger.info("---用户不存在汇盈金服账户---");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000006);
            ret.setStatusDesc("用户不存在汇盈金服账户");
            return ret;
        }
        if (user.getBankOpenAccount()==0) {
            logger.info("-------------------没有根据电子银行卡找到用户"+bankCardRequestBean.getAccountId()+"！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000004);
            ret.setStatusDesc("没有根据电子银行卡找到用户");
            return ret;
        }


        // 请求发送短信验证码
        BankCallBean bean = bindCardService.callSendCode(bankOpenAccount.getUserId(),user.getMobile(), BankCallMethodConstant.TXCODE_CARD_BIND_PLUS, ClientConstants.CHANNEL_PC,bankCardRequestBean.getCardNo());
        if (bean == null) {
            logger.info("-------------------发送短信验证码异常！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE999999);
            ret.setStatusDesc("发送短信验证码异常");
            return ret;
        }
        // 返回失败
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            if("JX900651".equals(bean.getRetCode())){
                // 成功返回业务授权码
                ret.setStatus(ErrorCodeConstant.STATUS_CE999999);
                ret.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
                ret.setSrvAuthCode(bean.getSrvAuthCode());
                return ret;
            }
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("发送短信验证码失败，失败原因：" + bindCardService.getBankRetMsg(bean.getRetCode()));
            logger.info("-------------------发送短信验证码失败，失败原因：" + bindCardService.getBankRetMsg(bean.getRetCode())+"--------------------");
            return ret;
        }
        // 成功返回业务授权码
        ret.setStatus(ErrorCodeConstant.SUCCESS);
        ret.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
        ret.setSrvAuthCode(bean.getSrvAuthCode());
        return ret;
    }

    /**
     * 用户绑卡
     */
    @PostMapping("/userBindCardPlus.do")
    public BaseResultBean userBindCardPlus(@RequestBody ThirdPartyBankCardRequestBean bankCardRequestBean,HttpServletRequest request) {
        //---
        ThirdPartyBankCardPlusResultBean ret = new ThirdPartyBankCardPlusResultBean();
        logger.info(bankCardRequestBean.getAccountId()+"用户绑卡开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(bankCardRequestBean));
        //验证请求参数
        if (Validator.isNull(bankCardRequestBean.getAccountId())||
                Validator.isNull(bankCardRequestBean.getCardNo())||
                Validator.isNull(bankCardRequestBean.getChannel())||
                Validator.isNull(bankCardRequestBean.getInstCode())||
                Validator.isNull(bankCardRequestBean.getLastSrvAuthCode())||
                Validator.isNull(bankCardRequestBean.getCode())||
                Validator.isNull(bankCardRequestBean.getMobile())) {

            logger.info("-------------------请求参数非法--------------------");
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("请求参数非法");
            return ret;
        }

        //验签
        if(!bindCardService.verifyRequestSign(bankCardRequestBean, BaseDefine.METHOD_SERVER_BIND_CARD)){
            logger.info("-------------------验签失败！--------------------");
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("验签失败");
            return ret;
        }

        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = bindCardService.getBankOpenAccountByAccount(bankCardRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+bankCardRequestBean.getAccountId()+"！--------------------");

            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("没有根据电子银行卡找到用户");
            return ret;
        }
        UserVO user = bindCardService.getUsersById(bankOpenAccount.getUserId());
        if(user == null){
            logger.info("---用户不存在汇盈金服账户---");
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("用户不存在汇盈金服账户");
            return ret;
        }
        if (user.getBankOpenAccount()==0) {
            logger.info("---用户未开户---");
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("用户未开户");
            return ret;
        }

        if (user.getIsSetPassword()==0) {
            logger.info("---用户未设置交易密码---");
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("未设置交易密码");
            return ret;
        }

        Integer userId = user.getUserId();
        BankCardVO bankCardVO=bindCardService.queryUserCardValid(String.valueOf(userId),bankCardRequestBean.getCardNo());
        if(bankCardVO!=null){
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("用户已绑定银行卡,请先解除绑定,然后重新操作！");
            return ret;
        }
        UserInfoVO usersInfo = bindCardService.getUserInfo(userId);

        // 调用汇付接口(4.2.2 用户绑卡接口)
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(StringUtil.valueOf(userId));
        bean.setLogRemark("用户绑卡增强");
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_PLUS);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        bean.setAccountId(bankCardRequestBean.getAccountId());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(usersInfo.getIdcard());// 证件号
        bean.setName(usersInfo.getTruename());// 姓名
        bean.setMobile(bankCardRequestBean.getMobile());// 手机号
        bean.setCardNo(bankCardRequestBean.getCardNo());// 银行卡号
        bean.setLastSrvAuthCode(bankCardRequestBean.getLastSrvAuthCode());
        bean.setSmsCode(bankCardRequestBean.getCode());
        bean.setUserIP(GetCilentIP.getIpAddr(request));// 客户IP
        LogAcqResBean logAcq = new LogAcqResBean();
        logAcq.setCardNo(bankCardRequestBean.getCardNo());
        bean.setLogAcqResBean(logAcq);
        BankCallBean retBean=null;
        // 跳转到江西银行天下画面
        try {
            retBean  = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.info("---调用银行接口失败~!---");
            logger.error(e.getMessage());

            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("调用银行接口失败~!");
            return ret;
        }

        if(retBean == null){
            logger.info("userBindCardPlus:调用银行接口失败，返回null");
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("调用银行接口失败，返回null");
            return ret;
        }

        // 回调数据处理
        if (!(BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()))) {
            // 执行结果(失败)
            String message = this.bindCardService.getBankRetMsg(retBean.getRetCode());
            logger.info("userBindCardPlus", "银行返码:" + retBean.getRetCode() + "绑卡失败:" + message);
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("绑卡失败");
            return ret;
        }

        try {
            // 绑卡后处理
            this.bindCardService.updateAfterBindCard(bean);
            bankCardVO = bindCardService.queryUserCardValid(String.valueOf(userId),bankCardRequestBean.getCardNo());
            if (bankCardVO != null) {
                ret.setStatus(BaseResultBean.STATUS_SUCCESS);
                ret.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
                return ret;
            } else {
                ret.setStatus(BaseResultBean.STATUS_FAIL);
                ret.setStatusDesc("绑卡失败");
                return ret;
            }
        } catch (Exception e) {
            // 执行结果(失败)
            logger.error(e.getMessage());
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("绑卡失败");
            return ret;
        }
    }

    /**
     * 用户删除银行卡
     */
    @PostMapping("/userDeleteCard")
    public ThirdPartyDeleteBankCardResultBean deleteCard(@RequestBody ThirdPartyBankCardRequestBean bankCardRequestBean,HttpServletRequest request, HttpServletResponse response) {
        logger.info(bankCardRequestBean.getAccountId()+"用户解除绑定银行卡开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(bankCardRequestBean));
        ThirdPartyDeleteBankCardResultBean resultBean = new ThirdPartyDeleteBankCardResultBean();
        //验证请求参数
        if (Validator.isNull(bankCardRequestBean.getAccountId())||
                Validator.isNull(bankCardRequestBean.getCardNo())||
                Validator.isNull(bankCardRequestBean.getChannel())||
                Validator.isNull(bankCardRequestBean.getInstCode())) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            logger.info("-------------------请求参数非法--------------------");
            resultBean.setStatusDesc("请求参数非法");
            return resultBean;
        }
        logger.info(bankCardRequestBean.getAccountId()+"第三方同步余额开始-----------------------------");
        //验签
        if(!bindCardService.verifyRequestSign(bankCardRequestBean, BaseDefine.METHOD_SERVER_DELETE_CARD)){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }
        resultBean.setAccountId(bankCardRequestBean.getAccountId());
        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = bindCardService.getBankOpenAccountByAccount(bankCardRequestBean.getAccountId());
        if(bankOpenAccount == null){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000004);
            logger.info("没有根据电子银行卡找到用户 "+bankCardRequestBean.getAccountId());
            resultBean.setStatusDesc("根据电子账户号查询用户信息失败 ");
            return resultBean;
        }

        UserVO user = bindCardService.getUsersById(bankOpenAccount.getUserId());
        Integer userId=user.getUserId();
        // 用户余额大于零不让解绑
        AccountVO account = bindCardService.getAccountByUserId(userId);
        // 用户在银行的账户余额
        BigDecimal bankBalance = bindCardService.getBankBalance(userId, bankOpenAccount.getAccount());
        if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
                || ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_BC000004);
            logger.info("解绑失败，余额大于0元是不能解绑银行卡"+bankCardRequestBean.getAccountId());
            resultBean.setStatusDesc("解绑失败，余额大于0元是不能解绑银行卡 ");
            return resultBean;
        }
        // 根据银行卡Id获取用户的银行卡信息
        BankCardVO bankCard=bindCardService.queryUserCardValid(String.valueOf(userId),bankCardRequestBean.getCardNo());
        if (bankCard == null || StringUtils.isEmpty(bankCard.getCardNo())) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_BC000002);
            logger.info("获取用户银行卡信息失败"+bankCardRequestBean.getCardNo());
            resultBean.setStatusDesc("获取用户银行卡信息失败 ");
            return resultBean;
        }
        String cardNo = bankCard.getCardNo();
        UserInfoVO usersInfo = bindCardService.getUserInfo(userId);
        // 调用汇付接口(4.2.6 删除银行卡接口)
        BankCallBean retBean = null;
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("解绑银行卡");
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_CARD_UNBIND);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(bankCardRequestBean.getChannel());// 交易渠道
        bean.setAccountId(bankCardRequestBean.getAccountId());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(usersInfo.getIdcard());// 证件号
        bean.setName(usersInfo.getTruename());// 姓名
        bean.setMobile(user.getMobile());// 手机号
        bean.setCardNo(cardNo);// 银行卡号
        LogAcqResBean logAcqResBean = new LogAcqResBean();
        logAcqResBean.setCardNo(cardNo);// 银行卡号
        logAcqResBean.setCardId(bankCard.getId()); // 银行卡Id
        bean.setLogAcqResBean(logAcqResBean);
        // 调用汇付接口
        try {
            retBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            logger.info("调用银行接口失败~!"+bankCardRequestBean.getAccountId());
            resultBean.setStatusDesc("调用银行接口失败~!");
            return resultBean;
        }

        if(retBean == null){
            logger.info("调用银行接口失败，返回null");
            resultBean.setStatus(BaseResultBean.STATUS_FAIL);
            resultBean.setStatusDesc("调用银行接口失败，返回null");
            return resultBean;
        }

        // 回调数据处理
        if (!(BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()))) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            logger.info(retBean.getRetMsg()+bankCardRequestBean.getAccountId());
            resultBean.setStatusDesc(retBean.getRetMsg());
            return resultBean;
        }
        // 执行删除卡后处理,判断银行卡状态，删除平台本地银行卡信息
        try {
            boolean isUpdateFlag = this.bindCardService.updateAfterDeleteCard(userId,user.getUsername(),bankCardRequestBean.getCardNo());
            if (!isUpdateFlag) {
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                logger.info((retBean.getRetMsg())+bankCardRequestBean.getAccountId());
                resultBean.setStatusDesc("系统异常,请稍后再试!");
            } else {
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                resultBean.setStatusDesc("解绑银行卡成功!");
            }
        } catch (Exception e) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            logger.info("操作异常");
            resultBean.setStatusDesc("系统异常,请稍后再试!");
        }

        logger.info(bankCardRequestBean.getAccountId()+"用户解除绑定银行卡结束-----------------------------");
        return resultBean;
    }
}
