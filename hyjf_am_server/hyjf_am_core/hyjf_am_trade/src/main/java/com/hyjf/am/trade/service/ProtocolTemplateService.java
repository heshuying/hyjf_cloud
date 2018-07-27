/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.List;

import com.hyjf.am.vo.trade.ProtocolTemplateVO;

/**
 * @author libin
 * @version ProtocolTemplateService.java, v0.1 2018年7月26日 下午5:11:19
 */
public interface ProtocolTemplateService {
	
    /**
     * 查询协议模板
     * @date 2018/7/4 15:38
     */
	List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName);
}
