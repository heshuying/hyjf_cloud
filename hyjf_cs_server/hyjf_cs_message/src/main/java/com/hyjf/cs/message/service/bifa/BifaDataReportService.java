/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.bifa;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BifaOperationDataEntityVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.*;

import java.util.List;

/**
 * @author jun
 * @version BifaDataReport, v0.1 2019/1/16 10:01
 */
public interface BifaDataReportService extends BaseService {

    BifaBorrowInfoEntity getBifaBorrowInfoFromMongoDB(String borrowNid);

    BifaUserInfoSHA256Entity selectUserIdToSHA256(JSONObject jsonObject);

    void insertBorrowInfoReportData(BifaBorrowInfoEntity data);

    BifaHjhPlanInfoEntity getBifaHjhPlanInfoFromMongoDB(String planNid);

    void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntity data);

    BifaBorrowStatusEntity getBifaBorrowStatusFromMongoDB(String borrowNid, Integer status);

    void insertBorrowStatusReportData(BifaBorrowStatusEntity data);

    BifaCreditTenderInfoEntity getBifaBorrowCreditInfoFromMongDB(String sourceProductCode);

    void insertCreditTenderInfoReportData(BifaCreditTenderInfoEntity data);

    List<BifaBorrowInfoEntity> getReportFailBorrowInfoFromMongo();

    void updateBorrowInfo(BifaBorrowInfoEntity data);

    List<BifaHjhPlanInfoEntity> getReportFailHjhPlanInfoFromMongo();

    void updateHjhPlanInfo(BifaHjhPlanInfoEntity data);

    List<BifaCreditTenderInfoEntity> getReportFailCreditInfoFromMongo();

    void updateCreditInfo(BifaCreditTenderInfoEntity data);

    List<BifaBorrowStatusEntity> getReportFailBorrowStatusFromMongo();

    BifaBorrowInfoEntity getReportSuccessBorrowInfoFromMongo(String borrowNid);

    BifaBorrowInfoEntity getReportFailBorrowInfoFromMongo(String borrowNid);

    BifaHjhPlanInfoEntity getReportSuccessHjhPlanInfoFromMongo(String planNid);

    BifaHjhPlanInfoEntity getReportFailHjhPlanInfoFromMongo(String planNid);

    void updateBorrowStatus(BifaBorrowStatusEntity data);

    void updateUserIndexReportStatus(Integer userId, String dataType);

    List<BifaUserInfoSHA256Entity> getUserInfoSHA256(String isOpenUp, String isLenderZeroUp, String isLenderOneUp);

    BifaIndexUserInfoEntity getBorrowUserInfoFromMongo(String borrowNid);

    void insertBorrowUserInfoReportData(BifaIndexUserInfoEntity result);

    TotalInvestAndInterestEntity getTotalInvestAndInterestVO();

    void insertOperationReportData(BifaOperationDataEntity data);
}
