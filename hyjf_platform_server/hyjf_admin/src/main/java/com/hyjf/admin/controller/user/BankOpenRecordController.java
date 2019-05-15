/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AccountRecordRequestBean;
import com.hyjf.admin.beans.request.BankCancellationAccountRequestBean;
import com.hyjf.admin.beans.response.UserManagerInitResponseBean;
import com.hyjf.admin.beans.vo.BankOpenAccountRecordCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankOpenRecordService;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankAccountRecordResponse;
import com.hyjf.am.response.user.BankCancellationAccountResponse;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.resquest.user.BankCancellationAccountRequest;
import com.hyjf.am.vo.user.BankCancellationAccountVO;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version RegistRecordController, v0.1 2018/6/23 15:16
 */

@Api(value = "会员中心-开户记录",tags = "会员中心-开户记录")
@RestController
@RequestMapping("/hyjf-admin/bankOpenRecord")
public class BankOpenRecordController extends BaseController {
    @Autowired
    private BankOpenRecordService bankOpenRecordService;
    @Autowired
    private UserCenterService userCenterService;
    public static final String PERMISSIONS = "accountlist";

    @ApiOperation(value = "开户记录页面初始化", notes = "开户记录页面初始化")
    @PostMapping(value = "/bankOpenRecordInit")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<UserManagerInitResponseBean>  userManagerInit() {
        UserManagerInitResponseBean userManagerInitResponseBean =bankOpenRecordService.initUserManaget();
        return new AdminResult<UserManagerInitResponseBean>(userManagerInitResponseBean);
    }

    //会员管理列表查询
    @ApiOperation(value = "汇付银行开户记录查询", notes = "汇付银行开户记录查询")
    @PostMapping(value = "/bankOpenRecordAccount")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<BankOpenAccountRecordCustomizeVO>> bankOpenRecordAccount(HttpServletRequest request,@RequestBody AccountRecordRequestBean accountRecordRequestBean){
        AccountRecordRequest accountRecordRequest =  new AccountRecordRequest();
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        BeanUtils.copyProperties(accountRecordRequestBean,accountRecordRequest);
        BankAccountRecordResponse bankOpenRecordServiceAccountRecordList =bankOpenRecordService.findAccountRecordList(accountRecordRequest);
        if(bankOpenRecordServiceAccountRecordList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(bankOpenRecordServiceAccountRecordList)) {
            return new AdminResult<>(FAIL, bankOpenRecordServiceAccountRecordList.getMessage());
        }
        List<BankOpenAccountRecordCustomizeVO> bankOpenAccountRecordCustomizeVOList = new ArrayList<BankOpenAccountRecordCustomizeVO>();
        List<BankOpenAccountRecordVO> bankOpenAccountRecordVOList = bankOpenRecordServiceAccountRecordList.getResultList();
        if(null!=bankOpenAccountRecordVOList&&bankOpenAccountRecordVOList.size()>0){
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for (BankOpenAccountRecordVO registRecordVO:bankOpenAccountRecordVOList){
                    registRecordVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(registRecordVO.getMobile()));
                    registRecordVO.setIdCard(AsteriskProcessUtil.getAsteriskedValue(registRecordVO.getIdCard()));
                }
            }
            bankOpenAccountRecordCustomizeVOList = CommonUtils.convertBeanList(bankOpenAccountRecordVOList,BankOpenAccountRecordCustomizeVO.class);
        }
        return new AdminResult<ListResult<BankOpenAccountRecordCustomizeVO>>(ListResult.build(bankOpenAccountRecordCustomizeVOList, bankOpenRecordServiceAccountRecordList.getCount())) ;
    }
    @ApiOperation(value = "江西银行开户记录查询", notes = "江西银行开户记录查询")
    @PostMapping(value = "/bankOpenRecordBankAccount")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<BankOpenAccountRecordCustomizeVO>> bankOpenRecordBankAccount(HttpServletRequest request,@RequestBody AccountRecordRequestBean accountRecordRequestBean){
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        BankAccountRecordRequest registerRcordeRequest = new BankAccountRecordRequest();
        BeanUtils.copyProperties(accountRecordRequestBean,registerRcordeRequest);
        BankAccountRecordResponse bankOpenRecordServiceAccountRecordList=bankOpenRecordService.findBankAccountRecordList(registerRcordeRequest);
        if(bankOpenRecordServiceAccountRecordList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(bankOpenRecordServiceAccountRecordList)) {
            return new AdminResult<>(FAIL, bankOpenRecordServiceAccountRecordList.getMessage());
        }
        List<BankOpenAccountRecordCustomizeVO> bankOpenAccountRecordCustomizeVOList = new ArrayList<BankOpenAccountRecordCustomizeVO>();
        List<BankOpenAccountRecordVO> bankOpenAccountRecordVOList = bankOpenRecordServiceAccountRecordList.getResultList();
        if(null!=bankOpenAccountRecordVOList&&bankOpenAccountRecordVOList.size()>0){
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for (BankOpenAccountRecordVO registRecordVO:bankOpenAccountRecordVOList){
                    registRecordVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(registRecordVO.getMobile()));
                    registRecordVO.setIdCard(AsteriskProcessUtil.getAsteriskedValue(registRecordVO.getIdCard()));
                }
            }
            bankOpenAccountRecordCustomizeVOList = CommonUtils.convertBeanList(bankOpenAccountRecordVOList,BankOpenAccountRecordCustomizeVO.class);
        }
        return new AdminResult<ListResult<BankOpenAccountRecordCustomizeVO>>(ListResult.build(bankOpenAccountRecordCustomizeVOList, bankOpenRecordServiceAccountRecordList.getCount())) ;
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
    //@ApiOperation(value = "汇付银行开户记录导出", notes = "汇付银行开户记录导出")
    //@PostMapping(value = "/exportaccount")
    /*public void exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody AccountRecordRequestBean accountRecordRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "开户记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        AccountRecordRequest accountRecordRequest = new AccountRecordRequest();
        BeanUtils.copyProperties(accountRecordRequestBean,accountRecordRequest);
        accountRecordRequest.setLimitFlg(true);
        // 需要输出的结果列表
        List<BankOpenAccountRecordVO> bankOpenRecordServiceAccountRecordList = new ArrayList<BankOpenAccountRecordVO>();
        BankAccountRecordResponse bankAccountRecordResponse =bankOpenRecordService.findAccountRecordList(accountRecordRequest);
        if(null!=bankAccountRecordResponse){
            bankOpenRecordServiceAccountRecordList = bankAccountRecordResponse.getResultList();
        }

        String[] titles = new String[] { "序号", "用户名", "姓名", "性别", "年龄", "生日", "户籍所在地", "身份证号码", "开户状态", "汇付账号", "开户平台", "开户时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (bankOpenRecordServiceAccountRecordList != null && bankOpenRecordServiceAccountRecordList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < bankOpenRecordServiceAccountRecordList.size(); i++) {
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
                    BankOpenAccountRecordVO user = bankOpenRecordServiceAccountRecordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 真实姓名
                        cell.setCellValue(user.getRealName());
                    } else if (celLength == 3) {// 性别
                        if ("1".equals(user.getSex())) {
                            cell.setCellValue("男");
                        } else if ("2".equals(user.getSex())) {
                            cell.setCellValue("女");
                        } else {
                            cell.setCellValue("未知");
                        }
                    } else if (celLength == 4) {// 年龄
                        cell.setCellValue(this.getAge(user.getBirthday()));
                    } else if (celLength == 5) {// 生日
                        cell.setCellValue(user.getBirthday());
                    } else if (celLength == 6) {// 户籍所在地
                        String area = userCenterService.getAreaByIdCard(user.getIdCard());
                        cell.setCellValue(area);
                    } else if (celLength == 7) {// 身份证号码
                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getIdCard(), 3,7));
                    }else if (celLength == 8) {// 开户状态
                        cell.setCellValue(user.getAccountStatusName());
                    } else if (celLength == 9) {// 汇付账号
                        cell.setCellValue(user.getAccount());
                    } else if (celLength == 10) {// 开户平台
                        cell.setCellValue(user.getOpenAccountPlat());
                    } else if (celLength == 11) {// 开户时间
                        cell.setCellValue(user.getOpenTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出excel
     *
     * @param accountRecordRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "汇付银行开户记录导出", notes = "汇付银行开户记录导出")
    @PostMapping(value = "/exportaccount")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportExcelAccount(HttpServletRequest request, HttpServletResponse response,@RequestBody AccountRecordRequestBean accountRecordRequestBean) throws Exception {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        // 封装查询条件
        AccountRecordRequest accountRecordRequest = new AccountRecordRequest();
        BeanUtils.copyProperties(accountRecordRequestBean,accountRecordRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "开户记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        accountRecordRequest.setLimitFlg(false);
        //请求第一页5000条
        accountRecordRequest.setPageSize(defaultRowMaxCount);
        accountRecordRequest.setCurrPage(1);
        // 需要输出的结果列表
        BankAccountRecordResponse bankAccountRecordResponse =bankOpenRecordService.findAccountRecordList(accountRecordRequest);
        Integer totalCount = bankAccountRecordResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMapAcc();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterAcc();
        if(!isShow){
            //如果没有查看脱敏权限,显示加星
            mapValueAdapter = buildValueAdapterAccAdaptertAsterisked();
        }
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bankAccountRecordResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            accountRecordRequest.setPageSize(defaultRowMaxCount);
            accountRecordRequest.setCurrPage(i+1);
            BankAccountRecordResponse bankAccountRecordResponse2 =bankOpenRecordService.findAccountRecordList(accountRecordRequest);
            if (bankAccountRecordResponse2 != null && bankAccountRecordResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankAccountRecordResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMapAcc() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("realName", "姓名");
        map.put("sex", "性别");
        map.put("age", "年龄");
        map.put("birthday", "生日");
        map.put("area", "户籍所在地");
        map.put("idCard", "身份证号码");
        map.put("accountStatusName", "开户状态");
        map.put("account", "汇付账号");
        map.put("openAccountPlat", "开户平台");
        map.put("openTime", "开户时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapterAcc() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter ageAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String age = (String) object;
                return getAge(age);
            }
        };

        IValueFormatter sexAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String sex = (String) object;
                if ("1".equals(sex)) {
                    sex = ("男");
                } else if ("2".equals(sex)) {
                    sex = ("女");
                } else {
                    sex = ("未知");
                }
                return sex;
            }
        };

        IValueFormatter areaAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String area = (String) object;
                return userCenterService.getAreaByIdCard(area);
            }
        };
        mapAdapter.put("sex", sexAdapter);
        mapAdapter.put("age", ageAdapter);
        mapAdapter.put("area", areaAdapter);
        //mapAdapter.put("idCard", idCardAdapter);
        return mapAdapter;
    }
    private Map<String, IValueFormatter> buildValueAdapterAccAdaptertAsterisked() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter ageAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String age = (String) object;
                return getAge(age);
            }
        };

        IValueFormatter sexAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String sex = (String) object;
                if ("1".equals(sex)) {
                    sex = ("男");
                } else if ("2".equals(sex)) {
                    sex = ("女");
                } else {
                    sex = ("未知");
                }
                return sex;
            }
        };

        IValueFormatter areaAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String area = (String) object;
                return userCenterService.getAreaByIdCard(area);
            }
        };

        IValueFormatter idCardAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String idCard = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(idCard);
            }
        };

        mapAdapter.put("sex", sexAdapter);
        mapAdapter.put("age", ageAdapter);
        mapAdapter.put("area", areaAdapter);
        mapAdapter.put("idCard", idCardAdapter);
        return mapAdapter;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param bankAccountRecordRequestBeans
     * @param response
     * @throws Exception
     */
    //@ApiOperation(value = "江西银行开户记录导出", notes = "江西银行开户记录导出")
    //@PostMapping(value = "/exportbankaccount")
    /*public void exportBankExcel( HttpServletResponse response, @RequestBody AccountRecordRequestBean bankAccountRecordRequestBeans) throws Exception {
        BankAccountRecordRequest registerRcordeRequest = new BankAccountRecordRequest();
        BeanUtils.copyProperties(bankAccountRecordRequestBeans,registerRcordeRequest);
        registerRcordeRequest.setLimitFlg(true);
        // 表格sheet名称
        String sheetName = "江西银行开户记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        List<BankOpenAccountRecordVO> bankOpenRecordServiceAccountRecordList=new ArrayList<BankOpenAccountRecordVO>();
        BankAccountRecordResponse bankAccountRecordResponse=bankOpenRecordService.findBankAccountRecordList(registerRcordeRequest);
        if(null!=bankAccountRecordResponse){
            bankOpenRecordServiceAccountRecordList = bankAccountRecordResponse.getResultList();
        }
        String[] titles = new String[] { "序号", "用户名","当前手机号", "姓名", "身份证号码","银行卡号", "银行电子账户", "开户平台", "开户时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (bankOpenRecordServiceAccountRecordList != null && bankOpenRecordServiceAccountRecordList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < bankOpenRecordServiceAccountRecordList.size(); i++) {
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
                    BankOpenAccountRecordVO user = bankOpenRecordServiceAccountRecordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    //序号、用户名、当前手机号、姓名、身份证号、银行卡号、银行电子账户、开户平台、开户时间
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 用户名
                        cell.setCellValue(user.getMobile());
                    } else if (celLength == 3) {// 真实姓名
                        cell.setCellValue(user.getRealName());
                    } else if (celLength == 4) {// 身份证号码
                        cell.setCellValue(user.getIdCard());
                    } else if (celLength == 5) {// 开户状态
                        cell.setCellValue(user.getAccount());
                    } else if (celLength == 6) {// 汇付账号
                        cell.setCellValue(user.getCustomerAccount());
                    } else if (celLength == 7) {// 开户平台
                        cell.setCellValue(user.getOpenAccountPlat());
                    } else if (celLength == 8) {// 开户时间
                        cell.setCellValue(user.getOpenTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出excel
     *
     * @param bankAccountRecordRequestBeans
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "江西银行开户记录导出", notes = "江西银行开户记录导出")
    @PostMapping(value = "/exportbankaccount")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportBankAccountExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountRecordRequestBean bankAccountRecordRequestBeans) throws Exception {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        // 封装查询条件
        BankAccountRecordRequest registerRcordeRequest = new BankAccountRecordRequest();
        BeanUtils.copyProperties(bankAccountRecordRequestBeans,registerRcordeRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "江西银行开户记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        registerRcordeRequest.setLimitFlg(true);
        //请求第一页5000条
        registerRcordeRequest.setPageSize(defaultRowMaxCount);
        registerRcordeRequest.setCurrPage(1);
        // 需要输出的结果列表
        BankAccountRecordResponse bankAccountRecordResponse=bankOpenRecordService.findBankAccountRecordList(registerRcordeRequest);
        Integer totalCount = bankAccountRecordResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMapBkAcc();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterBkAcc();
        if(!isShow){
            //如果没有查看脱敏权限,显示加星
            mapValueAdapter = buildValueAdapterBkAccAdaptertAsterisked();
        }
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bankAccountRecordResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            registerRcordeRequest.setPageSize(defaultRowMaxCount);
            registerRcordeRequest.setCurrPage(i+1);
            BankAccountRecordResponse bankAccountRecordResponse2=bankOpenRecordService.findBankAccountRecordList(registerRcordeRequest);
            if (bankAccountRecordResponse2 != null && bankAccountRecordResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankAccountRecordResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMapBkAcc() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("mobile", "当前手机号");
        map.put("realName", "姓名");
        map.put("idCard", "身份证号码");
        map.put("account", "银行卡号");
        map.put("customerAccount", "银行电子账户");
        map.put("openAccountPlat", "开户平台");
        map.put("openTime", "开户时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapterBkAcc() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
    private Map<String, IValueFormatter> buildValueAdapterBkAccAdaptertAsterisked() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();

        IValueFormatter idCardAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String idCard = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(idCard);
            }
        };
        IValueFormatter mobileAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String mobile = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(mobile);
            }
        };
        mapAdapter.put("mobile", mobileAdapter);
        mapAdapter.put("idCard", idCardAdapter);
        return mapAdapter;
    }

    /**
     *
     * @Description:获取年龄
     * @param birthday
     * @return String
     * @exception:
     * @author: xulijie
     * @time:2017年5月3日 下午4:00:48
     */
    public String getAge(String birthday) {
        if (org.apache.commons.lang.StringUtils.isBlank(birthday)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatDate = sdf.format(date);
        int age = Integer.parseInt(formatDate) - Integer.parseInt(birthday.substring(0, 4));
        return String.valueOf(age);
    }

    /**
     * 销户记录查询
     * @param requestBean
     * @param request
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "销户记录列表 ", notes = "销户记录列表查询")
    @PostMapping(value = "/bankCancellationAccountList")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult<ListResult<BankCancellationAccountVO>> getBankCancellationAccountList(@RequestBody BankCancellationAccountRequestBean requestBean, HttpServletRequest request) {
        BankCancellationAccountRequest  bankCancellationAccountRequest = new  BankCancellationAccountRequest();
        BeanUtils.copyProperties(requestBean,bankCancellationAccountRequest);
        BankCancellationAccountResponse response = this.userCenterService.getBankCancellationAccountList(bankCancellationAccountRequest);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<BankCancellationAccountVO>>(ListResult.build(response.getResultList(),response.getCount())) ;
    }

}
