package com.hyjf.pay.service.impl;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.pay.entity.BankExclusiveLog;
import com.hyjf.pay.entity.BankLog;
import com.hyjf.pay.entity.BankSendlog;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.mongo.BankExclusiveLogDao;
import com.hyjf.pay.mongo.BankLogDao;
import com.hyjf.pay.mongo.BankSendLogDao;
import com.hyjf.pay.service.BankPayLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class BankPayLogServiceImpl implements BankPayLogService {

    @Autowired
    private BankLogDao bankLogDao;

    @Autowired
    private BankSendLogDao bankSendLogDao;

    @Autowired
    private BankExclusiveLogDao bankExclusiveLogDao;

    /**
     * 保存发送日志
     *
     * @param
     */
    @Override
    public void saveChinapnrSendLog(BankCallPnrApiBean pnrApiBean, BankCallBean bean) {

        int nowTime = GetDate.getNowTime10();
        BankSendlog sendlog = new BankSendlog();
        sendlog.setOrdid(bean.getLogOrderId());
        sendlog.setOrddate(bean.getLogOrderDate());
        sendlog.setClient(bean.getLogClient());
        sendlog.setUserId(StringUtils.isNotEmpty(bean.getLogUserId()) ? Integer.valueOf(bean.getLogUserId()) : null);
        sendlog.setMsgType(bean.getTxCode());
        sendlog.setMsgdata(bean.get(BankCallConstant.PARAM_LOGMSGDATA));
        sendlog.setChkvalue(bean.get(BankCallConstant.PARAM_SIGN));
        sendlog.setContent(pnrApiBean.getJsonMap());
        sendlog.setRemark(bean.getLogRemark());
        sendlog.setClient(GetterUtil.getInteger(bean.get(BankCallConstant.PARAM_LOGCLIENT)));
        sendlog.setCreateTime(nowTime);
        sendlog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        sendlog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        sendlog.setSeqNo(bean.getSeqNo());

        bankSendLogDao.save(sendlog);

    }

    /**
     * 保存接受日志
     *
     * @param
     */
    @Override
    public void saveChinapnrLog(BankCallBean bean, int returnType) {
        String nowTime = GetDate.getServerDateTime(8, new Date());
        BankLog bankLog = new BankLog();
        bankLog.setIsbg(returnType);
        bankLog.setOrdid(bean.getLogOrderId());
        bankLog.setUserId(StringUtils.isNotEmpty(bean.getLogUserId()) ? Integer.parseInt(bean.getLogUserId()) : null);
        bankLog.setClient(bean.getLogClient());
        bankLog.setMsgType(bean.getTxCode());
        bankLog.setMsgdata(bean.getJsonMap());
        bankLog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        bankLog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        bankLog.setSeqNo(bean.getSeqNo());
        bankLog.setRemark(bean.getLogRemark());
        bankLog.setIp(bean.getLogIp());
        bankLog.setAddtime(nowTime);

        bankLogDao.save(bankLog);
    }

    @Override
    public void saveChinapnrLog(BankCallBean bean, Map<String, String> mapMsg, int returnType) {
        bean.getAllParams().clear();
        bean.getAllParams().putAll(mapMsg);
        saveChinapnrLog(bean, returnType);
    }

    /**
     * 查询检证日志
     *
     * @param orderId
     * @return
     */
    @Override
    public BankExclusiveLog selectChinapnrExclusiveLogByOrderId(String orderId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("ordid").is(orderId);
        query.addCriteria(criteria);
        return this.bankExclusiveLogDao.findOne(query);
    }

    @Override
    public String insertChinapnrSendLog(BankCallBean bean) {
        int nowTime = GetDate.getNowTime10();
        BankSendlog sendlog = new BankSendlog();
        sendlog.setOrdid(bean.getLogOrderId());
        sendlog.setOrddate(bean.getLogOrderDate());
        sendlog.setClient(bean.getLogClient());
        sendlog.setUserId(StringUtils.isNotEmpty(bean.getLogUserId()) ? Integer.valueOf(bean.getLogUserId()) : null);
        sendlog.setMsgType(bean.getTxCode());
        sendlog.setMsgdata(bean.get(BankCallConstant.PARAM_LOGMSGDATA));
        sendlog.setChkvalue(bean.get(BankCallConstant.PARAM_SIGN));
        sendlog.setContent(bean.getJsonMap());
        sendlog.setRemark(bean.getLogRemark());
        sendlog.setClient(GetterUtil.getInteger(bean.get(BankCallConstant.PARAM_LOGCLIENT)));
        sendlog.setCreateTime(nowTime);
        sendlog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        sendlog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        sendlog.setSeqNo(bean.getSeqNo());

        this.bankSendLogDao.insert(sendlog);

        return sendlog.getId();
    }

    @Override
    public String insertChinapnrExclusiveLog(BankCallBean bean) {
        int nowTime = GetDate.getNowTime10();
        BankExclusiveLog exclusiveLog = new BankExclusiveLog();
        exclusiveLog.setClient(bean.getLogClient());
        exclusiveLog.setCmdid(bean.getTxCode());
        exclusiveLog.setOrdid(bean.getLogOrderId());
        exclusiveLog.setContent(bean.getJson());
        exclusiveLog.setStatus(BankCallConstant.STATUS_SENDING);
        exclusiveLog.setType(bean.getLogType());
        exclusiveLog.setDelFlag(CustomConstants.FLAG_NORMAL);
        exclusiveLog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        exclusiveLog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        exclusiveLog.setSeqNo(bean.getSeqNo());
        exclusiveLog.setCreateuser(bean.getLogUserId());
        exclusiveLog.setCreatetime(String.valueOf(nowTime));
        exclusiveLog.setUpdateuser(bean.getLogUserId());
        exclusiveLog.setUpdatetime(String.valueOf(nowTime));
        this.bankExclusiveLogDao.insert(exclusiveLog);

        return exclusiveLog.getId();
    }

    @Override
    public String insertChinapnrLog(BankCallBean bean, int returnType) {
        String nowTime = GetDate.getServerDateTime(8, new Date());
        BankLog bankLog = new BankLog();
        bankLog.setIsbg(returnType);
        bankLog.setOrdid(bean.getLogOrderId());
        bankLog.setUserId(StringUtils.isNotEmpty(bean.getLogUserId()) ? Integer.parseInt(bean.getLogUserId()) : null);
        bankLog.setClient(bean.getLogClient());
        bankLog.setMsgType(bean.getTxCode());
        bankLog.setMsgdata(bean.getJsonMap());
        bankLog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        bankLog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        bankLog.setSeqNo(bean.getSeqNo());
        bankLog.setRemark(bean.getLogRemark());
        bankLog.setIp(bean.getLogIp());
        bankLog.setAddtime(nowTime);
        this.bankLogDao.insert(bankLog);

        return bankLog.getId();
    }

    @Override
    public void insertChinapnrLog(BankCallBean backBean, Map<String, String> mapParam, int i) {
        backBean.getAllParams().clear();
        backBean.getAllParams().putAll(mapParam);
        insertChinapnrLog(backBean, i);
    }

    @Override
    public void updateChinapnrExclusiveLog(String orderId, BankCallBean bean, int nowTime) {

//        BankExclusiveLog exclusiveLogInDb = this.bankExclusiveLogDao.findOne(
//                Query.query(Criteria.where("ordid").is(orderId)));
//        // 拼接相应的参数
//        exclusiveLogInDb.setStatus(bean.getLogOrderStatus());
//        exclusiveLogInDb.setResult(bean.getJson());
//        exclusiveLogInDb.setRespcode(bean.getRetCode());
//        exclusiveLogInDb.setUpdatetime(String.valueOf(nowTime));
//        exclusiveLogInDb.setUpdateuser(bean.getLogUserId());
//        this.bankExclusiveLogDao.save(exclusiveLogInDb);

        
        Query q1 = Query.query(Criteria.where("ordid").is(orderId));
        Update u1 = new Update();
        u1.set("status", bean.getLogOrderStatus());
        u1.set("result", bean.getJson());
        u1.set("respcode", bean.getRetCode());
        u1.set("updatetime", String.valueOf(nowTime));
        u1.set("updateuser", bean.getLogUserId());
        
        this.bankExclusiveLogDao.findAndModify(q1, u1);

    }

    @Override
    public void updateChinapnrExclusiveLog(String orderId, int status) {

//        BankExclusiveLog exclusiveLogInDb = this.bankExclusiveLogDao.findOne(
//                Query.query(Criteria.where("ordid").is(orderId)));
//        // 拼接相应的参数
//        exclusiveLogInDb.setStatus(status + "");
//
//        this.bankExclusiveLogDao.save(exclusiveLogInDb);
        
        Query q1 = Query.query(Criteria.where("ordid").is(orderId));
        Update u1 = new Update();
        u1.set("status", status + "");
        
        this.bankExclusiveLogDao.findAndModify(q1, u1);
    }


}
