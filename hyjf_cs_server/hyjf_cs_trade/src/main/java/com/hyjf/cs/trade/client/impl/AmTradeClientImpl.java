package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.MapResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.wdzj.BorrowDataResponse;
import com.hyjf.am.response.wdzj.PreapysListResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
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

    public static final  String BASE_URL = "http://AM-TRADE/am-trade/projectlist";

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
        HjhAccedeResponse response = restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
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
        IntegerResponse response = restTemplate.postForEntity(url, request, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
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

    @Override
    public List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/getmycouponbypage";
        CouponResponse response = restTemplate.postForEntity(url, requestBean, CouponResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 批次结束债权用更新 结束债权任务表
     * @author liubin
     */
    @Override
    public int updateCreditEndForBatch(BankCreditEndVO bankCreditEndVO) {
        String url = urlBase + "bankCreditEndController/updateBankCreditEndForBatch";
        BankCreditEndRequest request = new BankCreditEndRequest(bankCreditEndVO);
        IntegerResponse response = restTemplate.postForEntity(url, request, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    @Override
    public CouponResponse getBorrowCoupon(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/getborrowcoupon";
        CouponResponse response = restTemplate.postForEntity(url, requestBean, CouponResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponResponse getPlanCoupon(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/getplancoupon";
        CouponResponse response = restTemplate.postForEntity(url, requestBean, CouponResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据批次号和日期，取得结束债权任务列表
     * @param bankCreditEndVO
     * @return
     * @author liubin
     *
     */
    @Override
    public List<BankCreditEndVO> getBankCreditEndListByBatchnoTxdate(BankCreditEndVO bankCreditEndVO) {
        String url = urlBase + "bankCreditEndController/getBankCreditEndListByBatchnoTxdate/" + bankCreditEndVO.getBatchNo() + "/" + bankCreditEndVO.getTxDate();
        BankCreditEndResponse response = restTemplate.getForEntity(url, BankCreditEndResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据条件(批次号和日期)，更新结束债权任务状态
     * @param bankCreditEndVO
     * @param status
     * @return
     * @author liubin
     */
    @Override
    public int updateCreditEndForStatus(BankCreditEndVO bankCreditEndVO, int status) {
        String url = urlBase + "bankCreditEndController/updateBankCreditEndForStatus";
        UpdateBankCreditEndForStatusRequest request = new UpdateBankCreditEndForStatusRequest(bankCreditEndVO, status);
        IntegerResponse response = restTemplate.postForEntity(url, request, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 合法性检查后，更新批次结束债权任务
     * @param bankCallBeanVO
     * @return
     * @author liubin
     */
    @Override
    public int updateBatchCreditEndCheck(BankCallBeanVO bankCallBeanVO) {
        String url = urlBase + "bankCreditEndController/updateBatchCreditEndCheck";
        IntegerResponse response = restTemplate.postForEntity(url, bankCallBeanVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 银行完成后，更新批次结束债权任务
     * @param bankCallBeanVO
     * @return
     * @author liubin
     */
    @Override
    public int updateBatchCreditEndFinish(BankCallBeanVO bankCallBeanVO) {
        String url = urlBase + "bankCreditEndController/updateBatchCreditEndFinish";
        IntegerResponse response = restTemplate.postForEntity(url, bankCallBeanVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 根据borrowNid获取BorrowInfoVO对象
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfoVO getBorrowInfoByNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/"+borrowNid;
        BorrowInfoResponse response=restTemplate.getForEntity(url,BorrowInfoResponse.class).getBody();
        if(response!=null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 投资异常定时任务更新投资信息
     * @param request
     * @return
     */
    @Override
    public boolean updateTenderStart(BorrowTenderTmpRequest request) {
        String url = "http://AM-TRADE/am-trade/bankexception/updateTenderStart";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    /**
     * 获取BorrowTenderTmpVO列表
     */
	@Override
	public List<BorrowTenderTmpVO> getBorrowTenderTmpList() {
		String url = "http://AM-TRADE/am-trade/bankexception/getBorrowTenderTmpList";
		BorrowTenderTmpResponse response =restTemplate.getForEntity(url,BorrowTenderTmpResponse.class).getBody();
		if (response!=null){
			response.getResultList();
		}
		return null;
	}

	/**
	 * 获取BatchBorrowTenderCustomizeVO列表
	 */
	@Override
	public List<BatchBorrowTenderCustomizeVO> queryAuthCodeBorrowTenderList() {
		String url = "http://AM-TRADE/am-trade/bankException/queryAuthCodeBorrowTenderList";
		BatchBorrowTenderCustomizeResponse response =
				restTemplate.getForEntity(url,BatchBorrowTenderCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResultList();
		}
		return null;
	}

	
	/**
	 * 插入AuthCode
	 */
	@Override
	public void insertAuthCode(List<BatchBorrowTenderCustomizeVO> list) {
		String url = "http://AM-TRADE/am-trade/bankException/insertAuthCode";
		BatchBorrowTenderCustomizeRequest request = new BatchBorrowTenderCustomizeRequest();
		request.setBatchBorrowTenderCustomizeList(list);
		restTemplate.postForEntity(url,request,Boolean.class).getBody();
	}

	/**
	 * 网贷之家标的列表
	 * @auther: hesy
	 * @date: 2018/7/16
	 */
    @Override
    public List<BorrowListCustomizeVO> selectBorrowList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/get_borrowlist";
        BorrowDataResponse response =
                restTemplate.postForEntity(url,requestBean,BorrowDataResponse.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 网贷之家标的列表总记录数
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public Integer countBorrowList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/count_borrowlist";
        Response<Integer> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 网贷之家标的列表总金额
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public String sumBorrowAmount(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/sum_borrowamount";
        Response<String> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 网贷之家提前还款列表
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public List<PreapysListCustomizeVO> selectPreapysList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/get_preapyslist";
        PreapysListResponse response =
                restTemplate.postForEntity(url,requestBean,PreapysListResponse.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 网贷之家提前还款列表总记录数
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public Integer countPreapysList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/count_preapyslist";
        Response<Integer> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新借款API任务表
     * @auther: hesy
     * @date: 2018/7/17
     */
    @Override
    public Boolean updateBorrowApicron(ApiCronUpdateRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repay/update_apicron";
        Response<Boolean> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据bankSeqNo检索
     * @auther: hesy
     * @date: 2018/7/17
     */
    @Override
    public BorrowApicronVO selectBorrowApicron(String bankSeqNO) {
        String url = "http://AM-TRADE/am-trade/borrowApicron/getby_bankseqno/" + bankSeqNO;
        Response<BorrowApicronVO> response =
                restTemplate.getForEntity(url,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
    /**
     * 根据订单号查询充值信息
     * @param orderId
     * @return
     */
    @Override
    public AccountRechargeVO selectByOrderId(String orderId) {
        AccountRechargeResponse response = restTemplate
                .getForEntity(urlBase +"trade/selectByOrderId/"+orderId,AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
    /**
     * 更新充值详情信息
     * @param accountRecharge
     */
    @Override
    public void updateAccountRecharge(AccountRechargeVO accountRecharge) {
        restTemplate.put(urlBase +"trade/updateByPrimaryKeySelective",accountRecharge);
    }
    /**
     * 根据订单号查询充值数量
     * @param ordId
     * @return
     */
    @Override
    public int selectByOrdId(String ordId){
        Integer response = restTemplate
                .getForEntity(urlBase +"trade/selectByOrdId/"+ordId, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return -1;
    }
    /**
     * 插入银行卡信息
     * @param bankRequest
     * @return
     */
    @Override
    public int insertSelectiveBank(BankRequest bankRequest){
        Integer response = restTemplate
                .postForEntity(urlBase +"trade/insertSelectiveBank",bankRequest, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }
    /**
     * 修改银行卡信息
     * @param bankAccountBeanRequest
     * @return
     */
    @Override
    public boolean updateBanks(BankAccountBeanRequest bankAccountBeanRequest) {
        boolean response = restTemplate
                .postForEntity(urlBase +"trade/updateBanks",bankAccountBeanRequest, boolean.class).getBody();
        return response;
    }
    /**
     * 根据用户userId查询账号体系
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity(urlBase +"trade/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据订单号查询用户提现记录列表
     * @param ordId
     * @return
     */
    @Override
    public List<AccountWithdrawVO> selectAccountWithdrawByOrdId(String ordId) {
        AccountWithdrawResponse response = restTemplate
                .getForEntity(urlBase +"accountWithdraw/findByOrdId/" + ordId, AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 插入提现记录
     * @param record
     */
    @Override
    public void insertAccountWithdrawLog(AccountWithdrawVO record) {
        restTemplate.put(urlBase +"accountWithdraw/insertAccountWithdrawLog",record);
    }
    /**
     * 根据订单号查询用户提现记录信息
     * @param logOrderId
     * @return
     */
    @Override
    public AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId) {
        AccountWithdrawResponse response = restTemplate
                .getForEntity(urlBase +"accountWithdraw/getAccountWithdrawByOrdId/" + logOrderId, AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新用户提现记录
     * @param accountwithdraw
     * @return
     */
    @Override
    public int updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw) {
        int result = restTemplate
                .postForEntity(urlBase +"accountWithdraw/updateAccountwithdrawLog", accountwithdraw, Integer.class).getBody();
        return result;
    }
    /**
     * 提现后续操作
     * @param bankWithdrawBeanRequest
     * @return
     */
    @Override
    public int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankWithdrawBeanRequest) {
        int result = restTemplate
                .postForEntity(urlBase +"accountWithdraw/updatUserBankWithdrawHandler", bankWithdrawBeanRequest, Integer.class).getBody();
        return result;
    }
    /**
     * 查询用户标的投资数量
     * @param userId
     * @return
     */
    @Override
    public Integer getBorrowTender(Integer userId) {
        String url = urlBase +"accountWithdraw/getBorrowTender/"+userId;
        AccountWithdrawResponse response= restTemplate.getForEntity(url,AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getUserBorrowTenderCounte();
        }
        return 0;
    }
    /**
     * 根据用户id查询当前充值信息
     * @param userId
     * @return
     */
    @Override
    public List<AccountRechargeVO> getTodayRecharge(Integer userId) {
        AccountRechargeResponse response = restTemplate
                .getForEntity(urlBase +"accountWithdraw/getTodayRecharge/" + userId, AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 插入线下充值同步余额信息账户明细
     * @param synBalanceBeanRequest
     * @return
     */
    @Override
    public boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest) {
        String url = urlBase +"synBalance/insertAccountDetails";
        return restTemplate.postForEntity(url, synBalanceBeanRequest, Boolean.class).getBody();
    }




    @Override
    public List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer countProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    @Override
    public ProjectCustomeDetailVO searchProjectDetail(Map map) {
        ProjectDetailResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectDetail",map,ProjectDetailResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public CreditListResponse countCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countCreditList --> response = {}",response);
        return response;
    }

    @Override
    public CreditListResponse searchCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchWebCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchCreditList --> response = {}",response);
        return response;
    }

    /**
     * web:查询计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:27
     */
    @Override
    public Map<String, Object> searchPlanData(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/planData",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getTotalData();
        }
        return null;
    }

    /**
     * web:查询计划专区总数据count
     * @author zhangyk
     * @date 2018/6/21 15:28
     */
    @Override
    public Integer countPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countPlanList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * web:查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
    @Override
    public List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request) {
        com.hyjf.am.response.trade.HjhPlanResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchPlanList",request, com.hyjf.am.response.trade.HjhPlanResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:20
     */
    @Override
    public PlanDetailCustomizeVO getPlanDetail(String planNid){
        HjhPlanDetailResponse response = restTemplate.getForEntity(BASE_URL + "/web/searchPlanDetail/" + planNid,HjhPlanDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /*******************************  web end *************************************/
    /******************************  app start **************************************/
    /**
     *  app端获取散标投资项目count
     * @author zhangyk
     * @date 2018/6/20 17:23
     */
    @Override
    public Integer countAppProjectList(AppProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,AppProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countAppProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * app端获取散标投资项目列表
     * @author zhangyk
     * @date 2018/6/20 17:24
     */
    @Override
    public List<AppProjectListCustomizeVO> searchAppProjectList(AppProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppProjectList",request,AppProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     *  app端查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */

    @Override
    public ProjectListResponse countAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppCreditList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     *  APP端查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    @Override
    public ProjectListResponse searchAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     * APP端查询计划数据count
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    public Integer countAppPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countAppPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }


    /**
     * APP端查询计划数据list
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    public List<WebProjectListCustomizeVO> searchAppPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


    /******************************  app end **************************************/

    @Override
    public BorrowUserVO getBorrowUser(String borrowNid) {
        BorrowUserResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/borrowUserInfo/" + borrowNid,
                BorrowUserResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 借款公司信息
     * @author zhangyk
     * @date 2018/7/18 13:56
     */
    @Override
    public ProjectCompanyDetailVO searchProjectCompanyDetail(String borrowNid) {
        ProjectCompanyResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectCompany/" + borrowNid, ProjectCompanyResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 借款人信息
     * @author zhangyk
     * @date 2018/7/18 13:57
     */
    @Override
    public WebProjectPersonDetailVO searchProjectPersonDetail(String borrowNid) {
        ProjectPersonDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectPserson/" + borrowNid,ProjectPersonDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /**
     * 借款主体信息
     * @author zhangyk
     * @date 2018/7/18 13:58
     */
    @Override
    public BorrowManinfoVO getBorrowManinfo(String borrowNid) {
        BorrowManinfoResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowManinfo/" + borrowNid ,BorrowManinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /**
     * 房屋抵押信息
     * @author zhangyk
     * @date 2018/7/18 13:59
     */
    @Override
    public List<BorrowHousesVO> getBorrowHousesByNid(String borrowNid) {
        BorrowHousesResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowhouses/" + borrowNid, BorrowHousesResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 车辆抵押信息
     * @author zhangyk
     * @date 2018/7/18 14:01
     */
    @Override
    public List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid) {
        BorrowCarinfoResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowCarinfo/" + borrowNid,BorrowCarinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


    @Override
    public List<BorrowRepayVO> selectBorrowRepayList(String borrowNid, Integer repaySmsReminder) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/selectBorrowRepayList/" + borrowNid + "/" + repaySmsReminder,
                BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/updateBorrowRepay/",borrowRepayVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }



    @Override
    public List<BorrowRepayVO> getBorrowRepayList(String borrowNid) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/getBorrowRepayListByBorrowNid/" + borrowNid  ,
                BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowRepayVO getBorrowRepay(String borrowNid) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/getBorrowRepayListByBorrowNid/" + borrowNid  ,
                BorrowRepayResponse.class).getBody();
        if (response != null && response.getResultList() != null && !response.getResultList().isEmpty()) {
            return response.getResultList().get(0);
        }
        return null;
    }

    /**
     * 项目详情
     * @author zhangyk
     * @date 2018/7/18 14:06
     */
    @Override
    public ProjectCustomeDetailVO selectProjectDetail(String borrowNid) {
        ProjectDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectDetail/" + borrowNid, ProjectDetailResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 加入总数
     * @author zhangyk
     * @date 2018/7/18 14:11
     */
    @Override
    public Integer countPlanAccedeRecordTotal(HjhAccedeRequest request) {
        HjhAccedeResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAccede/countAccede/" + request.getPlanNid() ,HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getAccedeCount();
        }
        return null;
    }

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        AccountResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId ,AccountResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer updateOfPlanRepayAccount(AccountVO accountVO) {
        Integer result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateOfPlanRepayAccount/", accountVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }



    @Override
    public Integer countUserInvest(Integer userId, String borrowNid) {
        BorrowTenderResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrowTender/countUserInvest/" +userId + "/" + borrowNid,BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTenderCount();
        }
        return null;
    }

    @Override
    public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest) {
        String url = "http://AM-TRADE/am-trade/borrowTender/selectBorrowTender";
        BorrowTenderResponse response = restTemplate.postForEntity(url,btRequest,BorrowTenderResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<FddTempletVO> getFddTempletList(Integer protocolType) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getFddTempletList/"+protocolType;
        FddTempletResponse response = restTemplate.getForEntity(url,FddTempletResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int saveTenderAgreement(TenderAgreementVO info) {
        String url = "http://AM-TRADE/am-trade/borrowTender/saveTenderAgreement";
        return restTemplate.postForEntity(url,info,Integer.class).getBody();
    }

    @Override
    public int updateTenderAgreement(TenderAgreementVO tenderAgreement) {
        String url = "http://AM-TRADE/am-trade/borrowTender/updateTenderAgreement";
        return restTemplate.postForEntity(url,tenderAgreement,Integer.class).getBody();
    }

    @Override
    public List<BorrowTenderVO> getBorrowTenderListByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getBorrowTenderListByNid/"+nid;
        BorrowTenderResponse response = restTemplate.getForEntity(url,BorrowTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据投资订单号查询已承接金额
     *
     * @param tenderNid
     * @return
     */
    @Override
    public BigDecimal getAssignCapital(String tenderNid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/get_assign_capital_by_tender_nid/"+tenderNid;
        BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
        return response;
    }

    /**
     * 保存债转日志
     *
     * @param creditTenderLog
     * @return
     */
    @Override
    public Integer saveCreditTenderAssignLog(CreditTenderLogVO creditTenderLog) {
        String url = "http://AM-TRADE/am-trade/borrowTender/save_credit_tender_assign_log";
        return restTemplate.postForEntity(url,creditTenderLog,Integer.class).getBody();
    }

    /**
     * 查看是否已经插入网站收支明细
     *
     * @param logOrderId
     * @param tenderType 交易类型
     * @return
     */
    @Override
    public Integer countAccountWebListByOrdId(String logOrderId, String tenderType) {
        String url = "http://AM-TRADE/am-trade/borrowTender/countAccountWebListByOrdId/"+tenderType+"/"+tenderType;
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     * @param planAccede
     * @Description 插入计划交易明细表
     * @Author sunss
     * @Date 2018/6/22 10:34
     */
    @Override
    public boolean insertHJHPlanAccede(HjhAccedeVO planAccede) {
        Integer result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhPlan/insertHJHPlanAccede", planAccede, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 检索正在还款中的标的
     *
     * @Author liushouyi
     * @return
     */
    @Override
    public List<BorrowVO> selectBorrowList() {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/trade/selectRepayBorrowList/",
                BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取borrow对象
     * @param borrowId
     * @return
     */
    @Override
    public BorrowVO getBorrowByNid(String borrowId) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowByNid/"+borrowId;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    /**
     * 投资之前插入tmp表
     *
     * @param request
     */
    @Override
    public boolean updateBeforeChinaPnR(TenderRequest request) {
        Integer result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/insertBeforeTender", request, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 用户投资散标操作表
     *
     * @param tenderBg
     * @return
     */
    @Override
    public boolean borrowTender(TenderBgVO tenderBg) {
        Integer result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/borrowTender", tenderBg, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 修改状态临时表结果
     *  @param logUserId
     * @param logOrderId
     * @param respCode
     * @param retMsg
     * @param productId
     */
    @Override
    public boolean updateTenderResult(String logUserId, String logOrderId, String respCode, String retMsg, String productId) {
        TenderRetMsg tenderRetMsg = new TenderRetMsg();
        tenderRetMsg.setLogOrderId(logOrderId);
        tenderRetMsg.setLogUserId(logUserId);
        tenderRetMsg.setRespCode(respCode);
        tenderRetMsg.setRetMsg(retMsg);
        tenderRetMsg.setProductId(productId);
        Integer result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/updateTenderResult", tenderRetMsg, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 获取投资异步结果
     *
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    @Override
    public String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowTenderResult/" + userId + "/" + logOrdId + "/" + borrowNid;
        String response = restTemplate.getForEntity(url, String.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/hjhPlan/selectUserHjhInvistDetail";
        return null;
    }
    /**
     * 获取还款方式
     */
    @Override
    public BorrowStyleVO getBorrowStyle(String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowStyle/"+borrowStyle;
        BorrowStyleResponse response=restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
        if(response!=null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer getTotalInverestCount(String userId) {
        ProjectListResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/inverestCount/" + userId,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * 根据标的编号查询还款计划
     *
     * @param borrowNid 标的编号
     * @param repaySmsReminder 还款提示短信发送次数
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid, Integer repaySmsReminder) {

        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/selectBorrowRepayPlan/" + borrowNid + "/" + repaySmsReminder,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/updateBorrowRepayPlan/",borrowRepayPlanVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 根据borrowNid和borrowPeriod查询
     *
     * @param bidNid
     * @param borrowPeriod
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlansByPeriod(String bidNid, Integer borrowPeriod) {
        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/select_repay_list_by_period/" + bidNid + "/" + borrowPeriod,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据borrowNid检索
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlansByBorrowNid(String borrowNid) {
        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/select_by_borrownid/" + borrowNid,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询债转承接掉单的数据
     *
     * @return
     */
    @Override
    public List<CreditTenderLogVO> selectCreditTenderLogs() {
        CreditTenderLogResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTenderLogs",
                        CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据承接订单号查询债转投资表
     * @param assignNid
     * @return
     */
    @Override
    public List<CreditTenderVO> selectCreditTender(String assignNid) {
        CreditTenderResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTender/"+assignNid,
                        CreditTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 獲取銀行開戶信息
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新投標記錄
     * @param creditTenderLog
     * @return
     */
    @Override
    public Boolean updateCreditTenderLog(CreditTenderLogVO creditTenderLog) {
        String url = "http://AM-TRADE/am-trade/bankException/updateCreditTenderLog";
        return restTemplate.postForEntity(url, creditTenderLog, Boolean.class).getBody();
    }

    /**
     *同步回调收到后,根据logOrderId检索投资记录表
     * @param logOrderId
     * @return
     */
    @Override
    public CreditTenderLogVO selectCreditTenderLogByOrderId(String logOrderId) {
        CreditTenderLogResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTenderLogByOrderId/" + logOrderId, CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 獲取賬戶信息
     * @param
     * @return
     */
    @Override
    public AccountVO getAccount(int userId) {
        AccountResponse response
                = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public void handle() {

    }

    @Override
    public CreditTenderVO selectByAssignNidAndUserId(String assignNid, Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectByAssignNidAndUserId/" + assignNid+"/"+userId;
        CreditTenderResponse response = restTemplate.getForEntity(url,CreditTenderResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }


    @Override
    public boolean updateTenderCreditInfo(CreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/creditTender/updateTenderCreditInfo";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    /**
     * 获取CreditTenderLogs
     * add by jijun 20180622
     */
    @Override
    public List<CreditTenderLogVO> getCreditTenderLogs(String logOrderId, Integer userId) {
        String url = "http://AM-TRADE/am-trade/bankException/selectByOrderIdAndUserId/" + logOrderId+"/"+userId;
        CreditTenderLogResponse response = restTemplate.getForEntity(url,CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取BorrowCreditList
     * add by jijun 20180622
     */
    @Override
    public List<BorrowCreditVO> getBorrowCreditList(String creditNid, int sellerUserId, String tenderOrderId) {
        BorrowCreditRequest request = new BorrowCreditRequest();
        request.setCreditNid(creditNid);
        request.setSellerUserId(sellerUserId);
        request.setTenderOrderId(tenderOrderId);
        String url = "http://AM-TRADE/am-trade/bankException/getBorrowCreditList";
        BorrowCreditResponse response = restTemplate.postForEntity(url, request, BorrowCreditResponse.class).getBody();
        if(response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/creditTender/getCreditTenderList";
        CreditTenderResponse response = restTemplate.postForEntity(url, request, CreditTenderResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String, Object> params) {
        String url  = "http://AM-TRADE/am-trade/creditTender/selectWebCreditTenderDetailForContract";
        TenderToCreditDetailCustomizeResponse response = restTemplate.postForEntity(url, params, TenderToCreditDetailCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<TenderToCreditDetailCustomizeVO> selectHJHWebCreditTenderDetail(Map<String, Object> params) {
        String url  = "http://AM-TRADE/am-trade/creditTender/selectHJHWebCreditTenderDetail";
        TenderToCreditDetailCustomizeResponse response = restTemplate.postForEntity(url,params,TenderToCreditDetailCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            response.getResultList();
        }
        return null;
    }

    /**
     * 保存债转的数据
     * @param creditTenderBg
     * @return
     */
    @Override
    public Integer saveCreditBgData(CreditTenderBgVO creditTenderBg) {
        String url  = "http://AM-TRADE/am-trade/creditTender/saveCreditBgData";
        Integer response = restTemplate.postForEntity(url,creditTenderBg,Integer.class).getBody();
        return response;
    }

}
