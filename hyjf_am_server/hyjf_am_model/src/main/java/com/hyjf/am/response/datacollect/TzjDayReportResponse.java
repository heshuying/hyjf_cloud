package com.hyjf.am.response.datacollect;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;

import java.util.Set;

/**
 * @author xiasq
 * @version TzjDayReportResponse, v0.1 2018/7/6 17:48
 */
public class TzjDayReportResponse extends Response<TzjDayReportVO> {
    private Set<Integer> userIds;

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }
}
