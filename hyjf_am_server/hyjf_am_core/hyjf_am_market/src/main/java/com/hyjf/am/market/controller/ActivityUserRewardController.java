package com.hyjf.am.market.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.market.dao.model.auto.ActivityUserReward;
import com.hyjf.am.market.service.ActivityUserRewardService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.market.ActivityUserRewardResponse;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version ActivityUserRewardController, v0.1 2019-04-18 16:08
 */
@RestController
@RequestMapping("/am-market/activity/reward")
public class ActivityUserRewardController {
    private Logger logger = LoggerFactory.getLogger(ActivityUserRewardController.class);

    @Autowired
    private ActivityUserRewardService activityUserRewardService;
    /**
     * 保存领取记录 (不支持重复保存)
     * @param vo
     * @return
     */
    @RequestMapping("/insert")
    public BooleanResponse insert(@RequestBody ActivityUserRewardVO vo) {
        logger.info("insert ActivityUserReward, vo is: {}", vo);

		List<ActivityUserReward> list = activityUserRewardService.selectByUserId(vo.getUserId(), vo.getActivityId(), vo.getGrade());
		// logger.info("test, {}", list != null);
		if (!CollectionUtils.isEmpty(list)) {
			logger.error("用户：{}在活动： {}已经领取奖励", vo.getUserId(), vo.getActivityId());
			return new BooleanResponse(Boolean.FALSE);
		}

		ActivityUserReward reward = new ActivityUserReward();
		BeanUtils.copyProperties(vo, reward);
		int rewardId = activityUserRewardService.insertActivityUserReward(reward);
        logger.info("用户: {}竞猜成功， rewardId: {}", vo.getUserId(), rewardId);
        return new BooleanResponse(Boolean.TRUE);
    }

	/**
	 * 保存领取记录
	 * @param vo
	 * @return
	 */
	@RequestMapping("/savereward")
	public ActivityUserRewardResponse saveReward(@RequestBody ActivityUserRewardVO vo) {
		logger.info("insert ActivityUserReward, vo is: {}", vo);
		ActivityUserRewardResponse reaponse = new ActivityUserRewardResponse();
		ActivityUserReward reward = new ActivityUserReward();
		BeanUtils.copyProperties(vo, reward);
		int rewardId = activityUserRewardService.insertActivityUserReward(reward);
		logger.info("用户: {}参与活动成功， rewardId: {}", vo.getUserId(), rewardId);
		reaponse.setRewardId(rewardId);
		return reaponse;
	}

    /**
     * 查询用户领取奖励信息
     * @param activityId
     * @param userId
     * @param grade
     * @return
     */
	@RequestMapping("/select/{activityId}/{userId}/{grade}")
	public ActivityUserRewardResponse select(@PathVariable int activityId,
                                             @PathVariable int userId,
                                             @PathVariable int grade) {
		logger.info("select ActivityUserReward, activityId is: {}, userId is: {}, grade is: {}", activityId, userId,
				grade);

		List<ActivityUserReward> list = activityUserRewardService.selectByUserId(userId, activityId, grade);
		ActivityUserRewardResponse rewardResponse = new ActivityUserRewardResponse();

		if (!CollectionUtils.isEmpty(list)) {
			List<ActivityUserRewardVO> vos = new ArrayList<>(list.size());
			ActivityUserRewardVO vo = null;
			for (ActivityUserReward reward : list) {
				vo = new ActivityUserRewardVO();
				BeanUtils.copyProperties(reward, vo);
				vos.add(vo);
			}
			rewardResponse.setResultList(vos);
		}
		return rewardResponse;
	}

}
