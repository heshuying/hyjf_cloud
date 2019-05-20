/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalPredictionResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalPredictionRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalPrediction;
import com.hyjf.cs.message.service.plancapital.HjhPlanCapitalPredictionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 资金计划-预计新增复投/债转额相关mongo操作
 *
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalPredictionController, v0.1 2019/4/16 14:19
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/hjhPlanCapitalPrediction")
public class HjhPlanCapitalPredictionController extends BaseController {

    @Autowired
    private HjhPlanCapitalPredictionService hjhPlanCapitalPredictionService;

    /**
     * 汇计划 -- 资金计划 -- 预计相关字段 insert
     *
     * @param list
     * @return
     * @Author : liushouyi
     */
    @PostMapping(value = "/insertPlanCaptialPrediction")
    public BooleanResponse insertPlanCaptialPrediction(@RequestBody List<HjhPlanCapitalPredictionVO> list) {
        if (null != list || list.size() > 0) {
            return new BooleanResponse(this.hjhPlanCapitalPredictionService.insertPlanCaptialPrediction(list));
        } else {
            return new BooleanResponse(true);
        }
    }

    /**
     * 汇计划 -- 资金计划 -- 历史数据update delFlg->1
     *
     * @param initDate
     * @return
     * @Author : liushouyi
     */
    @GetMapping(value = "/updatePlanCaptialPrediction/{initDate}")
    public BooleanResponse updatePlanCaptialPrediction(@PathVariable String initDate) {
        return new BooleanResponse(this.hjhPlanCapitalPredictionService.updatePlanCaptialPrediction( GetDate.stringToDate2(initDate)));
    }

    /**
     * 汇计划 -- 资金计划 count
     *
     * @param request
     * @return
     * @Author : liushouyi
     */
    @RequestMapping(value = "/getPlanCapitalPredictionCount")
    public Integer getPlanCapitalPredictionCount(@RequestBody HjhPlanCapitalPredictionRequest request) {
        return this.hjhPlanCapitalPredictionService.getPlanCapitalPredictionCount(request);
    }

    /**
     * 汇计划 -- 资金计划列表
     *
     * @param request
     * @return
     * @Author : liushouyi
     */
    @RequestMapping(value = "/getPlanCapitalPredictionList", method = RequestMethod.POST)
    public HjhPlanCapitalPredictionResponse getPlanCapitalPredictionList(@RequestBody HjhPlanCapitalPredictionRequest request) {
        HjhPlanCapitalPredictionResponse hjhPlanCapitalPredictionResponse = new HjhPlanCapitalPredictionResponse();

        // 初始化总计数据
        BigDecimal sumCreditAccount = BigDecimal.ZERO;
        BigDecimal sumReinvestAccount = BigDecimal.ZERO;
        BigDecimal sumCapitalAccount = BigDecimal.ZERO;
        BigDecimal sumAssetAccount = BigDecimal.ZERO;

        //总计条数
        Integer count = this.hjhPlanCapitalPredictionService.getPlanCapitalPredictionCount(request);

        // 不加分页查询所有数据计算sum总值
        List<HjhPlanCapitalPrediction> recordAllList = this.hjhPlanCapitalPredictionService.getPlanCapitalPredictionList(request);
        // 为查询道数据时返回
        if (CollectionUtils.isNotEmpty(recordAllList)) {
            // 统计复投总额和债转总额
            for (HjhPlanCapitalPrediction record : recordAllList) {
                if (record.getReinvestAccount() != null) {
                    sumReinvestAccount = sumReinvestAccount.add(record.getReinvestAccount());
                }
                if (record.getCreditAccount() != null) {
                    sumCreditAccount = sumCreditAccount.add(record.getCreditAccount());
                }
                if (record.getCapitalAccount() != null) {
                    sumCapitalAccount = sumCapitalAccount.add(record.getCreditAccount());
                }
                if (record.getAssetAccount() != null) {
                    sumAssetAccount = sumAssetAccount.add(record.getAssetAccount());
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
        List<HjhPlanCapitalPrediction> recordList = this.hjhPlanCapitalPredictionService.getPlanCapitalPredictionList(request);

        if (CollectionUtils.isNotEmpty(recordList)) {
            List<HjhPlanCapitalPredictionVO> hjhPlanCapitalVOList = CommonUtils.convertBeanList(recordList, HjhPlanCapitalPredictionVO.class);

            for (HjhPlanCapitalPredictionVO vo : hjhPlanCapitalVOList) {
                if (null != vo.getIsMonth()) {
                    // 为服务回报期限 添加单位
                    if (vo.getIsMonth() == 0) {
                        vo.setLockPeriodView(vo.getLockPeriod() + "天");
                    } else {
                        vo.setLockPeriodView(vo.getLockPeriod() + "个月");
                    }
                } else {
                    vo.setLockPeriodView(vo.getLockPeriod() + "");
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                vo.setStringDate(formatter.format(vo.getDate()));
                try {
                    // 时间格式化
                    if (null != vo.getDualBaseDate()) {
                        vo.setDualBaseDateStr(formatter.format(vo.getDualBaseDate()));
                    }
                } catch (Exception e) {
                    logger.error("预估数据日期格式化失败!" + e);
                }
            }

            HjhPlanCapitalPredictionVO hjhPlanCapitalVO = new HjhPlanCapitalPredictionVO();
            hjhPlanCapitalPredictionResponse.setResultList(hjhPlanCapitalVOList);
            hjhPlanCapitalPredictionResponse.setCount(count);
            hjhPlanCapitalVO.setReinvestAccount(sumReinvestAccount);
            hjhPlanCapitalVO.setCreditAccount(sumCreditAccount);
            hjhPlanCapitalVO.setCapitalAccount(sumCapitalAccount);
            hjhPlanCapitalVO.setAssetAccount(sumAssetAccount);
            hjhPlanCapitalPredictionResponse.setSumHjhPlanCapitalPredictionVO(hjhPlanCapitalVO);
            hjhPlanCapitalPredictionResponse.setRtn(Response.SUCCESS);
        }
        return hjhPlanCapitalPredictionResponse;
    }

}
