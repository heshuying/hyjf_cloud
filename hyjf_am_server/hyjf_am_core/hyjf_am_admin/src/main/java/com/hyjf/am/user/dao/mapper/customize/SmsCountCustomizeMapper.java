/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version SmsCountCustomizeMapper, v0.1 2018/8/20 16:33
 */
public interface SmsCountCustomizeMapper {
    List<SmsCountCustomize> querySmsCountLlist(SmsCountRequest request);

    List<SmsCountCustomize> querySmsCountNumberTotal(SmsCountRequest request);

    List<OADepartmentCustomize> queryDepartmentInfo();

    int selectCount(SmsCountRequest request);

    List<String> queryUser(Map<String, Object> params);

    /**
     * 通过手机号获取部门
     * @param mobile
     * @return
     */
    Map<String,Object> getDeptByMobile(String mobile);

    List<SmsCountCustomize> querySms(SmsCountCustomize smsCountCustomize);

    /**
     * 批量插入
     * @param list
     * @return
     */
    void insertBatch(List<SmsCountCustomize> list);

    /***********  下面方法为临时使用 ****************/
    //获得内部员工的ID和部门
    List<SmsCountCustomize> getuserIdAnddepartmentName();

    List<UserVO> selectUserListByMobile(List<String> list);

    void insertBatchSmsCount(List<SmsCountCustomize> list);

    void updateBatch(List<SmsCountCustomize> list);

    void deleteBatch(int[] listid);

    //查询重复数据总数
    Integer selectRepeatSmsCount();

    //分页查询重复数据
    List<SmsCountCustomize> selectRepeatSmsCountData(Map<String, Object> params);
}
