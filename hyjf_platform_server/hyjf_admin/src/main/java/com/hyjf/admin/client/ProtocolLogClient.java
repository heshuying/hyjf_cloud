package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/10  14:33
 */
public interface ProtocolLogClient {

    /**
     *  统计模板日志
     *
     * @return
     */
    Integer countRecordLog(ProtocolLogRequest request);

    /**
     * 查询所有协议日志
     * @date 2018/7/4 15:38
     */
    List<ProtocolLogVO> getProtocolLogVOAll(ProtocolLogRequest request);
}
