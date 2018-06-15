/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.BatchTyjRepayCustomizeMapper;
import com.hyjf.am.trade.service.CouponRepayBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRepayBatchServiceImpl, v0.1 2018/6/15 17:33
 */
@Service
public class CouponRepayBatchServiceImpl implements CouponRepayBatchService {

    private static final Logger logger = LoggerFactory.getLogger(CouponRepayBatchServiceImpl.class);

    @Autowired
    private BatchTyjRepayCustomizeMapper batchTyjRepayCustomizeMapper;

    @Override
    public List<String> selectNidForCouponOnly() {
        logger.info("筛选是否有优惠券单独投资需还款");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<String> recoverNidList = this.batchTyjRepayCustomizeMapper.selectNidForTyj(paramMap);
        if (!CollectionUtils.isEmpty(recoverNidList)) {
            logger.info("有" + recoverNidList.size() + "条按优惠券单独投资需还款");
            return recoverNidList;
        }
        logger.info("没有优惠券单独投资需还款");
        return null;
    }
}
