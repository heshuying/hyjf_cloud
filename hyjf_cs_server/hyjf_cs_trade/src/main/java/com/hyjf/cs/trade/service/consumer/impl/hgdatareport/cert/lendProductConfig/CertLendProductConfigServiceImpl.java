package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.cert.CertBorrowUpdateVO;
import com.hyjf.am.vo.trade.cert.CertBorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig.CertLendProductConfigService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public JSONArray productConfigInfo(String orderId, String flag) {
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
                logger.info(logHeader+"智投发生出借推送数据，出借订单号为："+orderId);
                //加入智投
                //根据投资订单号查找标的投资详情信息
                BorrowTenderVO borrowTenderVO = selectBorrowTenderByOrderId(orderId);
                if (null == borrowTenderVO) {
                    throw new Exception("产品配置信息推送,获取标的投资详情表的信息为空！！出借订单号为:" + orderId);
                }
                //加入智投信息
                HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(borrowTenderVO.getAccedeOrderId());
                if (null == hjhAccedeVO) {
                    throw new Exception("产品配置信息推送,智投的加入记录为空！！智投加入订单号:" + borrowTenderVO.getAccedeOrderId());
                }
                userId = hjhAccedeVO.getUserId();
                sourceFinancingcode = hjhAccedeVO.getPlanNid();
                finClaimID = borrowTenderVO.getNid();
            } else if(flag.equals("1")) {
                //计划承接
                logger.info(logHeader+"智投承接后推送数据，承接单号为："+orderId);
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
            String idCardHash = getIdCard(userId);
            json = putParam(sourceFinancingcode, finClaimID, idCardHash, json,false,null);
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

    @Override
    public BorrowTenderVO selectBorrowTenderByOrderId(String orderId){
        return amTradeClient.selectBorrowTenderByOrderId(orderId);
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
        //借款人id
        Integer userId = 0;
        try {
            //未还款的标的
            List<CertBorrowVO> listJoin = amTradeClient.selectCertBorrowByFlg("2");
            if(CollectionUtils.isNotEmpty(listJoin)){
                for(CertBorrowVO certBorrowVO:listJoin){
                    String borrwoNid = certBorrowVO.getBorrowNid();
                    List<BorrowTenderVO> borrowTenderVOList = amTradeClient.getBorrowTenderListByBorrowNid(borrwoNid);
                    if(CollectionUtils.isEmpty(borrowTenderVOList)){
                        logger.info(logHeader+" 标的编号："+borrwoNid+" ,暂无投资信息！！");
                        continue;
                    }
                    for(BorrowTenderVO borrowTenderVO:borrowTenderVOList){
                        if(StringUtils.isBlank(borrowTenderVO.getAccedeOrderId())){
                            logger.info(logHeader+" 标的编号："+borrwoNid+" ,获取加入智投订单号为空！！");
                            continue;
                        }
                        HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(borrowTenderVO.getAccedeOrderId());
                        if (null!=hjhAccedeVO){
                            logger.info(logHeader+" 根据智投加入订单号："+borrowTenderVO.getAccedeOrderId()+" ,查询智投计入明细为空！！");
                            continue;
                        }
                        sourceFinancingcode = hjhAccedeVO.getPlanNid();
                        finClaimID = borrowTenderVO.getNid();
                        userId = hjhAccedeVO.getUserId();
                        String idCardHash = getIdCard(userId);
                        String strDate = fromatDate(borrowTenderVO.getCreateTime());
                        jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,strDate);
                    }
                }
            }
            // 未完全转让的标的
            List<CertBorrowVO> listCredit = amTradeClient.selectCertBorrowByFlg("1");
            if(CollectionUtils.isNotEmpty(listCredit)){
                for(CertBorrowVO certBorrowVO:listCredit){
                    List<HjhDebtCreditVO> hjhDebtCreditVOList = amTradeClient.getHjhDebtCreditListByBorrowNid(certBorrowVO.getBorrowNid());
                    if(CollectionUtils.isEmpty(hjhDebtCreditVOList)){
                        logger.info(logHeader+" 标的编号："+certBorrowVO.getBorrowNid()+" ,查询转让信息为空！！");
                        continue;
                    }
                    for(HjhDebtCreditVO hjhDebtCreditVO:hjhDebtCreditVOList){
                        sourceFinancingcode = hjhDebtCreditVO.getPlanNid();
                        HjhDebtCreditTenderRequest request = new HjhDebtCreditTenderRequest();
                        request.setCreditNid(hjhDebtCreditVO.getCreditNid());
                        List<HjhDebtCreditTenderVO> hjhDebtCreditTenderVOLists = amTradeClient.getHjhDebtCreditTenderList(request);
                        if(CollectionUtils.isEmpty(hjhDebtCreditTenderVOLists)){
                            logger.info(logHeader+" 债转标号："+hjhDebtCreditVO.getCreditNid()+" ,查询智投债转投资信息为空！！");
                            continue;
                        }
                        for(HjhDebtCreditTenderVO hjhDebtCreditTenderVO:hjhDebtCreditTenderVOLists){
                            finClaimID = hjhDebtCreditTenderVO.getAssignPlanOrderId();
                            userId = hjhDebtCreditTenderVO.getUserId();
                            String strDate = fromatDate(hjhDebtCreditTenderVO.getCreateTime());
                            String idCardHash = getIdCard(userId);
                            jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,strDate);
                        }
                    }
                }
            }

        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonArray;
    }

    private String fromatDate(Date date){
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = smp.format(date);
        return strDate;
    }

    /**
     * 未还款的标的
     * @return
     */
    @Override
    public List<CertBorrowVO> getBorrowNoRepay(){
        List<CertBorrowVO> listJoin = amTradeClient.selectCertBorrowByFlg("2");
        return listJoin;
    }

    /**
     * 未完全转让的标的
     * @return
     */
    @Override
    public List<CertBorrowVO> getBorrowNoTransferred(){
        List<CertBorrowVO> listCredit = amTradeClient.selectCertBorrowByFlg("1");
        return listCredit;
    }

    @Override
    public List<CertBorrowVO> getCertBorrowNoConfig(){
        List<CertBorrowVO> listAllCert = amTradeClient.selectCertBorrowByFlg("");
        return listAllCert;
    }


    public JSONArray getProdcutConfigHistory(List<CertBorrowVO> certBorrowVOList) {
        JSONArray jsonArray = new JSONArray();
        //产品信息编号
        String sourceFinancingcode = "";
        //债权编号
        String finClaimID = "";
        //借款人id
        Integer userId = 0;
        if(CollectionUtils.isEmpty(certBorrowVOList)){
            logger.info(logHeader+"暂无未上报的产品配置历史信息！");
            return null;
        }
        for(CertBorrowVO certBorrowVO:certBorrowVOList){
            String borrwoNid = certBorrowVO.getBorrowNid();
            if(certBorrowVO.getCreditFlg()==1){
                //未转让的标的


                        List<HjhDebtCreditVO> hjhDebtCreditVOList = amTradeClient.getHjhDebtCreditListByBorrowNid(certBorrowVO.getBorrowNid());
                        if(CollectionUtils.isEmpty(hjhDebtCreditVOList)){
                            logger.info(logHeader+" 标的编号："+certBorrowVO.getBorrowNid()+" ,查询转让信息为空！！");
                            continue;
                        }
                        for(HjhDebtCreditVO hjhDebtCreditVO:hjhDebtCreditVOList){
                            sourceFinancingcode = hjhDebtCreditVO.getPlanNid();
                            HjhDebtCreditTenderRequest request = new HjhDebtCreditTenderRequest();
                            request.setCreditNid(hjhDebtCreditVO.getCreditNid());
                            List<HjhDebtCreditTenderVO> hjhDebtCreditTenderVOLists = amTradeClient.getHjhDebtCreditTenderList(request);
                            if(CollectionUtils.isEmpty(hjhDebtCreditTenderVOLists)){
                                logger.info(logHeader+" 债转标号："+hjhDebtCreditVO.getCreditNid()+" ,查询智投债转投资信息为空！！");
                                continue;
                            }
                            for(HjhDebtCreditTenderVO hjhDebtCreditTenderVO:hjhDebtCreditTenderVOLists){
                                finClaimID = hjhDebtCreditTenderVO.getAssignPlanOrderId();
                                userId = hjhDebtCreditTenderVO.getUserId();
                                String strDate = fromatDate(hjhDebtCreditTenderVO.getCreateTime());
                                String idCardHash = getIdCard(userId);
                                jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,strDate);
                            }
                        }

            }
            if(certBorrowVO.getCreditFlg()==2){
                //未还款的标的
                List<BorrowTenderVO> borrowTenderVOList = amTradeClient.getBorrowTenderListByBorrowNid(borrwoNid);
                if(CollectionUtils.isEmpty(borrowTenderVOList)){
                    logger.info(logHeader+" 标的编号："+borrwoNid+" ,暂无投资信息！！");
                    continue;
                }
                for(BorrowTenderVO borrowTenderVO:borrowTenderVOList){
                    if(StringUtils.isBlank(borrowTenderVO.getAccedeOrderId())){
                        logger.info(logHeader+" 标的编号："+borrwoNid+" ,获取加入智投订单号为空！！");
                        continue;
                    }
                    jsonArray = productConfigInfo(borrowTenderVO.getNid(),"2");
                }
            }
        }
        return jsonArray;
    }

    /**
     * 批量更新
     * @param updateVO
     * @return
     */
    @Override
    public Integer updateCertBorrowStatusBatch(CertBorrowUpdateVO updateVO){
        return amTradeClient.updateCertBorrowStatusBatch(updateVO);
    }
}
