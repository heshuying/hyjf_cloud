package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/10/8  15:45
 */
public interface ProtocolLogAdminCustomizeMapper {

    List<ProtocolLogVO> getProtocolLogList(ProtocolLogRequest request);

}
