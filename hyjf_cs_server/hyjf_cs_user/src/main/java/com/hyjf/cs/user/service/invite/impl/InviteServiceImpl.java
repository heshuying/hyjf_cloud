package com.hyjf.cs.user.service.invite.impl;

import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.invite.InviteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励
 * @author hesy
 * @version InviteServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class InviteServiceImpl extends BaseUserServiceImpl implements InviteService {

    /**
     * 邀请列表请求校验
     */
    @Override
    public void checkForInviteList(Map<String, String> param){
        String currPage = param.get("currPage");
        String pageSize = param.get("pageSize");
        if(StringUtils.isBlank(currPage)){
            param.put("currPage", "1");
        }

        if(StringUtils.isBlank(pageSize)){
            param.put("pageSize", "10");
        }
    }

    /**
     * 我的邀请列表
     */
    @Override
    public List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd){
        MyInviteListRequest requestBean = new MyInviteListRequest();
        requestBean.setUserId(userId);
        requestBean.setLimitStart(limitStart);
        requestBean.setLimitEnd(limitEnd);
        return amUserClient.selectMyInviteList(requestBean);
    }

    /**
     * 我的邀请记录总数
     */
    @Override
    public Integer selectMyInviteCount(String userId){
        MyInviteListRequest requestBean = new MyInviteListRequest();
        requestBean.setUserId(userId);
        return amUserClient.selectMyInviteCount(requestBean);
    }

}
