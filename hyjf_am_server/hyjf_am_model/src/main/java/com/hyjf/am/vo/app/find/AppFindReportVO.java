package com.hyjf.am.vo.app.find;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/24
 */
@ApiModel(value = "运营报告", description = "发现页运营报告")
public class AppFindReportVO implements Serializable {
    private static final long serialVersionUID = 4414779102541772702L;
    @ApiModelProperty(value = "运营报告主键")
    private String id;
    @ApiModelProperty(value = "运营报告标题",example = "年度/上半年/一季度/1月运营报告")
    private String title;
    @ApiModelProperty(value = "运营报告日期", example = "2018年12月31日")
    private String date;
    @ApiModelProperty(value = "运营报告链接地址", example = "https://app.hyjf.com/...")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
