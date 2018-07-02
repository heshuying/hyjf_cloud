/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.BorrowRegistService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/7/2 14:08
 */
@Service
public class BorrowRegistServiceImpl implements BorrowRegistService {
    @Autowired
    BorrowClient borrowClient;

    @Autowired
    AmBorrowClient amBorrowClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    BankOpenClient bankOpenClient;

    @Autowired
    StzhWhiteListClient stzhWhiteListClient;

    @Value("${hyjf.bank.bankcode}")
    private String BANK_BANKCODE;

    @Value("${hyjf.bank.instcode}")
    private String BANK_INSTCODE;

    /**
     * 标的备案
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResponse updateBorrowRegist(String borrowNid){
        AdminResponse result = new AdminResponse();

        // 获取相应的标的详情
        BorrowVO borrowVO = borrowClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfoVO = amBorrowClient.getBorrowInfoByNid(borrowNid);
        if (borrowVO != null && borrowInfoVO!= null) {
            //項目还款方式
            String borrowStyle = borrowVO.getBorrowStyle();
            //是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            int userId = borrowVO.getUserId();
            UserVO user = amUserClient.findUserById(userId);
            if (Validator.isNotNull(user)) {
                BankOpenAccountVO bankOpenAccount = bankOpenClient.selectById(userId);
                if (Validator.isNotNull(bankOpenAccount)) {
                    // 更新相应的标的状态为备案中
                    //todo wangjun 分布式事务问题
                    boolean debtRegistingFlag = this.updateBorrowRegist(borrowVO, 0, 1,0);
                    if (debtRegistingFlag) {
                        // 获取共同参数
                        String bankCode = BANK_BANKCODE;
                        String instCode = BANK_INSTCODE;
                        String channel = BankCallConstant.CHANNEL_PC;
                        String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());
                        String orderDate = GetOrderIdUtils.getOrderDate();
                        String txDate = GetOrderIdUtils.getTxDate();
                        String txTime = GetOrderIdUtils.getTxTime();
                        String seqNo = GetOrderIdUtils.getSeqNo(6);
                        // 调用开户接口
                        BankCallBean debtRegistBean = new BankCallBean();
                        // 接口版本号
                        debtRegistBean.setVersion(BankCallConstant.VERSION_10);
                        // 消息类型(用户开户)
                        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);
                        // 机构代码
                        debtRegistBean.setInstCode(instCode);
                        debtRegistBean.setBankCode(bankCode);
                        debtRegistBean.setTxDate(txDate);
                        debtRegistBean.setTxTime(txTime);
                        debtRegistBean.setSeqNo(seqNo);
                        debtRegistBean.setChannel(channel);
                        // 借款人电子账号
                        debtRegistBean.setAccountId(bankOpenAccount.getAccount());
                        // 标的表id
                        debtRegistBean.setProductId(borrowNid);
                        // 标的名称
                        debtRegistBean.setProductDesc(borrowInfoVO.getName());
                        // 募集日,标的保存时间
                        debtRegistBean.setRaiseDate(borrowInfoVO.getBankRaiseStartDate());
                        // 募集结束日期
                        debtRegistBean.setRaiseEndDate(borrowInfoVO.getBankRaiseEndDate());
                        if (isMonth) {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);
                        } else {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
                        }
                        // (借款期限,天数）
                        debtRegistBean.setDuration(String.valueOf(borrowInfoVO.getBankBorrowDays()));
                        // 交易金额
                        debtRegistBean.setTxAmount(String.valueOf(borrowVO.getAccount()));
                        // 年化利率
                        debtRegistBean.setRate(String.valueOf(borrowVO.getBorrowApr()));
                        if (Validator.isNotNull(borrowInfoVO.getRepayOrgUserId())) {
                            BankOpenAccountVO account = bankOpenClient.selectById(borrowInfoVO.getRepayOrgUserId());
                            if (Validator.isNotNull(account)) {
                                debtRegistBean.setBailAccountId(account.getAccount());
                            }
                        }
                        debtRegistBean.setLogOrderId(orderId);
                        debtRegistBean.setLogOrderDate(orderDate);
                        debtRegistBean.setLogUserId(String.valueOf(user.getUserId()));
                        debtRegistBean.setLogRemark("借款人标的登记");
                        debtRegistBean.setLogClient(0);

                        //备案接口(EntrustFlag和ReceiptAccountId要么都传，要么都不传)
                        if(borrowInfoVO.getEntrustedFlg()==1){

                            STZHWhiteListVO stzhWhiteListVO = stzhWhiteListClient.selectStzfWhiteList(borrowInfoVO.getInstCode().trim(),String.valueOf(borrowInfoVO.getEntrustedUserId()));
                            if (stzhWhiteListVO != null) {
                                debtRegistBean.setEntrustFlag(borrowInfoVO.getEntrustedFlg().toString());
                                debtRegistBean.setReceiptAccountId(stzhWhiteListVO.getStAccountid());
                            } else {
                                this.updateBorrowRegist(borrowVO, 0, 4,0);
                                result.setRtn(AdminResponse.FAIL);
                                result.setMessage("受托白名单查询为空！");
                                return result;
                            }
                        }
                        try {
                            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                            String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                                //受托支付备案
                                if(borrowVO.getEntrustedFlg()==1){
                                    boolean debtEntrustedRegistedFlag = this.updateBorrowRegist(borrowVO, 7, 2,1);
                                    if (debtEntrustedRegistedFlag) {
                                        result.setRtn(AdminResponse.SUCCESS);
                                        result.setMessage("备案成功！");
                                    } else {
                                        result.setRtn(AdminResponse.FAIL);
                                        result.setMessage("备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                } else {
                                    boolean debtRegistedFlag = this.updateBorrowRegist(borrowVO, 1, 2,0);
                                    if (debtRegistedFlag) {
                                        result.setRtn(AdminResponse.SUCCESS);
                                        result.setMessage("备案成功！");
                                    } else {
                                        result.setRtn(AdminResponse.FAIL);
                                        result.setMessage("备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                }
                            } else {
                                this.updateBorrowRegist(borrowVO, 0, 4,0);
                                String message = registResult.getRetMsg();
                                result.setRtn(AdminResponse.FAIL);
                                result.setMessage(StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            this.updateBorrowRegist(borrowVO, 0, 4,0);
                            result.setRtn(AdminResponse.FAIL);
                            result.setMessage("银行备案接口调用失败！");
                        }
                    } else {
                        result.setRtn(AdminResponse.FAIL);
                        result.setMessage("更新相应的标的信息失败,请稍后再试！");
                    }
                } else {
                    result.setRtn(AdminResponse.FAIL);
                    result.setMessage("未查询到开户信息！");
                }
            } else {
                result.setRtn(AdminResponse.FAIL);
                result.setMessage("借款人信息错误！");
            }
        } else {
            result.setRtn(AdminResponse.FAIL);
            result.setMessage("未查询到相应标的信息！");
        }
        return result;
    }

    private boolean updateBorrowRegist(BorrowVO borrow, int status, int registStatus,int updateType) {
        boolean flag;
        borrow.setRegistStatus(registStatus);
        borrow.setStatus(status);
        //todo wangjun 取得当前用户信息
//        borrow.setRegistUserId(Integer.parseInt(adminSystem.getId()));
//        borrow.setRegistUserName(adminSystem.getUsername());
        borrow.setRegistTime(GetDate.getNowTime());
        BorrowRegistRequest request = new BorrowRegistRequest(borrow,status,registStatus);
        if(updateType == 0){
            flag = borrowClient.updateBorrowRegist(request) > 0 ? true : false;
        }else {
            flag = borrowClient.updateEntrustedBorrowRegist(request) > 0 ? true : false;
        }
        return flag;
    }
}
