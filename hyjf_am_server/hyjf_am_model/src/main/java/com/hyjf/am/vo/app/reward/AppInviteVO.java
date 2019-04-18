package com.hyjf.am.vo.app.reward;

import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppInviteVO {

    // 我的奖励信息
    private AppRewardDetailVO detail;
    // 邀请记录列表
    private List<AppInviteRecordVO> recordList;

    public AppRewardDetailVO getDetail() {
        return detail;
    }

    public void setDetail(AppRewardDetailVO detail) {
        this.detail = detail;
    }

    public List<AppInviteRecordVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AppInviteRecordVO> recordList) {
        this.recordList = recordList;
    }
}
