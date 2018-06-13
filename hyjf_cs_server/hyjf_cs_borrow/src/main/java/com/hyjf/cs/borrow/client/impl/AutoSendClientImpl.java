/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.client.impl;

import com.hyjf.am.vo.borrow.*;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.borrow.client.AutoSendClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version AutoSendClientImpl, v0.1 2018/6/12 16:13
 */
@Service
public class AutoSendClientImpl implements AutoSendClient {
    @Override
    public HjhPlanAssetVO selectPlanAsset(String assetId, String instCode) {
        return null;
    }

    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByHjhPlanAsset(String instCode) {
        return null;
    }

    @Override
    public void updatePlanAsset(HjhPlanAssetVO planAssetVO) {

    }

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectByBorrowCd(String borrowCd) {
        return null;
    }

    @Override
    public BorrowFinmanNewChargeVO selectBorrowApr(String borrowClass, String instCode, Integer assetType, String queryBorrowStyle, Integer borrowPeriod) {
        return null;
    }

    @Override
    public List<UserVO> selectUserByUsername(String repayOrgName) {
        return null;
    }

    @Override
    public BorrowConfigVO getBorrowConfig(String configCd) {
        return null;
    }

    @Override
    public void insertSelective(BorrowWithBLOBsVO borrow) {

    }

    @Override
    public void insertBorrowManinfo(String borrowNid, HjhPlanAssetVO hjhPlanAssetVO, BorrowWithBLOBsVO borrow) {

    }

    @Override
    public int updateHjhPlanAssetnew(HjhPlanAssetVO hjhPlanAssetnewVO) {
        return 0;
    }

    @Override
    public List<HjhLabelVO> seleHjhLabel(String borrowStyle) {
        return null;
    }
}
