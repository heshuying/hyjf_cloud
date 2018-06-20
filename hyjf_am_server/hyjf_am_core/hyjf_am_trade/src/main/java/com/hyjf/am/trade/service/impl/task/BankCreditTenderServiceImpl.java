/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.hyjf.am.trade.dao.mapper.auto.CreditTenderLogMapper;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLogExample;
import com.hyjf.am.trade.service.task.BankCreditTenderService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行债转异常处理
 * @author jun
 * @since 20180620
 */
@Service
public class BankCreditTenderServiceImpl implements BankCreditTenderService {

	@Autowired
	private CreditTenderLogMapper creditTenderLogMapper;

	/**
	 * 获取债转投资异常记录数据
	 * @return
	 */
	@Override
	public List<CreditTenderLog> selectCreditTenderLogs() {
		CreditTenderLogExample example = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo((byte) 0);
		// 添加时间 <当前时间-5分钟
		cra.andAddTimeLessThan(GetDate.getMinutesAfter(GetDate.getNowTime10(),-5));
		cra.andAddTimeGreaterThanOrEqualTo(GetDate.countDate(5,-2));//两天之前
		return creditTenderLogMapper.selectByExample(example);
	}
}
