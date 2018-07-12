/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Team;
import com.hyjf.am.resquest.admin.TeamRequest;

/**
 * @author fuqiang
 * @version TeamService, v0.1 2018/7/9 16:46
 */
public interface TeamService {
    /**
     * 获取创始人信息 （最新数据）
     *
     * @return
     */
    Team getFounder();

    /**
     * 根据条件查询公司管理-团队介绍
     *
     * @param request
     * @return
     */
    List<Team> searchAction(TeamRequest request);

    /**
     * 添加公司管理-团队介绍
     *
     * @param request
     */
    void insertAction(TeamRequest request);

    /**
     * 修改公司管理-团队介绍
     *
     * @param request
     */
    void updateAction(TeamRequest request);

    /**
     * 根据id查询公司管理-团队介绍
     *
     * @param id
     * @return
     */
    Team getRecord(Integer id);
}
