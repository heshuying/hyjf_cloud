/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.config.STZHWhiteListRequestBean;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * 受托支付白名单
 * 
 * @author fuqiang
 * @version StzfWhiteConfigService, v0.1 2018/7/10 9:20
 */
public interface StzfWhiteConfigService {

	/**
	 * 受托支付白名单列表
	 *
	 * @return
	 */
	STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 新增受托支付白名单
	 *
	 * @param requestBean
	 * @return
	 */
	STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 修改受托支付白名单
	 *
	 * @param requestBean
	 * @return
	 */
	STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 获取机构信息
	 *
	 * @param instcode
	 * @return
	 */
	HjhInstConfigVO selectHjhInstConfig(String instcode);

	/**
	 * 根据id查询白名单详情
	 * @param id
	 * @return
	 */
    STZHWhiteListResponse selectSTZHWhiteById(Integer id);

	/**
	 * 获取机构列表
	 * @return
	 */
	List<HjhInstConfigVO> getRegionList();

	/**
	 * 加载用户/企业信息
	 * @param requestBean
	 * @return
	 */
    STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean);
}
