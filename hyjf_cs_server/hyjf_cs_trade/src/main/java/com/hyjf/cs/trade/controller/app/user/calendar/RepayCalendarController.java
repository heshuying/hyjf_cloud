/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.user.calendar;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.calendar.RepayCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author dangzw
 * @version RepayCalendarController, v0.1 2018/7/27 11:30
 */
@Api(tags = "app-回款日历")
@RestController
@RequestMapping(value = "/hyjf-app/user/repayCalendar")
public class RepayCalendarController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(RepayCalendarController.class);
    private static final String CALENDAR = "/hyjf-app/user/repayCalendar";
    private static final String REQUEST = "/getRepayCalendar";
    @Autowired
    private RepayCalendarService repayCalendarService;


    @ResponseBody
    @ApiOperation(value = "回款日历", httpMethod = "POST", notes = "回款日历")
    @PostMapping(value = CALENDAR)
    public JSONObject getRepayCalendar(@RequestParam(required = false) String year,
                                       @RequestParam(required = false) String month,
                                       @RequestHeader(value = "userId") Integer userId,
                                       @RequestParam(value = "page", defaultValue = "1") String page,
                                       @RequestParam(value = "pageSize", defaultValue = "10") String pageSize) {
        logger.info("app-回款日历列表查询[开始] 接口路径");
        logger.info("请求参数 year:{}, month:{}, userId:{}, page:{}, pageSize:{}", year, month, userId, page, pageSize);

        JSONObject info = new JSONObject();
        // 构造查询参数列表
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        if(StringUtils.isNotBlank(year)){
            params.put("tradeYear", Integer.parseInt(year));
        }
        if(StringUtils.isNotBlank(month)){
            params.put("tradeMonth", Integer.parseInt(month));
        }

        // 构建分页参数
        this.buildPageCondition(page, pageSize, params);
        // 创建分页数据
        this.createRepayCalendar(info, params, year, month);

        info.put(CustomConstants.APP_REQUEST, CALENDAR+REQUEST);
        info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
        logger.info("app-回款日历列表查询[结束]");
        return info;
    }


    /**
     * 构建分页参数
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


    /**
     * 创建分页数据
     * @param info
     * @param params
     * @param year
     * @param month
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
