/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.callcenter;

import com.hyjf.am.response.callcenter.CallCenterUserBaseResponse;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.callcenter.CallCenterAdminService;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version CallCenterAdminController, v0.1 2018/6/6 14:14
 */
@RestController
@RequestMapping("/am-user/callcenter")
public class CallCenterAdminController extends BaseController {
    @Autowired
    CallCenterAdminService callCenterBankService;

    /**
     * 查询呼叫中心未分配客服的用户（复投用户筛选）
     *
     * @param callCenterUserInfoRequest
     * @return
     */
    @RequestMapping("/getNoServiceFuTouUsersList")
    public CallCenterUserBaseResponse getNoServiceFuTouUsersList(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getNoServiceFuTouUsersList(callCenterUserInfoRequest);
        if (!CollectionUtils.isEmpty(list)) {
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list, CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }

    /**
     * 查询呼叫中心未分配客服的用户（流失用户筛选）
     *
     * @param callCenterUserInfoRequest
     * @return
     */
    @RequestMapping("/getNoServiceLiuShiUsersList")
    public CallCenterUserBaseResponse getNoServiceLiuShiUsersList(@RequestBody @Valid CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = new CallCenterUserBaseResponse();
        List<CallcenterUserBaseCustomize> list = callCenterBankService.getNoServiceLiuShiUsersList(callCenterUserInfoRequest);
        if (!CollectionUtils.isEmpty(list)) {
            List<CallCenterUserBaseVO> callCenterUserBaseVOS = CommonUtils.convertBeanList(list, CallCenterUserBaseVO.class);
            callCenterUserBaseResponse.setResultList(callCenterUserBaseVOS);
        }
        return callCenterUserBaseResponse;
    }
}
