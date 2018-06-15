/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.rtbbatch.*;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.RtbBatchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${yaoy}
 * @version RtbBatchClientImpl, v0.1 2018/6/13 16:34
 */
@Service
public class RtbBatchClientImpl implements RtbBatchClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowApicronVo> getBorrowApicronList(Integer status, Integer apiType) {
        String url ="http://AM_TRADE/am-trade/borrowApicron/getBorrowApicronList/" + status +"/"+ apiType;
        BorrowApicronResponse response =  restTemplate.getForEntity(url,BorrowApicronResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<IncreaseInterestInvestVo> getBorrowTenderList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/getBorrowTenderList" + borrowNid;
        IncreaseInterestInvestResponse response = restTemplate.getForEntity(url,IncreaseInterestInvestResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int updateBorrowApicron(Integer id, Integer status, String data) {
        String url = "http://AM-TRADE/am-trade/borrowApicron/updateBorrowApicron/"+id +"/"+status +"/"+data;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public int updateBorrowApicron2(Integer id, Integer status) {
        String url = "http://AM-TRADE/am-trade/borrowApicron/updateBorrowApicron2/"+id +"/"+status;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public AccountVO getAccountByUserId(Integer borrowUserId) {
        String url = "http://AM-TRADE/am-trade/account/getAccountByUserId/"+borrowUserId;
        AccountResponse response = restTemplate.getForEntity(url,AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer borrowUserId) {
        String url = "http://AM-USER/am-user/bankopen/selectById/"+borrowUserId;
        BankOpenAccountResponse response = restTemplate.getForEntity(url,BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowWithBLOBsVo getBorrow(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/trade/getBorrow/"+borrowNid;
        BorrowWithBLOBSResponse response = restTemplate.getForEntity(url,BorrowWithBLOBSResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateBorrowTender(IncreaseInterestInvestVo borrowTender) {
        Integer response =  restTemplate.getForEntity("http://AM-TRADE/am-trade/rtbLoanBatch/updateBorrowTender"+borrowTender,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public List<Map<String, String>> updateBorrowLoans(BorrowApicronVo apicron, IncreaseInterestInvestVo borrowTender) throws Exception{
        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
        /** 基本变量 */
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 借款编号
        String borrowNid = apicron.getBorrowNid();
        // 借款人ID
        Integer borrowUserid = apicron.getUserId();
        // 借款人信息
        UserVO borrowUser = this.getUsersByUserId(borrowUserid);
        /** 标的基本数据 */
        // 取得标的详情
        BorrowWithBLOBsVo borrow = getBorrow(borrowNid);
        Map<String, String> msg = new HashMap<String, String>();
        retMsgList.add(msg);
        // 借款期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        BorrowStyleVo borrowStyleMain = this.getborrowStyleByNid(borrowStyle);
        String borrowStyleName = borrowStyleMain.getName();
        // 年利率
        BigDecimal borrowApr = borrow.getBorrowApr();
        // 加息年利率
        BigDecimal extraYieldApr = borrow.getBorrowExtraYield();
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = Validator.isNull(borrow.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(borrow.getBorrowManagerScaleEnd());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());
        // 借款成功时间
        Integer borrowSuccessTime = borrow.getBorrowSuccessTime();
        // 项目类型
        Integer projectType = borrow.getProjectType();
        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        // 投资人用户ID
        Integer outUserId = borrowTender.getUserId();
        // 投资费用
        BigDecimal tenderAccount = BigDecimal.ZERO;
        // 利息
        BigDecimal interestTender = BigDecimal.ZERO;
        String loanOrderId = GetOrderIdUtils.getOrderId2(borrowTender.getUserId());
        String loanOrderDate = GetOrderIdUtils.getOrderDate();
        // 估计还款时间
        Integer recoverTime = null;
        // 投资订单号
        String ordId = borrowTender.getOrderId();
        // 投资金额
        tenderAccount = borrowTender.getAccount();
        // 计算利息
        InterestInfo interestInfo = CalculatesUtil.getInterestInfo(tenderAccount, borrowPeriod, extraYieldApr, borrowStyle, borrowSuccessTime, borrowMonthRate, borrowManagerScaleEnd, projectType,
                differentialRate, borrowVerifyTime);
        if (interestInfo != null) {
            interestTender = interestInfo.getRepayAccountInterest(); // 利息
            recoverTime = interestInfo.getRepayTime(); // 估计还款时间
        }
        // 写入还款明细表(hyjf_increase_interest_loan)
        IncreaseInterestLoanVo increaseInterestLoan = new IncreaseInterestLoanVo();
        increaseInterestLoan.setUserId(borrowTender.getUserId()); // 投资人
        increaseInterestLoan.setUserName(borrowTender.getCreateUserName());
        increaseInterestLoan.setBorrowNid(borrowNid); // 借款编号
        increaseInterestLoan.setInvestId(borrowTender.getId());// 投资id
        increaseInterestLoan.setInvestOrderId(ordId); // 投资订单号
        increaseInterestLoan.setInvestAccount(borrowTender.getAccount());// 投资金额
        increaseInterestLoan.setBorrowUserId(borrowUserid); // 借款人
        increaseInterestLoan.setBorrowUserName(borrowUser.getUsername()); // 借款人
        increaseInterestLoan.setBorrowApr(borrowApr);
        increaseInterestLoan.setBorrowStyleName(borrowStyleName);
        increaseInterestLoan.setBorrowExtraYield(extraYieldApr);
        increaseInterestLoan.setRepayPeriod(0); // 还款期数
        increaseInterestLoan.setBorrowStyle(borrowStyle);
        increaseInterestLoan.setLoanInterest(interestTender);
        increaseInterestLoan.setRemainPeriod(isMonth ? borrowPeriod : 1);// 剩余期限
        increaseInterestLoan.setRepayTime(GetterUtil.getString(recoverTime)); // 估计还款时间
        increaseInterestLoan.setRepayInterestWait(interestTender); // 预还利息
        increaseInterestLoan.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
        increaseInterestLoan.setRepayStatus(0);// 还款状态 0未还款 1还款中 2已还款
        increaseInterestLoan.setWeb(0);
        increaseInterestLoan.setCreateTime(nowTime);
        increaseInterestLoan.setCreateUserId(borrowTender.getUserId());
        increaseInterestLoan.setCreateUserName(borrowTender.getCreateUserName());
        increaseInterestLoan.setAddip(borrowTender.getAddip());
        boolean borrowRecoverFlag = this.insertBorrowRecover(increaseInterestLoan) > 0 ? true : false;
        if (borrowRecoverFlag) {
            // 更新投资详情表
            IncreaseInterestInvestVo newIncreaseInterestInvest = new IncreaseInterestInvestVo();
            newIncreaseInterestInvest.setId(borrowTender.getId()); // ID
            newIncreaseInterestInvest.setLoanOrderId(loanOrderId);
            newIncreaseInterestInvest.setLoanOrderDate(loanOrderDate);
            newIncreaseInterestInvest.setLoanAmount(interestTender); // 实际放款金额
            newIncreaseInterestInvest.setRepayInterest(interestTender);
            newIncreaseInterestInvest.setRepayInterest(interestTender);
            newIncreaseInterestInvest.setRepayInterestYes(new BigDecimal("0"));
            newIncreaseInterestInvest.setRepayInterestWait(interestTender);
            newIncreaseInterestInvest.setRepayTimes(0);// 已还款次数
            newIncreaseInterestInvest.setStatus(1); // 状态 0，未放款，1，已放款
            newIncreaseInterestInvest.setClient(0);
            newIncreaseInterestInvest.setAddip(borrowTender.getAddip());
            newIncreaseInterestInvest.setWeb(2); // 写入网站收支明细
            newIncreaseInterestInvest.setUpdateTime(GetDate.getNowTime10());
            newIncreaseInterestInvest.setUpdateUserId(borrowTender.getUserId());
            newIncreaseInterestInvest.setUpdateUserName(borrowTender.getCreateUserName());
            boolean borrowTenderFlag = this.updateBorrowTender(newIncreaseInterestInvest) > 0 ? true : false;
            if (borrowTenderFlag) {
                // 插入每个标的总的还款信息
                boolean isInsert = false;
                IncreaseInterestRepayVo increaseInterestRepay = getBorrowRepay(borrowNid);
                if (increaseInterestRepay == null) {
                    isInsert = true;
                    increaseInterestRepay = new IncreaseInterestRepayVo();
                    increaseInterestRepay.setUserId(borrowUserid); // 借款人ID
                    increaseInterestRepay.setUserName(borrowUser.getUsername());
                    increaseInterestRepay.setBorrowNid(borrowNid); // 借款标号
                    increaseInterestRepay.setInvestId(borrowTender.getId());
                    increaseInterestRepay.setInvestOrderId(ordId);
                    increaseInterestRepay.setInvestAccount(borrowTender.getAccount());
                    increaseInterestRepay.setBorrowApr(borrowApr);
                    increaseInterestRepay.setBorrowStyleName(borrowStyleName);
                    increaseInterestRepay.setBorrowExtraYield(extraYieldApr);
                    increaseInterestRepay.setBorrowPeriod(borrowPeriod);
                    increaseInterestRepay.setBorrowStyle(borrowStyle);
                    increaseInterestRepay.setRepayStatus(0); // 还款状态
                    increaseInterestRepay.setRepayPeriod(1); // 还款期数
                    increaseInterestRepay.setRepayTime(Integer.valueOf(GetterUtil.getString(recoverTime))); // 估计还款时间
                    increaseInterestRepay.setOrderId("");
                    increaseInterestRepay.setOrderDate("");
                    increaseInterestRepay.setRemainPeriod(isMonth ? borrowPeriod : 1);
                    increaseInterestRepay.setAlreadyRepayPeriod(0);
                    increaseInterestRepay.setRepayInterest(BigDecimal.ZERO); // 预还利息
                    increaseInterestRepay.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
                    increaseInterestRepay.setRepayInterestWait(BigDecimal.ZERO); // 未还利息
                    increaseInterestRepay.setAddip(borrow.getAddip()); // 发标ip
                    increaseInterestRepay.setWeb(0);
                    increaseInterestRepay.setCreateTime(nowTime); // 创建时间
                    increaseInterestRepay.setCreateUserId(borrowUserid);
                    increaseInterestRepay.setCreateUserName(borrowUser.getUsername());
                }
                increaseInterestRepay.setRepayInterest(increaseInterestRepay.getRepayInterest().add(interestTender)); // 预还利息
                increaseInterestRepay.setRepayInterestWait(increaseInterestRepay.getRepayInterestWait().add(interestTender)); // 待还利息
                int borrowRepayCnt = isInsert ? this.insertIncreaseInterestRepay(increaseInterestRepay) : this.updateIncreaseInterestRepay(increaseInterestRepay);
                if (borrowRepayCnt > 0 ? true : false) {
                    // [principal: 等额本金, month:等额本息,
                    // month:等额本息,end:先息后本]
                    if (isMonth) {
                        // 更新分期还款计划表(increaseInterestLoanDetail)
                        if (interestInfo != null && interestInfo.getListMonthly() != null) {
                            IncreaseInterestLoanDetailVo increaseInterestLoanDetail = null;
                            InterestInfo monthly = null;
                            for (int j = 0; j < interestInfo.getListMonthly().size(); j++) {
                                monthly = interestInfo.getListMonthly().get(j);
                                increaseInterestLoanDetail = new IncreaseInterestLoanDetailVo();
                                increaseInterestLoanDetail.setUserId(outUserId); // 投资人id
                                increaseInterestLoanDetail.setBorrowNid(borrowNid); // 借款订单id
                                increaseInterestLoanDetail.setUserName(borrowTender.getCreateUserName());
                                increaseInterestLoanDetail.setBorrowUserId(borrowUserid); // 借款人ID
                                increaseInterestLoanDetail.setBorrowUserName(borrowUser.getUsername()); // 借款人ID
                                increaseInterestLoanDetail.setBorrowStyleName(borrowStyleName);
                                increaseInterestLoanDetail.setBorrowStyle(borrowStyle);
                                increaseInterestLoanDetail.setInvestId(borrowTender.getId());
                                increaseInterestLoanDetail.setInvestOrderId(ordId);
                                increaseInterestLoanDetail.setInvestAccount(borrowTender.getAccount());
                                increaseInterestLoanDetail.setLoanInterest(monthly.getRepayAccountInterest()); // 预还利息
                                increaseInterestLoanDetail.setRepayPeriod(j + 1);
                                increaseInterestLoanDetail.setRepayTime(Integer.valueOf(GetterUtil.getString(monthly.getRepayTime())));// 估计还款时间
                                increaseInterestLoanDetail.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
                                increaseInterestLoanDetail.setRepayInterestWait(monthly.getRepayAccountInterest()); // 未还利息
                                increaseInterestLoanDetail.setRepayStatus(0);// 还款状态
                                // 0未还款
                                // 1还款中
                                // 2已还款
                                increaseInterestLoanDetail.setCreateTime(nowTime); // 创建时间
                                increaseInterestLoanDetail.setBorrowApr(borrowApr);
                                increaseInterestLoanDetail.setBorrowExtraYield(extraYieldApr);
                                increaseInterestLoanDetail.setBorrowPeriod(borrowPeriod);
                                increaseInterestLoanDetail.setCreateUserId(borrowTender.getUserId());
                                increaseInterestLoanDetail.setCreateUserName(borrowTender.getCreateUserName());
                                increaseInterestLoanDetail.setAddip(borrowTender.getAddip());
                                increaseInterestLoanDetail.setWeb(0);
                                boolean borrowRecoverPlanFlag = this.insertIncreaseInterestLoanDetail(increaseInterestLoanDetail) > 0 ? true : false;
                                if (borrowRecoverPlanFlag) {
                                    // 更新总的还款计划表(increaseInterestRepayDetail)
                                    isInsert = false;
                                    IncreaseInterestRepayDetailVo increaseInterestRepayDetail = getBorrowRepayPlan(borrowNid, j + 1);
                                    if (increaseInterestRepayDetail == null) {
                                        isInsert = true;
                                        increaseInterestRepayDetail = new IncreaseInterestRepayDetailVo();
                                        increaseInterestRepayDetail.setUserId(borrowUserid); // 借款人ID
                                        increaseInterestRepayDetail.setUserName(borrowUser.getUsername());
                                        increaseInterestRepayDetail.setBorrowNid(borrowNid); // 借款标号
                                        increaseInterestRepayDetail.setInvestId(borrowTender.getId());
                                        increaseInterestRepayDetail.setInvestOrderId(ordId);
                                        increaseInterestRepayDetail.setInvestAccount(borrowTender.getAccount());
                                        increaseInterestRepayDetail.setBorrowApr(borrowApr);
                                        increaseInterestRepayDetail.setBorrowStyleName(borrowStyleName);
                                        increaseInterestRepayDetail.setBorrowExtraYield(extraYieldApr);
                                        increaseInterestRepayDetail.setBorrowPeriod(borrowPeriod);
                                        increaseInterestRepayDetail.setBorrowStyle(borrowStyle);
                                        increaseInterestRepayDetail.setRepayStatus(0); // 还款状态
                                        increaseInterestRepayDetail.setRepayPeriod(j + 1); // 还款期数
                                        increaseInterestRepayDetail.setRepayTime(Integer.valueOf(GetterUtil.getString(monthly.getRepayTime()))); // 估计还款时间
                                        increaseInterestRepayDetail.setOrderId("");
                                        increaseInterestRepayDetail.setOrderDate("");
                                        increaseInterestRepayDetail.setRepayInterest(BigDecimal.ZERO); // 预还利息
                                        increaseInterestRepayDetail.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
                                        increaseInterestRepayDetail.setRepayInterestWait(BigDecimal.ZERO); // 未还利息
                                        increaseInterestRepayDetail.setAddip(borrow.getAddip()); // 发标ip
                                        increaseInterestRepayDetail.setWeb(0);
                                        increaseInterestRepayDetail.setCreateTime(nowTime); // 创建时间
                                        increaseInterestRepayDetail.setCreateUserId(borrowUserid);
                                        increaseInterestRepayDetail.setCreateUserName(borrowUser.getUsername());
                                    }
                                    increaseInterestRepayDetail.setRepayInterest(increaseInterestRepayDetail.getRepayInterest().add(increaseInterestLoanDetail.getLoanInterest())); // 应还利息
                                    increaseInterestRepayDetail.setRepayInterestWait(increaseInterestRepayDetail.getRepayInterestWait().add(increaseInterestLoanDetail.getLoanInterest())); // 待还利息

                                    int borrowRepayPlanCnt = isInsert ? this.insertIncreaseInterestRepayDetail(increaseInterestRepayDetail) : this.updateIncreaseInterestRepayDetail(increaseInterestRepayDetail);
                                    if (borrowRepayPlanCnt > 0 ? false : true) {
                                        throw new Exception("分期还款计划表(increaseInterestRepayDetail)写入失败!" + "[投资订单号：" + ordId + "]，" + "[期数：" + j + 1 + "]");
                                    }

                                } else {
                                    throw new Exception("分期放款款计划表(huiyingdai_borrow_recover_plan)写入失败!" + "[投资订单号：" + ordId + "]，" + "[期数：" + j + 1 + "]");
                                }
                            }
                        }
                    }
                    // 更新账户信息(投资人)
                    AccountVO account = new AccountVO();
                    account.setUserId(borrowTender.getUserId());
                    // 投资人资金总额 + 利息
                    account.setBankTotal(interestTender);
                    // 投资人待收金额 + 利息+ 本金
                    account.setBankAwait(interestTender);
                    // 投资人待收利息
                    account.setBankAwaitInterest(interestTender);
                    boolean investaccountFlag = this.updateOfRTBLoansTender(account) > 0 ? true : false;
                    if (!investaccountFlag) {
                        throw new Exception("投资人资金记录(huiyingdai_account)更新失败!" + "[投资订单号：" + ordId + "]");
                    }
                } else {
                    throw new Exception("的总的还款信息(increaseInterestRepay)" + (isInsert ? "插入" : "更新") + "失败!" + "[投资订单号：" + ordId + "]");
                }
            } else {
                throw new Exception("投资详情(IncreaseInterestInvest)更新失败!" + "[投资订单号：" + ordId + "]");
            }
        } else {
            throw new Exception("总的放款明细表(increaseInterestLoan)写入失败!" + "[投资订单号：" + ordId + "]");
        }
        System.out.println("-----------放款结束---" + apicron.getBorrowNid() + "---------" + borrowTender.getLoanOrderId());
        return retMsgList;
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    private UserVO getUsersByUserId(Integer userId) {
        if (userId != null) {
            String url = "http://AM-USER/am-user/user/findById/"+userId;
            UserResponse response = restTemplate.getForEntity(url,UserResponse.class).getBody();
            if (response != null) {
                return response.getResult();
            }
        }
        return null;
    }

    private BorrowStyleVo getborrowStyleByNid(String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/trade/getBorrowStyleByNid/"+ borrowStyle;
        BorrowStyleResponse response = restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 写入还款明细
     *
     * @param borrowRecover
     * @return
     */
    private int insertBorrowRecover(IncreaseInterestLoanVo borrowRecover) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/insertBorrowRecover/"+borrowRecover;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }


    private int insertIncreaseInterestRepay(IncreaseInterestRepayVo increaseInterestRepay){
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/insertIncreaseInterestRepay/"+increaseInterestRepay;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    private int updateIncreaseInterestRepay(IncreaseInterestRepayVo increaseInterestRepay){
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/updateIncreaseInterestRepay/"+increaseInterestRepay;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    /**
     * 取得借款信息
     *
     * @return
     */
    public IncreaseInterestRepayVo getBorrowRepay(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/getBorrowRepay/"+borrowNid;
        IncreaseInterestRepayResponse response = restTemplate.getForEntity(url,IncreaseInterestRepayResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    private int insertIncreaseInterestLoanDetail(IncreaseInterestLoanDetailVo increaseInterestLoanDetail) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/insertIncreaseInterestLoanDetail"+increaseInterestLoanDetail;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    /**
     * 取得借款计划信息
     *
     * @return
     */
    public IncreaseInterestRepayDetailVo getBorrowRepayPlan(String borrowNid, Integer period) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/getBorrowRepayPlan/"+borrowNid +"/"+period;
        IncreaseInterestRepayDetailResponse response = restTemplate.getForEntity(url,IncreaseInterestRepayDetailResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    private int insertIncreaseInterestRepayDetail(IncreaseInterestRepayDetailVo increaseInterestRepayDetail) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/insertIncreaseInterestRepayDetail/"+increaseInterestRepayDetail;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    private int updateIncreaseInterestRepayDetail(IncreaseInterestRepayDetailVo increaseInterestRepayDetail) {
        String url = "http://AM-TRADE/am-trade/rtbLoanBatch/updateIncreaseInterestRepayDetail/"+increaseInterestRepayDetail;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    private int updateOfRTBLoansTender(AccountVO account) {
        String url = "http://AM-TRADE/am-trade/account/updateOfRTBLoansTender/"+account;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    /**
     * 取得借款API任务表
     *
     * @return
     */
    @Override
    public List<BorrowApicronVo> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType) {
        String url = "http://AM-TRADE/am-trade/borrowApicron/getBorrowApicronListWithRepayStatus/" + status + "/" + apiType;
        BorrowApicronResponse response = restTemplate.getForEntity(url, BorrowApicronResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int updateBorrowApicronOfRepayStatus(Integer id, Integer status) {
        String url = "http://AM-TRADE/am-trade/borrowApicron/updateBorrowApicronOfRepayStatus/"+id +"/"+status;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }
}
