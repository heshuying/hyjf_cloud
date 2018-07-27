/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.extensioncenter;

import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.vo.trade.OperationReportJobVO;

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
}
