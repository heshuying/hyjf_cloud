package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.getyibumessage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.CertLogRequestBean;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertSendUtils;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.getyibumessage.CertGetYiBuMessageService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 合规数据上报 CERT 查询批次数据入库消息
 * @Author nxl
 * @Date 2018/12/25 17:10
 */
@Service
public class CertGetYiBuMessageServiceImpl extends BaseHgCertReportServiceImpl implements CertGetYiBuMessageService {
    Logger logger = LoggerFactory.getLogger(BaseHgCertReportServiceImpl.class);

    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    SystemConfig systemConfig;

    @Override
    public List<CertLogVO> getCertLog(){
        List<CertLogVO> certLogVOList = amConfigClient.selectCertReportLogList();
        return certLogVOList;
    }
    @Override
    public CertReportEntityVO updateYiBuMessage(String batchNum, int certLogId, String infType){
        CertReportEntityVO entity = new CertReportEntityVO();
        entity = getCertReportEntity(batchNum,entity,infType);
        //拼接参数
        StringBuilder strUrl=new StringBuilder(entity.getUrl());
        strUrl.append("?apiKey=");
        strUrl.append(entity.getApiKey());
        strUrl.append("&nonce=");
        strUrl.append(entity.getNonce());
        strUrl.append("&sourceCode=");
        strUrl.append(entity.getSourceCode());
        strUrl.append("&timestamp=");
        strUrl.append(entity.getTimestamp()+"");
        strUrl.append("&version=");
        strUrl.append(entity.getVersion());
        strUrl.append("&infType=");
        strUrl.append(entity.getInfType());
        strUrl.append("&dataType=");
        strUrl.append(entity.getDataType());
        strUrl.append("&batchNum=");
        strUrl.append(entity.getBatchNum());
        //get 请求方式
        String rtnMsg = CertSendUtils.getReq(systemConfig.getCertCrtpath(),strUrl.toString());
        logger.info("[查询批次数据入库消息] 的返回结果为:"+rtnMsg);
        CertLogVO certLog =getCertLogById(certLogId);
        CertLogRequestBean requestBean = new CertLogRequestBean();
        if(null!=certLog){
            BeanUtils.copyProperties(certLog,requestBean);
            entity = setResult(entity,rtnMsg,requestBean);
        }
        return entity;
    }

    /**
     * 组装参数
     * @param batchNum
     * @param entity
     * @param infType
     * @return
     */
    private CertReportEntityVO getCertReportEntity(String batchNum,CertReportEntityVO entity,String infType){
        // nonce 随机数
        String nonce = Integer.toHexString(new Random().nextInt());
        entity.setNonce(nonce);
        // sourceCode  平台编号
        //平台编号
        entity.setSourceCode(systemConfig.getCertSourceCode());
        //版本号
        String version = CertCallConstant.CERT_CALL_VERSION;
        entity.setVersion(version);
        // timestamp 时间戳(1490601378778)
        long timestamp = System.currentTimeMillis();
        entity.setTimestamp(String.valueOf(timestamp));
        // apiKey
        String apiKey = CertCallUtil.getApiKey(systemConfig.getCertApiKey(), systemConfig.getCertSourceCode(), entity.getVersion(), timestamp, nonce);
        entity.setApiKey(apiKey);
        // dataType 数据类型（0测试，1正式）
        // 判断是否测试环境
        if (systemConfig.getCertIsTest() == null || "true".equals(systemConfig.getCertIsTest())) {
            // 测试数据
            entity.setDataType("0");
        }else{
            // 正式数据
            entity.setDataType("1");
        }
        // batchNum 批次号
        entity.setBatchNum(batchNum);
        // 请求地址
        // "https://api.ifcert.org.cn/balanceService/v15/yiBuMessage"
        String url =systemConfig.getCertWebYibu();
        logger.info("[查询批次数据入库消息] 请求地址是:"+url);
        entity.setUrl(url);
        entity.setInfType(infType);
        return entity;
    }

    private CertLogVO getCertLogById(int certLogId){
        CertLogVO certLogVO = amConfigClient.selectCertReportLogById(certLogId);
        return certLogVO;
    }

    protected CertReportEntityVO setResult(CertReportEntityVO bean, String rtnMsg,CertLogRequestBean certLog) {

        // 上报结果  0初始，1成功，9失败 99 无响应
        // 返回结果  例  {"resultMsg": {"code": "0000","message": "[调试]数据已成功上报，正在等待处理，请使用对账接口查看数据状态"}
        JSONObject resp = CertCallUtil.parseResultQuery(rtnMsg);
        if(null == rtnMsg||rtnMsg.equals("")){
            //请求失败  无响应
            certLog.setQueryResult(CertCallUtil.convertQueryResult("NO"));
            certLog.setQueryMsg("请求失败,无响应");
        }else{
            String errorMsg = resp.getString("message");
            String code = resp.getString("code");
            logger.info("[查询批次数据入库消息] code："+code);
            logger.info("[查询批次数据入库消息] message："+errorMsg);
            if(CertCallConstant.CERT_RESPONSE_SUCCESS.equals(code)){
                //代表入库成功
                JSONArray arrReturn =(JSONArray)resp.get("result");
                JSONObject resultRet= (JSONObject)arrReturn.get(0);
                String msg  = resultRet.getString("errorMsg");
//                certLog.setQueryResult(CertCallConstant.CERT_QUERY_RETURN_STATUS_SUCCESS);
                certLog.setQueryResult(CertCallUtil.convertQueryResult(msg));
                certLog.setQueryMsg(errorMsg);
            }else{
                // 请求失败
                Integer intCode= StringUtils.isNotBlank(code)?Integer.parseInt(code):99;
                certLog.setQueryResult(intCode);
                certLog.setQueryMsg(errorMsg);
            }

        }
        // 修改数据库
        amConfigClient.updateCertLog(certLog);
        return bean;
    }

    /**
     * 应急中心 查询待异步查询的日志数量
     * add by nxl
     * @return
     */
    @Override
    public int selectCertLogLength(){
        return amConfigClient.selectCertLogLength();
    }
}

