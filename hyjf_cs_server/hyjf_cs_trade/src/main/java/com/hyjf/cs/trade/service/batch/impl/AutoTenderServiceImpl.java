/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.batch.AutoTenderService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 自动投资
 *
 * @author liubin
 * @version AutoTenderServiceImpl, v0.1 2018/6/28 14:09
 */
@Service
public class AutoTenderServiceImpl extends BaseTradeServiceImpl implements AutoTenderService {
    @Value("${hyjf.bank.instcode}")
    private String instCode;
    @Value("${hyjf.bank.bankcode}")
    private String bankCode;

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 取得自动投资用加入计划列表
     *
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectPlanJoinList() {
        return this.amTradeClient.selectPlanJoinList();
    }

    /**
     * 汇计划加入订单 自动投资/复投
     * 0. 取得计划信息
     * 1. 取得投资人信息（授权账户等）
     * 2. 有可投金额循环投资
     * 3. 从队列中取得标的
     * 4. 自动投资债转标的（承接）
     * 4.1. 债转用金额计算
     * 4.2. 获取债转详情
     * 4.3. 校验是否可以债转
     * 4.4. 调用银行自动购买债权接口
     * 4.5. 减去被投标的可投金额，部分承接时，余额推回队列
     * 4.6. 更新同步数据库
     * 4.7. 完全承接时，结束债券
     * 5. 自动投资原始标的（投资）
     * 5.1. 投资用金额计算
     * 5.2. 获取标的详情
     * 5.3. 校验是否可以投资
     * 5.4. 调用银行自动投标申请接口
     * 5.5. 减去被投标的可投金额，部分投资时，余额推回队列
     * 5.6. 更新同步数据库
     *
     * @param hjhAccede
     * @return
     */
    @Override
    public boolean autoTenderForOneAccede(HjhAccedeVO hjhAccede) {
        //汇计划加入订单号
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        //银行交易前，异常订单状态设定，和系统异常
        final Integer ORDER_STATUS_ERR = hjhAccede.getOrderStatus() + 90;
        //银行交易后，异常订单状态设定
        final Integer ORDER_STATUS_FAIL = hjhAccede.getOrderStatus() + 80;
        //一个计划订单的连续失败次数
        int serialFaileCount = 0;

        // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
        //是否分散投资
        int diversifyCount = -1; //非分散投资
        //已投资笔数
        int investCountForLog = hjhAccede.getInvestCounts();
        // add 汇计划三期 汇计划自动投资(分散投资) liu0180515 end

        if (hjhAccede.getOrderStatus() == 0) {
            //0自动投标中
            logger.info("======计划加入订单号 " + accedeOrderId + "开始自动投资,订单状态" + hjhAccede.getOrderStatus() + "======");
            // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
            diversifyCount = 0; //初始分散投资 （只有投资原始标的（非复投）时，使用分散投资）
            // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end
        } else {
            //2自动投资成功或者3锁定中
            logger.info("======计划加入订单号 " + accedeOrderId + "开始自动复投,订单状态" + hjhAccede.getOrderStatus() + "======");
        }

        /** 0. 取得计划信息 */
        HjhPlanVO hjhPlan = amTradeClient.getPlanByNid(hjhAccede.getPlanNid());
        // 计划订单加入计划金额
        BigDecimal accedeAccount = hjhAccede.getAccedeAccount();
        // 计划订单可投金额
        BigDecimal ketouplanAmoust = hjhAccede.getAvailableInvestAccount();

        // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
        // 每组可投金额（分散投资的最小分散金额）
        BigDecimal groupAmoust = ketouplanAmoust;
        // 最小分散组数
        int groupCount = 1;
        // 标的队列名称
        // 原始标的队列
        String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
        // ★临时队列有标的时，先推回主队列（恢复前次分散投资）★
        RedisUtils.lpoprpush(queueName + RedisConstants.HJH_SLASH_TMP, queueName);
        // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end

        logger.info("====[" + accedeOrderId + "]" + "加入计划金额：" + accedeAccount.toString()+ "，初始可投金额：" + ketouplanAmoust.toString());

        /** 1. 取得投资人信息（授权账户等） */
        //获取投资授权码
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(hjhAccede.getUserId());
        if (hjhUserAuth == null || StringUtils.isEmpty(hjhUserAuth.getAutoOrderId())) {
            logger.error("====[" + accedeOrderId + "]" + "未获取到投资授权码  " + hjhAccede.getUserId());
            return false;
        }
        if (StringUtils.isEmpty(hjhUserAuth.getAutoCreditOrderId())) {
            logger.error("====[" + accedeOrderId + "]" + "未获取到债转授权码  " + hjhAccede.getUserId());
            return false;
        }
        //获取投资账户
        BankOpenAccountVO bankOpenAccount = this.amUserClient.selectBankAccountById(hjhAccede.getUserId());
        if (bankOpenAccount == null) {
            logger.error("====[" + accedeOrderId + "]" + "用户没开户 " + hjhAccede.getUserId());
            return false;
        }
        String tenderUsrcustid = bankOpenAccount.getAccount();//获取江西银行电子账号

        /** 2. 有可投金额循环投资	 */
        // 计划订单的可投金额 >= minAccountEnable 进行投资/复投
        // 计划订单的可投金额 < minAccountEnable 该计划订单投资/复投结束
        BigDecimal minAccountEnable = getMinAccountEnable(hjhAccede);
        while (ketouplanAmoust.compareTo(minAccountEnable) >= 0) {
            // add 汇计划三期 汇计划自动投资(投资笔数累计) liubin 20180515 start
            logger.info("====计划加入订单号 " + accedeOrderId + "投前累计投资笔数：" + investCountForLog + "====");
            investCountForLog += 1;
            // add 汇计划三期 汇计划自动投资(投资笔数累计) liubin 20180515 end

            // ketouplanAmoust小于1元时报警告信息
            if (ketouplanAmoust.compareTo(new BigDecimal(1)) < 0) {
                logger.warn("警告====[" + accedeOrderId + "]" + "的可投资金额为" + ketouplanAmoust.toString() + ",小于1元");
            }

            /** 3. 从队列中取得标的	 */
            /*******************取一个标的************************/
            RedisBorrow redisBorrow = null;

            String borrowStr = null;//标的的JsonString
            // del 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
            //String queueName = null;//标的队列名称
            // del 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end
            String borrowFlag = null;//标的类型（债转标，原始标）

            // 连续10次出投人相同后，换个计划订单投
            if (serialFaileCount >= CustomConstants.HJH_SERIAL_FAILE_COUNT) {
                logger.error("[" + accedeOrderId + "]" + "借款人/出让人和计划订单的投资人连续相同次数超过" + CustomConstants.HJH_SERIAL_FAILE_COUNT + "次,跳过该计划订单");
                return false;
            }
            logger.info("[" + accedeOrderId + "]" + "连续相同次数" + serialFaileCount + "次");
            // 取债转标的（优先） (连续5次不能投债转标时,取原始标的)
            if (serialFaileCount < CustomConstants.HJH_ASSIGN_SERIAL_FAILE_COUNT) {
                queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                borrowStr = getBorrowFromQueue(queueName);
                borrowFlag = RedisConstants.HJH_BORROW_CREDIT;
            } else {
                logger.info("[" + accedeOrderId + "]" + "出让人和计划订单的投资人连续相同次数超过" + CustomConstants.HJH_ASSIGN_SERIAL_FAILE_COUNT + "次,只投原始标的");
            }

            // 取原始标的(无债转标的时)
            if (borrowStr == null) {
                queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
                // ****分散投资主规则****
                // 初始分散投资时
                if (diversifyCount == 0) {
                    // 取得最小分散组数（计划最小分散笔数 & 队列中的标的数 取小的）
                    groupCount = (int) DigitalUtils.min((long) hjhPlan.getMinInvestCounts(), RedisUtils.llen(queueName));
                    groupCount = groupCount < 1 ? 1 : groupCount;//小于1时，组数取1
                    // 计算每组投资金额（取百位）
                    groupAmoust = ketouplanAmoust.divide(new BigDecimal(groupCount), -2, BigDecimal.ROUND_DOWN);
                }
                // 分散投资时（突然插入债转可能可投余额不够）
                if (diversifyCount >= 0) {
                    logger.info("[" + accedeOrderId + "]" + "开始分散投资。。。组数：" + groupCount + ", 每组金额：" + groupAmoust
                            + ", 当前组：" + (diversifyCount + 1));
                    groupAmoust = DigitalUtils.min(groupAmoust, ketouplanAmoust);
                }
                // 分散组数=1 或者 超出分散组数 或者 groupAmoust<100 的投资不再分散
                if (groupCount == 1 || diversifyCount == -1 || diversifyCount + 1 > groupCount || groupAmoust.compareTo(new BigDecimal(100)) < 0) {
                    groupAmoust = ketouplanAmoust;
                    diversifyCount = -1;
                }
                // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end
                borrowStr = getBorrowFromQueue(queueName);
                borrowFlag = RedisConstants.HJH_BORROW_INVEST;
            }

            // 没有可投资标的
            if (borrowStr == null || borrowFlag == null) {
                return false;
            }

            // 转为borrow对象
            redisBorrow = JSON.parseObject(borrowStr, RedisBorrow.class);

            // 标的编号为空
            if (redisBorrow.getBorrowNid() == null) {
                logger.error("[" + accedeOrderId + "]" + "队列的标的编号为空！");
                return false;
            }

            // 标的无可投余额
            if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) <= 0) {
                logger.error("[" + accedeOrderId + "]" + redisBorrow.getBorrowNid() + " 标的可投金额为 " + redisBorrow.getBorrowAccountWait());
                return false;
            }
            /*******************************************/

            boolean result = false;
            boolean isLast = false;
            String borrowNidForCredit = "";
            try {
                if (borrowFlag.equals(RedisConstants.HJH_BORROW_CREDIT)) {
                    /** 4. 自动投资债转标的（承接）	 */
                    logger.info("==[" + accedeOrderId + "]" + "自动承接债转标的" + redisBorrow.getBorrowNid());
                    logger.info("[" + accedeOrderId + "]" + "承前的可投金额：" + ketouplanAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());
                    /** 4.1. 债转用金额计算	 */
                    // 设置实际投资金额
                    // 债转标的： 清算时公允价值-已投本金和已投垫付利息
                    BigDecimal yujiAmoust = ketouplanAmoust;
                    if (yujiAmoust.compareTo(redisBorrow.getBorrowAccountWait()) >= 0) {
                        // 该标的/债转最后一笔投资
                        yujiAmoust = redisBorrow.getBorrowAccountWait();
                        isLast = true;
                    }

                    /** 4.2. 获取债转详情	 */
                    HjhDebtCreditVO credit = this.amTradeClient.selectHjhDebtCreditByCreditNid(redisBorrow.getBorrowNid());
                    if (credit == null) {
                        logger.error("[" + accedeOrderId + "]" + "债转号不存在 " + redisBorrow.getBorrowNid());
                        return false;
                    }

                    if (credit.getCreditStatus().compareTo(3) == 0) {
                        //3承接终止
                        logger.warn("[" + accedeOrderId + "]" + "债转标的" + redisBorrow.getBorrowNid() + "发生还款（3），被停止债转，不再推回队列。");
                        result = true;
                        continue;
                    }

                    // 债转加redis锁，禁止还款
                    boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrowNidForCredit, redisBorrow.getBorrowNid(), 300);
                    if (!tranactionSetFlag) {//设置失败
                        //承接终止
                        logger.warn("[" + accedeOrderId + "]" + "债转标的" + redisBorrow.getBorrowNid() + "发生还款（Redis），被停止债转，不再推回队列。");
                        result = true;
                        continue;
                    }

                    /** 4.3. 校验是否可以债转	 */
                    // 债权的转让人，和计划订单的投资人不能相同
                    if (credit.getUserId().compareTo(hjhAccede.getUserId()) == 0) {
//						if (serialFaileCount >= CustomConstants.HJH_TENDER_SERIAL_FAILE_COUNT) {
//							// 连续10次失败后，换个计划订单投
//							logger.error("[" + accedeOrderId + "]" + "借款人/出让人和计划订单的投资人连续相同次数超过"+CustomConstants.HJH_TENDER_SERIAL_FAILE_COUNT+"次,跳过该计划订单");
//							return false;
//						}
                        logger.info("[" + accedeOrderId + "]" + "债权的转让人(" + credit.getUserId() + ")和计划订单的投资人(" + hjhAccede.getUserId() + ")不能相同");
                        String redisStr = JSON.toJSONString(redisBorrow);
                        RedisUtils.leftpush(queueName, redisStr);//标的推回队列头，再取标的投资。
                        serialFaileCount++;
                        result = true;
                        continue;
                    }
                    // credit的CreditStatus = 3 时债转停止，不再推回队列，取下一个队列中的标的
                    if (credit.getCreditStatus().compareTo(3) == 0) {
                        logger.info("[" + accedeOrderId + "]" + "债转号 " + redisBorrow.getBorrowNid() + "的债权已经停止债转。");
                        result = true;
                        continue;
                    }

                    /** 4.4. 调用银行自动购买债权接口	 */
                    // 调用银行接口准备参数
                    //获取出让用户的江西银行电子账号
                    BankOpenAccountVO sellerBankOpenAccount = this.amUserClient.selectBankAccountById(credit.getUserId());
                    if (sellerBankOpenAccount == null) {
                        logger.info("[" + accedeOrderId + "]" + "转出用户没开户 " + credit.getUserId());
                        return false;
                    }
                    String sellerUsrcustid = sellerBankOpenAccount.getAccount();//出让用户的江西银行电子账号

                    // 生成承接日志
                    String orderId = GetOrderIdUtils.getOrderId2(hjhAccede.getUserId());
                    // 债权承接订单日期
                    String orderDate = GetOrderIdUtils.getTxDate();
                    // 计算计划债转实际金额 保存creditTenderLog表
                    HjhCreditCalcResultVO resultVO = this.amTradeClient.saveCreditTenderLog(credit, hjhAccede, orderId, orderDate, yujiAmoust, isLast);
                    if (Validator.isNull(resultVO)) {
                        throw new Exception("保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId());
                    }
                    //承接支付金额
                    BigDecimal assignPay = resultVO.getAssignPay();
                    //承接本金
                    BigDecimal assignCapital = resultVO.getAssignCapital();
                    //承接服务费
                    BigDecimal serviceFee = resultVO.getServiceFee();
                    logger.info("[" + accedeOrderId + "]" + "承接用计算完成"
                            + "\n,分期数据结果:" + resultVO.getAssignResult().toString()
                            + "\n,承接总额:" + resultVO.getAssignAccount()
                            + "\n,承接本金:" + resultVO.getAssignCapital()
                            + "\n,承接利息:" + resultVO.getAssignInterest()
                            + "\n,承接支付金额:" + resultVO.getAssignPay()
                            + "\n,承接垫付利息:" + resultVO.getAssignAdvanceMentInterest()
                            + "\n,承接延期利息:" + resultVO.getAssignRepayDelayInterest()
                            + "\n,承接逾期利息:" + resultVO.getAssignRepayLateInterest()
                            // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
                            + "\n,承接服务率:" + resultVO.getServiceApr()
                            // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
                            + "\n,承接服务费:" + resultVO.getServiceFee());

                    logger.info("[" + accedeOrderId + "]" + " 银行自动购买债权接口调用前  " + credit.getCreditNid());

                    //防止钱不够也承接校验
                    HjhAccedeVO hjhAccedeCheck = this.amTradeClient.getHjhAccedeByAccedeOrderId(hjhAccede.getAccedeOrderId());
                    if (assignPay.compareTo(hjhAccedeCheck.getAvailableInvestAccount()) == 1) {
                        logger.error("[" + accedeOrderId + "]" + " 承接支付金额" + assignPay + ">当前计划订单的剩余可投金额" + hjhAccedeCheck.getAvailableInvestAccount()
                                + "，承接操作不可，承接失败！");
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_ERR);
                        return false;
                    }

                    //调用银行自动购买债权接口
                    BankCallBean bean = this.autoCreditApi(credit, hjhAccede, hjhUserAuth,
                            assignPay, assignCapital, serviceFee,
                            tenderUsrcustid, sellerUsrcustid,
                            orderId, orderDate, isLast);

                    // 投资失败不回滚队列
                    if (bean == null) {
                        logger.error("[" + accedeOrderId + "]" + "用户投资失败,银行接口返回空,债转编号：" + credit.getBorrowNid() + "银行订单号：" + orderId);
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        // 不再操作队列
                        result = true;
                        return false;
                    }
                    if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                        logger.error("[" + accedeOrderId + "]" + "用户投资失败,银行接口返回 " + bean.getRetCode()
                                + " 债转编号：" + credit.getBorrowNid() + " 投资订单号：" + bean.getLogOrderId());
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        // 不再操作队列
                        result = true;
                        return false;
                    }
                    logger.info("[" + accedeOrderId + "]" + " 银行自动购买债权接口成功调用后  " + credit.getBorrowNid());

                    /** 4.5. 减去被投标的可投金额，部分承接时，余额推回队列	 */
                    ketouplanAmoust = setRedisList(ketouplanAmoust, redisBorrow, queueName, assignPay, "R");
                    // result = true 后继操作不再操作队列
                    logger.info("==投后[" + accedeOrderId + "]" + "自动承接债转标的" + redisBorrow.getBorrowNid() + "(银行承接成功！队列可承金额更新，不可撤销)");
                    logger.info("[" + accedeOrderId + "]" + "承后的可承金额：" + ketouplanAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可承余额：" + redisBorrow.getBorrowAccountWait());
                    // 不再操作队列
                    result = true;

                    /** 4.6. 更新同步数据库	 */
                    try {
                        this.amTradeClient.updateCreditForAutoTender(credit.getCreditNid(), hjhAccede.getAccedeOrderId(), hjhPlan.getPlanNid(),
                                bean, tenderUsrcustid, sellerUsrcustid, resultVO);
                    } catch (Exception e) {
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        logger.error("[" + accedeOrderId + "]对队列[" + queueName + "]的[" + redisBorrow.getBorrowNid() + "]的投资/承接操作出现 异常 被捕捉，HjhAccede状态更新为" + ORDER_STATUS_FAIL + "，请后台异常处理。"
                                , e);
                        return false;
                    }

                    /** 4.7. 完全承接时，结束债券  */
                    if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) == 0) {
                        //^^^^^^^^^^^^^^^^
                        HjhDebtCreditVO credittest = this.amTradeClient.selectHjhDebtCreditByCreditNid(redisBorrow.getBorrowNid());
                        logger.info("^^^^^^^^^^^^getSellerAuthCode前：" + credittest.getCreditCapitalWait());
                        //^^^^^^^^^^^^^^^^
                        //获取出让人投标成功的授权号
                        String sellerAuthCode = this.amTradeClient.getSellerAuthCode(credit.getSellOrderId(), credit.getSourceType());
                        if (sellerAuthCode == null) {
                            logger.info("[" + accedeOrderId + "]未取得出让人" + credit.getUserId() + "的债权类型" +
                                    credit.getSourceType() + "(1原始0原始)的授权码，结束债权失败。");
                        }
                        //^^^^^^^^^^^^^^^^
                         credittest = this.amTradeClient.selectHjhDebtCreditByCreditNid(redisBorrow.getBorrowNid());
                        logger.info("^^^^^^^^^^^^requestDebtEnd前：" + credittest.getCreditCapitalWait());
                        //^^^^^^^^^^^^^^^^
                        //调用银行结束债权接口
                        boolean ret = this.amTradeClient.requestDebtEnd(credit, sellerUsrcustid, sellerAuthCode) > 0 ? true : false;
                        if (!ret) {
                            logger.info("[" + accedeOrderId + "]被承接标的" + redisBorrow.getBorrowNid() + "被完全承接，银行结束债权失败。");
                        }
                        logger.info("[" + accedeOrderId + "]被承接标的" + redisBorrow.getBorrowNid() + "被完全承接，银行结束债权成功。");
                        //^^^^^^^^^^^^^^^^
                         credittest = this.amTradeClient.selectHjhDebtCreditByCreditNid(redisBorrow.getBorrowNid());
                        logger.info("^^^^^^^^^^^^selectHjhDebtCreditByCreditNid前：" + credittest.getCreditCapitalWait());
                        //^^^^^^^^^^^^^^^^
                        //银行结束债权后，更新债权表为完全承接
                        ret = this.amTradeClient.updateHjhDebtCreditForEnd(credit) > 0 ? true : false;
                        //^^^^^^^^^^^^^^^^
                         credittest = this.amTradeClient.selectHjhDebtCreditByCreditNid(redisBorrow.getBorrowNid());
                        logger.info("^^^^^^^^^^^^selectHjhDebtCreditByCreditNid后：" + credittest.getCreditCapitalWait());
                        //^^^^^^^^^^^^^^^^
                        if (!ret) {
                            logger.info("[" + accedeOrderId + "]银行结束债权后，更新债权表为完全承接失败。");
                        }
                    }
                } else if (borrowFlag.equals(RedisConstants.HJH_BORROW_INVEST)) {
                    /** 5. 自动投资原始标的（投资）	 */
                    logger.info("==投前[" + accedeOrderId + "]" + "自动投资原始标的" + redisBorrow.getBorrowNid());
                    logger.info("[" + accedeOrderId + "]" + "投前的可投金额：" + ketouplanAmoust + "，" + "投前的本组金额：" + groupAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());
                    /** 5.1. 投资用金额计算	 */
                    // 设置实际投资金额
                    // 原始标的： 标的本金-已投本金
                    BigDecimal realAmoust = groupAmoust;
                    if (realAmoust.compareTo(redisBorrow.getBorrowAccountWait()) >= 0) {
                        // 该标的/债转最后一笔投资
                        realAmoust = redisBorrow.getBorrowAccountWait();
                        isLast = true;
                    }

                    /** 5.2. 获取标的详情	 */
                    //根据borrowNid查询borrow表
                    BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(redisBorrow.getBorrowNid());
                    if (borrow == null) {
                        logger.error("[" + accedeOrderId + "]" + "标的号不存在 " + redisBorrow.getBorrowNid());
                        return false;
                    }

                    /** 5.3. 校验是否可以投资	 */
                    // 借款人和计划订单的投资人不能相同
                    if (borrow.getUserId().compareTo(hjhAccede.getUserId()) == 0) {
//						if (serialFaileCount >= CustomConstants.HJH_TENDER_SERIAL_FAILE_COUNT) {
//							// 连续10次失败后，换个计划订单投
//							logger.error("[" + accedeOrderId + "]" + "借款人/出让人和计划订单的投资人连续相同次数超过"+CustomConstants.HJH_TENDER_SERIAL_FAILE_COUNT+"次,跳过该计划订单");
//							return false;
//						}
                        logger.info("[" + accedeOrderId + "]" + "借款人(" + borrow.getUserId() + ")和计划订单的投资人(" + hjhAccede.getUserId() + ")不能相同");
                        String redisStr = JSON.toJSONString(redisBorrow);
                        RedisUtils.leftpush(queueName, redisStr);//标的推回队列头，再取标的投资。
                        serialFaileCount++;
                        result = true;
                        continue;
                    }
                    //防止钱不够也投资校验
                    HjhAccedeVO hjhAccedeCheck = this.amTradeClient.getHjhAccedeByAccedeOrderId(hjhAccede.getAccedeOrderId());
                    if (realAmoust.compareTo(hjhAccedeCheck.getAvailableInvestAccount()) == 1) {
                        logger.error("[" + accedeOrderId + "]" + " 投资支付金额" + realAmoust + ">当前计划订单的剩余可投金额" + hjhAccedeCheck.getAvailableInvestAccount()
                                + "，投资操作不可，投资失败！");
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_ERR);
                        return false;
                    }

                    /** 5.4. 调用银行自动投标申请接口	 */
                    logger.info("[" + accedeOrderId + "]" + " 银行自动投标申请接口调用前  " + borrow.getBorrowNid());

                    // 调用同步银行接口（投资）
                    BankCallBean bean = this.autotenderApi(borrow, hjhAccede, hjhUserAuth, realAmoust, tenderUsrcustid, isLast);

                    // 投资失败不回滚队列
                    if (bean == null) {
                        logger.error("[" + accedeOrderId + "]" + "用户投资失败,银行接口返回空,标的编号：" + borrow.getBorrowNid());
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        // 不再操作队列
                        result = true;
                        return false;
                    }
                    if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                        logger.error("[" + accedeOrderId + "]" + "用户投资失败,银行接口返回 " + bean.getRetCode()
                                + " 标的编号：" + borrow.getBorrowNid() + " 投资订单号：" + bean.getLogOrderId());
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        // 不再操作队列
                        result = true;
                        return false;
                    }

                    logger.info("[" + accedeOrderId + "]" + " 银行自动投标申请接口成功调用后  " + borrow.getBorrowNid());

                    /** 5.5. 减去被投标的可投金额，部分投资时，余额推回队列	 */
                    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
                    if (diversifyCount < 0) {
                        // 不分散投资（推回投资主队列）
                        ketouplanAmoust = setRedisList(ketouplanAmoust, redisBorrow, queueName, realAmoust, "R");
                    } else {
                        // 分散投资（推回投资临时队列）
                        ketouplanAmoust = setRedisList(ketouplanAmoust, redisBorrow, queueName + RedisConstants.HJH_SLASH_TMP, realAmoust
                                , "L");
                        diversifyCount += 1;
                        if (diversifyCount >= groupCount) {
                            // 最后一次分散投资，临时队列推回主队列
                            // ★临时队列有标的时，先推回主队列（恢复前次分散投资）★
                            RedisUtils.lpoprpush(queueName + RedisConstants.HJH_SLASH_TMP, queueName);
                        }
                    }
                    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end

                    // result = true 后继操作不再操作队列
                    logger.info("==投后[" + accedeOrderId + "]" + "自动投资原始标的" + redisBorrow.getBorrowNid() + "(银行投资冻结成功！队列可投金额更新，不可撤销)");
                    logger.info("[" + accedeOrderId + "]" + "投后的可投金额：" + ketouplanAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());
                    // 不再操作队列
                    result = true;

                    /** 5.6. 更新同步数据库	 */
                    // 单笔标的投资
                    try {
                        this.amTradeClient.updateBorrowForAutoTender(borrow.getBorrowNid(), hjhAccede.getAccedeOrderId(), bean);
                    } catch (Exception e) {
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        logger.error("[" + accedeOrderId + "]对队列[" + queueName + "]的[" + redisBorrow.getBorrowNid() + "]的投资/承接操作出现 异常 被捕捉，HjhAccede状态更新为" + ORDER_STATUS_FAIL + "，请后台异常处理。"
                                , e);
                        return false;
                    }
                } else {
                    logger.error("[" + accedeOrderId + "]" + "该计划没有可投标的！");
                    return false;
                }
            } catch (Exception e) {
                this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_ERR);
                logger.error("[" + accedeOrderId + "]对队列[" + queueName + "]的[" + redisBorrow.getBorrowNid() + "]的投资/承接操作出现 异常 被捕捉，HjhAccede状态更新为" + ORDER_STATUS_ERR + "，请后台异常处理。"
                        , e);
                return false;
            } finally {
                //删除债转中的redis，可以还款
                RedisUtils.del(RedisConstants.HJH_DEBT_SWAPING + borrowNidForCredit);
                if (result) {
                } else {
                    String redisStr = JSON.toJSONString(redisBorrow);
                    RedisUtils.rightpush(queueName, redisStr);//redis相应计划//可能放两遍
                    logger.info("[" + accedeOrderId + "]" + "剩余金额推回redis" + redisStr);
//				    break;
                }
            }
        }

        //投资完成更新计划明细
        // 如果计划成功，则更新计划表为投资完成
        if (ketouplanAmoust.compareTo(minAccountEnable) < 0) {
            // 0投资
            if (hjhAccede.getOrderStatus() == 0) {
                this.updateHjhAccedeOfOrderStatus(hjhAccede, 2);
            }
            // 1复投 不更新
        }

        return true;
    }

    /**
     * 更新加入订单状态
     *
     * @param hjhAccede
     * @param orderStatus
     */
    private int updateHjhAccedeOfOrderStatus(HjhAccedeVO hjhAccede, int orderStatus) {
        HjhAccedeVO newHjhAccede = new HjhAccedeVO();
        newHjhAccede.setId(hjhAccede.getId());
        newHjhAccede.setOrderStatus(orderStatus);
        newHjhAccede.setUpdateTime(GetDate.getDate());
        newHjhAccede.setUpdateUser(1);
        return this.amTradeClient.updateHjhAccedeByPrimaryKey(newHjhAccede);
    }

    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start

    /**
     * @param ketouplanAmoust
     * @param redisBorrow
     * @param queueName
     * @param realAmoust
     * @return
     */
    private BigDecimal setRedisList(BigDecimal ketouplanAmoust, RedisBorrow redisBorrow, String queueName,
                                    BigDecimal realAmoust, String pushFlag) {
        // 投资成功后减掉redis 钱
        redisBorrow.setBorrowAccountWait(redisBorrow.getBorrowAccountWait().subtract(realAmoust));
        ketouplanAmoust = ketouplanAmoust.subtract(realAmoust);

        // 银行成功后，如果标的可投金额非0，推回队列的尾部，标的可投金额为0，不再推回队列
        if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
            String redisStr = JSON.toJSONString(redisBorrow);
            if ("R".equals(pushFlag.toUpperCase())) {
                RedisUtils.rightpush(queueName, redisStr);
                logger.info("推回队列的尾部[" + queueName + "]r" + " : " + redisStr);
            } else {
                RedisUtils.leftpush(queueName, redisStr);
                logger.info("推回队列的头部l[" + queueName + "]" + " : " + redisStr);
            }

        }
        return ketouplanAmoust;
    }
    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end

    /**
     * 取得最小可投资/复投金额的条件
     *
     * @param hjhAccede
     * @return
     */
    private BigDecimal getMinAccountEnable(HjhAccedeVO hjhAccede) {
        BigDecimal minAccountEnable = null;
        if (hjhAccede.getOrderStatus() == 0) {
            // 0投资
            minAccountEnable = CustomConstants.HJH_TENDER_MIN_ACCOUNT;
        } else {
            // 1复投
            minAccountEnable = CustomConstants.HJH_RETENDER_MIN_ACCOUNT;
        }
        return minAccountEnable;
    }

    /**
     * 从队列中取得borrow
     *
     * @param queueName
     * @return JsonString
     */
    private String getBorrowFromQueue(String queueName) {
        String borrowStr = null;//标的内容JsonString
        borrowStr = RedisUtils.rpop(queueName);
        if (borrowStr == null) {
            logger.info("队列" + queueName + "中没有标的");
        } else {
            logger.info("队列" + queueName + "中取得标的");
        }
        return borrowStr;
    }

    /**
     * 调用同步银行接口（投资）
     *
     * @return
     */
    private BankCallBean autotenderApi(BorrowAndInfoVO borrow, HjhAccedeVO hjhAccede, HjhUserAuthVO hjhUserAuth, BigDecimal account, String tenderUsrcustid, boolean isLast) {
        BankCallBean bankResult = null;

        Integer userId = hjhAccede.getUserId();
        // 生成订单
        String orderId = GetOrderIdUtils.getOrderId2(userId);

        // 银行接口用bean
        BankCallBean bean = new BankCallBean(orderId, userId, BankCallConstant.TXCODE_BIDAUTO_APPLY, "自动投标申请", hjhAccede.getClient());
        bean.setAccountId(tenderUsrcustid);// 电子账号
        bean.setTxAmount(CustomUtil.formatAmount(account.toString()));// 交易金额
        bean.setProductId(borrow.getBorrowNid());// 标的号
        bean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_UNFREEZE);// 是否冻结金额
        bean.setContOrderId(hjhUserAuth.getAutoOrderId());// 签约订单号

        try {
            // 插入 自动投资临时表
            Integer idKey = this.insertBorrowTmp(borrow, null, hjhAccede, account, hjhUserAuth, bean, RedisConstants.HJH_BORROW_INVEST, isLast ? 1 : 0);

            // 调用银行接口
            logger.info(hjhAccede.getAccedeOrderId() + " 自动投资接口调用中  " + borrow.getBorrowNid() + " 请求订单号:" + orderId);
            bankResult = BankCallUtils.callApiBg(bean);

            // 更新 自动投资临时表
            boolean updResult = updateBorrowTmp(hjhAccede.getAccedeOrderId(), borrow.getBorrowNid(), bankResult);
            if (!updResult) {
                logger.error("更新自动投资临时表失败 idKey=" + idKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bankResult;
    }

    /**
     * 调用同步银行接口（自动债转）
     *
     * @return
     */
    private BankCallBean autoCreditApi(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhUserAuthVO hjhUserAuth,
                                       BigDecimal account, BigDecimal assignCapital, BigDecimal serviceFee,
                                       String tenderUsrcustid, String sellUsrcustid,
                                       String orderId, String orderDate, boolean isLast) {
        String borrowNid = credit.getBorrowNid();
        Integer userId = hjhAccede.getUserId();
        BankCallBean bankResult = null;

        // 取得当前债权在清算前已经发生债转的本金
        BigDecimal preCreditCapital = this.amTradeClient.getPreCreditCapital(credit);

        // 银行接口用bean
        BankCallBean bean = new BankCallBean(orderId, userId, BankCallConstant.TXCODE_CREDIT_AUTO_INVEST, "自动购买债权", hjhAccede.getClient());
        bean.setAccountId(tenderUsrcustid);// 电子账号
        bean.setTxAmount(CustomUtil.formatAmount(account.toString()));// 交易金额
        bean.setProductId(borrowNid);// 标的号
        bean.setTxFee(CustomUtil.formatAmount(serviceFee.toString()));    //手续费 交易金额*当前清算服务费率=债转服务费
        bean.setTsfAmount(CustomUtil.formatAmount(assignCapital.toString()));    //转让金额  实际承接本金
        bean.setForAccountId(sellUsrcustid);    //对手电子账号
        bean.setOrgOrderId(credit.getSellOrderId());    //原订单号 原投资订单号
        bean.setOrgTxAmount(CustomUtil.formatAmount((credit.getCreditCapital().add(preCreditCapital)).toString()));    //原交易金额  债转总本金
        bean.setContOrderId(hjhUserAuth.getAutoCreditOrderId());
        bean.setTxDate(orderDate);
        bean.setLogOrderDate(orderDate);

        try {
            // 插入 自动投资临时表
            Integer idKey = this.insertBorrowTmp(null, credit, hjhAccede, account, hjhUserAuth, bean, RedisConstants.HJH_BORROW_CREDIT, isLast ? 1 : 0);

            // 调用银行接口
            logger.info(hjhAccede.getAccedeOrderId() + " 银行自动投资接口("
                    + BankCallConstant.TXCODE_CREDIT_AUTO_INVEST + ")调用中  "
                    + credit.getCreditNid() + " 请求订单号:" + orderId);
            bankResult = BankCallUtils.callApiBg(bean);

            // 更新 自动投资临时表
            boolean updResult = updateBorrowTmp(hjhAccede.getAccedeOrderId(), credit.getCreditNid(), bankResult);
            if (!updResult) {
                logger.error("更新自动投资临时表失败 idKey=" + idKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bankResult;
    }

    /**
     * 插入 自动投资临时表
     *
     * @param borrow
     * @param hjhAccede
     * @param ketouplanAmoust
     * @param hjhUserAuth
     * @return
     */
    private Integer insertBorrowTmp(BorrowAndInfoVO borrow, HjhDebtCreditVO credit, HjhAccedeVO hjhAccede,
                                    BigDecimal ketouplanAmoust, HjhUserAuthVO hjhUserAuth,
                                    BankCallBean bean, String borrowFlag, int isLast) {

        HjhPlanBorrowTmpVO record = new HjhPlanBorrowTmpVO();
        record.setAccedeOrderId(hjhAccede.getAccedeOrderId());
        record.setPlanNid(hjhAccede.getPlanNid());
        record.setUserId(hjhAccede.getUserId());
        record.setUserName(hjhAccede.getUserName());
        record.setAccedeAccount(hjhAccede.getAccedeAccount());
        record.setAlreadyInvest(hjhAccede.getAlreadyInvest());
        record.setAccount(ketouplanAmoust);
        if (borrowFlag.equals(RedisConstants.HJH_BORROW_CREDIT)) {
            // 汇计划自动投资债转
            record.setInstCode(credit.getInstCode());
            record.setBorrowNid(credit.getCreditNid());//债转编号
            record.setBorrowAccount(credit.getCreditCapital());//债转总本金
            record.setBorrowPeriod(credit.getRemainDays());//剩余天数
            record.setBorrowStyle(credit.getBorrowStyle());
            record.setBorrowType(1);
            record.setSellUserId(credit.getUserId());
            record.setSellOrderId(credit.getSellOrderId());
        } else {
            // 汇计划自动投资原始标的
            record.setInstCode(borrow.getInstCode());
            record.setBorrowNid(borrow.getBorrowNid());
            record.setBorrowAccount(borrow.getAccount());
            record.setBorrowPeriod(borrow.getBorrowPeriod());
            record.setBorrowStyle(borrow.getBorrowStyle());
            record.setBorrowType(0);
            record.setSellUserId(null);
            record.setSellOrderId(null);
        }
        record.setStatus(0);
        record.setOrderId(bean.getOrderId());
        record.setIsLast(isLast);
        record.setCreateUserId(1);
        Date nowDate = GetDate.getDate();
        record.setCreateTime(nowDate);
        record.setUpdateUserId(1);
        record.setUpdateTime(nowDate);

        this.amTradeClient.insertHjhPlanBorrowTmp(record);

        return record.getId();
    }

    /**
     * 更新 自动投资临时表
     *
     * @param accedeOrderId
     * @param borrowNid
     * @param bankResult
     * @return
     */
    private boolean updateBorrowTmp(String accedeOrderId, String borrowNid, BankCallBean bankResult) {
        HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO = new HjhPlanBorrowTmpVO();
        hjhPlanBorrowTmpVO.setAccedeOrderId(accedeOrderId);
        hjhPlanBorrowTmpVO.setBorrowNid(borrowNid);
        hjhPlanBorrowTmpVO.setStatus(1);
        if (bankResult == null) {
            hjhPlanBorrowTmpVO.setRespCode("");
            hjhPlanBorrowTmpVO.setRespDesc("银行无返回结果");
        } else {
            hjhPlanBorrowTmpVO.setRespCode(bankResult.getRetCode());
            hjhPlanBorrowTmpVO.setRespDesc(bankResult.getRetMsg());
        }
        hjhPlanBorrowTmpVO.setUpdateTime(GetDate.getDate());
        return this.amTradeClient.updateHjhPlanBorrowTmp(hjhPlanBorrowTmpVO) > 0 ? true : false;
    }
}
