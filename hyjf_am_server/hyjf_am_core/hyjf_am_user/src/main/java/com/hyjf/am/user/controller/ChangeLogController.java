package com.hyjf.am.user.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hyjf.am.response.Response;

import com.hyjf.am.response.user.ChangeLogResponse;

import com.hyjf.am.resquest.user.ChangeLogRequest;
import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;
import com.hyjf.am.user.service.ChangeLogService;

import com.hyjf.am.vo.user.ChangeLogVO;
import com.hyjf.common.paginator.Paginator;


/**
 * 用户信息修改Controller
 * @author dongzeshan
 */
 @RestController
 @RequestMapping("/am-user/changelog")
public class ChangeLogController {

	@Autowired
	private ChangeLogService changeLogService;
	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	// 查询授权明细
	@PostMapping("/init")
	public ChangeLogResponse userauthlist(
			@RequestBody @Valid ChangeLogRequest changeLogRequest) {
		ChangeLogCustomize change=new ChangeLogCustomize();
		BeanUtils.copyProperties(changeLogRequest,change);
		int recordTotal = this.changeLogService.countRecordTotal(change);
		if (recordTotal > 0) {
			Paginator paginator = new Paginator(changeLogRequest.getPaginatorPage(), recordTotal);
			change.setLimitStart(paginator.getOffset());
			change.setLimitEnd(paginator.getLimit());
			List<ChangeLogCustomize> recordList = this.changeLogService.getChangeLogList(change);
			
			ChangeLogResponse aualr = new ChangeLogResponse();
			List<ChangeLogVO> avo = null;
			if (recordList != null) {
				BeanUtils.copyProperties(recordList, avo);
				aualr.setResultList(avo);
				aualr.setRecordTotal(recordTotal);
				aualr.setRtn(Response.SUCCESS);
			}
			return aualr;
		}
		return null;

	}



}
