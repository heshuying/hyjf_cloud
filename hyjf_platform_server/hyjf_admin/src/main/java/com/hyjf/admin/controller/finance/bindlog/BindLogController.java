/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bindlog;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BindLogService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.vo.admin.BindLogVO;
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
import java.io.UnsupportedEncodingException;
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
 * @version: BindLogController, v0.1 2018/7/5 15:36
 */
@Api(value = "资金中心-定向转账-绑定日志",tags ="资金中心-定向转账-绑定日志")
@Deprecated
@RestController
@RequestMapping(value = "/hyjf-admin/finance/bindlog")
public class BindLogController extends BaseController {

    @Autowired
    private BindLogService bindLogService;
    private static final String PERMISSIONS = "bindlog";

    /**
     * 查询绑定日志列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "查询绑定日志列表",notes = "查询绑定日志列表")
    @PostMapping(value = "/getbindloglist")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<BindLogVO>> getBindLogList(@RequestBody BindLogListRequest request){
        Integer count = bindLogService.getBindLogCount(request);
        count = (count == null)?0:count;
        List<BindLogVO> bindLogVOList = bindLogService.searchBindLogList(request);
        return new AdminResult<>(ListResult.build(bindLogVOList,count));
    }

    /**
     * 根据筛选条件导出绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "导出绑定日志列表",notes = "根据筛选条件导出绑定日志list")
    @PostMapping(value = "/bindloglistexport")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_EXPORT)
    public void exportBindLogList(HttpServletRequest httpRequest, HttpServletResponse response, @RequestBody BindLogListRequest request) throws UnsupportedEncodingException {


        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "绑定日志列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        request.setCurrPage(1);
        request.setPageSize(defaultRowMaxCount);

        Integer count = bindLogService.getBindLogCount(request);

        // 检索列表
        List<BindLogVO> bindLogVOList = bindLogService.searchBindLogList(request);
        if (count == null || count.equals(0)  ){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = count;
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bindLogVOList);
        }

        for (int i = 1; i < sheetCount; i++) {
            request.setCurrPage(i+1);
            List<BindLogVO> bindLogVOList2 = bindLogService.searchBindLogList(request);
            if (!CollectionUtils.isEmpty(bindLogVOList2)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bindLogVOList2);
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
        map.put("remark","说明");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter turnOutChinapnrUsrcustidAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Long turnOutChinapnrUsrcustid = (Long) object;
                return turnOutChinapnrUsrcustid != null ? String.valueOf(turnOutChinapnrUsrcustid) : "";
            }
        };
        IValueFormatter shiftToChinapnrUsrcustidAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Long shiftToChinapnrUsrcustid = (Long) object;
                return shiftToChinapnrUsrcustid != null ? String.valueOf(shiftToChinapnrUsrcustid) : "";
            }
        };
        IValueFormatter associatedStateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer associatedState = (Integer) object;
                String associatedStateStr = "";
                switch (associatedState){
                    case 0:
                        associatedStateStr = "未授权";
                        break;
                    case 1:
                        associatedStateStr = "成功";
                        break;
                    case 2:
                        associatedStateStr = "失败";
                        break;
                    default:
                }

                return associatedStateStr;
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
