package com.hyjf.am.vo.app.reward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "邀请信息", description = "邀请信息")
public class AppInviteVO implements Serializable {
    private static final long serialVersionUID = -5194522360940464242L;
    @ApiModelProperty(value = "我的奖励信息")
    private AppRewardDetailVO detail;
    @ApiModelProperty(value = "邀请记录列表")
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
