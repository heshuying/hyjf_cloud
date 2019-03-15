/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowCreditMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowCreditCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.BorrowCreditExample;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowCreditCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowCreditService;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditServiceImpl, v0.1 2018/6/24 10:59
 */
@Service
public class BorrowCreditServiceImpl implements BorrowCreditService {

    @Autowired
    BorrowCreditMapper borrowCreditMapper;

    @Autowired
    BorrowCreditCustomizeMapper borrowCreditCustomizeMapper;


    @Override
    public List<BorrowCredit> selectBorrowCreditList() {
        BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
        BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andCreditStatusEqualTo(0);
        return this.borrowCreditMapper.selectByExample(borrowCreditExample);
    }

    @Override
    public Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO) {
        BorrowCredit borrowCredit = new BorrowCredit();
        BeanUtils.copyProperties(borrowCreditVO,borrowCredit);
        boolean result =  this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public BorrowCreditDetailVO getBorrowCreditDetail(String creditNid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("creditNid", creditNid);
        List<BorrowCreditDetailVO> creditDetailList = borrowCreditCustomizeMapper.getBorrowCreditDetail(params);
        if (creditDetailList != null && creditDetailList.size() == 1) {
            BorrowCreditDetailVO creditDetail = creditDetailList.get(0);
            creditDetail.setCreditTimeInt(creditDetail.getCreditTimeInt() + 3 * 24 * 3600);
            return creditDetail;
        }
        return null;
    }

    /**
     * 获取债转信息
     */
	@Override
	public List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request1) {
		 // 获取债转信息
        BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
        BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        if(request1.getCreditNid()!=null){
            borrowCreditCra.andCreditNidEqualTo(Integer.parseInt(request1.getCreditNid()));
        }
        if(request1.getBidNid()!=null){
            borrowCreditCra.andBidNidEqualTo(request1.getBidNid());
        }
        if(request1.getTenderNid()!=null){
            borrowCreditCra.andTenderNidEqualTo(request1.getTenderNid());
        }

        List<BorrowCredit> borrowCredit = this.borrowCreditMapper.selectByExample(borrowCreditExample);
		return borrowCredit;
	}

    /**
     * 获取债权转让列表
     */
    @Override
    public List<AdminBorrowCreditCustomize> getBorrowCreditList4Admin(BorrowCreditAmRequest request) {
       Map<String,Object> params = new HashMap<>();
       params.put("limitStart",request.getLimitStart());
       params.put("limitEnd",request.getLimitEnd());
       params.put("userName",request.getUserName());
       params.put("creditNid",request.getCreditNid());
       params.put("bidNid",request.getBidNid());
       params.put("creditStatus",request.getCreditStatus());
       params.put("timeStart",request.getTimeStart());
       params.put("timeEnd",request.getTimeEnd());
       return borrowCreditCustomizeMapper.getBorrowCreditList4Admin(params);
    }


    /**
     * 获取债权转让count
     */
    @Override
    public Integer countBorrowCreditList4Admin(BorrowCreditAmRequest request) {
        Map<String,Object> params = new HashMap<>();
        params.put("creditNid",request.getCreditNid());
        params.put("userName",request.getUserName());
        params.put("bidNid",request.getBidNid());
        params.put("creditStatus",request.getCreditStatus());
        params.put("timeStart",request.getTimeStart());
        params.put("timeEnd",request.getTimeEnd());
        return borrowCreditCustomizeMapper.countBorrowCreditList4Admin(params);
    }

    /**
     * 统计债权转让统计数据
     * @date 2018/7/9 17:56
     */
    @Override
    public BorrowCreditSumVO getBorrowCreditTotalCount(BorrowCreditAmRequest request) {
        Map<String,Object> params = new HashMap<>();
        params.put("creditNid",request.getCreditNid());
        params.put("userName",request.getUserName());
        params.put("bidNid",request.getBidNid());
        params.put("creditStatus",request.getCreditStatus());
        params.put("timeStart",request.getTimeStart());
        params.put("timeEnd",request.getTimeEnd());
        return borrowCreditCustomizeMapper.getBorrowCreditTotalCount(params);
    }

    /**
     * 债权转让详情count
     * @author zhangyk
     * @date 2018/7/10 17:51
     */
    @Override
    public Integer countBorrowCreditInfo4Admin(BorrowCreditAmRequest request) {
        return borrowCreditCustomizeMapper.countBorrowCreditInfo4Admin(request);
    }


    /**
     * 债权转让详情list
     * @author zhangyk
     * @date 2018/7/10 17:51
     */
    @Override
    public List<AdminBorrowCreditCustomize> getBorrowCreditInfo4Admin(BorrowCreditAmRequest request) {
        return  borrowCreditCustomizeMapper.searchBorrowCreditInfo4Admin(request);
    }

    /**
     * 债权转让详情合计行
     * @author zhangyk
     * @date 2018/7/10 20:29
     */
    @Override
    public BorrowCreditInfoSumVO sumBorrowCreditInfoData(BorrowCreditAmRequest request) {
        return borrowCreditCustomizeMapper.sumBorrowCreditInfoData(request);
    }

    /**
     * 根据userId和tenderNid获取出借债转信息
     * @author zhangyk
     * @date 2018/8/30 10:58
     */
    @Override
    public List<BorrowCredit> getBorrowCreditList(String userId, String tenderNid) {
        if(StringUtils.isEmpty(tenderNid)){
            return null;
        }

        BorrowCreditExample example = new BorrowCreditExample();
        BorrowCreditExample.Criteria criteria =  example.createCriteria();
        criteria.andTenderNidEqualTo(tenderNid);
        if (StringUtils.isNotBlank(userId)){
            criteria.andCreditUserIdEqualTo(Integer.valueOf(userId));
        }
        List<BorrowCredit> list = borrowCreditMapper.selectByExample(example);
        return list;
    }

    /**
     * 根据creditNid获取出借债转信息
     * @author zhangyk
     * @date 2018/8/30 10:58
     */
    @Override
    public List<BorrowCredit> getBorrowCreditListByCreditNid(String creditNid) {
        if(StringUtils.isEmpty(creditNid)){
            return null;
        }

        BorrowCreditExample example = new BorrowCreditExample();
        BorrowCreditExample.Criteria criteria =  example.createCriteria();
        criteria.andCreditNidEqualTo(Integer.valueOf(creditNid));
        List<BorrowCredit> list = borrowCreditMapper.selectByExample(example);
        return list;
    }






}
