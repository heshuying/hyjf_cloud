/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverExample;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;
import com.hyjf.am.trade.dao.model.customize.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserInvestListCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowInvestService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowInvestServiceImpl, v0.1 2018/7/10 9:35
 */
@Service
public class BorrowInvestServiceImpl extends BaseServiceImpl implements BorrowInvestService {

    /**
     * 出借明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public int countBorrowFirst(BorrowInvestRequest borrowInvestRequest) {
        return borrowInvestCustomizeMapper.countBorrowInvest(borrowInvestRequest);
    }

    /**
     * 出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        List<BorrowInvestCustomize> list = borrowInvestCustomizeMapper.selectBorrowInvestList(borrowInvestRequest);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> clientMap = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> propertyMap = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> investMap = CacheUtil.getParamNameMap("INVEST_TYPE");
            for (BorrowInvestCustomize borrowInvestCustomize : list) {
                borrowInvestCustomize.setOperatingDeck(clientMap.getOrDefault(borrowInvestCustomize.getOperatingDeck(), null));
                borrowInvestCustomize.setTenderUserAttribute(propertyMap.getOrDefault(borrowInvestCustomize.getTenderUserAttribute(), null));
                borrowInvestCustomize.setInvestType(investMap.getOrDefault(borrowInvestCustomize.getInvestType(), null));
            }
        }
        return list;
    }

    /**
     * 出借明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest) {
        return borrowInvestCustomizeMapper.selectBorrowInvestAccount(borrowInvestRequest);
    }

    /**
     * 导出出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomize> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        List<BorrowInvestCustomize> list = borrowInvestCustomizeMapper.exportBorrowInvestList(borrowInvestRequest);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> clientMap = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> propertyMap = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> investMap = CacheUtil.getParamNameMap("INVEST_TYPE");
            for (BorrowInvestCustomize borrowInvestCustomize : list) {
                borrowInvestCustomize.setOperatingDeck(clientMap.getOrDefault(borrowInvestCustomize.getOperatingDeck(), null));
                borrowInvestCustomize.setTenderUserAttribute(propertyMap.getOrDefault(borrowInvestCustomize.getTenderUserAttribute(), null));
                //处理推荐人用户属性
                if("0".equals(borrowInvestCustomize.getTenderReferrerUserId())){
                    borrowInvestCustomize.setInviteUserAttribute("");
                } else {
                    borrowInvestCustomize.setInviteUserAttribute(propertyMap.getOrDefault(borrowInvestCustomize.getInviteUserAttribute(), null));
                }
                borrowInvestCustomize.setInvestType(investMap.getOrDefault(borrowInvestCustomize.getInvestType(), null));
            }
        }
        return list;
    }

    /**
     * 获取用户出借协议
     *
     * @param nid
     * @return
     */
    @Override
    public TenderAgreement selectTenderAgreementByNid(String nid) {
        TenderAgreementExample example = new TenderAgreementExample();
        TenderAgreementExample.Criteria cra = example.createCriteria();
        cra.andTenderNidEqualTo(nid);
        List<TenderAgreement> list = tenderAgreementMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取用户出借协议
     *
     * @param userId
     * @param borrowNid
     * @param nid
     * @return
     */
    @Override
    public BorrowRecover selectBorrowRecover(Integer userId, String borrowNid, String nid) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andNidEqualTo(nid);
        List<BorrowRecover> borrowRecovers = borrowRecoverMapper.selectByExample(example);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            return borrowRecovers.get(0);
        }
        return null;
    }

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowListCustomize> selectBorrowList(String borrowNid) {
        return borrowInvestCustomizeMapper.selectBorrowList(borrowNid);
    }

    /**
     * 标的出借信息
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<WebUserInvestListCustomize> selectUserInvestList(BorrowInvestRequest borrowInvestRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", borrowInvestRequest.getBorrowNid());
        params.put("userId", borrowInvestRequest.getUserId());
        params.put("nid", borrowInvestRequest.getNid());
        List<WebUserInvestListCustomize> list = webUserInvestListCustomizeMapper.selectUserInvestList(params);
        return list;
    }

    /**
     * 标的放款记录-分期 count
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public int countProjectRepayPlanRecordTotal(BorrowInvestRequest borrowInvestRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", borrowInvestRequest.getUserId());
        params.put("borrowNid", borrowInvestRequest.getBorrowNid());
        params.put("nid", borrowInvestRequest.getNid());
        return webUserInvestListCustomizeMapper.countProjectRepayPlanRecordTotal(params);
    }

    /**
     * 标的放款记录-分期
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<WebProjectRepayListCustomize> selectProjectRepayPlanList(BorrowInvestRequest borrowInvestRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", borrowInvestRequest.getUserId());
        params.put("borrowNid", borrowInvestRequest.getBorrowNid());
        params.put("nid", borrowInvestRequest.getNid());
        return webUserInvestListCustomizeMapper.selectProjectRepayPlanList(params);
    }

    /**
     * 更新标的放款记录
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public int updateBorrowRecover(BorrowInvestRequest borrowInvestRequest) {
        BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
        borrowRecoverExample.createCriteria().andUserIdEqualTo((borrowInvestRequest.getUserId()));
        borrowRecoverExample.createCriteria().andNidEqualTo(borrowInvestRequest.getNid());
        borrowRecoverExample.createCriteria().andBorrowNidEqualTo(borrowInvestRequest.getBorrowNid());
        BorrowRecover borrowRecover = new BorrowRecover();
        borrowRecover.setSendmail(1);
        return borrowRecoverMapper.updateByExampleSelective(borrowRecover, borrowRecoverExample);
    }
}
