package com.hyjf.am.config.dao.model.customize;

import com.hyjf.am.config.dao.model.auto.Submissions;

import java.io.Serializable;

/**
 * @author lisheng
 * @version SubmissionsWithBLOBs, v0.1 2018/7/18 16:28
 */

public class SubmissionsWithBLOBs extends Submissions implements Serializable {
    private String content;

    private String img;

    private String reply;

    private static final long serialVersionUID = 1L;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String getImg() {
        return img;
    }

    @Override
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    @Override
    public String getReply() {
        return reply;
    }

    @Override
    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

}
