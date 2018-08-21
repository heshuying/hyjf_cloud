/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.SmsCountService;
import com.hyjf.am.response.admin.SmsCountCustomizeResponse;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version SmsCountServiceImpl, v0.1 2018/8/17 14:01
 */
@Service
public class SmsCountServiceImpl implements SmsCountService {
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public SmsCountCustomizeResponse querySmsCountList(SmsCountCustomizeVO request) {
        return amUserClient.querySmsCountList(request);
    }

    @Override
    public Integer querySmsCountNumberTotal(SmsCountCustomizeVO request) {
        return amUserClient.querySmsCountNumberTotal(request);
    }

    @Override
    public JSONArray getCrmDepartmentList(String[] list) {
        List<OADepartmentCustomizeVO> departmentList = amUserClient.queryDepartmentInfo(null);

        Map<String, String> map = new HashMap<String, String>();
        if (departmentList != null && departmentList.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartment : departmentList) {
                map.put(String.valueOf(oaDepartment.getId()), HtmlUtil.unescape(oaDepartment.getName()));
            }
        }
        return treeDepartmentList(departmentList, map, list, "0", "");
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
