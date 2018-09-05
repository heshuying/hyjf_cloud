/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;

import java.util.List;

/**
 * @author fq
 * @version FddTempletCustomizeMapper, v0.1 2018/8/7 10:09
 */
public interface FddTempletCustomizeMapper {
    /**
     * 获取协议列表
     * @param request
     * @return
     */
    List<FddTempletCustomize> selectFddTempletList(FddTempletCustomize request);
    /**
     * 查找不再合同模版约定条款表里的协议模板号
     * @return
     */
   List<FddTempletCustomize> selectContractTempId();

    /**
     *取得新规的模板编号
     * @param templetId
     * @return
     */
    String getMaxTempletId(Integer templetId);
}
