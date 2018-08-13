/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon.impl;

import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.trade.TransferExceptionLogWithBLOBsVO;
import com.hyjf.am.trade.dao.mapper.auto.CouponConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.TransferExceptionLogMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponConfigCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CouponConfigCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponTenderCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponConfigService;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponConfigServiceImpl, v0.1 2018/6/19 19:30
 */
@Service
public class CouponConfigServiceImpl implements CouponConfigService {

	@Resource
	private CouponConfigMapper couponConfigMapper;
	@Resource
	private CouponConfigCustomizeMapper couponConfigCustomizeMapper;
	@Resource
	private CouponRecoverMapper couponRecoverMapper;
	@Resource
	private TransferExceptionLogMapper transferExceptionLogMapper;
	@Resource
	private CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;
	/**
	 * 根据优惠券编号查找优惠券配置
	 * 
	 * @param couponCode
	 * @return
	 */
	@Override
	public CouponConfig selectCouponConfig(String couponCode) {
		CouponConfigExample cExample = new CouponConfigExample();
		cExample.createCriteria().andCouponCodeEqualTo(couponCode);
		List<CouponConfig> couponConfigList = couponConfigMapper.selectByExample(cExample);
		if (!CollectionUtils.isEmpty(couponConfigList)) {
            return couponConfigList.get(0);
        }
		return null;
	}

	/**
	 * 获取记录数
	 * @param mapParam
	 * @return
	 */
	@Override
	public int countRecord(Map<String, Object> mapParam) {
		return couponConfigCustomizeMapper.countCouponConfig(mapParam);
	}

	/**
	 * 获取列表
	 * @param mapParam
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	@Override
	public List<CouponConfigCustomize> getRecordList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
		// 封装查询条件
		if (limitStart == 0 || limitStart > 0) {
			mapParam.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			mapParam.put("limitEnd", limitEnd);
		}
		//查询用户列表
		List<CouponConfigCustomize> recordList = couponConfigCustomizeMapper.selectCouponConfigList(mapParam);
		return recordList;
	}

	/**
	 * 获取编辑页信息
	 * @param id
	 * @return
	 */
	@Override
	public CouponConfig getCouponConfig(int id) {
		CouponConfig couponConfig = couponConfigMapper.selectByPrimaryKey(id);
		if (couponConfig != null) {
			return couponConfig;
		}
		return null;
	}

	/**
	 * 保存编辑信息
	 * @param couponConfig
	 * @return
	 */
	@Override
	public Map<String, Object> saveCouponConfig(CouponConfig couponConfig) {
		int count = couponConfigMapper.updateByPrimaryKey(couponConfig);
		Map<String,Object> map = new HashMap<>();
		if (count > 0) {
			map.put("success",true);
		}else {
			map.put("msg", "保存失败");
		}
		return map;
	}

	/**
	 * 添加发行信息
	 * @param couponConfig
	 * @return
	 */
	@Override
	public Map<String, Object> insertAction(CouponConfig couponConfig) {
		int insert = couponConfigMapper.insert(couponConfig);
		Map<String,Object> map = new HashMap<>();
		if (insert > 0) {
			map.put("success", true);
		} else {
			map.put("msg", "添加失败");
		}
		return map;
	}

	/**
	 * 根据id删除发行信息
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> deleteCouponConfig(int id) {
		int i = couponConfigMapper.deleteByPrimaryKey(id);
		Map<String,Object> map = new HashMap<>();
		if (i > 0) {
			map.put("success", true);
		} else {
			map.put("msg", "删除失败");
		}
		return map;
	}

	/**
	 * 根据优惠券编号查询已发行数量
	 * @param couponCode
	 * @return
	 */
	@Override
	public int checkCouponSendExcess(String couponCode) {
		int remain = couponConfigCustomizeMapper.checkCouponSendExcess(couponCode);
		return remain;
	}

    @Override
    public CouponConfigVO getCouponConfigByOrderId(String ordId) {
        return couponConfigCustomizeMapper.getCouponConfigByOrderId(ordId);
    }

    @Override
    public Integer countByTenderId(String tenderNid) {
		CouponRecoverExample checkExample = new CouponRecoverExample();
		checkExample.createCriteria().andTenderIdEqualTo(tenderNid);
		int count = couponRecoverMapper.countByExample(checkExample);
		return count;
    }

    @Override
    public Integer crRecoverPeriod(String tenderNid, Integer currentRecoverFlg, Integer period) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("currentRecoverFlg", currentRecoverFlg);
		paramMap.put("tenderNid", tenderNid);
		paramMap.put("period", period);
		couponConfigCustomizeMapper.crRecoverPeriod(paramMap);
		return 1;
    }

	@Override
	public Integer getCouponProfitTime(String tenderNid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nid", tenderNid);
		return couponConfigCustomizeMapper.getCouponProfitTime(paramMap);
	}

	@Override
	public Integer insertCouponRecover(CouponRecoverVO cr) {
		return  couponRecoverMapper.insertSelective(CommonUtils.convertBean(cr,CouponRecover.class));
	}

	@Override
	public Integer updateOfLoansTenderHjh(AccountVO account) {
		return couponConfigCustomizeMapper.updateOfLoansTenderHjh(account);
	}

    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		// 1 项目到期还款  2 收益期限到期还款
		paramMap.put("repayTimeConfig", 1);
        return couponConfigCustomizeMapper.getCouponTenderListHjh(paramMap);
    }

    @Override
    public void updateCouponRecover(CouponRecoverVO cr) {
		couponRecoverMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(cr,CouponRecover.class));
    }

	@Override
	public List<TransferExceptionLogVO> selectByRecoverId(Integer recoverId) {
		TransferExceptionLogExample example = new TransferExceptionLogExample();
		example.createCriteria().andRecoverIdEqualTo(recoverId);
		List<TransferExceptionLog> listLog = this.transferExceptionLogMapper.selectByExample(example);
		return CommonUtils.convertBeanList(listLog,TransferExceptionLogVO.class);
	}

	@Override
	public Integer insertTransferExLog(TransferExceptionLogWithBLOBsVO transferExceptionLog) {
		return transferExceptionLogMapper.insertSelective(CommonUtils.convertBean(transferExceptionLog,TransferExceptionLogWithBLOBs.class));
	}

    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowNid", borrowNid);
		// 1 项目到期还款  2 收益期限到期还款
		paramMap.put("repayTimeConfig", 1);
		List<CouponTenderCustomize> list = this.couponRecoverCustomizeMapper.selectCouponRecoverAll(paramMap);
		return CommonUtils.convertBeanList(list,CouponTenderCustomizeVO.class);
    }

	@Override
	public Integer updateRecoverPeriod(String tenderNid, Integer period) {
		Map<String, Object> paramMapCurrent = new HashMap<String, Object>();
		paramMapCurrent.put("currentRecoverFlg", 1);
		paramMapCurrent.put("tenderNid", tenderNid);
		paramMapCurrent.put("period", period);
		this.couponRecoverCustomizeMapper.crRecoverPeriod(paramMapCurrent);
		return 1;
	}

	@Override
	public String selectCouponInterestTotal(Integer userId){
		String couponInterestTotalWait = couponRecoverCustomizeMapper.selectCouponInterestTotal(userId);
		return couponInterestTotalWait;
	}

	@Override
	public String selectCouponReceivedInterestTotal(Integer userId) {
		String couponInterestTotalWait = couponRecoverCustomizeMapper.selectCouponReceivedInterestTotal(userId);
		return couponInterestTotalWait;
	}


	@Override
	public List<CouponConfigCustomize> getCouponConfigList(CouponConfigRequest request) {
		Map<String,Object> map = new HashMap<>();
		map.put("status",request.getStatus());
		map.put("limitStart",-1);
		map.put("limitEnd",-1);
		return couponConfigCustomizeMapper.selectCouponConfigList(map);
	}

}
