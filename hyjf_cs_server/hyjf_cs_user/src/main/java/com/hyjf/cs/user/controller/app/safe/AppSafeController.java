/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.safe.SafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AppUserController, v0.1 2018/6/11 14:51
 */

@Api(value = "app端用户账户设置",description = "app端-用户账户设置")
@RestController
@RequestMapping("/hyjf-app/appUser")
public class AppSafeController extends BaseUserController {

    @Autowired
    private SafeService safeService;

    /**
     * 获取联系人类型
     * @auther: hesy
     * @date: 2018/7/18
     */
    @ResponseBody
    @ApiOperation(value = "获取联系人类型",notes = "获取联系人类型")
    @PostMapping ("/getRelationTypes")
    public JSONObject getRelationTypes(HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/getRelationTypes");

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // token
        String token = request.getParameter("token");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 业务逻辑
        try {
            Map<String, String> relationMap = CacheUtil.getParamNameMap("USER_RELATION");
            JSONArray result = new JSONArray();
            for (Map.Entry<String, String> entry : relationMap.entrySet()) {
                JSONObject json = new JSONObject();
                json.put("name", entry.getValue());
                json.put("value", entry.getKey());
                result.add(json);
            }
            
            ret.put("status", "0");
            ret.put("statusDesc", "获取联系人类型成功");
            ret.put("relationTypes", result);
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "获取联系人类型发生错误");
        }

        return ret;
    }

    /**
     * 修改紧急联系人
     * @auther: hesy
     * @date: 2018/7/18
     */
    @ResponseBody
    @PostMapping("/updateUrgentAction")
    @ApiOperation(value = "修改紧急联系人",notes = "修改紧急联系人")
    public JSONObject updateUrgentAction(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/updateUrgentAction");

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // token
        String token = request.getParameter("token");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 紧急联系人关系
        String urgentRelation = request.getParameter("urgentRelation");
        // 紧急联系人姓名
        String urgentName = request.getParameter("urgentName");
        // 紧急联系人电话
        String urgentMobile = request.getParameter("urgentMobile");

        WebViewUserVO webViewUserVO = safeService.getWebViewUserByUserId(userId);

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 业务逻辑
        try {
            if (userId != null) {
                // 解密
                urgentRelation = DES.decodeValue(key, urgentRelation);
                urgentName = DES.decodeValue(key, urgentName);
                urgentMobile = DES.decodeValue(key, urgentMobile);
                if (Validator.isNull(urgentRelation)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "联系人关系不能为空");
                    return ret;
                }
                if (Validator.isNull(urgentName)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "联系人姓名不能为空");
                    return ret;
                }
                if (Validator.isNull(urgentMobile)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "联系人电话不能为空");
                    return ret;
                }
                Map<String, String> relationMap = CacheUtil.getParamNameMap("USER_RELATION");
                Boolean validUrgentRelation = false;
                for (Map.Entry<String, String> entry : relationMap.entrySet()) {
                    if (entry.getKey().equals(urgentRelation)) {
                        validUrgentRelation = true;
                        break;
                    }
                }
                if (!validUrgentRelation) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "无效的紧急联系人关系");
                    return ret;
                }
                if (urgentName.length() > 10) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "紧急联系人姓名不能大于10位");
                    return ret;
                }
                if (!Validator.isMobile(urgentMobile)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "无效的紧急联系人电话");
                    return ret;
                }

                // 更新联系人信息
                boolean success = safeService.saveContract(urgentRelation, urgentName, urgentMobile,webViewUserVO);

                if (success) {
                    ret.put("status", "0");
                    ret.put("statusDesc", "修改联系人成功");
                } else {
                    ret.put("status", "1");
                    ret.put("statusDesc", "修改联系人失败");
                }
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "修改联系人发生错误");
        }

        return ret;
    }

}
