/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.BorrowFullInfoResponseBean;
import com.hyjf.admin.beans.response.BorrowFullResponseBean;
import com.hyjf.admin.beans.vo.AdminBorrowFullCustomizeVO;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.BorrowFullService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowFullServiceImpl, v0.1 2018/7/6 10:18
 */
@Service
public class BorrowFullServiceImpl implements BorrowFullService {
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    public static final String OK = "OK";

    /**
     * 获取借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public BorrowFullResponseBean getBorrowFullList(BorrowFullRequest borrowFullRequest) {
        BorrowFullResponseBean borrowFullResponseBean = new BorrowFullResponseBean();
        //查询总条数
        Integer count = amTradeClient.countBorrowFull(borrowFullRequest);
        borrowFullResponseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowFullRequest.getCurrPage(), borrowFullRequest.getPageSize());
        page.setTotal(count);
        borrowFullRequest.setLimitStart(page.getOffset());
        borrowFullRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowFullCustomizeVO> list = amTradeClient.selectBorrowFullList(borrowFullRequest);
            BorrowFullCustomizeVO sumAccount = amTradeClient.sumAccount(borrowFullRequest);
            //对应前端swagger
            List<AdminBorrowFullCustomizeVO> adminList = CommonUtils.convertBeanList(list, AdminBorrowFullCustomizeVO.class);
            borrowFullResponseBean.setRecordList(adminList);
            Map<String, String> accountMap = new HashMap<>();
            accountMap.put("sumAccount", sumAccount.getSumAccount());
            accountMap.put("sumBorrowAccountYes", sumAccount.getSumBorrowAccountYes());
            accountMap.put("sumServiceScale", sumAccount.getSumServiceScale());
            borrowFullResponseBean.setSumAccount(accountMap);
        }
        return borrowFullResponseBean;
    }

    /**
     * 借款复审详细信息
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult getFullInfo(BorrowFullRequest borrowFullRequest) {
        BorrowFullInfoResponseBean borrowFullInfoResponseBean = new BorrowFullInfoResponseBean();
        if (StringUtils.isBlank(borrowFullRequest.getBorrowNidSrch())) {
            return new AdminResult(BaseResult.FAIL, "标的编号为空！");
        } else {
            BorrowFullCustomizeVO reverifyInfo = this.amTradeClient.getFullInfo(borrowFullRequest.getBorrowNidSrch());
            if (reverifyInfo != null) {
                borrowFullInfoResponseBean.setBorrowFullInfo(reverifyInfo);
                //查询复审详细列表总条数
                Integer count = amTradeClient.countFullList(borrowFullRequest.getBorrowNidSrch());
                borrowFullInfoResponseBean.setTotal(count);
                //查询复审详细列表及其合计
                if (count > 0) {
                    //分页参数
                    Page page = Page.initPage(borrowFullRequest.getCurrPage(), borrowFullRequest.getPageSize());
                    page.setTotal(count);
                    borrowFullRequest.setLimitStart(page.getOffset());
                    borrowFullRequest.setLimitEnd(page.getLimit());
                    //查询
                    List<BorrowFullCustomizeVO> list = amTradeClient.getFullList(borrowFullRequest);
                    BorrowFullCustomizeVO sumAccount = amTradeClient.sumAmount(borrowFullRequest.getBorrowNidSrch());
                    //对应前端swagger
                    List<AdminBorrowFullCustomizeVO> adminList = CommonUtils.convertBeanList(list, AdminBorrowFullCustomizeVO.class);
                    borrowFullInfoResponseBean.setRecordList(adminList);
                    Map<String, String> accountMap = new HashMap<>();
                    accountMap.put("investmentAmount", sumAccount.getInvestmentAmount());
                    accountMap.put("loanAmount", sumAccount.getLoanAmount());
                    accountMap.put("serviceCharge", sumAccount.getServiceCharge());
                    borrowFullInfoResponseBean.setSumAmount(accountMap);
                }
                return new AdminResult(borrowFullInfoResponseBean);
            } else {
                return new AdminResult(BaseResult.FAIL, "未查询到复审详细信息！");
            }
        }
    }

    /**
     * 复审提交
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult updateBorrowFull(BorrowFullRequest borrowFullRequest) {
        // 校验参数
        String checkResult = this.checkItems(borrowFullRequest);

        if (OK.equals(checkResult)) {
            String msg = this.updateReverifyRecord(borrowFullRequest);
            if (OK.equals(msg)) {
                return new AdminResult();
            } else {
                return new AdminResult(AdminResult.FAIL, msg);
            }
        } else {
            return new AdminResult(AdminResult.FAIL, checkResult);
        }
    }

    private String checkItems(BorrowFullRequest borrowFullRequest) {
        //标的编号
        if(StringUtils.isBlank(borrowFullRequest.getBorrowNidSrch())){
            return "标的编号！";
        }
        // 审核备注
        if(StringUtils.isBlank(borrowFullRequest.getReverifyRemark())){
            return "复审备注为空！";
        }

        //标的信息、状态
        BorrowVO borrow = amTradeClient.selectBorrowByNid(borrowFullRequest.getBorrowNidSrch());
        if(borrow == null){
            return "标的信息不存在！";
        } else if(borrow.getStatus() == 16) {
            return "标的状态不正确！";
        }
        return OK;
    }

    private String updateReverifyRecord(BorrowFullRequest borrowFullRequest) {
        // 借款编号
        String borrowNid = borrowFullRequest.getBorrowNidSrch();
        //查询标的信息
        BorrowVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfo = amTradeClient.selectBorrowInfoByNid(borrowNid);

        if (borrow == null || borrowInfo == null) {
            return "标的信息不存在。[借款编号：" + borrowNid + "]";
        } else {
            borrowFullRequest.setBorrowVO(borrow);
            borrowFullRequest.setBorrowInfoVO(borrowInfo);
            //如果标的未复审
            if (borrow.getReverifyStatus() == 0) {
                // 借款人userId
                Integer borrowUserId = borrow.getUserId();

                // 取得借款人账户信息
                AccountVO borrowAccount = amTradeClient.getAccountByUserId(borrowUserId);
                if (borrowAccount == null) {
                    return "借款人账户不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]";
                }
                // 获取借款人开户信息
                BankOpenAccountVO borrowerAccount = amUserClient.getBankOpenAccountByUserId(borrowUserId);
                if (borrowerAccount == null) {
                    return "借款人未开户。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]";
                } else {
                    //账户
                    borrowFullRequest.setAccountId(borrowerAccount.getAccount());
                }

                AdminResult updateResult = amTradeClient.updateBorrowFull(borrowFullRequest);
                if (updateResult != null) {
                    if (AdminResult.SUCCESS.equals(updateResult.getStatus())) {
                        return OK;
                    } else {
                        return updateResult.getStatusDesc();
                    }
                } else {
                    return "[借款编号：" + borrowNid + "]复审异常";
                }
            } else {
                return "[借款编号：" + borrowNid + "]" + "已经复审，请确认";
            }
        }
    }

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult updateBorrowOver(BorrowFullRequest borrowFullRequest) {
        return amTradeClient.updateBorrowOver(borrowFullRequest);
    }
}
