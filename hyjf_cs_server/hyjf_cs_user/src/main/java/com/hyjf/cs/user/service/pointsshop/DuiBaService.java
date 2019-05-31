/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pointsshop;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjun
 * @version DuiBaService, v0.1 2019/5/29 16:36
 */
public interface DuiBaService {
    JSONObject getUrl(HttpServletRequest request);
}
