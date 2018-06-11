/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.borrow.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.borrow.dao.model.auto.HjhInstConfig;
import com.hyjf.am.borrow.service.UserService;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhangqingqing
 * @version BorrowController, v0.1 2018/5/28 12:42
 */

@Api(value = "根据机构编号检索机构信息")
@RestController
@RequestMapping("/am-borrow/borrow")
public class BorrowController {

    @Autowired
    UserService userService;

    /**
     * @Author: zhangqingqing
     * @Desc :根据机构编号检索机构信息
     * @Param: * @param instCode
     * @Date: 9:00 2018/5/31
     * @Return: com.hyjf.am.response.user.HjhInstConfigResponse
     */
    @ApiOperation(value = " 根据机构编号检索机构信息")
    @GetMapping("/selectInstConfigByInstCode/{instCode}")
    public HjhInstConfigResponse selectInstConfigByInstCode(@PathVariable(value = "instCode") String instCode) {
        HjhInstConfigResponse response = new HjhInstConfigResponse();
        HjhInstConfig hjhInstConfig = userService.selectInstConfigByInstCode(instCode);
        if(null != hjhInstConfig){
            HjhInstConfigVO hjhInstConfigVO = new HjhInstConfigVO();
            BeanUtils.copyProperties(hjhInstConfig,hjhInstConfigVO);
            response.setResult(hjhInstConfigVO);
        }
        return response;
    }
}
