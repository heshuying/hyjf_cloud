/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.BorrowRegistResponseBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 15:34
 */
@Service
public class BorrowRegistServiceImpl implements BorrowRegistService {
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList() {
        return amTradeClient.selectBorrowProjectList();
    }

    @Override
    public Map<String, String> getParamNameMap(String param) {
        Map<String, String> resultMap = CacheUtil.getParamNameMap(param);
        return resultMap;
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public BorrowRegistResponseBean getRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistResponseBean borrowRegistResponseBean = new BorrowRegistResponseBean();
        //查询总条数
        Integer count = amTradeClient.getRegistCount(borrowRegistListRequest);
        borrowRegistResponseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowRegistListRequest.getCurrPage(), borrowRegistListRequest.getPageSize());
        page.setTotal(count);
        borrowRegistListRequest.setLimitStart(page.getOffset());
        borrowRegistListRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowRegistCustomizeVO> list = amTradeClient.selectBorrowRegistList(borrowRegistListRequest);
            String sumAccount = amTradeClient.sumBorrowRegistAccount(borrowRegistListRequest);
            borrowRegistResponseBean.setRecordList(list);
            borrowRegistResponseBean.setSumAccount(sumAccount);
        }
        return borrowRegistResponseBean;
    }

    /**
     * 标的备案
     *
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult updateBorrowRegist(String borrowNid, String currUserId, String currUserName) {
        AdminResult result = new AdminResult();
        // 获取相应的标的详情
        BorrowVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfoVO = amTradeClient.selectBorrowInfoByNid(borrowNid);
        if (borrowVO != null && borrowInfoVO != null) {
            //項目还款方式
            String borrowStyle = borrowVO.getBorrowStyle();
            //是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            int userId = borrowVO.getUserId();
            UserVO user = amUserClient.findUserById(userId);
            if (Validator.isNotNull(user)) {
                BankOpenAccountVO bankOpenAccount = amUserClient.getBankOpenAccountByUserId(userId);
                if (Validator.isNotNull(bankOpenAccount)) {
                    // 更新相应的标的状态为备案中
                    //todo wangjun 分布式事务问题
                    boolean debtRegistingFlag = this.updateBorrowRegist(borrowVO, 0, 1, 0, currUserId, currUserName);
                    if (debtRegistingFlag) {
                        // 获取共同参数
                        String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());
                        String orderDate = GetOrderIdUtils.getOrderDate();
                        // 调用开户接口
                        BankCallBean debtRegistBean = new BankCallBean();
                        // 接口版本号 共同参数删除
//                        debtRegistBean.setVersion(BankCallConstant.VERSION_10);
                        // 消息类型(用户开户)
                        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);
                        // 机构代码 共同参数删除
//                        debtRegistBean.setInstCode(instCode);
//                        debtRegistBean.setBankCode(bankCode);
//                        debtRegistBean.setTxDate(txDate);
//                        debtRegistBean.setTxTime(txTime);
//                        debtRegistBean.setSeqNo(seqNo);
//                        debtRegistBean.setChannel(channel);
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
                            BankOpenAccountVO account = amUserClient.getBankOpenAccountByUserId(borrowInfoVO.getRepayOrgUserId());
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
                        if (borrowInfoVO.getEntrustedFlg() == 1) {

                            STZHWhiteListVO stzhWhiteListVO = amTradeClient.selectStzfWhiteList(borrowInfoVO.getInstCode().trim(), String.valueOf(borrowInfoVO.getEntrustedUserId()));
                            if (stzhWhiteListVO != null) {
                                debtRegistBean.setEntrustFlag(borrowInfoVO.getEntrustedFlg().toString());
                                debtRegistBean.setReceiptAccountId(stzhWhiteListVO.getStAccountid());
                            } else {
                                this.updateBorrowRegist(borrowVO, 0, 4, 0, currUserId, currUserName);
                                return new AdminResult(BaseResult.FAIL, "受托白名单查询为空！");
                            }
                        }
                        try {
                            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                            String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                                //受托支付备案
                                if (borrowVO.getEntrustedFlg() == 1) {
                                    boolean debtEntrustedRegistedFlag = this.updateBorrowRegist(borrowVO, 7, 2, 1, currUserId, currUserName);
                                    if (debtEntrustedRegistedFlag) {
                                        result.setStatus(AdminResult.SUCCESS);
                                        result.setStatusDesc("备案成功！");
                                    } else {
                                        result.setStatus(AdminResult.FAIL);
                                        result.setStatusDesc("备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                } else {
                                    boolean debtRegistedFlag = this.updateBorrowRegist(borrowVO, 1, 2, 0, currUserId, currUserName);
                                    if (debtRegistedFlag) {
                                        result.setStatus(AdminResult.SUCCESS);
                                        result.setStatusDesc("备案成功！");
                                    } else {
                                        result.setStatus(AdminResult.FAIL);
                                        result.setStatusDesc("备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                }
                            } else {
                                this.updateBorrowRegist(borrowVO, 0, 4, 0, currUserId, currUserName);
                                String message = registResult.getRetMsg();
                                result.setStatus(AdminResult.FAIL);
                                result.setStatusDesc(StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            this.updateBorrowRegist(borrowVO, 0, 4, 0, currUserId, currUserName);
                            result.setStatus(AdminResult.FAIL);
                            result.setStatusDesc("银行备案接口调用失败！");
                        }
                    } else {
                        result.setStatus(AdminResult.FAIL);
                        result.setStatusDesc("更新相应的标的信息失败,请稍后再试！");
                    }
                } else {
                    result.setStatus(AdminResult.FAIL);
                    result.setStatusDesc("未查询到开户信息！");
                }
            } else {
                result.setStatus(AdminResult.FAIL);
                result.setStatusDesc("借款人信息错误！");
            }
        } else {
            result.setStatus(AdminResult.FAIL);
            result.setStatusDesc("未查询到相应标的信息！");
        }
        return result;
    }

    private boolean updateBorrowRegist(BorrowVO borrow, int status, int registStatus, int updateType, String currUserId, String currUserName) {
        boolean flag;
        borrow.setRegistStatus(registStatus);
        borrow.setStatus(status);
        borrow.setRegistUserId(Integer.parseInt(currUserId));
        borrow.setRegistUserName(currUserName);
        borrow.setRegistTime(GetDate.getNowTime());
        BorrowRegistRequest request = new BorrowRegistRequest(borrow, status, registStatus);
        if (updateType == 0) {
            flag = amTradeClient.updateBorrowRegist(request) > 0 ? true : false;
        } else {
            flag = amTradeClient.updateEntrustedBorrowRegist(request) > 0 ? true : false;
        }
        return flag;
    }
}
