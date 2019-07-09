package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.auto.ActivityUserGuess;
import com.hyjf.am.market.service.ActivityUserGuessService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.market.ActivityUserGuessResponse;
import com.hyjf.am.vo.activity.ActivityUserGuessVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version ActivityUserGuessController, v0.1 2019-04-17 17:15
 */
@RestController
@RequestMapping("/am-market/activity/guess")
public class ActivityUserGuessController {
	private Logger logger = LoggerFactory.getLogger(ActivityUserGuessController.class);

	@Autowired
	private ActivityUserGuessService activityUserGuessService;

	/**
	 * 保存竞猜结果
	 * @param userId
	 * @param guess
	 * @return
	 */
	@RequestMapping("/insert/{userId}/{guess}")
	public BooleanResponse insert(@PathVariable int userId,
                                  @PathVariable int guess) {
		logger.info("insert ActivityUserGuess, userId is: {}, guess is: {}", userId, guess);

		if (activityUserGuessService.selectByUserId(userId) != null) {
			logger.error("用户：{}已经参与竞猜，不重复...", userId);
			return new BooleanResponse(Boolean.FALSE);
		}
        int guessId = activityUserGuessService.insertActivityUserGuess(userId, guess);
		logger.info("用户: {}竞猜成功， id: {}", userId, guessId);
		return new BooleanResponse(Boolean.TRUE);
	}

	/**
	 * 查询用户是竞猜结果
	 * @param userId
	 * @return
	 */
	@RequestMapping("/select/{userId}")
	public ActivityUserGuessResponse select(@PathVariable int userId) {
		logger.info("select ActivityUserGuess, userId is: {}", userId);
		ActivityUserGuessResponse response = new ActivityUserGuessResponse();

		ActivityUserGuess guess = activityUserGuessService.selectByUserId(userId);
		if (guess != null) {
			ActivityUserGuessVO vo = new ActivityUserGuessVO();
			BeanUtils.copyProperties(guess, vo);
			response.setResult(vo);
		}
		return response;
	}
}
