/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.BorrowRegistResponseBean;
import com.hyjf.admin.beans.vo.AdminBorrowRegistCustomizeVO;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 15:34
 */
@Service
public class BorrowRegistServiceImpl implements BorrowRegistService {
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList() {
        return amTradeClient.selectBorrowProjectList();
    }

    @Override
    public Map<String, String> getParamNameMap(String param) {
        Map<String, String> resultMap = CacheUtil.getParamNameMap(param);
        return resultMap;
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public BorrowRegistResponseBean getRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistResponseBean borrowRegistResponseBean = new BorrowRegistResponseBean();
        //查询总条数
        Integer count = amTradeClient.getRegistListCount(borrowRegistListRequest);
        borrowRegistResponseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowRegistListRequest.getCurrPage(), borrowRegistListRequest.getPageSize());
        page.setTotal(count);
        borrowRegistListRequest.setLimitStart(page.getOffset());
        borrowRegistListRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowRegistCustomizeVO> list = amTradeClient.selectRegistList(borrowRegistListRequest);
            List<AdminBorrowRegistCustomizeVO> adminList = CommonUtils.convertBeanList(list, AdminBorrowRegistCustomizeVO.class);
            String sumAccount = amTradeClient.sumBorrowRegistAccount(borrowRegistListRequest);
            borrowRegistResponseBean.setRecordList(adminList);
            borrowRegistResponseBean.setSumAccount(sumAccount);
        }
        return borrowRegistResponseBean;
    }

    /**
     * 标的备案
     *
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult updateBorrowRegist(String borrowNid, String currUserId, String currUserName) {
        BorrowRegistUpdateRequest request = new BorrowRegistUpdateRequest();
        // 因为跨库原因，部分逻辑写在组合层
        // 查询标的信息
        BorrowVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfoVO = amTradeClient.selectBorrowInfoByNid(borrowNid);

        // 标的信息为空时，返回错误
        if(borrowVO == null || borrowInfoVO == null){
            return new AdminResult(BaseResult.FAIL,"未查询到相应标的信息！");
        }
        // 查询用户信息
        UserVO userVO = amUserClient.findUserById(borrowVO.getUserId());
        // 未查询到用户时，返回错误
        if(userVO == null){
            return new AdminResult(BaseResult.FAIL,"借款人信息错误！");
        }

        // 查询借款人开户信息
        BankOpenAccountVO bankOpenAccountVO = amUserClient.getBankOpenAccountByUserId(borrowVO.getUserId());
        // 未查询到开户信息时，返回错误
        if(bankOpenAccountVO == null){
            return new AdminResult(BaseResult.FAIL,"未查询到开户信息！");
        }

        //如果有担保机构ID，则查询担保机构账户
        if (Validator.isNotNull(borrowInfoVO.getRepayOrgUserId())) {
            BankOpenAccountVO RepayOrgAccountVO = amUserClient.getBankOpenAccountByUserId(borrowInfoVO.getRepayOrgUserId());
            if(RepayOrgAccountVO != null){
                request.setBailAccountId(RepayOrgAccountVO.getAccount());
            }
        }

        // 请求实体赋值
        request.setBorrowVO(borrowVO);
        request.setBorrowInfoVO(borrowInfoVO);
        request.setAccountId(bankOpenAccountVO.getAccount());
        request.setCurrUserId(currUserId);
        request.setCurrUserName(currUserName);

        // 标的备案
        return amTradeClient.updateBorrowRegist(request);
    }
}
