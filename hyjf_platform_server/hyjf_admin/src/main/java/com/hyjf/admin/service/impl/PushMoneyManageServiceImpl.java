package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.BankAccountManageClient;
import com.hyjf.admin.service.PushMoneyManageService;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.BorrowApicronRequest;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.resquest.admin.TenderCommissionRequest;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.AverageCapitalPlusInterestUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRecoverServiceImpl, v0.1 2018/7/2 10:17
 */
@Service
public class PushMoneyManageServiceImpl implements PushMoneyManageService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    public AmUserClient amUserClient;


    @Autowired
    private BankAccountManageClient bankAccountManageClient;




    /**
     * 查找直投提成管理列表
     *
     * @param request
     * @return
     */
    @Override
    public PushMoneyResponse findPushMoneyList(PushMoneyRequest request) {
        return amTradeClient.findPushMoneyList(request);
    }

    /**
     * 取得借款API表
     *
     * @param borrowNid
     * @return
     */
    public BorrowApicronVO getBorrowApicronBorrowNid(String borrowNid) {
        List<BorrowApicronVO> list = this.amTradeClient.selectBorrowApicronListByBorrowNid(borrowNid);
        if (list!= null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 发提成处理- 计算提成
     *
     * @param apicornId,request
     * @return
     */
    public int insertTenderCommissionRecord(Integer apicornId, PushMoneyRequest request) {
        int ret = -1;
        // 项目编号
        String borrowNid = request.getBorrowNid();

        // 根据项目编号取得borrow表

        BorrowVO borrow = this.amTradeClient.selectBorrowByNid(borrowNid);

        // 根据项目编号取得borrowTender表
        List<BorrowTenderVO> borrowTenderList = this.amTradeClient.getBorrowTenderListByNid(borrowNid);
        // 循环BorrowTender表,计算提成
        for (BorrowTenderVO borrowTender : borrowTenderList) {
            boolean is51 = false;
            // 插入提成数据
            TenderCommissionRequest tenderCommissionRequest = new TenderCommissionRequest();
            // 1 直投类提成
            tenderCommissionRequest.setTenderType(1);
            // 投资人
            tenderCommissionRequest.setTenderUserId(borrowTender.getUserId());
            // 投资金额
            tenderCommissionRequest.setAccountTender(borrowTender.getAccount());
            // 项目编号
            tenderCommissionRequest.setBorrowNid(borrow.getBorrowNid());
            // 投资ID
            tenderCommissionRequest.setTenderId(borrowTender.getId());
            // 投资时间
            tenderCommissionRequest.setTenderTime(borrowTender.getAddTime());
            // 状态 0：未发放；1：已发放
            tenderCommissionRequest.setStatus(0);
            // 备注
            tenderCommissionRequest.setRemark("3");
            // 计算时间
            tenderCommissionRequest.setComputeTime(GetDate.getMyTimeInMillis());
            // 订单号
            tenderCommissionRequest.setOrdid(borrowTender.getNid());

            // 获得提成的地区名
            tenderCommissionRequest.setRegionName(borrowTender.getInviteRegionName());
            tenderCommissionRequest.setRegionId(borrowTender.getInviteRegionId());
            // 获得提成的分公司
            tenderCommissionRequest.setBranchName(borrowTender.getInviteBranchName());
            tenderCommissionRequest.setBranchId(borrowTender.getInviteBranchId());
            // 获得提成的部门
            tenderCommissionRequest.setDepartmentName(borrowTender.getInviteDepartmentName());
            tenderCommissionRequest.setDepartmentId(borrowTender.getInviteDepartmentId());

            UserInfoVO tenderUsersInfo = amUserClient.findUserInfoById(borrowTender.getUserId());
            int tenderIs51 = 0; // 1 是
            if (tenderUsersInfo != null && tenderUsersInfo.getIs51()!=null) {
                tenderIs51 = tenderUsersInfo.getIs51();
            }
            // 提成人id
            if (borrowTender.getTenderUserAttribute() == 3) {
                // 投资时投资人是线上员工时，提成人是自己
                tenderCommissionRequest.setUserId(borrowTender.getUserId());
                if (tenderIs51 == 1) {
                    is51 = true;
                }
            } else {
                UserInfoVO refererUsersInfo = amUserClient.findUserInfoById(borrowTender.getInviteUserId());
                if (borrowTender.getInviteUserAttribute() != null && borrowTender.getInviteUserAttribute() == 3) {
                    // 投资时推荐人的用户属性是线上员工，提成人是推荐人
                    tenderCommissionRequest.setUserId(borrowTender.getInviteUserId());
                    if (refererUsersInfo != null && refererUsersInfo.getIs51()!=null && refererUsersInfo.getIs51() == 1) {
                        is51 = true;
                    }
                } else if (borrowTender.getInviteUserAttribute() != null && borrowTender.getInviteUserAttribute() < 2) {
                    // 投资时推荐人不是员工，且推荐人是51老用户，提成人是推荐人
                    if (refererUsersInfo != null && refererUsersInfo.getIs51()!=null && refererUsersInfo.getIs51() == 1) {
                        tenderCommissionRequest.setUserId(borrowTender.getInviteUserId());
                        is51 = true;
                    }
                }
            }
            if(tenderCommissionRequest.getUserId()==null ||tenderCommissionRequest.getUserId()==0){
                //如果没有提成人，返回
                continue;
            }
            BankOpenAccountVO bankOpenAccountInfo = bankAccountManageClient.getBankOpenAccount(tenderCommissionRequest.getUserId());
            if(bankOpenAccountInfo != null){
                tenderCommissionRequest.setAccountId(bankOpenAccountInfo.getAccount());
            }

            // 计算提成(提成金额,提成人,提成人部门ID,投资人部门ID)
            calculaeCommission(tenderCommissionRequest, borrowTender.getTenderUserAttribute(), // 投资时投资人的用户属性
                    borrowTender.getInviteUserAttribute(), // 投资时推荐人的用户属性
                    borrow.getBorrowStyle(), // 还款方式（endday表示天，其它表示月）
                    borrow.getBorrowPeriod(), // 借款期限（几个月/天）
                    borrow.getProjectType(), // 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
                    borrow.getBorrowApr(), // 借款利率`
                    is51);

            if (tenderCommissionRequest.getCommission()!=null && tenderCommissionRequest.getCommission().compareTo(BigDecimal.ZERO) > 0) {
                Integer counts = amTradeClient.getCountTenderCommissionBybBorrowNid(tenderCommissionRequest);
                if (counts == 0) {
                    // 执行插入
                    ret += this.amTradeClient.saveTenderCommission(tenderCommissionRequest);
                } else {
                    ret++;
                }
            }
        }

        // 更新借款API表
        BorrowApicronRequest borrowApicronRequest = new BorrowApicronRequest();
        borrowApicronRequest.setId(apicornId);
        borrowApicronRequest.setWebStatus(1);
        ret += this.amTradeClient.updateByPrimaryKeySelective(borrowApicronRequest);

        return ret;
    }

    /**
     * 取得提成配置
     *
     * @param type
     * @return
     */
    private PushMoneyVO getPushMoney(String type) {
        PushMoneyRequest pushMoneyRequest = new PushMoneyRequest();
        pushMoneyRequest.setProjectType(Integer.valueOf(type));
        List<PushMoneyVO> list = this.amTradeClient.findPushMoneyList(pushMoneyRequest).getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 计算提成
     *
     * @param tenderCommission
     * @param tenderAttr
     * @param refererAttr
     * @param borrowStyle
     * @param borrowPerios
     * @param projectType
     * @param borrowApr
     */
    private void calculaeCommission(TenderCommissionRequest tenderCommission, Integer tenderAttr, Integer refererAttr,
                                     String borrowStyle, Integer borrowPerios, Integer projectType, BigDecimal borrowApr, Boolean is51) {
        // 提成人
        Integer commissionUserId = tenderCommission.getUserId();
        // 提成金额
        BigDecimal commission = BigDecimal.ZERO;
        // 提成利率(天标)
        BigDecimal rateDay = BigDecimal.ZERO;
        // 提成利率(月标)
        BigDecimal rateMonth = BigDecimal.ZERO;
        // 投资金额
        BigDecimal accountTender = tenderCommission.getAccountTender();
        // 年利率
        borrowApr = borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);

        // 判断提成人是否开户  (提成人未开户的不计算提成)

       // BankOpenAccountVO bankOpenAccountInfo = this.getBankOpenAccount(commissionUserId);
        BankOpenAccountVO bankOpenAccountInfo = this.amTradeClient.getBankOpenAccount(commissionUserId);
        if (bankOpenAccountInfo == null || Validator.isNull(bankOpenAccountInfo.getAccount())) {
            //LogUtil.errorLog(this.getClass().getName(), "calculateCommission", "计算提成失败，因为用户没有开户", null);
            return;
        }

        // 取得提成利率
//		PushMoney pushMoney = getPushMoney(!is51 ? "51老用户" : "线上员工");//如果提成人即是51老用户，又是线上员工，那么安装线上员工标准来计算。
        String tcrAttr="";
        if( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ){
            tcrAttr="线上员工";
        }else{
            tcrAttr="51老用户";
        }

        // 51老用户不发提成
        if(tcrAttr.equals("51老用户")){
            return;
        }

        PushMoneyVO pushMoney = getPushMoney(tcrAttr);
        if(pushMoney ==null || pushMoney.getRewardSend() != 1){
            return;
        }

        if (pushMoney != null && NumberUtils.isNumber(pushMoney.getDayTender())) {
            rateDay = new BigDecimal(pushMoney.getDayTender());// 提成利率(天标)
            rateMonth = new BigDecimal(pushMoney.getMonthTender());// 提成利率(月标)
        }

        // 汇消费以外
        if (projectType != 8) {
            // 按天计息,到期还本还息
            if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                // 线上员工
                if ( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ) {
                    // 每笔提成= 投资金额*提成比例（天-线上员工）*融资天数
                    commission = accountTender.multiply(rateDay).multiply(new BigDecimal(borrowPerios));
                }
                //51老用户（非员工）
                else {
                    if (borrowPerios >= 50) {
                        // 融资期限≥50天时，每笔提成=投资金额*提成比例（月-51老用户）
                        commission = accountTender.multiply(rateMonth);
                    } else {
                        // 融资期限＜50天时，每笔提成=投资金额*提成比例（天-51老用户）*天数
                        commission = accountTender.multiply(rateDay).multiply(new BigDecimal(borrowPerios));
                    }
                }
            }
            // 其他还款方式 (月标)
            else {
                // 线上员工
                if ( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ) {
                    // 每笔提成=投资金额*提成比例（月-线上员工）*融资月数
                    commission = accountTender.multiply(rateMonth).multiply(new BigDecimal(borrowPerios));
                }
                //51老用户（非员工）
                else {
                    // 每笔提成= 投资金额*提成比例（月-51老用户）
                    commission = accountTender.multiply(rateMonth);
                }
            }
        }
        // 汇消费(等额本息)
        else {
            // 线上员工
            if ( (tenderAttr!=null && tenderAttr==3) || (refererAttr!=null && refererAttr==3) ) {
                // 等额本息还贷第n个月还贷本金之和
                Map<Integer, BigDecimal> monthPrincipal = AverageCapitalPlusInterestUtils
                        .getPerMonthPrincipal(accountTender, borrowApr, borrowPerios);
                if (monthPrincipal != null && monthPrincipal.size() > 0) {
                    for (Map.Entry<Integer, BigDecimal> entry : monthPrincipal.entrySet()) {
                        commission = commission
                                .add(entry.getValue().multiply(rateMonth).multiply(new BigDecimal(entry.getKey())));
                    }
                }
            }
            //51老用户（非员工）
            else {
                // 投资金额*提成比例
                commission = accountTender.multiply(rateMonth);
            }
        }

        // 提成金额
        tenderCommission.setCommission(commission == null ? BigDecimal.ZERO : commission);

        // 根据用户ID查询部门信息
        UserInfoCustomizeVO userInfoCustomize =  amUserClient.getUserInfoCustomizeByUserId(commissionUserId);
        if (userInfoCustomize != null) {
            tenderCommission.setRegionId(userInfoCustomize.getRegionId());
            tenderCommission.setRegionName(userInfoCustomize.getRegionName());
            tenderCommission.setBranchId(userInfoCustomize.getBranchId());
            tenderCommission.setBranchName(userInfoCustomize.getBranchName());
            tenderCommission.setDepartmentId(userInfoCustomize.getDepartmentId());
            tenderCommission.setDepartmentName(HtmlUtil.unescape(userInfoCustomize.getDepartmentName()));
        }
    }
}
