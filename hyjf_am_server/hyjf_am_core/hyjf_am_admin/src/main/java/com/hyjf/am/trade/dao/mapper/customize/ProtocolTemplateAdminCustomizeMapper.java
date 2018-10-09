package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.ProtocolTemplate;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/10/8  15:45
 */
public interface ProtocolTemplateAdminCustomizeMapper {

    List<ProtocolTemplate> getdisplayNameDynamic();

    ProtocolTemplate selectTemplateById(String protocolId);

    int startUseExistProtocol(ProtocolTemplate protocolTemplate);
}
