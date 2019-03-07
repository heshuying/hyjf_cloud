/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.InvestorDebtBean;
import com.hyjf.admin.beans.request.InvestorRequest;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.beans.vo.AdminBorrowInvestCustomizeVO;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.utils.PdfGenerator;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.admin.BorrowListCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.fdd.FddGenerateContractBeanVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangjun
 * @version BorrowInvestServiceImpl, v0.1 2018/7/10 9:18
 */
@Service
public class BorrowInvestServiceImpl implements BorrowInvestService {
    Logger logger = LoggerFactory.getLogger(BorrowInvestServiceImpl.class);
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmConfigClient amConfigClient;

    @Autowired
    AccedeListService accedeListService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

    private static final String OK = "OK";
    private static final String VAL_NAME = "val_name";
    private static final String VAL_SEX = "val_sex";

    /**
     * 查询总条数
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest){
        Integer count = amTradeClient.countBorrowInvest(borrowInvestRequest);
        return count;
    }

    /**
     * 出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public BorrowInvestResponseBean getBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestResponseBean responseBean = new BorrowInvestResponseBean();
        //查询总条数
        Integer count = amTradeClient.countBorrowInvest(borrowInvestRequest);
        responseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowInvestRequest.getCurrPage(), borrowInvestRequest.getPageSize());
        page.setTotal(count);
        borrowInvestRequest.setLimitStart(page.getOffset());
        borrowInvestRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowInvestCustomizeVO> list = amTradeClient.selectBorrowInvestList(borrowInvestRequest);
            //对应前端swagger
            List<AdminBorrowInvestCustomizeVO> voList = CommonUtils.convertBeanList(list,AdminBorrowInvestCustomizeVO.class);
            String sumAccount = amTradeClient.selectBorrowInvestAccount(borrowInvestRequest);
            responseBean.setRecordList(voList);
            responseBean.setSumAccount(sumAccount);
        } else {
            //没数据时初始化
            responseBean.setRecordList(new ArrayList<>());
        }
        return responseBean;
    }

    /**
     * 出借明细导出列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        return amTradeClient.getExportBorrowInvestList(borrowInvestRequest);
    }

    /**
     * 出借人债权明细
     *
     * @param investorDebtBean
     * @return
     */
    @Override
    public AdminResult debtInfo(InvestorDebtBean investorDebtBean) {
        List<BankCallBean> results = new ArrayList<BankCallBean>();
        //项目校验 成功返回"OK" 失败返回错误信息
        String checkResult = this.checkItems(investorDebtBean);
        if (!OK.equals(checkResult)) {
            return new AdminResult(BaseResult.FAIL, checkResult);
        }
        //查询开户信息
        BankOpenAccountVO bankOpenAccount = amUserClient.getBankOpenAccountByUserId(Integer.valueOf(investorDebtBean.getUserId()));
        if (bankOpenAccount == null) {
            return new AdminResult(BaseResult.FAIL, "该用户没有开户信息！");
        }
        String accountId = bankOpenAccount.getAccount();
        // 调用银行接口
        BankCallBean resultBean = this.bankCallInvestorDebtQuery(investorDebtBean, accountId);
        if (Validator.isNotNull(resultBean)) {
            String retCode = StringUtils.isNotBlank(resultBean.getRetCode()) ? resultBean.getRetCode() : "";
            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                results.add(resultBean);
            }
        }
        BorrowInvestResponseBean responseBean = new BorrowInvestResponseBean();
        //根据银行返回内容编辑前端展示列表
        responseBean.setDetailList(this.getDetailList(results));
        //前端需要total，加上
        if(!CollectionUtils.isEmpty(responseBean.getDetailList())){
            responseBean.setTotal(responseBean.getDetailList().size());
        } else {
            responseBean.setTotal(0);
        }
        return new AdminResult(responseBean);
    }

    /**
     * 字段check
     *
     * @param investorDebtBean
     * @return
     */
    private String checkItems(InvestorDebtBean investorDebtBean) {
        //校验标的编号
        if (StringUtils.isBlank(investorDebtBean.getBorrowNid())) {
            return "标的编号为空";
        }
        //校验用户ID
        if (StringUtils.isBlank(investorDebtBean.getUserId())) {
            return "用户ID为空";
        }
        try {
            Integer.parseInt(investorDebtBean.getUserId());
        } catch (NumberFormatException e) {
            return "用户ID不是数字";
        }
        return OK;
    }

    /**
     * 调用银行接口
     *
     * @param investorDebtBean
     * @param accountId
     * @return
     */
    private BankCallBean bankCallInvestorDebtQuery(InvestorDebtBean investorDebtBean, String accountId) {
        // 银行接口用BEAN
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10, BankCallConstant.TXCODE_CREDIT_DETAILS_QUERY, Integer.valueOf(investorDebtBean.getUserId()));

        //调用银行接口的共同参数已经删除
        String pageSize = "50";// 页长
        String pageNum = "1";// 页数（暂定一页）
        bean.setAccountId(accountId);// 借款人电子账号
        bean.setProductId(investorDebtBean.getBorrowNid());
        bean.setState(BankCallConstant.ALL_INVESTOR_DEBT);//目前查所有债权
        bean.setStartDate(investorDebtBean.getStartTime().replace("-", ""));//画面自选开始时间
        bean.setEndDate(investorDebtBean.getEndTime().replace("-", ""));//画面自选结束时间
        bean.setPageSize(pageSize);
        bean.setPageNum(pageNum);
        // 调用接口
        try {
            BankCallBean investorDebtResult = BankCallUtils.callApiBg(bean);
            if (investorDebtResult != null) {
                //根据响应代码取得响应描述
                investorDebtResult.setRetMsg(this.getBankRetMsg(investorDebtResult.getRetCode()));
            }
            return investorDebtResult;
        } catch (Exception e) {
            logger.error("调用银行接口错误：", e);
        }
        return null;
    }

    private String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO bankReturnCodeConfig = amConfigClient.getBankReturnCodeConfig(retCode);
            if (bankReturnCodeConfig != null) {
                String retMsg = bankReturnCodeConfig.getErrorMsg();
                if (StringUtils.isNotBlank(retMsg)) {
                    return retMsg;
                } else {
                    return "请联系客服！";
                }
            } else {
                return "请联系客服！";
            }
        } else {
            return "操作失败！";
        }
    }

    /**
     * 获得页面需要的展示列表
     *
     * @param resultBeans
     * @return
     */
    private List<InvestorDebtBean> getDetailList(List<BankCallBean> resultBeans) {
        //展示用LIST
        List<InvestorDebtBean> detailList = new ArrayList<>();

        if (Validator.isNotNull(resultBeans) && resultBeans.size() > 0) {
            for (int i = 0; i < resultBeans.size(); i++) {
                BankCallBean resultBean = resultBeans.get(i);
                //获取bean里的JOSN
                String subPacks = resultBean.getSubPacks();
                if (StringUtils.isNotBlank(subPacks)) {
                    JSONArray loanDetails = JSONObject.parseArray(subPacks);

                    BigDecimal sumTxAmount = BigDecimal.ZERO;
                    BigDecimal sumForIncome = BigDecimal.ZERO;
                    BigDecimal sumIntTotal = BigDecimal.ZERO;
                    BigDecimal sumIncome = BigDecimal.ZERO;

                    for (int j = 0; j < loanDetails.size(); j++) {
                        JSONObject loanDetail = loanDetails.getJSONObject(j);
                        InvestorDebtBean info = new InvestorDebtBean();
                        //标的号
                        info.setBorrowNid(loanDetail.getString(BankCallConstant.PARAM_PRODUCTID));
                        //投标日期
                        info.setBuyDate(loanDetail.getString(BankCallConstant.PARAM_BUYDATE));
                        //订单号
                        info.setOrderId(loanDetail.getString(BankCallConstant.PARAM_ORDERID));
                        //交易金额
                        info.setTxAmount(loanDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT));
                        //预期出借利率
                        info.setYield(loanDetail.getBigDecimal(BankCallConstant.PARAM_YIELD));
                        //预期收益
                        info.setForIncome(loanDetail.getBigDecimal(BankCallConstant.PARAM_FORINCOME));
                        //预期本息收益
                        info.setIntTotal(loanDetail.getBigDecimal(BankCallConstant.PARAM_INTTOTAL));
                        //实际收益
                        info.setIncome(loanDetail.getBigDecimal(BankCallConstant.PARAM_INCOME));
                        //实际收益符号
                        info.setIncFlag(loanDetail.getString(BankCallConstant.PARAM_INCFLAG));
                        //到期日
                        info.setEndDate(loanDetail.getString(BankCallConstant.PARAM_ENDDATE));
                        //状态
                        info.setState(loanDetail.getString(BankCallConstant.PARAM_STATE));
                        detailList.add(info);
                        sumTxAmount = sumTxAmount.add(loanDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT));
                        sumForIncome = sumForIncome.add(loanDetail.getBigDecimal(BankCallConstant.PARAM_FORINCOME));
                        sumIntTotal = sumIntTotal.add(loanDetail.getBigDecimal(BankCallConstant.PARAM_INTTOTAL));
                        sumIncome = sumIncome.add(loanDetail.getBigDecimal(BankCallConstant.PARAM_INCOME));

                    }
                    if (detailList != null && !detailList.isEmpty()) {
                        InvestorDebtBean temp = detailList.get(0);
                        temp.setSumTxAmount(sumTxAmount);
                        temp.setSumForIncome(sumForIncome);
                        temp.setSumIntTotal(sumIntTotal);
                        temp.setSumIncome(sumIncome);
                    }
                }
            }
        }
        return detailList;
    }

    /**
     * PDF脱敏图片预览
     *
     * @param nid
     * @return
     */
    @Override
    public AdminResult pdfPreview(String nid) {
        // 根据订单号查询用户出借协议记录表
        TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement(nid);
        if (tenderAgreement != null && StringUtils.isNotBlank(tenderAgreement.getImgUrl())) {
            BorrowInvestResponseBean responseBean = new BorrowInvestResponseBean();
            String imgUrl = tenderAgreement.getImgUrl();
            String[] imgs = imgUrl.split(";");
            List<String> imgList = Arrays.asList(imgs);
            responseBean.setImgList(imgList);
            // 文件服务器
            String fileDomainUrl = systemConfig.getFtpurl() + systemConfig.getFtpbasepathimg();
            responseBean.setFileDomainUrl(fileDomainUrl);
            return new AdminResult(responseBean);
        } else {
            return new AdminResult(BaseResult.FAIL, "未查询到用户出借协议");
        }
    }

    /**
     * PDF签署
     *
     * @param investorDebtBean
     * @return
     */
    @Override
    public AdminResult pdfSign(InvestorDebtBean investorDebtBean) {
        String checkResult = this.checkItems(investorDebtBean);
        if (!OK.equals(checkResult)) {
            return new AdminResult(BaseResult.FAIL, checkResult);
        }
        // 用户ID
        Integer userId = Integer.valueOf(investorDebtBean.getUserId());
        // 标的编号
        String borrowNid = investorDebtBean.getBorrowNid();
        // 出借订单号
        String nid = investorDebtBean.getNid();
        // 获取标的放款记录
        BorrowRecoverVO br = amTradeClient.selectBorrowRecover(userId, borrowNid, nid);
        if (br == null) {
            return new AdminResult(BaseResult.FAIL, "操作失败");
        }

        // 获取标的信息
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfo = amTradeClient.selectBorrowInfoByNid(borrowNid);
        if (borrow == null || borrowInfo == null) {
            return new AdminResult(BaseResult.FAIL, "标的不存在");
        }

        // 获取用户信息
        UserVO user = amUserClient.findUserById(userId);
        if (user == null) {
            return new AdminResult(BaseResult.FAIL, "用户信息不存在");
        }
        // 获取用户出借协议记录
        TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement(nid);
        // 签署成功(status = 2)
        if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
            accedeListService.updateSaveSignInfo(tenderAgreement, borrowNid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
        } else {
            FddGenerateContractBeanVO bean = new FddGenerateContractBeanVO();
            bean.setTenderUserId(userId);
            bean.setOrdid(nid);
            bean.setTransType(1);
            bean.setBorrowNid(borrowNid);
            bean.setSignDate(GetDate.date2Str(br.getCreateTime(), GetDate.date_sdf));
            bean.setTenderUserName(user.getUsername());
            bean.setTenderInterest(br.getRecoverInterest());
            bean.setTenderType(0);
            // 法大大生成合同
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
            } catch (MQException e) {
                logger.error("法大大合同生成MQ发送失败！");
                return new AdminResult(BaseResult.FAIL, "法大大合同生成MQ发送失败");
            }
        }
        return new AdminResult();
    }

    /**
     * 发送协议
     *
     * @param investorRequest
     * @return
     */
    @Override
    public AdminResult sendAgreement(InvestorRequest investorRequest) {
        //发送协议字段校验
        String checkResult = this.checkSendAgreement(investorRequest, 0);
        if (!OK.equals(checkResult)) {
            return new AdminResult(BaseResult.FAIL, checkResult);
        }
        String userId = investorRequest.getUserId();
        String nid = investorRequest.getNid();
        String borrowNid = investorRequest.getBorrowNid();
        String email = investorRequest.getEmail();
        //发送协议，异常时有返回信息
        String msg = this.resendMessageAction(userId, nid, borrowNid, email);

        if (msg == null) {
            return new AdminResult();
        } else {
            return new AdminResult(BaseResult.FAIL, msg);
        }
    }

    /**
     * 重发协议
     *
     * @param investorRequest
     * @return
     */
    @Override
    public AdminResult resendAgreement(InvestorRequest investorRequest) {
        //发送协议字段校验
        String checkResult = this.checkSendAgreement(investorRequest, 1);
        if (!OK.equals(checkResult)) {
            return new AdminResult(BaseResult.FAIL, checkResult);
        }

        String userId = investorRequest.getUserId();
        String nid = investorRequest.getNid();
        String borrowNid = investorRequest.getBorrowNid();
        //发送协议，异常时有返回信息
        String msg = this.resendMessageAction(userId, nid, borrowNid, null);

        if (msg == null) {
            return new AdminResult();
        } else {
            return new AdminResult(BaseResult.FAIL, msg);
        }
    }

    private String resendMessageAction(String userId, String nid, String borrowNid, String sendEmail) {
        try {
            PdfGenerator pdfGenerator = new PdfGenerator();
            //出借列表查询类
            BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
            borrowInvestRequest.setUserId(Integer.valueOf(userId));
            borrowInvestRequest.setBorrowNid(borrowNid);
            borrowInvestRequest.setNid(nid);
            // 向投出借发送邮件
            if (Validator.isNotNull(userId) && NumberUtils.isCreatable(userId)) {
                //查询用户
                UserVO user = amUserClient.findUserById(Integer.valueOf(userId));
                if (user == null) {
                    return "用户不存在！";
                }
                //校验用户邮箱地址
                String email = user.getEmail();
                if (StringUtils.isNotBlank(sendEmail)) {
                    email = sendEmail;
                }
                if (Validator.isNull(email)) {
                    return "用户邮箱不存在！";
                }
                Map<String, String> msg = new HashMap<String, String>();
                msg.put(VAL_NAME, user.getUsername());
                //查询用户信息
                UserInfoVO usersInfo = amUserClient.findUsersInfoById(Integer.valueOf(userId));
                if (Validator.isNotNull(usersInfo) && Validator.isNotNull(usersInfo.getSex())) {
                    if (usersInfo.getSex() == 2) {
                        msg.put(VAL_SEX, "女士");
                    } else {
                        msg.put(VAL_SEX, "先生");
                    }
                }
                String fileName = borrowNid + "_" + nid + ".pdf";
                String filePath = systemConfig.getHYJF_MAKEPDF_TEMPPATH() + "BorrowLoans_"
                        + GetDate.getMillis() + StringPool.FORWARD_SLASH;

                //查询借款相关信息
                List<BorrowListCustomizeVO> recordList = amTradeClient.selectBorrowList(borrowNid);
                if (recordList != null && recordList.size() == 1) {
                    //pdf文件信息组装
                    Map<String, Object> contents = new HashMap<String, Object>();
                    contents.put("record", recordList.get(0));
                    contents.put("borrowNid", borrowNid);
                    contents.put("nid", nid);
                    //借款人用户ID
                    int borrowerUserId = recordList.get(0).getUserId();
                    //查询借款人用户信息
                    UserInfoVO borrowerUserinfo = amUserClient.findUsersInfoById(borrowerUserId);
                    UserVO borrowerUser = amUserClient.findUserById(borrowerUserId);
                    //查询标的信息
                    BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
                    //汇计划的标的 借款人用真实姓名 否则用平台注册用户名
                    if (borrow.getPlanNid() != null) {
                        contents.put("borrowUsername", borrowerUserinfo.getTruename().substring(0, 1) + "**");
                    } else {
                        contents.put("borrowUsername", borrowerUser.getUsername().substring(0, 1) + "**");
                    }
                    //身份证号
                    contents.put("idCard", borrowerUserinfo.getIdcard());
                    // 本笔的放款完成时间 (协议签订日期)
                    contents.put("recoverTime", recordList.get(0).getRecoverLastTime());
                    // 用户出借列表
                    List<WebUserInvestListCustomizeVO> tzList =
                            amTradeClient.selectUserInvestList(borrowInvestRequest);
                    if (tzList != null && tzList.size() > 0) {
                        WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                        userInvest.setIdCard(borrowerUserinfo.getIdcard());
                        contents.put("userInvest", userInvest);
                    } else {
                        logger.error("标的出借信息异常,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。");
                        return "标的出借信息异常,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。";
                    }
                    // 如果是分期还款，查询分期信息
                    String borrowStyle = recordList.get(0).getBorrowStyle();
                    if (borrowStyle != null) {
                        //计算预期收益
                        BigDecimal earnings = new BigDecimal("0");
                        // 收益率

                        String borrowAprString = StringUtils.isEmpty(recordList.get(0).getBorrowApr()) ? "0.00" : recordList.get(0).getBorrowApr().replace("%", "");
                        BigDecimal borrowApr = new BigDecimal(borrowAprString);
                        //出借金额
                        String accountString = StringUtils.isEmpty(recordList.get(0).getAccount()) ? "0.00" : recordList.get(0).getAccount().replace(",", "");
                        BigDecimal account = new BigDecimal(accountString);
                        // 周期
                        String borrowPeriodString = StringUtils.isEmpty(recordList.get(0).getBorrowPeriod()) ? "0" : recordList.get(0).getBorrowPeriod();
                        String regEx = "[^0-9]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(borrowPeriodString);
                        borrowPeriodString = m.replaceAll("").trim();
                        Integer borrowPeriod = Integer.valueOf(borrowPeriodString);
                        if (org.apache.commons.lang3.StringUtils.equals("endday", borrowStyle)) {
                            // 还款方式为”按天计息，到期还本还息“：预期收益=出借金额*出借利率÷365*锁定期；
                            earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                        } else {
                            // 还款方式为”按月计息，到期还本还息“：预期收益=出借金额*出借利率÷12*月数；
                            earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                        }
                        contents.put("earnings", earnings);
                        if ("month".equals(borrowStyle) || "principal".equals(borrowStyle) || "endmonth".equals(borrowStyle)) {
                            int recordTotal = amTradeClient.countProjectRepayPlanRecordTotal(borrowInvestRequest);
                            if (recordTotal > 0) {
                                Paginator paginator = new Paginator(1, recordTotal);
                                List<WebProjectRepayListCustomizeVO> repayList = amTradeClient.selectProjectRepayPlanList(
                                        borrowInvestRequest);
                                contents.put("paginator", paginator);
                                contents.put("repayList", repayList);
                            } else {
                                Paginator paginator = new Paginator(1, recordTotal);
                                contents.put("paginator", paginator);
                                contents.put("repayList", "");
                            }
                        }
                    }
                    String pdfUrl = "";
                    // 融通宝协议
                    if ("13".equals(recordList.get(0).getProjectType())) {
                        UserInfoVO userinfo = amUserClient.findUsersInfoById(Integer.valueOf(userId));
                        if (tzList != null && tzList.size() > 0) {
                            contents.put("investDeatil", tzList.get(0));
                        }
                        contents.put("projectDeatil", recordList.get(0));
                        contents.put("truename", userinfo.getTruename());
                        contents.put("idcard", userinfo.getIdcard());
                        contents.put("borrowNid", borrowNid);
                        contents.put("nid", nid);
                        contents.put("assetNumber", recordList.get(0).getBorrowAssetNumber());
                        contents.put("projectType", recordList.get(0).getProjectType());
                        String fileType = CustomConstants.RTB_TENDER_CONTRACT;
                        if (recordList.get(0) != null && recordList.get(0).getBorrowPublisher() != null
                                && "中商储".equals(recordList.get(0).getBorrowPublisher())) {
                            fileType = CustomConstants.RTB_TENDER_CONTRACT_ZSC;
                        }
                        if (recordList.get(0) != null) {
                            recordList.get(0).setBorrowPeriod(
                                    recordList.get(0).getBorrowPeriod()
                                            .substring(0, recordList.get(0).getBorrowPeriod().length() - 1));
                        }
                        pdfUrl = pdfGenerator.generateLocal(fileName, fileType, contents);
                    } else {
                        //该标的与计划关联，应发计划的居间TENDER_CONTRACT 原代码的判断没意义 发送的都是一种协议
//                        if (borrow.getPlanNid() != null) {
//                            System.out.println("计划的居间协议");
//                            pdfUrl = pdfGenerator.generateLocal(fileName, CustomConstants.TENDER_CONTRACT, contents);
//                        } else {
//                            System.out.println("散标的居间协议");
//                            pdfUrl = pdfGenerator.generateLocal(fileName, CustomConstants.TENDER_CONTRACT, contents);
//                        }
                        pdfUrl = pdfGenerator.generateLocal(fileName, CustomConstants.TENDER_CONTRACT, contents);
                    }
                    if (StringUtils.isNotEmpty(pdfUrl)) {
                        File path = new File(filePath);
                        if (!path.exists()) {
                            path.mkdirs();
                        }
                        FileUtil.getRemoteFile(pdfUrl.substring(0, pdfUrl.length() - 1), filePath + fileName);
                    }
                    String[] emails = {email};
                    MailMessage message = new MailMessage(null, msg, "汇盈金服互联网金融服务平台居间服务协议", null,
                            new String[]{filePath + fileName}, emails, CustomConstants.EMAILPARAM_TPL_LOANS,
                            MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
                    //发送邮件MQ
                    commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), message));
                    int updateResult = amTradeClient.updateBorrowRecover(borrowInvestRequest);
                    if (updateResult > 0) {
                        return null;
                    } else {
                        return "发送邮件失败，请重试";
                    }
                } else {
                    logger.error("出借明细发送协议失败, 未查询到出借明细，出借订单号:"+ nid);
                    return "出借明细发送协议失败，未查询到出借明细";
                }
            } else {
                logger.error("出借明细发送协议失败，出借订单号：" + nid);
                return "出借明细发送协议失败";
            }
        } catch (Exception e) {
            logger.error("发送协议失败:", e);
            return "系统异常";
        }
    }

    /**
     *
     * @param investorRequest
     * @param resendFlag 是否重发标识 0否 1是
     * @return
     */
    private String checkSendAgreement(InvestorRequest investorRequest, int resendFlag) {
        //非重发的情况，校验邮箱
        if(0 == resendFlag){
            //校验邮箱地址
            if (StringUtils.isBlank(investorRequest.getEmail())) {
                return "邮箱地址为空！";
            }
            if (!Validator.isEmailAddress(investorRequest.getEmail())) {
                return "邮箱格式不正确！";
            }
        }
        //校验出借订单号与标的编号
        if (StringUtils.isBlank(investorRequest.getNid()) || StringUtils.isBlank(investorRequest.getBorrowNid())) {
            return "传递参数不正确！";
        }
        //校验用户ID
        if (StringUtils.isBlank(investorRequest.getUserId())) {
            return "用户ID为空！";
        }
        try {
            Integer.parseInt(investorRequest.getUserId());
        } catch (NumberFormatException e) {
            return "用户ID不是数字！";
        }
        return OK;
    }
}
