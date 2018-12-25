package com.hyjf.am.user.dao.mapper.customize;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lisheng
 * @version QianleUserCustomizeMapper, v0.1 2018/8/21 18:06
 */

public interface QianleUserCustomizeMapper {
    List<Integer> queryQianleUser(@Param(value = "sourceId") String sourceId);
}
