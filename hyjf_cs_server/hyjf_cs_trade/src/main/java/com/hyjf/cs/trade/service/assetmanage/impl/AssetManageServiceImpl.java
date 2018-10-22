package com.hyjf.cs.trade.service.assetmanage.impl;

import com.hyjf.am.response.trade.HjhUserInvestListResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.AssetManagePlanRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistListCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.assetmanage.AssetManageService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.vo.WebGetRepayMentRequestVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pangchengchao
 * @version AssetManageServiceImpl, v0.1 2018/6/20 17:31
 */
@Service
public class AssetManageServiceImpl extends BaseTradeServiceImpl implements AssetManageService  {

    private static final Logger logger = LoggerFactory.getLogger(AssetManageServiceImpl.class);

    @Autowired
    AmUserClient bindCardClient;

    @Autowired
    private BaseClient baseClient;


    @Override
    public AccountVO getAccount(Integer userId) {
        return bindCardClient.getAccount(userId);
    }

    @Override
    public ObligatoryRightAjaxBean createCurrentHoldObligatoryRightListPage(AssetManageBeanRequest form) {
        ObligatoryRightAjaxBean result = new ObligatoryRightAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        request.setUserId(form.getUserId());
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
            request.setLimitStart( page.getOffset());
            request.setLimitEnd( page.getLimit());
            // 获取用户当前持有债权列表
            List<CurrentHoldObligatoryRightListCustomizeVO> recordList = amTradeClient.selectCurrentHoldObligatoryRightList(request);
            //法大大协议信息
            if(recordList!=null && recordList.size()>0){
                for (CurrentHoldObligatoryRightListCustomizeVO currentHoldObligatoryRightListCustomize : recordList) {
                    String nid = currentHoldObligatoryRightListCustomize.getNid();
                    //法大大居间服务协议（type=2时候，为债转协议）
                    List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
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
        request.setUserId(form.getUserId());
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
            List<RepayMentListCustomizeVO> recordList = amTradeClient.selectRepaymentList(request);
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
        request.setUserId(form.getUserId());
        // 查询标签页显示数量
        searchListCount(result, request);
        // 查询相应的汇消费列表的总数
        int recordTotal = result.getTenderCreditDetailCount();
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            List<TenderCreditDetailCustomizeVO> recordList = amTradeClient.selectCreditRecordList(request);
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
        request.setUserId(form.getUserId());
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setOrderByFlag(form.getOrderByFlag());
        request.setSortBy(form.getSortBy());
        // 获取用户当前持有计划记录总数
        int recordTotal = this.amTradeClient.countCurrentHoldPlanTotal(request);
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            // 获取用户当前持有计划记录列表
            List<CurrentHoldPlanListCustomizeVO> recordList = amTradeClient.selectCurrentHoldPlanList(request);
            result.setCurrentHoldPlanList(recordList);
            result.setCurrentHoldPlanCount(recordTotal);
        } else {
            result.setCurrentHoldPlanList(new ArrayList<CurrentHoldPlanListCustomizeVO>());
            result.setCurrentHoldPlanCount(0);
        }
        result.setRepayMentPlanCount(this.amTradeClient.countRepayMentPlanTotal(request));
        result.setPage(page);
        return result;
    }

    @Override
    public PlanAjaxBean getRepayMentPlan(AssetManageBeanRequest form) {

        PlanAjaxBean result=new PlanAjaxBean();
        AssetManageBeanRequest request=new AssetManageBeanRequest();
        request.setUserId(form.getUserId());
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setOrderByFlag(form.getOrderByFlag());
        request.setSortBy(form.getSortBy());
        // 获取用户当前持有计划记录总数
        int recordTotal = this.amTradeClient.countRepayMentPlanTotal(request);
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 获取用户当前持有计划记录列表
            List<RepayMentPlanListCustomizeVO> recordList = amTradeClient.selectRepayMentPlanList(request);
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
        result.setCurrentHoldPlanCount(this.amTradeClient.countCurrentHoldPlanTotal(request));
        result.setPage(page);
        return result;
    }

    private void searchListCount(ObligatoryRightAjaxBean result,AssetManageBeanRequest request) {
        // 获取用户当前持有债权总数
        int currentHoldObligatoryRightCount = amTradeClient.selectCurrentHoldObligatoryRightListTotal(request);
        result.setCurrentHoldObligatoryRightCount(currentHoldObligatoryRightCount);
        // 获取用户已回款债权列表总数
        //int repaymentCount = amTradeClient.selectRepaymentListTotal(request);
        int repaymentCount = this.amTradeClient.selectRepaymentListTotalWeb(request);
        result.setRepayMentCount(repaymentCount);
        // 获取用户转让记录总数
        int tenderCreditDetailCount = amTradeClient.countCreditRecordTotal(request);
        result.setTenderCreditDetailCount(tenderCreditDetailCount);
    }

    /**
     * 获取用户还款计划数据
     * @param request
     * @return
     */
    @Override
    public RepayPlanInfoBean getRepayPlanInfo(WebGetRepayMentRequestVO request){
        if(StringUtils.isBlank(request.getBorrowNid()) || StringUtils.isBlank(request.getNid()) || StringUtils.isBlank(request.getTypeStr())){
            throw new CheckException(MsgEnum.ERR_PARAM_NUM);
        }
        return amTradeClient.getRepayPlanInfo(request.getBorrowNid(), request.getNid(), request.getTypeStr());
    }

    /**
     * 获取用户散标转让记录详情
     * @param creditNid
     * @return
     */
    @Override
    public MyCreditDetailBean getMyCreditAssignDetail(String creditNid){
        return amTradeClient.getMyCreditAssignDetail(creditNid);
    }


    /**
     * 获取我加入的计划详情信息
     * @author zhangyk
     * @date 2018/8/18 16:07
     */
    @Override
    public WebResult getMyPlanInfoDetail(AssetManagePlanRequest request, Integer userId) {
        WebResult result = new WebResult();
        Map<String,Object> info = new HashMap<>();
        if (null == userId){
            throw new CheckException("用户信息失效，请重新登录");
        }

        String accedeOrderId = request.getAccedeOrderId();
        // 页面固定传值0是投资中 1是锁定中 2是已回款
        String type = request.getType();
        CheckUtil.check(StringUtils.isNotBlank(accedeOrderId),MsgEnum.ERR_OBJECT_REQUIRED,"加入订单号");

        // 1基本信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accedeOrderId", accedeOrderId);
        params.put("userId", userId);
        UserHjhInvistDetailCustomizeVO hjhInvistDetailVO = amTradeClient.selectUserHjhInvistDetail(params);
        if (null != hjhInvistDetailVO){
            // add by nxl 智投服务：格式化参考回报和授权时间格式化Start
            if(hjhInvistDetailVO.getWaitInterest().equals("0.00")){
                hjhInvistDetailVO.setWaitInterest("--");
            }

            if(StringUtils.isNotBlank(hjhInvistDetailVO.getAddTime())){
                hjhInvistDetailVO.setAddTimeFormat(hjhInvistDetailVO.getAddTime().substring(0,10));
            }
            // add by nxl 智投服务：格式化参考回报和授权时间格式化End
            //计算实际收益
            if(type != null && "2".equals(type)){
                if(hjhInvistDetailVO.getAccedeAccount()!=null&&hjhInvistDetailVO.getReceivedTotal()!=null){
                    BigDecimal receivedTotal=new BigDecimal(hjhInvistDetailVO.getReceivedTotal().replaceAll(",", "").trim());
                    BigDecimal accedeAccount=new BigDecimal(hjhInvistDetailVO.getAccedeAccount().replaceAll(",", "").trim());
                    BigDecimal userHjhInvistDetail=receivedTotal.subtract(accedeAccount);
                    int i=userHjhInvistDetail.compareTo(BigDecimal.ZERO);
                    if(i == -1){
                        hjhInvistDetailVO.setReceivedInterest(BigDecimal.ZERO.toString());
                        logger.info("实际收益userHjhInvistDetail为负数");
                    }else{
                        hjhInvistDetailVO.setReceivedInterest(userHjhInvistDetail.toString());
                    }
                }
            }
            List<TenderAgreementVO> tenderAgreementVOList = amTradeClient.selectTenderAgreementByNid(accedeOrderId);
            if(CollectionUtils.isNotEmpty(tenderAgreementVOList)){
                TenderAgreementVO tenderAgreement = tenderAgreementVOList.get(0);
                Integer fddStatus = tenderAgreement.getStatus();
                //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                //System.out.println("******************1法大大协议状态："+tenderAgreement.getStatus());
                if(null !=fddStatus && fddStatus.equals(3)){
                    hjhInvistDetailVO.setFddStatus(1);
                }else {
                    //隐藏下载按钮
                    //System.out.println("******************2法大大协议状态：0");
                    hjhInvistDetailVO.setFddStatus(0);
                }
            }else {
                //下载老版本协议
                //System.out.println("******************3法大大协议状态：2");
                hjhInvistDetailVO.setFddStatus(1);
            }

            // add 汇计划二期前端优化 持有中计划详情修改锁定期和实际退出时间 nxl 20180419 start
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
            Date datePeriod = null;
            if (StringUtils.isNotEmpty(hjhInvistDetailVO.getCountInterestTime())&&!hjhInvistDetailVO.getCountInterestTime().equals("待确认")) {
                try {
                    Date dateAddTime = smp.parse(hjhInvistDetailVO.getCountInterestTime());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(dateAddTime);
                    if (hjhInvistDetailVO.getPlanPeriod().contains("天")) {
                        String days = hjhInvistDetailVO.getPlanPeriod().split("天")[0];
                        int intD = Integer.parseInt(days);
                        calendar.add(Calendar.DAY_OF_MONTH, +intD);
                        datePeriod = calendar.getTime();
                    }
                    if (hjhInvistDetailVO.getPlanPeriod().contains("个月")) {
                        String days = hjhInvistDetailVO.getPlanPeriod().split("个月")[0];
                        int intD = Integer.parseInt(days);
                        calendar.add(Calendar.MONTH, +intD);
                        datePeriod = calendar.getTime();
                    }
                    if (datePeriod != null) {
                        String endStrDate = smp.format(datePeriod);
                        String startStrDate = hjhInvistDetailVO.getAddTime().substring(0, 10);
                        // mod by nxl 智投服务，修改服务期限的显示格式
                       /* hjhInvistDetailVO.setPlanPeriod(startStrDate + "~" + endStrDate);*/
                        hjhInvistDetailVO.setEndInterestTime(endStrDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                // mod by nxl 智投服务，修改服务期限的显示格式
//				hjhInvistDetailVO.setPlanPeriod("— —");
                //add by nxl 如果开始计息时间为空或为待确认是,将开始计息时间设置为空
                hjhInvistDetailVO.setCountInterestTime(null);
                hjhInvistDetailVO.setEndInterestTime(null);
            }
            // add by nxl 智投服务：计划状态为退出中显示开始退出时间 start
            // 计划状态为5 代表退出中(如果不是退出中并且是持有中的时候，将开始推出时间设置为空）
            if(!hjhInvistDetailVO.getOrderStatus().equals("5")&&type.equals("1")){
                hjhInvistDetailVO.setEndInterestTime(null);
            }
            // add by nxl 智投服务：计划状态为退出中显示开始退出时间 end

            // 实际退出时间
            if(StringUtils.isEmpty(hjhInvistDetailVO.getRepayActualTime())) {
                hjhInvistDetailVO.setRepayActualTime("— —");
            }
            // add 汇计划二期前端优化 持有中计划详情修改锁定期和实际退出时间 nxl 20180419 end
            info.put("userHjhInvistDetail", hjhInvistDetailVO);
            info.put("type", type);

            // 3持有项目列表
            String url = "http://AM-TRADE/am-trade/hjhDebtCredit/getUserHjhInvestList";
            if (type != null && "1".equals(type)) {
                // 锁定中
                HjhUserInvestListResponse response = baseClient.postExe(url,params,HjhUserInvestListResponse.class);
                List<UserHjhInvistListCustomizeVO> userHjhInvistBorrowList = response.getResultList();
                List<UserHjhInvistListCustomizeVO> tmpList = new ArrayList<UserHjhInvistListCustomizeVO>();
                if (userHjhInvistBorrowList != null) {
                    // add by cwyang 2018-3-27 计划持有项目显示协议下载按钮
                    for (int i = 0; i < userHjhInvistBorrowList.size(); i++) {
                        UserHjhInvistListCustomizeVO userHjhInvistListCustomize = userHjhInvistBorrowList.get(i);
                        String nid = userHjhInvistListCustomize.getNid();
                        List<TenderAgreementVO> tenderAgreements= amTradeClient.selectTenderAgreementByNid(nid);
                        if(CollectionUtils.isNotEmpty(tenderAgreements)){
                            TenderAgreementVO tenderAgreement = tenderAgreements.get(0);
                            Integer fddStatus = tenderAgreement.getStatus();
                            //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                            //System.out.println("******************1法大大协议状态："+tenderAgreement.getStatus());
                            if(fddStatus.equals(3)){
                                userHjhInvistListCustomize.setFddStatus(1);
                            }else {
                                //隐藏下载按钮
                                //System.out.println("******************2法大大协议状态：0");
                                userHjhInvistListCustomize.setFddStatus(0);
                            }
                        }else {
                            //下载老版本协议
                            //System.out.println("******************3法大大协议状态：2");
                            userHjhInvistListCustomize.setFddStatus(1);
                        }
                    }
                    tmpList.addAll(userHjhInvistBorrowList);
                }
                info.put("investList", tmpList);
                // modelAndView.setViewName(AssetManageDefine.TO_MY_HJH_PLAN_INFO_DETAIL_PAGE_PATH);

            } else if (type != null && "2".equals(type)) {
                // 已退出
                //modelAndView.setViewName(AssetManageDefine.TO_MY_HJH_PLAN_INFO_DETAIL_PAGE_PATH);
                HjhUserInvestListResponse response = baseClient.postExe(url,params,HjhUserInvestListResponse.class);
                List<UserHjhInvistListCustomizeVO> debtInvestList = response.getResultList();
                List<UserHjhInvistListCustomizeVO> tmpList = new ArrayList<UserHjhInvistListCustomizeVO>();
                if (debtInvestList != null) {
                    tmpList.addAll(debtInvestList);
                }
                info.put("investList", tmpList);
            } else {
                // 申购中
                //modelAndView.setViewName(AssetManageDefine.TO_MY_HJH_PLAN_INFO_DETAIL_PAGE_PATH);
            }

        }else{
            throw new CheckException("加入信息不存在,请确认后重试");
        }
        result.setData(info);
        return result;
    }
}
