/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author fuqiang
 * @version AutoSendClient, v0.1 2018/6/12 16:13
 */
public interface AutoSendClient {
    /**
     * 查询单个资产根据资产ID
     *
     * @param assetId
     * @param instCode
     * @return
     */
    HjhPlanAssetVO selectPlanAsset(String assetId, String instCode);

    /**
     * 获取现金贷资产方信息配置
     *
     * @param instCode
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);

    /**
     * 更新资产表
     *
     * @param planAssetVO
     */
    void updatePlanAsset(HjhPlanAssetVO planAssetVO);

    /**
     * 获取项目类型
     *
     * @param borrowCd
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectByBorrowCd(String borrowCd);

    /**
     * 根据项目类型，期限，获取借款利率
     *
     * @param request
     * @return
     */
    BorrowFinmanNewChargeVO selectBorrowApr(BorrowFinmanNewChargeRequest request);

    /**
     * 根据垫付机构用户名检索垫付机构用户
     *
     * @param repayOrgName
     * @return
     */
    List<UserVO> selectUserByUsername(String repayOrgName);

    /**
     * 获取系统配置
     *
     * @param configCd
     * @return
     */
    BorrowConfigVO getBorrowConfig(String configCd);

    /**
     * 借款表插入
     *
     * @param borrow
     */
    void insertSelective(BorrowAndInfoVO borrow);

    /**
     * 个人信息
     *
     * @param borrowManinfoVO
     */
    void insertBorrowManinfo(BorrowManinfoVO borrowManinfoVO);

    /**
     * 更新资产表
     *
     * @param hjhPlanAssetnewVO
     * @return
     */
    int updateHjhPlanAssetnew(HjhPlanAssetVO hjhPlanAssetnewVO);

    /**
     * 获取标签
     *
     * @param borrowStyle
     * @return
     */
    List<HjhLabelVO> seleHjhLabel(String borrowStyle);
}
