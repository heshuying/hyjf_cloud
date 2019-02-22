package com.hyjf.cs.trade.service.assetmanage.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.HjhUserInvestListResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.AssetManagePlanRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
//import com.hyjf.am.vo.trade.hjh.PlanLockCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistListCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.GetDate;
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

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;


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
                    //法大大居间服务协议（type=2时候，为债转协议）
                    String nid = currentHoldObligatoryRightListCustomize.getNid();//出借订单号
                    if("2".equals(currentHoldObligatoryRightListCustomize.getType())){
                        nid = currentHoldObligatoryRightListCustomize.getCreditTenderNid();//债转投标单号
                    }
                    //法大大居间服务协议（type=1时候，为债转协议）
                    List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
                    if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                        TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                        Integer fddStatus = tenderAgreement.getStatus();
                        //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                        if(fddStatus.equals(3)){
                            currentHoldObligatoryRightListCustomize.setFddStatus(1);
                        }else {
                            //隐藏下载按钮
                            currentHoldObligatoryRightListCustomize.setFddStatus(2);
                        }
                    }else {
                        /**
                         * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                         * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                         * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                         */
                        BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(nid);
                        int addTime = ADD_TIME;
                        if(borrowRecoverVO != null){
                            addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                        }
                        if (addTime<ADD_TIME328) {
                            //下载老版本协议
                            currentHoldObligatoryRightListCustomize.setFddStatus(1);
                        }else{
                            //隐藏下载按钮
                            currentHoldObligatoryRightListCustomize.setFddStatus(0);
                        }
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
            logger.info("recordList:{}", JSONObject.toJSONString(recordList));
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
    public WebResult getMyHjhPlanInfoDetail(AssetManagePlanRequest request, Integer userId) {
        WebResult result = new WebResult();
        Map<String,Object> info = new HashMap<>();
        if (null == userId){
            throw new CheckException("用户信息失效，请重新登录");
        }

        String accedeOrderId = request.getAccedeOrderId();
        // 页面固定传值0是出借中 1是锁定中 2是已回款
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
                    hjhInvistDetailVO.setFddStatus(2);
                }
            }else {
                /**
                 * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                 * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                 * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                 */
                BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(accedeOrderId);
                int addTime = ADD_TIME;
                if(borrowRecoverVO != null){
                    addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                }
                if (addTime<ADD_TIME328) {
                    //下载老版本协议
                    hjhInvistDetailVO.setFddStatus(1);
                }else{
                    //隐藏下载按钮
                    hjhInvistDetailVO.setFddStatus(0);
                }
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
            /*if(StringUtils.isEmpty(hjhInvistDetailVO.getRepayActualTime())) {
                hjhInvistDetailVO.setRepayActualTime("— —");
            }*/
            // add 汇计划二期前端优化 持有中计划详情修改锁定期和实际退出时间 nxl 20180419 end
            String addTime = hjhInvistDetailVO.getAddTime();
            if (StringUtils.isNotBlank(addTime)){
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    hjhInvistDetailVO.setAddTime(dt.format(dt.parse(addTime)));
                } catch (ParseException e) {
                    logger.info("时间格式转换异常");
                }
            }
            info.put("userHjhInvistDetail", hjhInvistDetailVO);
            info.put("type", type);

            // 3持有项目列表 已经由别的方法替代， 此处不再提供investList
        }else{
            throw new CheckException("加入信息不存在,请确认后重试");
        }
        result.setData(info);
        return result;
    }


    @Override
    public WebResult getOrderInvestList(AssetManagePlanRequest request, Integer userId) {
        WebResult result = new WebResult();
        Map<String,Object> info = new HashMap<>();
        if (null == userId){
            throw new CheckException("用户信息失效，请重新登录");
        }

        String accedeOrderId = request.getAccedeOrderId();
        // 页面固定传值0是出借中 1是锁定中 2是已回款
        String type = request.getType();
        CheckUtil.check(StringUtils.isNotBlank(accedeOrderId),MsgEnum.ERR_OBJECT_REQUIRED,"加入订单号");

        // 1基本信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accedeOrderId", accedeOrderId);
        params.put("userId", userId);
        String ListUrl = "http://AM-TRADE/am-trade/hjhDebtCredit/getUserHjhInvestList";
        String countUrl = "http://AM-TRADE/am-trade/hjhDebtCredit/getUserHjhInvestCount";
        IntegerResponse countResponse = baseClient.postExe(countUrl,params,IntegerResponse.class);
        int projecTotal = countResponse.getResultInt();
        List<UserHjhInvistListCustomizeVO> tmpList = new ArrayList<>();
        Page page = Page.initPage(request.getCurrPage(),request.getPageSize());
        if (projecTotal > 0){
            page.setTotal(projecTotal);
            params.put("limitStart",page.getOffset());
            params.put("limitEnd",page.getLimit());
            HjhUserInvestListResponse response = baseClient.postExe(ListUrl,params,HjhUserInvestListResponse.class);
            tmpList = response.getResultList();
            if(tmpList!=null && tmpList.size()>0){
                for (UserHjhInvistListCustomizeVO userHjhInvistListCustomize : tmpList) {
                    String nid = userHjhInvistListCustomize.getNid();
                    //1是债转
                    if("1".equals(userHjhInvistListCustomize.getType())){
                        nid = userHjhInvistListCustomize.getInvestOrderId();
                    }
                    //法大大居间服务协议（type=2时候，为债转协议）
                    List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
                    if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                        TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                        Integer fddStatus = tenderAgreement.getStatus();
                        //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                        //System.out.println("******************1法大大协议状态："+tenderAgreement.getStatus());
                        if(fddStatus.equals(3)){
                            userHjhInvistListCustomize.setFddStatus(1);
                        }else {
                            //隐藏下载按钮
                            //System.out.println("******************2法大大协议状态：0");
                            userHjhInvistListCustomize.setFddStatus(2);
                        }
                    }else {
                        /**
                         * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                         * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                         * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                         */
                        BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(nid);
                        int addTime = ADD_TIME;
                        if(borrowRecoverVO != null){
                            addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                        }
                        if (addTime<ADD_TIME328) {
                            //下载老版本协议
                            userHjhInvistListCustomize.setFddStatus(1);
                        }else{
                            //隐藏下载按钮
                            userHjhInvistListCustomize.setFddStatus(0);
                        }
                    }
                }
            }
        }
        info.put("projectList",tmpList);
        //info.put("page",page);
        int nowTime = GetDate.getNowTime10();
        info.put("nowTime",nowTime);
        result.setPage(page);
        result.setData(info);
        return result;
    }

    @Override
    public WebResult getHtjDetail(AssetManagePlanRequest request, Integer userId) {
        WebResult result = new WebResult();
        Map<String,Object> info = new HashMap<>();
        if (null == userId){
            throw new CheckException("用户信息失效，请重新登录");
        }

        String accedeOrderId = request.getAccedeOrderId();
        // 页面固定传值0是出借中 1是锁定中 2是已回款
        String type = request.getType();
        CheckUtil.check(StringUtils.isNotBlank(accedeOrderId),MsgEnum.ERR_OBJECT_REQUIRED,"加入订单号");

        // 1基本信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accedeOrderId", accedeOrderId);
        params.put("userId", userId);
        List<PlanLockCustomizeVO> recordList = amTradeClient.selectUserProjectListCapital(params);
        if (recordList != null && recordList.size() > 0) {
            PlanLockCustomizeVO planinfo = recordList.get(0);
            info.put("planinfo", planinfo);
            BigDecimal accedeAccount = new BigDecimal(planinfo.getAccedeAccount());
            BigDecimal lockPeriod = new BigDecimal(planinfo.getDebtLockPeriod());
            BigDecimal expectApr = new BigDecimal(planinfo.getExpectApr()).divide(new BigDecimal("100"));
            BigDecimal repayAccountYes = new BigDecimal(planinfo.getRepayAccountYes());
            // 2资产统计
            String investTotal = amTradeClient.selectPlanInfoSum(accedeOrderId);
            BigDecimal investSum = BigDecimal.ZERO;
            if (StringUtils.isNotBlank(investTotal)) {
                // 当前持有资产总计
                investSum = new BigDecimal(investTotal);
                info.put("investSum", investSum);
            }
            // 预计到期收益 加入计划金额*计划期限*计划收益率/12；
            BigDecimal expectIntrest = accedeAccount.multiply(lockPeriod).multiply(expectApr).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_DOWN);
            info.put("expectIntrest", expectIntrest);
            // 回款总金额
            info.put("repayAccountYes", repayAccountYes);
            info.put("factIntrest", planinfo.getRepayInterestYes());
            Map<String, Object> params1 = new HashMap<String, Object>();
            params1.put("planOrderId", accedeOrderId);
            info.put("type", type);
            // params1.put("type", 1);
            // 3持有项目列表
            if (type != null && "1".equals(type)) {
                // 锁定中
                List<PlanInvestCustomizeVO> debtInvestList = amTradeClient.selectInvestCreditList(params1);
                List<PlanInvestCustomizeVO> debtCreditList = amTradeClient.selectCreditCreditList(params1);
                List<PlanInvestCustomizeVO> tmpList = new ArrayList<PlanInvestCustomizeVO>();
                if (debtInvestList != null) {
                    tmpList.addAll(debtInvestList);
                }
                if (debtCreditList != null) {

                    for (PlanInvestCustomizeVO userHjhInvistListCustomize : debtCreditList) {
                        logger.info("------------------------------userHjhInvistListCustomize:"+JSONObject.toJSONString(userHjhInvistListCustomize));
                        String nid = userHjhInvistListCustomize.getOrderId();
                        List<TenderAgreementVO> tenderAgreements= amTradeClient.selectTenderAgreementByNid(nid);
                        BorrowRecoverVO borrowRecovers =  amTradeClient.selectBorrowRecoverByNid(nid);
                        int addTimes = ADD_TIME;//放款时间
                        if(borrowRecovers!=null){
                            addTimes = (borrowRecovers.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecovers.getCreateTime()));
                        }
                        if(tenderAgreements!=null && tenderAgreements.size()>0){
                            TenderAgreementVO tenderAgreement = tenderAgreements.get(0);
                            Integer fddStatus = tenderAgreement.getStatus();
                            //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                            //System.out.println("******************1法大大协议状态："+tenderAgreement.getStatus());
                            if(fddStatus.equals(3)){
                                userHjhInvistListCustomize.setFddStatus(1);
                            }else {
                                //隐藏下载按钮
                                //System.out.println("******************2法大大协议状态：0");
                                userHjhInvistListCustomize.setFddStatus(2);
                            }
                        }else {
                            /**
                             * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                             * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                             * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                             */
                            if (addTimes<ADD_TIME328) {
                                //下载老版本协议
                                userHjhInvistListCustomize.setFddStatus(1);
                            }else{
                                //隐藏下载按钮
                                userHjhInvistListCustomize.setFddStatus(0);
                            }
                        }
                    }
                    tmpList.addAll(debtCreditList);
                }

                info.put("debtInvestList", tmpList);
               // modelAndView.setViewName(AssetManageDefine.TO_MY_PLAN_INFO_DETAIL_PAGE_PATH);

            } else if (type != null && "2".equals(type)) {
                params1.put("status", "11");
                // 已退出
                List<PlanInvestCustomizeVO> debtInvestList = amTradeClient.selectInvestCreditList(params1);
                List<PlanInvestCustomizeVO> debtCreditList = amTradeClient.selectCreditCreditList(params1);
                List<PlanInvestCustomizeVO> tmpList = new ArrayList<PlanInvestCustomizeVO>();
                if (debtInvestList != null) {
                    tmpList.addAll(debtInvestList);
                }
                if (debtCreditList != null) {
                    tmpList.addAll(debtCreditList);
                }
                info.put("debtInvestList", tmpList);
            } else {
                // 申购中
                //modelAndView.setViewName(AssetManageDefine.TO_MY_PLAN_INFO_DETAIL_PAGE_PATH);
            }
        }

     return  result;
    }
}
