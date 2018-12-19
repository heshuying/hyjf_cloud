package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.IdCardCustomize;
import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.util.List;


public interface IdCardCustomizeMapper {


	/**
	 * 统一获取idcard接口
	 *
	 * @param idCardCustomize
	 * @return
	 */
	IdCardCustomize getIdCardCustomize (IdCardCustomize idCardCustomize);

	List<IdCardCustomize> getIdCardList();

	/**
	 * 按照省份统计出借人的分布  上个月的最后一天
	 *
	 * @param bms
	 */
	List<OperationReportJobVO> getTenderCityGroupBy(List<OperationReportJobVO> bms);
}