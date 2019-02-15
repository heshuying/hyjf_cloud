/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.bifa;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.bifa.BifaIndexUserInfoBeanResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.bifa.BifaIndexUserInfoBean;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.am.vo.trade.bifa.BifaIndexUserInfoBeanVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jun
 * @version BifaDataReportController, v0.1 2019/1/21 10:23
 */
@RestController
@RequestMapping("/am-user/bifaDataReport")
public class BifaDataReportController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/getBankOpenedAccountUsers/{startDate}/{endDate}")
    public BifaIndexUserInfoBeanResponse getBankOpenedAccountUsers(@PathVariable Integer startDate, @PathVariable Integer endDate){
        BifaIndexUserInfoBeanResponse response = new BifaIndexUserInfoBeanResponse();
        List<BifaIndexUserInfoBean> recordList = userService.getBankOpenedAccountUsers(startDate,endDate);
        if (CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList,BifaIndexUserInfoBeanVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @GetMapping("/getBifaIndexUserInfo/{userId}")
    public BifaIndexUserInfoBeanResponse getBifaIndexUserInfo(@PathVariable Integer userId){
        BifaIndexUserInfoBeanResponse response = new BifaIndexUserInfoBeanResponse();
        BifaIndexUserInfoBean record = userService.getBifaIndexUserInfo(userId);
        if (record != null){
            response.setResult(CommonUtils.convertBean(record,BifaIndexUserInfoBeanVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;

    }

}
