package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.InvitePrizeConfCustomizeMapper;
import com.hyjf.am.market.service.InvitePrizeConfigService;
import com.hyjf.am.resquest.trade.InvitePrizeConfVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 19:31
 * @Description: InvitePrizeConfigServiceImpl
 */
@Service
public class InvitePrizeConfigServiceImpl implements InvitePrizeConfigService {

    @Resource
    private InvitePrizeConfCustomizeMapper invitePrizeConfCustomizeMapper;
    @Override
    public List<InvitePrizeConfVO> getListByGroupCode(String groupCode) {
        Map<String,Object> param = new HashMap<>();
        param.put("groupCode",groupCode);
        return invitePrizeConfCustomizeMapper.getListByGroupCode(param);
    }
}
