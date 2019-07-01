/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.customize.CustomizeMapper;
import com.hyjf.am.config.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2019/4/19 14:34
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
