package com.hyjf.am.trade.service.front.coupon.impl;

import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.CouponCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CouponCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponService;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:52
 */
@Service
public class CouponServiceImpl implements CouponService {

	@Resource
	private CouponCustomizeMapper couponCustomizeMapper;
	@Resource
	private BorrowTenderCpnMapper borrowTenderCpnMapper;
	@Resource
	private CouponTenderMapper couponTenderMapper;
	@Resource
	private CouponRealTenderMapper couponRealTenderMapper;
	@Resource
	private CouponUserMapper couponUserMapper;
	@Resource
	private CouponRecoverMapper couponRecoverMapper;
	/**
	 * @param couponGrantId
	 * @param userId
	 * @Description 根据用户ID和优惠券编号查询优惠券
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 16:51
	 */
	@Override
	public CouponCustomize getCouponUser(String couponGrantId, Integer userId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("couponGrantId", couponGrantId);
		paraMap.put("userId", userId);
		List<CouponCustomize> list = this.couponCustomizeMapper.getCouponUser(paraMap);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 优惠券投资
	 *
	 * @param couponTender
	 */
	@Override
	public void updateCouponTender(CouponTenderVO couponTender) {
		BorrowTenderCpnVO borrowTenderCpn = couponTender.getBorrowTenderCpn();
		CouponTenderVO couponTenderVO = couponTender.getCouponTender();
		CouponRealTenderVO couponRealTender = couponTender.getCouponRealTender();
		CouponUserVO couponUser = couponTender.getCouponUser();

		BorrowTenderCpn btc = new BorrowTenderCpn();
		BeanUtils.copyProperties(borrowTenderCpn,btc);
		borrowTenderCpnMapper.insertSelective(btc);

		CouponTender ct = new CouponTender();
		BeanUtils.copyProperties(couponTenderVO,ct);
		couponTenderMapper.insertSelective(ct);

		CouponRealTender crt = new CouponRealTender();
		BeanUtils.copyProperties(couponRealTender,crt);
		couponRealTenderMapper.insertSelective(crt);

		CouponUser cu = new CouponUser();
		BeanUtils.copyProperties(couponUser,cu);
		couponUserMapper.updateByPrimaryKeySelective(cu);
	}

	/**
	 * 获取优惠券投资
	 *
	 * @param userId
	 * @param borrowNid
	 * @param logOrdId borrowTender表
	 * @param couponGrantId
	 * @return
	 */
	@Override
	public BorrowTenderCpn getCouponTenderByTender(Integer userId, String borrowNid, String logOrdId, Integer couponGrantId) {
		Map<String,Object> paraMap = new HashedMap();
		paraMap.put("userId",userId);
		paraMap.put("borrowNid",borrowNid);
		paraMap.put("logOrdId",logOrdId);
		paraMap.put("couponGrantId",couponGrantId);
		BorrowTenderCpn cpn = couponCustomizeMapper.getCouponTenderByTender(paraMap);
		return cpn;
	}

	/**
	 * 获取优惠券还款记录
	 * @param id
	 * @return
	 */
	@Override
	public CouponRecover getCouponRecoverByPrimaryKey(Integer id) {
		return couponRecoverMapper.selectByPrimaryKey(id);
	}

	/**
	 * 取得优惠券投资信息
	 * @param nid
	 * @return
	 */
	@Override
	public BorrowTenderCpn getCouponTenderInfoByNid(String nid) {
		BorrowTenderCpnExample example = new BorrowTenderCpnExample();
		example.createCriteria().andNidEqualTo(nid);
		List<BorrowTenderCpn> btList = this.borrowTenderCpnMapper.selectByExample(example);
		if(btList!=null&&btList.size()>0){
			return btList.get(0);
		}
		return null;
	}

    @Override
    public List<BorrowTenderCpn> getBorrowTenderCpnHjhList(String orderId) {
		CouponRealTenderExample realExample = new CouponRealTenderExample();
		CouponRealTenderExample.Criteria realCriteria = realExample.createCriteria();
		realCriteria.andRealTenderIdEqualTo(orderId);
		List<CouponRealTender> realTenderList = couponRealTenderMapper.selectByExample(realExample);
		if(realTenderList == null || realTenderList.isEmpty()){
			return new ArrayList<BorrowTenderCpn>();
		}

		BorrowTenderCpnExample example = new BorrowTenderCpnExample();
		BorrowTenderCpnExample.Criteria criteria = example.createCriteria();
		criteria.andNidEqualTo(realTenderList.get(0).getCouponTenderId());
		criteria.andApiStatusEqualTo(0);
		example.setOrderByClause(" id asc ");
		List<BorrowTenderCpn> list = this.borrowTenderCpnMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<BorrowTenderCpn> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId) {
		BorrowTenderCpnExample example = new BorrowTenderCpnExample();
		BorrowTenderCpnExample.Criteria criteria = example.createCriteria();
		criteria.andNidEqualTo(couponOrderId);
		criteria.andApiStatusEqualTo(0);
		example.setOrderByClause(" id asc ");
		List<BorrowTenderCpn> list = this.borrowTenderCpnMapper.selectByExample(example);

		return list;
	}

	@Override
	public int updateBorrowTenderCpn(BorrowTenderCpn borrowTenderCpn) {
		return borrowTenderCpnMapper.updateByPrimaryKeySelective(borrowTenderCpn);
	}

    @Override
    public List<BorrowTenderCpn> getBorrowTenderCpnList(String borrowNid) {
		BorrowTenderCpnExample example = new BorrowTenderCpnExample();
		BorrowTenderCpnExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andApiStatusEqualTo(0);
		example.setOrderByClause(" id asc ");
		List<BorrowTenderCpn> list = this.borrowTenderCpnMapper.selectByExample(example);

		return list;
    }


	@Override
	public AppCouponInfoCustomizeVO getCouponInfo(Map<String, Object> params) {
		return couponCustomizeMapper.getCouponTenderListByUserIdAndOrderId(params);
	}

	@Override
	public List<CurrentHoldRepayMentPlanListVO> getCouponRecoverList(String nid) {
		return couponCustomizeMapper.couponRepaymentPlanList(nid);
	}


	@Override
	public List<AppCouponCustomizeVO> getAppMyPlanCouponInfo(Map<String, Object> params) {
		return couponCustomizeMapper.getMyCouponPlanList(params);
	}
}
