package com.hyjf.am.trade.dao.mapper.customize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DataCustomizeMapper {
	/**
	 * @method: selectDataInfo
	 * @description: 查询平台数据
	 * @return: List<Map<String,Object>>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年5月20日 上午10:31:36
	 */
	Map<String, Object> selectDataInfo(String day);

	/**
	 * @return
	 * @method: selectDataTenderInfo
	 * @description: 分组数据查询投标金额
	 * @return: List<Map<String,Object>>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年5月20日 上午9:47:05
	 */
	List<Map<String, Object>> selectDataTenderInfo();

	/**
	 * @return
	 * @method: selectDataCreditInfo
	 * @description: 分组数据查询债权投标金额
	 * @return: List<Map<String,Object>>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年5月20日 上午9:47:36
	 */
	List<Map<String, Object>> selectDataCreditInfo();

	/**
	 * @return
	 * @method: selectPeriodInfo
	 * @description: 融资期限分布
	 * @return: Map<String,Object>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年5月23日 上午9:00:21
	 */
	Map<String, Object> selectPeriodInfo();

	/**
	 * @return
	 * @method: selectTendMoneyInfo
	 * @description: 投资金额占比
	 * @return: Map<String,Object>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年5月23日 上午9:29:07
	 */
	Map<String, Object> selectTendMoneyInfo();

	/**
	 * @return
	 * @method: selectData
	 * @description: 投资相关统计信息
	 * @return: Map<String,Object>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年7月8日 上午11:53:12
	 */
	Map<String, Object> selectData();

	/**
	 * @param type 0投资 1债转投资
	 * @return
	 * @method: selectTenderListMap
	 * @description: 搜索月投资金额
	 * @return: List<Map<String,Object>>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年7月8日 下午12:04:25
	 */
	List<Map<String, Object>> selectTenderListMap(int type);

	/**
	 * @method: insertHyjfTenderMonthInfo
	 * @description: 插入上月投资金额
	 * @return: void
	 * @mender: zhouxiaoshuai
	 * @date: 2016年7月8日 下午3:33:25
	 */
	void insertHyjfTenderMonthInfo();

	/**
	 * @return
	 * @method: selectDebtBorrowWaitMoney
	 * @description: 专属标待投资资产
	 * @return: BigDecimal
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月10日 上午11:28:00
	 */
	BigDecimal selectDebtBorrowWaitMoney();

	/**
	 * @return
	 * @method: selectDebtCreditWaitMoney
	 * @description: 债权待承接的金额
	 * @return: BigDecimal
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月10日 下午1:43:55
	 */
	BigDecimal selectDebtCreditWaitMoney();

	/**
	 * @param orderType 0专属标投资 1债权
	 * @return
	 * @method: selectDebtDetailYesInvestCount
	 * @description: 计划持有债权数量
	 * @return: Integer
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月10日 下午1:55:43
	 */
	Long selectDebtDetailYesCount(Integer orderType);

	/**
	 * @param repayStatus 还款状态 0未还款 1已还款
	 * @return
	 * @method: selectDebtDetailRepayMoney
	 * @description:
	 * @return: BigDecimal
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月10日 下午2:19:44
	 */
	BigDecimal selectDebtDetailRepayMoney(Integer repayStatus);

	/**
	 * @return
	 * @method: selectDebtBorrowPeriodInfo
	 * @description: 待成交专属资产期限分布
	 * @return: Map<String,Object>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月10日 下午2:50:27
	 */
	Map<String, Object> selectDebtBorrowPeriodInfo();

	/**
	 * @return
	 * @method: selectDebtCreditPeriodInfo
	 * @description: 待成交债权转让-期限分布
	 * @return: Map<String,Object>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月10日 下午2:50:34
	 */
	Map<String, Object> selectDebtCreditPeriodInfo();

	/**
	 * 检索计划应还款总额
	 *
	 * @param repayStatus
	 * @return
	 */
	BigDecimal selectPlanRepayWait();

	/**
	 * 检索计划已还款总额
	 *
	 * @return
	 */
	BigDecimal selectPlanRepayYes();

	/**
	 * 检索计划加入总额
	 *
	 * @return
	 */
	BigDecimal selectPlanAccedeAll();

	/**
	 * @return
	 * @method: selectPlanInfoStaticCount
	 * @description: 查询是否有初始数据
	 * @return: Integer
	 * @mender: zhouxiaoshuai
	 * @date: 2016年12月15日 下午1:52:23
	 */
	Integer selectPlanInfoStaticCount();

	/**
	 * @method: insertPlanInfoStaticCount
	 * @description: 插入planinfostaticcount
	 * @return: void
	 * @mender: zhouxiaoshuai
	 * @date: 2016年12月15日 下午1:53:28
	 */
	void insertPlanInfoStaticCount();

	/**
	 * @method: detelePlanInfoStaticCount
	 * @description: 删除
	 * @return: void
	 * @mender: zhouxiaoshuai
	 * @date: 2016年12月15日 下午3:23:52
	 */
	void detelePlanInfoStaticCount();

	/**
	 * 计算公允价值
	 *
	 * @return
	 */
	BigDecimal selectPlanExpireFairValue();

	/**
	 * 汇直投交易总数
	 * @return
	 */
	Integer selectHztTenderCount();

	/**
	 * 汇添金交易总数
	 * @return
	 */
	Integer selectHtjTenderCount();

	/**
	 * 汇转让交易总数
	 * @return
	 */
	Integer selectHzrTenderCount();

	/**
	 * 平台累计注册人数
	 * @return
	 */
	Integer selectUserRegistCount();

	/**
	 * 平台累计注册人数,包含未开户
	 * @return
	 */
	Integer selectAllUserRegistCount();

	/**
	 * 计算平台待还金额
	 * @return
	 */
	BigDecimal selectRepayTotal();
}
