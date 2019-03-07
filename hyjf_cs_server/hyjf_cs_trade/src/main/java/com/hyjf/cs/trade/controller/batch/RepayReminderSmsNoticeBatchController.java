/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.service.batch.RepayReminderSmsNoticeBatchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version RepayReminderSmsNoticeBatchController, v0.1 2018/6/22 10:30
 */
@ApiIgnore
@RestController
@RequestMapping("/repayReminder")
public class RepayReminderSmsNoticeBatchController {
    private static final Logger logger = LoggerFactory.getLogger(RedPacketSmsNoticeBatchController.class);

    /** 用户ID */
    private static final String VAL_USERID = "userId";
    /** 应还日期 */
    private static final String VAL_DATE = "val_date";
    /** 标号 */
    private static final String VAL_BORROWNID = "val_borrownid";

    @Autowired
    private RepayReminderSmsNoticeBatchService repayReminderService;

    @RequestMapping("/smsNotice")
    public void entryUpdate(){
        logger.info("【还款前三天提醒借款人还款短信定时】开始。。。");

        try {
            // 检索正在还款中的标的
            List<BorrowAndInfoVO> borrows = this.repayReminderService.selectBorrowList();
            // 循环还款中的标的
            if (borrows != null && borrows.size() > 0) {
                for (BorrowAndInfoVO borrow : borrows) {
                    // 还款前三天短信提醒
                    List<Map<String, String>> msgList = new ArrayList<Map<String, String>>();
                    // 还款当天短息提醒
                    List<Map<String, String>> messages = new ArrayList<Map<String, String>>();
                    // 标的编号
                    String borrowNid = borrow.getBorrowNid();
                    // 借款人用户ID
                    Integer borrowUserId = borrow.getUserId();
                    // 担保机构用户ID
                    Integer repayOrgUserId = StringUtils.isEmpty(String.valueOf(borrow.getRepayOrgUserId())) ? 0 : borrow.getRepayOrgUserId();
                    // 还款方式
                    String borrowStyle = borrow.getBorrowStyle();
                    // 是否月标(true:月标, false:天标)
                    boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
                            || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                            || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                    if (isMonth) {
                        // 分期还款,取得还款计划表
                        List<BorrowRepayPlanVO> borrowRepayPlanList = this.repayReminderService.selectBorrowRepayPlan(borrowNid, 0);
                        // 还款计划
                        if (borrowRepayPlanList != null && borrowRepayPlanList.size() > 0) {
                            // 取最近一期未还款信息
                            BorrowRepayPlanVO borrowRepayPlan = borrowRepayPlanList.get(0);
                            // 应还时间
                            String repayTime = GetDate.getDateMyTimeInMillis(borrowRepayPlan.getRepayTime());
                            String now = GetDate.getDateMyTimeInMillis(GetDate.getMyTimeInMillis());
                            int count = GetDate.daysBetween(now, repayTime);
                            if (count <= 3) {
                                Map<String, String> borrowUserMsg = new HashMap<String, String>();
                                borrowUserMsg.put(VAL_USERID, String.valueOf(borrowUserId));
                                borrowUserMsg.put(VAL_BORROWNID, borrowNid);
                                borrowUserMsg.put(VAL_DATE, repayTime);
                                msgList.add(borrowUserMsg);
                                if (repayOrgUserId != 0) {
                                    Map<String, String> repayOrgUserMsg = new HashMap<String, String>();
                                    repayOrgUserMsg.put(VAL_USERID, String.valueOf(repayOrgUserId));
                                    repayOrgUserMsg.put(VAL_BORROWNID, borrowNid);
                                    repayOrgUserMsg.put(VAL_DATE, repayTime);
                                    msgList.add(repayOrgUserMsg);
                                }
                                this.repayReminderService.sendSms(msgList, CustomConstants.PARAM_TPL_HUANKUANTIXING);
                                // 更新borrowrecoverPlan短信
                                Integer isUpdateFlag = this.repayReminderService.updateBorrowRepayPlan(borrowRepayPlan, 1);
                                if (isUpdateFlag == null || isUpdateFlag == 0) {
                                    throw new Exception("短信发送后,更新borrow_repay_plan表失败");
                                }
                            }

                        }

                        // 还款日当天过了17:00,提醒短信提醒借款人还款
                        List<BorrowRepayPlanVO> borrowRepayPlans = this.repayReminderService.selectBorrowRepayPlan(borrowNid, 1);
                        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
                            // 取最近一期未还款信息
                            BorrowRepayPlanVO borrowRepayPlan = borrowRepayPlans.get(0);
                            // 应还时间
                            // 还款日
                            String repayTimeYYYYMMDD = GetDate.getDateMyTimeInMillis(borrowRepayPlan.getRepayTime());
                            // 当前日期
                            String nowDate = GetDate.getDate("yyyy-MM-dd");
                            if (repayTimeYYYYMMDD.equals(nowDate) && GetDate.getNowTime10() > GetDate.dateString2Timestamp(nowDate + " 17:00:00")) {
                                Map<String, String> borrowUserMsg = new HashMap<String, String>();
                                borrowUserMsg.put(VAL_USERID, String.valueOf(borrowUserId));
                                borrowUserMsg.put(VAL_BORROWNID, borrowNid);
                                messages.add(borrowUserMsg);
                                if (repayOrgUserId != 0) {
                                    Map<String, String> repayOrgUserMsg = new HashMap<String, String>();
                                    repayOrgUserMsg.put(VAL_USERID, String.valueOf(repayOrgUserId));
                                    repayOrgUserMsg.put(VAL_BORROWNID, borrowNid);
                                    messages.add(repayOrgUserMsg);
                                }

                                this.repayReminderService.sendSms(messages, CustomConstants.PARAM_TPL_HUANKUANGUOQI);
                                // 更新borrowrecoverPlan短信
                                Integer isUpdateFlag = this.repayReminderService.updateBorrowRepayPlan(borrowRepayPlan, 2);
                                if (isUpdateFlag == null || isUpdateFlag == 0) {
                                    throw new Exception("短信发送后,更新borrow_repay_plan表失败");
                                }
                            }
                        }

                    } else {
                        // 不分期的还款数据
                        List<BorrowRepayVO> borrowRepayList = this.repayReminderService.selectBorrowRepayList(borrowNid, 0);
                        if (borrowRepayList != null && borrowRepayList.size() > 0) {
                            BorrowRepayVO borrowRecover = borrowRepayList.get(0);
                            // 应还时间
                            String repayTime = GetDate.getDateMyTimeInMillis(borrowRecover.getRepayTime());
                            String now = GetDate.getDateMyTimeInMillis(GetDate.getMyTimeInMillis());
                            int count = GetDate.daysBetween(now, repayTime);
                            if (count <= 3) {
                                Map<String, String> borrowUserMsg = new HashMap<String, String>();
                                borrowUserMsg.put(VAL_USERID, String.valueOf(borrowUserId));
                                borrowUserMsg.put(VAL_BORROWNID, borrowNid);
                                borrowUserMsg.put(VAL_DATE, repayTime);
                                msgList.add(borrowUserMsg);
                                if (repayOrgUserId != 0) {
                                    Map<String, String> repayOrgUserMsg = new HashMap<String, String>();
                                    repayOrgUserMsg.put(VAL_USERID, String.valueOf(repayOrgUserId));
                                    repayOrgUserMsg.put(VAL_BORROWNID, borrowNid);
                                    repayOrgUserMsg.put(VAL_DATE, repayTime);
                                    msgList.add(repayOrgUserMsg);
                                }
                                this.repayReminderService.sendSms(msgList, CustomConstants.PARAM_TPL_HUANKUANTIXING);
                                // 更新borrowrecover信息
                                Integer isUpdateFlag = this.repayReminderService.updateBorrowRepay(borrowRecover, 1);
                                if (isUpdateFlag == null || isUpdateFlag == 0) {
                                    throw new Exception("短信发送后,更新borrow_recover表失败");
                                }
                            }
                        }

                        // 还款日当天过了17:00,提醒短信提醒借款人还款
                        List<BorrowRepayVO> borrowRepays = this.repayReminderService.selectBorrowRepayList(borrowNid, 1);
                        if (borrowRepays != null && borrowRepays.size() > 0) {
                            BorrowRepayVO borrowRepay = borrowRepays.get(0);
                            // 应还时间
                            // 还款日
                            String repayTimeYYYYMMDD = GetDate.getDateMyTimeInMillis(borrowRepay.getRepayTime());
                            // 当前日期
                            String nowDate = GetDate.getDate("yyyy-MM-dd");
                            if (repayTimeYYYYMMDD.equals(nowDate) && GetDate.getNowTime10() > GetDate.dateString2Timestamp(nowDate + " 17:00:00")) {
                                Map<String, String> borrowUserMsg = new HashMap<String, String>();
                                borrowUserMsg.put(VAL_USERID, String.valueOf(borrowUserId));
                                borrowUserMsg.put(VAL_BORROWNID, borrowNid);
                                messages.add(borrowUserMsg);
                                if (repayOrgUserId != 0) {
                                    Map<String, String> repayOrgUserMsg = new HashMap<String, String>();
                                    repayOrgUserMsg.put(VAL_USERID, String.valueOf(repayOrgUserId));
                                    repayOrgUserMsg.put(VAL_BORROWNID, borrowNid);
                                    messages.add(repayOrgUserMsg);
                                }
                                this.repayReminderService.sendSms(messages, CustomConstants.PARAM_TPL_HUANKUANGUOQI);
                                // 更新borrowrecoverPlan短信
                                Integer isUpdateFlag = this.repayReminderService.updateBorrowRepay(borrowRepay, 2);
                                if (isUpdateFlag == null || isUpdateFlag == 0) {
                                    throw new Exception("短信发送后,更新borrow_recover_plan表失败");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("【还款前三天提醒借款人还款短信定时】结束。。。");
    }
}
