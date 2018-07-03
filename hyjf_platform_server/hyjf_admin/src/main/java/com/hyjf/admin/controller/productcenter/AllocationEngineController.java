/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AllocationEngineService;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.common.util.GetDate;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * @author libin
 * @version AllocationEngineController.java, v0.1 2018年7月3日 上午11:46:27
 */
@Api(value = "计划专区列表")
@RestController
@RequestMapping("/hyjf-admin/allocation")
public class AllocationEngineController extends BaseController{

	@Autowired
	private AllocationEngineService allocationEngineService;
	
    /**
     * 画面初始化
     *
     * @param request
     * @return 标的分配规则引擎列表
     */
    @ApiOperation(value = "计划专区列表", notes = "计划专区列表初始化")
    @PostMapping(value = "/search")
    @ResponseBody
    public JSONObject search(HttpServletRequest request, @RequestBody @Valid AllocationEngineRuquest form) {
    	// 画面检索 计划编号/计划名称/添加时间/专区状态 无需初始化
    	JSONObject jsonObject = new JSONObject();
    	// 根据删选条件获取计划专区列表
    	List<HjhRegionVO> list = this.allocationEngineService.getHjhRegionList(form);
        if (null != list && list.size() > 0) {
            jsonObject.put("record", list);
            int count = list.size();
            success(String.valueOf(count),list);
        } else {
        	fail("标签配置列表查询为空！");
        }
        return jsonObject;
    }
    
	/**
	 * 计划专区列表 添加/修改 info画面
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "计划专区列表", notes = "生成添加/修改画面")
	@PostMapping(value = "/info")
	@ResponseBody
	// 计划专区根据业务要求只有添加计划而没有修改计划
	public JSONObject getAddOrModifyView(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineRuquest form) {
		JSONObject jsonObject = new JSONObject();
		// 初始画面没有需要初期化的数据
		// 计划编号 和 状态
		return jsonObject;
	}
	
	/**
	 * 计划专区列表 info画面确认后添加计划到专区
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "计划专区列表", notes = "添加画面确认后添加到计划专区")
	@PostMapping(value = "/insert")
	@ResponseBody
	public JSONObject addPlanToRegion(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineRuquest form) {
		JSONObject jsonObject = new JSONObject();
		// 与实体bean一致 用于转型
		HjhRegionVO VOrequest = new HjhRegionVO();
		// 获取当前登陆者id
		int userId = Integer.valueOf(this.getUser(request).getId());
		// 画面验证
		this.validatorFieldCheck(jsonObject, form);
		/*准备数据*/
		//1.计划编号
		VOrequest.setPlanNid(form.getPlanNidSrch());
		//2.计划名称
		String planName = this.allocationEngineService.getPlanNameByPlanNid(form);
		VOrequest.setPlanName(planName);
		//3.添加时间
		VOrequest.setConfigAddTime(GetDate.getNowTime10());
		//4.状态
		VOrequest.setConfigStatus(Integer.valueOf(form.getConfigStatus()));
		//5.添加人
		VOrequest.setCreateUser(userId);
		// 插表
		int flg = this.allocationEngineService.insertRecord(VOrequest);
		if(flg > 0){
			success();
		}
		return jsonObject;
	}
	
	/**
	 * 画面入力校验
	 * @param jsonObject,map
	 * @return
	 */
	private void validatorFieldCheck(JSONObject jsonObject, AllocationEngineRuquest form) {
		// 计划编号非空判断(状态是二选一不需要判断)
		if(StringUtils.isEmpty(form.getPlanNidSrch())){
			jsonObject.put("validatorMsg1", "在专区中添加计划时请输入计划编号!");
		}
	}
      
}
