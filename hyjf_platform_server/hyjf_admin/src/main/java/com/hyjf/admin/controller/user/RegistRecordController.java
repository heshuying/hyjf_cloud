/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.user.RegistRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version RegistRecordController, v0.1 2018/6/23 15:16
 */

@Api(value = "注册记录")
@RestController
@RequestMapping("/registRecord")
public class RegistRecordController {
    @Autowired
    public RegistRecordService registRecordService;

    //会员管理列表查询
    @ApiOperation(value = "注册记录", notes = "注册记录列表查询")
    @RequestMapping(value = "/registRecordList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject selectRegistRecordList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = null;
        RegistRcordRequest registerRcordeRequest =setRequese(map);
        List<RegistRecordVO> listRgistRecord =registRecordService.findRegistRecordList(registerRcordeRequest);
        String status="error";
        if(null!=listRgistRecord&&listRgistRecord.size()>0){
            jsonObject.put("record",listRgistRecord);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }

    private RegistRcordRequest setRequese(Map<String,Object> mapParam){
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        if(null!=mapParam&&mapParam.size()>0){
            if(mapParam.containsKey("userName")){
                registerRcordeRequest.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("mobile")){
                registerRcordeRequest.setMobile(mapParam.get("mobile").toString());
            }
            if(mapParam.containsKey("recommendName")){
                registerRcordeRequest.setRecommendName(mapParam.get("recommendName").toString());
            }
            if(mapParam.containsKey("registPlat")){
                registerRcordeRequest.setRegistPlat(mapParam.get("registPlat").toString());
            }
            if(mapParam.containsKey("regTimeStart")){
                registerRcordeRequest.setRegTimeStart(mapParam.get("regTimeStart").toString());
            }
            if(mapParam.containsKey("regTimeEnd")){
                registerRcordeRequest.setRegTimeEnd(mapParam.get("regTimeEnd").toString());
            }
            if (mapParam.containsKey("limitStart")&& StringUtils.isNotBlank(mapParam.get("limitStart").toString())) {
                registerRcordeRequest.setLimitStart(Integer.parseInt(mapParam.get("limitStart").toString()));
            }
            if (mapParam.containsKey("limitEnd")&& StringUtils.isNotBlank(mapParam.get("limitEnd").toString())) {
                registerRcordeRequest.setLimitEnd(Integer.parseInt(mapParam.get("limitEnd").toString()));
            }
        }
        return registerRcordeRequest;
    }
}
