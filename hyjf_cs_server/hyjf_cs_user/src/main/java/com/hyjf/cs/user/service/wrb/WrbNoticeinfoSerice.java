package com.hyjf.cs.user.service.wrb;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;

import java.util.List;

/**
 * @author lisheng
 * @version WrbNoticeinfoSerice, v0.1 2018/9/20 15:42
 */

public interface WrbNoticeinfoSerice {
    /**
     * 查询平台的公告信息
     * @param request
     * @return
     */
    List<MessagePushTemplateVO> getNoticeinfoDetailNew(MsgPushTemplateRequest request);
}
