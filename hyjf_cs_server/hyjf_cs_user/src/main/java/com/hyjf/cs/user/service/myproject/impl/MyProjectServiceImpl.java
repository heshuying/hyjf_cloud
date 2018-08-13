package com.hyjf.cs.user.service.myproject.impl;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.myproject.MyProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * jijun
 * 20180727
 */
@Service
public class MyProjectServiceImpl extends BaseUserServiceImpl implements MyProjectService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void selectCurrentHoldObligatoryRightList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo) {
        AssetManageBeanRequest request = new AssetManageBeanRequest();
        request.setUserId(userId);
        int total = amTradeClient.selectCurrentHoldObligatoryRightListTotal(request);
        if (total > 0) {
        	//update by jijun 按照投资时间排序
            request.setOrderByFlag("2");
            request.setSortBy("DESC");
            buildQueryPageParam(currentPage, pageSize, request);
            List<CurrentHoldObligatoryRightListCustomizeVO> lst = amTradeClient.selectCurrentHoldObligatoryRightList(request);
            for (CurrentHoldObligatoryRightListCustomizeVO customize: lst) {
                String capital = customize.getCapital();
                customize.setCapital(CommonUtils.formatAmount(capital));
                String couponType = customize.getCouponType();
                switch (couponType) {
                case "1":
                    customize.setData("体验金");
                    break;
                case "2":
                    customize.setData("加息券");
                    break;
                case "3":
                    customize.setData("代金券");
                    break;
                default:
                    if("2".equals(customize.getType())){
                        customize.setData("承接债转");
                    } else {
                        customize.setData("");
                    }
                }
            }
            vo.getLstProject().addAll(lst);
        }
        boolean isEnd = pageSize * currentPage >= total;
        vo.setEnd(isEnd);
    }


    @Override
    public void selectRepaymentList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo) {
        AssetManageBeanRequest request = new AssetManageBeanRequest();
        request.setUserId(userId);
        int total = amTradeClient.selectRepaymentListTotal(request);
        if (total > 0) {
            buildQueryPageParam(currentPage, pageSize, request);
            List<AppAlreadyRepayListCustomizeVO> lst = amTradeClient.selectAlreadyRepayList(request);
            for (AppAlreadyRepayListCustomizeVO customize: lst) {
                String account = customize.getAccount().replaceAll(",", "");
                String interest = customize.getInterest().replaceAll(",", "");
                customize.setAccount(CommonUtils.formatAmount(account));
                customize.setInterest(CommonUtils.formatAmount(interest));
                customize.setRecoverTime( GetDate.times10toStrYYYYMMDD(Integer.valueOf(customize.getRecoverTime())));
            }
            vo.getLstProject().addAll(lst);

        }
        boolean isEnd = pageSize * currentPage >= total;
        vo.setEnd(isEnd);
    }

    @Override
    public void selectCreditRecordList(Integer userId, int currentPage, int pageSize,QueryMyProjectVO vo) {
        AssetManageBeanRequest request = new AssetManageBeanRequest();
        request.setUserId(userId);
        int total=amTradeClient.countCreditRecordTotal(request);
        if(total>0){
            buildQueryPageParam(currentPage,pageSize,request);
            List<AppTenderCreditRecordListCustomizeVO> lst= amTradeClient.searchCreditRecordList(request);
            for (AppTenderCreditRecordListCustomizeVO customize: lst) {
                String creditCapital = customize.getCreditCapital();
                //update by jijun 20180427
                creditCapital = creditCapital.replaceAll(",", "");  
                String assigned = customize.getCreditCapitalAssigned().replaceAll(",", "");
                customize.setCreditCapital(CommonUtils.formatAmount(creditCapital));
                customize.setCreditCapitalAssigned(CommonUtils.formatAmount(assigned));
            }
            vo.getLstProject().addAll(lst);
        }
        boolean isEnd = pageSize * currentPage >= total;
        vo.setEnd(isEnd);

    }

    @Override
    public void selectCurrentHoldPlanList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo) {
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        request.setUserId(userId);
        int total=amTradeClient.countCurrentHoldPlanTotal(request);
        if(total>0){
        	//update by jijun 20180420
            request.setOrderByFlag("2");
            request.setSortBy("DESC");
            buildQueryPageParam(currentPage,pageSize,request);
            List<CurrentHoldPlanListCustomizeVO> lst=amTradeClient.selectCurrentHoldPlanList(request);
            vo.getLstProject().addAll(lst);
        }

        boolean isEnd = pageSize * currentPage >= total;
        vo.setEnd(isEnd);

    }

    @Override
    public void selectRepayMentPlanList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo) {
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        request.setUserId(userId);
        int total=amTradeClient.countRepayMentPlanTotal(request);
        if(total>0){
        	//按照汇款时间倒叙排序 update by jijun 2018/05/03
			request.setOrderByFlag("3");
			request.setSortBy("DESC");
            buildQueryPageParam(currentPage,pageSize,request);
            List<RepayMentPlanListCustomizeVO> recordList = amTradeClient.selectRepayMentPlanList(request);
            //计算实际收益
            for (RepayMentPlanListCustomizeVO record : recordList) {
                if (!"2".equals(record.getType()) && record.getRepayAccountYes() != null && record.getAccedeAccount() != null) {
                    BigDecimal receivedTotal = new BigDecimal(record.getRepayAccountYes().replaceAll(",", "").trim());
                    BigDecimal accedeAccount = new BigDecimal(record.getAccedeAccount().replaceAll(",", "").trim());
                    BigDecimal userHjhInvistDetail = receivedTotal.subtract(accedeAccount);
                    int account = userHjhInvistDetail.compareTo(BigDecimal.ZERO);
                    if (account == -1) {
                        record.setRepayInterestYes(BigDecimal.ZERO.toString());
                    } else {
                        record.setRepayInterestYes(userHjhInvistDetail.toString());
                    }
                }
            }

            vo.getLstProject().addAll(recordList);
        }

        boolean isEnd = pageSize * currentPage >= total;
        vo.setEnd(isEnd);
    }

    @Override
    public AccountVO getAccount(Integer userId) {
        return amTradeClient.getAccount(userId);
    }

    private void buildQueryPageParam(int currentPage, int pageSize, AssetManageBeanRequest request) {
        int offSet = (currentPage - 1) * pageSize;
        if (offSet == 0 || offSet > 0) {
            request.setLimitStart(offSet);
        }
        if (pageSize > 0) {
            request.setLimitEnd(pageSize);
        }
    }
}
