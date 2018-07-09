/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.trade.service.admin.finance.PlatformTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 11:11
 */
@Service
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        Integer count = accountRechargeMapper.countByExample(example);
        return count;
    }

    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public List<AccountRecharge> searchPlatformTransferList(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        List<AccountRecharge> accountRechargeList = accountRechargeMapper.selectByExample(example);
        return accountRechargeList;
    }

    private AccountRechargeExample convertExample(PlatformTransferListRequest request){
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria criteria = example.createCriteria();
        // 用户名
        if(StringUtils.isNotEmpty(request.getUsernameSearch())){
            criteria.andUsernameLike("%"+request.getUsernameSearch()+"%");
        }
        // 订单号
        if(StringUtils.isNotEmpty(request.getNidSearch())){
            criteria.andNidLike("%"+request.getNidSearch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getStatusSearch())){
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatusSearch()));
        }
        // 添加时间开始
        if(StringUtils.isNotEmpty(request.getStartDate())){
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getStartDate())));
        }
        //添加时间结束
        if(StringUtils.isNotEmpty(request.getEndDate())){
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayEnd(request.getEndDate())));
        }
        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }
}
