package com.hyjf.am.user.dao.mapper.customize;

import org.apache.ibatis.annotations.Param;

public interface AccountChinapnrCustomizeMapper {
    /**
     * 根据汇付账户查询user_id
     * @param chinapnrUsrcustid
     * @return
     */
    public Integer selectUserIdByUsrcustid(@Param("chinapnrUsrcustid") Long chinapnrUsrcustid);
}

	