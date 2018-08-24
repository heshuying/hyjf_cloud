/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author fq
 * @version MessagePushPlatStaticsRequest, v0.1 2018/8/14 15:49
 */
public class MessagePushPlatStaticsRequest extends BasePage {
    /**
     * 标签查询
     */
    @ApiModelProperty(value = "标签")
    private String tagIdSrch;
    /**
     * 时间查询
     */
    @ApiModelProperty(value = "开始时间")
    private String startDateSrch;
    /**
     * 时间查询
     */
    @ApiModelProperty(value = "结束时间")
    private String endDateSrch;

    public String getTagIdSrch() {
        return tagIdSrch;
    }

    public void setTagIdSrch(String tagIdSrch) {
        this.tagIdSrch = tagIdSrch;
    }

    public String getStartDateSrch() {
        return startDateSrch;
    }

    public void setStartDateSrch(String startDateSrch) {
        this.startDateSrch = startDateSrch;
    }

    public String getEndDateSrch() {
        return endDateSrch;
    }

    public void setEndDateSrch(String endDateSrch) {
        this.endDateSrch = endDateSrch;
    }
}
