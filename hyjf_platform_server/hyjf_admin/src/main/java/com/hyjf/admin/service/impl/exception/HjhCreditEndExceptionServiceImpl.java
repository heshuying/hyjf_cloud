package com.hyjf.admin.service.impl.exception;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.exception.HjhCreditEndExceptionService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hesy
 * @version HjhCreditEndExceptionServiceImpl, v0.1 2018/7/12 18:47
 */
@Service
public class HjhCreditEndExceptionServiceImpl extends BaseServiceImpl implements HjhCreditEndExceptionService {
    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 根据creditNid查询债转信息
     * @param creditNid
     * @return
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid){
        return amTradeClient.selectHjhDebtCreditByCreditNid(creditNid);
    }

    /**
     * 请求结束债权（追加结束债权任务记录）
     * @param credit
     * @param tenderAccountId
     * @param tenderAuthCode
     * @return
     * @throws Exception
     */
    @Override
    public boolean requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) throws Exception {
        return amTradeClient.requestDebtEnd(credit, tenderAccountId, tenderAuthCode) > 0 ? true : false;
    }

    /**
     * 银行结束债权后，更新债权表为完全承接
     * @param hjhDebtCreditVO
     * @return
     */
    @Override
    public boolean updateCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO){
        return amTradeClient.updateHjhDebtCreditForEnd(hjhDebtCreditVO) > 0 ? true : false;
    }

    @Override
    public String getSellerAuthCode(String tenderOrderId, Integer SourceType) {
        String authCode = null;
        if (SourceType.compareTo(1) == 0) {
            // 1原始债权
            BorrowTenderVO borrowtender = amTradeClient.getBorrowTenderByNid(tenderOrderId);
            if (borrowtender == null || borrowtender.getAuthCode() == null) {
                logger.error("未从BorrowTender获取出让人"+tenderOrderId+"的投标成功的授权号。  ");
                return null;
            }
            authCode = borrowtender.getAuthCode();
        }else {
            // 0非原始债权
            HjhDebtCreditTenderVO hjhDebtCreditTender = amTradeClient.getByAssignOrderId(tenderOrderId);
            if (hjhDebtCreditTender == null || hjhDebtCreditTender.getAuthCode() == null) {
                logger.error("未从HjhDebtCreditTender获取出让人"+tenderOrderId+"的债转成功的授权号。  ");
                return null;
            }
            authCode = hjhDebtCreditTender.getAuthCode();
        }
        return authCode;
    }

    /**
     * 查询汇计划转让列表
     * @param request
     * @return
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {
        return amTradeClient.queryHjhDebtCreditList(request);
    }

    /**
     * 组装汇计划转让列表显示状态名称
     * @param hjhDebtCreditVoList
     */
    @Override
    public void queryHjhDebtCreditListStatusName(List<HjhDebtCreditVo> hjhDebtCreditVoList) {
        //转让状态
        List<ParamNameVO> hjhDebtCreditStatus = this.getParamNameList(CustomConstants.HJH_DEBT_CREDIT_STATUS);
        //汇计划债转还款状态
        List<ParamNameVO> hjhDebtRepayStatus = this.getParamNameList(CustomConstants.HJH_DEBT_REPAY_STATUS);
        for (HjhDebtCreditVo vo: hjhDebtCreditVoList
                ) {
            String repayStatusName = this.getParamName(vo.getRepayStatus(),hjhDebtRepayStatus);
            String creditStatusName = this.getParamName(vo.getCreditStatus(), hjhDebtCreditStatus);
            vo.setRepayStatusName(repayStatusName);
            vo.setCreditStatusName(creditStatusName);
        }
    }
}
