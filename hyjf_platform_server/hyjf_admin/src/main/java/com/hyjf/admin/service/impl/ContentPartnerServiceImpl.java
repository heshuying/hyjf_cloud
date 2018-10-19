/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ContentPartnerRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ContentPartnerService;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version ContentPartnerServiceImpl, v0.1 2018/7/12 9:54
 */
@Service
public class ContentPartnerServiceImpl implements ContentPartnerService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public LinkResponse searchAction(ContentPartnerRequestBean requestBean) {
		return amConfigClient.searchAction(requestBean);
	}

	@Override
	public int insertAction(ContentPartnerRequestBean requestBean) {
		requestBean.setType(2);// 合作伙伴
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public int updateAction(ContentPartnerRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int updateStatus(ContentPartnerRequestBean requestBean) {
		Integer id = requestBean.getId();
		LinkVO record = amConfigClient.getLinkRecord(id);
		ContentPartnerRequestBean bean = new ContentPartnerRequestBean();
		BeanUtils.copyProperties(record, bean);
		bean.setStatus(requestBean.getStatus());
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int deleteById(Integer id) {
		return amConfigClient.deleteLinkById(id);
	}

	@Override
	public LinkVO selectById(ContentPartnerRequestBean requestBean) {
		return amConfigClient.getLinkRecord(requestBean.getId());
	}
}
