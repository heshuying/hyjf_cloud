package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.trade.SubCommissionListConfigVo;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/10.
 */
public interface SubConfigCustomizeMapper {
    /**
     * 保证金配置，根据用户名称查询用户信息
     *
     * @param userName
     * @return
     */
    Map<String, Object> selectUserInfoByUserName(String userName);
    /**
     * 根据用户名查询分账名单是否存在
     * @param subCommissionListConfigCustomize
     * @return
     */
    List<SubCommissionListConfigVo>  selectByExampleUsername(SubCommissionListConfigVo subCommissionListConfigCustomize);
}
