/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.promotion.AppChannelStatisticsService;
import com.hyjf.am.response.app.AppChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoyong
 * @version AppChannelStatisticsServiceImpl, v0.1 2018/9/21 9:42
 */
@Service
public class AppChannelStatisticsServiceImpl implements AppChannelStatisticsService {

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public AppChannelStatisticsResponse searchList(AppChannelStatisticsRequest statisticsRequest) {
        return csMessageClient.searchList(statisticsRequest);
    }

    @Override
    public AppChannelStatisticsResponse exportList(AppChannelStatisticsRequest statisticsRequest) {
        return csMessageClient.exportList(statisticsRequest);
    }

    @Override
    public List<AppChannelStatisticsVO> paging(AppChannelStatisticsRequest request, List<AppChannelStatisticsVO> result){
        int current=request.getCurrPage(); //页码
        int pageSize=request.getPageSize(); //每页显示的数量
        int totalCount=result.size();
        int pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

        if(current < 1){
            current = 1;
        }
        int start=(current-1) * pageSize;
        int end = Math.min(totalCount, current * pageSize);

        if(pageCount >= current){
            result=result.subList(start,end);
        }else{
            result = new ArrayList<>();
        }

        return result;
    }
}
