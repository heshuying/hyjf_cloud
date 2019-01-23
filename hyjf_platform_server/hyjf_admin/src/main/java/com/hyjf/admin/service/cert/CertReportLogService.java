package com.hyjf.admin.service.cert;

import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.response.admin.CertReportLogResponse;
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
}