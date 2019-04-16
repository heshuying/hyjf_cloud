package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.WorkName;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;

import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 11:37
 * @Version 1.0
 */
public interface BusinessNameMgAmService {

    int getTotalCount(BusinessNameMgRequest request);

    List<WorkName> getListBs(BusinessNameMgRequest request, int limitStart, int limitEnd);

    int insertBs(BusinessNameMgRequest request);

    WorkName findListBsById(int id);

    int updateBs(BusinessNameMgRequest request);

    int updateStatusBs(BusinessNameMgRequest request);
}
