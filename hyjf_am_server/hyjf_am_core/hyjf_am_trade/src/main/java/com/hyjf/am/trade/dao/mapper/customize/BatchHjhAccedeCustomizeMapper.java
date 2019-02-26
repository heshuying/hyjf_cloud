package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.customize.HjhAccedeCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BatchHjhAccedeCustomizeMapper {
	/**
	 * 取得汇计划自动出借的计划订单列表
	 * @param paramMap
	 * @return
	 */
	List<HjhAccedeCustomize> selectHjhAutoTenderList(Map<String,Object> paramMap);

	/**
	 *
	 * 计划放款后计划订单冻结金额变化
	 * @param accede
	 * @return
	 */
	int updateOfPlanLoansTender(HjhAccede accede);

	/**
	 *
	 * 更新待收收益
	 * @param hjhAccede
	 * @return
	 */
	int updateInterest(HjhAccede hjhAccede);

	// add 汇计划三期 计算匹配期处理 liubin 20180515 start
	/**
	 * 更新计算匹配期
	 * @return
	 */
	int updateMatchDates();
	// add 汇计划三期 计算匹配期处理 liubin 20180515 end

	// 计算匹配期处理 潜在问题修复 huanghui start
//	int updateMatchDatesTwo(@Param("start") Integer start, @Param("end") Integer end);
	int updateMatchDatesTwo(@Param("start") Integer start);

    // 计算匹配期处理 潜在问题修复 huanghui end
}