package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BatchBorrowRepayBankInfoVO;
import com.hyjf.am.vo.admin.BorrowRecoverBankInfoVo;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 批次中心-批次放款接口
 */
public interface BatchBorrowRecoverLogService extends BaseService{
    JSONObject queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request);

    void queryBatchCenterStatusName(List<BatchBorrowRecoverVo> listAccountDetail, String nameClass);

    JSONObject initPage(String nameClass);

    List<HjhInstConfigVO> findHjhInstConfigList();
}
