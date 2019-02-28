/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bankjournal;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankJournalService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BankEveRequest;
import com.hyjf.am.vo.admin.BankEveVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zdj
 * @version BankAleveController, v0.1 2018/7/20 15:16
 * 资金中心->银行账务明细
 */

@Api(value = "资金中心-银行交易明细",tags = "资金中心-银行交易明细")
@RestController
@RequestMapping("/hyjf-admin/bankeve")
public class BankJournalController {

    @Autowired
    public SystemConfig systemConfig;
    @Autowired
    private BankJournalService bankJournalService;

    /** 权限 */
    public static final String PERMISSIONS = "bankjournal";

    @ApiOperation(value = "银行交易明细", notes = "银行交易明细列表查询")
    @PostMapping(value = "/bankevelist")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<BankEveVO>> getBankeveList(@RequestBody BankEveRequest bankEveRequest){
        Integer count = bankJournalService.queryBankEveCount(bankEveRequest);
        count = (count == null)?0:count;
        List<BankEveVO> bankEveList =bankJournalService.queryBankEveList(bankEveRequest);
        return new AdminResult<>(ListResult.build(bankEveList,count));
    }
    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param bankEveRequest
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "银行交易明细导出", notes = "银行交易明细导出")
    @PostMapping(value = "/exportbankeeve")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportBankeeveList(HttpServletRequest request , HttpServletResponse response, @RequestBody BankEveRequest bankEveRequest) throws UnsupportedEncodingException {


        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "银行交易明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        bankEveRequest.setPageSize(defaultRowMaxCount);
        bankEveRequest.setCurrPage(1);

        Integer count = bankJournalService.queryBankEveCount(bankEveRequest);
        List<BankEveVO> bankEveList =bankJournalService.queryBankEveList(bankEveRequest);
        if (count == null || count.equals(0)){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = count;
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bankEveList);
        }

        for (int i = 1; i < sheetCount; i++) {
            bankEveRequest.setPageSize(defaultRowMaxCount);
            bankEveRequest.setCurrPage(i+1);
            // 需要输出的结果列表
            List<BankEveVO> bankEveList2 =bankJournalService.queryBankEveList(bankEveRequest);
            if (!CollectionUtils.isEmpty(bankEveList2)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankEveList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }


    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("forcode","发送方标识码");
        map.put("seqno","系统跟踪号");
        map.put("cendtString","交易传输时间");
        map.put("cardnbr","主账号");
        map.put("amount","交易金额");
        map.put("crflag","交易金额符号");
        map.put("msgtype","消息类型");
        map.put("proccode","交易类型码");
        map.put("orderno", "订单号");
        map.put("tranno","内部交易流水号");
        map.put("reserved", "内部保留域");
        map.put("revind","冲正撤销标志");
        map.put("transtype", "主机交易类型");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter revindAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if(object instanceof Integer){
                    Integer revind = (Integer) object;
                    return revind == 1 ? "已撤销/冲正" : "";
                }
                return "";
            }
        };
        mapAdapter.put("revind",revindAdapter);
        return mapAdapter;
    }
}
