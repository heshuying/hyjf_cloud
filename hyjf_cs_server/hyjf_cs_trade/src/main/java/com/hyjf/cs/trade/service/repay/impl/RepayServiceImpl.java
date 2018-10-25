package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.trade.ProjectBeanVO;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.bean.repay.RepayProjectListBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.repay.RepayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version RepayServiceImpl, v0.1 2018/8/27 15:39
 * @Author: Zha Daojian
 */
@Service
public class RepayServiceImpl implements RepayService {

    @Autowired
    AmTradeClient amTradeClient;

	@Autowired
	AmUserClient amUserClient;


    /**
     * 获取批次放款列表
     *
     * @param request
     * @return java.util.List<com.hyjf.am.vo.admin.BatchBorrowRecoverVo>
     * @author Zha Daojian
     * @date 2018/8/27 15:37
     **/
    @Override
    public List<BatchBorrowRecoverVo> getBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {
        return amTradeClient.getBatchBorrowRecoverList(request);
    }

    /**
     * 获取批次放款列表条数
     *
     * @param request
     * @return
     * @author Zha Daojian
     * @date 2018/8/27 15:57
     **/
    @Override
    public Integer countBatchCenter(BatchBorrowRecoverRequest request) {
         return amTradeClient.getCountBatchCenter(request);
    }

    /**
     * 查询用户的待还款项目列表信息
     */
    @Override
    public List<WebUserRepayProjectListCustomizeVO> searchUserRepayList(RepayProjectListBean form, int limitStart, int limitEnd) {
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        String roleId = StringUtils.isNotEmpty(form.getRoleId()) ? form.getRoleId() : null;
        String status = StringUtils.isNotEmpty(form.getStatus()) ? form.getStatus() : null;

        String startDate = StringUtils.isNotEmpty(form.getStartDate()) ? form.getStartDate() : null;
        String endDate = StringUtils.isNotEmpty(form.getEndDate()) ? form.getEndDate() : null;
        if (StringUtils.isNotBlank(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        String borrowNid = StringUtils.isNotEmpty(form.getBorrowNid()) ? form.getBorrowNid() : null;

        String repayTimeOrder = StringUtils.isNotEmpty(form.getRepayOrder()) ? form.getRepayOrder() : null;
        String checkTimeOrder = StringUtils.isNotEmpty(form.getCheckTimeOrder()) ? form.getCheckTimeOrder() : null;

        params.put("userId", userId);
        params.put("roleId", roleId);
        params.put("status", status);
        params.put("repayStatus", form.getRepayStatus());

        params.put("startDate", startDate);
        params.put("endDate", endDate);


        params.put("repayTimeOrder", repayTimeOrder);
        params.put("checkTimeOrder", checkTimeOrder);

		params.put("borrowNid", borrowNid);
		if (limitStart == 0 || limitStart > 0) {
			params.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			params.put("limitEnd", limitEnd);
		}
		List<WebUserRepayProjectListCustomizeVO> list = null;
		if (roleId != null && "3".equals(roleId)) {
			// 垫付机构
            list = amTradeClient.selectOrgRepayProjectList(params);
			if (list!=null) {
				for (int i = 0; i < list.size(); i++) {
					WebUserRepayProjectListCustomizeVO info = list.get(i);
					//获得标的类型
					String borrowStyle = info.getBorrowStyle();
					BigDecimal accountFee = BigDecimal.ZERO;
					BigDecimal borrowTotal = BigDecimal.ZERO;
					BigDecimal realAccountTotal = BigDecimal.ZERO;
					BigDecimal allAccountFee = BigDecimal.ZERO;
					if (StringUtils.isNotBlank(list.get(i).getRepayFee())) {
						accountFee = new BigDecimal(list.get(i).getRepayFee());
					}
					if (StringUtils.isNotBlank(list.get(i).getBorrowTotal())) {
						borrowTotal = new BigDecimal(list.get(i).getBorrowTotal());
					}
					if (StringUtils.isNotBlank(list.get(i).getRealAccountYes())) {
						realAccountTotal = new BigDecimal(list.get(i).getRealAccountYes());
					}
					if (StringUtils.isNotBlank(list.get(i).getAllRepayFee())) {
						allAccountFee = new BigDecimal(list.get(i).getAllRepayFee());
					}
					list.get(i).setBorrowTotal(borrowTotal.add(allAccountFee).toString());//应还总额
					list.get(i).setRealAccountYes(realAccountTotal.add(accountFee).toString());//本期应还总额

					if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)||CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {//日标
						info.setOrgBorrowPeriod("1");
					}else{//月标 获取当前应还期数
						if (StringUtils.isBlank(info.getBorrowAllPeriod())) {
							info.setBorrowAllPeriod("0");
						}
						if (StringUtils.isBlank(info.getRepayPeriod())) {
							info.setRepayPeriod("0");
						}
						int borrowPeriod = Integer.parseInt(info.getBorrowAllPeriod());
						int repayPeriod = Integer.parseInt(info.getRepayPeriod());
						int orgBorrowPeriod = borrowPeriod - repayPeriod + 1;
						info.setOrgBorrowPeriod(orgBorrowPeriod+"");
					}
				}
			}
		} else {
            list = amTradeClient.selectUserRepayProjectList(params);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
//					BigDecimal accountTotal = BigDecimal.ZERO;
					BigDecimal accountFee = BigDecimal.ZERO;
					BigDecimal borrowTotal = BigDecimal.ZERO;
					BigDecimal realAccountTotal = BigDecimal.ZERO;
					BigDecimal allAccountFee = BigDecimal.ZERO;
					BigDecimal serviceFee = BigDecimal.ZERO;
//					if (StringUtils.isNotBlank(list.get(i).getAccountTotal())) {
//						accountTotal = new BigDecimal(list.get(i).getAccountTotal());
//					}
					if (StringUtils.isNotBlank(list.get(i).getRepayFee())) {
						accountFee = new BigDecimal(list.get(i).getRepayFee());
					}
					if (StringUtils.isNotBlank(list.get(i).getBorrowTotal())) {
						borrowTotal = new BigDecimal(list.get(i).getBorrowTotal());
					}
					if (StringUtils.isNotBlank(list.get(i).getRealAccountYes())) {
						realAccountTotal = new BigDecimal(list.get(i).getRealAccountYes());
					}
					if (StringUtils.isNotBlank(list.get(i).getAllRepayFee())) {
						allAccountFee = new BigDecimal(list.get(i).getAllRepayFee());
					}
					if (StringUtils.isNotBlank(list.get(i).getServiceFee())) {
						serviceFee = new BigDecimal(list.get(i).getServiceFee());
					}
//					list.get(i).setAccountTotal(accountTotal.add(accountFee).toString());
					BigDecimal oldYesAccount = new BigDecimal(list.get(i).getYesAccount());
					BigDecimal yesAccount = oldYesAccount.subtract(serviceFee);
					list.get(i).setYesAccount(yesAccount.toString());
					list.get(i).setBorrowTotal(borrowTotal.add(allAccountFee).toString());
					list.get(i).setRealAccountYes(realAccountTotal.add(accountFee).toString());
				}
			}
		}
		return list;
	}

    /**
     * 查询用户的还款详情
     */
    @Override
    public ProjectBeanVO getRepayProjectDetail(ProjectBeanVO form) {
        return amTradeClient.getRepayProjectDetail(form);
    }


    /**
     * 查询用户的还款详情
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(String accountId) {
        return amUserClient.selectBankOpenAccountByAccountId(accountId);
    }
}
