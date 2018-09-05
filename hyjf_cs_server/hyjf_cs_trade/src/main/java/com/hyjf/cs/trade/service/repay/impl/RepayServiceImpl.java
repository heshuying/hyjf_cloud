package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.repay.RepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version RepayServiceImpl, v0.1 2018/8/27 15:39
 * @Author: Zha Daojian
 */
@Service
public class RepayServiceImpl implements RepayService {

    @Autowired
    AmTradeClient amTradeClient;
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
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayProjectListBean;
import com.hyjf.cs.trade.client.BorrowClient;
import com.hyjf.cs.trade.service.repay.RepayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RepayServiceImpl extends BaseServiceImpl implements RepayService {
    @Autowired
    private BorrowClient borrowClient;

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
			//list = webUserRepayListCustomizeMapper.selectOrgRepayProjectList(params);
            list = borrowClient.selectOrgRepayProjectList(params);
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
			//list = webUserRepayListCustomizeMapper.selectUserRepayProjectList(params);
            list = borrowClient.selectUserRepayProjectList(params);
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
    public ProjectBean searchRepayProjectDetail(ProjectBean form) {
        return borrowClient.searchRepayProjectDetail(form);
    }

    /**
     * 查询用户的还款详情
     */
   /* @Override
    public ProjectBean searchRepayProjectDetail(ProjectBean form, boolean isAllRepay) throws Exception {

        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        String borrowNid = StringUtils.isNotEmpty(form.getBorrowNid()) ? form.getBorrowNid() : null;
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        if (StringUtils.isNotEmpty(form.getRoleId()) && "3".equals(form.getRoleId())) {
            // 垫付机构
            crt.andRepayOrgUserIdEqualTo(Integer.parseInt(userId));
        } else {
            // 普通借款人
            crt.andUserIdEqualTo(Integer.parseInt(userId));
        }

        List<Borrow> projects = borrowMapper.selectByExample(example);// 查询相应的用户还款项目
        if (projects != null && projects.size() > 0) {
            Borrow borrow = projects.get(0);
            // userId 改成借款人的userid！！！
            userId = borrow.getUserId().toString();
            form.settType("0");// 设置为非汇添金专属项目
            // 设置相应的项目名称
            // 之前取borrow表的Name，现在取borrow表的projectName
            // form.setBorrowName(borrow.getName());
            form.setBorrowName(borrow.getProjectName());

            // 获取相应的项目还款方式
            String borrowStyle = StringUtils.isNotEmpty(borrow.getBorrowStyle()) ? borrow.getBorrowStyle() : null;
            form.setBorrowStyle(borrowStyle);


            // 用户是否全部结清，是否正在还款，是否只能全部结清 默认都否
            form.setAllRepay("0");
            form.setRepayStatus("0");
            form.setOnlyAllRepay("0");

            BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
            BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
            crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
            crtBorrowApicron.andApiTypeEqualTo(1);
            crtBorrowApicron.andStatusNotEqualTo(6);// 不是已经还款的，正在还款的
            List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);
            // 有正在还款，查看是否是一次结清，适用分期和一次性还款方式，   下面逻辑中的分期最后一期继续适用
            if (borrowApicrons != null && borrowApicrons.size() > 0) {
                BorrowApicron borrowApicron = borrowApicrons.get(0);
                Integer allrepay = borrowApicron.getIsAllrepay();
                if(allrepay != null && allrepay.intValue() ==1) {
                    form.setAllRepay("1");
                    isAllRepay = true;
                }
                // 能查到，无论如何都是有正在还款
                form.setRepayStatus("1");
            }


            // 一次性还款
            if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {

                RepayBean repay = this.calculateRepay(Integer.parseInt(userId), borrow);
                setRecoverDetail(form, userId, borrow,repay);
            } else {
                RepayBean repayByTerm = new RepayBean();
                BorrowRepayVO borrowRepay = this.searchRepay(Integer.parseInt(userId), borrow.getBorrowNid());
                // 获取相应的还款信息
                BeanUtils.copyProperties(borrowRepay, repayByTerm);
                repayByTerm.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
                // 计算当前还款期数
                int period = borrow.getBorrowPeriod() - borrowRepay.getRepayPeriod() + 1;
                BorrowRepayPlan borrowRepayPlan = null;
                String repayTimeStart =null;
                if(period ==1) {
                    borrowRepayPlan = this.searchRepayPlan(Integer.parseInt(userId), borrowNid, period);
                    repayTimeStart = borrowRepayPlan.getCreateTime().toString();
                }else {
                    borrowRepayPlan = this.searchRepayPlan(Integer.parseInt(userId), borrowNid, period-1);
                    repayTimeStart = borrowRepayPlan.getRepayTime();

                    int curPlanStart = GetDate.getIntYYMMDD(Integer.parseInt(repayTimeStart));
                    int nowDate = GetDate.getIntYYMMDD(new Date());
                    // 超前还款的情况，只能一次性还款
                    if(nowDate <= curPlanStart) {
                        form.setOnlyAllRepay("1");
                        isAllRepay = true;
                    }
                }

                // 计算分期的项目还款信息
                if(isAllRepay) {
                    // 全部结清的
//        			RepayBean repayByTerm = this.searchRepayPlanTotal(Integer.parseInt(userId), borrow);
                    // 分期 当前期 计算，如果当前期没有还款，则先算当前期，后算所有剩下的期数
                    this.calculateRepayPlanAll(repayByTerm, borrow, period);
                    setRecoverPlanAllDetail(form, isAllRepay, userId,borrow,repayByTerm);

                }else {
                    // 当期还款
                    this.calculateRepayPlan(repayByTerm, borrow, period);
                    setRecoverPlanDetail(form, isAllRepay, userId,borrow,repayByTerm);
                }
            }
            return form;

        } else {
            return null;
        }
    }*/

    /**
     * 查询用户的还款详情
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(String bankAccount) {
        return borrowClient.getBankOpenAccount(bankAccount);
    }

    /*@Override
    public BankOpenAccountVO getBankOpenAccount(String bankAccount) {
        BankOpenAccountExample accountExample = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
        crt.andAccountEqualTo(bankAccount);
        List<BankOpenAccountVO> bankAccounts = this.bankOpenAccountMapper.selectByExample(accountExample);
        if (bankAccounts != null && bankAccounts.size() == 1) {
            return bankAccounts.get(0);
        }
        return null;
    }*/
}
