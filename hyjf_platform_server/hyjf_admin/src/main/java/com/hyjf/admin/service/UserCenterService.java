/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface UserCenterService {
    /**
     *查找用户信息
     * @param request
     * @return
     */
    Map<String,Object> selectUserMemberList(UserManagerRequest request);

    /**
     * 根据机构编号获取机构列表
     * @param instCode
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigList(String instCode);

}
