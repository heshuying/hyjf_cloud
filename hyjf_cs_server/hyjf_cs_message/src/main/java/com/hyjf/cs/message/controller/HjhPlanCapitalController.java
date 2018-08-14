/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.HjhAccountBalanceService;
import com.hyjf.cs.message.service.HjhPlanCapitalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 定时计划资金统计任务
 * @author liubin
 * @version PlanCapitalController, v0.1 2018/7/31 16:48
 */
@Api(value = "汇计划资本预估统计(每日)任务")
@RestController
@RequestMapping("/cs-message/hjh_plan_capital")
public class HjhPlanCapitalController extends BaseController {

    @Autowired
    private HjhPlanCapitalService hjhPlanCapitalService;
    @Autowired
    private HjhAccountBalanceService hjhAccountBalanceService;

    @RequestMapping("/hjhplancapital")
    public Boolean hjhPlanCapital() {
            logger.info("汇计划资本预估统计(每日)任务 开始... ");

            try {
                Boolean result = false;
                // 取得当前日期为基准日期
                Date nowDate = GetDate.stringToDate2(GetDate.dateToString2(new Date()));
                // 取得前一天日期
                Date yDate = GetDate.countDate(nowDate, 5, -1);
                Date date9 = GetDate.countDate(nowDate, 5, 9);
                // 删除汇计划资本按天统计及预估表的昨天今天及之后9天的记录
                result = this.hjhPlanCapitalService.deleteHjhPlanCapital(yDate, date9);
                if (!result){
                    logger.error("删除汇计划资本按天统计及预估表的昨天今天及之后9天的记录失败 日期："
                            + GetDate.dateToString2(yDate) + " ~ " + GetDate.dateToString2(date9) );
                }

                // 更新前一天的汇计划资本统计
                List<HjhPlanCapitalVO> actList = this.hjhPlanCapitalService.getPlanCapitalForActList(yDate);
                if (actList != null && actList.size() > 0) {
                    for (HjhPlanCapitalVO hjhPlanCapital : actList) {
                        if (hjhPlanCapital.getDate() == null){
                            continue;
                        }
                        // 插入更新汇计划资本按天统计及预估表
                        result = this.hjhPlanCapitalService.updatePlanCapital(hjhPlanCapital);

                        if (!result){
                            logger.error("汇计划资本预估统计(每日)任务 更新前一天的汇计划资本统计(实际) 失败 日期："
                                    + GetDate.dateToString(hjhPlanCapital.getDate())
                                    + " 计划编号：" + hjhPlanCapital.getPlanNid());
                        }
                    }
                }

                // 更新当日到后9日的汇计划资本预估
                List<HjhPlanCapitalVO> proformaList = this.hjhPlanCapitalService.getPlanCapitalForProformaList(nowDate, date9);
                if (proformaList != null && proformaList.size() > 0) {
                    for (HjhPlanCapitalVO hjhPlanCapital :proformaList) {
                        if (hjhPlanCapital.getDate() == null){
                            continue;
                        }
                        // 插入更新汇计划资本按天统计及预估表
                        result = this.hjhPlanCapitalService.updatePlanCapital(hjhPlanCapital);

                        if (!result){
                            logger.error("汇计划资本预估统计(每日)任务 更新当日及后9天的汇计划资本统计(预估) 失败 日期："
                                    + GetDate.dateToString(hjhPlanCapital.getDate())
                                    + " 计划编号：" + hjhPlanCapital.getPlanNid());
                        }
                    }
                }

                // 更新前一天的汇计划日交易量
                List<HjhAccountBalanceVO> hjhAccountBalanceList = this.hjhAccountBalanceService.getHjhAccountBalanceForActList(yDate);
                if (hjhAccountBalanceList != null && hjhAccountBalanceList.size() > 0) {
                    for (HjhAccountBalanceVO hjhAccountBalance :hjhAccountBalanceList) {
                        if (hjhAccountBalance.getDate() == null){
                            continue;
                        }
                        // 插入更新汇计划按日对账统计表
                        result = this.hjhAccountBalanceService.updateAccountBalance(hjhAccountBalance);

                        if (!result){
                            logger.error("汇计划资本预估统计(每日)任务 更新前一天的汇计划资本统计(实际) 失败 日期："
                                    + GetDate.dateToString(hjhAccountBalance.getDate()));
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("汇计划资本预估统计(每日)任务 失败... ");
                e.printStackTrace();
            }
            logger.info("汇计划资本预估统计(每日)任务 结束... ");


        return false;
    }

    /**
     * 汇计划 -- 资金计划 count
     * @param request
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/getPlanCapitalCount")
    public Integer getPlanCapitalCount(@RequestBody HjhPlanCapitalRequest request){
        return this.hjhPlanCapitalService.getPlanCapitalCount(request);
    }
    /**
     * 汇计划 -- 资金计划列表
     * @param request
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/getPlanCapitalList")
    public HjhPlanCapitalResponse getPlanCapitalList(@RequestBody  HjhPlanCapitalRequest request){
        HjhPlanCapitalResponse hjhPlanCapitalResponse = new HjhPlanCapitalResponse();

        List<HjhPlanCapitalVO> recordList = this.hjhPlanCapitalService.getPlanCapitalList(request);
        hjhPlanCapitalResponse.setResultList(recordList);

        return hjhPlanCapitalResponse;
    }

}
