/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserPortraitScoreSerivce;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UserPortraitScoreResponse;
import com.hyjf.am.resquest.admin.UserPortraitScoreRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.admin.UserPortraitScoreCustomizeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version UserPortraitScoreController, v0.1 2018/8/9 11:06
 */
@Api(tags = "会员中心-用户画像评分",description = "会员中心-用户画像评分")
@RestController
@RequestMapping("hyjf-admin/userPortrait")
public class UserPortraitScoreController extends BaseController {

    private static final String PERMISSIONS = "userPortrait";

    @Autowired
    private UserPortraitScoreSerivce userPortraitScoreService;

    @ApiOperation(value = "用户画像评分列表",notes = "用户画像评分列表")
    @RequestMapping("/seletPortraitScoreList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<UserPortraitScoreCustomizeVO>> seletPortraitScoreList(UserPortraitScoreRequest request) {

        String redisKey = RedisConstants.USERPORTRAIT_SCORE;
        JedisPool pool = RedisUtils.getPool();
        Jedis jedis = pool.getResource();
        Boolean exists = jedis.exists(redisKey);
        String other1 = "1";
        if (!exists) {
            List<ParamNameVO> paramNameVOList = userPortraitScoreService.getParamNameList(other1);
            Map<String, String> map = new HashMap<>();
            for (ParamNameVO paramName : paramNameVOList) {
                String key = paramName.getName() + paramName.getNameCd();
                String value = paramName.getName();
                map.put(key, value);
            }
            jedis.hmset(redisKey, map);
        }

        UserPortraitScoreResponse response = userPortraitScoreService.selectRecordList(request);
        List<UserPortraitScoreCustomizeVO> list = response.getResultList();
        BigDecimal rechargeSum = new BigDecimal(0);
        try {
            for (UserPortraitScoreCustomizeVO userPortraitScoreCustomizeVO: list) {
                int userId = userPortraitScoreCustomizeVO.getUserId();
                List<AccountRechargeVO> rechargeVOList = userPortraitScoreService.getAccountRecharge(userId);
                for (AccountRechargeVO rechargeVO : rechargeVOList) {
                    rechargeSum = rechargeSum.add(rechargeVO.getBalance());
                }
                if (userPortraitScoreCustomizeVO.getLoginActive().compareTo("30") <= 0 && rechargeSum.compareTo(new BigDecimal(0)) == 0) {
                    userPortraitScoreCustomizeVO.setIdentityLabel("任务刷单者");
                }
                list.add(userPortraitScoreCustomizeVO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<UserPortraitScoreCustomizeVO>>(ListResult.build(list, response.getCount()));
    }
}
