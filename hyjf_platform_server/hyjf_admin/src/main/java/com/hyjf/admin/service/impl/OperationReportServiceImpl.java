package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.OperationReportClient;
import com.hyjf.admin.service.OperationReportService;
import com.hyjf.am.response.config.WhereaboutsPictureResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.config.WhereaboutsPagePictureVo;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author tanyy
 * @version 2.0
 */
@Service
public class OperationReportServiceImpl implements OperationReportService {

	@Autowired
	private OperationReportClient operationReportClient;

	@Value("${file.upload.activity.img.path}")
	private String FILEUPLOADTEMPPATH;

	@Override
	public OperationReportResponse getRecordList(OperationReportRequest request) {
		return operationReportClient.getRecordList(request);
	}
	@Override
	public OperationReportResponse listByRelease(OperationReportRequest request){
		return operationReportClient.listByRelease(request);
	}
	@Override
	public OperationReportResponse selectOperationreportCommon(String id) {
		return operationReportClient.selectOperationreportCommon(id);
	}
	@Override
	public OperationReportResponse reportInfo(String id) {
		return operationReportClient.reportInfo(id);
	}
	@Override
	public OperationReportResponse delete(String id){
		return operationReportClient.delete(id);
	}
	@Override
	public OperationReportResponse publish(OperationReportRequest request){
		return operationReportClient.publish(request);
	}
	@Override
	public OperationReportResponse insertOrUpdateMonthAction(OperationReportRequest request){
		return operationReportClient.insertOrUpdateMonthAction(request);
	}
	@Override
	public OperationReportResponse insertOrUpdateQuarterAction(OperationReportRequest request){
		return operationReportClient.insertOrUpdateQuarterAction(request);

	}
	@Override
	public OperationReportResponse insertOrUpdateHalfYearAction(OperationReportRequest request){
		return operationReportClient.insertOrUpdateHalfYearAction(request);

	}
	@Override
	public OperationReportResponse insertOrUpdateYearAction(OperationReportRequest request){
		return operationReportClient.insertOrUpdateYearAction(request);

	}
	@Override
	public OperationReportResponse monthPreview(OperationReportRequest request){
		return operationReportClient.monthPreview(request);

	}
	@Override
	public OperationReportResponse yearPreview(OperationReportRequest request){
		return operationReportClient.yearPreview(request);

	}
	@Override
	public OperationReportResponse quarterPreview(OperationReportRequest request){
		return operationReportClient.quarterPreview(request);

	}
	@Override
	public OperationReportResponse halfPreview(OperationReportRequest request){
		return operationReportClient.halfPreview(request);
	}

}
