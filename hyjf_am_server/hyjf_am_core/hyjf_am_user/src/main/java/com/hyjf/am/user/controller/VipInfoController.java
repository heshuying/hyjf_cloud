/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.VipInfoResponse;
import com.hyjf.am.user.dao.model.auto.VipInfo;
import com.hyjf.am.user.service.VipInfoService;
import com.hyjf.am.vo.user.VipInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version VipInfoController, v0.1 2018/7/23 10:54
 */
@RestController
@RequestMapping("/am-user/vipInfo")
public class VipInfoController {

    @Autowired
    VipInfoService vipInfoService;

    @GetMapping(value = "/findVipInfoById/{vipId}")
    public VipInfoResponse findVipInfoById(Integer vipId){
        VipInfoResponse response = new VipInfoResponse();
        VipInfo vipInfo  = vipInfoService.findVipInfoById(vipId);
        VipInfoVO vipInfoVO = new VipInfoVO();
        BeanUtils.copyProperties(vipInfo,vipInfoVO);
        if (null!=vipInfo){
            response.setResult(vipInfoVO);
        }
        return response;
    }
}
