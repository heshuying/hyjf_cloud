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
public interface BatchBorrowRecoverService extends BaseService{
    /**
     * 批次中心-批次放款导出记录总数
     * @param request
     * @return
     */
    int getBatchBorrowRecoverCount(BatchBorrowRecoverRequest request);

    /**
     * 获取批次中心列表
     * @param request
     * @return
     */
    JSONObject queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request);

    /**
     * 获取批次状态列表的显示内容
     * @param listAccountDetail
     * @param nameClass
     */
    void queryBatchCenterStatusName(List<BatchBorrowRecoverVo> listAccountDetail,String nameClass);

    /**
     * 获取批次列表的求和
     * @param request
     * @return
     */
    BatchBorrowRecoverVo queryBatchCenterListSum(BatchBorrowRecoverRequest request);

    /**
     * 获取实时放款的批次交易明细
     * @param borrowNid
     * @return
     */
    List<BorrowRecoverBankInfoVo> queryBatchBorrowRecoverBankInfoList(String borrowNid);

    /**
     * 获取批次还款的批次交易明细
     * @param apicronID
     * @return
     */
    List<BatchBorrowRepayBankInfoVO> queryBatchBorrowRepayBankInfoList(String apicronID);

    /**
     * 获取资金来源
     * @return
     */
    List<HjhInstConfigVO> findHjhInstConfigList();

    /**
     * 初始化页面获取下拉列表
     * @return
     */
    JSONObject initPage(String nameClass);
}
