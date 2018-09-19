/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.poundage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageController, v0.1 2018/9/3 11:47
 */
@Api(value = "资金中心-手续费分账",tags = "资金中心-手续费分账")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/poundage")
public class PoundageController extends BaseController {

    @Autowired
    private PoundageService poundageService;

    /**
     * 按条件查询手续费分账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "手续费分账列表",notes = "手续费分账列表")
    @PostMapping(value = "/poundagelist")
    public AdminResult<ListResult<PoundageCustomizeVO>> poundageList(@RequestBody PoundageListRequest request){
        Integer count = poundageService.getPoundageCount(request);
        count = (count == null)?0:count;
        PoundageCustomizeVO poundageSum = poundageService.getPoundageSum(request);
        List<PoundageCustomizeVO> poundageCustomizeVOList = poundageService.searchPoundageList(request);
        return new AdminResult<>(ListResult.build2(poundageCustomizeVOList,count,poundageSum));
    }

    /**
     * 根据id查询手续费分账
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据id查询手续费分账",notes = "根据id查询手续费分账")
    @PostMapping(value = "/poundagedetail")
    public AdminResult<PoundageCustomizeVO> poundageDetail(HttpServletRequest request, @RequestBody PoundageListRequest poundageListRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        Integer id = poundageListRequest.getIdSer();
        PoundageCustomizeVO poundageCustomizeVO = null;
        if(id != null){
            poundageCustomizeVO = poundageService.getPoundageById(userId,id);
        }
        return new AdminResult<>(poundageCustomizeVO);
    }

    /**
     * 审核
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "审核",notes = "审核")
    @PostMapping(value = "/audit")
    public AdminResult audit(HttpServletRequest request,@RequestBody PoundageCustomizeVO poundageCustomizeVO){
        Integer userId = Integer.valueOf(getUser(request).getId());
        poundageCustomizeVO.setStatus(poundageCustomizeVO.STATUS_AUDIT);
        poundageCustomizeVO.setUpdater(userId);
        poundageCustomizeVO.setUpdateTime(GetDate.getNowTime10());
        boolean isSuccess = poundageService.updatePoundage(poundageCustomizeVO);
        if(isSuccess){
            return new AdminResult(SUCCESS,SUCCESS_DESC);
        }else{
            return new AdminResult(FAIL,FAIL_DESC);
        }
    }

    /**
     * 佣金分账
     * 1.查询银行平台账户管理费账户余额
     * 2.调用江西银行接口分佣
     * 成功:更新手续费分账信息--更新分账时间和分账状态（分账成功）;
     * 更新转入用户账户信息--更新转入用户江西银行总资产和江西银行可用余额;
     * 插入交易明细（资金中心-资金明细）--交易类型:分账,收支类型：收入;
     * 插入手续费账户明细（资金中心-银行平台用户-手续费账户明细）--交易类型:分账,收支类型:支出.
     * 失败:更新手续费分账信息--更新分账状态（分账失败）;
     * 插入手续费分账异常信息（异常中心-手续费异常处理）--可根据订单号和手续费账号查询状态.
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "佣金分账",notes = "佣金分账")
    @PostMapping(value = "/poundagetransfer")
    public AdminResult poundageTransfer(HttpServletRequest request,@RequestBody PoundageCustomizeVO poundageCustomizeVO){
        // 检查参数正确性
        poundageService.checkParams(poundageCustomizeVO);
        Integer userId = Integer.valueOf(getUser(request).getId());
        poundageCustomizeVO.setUpdater(userId);
        JSONObject jsonObject = poundageService.poundageTransfer(poundageCustomizeVO);
        return new AdminResult();
    }


    /**
     * 手续费分账列表导出
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "手续费分账列表导出",notes = "手续费分账列表导出")
    @PostMapping(value = "/exportpoundagelist")
    public void exportPoundageList(HttpServletResponse response, @RequestBody PoundageListRequest request) throws UnsupportedEncodingException {
        request.setCurrPage(-1);
        // 表格sheet名称
        String sheetName = "手续费分账";
        // 取得数据
        request.setLimitStart(-1);
        request.setLimitEnd(-1);
        //PoundageCustomize poundageCustomize = new PoundageCustomize();
        //BeanUtils.copyProperties(form, poundageCustomize);
        List<PoundageCustomizeVO> recordList = this.poundageService.searchPoundageList(request);
        String fileName = URLEncoder.encode(sheetName,"UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "收款方用户名", "收款方姓名", "总分账金额", "总分账笔数", "分账时间段",
                "分账状态", "分账时间", "分账订单号", "分账流水号"};
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
                    PoundageCustomizeVO bean = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 收款方用户名
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getUserName());
                    }
                    // 姓名
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getRealName());
                    }
                    // 总分账金额
                    else if (celLength == 3) {
                        cell.setCellValue(bean.getAmount().toString());
                    }
                    // 总分账笔数
                    else if (celLength == 4) {
                        cell.setCellValue(bean.getQuantity());
                    }
                    // 分账时间段
                    else if (celLength == 5) {
                        Integer poundageTime = Integer.parseInt(bean.getPoundageTime());
                        cell.setCellValue(poundageTime == null ? "" : GetDate.timestamptoStrYYYYMMDD(poundageTime));
                    }
                    // 分账状态
                    else if (celLength == 6) {
                        cell.setCellValue(PoundageCustomizeVO.getStatusStr(bean.getStatus()));
                    }
                    // 分账时间
                    else if (celLength == 7) {
                        Integer addTime = bean.getAddTime();
                        cell.setCellValue(addTime == null ? "" : GetDate.timestamptoStrYYYYMMDDHHMMSS(addTime));
                    }
                    // 分账订单号
                    else if (celLength == 8) {
                        cell.setCellValue(bean.getNid());
                    }
                    // 分账流水号
                    else if (celLength == 9) {
                        cell.setCellValue(bean.getSeqNo());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
