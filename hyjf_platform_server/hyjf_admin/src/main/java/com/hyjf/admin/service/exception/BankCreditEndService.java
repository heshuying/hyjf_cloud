package com.hyjf.admin.service.exception;

import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndUpdateRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author hesy
 * @version BankCreditEndService, v0.1 2018/7/12 16:51
 */
public interface BankCreditEndService {
    /**
     * 结束债权列表
     * @param requestBean
     * @return
     */
    List<BankCreditEndVO> getCreditEndList(BankCreditEndListRequest requestBean);
    /**
     * 结束债权列表总记录数
     * @param requestBean
     * @return
     */
    int getCreditEndCount(BankCreditEndListRequest requestBean);
    /**
     * 根据orderid检索
     * @param orderId
     * @return
     */
    BankCreditEndVO getCreditEndByOrderId(String orderId);
    /**
     * 更新结束债权记录
     * @param requestBean
     * @return
     */
    int updateBankCreditEnd(BankCreditEndVO requestBean);

    /**
     * 批次恢复为初始状态
     * @param requestBean
     * @return
     */
    boolean updateCreditEndForInitial(BankCreditEndVO requestBean);
    /**
     * 请求参数校验
     * @param requestBean
     * @return
     */
    boolean checkForUpdate(BankCreditEndUpdateRequest requestBean);
    /**
     * 请求参数校验
     * @param requestBean
     * @return
     */
    boolean checkForUpdateInitial(BankCreditEndUpdateRequest requestBean);
    /**
     * 查询银行接口
     * @auther: hesy
     * @date: 2018/7/12
     */
    List<BankCallBean> queryBatchDetails(BankCreditEndUpdateRequest requestBean);
    /**
     * 更新数据
     * @auther: hesy
     * @date: 2018/7/12
     */
    boolean updateBankCreditEndFromBankQuery(List<BankCallBean> resultBeans);
}
