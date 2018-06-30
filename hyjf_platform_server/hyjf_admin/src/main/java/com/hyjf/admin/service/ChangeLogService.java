package com.hyjf.admin.service;

import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.resquest.user.ChangeLogRequest;

public interface ChangeLogService {

	public ChangeLogResponse getChangeLogList(ChangeLogRequest clr);

}
