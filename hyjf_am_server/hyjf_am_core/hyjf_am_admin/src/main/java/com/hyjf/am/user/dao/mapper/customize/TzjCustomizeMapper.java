package com.hyjf.am.user.dao.mapper.customize;

import java.util.Date;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

/**
 * @author xiasq
 * @version TzjCustomizeMapper, v0.1 2018/7/9 14:15
 */
public interface TzjCustomizeMapper {
    /**
     * 查询注册人数
     * @param startTime
     * @param endTime
     * @return
     */
    int getRegistCount(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 查询当天开户人数
     * @param startTime
     * @param endTime
     * @return
     */
    int getOpenCount(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 查询当天绑卡人数
     * @param startTime
     * @param endTime
     * @return
     */
    int getCardBindCount(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 查询投之家当天注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    Set<Integer> queryRegisterUsers(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询投之家所有注册用户
     * @return
     */
    Set<Integer> queryAllTzjUser();
}
