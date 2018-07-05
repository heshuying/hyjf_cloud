/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.response.admin.AccountDirectionalTransferResponse;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountDirectionalTransferMapper;
import com.hyjf.am.trade.dao.model.auto.AccountDirectionalTransfer;
import com.hyjf.am.trade.dao.model.auto.AccountDirectionalTransferExample;
import com.hyjf.am.trade.service.admin.finance.AccountDirectionalTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferServiceImpl, v0.1 2018/7/4 16:56
 */
@Service
public class AccountDirectionalTransferServiceImpl extends BaseServiceImpl implements AccountDirectionalTransferService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountDirectionalTransferMapper accountDirectionalTransferMapper;
    /**
     * 根据筛选条件 查询数据count
     * @auth sunpeikai
     * @param
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
        example.setLimitStart(request.getLimitStart());
        example.setLimitEnd(request.getLimitEnd());
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
        if(null != request.getTurnOutUsername()){
            criteria.andTurnOutUsernameEqualTo(request.getTurnOutUsername());
        }
        if(null != request.getShiftToUsername()){
            criteria.andShiftToUsernameEqualTo(request.getShiftToUsername());
        }
        if(null != request.getStatusSearch()){
            if("转账中".equals(request.getStatusSearch())){
                criteria.andTransferAccountsStateEqualTo(0);
            }else if("成功".equals(request.getStatusSearch())){
                criteria.andTransferAccountsStateEqualTo(1);
            }else if("失败".equals(request.getStatusSearch())){
                criteria.andTransferAccountsStateEqualTo(2);
            }
        }
        if(null != request.getOrderId()){
            criteria.andOrderIdEqualTo(request.getOrderId());
        }
        Date startTime = new Date(0);
        Date endTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(StringUtils.isNotEmpty(request.getStartDate())){
                startTime = simpleDateFormat.parse(request.getStartDate());
            }
            if(StringUtils.isNotEmpty(request.getEndDate())){
                endTime = simpleDateFormat.parse(request.getEndDate());
            }
        }catch (ParseException e) {
            logger.info("日期格式化异常");
        }
        criteria.andTransferAccountsTimeBetween(startTime,endTime);
        return accountDirectionalTransferExample;
    }
}
