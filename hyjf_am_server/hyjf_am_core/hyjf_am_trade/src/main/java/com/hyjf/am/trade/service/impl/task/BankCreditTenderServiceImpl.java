/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.hyjf.am.trade.dao.mapper.auto.CreditTenderLogMapper;
import com.hyjf.am.trade.dao.mapper.auto.CreditTenderMapper;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderExample;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLogExample;
import com.hyjf.am.trade.service.task.BankCreditTenderService;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.common.util.CommonUtils;
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
	@Autowired
	private CreditTenderMapper creditTenderMapper;
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

	/**
	 * 根据承接订单号查询债转投资表
	 * @param assignNid
	 * @return
	 */
	@Override
	public List<CreditTender> selectCreditTender(String assignNid) {
		CreditTenderExample example = new CreditTenderExample();
		CreditTenderExample.Criteria cra = example.createCriteria();
		cra.andAssignNidEqualTo(assignNid);
		return this.creditTenderMapper.selectByExample(example);
	}

	/**
	 * 更新債轉記錄
	 * @param creditTenderLog
	 * @return
	 */
	@Override
	public int updateCreditTenderLog(CreditTenderLogVO creditTenderLog) {
		return this.creditTenderLogMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(creditTenderLog,CreditTenderLog.class));
	}
}
