/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre;

import com.hyjf.am.resquest.admin.UserPortraitScoreRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.dao.model.customize.UserPortraitScoreCustomize;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.user.UserPortraitVO;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->借款盖章用户
 */
public interface UserPortraitManagerService extends BaseService {
    /**
     * 根据参数查询用户画像信息
     * @param userPortrait
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<UserPortraitVO> selectRecordList(Map<String, Object> userPortrait, int limitStart, int limitEnd);

    /**
     * 根据条件获取记录数
     * @param userPortrait
     * @return
     */
    int countLoanSubjectCertificateAuthority(Map<String, Object> userPortrait);

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @return
     */
    UserPortrait selectUsersPortraitByUserId(Integer userId);

    /**
     * 修改用户画像
     */
    int updateUserPortrait(UserPortraitRequest request);

    /**
     * 根据条件获取用户画像评分列表
     * @param userPortrait
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<UserPortraitScoreCustomize> selectUserPortraitScoreList(Map<String, Object> userPortrait, UserPortraitScoreRequest request, int limitStart, int limitEnd);
}
