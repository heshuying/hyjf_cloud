package com.hyjf.admin.service.impl.exception;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.beans.repaybean.RepayBean;
import com.hyjf.admin.service.exception.BankRepayFreezeOrgService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 还款冻结异常处理
 * @author hesy
 * @version BankRepayFreezeOrgServiceImpl, v0.1 2018/10/19 10:09
 */
@Service
public class BankRepayFreezeOrgServiceImpl extends BaseAdminServiceImpl implements BankRepayFreezeOrgService {

    /**
     * 异常中心-还款冻结异常列表数据
     * @param requestBean
     * @return
     */
    @Override
    public List<BankRepayFreezeOrgCustomizeVO> selectList(RepayFreezeOrgRequest requestBean){
        return amAdminClient.getBankRepayFreezeOrgList(requestBean);
    }

    /**
     * 异常中心-还款冻结异常列表记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectCount(RepayFreezeOrgRequest requestBean){
        return amAdminClient.getBankRepayFreezeOrgCount(requestBean);
    }

    /**
     * 查询担保机构冻结列表
     */
    @Override
    public BankRepayOrgFreezeLogVO getBankRepayOrgFreezeLogList(String orderId, String borrowNid, Integer currentPeriod) {
        if (StringUtils.isBlank(orderId)) {
            return null;
        }
        List<BankRepayOrgFreezeLogVO> listResult = amAdminClient.getBankRepayOrgFreezeLogList(orderId, borrowNid);
        if (listResult != null && !listResult.isEmpty()) {
            if (currentPeriod != null && listResult.size() > 1) {
                logger.info("currentPeriod:{}",currentPeriod);
                List<BankRepayOrgFreezeLogVO> filterList = listResult.stream().filter(item->currentPeriod.equals(item.getCurrentPeriod())).collect(Collectors.toList());
               if(filterList != null && !filterList.isEmpty()) {
                   logger.info("filterList.currentPeriod:{}",currentPeriod);
                   return filterList.get(0);
               }
            }
            return listResult.get(0);
        }
        return null;
    }

    /**
     * 根据id删除担保机构临时日志,外部调用
     */
    @Override
    public Integer deleteFreezeLogById(Integer id) {
        if (id == null) {
            return 0;
        }
        return amAdminClient.deleteOrgFreezeLogById(id);
    }

    /**
     * 根据ordId删除担保机构临时日志,外部调用
     */
    @Override
    public Integer deleteOrgFreezeTempLogs(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return 0;
        }
        return amAdminClient.deleteOrgFreezeLog(orderId);
    }

    @Override
    public RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay) {
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("userId", String.valueOf(userId));
        paraMap.put("roleId", roleId);
        paraMap.put("borrowNid", borrowNid);
        paraMap.put("isAllRepay", String.valueOf(isAllRepay));

        return amTradeClient.getRepayBean(paraMap);
    }

    /**
     * 还款申请回调数据更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean, boolean isAllRepay){
        RepayRequestUpdateRequest requestBean = new RepayRequestUpdateRequest();
        requestBean.setRepayBeanData(JSON.toJSONString(repayBean));
        requestBean.setBankCallBeanData(JSON.toJSONString(bankCallBean));
        requestBean.setAllRepay(isAllRepay);
        return amTradeClient.repayRequestUpdate(requestBean);
    }

    @Override
    public Boolean updateBorrowCreditStautus(String borrowNid) {
        return amTradeClient.updateBorrowCreditStautus(borrowNid);
    }



}
