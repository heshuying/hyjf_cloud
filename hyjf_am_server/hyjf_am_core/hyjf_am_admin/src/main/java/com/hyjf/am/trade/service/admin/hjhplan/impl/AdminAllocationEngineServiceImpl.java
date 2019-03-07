/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.hjhplan.AdminAllocationEngineService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author libin
 * @version AdminAllocationEngineServiceImpl.java, v0.1 2018年7月3日 下午2:02:58
 */
@Service
public class AdminAllocationEngineServiceImpl extends BaseServiceImpl implements AdminAllocationEngineService{

	
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
				logger.error(e.getMessage());
			}
			/*int starti=(int)start;*/
			crt.andConfigAddTimeLessThanOrEqualTo((int)end+86399);
			crt.andConfigAddTimeGreaterThanOrEqualTo((int)start);
		}
		// 传入排序
		example.setOrderByClause("create_time Desc");
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
 				logger.error(e.getMessage());
 			}
 			/*int starti=(int)start;*/
 			crt.andConfigAddTimeLessThanOrEqualTo((int)end+86399);
 			crt.andConfigAddTimeGreaterThanOrEqualTo((int)start);
 		}
 		
		// 传入排序
		example.setOrderByClause("create_time Desc");
 		
 		
 		list = hjhRegionMapper.selectByExample(example);
 		List<HjhRegionVO> volist = CommonUtils.convertBeanList(list, HjhRegionVO.class);
		return volist;
	}

	@Override
	public String selectPlanNameByPlanNid(AllocationEngineRuquest request) {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        cra.andPlanNidEqualTo(request.getPlanNidSrch());
        List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0).getPlanName();
        }
		return null;
	}

	@Override
	public int insertRecord(HjhRegionVO request) {
		HjhRegion hjhRegion = new HjhRegion();
		BeanUtils.copyProperties(request, hjhRegion);
		int flg = hjhRegionMapper.insertSelective(hjhRegion);
		return flg;
	}

	@Override
	public List<HjhPlan> getHjhPlanByPlanNid(String planNid) {
		if (StringUtils.isNotEmpty(planNid)) {
			HjhPlanExample example = new HjhPlanExample();
			HjhPlanExample.Criteria cra = example.createCriteria();
			cra.andPlanNidEqualTo(planNid);
			List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
			return planList;
		}
		return null;
	}

	@Override
	public List<HjhRegion> getHjhRegionByPlanNid(String planNid) {
		if (StringUtils.isNotEmpty(planNid)) {
			HjhRegionExample example1 = new HjhRegionExample();
			HjhRegionExample.Criteria cra1 = example1.createCriteria();
			cra1.andPlanNidEqualTo(planNid);
			List<HjhRegion> planList1 = this.hjhRegionMapper.selectByExample(example1);
			return planList1;
		}
		return null;
	}

	@Override
	public HjhRegionVO selectHjhRegionVOById(String id) {
		HjhRegionVO vo = new HjhRegionVO();
		HjhRegion hjhRegion = this.hjhRegionMapper.selectByPrimaryKey(Integer.valueOf(id));
		BeanUtils.copyProperties(hjhRegion, vo);
		return vo;
	}

	@Override
	public int updateHjhRegionRecord(HjhRegionVO request) {
		HjhRegion hjhRegion = new HjhRegion();
		BeanUtils.copyProperties(request, hjhRegion);
		hjhRegion.setUpdateTime(new Date());
		int flg = this.hjhRegionMapper.updateByPrimaryKeySelective(hjhRegion);
		return flg;
	}

	@Override
	public int updateAllocationEngineRecord(String planNid, Integer configStatus) {
		int flg = 0;
		if (StringUtils.isNotEmpty(planNid)) {
			HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
			HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
			criteria.andPlanNidEqualTo(planNid);
			List<HjhAllocationEngine> planList = this.hjhAllocationEngineMapper.selectByExample(example);
			if (planList != null && planList.size() > 0) {
				for( int i = 0 ; i < planList.size() ; i++) {
					HjhAllocationEngine hjhAllocationEngine = planList.get(i);
					//只有当计划专区的config_status状态更为0：停用时，将引擎表的计划状态和标签状态也置为0
					if (configStatus == 0) {
						hjhAllocationEngine.setConfigStatus(0);
						hjhAllocationEngine.setLabelStatus(0);
					} else {
						hjhAllocationEngine.setConfigStatus(1);//如果计划专区的计划改为启用，那么引擎的计划状态改为启用
					}
					flg = this.hjhAllocationEngineMapper.updateByPrimaryKeySelective(hjhAllocationEngine);
					//可能會有else
					// 数据更新
				}
			}
		}
		return flg;
	}

	@Override
	public List<HjhRegionVO> selectHjhRegionListWithOutPage(AllocationEngineRuquest request) {
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
 				logger.error(e.getMessage());
 			}
 			crt.andConfigAddTimeLessThanOrEqualTo((int)end+86399);
 			crt.andConfigAddTimeGreaterThanOrEqualTo((int)start);
 		}
 		list = hjhRegionMapper.selectByExample(example);
 		List<HjhRegionVO> volist = CommonUtils.convertBeanList(list, HjhRegionVO.class);
		return volist;
	}

	@Override
	public List<HjhAllocationEngineVO> selectHjhAllocationEngineList(String planNidSrch) {
		List<HjhAllocationEngine> aList;
		HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
		HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNidSrch);
		aList = this.hjhAllocationEngineMapper.selectByExample(example);
		List<HjhAllocationEngineVO> volist = CommonUtils.convertBeanList(aList, HjhAllocationEngineVO.class);
		return volist;
	}

	@Override
	public List<HjhAllocationEngineVO> selectHjhAllocationEngineListByPage(AllocationEngineRuquest request,
			int limitStart, int limitEnd) {
		List<HjhAllocationEngine> aList;
		HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
		HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        criteria.andPlanNidEqualTo(request.getPlanNidSrch());
        aList = this.hjhAllocationEngineMapper.selectByExample(example);
        List<HjhAllocationEngineVO> volist = CommonUtils.convertBeanList(aList, HjhAllocationEngineVO.class);
		return volist;
	}

	@Override
	public HjhAllocationEngineVO selectPlanConfigRecord(Integer engineId) {
		HjhAllocationEngineVO vo = new HjhAllocationEngineVO();
		HjhAllocationEngine hjhAllocationEngine = this.hjhAllocationEngineMapper.selectByPrimaryKey(engineId);
		BeanUtils.copyProperties(hjhAllocationEngine, vo);
		return vo;
	}

	@Override
	public int updateHjhAllocationEngineRecord(HjhAllocationEngineVO request) {
		HjhAllocationEngine engine = new HjhAllocationEngine();
		// 此时也会把主键传入
		BeanUtils.copyProperties(request, engine);
		engine.setUpdateTime(new Date());
		engine.setCreateTime(new Date());
		int flg = this.hjhAllocationEngineMapper.updateByPrimaryKeySelective(engine);
		return flg;
	}

	@Override
	public HjhAllocationEngineVO selectPlanConfigRecordByParam(String planNid, String labelId) {
		HjhAllocationEngineVO vo = new HjhAllocationEngineVO();
		List<HjhAllocationEngine> aList;
		HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
		HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNid);
		criteria.andLabelIdEqualTo(Integer.valueOf(labelId));
		aList = this.hjhAllocationEngineMapper.selectByExample(example);
		HjhAllocationEngine engine = aList.get(0);
		BeanUtils.copyProperties(engine, vo);
		return vo;
	}

	@Override
	public int checkRepeat(AllocationEngineRuquest form) {
		int flg = 0;
		Integer labelId = 0;
		HjhLabelExample example1 = new HjhLabelExample(); 
		HjhLabelExample.Criteria cra = example1.createCriteria();
		cra.andLabelNameEqualTo(form.getLabelName());
		List<HjhLabel> result1 = this.hjhLabelMapper.selectByExample(example1);
		if (result1 != null && result1.size() > 0) {
			 labelId = result1.get(0).getId();
		}
		//这个标签一旦存在引擎，那么新入力标签不只是在本计划添加不了，在其他计划也不能添加
		HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
		HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
		criteria.andLabelNameEqualTo(form.getLabelName());
		/*criteria.andConfigStatusEqualTo(1);//(父)计划专区(是否配置)状态 0：停用 1：启用
*/		criteria.andLabelStatusEqualTo(1);//(子)配置中心的标签状态 0：停用 1：启用
		criteria.andLabelIdEqualTo(labelId);
		/*criteria.andPlanNidEqualTo(form.getPlanNid());*/
		List<HjhAllocationEngine> result = this.hjhAllocationEngineMapper.selectByExample(example);
		if (result != null) {
			flg = result.size();
			return flg;
		}
		return flg;
	}

	@Override
	public String getPlanBorrowStyle(String planNid) {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        cra.andPlanNidEqualTo(planNid);
        List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0).getBorrowStyle();
        }
		return null;
	}

	@Override
	public HjhRegionVO getHjhRegionRecordByPlanNid(String planNid) {
		HjhRegionVO vo = new HjhRegionVO();
		HjhRegion hjhRegion;
		HjhRegionExample example = new HjhRegionExample(); 
		HjhRegionExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNid);
		List<HjhRegion> result = this.hjhRegionMapper.selectByExample(example);
		if (result != null && result.size() > 0) {
			hjhRegion = result.get(0);
			BeanUtils.copyProperties(hjhRegion, vo);
			return vo;
		}
		return null;
	}

	@Override
	public int insertHjhAllocationEngineRecord(HjhAllocationEngineVO request) {
		HjhAllocationEngine engine = new HjhAllocationEngine();
		BeanUtils.copyProperties(request, engine);
		int flg = this.hjhAllocationEngineMapper.insertSelective(engine);
		return flg;
	}

	@Override
	public List<HjhPlanVO> selectHjhPlanByPlanNid(String planNid) {
		List<HjhPlan> planList ;
		HjhPlanExample example = new HjhPlanExample();
		HjhPlanExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNid);
		planList = this.hjhPlanMapper.selectByExample(example);
		List<HjhPlanVO> volist = CommonUtils.convertBeanList(planList, HjhPlanVO.class);
		return volist;
	}

	@Override
	public List<HjhRegionVO> selectHjhRegioByPlanNid(String planNid) {
		List<HjhRegion> regionList ;
		HjhRegionExample example = new HjhRegionExample(); 
		HjhRegionExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNid);
		regionList = this.hjhRegionMapper.selectByExample(example);
		List<HjhRegionVO> volist = CommonUtils.convertBeanList(regionList, HjhRegionVO.class);
		return volist;
	}

	@Override
	public String getPlanNidByLable(Integer labelId) {
		HjhAllocationEngineExample example = new HjhAllocationEngineExample();
		HjhAllocationEngineExample.Criteria cra = example.createCriteria();

		cra.andDelFlagEqualTo(0);
		cra.andLabelIdEqualTo(labelId);
		cra.andConfigStatusEqualTo(1);
		cra.andLabelStatusEqualTo(1);
		List<HjhAllocationEngine> list = this.hjhAllocationEngineMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getPlanNid();
		}

		return null;
	}

	@Override
	public HjhAllocationEngineVO getPlanConfigRecordByPlanNidLabelName(String planNid, String labelName) {
		HjhAllocationEngineVO vo = new HjhAllocationEngineVO();
		List<HjhAllocationEngine> aList;
		HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
		HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNid);
		criteria.andLabelNameEqualTo(labelName);
		aList = this.hjhAllocationEngineMapper.selectByExample(example);
		HjhAllocationEngine engine = aList.get(0);
		BeanUtils.copyProperties(engine, vo);
		return vo;
	}

	@Override
	public List<HjhAllocationEngineVO> selectHjhAllocationEngineListByLabelId(Integer LabelId) {
		List<HjhAllocationEngine> aList;
		HjhAllocationEngineExample example = new HjhAllocationEngineExample(); 
		HjhAllocationEngineExample.Criteria criteria = example.createCriteria();
		criteria.andLabelIdEqualTo(LabelId);
		aList = this.hjhAllocationEngineMapper.selectByExample(example);
		List<HjhAllocationEngineVO> volist = CommonUtils.convertBeanList(aList, HjhAllocationEngineVO.class);
		return volist;
	}
}
