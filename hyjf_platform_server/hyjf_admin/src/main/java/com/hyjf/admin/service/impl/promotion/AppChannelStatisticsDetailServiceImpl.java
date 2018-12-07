package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
import com.hyjf.am.response.admin.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailServiceImpl, v0.1 2018/9/25 11:28
 */
@Service
public class AppChannelStatisticsDetailServiceImpl implements AppChannelStatisticsDetailService {
    @Resource
    private AmAdminClient amAdminClient;

    @Override
    public List<UtmPlatVO> getAppUtm(){
        return amAdminClient.getAppUtm();
    }

    @Override
    public AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request) {
        return amAdminClient.getstatisticsList(request);
    }
    @Override
    public AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request) {
        return amAdminClient.exportStatisticsList(request);
    }

    @Override
    public List<AppUtmRegVO> paging(AppChannelStatisticsDetailRequest request, List<AppUtmRegVO> result){
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
