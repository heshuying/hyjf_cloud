package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.WorkName;
import com.hyjf.am.response.config.BusinessNameMgResponse;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;
import com.hyjf.am.vo.config.WorkNameVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 11:37
 * @Version 1.0
 */
public interface BusinessNameMgAmService {

    int getTotalCount(BusinessNameMgRequest request);

    List<WorkName> getListBs(BusinessNameMgRequest request, int limitStart, int limitEnd);

    List<WorkName> findNameUq(BusinessNameMgRequest request);

    int insertBs(BusinessNameMgRequest request);

    WorkName findListBsById(int id);

    int updateBs(BusinessNameMgRequest request);

    int updateStatusBs(BusinessNameMgRequest request);
    /**
     * 查询业务名称
     */
    List<WorkNameVO> searchBusinessName(String businessName);
}
