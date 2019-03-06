package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelStatisticsDetailServiceImpl implements ChannelStatisticsDetailService {
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AmAdminClient amAdminClient;
    @Override
    public JSONObject searchAction(ChannelStatisticsDetailRequest request) {
        JSONObject response = new  JSONObject();
        List<ChannelStatisticsDetailVO> list = new ArrayList<>();
        int total = 0;
        int flag = 0;
        // 查询条件
        // 渠道
        String[] utmIds = new String[] {};
        if (Validator.isNotNull(request.getUtmIds())) {
            if (request.getUtmIds().contains(StringPool.COMMA)) {
                utmIds = request.getUtmIds().split(StringPool.COMMA);
                request.setUtmIdsSrch(utmIds);
            } else {
                utmIds = new String[] { request.getUtmIds() };
                request.setUtmIdsSrch(utmIds);
            }
            flag = 1;
        }
        request.setStartTime(request.getStartTime() == null ? null : request.getStartTime() + " 00:00:00");
        request.setEndTime(request.getEndTime() == null ? null : request.getEndTime() + " 23:59:59");
        IntegerResponse count = this.amAdminClient.countList(request);
        if (count.getResultInt()!=null&&count.getResultInt() > 0) {
            total = count.getResultInt();
                Paginator paginator = new Paginator(request.getCurrPage(), count.getResultInt(), request.getPageSize() == 0 ? 10 : request.getPageSize());
            if(request.getLimitStart()!=-1  ) {
                request.setLimitStart(paginator.getOffset());
                request.setLimitEnd(paginator.getLimit());
            }
            ChannelStatisticsDetailResponse channelStatisticsDetailResponse = this.amAdminClient.searchAction(request);
            list = channelStatisticsDetailResponse.getResultList();
        }
        response.put("count",total);
        response.put("flag", flag);
        response.put("recordList", list);
        response.put("UtmPlatList", amAdminClient.getPCUtm());
        return response;
    }
    @Override
    public  AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId){
        AdminUtmReadPermissionsVO vo = amConfigClient.selectAdminUtmReadPermissions(userId);
        return  vo;
    }

}
