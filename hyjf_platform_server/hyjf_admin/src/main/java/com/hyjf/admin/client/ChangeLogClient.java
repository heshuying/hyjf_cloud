package com.hyjf.admin.client;

import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.resquest.user.ChangeLogRequest;

public interface ChangeLogClient {

	public ChangeLogResponse getChangeLogList(ChangeLogRequest clr);

}
