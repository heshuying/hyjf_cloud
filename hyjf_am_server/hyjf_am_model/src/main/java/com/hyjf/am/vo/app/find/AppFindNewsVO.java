package com.hyjf.am.vo.app.find;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "公司动态", description = "发现页公司动态")
public class AppFindNewsVO implements Serializable {
    private static final long serialVersionUID = 4460355512524623472L;
    @ApiModelProperty(value = "公司动态主键")
    private Integer id;
    @ApiModelProperty(value = "文章图片", example = "null")
    private String img;
    @ApiModelProperty(value = "文章标题")
    private String title;
    @ApiModelProperty(value = "发布日期", example = "2018-10-24")
    private String date;
    @ApiModelProperty(value = "文章简介")
    private String shareContent;
    @ApiModelProperty(value = "分享图片", example = "https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png")
    private String sharePicUrl;
    @ApiModelProperty(value = "文章地址", example = "https://app.hyjf.com/...")
    private String shareUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getSharePicUrl() {
        return sharePicUrl;
    }

    public void setSharePicUrl(String sharePicUrl) {
        this.sharePicUrl = sharePicUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
