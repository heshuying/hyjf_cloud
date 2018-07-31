package com.hyjf.am.user.service.impl.admin.extensioncenter;

import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.user.dao.mapper.customize.KeyCountCustomMapper;
import com.hyjf.am.user.service.admin.extensioncenter.KeyCountService;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.user.KeyCountVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KeyCountServiceImpl implements KeyCountService {
	@Autowired
	private KeyCountCustomMapper keyCountCustomMapper;
	@Override
	public KeyCountResponse searchAction(KeyCountRequest request){
		KeyCountResponse response = new KeyCountResponse();
		int count = keyCountCustomMapper.countTotal(request);
		response.setCount(count);
		if(count>0){
			Paginator paginator = new Paginator(request.getCurrPage(), count);
			request.setLimitStart(paginator.getOffset());
			request.setLimitEnd(paginator.getLimit());
			List<KeyCountVO> list = keyCountCustomMapper.searchAction(request);
			response.setResultList(list);
		}
		return response;
	}
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

}
