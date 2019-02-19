package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.user.dao.mapper.auto.AppUtmRegMapper;
import com.hyjf.am.user.dao.mapper.customize.UtmPlatCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.AppUtmRegExample;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fuqiang
 * @version AppUtmRegServiceImpl, v0.1 2018/11/8 17:18
 */
@Service
public class AppUtmRegServiceImpl implements AppUtmRegService {
	@Resource
	private AppUtmRegMapper appUtmRegMapper;
	@Resource
    private UtmPlatCustomizeMapper utmPlatCustomizeMapper;

	@Override
	public AppUtmReg findByUserId(Integer userId) {
		AppUtmRegExample example = new AppUtmRegExample();
		AppUtmRegExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<AppUtmReg> list = appUtmRegMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(AppUtmReg entity) {
		appUtmRegMapper.updateByPrimaryKey(entity);
	}

	@Override
	public void insert(AppUtmReg entity) {
		appUtmRegMapper.insertSelective(entity);
	}

	@Override
	public List<AppUtmReg> exportStatisticsList(AppChannelStatisticsDetailRequest request) {
		AppUtmRegExample example = new AppUtmRegExample();
		AppUtmRegExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getUserNameSrch())) {
			criteria.andUserNameEqualTo(request.getUserNameSrch());
		}
		if (request.getSourceIdSrch() != null) {
			criteria.andSourceIdEqualTo(request.getSourceIdSrch());
		}
		return appUtmRegMapper.selectByExample(example);
	}

	@Override
	public int getAppUtmRegVOCount(AppChannelStatisticsRequest request) {
		AppUtmRegExample example = new AppUtmRegExample();
		AppUtmRegExample.Criteria criteria = example.createCriteria();

		String timeStartSrch = request.getTimeStartSrch();
		String timeEndSrch = request.getTimeEndSrch();
		String sourceId = request.getSourceId();

		if ("registerTime".equals(request.getSourceIdSrch())) {

			// 开始时间查询
			if (StringUtils.isNotBlank(timeStartSrch)) {
				criteria.andRegisterTimeGreaterThanOrEqualTo(GetDate.stringToDate(timeStartSrch));
			}

			// 结束时间查询
			if (StringUtils.isNotBlank(timeEndSrch)) {
				criteria.andRegisterTimeLessThanOrEqualTo(GetDate.stringToDate(timeEndSrch));
			}
		}

		if ("openAccountTime".equals(request.getSourceIdSrch())) {

			// 开始时间查询
			if (StringUtils.isNotBlank(timeStartSrch)) {
				criteria.andOpenAccountTimeGreaterThanOrEqualTo(GetDate.stringToDate(timeStartSrch));
			}

			// 结束时间查询
			if (StringUtils.isNotBlank(timeEndSrch)) {
				criteria.andOpenAccountTimeLessThanOrEqualTo(GetDate.stringToDate(timeEndSrch));
			}

		}

		if (StringUtils.isNotBlank(sourceId)) {
			criteria.andSourceIdEqualTo(Integer.valueOf(sourceId));
		}
		return appUtmRegMapper.countByExample(example);

	}

    @Override
    public List<AppUtmReg> findAll() {
        return appUtmRegMapper.selectByExample(new AppUtmRegExample());
    }

	@Override
	public Integer countAppUtmReg(AppChannelStatisticsDetailRequest request) {
		AppUtmRegExample appUtmRegExample = new AppUtmRegExample();
		AppUtmRegExample.Criteria criteria = appUtmRegExample.createCriteria();
		if (StringUtils.isNotBlank(request.getUserNameSrch())) {
			criteria.andSourceNameEqualTo(request.getUserNameSrch());
		}
		if (request.getSourceIdSrch()!=null) {
			criteria.andSourceIdEqualTo(request.getSourceIdSrch());
		}
		return appUtmRegMapper.countByExample(appUtmRegExample);
	}

	@Override
	public List<AppUtmReg> findAppUtmReg(AppChannelStatisticsDetailRequest request, Paginator paginator) {
		AppUtmRegExample appUtmRegExample = new AppUtmRegExample();
		AppUtmRegExample.Criteria criteria = appUtmRegExample.createCriteria();
		if (StringUtils.isNotBlank(request.getUserNameSrch())) {
			criteria.andSourceNameEqualTo(request.getUserNameSrch());
		}
		if (request.getSourceIdSrch()!=null) {
			criteria.andSourceIdEqualTo(request.getSourceIdSrch());
		}
		if (paginator.getLimit() >= 0 && paginator.getOffset() >= 0) {
			appUtmRegExample.setLimitStart(paginator.getOffset());
			appUtmRegExample.setLimitEnd(paginator.getLimit());
		}
		return appUtmRegMapper.selectByExample(appUtmRegExample);

	}

	@Override
	public int getOpenAccountAttrCount(AppChannelStatisticsRequest request) {

        List<Integer> uesrIdList = utmPlatCustomizeMapper.selectUsersInfo();
        if (CollectionUtils.isEmpty(uesrIdList)) {
            return 0;
        }

        AppUtmRegExample example = new AppUtmRegExample();
        AppUtmRegExample.Criteria criteria = example.createCriteria();

        String timeStartSrch = request.getTimeStartSrch();
        String timeEndSrch = request.getTimeEndSrch();
        String sourceId = request.getSourceId();

        if ("registerTime".equals(request.getSourceIdSrch())) {
            // 开始时间查询
            if (StringUtils.isNotBlank(timeStartSrch)) {
                criteria.andRegisterTimeGreaterThanOrEqualTo(GetDate.stringToDate(timeStartSrch));
            }
            // 结束时间查询
            if (StringUtils.isNotBlank(timeEndSrch)) {
                criteria.andRegisterTimeLessThanOrEqualTo(GetDate.stringToDate(timeEndSrch));
            }
        }
        if ("openAccountTime".equals(request.getSourceIdSrch())) {
            // 开始时间查询
            if (StringUtils.isNotBlank(timeStartSrch)) {
                criteria.andOpenAccountTimeGreaterThanOrEqualTo(GetDate.stringToDate(timeStartSrch));
            }
            // 结束时间查询
            if (StringUtils.isNotBlank(timeEndSrch)) {
                criteria.andOpenAccountTimeLessThanOrEqualTo(GetDate.stringToDate(timeEndSrch));
            }
        }
        if (StringUtils.isNotBlank(sourceId)) {
            criteria.andSourceIdEqualTo(Integer.valueOf(sourceId));
        }
        List<AppUtmReg> appUtmRegList = appUtmRegMapper.selectByExample(example);
        int i = 0;
        for(AppUtmReg vo : appUtmRegList){
            boolean flag = uesrIdList.contains(vo.getUserId());
            if(flag){
                i++;
            }
        }

        return i;
	}

    @Override
    public List<AppUtmReg> getAppUtmRegVO(AppChannelStatisticsRequest request) {
		AppUtmRegExample example = new AppUtmRegExample();
		AppUtmRegExample.Criteria criteria = example.createCriteria();

		String timeStartSrch = request.getTimeStartSrch();
		String timeEndSrch = request.getTimeEndSrch();
		String sourceId = request.getSourceId();

		if ("registerTime".equals(request.getSourceIdSrch())) {

			// 开始时间查询
			if (StringUtils.isNotBlank(timeStartSrch)) {
				criteria.andRegisterTimeGreaterThanOrEqualTo(GetDate.stringToDate(timeStartSrch));
			}

			// 结束时间查询
			if (StringUtils.isNotBlank(timeEndSrch)) {
				criteria.andRegisterTimeLessThanOrEqualTo(GetDate.stringToDate(timeEndSrch));
			}
		}

		if ("openAccountTime".equals(request.getSourceIdSrch())) {

			// 开始时间查询
			if (StringUtils.isNotBlank(timeStartSrch)) {
				criteria.andOpenAccountTimeGreaterThanOrEqualTo(GetDate.stringToDate(timeStartSrch));
			}

			// 结束时间查询
			if (StringUtils.isNotBlank(timeEndSrch)) {
				criteria.andOpenAccountTimeLessThanOrEqualTo(GetDate.stringToDate(timeEndSrch));
			}

		}

		if (StringUtils.isNotBlank(sourceId)) {
			criteria.andSourceIdEqualTo(Integer.valueOf(sourceId));
		}
		return appUtmRegMapper.selectByExample(example);
    }


}
