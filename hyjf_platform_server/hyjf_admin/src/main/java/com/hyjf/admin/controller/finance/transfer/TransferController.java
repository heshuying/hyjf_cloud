/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.transfer;

import com.hyjf.admin.beans.response.TransferResponse;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.admin.service.TransferService;
import com.hyjf.am.response.admin.UserTransferResponse;
import com.hyjf.am.resquest.admin.TransferListRequest;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version TransferController, v0.1 2018/7/6 17:51
 */

@Api(value = "资金中心-平台账户-子账户间转账",tags = "资金中心-平台账户-子账户间转账")
@RestController
@RequestMapping("/hyjf-admin/finance/transfer")
public class TransferController extends BaseController {

    @Autowired
    TransferService transferService;
    @Autowired
    CustomerTransferService customerTransferService;

    /**
     * 用户转账列表
     *
     * @param
     * @param form
     * @return
     */
    @ApiOperation(value = "用户转账列表", notes = "用户转账列表")
    @PostMapping(value = "/transferList")
    public AdminResult init(@RequestBody TransferListRequest form) {
        TransferResponse response = new TransferResponse();
        Map<String, String> transferStatus = CacheUtil.getParamNameMap("TRANSFER_STATUS");
        Map<String, String> transferTypes = CacheUtil.getParamNameMap("TRANSFER_TYPE");
        // 转账状态
        response.setTransferStatus(transferStatus);
        // 交易类型
        response.setTransferTypes(transferTypes);
        UserTransferResponse userTransferResponse = this.transferService.getRecordList(form);
        if (response == null || userTransferResponse.getRecordTotal() == 0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserTransferVO> list = userTransferResponse.getResultList();
        response.setTotal(userTransferResponse.getRecordTotal());
        response.setUserTransferVOList(list);
        return new AdminResult(response);
    }

    /**
     * 校验用户转账的结果
     *
     * @param param
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "校验用户转账的结果", notes = "校验用户转账的结果")
    @ApiImplicitParam(name = "param", value = "{outUserName:String}", dataType = "Map")
    @PostMapping(value = "/checkTransfer")
    public AdminResult checkTransfer(@RequestBody Map<String, String> param) {
        String outUserName = param.get("outUserName");
        CheckUtil.check(StringUtils.isNotBlank(outUserName), MsgEnum.ERR_OBJECT_REQUIRED, "用户账户");
        this.transferService.checkTransfer(outUserName);
        return new AdminResult();
    }


    /**
     * 获取用户的余额
     *
     * @param param
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "获取用户的余额", notes = "获取用户的余额")
    @ApiImplicitParam(name = "param", value = "{outUserName:String}", dataType = "Map")
    @PostMapping(value = "/searchBalance")
    public AdminResult searchUserBalance(@RequestBody Map<String, String> param) {
        String outUserName = param.get("outUserName");
        CheckUtil.check(StringUtils.isNotBlank(outUserName), MsgEnum.ERR_OBJECT_REQUIRED, "用户账户");
        String result = this.transferService.searchBalance(outUserName);
        return new AdminResult(result);
    }

    /**
     * 初始化用户转账画面
     *
     * @param
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "进入用户详情页面")
    @PostMapping(value = "/addTransfer")
    public AdminResult addTransfer(HttpServletRequest request, @RequestBody TransferCustomizeBean form) {
        Map<String, Object> map = new HashMap<>();
        map.put("transferForm", form);
        transferService.checkTransferParam(form);
        AdminSystemVO adminSystem = getUser(request);
        form.setCreateUserId(Integer.parseInt(adminSystem.getId()));
        form.setCreateUserName(adminSystem.getUsername());
        boolean flag = this.transferService.insertTransfer(form);
        if (flag) {
            map.clear();
            map.put(CommonConstant.SUCCESS, CommonConstant.SUCCESS);
        } else {
            map.put("transferForm", form);
        }
        return new AdminResult(map);
    }

    /**
     * 发送用户转账邮件
     * @param map
     * @return
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "transferId", value = "转账记录id", required = true, dataType = "String"),
    })
    @ApiOperation(value = "用户转账-发送邮件",notes = "发送邮件")
    @PostMapping(value = "/transfersendmail")
    public AdminResult transferSendMail(@RequestBody Map map){
        Integer id = (Integer) map.get("id");
        customerTransferService.transferSendMail(id);
        return new AdminResult();
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
    @ApiOperation(value = "导出")
    @PostMapping(value = "/exportTransfer")
    public void exportExcel(@RequestBody TransferListRequest form, HttpServletRequest request,
                            HttpServletResponse response) throws UnsupportedEncodingException {

        // 表格sheet名称
        String sheetName = "转账记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;

        //设置默认查询时间
        if(StringUtils.isEmpty(form.getTransferTimeStart())){
            form.setTransferTimeStart(GetDate.getDate("yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(form.getTransferTimeEnd())){
            form.setTransferTimeEnd(GetDate.getDate("yyyy-MM-dd"));
        }
        // 需要输出的结果列表
        UserTransferResponse userTransferResponse = this.transferService.getRecordList(form);
        List<UserTransferVO> recordList = userTransferResponse.getResultList();
        String[] titles = new String[] { "序号", "订单号","交易类型", "转出账户", "转入账户", "转账金额","对账标识", "转账状态", "转账链接", "操作员", "操作时间", "转账时间" };
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
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    UserTransferVO transfer = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        // 订单号
                        cell.setCellValue(transfer.getOrderId());
                    } else if (celLength == 2) {
                        // 交易类型
                        List<ParamNameVO> transferTypes = customerTransferService.searchParamNameList("TRANSFER_TYPE");
                        for(int j=0;j<transferTypes.size();j++){
                            if(transferTypes.get(j).getNameCd().equals(String.valueOf(transfer.getTransferType()))){
                                cell.setCellValue(transferTypes.get(j).getName());
                            }
                        }
                    } else if (celLength == 3) {
                        // 转出账户
                        cell.setCellValue(transfer.getOutUserName());
                    } else if (celLength == 4) {
                        // 转入账户
                        cell.setCellValue("平台");
                    } else if (celLength == 5) {
                        // 转账金额
                        cell.setCellValue(String.valueOf(transfer.getTransferAmount()));
                    } else if (celLength == 6) {
                        // 对账标识
                        if(transfer.getReconciliationId() == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(transfer.getReconciliationId());
                        }
                    } else if (celLength == 7) {
                        // 转账状态
                        List<ParamNameVO> transferStatus = customerTransferService.searchParamNameList("TRANSFER_STATUS");
                        for(int j=0;j<transferStatus.size();j++){
                            if(transferStatus.get(j).getNameCd().equals(String.valueOf(transfer.getStatus()))){
                                cell.setCellValue(transferStatus.get(j).getName());
                            }
                        }
                    }else if (celLength == 8) {
                        // 转账链接
                        cell.setCellValue(transfer.getTransferUrl());
                    }else if (celLength == 9) {
                        // 操作员
                        cell.setCellValue(transfer.getCreateUserName());
                    } else if (celLength == 10) {
                        // 操作时间
                        if(transfer.getCreateTime() == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(GetDate.date2Str(transfer.getCreateTime(), GetDate.datetimeFormat));
                        }
                    } else if (celLength == 11) {
                        // 转账时间
                        if(transfer.getTransferTime() == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(GetDate.date2Str(transfer.getTransferTime(), GetDate.datetimeFormat));
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
