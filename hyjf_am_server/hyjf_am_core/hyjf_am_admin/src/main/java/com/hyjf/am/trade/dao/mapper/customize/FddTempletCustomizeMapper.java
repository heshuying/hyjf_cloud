/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;

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
}
