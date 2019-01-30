/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author fq
 * @version PcChannelStatisticsRequest, v0.1 2018/9/26 14:15
 */
public class PcChannelStatisticsRequest extends BasePage {

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @ApiModelProperty(value = "渠道类别")
    private String utmIds;

    @ApiModelProperty(value = "渠道")
    private String[] utmIdsSrch;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUtmIds() {
        return utmIds;
    }

    public void setUtmIds(String utmIds) {
        this.utmIds = utmIds;
    }

    public String[] getUtmIdsSrch() {
        return utmIdsSrch;
    }

    public void setUtmIdsSrch(String[] utmIdsSrch) {
        this.utmIdsSrch = utmIdsSrch;
    }
}
