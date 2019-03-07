package com.hyjf.admin.controller.finance.directionaltransfer;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.DirectionalTransferService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_EXPORT;
import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

@Api(value = "资金中心-定向转账-定向转账",tags = "资金中心-定向转账-定向转账")
@Deprecated
@RestController
@RequestMapping(value = "/hyjf-admin/finance/directionaltransfer")
public class DirectionalTransferController extends BaseController {

    @Autowired
    private DirectionalTransferService directionaltransferService;
    private static final String PERMISSIONS = "directionaltransfer";

    /**
     * 定向转账列表
     * @param request
     * @return
     */
    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping(value = "/getdirectionaltransferlist")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<AccountDirectionalTransferVO>> getDirectionalTransferList(@RequestBody DirectionalTransferListRequest request) {
        Integer count = directionaltransferService.getDirectionalTransferCount(request);
        count = (count == null)?0:count;
        List<AccountDirectionalTransferVO> accountDirectionalTransferVOList = directionaltransferService.searchDirectionalTransferList(request);
        return new AdminResult<>(ListResult.build(accountDirectionalTransferVOList,count));
    }


    /**
     * 定向转账列表导出
     * @param request 筛选条件
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出定向转账列表",notes = "导出定向转账列表")
    @PostMapping(value = "/directionaltransferlistexport")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_EXPORT)
    public void exportDirectionalTransferListExcel(@RequestBody DirectionalTransferListRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "定向转账列表";
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


        Integer count = directionaltransferService.getDirectionalTransferCount(request);

        if (count == null || count <= 0 ){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = count;
            // 检索列表
            List<AccountDirectionalTransferVO> resultList = directionaltransferService.searchDirectionalTransferList(request);
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
        }

        for (int i = 1; i < sheetCount; i++) {
            request.setCurrPage(i+1);
            List<AccountDirectionalTransferVO> resultList2 = directionaltransferService.searchDirectionalTransferList(request);
            if (!CollectionUtils.isEmpty(resultList2)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(httpRequest, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("turnOutUsername", "转出账户");
        map.put("shiftToUsername", "转入账户");
        map.put("orderId", "转账订单号");
        map.put("transferAccountsMoney", "转账金额");
        map.put("transferAccountsState", "转账状态");
        map.put("transferAccountsTime", "转账时间");
        map.put("remark", "说明");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter transferAccountsMoneyAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal transferAccountsMoney = (BigDecimal) object;
                return String.valueOf(transferAccountsMoney);
            }
        };

        IValueFormatter transferAccountsStateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer transferAccountsState = (Integer) object;
                String transferAccountsStateStr = "";
                switch (transferAccountsState){
                    case 0:
                        transferAccountsStateStr = "转账中";
                        break;
                    case 1:
                        transferAccountsStateStr = "成功";
                        break;
                    case 2:
                        transferAccountsStateStr = "失败";
                        break;
                    default:
                }

                return transferAccountsStateStr;
            }
        };
        IValueFormatter transferAccountsTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date transferAccountsTime = (Date) object;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format(transferAccountsTime);
            }
        };
        mapAdapter.put("transferAccountsMoney",transferAccountsMoneyAdapter);
        mapAdapter.put("transferAccountsState",transferAccountsStateAdapter);
        mapAdapter.put("transferAccountsTime",transferAccountsTimeAdapter);
        return mapAdapter;
    }
}
