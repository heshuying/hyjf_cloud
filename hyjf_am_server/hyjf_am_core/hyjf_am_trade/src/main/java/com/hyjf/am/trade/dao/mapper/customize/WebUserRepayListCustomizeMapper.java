/**
 * Description:会员用户开户记录初始化列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */

package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.WebUserRepayTransferCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTransferBorrowInfoCustomize;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WebUserRepayListCustomizeMapper {
	/**  查询借款人借款列表  */
	List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params);
	/**  查询垫付机构借款列表   */
	List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params);

	/**
	 *
	 * @param borrowNid
	 * @return
	 * @Author : huanghui
	 */
	WebUserTransferBorrowInfoCustomize getBorrowInfo(String borrowNid);

	/**
	 * 用户待还债转详情列表总条数 非计划
	 * @param borrowNid
	 * @return
	 */
	int selectUserRepayTransferListTotalByCreditTender(String borrowNid);

	/**
	 * 用户待还债转详情列表总条数 计划
	 * @param borrowNid
	 * @return
	 */
	int selectUserRepayTransferListTotalByHjhCreditTender(String borrowNid);

	/**
	 * 用户待还债转详情列表 计划
	 * @param paraMap
	 * @return
	 */
	List<WebUserRepayTransferCustomize> selectUserRepayTransferListByHjhCreditTender(Map<String, Object> paraMap);

	/**
	 * 用户待还债转详情列表 非计划
	 * @param paraMap
	 * @return
	 */
	List<WebUserRepayTransferCustomize> selectUserRepayTransferListByCreditTender(Map<String, Object> paraMap);

	/**
	 * 查询借款人非分期待还管理服务费
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getWaitRepayManageFee(Integer userId);
	/**
	 * 查询借款人借款分期待还管理服务费
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getWaitRepayPlanManageFee(Integer userId);
	/**
	 * 查询垫付机构非分期待还管理服务费
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getOrgWaitRepayManageFee(Integer userId);
	/**
	 * 查询垫付机构分期待还管理服务费
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getOrgWaitRepayPlanManageFee(Integer userId);

	/**
	 * 查询借款人非分期待还逾期利息
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getWaitRepayLateInterest(Integer userId);
	/**
	 * 查询借款人借款分期待还逾期利息
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getWaitRepayPlanLateInterest(Integer userId);
	/**
	 * 查询垫付机构非分期待还逾期利息
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getOrgWaitRepayLateInterest(Integer userId);
	/**
	 * 查询垫付机构分期待还逾期利息
	 * @auther wgx
	 * @date 2018/11/10
	 */
	BigDecimal getOrgWaitRepayPlanLateInterest(Integer userId);

	Integer getFailCredit(String borrowNid);
}
