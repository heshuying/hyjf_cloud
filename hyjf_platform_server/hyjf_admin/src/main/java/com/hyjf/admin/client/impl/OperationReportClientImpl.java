package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.OperationLogClient;
import com.hyjf.admin.client.OperationReportClient;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by tanyy on 2018/7/17.
 */
@Service
public class OperationReportClientImpl implements OperationReportClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OperationReportResponse getRecordList(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/list",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse listByRelease(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/listbyrelease",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse selectOperationreportCommon(String id){
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/selectdetail/"+id,OperationReportResponse.class);
        return response;
    }
    @Override
    public OperationReportResponse reportInfo(String id){
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/reportinfo/"+id,OperationReportResponse.class);
        return response;
    }
    @Override
    public OperationReportResponse delete(String id){
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/delete/"+id,OperationReportResponse.class);
        return response;
    }
    @Override
    public OperationReportResponse publish(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/publish",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateMonthAction(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/insertmonthaction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateQuarterAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/insertquarteraction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateHalfYearAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/inserthalfyearaction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateYearAction(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/insertyearaction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse monthPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/monthpreview",request,OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse yearPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/yearpreview",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse quarterPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/quarterpreview",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse halfPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/halfpreview",request,OperationReportResponse.class).getBody();
        return response;
    }
}
