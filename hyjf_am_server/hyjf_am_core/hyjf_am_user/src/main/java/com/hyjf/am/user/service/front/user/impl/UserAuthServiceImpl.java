package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLogExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.front.user.UserAuthService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl extends BaseServiceImpl implements UserAuthService {


    @Override
    public void updateUserAuth(UserAuthRequest request) {

        //更新授权log
        HjhUserAuthLog hjhUserAuthLog=new HjhUserAuthLog();
        BeanUtils.copyProperties(request.getHjhUserAuthLogVO(),hjhUserAuthLog);
        hjhUserAuthLogMapper.updateByPrimaryKeySelective(hjhUserAuthLog);
        //更新用户信息
        if(request.getUser()!=null){
            User user=new User();
            BeanUtils.copyProperties(request.getUser(),user);
            usersMapper.updateByPrimaryKeySelective(user);
        }
        //更新用户授权
        HjhUserAuth hjhUserAuth=new HjhUserAuth();
        BeanUtils.copyProperties(request.getHjhUserAuth(),hjhUserAuth);
        if(hjhUserAuth.getId()!=null&&hjhUserAuth.getId()>0){
            hjhUserAuthMapper.updateByPrimaryKeySelective(hjhUserAuth);
        }else{
            hjhUserAuthMapper.insertSelective(hjhUserAuth);
        }


    }

    @Override
    public void updateUserAuthLog(String logOrderId, String message) {
        HjhUserAuthLog hjhUserAuthLog=new HjhUserAuthLog();
        hjhUserAuthLog.setRemark(message);
        HjhUserAuthLogExample example=new HjhUserAuthLogExample();
        example.createCriteria().andOrderIdEqualTo(logOrderId);
        hjhUserAuthLogMapper.updateByExampleSelective(hjhUserAuthLog,example);
    }
}
