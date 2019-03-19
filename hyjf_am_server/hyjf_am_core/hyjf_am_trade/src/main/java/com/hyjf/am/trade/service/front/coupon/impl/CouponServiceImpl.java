package com.hyjf.am.trade.service.front.coupon.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.customize.CustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CouponCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponService;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class CouponServiceImpl extends CustomizeMapper implements CouponService{

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CouponCustomizeMapper couponCustomizeMapper;
	@Resource
	private CouponTenderCustomizeMapper couponTenderCustomizeMapper;

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
		logger.info("根据用户ID和优惠券编号查询优惠券, get nothing...");
		return null;
	}

	/**
	 * 优惠券出借
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
		logger.info("散标优惠券出借  开始插入 borrowTenderCpn 参数为 {} ",JSONObject.toJSONString(btc));
		borrowTenderCpnMapper.insertSelective(btc);

		CouponTender ct = new CouponTender();
		BeanUtils.copyProperties(couponTenderVO,ct);
		ct.setCreateUserId(Integer.parseInt(couponTenderVO.getAddUser()));
		ct.setUpdateUserId(Integer.parseInt(couponTenderVO.getAddUser()));
		logger.info("散标优惠券出借  开始插入 couponTender 参数为 {} ",JSONObject.toJSONString(ct));
		couponTenderMapper.insertSelective(ct);

		CouponRealTender crt = new CouponRealTender();
		BeanUtils.copyProperties(couponRealTender,crt);
		crt.setCreateUserId(borrowTenderCpn.getUserId());
		crt.setUpdateUserId(borrowTenderCpn.getUserId());
		logger.info("散标优惠券出借  开始插入 couponRealTender 参数为 {} ",JSONObject.toJSONString(crt));
		couponRealTenderMapper.insertSelective(crt);

		CouponUser cu = new CouponUser();
		BeanUtils.copyProperties(couponUser,cu);
		logger.info("散标优惠券出借  开始修改 couponUser 参数为 {} ",JSONObject.toJSONString(cu));
		couponUserMapper.updateByPrimaryKeySelective(cu);
	}

	/**
	 * 获取优惠券出借
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
	 * 取得优惠券出借信息
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

	/**
	 * 根据出借订单号查询此笔出借是否使用优惠券
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public CouponRealTender selectCouponRealTenderByOrderId(String orderId) {
		CouponRealTenderExample example = new CouponRealTenderExample();
		CouponRealTenderExample.Criteria cra = example.createCriteria();
		cra.andRealTenderIdEqualTo(orderId);
		List<CouponRealTender> list = this.couponRealTenderMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据优惠券出借ID查询优惠券出借
	 *
	 * @param couponTenderId
	 * @return
	 */
	@Override
	public CouponTender selectCouponTenderByCouponTenderId(String couponTenderId) {
		CouponTenderExample example = new CouponTenderExample();
		CouponTenderExample.Criteria cra = example.createCriteria();
		cra.andOrderIdEqualTo(couponTenderId);
		List<CouponTender> list = this.couponTenderMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据优惠券ID查询优惠券使用信息
	 *
	 * @param couponGrantId
	 * @return
	 */
	@Override
	public CouponUser selectCouponUserById(Integer couponGrantId) {
		CouponUserExample example = new CouponUserExample();
		CouponUserExample.Criteria cra = example.createCriteria();
		cra.andIdEqualTo(couponGrantId);
		List<CouponUser> list = this.couponUserMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据优惠券出借ID获取优惠券出借信息
	 *
	 * @param couponTenderId
	 * @return
	 */
	@Override
	public BorrowTenderCpn selectBorrowTenderCpnByCouponTenderId(String couponTenderId) {
		BorrowTenderCpnExample example = new BorrowTenderCpnExample();
		BorrowTenderCpnExample.Criteria cra = example.createCriteria();
		cra.andNidEqualTo(couponTenderId);
		List<BorrowTenderCpn> list = this.borrowTenderCpnMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 获取投资红包金额
	 * add by nxl
	 * @param realTenderId
	 * @return
	 */
	@Override
	public BigDecimal getRedPackageSum(String realTenderId) {
		BigDecimal bigDecimalCouponQuota = new BigDecimal("0");
		//9.投资红包：抵扣券报送 红包面值 加息券报送加息券到期收益  没使用券报送0
		//获取优惠券信息
		CouponRealTender couponRealTender = selectCouponRealTenderByOrderId(realTenderId);
		if (null != couponRealTender) {
			//代表使用了红包,各种优惠券等
			//优惠券利息
			BigDecimal bigSumRecover = new BigDecimal("0");
			String sumRecoverInst = couponTenderCustomizeMapper.sunRecoverInterest(couponRealTender.getCouponTenderId());
			if (StringUtils.isNotBlank(sumRecoverInst)) {
				bigSumRecover = new BigDecimal(sumRecoverInst);
			}
			bigDecimalCouponQuota = bigDecimalCouponQuota.add(bigSumRecover);
		}
		return bigDecimalCouponQuota;
	}
}
