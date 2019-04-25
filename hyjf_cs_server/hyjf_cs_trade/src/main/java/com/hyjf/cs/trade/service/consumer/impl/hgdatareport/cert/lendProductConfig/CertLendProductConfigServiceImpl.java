package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditCustomizeVO;
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

import java.util.HashMap;
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
     *
     * @param orderId（加入订单号或承接订单号）
     * @param flag（1:承接智投，2：加入智投）
     * @return
     */
    @Override
    public JSONArray ProductConfigInfo(String orderId, String flag) {
        JSONArray json = new JSONArray();
        try {
            //产品信息编号
            String sourceFinancingcode = "";
            //债权编号
            String finClaimID = "";
            //借款人id
            Integer userId = 0;
            // flag  1:承接智投，2：加入智投
            if (flag.equals("2")) {
                //加入智投
                HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(orderId);
                if (null == hjhAccedeVO) {
                    throw new Exception("产品配置信息推送,智投的加入记录为空！！智投加入订单号:" + orderId);
                }
                //finClaimID
                List<BorrowTenderVO> borrowTenderList = amTradeClient.getBorrowTenderByAccede(hjhAccedeVO.getAccedeOrderId());
                if (!CollectionUtils.isNotEmpty(borrowTenderList)) {
                    throw new Exception("产品配置信息推送,获取标的投资详情表的信息为空！！智投计入订单号:" + hjhAccedeVO.getAccedeOrderId());
                }
                userId = hjhAccedeVO.getUserId();
                sourceFinancingcode = hjhAccedeVO.getPlanNid();
                String idCardHash = getIdCard(userId);
                for (BorrowTenderVO borrowTenderVO : borrowTenderList) {
                    finClaimID = borrowTenderVO.getNid();
                    json = putParam(sourceFinancingcode, finClaimID, idCardHash, json,false,null);
                }
            } else if(flag.equals("1")) {
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
                String idCardHash = getIdCard(userId);
                json = putParam(sourceFinancingcode, finClaimID, idCardHash, json,false,null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }

    public JSONArray putParam(String sourceFinancingcode, String finClaimID, String userIdcardHash, JSONArray json,Boolean isOld,String date) {
        Map<String, Object> param = new HashMap<String, Object>();
        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", systemConfig.getCertSourceCode());
        //产品信息编号
        param.put("sourceFinancingCode", sourceFinancingcode);
        //债权编号
        param.put("finClaimId", finClaimID);
        //产品配置编号
        param.put("configId", finClaimID);
        //智投出借人哈希
        param.put("userIdcardHash", userIdcardHash);
        if(isOld){
            param.put("groupByDate",date);
        }
        json.add(param);
        return json;
    }

    public String getIdCard(Integer userId) {
        //获取用户信息
        String userIdcardHash = "";
        try {
            UserInfoVO userVO = amUserClient.findUserInfoById(userId);
            if (null == userVO) {
                throw new Exception("产品配置信息推送,获取出借人信息为空！！用户id为：" + userId);
            }
            logger.info(logHeader + "用户id:" + userId + " 查询的身份证号为：" + JSONArray.toJSONString(userVO.getIdcard()));
            userIdcardHash = tool.idCardHash(userVO.getIdcard());
            logger.info(logHeader + "用户id:" + userId + " 加密后的哈希值为：" + userIdcardHash);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return userIdcardHash;
    }

    /**
     * 查找产品配置信息历史数据
     * @return
     */
    @Override
    public JSONArray getHistoryDate(){
        JSONArray jsonArray = new JSONArray();
        //产品信息编号
        String sourceFinancingcode = "";
        //债权编号
        String finClaimID = "";
        try {
            //查找未还款的债权信息
            List<BorrowTenderCustomizeVO> borrowTenderCustomizeVOList = amTradeClient.getBorrowTenderInfoCustomize();
            if(!CollectionUtils.isNotEmpty(borrowTenderCustomizeVOList)){
                throw new Exception("产品配置信息历史数据推送，获取未还款的债权信息为空！！");
            }
            // 查找未还款部分被转让的债权信息（承接信息）
            List<HjhDebtCreditCustomizeVO> hjhDebtCreditCustomizeVOList = amTradeClient.getHjhDebtCreditInfoCustomize();
            if(!CollectionUtils.isNotEmpty(hjhDebtCreditCustomizeVOList)){
                throw new Exception("产品配置信息历史数据推送，获取未还款的债权信息为空！！");
            }
            for(BorrowTenderCustomizeVO borrowTenderCustomizeVO:borrowTenderCustomizeVOList){
                //产品信息编号
                sourceFinancingcode = borrowTenderCustomizeVO.getPlanNid();
                //债权编号
                finClaimID = borrowTenderCustomizeVO.getNid();
                String idCardHash = getIdCard(borrowTenderCustomizeVO.getUserId());
                jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,borrowTenderCustomizeVO.getCreateTime());
            }
            for(HjhDebtCreditCustomizeVO hjhDebtCreditCustomizeVO:hjhDebtCreditCustomizeVOList){
                //产品信息编号
                sourceFinancingcode = hjhDebtCreditCustomizeVO.getPlanNid();
                //债权编号
                finClaimID = hjhDebtCreditCustomizeVO.getAssignOrderId();
                String idCardHash = getIdCard(hjhDebtCreditCustomizeVO.getUserId());
                jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,hjhDebtCreditCustomizeVO.getCreateTime());
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonArray;
    }
}
