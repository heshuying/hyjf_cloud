package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.client.AssetManageClient;
import com.hyjf.cs.trade.client.BindCardClient;
import com.hyjf.cs.trade.controller.web.assetmanage.WebAssetManageController;
import com.hyjf.cs.trade.service.AssetManageService;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version AssetManageServiceImpl, v0.1 2018/6/20 17:31
 */
@Service
public class AssetManageServiceImpl extends BaseTradeServiceImpl implements AssetManageService  {

    private static final Logger logger = LoggerFactory.getLogger(AssetManageServiceImpl.class);

    @Autowired
    BindCardClient bindCardClient;

    @Autowired
    AssetManageClient assetManageClient;

    @Override
    public AccountVO getAccount(Integer userId) {
        return bindCardClient.getAccount(userId);
    }

    @Override
    public ObligatoryRightAjaxBean createCurrentHoldObligatoryRightListPage(AssetManageBeanRequest form) {
        ObligatoryRightAjaxBean result = new ObligatoryRightAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        request.setUserId(userId);
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setOrderByFlag(form.getOrderByFlag());
        request.setSortBy(form.getSortBy());
        // 查询标签页显示数量
        searchListCount(result, request);
        // 获取用户当前持有债权总数
        int recordTotal = result.getCurrentHoldObligatoryRightCount();
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            request.setLimitEnd( page.getOffset());
            request.setLimitEnd( page.getLimit());
            // 获取用户当前持有债权列表
            List<CurrentHoldObligatoryRightListCustomizeVO> recordList = assetManageClient.selectCurrentHoldObligatoryRightList(request);
            //法大大协议信息
            if(recordList!=null && recordList.size()>0){
                for (CurrentHoldObligatoryRightListCustomizeVO currentHoldObligatoryRightListCustomize : recordList) {
                    String nid = currentHoldObligatoryRightListCustomize.getNid();
                    //法大大居间服务协议（type=2时候，为债转协议）
                    List<TenderAgreementVO> tenderAgreementsNid= assetManageClient.selectTenderAgreementByNid(nid);//居间协议
                    if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                        TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                        Integer fddStatus = tenderAgreement.getStatus();
                        //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                        if(fddStatus.equals(3)){
                            currentHoldObligatoryRightListCustomize.setFddStatus(1);
                        }else {
                            //隐藏下载按钮
                            currentHoldObligatoryRightListCustomize.setFddStatus(0);
                        }
                    }else {
                        //下载老版本协议
                        currentHoldObligatoryRightListCustomize.setFddStatus(1);
                    }
                }
            }
            result.setCurrentHoldObligatoryRightList(recordList);
            result.setCurrentHoldObligatoryRightCount(recordTotal);
        } else {
            result.setCurrentHoldObligatoryRightList(new ArrayList<CurrentHoldObligatoryRightListCustomizeVO>());
            result.setCurrentHoldObligatoryRightCount(0);
        }
        result.setPage(page);
        return result;
    }

    @Override
    public ObligatoryRightAjaxBean createRepayMentListPage(AssetManageBeanRequest form) {

        ObligatoryRightAjaxBean result = new ObligatoryRightAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        request.setUserId(userId);
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setOrderByFlag(form.getOrderByFlag());
        request.setSortBy(form.getSortBy());
        // 查询标签页显示数量
        searchListCount(result, request);
        // 获取用户已回款债权总数
        int recordTotal = result.getRepayMentCount();
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            request.setLimitEnd( page.getOffset());
            request.setLimitEnd( page.getLimit());
            // 获取用户已回款债权列表
            List<RepayMentListCustomizeVO> recordList = assetManageClient.selectRepaymentList(request);
            result.setRepayMentList(recordList);
            result.setRepayMentCount(recordTotal);
        } else {
            result.setRepayMentList(new ArrayList<RepayMentListCustomizeVO>());
            result.setRepayMentCount(0);
        }
        result.setPage(page);
        return result;
    }

    @Override
    public ObligatoryRightAjaxBean getCreditRecordList(AssetManageBeanRequest form) {
        ObligatoryRightAjaxBean result = new ObligatoryRightAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        request.setUserId(userId);
        // 查询标签页显示数量
        searchListCount(result, request);
        // 查询相应的汇消费列表的总数
        int recordTotal = result.getTenderCreditDetailCount();
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            List<TenderCreditDetailCustomizeVO> recordList = assetManageClient.selectCreditRecordList(request);
            result.setCreditRecordList(recordList);
            result.setTenderCreditDetailCount(recordTotal);
        } else {
            result.setCreditRecordList(new ArrayList<TenderCreditDetailCustomizeVO>());
            result.setTenderCreditDetailCount(0);
        }
        result.setPage(page);
        return result;
    }

    @Override
    public PlanAjaxBean getCurrentHoldPlan(AssetManageBeanRequest form) {
        PlanAjaxBean result=new PlanAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        request.setUserId(userId);
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setOrderByFlag(form.getOrderByFlag());
        request.setSortBy(form.getSortBy());
        // 获取用户当前持有计划记录总数
        int recordTotal = this.assetManageClient.countCurrentHoldPlanTotal(request);
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            // 获取用户当前持有计划记录列表
            List<CurrentHoldPlanListCustomizeVO> recordList = assetManageClient.selectCurrentHoldPlanList(request);
            result.setCurrentHoldPlanList(recordList);
            result.setCurrentHoldPlanCount(recordTotal);
        } else {
            result.setCurrentHoldPlanList(new ArrayList<CurrentHoldPlanListCustomizeVO>());
            result.setCurrentHoldPlanCount(0);
        }
        result.setRepayMentPlanCount(this.assetManageClient.countRepayMentPlanTotal(request));
        result.setPage(page);
        return result;
    }

    @Override
    public PlanAjaxBean getRepayMentPlan(AssetManageBeanRequest form) {

        PlanAjaxBean result=new PlanAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        request.setUserId(userId);
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setOrderByFlag(form.getOrderByFlag());
        request.setSortBy(form.getSortBy());
        // 获取用户当前持有计划记录总数
        int recordTotal = this.assetManageClient.countRepayMentPlanTotal(request);
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 获取用户当前持有计划记录列表
            List<RepayMentPlanListCustomizeVO> recordList = assetManageClient.selectRepayMentPlanList(request);
            if(recordList!=null && recordList.size()>0) {
                //计算实际收益
                for (int i = 0; i < recordList.size(); i++) {
                    if (!"2".equals(recordList.get(i).getType()) && recordList.get(i).getRepayAccountYes() != null && recordList.get(i).getAccedeAccount() != null) {
                        BigDecimal receivedTotal = new BigDecimal(recordList.get(i).getRepayAccountYes().replaceAll(",", "").trim());
                        BigDecimal accedeAccount = new BigDecimal(recordList.get(i).getAccedeAccount().replaceAll(",", "").trim());
                        BigDecimal userHjhInvistDetail = receivedTotal.subtract(accedeAccount);
                        int account = userHjhInvistDetail.compareTo(BigDecimal.ZERO);
                        if (account == -1) {
                            recordList.get(i).setRepayInterestYes(BigDecimal.ZERO.toString());
                            logger.info("实际收益userHjhInvistDetail为负数");
                        } else {
                            recordList.get(i).setRepayInterestYes(userHjhInvistDetail.toString());
                        }
                    }
                }
            }
            result.setRepayMentPlanList(recordList);
            result.setRepayMentPlanCount(recordTotal);
        } else {
            result.setRepayMentPlanList(new ArrayList<RepayMentPlanListCustomizeVO>());
            result.setCurrentHoldPlanCount(0);
        }
        result.setCurrentHoldPlanCount(this.assetManageClient.countCurrentHoldPlanTotal(request));
        result.setPage(page);
        return result;
    }

    private void searchListCount(ObligatoryRightAjaxBean result,AssetManageBeanRequest request) {
        // 获取用户当前持有债权总数
        int currentHoldObligatoryRightCount = assetManageClient.selectCurrentHoldObligatoryRightListTotal(request);
        result.setCurrentHoldObligatoryRightCount(currentHoldObligatoryRightCount);
        // 获取用户已回款债权列表总数
        int repaymentCount = assetManageClient.selectRepaymentListTotal(request);
        result.setRepayMentCount(repaymentCount);
        // 获取用户转让记录总数
        int tenderCreditDetailCount = assetManageClient.countCreditRecordTotal(request);
        result.setTenderCreditDetailCount(tenderCreditDetailCount);
    }
}
