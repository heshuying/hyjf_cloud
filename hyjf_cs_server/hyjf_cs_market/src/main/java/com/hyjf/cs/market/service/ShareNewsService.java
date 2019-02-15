package com.hyjf.cs.market.service;

import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.user.UserUtmInfoCustomizeVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 17:03
 * @Description: ShareNewsService
 */
public interface ShareNewsService {
    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  获取分享信息
     * @Date 9:05 2018/7/27
     * @Param 
     * @return ShareNewsBeanVO
     */
    ShareNewsBeanVO queryShareNews();

    /**
     * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
     * @param userId
     * @return
     * @Author : huanghui
     */
    UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId);
}
