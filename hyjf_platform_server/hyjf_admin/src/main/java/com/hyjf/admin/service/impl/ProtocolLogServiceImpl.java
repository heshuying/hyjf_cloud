package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.ProtocolLogClient;
import com.hyjf.admin.service.ProtocolLogService;
import com.hyjf.am.response.admin.ProtocolLogResponse;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/10  13:58
 */
@Service
public class ProtocolLogServiceImpl implements ProtocolLogService {


    @Autowired
    private ProtocolLogClient client;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @Override
    public ProtocolLogResponse searchPage(ProtocolLogRequest request) {
        ProtocolLogResponse response = new ProtocolLogResponse();
        List<ProtocolLogVO> recordList = null;
        Integer count = client.countRecordLog(request);
        response.setCount(count);
        if (count.intValue()>0) {
            Paginator paginator = new Paginator(request.getPaginatorPage(), count);
            recordList = client.getProtocolLogVOAll(request);
            response.setResultList(recordList);
        }
        return response;
    }
}
