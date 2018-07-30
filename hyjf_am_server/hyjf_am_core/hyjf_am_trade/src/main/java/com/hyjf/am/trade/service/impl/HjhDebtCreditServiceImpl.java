/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditTenderMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowCreditCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditExample;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTenderExample;
import com.hyjf.am.trade.service.HjhDebtCreditService;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditServiceImpl, v0.1 2018/6/27 14:45
 */
@Service
public class HjhDebtCreditServiceImpl implements HjhDebtCreditService {

    @Autowired
    HjhDebtCreditMapper hjhDebtCreditMapper;

    @Autowired
    private BorrowCreditCustomizeMapper borrowCreditCustomizeMapper;

    @Autowired
    private HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;

    /**
     * 判断是否存在债转未承接的项目
     * @Author liushouyi
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhDebtCredit> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId);
        return this.hjhDebtCreditMapper.selectByExample(example);
    }

    /**
     * 判断剩余待清算金额是否全部债转
     * @Author liushouyi
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    @Override
    public List<HjhDebtCredit> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId, String borrowNid) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId).andBorrowNidEqualTo(borrowNid).andCreditStatusNotEqualTo(3);
        return this.hjhDebtCreditMapper.selectByExample(example);
    }


    @Override
    public List<HjhDebtCreditTender> selectHjhCreditTenderListByAssignOrderId(String assignOrderId) {
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria cra = example.createCriteria();
        cra.andAssignOrderIdEqualTo(assignOrderId);
        List<HjhDebtCreditTender> hjhCreditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(example);
        return hjhCreditTenderList;
    }

    @Override
    public List<HjhDebtCredit> getHjhDebtCreditList(HjhDebtCreditRequest request) {
        HjhDebtCreditExample borrowCreditExample = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andBorrowNidEqualTo(request.getBorrowNid());
        borrowCreditCra.andCreditNidEqualTo(request.getCreditNid());
        borrowCreditCra.andInvestOrderIdEqualTo(request.getInvestOrderId());//??
        List<HjhDebtCredit> borrowCredit = this.hjhDebtCreditMapper.selectByExample(borrowCreditExample);
        return borrowCredit;
    }


    /**
     * 根据债转编号查询债转信息
     * @author zhangyk
     * @date 2018/6/30 11:23
     */
    @Override
    public AppCreditDetailCustomizeVO selectCreditDetailByCreditNid(String creditNid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("creditNid", creditNid);
        AppCreditDetailCustomizeVO appCreditDetailCustomizeVO = borrowCreditCustomizeMapper.getAppCreditDetailByCreditNid(params);
        return appCreditDetailCustomizeVO;
    }

    /**
     * 根据债转编号查询债转信息
     * @param creditNid
     * @return
     * @author liubin
     */
    @Override
    public HjhDebtCredit selectHjhDebtCreditByCreditNid(String creditNid) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andCreditNidEqualTo(creditNid);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据PK更新hjhDebtCredit
     * @param hjhDebtCredit
     * @return
     */
    @Override
    public int updateHjhDebtCreditByPK(HjhDebtCredit hjhDebtCredit) {
        return this.hjhDebtCreditMapper.updateByPrimaryKeySelective(hjhDebtCredit);
    }

	/**
	 * 获取债转承接信息
	 * by libin
	 * @param nid
	 * @return
	 */
	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid) {
		HjhDebtCreditTenderVO hjhDebtCreditTenderVO = new HjhDebtCreditTenderVO();
		HjhDebtCreditTender hjhDebtCreditTender = hjhDebtCreditTenderMapper.selectByPrimaryKey(nid);
		if(hjhDebtCreditTender != null){
			BeanUtils.copyProperties(hjhDebtCreditTender, hjhDebtCreditTenderVO);
		}
		return hjhDebtCreditTenderVO;
	}

	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId) {
		HjhDebtCreditTenderVO hjhDebtCreditTenderVO = new HjhDebtCreditTenderVO();
		HjhDebtCreditTender hjhDebtCreditTender = new HjhDebtCreditTender();
		
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria criteria = example.createCriteria();
        criteria.andAssignOrderIdEqualTo(assignOrderId);
        List<HjhDebtCreditTender> list = hjhDebtCreditTenderMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
        	hjhDebtCreditTender =  list.get(0);
    		if(hjhDebtCreditTender != null){
    			BeanUtils.copyProperties(hjhDebtCreditTender, hjhDebtCreditTenderVO);
    		}
    		return hjhDebtCreditTenderVO;
        }
		return null;
	}
}
