/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.user.calendar;

import java.util.*;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.calendar.RepayCalendarService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.common.util.CustomConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author dangzw
 * @version RepayCalendarController, v0.1 2018/7/27 11:30
 */
@Api(tags = "app端-app日历")
@RestController
@RequestMapping(value = "/hyjf-app/user/repayCalendar")
public class RepayCalendarController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(RepayCalendarController.class);
    @Autowired
    private RepayCalendarService repayCalendarService;

    @ResponseBody
    @ApiOperation(value = "日历", httpMethod = "POST", notes = "日历")
    @PostMapping(value = "/getRepayCalendar")
    public JSONObject getRepayCalendar(@RequestParam(required = false) String year,
                                       @RequestParam(required = false) String month,
                                       @RequestParam(value = "page") String page,
                                       @RequestParam(value = "pageSize") String pageSize,
                                       @RequestHeader(value = "userId") Integer userId
                                       ) {
        logger.info(RepayCalendarController.class.toString(), "startLog -- /hyjf-app/user/repayCalendar/getRepayCalendar");
        logger.info("getRepayCalendar start, year is :{}, month is :{}", year, month);
        JSONObject info = new JSONObject();
        info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
        logger.info("page is :{}, pageSize is :{}", page, pageSize);
        // 唯一标识
        logger.debug("userId is :{}", userId);
        // 构造查询参数列表
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        if(StringUtils.isNotBlank(year)){
            params.put("tradeYear", Integer.parseInt(year));
        }
        if(StringUtils.isNotBlank(month)){
            params.put("tradeMonth", Integer.parseInt(month));
        }

        this.buildPageCondition(page, pageSize, params);
        this.createRepayCalendar(info, params, year, month);

        info.put(CustomConstants.APP_REQUEST, "/hyjf-app/user/repayCalendar/getRepayCalendar");
        logger.info(RepayCalendarController.class.toString(), "endLog -- /hyjf-app/user/repayCalendar/getRepayCalendar");
        return info;
    }

    /**
     * 构建分页参数
     *
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    private Map<String, Object> buildPageCondition(String page, String pageSize, Map<String, Object> params) {

        int limit = Integer.parseInt(pageSize);

        int offSet = (Integer.parseInt(page) - 1) * limit;
        if (offSet == 0 || offSet > 0) {
            params.put("limitStart", offSet);
        }
        if (limit > 0) {
            params.put("limitEnd", limit);
        }

        return params;
    }

    /***
     * 创建分页数据
     *
     * @param info
     * @param params
     */
    private void createRepayCalendar(JSONObject info, Map<String, Object> params, String year, String month) {
        //查询回款日历总数
        Integer recordTotal = repayCalendarService.countRepaymentCalendar(params);
        if (recordTotal > 0) {
            //查询回款日历明细
            List<AppReapyCalendarResultVO> repayPlanDetail = repayCalendarService.searchRepaymentCalendar(params);
            //返回用户最近回款时间戳-秒
            info.put("beginTime", repayCalendarService.searchNearlyRepaymentTime(params));

            info.put("repayPlanDetail", repayPlanDetail);
            info.put("repayPlanTotal", String.valueOf(recordTotal));
        } else {
            logger.info("未查询到数据,返回月份标题....");
            info.put("repayPlanDetail", buildNoneRepaymentReturn(year, month));
            info.put("repayPlanTotal", "0");
        }
    }

    /**
     * 没有交易明细的返回json
     *
     * @param year
     * @param month
     * @return
     */
    private List<AppReapyCalendarResultVO> buildNoneRepaymentReturn(String year, String month) {
        List<AppReapyCalendarResultVO> list = new ArrayList<>();
        AppReapyCalendarResultVO customize = new AppReapyCalendarResultVO();
        Calendar cal = Calendar.getInstance();
        int newYear = cal.get(Calendar.YEAR);
        int newMonth = cal.get(Calendar.MONTH) + 1;

        // 没有年月的过滤条件，默认当前
        int paramYear = newYear;
        int paramMonth = newMonth;
        if (StringUtils.isNotBlank(year)) {
            paramYear = Integer.parseInt(year);
        }
        if (StringUtils.isNotBlank(month)) {
            paramMonth = Integer.parseInt(month);
        }

        if (paramYear != newYear) {
            customize.setMonth(year + "年" + month + "月");
        } else {
            if (paramMonth != newMonth) {
                customize.setMonth(month + "月");
            } else {
                customize.setMonth("本月");
            }
        }
        customize.setIsMonth("0");
        list.add(customize);
        return list;
    }

}
