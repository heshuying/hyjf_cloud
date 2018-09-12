package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.ProtocolTemplate;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/31  14:40
 */
public interface ProtocolTemplateCustomizeMapper {


    List<ProtocolTemplate> getdisplayNameDynamic();

    ProtocolTemplate selectTemplateById(String protocolId);

    int startUseExistProtocol(ProtocolTemplate protocolTemplate);
}
