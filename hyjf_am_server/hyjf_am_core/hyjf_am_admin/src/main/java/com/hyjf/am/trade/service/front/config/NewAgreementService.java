/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.config;


import com.hyjf.am.bean.app.NewAgreementResultBean;

/**
 * @author dangzw
 * @version NewAgreementService, v0.1 2018/7/31 17:41
 */
public interface NewAgreementService {
    /**
     * 获得 协议模板pdf显示地址
     * @param aliasName
     * @return
     */
    NewAgreementResultBean setProtocolImg(String aliasName);
}
