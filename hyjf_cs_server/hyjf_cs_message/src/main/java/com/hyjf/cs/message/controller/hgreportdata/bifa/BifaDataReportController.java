/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.hgreportdata.bifa;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.bifa.*;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.bifa.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.*;
import com.hyjf.cs.message.service.bifa.BifaDataReportService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author jun
 * @version BifaDataReportController, v0.1 2019/1/16 9:47
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/bifaDataReport")
public class BifaDataReportController extends BaseController {

    @Autowired
    private BifaDataReportService bifaDataReportService;

    @GetMapping("/getBifaBorrowInfoFromMongoDB/{borrowNid}")
    public BifaBorrowInfoEntityResponse getBifaBorrowInfoFromMongoDB(@PathVariable String borrowNid){
        BifaBorrowInfoEntityResponse response = new BifaBorrowInfoEntityResponse();
        BifaBorrowInfoEntity entityMd = bifaDataReportService.getBifaBorrowInfoFromMongoDB(borrowNid);
        if (entityMd!=null) {
            response.setResult(CommonUtils.convertBean(entityMd,BifaBorrowInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @PostMapping("/selectUserIdToSHA256")
    public UserInfoSHA256EntityResponse selectUserIdToSHA256(@RequestBody JSONObject jsonObject){
        UserInfoSHA256EntityResponse response = new UserInfoSHA256EntityResponse();
        BifaUserInfoSHA256Entity entityMd = bifaDataReportService.selectUserIdToSHA256(jsonObject);
        if (entityMd!=null){
            response.setResult(CommonUtils.convertBean(entityMd,BifaUserInfoSHA256EntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @PostMapping("/insertBorrowInfoReportData")
    public void insertBorrowInfoReportData(@RequestBody BifaBorrowInfoEntityVO reportResult){
        BifaBorrowInfoEntity data=CommonUtils.convertBean(reportResult,BifaBorrowInfoEntity.class);
        bifaDataReportService.insertBorrowInfoReportData(data);
    }

    @GetMapping("/getBifaHjhPlanInfoFromMongoDB/{planNid}")
    public BifaHjhPlanInfoEntityResponse getBifaHjhPlanInfoFromMongoDB(@PathVariable String planNid){
        BifaHjhPlanInfoEntityResponse response = new BifaHjhPlanInfoEntityResponse();
        BifaHjhPlanInfoEntity entityMd=bifaDataReportService.getBifaHjhPlanInfoFromMongoDB(planNid);
        if (entityMd!=null){
            response.setResult(CommonUtils.convertBean(entityMd,BifaHjhPlanInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @PostMapping("/insertHjhPlanInfoReportData")
    public void insertHjhPlanInfoReportData(@RequestBody BifaHjhPlanInfoEntityVO reportResult){
        BifaHjhPlanInfoEntity data=CommonUtils.convertBean(reportResult,BifaHjhPlanInfoEntity.class);
        bifaDataReportService.insertHjhPlanInfoReportData(data);
    }

    @GetMapping("/getBifaBorrowStatusFromMongoDB/{borrowNid}/{status}")
    public BifaBorrowStatusEntityResponse getBifaBorrowStatusFromMongoDB(@PathVariable String borrowNid, @PathVariable Integer status){
        BifaBorrowStatusEntityResponse response = new BifaBorrowStatusEntityResponse();
        BifaBorrowStatusEntity entityMd=bifaDataReportService.getBifaBorrowStatusFromMongoDB(borrowNid,status);
        if (entityMd!=null){
            response.setResult(CommonUtils.convertBean(entityMd,BifaBorrowStatusEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 产品状态更新数据保存本地mongo
     * @param reportResult
     */
    @PostMapping("/insertBorrowStatusReportData")
    public void insertBorrowStatusReportData(@RequestBody BifaBorrowStatusEntityVO reportResult){
        BifaBorrowStatusEntity data=CommonUtils.convertBean(reportResult,BifaBorrowStatusEntity.class);
        bifaDataReportService.insertBorrowStatusReportData(data);
    }

    /**
     * mongo中获取债转信息
     * @param sourceProductCode
     */
    @GetMapping("/getBifaBorrowCreditInfoFromMongDB/{sourceProductCode}")
    public BifaCreditTenderInfoEntityResponse getBifaBorrowCreditInfoFromMongDB(@PathVariable String sourceProductCode){
        BifaCreditTenderInfoEntityResponse response = new BifaCreditTenderInfoEntityResponse();
        BifaCreditTenderInfoEntity entityMd=bifaDataReportService.getBifaBorrowCreditInfoFromMongDB(sourceProductCode);
        if (entityMd!=null){
            response.setResult(CommonUtils.convertBean(entityMd,BifaCreditTenderInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 债转数据保存本地mongo
     * @param reportResult
     */
    @PostMapping("/insertCreditTenderInfoReportData")
    public void insertCreditTenderInfoReportData(@RequestBody BifaCreditTenderInfoEntityVO reportResult){
        BifaCreditTenderInfoEntity data = CommonUtils.convertBean(reportResult,BifaCreditTenderInfoEntity.class);
        bifaDataReportService.insertCreditTenderInfoReportData(data);
    }

    /**
     * 获取上报失败的散标数据
     */
    @GetMapping("/getReportFailBorrowInfoFromMongo")
    public BifaBorrowInfoEntityResponse getReportFailBorrowInfoFromMongo(){
        BifaBorrowInfoEntityResponse response = new BifaBorrowInfoEntityResponse();
        List<BifaBorrowInfoEntity> recordList = bifaDataReportService.getReportFailBorrowInfoFromMongo();
        if(CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList, BifaBorrowInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 更新mongo中的散标
     * @param reportResult
     */
    @PostMapping("/updateBorrowInfo")
    public void updateBorrowInfo(@RequestBody BifaBorrowInfoEntityVO reportResult){
        BifaBorrowInfoEntity data = CommonUtils.convertBean(reportResult,BifaBorrowInfoEntity.class);
        bifaDataReportService.updateBorrowInfo(data);
    }

    /**
     * 获取上报失败的智投
     * @return
     */
    @GetMapping("/getReportFailHjhPlanInfoFromMongo")
    public BifaHjhPlanInfoEntityResponse getReportFailHjhPlanInfoFromMongo(){
        BifaHjhPlanInfoEntityResponse response = new BifaHjhPlanInfoEntityResponse();
        List<BifaHjhPlanInfoEntity> recordList = bifaDataReportService.getReportFailHjhPlanInfoFromMongo();
        if(CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList, BifaHjhPlanInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 更新智投
     */
    @PostMapping("/updateHjhPlanInfo")
    public void updateHjhPlanInfo(@RequestBody BifaHjhPlanInfoEntityVO result){
        BifaHjhPlanInfoEntity data = CommonUtils.convertBean(result,BifaHjhPlanInfoEntity.class);
        bifaDataReportService.updateHjhPlanInfo(data);
    }

    /**
     * 获取上报失败的债转
     */
    @GetMapping("/getReportFailCreditInfoFromMongo")
    public BifaCreditTenderInfoEntityResponse getReportFailCreditInfoFromMongo(){
        BifaCreditTenderInfoEntityResponse response = new BifaCreditTenderInfoEntityResponse();
        List<BifaCreditTenderInfoEntity> recordList = bifaDataReportService.getReportFailCreditInfoFromMongo();
        if(CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList, BifaCreditTenderInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;

    }
    /**
     * 更新债转
     */
    @PostMapping("/updateCreditInfo")
    public void updateCreditInfo(@RequestBody BifaCreditTenderInfoEntityVO result){
        BifaCreditTenderInfoEntity data = CommonUtils.convertBean(result,BifaCreditTenderInfoEntity.class);
        bifaDataReportService.updateCreditInfo(data);
    }

    /**
     * 获取上报失败的产品状态
     * @return
     */
    @GetMapping("/getReportFailBorrowStatusFromMongo")
    public BifaBorrowStatusEntityResponse getReportFailBorrowStatusFromMongo(){
        BifaBorrowStatusEntityResponse response = new BifaBorrowStatusEntityResponse();
        List<BifaBorrowStatusEntity> recordList = bifaDataReportService.getReportFailBorrowStatusFromMongo();
        if(CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList, BifaBorrowStatusEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 获取上报成功的散标
     */
    @GetMapping("/getReportSuccessBorrowInfoFromMongo/{borrowNid}")
    public BifaBorrowInfoEntityResponse getReportSuccessBorrowInfoFromMongo(@PathVariable String borrowNid){
        BifaBorrowInfoEntityResponse response = new BifaBorrowInfoEntityResponse();
        BifaBorrowInfoEntity record = bifaDataReportService.getReportSuccessBorrowInfoFromMongo(borrowNid);
        if (record!=null){
            response.setResult(CommonUtils.convertBean(record, BifaBorrowInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 获取上报失败的散标
     */
    @GetMapping("/getReportFailBorrowInfoFromMongo/{borrowNid}")
    public BifaBorrowInfoEntityResponse getReportFailBorrowInfoFromMongo(@PathVariable String borrowNid){
        BifaBorrowInfoEntityResponse response = new BifaBorrowInfoEntityResponse();
        BifaBorrowInfoEntity record = bifaDataReportService.getReportFailBorrowInfoFromMongo(borrowNid);
        if (record!=null){
            response.setResult(CommonUtils.convertBean(record, BifaBorrowInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 获取上报成功的智投
     */
    @GetMapping("/getReportSuccessHjhPlanInfoFromMongo/{planNid}")
    public BifaHjhPlanInfoEntityResponse getReportSuccessHjhPlanInfoFromMongo(@PathVariable String planNid){
        BifaHjhPlanInfoEntityResponse response = new BifaHjhPlanInfoEntityResponse();
        BifaHjhPlanInfoEntity record = bifaDataReportService.getReportSuccessHjhPlanInfoFromMongo(planNid);
        if (record!=null){
            response.setResult(CommonUtils.convertBean(record, BifaHjhPlanInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 获取上报失败的智投
     * @param planNid
     * @return
     */
    @GetMapping("/getReportFailHjhPlanInfoFromMongo/{planNid}")
    public BifaHjhPlanInfoEntityResponse getReportFailHjhPlanInfoFromMongo(@PathVariable String planNid){
        BifaHjhPlanInfoEntityResponse response = new BifaHjhPlanInfoEntityResponse();
        BifaHjhPlanInfoEntity record = bifaDataReportService.getReportFailHjhPlanInfoFromMongo(planNid);
        if (record!=null){
            response.setResult(CommonUtils.convertBean(record, BifaHjhPlanInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 更新产品状态
     */
    @PostMapping("/updateBorrowStatus")
    public void updateBorrowStatus(@RequestBody BifaBorrowStatusEntityVO reportResult){
        BifaBorrowStatusEntity data = CommonUtils.convertBean(reportResult,BifaBorrowStatusEntity.class);
        bifaDataReportService.updateBorrowStatus(data);
    }

    /**
     *更新sha256表中用户对应状态
     */
    @GetMapping("/updateUserIndexReportStatus/{userId}/{dataType}")
    public void updateUserIndexReportStatus(@PathVariable Integer userId,@PathVariable String dataType){
        bifaDataReportService.updateUserIndexReportStatus(userId,dataType);
    }

    /**
     * 获取用户的sha256
     * @param isOpenUp
     * @param isLenderZeroUp
     * @param isLenderOneUp
     * @return
     */
    @GetMapping("/getUserInfoSHA256/{isOpenUp}/{isLenderZeroUp}/{isLenderOneUp}")
    public UserInfoSHA256EntityResponse getUserInfoSHA256(@PathVariable String isOpenUp,@PathVariable String isLenderZeroUp,@PathVariable String isLenderOneUp){
        UserInfoSHA256EntityResponse response = new UserInfoSHA256EntityResponse();
        List<BifaUserInfoSHA256Entity> recordList=bifaDataReportService.getUserInfoSHA256(isOpenUp,isLenderZeroUp,isLenderOneUp);
        if (CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList,BifaUserInfoSHA256EntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    /**
     * 获取借款人信息
     * @param borrowNid
     * @return
     */
    @GetMapping("/getBorrowUserInfoFromMongo/{borrowNid}")
    public BifaIndexUserInfoEntityResponse getBorrowUserInfoFromMongo(@PathVariable String borrowNid){
        BifaIndexUserInfoEntityResponse response = new BifaIndexUserInfoEntityResponse();
        BifaIndexUserInfoEntity record = bifaDataReportService.getBorrowUserInfoFromMongo(borrowNid);
        if (record!=null){
            response.setResult(CommonUtils.convertBean(record,BifaIndexUserInfoEntityVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 借贷用户保存mongo
     * @param data
     */
    @PostMapping("/insertBorrowUserInfoReportData")
    public void insertBorrowUserInfoReportData(@RequestBody BifaIndexUserInfoEntityVO data){
        BifaIndexUserInfoEntity result = CommonUtils.convertBean(data,BifaIndexUserInfoEntity.class);
        bifaDataReportService.insertBorrowUserInfoReportData(result);
    }



    @GetMapping("/getTotalInvestAndInterestVO")
    public TotalInvestAndInterestResponse getTotalInvestAndInterestVO(){
        TotalInvestAndInterestResponse response = new TotalInvestAndInterestResponse();
        TotalInvestAndInterestEntity record = bifaDataReportService.getTotalInvestAndInterestVO();
        if (record != null){
            response.setResult(CommonUtils.convertBean(record,TotalInvestAndInterestVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 运营数据保存本地mongo
     */
    @PostMapping("/insertOperationReportData")
    public void insertOperationReportData(@RequestBody BifaOperationDataEntityVO bifaOperationDataEntity) {
        BifaOperationDataEntity reportData = CommonUtils.convertBean(bifaOperationDataEntity,BifaOperationDataEntity.class);
        bifaDataReportService.insertOperationReportData(reportData);
    }

}
