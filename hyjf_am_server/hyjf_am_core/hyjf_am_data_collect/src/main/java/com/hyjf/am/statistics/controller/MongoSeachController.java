package com.hyjf.am.statistics.controller;

import com.hyjf.am.response.trade.AppChannelStatisticsDetailResponse;
import com.hyjf.am.statistics.bean.AppChannelStatisticsDetail;
import com.hyjf.am.statistics.bean.BankSmsAuthCode;
import com.hyjf.am.statistics.bean.DirectionalTransferAssociatedRecords;
import com.hyjf.am.statistics.mongo.AppChannelStatisticsDetailDao;
import com.hyjf.am.statistics.mongo.BankSmsAuthCodeDao;
import com.hyjf.am.statistics.mongo.DirectionalTransferAssociatedRecordsDao;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
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

	@Autowired
	DirectionalTransferAssociatedRecordsDao directionalTransferAssociatedRecordsDao;

	@Autowired
	BankSmsAuthCodeDao bankSmsAuthCodeDao;
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

	/**
	 * 企业用户是否已绑定用户
	 *
	 * @param userId
	 * @return -1 未绑定，0初始，1成功，2失败
	 * @author Michael
	 */
	@RequestMapping("/isCompBindUser/{userId}")
	public int isCompBindUser(@PathVariable Integer userId) {
		int result = -1;
		DirectionalTransferAssociatedRecords entity = directionalTransferAssociatedRecordsDao.findByUserId(userId);
		if (entity != null) {
			result = entity.getAssociatedState();
		}
		return result;
	}

	@RequestMapping("/selectBankSmsSeq/{userId}/{txcodeDirectRechargeOnline}")
	public String selectBankSmsSeq(@PathVariable Integer userId,@PathVariable String txcodeDirectRechargeOnline) {
		BankSmsAuthCode smsAuthCode = bankSmsAuthCodeDao.selectBankSmsSeq(userId,txcodeDirectRechargeOnline);
		if (smsAuthCode != null) {
			return smsAuthCode.getSrvAuthCode();
		}
		return null;
	}
}
