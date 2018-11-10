package com.hyjf.cs.trade.service.repay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.MD5;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.ProjectRepayListBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.repay.RepayManageService;
import com.hyjf.cs.trade.util.PdfGenerator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 * 还款管理列表
 *
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class RepayManageServiceImpl extends BaseTradeServiceImpl implements RepayManageService {
    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private AuthService authService;
    /**
     * 普通用户管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getUserRepayFeeWaitTotal(Integer userId) {
        return amTradeClient.getUserRepayFeeWaitTotal(userId);
    }

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayFeeWaitTotal(Integer userId) {
        return amTradeClient.getUserRepayFeeWaitTotal(userId);
    }

    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayWaitTotal(Integer userId) {
        return amTradeClient.getOrgRepayWaitTotal(userId);
    }

    /**
     * 请求参数校验
     *
     * @param requestBean
     */
    @Override
    public void checkForRepayList(RepayListRequest requestBean) {
        if (requestBean.getCurrPage() <= 0) {
            requestBean.setCurrPage(1);
        }

        if (requestBean.getPageSize() <= 0) {
            requestBean.setPageSize(10);
        }
    }

    /**
     * 用户已还款/待还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList =  amTradeClient.repayList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 垫付机构待还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList = amTradeClient.orgRepayList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 垫付机构已还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList = amTradeClient.orgRepayedList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 用户待还款/已还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectRepayCount(RepayListRequest requestBean) {
        return amTradeClient.repayCount(requestBean);
    }

    /**
     * 垫付机构待还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayCount(RepayListRequest requestBean) {
        return amTradeClient.orgRepayCount(requestBean);
    }

    /**
     * 垫付机构已还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayedCount(RepayListRequest requestBean) {
        return amTradeClient.orgRepayedCount(requestBean);
    }

    @Override
    public boolean checkPassword(Integer userId, String password) {
        UserVO user = this.getUserByUserId(userId);
        String codeSalt = user.getSalt();
        String passwordDb = user.getPassword();
        // 验证用的password
        password = MD5Utils.MD5(password + codeSalt);
        // 密码正确时
        if (password.equals(passwordDb)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject getRepayDetailData(RepayRequestDetailRequest requestBean) {
        JSONObject result = amTradeClient.getRepayDetailData(requestBean);
        if(result == null){
            result = new JSONObject();
        }
        return result;
    }

   /**
    * 还款申请校验
    * @auther: hesy
    * @date: 2018/7/10
    */
    @Override
    public void checkForRepayRequest(String borrowNid, String password, WebViewUserVO user, RepayBean repayBean){
        if(StringUtils.isBlank(borrowNid) || StringUtils.isBlank(password)){
            throw new CheckException(MsgEnum.ERR_PARAM_NUM);
        }
        // 平台密码校验
        UserVO userVO = getUserByUserId(user.getUserId());
        String mdPassword = MD5Utils.MD5(password + userVO.getSalt());
        if (!mdPassword.equals(userVO.getPassword())) {
            throw  new CheckException(MsgEnum.ERR_PASSWORD_INVALID);
        }

        // 开户校验
        if(!user.isBankOpenAccount()){
            throw  new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        if(borrow == null){
            throw  new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        repayBean.setRepayUserId(user.getUserId());

        // 服务费授权
        boolean isPaymentAuth = this.authService.checkPaymentAuthStatus(user.getUserId());
        if (!isPaymentAuth) {
            throw  new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
        }
        boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
        if (!tranactionSetFlag) {//设置失败
            throw  new CheckException(MsgEnum.ERR_SYSTEM_BUSY);
        }

        AccountVO accountVO = getAccountByUserId(user.getUserId());
        if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
            // ** 用户符合还款条件，可以还款 *//*
            // 查询用户在银行电子账户的余额
            BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
            if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                // ** 用户符合还款条件，可以还款 *//*
            } else {
                // 银行账户余额不足
                throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
            }
        } else {
            // 平台账户余额不足
            throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
        }


    }


    @Override
    public void checkForRepayRequestOrg(String borrowNid, String password, WebViewUserVO user, RepayBean repayBean, int flag){
        if(StringUtils.isBlank(borrowNid) || StringUtils.isBlank(password)){
            throw new CheckException(MsgEnum.ERR_PARAM_NUM);
        }
        // 平台密码校验
        UserVO userVO = getUserByUserId(user.getUserId());
        String mdPassword = MD5.toMD5Code(password + userVO.getSalt());
        if (!mdPassword.equals(userVO.getPassword())) {
            throw  new CheckException(MsgEnum.ERR_PASSWORD_INVALID);
        }
        // 服务费授权和还款授权校验
        boolean isPaymentAuth = this.authService.checkPaymentAuthStatus(user.getUserId());
        if (!isPaymentAuth) {
            throw  new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
        }
        // 开户校验
        if(!user.isBankOpenAccount()){
            throw  new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        if(borrow == null){
            throw  new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }

        boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
        if (!tranactionSetFlag) {//设置失败
            throw  new CheckException(MsgEnum.ERR_SYSTEM_BUSY);
        }

        AccountVO accountVO = getAccountByUserId(user.getUserId());
        // 一次性还款
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
            if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
                // ** 垫付机构符合还款条件，可以还款 *//*
                // 查询用户在银行的电子账户
                BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                // 获取用户在银行的电子账户余额
                if (flag == 1) {//垫付机构批量还款
                    repayBean.setRepayUserId(user.getUserId());// 垫付机构id
                }else{
                    if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                        // ** 垫付机构符合还款条件，可以还款 *//*
                        repayBean.setRepayUserId(user.getUserId());// 垫付机构id
                    } else {
                        // 银行账户余额不足
                        throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                    }
                }

            } else {
                // 用户平台账户余额不足
                throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
            }
        } // 分期还款
        else {
            repayBean.setRepayUserId(user.getUserId());// 垫付机构id

            if (flag ==1) {
                //垫付机构批量还款 ，不验证总额
                ;
            }else {

                if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
                    // ** 垫付机构符合还款条件，可以还款 *//*
                    // 查询用户在银行的电子账户
                    BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                    if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                        // ** 用户符合还款条件，可以还款 *//*
                        ;
                    } else {
                        // 银行账户余额不足
                        throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                    }
                } else {
                    // 用户平台账户余额不足
                    throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                }

            }


        }
        // 如果有正在出让的债权,先去把出让状态停止
        this.updateBorrowCreditStautus(borrow.getBorrowNid());

    }

    @Override
    public RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay) {
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("userId", String.valueOf(userId));
        paraMap.put("roleId", roleId);
        paraMap.put("borrowNid", borrowNid);
        paraMap.put("isAllRepay", String.valueOf(isAllRepay));

        return amTradeClient.getRepayBean(paraMap);
    }

    /**
     * 还款申请回调数据更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean, boolean isAllRepay){
        RepayRequestUpdateRequest requestBean = new RepayRequestUpdateRequest();
        requestBean.setRepayBeanData(JSON.toJSONString(repayBean));
        requestBean.setBankCallBeanData(JSON.toJSONString(bankCallBean));
        requestBean.setAllRepay(isAllRepay);
        return amTradeClient.repayRequestUpdate(requestBean);
    }

    /**
     * 如果有正在出让的债权,先去把出让状态停止
     * @param borrowNid
     * @return
     */
    @Override
    public Boolean updateBorrowCreditStautus(String borrowNid) {
        return amTradeClient.updateBorrowCreditStautus(borrowNid);
    }

    /**
     * 校验重复还款
     * @param userId
     * @param borrowNid
     * @return
     */
    @Override
    public boolean checkRepayInfo(Integer userId, String borrowNid) {
        BankRepayFreezeLogVO log = amTradeClient.getFreezeLogValid(userId, borrowNid);
        if(log != null){
            return false;
        }
        List<BankRepayOrgFreezeLogVO> orgList = amTradeClient.getBankRepayOrgFreezeLogList(borrowNid);
        if (orgList != null && orgList.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 添加冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Integer addFreezeLog(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                                String userName){
        BankRepayFreezeLogRequest requestBean = new BankRepayFreezeLogRequest();
        requestBean.setBorrowNid(borrowNid);
        requestBean.setAccount(account);
        requestBean.setAmount(repayTotal);
        requestBean.setOrderId(orderId);
        requestBean.setUserId(userId);
        requestBean.setUserName(userName);
        return amTradeClient.addFreezeLog(requestBean);
    }

    /**
     * 删除冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Integer deleteFreezeLogByOrderId(String orderId){
        if(StringUtils.isBlank(orderId)){
            return 0;
        }
        return amTradeClient.deleteFreezeLogByOrderId(orderId);
    }

    /**
     * 更新借款API任务表
     * @return
     */
    @Override
    public Boolean updateBorrowApicron(BorrowApicronVO apicronVO, Integer status) {
        ApiCronUpdateRequest requestBean = new ApiCronUpdateRequest();
        requestBean.setApicronVO(apicronVO);
        requestBean.setStatus(status);

        return amTradeClient.updateBorrowApicron(requestBean);
    }

    /**
     * 根据bankSeqNo检索
     * @param bankSeqNO
     * @return
     */
    @Override
    public BorrowApicronVO selectBorrowApicron(String bankSeqNO) {
        return amTradeClient.selectBorrowApicron(bankSeqNO);
    }

    /**
     * 批次状态查询
     * @param apicron
     * @return
     */
    @Override
    public BankCallBean batchQuery(BorrowApicronVO apicron) {
        // 获取共同参数
        String batchNo = apicron.getBatchNo();// 还款请求批次号
        String batchTxDate = String.valueOf(apicron.getTxDate());// 还款请求日期
        int userId = apicron.getUserId();
        for (int i = 0; i < 3; i++) {
            String logOrderId = GetOrderIdUtils.getOrderId2(userId);
            String orderDate = GetOrderIdUtils.getOrderDate();
            // 调用还款接口
            BankCallBean loanBean = new BankCallBean();
            loanBean.setTxCode(BankCallConstant.TXCODE_BATCH_QUERY);// 消息类型(批量还款)
            loanBean.setBatchNo(batchNo);
            loanBean.setBatchTxDate(batchTxDate);
            loanBean.setLogUserId(String.valueOf(apicron.getUserId()));
            loanBean.setLogOrderId(logOrderId);
            loanBean.setLogOrderDate(orderDate);
            loanBean.setLogRemark("批次状态查询");
            loanBean.setLogClient(0);
            BankCallBean queryResult = BankCallUtils.callApiBg(loanBean);
            if (Validator.isNotNull(queryResult)) {
                String retCode = StringUtils.isNotBlank(queryResult.getRetCode()) ? queryResult.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    return queryResult;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return null;
    }

    /**
     * 获取批量还款页面数据
     */
    @Override
    public ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate) {
        BatchRepayDataRequest requestBean = new BatchRepayDataRequest();
        requestBean.setUserId(userId);
        requestBean.setStartDate(startDate);
        requestBean.setEndDate(endDate);
        return amTradeClient.getOrgBatchRepayData(requestBean);
    }

    /**
     * 下载汇盈金服互联网金融服务平台居间服务协议—————— 借款人
     */
    @Override
    public File createAgreementPDFFileRepay(HttpServletRequest request, HttpServletResponse response, String borrowNid, String nid, String flag, Integer userId) {
        logger.info("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）");
        if (borrowNid == null || "".equals(borrowNid.trim())) {
            System.out.println("标的信息不存在,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。");
            return null;
        }
        // 查询借款人用户名
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("borrowNidSrch", borrowNid);
        BorrowCommonCustomizeVO borrowCommonCustomize = new BorrowCommonCustomizeVO();
        // 借款编码
        borrowCommonCustomize.setBorrowNidSrch(borrowNid);
        List<BorrowCustomizeVO> recordList1 = amTradeClient.searchBorrowList(paraMap);
        // 出让人userid
        TenderAgreementVO tenderAgreement = new TenderAgreementVO();
        List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
        File filePdf= null;
        //下载法大大协议--居间
        if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
            tenderAgreement = tenderAgreementsNid.get(0);
            if(tenderAgreement!=null){
                filePdf = createFaddPDFImgFileOne(tenderAgreement);//下载脱敏
            }
            return filePdf;
        }
        List<BorrowCustomizeVO> recordList = recordList1;
        Map<String, Object> contents = new HashMap<String, Object>();
        contents.put("record", recordList.get(0));
        contents.put("borrowNid", borrowNid);
        contents.put("nid", nid);

        contents.put("recoverLastDay", recordList.get(0).getRecoverLastDay());// 最后一笔的放款完成时间
        if (org.apache.commons.lang.StringUtils.isNotBlank(recordList.get(0).getRecoverLastTime())) {
            // 最后一笔的放款完成时间 (协议签订日期)
            contents.put("recoverTime", recordList.get(0).getRecoverLastTime());
        } else {
            // 设置为满标时间
            contents.put("recoverTime", recordList.get(0).getReverifyTime());
        }

        // 借款人用户名
        int userIds = recordList.get(0).getUserId();
        UserInfoVO userInfo= amUserClient.findUserInfoById(userIds);
        String borrowUsername = userInfo.getTruename();
        System.out.println("2221------------------------idCard:"+userInfo.getIdcard());
        if(flag!=null && flag=="1"){
            List<WebUserInvestListCustomizeVO> tzList = selectUserInvestList(borrowNid);
            if (tzList != null && tzList.size() > 0) {
                WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                if(userInvest.getUserId().equals(String.valueOf(userId))){

                    userInvest.setRealName(userInvest.getRealName().substring(0,1)+"**");
                    userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                    userInvest.setIdCard(userInvest.getIdCard().substring(0,4)+"**************");
                }
                contents.put("userInvest", userInvest);
            }else {
                return null;
            }
        } else {
            List<WebUserInvestListCustomizeVO> tzList = selectUserInvestList(borrowNid);
            if (tzList != null && tzList.size() > 0) {
                WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                if(userInvest.getUserId().equals(String.valueOf(userId))){
                    userInvest.setRealName(userInvest.getRealName().substring(0,1)+"**");
                    userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                    userInvest.setIdCard(userInvest.getIdCard().substring(0,4)+"**************");
                }
                contents.put("userInvest", userInvest);
            }else {
                return null;
            }
        }
        if(!(userId+"").equals(userInfo.getUserId()+"") ){
            borrowUsername = borrowUsername.substring(0,1)+"**";

        }
        contents.put("borrowUsername", borrowUsername);
        // 如果是分期还款，查询分期信息
        String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
        if (borrowStyle != null) {
            //计算预期收益
            BigDecimal earnings = new BigDecimal("0");
            // 收益率

            String borrowAprString = org.apache.commons.lang.StringUtils.isEmpty(recordList.get(0).getBorrowApr())?"0.00":recordList.get(0).getBorrowApr().replace("%", "");
            BigDecimal borrowApr = new BigDecimal(borrowAprString);
            //投资金额
            String accountString = org.apache.commons.lang.StringUtils.isEmpty(recordList.get(0).getAccount())?"0.00":recordList.get(0).getAccount().replace(",", "");
            BigDecimal account = new BigDecimal(accountString);
            // 周期
            String borrowPeriodString = org.apache.commons.lang.StringUtils.isEmpty(recordList.get(0).getBorrowPeriod())?"0":recordList.get(0).getBorrowPeriod();
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(borrowPeriodString);
            borrowPeriodString = m.replaceAll("").trim();
            Integer borrowPeriod = Integer.valueOf(borrowPeriodString);
            if (org.apache.commons.lang.StringUtils.equals("endday", borrowStyle)){
                // 还款方式为”按天计息，到期还本还息“：预期收益=投资金额*年化收益÷365*锁定期；
                earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
            } else {
                // 还款方式为”按月计息，到期还本还息“：预期收益=投资金额*年化收益÷12*月数；
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

            }
            contents.put("earnings", earnings);
            if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                    || "endmonth".equals(borrowStyle)) {
                Map<String,Object> paraBean = new HashMap<>();
                paraBean.put("userId", userId);
                paraBean.put("borrowNid", borrowNid);
                paraBean.put("nid", nid);
                int recordTotal = amTradeClient.countProjectRepayPlanRecordTotal(paraMap);
                if (recordTotal > 0) {
                    Paginator paginator = new Paginator(1, recordTotal);
                    List<WebProjectRepayListCustomizeVO> fqList = amTradeClient.selectProjectRepayPlanList(paraMap);
                    contents.put("paginator", paginator);
                    contents.put("repayList", fqList);
                } else {
                    Paginator paginator = new Paginator(1, recordTotal);
                    contents.put("paginator", paginator);
                    contents.put("repayList", "");
                }
            }
        }

        // 导出PDF文件
        try {
            String flag1 = flag;
            if(flag1!=null && flag1=="1"){
                File file  =  PdfGenerator.generatePdfFile(request, response, borrowNid + "_" + nid + ".pdf",
                        CustomConstants.TENDER_CONTRACT, contents);
                return file;
            }else {
                PdfGenerator.generatePdf(request, response, borrowNid + "_" + nid + ".pdf",
                        CustomConstants.TENDER_CONTRACT, contents);
            }

        } catch (Exception e) {
            logger.error("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）", e);
        }

        logger.info("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）");
        return null;
    }

    public File createFaddPDFImgFileOne(TenderAgreementVO tenderAgreement) {
        SFTPParameter para = new SFTPParameter() ;
        String ftpIP = systemConfig.getHyjfFtpIp();
        String port = systemConfig.getHyjfFtpPort();
        String basePathImage = systemConfig.getHyjfFtpBasepathImg();
        String basePathPdf = systemConfig.getHyjfFtpBasepathPdf();
        String password = systemConfig.getHyjfFtpPassword();
        String username = systemConfig.getHyjfFtpUsername();
        para.hostName = ftpIP;//ftp服务器地址
        para.userName = username;//ftp服务器用户名
        para.passWord = password;//ftp服务器密码
        para.port = Integer.valueOf(port);//ftp服务器端口
        para.savePath = "/pdf_tem/pdf/" + tenderAgreement.getTenderNid();
        para.fileName=tenderAgreement.getTenderNid();
        String imgUrl = tenderAgreement.getImgUrl();
        String pdfUrl = tenderAgreement.getPdfUrl();
        if(org.apache.commons.lang.StringUtils.isNotBlank(pdfUrl)){
            //获取文件目录
            int index = pdfUrl.lastIndexOf("/");
            String pdfPath = pdfUrl.substring(0,index);
            //文件名称
            String pdfName = pdfUrl.substring(index+1);
            para.downloadPath = basePathPdf + "/" + pdfPath;
            para.sftpKeyFile = pdfName;

        }else if(org.apache.commons.lang.StringUtils.isNotBlank(imgUrl)){
            int index = imgUrl.lastIndexOf("/");
            String imgPath = imgUrl.substring(0,index);
            //文件名称
            String imgName = imgUrl.substring(index+1);
            para.downloadPath = "/" + basePathImage + "/" + imgPath;
            para.sftpKeyFile = imgName;
        }else{
            return null;
        }
        File file =  FavFTPUtil.downloadDirectory(para);
        return file;
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(String borrowNid) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("borrowNid", borrowNid);

        return amTradeClient.selectUserInvestList(paraMap);
    }

    /**
     * 获取银行错误返回码
     * @param retCode
     * @return
     */
    @Override
    public String getBankRetMsg(String retCode) {
        return amConfigClient.getBankRetMsg(retCode);
    }

    /**
     * 插入垫付机构冻结表日志
     * @author wgx
     * @date 2018/10/11
     */
    @Override
    public Integer insertRepayOrgFreezeLof(Integer userId, String orderId, String account, String borrowNid, RepayBean repay, String userName, boolean isAllRepay) {
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(borrowNid);
        BankRepayOrgFreezeLogRequest requestBean = new BankRepayOrgFreezeLogRequest();
        requestBean.setRepayUserId(userId);// 还款人用户userId
        requestBean.setRepayUserName(userName);// 还款人用户名
        requestBean.setBorrowUserId(borrow.getUserId());// 借款人userId
        requestBean.setBorrowUserName(borrow.getBorrowUserName());// 借款人用户名
        requestBean.setAccount(account);// 电子账号
        requestBean.setOrderId(orderId);// 订单号:txDate+txTime+seqNo
        requestBean.setBorrowNid(borrow.getBorrowNid());// 借款编号
        requestBean.setPlanNid(borrow.getPlanNid());// 计划编号
        requestBean.setInstCode(borrowInfo.getInstCode());// 资产来源
        requestBean.setAmount(borrow.getAccount());// 借款金额
        requestBean.setAmountFreeze(repay.getRepayAccountAll());// 冻结金额
        requestBean.setRepayAccount(repay.getRepayAccount());// 应还本息
        requestBean.setRepayFee(repay.getRepayFee());// 还款服务费
        requestBean.setLowerInterest(BigDecimal.ZERO.subtract(repay.getChargeInterest()));// 减息金额
        requestBean.setPenaltyAmount(BigDecimal.ZERO);// 违约金
        requestBean.setDefaultInterest(repay.getLateInterest());// 逾期罚息?
        Integer period = borrow.getBorrowPeriod();
        String borrowStyle = borrow.getBorrowStyle();
        String borrowPeriod = period + (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) ? "天" : "个月");
        String periodTotal = CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ? "1" : repay.getBorrowPeriod();
        int remainRepayPeriod = CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ? 1 : repay.getRepayPeriod();
        int repayPeriod = Integer.parseInt(periodTotal) - remainRepayPeriod + 1;
        requestBean.setBorrowPeriod(borrowPeriod);// 借款期限
        requestBean.setTotalPeriod(Integer.parseInt(periodTotal));// 总期数
        requestBean.setCurrentPeriod(repayPeriod);// 当前期数
        requestBean.setAllRepayFlag(isAllRepay ? 1 : 0);// 是否全部还款 0 否 1 是
        requestBean.setDelFlag(0);// 0 有效 1 无效
        return amTradeClient.addOrgFreezeLog(requestBean);
    }

    /**
     * 查询垫付机构冻结列表
     * @author wgx
     * @date 2018/10/11
     */
    @Override
    public List<BankRepayOrgFreezeLogVO> getBankRepayOrgFreezeLogList(String borrowNid, String orderId) {
        if (StringUtils.isBlank(orderId) && StringUtils.isBlank(borrowNid)) {
            return new ArrayList<>();
        }
        return amTradeClient.getBankRepayOrgFreezeLogList(orderId, borrowNid);
    }

    /**
     * 删除垫付机构临时日志,外部调用
     * @author wgx
     * @date 2018/10/11
     */
    @Override
    public Integer deleteOrgFreezeTempLogs(String orderId, String borrowNid) {
        if (StringUtils.isBlank(orderId)) {
            return 0;
        }
        return amTradeClient.deleteOrgFreezeLog(orderId, borrowNid);
    }

    /**
     * 单笔还款申请冻结查询
     * @auther: wgx
     * @date: 2018/10/11
     */
    @Override
    public boolean queryBalanceFreeze(Integer userId, String userName, String orderId, String account) {
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_BALANCE_FREEZE_QUERY);
        bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        bean.setBankCode(systemConfig.getBankBankcode());
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(account);// 电子账号
        bean.setOrgOrderId(orderId);// 原订单号
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserName(userName);
        bean.setLogClient(0);
        try {
            BankCallBean callBackBean = BankCallUtils.callApiBg(bean);
            String respCode = callBackBean == null ? "" : callBackBean.getRetCode();
            // 单笔还款申请冻结查询失败
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                logger.info("【冻结查询】调用单笔还款申请冻结查询接口失败:{},订单号:{}", callBackBean.getRetMsg(), callBackBean.getOrderId());
                return false;
            } else {
                // 单笔还款申请冻结查询非正常
                if (!BankCallConstant.STATUS_SUCCESS.equals(callBackBean.getState())) {
                    deleteOrgFreezeTempLogs(orderId, null);
                    RedisUtils.del("batchOrgRepayUserid_" + userId);
                    logger.info("【冻结查询】单笔还款申请冻结未成功，订单号:{}", callBackBean.getOrderId());
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 还款申请冻结资金
     * @auther: wgx
     * @date: 2018/10/16
     */
    @Override
    public  WebResult getBalanceFreeze(WebViewUserVO userVO, String borrowNid, RepayBean repayBean, String orderId, String account, WebResult webResult, boolean isAllRepay) {
        Integer userId = userVO.getUserId();
        String userName = userVO.getUsername();
        String ip = repayBean.getIp();
        BigDecimal repayTotal = repayBean.getRepayAccountAll();
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_FREEZE);// 交易代码
        bean.setAccountId(account);// 电子账号
        bean.setOrderId(orderId); // 订单号
        bean.setTxAmount(String.valueOf(repayTotal));// 交易金额
        bean.setProductId(borrowNid);
        bean.setFrzType("0");
        bean.setLogOrderId(orderId);// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogUserName(userName);
        bean.setLogClient(0);
        bean.setLogIp(ip);
        bean.setProductId(borrowNid);
        BankCallBean callBackBean = BankCallUtils.callApiBg(bean);
        String respCode = callBackBean == null ? "" : callBackBean.getRetCode();
        // 申请冻结资金失败
        if (StringUtils.isBlank(respCode) || !BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            if (!"".equals(respCode)) {
                this.deleteFreezeLogByOrderId(orderId);
            }
            logger.info("调用还款申请冻结资金接口失败:" + callBackBean.getRetMsg() + "订单号:" + callBackBean.getOrderId());
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("还款失败，请稍后再试...");
            return webResult;
        }
        //还款后变更数据
        boolean updateResult = this.updateForRepayRequest(repayBean, callBackBean, isAllRepay);
        if(updateResult){
            updateResult = this.updateBorrowCreditStautus(borrowNid);
            if(!updateResult){
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("还款失败，请稍后再试...");
            }else {
                webResult.setStatus(WebResult.SUCCESS);
                webResult.setStatusDesc(WebResult.SUCCESS_DESC);
                return webResult;
            }
        }else {
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("还款失败，请稍后再试...");
        }
        return webResult;
    }

    /**
     * 代偿冻结（合规要求）
     * @auther: wgx
     * @date: 2018/10/11
     */
    @Override
    public Map<String, Object> getBankRefinanceFreezePage(Integer userId, String userName, String ip, String orderId, String borrowNid, BigDecimal repayTotal, String account) {
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        bean.setBankCode(systemConfig.getBankBankcode());
        bean.setTxCode(BankCallMethodConstant.TXCODE_REFINANCE_FREEZE_PAGE);// 交易代码
        bean.setTxDate(orderId.substring(0, 8));
        bean.setTxTime(orderId.substring(8, 14));
        bean.setSeqNo(orderId.substring(14));
        bean.setOrderId(orderId);// 订单号
        bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
        bean.setAccountId(account);// 电子账号
        bean.setTxAmount(String.valueOf(repayTotal));// 交易金额
        bean.setProductId(borrowNid);
        bean.setFrzType("0");
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserName(userName);
        bean.setLogClient(0);
        bean.setLogIp(ip);
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_REFINANCE_FREEZE_PAGE);
        // 同步调用失败页面路径
        String retUrl = systemConfig.getFrontHost() + "/user/repay/batchFail";
        // 同步调用成功页面路径
        String successfulUrl = systemConfig.getFrontHost() + "/user/repay";
        // 异步调用路
        String bgRetUrl = "http://CS-TRADE/hyjf-web/repay/getRepayAsyncReturn";
        bean.setRetUrl(retUrl + "?logOrdId=" + bean.getLogOrderId());
        //if(StringUtils.isNotBlank(borrowNid)){
        //    BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(borrowNid);
        //    bean.setSuccessfulUrl(successfulUrl + "/repaySuccess?borrowNid=" + borrowNid + "&borrowName=" + borrowInfo.getName());
        //}else{
            bean.setSuccessfulUrl(successfulUrl + "/batchSuccess");// 批次成功页面
        //}
        bean.setNotifyUrl(bgRetUrl + "?orderId=" + orderId + "&isBatchRepay=" + StringUtils.isBlank(borrowNid));// 页面异步返回URL(必须)
        try {
            logger.info("【代偿冻结】调用还款申请冻结资金接口成功,订单号:{}", orderId);
            return BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            logger.error("【代偿冻结】调用还款申请冻结资金接口失败,订单号:{}", orderId);
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
    }

    /**
     * 开始批量还款
     * @auther: wgx
     * @date: 2018/10/16
     */
    public Map<String, Object> startOrgRepay(String startDate, String endDate, Integer userId, String password, String ip, BankOpenAccountVO userBankOpenAccount) {
        RepayListRequest form = new RepayListRequest();
        form.setUserId(userId + "");
        form.setRoleId("3");
        form.setStartDate(startDate);
        form.setEndDate(endDate);
        form.setStatus("0");
        form.setRepayStatus("0");
        WebViewUserVO userVO = getUserFromCache(userId);
        List<RepayListCustomizeVO> list = selectOrgRepayList(form);
        String account = userBankOpenAccount.getAccount();
        String txDate = GetOrderIdUtils.getTxDate();// 交易日期
        String txTime = GetOrderIdUtils.getTxTime();// 交易时间
        String seqNo = GetOrderIdUtils.getSeqNo(6);// 交易流水号
        String orderId = txDate + txTime + seqNo;// 交易日期+交易时间+交易流水号
        BigDecimal repayTotal = BigDecimal.ZERO;
        if (list != null && list.size() > 0) {
            int allRepaySize = list.size();//所有还款标的数目
            logger.info("=================cwyang 总还款笔数:" + allRepaySize + ",还款id:" + userId);
            for (int i = 0; i < list.size(); i++) {
                RepayListCustomizeVO repayInfo = list.get(i);
                String borrowNid = repayInfo.getBorrowNid();
                try {
                    RepayBean repayBean = getRepayBean(userVO.getUserId(), userVO.getRoleId(), borrowNid, false);
                    int isOrg = 1;//垫付机构不校验单笔标的的冻结余额
                    checkForRepayRequestOrg(borrowNid, password, userVO, repayBean, isOrg);
                    //防止汇计划还款时正在发生债转操作
                    int errflag = repayBean.getFlag();
                    if (1 == errflag) {
                        throw new RuntimeException("标的号:" + borrowNid + ",存在正在债转的操作,无法还款........");
                    }
                    //还款去重
                    boolean result = checkRepayInfo(null, borrowNid);
                    if (!result) {
                        logger.info("标的号:{},项目正在还款中...", borrowNid);
                        continue;
                    }
                    //插入垫付机构冻结信息日志表 add by wgx 2018-09-11
                    insertRepayOrgFreezeLof(userId, orderId, account, borrowNid, repayBean, userVO.getUsername(), false);
                    BigDecimal total = repayBean.getRepayAccountAll();
                    repayTotal = repayTotal.add(total);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("==============垫付机构:" + userId + "批量还款存在失败标的,标的号:" + borrowNid);
                }
            }
            if (repayTotal.compareTo(BigDecimal.ZERO) == 1) {// 可正常还款金额大于0
                // 调用江西银行还款申请冻结资金
                return getBankRefinanceFreezePage(userId, userVO.getUsername(), ip, orderId, "", repayTotal, account);
            }
        }
        logger.info("==============垫付机构:" + userId + "批量还款金额为0,订单号:" + orderId);
        return Collections.emptyMap();
    }

}
