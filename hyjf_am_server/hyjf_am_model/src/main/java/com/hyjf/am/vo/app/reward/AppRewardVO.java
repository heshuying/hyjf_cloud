package com.hyjf.am.vo.app.reward;

import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppRewardVO {

    // 我的奖励信息
    private AppRewardDetailVO detail;
    // 我的奖励列表
    private List<AppRewardRecordVO> recordList;

    public AppRewardDetailVO getDetail() {
        return detail;
    }

    public void setDetail(AppRewardDetailVO detail) {
        this.detail = detail;
    }

    public List<AppRewardRecordVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AppRewardRecordVO> recordList) {
        this.recordList = recordList;
    }
}
