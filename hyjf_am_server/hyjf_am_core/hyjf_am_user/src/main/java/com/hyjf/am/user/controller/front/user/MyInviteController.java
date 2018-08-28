package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.MyInviteListResponse;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.MyInviteService;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 我的邀请及奖励
 * @author hesy
 * @version MyInviteController, v0.1 2018/6/23 12:19
 */
@RestController
@RequestMapping("/am-user/invite")
public class MyInviteController extends BaseController {
    @Autowired
    MyInviteService myInviteService;

    /**
     * 我的邀请
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myInviteList", method = RequestMethod.POST)
    public MyInviteListResponse myInviteList(@RequestBody @Valid MyInviteListRequest requestBean) {
        MyInviteListResponse responseBean = new MyInviteListResponse();

        List<MyInviteListCustomizeVO> resultList = myInviteService.selectMyInviteList(requestBean.getUserId(),requestBean.getLimitStart(),requestBean.getLimitEnd());
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计总的邀请数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myInviteCount", method = RequestMethod.POST)
    public IntegerResponse myInviteCount(@RequestBody @Valid MyInviteListRequest requestBean) {
        IntegerResponse responseBean = new IntegerResponse();
        Integer count = myInviteService.countMyInviteList(requestBean.getUserId());
        responseBean.setResultInt(count);
        return responseBean;
    }
}
