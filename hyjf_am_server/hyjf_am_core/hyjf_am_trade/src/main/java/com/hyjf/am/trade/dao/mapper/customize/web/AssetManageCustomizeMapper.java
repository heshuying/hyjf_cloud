/**
 * Description:汇转让WEB接口DAO
 * Copyright: Copyright (HYJF Corporation) 2016
 * Company: HYJF Corporation
 * @author: 朱晓东
 * @version: 1.0
 * Created at: 2015年03月24日 下午18:35:00
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.mapper.customize.web;

import com.hyjf.am.trade.dao.model.customize.web.RecentPaymentListCustomize;

import java.util.List;
import java.util.Map;


public interface AssetManageCustomizeMapper {

    /**
     *
     * 获取近期回款列表
     * @author pcc
     * @param paraMap
     * @return
     */
    List<RecentPaymentListCustomize> selectRecentPaymentList(Map<String, Object> paraMap);
}
