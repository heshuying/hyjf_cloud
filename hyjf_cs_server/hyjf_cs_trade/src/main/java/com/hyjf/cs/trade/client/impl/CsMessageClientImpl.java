/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalActualResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.bifa.*;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.CertReportEntityResponse;
import com.hyjf.am.response.trade.ChinapnrExclusiveLogWithBLOBsResponse;
import com.hyjf.am.response.trade.ChinapnrLogResponse;
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
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.trade.client.CsMessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version CsMessageClientImpl, v0.1 2018/8/14 19:46
 */
@Cilent
public class CsMessageClientImpl implements CsMessageClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer insertAccountWebList(AccountWebListVO accountWebList) {
        String url = "http://CS-MESSAGE/cs-message/accountweblist/insertaccountweblist";
        AccountWebListResponse response = restTemplate.postForEntity(url,accountWebList,AccountWebListResponse.class).getBody();
        if (response != null) {
            return response.getRecordTotal();
        }
        return null;
    }
    /**
     * 根据userId查询用户渠道信息
     *
     * @param userId
     * @return
     */
    @Override
    public AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId) {
        AppUtmRegResponse response = restTemplate.getForEntity(
                "http://AM-USER/am-user/app_utm_reg/findByUserId/" + userId,
                AppUtmRegResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id) {
        String url = "http://CS-MESSAGE/cs-message/chinapnr/selectChinapnrExclusiveLog/"+id ;
        ChinapnrExclusiveLogWithBLOBsResponse response = restTemplate.getForEntity(url,ChinapnrExclusiveLogWithBLOBsResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public void updateChinapnrExclusiveLogStatus(long uuid, String status) {
        String url = "http://CS-MESSAGE/cs-message/chinapnr/updateChinapnrExclusiveLogStatus/"+uuid+"/"+ status;
        restTemplate.getForEntity(url,IntegerResponse.class).getBody();
    }

    @Override
    public int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record) {
        IntegerResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/chinapnr/updateChinapnrExclusiveLog", record, IntegerResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultInt();
        }
        return 0;
    }



    @Override
    public List<ChinapnrLogVO> getChinapnrLog(String ordId) {
        String url = "http://CS-MESSAGE/cs-message/chinapnr/getChinapnrLog/"+ordId ;
        ChinapnrLogResponse response = restTemplate.getForEntity(url,ChinapnrLogResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据订单号查询
     *
     * @param logOrdId
     * @return
     */
    @Override
    public CertReportEntityVO getCertSendLogByLogOrdId(String logOrdId) {
        String url = "http://CS-MESSAGE/cs-message/certStatistical/getCertSendLogByLogOrdId/"+logOrdId ;
        CertReportEntityResponse response = restTemplate.getForEntity(url,CertReportEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BifaBorrowInfoEntityVO getBifaBorrowInfoFromMongoDB(String borrowNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getBifaBorrowInfoFromMongoDB/"+borrowNid;
        BifaBorrowInfoEntityResponse response = restTemplate.getForEntity(url,BifaBorrowInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户索引信息
     * @return
     */
    @Override
    public BifaUserInfoSHA256EntityVO selectUserIdToSHA256(JSONObject jsonObject) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/selectUserIdToSHA256";
        UserInfoSHA256EntityResponse response = restTemplate.postForEntity(url,jsonObject,UserInfoSHA256EntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public void insertBorrowInfoReportData(BifaBorrowInfoEntityVO reportResult) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/insertBorrowInfoReportData";
        restTemplate.postForEntity(url,reportResult,null).getBody();
    }

    @Override
    public BifaHjhPlanInfoEntityVO getBifaHjhPlanInfoFromMongoDB(String planNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getBifaHjhPlanInfoFromMongoDB/"+planNid;
        BifaHjhPlanInfoEntityResponse response = restTemplate.getForEntity(url,BifaHjhPlanInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntityVO reportResult) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/insertHjhPlanInfoReportData";
        restTemplate.postForEntity(url,reportResult,null).getBody();
    }

    @Override
    public BifaBorrowStatusEntityVO getBifaBorrowStatusFromMongoDB(String borrowNid, Integer status) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getBifaBorrowStatusFromMongoDB/"+borrowNid+"/"+status;
        BifaBorrowStatusEntityResponse response=restTemplate.getForEntity(url,BifaBorrowStatusEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public void insertBorrowStatusReportData(BifaBorrowStatusEntityVO reportResult) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/insertBorrowStatusReportData";
        restTemplate.postForEntity(url,reportResult,null).getBody();
    }

    @Override
    public BifaCreditTenderInfoEntityVO getBifaBorrowCreditInfoFromMongDB(String sourceProductCode) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getBifaBorrowCreditInfoFromMongDB/"+sourceProductCode;
        BifaCreditTenderInfoEntityResponse response=restTemplate.getForEntity(url,BifaCreditTenderInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 债转上报数据保存本地mongo
     * @param reportResult
     */
    @Override
    public void insertCreditTenderInfoReportData(BifaCreditTenderInfoEntityVO reportResult) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/insertCreditTenderInfoReportData";
        restTemplate.postForEntity(url,reportResult,null).getBody();
    }

    /**
     * 获取上报失败的散标信息
     * @return
     */
    @Override
    public List<BifaBorrowInfoEntityVO> getReportFailBorrowInfoFromMongo() {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportFailBorrowInfoFromMongo";
        BifaBorrowInfoEntityResponse response = restTemplate.getForEntity(url,BifaBorrowInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新mongo中的散标
     * @param result
     */
    @Override
    public void updateBorrowInfo(BifaBorrowInfoEntityVO result) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/updateBorrowInfo";
        restTemplate.postForEntity(url,result,null).getBody();
    }

    /**
     * 获取上报失败的智投
     * @return
     */
    @Override
    public List<BifaHjhPlanInfoEntityVO> getReportFailHjhPlanInfoFromMongo() {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportFailHjhPlanInfoFromMongo";
        BifaHjhPlanInfoEntityResponse response = restTemplate.getForEntity(url,BifaHjhPlanInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新mongo中的智投
     * @param result
     */
    @Override
    public void updateHjhPlanInfo(BifaHjhPlanInfoEntityVO result) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/updateHjhPlanInfo";
        restTemplate.postForEntity(url,result,null).getBody();
    }

    /**
     * 获取上报失败的债转
     * @return
     */
    @Override
    public List<BifaCreditTenderInfoEntityVO> getReportFailCreditInfoFromMongo() {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportFailCreditInfoFromMongo";
        BifaCreditTenderInfoEntityResponse response = restTemplate.getForEntity(url,BifaCreditTenderInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新债转
     * @param result
     */
    @Override
    public void updateCreditInfo(BifaCreditTenderInfoEntityVO result) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/updateCreditInfo";
        restTemplate.postForEntity(url,result,null).getBody();
    }

    /**
     * 获取上报失败的产品状态
     * @return
     */
    @Override
    public List<BifaBorrowStatusEntityVO> getReportFailBorrowStatusFromMongo() {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportFailBorrowStatusFromMongo";
        BifaBorrowStatusEntityResponse response = restTemplate.getForEntity(url,BifaBorrowStatusEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取上报失败的散标
     * @param borrowNid
     * @return
     */
    @Override
    public BifaBorrowInfoEntityVO getReportFailBorrowInfoFromMongo(String borrowNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportFailBorrowInfoFromMongo/"+borrowNid;
        BifaBorrowInfoEntityResponse response=restTemplate.getForEntity(url,BifaBorrowInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取上报成功的散标
     * @param borrowNid
     * @return
     */
    @Override
    public BifaBorrowInfoEntityVO getReportSuccessBorrowInfoFromMongo(String borrowNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportSuccessBorrowInfoFromMongo/"+borrowNid;
        BifaBorrowInfoEntityResponse response=restTemplate.getForEntity(url,BifaBorrowInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取上报成功的智投
     * @param planNid
     * @return
     */
    @Override
    public BifaHjhPlanInfoEntityVO getReportSuccessHjhPlanInfoFromMongo(String planNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportSuccessHjhPlanInfoFromMongo/"+planNid;
        BifaHjhPlanInfoEntityResponse response = restTemplate.getForEntity(url,BifaHjhPlanInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取上报失败的智投
     * @param planNid
     * @return
     */
    @Override
    public BifaHjhPlanInfoEntityVO getReportFailHjhPlanInfoFromMongo(String planNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getReportFailHjhPlanInfoFromMongo/"+planNid;
        BifaHjhPlanInfoEntityResponse response = restTemplate.getForEntity(url,BifaHjhPlanInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新产品状态
     * @param result
     */
    @Override
    public void updateBorrowStatus(BifaBorrowStatusEntityVO result) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/updateBorrowStatus";
        restTemplate.postForEntity(url,result,null).getBody();
    }

    /**
     * 更新sha256表中用户对应状态
     * @param userId
     * @param dataType
     */
    @Override
    public void updateUserIndexReportStatus(Integer userId, String dataType) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/updateUserIndexReportStatus/"+userId+"/"+dataType;
        restTemplate.getForEntity(url,null).getBody();
    }

    /**
     * 获取sha256对象
     * @param isOpenUp
     * @param isLenderZeroUp
     * @param isLenderOneUp
     * @return
     */
    @Override
    public List<BifaUserInfoSHA256EntityVO> getUserInfoSHA256(String isOpenUp, String isLenderZeroUp, String isLenderOneUp) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getUserInfoSHA256/"+isOpenUp+"/"+isLenderZeroUp+"/"+isLenderOneUp;
        UserInfoSHA256EntityResponse response=restTemplate.getForEntity(url,UserInfoSHA256EntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 从mongo中获取借贷用户索引信息
     * @param borrowNid
     * @return
     */
    @Override
    public BifaIndexUserInfoEntityVO getBorrowUserInfoFromMongo(String borrowNid) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getBorrowUserInfoFromMongo/"+borrowNid;
        BifaIndexUserInfoEntityResponse response = restTemplate.getForEntity(url,BifaIndexUserInfoEntityResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 借贷用户保存mongo
     * @param entity
     */
    @Override
    public void insertBorrowUserInfoReportData(BifaIndexUserInfoEntityVO entity) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/insertBorrowUserInfoReportData";
        restTemplate.postForEntity(url,entity,null).getBody();
    }

    @Override
    public TotalInvestAndInterestVO getTotalInvestAndInterestVO() {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/getTotalInvestAndInterestVO";
        TotalInvestAndInterestResponse response=restTemplate.getForEntity(url,TotalInvestAndInterestResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 运营数据保存本地mongo
     * @param bifaOperationDataEntity
     */
    @Override
    public void insertOperationReportData(BifaOperationDataEntityVO bifaOperationDataEntity) {
        String url = "http://CS-MESSAGE/cs-message/bifaDataReport/insertOperationReportData";
        restTemplate.postForEntity(url,bifaOperationDataEntity,null).getBody();
    }

    /**
     * 统计预计新增债转额
     * @param listHjhPlanCapitalPrediction
     * @return boolean
     */
    @Override
    public boolean insertPlanCapitalForCreditInfo(List<HjhPlanCapitalPredictionVO> listHjhPlanCapitalPrediction){
        String url = "http://CS-MESSAGE/cs-message/hjhPlanCapitalPrediction/insertPlanCaptialPrediction";
        BooleanResponse response = restTemplate.postForEntity(url, listHjhPlanCapitalPrediction, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }
    /**
     * 统计预计新增债转额
     * @param listHjhPlanCapitalActual
     * @return boolean
     */
    @Override
    public boolean insertCapitalActualInfo(List<HjhPlanCapitalActualVO> listHjhPlanCapitalActual){
        String url = "http://CS-MESSAGE/cs-message/hjhPlanCapitalActual/insertPlanCaptialActual";
        BooleanResponse response = restTemplate.postForEntity(url, listHjhPlanCapitalActual, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 获取汇计划--计划资金3.3.0列表（实际）(从MongoDB读取数据)
     * @param hjhPlanCapitalActualRequest
     * @return
     * @Author : wenxin
     */
    @Override
    public HjhPlanCapitalActualResponse getPlanCapitalActualInfo(HjhPlanCapitalActualRequest hjhPlanCapitalActualRequest){
        HjhPlanCapitalActualResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/hjhPlanCapitalActual/getPlanCapitalActualList",
                hjhPlanCapitalActualRequest, HjhPlanCapitalActualResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 历史数据update delFlg->1
     *
     * @param initDate
     * @return
     */
    @Override
    public boolean updatePlanCapitalForCreditInfo(String initDate) {
        String url = "http://CS-MESSAGE/cs-message/hjhPlanCapitalPrediction/updatePlanCaptialPrediction/" + initDate;
        BooleanResponse response = restTemplate.getForEntity(url, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }

}
