package com.hyjf.cs.trade.client;

import java.util.List;

import com.hyjf.am.vo.trade.borrow.BorrowVO;

/**
 * 还款逾期短信提醒
 * @author jun 20180626
 *
 */
public interface HjhSmsNoticeServiceClient {

	List<BorrowVO> selectOverdueBorrowList();

}
