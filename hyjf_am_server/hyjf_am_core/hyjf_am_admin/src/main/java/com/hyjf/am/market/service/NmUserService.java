package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.NmUser;

import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/1/22 15:15
 * @Version 1.0
 */
public interface NmUserService {

    List<NmUser> selectNmUserList(NmUser nmUser);
}
