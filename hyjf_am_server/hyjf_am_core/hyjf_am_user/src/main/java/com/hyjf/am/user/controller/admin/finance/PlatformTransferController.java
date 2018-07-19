/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.admin.finance.PlatformTransferService;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferController, v0.1 2018/7/9 13:49
 */
@Api(value = "资金中心-转账管理-平台转账",description = "资金中心-转账管理-平台转账")
@RestController
@RequestMapping(value = "/am-user/platformtransfer")
public class PlatformTransferController extends BaseController {

    @Autowired
    private PlatformTransferService platformTransferService;

    /**
     * 平台转账-userId的列表查询User的列表-因为每页要查询10条数据。如果循环调用findUserById的接口，网络消耗太大
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "平台转账-查询转账列表",notes = "平台转账-查询转账列表")
    @PostMapping(value = "/finduserlistbyuserids")
    public UserResponse findUserListByUserIds(@RequestBody String userIds){
        logger.info(userIds);
        UserResponse response = new UserResponse();
        List<User> userList = platformTransferService.findUserListByUserIds(userIds);
        if(!CollectionUtils.isEmpty(userList)){
            List<UserVO> userVOList = CommonUtils.convertBeanList(userList,UserVO.class);
            response.setResultList(userVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
