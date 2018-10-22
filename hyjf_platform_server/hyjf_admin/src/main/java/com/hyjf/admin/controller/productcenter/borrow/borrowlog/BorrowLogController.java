package com.hyjf.admin.controller.productcenter.borrow.borrowlog;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowLogBean;
import com.hyjf.admin.beans.request.BorrowLogRequsetBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowLogService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.net.URLEncoder;
import java.util.*;

/**
 * @author pangchengchao
 * @version BorrowLogController, v0.1 2018/7/11 9:50
 */

@Api(value = "借款操作日志",tags ="借款操作日志")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowlog")
public class BorrowLogController extends BaseController {

    /** 查看权限 */
    public static final String PERMISSIONS = "borrowlog";
    @Autowired
    private BorrowLogService borrowLogService;
    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "借款操作日志", notes = "借款操作日志页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowLogBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowLogRequsetBean form) {
        BorrowLogRequset copyForm=new BorrowLogRequset();
        BeanUtils.copyProperties(form, copyForm);
        BorrowLogBean bean = borrowLogService.selectBorrowLogList(copyForm);
        bean.setBorrowStatusList(ConvertUtils.convertParamMapToDropDown(CacheUtil.getParamNameMap("BORROW_STATUS")));
        AdminResult<BorrowLogBean> result=new AdminResult<BorrowLogBean> ();
        result.setData(bean);
        return result;
    }

    /**
     * 带条件导出
     * 1.无法指定相应的列的顺序，
     * 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet
     * 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * @param request
     * @return 带条件导出
     */
    @ApiOperation(value = "借款操作日志列表导出", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowLogRequsetBean form) throws Exception {
        BorrowLogRequset copyForm=new BorrowLogRequset();
        BeanUtils.copyProperties(form, copyForm);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "借款操作日志列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        copyForm.setPageSize(defaultRowMaxCount);
        copyForm.setCurrPage(1);
        // 查询
        List<BorrowLogCustomizeVO> resultList = this.borrowLogService.exportBorrowLogList(copyForm);
        Integer totalCount = resultList.size();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = new HashMap<>();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
        }
        for (int i = 1; i < sheetCount; i++) {

            copyForm.setPageSize(defaultRowMaxCount);
            copyForm.setCurrPage(i+1);
            List<BorrowLogCustomizeVO> resultList2 = this.borrowLogService.exportBorrowLogList(copyForm);
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","借款编号");
        map.put("borrowStatus","项目状态");
        map.put("type","修改类型");
        map.put("createUserName","操作人");
        map.put("createTime","操作时间");
        map.put("remark","备注");
        return map;
    }
}
