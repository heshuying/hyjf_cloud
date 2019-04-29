package com.hyjf.cs.trade.controller.app.reward;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.app.AppBaseRequest;
import com.hyjf.am.vo.app.reward.*;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.service.coupon.MyCouponListService;
import com.hyjf.cs.trade.service.reward.RewardService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version AppRewardController, v0.1 2018/8/15 19:52
 */
@Api(value = "app端-我的奖励",tags = "app端-奖励记录")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-app/user/reward")
public class AppRewardController {

    private static final Logger logger = LoggerFactory.getLogger(AppRewardController.class);

    @Autowired
    RewardService rewardService;
    @Autowired
    MyCouponListService myCouponListService;

    /**
     *
     * 我的奖励初始化数据
     *
     * @author pcc
     * @param request
     * @param response
     * @return
     */
    @GetMapping("")
    @ApiOperation(value = "我的奖励初始化数据", notes = "我的奖励初始化数据")
    public JSONObject reward(HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();

        // 版本号
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
        ret.put("status", "000");
        ret.put("statusDesc", "成功");
        Map<String,String> pageData = myCouponListService.selectInvitePageData(String.valueOf(userId));
        String inviteCount = pageData.get("inviteCount");
        String total = pageData.get("rewardRecordsSum");
        if (total == null) {
            ret.put("total", 0);
        } else {
            ret.put("total", total);
        }
        ret.put("friendCount", inviteCount);
        // int couponTotal = this.couponService.countUserCouponTotal(userId);
        ret.put("coupon", 0);

        WebViewUserVO users = rewardService.getUserFromCache(userId);
        ret.put("userName", users.getUsername());
        ret.put("userId", users.getUserId());
        ret.put("iconUrl", users.getIconUrl());
        return ret;
    }

    /**
     * 奖励记录
     * @author pcc
     */
    @GetMapping("/rewardRecord")
    @ApiOperation(value = "奖励记录", notes = "奖励记录")
    public JSONObject rewardList(@RequestParam(value = "currentPage", defaultValue = "1") int page,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest request,
                                 HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        // 版本号
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
        try {
//            page = Integer.valueOf(page);
//            pageSize = Integer.valueOf(pageSize);
            int limitStart = pageSize * (page - 1);
            int count = rewardService.selectMyRewardCount(String.valueOf(userId));
            double lastpage = Math.ceil((double) count / (double) pageSize);
            if (lastpage == page) {
                ret.put("isEnd", true);
            } else {
                ret.put("isEnd", false);
            }
            List<MyRewardRecordCustomizeVO> list = rewardService.selectMyRewardList(String.valueOf(userId), limitStart, pageSize);

            JSONArray jsonArray = new JSONArray();
            for (MyRewardRecordCustomizeVO user : list) {
                JSONObject detailsJson = new JSONObject();
                detailsJson.put("friendName", user.getUsername());
                detailsJson.put("source", "好友出借");
                detailsJson.put("content", user.getPushMoney() + "元现金");

                jsonArray.add(detailsJson);
            }

            ret.put("list", list);
            ret.put("status", "000");
            ret.put("statusDesc", "成功");
            ret.put("list", jsonArray);
        } catch (Exception e) {
            ret.put("isEnd", true);
            ret.put("list", new ArrayList<MyRewardRecordCustomizeVO>());
            ret.put("status", "000");
            ret.put("statusDesc", "成功");
        }

        return ret;
    }

    /**
     * 我的奖励初始化数据
     * @author wgx
     * @date 2019/04/18
     * @return
     */
    @ApiOperation(value = "我的奖励-奖励记录", notes = "我的奖励-奖励记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", dataType = "String", required = true),
            @ApiImplicitParam(name = "platform", value = "平台类型 2：Android，3：IOS", dataType = "String", required = true),
            @ApiImplicitParam(name = "sign", value = "用户唯一标识", dataType = "String", required = true)
    })
    @PostMapping("/rewardInit")
    @ResponseBody
    public WebResult<AppRewardVO> rewardInit(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @ModelAttribute AppBaseRequest appBaseRequest) {
        WebResult webResult = new WebResult();
        webResult.setData(Collections.emptyMap());
        String version = appBaseRequest.getVersion();
        String platform = appBaseRequest.getPlatform();
        String sign = appBaseRequest.getSign();
        String key = SecretUtil.getKey(sign);
        //检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(platform) || Validator.isNull(key)) {
            webResult.setStatus("999");
            webResult.setStatusDesc("请求参数非法");
            return webResult;
        }
        // 取得用户ID
        Integer userId = null;
        try {
            userId = SecretUtil.getUserId(sign);
            logger.debug("【奖励记录】用户id：{}", userId);
        } catch (Exception e) {
            webResult.setStatus("999");
            webResult.setStatusDesc("请求参数非法");
            return webResult;
        }
        AppRewardVO appRewardVO = new AppRewardVO();
        // 我的奖励信息
        AppRewardDetailVO appRewardDetailVO = new AppRewardDetailVO();
        Map<String,String> pageData = myCouponListService.selectInvitePageData(String.valueOf(userId));
        String inviteCount = pageData.get("inviteCount");
        String total = pageData.get("rewardRecordsSum");
        WebViewUserVO users = rewardService.getUserFromCache(userId);
        appRewardDetailVO.setTotal(StringUtils.isBlank(total) ? "0" :total);
        appRewardDetailVO.setFriendCount(inviteCount);
        appRewardDetailVO.setCouponCount("0");
        appRewardDetailVO.setCouponTag("邀请好友获得");
        appRewardDetailVO.setUserName(users.getUsername());
        appRewardDetailVO.setUserId(users.getUserId());
        appRewardDetailVO.setIconUrl(users.getIconUrl());
        appRewardVO.setDetail(appRewardDetailVO);
        // 邀请记录列表
        List<AppRewardRecordVO> appRewardRecordVOList = new ArrayList<>();
        try {
            int limitStart = pageSize * (currentPage - 1);
            int count = rewardService.selectMyRewardCount(String.valueOf(userId));
            List<MyRewardRecordCustomizeVO> list = rewardService.selectMyRewardList(String.valueOf(userId), limitStart, pageSize);
            if(list != null) {
                list.stream().forEach(p -> {
                    AppRewardRecordVO appRewardRecordVO = new AppRewardRecordVO();
                    appRewardRecordVO.setFriendName(p.getUsername());
                    appRewardRecordVO.setSource("好友出借");
                    appRewardRecordVO.setContent(p.getPushMoney() + "元现金");
                    appRewardRecordVOList.add(appRewardRecordVO);
                });
            }
            appRewardVO.setRecordList(appRewardRecordVOList);
            // 分页信息
            Page page = new Page();
            page.setCurrPage(currentPage);
            page.setPageSize(pageSize);
            page.setTotal(count);
            webResult.setPage(page);
        } catch (Exception e) {
            logger.error("【我的奖励】获取奖励记录列表发生异常！用户名：{}", users.getUsername(), e);
        }
        webResult.setData(appRewardVO);
        return webResult;
    }

}
