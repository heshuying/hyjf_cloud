package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.UtmPlat;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/10/23  16:05
 */
public interface UtmPlatService {

    UtmPlat getUtmPlat(Integer sourceId);

    List<Integer> getUsersInfoList();

    List<Integer> getUsersList(String sourceIdSrch);
}
