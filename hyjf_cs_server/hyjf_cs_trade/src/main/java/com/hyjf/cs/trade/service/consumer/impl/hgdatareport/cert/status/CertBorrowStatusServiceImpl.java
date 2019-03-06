package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.status;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.status.CertBorrowStatusService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author nxl
 */

@Service
public class CertBorrowStatusServiceImpl extends BaseHgCertReportServiceImpl implements CertBorrowStatusService {

    private static final Logger logger = LoggerFactory.getLogger(CertBorrowStatusServiceImpl.class);
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;
    /**
     * 获取标的的还款信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Map<String, Object> selectBorrowByBorrowNid(String borrowNid, String statusAfter, boolean isUserInfo) {
        //标的信息
        try {
            BorrowAndInfoVO borrow =amTradeClient.selectBorrowByNid(borrowNid);
            if (null == borrow) {
                throw new Exception("散标状态推送,标的信息为空！！borrowNid:" + borrowNid);
            }
            String productStatus = "";
            String productDate = "";
            String productStatusDesc = "";
            // 如果是用户信息保送完完毕后,保送投资中
            if (isUserInfo) {
                borrow.setStatus(2);
            }
            //标的还款信息
            BorrowRepayVO borrowRepay = amTradeClient.getBorrowRepay(borrowNid);
            //标的放款信息
            //BorrowRecoverVO borrowRecover =amTradeClient.selectBorrowRecoverByNid(borrowNid);
            if (null != borrow) {
                Map<String, Object> param = new HashMap<String, Object>();
                //判断标的状态 = 9 的,再报送一次 添加判断=9
                if (StringUtils.isNotBlank(statusAfter)) {
                    //放款后（报送5还款中）
                    productStatus = "5";
                    //放款时间
                    productDate = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getRecoverLastTime().toString());
                    productStatusDesc = "放款后（报送5还款中）";
                } else if (borrow.getStatus() == 2) {
                    //投资中
                    //标的状态投资中报送筹标中（报送6筹标中）
                    productStatus = "6";
                    //发标时间
                    productDate = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getVerifyTimeInteger().toString());
                    productStatusDesc = "标的状态投资中报送筹标中（报送6筹标中）";
                } else if (borrow.getStatus() == 5) {
                    //已还款
                    //最后一笔还款完成（报送3还款结束）
                    productStatus = "3";
                    //最后一笔还款时间(还款实际时间)
                    productDate = getLastRepayTime(borrowRepay);
                    productStatusDesc = "最后一笔还款完成（报送3还款结束）";
                } else if (borrow.getStatus() == 4) {
                    //还款中
                    //放款（报送9放款）
                    productStatus = "9";
                    //放款时间
                    productDate = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getRecoverLastTime().toString());
                    productStatusDesc = "放款（报送9放款）";
                } else if (borrow.getStatus() == 3 && borrow.getBorrowFullStatus() == 1) {
                    //满标
                    //满标时候（报送1满标）
                    productStatus = "1";
                    productDate = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getBorrowFullTime().toString());
                    productStatusDesc = "满标时候（报送1满标）";
                }
                param = putParamObject(borrowNid, productStatus, productDate, productStatusDesc);
                return param;

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 组装参数
     *
     * @param borrowNid
     * @param productStatus
     * @param productDate
     * @param productStatusDesc
     * @return
     */
    private Map<String, Object> putParamObject(String borrowNid, String productStatus, String productDate, String productStatusDesc) {
        Map<String, Object> param = new HashMap<String, Object>();
        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode",systemConfig.getCertSourceCode());
        //原产品信息编号
        param.put("sourceProductCode", borrowNid);
        //原产品信息编号
        param.put("sourceFinancingCode", "-1");
        //状态编码
        param.put("productStatus", productStatus);
        //状态描述
        param.put("productStatusDesc", productStatusDesc);
        //状态更新时间
        param.put("productDate", productDate);
        return param;

    }

    /**
     * 格式实际还款计划
     *
     * @param borrowRepay
     * @return
     */
    private String getLastRepayTime(BorrowRepayVO borrowRepay) {
        if (null != borrowRepay) {
            String repayTime = GetDate.timestamptoStrYYYYMMDDHHMMSS(String.valueOf(borrowRepay.getRepayActionTime()));
            return repayTime;
        }
        return null;
    }
}
