/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre;

import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserPayAuthService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->会员管理
 */
public interface UserPayAuthService extends BaseService {

    /**
     * 根据筛选条件查找会员列表
     * @param mapParam
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<AdminUserPayAuthCustomize> selectUserPayAuthList(Map<String, Object> mapParam, int limitStart, int limitEnd);

    /**
     * 根据条件查询用户管理总数
     *
     * @return
     */
    int countRecordTotalPay(Map<String, Object> mapParam);
    /**
     *
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    HjhUserAuth selectHjhUserAuthByUserId(Integer userId);

    /**
     * 缴费授权解约
     * @param userId
     * @return
     */
    boolean updateCancelPayAuth(int userId);

    /**
     * 插入授权记录表
     * @param hjhUserAuthLogRequest
     * @return
     */
    boolean insertUserAuthLog2(HjhUserAuthLog hjhUserAuthLogRequest);
    /**
     * 根据筛选条件查找还款授权列表
     *
     * @param mapParam 筛选条件
     * @return
     */
    List<AdminUserPayAuthCustomize> selectUserRePayAuthList(Map<String, Object> mapParam, int limitStart, int limitEnd);
    /**
     * 根据条件获取还款授权总数
     *
     * @return
     */
   int countRecordTotalRePay(Map<String, Object> mapParam);
    /**
     * 更新授权表
     * @param hjhUserAuth
     * @return
     */
    int updateCancelRePayAuth(HjhUserAuth hjhUserAuth);
}
