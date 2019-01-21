/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.SmsLogResponse;
import com.hyjf.am.response.admin.SmsOntimeResponse;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsLogVO;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.SmsLog;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.service.message.SmsLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 14:10
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/smsLog")
public class SmsLogController extends BaseController {

	@Autowired
	private SmsLogService smsLogService;

	/**
	 * 查询消息中心短信发送记录
	 * @return
	 */
	@RequestMapping("/list")
	public SmsLogResponse smsLogList() {
        SmsLogResponse response = new SmsLogResponse();
		List<SmsLog> list = smsLogService.findSmsLogList();
		if (!CollectionUtils.isEmpty(list)) {
            List<SmsLogVO> voList = CommonUtils.convertBeanList(list, SmsLogVO.class);
            response.setLogCount(voList.size());
            response.setResultList(voList);
        }
		return response;
	}

	/**
	 * 根据条件查询消息中心短信发送记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/find")
	public SmsLogResponse findSmsLog(@RequestBody SmsLogRequest request) {
        SmsLogResponse response = new SmsLogResponse();
		List<SmsLog> list = smsLogService.findSmsLog(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<SmsLogVO> voList = CommonUtils.convertBeanList(list, SmsLogVO.class);
            response.setResultList(voList);
        }
		int count = smsLogService.queryLogCount(request);
        response.setLogCount(count);
        return response;
	}

	/**
	 * 查询定时发送短信列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryTime")
	public SmsOntimeResponse queryTime(@RequestBody SmsLogRequest request) {
		SmsOntimeResponse response = new SmsOntimeResponse();
		List<SmsOntime> list = smsLogService.queryTime(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<SmsOntimeVO> voList = CommonUtils.convertBeanList(list, SmsOntimeVO.class);
			/*voList.stream().forEach(e -> e.setPostString(
					e.getEndtime() == null ? null : GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(e.getEndtime())));*/
			for (SmsOntimeVO vo : voList) {
				if (StringUtils.isBlank(vo.getMobile())) {
					String member = "";
					String money = "";
					String add_time_end = "";
					String add_time_begin = "";
					String re_time_end = "";
					String re_time_begin = "";
					if (vo.getOpenAccount() != null && vo.getOpenAccount() == 0) {
						member = "所有未开户用户";
					} else if (vo.getOpenAccount() != null && vo.getOpenAccount() == 1) {
						member = "所有已开户用户";
					} else if (vo.getOpenAccount() != null && vo.getOpenAccount() == 3) {
						member = "所有用户";
					}

					if (vo.getAddMoneyCount() != null && vo.getAddMoneyCount().compareTo(new BigDecimal(0)) != 0) {
						money = vo.getAddMoneyCount() + "";
					}
					if (vo.getAddTimeBegin() != null && !vo.getAddTimeBegin().equals("")) {
						add_time_begin = vo.getAddTimeBegin();
					}
					if (vo.getAddTimeEnd() != null && !vo.getAddTimeEnd().equals("")) {
						add_time_end = vo.getAddTimeEnd();
					}
					if (vo.getReTimeBegin() != null && !vo.getReTimeBegin().equals("")) {
						re_time_begin = vo.getReTimeBegin();
					}
					if (vo.getReTimeEnd() != null && !vo.getReTimeEnd().equals("")) {
						re_time_end = vo.getReTimeEnd();
					}
					vo.setMobile("筛选会员:" + member + ",累计出借金额：" + money + ",用户出借日期段：" + add_time_begin + "-"
							+ add_time_end + ",用户注册日期段：" + re_time_begin + "-" + re_time_end);
				}
				vo.setPostString(
						vo.getEndtime() == null ? null : GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(vo.getEndtime()));
			}

			response.setResultList(voList);
		}
		int count = smsLogService.queryOntimeCount(request);
		response.setCount(count);
		return response;
	}

	/**
	 * 查询条件查询短信记录列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryLogCount")
	public SmsLogResponse queryLogCount(@RequestBody SmsLogRequest request) {
		SmsLogResponse response = new SmsLogResponse();
		Integer logCount = smsLogService.queryLogCount(request);
		if (logCount != null) {
			response.setLogCount(logCount);
		}
		return response;
	}
}
