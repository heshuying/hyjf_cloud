package com.hyjf.am.vo.app.reward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "奖励信息", description = "奖励信息")
public class AppRewardVO implements Serializable {
    private static final long serialVersionUID = -290987346206116536L;
    @ApiModelProperty(value = "我的奖励信息")
    private AppRewardDetailVO detail;
    @ApiModelProperty(value = "我的奖励列表")
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
