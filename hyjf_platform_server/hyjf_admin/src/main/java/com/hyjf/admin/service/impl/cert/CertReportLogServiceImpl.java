/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.cert;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.controller.productcenter.plancenter.AccedeListController;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.service.cert.CertReportLogService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.response.admin.CertErrorReportLogResponse;
import com.hyjf.am.response.admin.CertReportLogResponse;
import com.hyjf.am.resquest.admin.CertErrorReportLogRequestBean;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertReportLogServiceImpl extends BaseAdminServiceImpl implements CertReportLogService {
	
    @Autowired
    private AmConfigClient configClient;

	@Autowired
	private CommonProducer commonProducer;

	private static final Logger _log = LoggerFactory.getLogger(AccedeListController.class);

	/**
	 * 分页查询
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public CertReportLogResponse selectCertReportLogList(CertReportLogRequestBean requestBean) {
		CertReportLogResponse response =configClient.selectCertReportLogList(requestBean);
		return response;
	}

	/**
	 * 分页查询错误日志
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public CertErrorReportLogResponse selectCertErrorReportLogList(CertErrorReportLogRequestBean requestBean) {
		CertErrorReportLogResponse response =configClient.selectCertErrorReportLogList(requestBean);
		return response;
	}

	/**
	 * 重新跑批
	 *
	 * @param id
	 */
	@Override
	public void updateErrorCount(Integer id) {
		configClient.updateCertErrorCount(id);
	}
	/**
	 * 修改对账状态（重新对账）add by nxl
	 * @param certLogId
	 * @return
	 */
	@Override
	public int againReconciliation(Integer certLogId){
		return amConfigClient.againReconciliation(certLogId);
	}
	/**
	 * 批量修改对账状态 add by nxl
	 * @param request
	 * @return
	 */
	@Override
	public int batchReconciliation(CertReportLogRequestBean request){
		return amConfigClient.batchReconciliation(request);
	}
	/**
	 * 批量修改对账状态 add by nxl
	 * @param logOrderId
	 * @return
	 */
	@Override
	public int insertCertErrorLogByLogOrderId(String logOrderId){
		return amConfigClient.insertCertErrorLogByLogOrderId(logOrderId);
	}
}
