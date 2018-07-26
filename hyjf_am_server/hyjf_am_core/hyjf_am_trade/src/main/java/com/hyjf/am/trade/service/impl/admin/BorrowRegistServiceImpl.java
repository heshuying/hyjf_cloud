/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.admin.BorrowRegistCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.trade.service.admin.BorrowRegistService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 16:54
 */
@Service
public class BorrowRegistServiceImpl extends BaseServiceImpl implements BorrowRegistService {
    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    private BorrowStyleMapper borrowStyleMapper;

    @Autowired
    private BorrowRegistCustomizeMapper borrowRegistCustomizeMapper;

    @Autowired
    private StzhWhiteListMapper stzhWhiteListMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private RUserMapper rUserMapper;

    @Autowired
    private AccountService accountService;

    /**
     * 获取项目类型
     *
     * @return
     */
    @Override
    public List<BorrowProjectType> selectBorrowProjectList() {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        // 不查询融通宝相关
        cra.andBorrowNameNotEqualTo(CustomConstants.RTB);

        return borrowProjectTypeMapper.selectByExample(example);
    }

    /**
     * 获取还款方式
     *
     * @return
     */
    @Override
    public List<BorrowStyle> selectBorrowStyleList() {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));

        return borrowStyleMapper.selectByExample(example);
    }

    /**
     * 获取标的备案列表count
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistCustomizeMapper.getRegistCount(borrowRegistListRequest);
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        List<BorrowRegistCustomize> list = borrowRegistCustomizeMapper.selectBorrowRegistList(borrowRegistListRequest);
        if (!CollectionUtils.isEmpty(list)) {
            //处理标的备案状态
            Map<String, String> map = CacheUtil.getParamNameMap("REGIST_STATUS");
            list.forEach((borrowRegistCustomize) -> borrowRegistCustomize.setRegistStatusName(map.getOrDefault(borrowRegistCustomize.getRegistStatus(), null)));
        }
        return list;
    }

    /**
     * 统计总额
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest) {
        return borrowRegistCustomizeMapper.sumBorrowRegistAccount(borrowRegistListRequest);
    }


    /**
     * 标的备案
     * @param borrowNid
     * @param currUserId
     * @param currUserName
     * @return
     */
    @Override
    public Response debtRegist(String borrowNid, String currUserId, String currUserName){
        Response result = new Response();
        // 获取相应的标的详情
        Borrow borrow = this.getBorrow(borrowNid);
        BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
        if (borrow != null && borrowInfo != null) {
            //項目还款方式
            String borrowStyle = borrow.getBorrowStyle();
            //是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            int userId = borrow.getUserId();
            RUser user = rUserMapper.selectByPrimaryKey(userId);
            if (Validator.isNotNull(user)) {
                //用account表中的account_id代替原代码中bank_open_account的account
                Account bankOpenAccount = accountService.getAccount(userId);
                if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
                    // 更新相应的标的状态为备案中
                    boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1, currUserId, currUserName);
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
                        debtRegistBean.setAccountId(bankOpenAccount.getAccountId());
                        // 标的表id
                        debtRegistBean.setProductId(borrowNid);
                        // 标的名称
                        debtRegistBean.setProductDesc(borrowInfo.getName());
                        // 募集日,标的保存时间
                        debtRegistBean.setRaiseDate(borrowInfo.getBankRaiseStartDate());
                        // 募集结束日期
                        debtRegistBean.setRaiseEndDate(borrowInfo.getBankRaiseEndDate());
                        if (isMonth) {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);
                        } else {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
                        }
                        // (借款期限,天数）
                        debtRegistBean.setDuration(String.valueOf(borrowInfo.getBankBorrowDays()));
                        // 交易金额
                        debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));
                        // 年化利率
                        debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));
                        //如果有担保机构ID，则查询担保机构账户
                        if (Validator.isNotNull(borrowInfo.getRepayOrgUserId())) {
                            //用account表中的account_id代替原代码中bank_open_account的account
                            Account account = accountService.getAccount(borrowInfo.getRepayOrgUserId());
                            if (Validator.isNotNull(account) && StringUtils.isNotBlank(account.getAccountId())) {
                                debtRegistBean.setBailAccountId(account.getAccountId());
                            }
                        }
                        debtRegistBean.setLogOrderId(orderId);
                        debtRegistBean.setLogOrderDate(orderDate);
                        debtRegistBean.setLogUserId(String.valueOf(user.getUserId()));
                        debtRegistBean.setLogRemark("借款人标的登记");
                        debtRegistBean.setLogClient(0);

                        //备案接口(EntrustFlag和ReceiptAccountId要么都传，要么都不传)
                        if (borrowInfo.getEntrustedFlg() == 1) {
                            //查询受托支付记录
                            StzhWhiteList stzhWhiteList = this.selectStzfWhiteList(borrowInfo.getInstCode().trim(), String.valueOf(borrowInfo.getEntrustedUserId()));
                            if (stzhWhiteList != null) {
                                debtRegistBean.setEntrustFlag(borrowInfo.getEntrustedFlg().toString());
                                debtRegistBean.setReceiptAccountId(stzhWhiteList.getStAccountid());
                            } else {
                                this.updateBorrowRegist(borrow, 0, 4, currUserId, currUserName);
                                return new Response(Response.FAIL, "受托白名单查询为空！");
                            }
                        }
                        try {
                            //调用银行接口
                            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                            String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                                //受托支付备案
                                if (borrowInfo.getEntrustedFlg() == 1) {
                                    boolean debtEntrustedRegistedFlag = this.updateEntrustedBorrowRegist(borrow, 7, 2, currUserId, currUserName);
                                    if (debtEntrustedRegistedFlag) {
                                        result.setRtn(Response.SUCCESS);
                                        result.setMessage("备案成功！");
                                    } else {
                                        result.setRtn(Response.FAIL);
                                        result.setMessage("备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                } else {
                                    boolean debtRegistedFlag = this.updateBorrowRegist(borrow, 1, 2, currUserId, currUserName);
                                    if (debtRegistedFlag) {
                                        result.setRtn(Response.SUCCESS);
                                        result.setMessage("备案成功！");
                                    } else {
                                        result.setRtn(Response.FAIL);
                                        result.setMessage("备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                }
                            } else {
                                this.updateBorrowRegist(borrow, 0, 4, currUserId, currUserName);
                                String message = registResult.getRetMsg();
                                result.setRtn(Response.FAIL);
                                result.setMessage(StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                            }
                        } catch (Exception e) {
                            logger.error("标的备案失败,编号："+ borrow.getBorrowNid(),e);
                            this.updateBorrowRegist(borrow, 0, 4, currUserId, currUserName);
                            result.setRtn(Response.FAIL);
                            result.setMessage("银行备案接口调用失败！");
                        }
                    } else {
                        result.setRtn(Response.FAIL);
                        result.setMessage("更新相应的标的信息失败,请稍后再试！");
                    }
                } else {
                    result.setRtn(Response.FAIL);
                    result.setMessage("未查询到开户信息！");
                }
            } else {
                result.setRtn(Response.FAIL);
                result.setMessage("借款人信息错误！");
            }
        } else {
            result.setRtn(Response.FAIL);
            result.setMessage("未查询到相应标的信息！");
        }
        return result;
    }

    private StzhWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId) {
        StzhWhiteListExample example = new StzhWhiteListExample();
        StzhWhiteListExample.Criteria crt = example.createCriteria();
        crt.andStAccountidEqualTo(entrustedAccountId);
        crt.andInstcodeEqualTo(instCode);
        crt.andDelFlagEqualTo(0);
        crt.andStateEqualTo(1);
        List<StzhWhiteList> list = this.stzhWhiteListMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新相应的标的信息
     * @param borrow
     * @param status
     * @param registStatus
     * @param currUserId
     * @param currUserName
     * @return
     */
    private boolean updateBorrowRegist(Borrow borrow, int status, int registStatus, String currUserId, String currUserName) {
        Date nowDate = new Date();
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrow.getId()).andStatusEqualTo(borrow.getStatus()).andRegistStatusEqualTo(borrow.getRegistStatus());
        borrow.setRegistStatus(registStatus);
        borrow.setStatus(status);
        borrow.setRegistUserId(Integer.parseInt(currUserId));
        borrow.setRegistUserName(currUserName);
        borrow.setRegistTime(nowDate);
        boolean flag = this.borrowMapper.updateByExampleSelective(borrow, example) > 0 ? true : false;
        return flag;
    }

    /**
     * 更新标的信息(受托支付备案)
     * @param borrow
     * @param status
     * @param registStatus
     * @param currUserId
     * @param currUserName
     * @return
     */
    private boolean updateEntrustedBorrowRegist(Borrow borrow, int status, int registStatus, String currUserId, String currUserName) {
        Date nowDate = new Date();
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrow.getId());
        borrow.setRegistStatus(registStatus);
        borrow.setStatus(status);
        borrow.setRegistUserId(Integer.parseInt(currUserId));
        borrow.setRegistUserName(currUserName);
        borrow.setRegistTime(nowDate);
        boolean flag = this.borrowMapper.updateByExampleSelective(borrow, example) > 0 ? true : false;
        return flag;
    }
}
