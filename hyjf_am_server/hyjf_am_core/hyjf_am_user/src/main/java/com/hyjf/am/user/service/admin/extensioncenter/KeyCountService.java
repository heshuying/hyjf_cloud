/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.extensioncenter;

import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.util.Date;
import java.util.List;


/**
 * @author tanyy
 * @version KeyCountService.java, v0.1 2018年7月17日 下午3:04:29
 */
public interface KeyCountService  {

	/**
	 * 根据条件查询列表
	 *
	 * @param request
	 * @return
	 */
	KeyCountResponse searchAction(KeyCountRequest request);

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
	 * 投资人按照年龄分布
	 *
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	int getTenderAgeByRange(OperationReportJobRequest request);

	/**
	 * 按照性别统计投资人的分布
	 * @param request 上个月的最后一天
	 */
	List<OperationReportJobVO>  getTenderSexGroupBy(OperationReportJobRequest request);
}
