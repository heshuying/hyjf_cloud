/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.customertransfer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: CustomerTransferController, v0.1 2018/7/5 18:00
 */
@Api(value = "资金中心-转账管理-用户转账",tags = "资金中心-转账管理-用户转账")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/customertransfer")
public class CustomerTransferController extends BaseController {

    @Autowired
    private CustomerTransferService customerTransferService;
    @Autowired
    private AdminCommonService adminCommonService;


    /**
     * 用户转账-查询转账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "用户转账-查询转账列表",notes = "用户转账-查询转账列表")
    @PostMapping(value = "/transferlist")
    public AdminResult<ListResult<UserTransferVO>> transferList(@RequestBody CustomerTransferListRequest request){
        Integer count = customerTransferService.getTransferCount(request);
        count = (count == null)?0:count;
        List<UserTransferVO> userTransferVOList = customerTransferService.searchUserTransferList(request);
        return new AdminResult<>(ListResult.build(userTransferVOList,count));
    }

    /**
     * 转账状态下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "转账状态下拉框数据",notes = "转账状态下拉框数据")
    @PostMapping(value = "/gettransferstatus")
    public AdminResult<ListResult<DropDownVO>> getTransferStatus(){
        List<DropDownVO> dropDownVOList = adminCommonService.getParamNameList("TRANSFER_STATUS");
        return new AdminResult<>(ListResult.build(dropDownVOList,0));
    }

    /**
     * 交易类型下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "交易类型下拉框数据",notes = "交易类型下拉框数据")
    @PostMapping(value = "/gettransfertypes")
    public AdminResult<ListResult<DropDownVO>> getTransferTypes(){
        List<DropDownVO> dropDownVOList = adminCommonService.getParamNameList("TRANSFER_TYPE");
        return new AdminResult<>(ListResult.build(dropDownVOList,0));
    }

    /**
     * 用户转账-导出转账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "用户转账-导出转账列表",notes = "用户转账-导出转账列表")
    @PostMapping(value = "/transferlistexport")
    public void exportTransferList(HttpServletResponse response, @RequestBody CustomerTransferListRequest request) throws UnsupportedEncodingException {
        /*
         * 原有注释
         * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
         * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
         * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
         */
        // currPage<0 为全部,currPage>0 为具体某一页
        request.setCurrPage(-1);
        // 表格sheet名称
        String sheetName = "转账记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;

        //设置默认查询时间
        if(StringUtils.isEmpty(request.getTransferTimeStart())){
            request.setTransferTimeStart(GetDate.getDate("yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(request.getTransferTimeEnd())){
            request.setTransferTimeEnd(GetDate.getDate("yyyy-MM-dd"));
        }
        // 需要输出的结果列表
        List<UserTransferVO> userTransferVOList = customerTransferService.searchUserTransferList(request);
        String[] titles = new String[] { "序号", "订单号","交易类型", "转出账户", "转入账户", "转账金额","对账标识", "转账状态", "转账链接", "操作员", "操作时间", "转账时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (userTransferVOList != null && userTransferVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < userTransferVOList.size(); i++) {
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
                    UserTransferVO userTransferVO = userTransferVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 订单号
                        cell.setCellValue(userTransferVO.getOrderId());
                    } else if (celLength == 2) {// 交易类型
                        // 交易类型
                        List<ParamNameVO> transferTypes = customerTransferService.searchParamNameList("TRANSFER_TYPE");
                        for(int j=0;j<transferTypes.size();j++){
                            if(transferTypes.get(j).getNameCd().equals(String.valueOf(userTransferVO.getTransferType()))){
                                cell.setCellValue(transferTypes.get(j).getName());
                            }
                        }
                    } else if (celLength == 3) {// 转出账户
                        cell.setCellValue(userTransferVO.getOutUserName());
                    } else if (celLength == 4) {// 转入账户
                        cell.setCellValue("平台");
                    } else if (celLength == 5) {// 转账金额
                        cell.setCellValue(String.valueOf(userTransferVO.getTransferAmount()));
                    } else if (celLength == 6) {// 对账标识
                        if(userTransferVO.getReconciliationId() == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(userTransferVO.getReconciliationId());
                        }
                    } else if (celLength == 7) {// 转账状态
                        // 转账状态
                        List<ParamNameVO> transferStatus = customerTransferService.searchParamNameList("TRANSFER_STATUS");
                        for(int j=0;j<transferStatus.size();j++){
                            if(transferStatus.get(j).getNameCd().equals(String.valueOf(userTransferVO.getStatus()))){
                                cell.setCellValue(transferStatus.get(j).getName());
                            }
                        }
                    }else if (celLength == 8) {// 转账链接
                        cell.setCellValue(userTransferVO.getTransferUrl());
                    }else if (celLength == 9) {// 操作员
                        cell.setCellValue(userTransferVO.getCreateUserName());
                    } else if (celLength == 10) {// 操作时间
                        if(userTransferVO.getCreateTime() == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(GetDate.date2Str(userTransferVO.getCreateTime(), GetDate.datetimeFormat));
                        }
                    } else if (celLength == 11) {// 转账时间
                        if(userTransferVO.getTransferTime() == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(GetDate.date2Str(userTransferVO.getTransferTime(), GetDate.datetimeFormat));
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * 根据userName查询账户余额-发起转账
     * @auth sunpeikai
     * @param map 包含用户名
     * @return
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "outUserName", value = "转出用户名", required = true, dataType = "String"),
    })
    @ApiOperation(value = "查询余额",notes = "根据用户账号查询余额-发起转账")
    @PostMapping(value = "/searchbalance")
    public AdminResult<String> searchBalanceByUsername(@RequestBody Map map){
        String outUserName = map.get("outUserName").toString();
        logger.info("outUserName=[{}]",outUserName);
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotEmpty(outUserName)){
            jsonObject = customerTransferService.searchBalanceByUsername(outUserName);
        }else{
            CheckUtil.check(false,MsgEnum.ERR_OBJECT_REQUIRED,"用户账号");
        }
        return new AdminResult<>(jsonObject.getString("result"));
    }

    /**
     * 发起转账-提交
     * @auth sunpeikai
     * @param request 发起转账提交时所用的参数
     * @return
     */
    @ApiOperation(value = "发起转账-提交",notes = "发起转账-提交")
    @PostMapping(value = "/addtransfer")
    public AdminResult addTransfer(HttpServletRequest request, @RequestBody CustomerTransferRequest customerTransferRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        JSONObject result = new JSONObject();
        result = customerTransferService.checkCustomerTransferParam(customerTransferRequest);
        if(result != null && "0".equals(result.get("status"))){
            AdminSystemVO adminSystemVO = customerTransferService.getAdminSystemByUserId(userId);
            customerTransferRequest.setCreateUserId(Integer.valueOf(adminSystemVO.getId()));
            customerTransferRequest.setCreateUserName(adminSystemVO.getUsername());
            boolean success = customerTransferService.insertTransfer(customerTransferRequest);
            if(success){
                return new AdminResult(SUCCESS,SUCCESS_DESC);
            }
        }
        return new AdminResult(FAIL,FAIL_DESC);
    }

    /**
     * 用户转账-发送邮件
     * @auth sunpeikai
     * @param map 包含transferId 转账记录id
     * @return
     */
    @ApiImplicitParam(name = "transferId", value = "转账记录id", required = true, dataType = "String")
    @ApiOperation(value = "用户转账-发送邮件",notes = "发送邮件")
    @PostMapping(value = "/transfersendmail")
    public AdminResult transferSendMail(@RequestBody Map map){
        Integer transferId = (Integer) map.get("transferId");
        customerTransferService.transferSendMail(transferId);
        return new AdminResult(SUCCESS,SUCCESS_DESC);
    }


}
