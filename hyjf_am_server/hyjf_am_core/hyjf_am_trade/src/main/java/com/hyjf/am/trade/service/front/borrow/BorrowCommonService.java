package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.trade.bean.BorrowWithBLOBs;
import com.hyjf.am.trade.service.BaseService;

public interface BorrowCommonService extends BaseService {

	/**
	 * 借款人信息数据获取
	 * 
	 * @param borrowBean
	 */
	void getBorrowManinfo(BorrowCommonBean borrowBean);

	/**
	 * 车辆信息数据获取
	 * 
	 * @param borrowBean
	 */
	void getBorrowCarinfo(BorrowCommonBean borrowBean);

	/**
	 * 用户信息数据获取
	 * 
	 * @param borrowBean
	 */
	void getBorrowUsers(BorrowCommonBean borrowBean);

	/**
	 * 获取借款预编号
	 * 
	 * @return
	 */
	String getBorrowPreNid();

	/**
	 * 根据项目编号获取借款信息
	 * 
	 * @param borrowNid
	 * @return
	 */
	BorrowWithBLOBs getBorrowWithBLOBs(String borrowNid);

	/**
	 * 项目类型
	 * 
	 * @return
	 * @author Administrator
	 */
	String getBorrowProjectClass(String borrowCd);

	/**
	 * 获取系统配置
	 *
	 * @return
	 */
	@Override
	String getBorrowConfig(String configCd);
}
