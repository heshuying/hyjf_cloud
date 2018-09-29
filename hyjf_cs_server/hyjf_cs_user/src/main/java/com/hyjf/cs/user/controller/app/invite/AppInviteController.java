package com.hyjf.cs.user.controller.app.invite;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.service.invite.InviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * app端邀请列表
 * @author hesy
 * @version AppInviteController, v0.1 2018/8/15 17:02
 */
@Api(value = "app端-邀请记录",tags = "app端-邀请记录")
@RestController
@CrossOrigin(origins = "*")
public class AppInviteController {
    @Autowired
    InviteService inviteService;

    /**
     * 邀请记录
     */
    @GetMapping("/hyjf-app/user/reward/inviteRecord")
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
        page = Integer.valueOf(page);
        pageSize = Integer.valueOf(pageSize);
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
            detailsJson.put("date", GetDate.timestamptoStrYYYYMMDDHHMM(user.getInviteTime()));

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
}
