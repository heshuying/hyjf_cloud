/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.hgreportdata.cert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.trade.CertReportEntityResponse;
import com.hyjf.am.resquest.hgreportdata.cert.CertReportEntitRequest;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.hgreportdata.cert.CertAccountList;
import com.hyjf.cs.message.bean.hgreportdata.cert.CertReportEntity;
import com.hyjf.cs.message.service.hgreportdata.cert.CertStatisticalService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 国家应急中心相关mongo处理
 *
 * @author nxl
 * @version CertStatisticalController, v0.1 2019/1/18 17:26
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/certStatistical")
public class CertStatisticalController extends BaseController {

    @Autowired
    CertStatisticalService certStatisticalService;


    /**
     * 插入mongo，保存报送记录
     *
     * @param certReportEntityVO
     * @return
     */
    @PostMapping("/insertAndSendPost")
    public BooleanResponse insertAndSendPost(@RequestBody @Valid CertReportEntityVO certReportEntityVO) {
        if(null!=certReportEntityVO){
            CertReportEntity certReportEntity = new CertReportEntity();
            BeanUtils.copyProperties(certReportEntityVO,certReportEntity);
            certStatisticalService.insertAndSendPost(certReportEntity);
            return new BooleanResponse(true);
        }
        return new BooleanResponse(false);
    }


    /**
     * 插入mongo，保存报送历史记录
     *
     * @param certReportEntityVO
     * @return
     */
    @PostMapping("/insertOldMessage")
    public BooleanResponse insertOldMessage(@RequestBody @Valid CertReportEntityVO certReportEntityVO) {
        if(null!=certReportEntityVO){
            CertAccountList certAccountList = new CertAccountList();
            BeanUtils.copyProperties(certReportEntityVO,certAccountList);
            certStatisticalService.insertOldMessage(certAccountList);
            return new BooleanResponse(true);
        }
        return new BooleanResponse(false);
    }

    /**
     * 查询未上报的交易明细
     *
     * @return
     */
    @PostMapping("/getNotSendAccountList")
    public CertReportEntityResponse getNotSendAccountList() {
        CertReportEntityResponse response = new CertReportEntityResponse();
        List<CertAccountList> recordList = certStatisticalService.getNotSendAccountList();
        if(CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList, CertReportEntityVO.class));
        }
        return response;

    }

    /**
     * 查询未上报的交易明细
     *
     * @return
     */
    @PostMapping("/updateAccountSuccess")
    public BooleanResponse updateAccountSuccess(@RequestBody @Valid CertReportEntityVO certReportEntityVO) {
        if(null!=certReportEntityVO){
            CertAccountList certAccountList = new CertAccountList();
            BeanUtils.copyProperties(certReportEntityVO,certAccountList);
            logger.info("certAccountList:"+JSONObject.toJSONString(certAccountList));
            logger.info("certReportEntityVO:"+JSONObject.toJSONString(certReportEntityVO));
            certStatisticalService.updateAccountSuccess(certAccountList);
            return new BooleanResponse(true);
        }
        return new BooleanResponse(false);
    }


    /**
     * 修改mongo，修改报送状态
     *
     * @param certReportEntityVO
     * @return
     */
    @PostMapping("/updateCertReport")
    public BooleanResponse updateAndSendPost(@RequestBody @Valid CertReportEntitRequest certReportEntityVO) {
        if(null!=certReportEntityVO){
            certStatisticalService.updateCertReport(certReportEntityVO);
            return new BooleanResponse(true);
        }
        return new BooleanResponse(false);
    }

    /**
     * 根据订单号查询
     * @param logOrdId
     * @return
     */
    @GetMapping("/getCertSendLogByLogOrdId/{logOrdId}")
    public CertReportEntityResponse getCertSendLogByLogOrdId(@PathVariable("logOrdId") String logOrdId) {
        CertReportEntityResponse response = new CertReportEntityResponse();
        CertReportEntity entity = certStatisticalService.getCertSendLogByLogOrdId(logOrdId);
        if(null!=entity){
            CertReportEntityVO certReportEntityVO = CommonUtils.convertBean(entity,CertReportEntityVO.class);
            response.setResult(certReportEntityVO);
        }
        return response;
    }
}
