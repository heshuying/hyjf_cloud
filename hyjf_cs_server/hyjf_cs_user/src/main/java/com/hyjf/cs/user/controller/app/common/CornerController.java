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
import com.hyjf.cs.user.service.common.CornerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingqing
 * @version CornerController, v0.1 2018/7/18 11:21
 */
@Api(tags = "app端-汇天利资金中心接口")
@RestController
@RequestMapping("/hyjf-app/app/common")
public class CornerController extends BaseController {

    @Autowired
    CornerService cornerService;

    @Autowired
    AmUserClient amUserClient;
    /**
     * 获取版本号
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "获取版本号",notes = "获取版本号")
    @PostMapping(value = "/getVersion")
    public JSONObject getVersion(@RequestHeader(value = "key") String key, HttpServletRequest request, HttpServletResponse response) {
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

    /**
     * 接收设备唯一标识
     * （数据库没有 存储，有 更新）
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "接收设备唯一标识",notes = "接收设备唯一标识")
    @PostMapping(value = "/mobileCode")
    public JSONObject mobileCode(@RequestHeader(value = "key") String key,@RequestHeader(value = "userId") Integer userId,HttpServletRequest request, HttpServletResponse response) {
        JSONObject map = new JSONObject();
        map.put("request", "/hyjf-app/app/common/mobileCode");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 平台
        String clientStr = request.getParameter("platform");
        //设备标识码
        String mobileCodeStr = request.getParameter("mobileCode");
        //版本号
        String versionStr = request.getParameter("version");
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            map.put("status", "1");
            map.put("statusDesc", "请求参数非法");
            return map;
        }
        if(StringUtils.isEmpty(mobileCodeStr)){
            map.put("status","1");
            map.put("statusDesc","请求参数非法");
            return map;
        }
        //用户id
        map.put("status","0");
        map.put("statusDesc","请求成功");

        //版本号为  1.0.0.16  16为渠道号 不记版本
        if(StringUtils.isNotEmpty(versionStr) ){
            String vers[] = versionStr.split("\\.");
            if(vers != null && vers.length > 0){
                versionStr = vers[3] ;
            }
        }
        try {
            UserAliasVO mobileCode = amUserClient.findAliasesByUserId(userId);
            if(mobileCode != null){
                if(!mobileCode.getAlias().equals(mobileCodeStr)){
                    mobileCode.setAlias(mobileCodeStr);
                    mobileCode.setClient(clientStr);
                    mobileCode.setPackageCode(versionStr);
                    mobileCode.setSign(sign);
                    amUserClient.updateAliases(mobileCode);
                }
            }else{
                mobileCode = new UserAliasVO();
                mobileCode.setAlias(mobileCodeStr);
                mobileCode.setUserId(userId);
                mobileCode.setSign(sign);
                mobileCode.setClient(clientStr);
                mobileCode.setPackageCode(versionStr);
                amUserClient.insertMobileCode(mobileCode);
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
/*
        Map<String,String> map = new HashMap<>();
        map.put("request", "/hyjf-app/app/common/setCorner");
        // 唯一标识
        String sign = request.getParameter("sign");
        //设备标识码
        String mobileCodeStr = request.getParameter("mobileCode");
        //角标
        String cornerStr = request.getParameter("corner");
        logger.info("key=[{}],sign=[{}],mobileCode=[{}],corner=[{}]",key,sign,mobileCodeStr,cornerStr);
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            result.setStatusInfo(MsgEnum.ERR_OBJECT_VALUE,"key");
            return result;
        }
        if(StringUtils.isEmpty(mobileCodeStr)){
            result.setStatusInfo(MsgEnum.ERR_OBJECT_VALUE,"mobileCode");
            return result;
        }
        if(StringUtils.isEmpty(cornerStr)){
            result.setStatusInfo(MsgEnum.ERR_OBJECT_VALUE,"corner");
            return result;
        }

        // 检查参数
        try {
            int corner = Integer.parseInt(cornerStr);
            UserCornerVO cornerInfo = cornerService.getUserCornerBySign(sign);
            if(cornerInfo != null){
                cornerInfo.setCorner(corner);
                cornerService.updateUserCorner(cornerInfo);
            }else{
                cornerInfo = new UserCornerVO();
                cornerInfo.setCorner(corner);
                cornerInfo.setSign(sign);
                cornerService.insertUserCorner(cornerInfo);
            }
            result.setStatusInfo(BaseResult.SUCCESS,"设置成功");
        } catch (Exception e) {
            result.setStatusInfo(BaseResult.FAIL,"设置角标异常");
        }
        result.setData(map);*/
        result.setStatusInfo("0","设置成功");
        return result;
    }
}
