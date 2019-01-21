/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.nifa.impl;

import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowerInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaTenderInfoVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaBorrowInfoEntity;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaBorrowerInfoEntity;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaTenderInfoEntity;
import com.hyjf.cs.message.mongo.hgreportdata.nifa.*;
import com.hyjf.cs.message.service.hgreportdata.nifa.NifaStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalServiceImpl, v0.1 2019/1/19 10:07
 */
@Service
public class NifaStatisticalServiceImpl extends BaseServiceImpl implements NifaStatisticalService {

    @Autowired
    NifaBorrowInfoDao nifaBorrowInfoDao;

    @Autowired
    NifaBorrowerInfoDao nifaBorrowerInfoDao;

    @Autowired
    NifaTenderInfoDao nifaTenderInfoDao;

    @Autowired
    NifaCreditInfoDao nifaCreditInfoDao;

    @Autowired
    NifaCreditTransferDao nifaCreditTransferDao;

    /**
     * 根据借款编号和消息体查询该数据是否入库
     *
     * @param projectNo
     * @param msgBody
     * @return
     */
    @Override
    public NifaBorrowInfoEntity selectNifaBorrowInfoByProjectNo(String projectNo, String msgBody) {
        Query query = new Query();
        Criteria criteria = Criteria.where("projectNo").is(projectNo).and("message").is(msgBody);
        query.addCriteria(criteria);
        NifaBorrowInfoEntity nifaBorrowInfoEntity = nifaBorrowInfoDao.findOne(query);
        if (null != nifaBorrowInfoEntity) {
            return nifaBorrowInfoEntity;
        }
        return null;
    }

    /**
     * 插入借款人信息
     *
     * @param nifaBorrowerInfoVO
     */
    @Override
    public void insertNifaBorrowerInfo(NifaBorrowerInfoVO nifaBorrowerInfoVO) {
        Query query = new Query();
        Criteria criteria = Criteria.where("projectNo").is(nifaBorrowerInfoVO.getProjectNo()).and("message").is(nifaBorrowerInfoVO.getMessage()).and("userId").is(nifaBorrowerInfoVO.getUserId()).and("reportStatus").is("0");
        query.addCriteria(criteria);
        List<NifaBorrowerInfoEntity> nifaBorrowerInfoEntityList = nifaBorrowerInfoDao.find(query);
        if (null != nifaBorrowerInfoEntityList && nifaBorrowerInfoEntityList.size() > 0) {
            nifaBorrowerInfoDao.deleteBatch(nifaBorrowerInfoEntityList);
        }
        nifaBorrowerInfoDao.save(CommonUtils.convertBean(nifaBorrowerInfoVO, NifaBorrowerInfoEntity.class));
    }

    /**
     * 投资人信息插入
     *
     * @param nifaTenderInfoVO
     */
    @Override
    public void insertNifaTenderInfo(NifaTenderInfoVO nifaTenderInfoVO) {
        Query query = new Query();
        Criteria criteria = Criteria.where("projectNo").is(nifaTenderInfoVO.getProjectNo()).and("message").is(nifaTenderInfoVO.getMessage()).and("lenderId").is(nifaTenderInfoVO.getLenderId()).and("reportStatus").is("0");
        query.addCriteria(criteria);
        List<NifaTenderInfoEntity> nifaTenderInfoEntities = this.nifaTenderInfoDao.find(query);
        if (null != nifaTenderInfoEntities && nifaTenderInfoEntities.size() > 0) {
            nifaBorrowerInfoDao.deleteBatch(nifaTenderInfoEntities);
        }
        nifaTenderInfoDao.save(CommonUtils.convertBean(nifaTenderInfoVO, NifaTenderInfoEntity.class));
    }

    /**
     * 借款信息插入
     *
     * @param nifaBorrowInfoVO
     */
    @Override
    public void insertNifaBorrowInfo(NifaBorrowInfoVO nifaBorrowInfoVO) {
        Query query = new Query();
        Criteria criteria = Criteria.where("projectNo").is(nifaBorrowInfoVO.getProjectNo()).and("message").is(nifaBorrowInfoVO.getMessage()).and("reportStatus").is("0");
        query.addCriteria(criteria);
        List<NifaBorrowInfoEntity> nifaBorrowInfoEntities = this.nifaBorrowInfoDao.find(query);
        if (null != nifaBorrowInfoEntities && nifaBorrowInfoEntities.size() > 0) {
            nifaBorrowerInfoDao.deleteBatch(nifaBorrowInfoEntities);
        }
        nifaBorrowInfoDao.save(CommonUtils.convertBean(nifaBorrowInfoVO, NifaBorrowInfoEntity.class));
    }

    /**
     * 已做成借款人数据的更新上报状态为1
     *
     * @param projectNo
     * @param msgBody
     */
    @Override
    public void updateNifaBorrowerInfo(String projectNo, String msgBody) {
        Query query = new Query();
        Criteria criteria = Criteria.where("projectNo").is(projectNo).and("message").is(msgBody);
        query.addCriteria(criteria);
        List<NifaBorrowerInfoEntity> nifaBorrowerInfoEntities = this.nifaBorrowerInfoDao.find(query);
        if (null != nifaBorrowerInfoEntities && nifaBorrowerInfoEntities.size() > 0) {
            Update update = new Update();
            update.set("reportStatus", "1");
            this.nifaBorrowInfoDao.update(query,update);
        }
    }

    /**
     * 已做成投资人数据的更新上报状态为1
     *
     * @param projectNo
     * @param msgBody
     */
    @Override
    public void updateNifaTenderInfo(String projectNo, String msgBody) {
        Query query = new Query();
        Criteria criteria = Criteria.where("projectNo").is(projectNo).and("message").is(msgBody);
        query.addCriteria(criteria);
        List<NifaTenderInfoEntity> nifaTenderInfoEntities = this.nifaTenderInfoDao.find(query);
        if (null != nifaTenderInfoEntities && nifaTenderInfoEntities.size() > 0) {
            Update update = new Update();
            update.set("reportStatus", "1");
            this.nifaTenderInfoDao.update(query,update);
        }
    }
}
