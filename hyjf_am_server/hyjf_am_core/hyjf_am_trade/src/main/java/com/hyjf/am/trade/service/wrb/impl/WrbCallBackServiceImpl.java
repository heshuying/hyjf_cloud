package com.hyjf.am.trade.service.wrb.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowTenderCustomizeMapper;
import com.hyjf.am.trade.service.wrb.WrbCallBackService;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 14:18
 * @Description: WrbCallBackServiceImpl
 */
@Service
public class WrbCallBackServiceImpl implements WrbCallBackService {
    private static final Logger logger = LoggerFactory.getLogger(WrbCallBackServiceImpl.class);
    @Resource
    private BorrowTenderCustomizeMapper borrowTenderCustomizeMapper;

    @Override
    public WrbTenderNotifyCustomizeVO searchBorrowTenderByNid(String nid, Integer userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("nid",nid);
        paramMap.put("userId",userId);
        return borrowTenderCustomizeMapper.searchBorrowTenderByNid(paramMap);
    }
}
