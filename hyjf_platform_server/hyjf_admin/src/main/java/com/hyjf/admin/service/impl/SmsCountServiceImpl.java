/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.SmsCountService;
import com.hyjf.am.response.admin.SmsCountCustomizeResponse;
import com.hyjf.am.resquest.admin.ListRequest;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author fq
 * @version SmsCountServiceImpl, v0.1 2018/8/17 14:01
 */
@Service
public class SmsCountServiceImpl implements SmsCountService {
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public SmsCountCustomizeResponse querySmsCountList(SmsCountRequest request) {
        return amUserClient.querySmsCountList(request);
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

    @Override
    public Integer getSmsCountForExport(SmsCountRequest request) {
        return amUserClient.getSmsCountForExport(request);
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

                jo.put("value", departmentTreeRecord.getId().toString());
                jo.put("title", departmentTreeRecord.getName());
                jo.put("key", UUID.randomUUID());

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

    @Override
    public List<SmsCountCustomizeVO>  getuserIdAnddepartmentName(){
        return amUserClient.getuserIdAnddepartmentName();
    }

    @Override
    public List<UserVO> getUsersVo(List<String> list){
        ListRequest request = new ListRequest();
        request.setList(list);
        return amUserClient.selectUserListByMobile(request);
    }

    @Override
    public void insertBatchSmsCount(List<SmsCountCustomizeVO> list){
        ListRequest request = new ListRequest();
        request.setSmsCountCustomizeVOList(list);
        amUserClient.insertBatchSmsCount(request);
    }

    /**
     * 获取查询底层部门 id
     * @param list
     * @return
     */
    public HashSet getByCrmDepartmentList(String[] list) {
        List<OADepartmentCustomizeVO> departmentList = amUserClient.queryDepartmentInfo(null);
        HashSet hashSet = new HashSet();
        for(String topCd : list){
            hashSet.addAll(getTreeDepartmentList(departmentList, topCd));
        }

        return hashSet;
    }

    /**
     * 部门树形结构
     *
     * @param departmentTreeDBList
     * @param topParentDepartmentCd
     * @return
     */
    private ArrayList getTreeDepartmentList(List<OADepartmentCustomizeVO> departmentTreeDBList, String topParentDepartmentCd) {
        ArrayList arrayList = new ArrayList();

        if (departmentTreeDBList != null && departmentTreeDBList.size() > 0) {
            ArrayList  jo = null;
            arrayList.add(topParentDepartmentCd);
            for (OADepartmentCustomizeVO departmentTreeRecord : departmentTreeDBList) {
                jo = new ArrayList();

                String departmentCd = String.valueOf(departmentTreeRecord.getId());
                String parentDepartmentCd = String.valueOf(departmentTreeRecord.getParentid());
                if (topParentDepartmentCd.equals(parentDepartmentCd)) {
                    jo.add(departmentTreeRecord.getId().toString());

                    ArrayList array = getTreeDepartmentList(departmentTreeDBList, departmentCd);
                    arrayList.addAll(jo);
                    arrayList.addAll(array);
                }
            }
        }
        return arrayList;
    }

    /**
     *  修改and删除短信统计重复数据
     */
    @Override
    public void updateOrDelectRepeatData(){
        amUserClient.updateOrDelectRepeatData();
    }
}
