package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.service.ActivityBatchService;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version ActivityBatchServiceImpl, v0.1 2018/4/19 15:42
 */
@Service
public class ActivityBatchServiceImpl implements ActivityBatchService {
    @Override
    public void updateActivityEndStatus() {
        //todo
//        // 检索进行中活动列表
//        List<Ads> activityList = this.activityEndService.selectActivityList();
//
//        if (activityList != null && activityList.size() > 0) {
//            for (Ads ads : activityList) {
//                // 取得活动结束时间
//                Integer endTime = GetDate.dateString2Timestamp(ads.getEndTime());
//                // 活动时间小于当前时间
//                if (endTime <= GetDate.getMyTimeInMillis()) {
//                    // 将活动结束状态更新为:已结束
//                    ads.setIsEnd(1);
//                    boolean isUpdateFlag = this.activityEndService.updateActivityEndStatus(ads);
//                    if (!isUpdateFlag) {
//                        throw new Exception("更新活动结束状态失败!");
//                    }
//                }
//            }
//        }
    }
}
