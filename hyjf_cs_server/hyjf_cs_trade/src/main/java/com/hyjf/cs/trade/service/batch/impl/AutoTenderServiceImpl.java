/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.CheckResult;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcPeriodResultVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.AutoTenderService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.*;

/**
 * 自动出借
 *
 * @author liubin
 * @version AutoTenderServiceImpl, v0.1 2018/6/28 14:09
 */
@Service
public class AutoTenderServiceImpl extends BaseTradeServiceImpl implements AutoTenderService {
    private String logHeader = "【智投自动出借任务】";
    private static final Integer OK = 1; // 投资结果是否成功(1：出借成功)
    private static final Integer FAIL = 2;// 投资结果是否成功(2：出借失败)
    private static final Integer NO_TENDER = 0;// 投资结果是否成功(0:未出借)

    @Value("${hyjf.bank.instcode}")
    private String instCode;
    @Value("${hyjf.bank.bankcode}")
    private String bankCode;

    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 取得自动出借用加入计划列表
     *
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectPlanJoinList() {
        return this.amTradeClient.selectPlanJoinList();
    }

    /**
     * 汇计划加入订单 自动出借/复投
     * 0. 取得计划信息
     * 1. 取得出借人信息（授权账户等）
     * 2. 有可投金额循环出借
     * 3. 从队列中取得标的
     * 4. 自动出借债转标的（承接）
     * 4.1. 债转用金额计算
     * 4.2. 获取债转详情
     * 4.3. 校验是否可以债转
     * 4.4. 调用银行自动购买债权接口
     * 4.5. 减去被投标的可投金额，部分承接时，余额推回队列
     * 4.6. 更新同步数据库
     * 4.7. 完全承接时，结束债券
     * 5. 自动出借原始标的（出借）
     * 5.1. 出借用金额计算
     * 5.2. 获取标的详情
     * 5.3. 校验是否可以出借
     * 5.4. 调用银行自动投标申请接口
     * 5.5. 减去被投标的可投金额，部分出借时，余额推回队列
     * 5.6. 更新同步数据库
     *
     * @param hjhAccede
     * @return
     */
    @Override
    public Integer autoTenderForOneAccede(HjhAccedeVO hjhAccede) {
        String logMsgHeader = logHeader + "智投订单号[" + hjhAccede.getAccedeOrderId() + "]";

        //汇计划加入订单号
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        // 计划订单加入计划金额
        BigDecimal accedeAccount = hjhAccede.getAccedeAccount();
        // 计划订单可投金额
        BigDecimal ketouplanAmoust = hjhAccede.getAvailableInvestAccount();

        //银行交易前，异常订单状态设定，和系统异常
        final Integer ORDER_STATUS_ERR = hjhAccede.getOrderStatus() + 90;
        //银行交易后，异常订单状态设定
        final Integer ORDER_STATUS_FAIL = hjhAccede.getOrderStatus() + 80;
        //银行交易前，初始订单状态设定
        final Integer ORDER_STATUS_INIT = hjhAccede.getOrderStatus() + 70;
        //一个计划订单的连续失败次数
        int serialFaileCount = 0;

        // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 start
        //是否分散出借
        int diversifyCount = -1; //非分散出借
        //已出借笔数
        int investCountForLog = hjhAccede.getInvestCounts();
        // 每组可投金额（分散出借的最小分散金额）
        BigDecimal groupAmoust = ketouplanAmoust;
        // 最小分散组数
        int groupCount = 1;
        // 标的队列名称
        // 原始标的队列
        String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + hjhAccede.getPlanNid();
        // ★临时队列有标的时，先推回队列（主）（恢复前次分散出借）★
        RedisUtils.lpoprpush(queueName + RedisConstants.HJH_SLASH_TMP, queueName);
        // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 end

        if (hjhAccede.getOrderStatus() == 0) {
            //0自动投标中
            logger.info("****" + logMsgHeader + "开始自动出借,订单状态:" + hjhAccede.getOrderStatus() + "************");
            // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 start
            diversifyCount = 0; //初始分散出借 （只有出借原始标的（非复投）时，使用分散出借）
            // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 end
        } else {
            //2自动投标成功或者3锁定中
            logger.info("****" + logMsgHeader + "开始自动复投,订单状态:" + hjhAccede.getOrderStatus() + "************");
        }
        logger.info(logMsgHeader + "加入计划金额：" + accedeAccount.toString()+ "，初始可投金额：" + ketouplanAmoust.toString());

        /** 0. 取得计划信息 */
        HjhPlanVO hjhPlan = amTradeClient.doGetPlanByNid(hjhAccede.getPlanNid());

        /** 1. 取得出借人信息（授权账户等） */
        //获取出借授权码
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(hjhAccede.getUserId());
        if (hjhUserAuth == null || StringUtils.isEmpty(hjhUserAuth.getAutoOrderId())) {
            logger.error(logMsgHeader + "未获取到出借授权码  " + hjhAccede.getUserId());
            return FAIL;
        }
        if (StringUtils.isEmpty(hjhUserAuth.getAutoCreditOrderId())) {
            logger.error(logMsgHeader + "未获取到债转授权码  " + hjhAccede.getUserId());
            return FAIL;
        }
        //获取出借账户
        BankOpenAccountVO bankOpenAccount = this.amUserClient.selectBankAccountById(hjhAccede.getUserId());
        if (bankOpenAccount == null) {
            logger.error(logMsgHeader + "用户没开户 " + hjhAccede.getUserId());
            return FAIL;
        }

        UserInfoVO usersInfo = amUserClient.findUsersInfoById(hjhAccede.getUserId());
        if (null != usersInfo) {
            String roleIsOpen = systemConfig.getRoleIsopen();
            if(org.apache.commons.lang3.StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
                if (usersInfo.getRoleId() != 1) {// 非出借用户
                    logger.error(logMsgHeader + "仅限出借人进行出借 " + hjhAccede.getUserId());
                    return FAIL;
                }
            }
        }
        String tenderUsrcustid = bankOpenAccount.getAccount();//获取江西银行电子账号

        /** 2. 有可投金额循环出借	 */
        // 计划订单的可投金额 >= minAccountEnable 进行出借(0.01)/复投(10.00)
        // 计划订单的可投金额 < minAccountEnable 该计划订单出借/复投结束
        BigDecimal minAccountEnable = getMinAccountEnable(hjhAccede);
        while (ketouplanAmoust.compareTo(minAccountEnable) >= 0) {
            // add 汇计划三期 汇计划自动出借(出借笔数累计) liubin 20180515 start
            logger.info(logMsgHeader + "投前累计出借笔数：" + investCountForLog + "============");
            investCountForLog += 1;
            // add 汇计划三期 汇计划自动出借(出借笔数累计) liubin 20180515 end

            // ketouplanAmoust小于1元时报警告信息
            if (ketouplanAmoust.compareTo(new BigDecimal(1)) < 0) {
                logger.warn(logMsgHeader + "的可出借金额为" + ketouplanAmoust.toString() + ",小于1元");
            }

            /** 3. 从队列中取得标的	 */
            /*******************取一个标的************************/
            RedisBorrow redisBorrow = null;

            String borrowStr = null;//标的的JsonString
            String borrowFlag = null;//标的类型（债转标，原始标）

            // 出让人/出借人相同的校验
            logger.info(logMsgHeader + "连续相同次数" + serialFaileCount + "次");
            // 连续10次出投人相同后，换个计划订单投
            if (serialFaileCount >= CustomConstants.HJH_SERIAL_FAILE_COUNT) {
                logger.error(logMsgHeader + "借款人/出让人和计划订单的出借人连续相同次数超过" + CustomConstants.HJH_SERIAL_FAILE_COUNT + "次,跳过该计划订单");
                return NO_TENDER;
            }
            // 取债转标的（优先） (连续5次不能投债转标时,取原始标的)
            if (serialFaileCount < CustomConstants.HJH_ASSIGN_SERIAL_FAILE_COUNT) {
                queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + hjhAccede.getPlanNid();
                borrowStr = getBorrowFromQueue(queueName);
                borrowFlag = RedisConstants.HJH_BORROW_CREDIT;
            } else {
                logger.info(logMsgHeader + "出让人和计划订单的出借人连续相同次数超过" + CustomConstants.HJH_ASSIGN_SERIAL_FAILE_COUNT + "次,只投原始标的");
            }

            // 取原始标的(无债转标的时)
            if (borrowStr == null) {
                queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + hjhAccede.getPlanNid();
                // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 start
                // ****分散出借主规则****
                // 初始分散出借时
                if (diversifyCount == 0) {
                    // 取得最小分散组数（计划最小分散笔数 & 队列中的标的数 取小的）
                    groupCount = (int) DigitalUtils.min((long) hjhPlan.getMinInvestCounts(), RedisUtils.llen(queueName));
                    groupCount = groupCount < 1 ? 1 : groupCount;//小于1时，组数取1
                    // 计算每组出借金额（取百位）
                    groupAmoust = ketouplanAmoust.divide(new BigDecimal(groupCount), -2, BigDecimal.ROUND_DOWN);
                }
                // 分散出借时（突然插入债转可能可投余额不够）
                if (diversifyCount >= 0) {
                    logger.info(logMsgHeader + "开始分散出借。。。组数：" + groupCount + ", 每组金额：" + groupAmoust
                            + ", 当前组：" + (diversifyCount + 1));
                    groupAmoust = DigitalUtils.min(groupAmoust, ketouplanAmoust);
                }
                // 分散组数=1 或者 超出分散组数 或者 groupAmoust<100 的出借不再分散
                if (groupCount == 1 || diversifyCount == -1 || diversifyCount + 1 > groupCount || groupAmoust.compareTo(new BigDecimal(100)) < 0) {
                    groupAmoust = ketouplanAmoust;
                    diversifyCount = -1;
                }
                // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 end
                borrowStr = getBorrowFromQueue(queueName);
                borrowFlag = RedisConstants.HJH_BORROW_INVEST;
            }

            // 没有可出借标的
            if (borrowStr == null || borrowFlag == null) {
                return NO_TENDER;
            }

            // 转为borrow对象
            redisBorrow = JSON.parseObject(borrowStr, RedisBorrow.class);

            // 标的编号为空
            if (redisBorrow.getBorrowNid() == null) {
                logger.error(logMsgHeader + "队列的标的编号为空！");
                return FAIL;
            }

            // 标的无可投余额
            if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) < 0) {
                logger.error(logMsgHeader + redisBorrow.getBorrowNid() + " 标的可投金额为 " + redisBorrow.getBorrowAccountWait());
                return FAIL;
            }
            /*******************************************/

            boolean noPushRedis = true; //finally不再推回队列
            boolean isLast = false; // 是否是最后一笔
            String borrowNidForCredit = "";
            try {
                if (borrowFlag.equals(RedisConstants.HJH_BORROW_CREDIT)) {
                    /** 4. 自动出借债转标的（承接）	 */
                    logger.info(logMsgHeader + "自动承接债转标的" + redisBorrow.getBorrowNid() + "--------");
                    logger.info(logMsgHeader + "承前的可投金额：" + ketouplanAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());
                    /** 4.1. 本次债转实际出借金额计算	 */
                    // 出借金额和待投金额，谁小用谁
                    // 债转标的： 清算时公允价值-已投本金和已投垫付利息
                    BigDecimal yujiAmoust = ketouplanAmoust;
                    if (yujiAmoust.compareTo(redisBorrow.getBorrowAccountWait()) >= 0) {
                        // 该标的/债转最后一笔出借
                        yujiAmoust = redisBorrow.getBorrowAccountWait();
                        isLast = true;
                    }

                    /** 4.2. 获取债转标的详情	 */
                    HjhDebtCreditVO credit = this.amTradeClient.doSelectHjhDebtCreditByCreditNid(redisBorrow.getBorrowNid()); // 从主库
                    if (credit == null) {
                        throw new Exception(logMsgHeader + "债转号不存在 " + redisBorrow.getBorrowNid());
                    }

                    /** 4.3. 校验是否可以债转	 */
                    // 债权的转让人，和计划订单的出借人不能相同
                    if (credit.getUserId().compareTo(hjhAccede.getUserId()) == 0) {
                        logger.warn(logMsgHeader + "债权的转让人(" + credit.getUserId() + ")和计划订单的出借人(" + hjhAccede.getUserId() + ")不能相同");
                        String redisStr = JSON.toJSONString(redisBorrow);
                        RedisUtils.leftpush(queueName, redisStr);//标的推回队列头，再取标的出借。
                        logger.info(logMsgHeader + "Redis:" + queueName + "(l)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
                        serialFaileCount++;
                        continue;
                    }

                    // credit的CreditStatus = 3 时债转停止，不再推回队列，取下一个队列中的标的
                    if (credit.getCreditStatus().compareTo(3) == 0) {
                        //3承接终止
                        logger.warn(logMsgHeader + "债转标的" + redisBorrow.getBorrowNid() + "被停止债转");
                        continue;
                    }

                    // 债转加redis锁，禁止还款
                    boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrowNidForCredit, redisBorrow.getBorrowNid(), 300);
                    if (!tranactionSetFlag) {//设置失败
                        //承接终止
                        logger.warn(logMsgHeader + "债转标的" + redisBorrow.getBorrowNid() + "发生还款（Redis），被停止债转。");
                        continue;
                    }

                    /** 4.4. 调用银行自动购买债权接口	 */
                    // 调用银行接口准备参数
                    //获取出让用户的江西银行电子账号
                    BankOpenAccountVO sellerBankOpenAccount = this.amUserClient.selectBankAccountById(credit.getUserId());
                    if (sellerBankOpenAccount == null) {
                        throw new Exception(logMsgHeader + "转出用户没开户 " + credit.getUserId() + "，标出错。");
                    }
                    String sellerUsrcustid = sellerBankOpenAccount.getAccount();//出让用户的江西银行电子账号

                    /** 4.7. 完全承接时，结束债券  */
                    if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) == 0) {
                        requestHjhCreditEnd(accedeOrderId, redisBorrow.getBorrowNid(), sellerUsrcustid);
                        logger.info(logMsgHeader + "被承接标的" + redisBorrow.getBorrowNid() + "银行结束债权成功。");
                        continue;
                    }

                    // 生成调用银行接口订单号
                    String orderId = GetOrderIdUtils.getOrderId2(hjhAccede.getUserId());
                    // 生成调用银行接口日期
                    String orderDate = GetOrderIdUtils.getTxDate();

                    // 计算计划债转实际金额 保存creditTenderLog表
                    HjhCreditCalcResultVO resultVO = this.amTradeClient.saveCreditTenderLog(credit, hjhAccede, orderId, orderDate, yujiAmoust, isLast);
                    if (Validator.isNull(resultVO)) {
                        noPushRedis = false;//finally推回队列
                        throw new Exception(logMsgHeader + "/finally推回队列/ 保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId());
                    }

                    //承接支付金额
                    BigDecimal assignPay = resultVO.getAssignPay();
                    //承接本金
                    BigDecimal assignCapital = resultVO.getAssignCapital();
                    //承接服务费
                    BigDecimal serviceFee = resultVO.getServiceFee();
                    logger.info(logMsgHeader + "承接用计算完成\n" + resultVO.toLog());

                    // add 出让人没有缴费授权临时对应（不收取服务费） liubin 20181113 start
//                    if(!this.amTradeClient.checkAutoPayment(credit.getCreditNid())){
//                        serviceFee = BigDecimal.ZERO;//承接服务费
//                        resultVO.setServiceFee(BigDecimal.ZERO);
//                        logger.warn(logMsgHeader + "债权转让人未做缴费授权,该笔债权的承接服务费置为" + serviceFee);
//                    }
                    // add 出让人没有缴费授权临时对应（不收取服务费） liubin 20181113 end

                    // 校验债转用的计算金额是否有异常数据
                    CheckResult checkResult = checkHjhCreditCalcResult(resultVO);
                    if (!checkResult.getResultBool()) {
                        throw new Exception(logMsgHeader + checkResult.getResultMsg());
                    }

                    //防止钱不够也承接校验(投资人的问题)
                    HjhAccedeVO hjhAccedeCheck = this.amTradeClient.getHjhAccedeByAccedeOrderId(hjhAccede.getAccedeOrderId());
                    if (assignPay.compareTo(hjhAccedeCheck.getAvailableInvestAccount()) == 1) {
                        noPushRedis = false;//finally推回队列
                        throw new Exception(logMsgHeader + "/finally推回队列/ 承接支付金额" + assignPay + ">当前计划订单的剩余可投金额" + hjhAccedeCheck.getAvailableInvestAccount()
                                + "，承接操作不可，承接失败！");
                    }

                    logger.info(logMsgHeader + "#### 开始 调用银行自动购买债权接口（承接）" + credit.getCreditNid() + "####");

                    // 智投订单状态改为初始状态7X（防止银行成功，am服务挂了，数据消失）
                    this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_INIT);

                    //调用银行自动购买债权接口
                    BankCallBean bean = this.autoCreditApi(credit, hjhAccede, hjhUserAuth,
                            assignPay, assignCapital, serviceFee,
                            tenderUsrcustid, sellerUsrcustid,
                            orderId, orderDate, isLast);

                    // 出借失败不回滚队列
                    if (bean == null) {
                        logger.error(logMsgHeader + "用户出借失败,银行接口返回空,债转编号：" + credit.getBorrowNid() + "银行订单号：" + orderId);
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        return FAIL;
                    }
                    if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                        logger.error(logMsgHeader + "用户出借失败,银行接口返回 " + bean.getRetCode()
                                + " 债转编号：" + credit.getBorrowNid() + " 出借订单号：" + bean.getLogOrderId());
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        return FAIL;
                    }
                    logger.info(logMsgHeader + "#### 调用银行自动购买债权接口（承接）成功 " + credit.getBorrowNid() + "####");

                    // add 合规数据上报 埋点 liubin 20181122 start
                    JSONObject params = new JSONObject();
                    params.put("assignOrderId", bean.getOrderId());
                    params.put("flag", "2");//1（散）2（智投）
                    params.put("status", "1"); //1承接（每笔）
                    // 推送数据到MQ 承接（每笔）
                    commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                    // add 合规数据上报 埋点 liubin 20181122 end

                    /** 4.5. 更新同步数据库	 */
                    try {
                        this.amTradeClient.updateCreditForAutoTender(credit.getCreditNid(), hjhAccede.getAccedeOrderId(), hjhPlan.getPlanNid(),
                                bean, tenderUsrcustid, sellerUsrcustid, resultVO);
                        logger.info(logMsgHeader + "#### BD更新承接数据成功" + credit.getCreditNid() + "####");
                        logger.info("删除临时表：hjhPlanBorrowTmp，（CreditNid：" + credit.getCreditNid() + "，AccedeOrderId：" + hjhAccede.getOrderStatus() + "）");
                    } catch (Exception e) {
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        logger.error(logMsgHeader + "对队列[" + queueName + "]的[" + redisBorrow.getBorrowNid() + "]的出借/承接操作出现 异常 被捕捉，HjhAccede状态更新为" + ORDER_STATUS_FAIL + "，请后台异常处理。"
                                , e);
                        return FAIL;
                    }

                    /** 4.6. 更新被投标的待投金额，部分承接时，余额推回队列	 (20180719改银行和表更新都成功才推回队列)	 */
                    ketouplanAmoust = setRedisList(ketouplanAmoust, redisBorrow, queueName, assignPay, "R");
                    // result = true 后继操作不再操作队列
                    logger.info(logMsgHeader + "自动承接债转标的" + redisBorrow.getBorrowNid() + "(银行承接成功！队列可承金额更新，不可撤销)");
                    logger.info(logMsgHeader + "承后的可承金额：" + ketouplanAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可承余额：" + redisBorrow.getBorrowAccountWait());

                    /** 4.7. 完全承接时，结束债券，更新债权表为完全承接  */
                    if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) == 0) {
                        requestHjhCreditEnd(accedeOrderId, credit.getCreditNid(), sellerUsrcustid);
                        logger.info(logMsgHeader + "被承接标的" + credit.getCreditNid() + "银行结束债权成功。");
                    }
                } else if (borrowFlag.equals(RedisConstants.HJH_BORROW_INVEST)) {
                    /** 5. 自动投资原始标的（投资）	 */
                    logger.info(logMsgHeader + "投前 自动出借原始标的" + redisBorrow.getBorrowNid()+"--------");
                    logger.info(logMsgHeader + "投前的可投金额：" + ketouplanAmoust + "，" + "投前的本组金额：" + groupAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());

                    /** 5.1. 本次出借金额计算(出借金额和待投金额用小的)	 */
                    BigDecimal realAmoust = groupAmoust;
                    if (realAmoust.compareTo(redisBorrow.getBorrowAccountWait()) >= 0) {
                        // 该标的/债转最后一笔出借
                        realAmoust = redisBorrow.getBorrowAccountWait();
                        isLast = true;
                    }

                    /** 5.2. 获取标的详情	 */
                    //根据borrowNid查询borrow表
                    BorrowAndInfoVO borrow = amTradeClient.doSelectBorrowByNid(redisBorrow.getBorrowNid());
                    if (borrow == null) {
                        throw new Exception(logMsgHeader + "标的号不存在 " + redisBorrow.getBorrowNid());
                    }

                    /** 5.3. 校验是否可以出借	 */
                    // 防止爆标校验
                    if (borrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) == 0){
                        logger.warn(logMsgHeader + "(防止爆标)该标的：" + borrow.getBorrowNid() + "已经投资完成。"
                                + "表可投金额为：" + borrow.getBorrowAccountWait()
                                + "redis可投金额为：" + redisBorrow.getBorrowAccountWait());
                        continue;
                    }
                    if (borrow.getBorrowAccountWait().compareTo(redisBorrow.getBorrowAccountWait()) != 0){
                        logger.error(logMsgHeader + "(防止爆标)该标的：" + borrow.getBorrowNid() + "DB和redis可投金额不一致。"
                                + "表可投金额为：" + borrow.getBorrowAccountWait()
                                + "redis可投金额为：" + redisBorrow.getBorrowAccountWait());
                        String redisStr = JSON.toJSONString(redisBorrow);
                        logger.error(logMsgHeader + "对应：1.确认金额不一致原因后，redis的"+queueName+"插入“"+redisStr+"”。2.智投订单状态 90改成0");
                        throw new Exception(logMsgHeader + "(防止爆标)该标的：" + borrow.getBorrowNid() + "DB和redis可投金额不一致。"
                                + "表可投金额为：" + borrow.getBorrowAccountWait()
                                + "redis可投金额为：" + redisBorrow.getBorrowAccountWait());
                    }

                    // 借款人和计划订单的出借人不能相同
                    if (borrow.getUserId().compareTo(hjhAccede.getUserId()) == 0) {
                        logger.info(logMsgHeader + "借款人(" + borrow.getUserId() + ")和计划订单的出借人(" + hjhAccede.getUserId() + ")不能相同");
                        String redisStr = JSON.toJSONString(redisBorrow);
                        RedisUtils.leftpush(queueName, redisStr);//标的推回队列头，再取标的出借。
                        logger.info(logMsgHeader + "Redis:" + queueName + "(l)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
                        serialFaileCount++;
                        continue;
                    }
                    //防止钱不够也出借校验(投资人的问题)
                    HjhAccedeVO hjhAccedeCheck = this.amTradeClient.getHjhAccedeByAccedeOrderId(hjhAccede.getAccedeOrderId());
                    if (realAmoust.compareTo(hjhAccedeCheck.getAvailableInvestAccount()) == 1) {
                        noPushRedis = false;//finally推回队列
                        throw new Exception(logMsgHeader + "/finally推回队列/ 出借支付金额" + realAmoust + ">当前计划订单的剩余可投金额" + hjhAccedeCheck.getAvailableInvestAccount()
                                + "，出借操作不可，出借失败！");
                    }

                    /** 5.4. 调用银行自动投标申请接口	 */
                    logger.info(logMsgHeader + "#### 开始 调用银行自动投标申请接口（出借）" + borrow.getBorrowNid()+ "####");
                    // 智投订单状态改为初始状态7X（防止银行成功，am服务挂了，数据消失）
                    this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_INIT);

                    // 调用同步银行接口（出借）
                    BankCallBean bean = this.autotenderApi(borrow, hjhAccede, hjhUserAuth, realAmoust, tenderUsrcustid, isLast);

                    // 出借失败不回滚队列
                    if (bean == null) {
                        logger.error(logMsgHeader + "用户出借失败,银行接口返回空,标的编号：" + borrow.getBorrowNid());
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        return FAIL;
                    }
                    if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                        logger.error(logMsgHeader + "用户出借失败,银行接口返回 " + bean.getRetCode()
                                + " 标的编号：" + borrow.getBorrowNid() + " 出借订单号：" + bean.getLogOrderId());
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        return FAIL;
                    }

                    logger.info(logMsgHeader + "#### 调用银行自动投标申请接口（出借）成功 " + borrow.getBorrowNid() + "####");

                    // add by liushouyi nifa2 20181204 start
                    if(redisBorrow.getBorrowAccountWait().compareTo(realAmoust) == 0){
                        // 满标发送满标状态埋点
                        // 发送发标成功的消息队列到互金上报数据
                        JSONObject params = new JSONObject();
                        params.put("borrowNid", borrow.getBorrowNid());
                        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTED_TAG, UUID.randomUUID().toString(), params),
                                MQConstant.HG_REPORT_DELAY_LEVEL);
                    }
                    // add by liushouyi nifa2 20181204 end

                    /** 5.5. 更新同步数据库	 */
                    // 单笔标的出借
                    try {
                        this.amTradeClient.updateBorrowForAutoTender(borrow.getBorrowNid(), hjhAccede.getAccedeOrderId(), bean);
                        logger.info(logMsgHeader + "#### BD更新出借数据成功" + borrow.getBorrowNid() + "####");
                        logger.info(logMsgHeader + "删除临时表：hjhPlanBorrowTmp，（BorrowNid：" + borrow.getBorrowNid() + "，AccedeOrderId：" + hjhAccede.getOrderStatus() + "）");
                    } catch (Exception e) {
                        this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_FAIL);
                        logger.error(logMsgHeader + "对队列[" + queueName + "]的[" + redisBorrow.getBorrowNid() + "]的出借/承接操作出现 异常 被捕捉，HjhAccede状态更新为" + ORDER_STATUS_FAIL + "，请后台异常处理。"
                                , e);
                        return FAIL;
                    }

                    /** 5.6. 减去被投标的可投金额，部分出借时，余额推回队列	 */
                    // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 start
                    if (diversifyCount < 0) {
                        // 不分散出借（推回出借主队列）
                        ketouplanAmoust = setRedisList(ketouplanAmoust, redisBorrow, queueName, realAmoust, "R");
                    } else {
                        // 分散出借（推回出借临时队列）
                        ketouplanAmoust = setRedisList(ketouplanAmoust, redisBorrow, queueName + RedisConstants.HJH_SLASH_TMP, realAmoust
                                , "L");
                        diversifyCount += 1;
                        if (diversifyCount >= groupCount) {
                            // 最后一次分散出借，临时队列推回主队列
                            // ★临时队列有标的时，先推回主队列（恢复前次分散出借）★
                            RedisUtils.lpoprpush(queueName + RedisConstants.HJH_SLASH_TMP, queueName);
                        }
                    }
                    // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 end

                    logger.info(logMsgHeader + "==投后, 自动出借原始标的" + redisBorrow.getBorrowNid() + "(银行出借冻结成功！队列可投金额更新，不可撤销)");
                    logger.info(logMsgHeader + "投后的可投金额：" + ketouplanAmoust + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());

                } else {
                    logger.error(logMsgHeader + "该计划没有可投标的！");
                    return FAIL;
                }
            } catch (Exception e) {
                this.updateHjhAccedeOfOrderStatus(hjhAccede, ORDER_STATUS_ERR);
                logger.error(logMsgHeader + "对队列[" + queueName + "]的[" + redisBorrow.getBorrowNid() + "]的出借/承接操作出现 未知异常 被捕捉，HjhAccede状态更新为" + ORDER_STATUS_ERR + "，请后台异常处理。"
                        , e);
                return FAIL;
            } finally {
                //删除债转中的redis，可以还款
                RedisUtils.del(RedisConstants.HJH_DEBT_SWAPING + borrowNidForCredit);
                if (noPushRedis) {
                    String redisStr = JSON.toJSONString(redisBorrow);
                    logger.info(logMsgHeader + "不再推回队列!!!!!!!!!!"+ redisStr);
                } else {
                    String redisStr = JSON.toJSONString(redisBorrow);
                    RedisUtils.rightpush(queueName, redisStr);//redis相应计划//可能放两遍
                    logger.info(logMsgHeader + "Redis:" + queueName + "(r)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
                    logger.info(logMsgHeader + "剩余金额推回redis!!!!!!!!!!" + redisStr);
//				    break;
                }
            }
        }

        //出借完成更新计划明细
        // 如果计划成功，则更新计划表为出借完成
        if (ketouplanAmoust.compareTo(minAccountEnable) < 0) {
            // 0出借
            if (hjhAccede.getOrderStatus() == 0) {
                this.updateHjhAccedeOfOrderStatus(hjhAccede, 2);
            }
            // 1复投 不更新
        }

        return OK;
    }

    /**
     * 校验债转用的计算金额是否有异常数据
     * @param resultVO
     * @return
     */
    private CheckResult checkHjhCreditCalcResult(HjhCreditCalcResultVO resultVO) {
        // 启动限制开关 redis.check_hjh_credit_calc_flag = 0 时，不执行校验。
        if ( RedisUtils.get(RedisConstants.CHECK_HJH_CREDIT_CALC_FLAG) != null
                && RedisUtils.get(RedisConstants.CHECK_HJH_CREDIT_CALC_FLAG).equals("0") ) {
            return new CheckResult(true);
        }

        logger.debug(logHeader + "校验债转用的计算金额是否有异常数据 开启！");

        // 承接的应该大于等于支付的
        if(resultVO.getAssignPay().compareTo(resultVO.getAssignAccount()) > 0){
            return new CheckResult(false, "校验债转用的计算金额：承接支付金额 > 承接本金+利息");
        }
        // 承接服务率不能大于1
        if(resultVO.getServiceApr().compareTo(BigDecimal.ONE) >= 0){
            return new CheckResult(false, "校验债转用的计算金额：承接支付金额 > 承接本金+利息");
        }
        // 校验通过
        return new CheckResult(true);
//        result = " 承接总额:" + assignAccount +
//                "\n,承接本金:" + assignCapital +
//                "\n,承接利息:" + assignInterest +
//                "\n,承接支付金额:" + assignPay +
//                "\n,承接垫付利息:" + assignAdvanceMentInterest +
//                "\n,承接延期利息:" + assignRepayDelayInterest +
//                "\n,承接逾期利息:" + assignRepayLateInterest +
//                "\n,承接服务率:" + serviceApr +
//                "\n,承接服务费:" + serviceFee +
//                "\n,分期数据结果:" + assignResult;
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

    // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 start

    /**
     * @param ketouplanAmoust
     * @param redisBorrow
     * @param queueName
     * @param realAmoust
     * @return
     */
    private BigDecimal setRedisList(BigDecimal ketouplanAmoust, RedisBorrow redisBorrow, String queueName,
                                    BigDecimal realAmoust, String pushFlag) {
        // 投标成功后减掉redis 钱
        redisBorrow.setBorrowAccountWait(redisBorrow.getBorrowAccountWait().subtract(realAmoust));
        ketouplanAmoust = ketouplanAmoust.subtract(realAmoust);

        // 银行成功后，如果标的可投金额非0，推回队列的尾部，标的可投金额为0，不再推回队列
        if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
            String redisStr = JSON.toJSONString(redisBorrow);
            if ("R".equals(pushFlag.toUpperCase())) {
                RedisUtils.rightpush(queueName, redisStr);
                logger.info(logHeader + "Redis:" + queueName + "(r)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
            } else {
                RedisUtils.leftpush(queueName, redisStr);
                logger.info(logHeader + "Redis:" + queueName + "(l)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
            }

        }
        return ketouplanAmoust;
    }
    // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 end

    /**
     * 取得最小可出借/复投金额的条件
     *
     * @param hjhAccede
     * @return
     */
    private BigDecimal getMinAccountEnable(HjhAccedeVO hjhAccede) {
        BigDecimal minAccountEnable = null;
        if (hjhAccede.getOrderStatus() == 0) {
            // 0出借
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
            logger.info("Redis:" + queueName + "(r)>>>>>>>>>>>>>>>>>>>>>>>>>>没有标的");
        } else {
            logger.info("Redis:" + queueName + "(r)>>>>>>>>>>>>>>>>>>>>>>>>>>" + borrowStr);
        }
        return borrowStr;
    }

    /**
     * 调用同步银行接口（出借）
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
            // 插入 自动出借临时表
            Integer idKey = this.insertBorrowTmp(borrow, null, hjhAccede, account, hjhUserAuth, bean, RedisConstants.HJH_BORROW_INVEST, isLast ? 1 : 0);

            // 调用银行接口
            logger.info(hjhAccede.getAccedeOrderId() + " 自动出借接口调用中  " + borrow.getBorrowNid() + " 请求订单号:" + orderId);
            bankResult = BankCallUtils.callApiBg(bean);

            // 更新 自动出借临时表
            boolean updResult = updateBorrowTmp(hjhAccede.getAccedeOrderId(), borrow.getBorrowNid(), bankResult);
            if (!updResult) {
                logger.error("更新自动出借临时表失败 idKey=" + idKey);
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
        BigDecimal preCreditCapital = this.amTradeClient.doGetPreCreditCapital(credit);

        // 银行接口用bean
        BankCallBean bean = new BankCallBean(orderId, userId, BankCallConstant.TXCODE_CREDIT_AUTO_INVEST, "自动购买债权", hjhAccede.getClient());
        bean.setAccountId(tenderUsrcustid);// 电子账号
        bean.setTxAmount(CustomUtil.formatAmount(account.toString()));// 交易金额
        bean.setProductId(borrowNid);// 标的号
        bean.setTxFee(CustomUtil.formatAmount(serviceFee.toString()));    //手续费 交易金额*当前清算服务费率=债转服务费
        bean.setTsfAmount(CustomUtil.formatAmount(assignCapital.toString()));    //转让金额  实际承接本金
        bean.setForAccountId(sellUsrcustid);    //对手电子账号
        bean.setOrgOrderId(credit.getSellOrderId());    //原订单号 原出借订单号
        bean.setOrgTxAmount(CustomUtil.formatAmount((credit.getCreditCapital().add(preCreditCapital)).toString()));    //原交易金额  债转总本金
        bean.setContOrderId(hjhUserAuth.getAutoCreditOrderId());
        bean.setTxDate(orderDate);
        bean.setLogOrderDate(orderDate);

        try {
            // 插入 自动出借临时表
            Integer idKey = this.insertBorrowTmp(null, credit, hjhAccede, account, hjhUserAuth, bean, RedisConstants.HJH_BORROW_CREDIT, isLast ? 1 : 0);

            // 调用银行接口
            logger.info(hjhAccede.getAccedeOrderId() + " 银行自动出借接口("
                    + BankCallConstant.TXCODE_CREDIT_AUTO_INVEST + ")调用中  "
                    + credit.getCreditNid() + " 请求订单号:" + orderId);
            bankResult = BankCallUtils.callApiBg(bean);

            // 更新 自动出借临时表
            boolean updResult = updateBorrowTmp(hjhAccede.getAccedeOrderId(), credit.getCreditNid(), bankResult);
            if (!updResult) {
                logger.error("更新自动出借临时表失败 idKey=" + idKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bankResult;
    }

    /**
     * 插入 自动出借临时表
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
        record.setAccedeAccount(hjhAccede.getAccedeAccount());//加入金额
        record.setAlreadyInvest(hjhAccede.getAlreadyInvest());//已投资金额
        record.setAccount(ketouplanAmoust);//交易金额
        if (borrowFlag.equals(RedisConstants.HJH_BORROW_CREDIT)) {
            // 汇计划自动出借债转
            record.setInstCode(credit.getInstCode());
            record.setBorrowNid(credit.getCreditNid());//债转编号
            record.setBorrowAccount(credit.getCreditCapital());//债转总本金
            record.setBorrowPeriod(credit.getRemainDays());//剩余天数
            record.setBorrowStyle(credit.getBorrowStyle());//还款方式
            record.setBorrowType(1);//标的类型：0原始标的,1债转标的
            record.setSellUserId(credit.getUserId());//债转原用户id
            record.setSellOrderId(credit.getSellOrderId());//债转原投资订单号
        } else {
            // 汇计划自动出借原始标的
            record.setInstCode(borrow.getInstCode());
            record.setBorrowNid(borrow.getBorrowNid());
            record.setBorrowAccount(borrow.getAccount());
            record.setBorrowPeriod(borrow.getBorrowPeriod());
            record.setBorrowStyle(borrow.getBorrowStyle());//还款方式
            record.setBorrowType(0);//标的类型：0原始标的,1债转标的
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
        logger.info("插入临时表：ht_hjh_plan_borrow_tmp,record为:"+ JSONObject.toJSON(record)+"===============");

        int intInsetFlg = this.amTradeClient.insertHjhPlanBorrowTmp(record);
        if(intInsetFlg >0){
            logger.info("插入临时表：ht_hjh_plan_borrow_tmp 成功! id为:"+record.getId());
        }
        return record.getId();
    }

    /**
     * 更新 自动出借临时表
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
        hjhPlanBorrowTmpVO.setStatus(1);//已调用银行
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

    /**
     * 请求结束债权（插入结束债权任务）
     * @param accedeOrderId
     * @param creditNid
     * @param sellerUsrcustid
     * @throws Exception
     */
    private void requestHjhCreditEnd(String accedeOrderId, String creditNid, String sellerUsrcustid) throws Exception {
        String logMsgHeader = "【请求结束债权(智投完全承接)】";
        logger.info(logMsgHeader + "----------开始--------- 债转号：" + creditNid);

        // 1.获取债转详情	 */
        HjhDebtCreditVO credit = this.amTradeClient.doSelectHjhDebtCreditByCreditNid(creditNid); // 从主库
        if (credit == null) {
            throw new RuntimeException(logMsgHeader + "债转号不存在 "+creditNid);
        }
        if (credit.getCreditAccountWait().compareTo(BigDecimal.ZERO) != 0){ // 待承接金额不为0时
            throw new RuntimeException(logMsgHeader + "债转号" + creditNid + "未被完全承接，不能结束债权。未被承接金额： " + credit.getCreditAccountWait());
        }

        // 2.获取出让人投标成功的授权号
        String sellerAuthCode = this.amTradeClient.getSellerAuthCode(credit.getSellOrderId(), credit.getSourceType());
        if (sellerAuthCode == null) {
            throw new RuntimeException(logMsgHeader + "未取得出让人" + credit.getUserId() + "的债权类型" +
                    credit.getSourceType() + "(0非原始 1原始)的授权码，结束债权失败。");
        }

        // add 合规数据上报 埋点 liubin 20181122 start
        // 推送数据到MQ 承接（完全）
        JSONObject params = new JSONObject();
        params.put("creditNid", credit.getCreditNid());
        params.put("flag", "2"); //1（散）2（智投）
        params.put("status", "2"); //2承接（完全）
        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                MQConstant.HG_REPORT_DELAY_LEVEL);
        // add 合规数据上报 埋点 liubin 20181122 end

        // 3.插入结束债权任务
        boolean ret = this.amTradeClient.requestDebtEnd(credit, sellerUsrcustid, sellerAuthCode) > 0 ? true : false;
        if (!ret) {
            logger.info(logMsgHeader + "被承接标的" + credit.getCreditNid() + "被完全承接，银行结束债权失败。");
        }

        // 4.更新债权表为完全承接
        ret = this.amTradeClient.updateHjhDebtCreditForEnd(credit) > 0 ? true : false;
        if (!ret) {
            logger.info(logMsgHeader + "银行结束债权后，更新债权表为完全承接失败。");
        }
        logger.info(logMsgHeader + "被承接标的" + credit.getCreditNid() + "被完全承接，银行结束债权成功。");
    }
}
