/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.client;

import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.assetpush.STZHWhiteListVO;
import com.hyjf.am.vo.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.borrow.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.borrow.bean.assetpush.InfoBean;

import java.util.List;

/**
 * @author fuqiang
 * @version ApiAssetClient, v0.1 2018/6/11 18:12
 */
public interface ApiAssetClient {
    /**
     * 获取机构信息
     *
     * @param instCode
     * @param assetType
     * @return
     */
    HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, Integer assetType);

    /**
     * 根据项目类型去还款方式
     *
     * @param borrowcCd
     * @return
     */
    List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd);

    /**
     * 获取用户信息
     *
     * @param truename
     * @param idcard
     * @return
     */
    UserInfoVO selectUserInfoByNameAndCard(String truename, String idcard);

    /**
     * 获取开户的数据
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO selectBankAccountById(Integer userId);

    /**
     * 获取用户的数据
     *
     * @param userId
     * @return
     */
    UserVO selectUsersById(Integer userId);

    /**
     * 获取受托支付电子账户列表
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId);

    /**
     * 插入资产表
     *
     * @param record
     * @return
     */
    int insertAssert(HjhPlanAssetVO record);

    /**
     * 插入资产表
     *
     * @param riskInfo
     */
    void insertRiskInfo(List<InfoBean> riskInfo);
}
