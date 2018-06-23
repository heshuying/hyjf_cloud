package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UserInfoCustomize;

public interface UserInfoCustomizeMapper {

    /**
     * 查询非员工用户信息
     *
     * @param username
     * @return
     */
    public UserInfoCustomize queryUserInfoByName(@Param("userName") String username);

    /**
     * 查询员工信息
     *
     * @param referrerName
     * @return
     */
    public UserInfoCustomize queryUserInfoByEmployeeName(@Param("employeeName") String employeeName);

    /**
     * 查询员工部门信息
     *
     * @param userInfoCustomize
     * @return
     */
    public List<UserInfoCustomize> queryDepartmentInfoByUserId(@Param("userId") Integer userId);

    
    /**
     * 
     * 根据用户id查询用户情报
     * @author liuyang
     * @param user_id
     * @return
     */
    public UserInfoCustomize queryUserInfoByUserId(@Param("user_id") Integer user_id);
    
    /**
     * 
     * 根据用户id查询用户情报
     * @author liuyang
     * @param user_id
     * @return
     */
    public UserInfoCustomize selectUserInfoByUserId(@Param("user_id") Integer user_id);
    
    /**
     * 根据用户名查询用户部门信息
     * @param username
     * @return
     */
    public UserInfoCustomize queryUserDepartmentInfoByUserName(@Param("userName") String username);

    /**
     * 根据用户画像查询用户记录数
     * @param userPortrait
     * @return
     */
    int countRecordTotal(Map<String, Object> userPortrait);

}
