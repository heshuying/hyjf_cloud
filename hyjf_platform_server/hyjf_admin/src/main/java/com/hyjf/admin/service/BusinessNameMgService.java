package com.hyjf.admin.service;

import com.hyjf.am.response.config.BusinessNameMgResponse;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;
import com.hyjf.am.vo.config.WorkNameVO;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 9:54
 * @Version 1.0
 */
public interface BusinessNameMgService {

    boolean insertBusinessName(BusinessNameMgRequest request);

    BusinessNameMgResponse searchBusinessName(BusinessNameMgRequest request);

    WorkNameVO findBusinessNameById(int id);

    boolean updateBusinessName(BusinessNameMgRequest request);

    boolean updateStatusBusinessName(BusinessNameMgRequest request);

    boolean searchBusinessNameUq(BusinessNameMgRequest request,String fun);

    /**
     * 查询业务名称
     * @param businessName
     * @return
     */
    BusinessNameMgResponse searchBusinessNameList(BusinessNameMgRequest request);
}
