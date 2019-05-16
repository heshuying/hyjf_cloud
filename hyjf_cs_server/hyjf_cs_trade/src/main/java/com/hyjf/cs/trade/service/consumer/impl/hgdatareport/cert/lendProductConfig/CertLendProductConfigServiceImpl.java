package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.cert.CertClaimUpdateVO;
import com.hyjf.am.vo.trade.cert.CertClaimVO;
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
import java.util.*;
import java.util.stream.Collectors;


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
                logger.info(logHeader + "智投发生出借推送数据，标的编号：" + orderId);
                BorrowAndInfoVO borrowVO = amTradeClient.selectBorrowByNid(orderId);
                if (null == borrowVO) {
                    throw new Exception(logHeader + "标的信息为空！！borrowNid:" + orderId);
                }
                //如果标的信息不为空,而且标的状态为放款中,即标的放款成功
                if (borrowVO.getStatus() != 4) {
                    throw new Exception(logHeader + "标的未放款成功！！borrowNid:" + orderId);
                }
                List<BorrowTenderVO> borrowTenderList = amTradeClient.getBorrowTenderListByBorrowNid(orderId);
                if (null != borrowTenderList && borrowTenderList.size() > 0) {
                    for (BorrowTenderVO borrowTenderVO : borrowTenderList) {
                        if (StringUtils.isBlank(borrowTenderVO.getAccedeOrderId())) {
                            continue;
                        }
                        //加入智投信息
                        HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(borrowTenderVO.getAccedeOrderId());
                        if (null == hjhAccedeVO) {
                            throw new Exception("产品配置信息推送,智投的加入记录为空！！智投加入订单号:" + borrowTenderVO.getAccedeOrderId());
                        }
                        userId = hjhAccedeVO.getUserId();
                        sourceFinancingcode = hjhAccedeVO.getPlanNid();
                        finClaimID = borrowTenderVO.getNid();
                        String idCardHash = getIdCard(userId);
                        json = putParam(sourceFinancingcode, finClaimID, idCardHash, json, false, null);
                    }
                }
                //加入智投
                //根据投资订单号查找标的投资详情信息
                /*BorrowTenderVO borrowTenderVO = selectBorrowTenderByOrderId(orderId);
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
                finClaimID = borrowTenderVO.getNid();*/
            } else if (flag.equals("1")) {
                //计划承接
                logger.info(logHeader + "智投承接后推送数据，承接单号为：" + orderId);
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
                json = putParam(sourceFinancingcode, finClaimID, idCardHash, json, false, null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }

    public JSONArray putParam(String sourceFinancingcode, String finClaimID, String userIdcardHash, JSONArray json, Boolean isOld, String date) {
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
        if (isOld) {
            param.put("groupByDate", date);
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
            userIdcardHash = tool.idCardHash(userVO.getIdcard());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return userIdcardHash;
    }

    @Override
    public BorrowTenderVO selectBorrowTenderByOrderId(String orderId) {
        return amTradeClient.selectBorrowTenderByOrderId(orderId);
    }

    /**
     * 查找产品配置信息历史数据
     *
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
            List<CertClaimVO> listJoin = amTradeClient.selectCertBorrowByFlg("2");
            if(CollectionUtils.isNotEmpty(listJoin)){
                for(CertClaimVO certClaimVO:listJoin){
                    String nid = certClaimVO.getClaimNid();
//                    logger.info("产品配置信息历史数据推送日志 原始债权编号："+nid);
                    List<BorrowTenderVO> borrowTenderVOList = amTradeClient.getBorrowTenderListByNid(nid);
                    if(CollectionUtils.isEmpty(borrowTenderVOList)){
                        logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】债权编号："+nid+" ,暂无投资信息！！");
                        continue;
                    }
                    BorrowTenderVO borrowTenderVO = borrowTenderVOList.get(0);
                    HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(borrowTenderVO.getAccedeOrderId());
                    if (null==hjhAccedeVO){
                        logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】债权编码："+borrowTenderVO.getNid()+" ,查询智投计入明细为空！！");
                        logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】根据智投加入订单号："+borrowTenderVO.getAccedeOrderId()+" ,查询智投计入明细为空！！");
                        continue;
                    }
                    //根据原投资订单号查找转让信息
                    List<HjhDebtCreditVO> hjhDebtCreditVOList = amTradeClient.selectCreditBySellOrderId(borrowTenderVO.getNid());
                    if (CollectionUtils.isNotEmpty(hjhDebtCreditVOList)) {
                        //完全承接后就不能发起转让了
                        List<HjhDebtCreditVO> filterList = hjhDebtCreditVOList.stream().filter(t -> t.getCreditStatus() == 2).collect(Collectors.toList());
                        if(filterList.size()>=1) {
                            logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】初始债权编号：" + nid + " ,完全承接！！");
                            continue;
                        }
                    }
//                    logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】初始债权编号：" + nid  +"组装数据");
                    sourceFinancingcode = hjhAccedeVO.getPlanNid();
                    finClaimID = borrowTenderVO.getNid();
                    userId = hjhAccedeVO.getUserId();
                    String idCardHash = getIdCard(userId);
                    String strDate = fromatDate(borrowTenderVO.getCreateTime());
                    jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,strDate);
                }
            }
            // 承接债权（未还款）
            List<CertClaimVO> listCredit = amTradeClient.selectCertBorrowByFlg("1");
            if(CollectionUtils.isNotEmpty(listCredit)){
                for(CertClaimVO certClaimVO:listCredit){
                    String nid = certClaimVO.getClaimNid();
//                    logger.info("产品配置信息历史数据推送日志 承接债权编号："+nid);
                    List<HjhDebtCreditTenderVO> hjhDebtCreditTenderVOLists = amTradeClient.selectHjhCreditTenderListByAssignOrderId(nid);
                    if(CollectionUtils.isEmpty(hjhDebtCreditTenderVOLists)){
                        logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】承接债转编码："+nid+" ,查询智投债转投资信息为空！！");
                        continue;
                    }
                    HjhDebtCreditTenderVO hjhDebtCreditTenderVO = hjhDebtCreditTenderVOLists.get(0);
                    //根据原投资订单号查找转让信息
                    List<HjhDebtCreditVO> hjhDebtCreditVOList = amTradeClient.selectCreditBySellOrderId(hjhDebtCreditTenderVO.getAssignOrderId());
                    if (CollectionUtils.isNotEmpty(hjhDebtCreditVOList)) {
                        List<HjhDebtCreditVO> filterList = hjhDebtCreditVOList.stream().filter(t -> t.getCreditStatus() == 2).collect(Collectors.toList());
                        if(filterList.size()>=1) {
                            logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】承接债转编码：" + nid + " ,完全承接！！");
                            continue;
                        }
                    }
//                    logger.info("【合规数据上报_CERT 产品配置信息历史数据推送】承接债转编码：" + nid +"组装数据");
                    sourceFinancingcode = hjhDebtCreditTenderVO.getAssignPlanNid();
                    finClaimID = hjhDebtCreditTenderVO.getAssignOrderId();
                    userId = hjhDebtCreditTenderVO.getUserId();
                    String strDate = fromatDate(hjhDebtCreditTenderVO.getCreateTime());
                    String idCardHash = getIdCard(userId);
                    jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray,true,strDate);
                }
            }

        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonArray;
    }

    private String fromatDate(Date date) {
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM");
        String strDate = smp.format(date);
        return strDate;
    }

    /**
     * 未还款的标的
     *
     * @return
     */
    @Override
    public List<CertClaimVO> getBorrowNoRepay() {
        List<CertClaimVO> listJoin = amTradeClient.selectCertBorrowByFlg("2");
        return listJoin;
    }

    /**
     * 未完全转让的标的
     *
     * @return
     */
    @Override
    public List<CertClaimVO> getBorrowNoTransferred() {
        List<CertClaimVO> listCredit = amTradeClient.selectCertBorrowByFlg("1");
        return listCredit;
    }

    @Override
    public List<CertClaimVO> getCertBorrowNoConfig() {
        List<CertClaimVO> listCredit = getBorrowNoTransferred();
        List<CertClaimVO> listJoin = getBorrowNoRepay();
        if (CollectionUtils.isEmpty(listCredit)) {
            listCredit = new ArrayList<CertClaimVO>();
        }
        if (CollectionUtils.isEmpty(listJoin)) {
            listJoin = new ArrayList<CertClaimVO>();
        }
        listJoin.addAll(listCredit);
        return listJoin;
    }

    /**
     * 批量更新
     *
     * @param updateVO
     * @return
     */
    @Override
    public Integer updateCertBorrowStatusBatch(CertClaimUpdateVO updateVO) {
        return amTradeClient.updateCertBorrowStatusBatch(updateVO);
    }
}
