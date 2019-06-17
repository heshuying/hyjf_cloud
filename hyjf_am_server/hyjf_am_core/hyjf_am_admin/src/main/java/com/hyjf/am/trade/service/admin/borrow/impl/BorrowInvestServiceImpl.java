/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.am.user.service.front.user.UtmPlatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserInvestListCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowInvestService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeExtVO;
import com.hyjf.common.cache.CacheUtil;

/**
 * @author wangjun
 * @version BorrowInvestServiceImpl, v0.1 2018/7/10 9:35
 */
@Service
public class BorrowInvestServiceImpl extends BaseServiceImpl implements BorrowInvestService {

    @Autowired
    private UtmPlatService utmPlatService;

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

    @Override
    public BorrowInvestCustomizeExtVO selectBorrowInvestByNid(String nid) {
        BorrowTenderExample example=new BorrowTenderExample();
        example.or().andNidEqualTo(nid);
        List<BorrowTender> lstBorrowTender=borrowTenderMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lstBorrowTender)){
            throw new IllegalArgumentException("nid="+nid+"订单为空！");
        }
        BorrowTender borrowTender=lstBorrowTender.get(0);
        BorrowInvestCustomizeExtVO vo=new BorrowInvestCustomizeExtVO();
        BeanUtils.copyProperties(borrowTender,vo);

        build(vo,borrowTender.getTenderUserUtmId());

        return vo;
    }

    private void build(BorrowInvestCustomizeExtVO vo, Integer tenderUserUtmId) {

        if (vo.getCreateTime()!=null){
            //格式化时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setCreateTimeStr(sdf.format(vo.getCreateTime()));
        }

        if(null!=tenderUserUtmId){
            UtmPlat utmPlat =utmPlatService.getUtmPlat(tenderUserUtmId);

            if(utmPlat!=null){
                vo.setUtmName(utmPlat.getSourceName());
            }else{
                throw new IllegalArgumentException(String.format("不存在渠道utmId=【%s】",tenderUserUtmId));
            }
        }

        UtmPlat utmPlatt=utmPlatService.getUtmByUserId(vo.getUserId());

        if(utmPlatt!=null){
            vo.setUtmNameNow(utmPlatt.getSourceName());
        }

    }
}
