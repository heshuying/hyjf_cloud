package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.resquest.trade.SponsorLogRequest;
import com.hyjf.am.trade.dao.model.auto.SponsorLog;
import com.hyjf.am.trade.dao.model.auto.SponsorLogExample;


public interface SponsorLogService {

	
	List<SponsorLog> getRecordList(SponsorLogExample example);
	int deleteSponsorLog(SponsorLogRequest sponsorLogRequest);
	int insertSponsorLog(SponsorLogRequest sponsorLogRequest);
	int updateSponsorLog(SponsorLogRequest sponsorLogRequest);
	int countSponsorLog(SponsorLogExample example);
}
