package com.hyjf.cs.trade.service.aems.borrow;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.AemsBorrowDetailRequestBean;
import com.hyjf.cs.trade.bean.AemsBorrowListRequestBean;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;


public interface AemsBorrowService {

    /**
    *  查询标的列表
    * @author Zha Daojian
    * @date 2018/12/17 9:41
    * @param reqBean
    * @return com.hyjf.cs.common.bean.result.ApiResult
    **/
    ApiResult getBorrowList(AemsBorrowListRequestBean reqBean);

    /**
    * 查询标的详情
    * @author Zha Daojian
    * @date 2018/12/17 9:41
    * @param reqBean
    * @return com.hyjf.cs.common.bean.result.ApiResult
    **/
    ApiResult getBorrowDetail(AemsBorrowDetailRequestBean reqBean);
}
