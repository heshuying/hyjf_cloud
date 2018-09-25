package com.hyjf.am.user.service.admin.extensioncenter.impl;

import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.user.service.admin.extensioncenter.OperationReportJobCountService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationReportJobCountServiceImpl extends BaseServiceImpl implements OperationReportJobCountService {

	@Override
	public int countRegistUser(){
		return keyCountCustomMapper.countRegistUser();
	}
	@Override
	public List<OperationReportJobVO> getSexCount( List<OperationReportJobVO> list){
		return keyCountCustomMapper.getSexCount(list);
	}
    @Override
    public List<OperationReportJobVO> getAgeCount( List<OperationReportJobVO> list){
        return keyCountCustomMapper.getAgeCount(list);
    }
	@Override
	public List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list){
    	if(CollectionUtils.isEmpty(list)){
    		return null;
		}
		return keyCountCustomMapper.getUserNames(list);
	}
	@Override
	public OperationReportJobVO getUserAgeAndArea(int userId){
		return keyCountCustomMapper.getUserAgeAndArea(userId);
	}
	@Override
	public int getTenderAgeByRange(OperationReportJobRequest request){
		return keyCountCustomMapper.getTenderAgeByRange(request);
	}
    @Override
    public List<OperationReportJobVO>  getTenderSexGroupBy(OperationReportJobRequest request){
        return keyCountCustomMapper.getTenderSexGroupBy(request);
    }

	@Override
	public	List<OperationReportJobVO> getTenderCityGroupByUserIds(List<OperationReportJobVO> cityUserIds){
		return keyCountCustomMapper.getTenderCityGroupByUserIds(cityUserIds);
	}

}
