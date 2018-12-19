/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.extensioncenter;

import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.util.List;


/**
 * @author tanyy
 * @version OperationReportJobCountService.java, v0.1 2018年7月17日 下午3:04:29
 */
public interface OperationReportJobCountService extends BaseService {


	/**
	 * 通过时间统计平台注册人数
	 * @param
	 * @return
	 *
	 */
	int countRegistUser();

	/**
	 * 用户分析 - 性别分布拆分
	 *
	 * @param list 多个用户id
	 * @return
	 */
	List<OperationReportJobVO> getSexCount( List<OperationReportJobVO> list);

    /**
     * 用户分析 - 年龄分布拆分
     *
     * @param list 多个用户id
     * @return
     */
    List<OperationReportJobVO> getAgeCount( List<OperationReportJobVO> list);

	/**
	 * 获取用户名
	 *
	 * @param list 多个用户id
	 * @return
	 */
	List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list);
	/**
	 * 通过用户ID查询 用户年龄，用户地区
	 *
	 * @param userId 用户ID
	 * @return
	 */
	OperationReportJobVO getUserAgeAndArea(int userId);


	/**
	 * 出借人按照年龄分布
	 *
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	int getTenderAgeByRange(OperationReportJobRequest request);

	/**
	 * 按照性别统计出借人的分布
	 * @param request 上个月的最后一天
	 */
	List<OperationReportJobVO>  getTenderSexGroupBy(OperationReportJobRequest request);

	/**
	 * 按照省份统计出借人的分布  上个月的最后一天
	 *
	 * @param cityUserIds
	 */
	List<OperationReportJobVO> getTenderCityGroupByUserIds(List<OperationReportJobVO> cityUserIds);
}
