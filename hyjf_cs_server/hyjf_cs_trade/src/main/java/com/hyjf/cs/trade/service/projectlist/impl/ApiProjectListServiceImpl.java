package com.hyjf.cs.trade.service.projectlist.impl;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.service.projectlist.ApiProjectListService;
import org.springframework.stereotype.Service;

/**
 * api端 业务service
 * @author zhangyk
 * @date 2018/8/27 10:06
 */
@Service
public class ApiProjectListServiceImpl implements ApiProjectListService {


    /**
     * 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 10:12
     */
    @Override
    public ApiResult getBorrowList(ApiBorrowReqBean reqBean) {
        ApiResult result = new ApiResult();
        return result;
    }
}
