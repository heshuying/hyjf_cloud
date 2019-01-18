package com.hyjf.am.trade.service.task.issuerecover.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowBailMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowBail;
import com.hyjf.am.trade.dao.model.auto.BorrowBailExample;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.issuerecover.AutoBailMessageService;
import com.hyjf.common.util.CustomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/11 19:03
 * @Description: AutoBailMessageServiceImpl
 */
@Service
public class AutoBailMessageServiceImpl extends BaseServiceImpl implements AutoBailMessageService {
	private static final Logger logger = LoggerFactory.getLogger(AutoBailMessageServiceImpl.class);
	@Resource
	private BorrowMapper borrowMapper;
	@Resource
	private BorrowBailMapper borrowBailMapper;

	@Override
	public boolean updateRecordBorrow(Borrow borrow) {
		// 该借款编号没有交过保证金
		BorrowBail borrowBail = getBorrowBailByNid(borrow.getBorrowNid());
		if (borrowBail == null) {
			borrowBail = new BorrowBail();
			// 借款人的ID
			borrowBail.setBorrowUid(borrow.getUserId());
			// 操作人的ID
			borrowBail.setOperaterUid(1);
			// 借款编号
			borrowBail.setBorrowNid(borrow.getBorrowNid());
			// 保证金数值
			BigDecimal bailPercent = new BigDecimal(this.getBorrowConfig(CustomConstants.BORROW_BAIL_RATE));// 计算公式：保证金金额=借款金额×3％
			BigDecimal accountBail = (borrow.getAccount()).multiply(bailPercent).setScale(2, BigDecimal.ROUND_DOWN);
			borrowBail.setBailNum(accountBail);
			// 10位系统时间（到秒）
			borrowBail.setUpdateTime(new Date());
			boolean bailFlag = this.borrowBailMapper.insertSelective(borrowBail) > 0 ? true : false;
			if (bailFlag) {
				borrow.setVerifyStatus(1);
				boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
				if (borrowFlag) {
					return true;
				}
			}
		}
		return false;
	}

	private BorrowBail getBorrowBailByNid(String borrowNid) {
		BorrowBailExample exampleBail = new BorrowBailExample();
		BorrowBailExample.Criteria craBail = exampleBail.createCriteria();
		craBail.andBorrowNidEqualTo(borrowNid);
		List<BorrowBail> list = this.borrowBailMapper.selectByExample(exampleBail);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}
