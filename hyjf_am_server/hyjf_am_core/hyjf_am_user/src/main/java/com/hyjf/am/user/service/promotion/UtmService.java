package com.hyjf.am.user.service.promotion;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.UtmVO;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface UtmService extends BaseService {
    /**
     * 分页获取数据
     * @param map 查询参数
     * @return List<Utm>
     */
    List<UtmVO> getByPageList(Map<String,Object> map);

    /**
     * 根据条件获取总条数
     * @param map 查询参数
     * @return Integer
     */
    Integer getCountByParam(Map<String,Object> map);
}
