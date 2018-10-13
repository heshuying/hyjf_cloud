package com.hyjf.cs.trade.controller.app.reward;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.service.coupon.MyCouponListService;
import com.hyjf.cs.trade.service.reward.RewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version AppRewardController, v0.1 2018/8/15 19:52
 */
@Api(value = "app端-我的奖励",tags = "app端-邀请记录")
@RestController
@CrossOrigin(origins = "*")
public class AppRewardController {
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
    @GetMapping("/hyjf-app/user/reward")
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
    @GetMapping("/hyjf-app/user/reward/rewardRecord")
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
                detailsJson.put("source", "好友投资");
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
}
