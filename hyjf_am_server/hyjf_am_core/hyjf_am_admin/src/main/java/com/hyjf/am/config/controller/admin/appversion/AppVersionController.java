package com.hyjf.am.config.controller.admin.appversion;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.dao.model.customize.VersionConfigBean;
import com.hyjf.am.config.service.AppVersionService;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version AppVersionController, v0.1 2018/7/14 11:45
 */
@RestController
@RequestMapping("/am-config/appversion")
public class AppVersionController {
    @Autowired
    AppVersionService appVersionService;

    /**
     * 查询列表
     * @return
     */
    @PostMapping("/getRecordList")
    public VersionConfigBeanResponse findAppBannerData(@RequestBody VersionConfigBeanRequest form) {
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        VersionConfigBean version = new VersionConfigBean();
        if(StringUtils.isNotEmpty(form.getNameSrh())){
            version.setNameSrh(form.getNameSrh());
        }
        if(StringUtils.isNotEmpty(form.getVersionSrh())){
            version.setVersionSrh(form.getVersionSrh());
        }
        Integer count = appVersionService.countRecord(version);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(form.getCurrPage(), count,form.getPageSize());
            List<Version> recordList  = appVersionService.getRecordList(version, paginator.getOffset(), paginator.getLimit());
            List<VersionVO> versionVOS = CommonUtils.convertBeanList(recordList, VersionVO.class);
            response.setResultList(versionVOS);
            response.setCount(count);
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            response.setClient(client);
        }
        return response;
    }


    /**
     * 查询详情
     *
     * @return
     */
    @PostMapping("/infoAction")
    public VersionConfigBeanResponse moveToInfoAction(@RequestBody VersionConfigBeanRequest form) {
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        String ids = form.getIds();
        VersionConfigBean bean = new VersionConfigBean();
        if (Validator.isNotNull(ids)) {
            Integer id = Integer.valueOf(ids);
            bean.setId(id);
            // 根据主键检索数据
            Version record = appVersionService.getRecord(id);
            VersionVO versionVO = CommonUtils.convertBean(record, VersionVO.class);
            response.setResult(versionVO);
        }
        Map<String, String> versionName = CacheUtil.getParamNameMap("VERSION_NAME");
        Map<String, String> isUpdate = CacheUtil.getParamNameMap("IS_UPDATE");
        response.setIsUpdate(isUpdate);
        response.setIsUpdate(versionName);
        return response;
    }


    /**
     * 添加数据
     *
     * @return
     */
    @PostMapping("/insertAction")
    public VersionConfigBeanResponse insertAction(@RequestBody VersionConfigBeanRequest form) {
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        VersionConfigBean versionConfigBean = CommonUtils.convertBean(form, VersionConfigBean.class);
        appVersionService.insertRecord(versionConfigBean);
        return response;

    }

    /**
     * 修改数据
     *
     * @return
     */
    @PostMapping("/updateAction")
    public VersionConfigBeanResponse updateAction(@RequestBody VersionConfigBeanRequest form) {
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        VersionConfigBean versionConfigBean = CommonUtils.convertBean(form, VersionConfigBean.class);
        appVersionService.updateRecord(versionConfigBean);
        return response;

    }


    /**
     * 删除数据
     *
     * @return
     */
    @PostMapping("/deleteAction")
    public VersionConfigBeanResponse deleteRecordAction(@RequestBody VersionConfigBeanRequest form) {
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        List<Integer> recordList = JSONArray.parseArray(form.getIds(), Integer.class);
        appVersionService.deleteRecord(recordList);
        return response;

    }

    @RequestMapping("/getNewVersionByType/{type}")
    public VersionConfigBeanResponse getNewVersionByType(@PathVariable Integer type){
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        Version version = appVersionService.getNewVersionByType(type);
        if (null!= version){
            VersionVO versionVO = CommonUtils.convertBean(version, VersionVO.class);
            response.setResult(versionVO);
        }
        return response;
    }


    @RequestMapping("/getUpdateversion/{type}/{isupdate}/{versionStr}")
    public VersionConfigBeanResponse getUpdateversion(@PathVariable Integer type,@PathVariable Integer isupdate,@PathVariable String versionStr ){
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        Version version = appVersionService.getUpdateversion(type,isupdate,versionStr);
        if (null!= version){
            VersionVO versionVO = CommonUtils.convertBean(version, VersionVO.class);
            response.setResult(versionVO);
        }
        return response;
    }

}
