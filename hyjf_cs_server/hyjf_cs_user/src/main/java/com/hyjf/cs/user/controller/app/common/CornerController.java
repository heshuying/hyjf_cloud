/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.common;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.service.common.CornerService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingqing
 * @version CornerController, v0.1 2018/7/18 11:21
 */
@Api(description = "汇天利资金中心接口")
@Controller
@RequestMapping("/app/hyjf-app/app/common")
public class CornerController {

    @Autowired
    CornerService cornerService;
    /**
     * 获取版本号
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/getVersion")
    public JSONObject getVersion(@RequestHeader(value = "key", required = true) String key, HttpServletRequest request, HttpServletResponse response) {
        JSONObject map = new JSONObject();
        map.put("request", "/hyjf-app/app/common/getVersion");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 平台
        String clientStr = request.getParameter("platform");
        //版本号
        String versionStr = request.getParameter("version");
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            map.put("status", "1");
            map.put("statusDesc", "请求参数非法");
            return map;
        }
        map.put("status", "0");
        map.put("statusDesc", "请求成功");
        int type = 0;
        if(StringUtils.isNotEmpty(clientStr)){
            if(clientStr.equals(CustomConstants.CLIENT_ANDROID)){
                type = 1;
            }else if(clientStr.equals(CustomConstants.CLIENT_IOS)){
                type = 2;
            }
        }

        //版本更新 逻辑修改
        if(StringUtils.isEmpty(versionStr) ){
            //没有版本号
            map.put("newVersion",versionStr);
            map.put("action","2");
            map.put("url", "");
            map.put("content", "");
            return map;
        }

        //版本号为  1.0.0.16  16为渠道号 不记版本// 取前三位版本号
        String vers[] = versionStr.split("\\.");
        if(vers != null && vers.length > 0){
            versionStr = vers[0] +"." + vers[1] +"." + vers[2];
        }
        VersionVO version = cornerService.getNewVersionByType(type);

        VersionVO forceVersion=cornerService.getVersionByType(type,0,null);

        VersionVO currentVersion=cornerService.getVersionByType(type,null,versionStr);
        if(currentVersion == null){
            map.put("status","1");
            map.put("statusDesc","当前版本号未在hyjf_version中配置");
            return map;
        }

        if(version == null){
            map.put("status","1");
            map.put("statusDesc","获取最新版本号异常");
            return map;
        }
        if(forceVersion == null){
            map.put("status","1");
            map.put("statusDesc","获取强制更新版本号异常");
            return map;
        }

        if(versionStr.equals(forceVersion.getVersion())){
            // 当前版本号等于强制更新版本号操作
            if(versionStr.equals(version.getVersion())){
                //当前版本号等于最新版本号
                map.put("newVersion",versionStr);
                map.put("action","2");
                map.put("url", "");
                map.put("content", "");
            }else{
                //当前版本号不等于最新版本号
                map.put("newVersion",version.getVersion());
                map.put("action",String.valueOf(version.getIsUpdate()));
                map.put("url", version.getUrl());
                map.put("content", version.getContent());
            }
        } else {
            if(forceVersion.getId()<currentVersion.getId()&&currentVersion.getId()<version.getId()){
                //强制更新和最新版本中间版本
                map.put("newVersion",version.getVersion());
                map.put("action",String.valueOf(version.getIsUpdate()));
                map.put("url", version.getUrl());
                map.put("content", version.getContent());
            } else {
                if(versionStr.equals(version.getVersion())){
                    map.put("newVersion",versionStr);
                    map.put("action","2");
                    map.put("url", "");
                    map.put("content", "");
                }else{
                    //强制更新和最新版本中间版本
                    map.put("newVersion",version.getVersion());
                    map.put("action",String.valueOf(forceVersion.getIsUpdate()));
                    map.put("url", version.getUrl());
                    map.put("content", version.getContent());
                }
            }
        }
        return map;
    }
}
