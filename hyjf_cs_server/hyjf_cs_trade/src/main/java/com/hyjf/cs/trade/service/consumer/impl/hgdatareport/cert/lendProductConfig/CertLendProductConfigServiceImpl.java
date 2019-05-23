package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.CreditTenderVO;
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
    public JSONArray productConfigInfo(String orderId, String isTender, String flag) {
        JSONArray json = new JSONArray();
        try {
            //产品信息编号
            String sourceFinancingcode = "";
            //债权编号
            String finClaimID = "";
            //借款人id
            Integer userId = 0;
            // isTender  1:承接债权，2：原始债权
            if (isTender.equals("2")) {
                //原始债权
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
                        if (StringUtils.isNotBlank(borrowTenderVO.getAccedeOrderId())) {
                            //加入智投信息
                            HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(borrowTenderVO.getAccedeOrderId());
                            if (null == hjhAccedeVO) {
                                throw new Exception("产品配置信息推送,智投的加入记录为空！！智投加入订单号:" + borrowTenderVO.getAccedeOrderId());
                            }
                            userId = hjhAccedeVO.getUserId();
                            sourceFinancingcode = hjhAccedeVO.getPlanNid();
                            finClaimID = borrowTenderVO.getNid();

                        } else {
                            //散标债权信息
                            userId = borrowTenderVO.getUserId();
                            sourceFinancingcode = borrowTenderVO.getBorrowNid();
                            finClaimID = borrowTenderVO.getNid();
                        }
                        String idCardHash = getIdCard(userId);
                        json = putParam(sourceFinancingcode, finClaimID, idCardHash, json, false, null);
                    }
                }
            } else if (isTender.equals("1")) {
                //承接债权
                if (flag.equals("1")) {
                    //散标承接
                    logger.info(logHeader + "散标承接后推送数据，承接单号为：" + orderId);
                    List<CreditTenderVO> creditTenderList = amTradeClient.selectCreditTender(orderId);
                    if (!CollectionUtils.isNotEmpty(creditTenderList)) {
                        throw new Exception("产品配置信息推送,获取散标承接信息为空！！承接单号为:" + orderId);
                    }
                    CreditTenderVO creditTenderVO = creditTenderList.get(0);
                    //产品信息编号 报送散标编号
                    sourceFinancingcode = creditTenderVO.getBidNid();
                    //债权来源承接转让时，填写承接转让编号
                    finClaimID = creditTenderVO.getAssignNid();
                    //承接用户id
                    userId = creditTenderVO.getUserId();
                } else if (flag.equals("2")) {
                    //计划承接
                    logger.info(logHeader + "智投承接后推送数据，承接单号为：" + orderId);
                    List<HjhDebtCreditTenderVO> hjhDebtCreditTenderList = amTradeClient.selectHjhCreditTenderListByAssignOrderId(orderId);
                    if (!CollectionUtils.isNotEmpty(hjhDebtCreditTenderList)) {
                        throw new Exception("产品配置信息推送,获取计划承接信息为空！！承接单号为:" + orderId);
                    }
                    HjhDebtCreditTenderVO hjhDebtCreditTenderVO = hjhDebtCreditTenderList.get(0);
                    //产品信息编号 报送智投编码
                    sourceFinancingcode = hjhDebtCreditTenderVO.getAssignPlanNid();
                    //债权来源承接转让时，填写承接转让编号
                    finClaimID = hjhDebtCreditTenderVO.getAssignOrderId();
                    //承接用户id
                    userId = hjhDebtCreditTenderVO.getUserId();
                }
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
    public JSONArray getHistoryDate() {
        JSONArray jsonArray = new JSONArray();
        try {
            List<CertClaimVO> listCredit = getCertBorrowNoConfig();
            if (CollectionUtils.isNotEmpty(listCredit)) {
                for (CertClaimVO certClaimVO : listCredit) {
                    String nid = certClaimVO.getClaimNid();
                    if (certClaimVO.getCreditFlg() == 1) {
                        //承接债权
                        jsonArray = creditInfo(certClaimVO, nid, jsonArray);
                        if (null == jsonArray) {
                            continue;
                        }
                    }
                    if (certClaimVO.getCreditFlg() == 2) {
                        //原始债权
                        jsonArray = tenderInfo(certClaimVO, nid, jsonArray);
                        if (null == jsonArray) {
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonArray;
    }

    /**
     * 承接债权
     *
     * @param certClaimVO
     * @param nid
     * @return
     */
    public JSONArray creditInfo(CertClaimVO certClaimVO, String nid, JSONArray jsonArray) {
        //产品信息编号
        String sourceFinancingcode = "";
        //债权编号
        String finClaimID = "";
        //借款人id
        Integer userId = 0;
        if (certClaimVO.getIsPlan() == 0) {
            //散标承接债权
            CreditTenderVO creditTenderVO = amTradeClient.getCreditTenderByAssignNid(certClaimVO.getClaimNid());
            if (null == creditTenderVO) {
                logger.info("【产品配置信息历史数据推送】散标承接债转编码：" + nid + " ,查询承接信息为空！！");
                return null;
            }
            sourceFinancingcode = creditTenderVO.getBidNid();
            finClaimID = creditTenderVO.getAssignNid();
            userId = creditTenderVO.getUserId();
            String strDate = fromatDate(creditTenderVO.getCreateTime());
            String idCardHash = getIdCard(userId);
            jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray, true, strDate);
        }
        if (certClaimVO.getIsPlan() == 1) {
            //智投承接债权
            List<HjhDebtCreditTenderVO> hjhDebtCreditTenderVOLists = amTradeClient.selectHjhCreditTenderListByAssignOrderId(nid);
            if (CollectionUtils.isEmpty(hjhDebtCreditTenderVOLists)) {
                logger.info("【产品配置信息历史数据推送】承接债转编码：" + nid + " ,查询智投债转投资信息为空！！");
                return null;
            }
            HjhDebtCreditTenderVO hjhDebtCreditTenderVO = hjhDebtCreditTenderVOLists.get(0);
            //根据原投资订单号查找转让信息
            List<HjhDebtCreditVO> hjhDebtCreditVOList = amTradeClient.selectCreditBySellOrderId(hjhDebtCreditTenderVO.getAssignOrderId());
            if (CollectionUtils.isNotEmpty(hjhDebtCreditVOList)) {
                List<HjhDebtCreditVO> filterList = hjhDebtCreditVOList.stream().filter(t -> t.getCreditStatus() == 2).collect(Collectors.toList());
                if (filterList.size() >= 1) {
                    //完全承接就不报送
                    logger.info("【产品配置信息历史数据推送】承接债转编码：" + nid + " ,完全承接！！");
                    return null;
                }
            }
            sourceFinancingcode = hjhDebtCreditTenderVO.getAssignPlanNid();
            finClaimID = hjhDebtCreditTenderVO.getAssignOrderId();
            userId = hjhDebtCreditTenderVO.getUserId();
            String strDate = fromatDate(hjhDebtCreditTenderVO.getCreateTime());
            String idCardHash = getIdCard(userId);
            jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray, true, strDate);
        }
        return jsonArray;
    }

    /**
     * 原始债权信息
     *
     * @param certClaimVO
     * @param nid
     * @param jsonArray
     * @return
     */
    public JSONArray tenderInfo(CertClaimVO certClaimVO, String nid, JSONArray jsonArray) {
        //产品信息编号
        String sourceFinancingcode = "";
        //债权编号
        String finClaimID = "";
        //借款人id
        Integer userId = 0;
        //初始债券
        List<BorrowTenderVO> borrowTenderVOList = amTradeClient.getBorrowTenderListByNid(nid);
        if (CollectionUtils.isEmpty(borrowTenderVOList)) {
            logger.info("【产品配置信息历史数据推送】债权编号：" + nid + " ,暂无投资信息！！");
            return null;
        }
        BorrowTenderVO borrowTenderVO = borrowTenderVOList.get(0);
        if (certClaimVO.getIsPlan() == 0) {
            //散标原始债权
            sourceFinancingcode = borrowTenderVO.getBorrowNid();
            finClaimID = borrowTenderVO.getNid();
            userId = borrowTenderVO.getUserId();
            String strDate = fromatDate(borrowTenderVO.getCreateTime());
            String idCardHash = getIdCard(userId);
            jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray, true, strDate);
        }
        if (certClaimVO.getIsPlan() == 1) {
            //计划承接债权
            HjhAccedeVO hjhAccedeVO = amTradeClient.getHjhAccedeByAccedeOrderId(borrowTenderVO.getAccedeOrderId());
            if (null == hjhAccedeVO) {
                logger.info("【产品配置信息历史数据推送】债权编码：" + borrowTenderVO.getNid() + " ,查询智投计入明细为空！！");
                logger.info("【产品配置信息历史数据推送】根据智投加入订单号：" + borrowTenderVO.getAccedeOrderId() + " ,查询智投计入明细为空！！");
                return null;
            }
            //根据原投资订单号查找转让信息
            List<HjhDebtCreditVO> hjhDebtCreditVOList = amTradeClient.selectCreditBySellOrderId(borrowTenderVO.getNid());
            if (CollectionUtils.isNotEmpty(hjhDebtCreditVOList)) {
                //完全承接后就不能发起转让了
                List<HjhDebtCreditVO> filterList = hjhDebtCreditVOList.stream().filter(t -> t.getCreditStatus() == 2).collect(Collectors.toList());
                if (filterList.size() >= 1) {
                    logger.info("【产品配置信息历史数据推送】初始债权编号：" + nid + " ,完全承接！！");
                    return null;
                }
            }
            sourceFinancingcode = hjhAccedeVO.getPlanNid();
            finClaimID = borrowTenderVO.getNid();
            userId = hjhAccedeVO.getUserId();
            String idCardHash = getIdCard(userId);
            String strDate = fromatDate(borrowTenderVO.getCreateTime());
            jsonArray = putParam(sourceFinancingcode, finClaimID, idCardHash, jsonArray, true, strDate);
        }
        return jsonArray;
    }

    private String fromatDate(Date date) {
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM");
        String strDate = smp.format(date);
        return strDate;
    }

    /**
     * 未上报的债权信息
     *
     * @return
     */
    @Override
    public List<CertClaimVO> getCertBorrowNoConfig() {
        List<CertClaimVO> listCredit = amTradeClient.selectCertBorrowByFlg();
        /*List<CertClaimVO> listJoin = getBorrowNoRepay();
        if (CollectionUtils.isEmpty(listCredit)) {
            listCredit = new ArrayList<CertClaimVO>();
        }
        if (CollectionUtils.isEmpty(listJoin)) {
            listJoin = new ArrayList<CertClaimVO>();
        }
        listJoin.addAll(listCredit);*/
        return listCredit;
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
