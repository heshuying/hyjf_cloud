package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderCpnMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponRealTenderMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponTenderMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponUserMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.CouponCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.auto.CouponRealTender;
import com.hyjf.am.trade.dao.model.auto.CouponTender;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.trade.CouponCustomize;
import com.hyjf.am.trade.service.CouponService;
import com.hyjf.am.vo.trade.CouponRealTenderVO;
import com.hyjf.am.vo.trade.CouponTenderVO;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

	@Autowired
	private CouponCustomizeMapper couponCustomizeMapper;
	@Autowired
	private BorrowTenderCpnMapper borrowTenderCpnMapper;
	@Autowired
	private CouponTenderMapper couponTenderMapper;
	@Autowired
	private CouponRealTenderMapper couponRealTenderMapper;
	@Autowired
	private CouponUserMapper couponUserMapper;

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
}
