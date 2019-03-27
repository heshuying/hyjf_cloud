/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.protocol;

import com.hyjf.am.response.MapResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.response.admin.ProtocolLogResponse;
import com.hyjf.am.response.trade.ProtocolTemplateResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.ProtocolTemplate;
import com.hyjf.am.trade.service.front.config.ProtocolTemplateService;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.admin.ProtocolVersionVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version ProtocolTemplateController.java, v0.1 2018年7月26日 下午5:07:37
 */
@Api(value = "协议模板")
@RestController
@RequestMapping("/am-trade/protocol")
public class ProtocolTemplateController extends BaseController {

    @Autowired
    private ProtocolTemplateService protocolTemplateService;

    @GetMapping("/getProtocolTemplateVOByDisplayName/{displayName}")
    public ProtocolTemplateResponse getProtocolTemplateVOByDisplayName(@PathVariable String displayName) {
        ProtocolTemplateResponse response = new ProtocolTemplateResponse();
        List<ProtocolTemplateVO> list = this.protocolTemplateService.getProtocolTemplateVOByDisplayName(displayName);
        if (CollectionUtils.isNotEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 分页查询统计全部个数
     *
     * @return
     */
    @RequestMapping("/countRecord")
    public AdminProtocolResponse countRecord(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer count = this.protocolTemplateService.countRecord(request);
        response.setCount(count);
        return response;
    }

    /**
     * 统计全部个数
     *
     * @return
     */
    @RequestMapping("/startuseexistprotocol")
    public AdminProtocolResponse startUseExistProtocol(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer count = this.protocolTemplateService.startUseExistProtocol(request);
        if(count == 0){
            response.setRtn(Response.ERROR);
            response.setMessage(Response.ERROR_MSG);
        }
        return response;
    }

    /**
     * 获取全部列表
     *
     * @return
     */
    @RequestMapping("/getRecordList")
    public AdminProtocolResponse getRecordList(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        List<ProtocolTemplateCommonVO> list = this.protocolTemplateService.getRecordList(request);
        response.setResultList(list);
        return response;
    }

    /**
     * 根据协议id查询协议和版本
     *
     * @return
     */
    @RequestMapping("/getProtocolTemplateById")
    public AdminProtocolResponse getProtocolTemplateById(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer ids = Integer.valueOf(request.getIds());
        ProtocolTemplateCommonVO vo = this.protocolTemplateService.getProtocolTemplateById(ids);
        response.setResult(vo);
        return response;
    }

    /**
     * 查询协议模板数量
     *
     * @return
     */
    @RequestMapping("/getProtocolTemplateNum")
    public AdminProtocolResponse getProtocolTemplateNum(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer count = this.protocolTemplateService.getProtocolTemplateNum(request);
        response.setCount(count);
        return response;
    }

    /**
     * 查询协议模板数量
     *
     * @return
     */
    @RequestMapping("/getProtocolTemplateByProtocolName")
    public AdminProtocolResponse getProtocolTemplateByProtocolName(@RequestBody AdminProtocolRequest request) {

        AdminProtocolResponse response = new AdminProtocolResponse();
        ProtocolTemplateCommonVO vo = this.protocolTemplateService.getProtocolTemplateByProtocolName(request);
        response.setResult(vo);
        return response;
    }

    /**
     * 查询协议模板数量
     *
     * @return
     */
    @RequestMapping("/insert")
    public AdminProtocolResponse insert(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer count = this.protocolTemplateService.insert(request);
        response.setCount(count);
        return response;
    }

    /**
     * 获得最新协议模板数据
     *
     * @return
     */
    @RequestMapping("/getnewinfo")
    public Response getnewinfo() {
        Response<ProtocolTemplateVO> response = new Response<>();
        List<ProtocolTemplateVO> list = protocolTemplateService.getnewinfo();
        response.setResultList(list);
        return response;
    }

    /**
     * 修改 协议模板
     *
     * @return
     */
    @RequestMapping("/updateProtocolTemplate")
    public AdminProtocolResponse updateProtocolTemplate(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer count = this.protocolTemplateService.updateProtocolTemplate(request);
        response.setCount(count);
        return response;
    }

    /**
     * 修改 之前的版本的启用状态改成不启用
     *
     * @return
     */
    @RequestMapping("/updateDisplayFlag")
    public AdminProtocolResponse updateDisplayFlag(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        Integer count = this.protocolTemplateService.updateDisplayFlag(request);
        response.setCount(count);
        return response;
    }

    /**
     * 删除协议模板
     *
     * @return
     */
    @RequestMapping("/deleteProtocolTemplate")
    public AdminProtocolResponse deleteProtocolTemplate(@RequestBody AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        ProtocolTemplateCommonVO vo = this.protocolTemplateService.deleteProtocolTemplate(request);
        response.setResult(vo);
        return response;
    }

    /**
     * 查询协议日志模板数量
     *
     * @return
     */
    @RequestMapping("/countRecordLog")
    public ProtocolLogResponse countRecordLog(@RequestBody ProtocolLogRequest request) {

        ProtocolLogResponse response = new ProtocolLogResponse();
        Integer count = this.protocolTemplateService.countRecordLog(request);
        response.setCount(count);
        return response;
    }

    /**
     * 查询全部协议日志模板
     *
     * @return
     */
    @RequestMapping("/getProtocolLogVOAll")
    public ProtocolLogResponse getProtocolTemplateByProtocolName(@RequestBody ProtocolLogRequest request) {

        ProtocolLogResponse response = new ProtocolLogResponse();
        List<ProtocolLogVO>  list = this.protocolTemplateService.getProtocolLogVOAll(request);
        response.setResultList(list);
        return response;
    }

    /**
     * 根据id查询
     *
     * @return
     */
    @RequestMapping("/byIdProtocolVersion/{id}")
    public Response byIdProtocolVersion(@PathVariable Integer id) {

        Response response = new Response();
        ProtocolVersionVO vo = this.protocolTemplateService.byIdProtocolVersion(id);
        response.setResult(vo);
        return response;
    }

    /**
     * 根据id查询
     *
     * @return
     */
    @RequestMapping("/byIdTemplateBy/{protocolId}")
    public Response byIdTemplateBy(@PathVariable String protocolId) {

        Response response = new Response();
        ProtocolTemplateVO vo = this.protocolTemplateService.byIdTemplateBy(protocolId);
        response.setResult(vo);
        return response;
    }

    /**
     *
     *
     * @return
     */
    @RequestMapping("/getProtocolVersionSize")
    public AdminProtocolResponse byIdTemplateBy(@RequestBody AdminProtocolRequest request) {

        AdminProtocolResponse response = new AdminProtocolResponse();
        int countSize = this.protocolTemplateService.updateProtocolVersionSize(request.getProtocolTemplateVO());
        response.setCount(countSize);
        return response;
    }

    /**
     *校验字段是否为唯一
     *
     * @return
     */
    @RequestMapping("/validatorfieldcheck")
    public MapResponse validatorFieldCheck(@RequestBody AdminProtocolRequest request) {

        MapResponse mapResponse = new MapResponse();
        //获取校验参数
        String protocolName = request.getProtocolName();//协议模板名称
        String protocolType = request.getProtocolType();//协议类别
        String versionNumber = request.getVersionNumber();//版本号
        String displayName = request.getDisplayName();//前台展示名称
        String protocolUrl = request.getProtocolUrl();//协议上传路径
        String oldDisplayName = request.getOldDisplayName();//原前台展示名称
        String flag = request.getFlag();//原前台展示名称
        Map<String,Object> map = protocolTemplateService.validatorFieldCheck(protocolName,versionNumber,displayName,protocolUrl,protocolType,oldDisplayName,flag);
        mapResponse.setResultMap(map);
        return mapResponse;
    }

    /**
     * 获取所有在帮助中心显示的模板列表
     * add by nxl 20190313
     * PC 1.1.2
     * @return
     */
    @RequestMapping("/getAllShowProtocolTemp")
    public ProtocolTemplateResponse getAllShowProtocolTemp(){
        ProtocolTemplateResponse response = new ProtocolTemplateResponse();
        response.setRtn(Response.FAIL);
        List<ProtocolTemplate> protocolTemplateList = protocolTemplateService.selectAllShowProtocolTemplate();
        if(CollectionUtils.isNotEmpty(protocolTemplateList)){
            List<ProtocolTemplateVO> protocolTemplateVOList = CommonUtils.convertBeanList(protocolTemplateList, ProtocolTemplateVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(protocolTemplateVOList);
        }
        return response;
    }
}
