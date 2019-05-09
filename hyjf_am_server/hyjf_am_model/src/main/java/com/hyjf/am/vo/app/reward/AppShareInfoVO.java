package com.hyjf.am.vo.app.reward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/5/9
 */
@ApiModel(value = "分享信息", description = "分享信息")
public class AppShareInfoVO implements Serializable {
    private static final long serialVersionUID = -3554111929858661605L;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "分享图片", example = "https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png")
    private String image;
    @ApiModelProperty(value = "跳转地址", example = "https://app.hyjf.com/...")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
