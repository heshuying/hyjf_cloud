/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.pushMoney;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PushMoneyManageService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author zdj
 * @version PushMoneyManageController, v0.1 2018/7/3 15:16
 * 资金中心->直投提成管理
 */

@Api(value = "资金中心-直投提成管理",tags = "资金中心-直投提成管理")
@RestController
@RequestMapping("/hyjf-admin/finance/pushMoney")
public class PushMoneyManageController extends BaseController {
    @Autowired
    private PushMoneyManageService pushMoneyManageService;

    /** 权限 */
    public static final String PERMISSIONS = "pushmoneymanagelist";

    @ApiOperation(value = "直投提成管理", notes = "直投提成管理列表查询")
    @PostMapping(value = "/pushmoneylist")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<PushMoneyVO>> getPushMoneyList( @RequestBody @Valid PushMoneyRequest request) {
        // 初始化原子层请求实体
        // 初始化返回LIST
        List<PushMoneyVO> pushMoneyList = null;
        // 将画面请求request赋值给原子层 request
        // 根据删选条件获取计划列表

        PushMoneyResponse response = pushMoneyManageService.findPushMoneyList(request);
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        if(CollectionUtils.isNotEmpty(response.getResultList())){
            // 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
            pushMoneyList = CommonUtils.convertBeanList(response.getResultList(), PushMoneyVO.class);
            return new AdminResult<ListResult<PushMoneyVO>>(ListResult.build(pushMoneyList, response.getCount()));
        } else {
            return new AdminResult<ListResult<PushMoneyVO>>(ListResult.build(pushMoneyList, 0));
        }
    }
    //计算提成
    @ApiOperation(value = "计算提成", notes = "计算提成")
    @PostMapping(value = "/calculateushmoney")
    public JSONObject calculatePushMoney(@RequestBody @Valid PushMoneyRequestBean requestBean){
        // 初始化原子层请求实体
        PushMoneyRequest form = new PushMoneyRequest();
        // 将画面请求request赋值给原子层 request
        BeanUtils.copyProperties(requestBean, form);
        JSONObject jsonObject = new JSONObject();
        // 提成ID
        String borrowNid = form.getBorrowNid();
        // 取得借款API表
        BorrowApicronVO apicron = this.pushMoneyManageService.getBorrowApicronBorrowNid(borrowNid);
        String status= Response.SUCCESS;
        if (apicron == null) {
            jsonObject.put("record","该项目不存在!");
            status = Response.FAIL;
        }
        if (GetterUtil.getInteger(apicron.getWebStatus()) == 1) {
            jsonObject.put("record","该标的提成已经计算完成!");
            status = Response.SUCCESS;
        }
        int cnt = -1;
        try {
            // 发提成处理
            cnt = this.pushMoneyManageService.insertTenderCommissionRecord(apicron.getId(), form);
        } catch (Exception e) {
            jsonObject.put("record","提成计算失败,请重新操作!");
            status = Response.FAIL;
        }

        if (cnt >= 0) {
            jsonObject.put("record","提成计算成功！");
            status = Response.SUCCESS;
        } else {
            jsonObject.put("record","提成计算失败,请重新操作!");
            status = Response.FAIL;
        }
        jsonObject.put("status",status);
        return jsonObject;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param requestBean
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "直投提成管理记录导出", notes = "直投提成管理记录导出")
    @PostMapping(value = "/exportpushmoney")
    public void exportPushMoney(HttpServletRequest request, HttpServletResponse response,@RequestBody PushMoneyRequest requestBean) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "直投提成管理";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        requestBean.setCurrPage(1);
        requestBean.setPageSize(defaultRowMaxCount);
        Integer totalCount = pushMoneyManageService.findPushMoneyList(requestBean).getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            requestBean.setPageSize(defaultRowMaxCount);
            requestBean.setCurrPage(i);
            List<PushMoneyVO> pushMoneyVOList = pushMoneyManageService.findPushMoneyList(requestBean).getResultList();
            if (pushMoneyVOList != null && pushMoneyVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  pushMoneyVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {

        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid", "项目编号");
        map.put("borrowName", "项目标题");
        map.put("borrowPeriodByStyle", "融资期限");
        map.put("account", "融资金额");
        map.put("commission", "提成总额");
        map.put("recoverLastTime", "放款时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        return Maps.newHashMap();
    }




    /**
     * 直投提成列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "直投提成列表", notes = "直投提成列表")
    @PostMapping(value = "/pushMoneyList")
    public AdminResult pushMoneyList(@RequestBody PushMoneyRequest request){
        Map<String,Object> result = new HashMap<>();
        Integer count = pushMoneyManageService.getPushMoneyListCount(request);
        count = (count == null)?0:count;
        result.put("count",count);
        List<PushMoneyVO> pushMoneyVOList = pushMoneyManageService.searchPushMoneyList(request);
        result.put("pushMoneyVOList",pushMoneyVOList);
        Map<String,Object> totle = pushMoneyManageService.queryPushMoneyTotle(request);
        result.put("pushMoneyTotle",totle);
        return new AdminResult<>(result);
    }

    /**
     * 直投提成列表导出
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "直投提成列表导出",notes = "直投提成列表导出")
    @PostMapping(value = "/exportPushMoneyDetailExcelAction")
    public void exportPushMoneyDetailExcelAction(@RequestBody @Valid PushMoneyRequest pushMoneyRequest,HttpServletResponse response) throws UnsupportedEncodingException {
        // currPage<0 为全部,currPage>0 为具体某一页
        pushMoneyRequest.setCurrPage(-1);

        // 设置默认查询时间
/*        if (StringUtils.isEmpty(pushMoneyRequest.getStartDate())) {
            pushMoneyRequest.setStartDate(GetDate.getDate("yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(pushMoneyRequest.getEndDate())) {
            pushMoneyRequest.setEndDate(GetDate.getDate("yyyy-MM-dd"));
        }*/
        // 表格sheet名称
        String sheetName = "推广提成发放列表";

        List<PushMoneyVO> recordList = pushMoneyManageService.searchPushMoneyList(pushMoneyRequest);
        logger.debug(JSON.toJSONString(recordList));
/*
        PushMoneyCustomize pushMoneyCustomize = new PushMoneyCustomize();
        BeanUtils.copyProperties(form, pushMoneyCustomize);
        pushMoneyCustomize.setTenderType(1);
        List<PushMoneyCustomize> recordList = this.pushMoneyService.queryPushMoneyDetail(pushMoneyCustomize);
*/

        String fileName =
                URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles =
                new String[] { "序号", "项目编号", "融资期限", "分公司", "分部", "团队", "提成人", "电子账号", "用户属性", "投资人", "投资金额", "提成金额",
                        "状态", "投资时间", "发放时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (recordList != null && recordList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < recordList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    PushMoneyVO bean = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 项目编号
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getBorrowNid());
                    }
                    // 融资期限
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getRzqx());
                    }
                    // 分公司
                    else if (celLength == 3) {
                        cell.setCellValue(bean.getRegionName());
                    }
                    // 分部
                    else if (celLength == 4) {
                        cell.setCellValue(bean.getBranchName());
                    }
                    // 团队
                    else if (celLength == 5) {
                        cell.setCellValue(HtmlUtil.unescape(bean.getDepartmentName()));
                    }
                    // 提成人
                    else if (celLength == 6) {
                        cell.setCellValue(bean.getUsername());
                    }
                    else if (celLength == 7) {
                        cell.setCellValue(bean.getAccountId());
                    }
                    // 提成人用户属性
                    else if (celLength == 8) {
                        // cell.setCellValue(bean.getAttributeName());
                        String attribute = "";
                        if ("0".equals(bean.getAttribute())) {
                            attribute = "无主单";
                        } else if ("1".equals(bean.getAttribute())) {
                            attribute = "有主单";
                        } else if ("2".equals(bean.getAttribute())) {
                            attribute = "线下员工";
                        } else if ("3".equals(bean.getAttribute())) {
                            attribute = "线上员工";
                        }
                        cell.setCellValue(attribute);
                    }
                    // 51老用户
/*                    else if (celLength == 9) {
                        cell.setCellValue(bean.getIs51Name());
                    }*/
                    // 投资人
                    else if (celLength == 9) {
                        cell.setCellValue(bean.getUsernameTender());
                    }
                    // 投资金额
                    else if (celLength == 10) {
                        cell.setCellValue(bean.getAccountTender().toString());
                    }
                    // 提成金额
                    else if (celLength == 11) {
                        cell.setCellValue(bean.getCommission().toString());
                    }
                    // 状态
                    else if (celLength == 12) {
                        cell.setCellValue(bean.getStatusName());
                    }
                    // 投资时间
                    else if (celLength == 13) {
                        cell.setCellValue(bean.getTenderTimeView());
                    }
                    // 发放时间
                    else if (celLength == 14) {
                        cell.setCellValue(bean.getSendTimeView());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }


    @ApiOperation(value = "直投提成管理记录导出", notes = "直投提成管理记录导出")
    @PostMapping(value = "/exportpushmoney")
    public void exportPushMoney1(HttpServletRequest request, HttpServletResponse response,@RequestBody PushMoneyRequest requestBean) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "直投提成管理";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        requestBean.setCurrPage(1);
        requestBean.setPageSize(defaultRowMaxCount);
        Integer totalCount = pushMoneyManageService.findPushMoneyList(requestBean).getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildDetailsMap();
        Map<String, IValueFormatter> mapValueAdapter = buildDetailsValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            requestBean.setPageSize(defaultRowMaxCount);
            requestBean.setCurrPage(i);
            List<PushMoneyVO> pushMoneyVOList = pushMoneyManageService.findPushMoneyList(requestBean).getResultList();
            if (pushMoneyVOList != null && pushMoneyVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  pushMoneyVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildDetailsMap() {

        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid", "项目编号");
        map.put("borrowName", "项目标题");
        map.put("borrowPeriodByStyle", "融资期限");
        map.put("account", "融资金额");
        map.put("commission", "提成总额");
        map.put("recoverLastTime", "放款时间");
        return map;
    }
    private Map<String, IValueFormatter> buildDetailsValueAdapter() {
        return Maps.newHashMap();
    }




    @ApiOperation(value = "发提成",notes = "发提成")
    @PostMapping(value = "/confirmPushMoneyAction")
    public AdminResult confirmPushMoneyAction(HttpServletRequest request, @RequestBody PushMoneyRequest pushMoneyRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        AdminResult result = new AdminResult();
        Integer id = pushMoneyRequest.getId();
        JSONObject jsonObject = pushMoneyManageService.pushMoney(request,id,userId);
        result.setStatusInfo(jsonObject.getString("status"),jsonObject.getString("statusDesc"));
        return result;
    }

}
