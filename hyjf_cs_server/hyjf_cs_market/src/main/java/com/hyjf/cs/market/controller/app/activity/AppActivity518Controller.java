package com.hyjf.cs.market.controller.app.activity;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.cs.market.controller.AbstractActivity518Controller;
import com.hyjf.cs.market.vo.activity518.Activity518DrawVO;
import com.hyjf.cs.market.vo.activity518.Activity518InfoVO;
import com.hyjf.cs.market.vo.activity518.Activity518UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version AppActivity518Controller, v0.1 2019-04-17 9:48
 */
@Api(tags = "app端-2019-518活动")
@RestController
@RequestMapping("/hyjf-app/activity/2019518")
public class AppActivity518Controller extends AbstractActivity518Controller {
    private Logger logger = LoggerFactory.getLogger(AppActivity518Controller.class);

    private final String SUCCESS_STATUS = "000";
    private final String FAIL_STATUS = "99";

    /**
     * 查询活动信息
     *
     * @return
     */
    @ApiOperation(value = "活动信息接口", notes = "查询活动信息")
    @RequestMapping(value = "/activityinfo", method = RequestMethod.POST)
    public BaseResult<Activity518InfoVO> queryActivityInfo(@RequestHeader(required = false) Integer userId) {
        logger.info("app端-活动信息接口...");
        return super.queryActivityInfo(userId);
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    @ApiOperation(value = "用户信息接口", notes = "查询用户信息和排行榜")
    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    public BaseResult<Activity518UserInfoVO> queryUserInfo(@RequestHeader Integer userId) {
        logger.info("app端-用户信息接口...");
        return super.queryUserInfo(userId);
    }

    /**
     * 抽奖
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "抽奖", notes = "用户大转盘抽奖")
    @RequestMapping(value = "/draw", method = RequestMethod.POST)
    public BaseResult<Activity518DrawVO> draw(@RequestHeader Integer userId) {
        logger.info("app端-抽奖, userId is: {}", userId);
        return super.doDraw(userId);
    }

    @Override
    protected String getSuccessStatus() {
        return SUCCESS_STATUS;
    }

    @Override
    protected String getFailStatus() {
        return FAIL_STATUS;
    }

}
