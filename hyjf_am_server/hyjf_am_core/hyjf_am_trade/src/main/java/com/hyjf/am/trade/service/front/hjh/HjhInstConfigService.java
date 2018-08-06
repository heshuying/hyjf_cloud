/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.customize.web.RecentPaymentListCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version UserService, v0.1 2018/5/28 12:53
 */
public interface HjhInstConfigService {

    /**
     * 根据机构编号检索机构信息
     *
     * @param instCode
     * @return
     */
    HjhInstConfig selectInstConfigByInstCode(String instCode);

    /**
     * 根据机构编号查询机构列表
     * @return
     */
    List<HjhInstConfig> selectInstConfigAll();

    List<RecentPaymentListCustomize> selectRecentPaymentList(Map<String, Object> paraMap);
}
