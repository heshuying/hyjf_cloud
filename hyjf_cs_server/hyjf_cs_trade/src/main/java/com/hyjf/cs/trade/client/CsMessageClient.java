/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.HjhPlanCapitalActualResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.bifa.*;

import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version CsMessageClient, v0.1 2018/8/14 19:46
 */
public interface CsMessageClient {
    Integer insertAccountWebList(AccountWebListVO accountWebList);

	AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId);

    /**
     * 取得检证数据
     * @param id
     * @return
     */
    ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id);

    /**
     * 更新状态
     * @param uuid
     * @param status
     */
    void updateChinapnrExclusiveLogStatus(long uuid, String status);

    /**
     * 将状态更新成[2:处理中]
     * @param record
     * @return
     */
    int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record);

    /**
     * 取得成功时的信息
     * @param ordId
     * @return
     */
    List<ChinapnrLogVO> getChinapnrLog(String ordId);

    /**
     * 根据订单号查询
     * @param logOrdId
     * @return
     */
    CertReportEntityVO getCertSendLogByLogOrdId(String logOrdId);

    /**
     * mongo中获取散标信息
     * @param borrowNid
     * @return
     */
    BifaBorrowInfoEntityVO getBifaBorrowInfoFromMongoDB(String borrowNid);

    /**
     * 获取用户索引信息
     * @param jsonObject
     * @return
     */
    BifaUserInfoSHA256EntityVO selectUserIdToSHA256(JSONObject jsonObject);

    /**
     * 散标上报保存本地mongo
     * @param reportResult
     */
    void insertBorrowInfoReportData(BifaBorrowInfoEntityVO reportResult);

    /**
     * mongo中获取智投信息
     * @param planNid
     * @return
     */
    BifaHjhPlanInfoEntityVO getBifaHjhPlanInfoFromMongoDB(String planNid);

    /**
     * 智投上报数据保存本地mongo
     * @param reportResult
     */
    void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntityVO reportResult);

    /**
     * mongo中获取产品状态更新
     * @param borrowNid
     * @param status
     * @return
     */
    BifaBorrowStatusEntityVO getBifaBorrowStatusFromMongoDB(String borrowNid, Integer status);

    /**
     * 产品状态更新上报数据保存本地mongo
     * @param reportResult
     */
    void insertBorrowStatusReportData(BifaBorrowStatusEntityVO reportResult);

    /**
     * mongo中获取债转信息
     * @param sourceProductCode
     * @return
     */
    BifaCreditTenderInfoEntityVO getBifaBorrowCreditInfoFromMongDB(String sourceProductCode);

    /**
     * 债转上报信息保存本地mongo
     * @param reportResult
     */
    void insertCreditTenderInfoReportData(BifaCreditTenderInfoEntityVO reportResult);

    /**
     * 获取上报失败的标的信息
     * @return
     */
    List<BifaBorrowInfoEntityVO> getReportFailBorrowInfoFromMongo();

    /**
     * 更新mongo中的散标
     * @param result
     */
    void updateBorrowInfo(BifaBorrowInfoEntityVO result);

    /**
     * 获取上报失败的智投信息
     * @return
     */
    List<BifaHjhPlanInfoEntityVO> getReportFailHjhPlanInfoFromMongo();

    /**
     * 更新mongo中的智投
     * @param result
     */
    void updateHjhPlanInfo(BifaHjhPlanInfoEntityVO result);

    /**
     * 获取上报失败的债转
     * @return
     */
    List<BifaCreditTenderInfoEntityVO> getReportFailCreditInfoFromMongo();

    /**
     * 更新债转
     * @param result
     */
    void updateCreditInfo(BifaCreditTenderInfoEntityVO result);

    /**
     * 获取上报失败的产品状态
     * @return
     */
    List<BifaBorrowStatusEntityVO> getReportFailBorrowStatusFromMongo();

    /**
     * 获取上报失败的散标
     * @param borrowNid
     * @return
     */
    BifaBorrowInfoEntityVO getReportFailBorrowInfoFromMongo(String borrowNid);

    /**
     * 获取上报成功的散标
     * @param borrowNid
     * @return
     */
    BifaBorrowInfoEntityVO getReportSuccessBorrowInfoFromMongo(String borrowNid);

    /**
     * 获取上报成功的智投
     * @param planNid
     * @return
     */
    BifaHjhPlanInfoEntityVO getReportSuccessHjhPlanInfoFromMongo(String planNid);

    /**
     * 获取上报失败的智投
     * @param planNid
     * @return
     */
    BifaHjhPlanInfoEntityVO getReportFailHjhPlanInfoFromMongo(String planNid);

    /**
     * 更新产品状态
     * @param result
     */
    void updateBorrowStatus(BifaBorrowStatusEntityVO result);

    /**
     * 更新sha256表中用户对应状态
     * @param dataType
     */
    void updateUserIndexReportStatus(Integer userId, String dataType);

    /**
     * 获取sha256对象
     * @param isOpenUp
     * @param isLenderZeroUp
     * @param isLenderOneUp
     * @return
     */
    List<BifaUserInfoSHA256EntityVO> getUserInfoSHA256(String isOpenUp, String isLenderZeroUp, String isLenderOneUp);

    /**
     * 从mongo中获取借贷用户索引信息
     * @param borrowNid
     * @return
     */
    BifaIndexUserInfoEntityVO getBorrowUserInfoFromMongo(String borrowNid);

    /**
     * 借贷用户数据保存mongo
     * @param entity
     */
    void insertBorrowUserInfoReportData(BifaIndexUserInfoEntityVO entity);

    /**
     *
     * @return
     */
    TotalInvestAndInterestVO getTotalInvestAndInterestVO();

    /**
     * 运营数据保存本地mongo
     * @param bifaOperationDataEntity
     */
    void insertOperationReportData(BifaOperationDataEntityVO bifaOperationDataEntity);

    /**
     * 资金计划3.3.0插入mongo
     *  @author wenxin
     * @date 2019/4/16 15:18
     */
    boolean insertPlanCapitalForCreditInfo(List<HjhPlanCapitalPredictionVO> listHjhPlanCapitalPrediction);

    /**
     * 资金计划3.3.0插入mongo（实际）
     *  @author wenxin
     * @date 2019/4/23 12:16
     */
    boolean insertCapitalActualInfo(List<HjhPlanCapitalActualVO> listHjhPlanCapitalActual);

    /**
     * 资金计划3.3.0获取mongo（实际）
     *  @author wenxin
     * @date 2019/4/23 14:31
     */
    HjhPlanCapitalActualResponse getPlanCapitalActualInfo(HjhPlanCapitalActualRequest hjhPlanCapitalActualRequest);

    /**
     * 历史数据update delFlg->1
     *
     * @param initDate
     * @return
     */
    boolean updatePlanCapitalForCreditInfo(Date initDate);
}
