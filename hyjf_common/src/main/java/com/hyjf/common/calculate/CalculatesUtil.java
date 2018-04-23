package com.hyjf.common.calculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;

/**
 * 借款利息计算类
 *
 * @author GOGTZ-T
 */
public class CalculatesUtil {

    /** 等额本息 */
	public static final String STYLE_MONTH = "month";
    /** 等额本金 */
    public static final String STYLE_PRINCIPAL = "principal";
    /** 按月计息，到期还本还息 */
    public static final String STYLE_END = "end";
    /** 按天计息，到期还本还息 */
    public static final String STYLE_ENDDAY = "endday";
    /** 先息后本 */
    public static final String STYLE_ENDMONTH = "endmonth";

    /** 汇消费 */
    public static final Integer PROJECT_TYPE_HXF = 8;

    /**
     * 计算借款利息
     *
     * @param account
     *            本金
     * @param period
     *            还款期数
     * @param apr
     *            年利率
     * @param style
     *            还款类型
     * @param time
     *            本期还款时间
     * @param monthRate
     *            月利率
     * @param monthRateEnd
     *            月利率(下限)
     * @param projectType
     *            项目类型
     * @param return_rate 
     * //新加收益差率
     * @param man_charge_rate 
     *  //新加管理费率
     * @param verifyTime 
     *  //初审时间
     */
    public static InterestInfo getInterestInfo(
            BigDecimal account,
            Integer borrowPeriod,
            BigDecimal apr,
            String borrowStyle,
            Integer time,
            BigDecimal monthRate,
            BigDecimal monthRateEnd,
            Integer projectType, 
            BigDecimal return_rate ,
            Integer verifyTime
            ) {
        // 空判断
        if (Validator.isNull(account) || Validator.isNull(borrowPeriod) || Validator.isNull(apr)) {
            return null;
        }
        // 年利率
        BigDecimal yearRate = apr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);

        InterestInfo info = null;
        // 等额本息
        if (STYLE_MONTH.equals(borrowStyle)) {
            info = getMonth(account, borrowPeriod, yearRate, time, monthRate, monthRateEnd, projectType,return_rate,verifyTime);
        }
        // 等额本金
        else if (STYLE_PRINCIPAL.equals(borrowStyle)) {
            info = getPrincipal(account, borrowPeriod, yearRate, time, monthRate,return_rate,verifyTime);
        }
        // 按月计息，到期还本还息
        else if (STYLE_END.equals(borrowStyle)) {
            info = getEnd(account, borrowPeriod, yearRate, time, monthRate,return_rate,verifyTime);
        }
        // 按天计息，到期还本还息
        else if (STYLE_ENDDAY.equals(borrowStyle)) {
            info = getEndDay(account, borrowPeriod, yearRate, time, monthRate,return_rate,verifyTime);
        }
        // 先息后本
        else if (STYLE_ENDMONTH.equals(borrowStyle)) {
            info = getEndMonth(account, borrowPeriod, yearRate, time, monthRate,return_rate,verifyTime);
        }

        return info;
    }

    /**
     * 计算等额本息
     *
     * @param account
     *            本金
     * @param period
     *            还款期数
     * @param apr
     *            年利率
     * @param style
     *            还款类型
     * @param time
     *            本期还款时间
     * @param monthRate
     *            月利率
     * @param monthRateEnd
     *            月利率(下限)
     * @param projectType
     *            项目类型
     * @param return_rate 
     * @param man_charge_rate 
     * @return
     */
    public static InterestInfo getMonth(BigDecimal account, Integer borrowPeriod, BigDecimal apr, Integer time, BigDecimal monthRate, BigDecimal monthRateEnd, Integer projectType, BigDecimal return_rate,int verifyTime) {
        // 空判断
        if (Validator.isNull(account) || Validator.isNull(borrowPeriod) || Validator.isNull(apr)) {
            return null;
        }
        if (Validator.isNull(time)) {
            time = GetDate.getMyTimeInMillis();
        }

        InterestInfo info = new InterestInfo();

        // 应还款总额（本息总额）
        BigDecimal repayAccount = BigDecimal.ZERO;
        // 还款利息（利息）
        BigDecimal repayAccountInterest = BigDecimal.ZERO;
        // 还款本金（本金）
        BigDecimal repayAccountCapital = BigDecimal.ZERO;
        // 管理费
        BigDecimal feeTotal = BigDecimal.ZERO;

        // 每月还款本金
        Map<Integer, BigDecimal> monthlyCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(account, apr, borrowPeriod);
        // 每月还款利息
        Map<Integer, BigDecimal> monthlyInterest = AverageCapitalPlusInterestUtils.getPerMonthInterest(account, apr, borrowPeriod);
        // 每月还款本息
        BigDecimal monthlyAccount = AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(account, apr, borrowPeriod);

        BigDecimal monthlyInterestStart = null;
        BigDecimal monthlyInterestEnd = null;
        // 汇消费时
        if (PROJECT_TYPE_HXF == projectType) {
        	//TODO
        	
            monthlyInterestStart = AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(account, monthRate, borrowPeriod);
            monthlyInterestEnd = AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(account, monthRateEnd, borrowPeriod);
        }
        // 设置月还款列表
        List<InterestInfo> listMonthly = null;
        if (monthlyCapital != null && monthlyInterest != null && monthlyAccount != null && monthlyCapital.size() == monthlyInterest.size()) {
            listMonthly = new ArrayList<InterestInfo>();
            InterestInfo monthly = null;
            for (Entry<Integer, BigDecimal> entry : monthlyCapital.entrySet()) {
                monthly = new InterestInfo();
                // 期数
                monthly.setMontyNo(entry.getKey());
                // 本息总额
                monthly.setRepayAccount(monthlyAccount);
                // 利息
                monthly.setRepayAccountInterest(monthlyInterest.get(entry.getKey()));
                // 本金
                monthly.setRepayAccountCapital(entry.getValue());
                // 还款时间
                monthly.setRepayTime(DateUtils.getRepayNextDate(STYLE_MONTH, GetDate.getDate(time * 1000L), entry.getKey(), 0));
                // 管理费
                if (PROJECT_TYPE_HXF == projectType && monthlyInterestStart != null && monthlyInterestEnd != null) { // 汇消费(最大费率-最小费率)
                    monthly.setFee(monthlyInterestStart.subtract(monthlyInterestEnd));
                } else {
                	if (entry.getKey()==monthlyCapital.size()) {
                		monthly.setFee(AccountManagementFeeUtils.getMonthAccountManagementFee(entry.getValue(), monthRate, entry.getKey(),return_rate,1,account,borrowPeriod,verifyTime));
	                }else {
	                    monthly.setFee(AccountManagementFeeUtils.getMonthAccountManagementFee(entry.getValue(), monthRate, entry.getKey(),return_rate,0,account,borrowPeriod,verifyTime));
	                }
                }
                listMonthly.add(monthly);

                // 应还款总额（本息总额）
                repayAccount = repayAccount.add(monthly.getRepayAccount());
                // 应还款利息（利息总额）
                repayAccountInterest = repayAccountInterest.add(monthly.getRepayAccountInterest());
                // 应还款本金（本金总额）
                repayAccountCapital = repayAccountCapital.add(monthly.getRepayAccountCapital());
                // 管理费总额
                feeTotal = feeTotal.add(monthly.getFee());
            }
        }

        // 还款总额
        info.setRepayAccount(repayAccount);
        // 还款利息
        info.setRepayAccountInterest(repayAccountInterest);
        // 还款本金
        info.setRepayAccountCapital(repayAccountCapital);
        // 还款时间
        info.setRepayTime(DateUtils.getRepayNextDate(STYLE_MONTH, GetDate.getDate(time * 1000L), 1, 0));
        // 管理费
        info.setFee(feeTotal);
        // 月还款列表
        info.setListMonthly(listMonthly);

        return info;
    }

    /**
     * 计算等额本金
     *
     * @param account
     *            本金
     * @param period
     *            还款期数
     * @param apr
     *            年利率
     * @param style
     *            还款类型
     * @param time
     *            本期还款时间
     * @param monthRate
     *            月利率
     * @param return_rate 
     * @param man_charge_rate 
     * @param verifyTime 
     * @return
     */
    public static InterestInfo getPrincipal(BigDecimal account, Integer borrowPeriod, BigDecimal apr, Integer time, BigDecimal monthRate, BigDecimal return_rate, int verifyTime) {
        // 空判断
        if (Validator.isNull(account) || Validator.isNull(borrowPeriod) || Validator.isNull(apr)) {
            return null;
        }
        if (Validator.isNull(time)) {
            time = GetDate.getMyTimeInMillis();
        }

        // 返回值
        InterestInfo info = new InterestInfo();

        // 应还款总额（本息总额）
        BigDecimal repayAccount = BigDecimal.ZERO;
        // 还款利息（利息）
        BigDecimal repayAccountInterest = BigDecimal.ZERO;
        // 还款本金（本金）
        BigDecimal repayAccountCapital = BigDecimal.ZERO;
        // 管理费
        BigDecimal feeTotal = BigDecimal.ZERO;

        // 设置月还款列表
        List<InterestInfo> listMonthly = null;
        InterestInfo monthly = null;
        // 每月还款本金
        BigDecimal monthlyCapital = AverageCapitalUtils.getPerMonthPrincipal(account, borrowPeriod);
        // 每月还款利息
        Map<Integer, BigDecimal> monthlyInterest = AverageCapitalUtils.getPerMonthInterest(account, apr, borrowPeriod);
        if (Validator.isNotNull(monthlyInterest)) {
            listMonthly = new ArrayList<InterestInfo>();
            for (Entry<Integer, BigDecimal> entry : monthlyInterest.entrySet()) {
                monthly = new InterestInfo();
                // 期数
                monthly.setMontyNo(entry.getKey());
                
                // 还款时间
                monthly.setRepayTime(DateUtils.getRepayNextDate(STYLE_PRINCIPAL, GetDate.getDate(time * 1000L), entry.getKey(), 0));
                // 管理费
               // monthly.setFee(AccountManagementFeeUtils.getMonthAccountManagementFee(monthlyCapital, monthRate, entry.getKey()));
                if (monthlyInterest.size()==entry.getKey()) {
                	monthlyCapital = account.subtract(monthlyCapital.multiply(new BigDecimal(entry.getKey()).subtract(new BigDecimal(1))));
                	// 本息总额
                    monthly.setRepayAccount(monthlyCapital.add(entry.getValue()));
                    // 利息
                    monthly.setRepayAccountInterest(entry.getValue());
                    // 本金
                    monthly.setRepayAccountCapital(monthlyCapital);
                    //管理费
                    monthly.setFee(AccountManagementFeeUtils.getMonthAccountManagementFee(monthlyCapital, monthRate , entry.getKey(),return_rate,1,account,borrowPeriod,verifyTime));
                }else { 
                	// 本息总额
                    monthly.setRepayAccount(monthlyCapital.add(entry.getValue()));
                    // 利息
                    monthly.setRepayAccountInterest(entry.getValue());
                    // 本金
                    monthly.setRepayAccountCapital(monthlyCapital);
                  //管理费
                    monthly.setFee(AccountManagementFeeUtils.getMonthAccountManagementFee(monthlyCapital, monthRate , entry.getKey(),return_rate,0,account,borrowPeriod,verifyTime));
                }
               
                listMonthly.add(monthly);

                // 应还款总额（本息总额）
                repayAccount = repayAccount.add(monthly.getRepayAccount());
                // 应还款利息（利息总额）
                repayAccountInterest = repayAccountInterest.add(monthly.getRepayAccountInterest());
                // 应还款本金（本金总额）
                repayAccountCapital = repayAccountCapital.add(monthly.getRepayAccountCapital());
                // 管理费总额
                feeTotal = feeTotal.add(monthly.getFee());
            }
        }

        // 还款总额
        info.setRepayAccount(repayAccount);
        // 还款利息
        info.setRepayAccountInterest(repayAccountInterest);
        // 还款本金
        info.setRepayAccountCapital(repayAccountCapital);
        // 还款时间
        info.setRepayTime(DateUtils.getRepayNextDate(STYLE_PRINCIPAL, GetDate.getDate(time * 1000L), 1, 0));
        // 管理费
        info.setFee(feeTotal);
        // 月还款列表
        info.setListMonthly(listMonthly);

        return info;
    }

    /**
     * 按月计息，到期还本还息
     *
     * @param account
     *            本金
     * @param period
     *            还款期数
     * @param apr
     *            年利率
     * @param style
     *            还款类型
     * @param time
     *            本期还款时间
     * @param monthRate
     *            服务费率
     * @param return_rate 
     * @param man_charge_rate 
     * @param verifyTime 
     * @return
     */
    public static InterestInfo getEnd(BigDecimal account, Integer borrowPeriod, BigDecimal apr, Integer time, BigDecimal monthRate, BigDecimal return_rate, int verifyTime) {
        // 空判断
        if (Validator.isNull(account) || Validator.isNull(borrowPeriod) || Validator.isNull(apr)) {
            return null;
        }
        if (Validator.isNull(time)) {
            time = GetDate.getMyTimeInMillis();
        }

        InterestInfo info = new InterestInfo();

        // 应还款总额（本息总额）
        BigDecimal repayAccount = DuePrincipalAndInterestUtils.getMonthPrincipalInterest(account, apr, borrowPeriod);
        // 还款利息（利息）
        BigDecimal repayAccountInterest = DuePrincipalAndInterestUtils.getMonthInterest(account, apr, borrowPeriod);
        // 还款本金（本金）
        BigDecimal repayAccountCapital = account;

        // 还款总额
        info.setRepayAccount(repayAccount);
        // 还款利息
        info.setRepayAccountInterest(repayAccountInterest);
        // 还款本金
        info.setRepayAccountCapital(repayAccountCapital);
        // 还款时间
        info.setRepayTime(DateUtils.getRepayNextDate(STYLE_END, GetDate.getDate(time * 1000L), borrowPeriod, borrowPeriod));
        // 管理费
       // info.setFee(AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(repayAccountCapital, monthRate, borrowPeriod));
        info.setFee(AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(repayAccountCapital, monthRate, borrowPeriod,return_rate,verifyTime));
        return info;
    }

    /**
     * 按天计息，到期还本息
     *
     * @param account
     *            本金
     * @param period
     *            还款期数
     * @param apr
     *            年利率
     * @param style
     *            还款类型
     * @param time
     *            本期还款时间
     * @param monthRate
     *            月利率
     * @param return_rate 
     * @param man_charge_rate 
     * @param verifyTime 
     * @return
     */
    public static InterestInfo getEndDay(BigDecimal account, Integer borrowPeriod, BigDecimal apr, Integer time, BigDecimal monthRate, BigDecimal return_rate, int verifyTime) {
        // 空判断
        if (Validator.isNull(account) || Validator.isNull(borrowPeriod) || Validator.isNull(apr)) {
            return null;
        }
        if (Validator.isNull(time)) {
            time = GetDate.getMyTimeInMillis();
        }

        InterestInfo info = new InterestInfo();

        // 应还款总额（本息总额）
        BigDecimal repayAccount = DuePrincipalAndInterestUtils.getDayPrincipalInterest(account, apr, borrowPeriod);
        // 还款利息（利息）
        BigDecimal repayAccountInterest = DuePrincipalAndInterestUtils.getDayInterest(account, apr, borrowPeriod);
        // 还款本金（本金）
        BigDecimal repayAccountCapital = account;

        // 还款总额
        info.setRepayAccount(repayAccount);
        // 还款利息
        info.setRepayAccountInterest(repayAccountInterest);
        // 还款本金
        info.setRepayAccountCapital(repayAccountCapital);
        // 还款时间
        info.setRepayTime(DateUtils.getRepayNextDate(STYLE_ENDDAY, GetDate.getDate(time * 1000L), borrowPeriod, borrowPeriod));
        // 管理费
        //info.setFee(AccountManagementFeeUtils.getDueAccountManagementFeeByDay(repayAccountCapital, monthRate, borrowPeriod));
        info.setFee(AccountManagementFeeUtils.getDueAccountManagementFeeByDay(repayAccountCapital,monthRate, borrowPeriod, return_rate,verifyTime));

        return info;
    }

    /**
     * 先息后本
     *
     * @param account
     *            本金
     * @param period
     *            还款期数
     * @param apr
     *            年利率
     * @param style
     *            还款类型
     * @param time
     *            本期还款时间
     * @param monthRate
     *            月利率
     * @param return_rate 
     * @param man_charge_rate 
     * @param verifyTime 
     * @return
     */
    public static InterestInfo getEndMonth(BigDecimal account, Integer borrowPeriod, BigDecimal apr, Integer time, BigDecimal monthRate, BigDecimal return_rate, int verifyTime) {
        // 空判断
        if (Validator.isNull(account) || Validator.isNull(borrowPeriod) || Validator.isNull(apr)) {
            return null;
        }
        if (Validator.isNull(time)) {
            time = GetDate.getMyTimeInMillis();
        }

        InterestInfo info = new InterestInfo();

        // 应还款总额（本息总额）
        BigDecimal repayAccount = BigDecimal.ZERO;// BeforeInterestAfterPrincipalUtils.getPrincipalInterestCount(account,
                                                  // apr, borrowPeriod,
                                                  // borrowPeriod);
        // 还款利息（利息）
        BigDecimal repayAccountInterest = BigDecimal.ZERO; // BeforeInterestAfterPrincipalUtils.getInterestCount(account,
                                                           // apr, borrowPeriod,
                                                           // borrowPeriod);
        // 还款本金（本金）
        BigDecimal repayAccountCapital = BigDecimal.ZERO;
        // 每月账户管理费
        BigDecimal feeTotal = BigDecimal.ZERO;

        // 每月还款本金
        BigDecimal monthlyCapital = account;
        // 每月还款利息
        BigDecimal monthlyInterest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(account, apr, borrowPeriod, borrowPeriod);
        // 每月还款本息
        BigDecimal monthlyAccount = monthlyCapital.add(monthlyInterest);
        // 设置月还款列表
        List<InterestInfo> listMonthly = null;
        listMonthly = new ArrayList<InterestInfo>();
        InterestInfo monthly = null;
        for (int i = 1; i <= borrowPeriod; i++) {
            monthly = new InterestInfo();
            // 期数
            monthly.setMontyNo(i);
            if (i == borrowPeriod) {
                // 本息总额
                monthly.setRepayAccount(monthlyAccount);
                // 利息
                monthly.setRepayAccountInterest(monthlyInterest);
                // 本金
                monthly.setRepayAccountCapital(monthlyCapital);
                //新管理费
                monthly.setFee(AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(account, monthRate, borrowPeriod, borrowPeriod,return_rate,1,verifyTime));
            } else {
                // 本息总额
                monthly.setRepayAccount(monthlyInterest);
                // 利息
                monthly.setRepayAccountInterest(monthlyInterest);
                // 本金
                monthly.setRepayAccountCapital(BigDecimal.ZERO);
                monthly.setFee(AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(account, monthRate, borrowPeriod, borrowPeriod, return_rate,0,verifyTime));
            }
            // 还款时间
            monthly.setRepayTime(DateUtils.getRepayNextDate(STYLE_ENDMONTH, GetDate.getDate(time * 1000L), i, 0));
            // 管理费
            // monthly.setFee(AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(account, monthRate, borrowPeriod, borrowPeriod));
             listMonthly.add(monthly);

            // 应还款总额（本息总额）
            repayAccount = repayAccount.add(monthly.getRepayAccount());
            // 应还款利息（利息总额）
            repayAccountInterest = repayAccountInterest.add(monthly.getRepayAccountInterest());
            // 应还款本金（本金总额）
            repayAccountCapital = repayAccountCapital.add(monthly.getRepayAccountCapital());
            // 管理费总额
            feeTotal = feeTotal.add(monthly.getFee());
        }

        // 还款总额
        info.setRepayAccount(repayAccount);
        // 还款利息
        info.setRepayAccountInterest(repayAccountInterest);
        // 还款本金
        info.setRepayAccountCapital(repayAccountCapital);
        // 还款时间
        info.setRepayTime(DateUtils.getRepayNextDate(STYLE_ENDMONTH, GetDate.getDate(time * 1000L), 1, 0));
        // 管理费
        info.setFee(feeTotal);
        // 月还款列表
        info.setListMonthly(listMonthly);

        return info;
    }

    public static void main(String[] args) {
        BigDecimal account = new BigDecimal(466000.00);
        Integer borrowPeriod = 12;
        BigDecimal apr = new BigDecimal(15);
        String borrowStyle = STYLE_MONTH;
        Integer time = (int) (GetDate.str2Timestamp("2015-12-30").getTime() / 1000L);
        BigDecimal monthRate = new BigDecimal(0.195);
        BigDecimal monthRateEnd = new BigDecimal(0.15);
        Integer projectType = 8;
        InterestInfo info = getInterestInfo(account, borrowPeriod, apr, borrowStyle, time, monthRate, monthRateEnd, projectType,new BigDecimal("0.002"),1463134474);
        System.out.println("还款总额:" + info.getRepayAccount());
        System.out.println("还款利息:" + info.getRepayAccountInterest());
        System.out.println("还款本金:" + info.getRepayAccountCapital());
        System.out.println("还款时间:" + info.getRepayTime() + "   " + GetDate.formatDate(GetDate.getDate(info.getRepayTime() * 1000L)));
        System.out.println("管理费:" + info.getFee());
        if (info.getListMonthly() != null) {
            int i = 0;
            for (InterestInfo sub : info.getListMonthly()) {
                System.out.println("--------------第" + ++i + "期-----------------");
                System.out.println("还款总额:" + sub.getRepayAccount());
                System.out.println("还款利息:" + sub.getRepayAccountInterest());
                System.out.println("还款本金:" + sub.getRepayAccountCapital());
                System.out.println("还款时间:" + sub.getRepayTime() + "   " + GetDate.formatDate(GetDate.getDate(sub.getRepayTime() * 1000L)));
                System.out.println("管理费:" + sub.getFee());
            }
        }
    }

}
