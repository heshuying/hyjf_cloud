/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.HjhCommissionService;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.response.admin.OADepartmentResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.common.http.HtmlUtil;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.ArrayUtils;
/**
 * @author libin
 * @version HjhCommissionServiceImpl.java, v0.1 2018年8月7日 下午2:45:19
 */
@Service
public class HjhCommissionServiceImpl implements HjhCommissionService{
	
	@Autowired
	public AmTradeClient amTradeClient;

	@Override
	public HjhCommissionResponse selectHjhCommissionList(HjhCommissionRequest form) {
		HjhCommissionResponse response = amTradeClient.selectHjhCommissionList(form);
		return response;
	}

	@Override
	public HjhCommissionResponse selecthjhCommissionTotal(HjhCommissionRequest form) {
		HjhCommissionResponse response = amTradeClient.selecthjhCommissionTotal(form);
		return response;
	}

	@Override
	public TenderCommissionVO queryTenderCommissionByPrimaryKey(int ids) {
		TenderCommissionVO vo = amTradeClient.queryTenderCommissionByPrimaryKey(ids);
		return vo;
	}

	@Override
	public JSONArray getCrmDepartmentList(String[] list) {
		// 实际未传任何参数
		HjhCommissionRequest form = new HjhCommissionRequest();
		List<OADepartmentCustomizeVO> departmentList = null;
		OADepartmentResponse response = amTradeClient.getCrmDepartmentList(form);
		if(response != null){
			departmentList = response.getResultList();
		}
		Map<String, String> map = new HashMap<String, String>();
		if (departmentList != null && departmentList.size() > 0) {
			for (OADepartmentCustomizeVO oaDepartment : departmentList) {
				map.put(String.valueOf(oaDepartment.getId()), HtmlUtil.unescape(oaDepartment.getName()));
			}
		}
		// 部门树形结构
		return this.treeDepartmentList(departmentList, map, list, "0", "");
	}
	
	/**
	 * 部门树形结构
	 *
	 * @param departmentTreeDBList
	 * @param topParentDepartmentCd
	 * @return
	 */
	private JSONArray treeDepartmentList(List<OADepartmentCustomizeVO> departmentTreeDBList, Map<String, String> map, String[] selectedNode, String topParentDepartmentCd,
			String topParentDepartmentName) {
		JSONArray ja = new JSONArray();
		JSONObject joAttr = new JSONObject();
		if (departmentTreeDBList != null && departmentTreeDBList.size() > 0) {
			JSONObject jo = null;
			for (OADepartmentCustomizeVO departmentTreeRecord : departmentTreeDBList) {
				jo = new JSONObject();
				jo.put("id", departmentTreeRecord.getId());
				jo.put("text", departmentTreeRecord.getName());
				joAttr = new JSONObject();
				joAttr.put("id", departmentTreeRecord.getId());
				joAttr.put("parentid", departmentTreeRecord.getParentid());
				joAttr.put("parentname", Validator.isNull(topParentDepartmentName) ? "" : topParentDepartmentName);
				joAttr.put("name", departmentTreeRecord.getName());
				joAttr.put("listorder", departmentTreeRecord.getListorder());
				jo.put("li_attr", joAttr);
				if (Validator.isNotNull(selectedNode) && ArrayUtils.contains(selectedNode, String.valueOf(departmentTreeRecord.getId()))) {
					JSONObject selectObj = new JSONObject();
					selectObj.put("selected", true);
					// selectObj.put("opened", true);
					jo.put("state", selectObj);
				}
				String departmentCd = String.valueOf(departmentTreeRecord.getId());
				String departmentName = String.valueOf(departmentTreeRecord.getName());
				String parentDepartmentCd = String.valueOf(departmentTreeRecord.getParentid());
				if (topParentDepartmentCd.equals(parentDepartmentCd)) {
					JSONArray array = treeDepartmentList(departmentTreeDBList, map, selectedNode, departmentCd, departmentName);
					jo.put("children", array);
					ja.add(jo);
				}
			}
		}
		return ja;
	}
}
