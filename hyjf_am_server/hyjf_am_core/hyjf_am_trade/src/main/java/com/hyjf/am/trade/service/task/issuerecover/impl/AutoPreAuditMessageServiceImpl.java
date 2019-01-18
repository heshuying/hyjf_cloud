package com.hyjf.am.trade.service.task.issuerecover.impl;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 09:24 自动初审消息
 * @Description: AutoPreAuditMessageServiceImpl
 */
@Service
public class AutoPreAuditMessageServiceImpl extends BaseServiceImpl implements AutoPreAuditMessageService {

	@Override
	public boolean updateRecordBorrow(Borrow borrow, BorrowInfo borrowInfo) {

		// 插入时间
		int systemNowDateLong = GetDate.getNowTime10();
		Date systemNowDate = GetDate.getDate(systemNowDateLong);
		if (borrow != null && borrow.getId() != null) {
			// 剩余的金额
			borrow.setBorrowAccountWait(borrow.getAccount());
			int time = systemNowDateLong;

			// 是否可以进行借款
			borrow.setBorrowStatus(1);
			// 初审时间
			borrow.setVerifyTime(GetDate.getNowTime10());
			// 发标的状态
			borrow.setVerifyStatus(Integer.valueOf(4));
			// 状态
			borrow.setStatus(2);
			// 借款到期时间
			borrow.setBorrowEndTime(String.valueOf(time + borrowInfo.getBorrowValidTime() * 86400));
			// 更新时间
			borrow.setUpdatetime(systemNowDate);
			this.borrowMapper.updateByPrimaryKey(borrow);

			return true;

		}

		return false;
	}

	@Override
	public boolean updateRecordBorrow(HjhPlanAsset hjhPlanAsset, Borrow borrow) {
		return this.updateBorrowOfAudit(borrow) && this.updateAssetOfAudit(hjhPlanAsset);
	}

	/**
	 * 初审通过发标，更新borrow状态
	 *
	 * @param borrow
	 */
	private boolean updateBorrowOfAudit(Borrow borrow) {

		// 插入时间
		int systemNowDateLong = GetDate.getNowTime10();
		Date systemNowDate = GetDate.getDate(systemNowDateLong);

		if (borrow != null) {
			BorrowInfo borrowInfo = getBorrowInfoByNid(borrow.getBorrowNid());
			// 剩余的金额
			borrow.setBorrowAccountWait(borrow.getAccount());
			int time = systemNowDateLong;

			// 是否可以进行借款
			borrow.setBorrowStatus(1);
			// 初审时间
			borrow.setVerifyTime(GetDate.getNowTime10());
			// 发标的状态
			borrow.setVerifyStatus(Integer.valueOf(4));
			// 状态
			borrow.setStatus(2);
			// 借款到期时间
			borrow.setBorrowEndTime(
					String.valueOf(time + (borrowInfo == null ? 0 : borrowInfo.getBorrowValidTime() * 86400)));
			// 更新时间
			borrow.setUpdatetime(systemNowDate);
			this.borrowMapper.updateByPrimaryKeySelective(borrow);
			return true;
		}
		return false;
	}

	/**
	 * 初审通过发标，更新asset状态
	 *
	 * @param hjhPlanAsset
	 */
	private boolean updateAssetOfAudit(HjhPlanAsset hjhPlanAsset) {
		hjhPlanAsset.setStatus(7);// 出借中
		// 获取当前时间
		hjhPlanAsset.setUpdateTime(new Date());
		hjhPlanAsset.setUpdateUserId(1);
		return this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAsset) > 0 ? true : false;
	}
}
