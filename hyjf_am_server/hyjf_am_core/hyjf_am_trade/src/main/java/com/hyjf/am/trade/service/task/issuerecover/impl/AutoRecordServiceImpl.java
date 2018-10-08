package com.hyjf.am.trade.service.task.issuerecover.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.task.issuerecover.AutoRecordService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 汇计划自动发标修复
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 16:30
 */
@Service
public class AutoRecordServiceImpl implements AutoRecordService {
    private static final Logger logger = LoggerFactory.getLogger(AutoRecordServiceImpl.class);

    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BorrowInfoMapper borrowInfoMapper;
    @Resource
    private HjhAssetBorrowtypeMapper hjhAssetBorrowtypeMapper;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private StzhWhiteListMapper stzhWhiteListMapper;
    @Resource
    private SmsProducer smsProducer;
    @Resource
    private HjhPlanAssetMapper hjhPlanAssetMapper;
    @Override
    public Borrow getBorrowByBorrowNid(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = borrowMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public HjhAssetBorrowtype selectAssetBorrowType(BorrowInfo borrowInfo) {
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(borrowInfo.getInstCode());
        cra.andAssetTypeEqualTo(borrowInfo.getAssetType());
        cra.andIsOpenEqualTo(1);

        List<HjhAssetBorrowtype> list = this.hjhAssetBorrowtypeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public BorrowInfo getBorrowInfoById(String borrowNid) {
        BorrowInfoExample borrowExample = new BorrowInfoExample();
        BorrowInfoExample.Criteria borrowCra = borrowExample.createCriteria();
        borrowCra.andBorrowNidEqualTo(borrowNid);
        List<BorrowInfo> list = borrowInfoMapper.selectByExample(borrowExample);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean updateRecordBorrow(Borrow borrow,BorrowInfo borrowInfo) {
        // 备案，需要更新资产表
        JSONObject jsonObject = debtRegist(borrow,borrowInfo);

        //更新资产表为初审中
        if("0".equals(jsonObject.get("success"))){
            return true;
            // 重复失败的情况
        }else if("2".equals(jsonObject.get("success"))) {
            logger.info("备案失败 "+borrow.getBorrowNid() + " 原因："+jsonObject.get("msg"));
        }else{
            logger.info("备案失败 "+borrow.getBorrowNid()+ " 原因："+jsonObject.get("msg"));
            // 备案失败发送短信//TODO: 没有配合模板
            // 您好，有一笔标的备案失败，请及时关注！
            Map<String, String> replaceStrs = new HashMap<String, String>();
            replaceStrs.put("val_title", borrow.getBorrowNid());

            SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_NOTICE_BORROW_RECORD_FAIL, CustomConstants.CHANNEL_TYPE_NORMAL);

            try {
                smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
            } catch (MQException e2) {
                logger.error("发送备案失败短信失败..", e2);
            }
        }

        return false;
    }

    @Override
    public boolean updateRecordBorrow(HjhPlanAsset mqHjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType) {
        // 备案，需要更新资产表
        JSONObject jsonObject = debtRegist(mqHjhPlanAsset.getBorrowNid());

        //更新资产表为初审中
        if("0".equals(jsonObject.get("success"))){

            HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
            hjhPlanAssetnew.setId(mqHjhPlanAsset.getId());

            // 受托支付，更新为待授权
            if(mqHjhPlanAsset.getEntrustedFlg() != null && mqHjhPlanAsset.getEntrustedFlg().intValue() ==1){
                hjhPlanAssetnew.setStatus(4);//4 待授权
            }else{
                hjhPlanAssetnew.setStatus(5);//初审中
            }
            //获取当前时间
            int nowTime = GetDate.getNowTime10();
            hjhPlanAssetnew.setUpdateTime(new Date());
            hjhPlanAssetnew.setUpdateUserId(1);
            boolean borrowFlag = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew)>0?true:false;
            if(borrowFlag){
                return true;
            }

            // 重复失败的情况
        }else if("2".equals(jsonObject.get("success"))) {
            logger.info("备案失败 "+mqHjhPlanAsset.getBorrowNid() + " 原因："+jsonObject.get("msg"));
        }else{
            logger.info("备案失败 "+mqHjhPlanAsset.getBorrowNid()+ " 原因："+jsonObject.get("msg"));
            // 备案失败发送短信//TODO: 没有配合模板
            // 您好，有一笔标的备案失败，请及时关注！
           try{
               Map<String, String> replaceStrs = new HashMap<String, String>();
               replaceStrs.put("val_title", mqHjhPlanAsset.getBorrowNid());
               SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_NOTICE_BORROW_RECORD_FAIL,
                       CustomConstants.CHANNEL_TYPE_NORMAL);
               smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                       UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
           }catch (Exception e){
               logger.info("备案失败 "+mqHjhPlanAsset.getBorrowNid()+ " 原因："+jsonObject.get("msg")+",消息发送失败！");
           }
        }
        return false;
    }

    /**
     * 备案
     * @param borrowNid
     * @return
     */
    public JSONObject debtRegist(String borrowNid) {
        // 返回结果
        JSONObject result = new JSONObject();
        // 获取相应的标的详情
        BorrowExample borrowExample = new BorrowExample();
        BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
        borrowCra.andBorrowNidEqualTo(borrowNid);
        List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);// 获取相应的标的信息
        if (borrowList != null && borrowList.size() == 1) {
            Borrow borrow = borrowList.get(0);
            BorrowInfo borrowInfo = getBorrowInfoById(borrow.getBorrowNid());
            // 检查是否备案失败，如果是，跳过
            if(borrow.getStatus()==0 && borrow.getRegistStatus()==4){
                logger.info("标的："+borrowNid+" 自动备案失败过");
                result.put("success", "2");
                result.put("msg", "自动备案失败过！");
                return result;
            }


            String borrowStyle = borrow.getBorrowStyle();// 項目还款方式
            // 是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            int userId = borrow.getUserId();// 借款人userId
            AccountExample example = new AccountExample();
            AccountExample.Criteria cra = example.createCriteria();
            cra.andUserIdEqualTo(userId);
            List<Account> accountList = accountMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(accountList)) {
                Account bankOpenAccount = accountList.get(0);
                if (Validator.isNotNull(bankOpenAccount)) {
                    // 更新相应的标的状态为备案中
                    boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1);
                    if (debtRegistingFlag) {
                        // 获取共同参数
//                        String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
//                        String instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
                        String channel = BankCallConstant.CHANNEL_PC;
                        String orderId = GetOrderIdUtils.getOrderId2(userId);
                        String orderDate = GetOrderIdUtils.getOrderDate();
                        String txDate = GetOrderIdUtils.getTxDate();
                        String txTime = GetOrderIdUtils.getTxTime();
                        String seqNo = GetOrderIdUtils.getSeqNo(6);
                        // 调用开户接口
                        BankCallBean debtRegistBean = new BankCallBean();
                        debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);// 消息类型(用户开户)
//                        debtRegistBean.setInstCode(instCode);// 机构代码
//                        debtRegistBean.setBankCode(bankCode);
                        debtRegistBean.setTxDate(txDate);
                        debtRegistBean.setTxTime(txTime);
                        debtRegistBean.setSeqNo(seqNo);
                        debtRegistBean.setChannel(channel);
                        debtRegistBean.setAccountId(bankOpenAccount.getAccountId());// 借款人电子账号
                        debtRegistBean.setProductId(borrowNid);// 标的表id
                        debtRegistBean.setProductDesc(borrowInfo.getName());// 标的名称
                        debtRegistBean.setRaiseDate(borrowInfo.getBankRaiseStartDate());// 募集日,标的保存时间
                        debtRegistBean.setRaiseEndDate(borrowInfo.getBankRaiseEndDate());// 募集结束日期
                        if (isMonth) {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);// 付息方式没有不确定日期
                        } else {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
                        }
                        debtRegistBean.setDuration(String.valueOf(borrowInfo.getBankBorrowDays()));// (借款期限,天数）
                        debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));// 交易金额
                        debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));// 年华利率

                        // 受托支付
						/*receiptAccountId	收款人电子帐户	A	19	C	当entrustFlag不为空时必填
						entrustFlag	受托支付标志	A	1	C	为空时单一借款人模式
						0：非受托支付业务类别
						1：受托支付业务类别*/
                        if(borrowInfo.getEntrustedFlg()!= null && borrowInfo.getEntrustedFlg().intValue() ==1){
                            StzhWhiteListExample sTZHWhiteListExample = new StzhWhiteListExample();
                            StzhWhiteListExample.Criteria sTZHCra = sTZHWhiteListExample.createCriteria();
                            sTZHCra.andStUserIdEqualTo(borrowInfo.getEntrustedUserId());
                            sTZHCra.andInstcodeEqualTo(borrowInfo.getInstCode());
                            /*----------------upd by liushouyi HJH3 Start-----------------------*/
                            sTZHCra.andDelFlagEqualTo(0);
                            sTZHCra.andStateEqualTo(1);
                            /*----------------upd by liushouyi HJH3 End-----------------------*/
                            List<StzhWhiteList> sTZHWhiteList = this.stzhWhiteListMapper.selectByExample(sTZHWhiteListExample);

                            if (sTZHWhiteList != null && sTZHWhiteList.size() >= 1) {
                                StzhWhiteList stzf = sTZHWhiteList.get(0);
                                debtRegistBean.setEntrustFlag(borrowInfo.getEntrustedFlg().toString());
                                debtRegistBean.setReceiptAccountId(stzf.getStAccountid());
                                /*----------------upd by liushouyi HJH3 Start-----------------------*/
                            } else {
                                //原逻辑三方资产此处未处理、推送资产时已校验过白名单、手动录标的时候白名单依然做校验处理
                                this.updateBorrowRegist(borrow, 0, 4);
                                result.put("error", "1");
                                result.put("msg", "受托白名单查询为空！");
                                return result;
                            }
                            /*----------------upd by liushouyi HJH3 End-----------------------*/

                        }

                        if (Validator.isNotNull(borrowInfo.getRepayOrgUserId())) {
                            AccountExample examples = new AccountExample();
                            AccountExample.Criteria cras = examples.createCriteria();
                            cras.andUserIdEqualTo(borrowInfo.getRepayOrgUserId());
                            List<Account> accountLists = accountMapper.selectByExample(examples);
                            if (!CollectionUtils.isEmpty(accountList)){
                                Account accounts = accountLists.get(0);
                                if (Validator.isNotNull(accounts.getAccountId())) {
                                    debtRegistBean.setBailAccountId(accounts.getAccountId());
                                }
                            }
                        }
                        debtRegistBean.setLogOrderId(orderId);
                        debtRegistBean.setLogOrderDate(orderDate);
                        debtRegistBean.setLogUserId(String.valueOf(userId));
                        debtRegistBean.setLogRemark("借款人标的登记");
                        debtRegistBean.setLogClient(0);
                        try {
                            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                            String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {

                                // 如果是受托支付 备案成功时更改状态
                                int status = 1;
                                if(borrowInfo.getEntrustedFlg()!= null && borrowInfo.getEntrustedFlg().intValue() ==1){
                                    status = 7;
                                }

                                boolean debtRegistedFlag = this.updateBorrowRegist(borrow, status, 2);
                                if (debtRegistedFlag) {
                                    result.put("success", "0");
                                    result.put("msg", "备案成功！");
                                } else {
                                    result.put("success", "1");
                                    result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                }
                            } else {
                                this.updateBorrowRegist(borrow, 0, 4);
                                String message = registResult.getRetMsg();
                                result.put("success", "1");
                                result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            this.updateBorrowRegist(borrow, 0, 4);
                            result.put("success", "1");
                            result.put("msg", "银行备案接口调用失败！");
                        }
                    } else {
                        result.put("success", "1");
                        result.put("msg", "更新相应的标的信息失败,请稍后再试！");
                    }
                }
            } else {
                result.put("success", "1");
                result.put("msg", "借款人信息错误！");
            }
        } else {
            result.put("success", "1");
            result.put("msg", "项目编号为空！");
            logger.info("标的："+borrowNid+" 项目编号为空");
        }
        return result;
    }

    /**
     * 备案
     * @param borrow
     * @return
     */
    public JSONObject debtRegist(Borrow borrow,BorrowInfo borrowInfo) {
        // 返回结果
        JSONObject result = new JSONObject();

            // 检查是否备案失败，如果是，跳过
            if(borrow.getStatus()==0 && borrow.getRegistStatus()==4){
                logger.info("标的："+borrow.getBorrowNid()+" 自动备案失败过");
                result.put("success", "2");
                result.put("msg", "自动备案失败过！");
                return result;
            }


            String borrowStyle = borrow.getBorrowStyle();// 項目还款方式
            // 是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            int userId = borrow.getUserId();// 借款人userId
            AccountExample example = new AccountExample();
            AccountExample.Criteria cra = example.createCriteria();
            cra.andUserIdEqualTo(userId);
            List<Account> accountList = accountMapper.selectByExample(example);
            if(null != accountList && accountList.size() > 0){
                Account account = accountList.get(0);
                if (Validator.isNotNull(account.getAccountId())) {
                        // 更新相应的标的状态为备案中
                        boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1);
                        if (debtRegistingFlag) {
                            // 获取共同参数
//                            String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
                            String channel = BankCallConstant.CHANNEL_PC;
                            String orderId = GetOrderIdUtils.getOrderId2(userId);
                            String orderDate = GetOrderIdUtils.getOrderDate();
                            String txDate = GetOrderIdUtils.getTxDate();
                            String txTime = GetOrderIdUtils.getTxTime();
                            String seqNo = GetOrderIdUtils.getSeqNo(6);
                            // 调用开户接口
                            BankCallBean debtRegistBean = new BankCallBean();
                            debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                            debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);// 消息类型(用户开户)
//                            debtRegistBean.setBankCode(bankCode);
                            debtRegistBean.setTxDate(txDate);
                            debtRegistBean.setTxTime(txTime);
                            debtRegistBean.setSeqNo(seqNo);
                            debtRegistBean.setChannel(channel);
                            debtRegistBean.setAccountId(account.getAccountId());// 借款人电子账号
                            debtRegistBean.setProductId(borrow.getBorrowNid());// 标的表id
                            debtRegistBean.setProductDesc(borrowInfo.getName());// 标的名称
                            debtRegistBean.setRaiseDate(borrowInfo.getBankRaiseStartDate());// 募集日,标的保存时间
                            debtRegistBean.setRaiseEndDate(borrowInfo.getBankRaiseEndDate());// 募集结束日期
                            if (isMonth) {
                                debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);// 付息方式没有不确定日期
                            } else {
                                debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
                            }
                            debtRegistBean.setDuration(String.valueOf(borrowInfo.getBankBorrowDays()));// (借款期限,天数）
                            debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));// 交易金额
                            debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));// 年华利率

                            // 受托支付
						/*receiptAccountId	收款人电子帐户	A	19	C	当entrustFlag不为空时必填
						entrustFlag	受托支付标志	A	1	C	为空时单一借款人模式
						0：非受托支付业务类别
						1：受托支付业务类别*/
                            if(borrowInfo.getEntrustedFlg()!= null && borrowInfo.getEntrustedFlg().intValue() ==1){
                                StzhWhiteListExample sTZHWhiteListExample = new StzhWhiteListExample();
                                StzhWhiteListExample.Criteria sTZHCra = sTZHWhiteListExample.createCriteria();
                                sTZHCra.andStUserIdEqualTo(borrowInfo.getEntrustedUserId());
                                sTZHCra.andInstcodeEqualTo(borrowInfo.getInstCode());
                                /*----------------upd by liushouyi HJH3 Start-----------------------*/
                                sTZHCra.andDelFlagEqualTo(0);
                                sTZHCra.andStateEqualTo(1);
                                /*----------------upd by liushouyi HJH3 End-----------------------*/
                                List<StzhWhiteList> sTZHWhiteList = this.stzhWhiteListMapper.selectByExample(sTZHWhiteListExample);

                                if (sTZHWhiteList != null && sTZHWhiteList.size() >= 1) {
                                    StzhWhiteList stzf = sTZHWhiteList.get(0);
                                    debtRegistBean.setEntrustFlag(borrowInfo.getEntrustedFlg().toString());
                                    debtRegistBean.setReceiptAccountId(stzf.getStAccountid());
                                    /*----------------upd by liushouyi HJH3 Start-----------------------*/
                                } else {
                                    //原逻辑三方资产此处未处理、推送资产时已校验过白名单、手动录标的时候白名单依然做校验处理
                                    this.updateBorrowRegist(borrow, 0, 4);
                                    result.put("error", "1");
                                    result.put("msg", "受托白名单查询为空！");
                                    return result;
                                }
                                /*----------------upd by liushouyi HJH3 End-----------------------*/

                            }

                            if (Validator.isNotNull(borrowInfo.getRepayOrgUserId())) {
                                AccountExample examples = new AccountExample();
                                AccountExample.Criteria cras = examples.createCriteria();
                                cras.andUserIdEqualTo(borrowInfo.getRepayOrgUserId());
                                List<Account> accountLists = accountMapper.selectByExample(examples);
                                if(null != accountLists && accountLists.size() > 0){
                                    Account accounts = accountLists.get(0);
                                    if (Validator.isNotNull(accounts.getAccountId())) {
                                        debtRegistBean.setBailAccountId(accounts.getAccountId());
                                    }
                                }
                            }
                            debtRegistBean.setLogOrderId(orderId);
                            debtRegistBean.setLogOrderDate(orderDate);
                            debtRegistBean.setLogUserId(String.valueOf(userId));
                            debtRegistBean.setLogRemark("借款人标的登记");
                            debtRegistBean.setLogClient(0);
                            try {
                                BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                                String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {

                                    // 如果是受托支付 备案成功时更改状态
                                    int status = 1;
                                    if(borrowInfo.getEntrustedFlg()!= null && borrowInfo.getEntrustedFlg().intValue() ==1){
                                        status = 7;
                                    }

                                    boolean debtRegistedFlag = this.updateBorrowRegist(borrow, status, 2);
                                    if (debtRegistedFlag) {
                                        result.put("success", "0");
                                        result.put("msg", "备案成功！");
                                    } else {
                                        result.put("success", "1");
                                        result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                    }
                                } else {
                                    this.updateBorrowRegist(borrow, 0, 4);
                                    String message = registResult.getRetMsg();
                                    result.put("success", "1");
                                    result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                this.updateBorrowRegist(borrow, 0, 4);
                                result.put("success", "1");
                                result.put("msg", "银行备案接口调用失败！");
                            }
                        } else {
                            result.put("success", "1");
                            result.put("msg", "更新相应的标的信息失败,请稍后再试！");
                        }
                } else {
                    result.put("success", "1");
                    result.put("msg", "借款人信息错误！");
                }
            }else{
                result.put("success", "1");
                result.put("msg", "借款人信息错误！");
            }

        return result;
    }

    private boolean updateBorrowRegist(Borrow borrow, int status, int registStatus) {
        Date nowDate = new Date();
//		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrow.getId()).andStatusEqualTo(borrow.getStatus()).andRegistStatusEqualTo(borrow.getRegistStatus());
        borrow.setRegistStatus(registStatus);
        borrow.setStatus(status);
        borrow.setRegistUserId(1);//TODO:id写死1
        borrow.setRegistUserName("AutoRecord");
        borrow.setRegistTime(nowDate);
        boolean flag = this.borrowMapper.updateByExampleSelective(borrow, example) > 0 ? true : false;
        return flag;
    }
}
