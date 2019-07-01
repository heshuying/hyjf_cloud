package com.hyjf.am.vo.app.reward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "奖励记录", description = "奖励记录")
public class AppRewardRecordVO implements Serializable {
    private static final long serialVersionUID = 6608276276834175783L;
    @ApiModelProperty(value = "好友名称")
    private String friendName;
    @ApiModelProperty(value = "奖励来源", example = "好友出借")
    private String source;
    @ApiModelProperty(value = "我的奖励", example = "X元现金")
    private String content;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
