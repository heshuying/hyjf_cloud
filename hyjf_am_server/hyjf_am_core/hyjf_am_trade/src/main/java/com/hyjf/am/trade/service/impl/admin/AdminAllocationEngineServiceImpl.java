/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.trade.dao.mapper.auto.HjhRegionMapper;
import com.hyjf.am.trade.dao.model.auto.HjhRegion;
import com.hyjf.am.trade.dao.model.auto.HjhRegionExample;
import com.hyjf.am.trade.service.admin.AdminAllocationEngineService;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author libin
 * @version AdminAllocationEngineServiceImpl.java, v0.1 2018年7月3日 下午2:02:58
 */
@Service
public class AdminAllocationEngineServiceImpl implements AdminAllocationEngineService{

    @Autowired
    HjhRegionMapper hjhRegionMapper;
	
	@Override
	public Integer countHjhRegionRecordTotal(AllocationEngineRuquest request) {
		List<HjhRegion> list = null;
		HjhRegionExample example = new HjhRegionExample();
		HjhRegionExample.Criteria crt = example.createCriteria();
		// 条件查询 1--计划编号
		if (StringUtils.isNotEmpty(request.getPlanNidSrch())) {
			//计划编号支持模糊查询
			crt.andPlanNidLike("%" + request.getPlanNidSrch() + "%");	
		}
		// 条件查询 2--计划名称
		if (StringUtils.isNotEmpty(request.getPlanNameSrch())) {
			crt.andPlanNameLike("%" + request.getPlanNameSrch() + "%");
		}
		// 条件查询 3--状态
		if (request.getConfigStatusSrch()!=null) {
			crt.andConfigStatusEqualTo(request.getConfigStatusSrch());
		}
		// 条件查询 4--传入查询添加时间
		if (StringUtils.isNotEmpty(request.getStartCreateSrch())&&StringUtils.isNotEmpty(request.getEndCreateSrch())) {		
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			long start = 0 ;
			long end = 0 ;
			try {
				start = formatter.parse(request.getStartCreateSrch()).getTime()/1000;
				end = formatter.parse(request.getEndCreateSrch()).getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			/*int starti=(int)start;*/
			crt.andConfigAddTimeLessThanOrEqualTo((int)end+86399);
			crt.andConfigAddTimeGreaterThanOrEqualTo((int)start);
		}
		// 总计查询不带分页参数
		list = hjhRegionMapper.selectByExample(example);
		return list.size();
	}

	@Override
	public List<HjhRegionVO> selectHjhRegionList(AllocationEngineRuquest request, int limitStart, int limitEnd) {
		List<HjhRegion> list = null;
		HjhRegionExample example = new HjhRegionExample();
		HjhRegionExample.Criteria crt = example.createCriteria();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        // 条件查询 1--计划编号
 		if (StringUtils.isNotEmpty(request.getPlanNidSrch())) {
 			//计划编号支持模糊查询
 			crt.andPlanNidLike("%" + request.getPlanNidSrch() + "%");	
 		}
 		// 条件查询 2--计划名称
 		if (StringUtils.isNotEmpty(request.getPlanNameSrch())) {
 			crt.andPlanNameLike("%" + request.getPlanNameSrch() + "%");
 		}
 		// 条件查询 3--状态
 		if (request.getConfigStatusSrch()!=null) {
 			crt.andConfigStatusEqualTo(request.getConfigStatusSrch());
 		}
 		// 条件查询 4--传入查询添加时间
 		if (StringUtils.isNotEmpty(request.getStartCreateSrch())&&StringUtils.isNotEmpty(request.getEndCreateSrch())) {		
 			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
 			long start = 0 ;
 			long end = 0 ;
 			try {
 				start = formatter.parse(request.getStartCreateSrch()).getTime()/1000;
 				end = formatter.parse(request.getEndCreateSrch()).getTime()/1000;
 			} catch (ParseException e) {
 				e.printStackTrace();
 			}
 			/*int starti=(int)start;*/
 			crt.andConfigAddTimeLessThanOrEqualTo((int)end+86399);
 			crt.andConfigAddTimeGreaterThanOrEqualTo((int)start);
 		}
 		list = hjhRegionMapper.selectByExample(example);
 		List<HjhRegionVO> volist = CommonUtils.convertBeanList(list, HjhRegionVO.class);
		return volist;
	}
}
