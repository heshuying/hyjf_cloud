package com.hyjf.admin.service.promotion;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.promotion.UtmResultResponse;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface UtmService {
    /**
     * 分页获取数据
     * @param map 参数
     * @param currPage 页数
     * @param pageSize 条数
     * @return PageList
     */
    UtmResultResponse getByPageList(Map<String,Object> map, Integer currPage, Integer pageSize);

    /**
     * @Author walter.limeng
     * @Description  根据主键ID获取对象
     * @Date 10:48 2018/7/16
     * @Param 
     * @return 
     */
    UtmPlatVO getDataById(Integer id);

    /**
     * @Author walter.limeng
     * @Description  根据sourceName和sourceId验证是否重复
     * @Date 11:15 2018/7/16
     * @Param sourceName
     * @Param sourceId
     * @return
     */
    int sourceNameIsExists(String sourceName, Integer sourceId);

    /**
     * @Author walter.limeng
     * @Description  新增或者修改对象
     * @Date 11:24 2018/7/16
     * @Param utmPlatVO
     * @return
     */
    boolean insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO);

    /**
     * @Author walter.limeng
     * @Description  删除对象
     * @Date 11:45 2018/7/16
     * @Param utmPlatVO
     * @return 
     */
    boolean deleteUtmPlatAction(UtmPlatVO utmPlatVO);

    /**
     * @Author cwyang
     * 检查编号唯一性
     * @param sourceId
     * @return
     */
    int sourceIdIsExists(Integer sourceId);
}
