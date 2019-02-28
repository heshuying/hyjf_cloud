package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.repair;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertSendUtils;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.exception.CertSendExceptionService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author sss
 */

@Service
public class CertSendRepairServiceImpl extends BaseHgCertReportServiceImpl implements CertSendExceptionService {

    Logger logger = LoggerFactory.getLogger(CertSendRepairServiceImpl.class);

    private String thisMessName = "上报失败异常处理";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";


    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    CsMessageClient csMessageClient;
    @Autowired
    SystemConfig systemConfig;


    /**
     * 获取待处理的异常
     *
     * @return
     */
    @Override
    public List<CertErrLogVO> getCertErrLogs() {
        return amConfigClient.getCertErrLogs();
    }

    /**
     * 重新上报  并修改数据库
     *
     * @param item
     */
    @Override
    public void insertData(CertErrLogVO item) {
        CertReportEntityVO  entity = csMessageClient.getCertSendLogByLogOrdId(item.getLogOrdId());
        logger.info(logHeader+"mongo查询记录为"+JSONObject.toJSONString(entity));
        if(entity == null ){
            logger.info(logHeader+"mongo查询无记录！订单号:{}",item.getLogOrdId());
        }
        try {
            setCommonParam(entity);
        }catch (Exception e){
            logger.error(logHeader+"设置参数错误",e);
        }
        this.insertCertReport(entity);
        // 开始上报数据
        Map<String, String> params = getBankParam(entity);
        String rtnMsg = CertSendUtils.postRequest(systemConfig.getCertCrtpath(),entity.getUrl(), params);
        logger.info(logHeader+"异常返回结果为："+rtnMsg);
        updateResult(rtnMsg,entity,item.getSendCount(),item.getLogOrdId());
    }

    /**
     * 操作结果
     * @param rtnMsg
     */
    private void updateResult(String rtnMsg,CertReportEntityVO bean,Integer count,String oldLogOrdId) {
        // 上报结果  0初始，1成功，9失败 99 无响应
        // 返回结果  例  {"resultMsg": {"code": "0000","message": "[调试]数据已成功上报，正在等待处理，请使用对账接口查看数据状态"}
        bean =  super.setResult(bean,rtnMsg);
        // 如果成功的话 删除数据
        if(CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(bean.getReportStatus())){
            // 删除数据
            amConfigClient.deleteCertErrByLogOrdId(oldLogOrdId);
        }else{
            // count+1
            JSONObject resp = CertCallUtil.parseResult(rtnMsg);
            String retCode = resp.getString("code");
            String retMess = resp.getString("message");
            CertErrLogVO log = new CertErrLogVO();
            log.setResultCode(retCode);
            log.setResultMsg(retMess);
            log.setLogOrdId(oldLogOrdId);
            log.setSendCount(++count);
            amConfigClient.updateErrorLogCount(log);
        }

    }
}
