/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.promotion.ChannelReconciliationService;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.response.app.AppChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version ChannelReconciliationServiceImpl, v0.1 2018/9/21 9:57
 */
@Service
public class ChannelReconciliationServiceImpl implements ChannelReconciliationService {
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmAdminClient amAdminClient;
    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public List<UtmVO> searchUtmList(int sourceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("sourceType", sourceType);
        UtmResponse response = amUserClient.getByPageList(map);
        if (response != null) {
            return response.getResultListS();
        }
        return null;
    }

    @Override
    public ChannelReconciliationResponse searchAction(ChannelReconciliationRequest request) {
        return amAdminClient.selectPcChannelReconciliationRecord(request);
    }

    @Override
    public ChannelReconciliationResponse searchHJHAction(ChannelReconciliationRequest request) {
        return amAdminClient.selectPcChannelReconciliationRecordHjh(request);
    }

    @Override
    public ChannelReconciliationResponse searchAppAction(ChannelReconciliationRequest request) {

        // app渠道信息
        AppChannelStatisticsDetailRequest request1 = new AppChannelStatisticsDetailRequest();
        if (request.getUtmPlat() != null) {
            request1.setSourceIdSrch(Integer.valueOf(request.getUtmPlat()[0]));
        }
        AppChannelStatisticsDetailResponse appResponse = csMessageClient.exportStatisticsList(request1);
        if (appResponse != null) {
            List<AppChannelStatisticsDetailVO> appResultList = appResponse.getResultList();
            if (!CollectionUtils.isEmpty(appResultList)) {
                List<Integer> userIdList = new ArrayList<>();
                for (AppChannelStatisticsDetailVO appVo: appResultList) {
                    Integer userId = appVo.getUserId();
                    userIdList.add(userId);
                }
                request.setUserIdList(userIdList);
            } else {
                return new ChannelReconciliationResponse();
            }
        } else {
            return new ChannelReconciliationResponse();
        }
        // 投资信息
        ChannelReconciliationResponse response = amAdminClient.selectAppChannelReconciliationRecord(request);
        if (response != null) {
            List<ChannelReconciliationVO> resultList = response.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                for (ChannelReconciliationVO vo : resultList) {
                    if (appResponse != null) {
                        List<AppChannelStatisticsDetailVO> appResultList = appResponse.getResultList();
                        if (!CollectionUtils.isEmpty(appResultList)) {
                            for (AppChannelStatisticsDetailVO appVo: appResultList) {
                                if (appVo.getUserId().equals(vo.getUserId())) {
                                    vo.setUtmName(appVo.getSourceName());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return response;
    }

    @Override
    public ChannelReconciliationResponse searchAppHJHAction(ChannelReconciliationRequest request) {
        // app渠道信息
        AppChannelStatisticsDetailRequest request1 = new AppChannelStatisticsDetailRequest();
        if (request.getUtmPlat() != null) {
            request1.setSourceIdSrch(Integer.valueOf(request.getUtmPlat()[0]));
        }
        AppChannelStatisticsDetailResponse appResponse = csMessageClient.exportStatisticsList(request1);
        if (appResponse != null) {
            List<AppChannelStatisticsDetailVO> appResultList = appResponse.getResultList();
            if (!CollectionUtils.isEmpty(appResultList)) {
                List<Integer> userIdList = new ArrayList<>();
                for (AppChannelStatisticsDetailVO appVo: appResultList) {
                    Integer userId = appVo.getUserId();
                    userIdList.add(userId);
                }
                request.setUserIdList(userIdList);
            } else {
                return new ChannelReconciliationResponse();
            }
        } else {
            return new ChannelReconciliationResponse();
        }
        // 投资信息
        ChannelReconciliationResponse response = amAdminClient.selectAppChannelReconciliationRecordHjh(request);
        if (response != null) {
            List<ChannelReconciliationVO> resultList = response.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                for (ChannelReconciliationVO vo : resultList) {
                    if (appResponse != null) {
                        List<AppChannelStatisticsDetailVO> appResultList = appResponse.getResultList();
                        if (!CollectionUtils.isEmpty(appResultList)) {
                            for (AppChannelStatisticsDetailVO appVo: appResultList) {
                                if (appVo.getUserId().equals(vo.getUserId())) {
                                    vo.setUtmName(appVo.getSourceName());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return response;
    }
}
