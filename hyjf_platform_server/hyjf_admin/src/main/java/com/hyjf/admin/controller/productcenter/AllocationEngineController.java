/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
}
