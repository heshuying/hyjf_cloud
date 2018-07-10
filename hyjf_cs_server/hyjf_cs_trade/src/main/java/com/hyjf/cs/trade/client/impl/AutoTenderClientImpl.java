/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.MapResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.trade.InsertBankCreditEndForCreditEndRequest;
import com.hyjf.am.resquest.trade.SaveCreditTenderLogRequest;
import com.hyjf.am.resquest.trade.UpdateBorrowForAutoTenderRequest;
import com.hyjf.am.resquest.trade.UpdateCreditForAutoTenderRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.cs.trade.client.AutoTenderClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author liubin
 * @version AutoTenderClientImpl, v0.1 2018/7/3 15:04
 */
public class AutoTenderClientImpl implements AutoTenderClient {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestTemplate restTemplate;

    public static final String urlBase = "http://AM-TRADE/am-trade/";

    /**
     * 取得自动投资用加入计划列表
     *
     * @return
     * @author liubin
     */
    @Override
    public List<HjhAccedeVO> selectPlanJoinList() {
        String url = urlBase + "autoTenderController/selectPlanJoinList";
        Response<HjhAccedeVO> response = restTemplate.getForEntity(url, Response.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author liubin
     */
    @Override
    public Map<String, Object> saveCreditTenderLog(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        String url = urlBase + "autoTenderController/saveCreditTenderLog";
        SaveCreditTenderLogRequest request = new SaveCreditTenderLogRequest(credit, hjhAccede, orderId, orderDate, yujiAmoust, isLast);
        MapResponse response = restTemplate.postForEntity(url, request, MapResponse.class).getBody();
        if (response != null) {
            return response.getResultMap();
        }
        return null;
    }

    /**
     * 取得当前债权在清算前已经发生债转的本金
     *
     * @return
     * @author liubin
     */
    @Override
    public BigDecimal getPreCreditCapital(HjhDebtCreditVO hjhDebtCreditVO) {
        String url = urlBase + "autoTenderController/getPreCreditCapital";
        BigDecimal result = restTemplate.postForEntity(url, hjhDebtCreditVO, BigDecimal.class).getBody();
        return result;
    }

    /**
     * 银行自动债转成功后，更新债转数据
     *
     * @return
     * @author liubin
     */
    @Override
    public boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean, String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap) {
        String url = "autoTenderController/updateCreditForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateCreditForAutoTenderRequest request = new UpdateCreditForAutoTenderRequest(credit, hjhAccede, hjhPlan, bankCallBeanVO, tenderUsrcustid, sellerUsrcustid, resultMap);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            logger.error("[" + hjhAccede.getAccedeOrderId() + "] 银行自动债转成功后，更新债转数据失败。");
            throw new RuntimeException("银行自动债转成功后，更新债转数据失败。");
        }
        return true;
    }

    /**
     * 银行自动投资成功后，更新投资数据
     *
     * @return
     * @author liubin
     */
    @Override
    public boolean updateBorrowForAutoTender(BorrowVO borrow, HjhAccedeVO hjhAccede, BankCallBean bean) {
        String url = urlBase + "autoTenderController/updateBorrowForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateBorrowForAutoTenderRequest request = new UpdateBorrowForAutoTenderRequest(borrow, hjhAccede, bankCallBeanVO);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            logger.error("[" + hjhAccede.getAccedeOrderId() + "] 银行自动投资成功后，更新投资数据失败。");
            throw new RuntimeException("银行自动投资成功后，更新投资数据失败。");
        }
        return true;
    }

    /**
     * 根据是否原始债权获出让人投标成功的授权号
     *
     * @return
     * @author liubin
     */
    @Override
    public String getSellerAuthCode(String sellOrderId, Integer sourceType) {
        String url = urlBase + "autoTenderController/getSellerAuthCode/" + sellOrderId + "/" + sourceType;
        Response<String> response = restTemplate.getForEntity(url, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            logger.error("根据是否原始债权获出让人投标成功的授权号失败。");
            throw new RuntimeException("根据是否原始债权获出让人投标成功的授权号失败。");
        }
        return response.getResult();
    }

    /**
     * 银行结束债权后，更新债权表为完全承接
     *
     * @return
     * @author liubin
     */
    @Override
    public int updateHjhDebtCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO) {
        String url = urlBase + "hjhDebtCredit/updateHjhDebtCreditByPK";
        hjhDebtCreditVO.setCreditStatus(2);//转让状态 2完全承接
        hjhDebtCreditVO.setIsLiquidates(1);
        Response<Integer> response = restTemplate.postForEntity(url, hjhDebtCreditVO, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 请求结束债权（追加结束债权任务记录）
     * @return
     * @author liubin
     */
    @Override
    public int requestDebtEnd(HjhDebtCreditVO credit, String sellerUsrcustid, String sellerAuthCode) {
        String url = urlBase + "bankCreditEndController/insertBankCreditEndForCreditEnd";
        InsertBankCreditEndForCreditEndRequest request = new InsertBankCreditEndForCreditEndRequest(credit, sellerUsrcustid, sellerAuthCode);
        Response<Integer> response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }
}
