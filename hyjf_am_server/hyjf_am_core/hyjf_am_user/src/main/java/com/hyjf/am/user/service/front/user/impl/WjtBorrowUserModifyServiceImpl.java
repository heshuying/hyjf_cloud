/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.front.user.WjtBorrowUserModifyService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 温金投修改借款人机构编号
 *
 * @author liuyang
 * @version WjtBorrowUserModifyServiceImpl, v0.1 2019/5/14 15:38
 */
@Service
public class WjtBorrowUserModifyServiceImpl extends BaseServiceImpl implements WjtBorrowUserModifyService {
    /**
     * 更新借款人机构编号
     *
     * @param borrowUser
     */
    @Override
    public void modifyUserInstCode(User borrowUser) {
        this.userMapper.updateByPrimaryKeySelective(borrowUser);
    }

    /**
     * 根据用户ID查询用户渠道是否存在
     *
     * @param userId
     * @return
     */
    @Override
    public boolean findUtmReg(String userId) {
        UtmRegExample example = new UtmRegExample();
        UtmRegExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(Integer.parseInt(userId));
        List<UtmReg> list = this.utmRegMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            // 用户已是渠道用户
            return true;
        }
        return false;
    }

    /**
     * 插入utmreg
     *
     * @param userId
     * @param wjtChannel
     */
    @Override
    public void insertBorrowUserUtmReg(String userId, String wjtChannel) {
        User user = this.userMapper.selectByPrimaryKey(Integer.parseInt(userId));
        boolean isBindCard = false;
        // 查询用户银行
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(Integer.parseInt(userId));
        List<BankCard> bankCard = this.bankCardMapper.selectByExample(example);
        if (bankCard != null && bankCard.size() > 0) {
            isBindCard = true;
        }
        UtmReg utmReg = new UtmReg();
        // 是否开户
        utmReg.setOpenAccount(user.getBankOpenAccount());
        utmReg.setUserId(Integer.parseInt(userId));
        utmReg.setUtmId(Integer.parseInt(wjtChannel));
        utmReg.setCreateTime(user.getCreateTime());
        utmReg.setBindCard(isBindCard ? 1 : 0);
        this.utmRegMapper.insertSelective(utmReg);
    }


}
