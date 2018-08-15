/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.service.SmsNoticeConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SmsNoticeConfigResponse;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
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
 * @version SmsNoticeController, v0.1 2018/5/8 9:30
 */
@RestController
@RequestMapping("/am-config/smsNoticeConfig")
public class SmsNoticeConfigController extends BaseConfigController{
    
    @Autowired
    private SmsNoticeConfigService smsNoticeConfigService;

    /**
     * 根据tplCode查询短信通知配置
     *
     * @param tplCode
     * @return
     */
    @RequestMapping("/findSmsNoticeByCode/{tplCode}")
    public SmsNoticeConfigResponse findSmsNoticeByCode(@PathVariable String tplCode) {
        logger.info("findSmsNoticeByCode start... tplCode is :{}", tplCode);
        SmsNoticeConfigResponse response = new SmsNoticeConfigResponse();
        SmsNoticeConfigVO smsNoticeConfigVO = null;
        SmsNoticeConfig smsNoticeConfig = smsNoticeConfigService.findSmsNoticeByCode(tplCode);
        if (smsNoticeConfig != null) {
            smsNoticeConfigVO = new SmsNoticeConfigVO();
            BeanUtils.copyProperties(smsNoticeConfig, smsNoticeConfigVO);
        }
        logger.info("smsNoticeConfigVO is :{}", smsNoticeConfigVO);
        response.setResult(smsNoticeConfigVO);
        return response;
    }

    /**
     * 查询通知配置列表
     * @author xiehuili
     * @return
     */
    @RequestMapping("/list")
    public SmsNoticeConfigResponse findSmsNoticeList() {
        logger.info("查询通知配置列表..." );
        SmsNoticeConfigResponse result=new SmsNoticeConfigResponse();
        //查询记录
        List<SmsNoticeConfig> recordList =smsNoticeConfigService.findSmsNoticeList();
        if(!CollectionUtils.isEmpty(recordList)){
            List<SmsNoticeConfigVO> configList = CommonUtils.convertBeanList(recordList, SmsNoticeConfigVO.class);
            result.setResultList(configList);
            result.setRtn(Response.SUCCESS);
            return result;
        }
        result.setRtn(Response.FAIL);
        result.setMessage(Response.FAIL_MSG);
        return result;
    }
    /**
     * 查询通知配置详情
     * @author xiehuili
     * @return
     */
    @RequestMapping("/info")
    public SmsNoticeConfigResponse smsNoticeConfigInfo(@RequestBody SmsNoticeConfigRequest request) {
        logger.info("查询通知配置详情..."+ JSONObject.toJSON(request));
        SmsNoticeConfigResponse result=new SmsNoticeConfigResponse();
        if (request.getId() != null) {
            SmsNoticeConfig record = this.smsNoticeConfigService.smsNoticeConfigInfo(request.getId(),request.getName());
            SmsNoticeConfigVO recordVo = new SmsNoticeConfigVO();
            if(null != record){
                BeanUtils.copyProperties(record, recordVo);
                result.setResult(recordVo);
                result.setRtn(Response.SUCCESS);
                return result;
            }
            result.setRtn(Response.FAIL);
            result.setMessage(Response.FAIL_MSG);
            return result;
        }
        return null;
    }

    /**
     * 添加通知配置
     * @author xiehuili
     * @return
     */
    @RequestMapping("/insert")
    public SmsNoticeConfigResponse insertSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest request) {
        logger.info("查询通知配置列表..."+JSONObject.toJSON(request) );
        SmsNoticeConfigResponse resp=new SmsNoticeConfigResponse();
//        request.setName(request.getConfigName());
        request.setStatus(1);
        request.setCreateTime(GetDate.getNowTime10());
        request.setUpdateTime(GetDate.getNowTime10());
        // 插入
        int cot = this.smsNoticeConfigService.insertSmsNoticeConfig(request);
        if(cot > 0 ) {
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage(Response.FAIL_MSG);
        return resp;
    }
    /**
     * 修改通知配置
     * @author xiehuili
     * @return
     */
    @RequestMapping("/update")
    public SmsNoticeConfigResponse updateSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest request) {
        logger.info("查询通知配置列表..."+JSONObject.toJSON(request) );
        SmsNoticeConfigResponse resp=new SmsNoticeConfigResponse();
        request.setUpdateTime(GetDate.getNowTime10());
        // 修改
        int cot = this.smsNoticeConfigService.updateSmsNoticeConfig(request);
        if(cot > 0 ){
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage(Response.FAIL_MSG);
        return resp;
    }

    /**
     * 关闭通知配置详情
     * @author xiehuili
     * @return
     */
    @RequestMapping("/close")
    public SmsNoticeConfigResponse closeSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest request) {
        logger.info("关闭通知配置列表..." +JSONObject.toJSON(request) );
        SmsNoticeConfigResponse resp=new SmsNoticeConfigResponse();
        request.setStatus(0);
        // 插入
        int cot = this.smsNoticeConfigService.updateSmsNoticeConfig(request);
        if(cot > 0 ) {
            //查询记录
            List<SmsNoticeConfig> recordList =smsNoticeConfigService.findSmsNoticeList();
            if(!CollectionUtils.isEmpty(recordList)){
                List<SmsNoticeConfigVO> configList = CommonUtils.convertBeanList(recordList, SmsNoticeConfigVO.class);
                resp.setResultList(configList);
                return resp;
            }
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage(Response.FAIL_MSG);
        return resp;
    }

    /**
     * 开启通知配置详情
     * @author xiehuili
     * @return
     */
    @RequestMapping("/open")
    public SmsNoticeConfigResponse openSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest request) {
        logger.info("开启通知配置列表..."+JSONObject.toJSON(request));
        SmsNoticeConfigResponse result=new SmsNoticeConfigResponse();
        request.setStatus(1);
        // 开启
        int cot = this.smsNoticeConfigService.updateSmsNoticeConfig(request);
        if(cot > 0 ){
            //查询记录
            List<SmsNoticeConfig> recordList =smsNoticeConfigService.findSmsNoticeList();
            if(!CollectionUtils.isEmpty(recordList)){
                List<SmsNoticeConfigVO> configList = CommonUtils.convertBeanList(recordList, SmsNoticeConfigVO.class);
                result.setResultList(configList);
                return result;
            }
            return result;
        }
        result.setRtn(Response.FAIL);
        result.setMessage(Response.FAIL_MSG);
        return result;
    }

    /**
     * 校验通知配置详情
     * @author xiehuili
     * @return
     */
    @RequestMapping("/onlyName/{name}")
    public Integer onlyName(@PathVariable String name) {
        logger.info("查询通知配置列表..." +JSONObject.toJSON(name) );
        SmsNoticeConfigResponse result=new SmsNoticeConfigResponse();
        //查询记录
        return smsNoticeConfigService.onlyName(name);
    }


}
