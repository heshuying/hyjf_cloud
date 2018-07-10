package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.AdminTransferExceptionLogRequest;
import com.hyjf.am.vo.admin.AdminTransferExceptionLogCustomizeVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;

import java.util.List;

/**
 * admin 异常中心-银行转账异常 Create by jijun 20180710
 * service接口定义
 */
public interface TransferExceptionLogService extends BaseAdminService{

    /**
     * 
     * 获取转账异常列表
     * @author jijun
     * @return
     */
	public List<AdminTransferExceptionLogCustomizeVO> getRecordList(AdminTransferExceptionLogRequest request);

	/**
	 * 获取转账异常记录数
	 * @author jijun
	 * @return
	 */
	public Integer countRecord(AdminTransferExceptionLogRequest request);


	/**
	 *	更新转账信息
	 * @param request
	 * @return
	 */
    int updateRecordByUUID(AdminTransferExceptionLogRequest request);

	/**
	 * 更新转账异常
	 * @param transferExceptionLog
	 * @return
	 */
    int updateRecordByUUID(TransferExceptionLogVO transferExceptionLog);

	/**
	 * 获取银行转账异常通过uuid
	 * @param uuid
	 * @return
	 */
	TransferExceptionLogVO getRecordByUUID(String uuid);
}