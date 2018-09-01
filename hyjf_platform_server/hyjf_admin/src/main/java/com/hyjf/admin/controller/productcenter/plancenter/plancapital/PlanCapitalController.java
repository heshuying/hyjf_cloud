package com.hyjf.admin.controller.productcenter.plancenter.plancapital;

import com.hyjf.admin.beans.request.HjhPlanCapitalRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanCapitalService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 资金计划
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划")
@RestController
@RequestMapping(value = "/hyjf-admin/plancapital")
public class PlanCapitalController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanCapitalService planCapitalService;

    /**
     * 计划资金 列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "资金计划列表", notes = "资金计划列表")
    @PostMapping(value = "/init")
    public AdminResult<ListResult<HjhPlanCapitalVO>> init(@RequestBody HjhPlanCapitalRequestBean requestBean){

        HjhPlanCapitalRequest hjhPlanCapitalRequest = new HjhPlanCapitalRequest();
        BeanUtils.copyProperties(requestBean, hjhPlanCapitalRequest);

        //初始化时时间不能为空
        if (StringUtils.isEmpty(requestBean.getDateFromSrch()) && StringUtils.isEmpty(requestBean.getDateToSrch())){
            return new AdminResult<>(FAIL, "日期开始或日期结束不能为空!");
        }

        try{
            Date timeStart = dateFormat.parse(requestBean.getDateFromSrch());
            Date timeEnd = dateFormat.parse(requestBean.getDateToSrch());

            if (timeStart.getTime() > timeEnd.getTime()){
                return new AdminResult<>(FAIL, "结束时间应大于等于开始时间!");
            }
        }catch (ParseException e){
            return new AdminResult<>(FAIL, e.getMessage());
        }

        // 初始化返回list
        List<HjhPlanCapitalVO> returnList = null;

        // 获取结果集
        HjhPlanCapitalResponse planCapitalResponse = planCapitalService.getPlanCapitalList(hjhPlanCapitalRequest);

        if (planCapitalResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(planCapitalResponse)){
            return new AdminResult<>(FAIL, planCapitalResponse.getMessage());
        }

        if (CollectionUtils.isNotEmpty(planCapitalResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(planCapitalResponse.getResultList(), HjhPlanCapitalVO.class);
            return new AdminResult<ListResult<HjhPlanCapitalVO>>(ListResult.build2(returnList, planCapitalResponse.getCount(), planCapitalResponse.getSumHjhPlanCapitalVO()));
        }else {
            return new AdminResult<ListResult<HjhPlanCapitalVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "资金计划列表", notes = "资金计划列表导出")
    @PostMapping(value = "/exportExcel")
    public void exportExcel(@ModelAttribute HjhPlanCapitalRequestBean requestBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HjhPlanCapitalRequest planCapitalRequest = new HjhPlanCapitalRequest();
        BeanUtils.copyProperties(requestBean, planCapitalRequest);

        // currPage<0 为全部,currPage>0 为具体某一页
        planCapitalRequest.setCurrPage(-1);

        // 表格sheet名称
        String sheetName = "资金计划";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // 需要输出的结果列表
        List<HjhPlanCapitalVO> returnList = null;
        HjhPlanCapitalResponse planCapitalResponse = planCapitalService.getPlanCapitalList(planCapitalRequest);

        if (planCapitalResponse.getCount() > 0){
            returnList = CommonUtils.convertBeanList(planCapitalResponse.getResultList(), HjhPlanCapitalVO.class);
        }
        String[] titles = new String[] { "序号", "日期", "计划编号", "计划名称", "锁定期", "复投总额（元）", "债转总额（元）" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (returnList != null && returnList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < returnList.size(); i++) {
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
                    HjhPlanCapitalVO data = returnList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 日期
//                        cell.setCellValue(GetDate.dateToString2(data.getDate()));
                        cell.setCellValue(data.getDate());
                    } else if (celLength == 2) {// 计划编号
                        cell.setCellValue(data.getPlanNid());
                    } else if (celLength == 3) {// 计划名称
                        cell.setCellValue(data.getPlanName());
                    } else if (celLength == 4) {// 锁定期
                        cell.setCellValue(data.getLockPeriod() + (data.getIsMonth()==0?"天":"个月"));
                    } else if (celLength == 5) {// 复投总额（元）
                        cell.setCellValue(CustomConstants.DF_FOR_VIEW.format(data.getReinvestAccount()));
                    } else if (celLength == 6) {// 债转总额（元）
                        cell.setCellValue(CustomConstants.DF_FOR_VIEW.format(data.getCreditAccount()));
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

}
