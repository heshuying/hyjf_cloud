/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.SmsLogResponse;
import com.hyjf.am.response.admin.SmsOntimeResponse;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsLogVO;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.common.util.AsteriskProcessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 15:09
 */
@Api(tags = "消息中心-短信")
@RestController
@RequestMapping("/hyjf-admin/message/smsLog")
public class SmsLogController extends BaseController {

    public static final String PERMISSIONS = "smsLog";
    @Autowired
    private SmsLogService smsLogService;

    @ApiOperation(value = "查询消息中心短信发送记录", notes = "查询消息中心短信发送记录")
    @GetMapping("/smsLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SmsLogVO>> smsLogList(HttpServletRequest request) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow = true;
            }
        }

        SmsLogResponse response = smsLogService.smsLogList();
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<SmsLogVO> list = response.getResultList();
        if (!CollectionUtils.isEmpty(list)) {
            for (SmsLogVO vo : list) {
                if (!isShow) {
                    //如果没有查看脱敏权限,显示加星
                    vo.setMobile(AsteriskProcessUtil.getAsteriskedValue(vo.getMobile()));
                }
                vo.setContent(displayContent(vo));
            }
        }
        return new AdminResult<>(ListResult.build(list, response.getLogCount()));
    }

    List<String> types = Arrays.asList("提现成功", "还款成功", "投标成功", "充值成功", "平台转账-收到推广提成", "平台转账-收到活动奖励", "平台转账-收到手续费返现", "资金相关-充值成功", "资金相关-提现成功", "债转部分转让成功", "债转全部转让成功", "优惠券收益到账", "优惠券出借成功", "红包短信提醒", "智投订单进入服务回报期限", "智投订单已退出", "加息还款", "计划已还款", "优惠券还款成功");
    List<String> types2 = Arrays.asList("资金相关-投资成功", "提现失败", "分期出借成功", "加息放款", "资金相关-收到还款", "收到借款", "计划自动投标完成", "资金相关收到还款", "资金相关-出借成功", "还款成功-提前还款");
    List<String> types3 = Arrays.asList("用户注册验证码", "找回密码验证码", "绑定手机验证码", "解绑手机验证码", "注册", "找回密码", "更换手机号-验证原手机号", "更换手机号-绑定新手机号", "一键注册", "定向转账", "静默注册密码", "第三方用户注册密码发送", "微官网注册", "微官网找回密码");

    private String displayContent(SmsLogVO vo) {
        String content = null;
        Integer isDisplay = vo.getIsDisplay() == null ? 0 : vo.getIsDisplay();
        if (isDisplay == 1) {
            content = "********************";
        } else {
            if (types.contains(vo.getType())) {
                String regex = "[0-9]{1,14}\\.{0,1}[0-9]{0,2}";
                content = vo.getContent().replaceAll(regex, "********");
            } else if (types2.contains(vo.getType())) {
                String regex = "(([1-9]{1}\\d*)|(0{1}))(\\.\\d{2})";
                content = vo.getContent().replaceAll(regex, "********");
            } else if (types3.contains(vo.getType())) {
                String regex = "[0-9a-zA-Z]{6,16}";
                content = vo.getContent().replaceAll(regex, "********");
            } else if ("新增管理员密码提醒".equals(vo.getType())) {
                StringBuffer contentBuffer = new StringBuffer(vo.getContent());
                StringBuffer s = contentBuffer.replace(vo.getContent().indexOf("为", 21) + 1, vo.getContent().indexOf("，", 21), "********");
                content = contentBuffer.replace(s.indexOf("为", 23) + 1, s.lastIndexOf("，"), "********").toString();
            } else if ("后台重置密码".equals(vo.getType())) {
                StringBuffer contentBuffer = new StringBuffer(vo.getContent());
                StringBuffer s = contentBuffer.replace(vo.getContent().indexOf("号") + 1, vo.getContent().indexOf("密") - 1, "********");
                content = contentBuffer.replace(s.indexOf("：") + 1, s.indexOf("如") - 1, "********").toString();
            } else if ("提现验证码".equals(vo.getType())) {
                StringBuffer contentBuffer = new StringBuffer(vo.getContent());
                StringBuffer s = contentBuffer.replace(vo.getContent().indexOf("：", 9) + 1, vo.getContent().indexOf("。", 10), "********");
                StringBuffer s1 = s.replace(content.indexOf("：", 11) + 1, content.indexOf("元"), "********");
                content = s1.toString();
            } else {
                content = vo.getContent();
            }
        }
        return content;
    }

    @ApiOperation(value = "根据条件查询消息中心短信发送记录", notes = "根据条件查询消息中心短信发送记录")
    @PostMapping("/findSmsLog")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SmsLogVO>> findSmsLog(@RequestBody SmsLogRequest request, HttpServletRequest servletRequest) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) servletRequest.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow = true;
            }
        }
        SmsLogResponse response = smsLogService.findSmsLog(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<SmsLogVO> list = response.getResultList();
        if (!CollectionUtils.isEmpty(list)) {
            for (SmsLogVO vo : list) {
                if (!isShow) {
                    //如果没有查看脱敏权限,显示加星
                    if (vo.getMobile().contains(",")) {
                        String[] mobiles = vo.getMobile().split(",");
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < mobiles.length; i++) {
                            if (i != mobiles.length - 1) {
                                stringBuilder.append(AsteriskProcessUtil.getAsteriskedValue(mobiles[i])).append(",");
                            } else {
                                stringBuilder.append(AsteriskProcessUtil.getAsteriskedValue(mobiles[i]));
                            }
                        }
                        vo.setMobile(stringBuilder.toString());
                    } else {
                        vo.setMobile(AsteriskProcessUtil.getAsteriskedValue(vo.getMobile()));
                    }
                }
                vo.setContent(displayContent(vo));
            }
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getLogCount()));
    }

    @ApiOperation(value = "查询定时发送短信列表", notes = "查询定时发送短信列表")
    @PostMapping("/timeinit")
    public AdminResult<ListResult<SmsOntimeVO>> timeinit(@RequestBody SmsLogRequest request) {
        SmsOntimeResponse response = smsLogService.queryTime(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
