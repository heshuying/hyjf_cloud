/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;

/**
 * @author fq
 * @version ContentArticleCustomize, v0.1 2018/7/20 9:52
 */
public class ContentArticleCustomize implements Serializable {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -5941155860135801612L;
    //标题
    private String title;
    //时间
    private String time;
    //网贷知识id
    private String messageId;
    //网贷知识详情页url
    private String messageUrl;
    //分享标题
    private String shareTitle;
    //分享内容
    private String shareContent;
    //分享图片url
    private String sharePicUrl;
    //分享url
    private String shareUrl;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getMessageUrl() {
        return messageUrl;
    }
    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }
    public String getShareTitle() {
        return shareTitle;
    }
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
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

    @Override
    public String toString() {
        return "ContentArticleCustomize{" +
                "title='" + title + '\'' +
                ", messageUrl='" + messageUrl + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }
}
