package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowRepaymentBean;
import com.hyjf.admin.beans.BorrowRepaymentPlanBean;
import com.hyjf.admin.beans.DelayRepayInfoBean;
import com.hyjf.admin.beans.RepayInfoBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BorrowRepaymentService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.AdminRepayDelayCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentServiceImpl, v0.1 2018/7/4 11:37
 */
@Service
public class BorrowRepaymentServiceImpl implements BorrowRepaymentService {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AmTradeClient amTradeClient;
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> hjhInstConfigVOList = amTradeClient.selectCommonHjhInstConfigList();
        return hjhInstConfigVOList;
    }

    @Override
    public BorrowRepaymentBean searchBorrowRepayment(BorrowRepaymentRequest request) {

        BorrowRepaymentBean bean=new BorrowRepaymentBean();
        Integer count = this.amTradeClient.countBorrowRepayment(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);
        if (count != null && count > 0) {
            List<BorrowRepaymentCustomizeVO> recordList = this.amTradeClient.selectBorrowRepaymentList(request);
            bean.setRecordList(recordList);
            BorrowRepaymentCustomizeVO sumObject = this.amTradeClient.sumBorrowRepaymentInfo(request);
            bean.setSumObject(sumObject);
        }else{
            bean.setSumObject(new BorrowRepaymentCustomizeVO());
            bean.setRecordList(new ArrayList<BorrowRepaymentCustomizeVO>());
        }
        return bean;
    }

    @Override
    public AdminBorrowRepaymentResponse exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        return  this.amTradeClient.exportRepayClkActBorrowRepaymentInfoList(request);
    }

    @Override
    public List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest request) {
        return this.amTradeClient.selectBorrowRepaymentList(request);
    }

    @Override
    public DelayRepayInfoBean getDelayRepayInfo(String borrowNid) {
        DelayRepayInfoBean bean=new DelayRepayInfoBean();
        AdminRepayDelayCustomizeVO repayDelay = this.amTradeClient.selectBorrowInfo(borrowNid);
        bean.setBorrowRepayInfo(repayDelay);
        // 单期标
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(repayDelay.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(repayDelay.getBorrowStyle())) {
            BorrowRepayVO borrowRepay = this.amTradeClient.getBorrowRepayDelay(borrowNid, repayDelay.getBorrowApr(), repayDelay.getBorrowStyle());
            bean.setRepayInfo(borrowRepay);
            bean.setRepayTime(GetDate.formatDate(Long.valueOf(GetDate.countDate(borrowRepay.getRepayTime(),5,borrowRepay.getDelayDays())) * 1000L));
            bean.setDelayDays(borrowRepay.getDelayDays());
        } else {
            BorrowRepayPlanVO borrowRepayPlan = this.amTradeClient.getBorrowRepayPlanDelay(borrowNid, repayDelay.getBorrowApr(), repayDelay.getBorrowStyle());
            bean.setRepayInfo(borrowRepayPlan);
            bean.setRepayTime(GetDate.formatDate(Long.valueOf(GetDate.countDate(borrowRepayPlan.getRepayTime(),5,borrowRepayPlan.getDelayDays())) * 1000L));
            bean.setDelayDays(borrowRepayPlan.getDelayDays());
        }
        return bean;
    }

    @Override
    public DelayRepayInfoBean updateBorrowRepayDelayDays(String borrowNid, String delayDays, String repayTime) {
        DelayRepayInfoBean bean=new DelayRepayInfoBean();
        boolean afterDayFlag = validateSignlessNum("delayDays", delayDays, 1, true);
        boolean updateFlag=false;
        if (afterDayFlag) {
            if (!(Integer.valueOf(delayDays) >= 1 && Integer.valueOf(delayDays) <= 8)) {
                updateFlag=true;
            } else {
                // 延期日8天后、应该还款的日期
                Integer lastReapyTime = Integer.valueOf(repayTime) + (8 * 24 * 3600);
                // 延期日加上输入的天数后、应该还款的日期
                Integer nowTimePlusDelayDays = Integer.valueOf(repayTime) + (Integer.valueOf(delayDays) * 24 * 3600);
                if (nowTimePlusDelayDays > lastReapyTime) {
                    String repayTimeForMsg = GetDate.formatDate(Long.valueOf(lastReapyTime) * 1000);
                    bean.setRepayTimeForMsg(repayTimeForMsg);
                    updateFlag=true;
                }
            }
        }

        if (updateFlag) {
            bean.setDelayDays(Integer.parseInt(delayDays));
            return bean;
        }

        this.amTradeClient.updateBorrowRepayDelayDays(borrowNid, delayDays);
        bean.setSuccess("success");
        return bean;
    }

    @Override
    public RepayInfoBean getRepayInfo(String borrowNid) {
        RepayInfoBean bean=new RepayInfoBean();
        AdminRepayDelayCustomizeVO repayDelay = this.amTradeClient.selectBorrowInfo(borrowNid);
        bean.setBorrowRepayInfo(repayDelay);
        String userId = repayDelay.getUserId();
        AccountVO account = this.amTradeClient.getAccountByUserId(Integer.parseInt(userId));
        // 借款人账户余额
        BigDecimal balance = account.getBankBalance();
        bean.setBalance(balance);
        // 单期标
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(repayDelay.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(repayDelay.getBorrowStyle())) {
            BorrowRepayBeanVO borrowRepay = this.amTradeClient.getBorrowRepayInfo(borrowNid, repayDelay.getBorrowApr(), repayDelay.getBorrowStyle());
            bean.setRepayInfo(borrowRepay);
        } else {// 多期标
            BorrowRepayPlanBeanVO borrowRepayPlan = this.amTradeClient.getBorrowRepayPlanInfo(borrowNid, repayDelay.getBorrowApr(), repayDelay.getBorrowStyle());
            bean.setRepayInfo(borrowRepayPlan);
        }
        return bean;
    }

    @Override
    public BorrowRepaymentPlanBean searchBorrowRepaymentPlan(BorrowRepaymentRequest request) {
        BorrowRepaymentPlanBean bean=new BorrowRepaymentPlanBean();
        Integer count = this.amTradeClient.countBorrowRepaymentPlan(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);
        if (count != null && count > 0) {
            List<BorrowRepaymentPlanCustomizeVO> recordList = this.amTradeClient.selectBorrowRepaymentPlanList(request);
            bean.setRecordList(recordList);
            BorrowRepaymentPlanCustomizeVO sumObject = this.amTradeClient.sumBorrowRepaymentPlanInfo(request);
            bean.setSumObject(sumObject);
        }else{
            bean.setSumObject(new BorrowRepaymentPlanCustomizeVO());
            bean.setRecordList(new ArrayList<BorrowRepaymentPlanCustomizeVO>());
        }
        return bean;
    }

    @Override
    public List<BorrowRepaymentPlanCustomizeVO> selectBorrowRepaymentPlanList(BorrowRepaymentRequest request) {
        return  this.amTradeClient.selectBorrowRepaymentPlanList(request);
    }

    /**
     * 检查半角数字最大长度（无小数点）正整数
     *
     * @param itemname
     * @param value
     * @param required
     * @return
     */
    public static boolean validateSignlessNum( String itemname, String value, int maxLength,
                                              boolean required) {
        boolean retValue = validateMaxLength(itemname, value, maxLength, required);

        if (retValue && !StringUtils.isEmpty(value)) {
            if (!GenericValidator.isInt(value) || !NumberUtils.isCreatable(value) || Integer.valueOf(value) < 0) {
                retValue = false;
            }
        }
        return retValue;
    }
    /**
     * 检查最大文字数
     *
     * @param itemname
     * @param value
     * @param maxlength
     * @param required
     * @return
     */
    public static boolean validateMaxLength(String itemname, String value, int maxlength,
                                            boolean required) {
        boolean retValue = true;
        if (required) {
            retValue = validateRequired(itemname, value);
        }

        if (retValue) {
            if (value != null && maxlength > 0) {
                try {
                    retValue = GenericValidator.maxLength(value, maxlength);
                    if (!retValue) {
                        retValue = false;
                    }
                } catch (Exception e) {
                    retValue = false;
                }
            }
        }
        return retValue;
    }
    /**
     * 必须输入项目
     *
     * @param itemname
     * @param value
     * @return true:正常 false:错误
     */
    public static boolean validateRequired( String itemname, String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return true;
    }
    @Override
    public Integer countBorrowRepayment(BorrowRepaymentRequest request) {

		return   this.amTradeClient.countBorrowRepayment(request);
    }
    @Override
    public RepayInfoBean getRepayInfo2(String borrowNid) {
        RepayInfoBean bean=new RepayInfoBean();
        AdminRepayDelayCustomizeVO repayDelay = this.amTradeClient.selectBorrowInfo(borrowNid);
        bean.setBorrowRepayInfo(repayDelay);
//        String userId = repayDelay.getUserId();
//        AccountVO account = this.amTradeClient.getAccountByUserId(Integer.parseInt(userId));
//        // 借款人账户余额
//        BigDecimal balance = account.getBankBalance();
//        bean.setBalance(balance);
        
        // 单期标
//        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(repayDelay.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(repayDelay.getBorrowStyle())) {
//            BorrowRepayBeanVO borrowRepay = this.amTradeClient.getBorrowRepayInfo(borrowNid, repayDelay.getBorrowApr(), repayDelay.getBorrowStyle());
//            bean.setRepayInfo(borrowRepay);
//        } else {// 多期标
//            BorrowRepayPlanBeanVO borrowRepayPlan = this.amTradeClient.getBorrowRepayPlanInfo(borrowNid, repayDelay.getBorrowApr(), repayDelay.getBorrowStyle());
//            bean.setRepayInfo(borrowRepayPlan);
//            
            BorrowRepaymentRequest request=new BorrowRepaymentRequest();
            request.setBorrowNid(borrowNid);
			List<BorrowRepaymentPlanCustomizeVO> re = amTradeClient.selectBorrowRepaymentPlanList(request);
			bean.setList(re);
        //}
        return bean;
    }
}
