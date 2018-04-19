package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.AdsMapper;
import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsExample;
import com.hyjf.am.market.service.ActivityBatchService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiasq
 * @version ActivityBatchServiceImpl, v0.1 2018/4/19 15:42
 */
@Service
public class ActivityBatchServiceImpl implements ActivityBatchService {

    @Autowired
    AdsMapper adsMapper;

    @Override
    public void updateActivityEndStatus() {
        // 检索进行中活动列表
        List<Ads> activityList = this.selectActivityList();

        if (activityList != null && activityList.size() > 0) {
            for (Ads ads : activityList) {
                // 取得活动结束时间
                Integer endTime = GetDate.dateString2Timestamp(ads.getEndTime());
                // 活动时间小于当前时间
                if (endTime <= GetDate.getMyTimeInMillis()) {
                    // 将活动结束状态更新为:已结束
                    ads.setIsEnd(1);
                    adsMapper.updateByPrimaryKey(ads);
                }
            }
        }
    }

    private List<Ads> selectActivityList() {
        AdsExample example = new AdsExample();
        AdsExample.Criteria cra = example.createCriteria();
        // 是否结束状态:0:进行中,1:已结束
        cra.andIsEndEqualTo(0);
        // 状态:0:启用
        cra.andStatusEqualTo((short) 1);
        cra.andTypeidEqualTo(9);
        return adsMapper.selectByExample(example);
    }
}
