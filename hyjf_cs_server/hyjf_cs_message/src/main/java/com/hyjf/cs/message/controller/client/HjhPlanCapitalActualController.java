/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalActualResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalActual;
import com.hyjf.cs.message.service.plancapital.HjhPlanCapitalActualService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 资金计划-当日新增复投/债转额相关mongo操作
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualController, v0.1 2019/4/23 9:48
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/hjhPlanCapitalActual")
public class HjhPlanCapitalActualController extends BaseController {

    @Autowired
    HjhPlanCapitalActualService hjhPlanCapitalActualService;


    /**
     * 汇计划 -- 资金计划 -- 预计相关字段 insert
     *
     * @param list
     * @return
     * @Author : liushouyi
     */
    @PostMapping(value = "/insertPlanCaptialActual")
    public BooleanResponse insertPlanCaptialActual(@RequestBody List<HjhPlanCapitalActualVO> list) {
        if(null != list || list.size() > 0 ) {
            return new BooleanResponse(this.hjhPlanCapitalActualService.insertPlanCaptialActual(list));
        } else {
            return new BooleanResponse(true);
        }
    }

    /**
     * 汇计划 -- 资金计划 count
     *
     * @param request
     * @return
     * @Author : liushouyi
     */
    @RequestMapping(value = "/getPlanCapitalActualCount")
    public Integer getPlanCapitalActualCount(@RequestBody HjhPlanCapitalActualRequest request) {
        return this.hjhPlanCapitalActualService.getPlanCapitalActualCount(request);
    }

    /**
     * 汇计划 -- 资金计划列表
     *
     * @param request
     * @return
     * @Author : liushouyi
     */
    @RequestMapping(value = "/getPlanCapitalActualList", method = RequestMethod.POST)
    public HjhPlanCapitalActualResponse getPlanCapitalActualList(@RequestBody HjhPlanCapitalActualRequest request) {
        HjhPlanCapitalActualResponse hjhPlanCapitalActualResponse = new HjhPlanCapitalActualResponse();

        // 初始化总计数据
        // 当日新增债转额
        BigDecimal sumAddCreditAccount = BigDecimal.ZERO;
        // 当日发起债转额:当日已承接+当日未承接
        BigDecimal sumCreateCreditAccount = BigDecimal.ZERO;
        // 当日已承接债转额
        BigDecimal sumUsedCreditAccount = BigDecimal.ZERO;
        // 当日未承接额
        BigDecimal sumLeaveCreditAccount = BigDecimal.ZERO;
        // 当日新增复投额:当日可复投额-昨日未复投额
        BigDecimal sumAddReinvestAccount = BigDecimal.ZERO;
        // 当日可复投额:当日已复投额+当日未复投额
        BigDecimal sumSumReinvestAccount = BigDecimal.ZERO;
        // 当日已复投额
        BigDecimal sumUsedReinvestAccount = BigDecimal.ZERO;
        // 当日未复投额
        BigDecimal sumLeaveReinvestAccount = BigDecimal.ZERO;

        //总计条数
        Integer count = this.hjhPlanCapitalActualService.getPlanCapitalActualCount(request);

        // 不加分页查询所有数据计算sum总值
        List<HjhPlanCapitalActual> recordAllList = this.hjhPlanCapitalActualService.getPlanCapitalActualList(request);
        // 为查询道数据时返回
        if (CollectionUtils.isNotEmpty(recordAllList)) {
            // 统计复投总额和债转总额
            for (HjhPlanCapitalActual record : recordAllList) {
                if (record.getAddCreditAccount() != null) {
                    sumAddCreditAccount = sumAddCreditAccount.add(record.getAddCreditAccount());
                }
                if (record.getCreateCreditAccount() != null) {
                    sumCreateCreditAccount = sumCreateCreditAccount.add(record.getCreateCreditAccount());
                }
                if (record.getUsedCreditAccount() != null) {
                    sumUsedCreditAccount = sumUsedCreditAccount.add(record.getUsedCreditAccount());
                }
                if (record.getLeaveCreditAccount() != null) {
                    sumLeaveCreditAccount = sumLeaveCreditAccount.add(record.getLeaveCreditAccount());
                }
                if (record.getAddReinvestAccount() != null) {
                    sumAddReinvestAccount = sumAddReinvestAccount.add(record.getAddReinvestAccount());
                }
                if (record.getSumReinvestAccount() != null) {
                    sumSumReinvestAccount = sumSumReinvestAccount.add(record.getSumReinvestAccount());
                }
                if (record.getUsedReinvestAccount() != null) {
                    sumUsedReinvestAccount = sumUsedReinvestAccount.add(record.getUsedReinvestAccount());
                }
                if (record.getLeaveReinvestAccount() != null) {
                    sumLeaveReinvestAccount = sumLeaveReinvestAccount.add(record.getLeaveReinvestAccount());
                }
            }
        }

        // 分页
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count.intValue(), request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        // 增加分页查询查找具体数据
        List<HjhPlanCapitalActual> recordList = this.hjhPlanCapitalActualService.getPlanCapitalActualList(request);

        if (CollectionUtils.isNotEmpty(recordList)) {
            List<HjhPlanCapitalActualVO> hjhPlanCapitalVOList = CommonUtils.convertBeanList(recordList, HjhPlanCapitalActualVO.class);

            for(HjhPlanCapitalActualVO vo: hjhPlanCapitalVOList) {
                // 为服务回报期限 添加单位
                if(null != vo.getIsMonth()) {
                    if (vo.getIsMonth() == 0) {
                        vo.setLockPeriodView(vo.getLockPeriod() + "天");
                    } else {
                        vo.setLockPeriodView(vo.getLockPeriod() + "个月");
                    }
                } else {
                    vo.setLockPeriodView(vo.getLockPeriod() + "");
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                vo.setStrDate(formatter.format(vo.getDate()));
            }

            HjhPlanCapitalActualVO hjhPlanCapitalVO = new HjhPlanCapitalActualVO();
            hjhPlanCapitalActualResponse.setResultList(hjhPlanCapitalVOList);
            hjhPlanCapitalActualResponse.setCount(count);

            hjhPlanCapitalVO.setAddCreditAccount(sumAddCreditAccount);
            hjhPlanCapitalVO.setCreateCreditAccount(sumCreateCreditAccount);
            hjhPlanCapitalVO.setUsedCreditAccount(sumUsedCreditAccount);
            hjhPlanCapitalVO.setLeaveCreditAccount(sumLeaveCreditAccount);
            hjhPlanCapitalVO.setAddReinvestAccount(sumAddReinvestAccount);
            hjhPlanCapitalVO.setSumReinvestAccount(sumSumReinvestAccount);
            hjhPlanCapitalVO.setUsedReinvestAccount(sumUsedReinvestAccount);
            hjhPlanCapitalVO.setLeaveReinvestAccount(sumLeaveReinvestAccount);
            hjhPlanCapitalActualResponse.setSumHjhPlanCapitalActualVO(hjhPlanCapitalVO);
            hjhPlanCapitalActualResponse.setRtn(Response.SUCCESS);
        }
        return hjhPlanCapitalActualResponse;
    }



}
