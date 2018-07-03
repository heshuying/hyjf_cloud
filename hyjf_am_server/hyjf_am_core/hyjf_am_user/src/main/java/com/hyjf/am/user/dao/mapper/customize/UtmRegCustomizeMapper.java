/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.admin.UtmVo;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author sunss
 * @Date 2018/6/23 11:08
 */
public interface UtmRegCustomizeMapper {

    /**
     * 更新huiyingdai_utm_reg相应的用户信息
     *
     * @param params
     */
    void updateFirstUtmReg(Map<String, Object> params);

    /**
     * 获取数据
     * @param map 查询参数
     * @return List<UtmPlat>
     */
    List<UtmVo> getByPageList(Map<String,Object> map);

    /**
     * 根据条件获取总条数
     * @param map 查询参数
     * @return Integer
     */
    Integer getCountByParam(Map<String,Object> map);
}
