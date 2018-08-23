package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.service.VersionConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.am.config.dao.model.auto.Version;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/16.
 */
@RestController
@RequestMapping("/am-config/config/versionconfig")
public class VersionConfigController extends BaseConfigController{

    @Autowired
    private VersionConfigService versionConfigService;


    /**
     * 分页查询配置中心版本配置列表
     * @return
     */
    @RequestMapping("/list")
    public AdminVersionResponse versionConfigInitByPage(AdminVersionRequest adminRequest) {
        logger.info("版本配置列表..." + JSONObject.toJSON(adminRequest));
        AdminVersionResponse  response =new AdminVersionResponse();
        //查询版本配置列表条数
        int recordTotal = this.versionConfigService.getVersionConfigCount(adminRequest);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(), recordTotal);
            //查询记录
            List<Version> recordList =versionConfigService.getVersionConfigListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<VersionVO> hicv = CommonUtils.convertBeanList(recordList, VersionVO.class);
                response.setResultList(hicv);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return null;
    }

    /**
     * 查询详情页面
     * @return
     */
    @RequestMapping("/info")
    public AdminVersionResponse versionConfigInfoById(@RequestBody  AdminVersionRequest adminRequest) {
        logger.info("版本配置详情..." + JSONObject.toJSON(adminRequest));
        AdminVersionResponse  response =new AdminVersionResponse();
        VersionVO record = this.versionConfigService.getVersionConfigInfoById(adminRequest.getId());
        if(null != record){
            response.setResult(record);
            response.setRtn(Response.SUCCESS);
            return response;
        }
        response.setRtn(Response.FAIL);
        return response;
    }

    /**
     * 添加版本配置
     * @param req
     */
    @RequestMapping("/insert")
    public AdminVersionResponse insertVersionConfig(@RequestBody AdminVersionRequest req) {
        AdminVersionResponse resp = new AdminVersionResponse();
        int result =this.versionConfigService.insertVersionConfig(req);
        if(result > 0 ){
            //分页查询
            resp = versionConfigInitByPage(req);
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        return resp;
    }

    /**
     * 修改版本配置
     * @param req
     */
    @RequestMapping("/update")
    public AdminVersionResponse updateVersionConfig(@RequestBody AdminVersionRequest req) {
        AdminVersionResponse resp = new AdminVersionResponse();
        int result =this.versionConfigService.updateVersionConfig(req);
        if(result > 0 ){
            //分页查询
            resp = versionConfigInitByPage(req);
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        return resp;
    }

    /**
     * 删除版本配置
     * @param id
     */
    @RequestMapping("/delete")
    public AdminVersionResponse deleteVersionConfig(@RequestBody Integer id) {
        AdminVersionResponse resp = new AdminVersionResponse();
        try{
            this.versionConfigService.deleteVersionConfig(id);
            resp.setRtn(Response.SUCCESS);
        }catch (Exception e){
            resp.setRtn(Response.FAIL);
        }
        return  resp;
    }

    /**
     * 校验版本配置当前系统版本号是否唯一
     * @param map
     */
    @RequestMapping("/validationFeild")
    public VersionVO validationFeild(@RequestBody Map map) {
        return  this.versionConfigService.validationFeild(map);
    }



}
