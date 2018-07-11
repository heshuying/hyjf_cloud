/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.BorrowInvestCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowInvestCustomize;
import com.hyjf.am.trade.service.admin.BorrowInvestService;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowInvestServiceImpl, v0.1 2018/7/10 9:35
 */
@Service
public class BorrowInvestServiceImpl implements BorrowInvestService {
    @Autowired
    BorrowInvestCustomizeMapper borrowInvestCustomizeMapper;
    /**
     * 投资明细记录 总数COUNT
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public int countBorrowFirst(BorrowInvestRequest borrowInvestRequest){
        return borrowInvestCustomizeMapper.countBorrowInvest(borrowInvestRequest);
    }

    /**
     * 投资明细列表
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest){
        List<BorrowInvestCustomize> list = borrowInvestCustomizeMapper.selectBorrowInvestList(borrowInvestRequest);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> clientMap = CacheUtil.getParamNameMap("hyjf_param_name:CLIENT");
            Map<String, String> propertyMap = CacheUtil.getParamNameMap("hyjf_param_name:USER_PROPERTY");
            Map<String, String> investMap = CacheUtil.getParamNameMap("hyjf_param_name:INVEST_TYPE");
            for (BorrowInvestCustomize borrowInvestCustomize : list) {
                borrowInvestCustomize.setOperatingDeck(clientMap.getOrDefault(borrowInvestCustomize.getOperatingDeck(), null));
                borrowInvestCustomize.setTenderUserAttribute(propertyMap.getOrDefault(borrowInvestCustomize.getTenderUserAttribute(),null));
                borrowInvestCustomize.setInvestType(investMap.getOrDefault(borrowInvestCustomize.getInvestType(),null));
            }
        }
        return list;
    }

    /**
     * 投资明细列表合计
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest){
        return borrowInvestCustomizeMapper.selectBorrowInvestAccount(borrowInvestRequest);
    }

    /**
     * 导出投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomize> exportBorrowInvestList(BorrowInvestRequest borrowInvestRequest){
        return borrowInvestCustomizeMapper.exportBorrowInvestList(borrowInvestRequest);
    }

    /**
     * 投资金额合计值取得
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public String sumBorrowInvest(BorrowInvestRequest borrowInvestRequest){
        return borrowInvestCustomizeMapper.sumBorrowInvest(borrowInvestRequest);
    }
}
