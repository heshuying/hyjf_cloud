package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.ProtocolLogService;
import com.hyjf.am.response.admin.ProtocolLogResponse;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import com.hyjf.am.vo.config.AdminSystemVO;
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
	AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @Override
    public ProtocolLogResponse searchPage(ProtocolLogRequest request) {
        ProtocolLogResponse response = new ProtocolLogResponse();
        List<ProtocolLogVO> recordList = null;
        Integer count = amTradeClient.countRecordLog(request);
        response.setCount(count);
        if (count.intValue()>0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize()==0?10:request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
            recordList = amTradeClient.getProtocolLogVOAll(request);

            if(recordList.size()>0){
                ProtocolLogVO vo = null;
                for(int i=0;i<recordList.size();i++){
                    vo = recordList.get(i);
                    AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(vo.getUpdateUser());
                    if(adminSystemVO != null){
                        vo.setUserName(adminSystemVO.getUsername());
                    }else{
                        vo.setUserName("admin");
                    }

                }
            }

            response.setResultList(recordList);
        }
        return response;
    }
}
