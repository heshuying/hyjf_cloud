package com.hyjf.cs.trade.mq.handle;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.cs.trade.client.AmUserClient;

/**
 * @Description 更新huiyingdai_utm_reg的首投信息
 * @Author sunss
 * @Date 2018/7/7 14:54
 */
@Component
public class UtmRegHandle {

	private static final Logger logger = LoggerFactory.getLogger(UtmRegHandle.class);
	@Autowired
	private AmUserClient amUserClient;

	public void sendMessage( Map<String, Object> param) {
		Integer userId = (Integer)param.get("userId");
		UtmRegVO utmReg = amUserClient.findUtmRegByUserId(userId);
		if (utmReg != null) {
			// 更新huiyingdai_utm_reg的首投信息
			boolean updateUtmFlag = amUserClient.updateFirstUtmReg(param);
		}
		logger.info("----------------------------更新huiyingdai_utm_reg的首投信息结束--------------------------------");

	}
}
