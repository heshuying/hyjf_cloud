/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.cert.impl;

import com.hyjf.am.resquest.hgreportdata.cert.CertReportEntitRequest;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.hgreportdata.cert.CertAccountList;
import com.hyjf.cs.message.bean.hgreportdata.cert.CertReportEntity;
import com.hyjf.cs.message.mongo.hgreportdata.cert.CertAccountListDao;
import com.hyjf.cs.message.mongo.hgreportdata.cert.CertReportDao;
import com.hyjf.cs.message.service.hgreportdata.cert.CertStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nxl
 * @version CertStatisticalServiceImpl, v0.1 2019/1/19 10:07
 */
@Service
public class CertStatisticalServiceImpl extends BaseServiceImpl implements CertStatisticalService {

    @Autowired
    CertReportDao certReportDao;

    @Autowired
    CertAccountListDao certAccountListDao;

    /**
     * 插入mongo数据
     * @param bean
     */
    @Override
    public void insertAndSendPost(CertReportEntity bean) {
        certReportDao.insert(bean);
    }
    /**
     * 插入mongo数据
     * @param bean
     */
    @Override
    public void updateCertReport(CertReportEntitRequest bean) {
        // 操作数据库 修改
        Query q1 = Query.query(Criteria.where("logOrdId").is(bean.getLogOrdId()));
        Update u1 = new Update();
        u1.set("reportStatus", bean.getReportStatus());
        u1.set("retMess", bean.getResp());
        this.certReportDao.update(q1, u1);
    }

    /**
     * 根据订单号查询
     *
     * @param logOrdId
     * @return
     */
    @Override
    public CertReportEntity getCertSendLogByLogOrdId(String logOrdId) {
        Query q1 = Query.query(Criteria.where("logOrdId").is(logOrdId));
        CertReportEntity entity = certReportDao.findOne(q1);
        return entity;
    }

    @Override
    public void insertOldMessage(CertAccountList certAccountList) {
        certAccountListDao.insert(certAccountList);
    }

    @Override
    public List<CertReportEntity> getNotSendAccountList() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 查询未上报的是0
        criteria.and("isSend").is(0);
        query.addCriteria(criteria);
        query.limit(1000);
        return certReportDao.find(query);
    }

    @Override
    public void updateAccountSuccess(CertAccountList certAccountList) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("logOrdId").is(certAccountList.getLogOrdId());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("isSend",1);
        certAccountListDao.update(query,update);
    }
}
