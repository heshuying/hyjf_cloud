/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateResponse, v0.1 2018/5/8 10:34
 */
public class MessagePushTemplateResponse extends Response<MessagePushTemplateVO> {

    private int count;

    private List<MessagePushTagVO> templatePushTags;

    private List<ParamNameVO> templateStatus;

    private List<ParamNameVO> templateActions;

    private List<ParamNameVO> naturePages;

    private List<ParamNameVO> plats;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MessagePushTagVO> getTemplatePushTags() {
        return templatePushTags;
    }

    public void setTemplatePushTags(List<MessagePushTagVO> templatePushTags) {
        this.templatePushTags = templatePushTags;
    }

    public List<ParamNameVO> getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(List<ParamNameVO> templateStatus) {
        this.templateStatus = templateStatus;
    }

    public List<ParamNameVO> getTemplateActions() {
        return templateActions;
    }

    public void setTemplateActions(List<ParamNameVO> templateActions) {
        this.templateActions = templateActions;
    }

    public List<ParamNameVO> getNaturePages() {
        return naturePages;
    }

    public void setNaturePages(List<ParamNameVO> naturePages) {
        this.naturePages = naturePages;
    }

    public List<ParamNameVO> getPlats() {
        return plats;
    }

    public void setPlats(List<ParamNameVO> plats) {
        this.plats = plats;
    }
}
