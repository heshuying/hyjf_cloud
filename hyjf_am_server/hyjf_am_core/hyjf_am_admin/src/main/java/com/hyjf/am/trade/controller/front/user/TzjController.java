package com.hyjf.am.trade.controller.front.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.trade.bean.TzjDayReportBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.task.TzjService;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author xiasq
 * @version TzjController, v0.1 2018/7/9 9:28
 */
@RequestMapping("/am-trade/tzj")
@RestController
public class TzjController extends BaseController {
	@Autowired
	TzjService tzjService;

	/**
	 * 查询投之家当日投资数据：每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryTradeDataOnToday")
	public TzjDayReportResponse queryTradeDataOnToday(@RequestBody @Valid TzjDayReportRequest request) {
		TzjDayReportResponse response = new TzjDayReportResponse();

		TzjDayReportBean tzjDayReportBean = tzjService.queryTradeDataOnToday(request.getTzjUserIds(),
				request.getStartTime(), request.getEndTime());
		if (tzjDayReportBean != null) {
			TzjDayReportVO vo = new TzjDayReportVO();
			BeanUtils.copyProperties(tzjDayReportBean, vo);
			response.setResult(vo);
		} else {
			logger.info("今日无投之家投资数据...request is：{}", JSONObject.toJSONString(request));
		}
		return response;
	}

	/**
	 * 查询投之家当日新充人数 新投人数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryTradeNewDataOnToday")
	public TzjDayReportResponse queryTradeNewDataOnToday(@RequestBody @Valid TzjDayReportRequest request) {
		TzjDayReportResponse response = new TzjDayReportResponse();

		TzjDayReportBean tzjDayReportBean = tzjService.queryTradeNewDataOnToday(request.getTzjUserIds(),
				request.getStartTime(), request.getEndTime());
		if (tzjDayReportBean != null) {
			TzjDayReportVO vo = new TzjDayReportVO();
			BeanUtils.copyProperties(tzjDayReportBean, vo);
			response.setResult(vo);
		} else {
			logger.info("今日无投之家新投资数据...request is：{}", JSONObject.toJSONString(request));
		}
		return response;
	}

}
