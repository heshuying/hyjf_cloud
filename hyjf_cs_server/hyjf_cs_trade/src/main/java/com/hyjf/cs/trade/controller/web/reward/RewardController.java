package com.hyjf.cs.trade.controller.web.reward;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.service.reward.RewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励记录
 *
 * @author hesy
 * @version RewardController, v0.1 2018/6/23 17:14
 */
@Api(value = "web端-奖励记录", tags = "web端-奖励记录")
@RestController
@RequestMapping("/hyjf-web/invite")
public class RewardController {
    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    RewardService rewardService;

    /**
     * 我的奖励列表
     */
    @ApiOperation(value = "我的奖励列表", notes = "我的奖励列表")
    @ApiImplicitParam(name = "param", value = "{currPage:string,pageSize:string}", dataType = "Map")
    @PostMapping(value = "/myRewardList", produces = "application/json; charset=utf-8")
    public WebResult<List<MyRewardRecordCustomizeVO>> selectMyRewardList(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> param) {
        WebResult<List<MyRewardRecordCustomizeVO>> result = new WebResult<List<MyRewardRecordCustomizeVO>>();
        WebViewUserVO userVO = rewardService.getUserFromCache(userId);

        if (userVO != null) {
            logger.info("获取我的奖励列表开始，userId：{}", userVO.getUserId());

            // 请求参数校验
            rewardService.checkForRewardList(param);

            Page page = Page.initPage(Integer.parseInt(param.get("currPage")), Integer.parseInt(param.get("pageSize")));
            Integer rewardCount = rewardService.selectMyRewardCount(String.valueOf(userVO.getUserId()));
            page.setTotal(rewardCount);

            try {
                List<MyRewardRecordCustomizeVO> resultList = rewardService.selectMyRewardList(String.valueOf(userVO.getUserId()), page.getOffset(), page.getLimit());
                result.setData(resultList);
            } catch (Exception e) {
                logger.error("获取我的奖励列表异常", e);
                result.setStatus(WebResult.ERROR);
                result.setStatusDesc(WebResult.ERROR_DESC);
                result.setData(Collections.emptyList());
            }
            result.setPage(page);
        } else {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            result.setData(Collections.emptyList());
        }
        return result;
    }
}
