/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.synbalance.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DateDistance;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.user.bean.BankResultBean;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.SynBalanceResultBean;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqingqing
 * @version SynBalanceServiceImpl, v0.1 2018/7/25 15:11
 */
@Service
public class SynBalanceServiceImpl extends BaseUserServiceImpl implements SynBalanceService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;


    /**
     * @Description 获取用户开户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(String accountId) {
        return amUserClient.selectBankOpenAccountByAccountId(accountId);
    }
    /**
     * @Description 根据用户id获取账户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        return amTradeClient.getAccount(userId);
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Deprecated
    public BankCallBean queryAccountDetails(Integer userId, String accountId, String startDate, String endDate, String type, String transType, String pageNum, String pageSize, String inpDate, String inpTime, String relDate, String traceNo) {
        // 参数不正确
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(type)) {
            return null;
        }
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_DETAILS_QUERY);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 起始日期
        bean.setStartDate(startDate);
        // 结束日期
        bean.setEndDate(endDate);
        // 交易种类 0-所有交易 1-转入交易 2-转出交易 9-指定交易类型
        bean.setType(type);
        if ("9".equals(type)) {
            // 交易类型
            bean.setTranType(transType);
        }
        // 翻页标识  空：首次查询；1：翻页查询；
        if (StringUtils.isNotEmpty(pageNum)&&!"1".equals(pageNum)) {
            bean.setRtnInd("1");
        } else {
            bean.setRtnInd("");
        }
        bean.setInpDate(inpDate);
        bean.setInpTime(inpTime);
        bean.setRelDate(relDate);
        bean.setTraceNo(traceNo);
        // 操作者ID
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogRemark("资金交易明细查询");
        bean.setLogUserId(String.valueOf(userId));
        // 调用接口
        return BankCallUtils.callApiBg(bean);
    }


    public BankCallBean queryAccountDetailsNew(Integer userId, String accountId, String startDate, String endDate, String transType, String pageNum, String inpDate, String inpTime, String relDate, String traceNo) {
        // 参数不正确
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(transType)) {
            return null;
        }
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_OFFLINE_RECHARGE_DETAILS_QUERY);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 起始日期
        bean.setStartDate(startDate);
        // 结束日期
        bean.setEndDate(endDate);

        // 交易类型
        bean.setTranType(transType);
        // 翻页标识  空：首次查询；1：翻页查询；
        if (StringUtils.isNotEmpty(pageNum)&&!"1".equals(pageNum)) {
            bean.setRtnInd("1");
        } else {
            bean.setRtnInd("");
        }
        bean.setInpDate(inpDate);
        bean.setInpTime(inpTime);
        bean.setRelDate(relDate);
        bean.setTraceNo(traceNo);
        // 操作者ID
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogRemark("hyjf线下充值明细查询");
        bean.setLogUserId(String.valueOf(userId));
        // 调用接口
        return BankCallUtils.callApiBg(bean);
    }

    /**
     * @Description 获取银行错误返回码
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO retCodes =amUserClient.getBankReturnCodeConfig(retCode);
            if (retCodes != null) {
                String retMsg = retCodes.getErrorMsg();
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
     * @Description 插入用户线下充值信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public boolean insertAccountDetails(AccountVO accountUser, SynBalanceVO synBalanceBean, UserVO user, String ipAddr) {
        SynBalanceBeanRequest synBalanceBeanRequest=new SynBalanceBeanRequest();
        synBalanceBeanRequest.setAccountUser(accountUser);
        synBalanceBeanRequest.setSynBalanceBean(synBalanceBean);
        synBalanceBeanRequest.setUsername(user.getUsername());
        synBalanceBeanRequest.setIpAddr(ipAddr);

        // 获取用户绑卡信息
        BankCardVO bankCardVO = amUserClient.getBankCardByUserId(user.getUserId());
        if (null != bankCardVO){
            synBalanceBeanRequest.setBankCardVO(bankCardVO);
            return  amTradeClient.insertAccountDetails(synBalanceBeanRequest);
        } else {
            return false;
        }
    }

    /**
     * 获取数据表中线下充值类型
     * @return
     * @Author : huanghui
     */
    @Override
    public List<UnderLineRechargeVO> selectUnderLineRechargeList(UnderLineRechargeRequest request){
        return amTradeClient.selectUnderLineRechargeList(request);
    }

    @Override
    public SynBalanceResultBean synBalance(String accountId, String ip) {
        SynBalanceResultBean resultBean = new SynBalanceResultBean();
        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = getBankOpenAccount(accountId);
        if(bankOpenAccount == null){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000004);
            logger.info("没有根据电子银行卡找到用户 "+accountId);
            resultBean.setStatusDesc("没有根据电子银行卡找到用户 ");
            return resultBean;
        }

        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        //用户ID
        UserVO user = getUsersById(bankOpenAccount.getUserId());
        if(user == null){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000007);
            logger.info("-------------------未找到用户--------------------");
            resultBean.setStatusDesc("未找到用户");

            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }
        //用户可用余额
        AccountVO accountUser = getAccount(user.getUserId());
        if(accountUser==null){
            accountUser = new AccountVO();
        }
        BigDecimal accountBalance = accountUser.getBankBalance();
        //客户号
        if(systemConfig.isHyjfEnvTest()){
            resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
            resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
            resultBean.setBankBalance(df.format(accountUser.getBankBalance()));
            resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc("成功");
            return resultBean;
        }

        //查询时间段 (只查当天)
        //上线需放开
        /*String startDate = getTxDate(bankOpenAccount);
        String endDate = GetOrderIdUtils.getTxDate();*/

        // 测试环境

            String startDate = "20170310";
            String endDate = GetOrderIdUtils.getTxDate();
        
        List<UnderLineRechargeVO> codeList=getUnderLineRechargeList();
        List<BankResultBean> recordList = new ArrayList<BankResultBean>();
        int pageSize = 10;
        for (UnderLineRechargeVO code : codeList){
            //页码定义
            int pageNum = 1;
            // 查询参数定义
            String inpDate = "";
            String inpTime = "";
            String relDate = "";
            String traceNo = "";

            //调用查询明细接口 查线下充值数据
            BankCallBean retBean = queryAccountDetailsNew(user.getUserId(), bankOpenAccount.getAccount(),
                    startDate, endDate, code.getCode(), String.valueOf(pageNum),inpDate,inpTime,relDate,traceNo);

            if(retBean == null){
                logger.info("-------------------同步余额失败--------------------"+code.getCode());
                continue;
            }
            //返回失败
            if(!BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())){
                logger.info("-------------------同步余额失败--------------------"+code.getCode());
                logger.info("-------------------调用查询接口失败，失败原因：" + getBankRetMsg(retBean.getRetCode())+"--------------------");
                logger.info(this.getClass().getName(), "/synbalance");
                continue;
            }

            //解析返回数据(记录为空)
            String content = retBean.getSubPacks();
            if(StringUtils.isEmpty(content)){
                continue;
            }
            //返回结果记录数
            //转换结果

            List<BankResultBean> list = new ArrayList<BankResultBean>();
            list = JSONArray.parseArray(retBean.getSubPacks(), BankResultBean.class);
            if(list==null|| list.size()==0){
                continue;
            }else{
                BankResultBean lastResult = CommonUtils.convertBean(list.get(list.size()-1),BankResultBean.class);
                inpDate = lastResult.getInpDate();
                inpTime = lastResult.getInpTime();
                relDate = lastResult.getRelDate();
                traceNo = String.valueOf(lastResult.getTraceNo());
            }
            recordList.addAll(list);

            while (list.size()==pageSize) {
                pageNum ++;
                //调用查询明细接口 查线下充值数据
                BankCallBean bean = queryAccountDetailsNew(user.getUserId(), bankOpenAccount.getAccount(),
                        startDate, endDate, code.getCode(), String.valueOf(pageNum),inpDate,inpTime,relDate,traceNo);
                if(bean == null){
                    continue;
                }
                list = JSONArray.parseArray(bean.getSubPacks(), BankResultBean.class);
                recordList.addAll(list);
                if(list!=null&&list.size()>0){
                    BankResultBean lastResult = CommonUtils.convertBean(list.get(list.size()-1),BankResultBean.class);
                    inpDate = lastResult.getInpDate();
                    inpTime = lastResult.getInpTime();
                    relDate = lastResult.getRelDate();
                    traceNo = String.valueOf(lastResult.getTraceNo());
                }
            }
        }
        logger.info("-------------------"+recordList.size()+"同步余额总条数--------------------");
        /**redis 锁 */
        boolean reslut = RedisUtils.tranactionSet(RedisConstants.SYNBALANCE+user.getUserId(),30);
        // 如果没有设置成功，说明有请求来设置过
        //用户可用余额
        accountUser = getAccount(user.getUserId());
        if(!reslut){
            //成功???
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
            resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
            resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
            resultBean.setBankBalance(df.format(accountBalance));
            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }
        for (int i = 0; i < recordList.size(); i++) {
            BankResultBean record = recordList.get(i);
            //是否冲正订单
            if(record != null && ("O".equals(record.getOrFlag()) || "0".equals(record.getOrFlag()))){ //原始订单，冲正订单不处理
                //是否是线下充值交易类型
                boolean isType = isRechargeTransType(record.getTranType());
                if(isType){
                    SynBalanceVO synBalanceBean=new SynBalanceVO();
                    // 如果江西银行不返回电子账户号  就设置本地的电子帐户号
                    if(record.getAccountId()==null){
                        record.setAccountId(bankOpenAccount.getAccount());
                    }
                    BeanUtils.copyProperties(record, synBalanceBean);

                    boolean flag=false;

                    try {
                        flag = insertAccountDetails(accountUser,synBalanceBean,user, ip);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    if(flag){
                        // TODO 活动如需使用重新编写
                        /*CommonSoaUtils.listedTwoRecharge(user.getUserId(), record.getTxAmount());*/
                        //更新金额
                        accountBalance = accountBalance.add(record.getTxAmount());
                    }
                    logger.info("-------------------更新线下充值，电子帐户号："+synBalanceBean.getAccountId()+"完毕--------------------");
                }
            }
        }
        logger.info("{}同步余额结束--------------------", accountId);
        accountUser = getAccount(user.getUserId());
        resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
        resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
        resultBean.setBankBalance(df.format(accountUser.getBankBalance()));
        resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);

        logger.info(this.getClass().getName(),"/synbalance");
        return resultBean;
    }

    /**
     * 从Redis获取线下充值类型List
     * @return
     * @Author pcc
     */
    private List<UnderLineRechargeVO> getUnderLineRechargeList() {
        //从Redis获取线下充值类型List
        String codeStringList = RedisUtils.get(RedisConstants.UNDER_LINE_RECHARGE_TYPE);
        JSONArray redisCodeList = JSONArray.parseArray(codeStringList);

        if (StringUtils.isBlank(codeStringList) || redisCodeList.size() <= 0) {
            logger.info(this.getClass().getName(), "---------------------------线下充值类型Redis为空!-------------------------");
            UnderLineRechargeRequest request = new UnderLineRechargeRequest();
            List<UnderLineRechargeVO> codeList = selectUnderLineRechargeList(request);
            return codeList;
        }else{
            List<UnderLineRechargeVO> codeList =new ArrayList<UnderLineRechargeVO>();
            for(Object code : redisCodeList) {
                UnderLineRechargeVO codeListVO=new UnderLineRechargeVO();
                codeListVO.setCode(code.toString());
                codeList.add(codeListVO);
            }
            return codeList;
        }
    }

    private String getTxDate(BankOpenAccountVO bankOpenAccount) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 2);

        String startDate = dft.format(date.getTime());
        if(bankOpenAccount==null){
            return GetOrderIdUtils.getTxDate();
        }
        Date createTime=bankOpenAccount.getCreateTime();
        String startDate1 ="";
        startDate1=dft.format(createTime);

        try {
            if((DateDistance.getDistanceDays2(GetOrderIdUtils.getTxDate(),startDate1))>=2){
                return startDate;
            }else{
                return startDate1;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return GetOrderIdUtils.getTxDate();
    }

    /**
     * 判断是否属于线下充值类型.
     * 	优先从Redis中取数据,当Redis中的数据为空时,从数据表中读取数据
     * @param tranType
     * @return
     * @Author : huanghui
     */
    private boolean isRechargeTransType(String tranType) {
        //从Redis获取线下充值类型List
        String codeStringList = RedisUtils.get(RedisConstants.UNDER_LINE_RECHARGE_TYPE);
        JSONArray redisCodeList = JSONArray.parseArray(codeStringList);

        if (StringUtils.isBlank(codeStringList) || redisCodeList.size() <= 0){
            logger.info(this.getClass().getName(), "---------------------------线下充值类型Redis为空!-------------------------");

            UnderLineRechargeRequest request = new UnderLineRechargeRequest();
            List<UnderLineRechargeVO> codeList = selectUnderLineRechargeList(request);
            if (codeList.isEmpty()){
                logger.info(this.getClass().getName(), "---------------------------线下充值类型数据库未配置!-------------------------");
                return false;
            }else {
                for (UnderLineRechargeVO code : codeList){
                    if (code.getCode().equals(tranType)){
                        return true;
                    }else {
                        continue;
                    }
                }
            }
        }else {

            for(Object code : redisCodeList) {
                if (code.equals(tranType)){
                    return true;
                }else {
                    continue;
                }
            }
        }
        return false;
    }
}
