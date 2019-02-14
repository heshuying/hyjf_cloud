/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountExceptionMapper;
import com.hyjf.am.trade.dao.model.auto.AccountException;
import com.hyjf.am.trade.dao.model.auto.AccountExceptionExample;
import com.hyjf.am.trade.service.admin.exception.AccountRepairService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AccountExceptionVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountRepairServiceImpl, v0.1 2018/7/11 15:22
 */
@Service(value = "tradeAccountRepairServiceImpl")
public class AccountRepairServiceImpl extends BaseServiceImpl implements AccountRepairService {

    @Autowired
    private AccountExceptionMapper accountExceptionMapper;

    /**
     * 根据筛选条件查询汇付对账count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getAccountExceptionCount(AccountExceptionRequest request) {
        AccountExceptionExample example = convertExample(request);
        return accountExceptionMapper.countByExample(example);
    }

    /**
     * 根据筛选条件查询汇付对账列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<AccountException> searchAccountExceptionList(AccountExceptionRequest request) {
        AccountExceptionExample example = convertExample(request);
        return accountExceptionMapper.selectByExample(example);
    }

    /**
     * 根据id查询汇付对账异常信息
     * @auth sunpeikai
     * @param id
     * @return
     */
    @Override
    public AccountException searchAccountExceptionById(Integer id) {
        return accountExceptionMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新汇付对账异常
     * @auth sunpeikai
     * @param accountExceptionVO 更新参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateAccountException(AccountExceptionVO accountExceptionVO) {
        AccountException accountException = CommonUtils.convertBean(accountExceptionVO,AccountException.class);
        return accountExceptionMapper.updateByPrimaryKeySelective(accountException);
    }

    /**
     * 删除汇付对账异常
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteAccountExceptionById(Integer id) {
        return accountExceptionMapper.deleteByPrimaryKey(id);
    }

    private AccountExceptionExample convertExample(AccountExceptionRequest request){
        AccountExceptionExample exceptionExample = new AccountExceptionExample();
        AccountExceptionExample.Criteria criteria = exceptionExample.createCriteria();
        if (null != request.getCustomId()) {
            criteria.andCustomIdEqualTo(request.getCustomId());
        }
        if(StringUtils.isNotBlank(request.getUsername())){
            criteria.andUsernameLike("%"+request.getUsername()+"%");
        }
        if(StringUtils.isNotBlank(request.getMobile())){
            criteria.andMobileLike("%"+request.getMobile()+"%");
        }
        if (request.getLimitStart() != -1) {
            exceptionExample.setLimitStart(request.getLimitStart());
            exceptionExample.setLimitEnd(request.getLimitEnd());
        }
        return exceptionExample;
    }
}
