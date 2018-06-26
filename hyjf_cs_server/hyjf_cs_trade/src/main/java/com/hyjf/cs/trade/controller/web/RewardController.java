package com.hyjf.cs.trade.controller.web;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.service.RewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励记录
 * @author hesy
 * @version InviteController, v0.1 2018/6/23 17:14
 */
@Api(value = "Web端奖励记录")
@RestController
@RequestMapping("/web/invite")
public class RewardController {
    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    RewardService rewardService;

    /**
     * 我的奖励列表
     * @param token
     * @param param
     * @param request
     * @return
     */
    @ApiOperation(value = "我的奖励列表", notes = "我的奖励列表")
    @ApiImplicitParam(name = "param",value = "{currPage:string,pageSize:string}", dataType = "Map")
    @PostMapping(value = "/myRewardList", produces = "application/json; charset=utf-8")
    public WebResult<List<MyRewardRecordCustomizeVO>> selectMyRewardList(@RequestHeader(value = "token", required = true) String token, Map<String,String> param, HttpServletRequest request){
        WebResult<List<MyRewardRecordCustomizeVO>> result = new WebResult<List<MyRewardRecordCustomizeVO>>();
        WebViewUserVO userVO = rewardService.getUsersByToken(token);
        // 请求参数校验
        rewardService.checkForRewardList(param);

        Page page = Page.initPage(Integer.parseInt(param.get("currPage")), Integer.parseInt(param.get("pageSize")));

        try {
            List<MyRewardRecordCustomizeVO> resultList = rewardService.selectMyRewardList(String.valueOf(userVO.getUserId()), page.getOffset(), page.getLimit());
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取我的奖励列表异常", e);
        }

        return result;
    }
}
