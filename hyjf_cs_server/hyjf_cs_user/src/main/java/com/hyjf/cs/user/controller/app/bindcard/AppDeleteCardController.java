package com.hyjf.cs.user.controller.app.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * app端解绑银行卡
 * @author hesy
 * @version AppDeleteCardController, v0.1 2018/7/19 9:38
 */
@Api(value = "app端-解绑银行卡",description = "app端-解绑银行卡")
@RestController
@RequestMapping("/app/deleteCard")
public class AppDeleteCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;

    @PostMapping("/deleteCard")
    public JSONObject deleteCard(@RequestHeader(value = "userId") Integer userId,  HttpServletRequest request) {
       /* String cardId = request.getParameter("cardId");
        JSONObject ret = new JSONObject();
        // 检查参数
        if(userId == null || userId == 0){
            ret.put("status", "false");
            return ret;
        }

        // 条件校验
        bindCardService.checkParamBindCardPage(user);

        String cardNo = bankCard.getCardNo();
        Users users = userDeleteCardService.getUsers(userId);
        UsersInfo usersInfo = userDeleteCardService.getUsersInfoByUserId(userId);
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
        bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        bean.setAccountId(accountChinapnrTender.getAccount());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(usersInfo.getIdcard());// 证件号
        bean.setName(usersInfo.getTruename());// 姓名
        bean.setMobile(users.getMobile());// 手机号
        bean.setCardNo(cardNo);// 银行卡号
        LogAcqResBean logAcqResBean = new LogAcqResBean();
        logAcqResBean.setCardNo(cardNo);// 银行卡号
        logAcqResBean.setCardId(Integer.parseInt(cardId)); // 银行卡Id
        bean.setLogAcqResBean(logAcqResBean);
        // 调用汇付接口
        try {
            retBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用银行接口失败~!");
            ret.put("status", "false");
            return ret;
        }
        // 回调数据处理
        if (retBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()))) {
            LogUtil.debugLog(THIS_CLASS, "RetCode:" + (retBean == null ? "" : retBean.getRetCode()) + "&&&&&&&&&&& RetMsg:" + (retBean == null ? "" : retBean.getRetMsg()));
            ret.put("status", "false");
            return ret;
        }
        // 执行删除卡后处理,判断银行卡状态，删除平台本地银行卡信息
        try {
            boolean isUpdateFlag = this.userDeleteCardService.updateAfterDeleteCard(bean, userId);
            if (!isUpdateFlag) {
                ret.put("status", "false");
            } else {
                ret.put("status", "true");
            }
        } catch (Exception e) {
            ret.put("status", "false");
        }
        LogUtil.endLog(THIS_CLASS, DeleteCardDefine.REQUEST_MAPPING);
        return ret;*/

       return null;
    }
}
