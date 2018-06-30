package com.hyjf.pay.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.mongo.ChinapnrExclusiveLogDao;
import com.hyjf.pay.mongo.ChinapnrLog;
import com.hyjf.pay.mongo.ChinapnrLogDao;
import com.hyjf.pay.mongo.ChinapnrSendLogDao;
import com.hyjf.pay.mongo.ChinapnrSendlog;
import com.hyjf.pay.service.BankPayLogService;

@Service
public class BankPayLogServiceImpl implements BankPayLogService {

    @Autowired
    private ChinapnrLogDao chinapnrLogDao;

    @Autowired
    private ChinapnrSendLogDao chinapnrSendLogDao;

    @Autowired
    private ChinapnrExclusiveLogDao chinapnrExclusiveLogDao;

    private static final String SENDLOG = "banksendlog";
    private static final String BACKLOG = "banklog";
    private static final String EXCLUSENDLOG = "bankexclusivelog";

    /**
     * 保存发送日志
     *
     * @param chinapnrSendlog
     */
    @Override
    public void saveChinapnrSendLog(BankCallPnrApiBean pnrApiBean, BankCallBean bean) {

        int nowTime = GetDate.getNowTime10();
        ChinapnrSendlog sendlog = new ChinapnrSendlog();
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

        chinapnrSendLogDao.save(sendlog, SENDLOG);

    }

    /**
     * 保存接受日志
     *
     * @param chinapnrLog
     */
    @Override
    public void saveChinapnrLog(BankCallBean bean, int returnType) {
        String nowTime = GetDate.getServerDateTime(8, new Date());
        ChinapnrLog chinapnrLog = new ChinapnrLog();
        chinapnrLog.setIsbg(returnType);
        chinapnrLog.setOrdid(bean.getLogOrderId());
        chinapnrLog.setUserId(StringUtils.isNotEmpty(bean.getLogUserId()) ? Integer.parseInt(bean.getLogUserId()) : null);
        chinapnrLog.setClient(bean.getLogClient());
        chinapnrLog.setMsgType(bean.getTxCode());
        chinapnrLog.setMsgdata(bean.getJsonMap());
        chinapnrLog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        chinapnrLog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        chinapnrLog.setSeqNo(bean.getSeqNo());
        chinapnrLog.setRemark(bean.getLogRemark());
        chinapnrLog.setIp(bean.getLogIp());
        chinapnrLog.setAddtime(nowTime);

        chinapnrLogDao.save(chinapnrLog, BACKLOG);
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
    public ChinapnrExclusiveLog selectChinapnrExclusiveLogByOrderId(String orderId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("ordid").is(orderId);
        query.addCriteria(criteria);
        return this.chinapnrExclusiveLogDao.findOne(query, EXCLUSENDLOG);
    }

    @Override
    public String insertChinapnrSendLog(BankCallPnrApiBean pnrApiBean, BankCallBean bean) {
        int nowTime = GetDate.getNowTime10();
        ChinapnrSendlog sendlog = new ChinapnrSendlog();
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

        this.chinapnrSendLogDao.insert(sendlog, SENDLOG);

        return sendlog.getId();
    }

    @Override
    public String insertChinapnrExclusiveLog(BankCallBean bean) {
        int nowTime = GetDate.getNowTime10();
        ChinapnrExclusiveLog exclusiveLog = new ChinapnrExclusiveLog();
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
        this.chinapnrExclusiveLogDao.insert(exclusiveLog, EXCLUSENDLOG);

        return exclusiveLog.getId();
    }

    @Override
    public String insertChinapnrLog(BankCallBean bean, int returnType) {
        String nowTime = GetDate.getServerDateTime(8, new Date());
        ChinapnrLog chinapnrLog = new ChinapnrLog();
        chinapnrLog.setIsbg(returnType);
        chinapnrLog.setOrdid(bean.getLogOrderId());
        chinapnrLog.setUserId(StringUtils.isNotEmpty(bean.getLogUserId()) ? Integer.parseInt(bean.getLogUserId()) : null);
        chinapnrLog.setClient(bean.getLogClient());
        chinapnrLog.setMsgType(bean.getTxCode());
        chinapnrLog.setMsgdata(bean.getJsonMap());
        chinapnrLog.setTxDate(StringUtils.isNotEmpty(bean.getTxDate()) ? Integer.valueOf(bean.getTxDate()) : null);
        chinapnrLog.setTxTime(StringUtils.isNotEmpty(bean.getTxTime()) ? Integer.valueOf(bean.getTxTime()) : null);
        chinapnrLog.setSeqNo(bean.getSeqNo());
        chinapnrLog.setRemark(bean.getLogRemark());
        chinapnrLog.setIp(bean.getLogIp());
        chinapnrLog.setAddtime(nowTime);
        this.chinapnrLogDao.insert(chinapnrLog, BACKLOG);

        return chinapnrLog.getId();
    }

    @Override
    public void insertChinapnrLog(BankCallBean backBean, Map<String, String> mapParam, int i) {
        backBean.getAllParams().clear();
        backBean.getAllParams().putAll(mapParam);
        insertChinapnrLog(backBean, i);
    }

    @Override
    public void updateChinapnrExclusiveLog(String orderId, BankCallBean bean, int nowTime) {

        ChinapnrExclusiveLog exclusiveLogInDb = this.chinapnrExclusiveLogDao.findOne(
                Query.query(Criteria.where("ordid").is(orderId)), EXCLUSENDLOG);
        // 拼接相应的参数
        exclusiveLogInDb.setStatus(bean.getLogOrderStatus());
        exclusiveLogInDb.setResult(bean.getJson());
        exclusiveLogInDb.setRespcode(bean.getRetCode());
        exclusiveLogInDb.setUpdatetime(String.valueOf(nowTime));
        exclusiveLogInDb.setUpdateuser(bean.getLogUserId());

        this.chinapnrExclusiveLogDao.save(exclusiveLogInDb, EXCLUSENDLOG);

    }

    @Override
    public void updateChinapnrExclusiveLog(String orderId, int status) {

        ChinapnrExclusiveLog exclusiveLogInDb = this.chinapnrExclusiveLogDao.findOne(
                Query.query(Criteria.where("ordid").is(orderId)), EXCLUSENDLOG);
        // 拼接相应的参数
        exclusiveLogInDb.setStatus(status + "");

        this.chinapnrExclusiveLogDao.save(exclusiveLogInDb, EXCLUSENDLOG);
    }


}
