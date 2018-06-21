/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import java.util.List;

/**
 * @author zhangqingqing
 * @version UserService, v0.1 2018/5/28 12:53
 */
public interface UserService {

    /**
     * 根据机构编号检索机构信息
     *
     * @param instCode
     * @return
     */
    HjhInstConfig selectInstConfigByInstCode(String instCode);

    /**
     * 根据机构编号查询机构列表
     * @param instCode
     * @return
     */
    List<HjhInstConfig> selectInstConfigListByInstCode(String instCode);
}
