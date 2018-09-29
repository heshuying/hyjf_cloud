/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountDirectionalTransferMapper;
import com.hyjf.am.trade.dao.model.auto.AccountDirectionalTransfer;
import com.hyjf.am.trade.dao.model.auto.AccountDirectionalTransferExample;
import com.hyjf.am.trade.service.admin.finance.AccountDirectionalTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferServiceImpl, v0.1 2018/7/4 16:56
 */
@Service(value = "tradeAccountDirectionalTransferServiceImpl")
public class AccountDirectionalTransferServiceImpl extends BaseServiceImpl implements AccountDirectionalTransferService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountDirectionalTransferMapper accountDirectionalTransferMapper;
    /**
     * 根据筛选条件 查询数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getDirectionalTransferCount(DirectionalTransferListRequest request) {
        AccountDirectionalTransferExample accountDirectionalTransferExample = convertExample(request);
        Integer count = accountDirectionalTransferMapper.countByExample(accountDirectionalTransferExample);
        return count;
    }

    @Override
    public List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request) {
        AccountDirectionalTransferExample example = convertExample(request);
        List<AccountDirectionalTransfer> accountDirectionalTransferList = accountDirectionalTransferMapper.selectByExample(example);
        List<AccountDirectionalTransferVO> accountDirectionalTransferVOList = CommonUtils.convertBeanList(accountDirectionalTransferList,AccountDirectionalTransferVO.class);
        return accountDirectionalTransferVOList;
    }

    /**
     * 公共方法，将request转成Example
     * @auth sunpeikai
     * @param
     * @return
     */
    private AccountDirectionalTransferExample convertExample(DirectionalTransferListRequest request){
        AccountDirectionalTransferExample accountDirectionalTransferExample = new AccountDirectionalTransferExample();
        AccountDirectionalTransferExample.Criteria criteria = accountDirectionalTransferExample.createCriteria();
        // 转出账户
        if(StringUtils.isNotEmpty(request.getTurnOutUsername())){
            criteria.andTurnOutUsernameLike("%" + request.getTurnOutUsername() + "%");
        }
        // 转入账户
        if(StringUtils.isNotEmpty(request.getShiftToUsername())){
            criteria.andShiftToUsernameLike("%" + request.getShiftToUsername() + "%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getStatusSearch())){
            criteria.andTransferAccountsStateEqualTo(Integer.valueOf(request.getStatusSearch()));
        }
        // 订单号
        if(StringUtils.isNotEmpty(request.getOrderId())){
            criteria.andOrderIdLike("%" + request.getOrderId() + "%");
        }
        // 转账开始时间
        if(StringUtils.isNotEmpty(request.getStartDate())) {
            criteria.andTransferAccountsTimeGreaterThanOrEqualTo(GetDate.stringToDate(request.getStartDate() + " 00:00:00"));
        }
        // 转账结束时间
        if(StringUtils.isNotEmpty(request.getEndDate())) {
            criteria.andTransferAccountsTimeLessThanOrEqualTo(GetDate.stringToDate(request.getEndDate() + " 23:59:59"));
        }
        // 分页条件
        if(request.getLimitStart() != -1) {
            accountDirectionalTransferExample.setLimitStart(request.getLimitStart());
            accountDirectionalTransferExample.setLimitEnd(request.getLimitEnd());
        }
        return accountDirectionalTransferExample;
    }
}
