/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.client.PlatformCountClient;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;
import com.hyjf.am.vo.admin.PlatformUserCountCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountClientImpl, v0.1 2018/8/9 15:09
 */
@Service
public class PlatformCountClientImpl implements PlatformCountClient {
    @Autowired
    private RestTemplate restTemplate;

	@Override
	public PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean) {
		// 获取投资信息
		PlatformCountCustomizeResponse response = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/platform_count/search_action", requestBean,
				PlatformCountCustomizeResponse.class);
		List<PlatformCountCustomizeVO> resultList = response.getResultList();
		// 获取用户相关信息
		PlatformUserCountCustomizeResponse userResponse = restTemplate.postForObject(
				"http://AM-USER/am-user/platform_count/get_info", requestBean, PlatformUserCountCustomizeResponse.class);
		List<PlatformUserCountCustomizeVO> userList = userResponse.getResultList();
		if (!CollectionUtils.isEmpty(resultList) && !CollectionUtils.isEmpty(userList)) {
			for (PlatformCountCustomizeVO vo : resultList) {
				String sourceName = vo.getSourceName();
				for (PlatformUserCountCustomizeVO userVo : userList) {
					if (sourceName.equals(userVo.getClient())) {
						vo.setRegistNumber(userVo.getRegistNumber().toString());
						vo.setAccessNumber(userVo.getAccountNumber().toString());
						break;
					}
				}
				// 修改客户端
				switch (sourceName) {
					case "0":
						vo.setSourceName("PC");
						break;
					case "1":
						vo.setSourceName("微信");
						break;
					case "2":
						vo.setSourceName("安卓");
						break;
					case "3":
						vo.setSourceName("IOS");
						break;
					case "4":
						vo.setSourceName("其他");
						break;
				}
			}
		}
		return response;
	}
}
