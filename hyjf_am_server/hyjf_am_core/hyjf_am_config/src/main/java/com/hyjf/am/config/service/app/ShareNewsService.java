package com.hyjf.am.config.service.app;

import com.hyjf.am.vo.market.ShareNewsBeanVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/27 09:22
 * @Description: ShareNewsService
 */
public interface ShareNewsService {

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  获取分享信息
     * @Date 9:24 2018/7/27
     * @Param
     * @return ShareNewsBeanVO
     */
    ShareNewsBeanVO queryShareNews();
}
