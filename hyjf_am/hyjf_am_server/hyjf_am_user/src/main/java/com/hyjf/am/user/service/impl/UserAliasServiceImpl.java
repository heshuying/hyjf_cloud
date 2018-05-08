/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.MobileCodeMapper;
import com.hyjf.am.user.dao.mapper.customize.MobileCodeCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.MobileCode;
import com.hyjf.am.user.dao.model.auto.MobileCodeExample;
import com.hyjf.am.user.service.UserAliasService;
import com.hyjf.am.vo.user.UserAliasVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version UserAliasServiceImpl, v0.1 2018/5/8 11:05
 */
@Service
public class UserAliasServiceImpl implements UserAliasService {

    @Autowired
    private MobileCodeMapper mobileCodeMapper;

    @Autowired
    private MobileCodeCustomizeMapper mobileCodeCustomizeMapper;

    @Override
    public MobileCode findAliasByMobile(String mobile) {
        MobileCodeExample example = new MobileCodeExample();
        example.createCriteria().andMobileCodeEqualTo(mobile);
        List<MobileCode> mobileCodeList = mobileCodeMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(mobileCodeList)) {
            return mobileCodeList.get(0);
        }
        return null;
    }

    @Override
    public List<UserAliasVO> findAliasByMobiles(List<String> mobiles) {
        List<UserAliasVO> userAliasVOList = mobileCodeCustomizeMapper.findAliasByMobiles(mobiles);
        return userAliasVOList;
    }
}
