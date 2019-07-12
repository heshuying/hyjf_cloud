package com.hyjf.admin.service.cert;

import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.response.admin.CertErrorReportLogResponse;
import com.hyjf.am.response.admin.CertReportLogResponse;
import com.hyjf.am.resquest.admin.CertErrorReportLogRequestBean;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;

/**
 * @Description 合规数据上报 CERT 应急中心上报记录
 * @Author sunss
 * @Date 2019/1/22 15:42
 */
public interface CertReportLogService extends BaseAdminService{


	/**
	 * 分页查询
	 * @param requestBean
	 * @return
	 */
	CertReportLogResponse selectCertReportLogList(CertReportLogRequestBean requestBean);

	/**
	 * 分页查询错误日志
	 * @param requestBean
	 * @return
	 */
	CertErrorReportLogResponse selectCertErrorReportLogList(CertErrorReportLogRequestBean requestBean);

	/**
	 * 重新跑批
	 * @param id
	 */
	void updateErrorCount(Integer id);
	/**
	 * 修改对账状态（重新对账）add by nxl
	 * @param certLogId
	 * @return
	 */
	int againReconciliation(Integer certLogId);
	/**
	 * 批量修改对账状态 add by nxl
	 * @param request
	 * @return
	 */
	int batchReconciliation(CertReportLogRequestBean request);
	/**
	 * 批量修改对账状态 add by nxl
	 * @param logOrderId
	 * @return
	 */
	int insertCertErrorLogByLogOrderId(String logOrderId);
}