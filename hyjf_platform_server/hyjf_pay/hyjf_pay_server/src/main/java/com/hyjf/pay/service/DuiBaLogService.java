package com.hyjf.pay.service;

import com.hyjf.pay.entity.DuiBaReturnLog;
import com.hyjf.pay.entity.DuiBaSendLog;

public interface DuiBaLogService {

	void insertDuiBaSendLog(DuiBaSendLog duiBaSendLog);

	void insertDuiBaReturnLog(DuiBaReturnLog duiBaReturnLog);

}
