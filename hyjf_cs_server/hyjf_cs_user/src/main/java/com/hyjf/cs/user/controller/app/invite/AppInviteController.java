package com.hyjf.cs.user.controller.app.invite;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.app.AppBaseRequest;
import com.hyjf.am.vo.app.reward.*;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.user.service.invite.InviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * app端邀请列表
 * @author hesy
 * @version AppInviteController, v0.1 2018/8/15 17:02
 */
@Api(value = "app端-我的奖励",tags = "app端-邀请记录")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-app/user/reward")
public class AppInviteController {

    private static final Logger logger = LoggerFactory.getLogger(AppInviteController.class);

    @Autowired
    InviteService inviteService;

    /**
     * 邀请记录
     */
    @GetMapping("/inviteRecord")
    @ApiOperation(value = "邀请记录", notes = "邀请记录")
    public JSONObject inviteList(@RequestParam(value = "currentPage", defaultValue = "1") int page,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest request) {
        JSONObject ret = new JSONObject();

        String version = request.getParameter("version");
        // 平台 系统类别 0：PC，1：微官网，2：Android，3：IOS，4：其他
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(platform)) {
            ret.put("status", "999");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "999");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得用户iD
        Integer userId = null;
        try {
            userId = SecretUtil.getUserId(sign);
        } catch (Exception e) {
            ret.put("status", "999");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
//        page = Integer.valueOf(page);
//        pageSize = Integer.valueOf(pageSize);
        int limitStart = pageSize * (page - 1);
        ret.put("status", "000");
        ret.put("statusDesc", "成功");
        int count = inviteService.selectMyInviteCount(String.valueOf(userId));
        double lastpage = Math.ceil((double) count / (double) pageSize);
        if (lastpage == page) {
            ret.put("isEnd", true);
        }else{
            ret.put("isEnd", false);
        }
        List<MyInviteListCustomizeVO> list = inviteService.selectMyInviteList(String.valueOf(userId), limitStart, pageSize);
        JSONArray jsonArray = new JSONArray();
        for (MyInviteListCustomizeVO user : list) {
            JSONObject detailsJson = new JSONObject();
            detailsJson.put("friendName", user.getUsername());
            detailsJson.put("date", user.getInviteTime());

            if ("0".equals(user.getUserStatus())) {
                detailsJson.put("openStatus", "未开户");
            } else {
                detailsJson.put("openStatus", "已开户");
            }
            jsonArray.add(detailsJson);
        }
        ret.put("list", jsonArray);
        return ret;

    }

    /**
     * 邀请记录初始化数据
     * @author wgx
     * @date 2019/04/18
     * @return
     */
    @ApiOperation(value = "我的奖励-邀请记录", notes = "我的奖励-邀请记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", dataType = "String", required = true),
            @ApiImplicitParam(name = "platform", value = "平台类型 2：Android，3：IOS", dataType = "String", required = true),
            @ApiImplicitParam(name = "sign", value = "用户唯一标识", dataType = "String", required = true)
    })
    @PostMapping("/inviteInit")
    @ResponseBody
    public WebResult<AppInviteVO> inviteInit(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @ModelAttribute AppBaseRequest appBaseRequest) {
        WebResult webResult = new WebResult("0","成功");
        webResult.setData(Collections.emptyMap());
        String version = appBaseRequest.getVersion();
        String platform = appBaseRequest.getPlatform();
        String sign = appBaseRequest.getSign();
        String key = SecretUtil.getKey(sign);
        //检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(platform) || Validator.isNull(key)) {
            webResult.setStatus("1");
            webResult.setStatusDesc("请求参数非法");
            return webResult;
        }
        // 取得用户ID
        Integer userId = null;
        try {
            userId = SecretUtil.getUserId(sign);
            logger.debug("【邀请记录】用户id：{}", userId);
        } catch (Exception e) {
            webResult.setStatus("1");
            webResult.setStatusDesc("请求参数非法");
            return webResult;
        }
        AppInviteVO appInviteVO = new AppInviteVO();
        Page page = new Page();
        WebViewUserVO users = null;
        try {
            // 我的奖励信息
            AppRewardDetailVO appRewardDetailVO = new AppRewardDetailVO();
            Map<String, String> pageData = inviteService.selectInvitePageData(String.valueOf(userId));
            String inviteCount = pageData.get("inviteCount");
            String total = pageData.get("rewardRecordsSum");
            users = inviteService.getUserFromCache(userId);
            appRewardDetailVO.setTotal(StringUtils.isBlank(total) ? "0" : total);
            appRewardDetailVO.setFriendCount(inviteCount);
            appRewardDetailVO.setCouponCount("0");
            appRewardDetailVO.setCouponTag("邀请好友获得");
            appRewardDetailVO.setUserName(users.getUsername());
            appRewardDetailVO.setUserId(users.getUserId());
            appRewardDetailVO.setIconUrl(users.getIconUrl());
            appInviteVO.setDetail(appRewardDetailVO);
        } catch (Exception e){
            logger.error("【我的奖励】我的奖励信息获取失败！用户id：{}", userId, e);
            webResult.setStatus("1");
            webResult.setStatusDesc("我的奖励信息获取失败");
            return webResult;
        }
        // 邀请记录列表
        List<AppInviteRecordVO> appInviteRecordVOList = new ArrayList<>();
        try{
            int limitStart = pageSize * (currentPage - 1);
            int count = inviteService.selectMyInviteCount(String.valueOf(userId));
            List<MyInviteListCustomizeVO> list = inviteService.selectMyInviteList(String.valueOf(userId), limitStart, pageSize);
            if(list != null) {
                list.stream().forEach(p -> {
                    AppInviteRecordVO appInviteRecordVO = new AppInviteRecordVO();
                    appInviteRecordVO.setFriendName(p.getUsername());
                    appInviteRecordVO.setDate(p.getInviteTime());
                    appInviteRecordVO.setOpenStatus("0".equals(p.getUserStatus()) ? "未开户" : "已开户");
                    appInviteRecordVOList.add(appInviteRecordVO);
                });
            }
            appInviteVO.setRecordList(appInviteRecordVOList);
            // 分页信息
            page.setCurrPage(currentPage);
            page.setPageSize(pageSize);
            page.setTotal(count);
        } catch (Exception e) {
            logger.error("【我的奖励】获取邀请记录列表发生异常！用户名：{}", users.getUsername(), e);
            appInviteVO.setRecordList(new ArrayList<>());
            page.setTotal(0);
        }
        webResult.setData(appInviteVO);
        webResult.setPage(page);
        return webResult;
    }
}
