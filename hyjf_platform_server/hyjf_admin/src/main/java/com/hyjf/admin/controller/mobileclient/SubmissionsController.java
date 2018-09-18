package com.hyjf.admin.controller.mobileclient;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.mobileclient.SubmissionsService;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 意见反馈
 * @author lisheng
 * @version SubmissionsController, v0.1 2018/7/11 11:25
 */
@Api(tags = "移动客户端-意见反馈")
@RestController
@RequestMapping("/hyjf-admin/submissions")
public class SubmissionsController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SubmissionsService  submissionsService;

    /**
     * 查询列表
     * @return
     */
    @ApiOperation(value = "意见反馈列表查询", notes = "意见反馈列表查询")
    @PostMapping("/getRecordList")
    @ResponseBody
    public AdminResult<ListResult<SubmissionsCustomizeVO>> findAppBannerData(@RequestBody SubmissionsRequest form) {
        try {
            SubmissionsResponse response = new SubmissionsResponse();
            Map<String, String> userStatus = CacheUtil.getParamNameMap("CLIENT");
            if (StringUtils.isNotBlank(form.getUserName())) {
                UserResponse user = submissionsService.getUserIdByUserName(form.getUserName());
                if (user != null && user.getResult() != null) {
                    Integer userId = user.getResult().getUserId();
                    form.setUserId(userId);
                }
            }
            SubmissionsResponse submissionList = submissionsService.getSubmissionList(form);
            List<SubmissionsCustomizeVO> resultList = submissionList.getResultList();
            for (SubmissionsCustomizeVO submissionsCustomizeVO : resultList) {
                String type = userStatus.get(submissionsCustomizeVO.getSysType()) + "-" + submissionsCustomizeVO.getSysVersion();
                submissionsCustomizeVO.setSysType(type);
                Integer userId = submissionsCustomizeVO.getUserId();
                UserResponse users = submissionsService.getUserIdByUserId(userId);
                String userName = users.getResult() != null ? users.getResult().getUsername() : "";
                submissionsCustomizeVO.setUserName(userName);
            }

            return new AdminResult<ListResult<SubmissionsCustomizeVO>>(ListResult.build(submissionList.getResultList(), submissionList.getRecordTotal()));

        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

    }

    /**
     * 意见反馈更新保存
     *
     * @return
     * @throws ParseException
     */
    @ApiOperation(value = "意见反馈更新保存", notes = "意见反馈更新保存")
    @PostMapping("/updateSubmissionsAction")
    public AdminResult<ListResult<SubmissionsCustomizeVO>> updateSubmissions(@RequestBody SubmissionsRequest form) {
        try {
            SubmissionsResponse response = submissionsService.updateSubmissionStatus(form);
            return new AdminResult<ListResult<SubmissionsCustomizeVO>>(ListResult.build(response.getResultList(), response.getRecordTotal()));
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "意见反馈导出", notes = "意见反馈导出")
    @PostMapping("/exportListAction")
    public void exportListAction(@RequestBody SubmissionsRequest form,
                                 HttpServletResponse response) throws Exception {
        Map<String, String> userStatus = CacheUtil.getParamNameMap("CLIENT");
        // 表格sheet名称
        String sheetName = "意见反馈列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        if(StringUtils.isNotBlank(form.getUserName())){
            UserResponse user = submissionsService.getUserIdByUserName(form.getUserName());
            if(user!=null&&user.getResult()!=null){
                Integer userId = user.getResult().getUserId();
                form.setUserId(userId);
            }
        }
        SubmissionsResponse submissionList = submissionsService.getExportSubmissionList(form);
        List<SubmissionsCustomizeVO> submissionsList = submissionList.getResultList();
        for (SubmissionsCustomizeVO submissionsCustomizeVO : submissionsList) {
            String type = userStatus.get(submissionsCustomizeVO.getSysType()) + "-" + submissionsCustomizeVO.getSysVersion();
            submissionsCustomizeVO.setSysType(type);
            Integer userId = submissionsCustomizeVO.getUserId();
            UserResponse users = submissionsService.getUserIdByUserId(userId);
            String userName = users.getResult() != null ? users.getResult().getUsername() : "";
            submissionsCustomizeVO.setUserName(userName);
        }
      //  List<SubmissionsCustomizeVO> submissionsList = this.submissionsService.queryRecordList(searchCon, -1, -1);

        String[] titles = new String[] { "序号", "用户名", "操作系统", "版本号", "手机型号", "反馈内容", "时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (submissionsList != null && submissionsList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < submissionsList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    SubmissionsCustomizeVO submissions = submissionsList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) { // 用户名
                        cell.setCellValue(submissions.getUserName());
                    } else if (celLength == 2) { // 系统
                        cell.setCellValue(submissions.getSysType());
                    } else if (celLength == 3) { // 平台版本号
                        cell.setCellValue(submissions.getPlatformVersion());
                    } else if (celLength == 4) {// 手机型号
                        cell.setCellValue(submissions.getPhoneType());
                    } else if (celLength == 5) {// 反馈内容
                        cell.setCellValue(submissions.getContent());
                    } else if (celLength == 6) {// 时间
                        cell.setCellValue(submissions.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }



}
