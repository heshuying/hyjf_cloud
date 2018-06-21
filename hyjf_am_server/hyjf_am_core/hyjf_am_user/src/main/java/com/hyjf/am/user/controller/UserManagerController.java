/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.service.UserManagerService;
import com.hyjf.am.vo.user.UserManagerVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 * 后台会员中心-会员管理
 */

@RestController
@RequestMapping("/am-user/userManager")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;

    /**
     * 根据筛选条件查找(用户管理列表显示)
     * @param request
     * @return
     */
    @RequestMapping("/userslist")
    public UserManagerResponse findUserslist(@RequestBody @Valid UserManagerRequest request) {
        UserManagerResponse response = new UserManagerResponse();
        List<UserManagerCustomize> userManagerCustomizeList = userManagerService.selectUserMemberList(request);
        if(!CollectionUtils.isEmpty(userManagerCustomizeList)){
            List<UserManagerVO> userVoList = CommonUtils.convertBeanList(userManagerCustomizeList,UserManagerVO.class);
            response.setResultList(userVoList);
        }
        return response;
    }

    /**
     * 获取列表总数
     * @param request
     * @return
     */
    @RequestMapping("/countUserList")
    public UserManagerResponse countUserList(@RequestBody @Valid UserManagerRequest request){
        UserManagerResponse response = new UserManagerResponse();
        int usesrCount = userManagerService.countUserRecord(request);
        response.setCount(usesrCount);
        return response;
    }
}
