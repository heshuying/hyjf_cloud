package com.hyjf.am.statistics.controller;

import com.hyjf.am.response.trade.AppChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.statistics.bean.AppChannelStatisticsDetail;
import com.hyjf.am.statistics.mongo.AppChannelStatisticsDetailDao;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/am-statistics/seach")
public class MongoSeachController {
	
	private static Logger logger = LoggerFactory.getLogger(MongoSeachController.class);

	@Autowired
	private AppChannelStatisticsDetailDao appChannelStatisticsDetailDao;

	/**
	 * 根据userId查询渠道投资信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getAppChannelStatisticsDetailByUserId/{userId}")
	public AppChannelStatisticsDetailResponse selectById(@PathVariable int userId) {
		AppChannelStatisticsDetail entity = appChannelStatisticsDetailDao.findByUserId(userId);
		AppChannelStatisticsDetailResponse response = new AppChannelStatisticsDetailResponse();
		if(entity != null){
			AppChannelStatisticsDetailVO appChannelStatisticsDetailVO = new AppChannelStatisticsDetailVO();
			BeanUtils.copyProperties(entity, appChannelStatisticsDetailVO);
			response.setResult(appChannelStatisticsDetailVO);
		}
		return response;
	}
}
