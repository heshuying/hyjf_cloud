/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.cert;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.cert.CertReportLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CertErrorReportLogResponse;
import com.hyjf.am.resquest.admin.CertErrorReportLogRequestBean;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
@Api(value = "数据中心-应急中心错误日志", tags = "数据中心-应急中心错误日志")
@RestController
@RequestMapping("/hyjf-admin/repair/certsendrepair")
public class CertSendRepairController extends BaseController{

    @Autowired
    private CertReportLogService certReportLogService;
    Logger _log = LoggerFactory.getLogger(CertSendRepairController.class);

    private static final String PERMISSIONS = "certerrorlog";
    @Autowired
    private CommonProducer commonProducer;


    @ApiOperation(value = "应急中心错误日志列表显示", notes = "应急中心错误日志列表显示")
    @PostMapping("/selectCertRepairLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CertErrLogVO>> selectCertErrorLogList(@RequestBody CertErrorReportLogRequestBean requestBean){
        CertErrorReportLogResponse response = certReportLogService.selectCertErrorReportLogList(requestBean);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<CertErrLogVO> certLogVOS = new ArrayList<CertErrLogVO>();
        if(null!=response.getResultList()&&response.getResultList().size()>0){
            certLogVOS = CommonUtils.convertBeanList(response.getResultList(),CertErrLogVO.class);
        }
        return new AdminResult<>(ListResult.build(certLogVOS, response.getRecordTotal())) ;
    }

    /**
     * 重新跑批
     * @return
     */
    @ApiOperation(value = "重新跑批", notes = "重新跑批")
    @PostMapping("/updateCount")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateCount(@RequestBody CertSendMqReqBean reqBean) {
        try{
            Integer id = reqBean.getId();
            logger.info("应急中心重新跑批 请求参数：{}",id);
            certReportLogService.updateErrorCount(id);
        }catch (Exception e){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 发送MQ
     * @return
     */
    @ApiOperation(value = "发送MQ", notes = "发送MQ")
    @PostMapping("/doSendMq")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult doSendMQ(HttpServletRequest request, @RequestBody CertSendMqReqBean reqBean) {
        String dataType = reqBean.getDataType();
        String mqValue = reqBean.getMqValue();
        try {
            _log.info("应急中心掉单处理，请求人【"+getUser(request).getId()+"】，请求类型【"+dataType+"】，请求参数【"+mqValue+"】");
            if("1".equals(dataType)){
                // 用户数据同步
               commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_USER_INFO, UUID.randomUUID().toString(), mqValue));
            }
            if("2".equals(dataType)){
                // 散标数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_SCATTER_INVEST ,UUID.randomUUID().toString(), mqValue));
            }
            if("6".equals(dataType)){
                // 散标状态数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_BORROW_STATUS ,UUID.randomUUID().toString(), mqValue));
            }
            if("81".equals(dataType)){
                // 还款计划数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_BORROW_REPAYMENTPLAN ,UUID.randomUUID().toString(), mqValue));
            }
            if("82".equals(dataType)){
                // 债权信息数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC ,MQConstant.CERT_REPAIR_TENDER_INFO ,UUID.randomUUID().toString(), mqValue));
            }
            if("83".equals(dataType)){
                // 转让项目数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_TRANSFER_PROJECT, UUID.randomUUID().toString(), mqValue));
            }
            if("84".equals(dataType)){
                // 转让状态数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_TRANSFER_STATUS, UUID.randomUUID().toString(), mqValue));
            }
            if("85".equals(dataType)){
                // 承接信息数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC ,MQConstant.CERT_REPAIR_CREDITTENDERINFO ,UUID.randomUUID().toString(), mqValue));
            }
            if("4".equals(dataType)){
                // 交易流水数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_TRANSACT_TAG, UUID.randomUUID().toString(), mqValue));
                _log.info(" 交易流水数据同步:"+mqValue);
            }
            if("86".equals(dataType)){
                // 产品信息数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_LENDPRODUCT, UUID.randomUUID().toString(), mqValue));
            }
            if("87".equals(dataType)){
                // 产品配置数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_REPAIR_LENDPRODUCTCONFIG, UUID.randomUUID().toString(), mqValue));
            }
            if("88".equals(dataType)){
                // 投资明细数据同步
                commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_INVEST_DETAIL_TAG, UUID.randomUUID().toString(), mqValue));
            }
        }catch (Exception e){
            _log.info("应急中心发送MQ出错，请求人【"+getUser(request).getId()+"】，请求类型【"+dataType+"】，请求参数【"+mqValue+"】",e);
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }
}
