/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.user.RegistRecordVO;
import com.hyjf.common.cache.CacheUtil;
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
@RequestMapping("/hyjf-admin/registRecord")
public class RegistRecordController {
    @Autowired
    private RegistRecordService registRecordService;

    @ApiOperation(value = "注册记录", notes = "注册记录页面初始化")
//    @RequestMapping(value = "/usersInit", method = {RequestMethod.GET, RequestMethod.POST})
    @PostMapping(value = "/usersInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = new JSONObject();
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        jsonObject.put("registPlat", registPlat);
        return jsonObject;
    }

    //会员管理列表查询
    @ApiOperation(value = "注册记录", notes = "注册记录列表查询")
    /*@RequestMapping(value = "/registRecordList", method = {RequestMethod.GET, RequestMethod.POST})*/
    @PostMapping(value = "/registRecordList")
    @ResponseBody
    public JSONObject selectRegistRecordList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        RegistRcordRequest registerRcordeRequest = setRequese(map);
        List<RegistRecordVO> listRgistRecord = registRecordService.findRegistRecordList(registerRcordeRequest);
        String status = Response.FAIL;
        if (null != listRgistRecord && listRgistRecord.size() > 0) {
            jsonObject.put("record", listRgistRecord);
            status = Response.SUCCESS;
        }
        jsonObject.put("status", status);
        return jsonObject;
    }

    private RegistRcordRequest setRequese(Map<String, Object> mapParam) {
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        if (null != mapParam && mapParam.size() > 0) {
            if (mapParam.containsKey("userName")) {
                registerRcordeRequest.setUserName(mapParam.get("userName").toString());
            }
            if (mapParam.containsKey("mobile")) {
                registerRcordeRequest.setMobile(mapParam.get("mobile").toString());
            }
            if (mapParam.containsKey("recommendName")) {
                registerRcordeRequest.setRecommendName(mapParam.get("recommendName").toString());
            }
            if (mapParam.containsKey("registPlat")) {
                registerRcordeRequest.setRegistPlat(mapParam.get("registPlat").toString());
            }
            if (mapParam.containsKey("regTimeStart")) {
                registerRcordeRequest.setRegTimeStart(mapParam.get("regTimeStart").toString());
            }
            if (mapParam.containsKey("regTimeEnd")) {
                registerRcordeRequest.setRegTimeEnd(mapParam.get("regTimeEnd").toString());
            }
            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                registerRcordeRequest.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return registerRcordeRequest;
    }
}
