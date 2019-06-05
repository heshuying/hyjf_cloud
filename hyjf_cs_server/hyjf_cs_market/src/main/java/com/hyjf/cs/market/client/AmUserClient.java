package com.hyjf.cs.market.client;

import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.am.vo.user.UserUtmInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/7/6 11:04
 */
public interface AmUserClient {
    /**
     * 查询投之家当天注册人数、开户人数、绑卡人数
     * @param startTime
     * @param endTime
     * @return
     */
    TzjDayReportVO queryUserDataOnToday(Date startTime,Date endTime);

    /**
     * 查询投之家当天注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    Set<Integer> queryRegisterUsersOnToday(Date startTime,Date endTime);
    /**
     * 查询投之家所有注册用户
     * @return
     */
    Set<Integer> queryAllTzjUsers();

    /**
     * 获取所有渠道
     * @param type 类型:app,pc
     * @return
     */
    List<UtmVO> selectUtmPlatList(String type);

    /**
     * 修改短信与邮件是否开启状态
     * @param userId
     * @param smsOpenStatus
     * @param emailOpenStatus
     * @return
     */
    Integer updateStatusByUserId( Integer userId,String smsOpenStatus,String emailOpenStatus);

    /**
     * 得到千乐渠道用户
     * @return
     */
    List<Integer> getQianleUser(String sourceId);

    /**
     * 保存短信信息
     * @param mobile
     * @param checkCode
     * @param validCodeType
     * @param status
     * @param platform
     * @return
     */
    int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

    int onlyCheckMobileCode(String mobile, String code);

    List<Integer> getUsersInfoList();

    List<Integer> getUsersList(String source);

    /**
     * 根据一级部门查询二级部门
     * @param nmzxDivisionName
     * @return
     */
    List<String> selectTwoDivisionByPrimaryDivision(String nmzxDivisionName);

    /**
     * 获取评分标准列表
     * @return
     */
    List<EvalationCustomizeVO> getEvalationRecord();

    /**
     * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
     * @param userId
     * @return
     * @Author : huanghui
     */
    UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId);

    /**
     * 根据userId查询用户信息
     * @param userId
     * @return
     */
    UserVO getUserById(Integer userId);

    /**
     * 查询用户活动期间登陆情况
     * @param userId
     * @param activityStartDate
     * @param activityEndDate
     * @return
     */
    boolean hasLoginInActivity(int userId, Date activityStartDate, Date activityEndDate);
}
