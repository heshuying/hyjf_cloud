package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.OperationReportService;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author tanyy
 * @version 2.0
 */
@Service
public class OperationReportServiceImpl implements OperationReportService {

	@Autowired
	private CsMessageClient csMessageClient;

	@Value("${file.domain.url}")
	private String FILEDOMAILURL;
	@Value("${file.physical.path}")
	private String FILEPHYSICALPATH;
	@Value("${file.upload.temp.path}")
	private String FILEUPLOADTEMPPATH;

	@Override
	public OperationReportResponse getRecordList(OperationReportRequest request) {
		return csMessageClient.getRecordList(request);
	}
	@Override
	public OperationReportResponse listByRelease(OperationReportRequest request){
		return csMessageClient.listByRelease(request);
	}
	@Override
	public OperationReportResponse selectOperationreportCommon(String id) {
		return csMessageClient.selectOperationreportCommon(id);
	}
	@Override
	public OperationReportResponse reportInfo(String id) {
		return csMessageClient.reportInfo(id);
	}
	@Override
	public OperationReportResponse delete(String id){
		return csMessageClient.delete(id);
	}
	@Override
	public OperationReportResponse publish(OperationReportRequest request){
		return csMessageClient.publish(request);
	}
	@Override
	public OperationReportResponse insertOrUpdateMonthAction(OperationReportRequest request){
		return csMessageClient.insertOrUpdateMonthAction(request);
	}
	@Override
	public OperationReportResponse insertOrUpdateQuarterAction(OperationReportRequest request){
		return csMessageClient.insertOrUpdateQuarterAction(request);

	}
	@Override
	public OperationReportResponse insertOrUpdateHalfYearAction(OperationReportRequest request){
		return csMessageClient.insertOrUpdateHalfYearAction(request);

	}
	@Override
	public OperationReportResponse insertOrUpdateYearAction(OperationReportRequest request){
		return csMessageClient.insertOrUpdateYearAction(request);

	}
	@Override
	public OperationReportResponse monthPreview(OperationReportRequest request){
		return csMessageClient.monthPreview(request);

	}
	@Override
	public OperationReportResponse yearPreview(OperationReportRequest request){
		return csMessageClient.yearPreview(request);

	}
	@Override
	public OperationReportResponse quarterPreview(OperationReportRequest request){
		return csMessageClient.quarterPreview(request);

	}
	@Override
	public OperationReportResponse halfPreview(OperationReportRequest request){
		return csMessageClient.halfPreview(request);
	}
}
