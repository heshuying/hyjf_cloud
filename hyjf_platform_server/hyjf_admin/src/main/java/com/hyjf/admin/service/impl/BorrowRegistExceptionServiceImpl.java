/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BorrowRegistExceptionService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionServiceImpl, v0.1 2018/7/3 14:52
 */
@Service
public class BorrowRegistExceptionServiceImpl extends BaseServiceImpl implements BorrowRegistExceptionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hyjf.bank.instcode}")
    private String BANK_INSTCODE;

    @Value("${hyjf.bank.bankcode}")
    private String BANK_BANKCODE;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 获取项目类型list,用于筛选条件展示
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList(){
        return amTradeClient.selectBorrowProjectList();
    }

    /**
     * 获取还款方式list,用于筛选条件展示
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<BorrowStyleVO> selectBorrowStyleList(){
        return amTradeClient.selectBorrowStyleList();
    }

    /**
     * 获取标的列表count,用于前端分页显示条数
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest){
        return amTradeClient.getRegistCount(borrowRegistListRequest);
    }

    /**
     * 获取标的备案异常列表
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    @Override
    public List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest){
        return amTradeClient.selectBorrowRegistList(borrowRegistListRequest);
    }

    /**
     * 备案异常处理
     * @auth sunpeikai
     * @param borrowNid 项目编号
     * @param loginUserId 当前登录用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject handleBorrowRegistException(String borrowNid, Integer loginUserId) {
        JSONObject result = new JSONObject();
        BorrowAndInfoVO borrowVO = amTradeClient.searchBorrowByBorrowNid(borrowNid);
        if(null != borrowVO){
            AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
            // 操作用户id
            borrowVO.setRegistUserId(Integer.valueOf(adminSystemVO.getId()));
            // 操作用户userName
            borrowVO.setRegistUserName(adminSystemVO.getUsername());

            Integer borrowUserId = borrowVO.getUserId();
            logger.info("handleBorrowRegistException::::::::borrowUserId=======[{}]",borrowUserId);
            BankOpenAccountVO bankOpenAccountVO = amUserClient.searchBankOpenAccount(borrowUserId);
            if(null != bankOpenAccountVO){
                logger.info("查询出来借款人银行账户::::::::[{}]",bankOpenAccountVO.getAccount());
                // 項目还款方式
                String borrowStyle = borrowVO.getBorrowStyle();
                // 是否月标(true:月标, false:天标)
                boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                        || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                // 借款人银行账户
                String accountId = bankOpenAccountVO.getAccount();
                // 查询相应的标的备案状态
                logger.info("标的备案异常，borrowNid:[{}],accountId:[{}],loginUserId:[{}]",borrowNid,accountId,loginUserId);
                BankCallBean searchResult = this.borrowRegistSearch(borrowNid, accountId, loginUserId);
                logger.info("银行返回报文:【{}】",searchResult);
                if (Validator.isNotNull(searchResult)) {
                    String searchRetCode = StringUtils.isNotBlank(searchResult.getRetCode()) ? searchResult.getRetCode() : "";
                    logger.info("银行返回报文searchRetCode:[{}]",searchRetCode);
                    // 如果返回成功
                    if (BankCallConstant.RESPCODE_SUCCESS.equals(searchRetCode)) {
                        String subPacks = searchResult.getSubPacks();
                        logger.info("subPacks:[{}]",subPacks);
                        if (StringUtils.isNotBlank(subPacks)) {
                            logger.debug("subPacks不为空");
                            JSONArray debtDetails = JSONObject.parseArray(subPacks);
                            if (debtDetails != null) {
                                logger.debug("debtDetails不为空");
                                if (debtDetails.size() == 0) {
                                    logger.debug("debtDetails.size == 0");
                                    // 更新相应的标的状态为备案中
                                    boolean debtRegistingFlag = this.updateBorrowRegist(borrowVO, 0, 1,1);
                                    if (debtRegistingFlag) {
                                        logger.debug("debtRegistingFlag=[{}]",debtRegistingFlag);
                                        try {
                                            //modify by 受托支付
                                            BankCallBean registResult = this.borrowRegist(borrowVO, isMonth, bankOpenAccountVO.getAccount(), loginUserId);
                                            if (Validator.isNotNull(registResult)) {
                                                logger.debug("registResult不为空");
                                                String registRetCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                                                if (BankCallConstant.RESPCODE_SUCCESS.equals(registRetCode)) {
                                                    logger.debug("registRetCode是成功状态");
                                                    //new added by 受托支付备案
                                                    if(borrowVO.getEntrustedFlg()==1){
                                                        logger.debug("受托支付备案状态==1");
                                                        boolean debtEntrustedRegistedFlag = this.updateBorrowRegist(borrowVO, 7, 2,2);
                                                        if (debtEntrustedRegistedFlag) {
                                                            logger.debug("备案成功");
                                                            result.put("success", "0");
                                                            result.put("msg", "备案成功！");
                                                            // 更新标的资产信息如果关联计划的话
                                                            //受托支付传4
                                                            amTradeClient.updateBorrowAsset(borrowVO,4);
                                                        } else {
                                                            logger.debug("@@1@@");
                                                            result.put("success", "1");
                                                            result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                                        }
                                                    } else {
                                                        boolean debtRegistedFlag = this.updateBorrowRegist(borrowVO, 1, 2,1);
                                                        if (debtRegistedFlag) {
                                                            logger.debug("备案成功");
                                                            result.put("success", "0");
                                                            result.put("msg", "备案成功！");
                                                            // 更新标的资产信息如果关联计划的话
                                                            //非受托支付5
                                                            amTradeClient.updateBorrowAsset(borrowVO,5);

                                                        } else {
                                                            logger.debug("@@2@@");
                                                            result.put("success", "1");
                                                            result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                                        }
                                                    }
                                                } else {
                                                    logger.debug("@@3@@");
                                                    this.updateBorrowRegist(borrowVO, 0, 4,1);
                                                    String message = registResult.getRetMsg();
                                                    result.put("success", "1");
                                                    result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                                                }
                                            } else {
                                                logger.debug("@@4@@");
                                                result.put("success", "1");
                                                result.put("msg", "银行备案接口调用失败！");
                                            }
                                        } catch (Exception e) {
                                            logger.debug("@@5@@");
                                            logger.error(e.getMessage());
                                            this.updateBorrowRegist(borrowVO, 0, 4,1);
                                            result.put("success", "1");
                                            result.put("msg", "银行备案接口调用失败！");
                                        }
                                    } else {
                                        logger.debug("@@6@@");
                                        result.put("success", "1");
                                        result.put("msg", "更新相应的标的信息失败,请稍后再试！");
                                    }
                                }else if (debtDetails.size() == 1) {
                                    logger.debug("debtDetails.size == 1");
                                    JSONObject debtDetail = debtDetails.getJSONObject(0);
                                    // 这里state是2
                                    String state = debtDetail.getString(BankCallConstant.PARAM_STATE);
                                    if ("9".equals(state)) {
                                        logger.debug("@@7@@");
                                        // 2备案状态已撤销直接更改标的状态为:流标,撤销备案
                                        boolean debtRegistedNewFlag = this.updateBorrowRegist(borrowVO, 6, 3,1);
                                        if (debtRegistedNewFlag) {
                                            logger.debug("@@8@@");
                                            result.put("success", "0");
                                            result.put("msg", "备案已经撤销！");
                                        } else {
                                            logger.debug("@@9@@");
                                            result.put("success", "1");
                                            result.put("msg", "备案已经撤销后，更新相应的状态失败,请联系客服！");
                                        }
                                    } else if ("8".equals(state)) {
                                        logger.debug("@@10@@");
                                        // 2备案处理中更改备案状态为备案中
                                        boolean debtRegistedNewFlag = this.updateBorrowRegist(borrowVO, 0, 1,1);
                                        if (debtRegistedNewFlag) {
                                            logger.debug("@@11@@");
                                            result.put("success", "0");
                                            result.put("msg", "银行备案处理中！");
                                        } else {
                                            logger.debug("@@12@@");
                                            result.put("success", "1");
                                            result.put("msg", "银行备案处理中，更新相应的状态失败,请联系客服！");
                                        }
                                    } else if ("1".equals(state)) {
                                        logger.debug("@@13@@");
                                        // 2备案成功直接更改备案状态:银行确认state为1的情况,银行标的备案成功
                                        //new added by 受托支付备案
                                        if(1==borrowVO.getEntrustedFlg()){
                                            logger.debug("@@14@@");
                                            boolean debtEntrustedRegistedFlag = this.updateBorrowRegist(borrowVO, 7, 2,2);
                                            if (debtEntrustedRegistedFlag) {
                                                logger.debug("@@15@@");
                                                result.put("success", "0");
                                                result.put("msg", "备案成功！");
                                                //受托支付传4
                                                amTradeClient.updateBorrowAsset(borrowVO,4);
                                            } else {
                                                logger.debug("@@16@@");
                                                result.put("success", "1");
                                                result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                            }
                                        } else {
                                            logger.debug("@@17@@");
                                            boolean debtRegistedNewFlag = this.updateBorrowRegist(borrowVO, 1, 2,1);
                                            if (debtRegistedNewFlag) {
                                                logger.debug("@@18@@");
                                                result.put("success", "0");
                                                result.put("msg", "备案成功！");
                                                // 更新标的资产信息如果关联计划的话
                                                // 非受托支付传5
                                                amTradeClient.updateBorrowAsset(borrowVO,5);
                                            } else {
                                                logger.debug("@@19@@");
                                                result.put("success", "1");
                                                result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                            }
                                        }
                                    }else{
                                        result.put("success", "1");
                                        result.put("msg", "在银行方此标的状态为:" + state + "，暂时无法备案");
                                    }
                                } else {
                                    logger.debug("@@20@@");
                                    result.put("success", "1");
                                    result.put("msg", "银行端标号重复,请联系客服！");
                                }
                            } else {
                                logger.debug("@@21@@");
                                // 更新相应的标的状态为备案中
                                boolean debtRegistingFlag = this.updateBorrowRegist(borrowVO, 0, 1,1);
                                if (debtRegistingFlag) {
                                    logger.debug("@@22@@");
                                    try {
                                        BankCallBean registResult = this.borrowRegist(borrowVO, isMonth, bankOpenAccountVO.getAccount(), loginUserId);
                                        if (Validator.isNotNull(registResult)) {
                                            logger.debug("@@23@@");
                                            String registRetCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                                            if (BankCallConstant.RESPCODE_SUCCESS.equals(registRetCode)) {
                                                logger.debug("@@24@@");
                                                boolean debtRegistedFlag = this.updateBorrowRegist(borrowVO, 1, 2,1);
                                                if (debtRegistedFlag) {
                                                    logger.debug("@@25@@");
                                                    result.put("success", "0");
                                                    result.put("msg", "备案成功！");
                                                } else {
                                                    logger.debug("@@26@@");
                                                    result.put("success", "1");
                                                    result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                                }
                                            } else {
                                                logger.debug("@@27@@");
                                                this.updateBorrowRegist(borrowVO, 0, 4,1);
                                                String message = registResult.getRetMsg();
                                                result.put("success", "1");
                                                result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                                            }
                                        } else {
                                            logger.debug("@@28@@");
                                            result.put("success", "1");
                                            result.put("msg", "银行备案接口调用失败！");
                                        }
                                    } catch (Exception e) {
                                        logger.debug("@@29@@");
                                        logger.error(e.getMessage());
                                        this.updateBorrowRegist(borrowVO, 0, 4,1);
                                        result.put("success", "1");
                                        result.put("msg", "银行备案接口调用失败！");
                                    }
                                } else {
                                    logger.debug("@@30@@");
                                    result.put("success", "1");
                                    result.put("msg", "更新相应的标的信息失败,请稍后再试！");
                                }
                            }
                        } else {
                            logger.debug("@@31@@");
                            result.put("success", "1");
                            result.put("msg", "查询标的备案状态失败,请联系客服！");
                        }
                    } else {
                        logger.debug("@@32@@");
                        result.put("success", "1");
                        result.put("msg", "查询标的备案状态失败,请联系客服！");
                    }
                } else {
                    logger.debug("@@33@@");
                    result.put("success", "1");
                    result.put("msg", "查询标的备案状态失败,请联系客服！");
                }
            }else{
                logger.debug("@@34@@");
                result.put("success", "1");
                result.put("msg", "未查询到借款人开户信息！");
            }
        }else{
            logger.debug("@@35@@");
            result.put("success", "1");
            result.put("msg", "项目编号为空！");
        }


        return result;
    }

    /**
     * 查询相应的标的备案状态
     * @param borrowNid 标的id
     * @param accountId 银行账户
     * @param loginUserId 登录用户id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BankCallBean borrowRegistSearch(String borrowNid, String accountId, int loginUserId) {
        // 获取共同参数
        String channel = BankCallConstant.CHANNEL_PC;
        String orderId = GetOrderIdUtils.getOrderId2(loginUserId);
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 调用开户接口
        BankCallBean debtRegistBean = new BankCallBean();
        debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_DETAILS_QUERY);// 消息类型(借款人标的信息查询)
        debtRegistBean.setInstCode(this.BANK_INSTCODE);// 机构代码
        debtRegistBean.setBankCode(this.BANK_BANKCODE);
        debtRegistBean.setTxDate(txDate);
        debtRegistBean.setTxTime(txTime);
        debtRegistBean.setSeqNo(seqNo);
        debtRegistBean.setChannel(channel);
        debtRegistBean.setAccountId(accountId);// 借款人电子账号
        debtRegistBean.setProductId(borrowNid);// 标的表id
        debtRegistBean.setPageNum("1");
        debtRegistBean.setPageSize("10");
        debtRegistBean.setLogOrderId(orderId);
        debtRegistBean.setLogOrderDate(orderDate);
        debtRegistBean.setLogUserId(String.valueOf(loginUserId));
        debtRegistBean.setLogRemark("借款人标的登记");
        debtRegistBean.setLogClient(0);
        try {
            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
            return registResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 标的备案
     *
     * @param borrow
     * @param isMonth
     * @param accountId
     * @param loginUserId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BankCallBean borrowRegist(BorrowAndInfoVO borrow, boolean isMonth, String accountId, int loginUserId) {
        // 获取共同参数
        String channel = BankCallConstant.CHANNEL_PC;
        String orderId = GetOrderIdUtils.getOrderId2(loginUserId);
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 调用开户接口
        BankCallBean debtRegistBean = new BankCallBean();
        debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);// 消息类型(用户开户)
        debtRegistBean.setInstCode(this.BANK_INSTCODE);// 机构代码
        debtRegistBean.setBankCode(this.BANK_BANKCODE);
        debtRegistBean.setTxDate(txDate);
        debtRegistBean.setTxTime(txTime);
        debtRegistBean.setSeqNo(seqNo);
        debtRegistBean.setChannel(channel);
        debtRegistBean.setAccountId(accountId);// 借款人电子账号
        debtRegistBean.setProductId(borrow.getBorrowNid());// 标的表id
        debtRegistBean.setProductDesc(borrow.getName());// 标的名称
        debtRegistBean.setRaiseDate(borrow.getBankRaiseStartDate());// 募集日,标的保存时间
        debtRegistBean.setRaiseEndDate(borrow.getBankRaiseEndDate());// 募集结束日期
        if (isMonth) {
            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);// 付息方式没有不确定日期
        } else {
            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
        }
        debtRegistBean.setDuration(String.valueOf(borrow.getBankBorrowDays()));// (借款期限,天数）
        debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));// 交易金额
        debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));// 年华利率
        if (Validator.isNotNull(borrow.getRepayOrgUserId())) {
            BankOpenAccountVO account = amUserClient.searchBankOpenAccount(borrow.getRepayOrgUserId());
            if (Validator.isNotNull(account)) {
                debtRegistBean.setBailAccountId(account.getAccount());
            }
        }
        debtRegistBean.setLogOrderId(orderId);
        debtRegistBean.setLogOrderDate(orderDate);
        debtRegistBean.setLogUserId(String.valueOf(loginUserId));
        debtRegistBean.setLogRemark("借款人标的登记");
        debtRegistBean.setLogClient(0);
        //备案接口(EntrustFlag和ReceiptAccountId要么都传，要么都不传)
        if(borrow.getEntrustedFlg()==1){
            String stAccountId = amTradeClient.getStAccountIdByEntrustedUserId(borrow.getEntrustedUserId());
            debtRegistBean.setEntrustFlag(borrow.getEntrustedFlg().toString());
            debtRegistBean.setReceiptAccountId(stAccountId);
        }
        try {
            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
            return registResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /**
     * 更新标的备案
     * @auth sunpeikai
     * @param type 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBorrowRegist(BorrowAndInfoVO borrow, int status, int registStatus,Integer type){
        BorrowRegistUpdateRequest registUpdateRequest = new BorrowRegistUpdateRequest();
        registUpdateRequest.setBorrowVO(borrow);
        registUpdateRequest.setStatus(status);
        registUpdateRequest.setRegistStatus(registStatus);
        registUpdateRequest.setType(type);
        return amTradeClient.updateBorrowRegistException(registUpdateRequest);
    }
}
