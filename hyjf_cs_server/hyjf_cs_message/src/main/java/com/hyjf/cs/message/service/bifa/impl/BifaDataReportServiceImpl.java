/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.SHA256Util;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.*;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.mongo.mc.bifa.*;
import com.hyjf.cs.message.service.bifa.BifaDataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jun
 * @version BifaDataReportImpl, v0.1 2019/1/16 10:02
 */
@Service
public class BifaDataReportServiceImpl extends BaseServiceImpl implements BifaDataReportService {
    @Autowired
    private BifaBorrowInfoDao bifaBorrowInfoDao;
    @Autowired
    private UserInfoSHA256Dao userInfoSHA256Dao;
    @Autowired
    private BifaHjhPlanInfoDao bifaHjhPlanInfoDao;
    @Autowired
    private BifaBorrowStatusDao bifaBorrowStatusDao;
    @Autowired
    private BifaCreditTenderInfoDao bifaCreditTenderInfoDao;
    @Autowired
    private BifaBorrowUserInfoIndexDao bifaBorrowUserInfoIndexDao;
    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;
    @Autowired
    private BifaOperationDataDao bifaOperationDataDao;
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 出借=0的用户  未开户用户不报送
     */
    public static final String DATATYPE_1003 = "1003";
    /**
     * 出借大于0的用户
     */
    public static final String DATATYPE_1002 = "1002";
    /**
     * 注册用户
     */
    public static final String DATATYPE_1005 = "1005";
    /**
     *借贷用户
     */
    public static final String DATATYPE_1000 = "1000";

    @Override
    public BifaBorrowInfoEntity getBifaBorrowInfoFromMongoDB(String borrowNid) {
        return bifaBorrowInfoDao.findOne(new Query(Criteria.where("source_product_code").is(borrowNid)));
    }

    @Override
    public BifaUserInfoSHA256Entity selectUserIdToSHA256(JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        String trueName = jsonObject.getString("trueName");
        String idCard = jsonObject.getString("idCard");
        // 查询mongo是否已存该加密信息
        Query query = new Query();
        Criteria criteria = null;
        if (userId != null) {
            criteria = Criteria.where("userId").is(userId);
        } else {
            criteria = Criteria.where("trueName").is(trueName).and("idCard").is(idCard);
        }
        query.addCriteria(criteria);
        BifaUserInfoSHA256Entity userInfoSHA256Entity = this.userInfoSHA256Dao.findOne(query);
        // 取到返回、未取到初始化后入库
        if (null == userInfoSHA256Entity) {
            // 未取到数据重新加密传入
            userInfoSHA256Entity = new BifaUserInfoSHA256Entity();
            String input = trueName.concat(idCard);
            String output = SHA256Util.getSHA256(input);
            // 主键
            userInfoSHA256Entity.setUserId(userId);
            // 用户姓名
            userInfoSHA256Entity.setTrueName(trueName);
            // 用户身份证号
            userInfoSHA256Entity.setIdCard(idCard);
            // 已投资用户是否上报数据
            userInfoSHA256Entity.setIsLenderOneUp("0");
            // 开户未投资用户是否上报数据
            userInfoSHA256Entity.setIsLenderZeroUp("0");
            // 开户用户是否上报数据
            userInfoSHA256Entity.setIsOpenUp("0");
            // 密文
            userInfoSHA256Entity.setSha256(output);
            // 插入时间
            userInfoSHA256Entity.setCreateTime(GetDate.getDate());
            // mongo插入
            this.userInfoSHA256Dao.save(userInfoSHA256Entity);
        }
        return userInfoSHA256Entity;
    }

    @Override
    public synchronized void insertBorrowInfoReportData(BifaBorrowInfoEntity data) {
        BifaBorrowInfoEntity resultMongoDB = bifaBorrowInfoDao.findOne(new Query(Criteria.where("source_product_code").is(data.getSource_product_code())));
        if (resultMongoDB == null){
            bifaBorrowInfoDao.insert(data);
        }
    }

    /**
     *  mongo中获取智投信息
     * @param planNid
     * @return
     */
    @Override
    public BifaHjhPlanInfoEntity getBifaHjhPlanInfoFromMongoDB(String planNid) {
        return bifaHjhPlanInfoDao.findOne(new Query(Criteria.where("source_product_code").is(planNid)));
    }

    /**
     * 智投上报数据保存本能mongo
     * @param data
     */
    @Override
    public synchronized void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntity data) {
        BifaHjhPlanInfoEntity resultMongoDB = bifaHjhPlanInfoDao.findOne(new Query(Criteria.where("source_product_code").is(data.getSource_product_code())));
        if (resultMongoDB == null){
            bifaHjhPlanInfoDao.insert(data);
        }
    }

    @Override
    public BifaBorrowStatusEntity getBifaBorrowStatusFromMongoDB(String borrowNid, Integer status) {
        String statusStr = "";
        switch (status){
            case 4:
                //还款中(放款后)
                statusStr="1";
                break;
            case 5:
                //最后一笔还款完后
                statusStr="3";
                break;
            default:
                break;
        }
        //失败的情况留给batch处理
        return bifaBorrowStatusDao.findOne(new Query(Criteria.where("source_product_code").is(borrowNid).and("product_status").is(statusStr)));
    }

    /**
     * 保存产品状态更新到mongoDB
     * @param data
     */
    @Override
    public synchronized void insertBorrowStatusReportData(BifaBorrowStatusEntity data) {
        //校验是否智投
        boolean isHjh = this.isHjh(data.getSource_product_code());
        if (!isHjh){
            //散标
            BifaBorrowStatusEntity resultMongoDB = bifaBorrowStatusDao.findOne(
                    new Query(Criteria.where("source_product_code").is(data.getSource_product_code()).and("product_status").is(data.getProduct_status())));
            if (resultMongoDB == null){
                bifaBorrowStatusDao.insert(data);
            }
        }else {
            //智投下的标的
            bifaBorrowStatusDao.insert(data);
        }
    }

    /**
     * 校验是否智投
     * @param source_product_code
     * @return
     */
    private boolean isHjh(String planNid) {
        HjhPlanVO hjhPlanVO = amTradeClient.getHjhPlan(planNid);
        if (hjhPlanVO != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * mongo中获取债转信息
     * @param sourceProductCode
     * @return
     */
    @Override
    public BifaCreditTenderInfoEntity getBifaBorrowCreditInfoFromMongDB(String sourceProductCode) {
        return bifaCreditTenderInfoDao.findOne(new Query(Criteria.where("source_product_code").is(sourceProductCode)));
    }

    /**
     * 债转上报数据保存本地mongo
     * @param data
     */
    @Override
    public synchronized void insertCreditTenderInfoReportData(BifaCreditTenderInfoEntity data) {
        BifaCreditTenderInfoEntity resultMongoDB = bifaCreditTenderInfoDao.findOne(
                new Query(Criteria.where("source_product_code").is(data.getSource_product_code())));
        if (resultMongoDB == null){
            bifaCreditTenderInfoDao.insert(data);
        }
    }

    /**
     * 获取上报失败的散标
     * @return
     */
    @Override
    public List<BifaBorrowInfoEntity> getReportFailBorrowInfoFromMongo() {
        return bifaBorrowInfoDao.find(new Query(Criteria.where("reportStatus").is("9")));
    }

    /**
     * 更新mongo中的散标
     * @param data
     */
    @Override
    public void updateBorrowInfo(BifaBorrowInfoEntity result) {
        Query query = new Query(Criteria.where("_id").is(result.getId()));
        Update update = new Update();
        update.set("reportStatus", result.getReportStatus());
        update.set("errCode",result.getErrCode());
        update.set("errDesc",result.getErrDesc());
        update.set("updateTime",GetDate.getDate());
        bifaBorrowInfoDao.update(query,update);
    }

    /**
     * 获取上报失败的智投
     * @return
     */
    @Override
    public List<BifaHjhPlanInfoEntity> getReportFailHjhPlanInfoFromMongo() {
        return bifaHjhPlanInfoDao.find(new Query(Criteria.where("reportStatus").is("9")));
    }

    /**
     * 更新智投
     * @param data
     */
    @Override
    public void updateHjhPlanInfo(BifaHjhPlanInfoEntity result) {
        Query query = new Query(Criteria.where("_id").is(result.getId()));
        Update update = new Update();
        update.set("reportStatus", result.getReportStatus());
        update.set("errCode",result.getErrCode());
        update.set("errDesc",result.getErrDesc());
        update.set("updateTime",GetDate.getDate());
        bifaHjhPlanInfoDao.update(query,update);
    }

    /**
     * 获取上报失败的债转
     * @return
     */
    @Override
    public List<BifaCreditTenderInfoEntity> getReportFailCreditInfoFromMongo() {
        return bifaCreditTenderInfoDao.find(new Query(Criteria.where("reportStatus").is("9")));
    }

    /**
     * 更新债转
     * @param result
     */
    @Override
    public void updateCreditInfo(BifaCreditTenderInfoEntity result) {
        Query query = new Query(Criteria.where("_id").is(result.getId()));
        Update update = new Update();
        update.set("reportStatus", result.getReportStatus());
        update.set("errCode",result.getErrCode());
        update.set("errDesc",result.getErrDesc());
        update.set("updateTime",GetDate.getDate());
        bifaCreditTenderInfoDao.update(query,update);
    }

    /**
     * 获取上报失败的产品状态
     * @return
     */
    @Override
    public List<BifaBorrowStatusEntity> getReportFailBorrowStatusFromMongo() {
        return bifaBorrowStatusDao.find(new Query(Criteria.where("reportStatus").is("9")));
    }

    /**
     * 获取上报成功的散标
     * @param borrowNid
     * @return
     */
    @Override
    public BifaBorrowInfoEntity getReportSuccessBorrowInfoFromMongo(String borrowNid) {
        Query query = new Query(Criteria.where("source_product_code").is(borrowNid).and("reportStatus").in("1","7"));
        BifaBorrowInfoEntity borrowInfo = bifaBorrowInfoDao.findOne(query);
        return borrowInfo;
    }

    /**
     * 获取上报失败的散标
     * @param borrowNid
     * @return
     */
    @Override
    public BifaBorrowInfoEntity getReportFailBorrowInfoFromMongo(String borrowNid) {
        Query query = new Query(Criteria.where("source_product_code").is(borrowNid).and("reportStatus").is("9"));
        BifaBorrowInfoEntity borrowInfo = bifaBorrowInfoDao.findOne(query);
        return borrowInfo;
    }

    /**
     * 获取上报成功的智投
     * @param planNid
     * @return
     */
    @Override
    public BifaHjhPlanInfoEntity getReportSuccessHjhPlanInfoFromMongo(String planNid) {
        BifaHjhPlanInfoEntity bifaHjhPlanInfoEntity=bifaHjhPlanInfoDao.findOne(new Query(Criteria.where("reportStatus").in("1","7").and("source_product_code").is(planNid)));
        return bifaHjhPlanInfoEntity;
    }

    /**
     * 获取上报失败的智投
     * @param planNid
     * @return
     */
    @Override
    public BifaHjhPlanInfoEntity getReportFailHjhPlanInfoFromMongo(String planNid) {
        BifaHjhPlanInfoEntity bifaHjhPlanInfoEntity = bifaHjhPlanInfoDao.findOne(new Query(Criteria.where("source_product_code").is(planNid).and("reportStatus").is("9")));
        return bifaHjhPlanInfoEntity;
    }

    /**
     * 更新产品状态
     * @param result
     */
    @Override
    public void updateBorrowStatus(BifaBorrowStatusEntity result) {
        Query query = new Query(Criteria.where("_id").is(result.getId()));
        Update update = new Update();
        update.set("reportStatus", result.getReportStatus());
        update.set("errCode",result.getErrCode());
        update.set("errDesc",result.getErrDesc());
        update.set("updateTime",GetDate.getDate());
        bifaBorrowStatusDao.update(query,update);
    }

    /**
     * 更新sha256表中用户对应状态
     * @param userId
     * @param dataType
     */
    @Override
    public void updateUserIndexReportStatus(Integer userId, String dataType) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);
        Update update = new Update();

        if (DATATYPE_1003.equals(dataType)) {
            //出借等于0的用户
            update.set("isLenderZeroUp", "1");
        } else if (DATATYPE_1002.equals(dataType)) {
            //出借大于0的用户
            update.set("isLenderOneUp", "1");
        } else if (DATATYPE_1005.equals(dataType)) {
            //已开户的用户
            update.set("isOpenUp", "1");
        }
        update.set("updateTime", GetDate.getDate());
        this.userInfoSHA256Dao.update(query, update);
    }

    /**
     * 获取用户的sha256
     * @param isOpenUp
     * @param isLenderZeroUp
     * @param isLenderOneUp
     * @return
     */
    @Override
    public List<BifaUserInfoSHA256Entity> getUserInfoSHA256(String isOpenUp, String isLenderZeroUp, String isLenderOneUp) {
        Query query = new Query();
        Criteria criteria = Criteria.where("isOpenUp").is(isOpenUp).and("isLenderZeroUp").is(isLenderZeroUp).and("isLenderOneUp").is(isLenderOneUp);
        query.addCriteria(criteria);
        List<BifaUserInfoSHA256Entity> userInfoSHA256EntityList = this.userInfoSHA256Dao.find(query);
        return userInfoSHA256EntityList;
    }

    /**
     * 获取借贷用户信息
     * @param borrowNid
     * @return
     */
    @Override
    public BifaIndexUserInfoEntity getBorrowUserInfoFromMongo(String borrowNid) {
        return bifaBorrowUserInfoIndexDao.findOne(new Query(Criteria.where("borrowNid").is(borrowNid)));
    }

    /**
     * 借贷用户信息保存本地mongo
     * @param result
     */
    @Override
    public void insertBorrowUserInfoReportData(BifaIndexUserInfoEntity result) {
        bifaBorrowUserInfoIndexDao.insert(result);
    }

    @Override
    public TotalInvestAndInterestEntity getTotalInvestAndInterestVO() {
        return totalInvestAndInterestMongoDao.findOne(new Query());
    }

    /**
     * 运营数据保存本地mongo
     * @param data
     */
    @Override
    public void insertOperationReportData(BifaOperationDataEntity data) {
        bifaOperationDataDao.insert(data);
    }
}
