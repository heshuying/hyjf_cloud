package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.TzjService;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author xiasq
 * @version TzjController, v0.1 2018/7/9 14:05
 */
@RestController
@RequestMapping("/am-user/tzj")
public class TzjController extends BaseController {
	@Autowired
	TzjService tzjService;

	/**
	 * 查询投之家当天注册人数、开户人数、绑卡人数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserDataOnToday")
	public TzjDayReportResponse queryUserDataOnToday(@RequestBody @Valid TzjDayReportRequest request) {
		Date startDate = request.getStartTime();
		Date endDate = request.getEndTime();
		TzjDayReportResponse response = new TzjDayReportResponse();
		TzjDayReportVO vo = new TzjDayReportVO();
		vo.setRegistCount(tzjService.getRegistCount(startDate, endDate));
		vo.setOpenCount(tzjService.getOpenCount(startDate, endDate));
		vo.setCardBindCount(tzjService.getCardBindCount(startDate, endDate));
		response.setResult(vo);
		return response;
	}

	/**
	 * 查询投之家注册用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryRegisterUsersOnToday")
	public TzjDayReportResponse queryRegisterUsers(@RequestBody @Valid TzjDayReportRequest request) {
		TzjDayReportResponse response = new TzjDayReportResponse();
		response.setUserIds(tzjService.queryRegisterUsers(request.getStartTime(), request.getEndTime()));
		return response;
	}

	/**
	 * 查询投之家所有注册用户
	 * @return
	 */
	@RequestMapping("/queryAllTzjUsers")
	public TzjDayReportResponse queryAllTzjUsers() {
		TzjDayReportResponse response = new TzjDayReportResponse();
		response.setUserIds(tzjService.queryAllTzjUsers());
		return response;
	}
}
