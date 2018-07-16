/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.ContentLinksRequestBean;
import com.hyjf.admin.client.ContentLinsClinet;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;

/**
 * @author fuqiang
 * @version ContentLinsClinetImpl, v0.1 2018/7/14 14:06
 */
@Service
public class ContentLinsClinetImpl implements ContentLinsClinet {
    @Autowired
    private RestTemplate restTemplate;

	@Override
	public LinkResponse searchAction(ContentLinksRequestBean requestBean) {
		return restTemplate.postForObject("http://hyjf-admin/content/contentlinks/searchaction", requestBean,
				LinkResponse.class);
	}

	@Override
	public LinkResponse insertAction(ContentLinksRequestBean requestBean) {
		return restTemplate.postForObject("http://hyjf-admin/content/contentlinks/insertaction", requestBean,
				LinkResponse.class);
	}

	@Override
	public LinkResponse updateAction(ContentLinksRequestBean requestBean) {
		return restTemplate.postForObject("http://hyjf-admin/content/contentlinks/updateaction", requestBean,
				LinkResponse.class);
	}

	@Override
	public LinkVO getRecord(Integer id) {
		LinkResponse response = restTemplate.getForObject("http://hyjf-admin/content/contentlinks/getrecord/" + id,
				LinkResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public LinkResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://hyjf-admin/content/contentlinks/delete/" + id, LinkResponse.class);
	}
}
