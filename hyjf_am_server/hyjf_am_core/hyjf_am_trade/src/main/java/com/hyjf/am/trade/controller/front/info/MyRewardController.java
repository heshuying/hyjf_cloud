package com.hyjf.am.trade.controller.front.info;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.MyRewardListResponse;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.trade.MyRewardService;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 我的邀请及奖励
 * @author hesy
 * @version MyInviteController, v0.1 2018/6/23 12:19
 */
@RestController
@RequestMapping("/am-trade/reward")
public class MyRewardController extends BaseController {
    @Autowired
    MyRewardService myRewardService;

    /**
     * 我的奖励
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myRewardList", method = RequestMethod.POST)
    public MyRewardListResponse myRewardList(@RequestBody @Valid MyInviteListRequest requestBean) {
        MyRewardListResponse responseBean = new MyRewardListResponse();

        List<MyRewardRecordCustomizeVO> resultList = myRewardService.selectMyRewardList(requestBean.getUserId(),requestBean.getLimitStart(),requestBean.getLimitEnd());
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 奖励总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myRewardCount", method = RequestMethod.POST)
    public Response<Integer> myRewardCount(@RequestBody @Valid MyInviteListRequest requestBean) {
        Integer countResult =  myRewardService.countMyRewardTotal(requestBean.getUserId());
        return new Response<>(countResult);
    }

    /**
     * 统计奖励总金额
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myRewardTotal", method = RequestMethod.POST)
    public BigDecimalResponse myRewardTotal(@RequestBody @Valid MyInviteListRequest requestBean) {
        BigDecimalResponse responseBean = new BigDecimalResponse();
        BigDecimal value = myRewardService.sumMyRewardTotal(requestBean.getUserId());
        responseBean.setResultDec(value);
        return responseBean;
    }
}
