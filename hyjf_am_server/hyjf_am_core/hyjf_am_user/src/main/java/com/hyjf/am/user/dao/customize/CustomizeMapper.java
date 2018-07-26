package com.hyjf.am.user.dao.customize;

import com.hyjf.am.user.dao.auto.AutoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserAliasMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.*;
import com.hyjf.am.user.dao.mapper.customize.batch.TzjCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomizeMapper extends AutoMapper {

    @Autowired
    protected UtmRegCustomizeMapper utmRegCustomizeMapper;
    @Autowired
    protected UserManagerCustomizeMapper userManagerCustomizeMapper;
    @Autowired
	protected MspConfigureCustomizeMapper mspConfigureCustomizeMapper;
    @Autowired
    protected TzjCustomizeMapper tzjCustomizeMapper;
    @Autowired
    protected UserAliasMapper userAliasMapper;
    @Autowired
    protected UserAliasCustomizeMapper userAliasCustomizeMapper;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected UserInfoMapper userInfoMapper;

    @Autowired
    protected UserAdminBankAccountCheckCustomizeMapper userAdminBankAccountCheckCustomizeMapper;
}
