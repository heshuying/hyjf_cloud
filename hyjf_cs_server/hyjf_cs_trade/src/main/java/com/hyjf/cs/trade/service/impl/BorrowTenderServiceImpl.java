/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.constants.TenderError;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.cs.trade.service.CouponService;
import com.hyjf.cs.trade.service.HjhTenderService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 投资接口
 * @Author sunss
 * @Date 2018/6/24 14:30
 */
@Service
public class BorrowTenderServiceImpl extends BaseTradeServiceImpl implements BorrowTenderService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderServiceImpl.class);

    /**
     * @param tender
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    @Override
    public void borrowTender(TenderRequest tender) {

    }
}
