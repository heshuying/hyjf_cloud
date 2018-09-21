package com.hyjf.cs.user.service.wrb.impl;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.service.wrb.WrbNoticeinfoSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version WrbNoticeinfoSericeImpl, v0.1 2018/9/20 15:43
 */
@Service
public class WrbNoticeinfoSericeImpl implements WrbNoticeinfoSerice {
    @Autowired
    AmConfigClient amConfigClient;


    @Override
    public List<MessagePushTemplateVO> getNoticeinfoDetailNew(MsgPushTemplateRequest request) {
        return amConfigClient.searchList(request);
    }
}
