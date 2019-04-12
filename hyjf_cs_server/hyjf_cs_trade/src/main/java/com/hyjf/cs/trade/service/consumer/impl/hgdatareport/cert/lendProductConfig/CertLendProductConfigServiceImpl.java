package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig.CertLendProductConfigService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nxl
 */

@Service
public class CertLendProductConfigServiceImpl extends BaseHgCertReportServiceImpl implements CertLendProductConfigService {

    Logger logger = LoggerFactory.getLogger(CertLendProductConfigServiceImpl.class);
    private String thisMessName = "产品配置信息推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 组装产品配置上报信息
     * @param orderId（加入订单号或承接订单号）
     * @param flag（1代表加入智投，2代表承接智投）
     * @return
     */
    @Override
    public JSONArray ProductConfigInfo(String orderId, String flag) {
        JSONArray json = new JSONArray();
        try {
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            //产品信息编号
            String sourceFinancingcode = "";
            //债权编号
            String finClaimID = "";
            //借款人id
            Integer userId = 0;
            if (flag.equals("1")) {
                //加入计划
                HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(orderId);
                logger.info(logHeader + "智投加入订单号:" + orderId + "智投的加入记录为：" + JSONArray.toJSONString(hjhAccedeVO));
                if (null == hjhAccedeVO) {
                    throw new Exception("产品配置信息推送,智投的加入记录为空！！智投加入订单号:" + orderId);
                }
                sourceFinancingcode = hjhAccedeVO.getPlanNid();
                //finClaimID
                List<BorrowTenderVO> borrowTenderList = amTradeClient.getBorrowTenderByAccede(hjhAccedeVO.getAccedeOrderId());
                logger.info(logHeader + "智投加入订单号:" + hjhAccedeVO.getAccedeOrderId() + "智投的加入记录为：" + JSONArray.toJSONString(hjhAccedeVO));

                if (!CollectionUtils.isNotEmpty(borrowTenderList)) {
                    throw new Exception("产品配置信息推送,获取标的投资详情表的信息为空！！智投计入订单号:" + hjhAccedeVO.getAccedeOrderId());
                }
                BorrowTenderVO borrowTenderVO = borrowTenderList.get(0);
                ////债权编号
                finClaimID = borrowTenderVO.getNid();
                userId = hjhAccedeVO.getUserId();
            } else {
                //计划承接
                List<HjhDebtCreditTenderVO> hjhDebtCreditTenderList = amTradeClient.selectHjhCreditTenderListByAssignOrderId(orderId);
                if (!CollectionUtils.isNotEmpty(hjhDebtCreditTenderList)) {
                    throw new Exception("产品配置信息推送,获取计划承接信息为空！！承接单号为:" + orderId);
                }
                HjhDebtCreditTenderVO hjhDebtCreditTenderVO = hjhDebtCreditTenderList.get(0);
                //智投编码
                sourceFinancingcode = hjhDebtCreditTenderVO.getAssignPlanNid();
                //债权来源承接转让时，填写承接转让编号
                finClaimID = hjhDebtCreditTenderVO.getAssignOrderId();
                //承接用户id
                userId = hjhDebtCreditTenderVO.getUserId();
            }
            //获取用户信息
            UserInfoVO userVO = amUserClient.findUserInfoById(userId);
            logger.info(logHeader + "根据用户id:" + userId + " 查询的信息为：" + JSONArray.toJSONString(userVO));
            if (null == userVO) {
                throw new Exception("产品配置信息推送,获取出借人信息为空！！用户id为:" + userId);
            }
            String userIdcardHash = tool.idCardHash(userVO.getIdcard());
            //接口版本号
            param.put("version", CertCallConstant.CERT_CALL_VERSION);
            //平台编号
            param.put("sourceCode", systemConfig.getCertSourceCode());
            //产品信息编号
            param.put("sourceFinancingcode", sourceFinancingcode);
            //债权编号
            param.put("finClaimID", finClaimID);
            //产品配置编号
            param.put("configID", finClaimID);
            //智投出借人哈希
            param.put("userIdcardHash", userIdcardHash);
            json.add(param);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }
}
