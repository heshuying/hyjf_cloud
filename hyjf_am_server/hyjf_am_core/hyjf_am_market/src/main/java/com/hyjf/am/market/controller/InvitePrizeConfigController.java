package com.hyjf.am.market.controller;

import com.hyjf.am.market.service.InvitePrizeConfigService;
import com.hyjf.am.response.market.InvitePrizeConfResponse;
import com.hyjf.am.resquest.trade.InvitePrizeConfVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 19:27
 * @Description: InvitePrizeConfigController
 */
@RestController
@RequestMapping("/am-market/inviteprizeconfig")
public class InvitePrizeConfigController {

    @Autowired
    private InvitePrizeConfigService invitePrizeConfigService;

    @RequestMapping("/getlistbygroupcode/{groupCode}")
    public InvitePrizeConfResponse findActivityById(@PathVariable String groupCode) {
        InvitePrizeConfResponse response = new InvitePrizeConfResponse();
        List<InvitePrizeConfVO> list = invitePrizeConfigService.getListByGroupCode(groupCode);
        response.setResultList(list);
        return response;
    }
}
