/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.util.Result;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmBorrowClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.constants.TenderError;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.HjhTenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:50
 */
@Service
public class HjhTenderServiceImpl extends BaseTradeServiceImpl implements HjhTenderService {
    private static final Logger logger = LoggerFactory.getLogger(HjhTenderServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBorrowClient amBorrowClient;

    /**
     * @param request
     * @Description 检查加入计划的参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 9:47
     */
    @Override
    public void checkTenderParam(TenderRequest request) {
        Result result = new Result();
        WebViewUser loginUser = RedisUtils.getObj(request.getToken(), WebViewUser.class);
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        logger.info("加入计划投资开始userId:{},ip:{},平台{}", userId, request.getIp());
        // 先检查redis  看用户是否重复投资
        boolean checkToken = checkRedisToken(request);
        if (!checkToken) {
            // 用户正在加入计划
            throw new ReturnMessageException(TenderError.TENDERING_ERROR);
        }
        // 设置redis 的值  防重校验
        String redisValue = GetDate.getCalendar().getTimeInMillis() + GetCode.getRandomCode(5);
        RedisUtils.set(RedisConstants.HJH_TENDER_REPEAT + userId, redisValue, 300);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        UserVO user = amUserClient.findUserById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        checkUser(user,userInfo);
        // 查询计划
        HjhPlanVO plan = amBorrowClient.getPlanByNid(request.getBorrowNid());

    }

    /**
     * @Description 检查用户状态  角色  授权状态 等  是否允许投资
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 11:37
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        if (userInfo.getRoleId() == 3) {// 担保机构用户
            throw new ReturnMessageException(TenderError.USER_ROLE_ERROR);
        }
        if (userInfo.getRoleId() == 2){// 借款人不能投资
            throw new ReturnMessageException(TenderError.USER_ROLE_ERROR);
        }
        // 判断用户是否禁用
        if (user.getStatus() == 1) {// 0启用，1禁用
            throw new ReturnMessageException(TenderError.USER_DISABLED_ERROR);
        }
        // 检查用户授权状态
        HjhUserAuthVO userAuth =  amUserClient.getHjhUserAuthVO(user.getUserId());
        // 为空则无授权
        if (userAuth == null) {
            throw new ReturnMessageException(TenderError.USER_AUTO_INVES_ERROR);
        } else {
            if (userAuth.getAutoInvesStatus()==0) {
                throw new ReturnMessageException(TenderError.USER_AUTO_INVES_ERROR);
            }
            if (userAuth.getAutoCreditStatus()==0) {
                throw new ReturnMessageException(TenderError.USER_AUTO_CREDIT_ERROR);
            }
        }
        // 服务费授权校验
        if (userAuth.getAutoPaymentStatus() == 0) {
            throw new ReturnMessageException(TenderError.USER_PAYMENT_AUTH_ERROR);
        }
    }

    /**
     * @Description 检查redis里面的缓存  用户是否能投资
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 10:38
     */
    private boolean checkRedisToken(TenderRequest request) {
        String token_ = "";
        if (RedisUtils.exists(RedisConstants.HJH_TENDER_REPEAT + request.getUser().getUserId())) {
            // 如果已经有了
            String redisTenderToken = RedisUtils.get(token_ + request.getUser().getUserId());
            if(!redisTenderToken.equals(request.getTenderToken())){
                logger.info("用户已经加入计划userId:{},ip:{},ip{},平台{}", request.getUser().getUserId(), request.getIp(),request.getPlatform());
                return false;
            }
        }

        return true;
    }
}
