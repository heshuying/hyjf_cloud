/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.HjhPlanCapital;
import com.hyjf.cs.message.service.HjhAccountBalanceService;
import com.hyjf.cs.message.service.HjhPlanCapitalService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时计划资金统计任务
 * @author liubin
 * @version PlanCapitalController, v0.1 2018/7/31 16:48
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/hjhPlanCapital")
public class HjhPlanCapitalController extends BaseController {

    @Autowired
    private HjhPlanCapitalService hjhPlanCapitalService;
    @Autowired
    private HjhAccountBalanceService hjhAccountBalanceService;

    /**
     * 定时计划资金统计任务
     * @return
     */
    @RequestMapping("/hjhPlanCapital")
    public BooleanResponse hjhPlanCapital() {
            logger.info("汇计划资本预估统计(每日)任务 开始... ");
            Boolean result = false;
            try {
                // 取得当前日期为基准日期
                Date nowDate = GetDate.stringToDate2(GetDate.dateToString2(new Date()));
                // 取得前一天日期
                Date yDate = GetDate.countDate(nowDate, 5, -1);
/*
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
//                                    + GetDate.dateToString(hjhPlanCapital.getDate())
                                    + hjhPlanCapital.getDate()
                                    + " 计划编号：" + hjhPlanCapital.getPlanNid());
                        }
                    }
                }
                logger.info("汇计划资本预估统计(每日)任务 更新前一天的汇计划资本统计 OK ");

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
//                                    + GetDate.dateToString(hjhPlanCapital.getDate())
                                    + hjhPlanCapital.getDate()
                                    + " 计划编号：" + hjhPlanCapital.getPlanNid());
                        }
                    }
                }
                logger.info("汇计划资本预估统计(每日)任务 更新当日到后9日的汇计划资本预估 OK ");
*/

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
                logger.info("汇计划资本预估统计(每日)任务 更新前一天的汇计划日交易量 OK ");

            } catch (Exception e) {
                logger.error("汇计划资本预估统计(每日)任务 失败... ", e);
            }
            logger.info("汇计划资本预估统计(每日)任务 结束... ");

        return new BooleanResponse(result);
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
    @RequestMapping(value = "/getPlanCapitalList", method = RequestMethod.POST)
    public HjhPlanCapitalResponse getPlanCapitalList(@RequestBody  HjhPlanCapitalRequest request){
        HjhPlanCapitalResponse hjhPlanCapitalResponse = new HjhPlanCapitalResponse();

        //总计条数
        Integer count = this.hjhPlanCapitalService.getPlanCapitalCount(request);

        List<HjhPlanCapital> recordAllList = this.hjhPlanCapitalService.getPlanCapitalList(request);

        // 分页
        if (request.getCurrPage() > 0 && request.getPageSize() > 0){
            Paginator paginator = new Paginator(request.getCurrPage(), count.intValue(), request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<HjhPlanCapital> recordList = this.hjhPlanCapitalService.getPlanCapitalList(request);

        // 初始化总计数据
        BigDecimal sumAccedeAccount = BigDecimal.ZERO;
        BigDecimal sumRepayInterest = BigDecimal.ZERO;

        // 统计复投总额和债转总额
        for (int i = 0; i < recordAllList.size(); i++){
            if (recordAllList.get(i).getReinvestAccount() == null){
                sumAccedeAccount = BigDecimal.ZERO;
            }else {
                sumAccedeAccount = sumAccedeAccount.add(recordAllList.get(i).getReinvestAccount());
            }
            if (recordAllList.get(i).getCreditAccount() == null){
                sumRepayInterest = BigDecimal.ZERO;
            }else {
                sumRepayInterest = sumRepayInterest.add(recordAllList.get(i).getCreditAccount());
            }
        }

        // 为服务回报期限 添加单位
        for (int i = 0; i < recordList.size(); i++){
            if(recordList.get(i).getIsMonth() == 0){
                recordList.get(i).setLockPeriodView(recordList.get(i).getLockPeriod() + "天");
            }else{
                recordList.get(i).setLockPeriodView(recordList.get(i).getLockPeriod() + "个月");
            }
        }

        if (CollectionUtils.isNotEmpty(recordList)){
            List<HjhPlanCapitalVO> hjhPlanCapitalVOList = CommonUtils.convertBeanList(recordList, HjhPlanCapitalVO.class);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < recordList.size(); i++){
                hjhPlanCapitalVOList.get(i).setStringDate(formatter.format(recordList.get(i).getDate()));
            }

            HjhPlanCapitalVO hjhPlanCapitalVO = new HjhPlanCapitalVO();
            hjhPlanCapitalResponse.setResultList(hjhPlanCapitalVOList);
            hjhPlanCapitalResponse.setCount(count);
            hjhPlanCapitalVO.setReinvestAccount(sumAccedeAccount);
            hjhPlanCapitalVO.setCreditAccount(sumRepayInterest);
            hjhPlanCapitalResponse.setSumHjhPlanCapitalVO(hjhPlanCapitalVO);
            hjhPlanCapitalResponse.setRtn(Response.SUCCESS);
        }
        return hjhPlanCapitalResponse;
    }

}
