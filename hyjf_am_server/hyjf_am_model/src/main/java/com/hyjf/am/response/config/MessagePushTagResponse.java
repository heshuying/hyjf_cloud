/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTagResponse, v0.1 2018/6/26 11:45
 */
public class MessagePushTagResponse extends Response<MessagePushTagVO> {
    private int count;

    List<ParamNameVO> paramNameVOS;

    private String fileDomainUrl;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ParamNameVO> getParamNameVOS() {
        return paramNameVOS;
    }

    public void setParamNameVOS(List<ParamNameVO> paramNameVOS) {
        this.paramNameVOS = paramNameVOS;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }
}
