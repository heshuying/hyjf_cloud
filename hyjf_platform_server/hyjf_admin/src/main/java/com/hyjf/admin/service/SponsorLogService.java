package com.hyjf.admin.service;

import com.hyjf.am.response.trade.SponsorLogResponse;
import com.hyjf.am.resquest.trade.SponsorLogRequest;

public interface SponsorLogService {

	SponsorLogResponse sponsorLogList(SponsorLogRequest sponsorLogRequest);

	SponsorLogResponse deleteSponsorLog(SponsorLogRequest sponsorLogRequest);

	SponsorLogResponse insertSponsorLog(SponsorLogRequest sponsorLogRequest);

	SponsorLogResponse selectSponsorLog(SponsorLogRequest sponsorLogRequest);

}
