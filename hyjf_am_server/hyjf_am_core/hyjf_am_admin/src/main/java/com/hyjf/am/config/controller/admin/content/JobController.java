/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Job;
import com.hyjf.am.config.service.JobService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.resquest.admin.JobRequest;
import com.hyjf.am.vo.config.JobsVo;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version JobController, v0.1 2018/7/12 14:32
 */
@RestController
@RequestMapping("/am-config/content/contentjob")
public class JobController extends BaseConfigController {
	@Autowired
	private JobService jobService;

	/**
	 * 根据条件查询公司管理-招贤纳士
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public JobResponse searchAction(@RequestBody JobRequest request) {
		logger.info("查询公司管理-招贤纳士开始......");
		JobResponse response = new JobResponse();
		List<Job> list = jobService.searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<JobsVo> voList = CommonUtils.convertBeanList(list, JobsVo.class);
			response.setResultList(voList);
		}
		// 查询满足条件的件数
		int count = jobService.selectCount(request);
		response.setCount(count);
		return response;
	}

	/**
	 * 添加公司管理-招贤纳士
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public IntegerResponse insertAction(@RequestBody JobRequest request) {
		int num = jobService.insertAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 修改公司管理-招贤纳士
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public IntegerResponse updateAction(@RequestBody JobRequest request) {
		int num =  jobService.updateAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 根据id查询公司管理-招贤纳士
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public JobResponse getRecord(@PathVariable Integer id) {
		JobResponse response = new JobResponse();
		Job job = jobService.getRecord(id);
		if (job != null) {
			JobsVo vo = new JobsVo();
			BeanUtils.copyProperties(job, vo);
			response.setResult(vo);
		}
		return response;
	}

	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public IntegerResponse delete(@PathVariable Integer id) {
		int num = jobService.delete(id);
		return new IntegerResponse(num);
	}
}
