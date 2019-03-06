package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.service.VersionConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AdminVersionResponse versionConfigInitByPage(@RequestBody  AdminVersionRequest adminRequest) {
        logger.info("版本配置列表..." + JSONObject.toJSON(adminRequest));
        AdminVersionResponse  response =new AdminVersionResponse();
        //查询版本配置列表条数
        int recordTotal = this.versionConfigService.getVersionConfigCount(adminRequest);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), recordTotal,adminRequest.getPageSize()== 0?10:adminRequest.getPageSize());
            //查询记录
            List<Version> recordList =versionConfigService.getVersionConfigListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<VersionVO> hicv = CommonUtils.convertBeanList(recordList, VersionVO.class);
                response.setResultList(hicv);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询的数据为空");
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
        response.setMessage("查询的数据为空");
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
        resp.setMessage("添加失败！");
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
        resp.setMessage("修改失败！");
        return resp;
    }

    /**
     * 删除版本配置
     * @param id
     */
    @RequestMapping("/delete")
    public AdminVersionResponse deleteVersionConfig(@RequestBody List<Integer> id) {
        AdminVersionResponse resp = new AdminVersionResponse();
        try{
            this.versionConfigService.deleteVersionConfig(id);
            resp.setRtn(Response.SUCCESS);
        }catch (Exception e){
           logger.error(e.getMessage());
        }
        return  resp;
    }

    /**
     * 校验版本配置当前系统版本号是否唯一
     * @param map
     */
    @RequestMapping("/validationFeild")
    public AdminVersionResponse validationFeild(@RequestBody Map map) {
        AdminVersionResponse response = new AdminVersionResponse();
        VersionVO vo= this.versionConfigService.validationFeild(map);
        if(vo != null){
            response.setResult(vo);
            return response;
        }
        return  null;
    }


    /**
     * 获取最新版本信息
     * @author zhangyk
     * @date 2018/9/5 11:57
     */
    @GetMapping("/getLastestVersion")
    public VersionConfigBeanResponse  getLastestVersion(){
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        Version version = versionConfigService.getLastestVersion();
        if (version != null){
            response.setResult(CommonUtils.convertBean(version, com.hyjf.am.vo.config.VersionVO.class));
        }
        return response;
    }



}
