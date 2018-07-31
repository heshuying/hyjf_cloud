/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.RepayCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author dangzw
 * @version RepayCalendarController, v0.1 2018/7/27 11:30
 */
@Api(value = "app", description = "app")
@RestController
@RequestMapping("/hyjf-app/user")
public class RepayCalendarController extends BaseMarketController {

    @Autowired
    private RepayCalendarService repayCalendarService;

    @ResponseBody
    @ApiOperation(value = "日历", notes = "日历")
    @RequestMapping(value = "/repayCalendar/getRepayCalendar", method = RequestMethod.POST ,produces = "application/json; charset=utf-8")
    public JSONObject getRepayCalendar(@RequestParam(required = false) String year,
                                       @RequestParam(required = false) String month, HttpServletRequest request) {
        logger.info("getRepayCalendar start, year is :{}, month is :{}", year, month);
        JSONObject info = new JSONObject();
        info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);

        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        logger.info("page is :{}, pageSize is :{}", page, pageSize);
        if (StringUtils.isBlank(page) || StringUtils.isBlank(pageSize)) {
            info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
            info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_FAIL);
            return info;
        }

        // 唯一标识
        String sign = request.getParameter("sign");
        logger.info("sign is :{}", sign);
        if (StringUtils.isBlank(sign)) {
            info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
            info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_FAIL);
            return info;
        }
        // 用户id
        Integer userId = null;
        try{
            userId = SecretUtil.getUserId(sign);
        }catch (Exception e){
            logger.error("解析sign错误...", e);
            info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
            info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_FAIL);
            return info;
        }

        //userId = Integer.parseInt(request.getParameter("userId"));
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

        Integer recordTotal = repayCalendarService.countRepaymentCalendar(params);
        if (recordTotal > 0) {

            List<AppReapyCalendarResultVO> repayPlanDetail = repayCalendarService.searchRepaymentCalendar(params);

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
