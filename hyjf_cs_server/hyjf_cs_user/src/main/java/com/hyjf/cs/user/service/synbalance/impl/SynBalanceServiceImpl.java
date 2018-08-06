/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.synbalance.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.SynBalanceRequestBean;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version SynBalanceServiceImpl, v0.1 2018/7/25 15:11
 */
@Service
public class SynBalanceServiceImpl extends BaseUserServiceImpl implements SynBalanceService {

    protected static final Logger logger = LoggerFactory.getLogger(SynBalanceServiceImpl.class);

}
