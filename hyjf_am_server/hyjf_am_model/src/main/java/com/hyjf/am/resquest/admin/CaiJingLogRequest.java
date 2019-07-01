/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CaiJingLogRequest, v0.1 2019/6/10 9:20
 */
@ApiModel(value = "查询财经日志")
public class CaiJingLogRequest extends BasePage implements Serializable {

    private static final long serialVersionUID = 3803722754627032581L;

    @ApiModelProperty(value = "报送类型")
    private String logType;

    @ApiModelProperty(value = "报送状态")
    private Integer status;

    @ApiModelProperty(value = "报送时间起始")
    private String presentationTimeStart;

    @ApiModelProperty(value = "报送时间结束")
    private String presentationTimeEnd;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPresentationTimeStart() {
        return presentationTimeStart;
    }

    public void setPresentationTimeStart(String presentationTimeStart) {
        this.presentationTimeStart = presentationTimeStart;
    }

    public String getPresentationTimeEnd() {
        return presentationTimeEnd;
    }

    public void setPresentationTimeEnd(String presentationTimeEnd) {
        this.presentationTimeEnd = presentationTimeEnd;
    }
}
