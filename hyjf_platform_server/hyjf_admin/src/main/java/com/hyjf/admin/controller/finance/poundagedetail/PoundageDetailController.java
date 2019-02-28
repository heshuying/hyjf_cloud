/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.poundagedetail;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.PoundageDetailService;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author: sunpeikai
 * @version: PoundageDetailController, v0.1 2018/9/7 9:53
 */
@Api(value = "资金中心-手续费分账详细信息(二级页面)",tags = "资金中心-手续费分账详细信息(二级页面)")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/poundagedetail")
public class PoundageDetailController extends BaseController {
    @Autowired
    private PoundageDetailService poundageDetailService;
    @Autowired
    private PoundageService poundageService;
    @Autowired
    private AdminCommonService adminCommonService;

    @ApiOperation(value = "手续费分账详细信息列表",notes = "手续费分账详细信息列表")
    @PostMapping(value = "/poundagedetaillist")
    public AdminResult poundageDetailList(HttpServletRequest request, @RequestBody AdminPoundageDetailRequest poundageDetailRequest){
        Map<String,Object> result = new HashMap<>();
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        PoundageCustomizeVO poundageCustomizeVO = poundageService.getPoundageById(loginUserId,poundageDetailRequest.getPoundageId());
        result.put("poundage",poundageCustomizeVO);
        // 查询明细对应的手续费配置项
        PoundageLedgerVO poundageLedgerVO = new PoundageLedgerVO();
        if(poundageCustomizeVO != null){
            if(poundageCustomizeVO.getLedgerId()!=null) {
                poundageLedgerVO = poundageDetailService.getPoundageLedgerById(poundageCustomizeVO.getLedgerId());
            }
            result.put("poundageLedger", poundageLedgerVO);
            // 设置明细查询条件
            poundageDetailRequest.setLedgerIdSer(poundageCustomizeVO.getLedgerId());
            if(poundageDetailRequest.getLedgerTimeSer()==null) {
                poundageDetailRequest.setLedgerTimeSer(Integer.parseInt(poundageCustomizeVO.getPoundageTime()));
            }
        }
        Integer count = poundageDetailService.getPoundageDetailCount(poundageDetailRequest);
        count = (count == null)?0:count;
        List<PoundageDetailVO> poundageDetailVOList = poundageDetailService.searchPoundageDetailList(poundageDetailRequest);
        result.put("count",count);
        result.put("poundageDetail",poundageDetailVOList);
        return new AdminResult<>(result);
    }

    /**
     * 获取项目类型下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "项目类型下拉框数据",notes = "项目类型下拉框数据")
    @PostMapping(value = "/getprojecttype")
    public AdminResult<ListResult<DropDownVO>> getSelectorData(){
        List<DropDownVO> projectType = adminCommonService.selectProjectType();
        return new AdminResult<>(ListResult.build(projectType,0));
    }
    /**
     * 导出手续费明细列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "导出手续费明细列表",notes = "导出手续费明细列表")
    @PostMapping(value = "/exportpoundagedetaillist")
    public void exportPoundageDetailList(HttpServletRequest request, HttpServletResponse response,@RequestBody AdminPoundageDetailRequest poundageDetailRequest) throws Exception {
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "手续费分账明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        //手续费分账信息
        PoundageCustomizeVO poundageCustomizeVO = poundageService.getPoundageById(loginUserId,poundageDetailRequest.getPoundageId());

        // 查询明细对应的手续费配置项，format使用
        PoundageLedgerVO poundageLedgerCustomize = this.poundageDetailService.getPoundageLedgerById(poundageCustomizeVO.getLedgerId());
        // 根据手续费配置id和分账时间段查询对应的手续费明细
        poundageDetailRequest.setLedgerIdSer(poundageCustomizeVO.getLedgerId());
        poundageDetailRequest.setLedgerTimeSer(Integer.parseInt(poundageCustomizeVO.getPoundageTime()));
        poundageDetailRequest.setCurrPage(-1);
        Integer totalCount = poundageDetailService.getPoundageDetailCount(poundageDetailRequest);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter(poundageLedgerCustomize,poundageCustomizeVO);
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            poundageDetailRequest.setPageSize(defaultRowMaxCount);
            poundageDetailRequest.setCurrPage(i);
            List<PoundageDetailVO> recordList = this.poundageDetailService.searchPoundageDetailList(poundageDetailRequest);
            logger.info(JSON.toJSONString(recordList));
            if (recordList != null && recordList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }
    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid", "项目编号");
        map.put("borrowType", "项目类型");
        map.put("createTime", "放款/还款时间");
        map.put("usernname", "出借人");
        map.put("investorCompany", "出借人分公司");
        map.put("type", "分账类型");
        map.put("source", "分账来源");
        map.put("serviceRatio", "服务费分账比例");
        map.put("creditRatio", "债转服务费分账比例");
        map.put("manageRatio", "管理费分账比例");
        map.put("amount", "分账金额");
        map.put("userName", "收款方用户名");
        map.put("trueName", "收款方姓名");
        map.put("account", "收款方电子帐号");
        map.put("status", "分账状态");
        map.put("addTime", "实际分账时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter(PoundageLedgerVO poundageLedgerCustomize,PoundageCustomizeVO poundageCustomizeVO) {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter createTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date createTime = (Date) object;
                return createTime == null ? "" : GetDate.dateToString(createTime);
            }
        };
        IValueFormatter investorCompanyAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return poundageLedgerCustomize.getInvestorCompany();
            }
        };
        IValueFormatter typeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return getTypeStr(poundageLedgerCustomize.getType());
            }
        };
        IValueFormatter sourceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return getSourceStr(poundageLedgerCustomize.getSource());
            }
        };
        IValueFormatter serviceRatioAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return getRatio(poundageLedgerCustomize.getServiceRatio());
            }
        };
        IValueFormatter creditRatioAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return getRatio(poundageLedgerCustomize.getCreditRatio());
            }
        };
        IValueFormatter manageRatioAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return getRatio(poundageLedgerCustomize.getManageRatio());
            }
        };
        IValueFormatter amountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal amount = (BigDecimal) object;
                return amount != null ? amount.toString() : "";
            }
        };
        IValueFormatter userNameAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return poundageLedgerCustomize.getUsername();
            }
        };
        IValueFormatter trueNameAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return poundageLedgerCustomize.getTruename();
            }
        };
        IValueFormatter accountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return poundageLedgerCustomize.getAccount();
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return PoundageCustomizeVO.getStatusStr(poundageCustomizeVO.getStatus());
            }
        };
        IValueFormatter addTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer addTime = poundageCustomizeVO.getAddTime();
                return addTime == null ? "" : GetDate.timestamptoStrYYYYMMDDHHMMSS(addTime);
            }
        };
        mapAdapter.put("createTime", createTimeAdapter);
        mapAdapter.put("investorCompany", investorCompanyAdapter);
        mapAdapter.put("type", typeAdapter);
        mapAdapter.put("source", sourceAdapter);
        mapAdapter.put("serviceRatio", serviceRatioAdapter);
        mapAdapter.put("creditRatio", creditRatioAdapter);
        mapAdapter.put("manageRatio", manageRatioAdapter);
        mapAdapter.put("amount", amountAdapter);
        mapAdapter.put("userName", userNameAdapter);
        mapAdapter.put("trueName", trueNameAdapter);
        mapAdapter.put("account", accountAdapter);
        mapAdapter.put("status", statusAdapter);
        mapAdapter.put("addTime", addTimeAdapter);
        return mapAdapter;
    }

    /**
     * 获取分账比例
     *
     * @param ratio
     * @return
     */
    private String getRatio(BigDecimal ratio) {
        if (ratio == null || ratio.compareTo(BigDecimal.valueOf(0.00)) == 0) {
            return "--";
        }
        return ratio.toString();
    }

    private static final int TYPE_INVEST = 1;//"分账类型 1:按出借人分账";
    private static final int TYPE_LOAN = 2;//"分账类型 2:按借款人分账";
    private static final String TYPE_INVEST_STR = "按出借人分账";
    private static final String TYPE_LOAN_STR = "按借款人分账";

    private static final String SOURCE_ALL = "0";//"分账来源  0:全部";
    private static final String SOURCE_SERVICE = "1";//"分账来源 1:服务费";
    private static final String SOURCE_CREDIT = "2"; //"分账来源 2:债转服务费";
    private static final String SOURCE_MANAGE = "3";//"分账来源 3:管理费";
    private static final String SOURCE_ALL_STR = "全部";
    private static final String SOURCE_SERVICE_STR = "服务费";
    private static final String SOURCE_CREDIT_STR = "债转服务费";
    private static final String SOURCE_MANAGE_STR = "管理费";

    /**
     * 获取分账类型信息
     *
     * @param type
     * @return
     * @author wgx
     */
    private static String getTypeStr(int type) {
        switch (type) {
            case TYPE_INVEST:
                return TYPE_INVEST_STR;
            case TYPE_LOAN:
                return TYPE_LOAN_STR;
        }
        return "";
    }

    /**
     * 获取分账来源信息
     *
     * @param source
     * @return
     * @author wgx
     */
    private static String getSourceStr(String source) {
        switch (source) {
            case SOURCE_ALL:
                return SOURCE_ALL_STR;
            case SOURCE_SERVICE:
                return SOURCE_SERVICE_STR;
            case SOURCE_CREDIT:
                return SOURCE_CREDIT_STR;
            case SOURCE_MANAGE:
                return SOURCE_MANAGE_STR;
        }
        return "";
    }

}
