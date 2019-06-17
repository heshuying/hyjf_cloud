package com.hyjf.am.trade.service.admin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.trade.SponsorLogRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowInfoMapper;
import com.hyjf.am.trade.dao.mapper.auto.SponsorLogMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.SponsorLog;
import com.hyjf.am.trade.dao.model.auto.SponsorLogExample;
import com.hyjf.am.trade.service.admin.SponsorLogService;

@Service
public class SponsorLogServiceImpl implements SponsorLogService{

	@Autowired
	private SponsorLogMapper sponsorLogMapper;
	@Autowired
	private BorrowInfoMapper borrowInfoMapper;
	@Override
	public List<SponsorLog> getRecordList(SponsorLogExample example) {
		
		return sponsorLogMapper.selectByExample(example);
	}

	@Override
	public int deleteSponsorLog(SponsorLogRequest sponsorLogRequest) {
		
		return sponsorLogMapper.deleteByPrimaryKey(sponsorLogRequest.getId());
	}

	@Override
	public int insertSponsorLog(SponsorLogRequest sponsorLogRequest) {
		
//		SponsorLogExample example=new SponsorLogExample();
//		Criteria cr = example.createCriteria();
//		cr.andBorrowNidEqualTo(sponsorLogRequest.getBorrowNid());
//		
//		SponsorLog re=new SponsorLog();
//		re.setDelFlag(1);
//		sponsorLogMapper.updateByExample(re, example);
		SponsorLog record=new SponsorLog();
		BeanUtils.copyProperties(sponsorLogRequest,record);
		record.setCreateUserName(sponsorLogRequest.getAdminUserName());
		record.setUpdateUserName(sponsorLogRequest.getAdminUserName());
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		
		return sponsorLogMapper.insert(record);
	}

	@Override
	public int updateSponsorLog(SponsorLogRequest sponsorLogRequest) {

		//		SponsorLogExample example=new SponsorLogExample();
//		Criteria cri = example.createCriteria();
//		cri.andBorrowNidEqualTo(sponsorLogRequest.getBorrowNid());
//		cri.andStatusEqualTo(0);
//		List<SponsorLog> list=sponsorLogMapper.selectByExample(example);

		SponsorLog sl=sponsorLogMapper.selectByPrimaryKey(sponsorLogRequest.getId());
		BorrowInfoExample example=new BorrowInfoExample();
		example.or().andBorrowNidEqualTo(sl.getBorrowNid());
		BorrowInfoWithBLOBs borrowInfo = borrowInfoMapper.selectByExampleWithBLOBs(example).get(0);
		borrowInfo.setRepayOrgName(sl.getNewSponsor());
		borrowInfo.setIsRepayOrgFlag(1);
		borrowInfo.setRepayOrgUserId(Integer.valueOf(sponsorLogRequest.getNewSponsorId()));
		borrowInfoMapper.updateByPrimaryKeyWithBLOBs(borrowInfo);
		sl.setStatus(sponsorLogRequest.getStatus());
		//sl.setDelFlag(sponsorLogRequest.getDelFlag());
		sl.setUpdateUserName(sponsorLogRequest.getAdminUserName());
		sl.setUpdateTime(new Date());
		return sponsorLogMapper.updateByPrimaryKey(sl);
	}

	@Override
	public int countSponsorLog(SponsorLogExample example) {
		return sponsorLogMapper.countByExample(example);
	}

	

}
