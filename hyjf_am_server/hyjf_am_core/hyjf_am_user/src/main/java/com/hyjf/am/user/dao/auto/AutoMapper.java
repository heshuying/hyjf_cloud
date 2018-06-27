package com.hyjf.am.user.dao.auto;


import com.hyjf.am.user.dao.mapper.auto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapper {
    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected UserInfoMapper userInfoMapper;

    @Autowired
    protected SpreadsUserMapper spreadsUserMapper;

    @Autowired
    protected UserLogMapper usersLogMapper;

    @Autowired
    protected EvalationMapper evalationMapper;

    @Autowired
    protected HjhUserAuthMapper hjhUserAuthMapper;

    @Autowired
    protected HjhUserAuthLogMapper hjhUserAuthLogMapper;

    @Autowired
    protected UserEvalationResultMapper userEvalationResultMapper;

    @Autowired
    protected UserEvalationMapper userEvalationMapper;

    @Autowired
    protected AccountChinapnrMapper accountChinapnrMapper;

    @Autowired
    protected UserContactMapper UserContactMapper;

    @Autowired
    protected UserBindEmailLogMapper userBindEmailLogMapper;

    @Autowired
    protected UtmRegMapper utmRegMapper;

    @Autowired
    protected UtmPlatMapper utmPlatMapper;

    @Autowired
    protected VipUserTenderMapper vipUserTenderMapper;

    @Autowired
    protected CorpOpenAccountRecordMapper corpOpenAccountRecordMapper;

    @Autowired
    protected UserLoginLogMapper userLoginLogMapper;

    @Autowired
    protected PreRegistMapper preRegistMapper;
}

