package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/6/19 15:44
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

    public static final String urlBase = "http://AM-TRADE/am-trade/";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 统计加息券每日待收收益
     *
     * @param
     * @return
     */
    @Override
    public List<CouponRecoverCustomizeVO> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
        String url = urlBase + "couponRepay/selectCouponInterestWaitToday/" + timeStart + "/" + timeEnd;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url, CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 统计加息券每日已收收益
     *
     * @param
     * @return
     */
    @Override
    public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
        String url = urlBase + "couponRepay/selectCouponInterestReceivedToday/" + timeStart + "/" + timeEnd;
        BigDecimal interest = restTemplate.getForEntity(url, BigDecimal.class).getBody();
        if (interest != null) {
            return interest;
        }
        return null;
    }

    /**
     * 我的优惠券列表
     *
     * @auther: hesy
     * @date: 2018/6/23
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponList(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/myCouponList";
        MyCouponListResponse response = restTemplate.postForEntity(url, requestBean, MyCouponListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CouponTenderCustomizeVO> selectCouponRecoverAll(String borrowNid, int repayTimeConfig) {
        String url = urlBase + "batch/selectCouponRecover/" + borrowNid + "/" + repayTimeConfig;
        CouponTenderCustomizeResponse response = restTemplate.getForEntity(url, CouponTenderCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow) {
        String url = urlBase + "batch/getCurrentCouponRecover/" + couponTenderNid + "/" + periodNow;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url, CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 统计总的优惠券数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectMyCouponCount(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/myCouponCount";
        Integer result = restTemplate.postForEntity(url, requestBean, Integer.class).getBody();
        return result;
    }

    /**
     * 我的奖励列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean) {
        String url = urlBase + "reward/myRewardList";
        MyRewardListResponse response = restTemplate.postForEntity(url, requestBean, MyRewardListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 我的奖励列表总记录数
     */
    @Override
    public int selectMyRewardCount(MyInviteListRequest requestBean) {
        int count = restTemplate
                .postForEntity(urlBase + "reward/myRewardTotal", requestBean, Integer.class).getBody();
        return count;
    }

    /**
     * 统计总的奖励金额
     *
     * @param requestBean
     * @return
     */
    @Override
    public BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean) {
        String url = urlBase + "reward/myRewardTotal";
        BigDecimal result = restTemplate.postForEntity(url, requestBean, BigDecimal.class).getBody();
        return result;
    }

    /**
     * 受托支付待授权列表
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectBorrowAuthList(BorrowAuthRequest requestBean) {
        String url = urlBase + "borrowauth/list_auth";
        BorrowAuthResponse response = restTemplate.postForEntity(url, requestBean, BorrowAuthResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 受托支付待授权总记录数
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public int selectBorrowAuthCount(BorrowAuthRequest requestBean) {
        int count = restTemplate
                .postForEntity(urlBase + "borrowauth/count_auth", requestBean, Integer.class).getBody();
        return count;
    }

    /**
     * 受托支付已授权列表
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectBorrowAuthedList(BorrowAuthRequest requestBean) {
        String url = urlBase + "borrowauth/list_authed";
        BorrowAuthResponse response = restTemplate.postForEntity(url, requestBean, BorrowAuthResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 受托支付已授权总记录数
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public int selectBorrowAuthedCount(BorrowAuthRequest requestBean) {
        int count = restTemplate
                .postForEntity(urlBase + "borrowauth/count_authed", requestBean, Integer.class).getBody();
        return count;
    }

    /**
     * 受托支付回调更新
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Integer updateTrusteePaySuccess(String borrowNid) {
        String url = urlBase + "borrowauth/auth_update/" + borrowNid;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    /**
     * 根据用户id查询受托支付白名单
     *
     * @param userId
     * @param stzUserId
     * @return
     */
    @Override
    public STZHWhiteListVO getStzhWhiteListVO(Integer userId, Integer stzUserId) {
        String url = urlBase + "borrowauth/get_whitelist/" + userId + "/" + stzUserId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户权限
     * @author zhangyk
     * @date 2018/6/29 18:36
     */
    @Override
    public HjhUserAuthVO getUserAuthByUserId(Integer userId) {
        HjhUserAuthResponse response = restTemplate.getForEntity("",HjhUserAuthResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid,
                BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

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
        Response<Map<String, Object>> response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (response != null) {
            return response.getResult();
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
        Response<BigDecimal> result = restTemplate.postForEntity(url, hjhDebtCreditVO, Response.class).getBody();
        if (result != null){
            return result.getResult();
        }
        return null;
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
        if (response == null || !Response.isSuccess(response)) {
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
        if (response == null || !Response.isSuccess(response)) {
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
        if (response == null || !Response.isSuccess(response)) {
            return null;
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
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 根据标的编号，查询汇计划信息
     * @return
     * @author liubin
     */
    @Override
    public HjhPlanVO getPlanByNid(String borrowNid) {
        String url = urlBase + "hjhPlan/getHjhPlanByPlanNid/" + borrowNid;
        HjhPlanResponse response = restTemplate.getForEntity(url, HjhPlanResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据creditNid查询债转信息
     * @author liubin
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid) {
        String url = urlBase + "hjhDebtCredit/selectHjhDebtCreditByCreditNid/" + creditNid;
        HjhDebtCreditResponse response = restTemplate.getForEntity(url, HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据加入计划订单，取得加入订单
     * @author liubin
     * @date 2018/7/04 19:26
     */
    @Override
    public HjhAccedeVO getHjhAccedeByAccedeOrderId(String accedeOrderId) {
        String url = urlBase + "hjhAccede/getHjhAccedeListByAccedeOrderId/" + accedeOrderId;
        HjhAccedeResponse response = restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新加入计划状态
     * @author liubin
     * @date 2018/7/04 19:26
     */
    @Override
    public int updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO) {
        String url = urlBase + "hjhAccede/updateHjhAccedeByPrimaryKey";
        Response<Integer> response = restTemplate.postForEntity(url, hjhAccedeVO, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 插入汇计划自动投资临时表
     * @author liubin
     */
    @Override
    public int insertHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/insertHjhPlanBorrowTmp";
        Response<Integer> response = restTemplate.postForEntity(url, hjhPlanBorrowTmpVO, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 删除汇计划自动投资临时表
     * @author liubin
     */
    @Override
    public int deleteHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/deleteHjhPlanBorrowTmp";
        Response<Integer> response = restTemplate.postForEntity(url, hjhPlanBorrowTmpVO, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 根据主键，更新汇计划自动投资临时表
     * @author liubin
     */
    @Override
    public int updateHjhPlanBorrowTmpByPK(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/updateHjhPlanBorrowTmpByPK";
        Response<Integer> response = restTemplate.postForEntity(url, hjhPlanBorrowTmpVO, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }
}
