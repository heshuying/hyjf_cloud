/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.constants.TenderError;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.cs.trade.service.CouponService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 投资接口
 * @Author sunss
 * @Date 2018/6/24 14:30
 */
@Service
public class BorrowTenderServiceImpl extends BaseTradeServiceImpl implements BorrowTenderService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBorrowClient amBorrowClient;

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private RechargeClient rechargeClient;

    @Autowired
    private AmMongoClient amMongoClient;

    @Autowired
    private CouponService couponService;

    @Autowired
    private BorrowClient borrowClient;

    /**
     * @param request
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    @Override
    public WebResult<Map<String, Object>> borrowTender(TenderRequest request) {
        WebViewUserVO loginUser = RedisUtils.getObj(request.getToken(), WebViewUserVO.class);
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            // 项目编号不能为空
            throw new ReturnMessageException(MsgEnum.STATUS_CE000013);
        }
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = couponClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 查询散标是否存在
        BorrowVO borrow = borrowClient.selectBorrowByNid(request.getBorrowNid());
        if (borrow == null) {
            throw new ReturnMessageException(MsgEnum.FIND_BORROW_ERROR);
        }
        logger.info("散标投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        UserVO user = amUserClient.findUserById(request.getUser().getUserId());
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        checkUser(user, userInfo);
        // 投资检查参数
        this.checkParam(request,borrow,cuc);
        return null;
    }

    /**
     *  检查用户状态  角色  授权状态等  是否允许投资
     * @param user
     * @param userInfo
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        if (userInfo.getRoleId() == 3) {// 担保机构用户
            throw new ReturnMessageException(TenderError.USER_ROLE_ERROR);
        }
        if (userInfo.getRoleId() == 2) {// 借款人不能投资
            throw new ReturnMessageException(TenderError.USER_ROLE_ERROR);
        }
        // 判断用户是否禁用
        if (user.getStatus() == 1) {// 0启用，1禁用
            throw new ReturnMessageException(TenderError.USER_DISABLED_ERROR);
        }
        // 服务费授权校验  合规需要
        // 风险测评校验
        this.checkEvaluation(user);
    }

    /**
     * 散标投资参数校验
     * @param request
     * @param borrow
     * @param cuc
     */
    private void checkParam(TenderRequest request, BorrowVO borrow, CouponUserVO cuc) {


    }
}
