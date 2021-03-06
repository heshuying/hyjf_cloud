package com.hyjf.cs.message.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.message.AppAccesStatisticsResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.access.AppChannelStatisticsService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * APP渠道统计
 * @author：yinhui
 * @Date: 2018/10/22  16:33
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/app_channel_statistics")
public class AppChannelStatisticsController  extends BaseController {

    @Autowired
    private AppChannelStatisticsService appChannelStatisticsService;

    /**
     * 根据开始时间、结束时间和来源查询数据
     * @return
     */
    @RequestMapping("/getAccessNumber")
    public AppAccesStatisticsResponse getAccessNumber(@RequestBody AppChannelStatisticsRequest request) {
        AppAccesStatisticsResponse response = new AppAccesStatisticsResponse();
        List<AppAccesStatisticsVO>  list = appChannelStatisticsService.getAppAccesStatisticsVO(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }
}
