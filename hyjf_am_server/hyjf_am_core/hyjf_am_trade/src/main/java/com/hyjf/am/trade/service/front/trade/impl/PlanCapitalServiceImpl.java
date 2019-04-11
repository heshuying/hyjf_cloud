package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.HjhAccedeCustomize;
import com.hyjf.am.trade.service.front.trade.PlanCapitalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.utils.TransUtil;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlanCapitalServiceImpl extends BaseServiceImpl implements PlanCapitalService {

    private static final Logger logger = LoggerFactory.getLogger(PlanCapitalServiceImpl.class);

    private static final String LOG_MAIN_INFO = "【资金计划3.3.0】";

    /**
     * 获取该日期的实际债转和复投金额
     *
     * @param date
     * @return
     */
    @Override
    public List<HjhPlanCapitalVO> getPlanCapitalForActList(Date date) {
        List<HjhPlanCapitalVO> list = this.hjhPlanCapitalCustomizeMapper.selectPlanCapitalForActList(date);
        return list;
    }

    /**
     * 获取该期间的预估债转和复投金额
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public List<HjhPlanCapitalVO> getPlanCapitalForProformaList(Date fromDate, Date toDate) {
        List<HjhPlanCapitalVO> list = this.hjhPlanCapitalCustomizeMapper.selectPlanCapitalForProformaList(fromDate, toDate);
        return list;
    }

    /**
     * 获取该期间的预计当日新增复投额
     *
     * @param date
     * @return
     */
    @Override
    public List<HjhPlanCapitalPredictionVO> getPlanCapitalPredictionForProformaList(Date date) {
        List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionVO = new ArrayList<HjhPlanCapitalPredictionVO>();
        logger.info("获取该期间的预计当日新增复投额开始,date:【" + date + "】");
        // 检索还款日为T日  当前有效的债权（HJH_DEBT_DETAL） 有效条件（status=1，repayStatus=0，delFlag=0）
        // 日期不为空才做处理
        if (date != null) {
            // 预估时间
            String dualDateStr = GetDate.date2Str(date, new SimpleDateFormat("yyyy-MM-dd"));
            // 预估时间+3天
            String dualDateAddThreeStr = GetDate.date2Str(TransUtil.datesAdd(date, 3), new SimpleDateFormat("yyyy-MM-dd"));
            // 1.检索对应日期范围List的有效债权数据（2.还款金额+计划订单 <10 元的不计算在当日的新增复投额之内（未收本金+未收利息））
            List<HjhDebtDetail> hjhdebtdetal = hjhDebtDetailCustomizeMapper.selectDebtDetailToDate(dualDateStr);
            // 2.根据投资人的用户名检索相应的 “计划订单” （1.计划订单“开始发起退出的时间”小于T，大于T+3日的订单。）
            for (HjhDebtDetail detal : hjhdebtdetal) {
                BigDecimal account = new BigDecimal(String.valueOf(BigDecimal.ZERO));
                // 根据不同的planNid去放入hjhPlanCapitalPredictionVO中，根据条件查询 accede 表（1.计划订单“开始发起退出的时间”小于T，大于T+3日的订单。之外的日期不进行累加）
                Map<String, Object> paramMap = new HashMap<>();
                // 投资人用户编号
                paramMap.put("userId", detal.getUserId());
                // 计划订单号
                paramMap.put("planOrderId", detal.getPlanOrderId());
                // 查询汇计划加入明细表
                List<HjhAccedeCustomize> accede = batchHjhAccedeCustomizeMapper.selectHjhAccedeList(paramMap);
                if (accede.size() != 1) {
                    logger.info("查询汇计划加入明细表时数据状态为锁定中或未查到数据！“accede.size() != 1”:" + "planOrderId:【" + detal.getPlanOrderId() + "】" + "userId:【" + detal.getUserId() + "】");
                    continue;
                }
                // 退出时间（时间戳）
                int quiteTime = accede.get(0).getQuitTime();
                // 開始時間戳（預估日期+3天）
                // int dualDateAddThreeStrStart = GetDate.getDayStart10(dualDateAddThreeStr);
                // 結束時間戳（預估日期+3天）
                int dualDateAddThreeStrend = GetDate.getDayEnd10(dualDateAddThreeStr);
                // 開始時間戳（預估日期）
                int dualDateStrStart = GetDate.getDayStart10(dualDateStr);
                // 結束時間戳（預估日期）
                // int dualDateStrend = GetDate.getDayEnd10(dualDateStr);
                // 1.计划订单“开始发起退出的时间”小于T，大于T+3日的订单。
                if (!(quiteTime < dualDateStrStart && quiteTime > dualDateAddThreeStrend)) {
                    continue;
                } else {
                    boolean flagAddVo = true;
                    // detal.getRepayCapitalWait(); // 未收本金 +  detal.getRepayInterestWait(); // 未收利息 = 当前计算k
                    account = account.add(detal.getRepayCapitalWait()).add(detal.getRepayInterestWait());
                    // 判断vo中是否已存在相同的PlaNid计划（相同金额累加，不相同新增一条vo）
                    for (HjhPlanCapitalPredictionVO voStr : hjhPlanCapitalPredictionVO) {
                        // 已经存在PlanNid
                        if (detal.getPlanNid().equals(voStr.getPlanNid())) {
                            // 再根据“计划订单”对应的‘计划编号’进行区分，将标的‘当前持有人的还款额’进行（累加）
                            BigDecimal reinvestAccount = BigDecimal.ZERO;
                            // 累加预计当日新增复投额
                            reinvestAccount = reinvestAccount.add(voStr.getReinvestAccount()).add(account);
                            voStr.setReinvestAccount(reinvestAccount);
                            // 不新增新list數據
                            flagAddVo = false;
                        }
                    }
                    // 如果list中不存在則新增計劃
                    if (flagAddVo) {
                        // 新增对应PlanNid的计划行
                        HjhPlanCapitalPredictionVO hjhPlanCapitalPrediction = new HjhPlanCapitalPredictionVO();
                        hjhPlanCapitalPrediction.setDate(date);
                        // 智投编号
                        hjhPlanCapitalPrediction.setPlanNid(detal.getPlanNid());
                        // 智投名称
                        // hjhPlanCapitalPrediction.setPlanName("");
                        // 锁定期
                        // hjhPlanCapitalPrediction.setLockPeriod(0);
                        // 预计当日新增复投额
                        hjhPlanCapitalPrediction.setReinvestAccount(account);
                        hjhPlanCapitalPrediction.setCreateTime(new Date());
                        hjhPlanCapitalPrediction.setDelFlg(0);
                        hjhPlanCapitalPredictionVO.add(hjhPlanCapitalPrediction);
                    }
                }
            }
        } else {
            logger.info("开始结束日期为空,date:【" + date + "】");
        }
        logger.info("获取该期间的预计当日新增复投额结束,date:【" + date + "】");
        return hjhPlanCapitalPredictionVO;
    }

    /**
     * 获取该期间的预计当日新增债转额
     *
     * @param dualDate
     * @return
     */
    @Override
    public List<HjhPlanCapitalPredictionVO> getPlanCapitalForCreditList(Date dualDate) {
        try {
            List<HjhPlanCapitalPredictionVO> list = new ArrayList<HjhPlanCapitalPredictionVO>();
            logger.info(LOG_MAIN_INFO + "获取该期间的预计当日新增复投额开始,dualDate:【" + dualDate + "】");
            // 预计当日新增债转额公式：
            // 1、筛选（预计开始退出时间=T日）的订单；      ht_hjh_accede.end_date
            // 2、找出这些订单当前持有的债权，包括原始和债转
            // 3、关联这些债权所对应标的的应还日期
            // 4、计算值：
//		若标的应还时间<T-3日，则根据债权当前所在智投编号，累加还款额；（若是分期则累计所有未还款，且应还日期<T-3）
//		则计算这笔债权在T日的预计承接收益率和债权价值，再将T日的债权价值根据预计承接收益率累加至相应智投编号；
            // 检索到期的计划加入订单,用于清算 按智投编号排序
            List<HjhAccede> hjhAccedeList = hjhDeadLineAccedeList(dualDate);
            String tmpPlanNid = "";
            BigDecimal tmpCreditAccount = BigDecimal.ZERO;
            // 循环到期的计划加入订单
            for (int i = 0; i < hjhAccedeList.size(); i++) {
                HjhAccede hjhAccede = hjhAccedeList.get(i);
                //  清算出的债转信息
                Integer nowTime = (int) GetDate.getMillis10(dualDate);
                // 计划加入订单号
                String accedeOrderId = hjhAccede.getAccedeOrderId();
                logger.info(LOG_MAIN_INFO + "预计新增债转额开始:计划加入订单号:[" + accedeOrderId + "].");
                // 根据加入订单号,日期进行清算
                BigDecimal totalFairValue = this.getLiquidation(dualDate, accedeOrderId, nowTime, hjhAccede.getCreditCompleteFlag());
                logger.info(LOG_MAIN_INFO + "智投订单：{}提前清算后的债权价值为：{}", accedeOrderId, totalFairValue);
                // 投资相同智投编号的计划订单债权价值放到一起
                if (StringUtils.isBlank(tmpPlanNid) || tmpPlanNid.equals(hjhAccede.getPlanNid())) {
                    // 当前处理数据于前一条数据planNid相同时累加债权价值
                    tmpCreditAccount.add(totalFairValue);
                    // 当前智投编号记录到中间值
                    tmpPlanNid = hjhAccede.getPlanNid();
                } else {
                    // 上一条数据与本条数据不是同一个planNid时将上一条智投编号累加的金额计入list
                    HjhPlanCapitalPredictionVO vo = new HjhPlanCapitalPredictionVO();
                    vo.setDate(dualDate);
                    vo.setPlanNid(tmpPlanNid);
                    vo.setCreditAccount(tmpCreditAccount);
                    list.add(vo);
                    // 中间值计入返回结果后初始化中间值
                    tmpCreditAccount = totalFairValue;
                    tmpPlanNid = hjhAccede.getPlanNid();
                }
                if (i == hjhAccedeList.size() - 1) {
                    // 最后一条记录处理完后计入list
                    HjhPlanCapitalPredictionVO vo = new HjhPlanCapitalPredictionVO();
                    vo.setDate(dualDate);
                    vo.setPlanNid(tmpPlanNid);
                    vo.setCreditAccount(tmpCreditAccount);
                    list.add(vo);
                }
                logger.info(LOG_MAIN_INFO + "预计新增债转额:计划加入订单号:[" + accedeOrderId + "].");
            }
            logger.info(LOG_MAIN_INFO + "获取该期间的预计当日新增复投额结束,dualDate:【" + dualDate + "】");
            return list;
        } catch (Exception e) {
            logger.error(LOG_MAIN_INFO + e.getMessage());
            return null;
        }
    }

    /**
     * 获取T日预计退出的计划订单
     *
     * @param dualDate
     * @return
     */
    private List<HjhAccede> hjhDeadLineAccedeList(Date dualDate) {
        HjhAccedeExample example = new HjhAccedeExample();
        // 订单结束日期为T天的计划订单
        example.createCriteria().andEndDateIsNotNull().andEndDateEqualTo(dualDate);
        example.setOrderByClause(" plan_nid asc");
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        return list;
    }

    /**
     * 计算公允价值
     *
     * @param accedeOrderId
     * @param liquidationShouldTime
     * @param creditCompleteFlag
     * @return
     * @throws Exception
     */
    private BigDecimal getLiquidation(Date dualDate, String accedeOrderId, Integer liquidationShouldTime, Integer creditCompleteFlag) throws Exception {
        String dualDateStr = GetDate.date2Str(dualDate, new SimpleDateFormat("yyyy-MM-dd"));
        // 是否有正在还款的标的标志
        int isRepayFlag = 0;
        // 加入订单的总的债权价值
        BigDecimal totalFairValue = BigDecimal.ZERO;
        // 根据计划加入订单号查询当前持有有效债权
        List<HjhDebtDetail> debtDetails = this.hjhDebtDetailCustomizeMapper.selectDebtDetailForLiquidation(accedeOrderId);
        // 如果没有当前有效债权,只更新加入订单的状态
        if (debtDetails != null && debtDetails.size() > 0) {
            //  债权价值
            BigDecimal creditValue = BigDecimal.ZERO;
            // 提前还款债权价值减扣部分
            BigDecimal advanceCreditValue = BigDecimal.ZERO;
            // 持有天数
            int holdDays = 0;
            // 剩余天数
            int remainDays = 0;
            // 当前期计息天数
            int duringDays = 0;
            // 循环有效债权信息
            for (HjhDebtDetail hjhDebtDetail : debtDetails) {
                logger.info(LOG_MAIN_INFO + "清算计划编号:[" + hjhDebtDetail.getPlanNid() + "],计划加入订单号:" + hjhDebtDetail.getPlanOrderId() + "],出借订单号或承接订单号:[" + hjhDebtDetail.getOrderId() + "].");
                // 债权原标编号
                String borrowNid = hjhDebtDetail.getBorrowNid();
                // 根据标的号查询项目详情
                Borrow borrow = this.getBorrowByNid(borrowNid);
                if (borrow == null) {
                    throw new RuntimeException(LOG_MAIN_INFO + "根据标的编号查询标的详情失败,标的编号:[" + borrowNid + "].");
                }
                // 获取标的信息
                BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
                if (borrowInfo == null) {
                    throw new RuntimeException(LOG_MAIN_INFO + "根据标的编号查询标的信息失败,标的编号:[" + borrowNid + "].");
                }

                // 还款方式
                String borrowStyle = borrow.getBorrowStyle();

                // 是否月标(true:月标, false:天标)
                boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

                // 根据标的查询是否有正在进行还款
//                List<BorrowApicron> repayApicronList = this.selectRepayApicronList(borrowNid);
//                if (repayApicronList != null && repayApicronList.size() > 0) {
//                    logger.info("标的正在还款中,稍后清算,标的编号:[" + borrowNid + "]");
//                    isRepayFlag = 1;
//                    continue;
//                }
//
//                // 如果还款日为T日期,不进行清算
//                if (isMonth) {
//                    // 如果是分期标的,查询还款计划
//                    List<BorrowRepayPlan> borrowRepayPlanList = this.selectBorrowRepayPlanList(borrowNid, dualDate);
//                    if (borrowRepayPlanList != null && borrowRepayPlanList.size() > 0) {
//                        logger.info(LOG_MAIN_INFO + "分期标的.应还日期在当天的债权,暂时不进行清算,标的编号:[" + borrowNid + "]");
//                        isRepayFlag = 1;
//                        continue;
//                    }
//                } else {
//                    // 不分期标的
//                    List<BorrowRepay> borrowRepayList = this.selectBorrowRepayList(borrowNid, dualDate);
//                    if (borrowRepayList != null && borrowRepayList.size() > 0) {
//                        logger.info(LOG_MAIN_INFO + "不分期标的,应还日期在当天的债权,暂时不进行清算,标的编号:[" + borrowNid + "].");
//                        isRepayFlag = 1;
//                        continue;
//                    }
//                }

                // 查询债权是否已被清算 债转状态为 0 ,1
                HjhDebtCredit hjhDebtCreditYes = this.selectHjhDebtCreditYes(hjhDebtDetail.getBorrowNid(), hjhDebtDetail.getOrderId());
                if (hjhDebtCreditYes != null) {
                    // 如果债权已被转让出去,就不进行转让,跳出此次循环
                    logger.info(LOG_MAIN_INFO + "债权已被清算出,债权出借订单号:[" + hjhDebtDetail.getOrderId() + "],项目编号:[" + hjhDebtDetail.getBorrowNid() + "].");
                    continue;
                }
                // 查询完全承接的债权
                HjhDebtCredit assignComplete = this.selectHjhDebtCreditAssignComplete(hjhDebtDetail.getBorrowNid(), hjhDebtDetail.getOrderId());
                // 如果上次清算债转编号不为空
                if (creditCompleteFlag == 2 && assignComplete != null) {
                    // 如果债权已被转让出去,就不进行转让,跳出此次循环
                    // 一笔加入订单 既有完全承接 又有正在还款的还款的债权,此时 不清算
                    logger.info(LOG_MAIN_INFO + "债权已被清算出,债权出借订单号:[" + hjhDebtDetail.getInvestOrderId() + "],项目编号:[" + hjhDebtDetail.getBorrowNid() + "].");
                    continue;
                }
//                if (StringUtils.isNotEmpty(hjhDebtDetail.getCreditNid())) {
//                    // 根据债转编号查询债权出让信息
//                    HjhDebtCredit hjhDebtCredit = this.getHjhDebtCreditByCreditNid(hjhDebtDetail.getCreditNid());
//                    if (hjhDebtCredit != null && hjhDebtCredit.getDelFlag() == 0 && hjhDebtCredit.getCreditStatus() == 2) {
//                        // 将hyjf_hjh_debt_credit 表中的数据置为无效
//                        hjhDebtCredit.setDelFlag(1);
//                        this.hjhDebtCreditMapper.updateByPrimaryKey(hjhDebtCredit);
//                    }
//                }
//                // 根据原始出借订单号查询
//                BorrowRecover borrowRecover = this.getBrorrowRecoverByInvestOrderId(hjhDebtDetail.getInvestOrderId());
//                if (borrowRecover != null) {
//                    borrowRecover.setCreditStatus(1);
//                    borrowRecover.setCreditTime(liquidationShouldTime);
//                    this.borrowRecoverMapper.updateByPrimaryKey(borrowRecover);
//                }
//                // 新的债权信息
//                HjhDebtCredit hjhDebtCredit = new HjhDebtCredit();
//                // 出让人用户ID
//                hjhDebtCredit.setUserId(hjhDebtDetail.getUserId());
//                // 出让人用户名
//                hjhDebtCredit.setUserName(hjhDebtDetail.getUserName());
//                // 出让人加入计划编号
//                hjhDebtCredit.setPlanNid(hjhDebtDetail.getPlanNid());
//                // 出让人的计划加入订单号
//                hjhDebtCredit.setPlanOrderId(hjhDebtDetail.getPlanOrderId());
//                // 项目编号
//                hjhDebtCredit.setBorrowNid(hjhDebtDetail.getBorrowNid());
//                // 项目名称
//                hjhDebtCredit.setBorrowName(hjhDebtDetail.getBorrowName());
//                // 原标项目利率
//                hjhDebtCredit.setBorrowApr(hjhDebtDetail.getBorrowApr());
//                // 项目类型
//                hjhDebtCredit.setProjectType(borrowInfo.getProjectType());
//                // 原标还款方式
//                hjhDebtCredit.setBorrowStyle(borrow.getBorrowStyle());
//                // 原标项目期限
//                hjhDebtCredit.setBorrowPeriod(borrow.getBorrowPeriod());
//                // 原标机构编号
//                hjhDebtCredit.setInstCode(borrowInfo.getInstCode());
//                // 原标资产编号
//                hjhDebtCredit.setAssetType(borrowInfo.getAssetType());
//                // 生成债转编号
//                String creditNid = GetOrderIdUtils.getOrderId0(hjhDebtDetail.getUserId());
//                // 债转编号
//                hjhDebtCredit.setCreditNid(creditNid);
//                // 债转状态
//                hjhDebtCredit.setCreditStatus(0);
//                // 还款状态
//                hjhDebtCredit.setRepayStatus(0);
//                // 是否清算
//                hjhDebtCredit.setIsLiquidates(0);
//                // 是否原始债权 0非原始 1原始
//                hjhDebtCredit.setSourceType(hjhDebtDetail.getSourceType());
                // 不分期项目
                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(hjhDebtDetail.getBorrowStyle())) {

                    // 查询当前所处的计息期数的债权信息
                    // 应还日期>= 当前日期 未还款的债权
                    // 日期需要修改
                    HjhDebtDetail hjhDebtDetailCur = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurRepayPeriodByDate(hjhDebtDetail.getOrderId(), dualDateStr);
                    // 如果取不到债权说明有逾期的债权
                    if (hjhDebtDetailCur != null) {
//                        // 出让人
//                        hjhDebtCredit.setSellOrderId(hjhDebtDetailCur.getOrderId());
//                        // 出借订单号
//                        hjhDebtCredit.setInvestOrderId(hjhDebtDetailCur.getInvestOrderId());
                        // 剩余未还本金
                        BigDecimal capital = hjhDebtDetailCur.getRepayCapitalWait();
                        // 待收利息
                        BigDecimal interest = hjhDebtDetailCur.getRepayInterestWait();
                        // 待收总额
                        BigDecimal total = capital.add(interest);
                        // 应还时间
                        Integer repayTime = hjhDebtDetailCur.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetailCur.getLoanTime();

                        // 当前期计息天数 =  放款日期到还款日 + 1 天
                        try {
                            duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        // 持有天数 =  放款日期 到 清算日 - 1 天
                        try {
                            holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime));
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }

                        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle())) {
                            // 按天计息,到期还本还息
                            remainDays = borrow.getBorrowPeriod() - holdDays;
                        } else {
                            // 按月计息,到期还本还息
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetail.getRepayTime())) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }

                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算债权价值 = 剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        // F=Ar+I+Ir/D*Dr
                        logger.info(LOG_MAIN_INFO + "当前期利息:[" + interest + "].");
                        logger.info(LOG_MAIN_INFO + "当前期持有天数:[" + holdDays + "].");
                        logger.info(LOG_MAIN_INFO + "当前期计息天数:[" + duringDays + "].");
                        creditValue = (interest.multiply(new BigDecimal(holdDays)).divide(new BigDecimal(duringDays), 8, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN));
                        logger.info(LOG_MAIN_INFO + "垫付利息:[" + creditValue + "].");
//                        // 承接所在期数
//                        hjhDebtCredit.setAssignPeriod(1);
//                        // 清算期数
//                        hjhDebtCredit.setLiquidatesPeriod(1);
//                        // 债转期数
//                        hjhDebtCredit.setCreditPeriod(1);
//                        // 已还期数
//                        hjhDebtCredit.setRepayPeriod(0);
//                        // 债转总额
//                        hjhDebtCredit.setCreditAccount(total);
//                        // 债转总本金
//                        hjhDebtCredit.setCreditCapital(capital);
//                        // 清算总本金
//                        hjhDebtCredit.setLiquidatesCapital(capital);
//                        // 债转总利息
//                        hjhDebtCredit.setCreditInterest(interest);
//                        // 待承接总金额
//                        hjhDebtCredit.setCreditAccountWait(total);
//                        // 待承接本金
//                        hjhDebtCredit.setCreditCapitalWait(capital);
//                        // 待承接利息
//                        hjhDebtCredit.setCreditInterestWait(interest);
//                        if (remainDays == 0) {
//                            // 债权的实际年化收益率
//                            hjhDebtCredit.setActualApr(hjhDebtDetail.getBorrowApr());
//                        } else {
//                            // 债权的实际年化收益率
//                            hjhDebtCredit.setActualApr((hjhDebtDetail.getRepayInterestWait().subtract(creditValue)).divide(hjhDebtDetail.getRepayCapitalWait().add(creditValue), 8, BigDecimal.ROUND_DOWN).divide(new BigDecimal(remainDays), 8, BigDecimal.ROUND_DOWN)
//                                    .multiply(new BigDecimal(360)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
//                        }
//                        // 垫付总利息
//                        hjhDebtCredit.setCreditInterestAdvance(creditValue);
//                        // 待承接垫付总利息
//                        hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                        //  剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        logger.info(LOG_MAIN_INFO + "剩余未还债权本金:[" + capital + "].");
                        logger.info(LOG_MAIN_INFO + "计划订单的债权价值 = 剩余未还债权本金 + 垫付利息,[" + totalFairValue + "].");
//                        hjhDebtCredit.setLiquidationFairValue(capital.add(creditValue));
//                        // 已还总额
//                        hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
//                        // 已还本金
//                        hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
//                        // 已还利息
//                        hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
//                        // 待还总额
//                        hjhDebtCredit.setRepayAccountWait(total);
//                        // 待还本金
//                        hjhDebtCredit.setRepayCapitalWait(capital);
//                        // 待还利息
//                        hjhDebtCredit.setRepayInterestWait(interest);
//                        // 债权持有时间
//                        hjhDebtCredit.setHoldDays(holdDays);
//                        // 还款剩余时间
//                        hjhDebtCredit.setRemainDays(remainDays);
//                        // 当前期计息天数
//                        hjhDebtCredit.setDuringDays(duringDays);
//                        // 债转剩余天数
//                        hjhDebtCredit.setCreditTerm(remainDays);
//                        // 设置还款时间
//                        hjhDebtCredit.setCreditRepayEndTime(hjhDebtDetail.getRepayTime());
//                        // 上次债转时间
//                        hjhDebtCredit.setCreditRepayLastTime(0);
                    } else {
                        // 应还日期 < 当前日期 未还款的债权 延期或逾期 (不分期项目延期或逾期)
//                        // 是否是逾期债权
//                        hjhDebtCredit.setIsLateCredit(1);
//                        // 出让人
//                        hjhDebtCredit.setSellOrderId(hjhDebtDetail.getOrderId());
//                        // 出借订单号
//                        hjhDebtCredit.setInvestOrderId(hjhDebtDetail.getInvestOrderId());
                        // 剩余未还本金
                        BigDecimal capital = hjhDebtDetail.getRepayCapitalWait();
                        // 待收利息
                        BigDecimal interest = hjhDebtDetail.getRepayInterestWait();
                        // 待收总额
                        BigDecimal total = capital.add(interest);
                        // 应还时间
                        Integer repayTime = hjhDebtDetail.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetail.getLoanTime();

                        try {
                            // 当前期计息天数  =  放款日期到还款日 + 1 天
                            duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }

                        try {
                            // 持有天数 = 放款日到还款日 + 1
                            holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        // 按天计息,到期还本还息
                        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle())) {
                            remainDays = borrow.getBorrowPeriod() - holdDays;
                        } else {
                            // 按月计息,到期还本还息
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetail.getRepayTime()));
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算债权价值 = 剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        // F=Ar+I+Ir/D*Dr
                        logger.info(LOG_MAIN_INFO + "当前期利息:[" + interest + "].");
                        logger.info(LOG_MAIN_INFO + "当前期持有天数:[" + holdDays + "].");
                        logger.info(LOG_MAIN_INFO + "当前期计息天数:[" + duringDays + "].");
                        creditValue = (interest.multiply(new BigDecimal(holdDays)).divide(new BigDecimal(duringDays), 8, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN));
                        logger.info(LOG_MAIN_INFO + "垫付利息:[" + creditValue + "].");
                        // 承接所在期数
//                        hjhDebtCredit.setAssignPeriod(1);
//                        // 清算期数
//                        hjhDebtCredit.setLiquidatesPeriod(1);
//                        // 债转期数
//                        hjhDebtCredit.setCreditPeriod(1);
//                        // 已还期数
//                        hjhDebtCredit.setRepayPeriod(0);
//                        // 债转总额
//                        hjhDebtCredit.setCreditAccount(total);
//                        // 债转总本金
//                        hjhDebtCredit.setCreditCapital(capital);
//                        // 清算总本金
//                        hjhDebtCredit.setLiquidatesCapital(capital);
//                        // 债转总利息
//                        hjhDebtCredit.setCreditInterest(interest);
//                        // 待承接总金额
//                        hjhDebtCredit.setCreditAccountWait(total);
//                        // 待承接本金
//                        hjhDebtCredit.setCreditCapitalWait(capital);
//                        // 待承接利息
//                        hjhDebtCredit.setCreditInterestWait(interest);
//                        if (remainDays == 0) {
//                            // 债权的实际年化收益率
//                            hjhDebtCredit.setActualApr(hjhDebtDetail.getBorrowApr());
//                        } else {
//                            // 债权的实际年化收益率
//                            hjhDebtCredit.setActualApr((hjhDebtDetail.getRepayInterestWait().subtract(creditValue)).divide(hjhDebtDetail.getRepayCapitalWait().add(creditValue), 8, BigDecimal.ROUND_DOWN).divide(new BigDecimal(remainDays), 8, BigDecimal.ROUND_DOWN)
//                                    .multiply(new BigDecimal(360)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
//                        }
//                        // 垫付总利息
//                        hjhDebtCredit.setCreditInterestAdvance(creditValue);
//                        // 待承接垫付总利息
//                        hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                        //  剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        logger.info(LOG_MAIN_INFO + "剩余未还债权本金:[" + capital + "].");
                        logger.info(LOG_MAIN_INFO + "计划订单的债权价值 = 剩余未还债权本金 + 垫付利息,[" + totalFairValue + "].");
//                        hjhDebtCredit.setLiquidationFairValue(fairValue);
//                        // 已还总额
//                        hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
//                        // 已还本金
//                        hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
//                        // 已还利息
//                        hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
//                        // 待还总额
//                        hjhDebtCredit.setRepayAccountWait(total);
//                        // 待还本金
//                        hjhDebtCredit.setRepayCapitalWait(capital);
//                        // 待还利息
//                        hjhDebtCredit.setRepayInterestWait(interest);
//                        // 债权持有时间
//                        hjhDebtCredit.setHoldDays(holdDays);
//                        // 还款剩余时间
//                        hjhDebtCredit.setRemainDays(remainDays);
//                        // 当前期计息天数
//                        hjhDebtCredit.setDuringDays(duringDays);
//
//                        hjhDebtCredit.setCreditTerm(remainDays);
//                        // 设置还款时间
//                        hjhDebtCredit.setCreditRepayEndTime(hjhDebtDetail.getRepayTime());
//                        // 上次债转时间
//                        hjhDebtCredit.setCreditRepayLastTime(0);
                    }
                } else {
                    // 分期项目
                    // 查询当前所处的计息期数的债权信息
                    // 应还日期 >= 当前日期 未还款的债权
                    HjhDebtDetail hjhDebtDetailCur = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurRepayPeriodByDate(hjhDebtDetail.getOrderId(), dualDateStr);
                    if (hjhDebtDetailCur != null) {
                        // 应还时间
                        Integer repayTime = hjhDebtDetailCur.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetailCur.getLoanTime();
//                        // 出让人
//                        hjhDebtCredit.setSellOrderId(hjhDebtDetailCur.getOrderId());
//                        // 出借订单号
//                        hjhDebtCredit.setInvestOrderId(hjhDebtDetailCur.getInvestOrderId());
//
//                        // 说明之前的分期还款正常完成
//                        hjhDebtCredit.setActualApr(hjhDebtDetailCur.getBorrowApr());
                        // 剩余天数 清算日到本期应还日
                        try {
                            remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetailCur.getRepayTime()));
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        // 如果是第一期尚未还款
                        if (hjhDebtDetailCur.getRepayPeriod() == 1) {
                            // 如果第一期尚未还款
//                            hjhDebtCredit.setCreditRepayLastTime(0);
                            Integer liquidationBeforeTime = liquidationShouldTime - 60 * 60 * 24;
                            // 持有时间是放款时间至清算日前一天
                            try {
                                holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(hjhDebtDetailCur.getLoanTime()), GetDate.timestamptoStrYYYYMMDD(liquidationBeforeTime)) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                            // 当前期计息天数
                            try {
                                duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        } else {
                            // 如果不是第一期
                            // 查询上一期还款的债权详情
                            BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(hjhDebtDetailCur.getBorrowNid(), hjhDebtDetailCur.getInvestOrderId(), hjhDebtDetailCur.getRepayPeriod() - 1);
                            // 上期还款日期
//                            hjhDebtCredit.setCreditRepayLastTime(Integer.valueOf(borrowRecoverPlan.getRecoverTime()));
                            // 清算日前一天
                            Integer liquidationBeforeTime = liquidationShouldTime - 60 * 60 * 24;
                            // 还款日后一天
                            Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                            // 持有期是上一期应还时间的后一天至当清算日前一天
                            try {
                                holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(liquidationBeforeTime)) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                            if (holdDays < 0) {
                                holdDays = 0;
                            }
                            // 当前期计息天数
                            try {
                                duringDays = GetDate.daysBetween(repayPreTime, repayTime) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        // 最后一期还款的信息
                        BorrowRepayPlan borrowRepayPlan = this.getLastPeriodBorrowRepayPlan(borrowNid, hjhDebtDetailCur.getBorrowPeriod());
                        if (borrowRepayPlan != null) {
                            // 剩余期限 当前日期 到 最后一期还款日
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(borrowRepayPlan.getRepayTime())) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 设置分期相关信息
//                        // 承接所在期数
//                        hjhDebtCredit.setAssignPeriod(hjhDebtDetailCur.getRepayPeriod());
//                        // 清算所在期数
//                        hjhDebtCredit.setLiquidatesPeriod(hjhDebtDetailCur.getRepayPeriod());
//                        // 下期还款时间
//                        hjhDebtCredit.setCreditRepayNextTime(hjhDebtDetailCur.getRepayTime());
                        // 计算待垫付的利息
                        logger.info(LOG_MAIN_INFO + "分期项目,当前期利息:[" + hjhDebtDetailCur.getRepayInterestWait() + "].");
                        creditValue = ((hjhDebtDetailCur.getRepayInterestWait().multiply(new BigDecimal(holdDays))).divide(new BigDecimal(duringDays), 2, BigDecimal.ROUND_DOWN));
                        // 检索是否有之前期有逾期
                        List<HjhDebtDetail> overdueDetailList = this.selectOverdueDetailList(hjhDebtDetailCur.getOrderId(), hjhDebtDetailCur.getRepayPeriod());
                        if (overdueDetailList != null && overdueDetailList.size() > 0) {
                            for (HjhDebtDetail overdueDetail : overdueDetailList) {
                                logger.info(LOG_MAIN_INFO + "之前期逾期利息:[" + overdueDetail.getRepayInterestWait() + "].");
                                creditValue = creditValue.add(overdueDetail.getRepayInterestWait());
                            }
//                            hjhDebtCredit.setIsLateCredit(1);
                        }

                        logger.info(LOG_MAIN_INFO + "分期项目,当前期持有天数:[" + holdDays + "].");
                        logger.info(LOG_MAIN_INFO + "分期项目,当前期计息天数:[" + duringDays + "].");
//                        // 垫付利息
//                        hjhDebtCredit.setCreditInterestAdvance(creditValue);
//                        // 待垫付利息
//                        hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                        BigDecimal capital = hjhDebtDetailCur.getRepayCapitalWait();
                        BigDecimal interest = hjhDebtDetailCur.getRepayInterestWait();
                        BigDecimal total = capital.add(interest);
                        // 计算真实的债权总额、本金、利息
                        // 未还款期数
                        Integer unRepayPeriod = 0;
                        // 如果当前期不是最后一期 则需要查询当前期到最后一期的债权信息
                        List<HjhDebtDetail> hjhDebtDetailsNoRepay = this.hjhDebtDetailCustomizeMapper.selectDebtDetailNoRepay(hjhDebtDetailCur.getOrderId());
                        capital = new BigDecimal(0);
                        interest = new BigDecimal(0);
                        total = new BigDecimal(0);
                        if (hjhDebtDetailsNoRepay != null && hjhDebtDetailsNoRepay.size() > 0) {
                            for (HjhDebtDetail debtDetailNoRepay : hjhDebtDetailsNoRepay) {
                                capital = capital.add(debtDetailNoRepay.getRepayCapitalWait());
                                interest = interest.add(debtDetailNoRepay.getRepayInterestWait());
                                total = capital.add(interest);
                                if (debtDetailNoRepay.getRepayPeriod().equals(debtDetailNoRepay.getBorrowPeriod())) {
                                    // 最后还款日
//                                    hjhDebtCredit.setCreditRepayEndTime(debtDetailNoRepay.getRepayTime());
                                }
                                unRepayPeriod++;
                            }
                        } else {
                            // 最后还款日
//                            hjhDebtCredit.setCreditRepayEndTime(hjhDebtDetailCur.getRepayTime());
                        }
                        // 查询当前时间落在哪一期
                        // 应还日期 >= 当前日期 的债权
                        HjhDebtDetail currentPeriodDebtDetail = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurPeriodByDate(hjhDebtDetail.getOrderId(), dualDateStr);
                        // 如果不为空
                        if (currentPeriodDebtDetail != null) {
                            // 当前期计息期间
                            Integer currentPeriodDuringDays = 0;
                            // 判断是否提前还款
                            // 如果债权已还款
                            if (currentPeriodDebtDetail.getRepayStatus() == 1) {
                                // 债权实际还款时间
                                Integer repayActionTime = currentPeriodDebtDetail.getRepayActionTime();
                                // 债权应还时间
                                Integer repayTimeDebtDetail = currentPeriodDebtDetail.getRepayTime();
                                // 应还时间 > 当前清算时间
                                if (repayTimeDebtDetail > liquidationShouldTime) {
                                    // 应还时间到清算日天数
                                    int currentPeriodAdvanceDays = 0;
                                    try {
                                        currentPeriodAdvanceDays = GetDate.daysBetween(repayTimeDebtDetail, liquidationShouldTime);
                                    } catch (ParseException e) {
                                        logger.error(e.getMessage());
                                    }
                                    // 提前还款，债权价值计算减扣
                                    // 计算当前期计息天数
                                    // 如果是第一期尚未还款
                                    if (currentPeriodDebtDetail.getRepayPeriod() == 1) {
                                        // 如果第一期尚未还款
                                        // 当前期计息天数的计算
                                        // 当前期放款时间
                                        Integer currentPeriodLoanTime = currentPeriodDebtDetail.getLoanTime();
                                        // 当前期计息期间 = 放款时间 到 应还款时间
                                        try {
                                            currentPeriodDuringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(currentPeriodLoanTime), GetDate.timestamptoStrYYYYMMDD(repayTimeDebtDetail)) + 1;
                                        } catch (ParseException e) {
                                            logger.error(e.getMessage());
                                        }
                                    } else {
                                        // 如果不是第一期
                                        // 查询上一期还款的债权详情
                                        BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(currentPeriodDebtDetail.getBorrowNid(), currentPeriodDebtDetail.getInvestOrderId(), currentPeriodDebtDetail.getRepayPeriod() - 1);
                                        // 还款日后一天
                                        Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                        // 当前期计息期间 = 上一期应还时间 到 应还款时间
                                        try {
                                            currentPeriodDuringDays = GetDate.daysBetween(repayPreTime, repayTimeDebtDetail) + 1;
                                        } catch (ParseException e) {
                                            logger.error(e.getMessage());
                                        }
                                    }
                                    // 提前还款，债权价值计算
                                    // 提前还款债权价值减扣部分
                                    advanceCreditValue = ((currentPeriodDebtDetail.getRepayInterestYes().multiply(new BigDecimal(currentPeriodAdvanceDays))).divide(new BigDecimal(currentPeriodDuringDays), 2, BigDecimal.ROUND_DOWN));
                                    logger.info(LOG_MAIN_INFO + "提前还款债权价值减扣部分:[" + advanceCreditValue + "].");
                                    logger.info(LOG_MAIN_INFO + "已还利息:[" + currentPeriodDebtDetail.getRepayInterestYes() + "].");
                                    logger.info(LOG_MAIN_INFO + "提前天数:[" + currentPeriodAdvanceDays + "].");
                                    logger.info(LOG_MAIN_INFO + "当前期计息天数:[" + currentPeriodDuringDays + "].");
                                }
                            }
                        }

//                        // 债转期数 = 项目期限 - 已还期数 + 1
//                        hjhDebtCredit.setCreditPeriod(unRepayPeriod);
//                        // 还款期数
//                        hjhDebtCredit.setRepayPeriod(hjhDebtDetailCur.getBorrowPeriod() - unRepayPeriod);
//                        // 债转总额
//                        hjhDebtCredit.setCreditAccount(total);
//                        // 债转总本金
//                        hjhDebtCredit.setCreditCapital(capital);
//                        // 清算总本金
//                        hjhDebtCredit.setLiquidatesCapital(capital);
//                        // 债转总利息
//                        hjhDebtCredit.setCreditInterest(interest);
//                        // 待承接总金额
//                        hjhDebtCredit.setCreditAccountWait(total);
//                        // 待承接本金
//                        hjhDebtCredit.setCreditCapitalWait(capital);
//                        // 待承接利息
//                        hjhDebtCredit.setCreditInterestWait(interest);
//                        // 已还总额
//                        hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
//                        // 已还本金
//                        hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
//                        // 已还利息
//                        hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
//                        // 待还总额
//                        hjhDebtCredit.setRepayAccountWait(total);
//                        // 待还本金
//                        hjhDebtCredit.setRepayCapitalWait(capital);
//                        // 待还利息
//                        hjhDebtCredit.setRepayInterestWait(interest);
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue).subtract(advanceCreditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                        // 计算此时的公允价值
//                        hjhDebtCredit.setLiquidationFairValue(fairValue);
//                        // 债权持有时间
//                        hjhDebtCredit.setHoldDays(holdDays);
//                        // 当前期计息天数
//                        hjhDebtCredit.setDuringDays(duringDays);
//                        // 债权剩余期限
//                        hjhDebtCredit.setRemainDays(remainDays);
//                        hjhDebtCredit.setCreditTerm(remainDays);
                    } else {
                        //  有未还款 最后一期或者往前逾期
                        // 三个月计划 包含 2个月 标的
                        // 取最后一期债权信息
                        HjhDebtDetail lastTermDebtDetail = this.selectLastTermDebtDetail(hjhDebtDetail);
                        if (lastTermDebtDetail != null) {
                            // 应还时间
                            Integer repayTime = lastTermDebtDetail.getRepayTime();
                            // 放款时间
                            Integer loanTime = lastTermDebtDetail.getLoanTime();
//                            // 出让人
//                            hjhDebtCredit.setSellOrderId(lastTermDebtDetail.getOrderId());
//                            // 出借订单号
//                            hjhDebtCredit.setInvestOrderId(lastTermDebtDetail.getInvestOrderId());
//                            // 说明之前的分期还款正常完成
//                            hjhDebtCredit.setActualApr(lastTermDebtDetail.getBorrowApr());
                            // 如果是第一期尚未还款
                            if (lastTermDebtDetail.getRepayPeriod() == 1) {
                                // 如果第一期尚未还款
//                                hjhDebtCredit.setCreditRepayLastTime(0);
                                // 持有时间是放款时间至还款日
                                try {
                                    holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(lastTermDebtDetail.getLoanTime()), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                                // 当前期计息天数
                                try {
                                    duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                            } else {
                                // 如果不是第一期
                                // 查询上一期还款的债权详情
                                BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(lastTermDebtDetail.getBorrowNid(), lastTermDebtDetail.getInvestOrderId(), lastTermDebtDetail.getRepayPeriod() - 1);
                                // 上期还款日期
//                                hjhDebtCredit.setCreditRepayLastTime(Integer.valueOf(borrowRecoverPlan.getRecoverTime()));
                                // 还款日后一天
                                Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                // 持有期是上一期应还时间的后一天至当清算日前一天
                                try {
                                    holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                                if (holdDays < 0) {
                                    holdDays = 0;
                                }
                                // 当前期计息天数
                                try {
                                    duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                            }
                            // 最后一期还款的信息
                            BorrowRepayPlan borrowRepayPlan = this.getLastPeriodBorrowRepayPlan(borrowNid, lastTermDebtDetail.getBorrowPeriod());
                            if (borrowRepayPlan != null) {
                                // 剩余期限 当前日期 到 最后一期还款日
                                try {
                                    remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(borrowRepayPlan.getRepayTime())) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                            }
                            if (remainDays <= 0) {
                                remainDays = 0;
                            }
                            // 设置分期相关信息
//                            // 承接所在期数
//                            hjhDebtCredit.setAssignPeriod(lastTermDebtDetail.getRepayPeriod());
//                            // 清算所在期数
//                            hjhDebtCredit.setLiquidatesPeriod(lastTermDebtDetail.getRepayPeriod());
//                            // 下期还款时间
//                            hjhDebtCredit.setCreditRepayNextTime(lastTermDebtDetail.getRepayTime());
                            // 计算待垫付的利息
                            creditValue = ((lastTermDebtDetail.getRepayInterestWait().multiply(new BigDecimal(holdDays))).divide(new BigDecimal(duringDays), 2, BigDecimal.ROUND_DOWN));
                            // 检索是否有之前期有逾期
                            List<HjhDebtDetail> overdueDetailList = this.selectOverdueDetailList(lastTermDebtDetail.getOrderId(), lastTermDebtDetail.getRepayPeriod());
                            if (overdueDetailList != null && overdueDetailList.size() > 0) {
                                for (HjhDebtDetail overdueDetail : overdueDetailList) {
                                    creditValue = creditValue.add(overdueDetail.getRepayInterestWait());
                                }
//                                hjhDebtCredit.setIsLateCredit(1);
                            }
                            // 垫付利息
//                            hjhDebtCredit.setCreditInterestAdvance(creditValue);
//                            // 待垫付利息
//                            hjhDebtCredit.setCreditInterestAdvanceWait(creditValue);
                            BigDecimal capital = lastTermDebtDetail.getRepayCapitalWait();
                            BigDecimal interest = lastTermDebtDetail.getRepayInterestWait();
                            BigDecimal total = capital.add(interest);

                            // 未还款期数
                            Integer unRepayPeriod = 0;
                            // 如果当前期不是最后一期 则需要查询当前期到最后一期的债权信息
                            List<HjhDebtDetail> hjhDebtDetailsNoRepay = this.hjhDebtDetailCustomizeMapper.selectDebtDetailNoRepay(lastTermDebtDetail.getOrderId());
                            capital = new BigDecimal(0);
                            interest = new BigDecimal(0);
                            total = new BigDecimal(0);
                            if (hjhDebtDetailsNoRepay != null && hjhDebtDetailsNoRepay.size() > 0) {
                                for (HjhDebtDetail debtDetailNoRepay : hjhDebtDetailsNoRepay) {
                                    capital = capital.add(debtDetailNoRepay.getRepayCapitalWait());
                                    interest = interest.add(debtDetailNoRepay.getRepayInterestWait());
                                    total = capital.add(interest);
                                    if (debtDetailNoRepay.getRepayPeriod().equals(debtDetailNoRepay.getBorrowPeriod())) {
                                        // 最后还款日
//                                        hjhDebtCredit.setCreditRepayEndTime(debtDetailNoRepay.getRepayTime());
                                    }
                                    unRepayPeriod++;
                                }
                            } else {
                                // 最后还款日
//                                hjhDebtCredit.setCreditRepayEndTime(lastTermDebtDetail.getRepayTime());
                            }

                            // 查询当前时间落在哪一期
                            // 应还日期 >= 当前日期 的债权
                            HjhDebtDetail currentPeriodDebtDetail = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurPeriodByDate(hjhDebtDetail.getOrderId(), dualDateStr);
                            // 如果不为空
                            if (currentPeriodDebtDetail != null) {
                                // 当前期计息期间
                                Integer currentPeriodDuringDays = 0;
                                // 判断是否提前还款
                                // 如果债权已还款
                                if (currentPeriodDebtDetail.getRepayStatus() == 1) {
                                    // 债权实际还款时间
                                    Integer repayActionTime = currentPeriodDebtDetail.getRepayActionTime();
                                    // 债权应还时间
                                    Integer repayTimeDebtDetail = currentPeriodDebtDetail.getRepayTime();
                                    // 应还时间 > 当前清算时间
                                    if (repayTimeDebtDetail > liquidationShouldTime) {
                                        // 应还时间到清算日天数
                                        int currentPeriodAdvanceDays = 0;
                                        try {
                                            currentPeriodAdvanceDays = GetDate.daysBetween(repayTimeDebtDetail, liquidationShouldTime);
                                        } catch (ParseException e) {
                                            logger.error(e.getMessage());
                                        }
                                        // 提前还款，债权价值计算减扣
                                        // 计算当前期计息天数
                                        // 如果是第一期尚未还款
                                        if (currentPeriodDebtDetail.getRepayPeriod() == 1) {
                                            // 如果第一期尚未还款
                                            // 当前期计息天数的计算
                                            // 当前期放款时间
                                            Integer currentPeriodLoanTime = currentPeriodDebtDetail.getLoanTime();
                                            // 当前期计息期间 = 放款时间 到 应还款时间
                                            try {
                                                currentPeriodDuringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(currentPeriodLoanTime), GetDate.timestamptoStrYYYYMMDD(repayTimeDebtDetail)) + 1;
                                            } catch (ParseException e) {
                                                logger.error(e.getMessage());
                                            }
                                        } else {
                                            // 如果不是第一期
                                            // 查询上一期还款的债权详情
                                            BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(currentPeriodDebtDetail.getBorrowNid(), currentPeriodDebtDetail.getInvestOrderId(), currentPeriodDebtDetail.getRepayPeriod() - 1);
                                            // 还款日后一天
                                            Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                            // 当前期计息期间 = 上一期应还时间 到 应还款时间
                                            try {
                                                currentPeriodDuringDays = GetDate.daysBetween(repayPreTime, repayTimeDebtDetail) + 1;
                                            } catch (ParseException e) {
                                                logger.error(e.getMessage());
                                            }
                                        }
                                        // 提前还款，债权价值计算
                                        // 提前还款债权价值减扣部分
                                        advanceCreditValue = ((currentPeriodDebtDetail.getRepayInterestYes().multiply(new BigDecimal(currentPeriodAdvanceDays))).divide(new BigDecimal(currentPeriodDuringDays), 2, BigDecimal.ROUND_DOWN));
                                        logger.info(LOG_MAIN_INFO + "提前还款债权价值减扣部分:[" + advanceCreditValue + "].");
                                        logger.info(LOG_MAIN_INFO + "已还利息:[" + currentPeriodDebtDetail.getRepayInterestYes() + "].");
                                        logger.info(LOG_MAIN_INFO + "提前天数:[" + currentPeriodAdvanceDays + "].");
                                        logger.info(LOG_MAIN_INFO + "当前期计息天数:[" + currentPeriodDuringDays + "].");
                                    }
                                }
                            }
//                            // 债转期数 = 项目期限 - 已还期数 + 1
//                            hjhDebtCredit.setCreditPeriod(unRepayPeriod);
//                            // 还款期数
//                            hjhDebtCredit.setRepayPeriod(lastTermDebtDetail.getBorrowPeriod() - unRepayPeriod);
//                            // 债转总额
//                            hjhDebtCredit.setCreditAccount(total);
//                            // 债转总本金
//                            hjhDebtCredit.setCreditCapital(capital);
//                            // 清算总本金
//                            hjhDebtCredit.setLiquidatesCapital(capital);
//                            // 债转总利息
//                            hjhDebtCredit.setCreditInterest(interest);
//                            // 待承接总金额
//                            hjhDebtCredit.setCreditAccountWait(total);
//                            // 待承接本金
//                            hjhDebtCredit.setCreditCapitalWait(capital);
//                            // 待承接利息
//                            hjhDebtCredit.setCreditInterestWait(interest);
//                            // 已还总额
//                            hjhDebtCredit.setRepayAccount(BigDecimal.ZERO);
//                            // 已还本金
//                            hjhDebtCredit.setRepayCapital(BigDecimal.ZERO);
//                            // 已还利息
//                            hjhDebtCredit.setRepayInterest(BigDecimal.ZERO);
//                            // 待还总额
//                            hjhDebtCredit.setRepayAccountWait(total);
//                            // 待还本金
//                            hjhDebtCredit.setRepayCapitalWait(capital);
//                            // 待还利息
//                            hjhDebtCredit.setRepayInterestWait(interest);
                            // 债权价值
                            BigDecimal fairValue = capital.add(creditValue).subtract(advanceCreditValue);
                            // 加入订单的债权价值
                            totalFairValue = totalFairValue.add(fairValue);
                            // 计算此时的公允价值
//                            hjhDebtCredit.setLiquidationFairValue(fairValue);
//                            // 债权持有时间
//                            hjhDebtCredit.setHoldDays(holdDays);
//                            // 当前期计息天数
//                            hjhDebtCredit.setDuringDays(duringDays);
//                            // 债权剩余期限
//                            hjhDebtCredit.setRemainDays(remainDays);
//                            hjhDebtCredit.setCreditTerm(remainDays);
//                            hjhDebtCredit.setIsLateCredit(1);
                        }
                    }
                }
                // 已承接总额
//                hjhDebtCredit.setCreditAccountAssigned(BigDecimal.ZERO);
//                // 已承接本金
//                hjhDebtCredit.setCreditCapitalAssigned(BigDecimal.ZERO);
//                // 已承接利息
//                hjhDebtCredit.setCreditInterestAssigned(BigDecimal.ZERO);
//                // 已承接垫付利息
//                hjhDebtCredit.setCreditInterestAdvanceAssigned(BigDecimal.ZERO);
//                // 债转总收入
//                hjhDebtCredit.setCreditIncome(BigDecimal.ZERO);
//                // 债转服务费
//                hjhDebtCredit.setCreditServiceFee(BigDecimal.ZERO);
//                // 债转价格
//                hjhDebtCredit.setCreditPrice(BigDecimal.ZERO);
//                // 实际还款时间
//                hjhDebtCredit.setCreditRepayYesTime(0);
//                // 债转结束时间
//                hjhDebtCredit.setEndTime(0);
//                // 承接次数
//                hjhDebtCredit.setAssignNum(0);
//                hjhDebtCredit.setDelFlag(0);
//                hjhDebtCredit.setClient(0);
//                hjhDebtCredit.setCreateTime(GetDate.getDate(liquidationShouldTime));
//                hjhDebtCredit.setCreateUserId(hjhDebtDetail.getUserId());
//                hjhDebtCredit.setCreateUserName(hjhDebtDetail.getUserName());
                // 出让次数
//                hjhDebtCredit.setCreditTimes(hjhDebtDetail.getCreditTimes() + 1);
//                boolean isInsertFlag = this.hjhDebtCreditMapper.insertSelective(hjhDebtCredit) > 0 ? true : false;
//                if (!isInsertFlag) {
//                    throw new RuntimeException("清算异常,插入债转信息失败,加入订单号:[" + accedeOrderId + "].");
//                }
                // 债权价值记录
//                creditList.add(hjhDebtCredit.getCreditNid());
                // 清算完成后,将清算出的债权置为无效
//                boolean isUpdateFlag = this.hjhDebtDetailCustomizeMapper.updateDetailDelFlagToOne(hjhDebtDetail.getOrderId()) > 0 ? true : false;
//                if (!isUpdateFlag) {
//                    throw new RuntimeException("清算完成后,将清算出的债权置为无效操作失败,债权ID:" + hjhDebtDetail.getOrderId() + "].");
//                }
            }
        }
        // 清算出债权之后,更新自动债转是否完成标示
//        if (isRepayFlag == 0) {
//            // 如果没有正在还款的债权,更新清算完成标志位为1.
//            this.updateAccedeCreditCompleteFlag(accedeOrderId, 1);
//        }

//        // 根据加入订单号查询出借的标的
//        List<BorrowTender> borrowTenderList = this.selectBorrowTenderList(accedeOrderId);
//
//        if (borrowTenderList != null && borrowTenderList.size() > 0) {
//            // 循环已出借的出借记录
//            for (int i = 0; i < borrowTenderList.size(); i++) {
//                BorrowTender borrowTender = borrowTenderList.get(i);
//                // 判断出借的标的是否未放款
//                Borrow tenderBorrow = this.getBorrowByNid(borrowTender.getBorrowNid());
//                if (tenderBorrow == null) {
//                    throw new RuntimeException("根据标的编号查询标的信息失败,标的编号:[" + borrowTender.getBorrowNid() + "].");
//                }
//                BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowTender.getBorrowNid());
//                if (borrowInfo == null) {
//                    throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + borrowTender.getBorrowNid() + "].");
//                }
//                // 标的状态
//                Integer loanStatus = tenderBorrow.getStatus();
//                // 如果标的状态<4 说明这笔出借的标的未放款完成
//                if (loanStatus < 4) {
//                    // 更新加入订单的清算标志位为2,需要再次清算
//                    this.updateAccedeCreditCompleteFlag(accedeOrderId, 2);
//                }
//            }
//        }
        return totalFairValue;
    }

    /**
     * 分期标的检索当天是否有还款
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRepayPlan> selectBorrowRepayPlanList(String borrowNid, Date dualDate) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        // 应还时间 >= 当天开始时间
        cra.andRepayTimeGreaterThanOrEqualTo(GetDate.getDayStart11(dualDate));
        // 应还时间 <= 当天结束时间
        cra.andRepayTimeLessThanOrEqualTo(GetDate.getDayEnd10(dualDate));
        // 还款状态未还款
        cra.andRepayStatusEqualTo(0);
        List<BorrowRepayPlan> borrowRepayPlanList = this.borrowRepayPlanMapper.selectByExample(example);
        return borrowRepayPlanList;
    }

    /**
     * 不分期标的检索当天是否有还款
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRepay> selectBorrowRepayList(String borrowNid, Date dualDate) {
        BorrowRepayExample example = new BorrowRepayExample();
        BorrowRepayExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        // 应还时间 >= 当天开始时间
        cra.andRepayTimeGreaterThanOrEqualTo(GetDate.getDayStart11(dualDate));
        // 应还时间 <= 当天结束时间
        cra.andRepayTimeLessThanOrEqualTo(GetDate.getDayEnd10(dualDate));
        // 还款状态未还款
        cra.andRepayStatusEqualTo(0);
        List<BorrowRepay> borrowRepayList = this.borrowRepayMapper.selectByExample(example);
        return borrowRepayList;
    }

    /**
     * 根据出借订单号,标的编号查询是否债权已被出让
     *
     * @param borrowNid
     * @param investOrderId
     * @return
     */
    private HjhDebtCredit selectHjhDebtCreditYes(String borrowNid, String investOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andSellOrderIdEqualTo(investOrderId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(2);
        statusList.add(3);
        cra.andCreditStatusNotIn(statusList);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询完全承接的出让
     *
     * @param borrowNid
     * @param investOrderId
     * @return
     */
    private HjhDebtCredit selectHjhDebtCreditAssignComplete(String borrowNid, String investOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andInvestOrderIdEqualTo(investOrderId);
        cra.andCreditStatusEqualTo(2);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询上一期还款信息
     *
     * @param borrowNid
     * @param investOrderId
     * @param repayPeriod
     * @return
     */
    private BorrowRecoverPlan selectLastPeriodRecoverPlan(String borrowNid, String investOrderId, int repayPeriod) {
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andNidEqualTo(investOrderId);
        cra.andRecoverPeriodEqualTo(repayPeriod);
        List<BorrowRecoverPlan> list = this.borrowRecoverPlanMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据项目编号,借款期限检索最后一期还款信息
     *
     * @param borrowNid
     * @param borrowPeriod
     * @return
     */
    private BorrowRepayPlan getLastPeriodBorrowRepayPlan(String borrowNid, Integer borrowPeriod) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepayPeriodEqualTo(borrowPeriod);
        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 检索当前期逾期的债权
     *
     * @param orderId
     * @param repayPeriod
     * @return
     */
    private List<HjhDebtDetail> selectOverdueDetailList(String orderId, Integer repayPeriod) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        HjhDebtDetailExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(orderId);
        cra.andStatusEqualTo(1);
        cra.andRepayStatusEqualTo(0);
        cra.andRepayPeriodLessThan(repayPeriod);
        List<HjhDebtDetail> list = this.hjhDebtDetailMapper.selectByExample(example);
        return list;
    }

    /**
     * 取最后一期债权信息
     *
     * @param hjhDebtDetail
     * @return
     */
    private HjhDebtDetail selectLastTermDebtDetail(HjhDebtDetail hjhDebtDetail) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        HjhDebtDetailExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(hjhDebtDetail.getOrderId());
        cra.andStatusEqualTo(1);
        cra.andRepayStatusEqualTo(0);
        cra.andRepayPeriodEqualTo(hjhDebtDetail.getBorrowPeriod());
        List<HjhDebtDetail> list = this.hjhDebtDetailMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}