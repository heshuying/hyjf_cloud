/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.associatedrecords;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AssociatedRecordsService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_EXPORT;
import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

/**
 * @author: sunpeikai
 * @version: AssociatedRecodesController, v0.1 2018/7/5 11:25
 */
@Api(value = "资金中心-定向转账-关联记录",tags = "资金中心-定向转账-关联记录")
@Deprecated
@RestController
@RequestMapping(value = "/hyjf-admin/finance/associatedrecords")
public class AssociatedRecodesController extends BaseController {

    @Autowired
    private AssociatedRecordsService associatedRecordsService;
    private static final String PERMISSIONS = "associatedrecords";

    /**
     * 查询关联记录列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "查询关联记录列表",notes = "查询关联记录列表")
    @PostMapping(value = "/getassociatedrecordlist")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<AssociatedRecordListVO>> getAssociatedRecordList(@RequestBody AssociatedRecordListRequest request){
        Integer count = associatedRecordsService.getAssociatedRecordsCount(request);
        count = (count == null)?0:count;
        List<AssociatedRecordListVO> associatedRecordListVoList = associatedRecordsService.getAssociatedRecordList(request);
        return new AdminResult<>(ListResult.build(associatedRecordListVoList,count));
    }
    /**
     * 关联记录列表导出
     * @param request 筛选条件
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出关联记录列表",notes = "导出关联记录列表")
    @PostMapping(value = "/associatedrecordlistexport")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_EXPORT)
    public void exportAssociatedRecordListExcel(@RequestBody AssociatedRecordListRequest request, HttpServletRequest httpRequest,HttpServletResponse response) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "关联记录列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
//        request.setCurrPage(1);
//        request.setPageSize(defaultRowMaxCount);

        Integer count = associatedRecordsService.getAssociatedRecordsCount(request);
        sheetCount = (count % defaultRowMaxCount) == 0 ? count / defaultRowMaxCount : count / defaultRowMaxCount + 1;
        if (count == null || count.equals(0)  ){
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i < sheetCount; i++) {
            request.setCurrPage(i+1);
            request.setPageSize(defaultRowMaxCount);
            List<AssociatedRecordListVO> associatedRecordListVOS2 = associatedRecordsService.getAssociatedRecordList(request);
            if (!CollectionUtils.isEmpty(associatedRecordListVOS2)) {
                String sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  associatedRecordListVOS2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(httpRequest, response, fileName, workbook);
    }


    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("turnOutUsername", "转出账户");
        map.put("turnOutMobile", "转出账户手机");
        map.put("turnOutChinapnrUsrcustid", "转出账户客户号");
        map.put("shiftToUsername", "转入账户");
        map.put("shiftToMobile", "转入账户手机");
        map.put("shiftToChinapnrUsrcustid", "转入账户客户号");
        map.put("associatedState", "关联状态");
        map.put("associatedTime", "关联时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter turnOutChinapnrUsrcustidAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Long turnOutChinapnrUsrcustid = (Long) object;
                return String.valueOf(turnOutChinapnrUsrcustid);
            }
        };
        IValueFormatter shiftToChinapnrUsrcustidAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Long shiftToChinapnrUsrcustid = (Long) object;
                return String.valueOf(shiftToChinapnrUsrcustid);
            }
        };
        IValueFormatter associatedStateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer associatedState = (Integer) object;
                String associatedStateDesc = "";
                switch (associatedState){
                    case 1:
                        associatedStateDesc = "未授权";
                        break;
                    case 2:
                        associatedStateDesc = "成功";
                        break;
                    case 3:
                        associatedStateDesc = "失败";
                        break;
                    default:
                }
                return associatedStateDesc;
            }
        };
        IValueFormatter associatedTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date associatedTime = (Date) object;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format(associatedTime);
            }
        };
        mapAdapter.put("turnOutChinapnrUsrcustid",turnOutChinapnrUsrcustidAdapter);
        mapAdapter.put("shiftToChinapnrUsrcustid",shiftToChinapnrUsrcustidAdapter);
        mapAdapter.put("associatedState",associatedStateAdapter);
        mapAdapter.put("associatedTime",associatedTimeAdapter);
        return mapAdapter;
    }

}
