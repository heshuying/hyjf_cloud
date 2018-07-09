/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.resquest.admin.TransferListRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.UserTransferMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.finance.CustomerTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.ThreeDESUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 10:17
 */
@Service
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserTransferMapper userTransferMapper;

    @Value("${hyjf.web.transfer.url}")
    private String URL;
    @Value("${hyjf.transfer.3des.key}")
    private String KEY;

    /**
     * 根据筛选条件查询UserTransfer数据总数
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getUserTransferCount(CustomerTransferListRequest request) {
        UserTransferExample example = convertExample(request);
        Integer count = userTransferMapper.countByExample(example);
        return count;
    }

    /**
     * 根据筛选条件查询UserTransfer列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<UserTransfer> searchUserTransferList(CustomerTransferListRequest request) {
        UserTransferExample example = convertExample(request);
        List<UserTransfer> userTransferList = userTransferMapper.selectByExample(example);
        return userTransferList;
    }

    /**
     * 筛选条件转example
     * @auth sunpeikai
     * @param
     * @return
     */
    private UserTransferExample convertExample(CustomerTransferListRequest request){
        UserTransferExample example = new UserTransferExample();
        UserTransferExample.Criteria criteria = example.createCriteria();

        // 订单号
        if(StringUtils.isNotEmpty(request.getOrderIdSrch())){
            criteria.andOrderIdEqualTo(request.getOrderIdSrch());
        }
        // 交易类型
        if(StringUtils.isNotEmpty(request.getTransferTypeSrch())){
            criteria.andTransferTypeEqualTo(Integer.valueOf(request.getTransferTypeSrch()));
        }
        // 转出账户
        if(StringUtils.isNotEmpty(request.getOutUserNameSrch())){
            criteria.andOutUserNameLike("%"+request.getOutUserNameSrch()+"%");
        }
        // 对账标识
        if(StringUtils.isNotEmpty(request.getReconciliationIdSrch())){
            criteria.andReconciliationIdLike("%"+request.getReconciliationIdSrch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getStatusSrch())){
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatusSrch()));
        }
        // 转出时间开始
        if(StringUtils.isNotEmpty(request.getTransferTimeStart())){
            criteria.andTransferTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getTransferTimeStart())));
        }
        //转出时间结束
        if(StringUtils.isNotEmpty(request.getTransferTimeEnd())){
            criteria.andTransferTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(request.getTransferTimeEnd())));
        }
        if(StringUtils.isNotEmpty(request.getOpreateTimeStart())){
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getOpreateTimeStart())));
        }
        if(StringUtils.isNotEmpty(request.getOpreateTimeEnd())){
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(request.getOpreateTimeEnd())));
        }
        if(StringUtils.isNotEmpty(request.getTransferTypeSrch())){
            criteria.andTransferTypeEqualTo(Integer.valueOf(request.getTransferTypeSrch()));
        }

        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return example;
    }

    /**
     * 根据userId查询账户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<Account> searchAccountByUserId(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> accountList = accountMapper.selectByExample(example);
        return accountList;
    }
    /**
     * 向ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 发起转账的参数
     * @return
     */
    @Override
    public Boolean insertUserTransfer(CustomerTransferRequest request) {
        Date nowTime = new Date();
        UserTransfer userTransfer = new UserTransfer();
        // 生成订单
        String orderId = GetOrderIdUtils.getOrderId2(request.getOutUserId());
        userTransfer.setOrderId(orderId);
        userTransfer.setOutUserId(request.getOutUserId());
        userTransfer.setOutUserName(request.getOutUserName());
        userTransfer.setTransferAmount(request.getTransferAmount());
        userTransfer.setTransferType(0);
        userTransfer.setStatus(0);
        userTransfer.setRemark(request.getRemark());
        userTransfer.setCreateUserId(request.getCreateUserId());
        userTransfer.setCreateTime(nowTime);
        userTransfer.setCreateUserName(request.getCreateUserName());
        // 转账url
        String transferUrl = "";
        try {
            transferUrl = URL + "?orderId="
                    + ThreeDESUtils.Encrypt3DES(KEY, userTransfer.getOrderId());
            userTransfer.setTransferUrl(transferUrl);
            boolean flag = this.userTransferMapper.insertSelective(userTransfer) > 0;
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据主键id查询userTransfer
     * @auth sunpeikai
     * @param id ht_user_transfer表的主键id
     * @return
     */
    @Override
    public UserTransfer searchUserTransferById(Integer id) {
        return userTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getRecordCount(TransferListRequest request) {
        UserTransferExample example = new UserTransferExample();
        UserTransferExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(request.getOutUserNameSrch())){
            cra.andOutUserNameLike("%"+request.getOutUserNameSrch()+"%");
        }
        if(StringUtils.isNotEmpty(request.getReconciliationIdSrch())){
            cra.andReconciliationIdLike("%"+request.getReconciliationIdSrch()+"%");
        }
        if(StringUtils.isNotEmpty(request.getStatusSrch())){
            cra.andStatusEqualTo(Integer.valueOf(request.getStatusSrch()));
        }
        if(StringUtils.isNotEmpty(request.getOrderIdSrch())){
            cra.andOrderIdEqualTo(request.getOrderIdSrch());
        }
        if(StringUtils.isNotEmpty(request.getOpreateTimeStart())){
            cra.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getOpreateTimeStart())));
        }
        if(StringUtils.isNotEmpty(request.getOpreateTimeEnd())){
            cra.andCreateTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(request.getOpreateTimeEnd())));
        }
        if(StringUtils.isNotEmpty(request.getTransferTimeStart())){
            cra.andTransferTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getTransferTimeStart())));
        }
        if(StringUtils.isNotEmpty(request.getTransferTimeEnd())){
            cra.andTransferTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(request.getTransferTimeEnd())));
        }
        if(StringUtils.isNotEmpty(request.getReconciliationId())){
            cra.andReconciliationIdEqualTo(request.getReconciliationId());
        }
        if(StringUtils.isNotEmpty(request.getTransferTypeSrch())){
            cra.andTransferTypeEqualTo(Integer.valueOf(request.getTransferTypeSrch()));
        }

        example.setOrderByClause("create_time desc");
        return userTransferMapper.countByExample(example);
    }

    @Override
    public List<UserTransfer> selectRecordList(TransferListRequest request, int offset, int limit) {
        UserTransferExample example = new UserTransferExample();
        UserTransferExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(request.getOutUserNameSrch())){
            cra.andOutUserNameLike("%"+request.getOutUserNameSrch()+"%");
        }
        if(StringUtils.isNotEmpty(request.getReconciliationIdSrch())){
            cra.andReconciliationIdLike("%"+request.getReconciliationIdSrch()+"%");
        }
        if(StringUtils.isNotEmpty(request.getStatusSrch())){
            cra.andStatusEqualTo(Integer.valueOf(request.getStatusSrch()));
        }
        if(StringUtils.isNotEmpty(request.getOrderIdSrch())){
            cra.andOrderIdEqualTo(request.getOrderIdSrch());
        }
        if(StringUtils.isNotEmpty(request.getOpreateTimeStart())){
            cra.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getOpreateTimeStart())));
        }
        if(StringUtils.isNotEmpty(request.getOpreateTimeEnd())){
            cra.andCreateTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(request.getOpreateTimeEnd())));
        }
        if(StringUtils.isNotEmpty(request.getTransferTimeStart())){
            cra.andTransferTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getTransferTimeStart())));
        }
        if(StringUtils.isNotEmpty(request.getTransferTimeEnd())){
            cra.andTransferTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(request.getTransferTimeEnd())));
        }
        if(StringUtils.isNotEmpty(request.getReconciliationId())){
            cra.andReconciliationIdEqualTo(request.getReconciliationId());
        }
        if(StringUtils.isNotEmpty(request.getTransferTypeSrch())){
            cra.andTransferTypeEqualTo(Integer.valueOf(request.getTransferTypeSrch()));
        }
        example.setOrderByClause("create_time desc");
        if (offset != -1) {
            example.setLimitStart(offset);
            example.setLimitEnd(limit);
        }
        return userTransferMapper.selectByExample(example);
    }
}
