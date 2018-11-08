/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.wrb.impl;

import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderMapper;
import com.hyjf.am.trade.dao.mapper.customize.WrbQueryCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderExample;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderSumCustomize;
import com.hyjf.am.trade.service.api.wrb.WrbInfoService;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version WrbInfoServiceImpl, v0.1 2018/9/25 11:12
 */
@Service
public class WrbInfoServiceImpl implements WrbInfoService {
    @Resource
    private WrbQueryCustomizeMapper wrbQueryCustomizeMapper;
    @Resource
    private BorrowTenderMapper borrowTenderMapper;

    @Override
    public List<WrbBorrowListCustomize> borrowList(String borrowNid)  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowClass", "");

        // 定向标过滤
        params.put("publishInstCode", "");
        if (StringUtils.isNotBlank(borrowNid)) {
            params.put("borrowNid", borrowNid);
        }else{
            params.put("projectType", "HZT");
            params.put("status", 2);// 获取 投资中
        }
        List<WrbBorrowListCustomize> customizeList = wrbQueryCustomizeMapper.searchBorrowListByNid(params);
        for (WrbBorrowListCustomize wrbBorrowListCustomize : customizeList) {
            String url = wrbBorrowListCustomize.getInvest_url();
            wrbBorrowListCustomize.setInvest_url(url);
        }
        return customizeList;
    }

    @Override
    public List<BorrowTender> getBorrowTenderList(WrbInvestRequest request) {
        Date date = request.getDate();
        Integer limit = request.getLimit();
        Integer page = request.getPage();
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        // 查询开始条数
        Integer limitStart = (page-1) * limit;
        // 查询结束条数
        Integer limitEnd = limit;
        criteria.andCreateTimeBetween(GetDate.getSomeDayStart(date), GetDate.getSomeDayEnd(date));
        example.setLimitStart(limitStart);
        example.setLimitEnd(limitEnd);
        example.setOrderByClause("create_time desc");
        List<BorrowTender> borrowTenderList = borrowTenderMapper.selectByExample(example);

        return borrowTenderList;
    }

    @Override
    public List<WrbBorrowTenderCustomize> getBorrowTenderByBorrowNid(WrbInvestRequest request) {
        return wrbQueryCustomizeMapper.selectWrbBorrowTender(request.getBorrowNid(), request.getDate());
    }

    @Override
    public WrbBorrowTenderSumCustomize getBorrowTenderByBorrowNidAndTime(WrbInvestRequest request) {
        return wrbQueryCustomizeMapper.getBorrowTenderByBorrowNidAndTime(request.getBorrowNid(), request.getDate());
    }


}
