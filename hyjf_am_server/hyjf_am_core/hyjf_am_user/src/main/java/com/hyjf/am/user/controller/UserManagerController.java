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
}
