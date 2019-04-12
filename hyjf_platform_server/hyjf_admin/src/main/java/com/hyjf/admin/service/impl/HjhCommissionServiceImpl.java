/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.controller.finance.hjhcommission.HjhCommissionController;
import com.hyjf.admin.service.HjhCommissionService;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.response.admin.OADepartmentResponse;
import com.hyjf.am.resquest.admin.CommissionComboRequest;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author libin
 * @version HjhCommissionServiceImpl.java, v0.1 2018年8月7日 下午2:45:19
 */
@Service
public class HjhCommissionServiceImpl implements HjhCommissionService{
	
	private static final Logger logger = LoggerFactory.getLogger(HjhCommissionController.class);
	
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

	/**
	 * 根据userId获取提成方式
	 * 此方法后期可以做成基类的方法
	 * @return
	 */
	@Override
	public Integer queryCrmCuttype(Integer userId) {
		Integer type = amTradeClient.queryCrmCuttype(userId);
		return type;
	}

	/**
	 * 汇计划发提成
	 * @return
	 */
	@Override
	public Integer updateTenderCommissionRecord(TenderCommissionVO commission, BankCallBean resultBean,
			ChinapnrBean chinapnrBean) {
		Integer ret = 0;
		// 初始化请求综合体
		CommissionComboRequest request = new CommissionComboRequest();
		// 将参数拼装
		if(commission != null){
			request.setTenderCommission(commission);
		} else {
			// modify by libin sonar start
			logger.error("controller传过来的提成记录为空！");
			return 0;
			// modify by libin sonar end
		}
		if(resultBean != null){
			request.setTxAmount(resultBean.getTxAmount());
			request.setSeqNo(resultBean.getSeqNo());
			request.setTxDate(resultBean.getTxDate());
			request.setTxTime(resultBean.getTxTime());
			request.setLogIp(resultBean.getLogIp());
			request.setAccountId(resultBean.getAccountId());
			request.setLogOrderId(resultBean.getLogOrderId());
		}
		if(chinapnrBean !=null){
			request.setTransAmt(chinapnrBean.getTransAmt());
			request.setChinapnrlogIp(chinapnrBean.getLogIp());
		}
		// 单独放到request中，不用bean copy ，因为原生 commission 不易修改
		if(commission.getAccount()!= null && StringUtils.isNotEmpty(commission.getAccount())){
			request.setAccount(commission.getAccount());
		}
		// 单独放到request中，防止 bean copy，因为原生 commission 不易修改
		request.setUserName(commission.getUserName());
		request.setRegionName(commission.getRegionName());
		request.setBranchName(commission.getBranchName());
		request.setDepartmentName(commission.getBranchName());
		request.setTrueName(commission.getTrueName());
		request.setSex(commission.getSex());
		request.setMobile(commission.getMobile());
		request.setAttribute(commission.getAttribute());
		ret = amTradeClient.updateTenderCommissionRecord(request);
		return ret;
	}

	@Override
	public HjhCommissionResponse selectHjhCommissionListWithOutPage(HjhCommissionRequest form) {
		HjhCommissionResponse response = amTradeClient.selectHjhCommissionListWithOutPage(form);
		return response;
	}

	/**
	 * 资金中心-汇计划提成导出记录总数
	 * @param request
	 * @return
	 */
	@Override
	public int getHjhCommissionCountForExport(HjhCommissionRequest request) {
		return amTradeClient.getHjhCommissionCountForExport(request);
	}
}
