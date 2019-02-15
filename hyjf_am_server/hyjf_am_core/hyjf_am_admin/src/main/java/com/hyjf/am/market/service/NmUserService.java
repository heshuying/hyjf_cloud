package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.InviterReturnDetail;
import com.hyjf.am.market.dao.model.auto.NmUser;
import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.response.IntegerResponse;

import java.util.List;
import java.util.Map;

/**
 * @Author: yinhui
 * @Date: 2019/1/22 15:15
 * @Version 1.0
 */
public interface NmUserService {

    List<NmUser> selectNmUserList(NmUser nmUser);

    IntegerResponse saveReutrnCash(Map<String,Object> map);

    void updateJoinTime(Integer nowTime, List<InviterReturnDetail> inviterReturnDetailList, List<PerformanceReturnDetail> performanceReturnDetailList);

}
