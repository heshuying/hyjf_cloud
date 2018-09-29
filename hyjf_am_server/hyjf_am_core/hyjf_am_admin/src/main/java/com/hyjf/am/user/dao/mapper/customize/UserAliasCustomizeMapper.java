/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.user.UserAliasVO;

import java.util.List;

/**
 * @author fuqiang
 * @version UserAliasCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface UserAliasCustomizeMapper {
    /**
     * 根据手机号查询推送别名 - 批量
     * @param mobiles
     * @return
     */
    List<UserAliasVO> findAliasByMobiles(List<String> mobiles);
}
