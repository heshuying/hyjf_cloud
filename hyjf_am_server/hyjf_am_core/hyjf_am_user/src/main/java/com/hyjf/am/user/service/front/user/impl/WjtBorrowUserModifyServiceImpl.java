/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.dao.model.auto.UtmRegExample;
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
     * @param userId
     * @return
     */
    @Override
    public boolean findUtmReg(String userId) {
        UtmRegExample example = new UtmRegExample();
        UtmRegExample.Criteria cra =example.createCriteria();
        cra.andUserIdEqualTo(Integer.parseInt(userId));
        List<UtmReg> list = this.utmRegMapper.selectByExample(example);
        return false;
    }


}
