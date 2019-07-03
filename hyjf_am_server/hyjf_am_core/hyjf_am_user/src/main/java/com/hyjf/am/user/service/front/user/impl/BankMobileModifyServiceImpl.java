/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.BankMobileModify;
import com.hyjf.am.user.dao.model.auto.BankMobileModifyExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.front.user.BankMobileModifyService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.BankMobileModifyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankMobileModifyServiceImpl, v0.1 2019/5/9 15:30
 */
@Service
public class BankMobileModifyServiceImpl extends BaseServiceImpl implements BankMobileModifyService {

    /**
     * 插入银行预留手机号日志表
     *
     * @param bankMobileModify
     * @return
     */
    @Override
    public Integer insertBankMobileModify(BankMobileModify bankMobileModify) {
        return bankMobileModifyMapper.insertSelective(bankMobileModify);
    }

    /**
     * 查询修改银预留手机号日志表
     *
     * @param vo
     * @return
     */
    @Override
    public List<BankMobileModify> selectBankMobileModify(BankMobileModifyVO vo) {
        BankMobileModifyExample example = new BankMobileModifyExample();
        BankMobileModifyExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(vo.getAccount())) {
            criteria.andAccountEqualTo(vo.getAccount());
        }
        if (null != vo.getStatus()) {
            criteria.andStatusEqualTo(vo.getStatus());
        }
        List<BankMobileModify> list = bankMobileModifyMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 更新银预留手机号日志表
     *
     * @param bankMobileModify
     * @return
     */
    @Override
    public Integer updateBankMoblieModify(BankMobileModify bankMobileModify) {
        boolean updateFlg = false;
        BankMobileModifyExample example = new BankMobileModifyExample();
        BankMobileModifyExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bankMobileModify.getAccount())) {
            // 更新条件：状态为0初始的该账号的值
            criteria.andAccountEqualTo(bankMobileModify.getAccount()).andStatusEqualTo(0);
            updateFlg = true;
        }
        if(null != bankMobileModify.getUserId()) {
            // 更新条件：状态为0初始的该账号的值
            criteria.andUserIdEqualTo(bankMobileModify.getUserId()).andStatusEqualTo(0);
            updateFlg = true;
        }
        // 更新条件未传情况不做更新
        if(!updateFlg) {
            return 0;
        }
        // 银行返回手机号修改成功
        if(StringUtils.isNotBlank(bankMobileModify.getBankMobileNew())) {
            bankMobileModify.setBankMobileNew(bankMobileModify.getBankMobileNew());
            // 修改手机号状态为1完成
            bankMobileModify.setStatus(1);
        }
        return bankMobileModifyMapper.updateByExampleSelective(bankMobileModify,example);
    }


    /**
     * 更新用户银行预留手机号
     *
     * @param userId
     * @param bankMobile
     * @return
     */
    @Override
    public Integer updateBankMobileByUserId(Integer userId, String bankMobile) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            user.setBankMobile(bankMobile);
            user.setUpdateTime(new Date());
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
