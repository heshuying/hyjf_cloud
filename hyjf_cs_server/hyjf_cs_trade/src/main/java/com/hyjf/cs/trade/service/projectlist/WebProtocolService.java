package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.cs.trade.bean.ProtocolRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface WebProtocolService {

    /**
     * 下载脱敏后居间服务借款协议（原始标的）_计划投资人
     * @author zhangyk
     * @date 2018/10/18 11:34
     */
    public File creditPaymentPlan(ProtocolRequest form, Integer userId, HttpServletRequest request, HttpServletResponse response);
}
