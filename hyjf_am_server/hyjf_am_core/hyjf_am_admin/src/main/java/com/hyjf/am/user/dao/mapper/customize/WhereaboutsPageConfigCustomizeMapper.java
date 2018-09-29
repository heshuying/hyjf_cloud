package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.admin.WhereaboutsPageRequest;
import com.hyjf.am.vo.config.WhereaboutsPageVo;

import java.util.List;

/**
 * @author tanyy
 * @version WhereaboutsPageConfigCustomizeMapper, v0.1 2018/7/16 17:25
 */

public interface WhereaboutsPageConfigCustomizeMapper {
    /**
     * 取得移动端着陆页管理总数量
     *
     * @return
     */
    int countWhereaboutsPage(WhereaboutsPageRequest request);

    /**
     * 根据查询条件 取得移动端着陆页管理
     * @param request
     * @return
     */
    List<WhereaboutsPageVo> selectWhereaboutsPageList(WhereaboutsPageRequest request);

}
