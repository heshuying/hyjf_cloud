/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.common;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.common.CornerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version CornerController, v0.1 2018/7/18 11:21
 */
@Api(tags = "app端-基础信息设置")
@RestController
@RequestMapping("/hyjf-app/app/common")
public class CornerController extends BaseController {

    @Autowired
    CornerService cornerService;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    SystemConfig systemConfig;
    /**
     * 获取版本号
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "获取版本号",notes = "获取版本号")
    @PostMapping(value = "/getVersion")
    public JSONObject getVersion(@RequestHeader(value = "key") String key, HttpServletRequest request) {
        JSONObject map = new JSONObject();
        map.put("request", "/hyjf-app/app/common/getVersion");
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
                map.put("url", systemConfig.getAppFrontHost()+version.getUrl());
                map.put("content", version.getContent());
            }
        } else {
            if(forceVersion.getId()<currentVersion.getId()&&currentVersion.getId()<version.getId()){
                //强制更新和最新版本中间版本
                map.put("newVersion",version.getVersion());
                map.put("action",String.valueOf(version.getIsUpdate()));
                map.put("url", systemConfig.getAppFrontHost()+version.getUrl());
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
                    map.put("url", systemConfig.getAppFrontHost()+version.getUrl());
                    map.put("content", version.getContent());
                }
            }
        }
        return map;
    }

    /**
     * 接收设备唯一标识
     * （数据库没有 存储，有 更新）
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "接收设备唯一标识",notes = "接收设备唯一标识")
    @PostMapping(value = "/mobileCode")
    public JSONObject mobileCode(@RequestHeader(value = "userId") Integer userId,
                                 @RequestHeader(value = "sign") String sign,
                                 @RequestHeader(value = "platform") String platform,
                                 @RequestHeader(value = "version") String version,
                                 @RequestParam(value = "mobileCode") String alias) {
        logger.debug("userId is : {}, alias is: {}", userId, alias);

        JSONObject map = new JSONObject();
        map.put("request", "/hyjf-app/app/common/mobileCode");
        map.put("status","0");
        map.put("statusDesc","请求成功");

        //版本号为  1.0.0.16  16为渠道号 不记版本
        if(StringUtils.isNotEmpty(version) ){
            String vers[] = version.split("\\.");
            if(vers != null && vers.length > 0){
                version = vers[3] ;
            }
        }
        try {
            UserAliasVO userAliasVO = amUserClient.findAliasesByUserId(userId);
            if(userAliasVO != null){
                logger.debug("userAliasVO is : {}", JSONObject.toJSONString(userAliasVO));
                if(!userAliasVO.getAlias().equals(alias)){
                    userAliasVO.setAlias(alias);
                    userAliasVO.setClient(platform);
                    userAliasVO.setPackageCode(version);
                    userAliasVO.setSign(sign);
                    amUserClient.updateAliases(userAliasVO);
                }
            }else{
                userAliasVO = new UserAliasVO();
                userAliasVO.setAlias(alias);
                userAliasVO.setUserId(userId);
                userAliasVO.setSign(sign);
                userAliasVO.setClient(platform);
                userAliasVO.setPackageCode(version);
                amUserClient.insertMobileCode(userAliasVO);
            }
        } catch (Exception e) {
            map.put("status","1");
            map.put("statusDesc","更新设备唯一标识异常");
        }
        return map;
    }

    /**
     * 设置角标
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "设置角标",notes = "设置角标")
    @PostMapping(value = "/setCorner")
    public AppResult setCorner(HttpServletRequest request) {
        AppResult result = new AppResult();
        result.setStatusInfo("0","设置成功");
        return result;
    }

    /**
     * 获取最新版本号下载地址
     * @return
     */
    @ApiOperation("获取最新版本号下载地址")
    @ResponseBody
    @PostMapping(value = "/getNewVersionURL")
    public JSONObject getNewVersionURL() {
        JSONObject map = new JSONObject();
        map.put("status", "000");
        map.put("request", "/hyjf-app/app/common/getVersion");
        map.put("statusDesc", "请求成功");
        VersionVO version = cornerService.getNewVersionByType(1);
        map.put("url", version.getUrl()+"?ignoreSign=true");
        return map;
    }

    /**
     *
     * ios下载页
     * @return
     */
    @ApiOperation("ios下载页")
    @GetMapping(value = "/iosinit")
    public AppResult initIOS() {
        AppResult result = new AppResult();
        String downloadUrl = "itms-apps://itunes.apple.com/cn/app/id1044961717?mt=8";
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("downloadUrl",downloadUrl);
        result.setData(resultMap);
        result.setStatus("000");
        return result;
    }
}
