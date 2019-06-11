/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.UtmVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version ChannelService, v0.1 2018/7/9 12:00
 */
public interface ChannelService extends BaseService {
    String selectChannelName(Integer userId);

    List<Integer> getUsersInfoList();

    List<Integer> getUsersList(String sourceIdSrch);

    /**
     * 分页获取数据
     * @param map 查询参数
     * @return List<Utm>
     */
    List<UtmVO> getByPageList(Map<String,Object> map);

}
