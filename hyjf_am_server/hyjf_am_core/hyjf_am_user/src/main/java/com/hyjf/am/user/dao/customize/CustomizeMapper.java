package com.hyjf.am.user.dao.customize;

import com.hyjf.am.user.dao.auto.AutoMapper;
import com.hyjf.am.user.dao.mapper.customize.UserManagerCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UtmRegCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomizeMapper extends AutoMapper {

    @Autowired
    protected UtmRegCustomizeMapper utmRegCustomizeMapper;

    @Autowired
    protected UserManagerCustomizeMapper userManagerCustomizeMapper;

}
