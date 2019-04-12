package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProduct;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProduct.CertLendProductService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author nxl
 */

@Service
public class CertLendProductServiceImpl extends BaseHgCertReportServiceImpl implements CertLendProductService {

    Logger logger = LoggerFactory.getLogger(CertLendProductServiceImpl.class);
    private String thisMessName = "产品信息推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 日期转换
     *
     * @param repayTime
     * @return
     */
    public String dateFormatTransformationDate(Date repayTime, String flg) {
        if (flg.equals("H")) {
            //代表获取有时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(repayTime);
            return dateStr;
        }
        if (flg.equals("Y")) {
            //代表只有年与日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(repayTime);
            return dateStr;
        }
        return null;
    }
    /**
     * 查询计划产品信息,组装上报信息
     * @param planNid
     * @return
     */
    @Override
    public JSONArray getPlanProdouct(String planNid){
        JSONArray json = new JSONArray();
        try {
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            HjhPlanVO hjhPlanVO = amTradeClient.getHjhPlan(planNid);
            logger.info(logHeader+"根据计划编号:" + planNid+"获取的计划产品信息为："+JSONArray.toJSONString(hjhPlanVO));
            if (null == hjhPlanVO) {
                throw new Exception("产品信息推送,获取计划信息为空！！计划编码:" + planNid);
            }
            if(null!=hjhPlanVO){
                //接口版本号
                param.put("version", CertCallConstant.CERT_CALL_VERSION);
                //平台编号
                param.put("sourceCode", systemConfig.getCertSourceCode());
                //产品信息编号
                param.put("sourceFinancingCode",hjhPlanVO.getPlanNid());
                //发布时间
                String financingStartTime =dateFormatTransformationDate(hjhPlanVO.getCreateTime(),"H");
                param.put("financingStartTime",financingStartTime);
                //产品名称
                param.put("productName",hjhPlanVO.getPlanName());
                //预期年化利率（参考回报率）
                String rate = CertCallUtil.convertLoanRate(hjhPlanVO.getExpectApr(),0,"");
                param.put("rate",rate);
                //最小预期年化利率
                param.put("minRate","-1");
                //最大预期年化利率
                param.put("maxRate","-1");
                //产品期限（服务期限）(天)
                int termDays = hjhPlanVO.getLockPeriod();
                if (hjhPlanVO.getIsMonth() == 1) {
                    //月标（转换为天数）
                    termDays = hjhPlanVO.getLockPeriod() * 30;
                }
                param.put("term", termDays);
                json.add(param);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }
}
