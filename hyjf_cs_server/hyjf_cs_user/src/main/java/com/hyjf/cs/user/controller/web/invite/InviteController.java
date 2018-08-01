package com.hyjf.cs.user.controller.web.invite;

import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.user.service.invite.InviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励记录
 * @author hesy
 * @version InviteController, v0.1 2018/6/23 17:14
 */
@Api(value = "web端-邀请记录",tags = "web端-邀请记录")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/user/invite")
public class InviteController {
    private static final Logger logger = LoggerFactory.getLogger(InviteController.class);

    @Autowired
    InviteService inviteService;

    /**
     * 我的邀请列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "我的邀请列表", notes = "我的邀请列表")
    @ApiImplicitParam(name = "param",value = "{currPage:string,pageSize:string}", dataType = "Map")
    @PostMapping(value = "/myInviteList", produces = "application/json; charset=utf-8")
    public WebResult<List<MyInviteListCustomizeVO>> selectMyInviteList(@RequestHeader(value = "token", required = true) String token, @RequestBody  Map<String,String> param, HttpServletRequest request){
        WebResult<List<MyInviteListCustomizeVO>> result = new WebResult<List<MyInviteListCustomizeVO>>();
        List<MyInviteListCustomizeVO> resultList = Collections.emptyList();
        WebViewUserVO userVO = inviteService.getUsersByToken(token);

        logger.info("获取我的邀请列表开始，userId：{}", userVO.getUserId());

        // 请求参数校验
        inviteService.checkForInviteList(param);

        Integer inviteCount = inviteService.selectMyInviteCount(String.valueOf(userVO.getUserId()));
        Page page = Page.initPage(Integer.parseInt(param.get("currPage")), Integer.parseInt(param.get("pageSize")));
        page.setTotal(inviteCount);

        try {
            resultList = inviteService.selectMyInviteList(String.valueOf(userVO.getUserId()), page.getOffset(), page.getLimit());
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取我的邀请列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }

        result.setPage(page);
        return result;
    }

}
