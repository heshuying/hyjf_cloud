package com.hyjf.admin.service.exception;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import java.util.List;

/**
 * @author hesy
 * @version HjhCreditEndExceptionService, v0.1 2018/7/12 20:01
 */
public interface HjhCreditEndExceptionService extends BaseService {
    /**
     * 根据creditNid查询债转信息
     * @param creditNid
     * @return
     */
    HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid);
    /**
     * 请求结束债权（追加结束债权任务记录）
     * @param credit
     * @param tenderAccountId
     * @param tenderAuthCode
     * @return
     * @throws Exception
     */
    boolean requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) throws Exception;
    /**
     * 银行结束债权后，更新债权表为完全承接
     * @param hjhDebtCreditVO
     * @return
     */
    boolean updateCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO);

    String getSellerAuthCode(String tenderOrderId, Integer SourceType);
    /**
     * 查询汇计划转让列表
     * @param request
     * @return
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);
    /**
     * 组装汇计划转让列表显示状态名称
     * @param hjhDebtCreditVoList
     */
    void queryHjhDebtCreditListStatusName(List<HjhDebtCreditVo> hjhDebtCreditVoList);
}
