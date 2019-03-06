/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.assetrepair;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AssetExceptionRequestBean;
import com.hyjf.admin.beans.response.AssetExceptionResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetExceptionService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.admin.utils.ExportExcel;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author PC-LIUSHOUYI
 * @version AssetException, v0.1 2018/9/28 17:54
 */
@Api(value = "异常中心-异常标的处理", tags = "异常中心-异常标的处理")
@RestController
@RequestMapping("/hyjf-admin/asset_exception")
public class AssetExceptionController extends BaseController {

    @Autowired
    AssetExceptionService assetExceptionService;

    /**
     * 异常标的列表查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "异常标的列表查询", notes = "异常标的列表查询")
    @PostMapping(value = "/search")
    @ResponseBody
    public AdminResult<AssetExceptionResponseBean> search(@RequestBody AssetExceptionRequest request) {

        AssetExceptionResponseBean bean = new AssetExceptionResponseBean();

        // 资产来源
        List<DropDownVO> hjhInstConfigList = assetExceptionService.selectHjhInstConfigList();
        bean.setInstNameList(hjhInstConfigList);

        Integer count = assetExceptionService.selectAssetExceptionCount(request);
        count = (count == null) ? 0 : count;
        bean.setTotal(count);
        //分页参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count > 0) {
            List<AssetExceptionCustomizeVO> assetExceptionCustomizeVOList = assetExceptionService.selectAssetExceptionList(request);
            if (assetExceptionCustomizeVOList == null || assetExceptionCustomizeVOList.size() == 0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            bean.setRecordList(assetExceptionCustomizeVOList);
        }
        return new AdminResult(bean);
    }

    /**
     * 异常标的类型下拉值获取
     *
     * @return
     */
    @ApiOperation(value = "异常标的类型下拉值获取", notes = "异常标的类型下拉值获取")
    @GetMapping("/asset_exception_type")
    public AdminResult<List<DropDownVO>> selectAssetExceptionType() {
        Map<String, String> modifyColumnProperty = CacheUtil.getParamNameMap("ASSET_EXCEPTION_TYPE");
        List<DropDownVO> modifyColumnList = ConvertUtils.convertParamMapToDropDown(modifyColumnProperty);
        AdminResult<List<DropDownVO>> result = new AdminResult<List<DropDownVO>>();
        result.setData(modifyColumnList);
        return result;
    }

    /**
     * 添加异常标的
     *
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "添加异常标的", notes = "添加异常标的")
    @PostMapping("/insert_asset_exception")
    public AdminResult insertAssetException(HttpServletRequest request, @RequestBody AssetExceptionRequestBean requestBean) {

        AssetExceptionRequest assetExceptionRequest = new AssetExceptionRequest();
        BeanUtils.copyProperties(requestBean, assetExceptionRequest);

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            assetExceptionRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        assetExceptionRequest.setCreateTime(new Date());

        // 插入异常标的、更新保证金
        boolean isInset = assetExceptionService.insertAssetException(assetExceptionRequest);

        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    @ApiOperation(value = "项目编号是否存在", notes = "项目编号是否存在")
    @GetMapping("/is_exists_borrow/{borrowNid}")
    public AdminResult isExistsBorrow(@PathVariable String borrowNid) {
        String message = this.assetExceptionService.isExistsBorrow(borrowNid);
        if (StringUtils.isNotBlank(message)) {
            return new AdminResult<>(FAIL, message);
        }
        return new AdminResult<>();
    }


    /**
     * 删除异常标的
     *
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "删除异常标的", notes = "删除异常标的")
    @PostMapping("/delete_asset_exception")
    public AdminResult deleteAssetException(HttpServletRequest request, @RequestBody AssetExceptionRequestBean requestBean) {

        AssetExceptionRequest assetExceptionRequest = new AssetExceptionRequest();
        assetExceptionRequest.setId(requestBean.getId());

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            assetExceptionRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        assetExceptionRequest.setCreateTime(new Date());

        // 删除异常标的、更新保证金
        boolean isInset = assetExceptionService.deleteAssetException(assetExceptionRequest);

        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 修改异常标的
     *
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "修改异常标的", notes = "修改异常标的")
    @PostMapping("/update_asset_exception")
    public AdminResult updateAssetException(HttpServletRequest request, @RequestBody AssetExceptionRequestBean requestBean) {

        AssetExceptionRequest assetExceptionRequest = new AssetExceptionRequest();
        BeanUtils.copyProperties(requestBean, assetExceptionRequest);

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            assetExceptionRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        }
        assetExceptionRequest.setUpdateTime(new Date());

        // 删除异常标的、更新保证金
        boolean isInset = assetExceptionService.updateAssetException(assetExceptionRequest);

        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult<>();
    }


    public void exportAssetExceptionExcel(@RequestBody AssetExceptionRequest request, HttpServletResponse response) throws Exception {
        // 表格sheet名称
        String sheetName = "项目异常列表";

        List<AssetExceptionCustomizeVO> assetExceptionCustomizeVOList = assetExceptionService.selectAssetExceptionList(request);
        // 文件名称转码
        String fileName = null;
        try {
            fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("转码错误....", e);
        }
        String[] titles = new String[]{"序号", "资产来源", "项目编号", "借款金额", "异常类型", "异常原因", "项目状态", "异常时间"};

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (assetExceptionCustomizeVOList != null && assetExceptionCustomizeVOList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < assetExceptionCustomizeVOList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel
                            .createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    AssetExceptionCustomizeVO record = assetExceptionCustomizeVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 资产来源
                    else if (celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    // 项目编号
                    else if (celLength == 2) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    // 借款金额
                    else if (celLength == 3) {
                        cell.setCellValue(record.getAccount().toString());
                    }
                    // 异常类型
                    else if (celLength == 4) {
                        cell.setCellValue(record.getExceptionType() == 0 ? "流标" : "删标");
                    }
                    // 异常原因
                    else if (celLength == 5) {
                        cell.setCellValue(record.getExceptionRemark());
                    }
                    // 项目状态
                    else if (celLength == 6) {
                        cell.setCellValue(record.getStatus());
                    }
                    // 异常时间
                    else if (celLength == 7) {
                        cell.setCellValue(record.getExceptionTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
    /**
     * 异常编号列表导出
     *
     * @param request
     * @param response
     */
    @ApiOperation(value = "异常编号列表导出", notes = "异常编号列表导出")
    @PostMapping("/export_asset_exception_excel")
	 public void exportToExcel(HttpServletResponse response, @RequestBody AssetExceptionRequest request,HttpServletRequest requestt) throws Exception {
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
	        // 表格sheet名称
	        String sheetName = "项目异常列表";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

	        List<AssetExceptionCustomizeVO> assetExceptionCustomizeVOList = assetExceptionService.exportAssetExceptionList(request);
	        
	        Integer totalCount = assetExceptionCustomizeVOList.size();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = sheetName + "_第1页";
	        if (totalCount == 0) {
	        	
	            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }else {
	            // 当前下载数据超过一页上限
	            if(defaultRowMaxCount < assetExceptionCustomizeVOList.size()) {
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, assetExceptionCustomizeVOList.subList(0, defaultRowMaxCount));
                    for (int i = 1; i < sheetCount; i++) {
                        sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                        helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, assetExceptionCustomizeVOList.subList(defaultRowMaxCount * i, defaultRowMaxCount * (i + 1)));
                    }
                } else {
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, assetExceptionCustomizeVOList.subList(0, assetExceptionCustomizeVOList.size()));
                }
            }
	        
	        DataSet2ExcelSXSSFHelper.write2Response(requestt, response, fileName, workbook);
	    }

	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("instName", "资产来源");
	        map.put("borrowNid", "项目编号");
	        map.put("account", "借款金额");
	        map.put("exceptionType", "异常类型");
	        map.put("exceptionRemark", "异常原因");
	        map.put("status", "项目状态");
	        map.put("exceptionTime", "异常时间");
	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        IValueFormatter exceptionTypeAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	                Integer exceptionType = (Integer) object;
	                if(exceptionType == 0) {
	                	return "流标";
	                }else {
	                	return "删标";
	                }
	             
	            }
	        };
	        mapAdapter.put("exceptionType", exceptionTypeAdapter);
	        return mapAdapter;
	    }
}