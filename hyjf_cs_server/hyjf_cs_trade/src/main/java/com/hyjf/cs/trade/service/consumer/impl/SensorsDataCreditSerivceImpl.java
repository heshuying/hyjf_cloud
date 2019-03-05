/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.CreditTenderBgVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserDepartmentInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataCreditSerivce;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 神策数据统计:债转相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataCreditSerivceImpl, v0.1 2018/10/22 17:09
 */
@Service
public class SensorsDataCreditSerivceImpl extends BaseServiceImpl implements SensorsDataCreditSerivce {


    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 发送神策数据
     *
     * @param sensorsDataBean
     * @throws IOException
     * @throws InvalidArgumentException
     */
    @Override
    public void sendSensorsData(SensorsDataBean sensorsDataBean) throws IOException, InvalidArgumentException {

        // log文件存放位置
        String logFilePath = systemConfig.getSensorsDataLogPath();
        // 初始化神策SDK
        SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer(logFilePath + "sensorsData.log"));

        // 事件类型
        String eventCode = sensorsDataBean.getEventCode();
        if (StringUtils.isBlank(eventCode)) {
            logger.error("事件类型为空.");
            return;
        }

        // 债转承接
        if ("receive_credit_assign".equals(eventCode)) {
            // 债转承接
            String assignOrderId = sensorsDataBean.getOrderId();
            if (StringUtils.isBlank(assignOrderId)) {
                logger.error("承接订单号为空");
                return;
            }
            logger.info("承接订单号:[" + assignOrderId + "].");
            // 根据承接订单号查询承接信息
            CreditTenderVO creditTenderVO = this.amTradeClient.selectCreditTenderByAssignOrderId(assignOrderId);
            if (creditTenderVO == null) {
                logger.error("根据承接订单号查询用户承接记录失败,承接订单号:[" + assignOrderId + "].");
                return;
            }
            // 承接人用户ID
            Integer userId = creditTenderVO.getUserId();
            // 债转编号
            String creditNid = creditTenderVO.getCreditNid();

            // 根据债转编号查询转让信息
            BorrowCreditVO borrowCredit = this.amTradeClient.getBorrowCreditByCreditNid(creditNid);
            if (borrowCredit == null) {
                logger.error("根据债转编号查询转让记录不存在,承接订单号:[" + assignOrderId + "],债转编号:[" + creditNid + "].");
                return;
            }

            // 原标标号
            String borrowNid = borrowCredit.getBidNid();
            // 根据标的编号查询标的信息
            RightBorrowVO borrow = this.amTradeClient.getRightBorrowByNid(borrowNid);
            if (borrow == null) {
                logger.error("根据标的编号查询标的信息失败,标的编号:[" + borrowNid + "].");
                return;
            }

            // 根据标的编号查询标的详情信息
            BorrowInfoVO borrowInfoVO = this.amTradeClient.selectBorrowInfoWithBLOBSVOByBorrowId(borrowNid);
            if (borrowInfoVO == null) {
                logger.error("根据标的编号查询标的详情信息失败,标的编号:[" + borrowNid + "].");
                return;
            }

            // 事件属性
            Map<String, Object> properties = new HashMap<String, Object>();
            // 订单ID
            properties.put("order_id", assignOrderId);
            // 项目名称
            properties.put("project_name", borrowCredit.getBidName());
            // 项目编号
            properties.put("project_id", "HZR" + borrowCredit.getCreditNid());
            // 项目剩余期限
            properties.put("credit_rest_duration", borrowCredit.getCreditTerm());
            // 折价率
            properties.put("discount_apr", borrowCredit.getCreditDiscount().divide(new BigDecimal("100")));
            // 历史年回报率
            properties.put("project_apr", borrowCredit.getBidApr().divide(new BigDecimal("100")));
            //垫付利息
            properties.put("advance_pay_interest", creditTenderVO.getAssignInterestAdvance());
            // 承接本金
            properties.put("receive_credit_amount", creditTenderVO.getAssignCapital());
            // 原项目编号
            properties.put("old_project_id", borrowCredit.getBidNid());
            // 转让本金
            properties.put("transfer_money", borrowCredit.getCreditCapital());
            // 实际支付
            properties.put("pay_amount", creditTenderVO.getAssignPay());
            // 历史回报金额
            properties.put("expect_income", creditTenderVO.getAssignInterest());
            // 查询还款方式
            BorrowStyleVO borrowStyle = this.amTradeClient.getBorrowStyle(borrow.getBorrowStyle());
            // 还款方式
            properties.put("project_repayment_type", borrowStyle.getName());
            // 平台类型
            if (creditTenderVO.getClient() == 0) {
                properties.put("PlatformType", "PC");
            } else if (creditTenderVO.getClient() == 1) {
                properties.put("PlatformType", "wechat");
            } else if (creditTenderVO.getClient() == 2) {
                properties.put("PlatformType", "Android");
            } else if (creditTenderVO.getClient() == 3) {
                properties.put("PlatformType", "iOS");
            }

            // 获取出借时推荐人部门等信息
            // 根据用户ID 查询用户推荐人信息
            SpreadsUserVO spreadsUsers = this.amUserClient.querySpreadsUsersByUserId(userId);
            // 用户没有推荐人
            if (spreadsUsers == null) {
                // 注册时邀请人
                properties.put("inviter", "");
            } else {
                // 推荐人用户ID
                Integer spreadsUserId = spreadsUsers.getSpreadsUserId();
                // 推荐人用户名
                UserVO spreadsUser = this.amUserClient.findUserById(spreadsUserId);
                // 注册时邀请人
                properties.put("inviter", spreadsUser == null ? "" : spreadsUser.getUsername());
            }
            // 用户信息
            UserInfoVO usersInfo = this.amUserClient.getUserInfo(userId);
            // 用户属性
            if (usersInfo.getAttribute() == 0) {
                // 当前用户属性
                properties.put("attribute", "无主单");
            } else if (usersInfo.getAttribute() == 1) {
                // 当前用户属性
                properties.put("attribute", "有主单");
            } else if (usersInfo.getAttribute() == 2) {
                // 当前用户属性
                properties.put("attribute", "线下员工");
            } else if (usersInfo.getAttribute() == 3) {
                // 当前用户属性
                properties.put("attribute", "线上员工");
            }

            // 根据用户ID 查询用户部门信息
            UserDepartmentInfoCustomizeVO userDepartmentInfoCustomize = this.amUserClient.queryUserDepartmentInfoByUserId(userId);
            if (userDepartmentInfoCustomize == null) {
                // 分公司
                properties.put("regionName", "");
                // 分部
                properties.put("branchName", "");
                // 团队
                properties.put("departmentName", "");
            } else {
                // 注册时分公司
                properties.put("regionName", StringUtils.isBlank(userDepartmentInfoCustomize.getRegionName()) ? "" : userDepartmentInfoCustomize.getRegionName());
                // 注册时分部
                properties.put("branchName", StringUtils.isBlank(userDepartmentInfoCustomize.getBranchName()) ? "" : userDepartmentInfoCustomize.getBranchName());
                // 注册时团队
                properties.put("departmentName", StringUtils.isBlank(userDepartmentInfoCustomize.getDepartmentName()) ? "" : userDepartmentInfoCustomize.getDepartmentName());
            }

            // 调用神策track事件
            sa.track(String.valueOf(userId), true, "receive_credit_assign", properties);
            sa.shutdown();
        } else {
            // 发起转让
            // 债转编号
            String creditNid = sensorsDataBean.getCreditNid();
            if (StringUtils.isBlank(creditNid)) {
                logger.error("债转编号为空");
                return;
            }
            logger.info("神策采集事件:债转编号:[" + creditNid + "].");

            // 根据债转编号查询转让信息
            BorrowCreditVO borrowCredit = this.amTradeClient.getBorrowCreditByCreditNid(creditNid);
            if (borrowCredit == null) {
                logger.error("根据债转编号查询转让信息失败,债转编号:[" + creditNid + "].");
                return;
            }
            // 出让人用户ID
            Integer userId = borrowCredit.getCreditUserId();

            // 原标项目编号
            String borrowNid = borrowCredit.getBidNid();

            // 根据标的编号查询标的信息
            RightBorrowVO borrow = this.amTradeClient.getRightBorrowByNid(borrowNid);
            if (borrow == null) {
                logger.error("根据标的编号查询标的信息失败,标的编号:[" + borrowNid + "].");
                return;
            }

            // 根据标的编号查询标的详情信息
            BorrowInfoVO borrowInfoVO = this.amTradeClient.selectBorrowInfoWithBLOBSVOByBorrowId(borrowNid);
            if (borrowInfoVO == null) {
                logger.error("根据标的编号查询标的详情信息失败,标的编号:[" + borrowNid + "].");
                return;
            }

            // 原始出借订单号
            String tenderNid = borrowCredit.getTenderNid();
            BorrowTenderRequest borrowTenderRequest = new BorrowTenderRequest();
            borrowTenderRequest.setTenderNid(tenderNid);
            BorrowTenderVO borrowTenderVO = this.amTradeClient.selectBorrowTender(borrowTenderRequest);
            if (borrowTenderVO == null) {
                logger.error("根据出借订单号查询出借记录不存在,出借订单号:[" + tenderNid + "].");
                return;
            }

            // 预置属性
            Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
            // 事件属性
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.putAll(presetProps);
            // 订单ID
            properties.put("order_id", "HZR" + creditNid);
            // 项目编号
            properties.put("project_id", borrowCredit.getBidNid());
            // 项目名称
            properties.put("project_name", borrowCredit.getBidName());
            // 历史回报率
            properties.put("project_apr", borrowCredit.getBidApr().divide(new BigDecimal("100")));
            // 项目期限
            properties.put("project_duration", borrow.getBorrowPeriod());
            // 项目期限单位
            if ("endday".equals(borrow.getBorrowStyle())) {
                properties.put("duration_unit", "天");
            } else {
                properties.put("duration_unit", "月");
            }
            // 当前持有期限
            properties.put("hold_days", borrowCredit.getCreditTermHold());
            // 当前剩余天数
            properties.put("remain_days", borrowCredit.getCreditTerm());
            // 持有本金
            properties.put("hold_amount", borrowTenderVO.getAccount());
            // 查询还款方式
            BorrowStyleVO borrowStyle = this.amTradeClient.getBorrowStyle(borrow.getBorrowStyle());
            // 还款方式
            properties.put("project_repayment_type", borrowStyle.getName());
            // 当前期次
            properties.put("current_period", String.valueOf(borrowCredit.getCreditPeriod()));
            // 出借时间
            properties.put("invest_time", GetDate.getDateTimeMyTimeInMillis(borrowTenderVO.getCreateTime()));
            // 转让本金
            properties.put("transfer_money", borrowCredit.getCreditCapital());
            // 折价率
            properties.put("discount_apr", borrowCredit.getCreditDiscount().divide(new BigDecimal("100")));
            // 预计本金折让金额
            properties.put("expect_discount_money", borrowCredit.getCreditPrice());
            // 预计持有期收益
            properties.put("expect_hold_income", borrowCredit.getCreditInterestAdvance());
            // 预计服务费
            properties.put("expect_service_fee", borrowCredit.getCreditFee());
            // 截止日期
            properties.put("end_time", GetDate.getDateTimeMyTime(borrowCredit.getEndTime()));
            // 预计到账金额
            properties.put("expect_arrive_money", borrowCredit.getCreditPrice().add(borrowCredit.getCreditInterestAdvance()).subtract(borrowCredit.getCreditFee()));
            // 平台类型
            if (borrowCredit.getClient() == 0) {
                properties.put("PlatformType", "PC");
            } else if (borrowCredit.getClient() == 1) {
                properties.put("PlatformType", "wechat");
            } else if (borrowCredit.getClient() == 2) {
                properties.put("PlatformType", "Android");
            } else if (borrowCredit.getClient() == 3) {
                properties.put("PlatformType", "iOS");
            }
            // 调用神策track事件
            sa.track(String.valueOf(userId), true, "submit_credit_assign", properties);
            sa.shutdown();

        }


    }
}
