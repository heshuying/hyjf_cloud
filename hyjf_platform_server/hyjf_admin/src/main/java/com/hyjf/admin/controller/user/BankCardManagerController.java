/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.BankCardLogRequestBean;
import com.hyjf.admin.beans.request.BankCardManagerRequestBean;
import com.hyjf.admin.beans.vo.BankCardLogCustomizedVO;
import com.hyjf.admin.beans.vo.BankcardInitCustomizeVO;
import com.hyjf.admin.beans.vo.BankcardManagerCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankCardManagerService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankCardLogResponse;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.BankCardLogVO;
import com.hyjf.am.vo.user.BankcardManagerVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
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

@Api(value = "会员中心-銀行卡管理",tags = "会员中心-銀行卡管理")
@RestController
@RequestMapping("/hyjf-admin/bankcardManager")
public class BankCardManagerController extends BaseController {
    @Autowired
    private BankCardManagerService bankCardManagerService;
    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "銀行卡管理页面初始化", notes = "銀行卡管理页面初始化")
    @PostMapping(value = "/bankCardInit")
    @ResponseBody
    public AdminResult<BankcardInitCustomizeVO> userManagerInit() {
        // 银行卡属性
        Map<String, String> bankcardProperty = CacheUtil.getParamNameMap("BANKCARD_PROPERTY");
        List<DropDownVO> bankcardPropertyList = ConvertUtils.convertParamMapToDropDown(bankcardProperty);
        // 是否默认
        Map<String, String> bankcardType = CacheUtil.getParamNameMap("BANKCARD_TYPE");
        List<DropDownVO> bankcardTypeList = ConvertUtils.convertParamMapToDropDown(bankcardType);

        List<BankConfigVO> listBanksConfigVO = bankCardManagerService.selectBankConfigList();
        List<DropDownVO> listBanksConfigDrop = ConvertUtils.convertListToDropDown(listBanksConfigVO,"code","name");

        BankcardInitCustomizeVO bankcardInitCustomizeVO = new BankcardInitCustomizeVO();
        bankcardInitCustomizeVO.setBankcardType(bankcardTypeList);
        bankcardInitCustomizeVO.setBankcardProperty(bankcardPropertyList);
        bankcardInitCustomizeVO.setListBanksConfigVO(listBanksConfigDrop);

        return new AdminResult<BankcardInitCustomizeVO>(bankcardInitCustomizeVO);


    }
    //汇付银行开户銀行卡記錄查询
    @ApiOperation(value = "汇付银行开户銀行卡記錄查询", notes = "汇付银行开户銀行卡記錄查询")
    @PostMapping(value = "/bankOpenRecordAccount")
    @ResponseBody
    public AdminResult<ListResult<BankcardManagerCustomizeVO>> bankOpenRecordAccount(HttpServletRequest request, @RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) {
        BankCardManagerRequest bankCardManagerRequest = new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean, bankCardManagerRequest);
        BankCardManagerResponse bankCardManagerResponse = bankCardManagerService.selectBankCardList(bankCardManagerRequest);
        if (bankCardManagerResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(bankCardManagerResponse)) {
            return new AdminResult<>(FAIL, bankCardManagerResponse.getMessage());
        }
        List<BankcardManagerVO> bankcardManagerVOList = bankCardManagerResponse.getResultList();
        List<BankcardManagerCustomizeVO> bankcardManagerCustomizeVOList = new ArrayList<BankcardManagerCustomizeVO>();
        if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
            bankcardManagerCustomizeVOList = CommonUtils.convertBeanList(bankcardManagerVOList, BankcardManagerCustomizeVO.class);
        }
        return new AdminResult<ListResult<BankcardManagerCustomizeVO>>(ListResult.build(bankcardManagerCustomizeVOList, bankCardManagerResponse.getCount()));
    }

    @ApiOperation(value = "江西银行开户銀行卡記錄查询", notes = "江西银行开户銀行卡記錄查询")
    @PostMapping(value = "/bankOpenRecordBankAccount")
    @ResponseBody
    public AdminResult<ListResult<BankcardManagerCustomizeVO>> bankOpenRecordBankAccount(@RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) {
        JSONObject jsonObject = new JSONObject();
        BankCardManagerRequest bankCardManagerRequest = new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean, bankCardManagerRequest);
        BankCardManagerResponse bankCardManagerResponse = bankCardManagerService.selectNewBankCardList(bankCardManagerRequest);
        if (bankCardManagerResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(bankCardManagerResponse)) {
            return new AdminResult<>(FAIL, bankCardManagerResponse.getMessage());
        }
        List<BankcardManagerVO> bankcardManagerVOList = bankCardManagerResponse.getResultList();
        List<BankcardManagerCustomizeVO> bankcardManagerCustomizeVOList = new ArrayList<BankcardManagerCustomizeVO>();
        if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
            bankcardManagerCustomizeVOList = CommonUtils.convertBeanList(bankcardManagerVOList, BankcardManagerCustomizeVO.class);
        }
        return new AdminResult<ListResult<BankcardManagerCustomizeVO>>(ListResult.build(bankcardManagerCustomizeVOList, bankCardManagerResponse.getCount()));
    }



    /**
     * 新版导出： 缺点， 需要增加一条count计数， 列表查询要传分页信息， 工作量略微增加
     *
     * @param response
     * @throws Exception
     */
   /*  @ApiOperation(value = "汇付银行开户銀行卡記錄导出", notes = "汇付银行开户銀行卡記錄导出")
    @PostMapping(value = "/exportbankcard")
   public void exportExcel(HttpServletResponse response,@RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) {
        // 封装查询条件
        final BankCardManagerRequest requestBank = new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean,requestBank);
        requestBank.setLimitFlg(true);
        // 表格sheet名称
        String sheetName = "银行卡管理";

        new AbstractExcelExportHandler<BankcardManagerVO>() {

            @Override
            public List<ExcelField> buildExcelFields() {
                List<ExcelField> excelFields = new ArrayList<>();
                excelFields.add(new ExcelField("序号", "userId"));
                excelFields.add(new ExcelField("用户名", "userName"));
                excelFields.add(new ExcelField("银行账号", "account"));
                excelFields.add(new ExcelField("所属银行", "bank"));
                excelFields.add(new ExcelField("是否默认", "cardType"));
                excelFields.add(new ExcelField("银行卡属性", "cardProperty"));
                excelFields.add(new ExcelField("添加时间", "addTime"));

                return excelFields;
            }

            @Override
            public int countExportData() {
                BankCardManagerResponse bankCardManagerResponse = bankCardManagerService.selectBankCardList(requestBank);
                if (null != bankCardManagerResponse) {
                    return bankCardManagerResponse.getCount();
                }
                return 0;
            }

            @Override
            public List<BankcardManagerVO> selectExportDataList(int limitStart, int limit) {
                BankCardManagerResponse bankCardManagerResponse = bankCardManagerService.selectBankCardList(requestBank);
                if (null != bankCardManagerResponse) {
                    return bankCardManagerResponse.getResultList();
                }
                return null;
//                return bankCardManagerService.selectBankCardList(requestBank,limitStart,limit);
            }
        }.exportExcel(response, sheetName);

    }*/

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    //@ApiOperation(value = "汇付银行开户銀行卡記錄导出", notes = "銀行卡管理")
    //@PostMapping(value = "/exportbankcard")
    /*public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) throws Exception {
        // 封装查询条件
        BankCardManagerRequest requestBank =new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean, requestBank);
        // 表格sheet名称
        String sheetName = "银行卡管理";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        BankCardManagerResponse bankCardManagerResponse  =bankCardManagerService.selectBankCardList(requestBank);
        List<BankcardManagerVO> bankcardManagerVOList = new ArrayList<BankcardManagerVO>();
        String[] titles = new String[] { "序号", "用户名", "银行账号", "所属银行", "是否默认", "银行卡属性", "添加时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if(null!=bankCardManagerResponse){
            bankcardManagerVOList = bankCardManagerResponse.getResultList();
        }
        if (bankcardManagerVOList != null && bankcardManagerVOList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < bankcardManagerVOList.size(); i++) {
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
                    BankcardManagerVO user = bankcardManagerVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 银行账号
                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getAccount(),3,7));
                    } else if (celLength == 3) {// 所属银行
                        cell.setCellValue(user.getBank());
                    } else if (celLength == 4) {// 是否默认
                        cell.setCellValue(user.getCardType());
                    } else if (celLength == 5) {// 银行卡属性
                        cell.setCellValue(user.getCardProperty());
                    } else if (celLength == 6) {// 添加时间
                        cell.setCellValue(user.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("==================汇付银行开户銀行卡記錄导出==================");
    }*/

    /**
     * 导出excel
     *
     * @param bankCardManagerRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "汇付银行开户銀行卡記錄导出", notes = "銀行卡管理")
    @PostMapping(value = "/exportbankcard")
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) throws Exception {
        // 封装查询条件
        BankCardManagerRequest requestBank =new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean, requestBank);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "银行卡管理";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        requestBank.setLimitFlg(false);
        //请求第一页5000条
        requestBank.setPageSize(defaultRowMaxCount);
        requestBank.setCurrPage(1);
        // 需要输出的结果列表
        BankCardManagerResponse bankCardManager  = bankCardManagerService.selectBankCardList(requestBank);
        Integer totalCount = bankCardManager.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bankCardManager.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            requestBank.setPageSize(defaultRowMaxCount);
            requestBank.setCurrPage(i+1);
            BankCardManagerResponse bankCardManager2  = bankCardManagerService.selectBankCardList(requestBank);
            if (bankCardManager2 != null && bankCardManager2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankCardManager2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("account", "银行账号");
        map.put("bank", "所属银行");
        map.put("cardType", "是否默认");
        map.put("cardProperty", "银行卡属性");
        map.put("addTime", "添加时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter accountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String account = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(account,3,7);
            }
        };
        mapAdapter.put("account", accountAdapter);
        return mapAdapter;
    }

    /**
     * 导出方法
     * @param request
     * @param response
     * @param bankCardManagerRequestBean
     * @throws Exception
     */
    //@ApiOperation(value = "江西银行开户銀行卡記錄导出", notes = "江西银行开户銀行卡記錄导出")
    //@PostMapping(value = "/exportnewbankcard")
    /*public void exportExcelNew(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) throws Exception {

        // 表格sheet名称
        String sheetName = "银行卡管理";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 封装查询条件
        BankCardManagerRequest requestBank = new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean, requestBank);
        //查找全部
        requestBank.setLimitFlg(true);
        // 需要输出的结果列表
        BankCardManagerResponse bankCardManagerResponse = bankCardManagerService.selectNewBankCardList(requestBank);
//        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectNewBankCardList(requestBank);
        //序号、用户名、当前手机号、姓名、身份证号、银行卡号、绑卡时间
        String[] titles = new String[]{"序号", "用户名", "当前手机号", "姓名", "身份证号", "银行卡号", "绑卡时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (null != bankCardManagerResponse) {
            List<BankcardManagerVO> bankcardManagerVOList = bankCardManagerResponse.getResultList();
            if (bankcardManagerVOList != null && bankcardManagerVOList.size() > 0) {
                int sheetCount = 1;
                int rowNum = 0;
                for (int i = 0; i < bankcardManagerVOList.size(); i++) {
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
                        BankcardManagerVO user = bankcardManagerVOList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 序号
                            cell.setCellValue(i + 1);
                        } else if (celLength == 1) {// 用户名
                            cell.setCellValue(user.getUserName());
                        } else if (celLength == 2) {// 当前手机号
                            cell.setCellValue(user.getMobile());
                        } else if (celLength == 3) {// 姓名
                            cell.setCellValue(user.getRealName());
                        } else if (celLength == 4) {// 身份证号
                            cell.setCellValue(user.getIdcard());
                        } else if (celLength == 5) {// 银行卡号
                            cell.setCellValue(user.getAccount());
                        } else if (celLength == 6) {// 绑卡时间
                            cell.setCellValue(user.getAddTime());
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("==================江西银行开户銀行卡記錄导出成功==================");
    }*/

    /**
     * 导出excel
     *
     * @param bankCardManagerRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "江西银行开户銀行卡記錄导出", notes = "江西银行开户銀行卡記錄导出")
    @PostMapping(value = "/exportnewbankcard")
    public void exportToExcelTwo(HttpServletRequest request, HttpServletResponse response,@RequestBody BankCardManagerRequestBean bankCardManagerRequestBean) throws Exception {
        // 封装查询条件
        BankCardManagerRequest requestBank = new BankCardManagerRequest();
        BeanUtils.copyProperties(bankCardManagerRequestBean, requestBank);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "银行卡管理";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        requestBank.setLimitFlg(false);
        //请求第一页5000条
        requestBank.setPageSize(defaultRowMaxCount);
        requestBank.setCurrPage(1);
        // 需要输出的结果列表
        BankCardManagerResponse bankCardManagerResponse = bankCardManagerService.selectNewBankCardList(requestBank);
        Integer totalCount = bankCardManagerResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMapTwo();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterTwo();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bankCardManagerResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            requestBank.setPageSize(defaultRowMaxCount);
            requestBank.setCurrPage(i+1);
            BankCardManagerResponse bankCardManagerResponse2 = bankCardManagerService.selectNewBankCardList(requestBank);
            if (bankCardManagerResponse2 != null && bankCardManagerResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankCardManagerResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMapTwo() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("mobile", "当前手机号");
        map.put("realName", "姓名");
        map.put("idcard", "身份证号");
        map.put("account", "银行卡号");
        map.put("addTime", "绑卡时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapterTwo() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }

    //汇付银行操作记录記錄查询
    @ApiOperation(value = "汇付银行操作记录", notes = "汇付银行操作记录")
    @PostMapping(value = "/selectbankcardlogbyexample")
    @ResponseBody
    public AdminResult<ListResult<BankCardLogCustomizedVO>> selectBankCardLogByExample(HttpServletRequest request, @RequestBody BankCardLogRequestBean bankCardLogRequestBean){
        BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
        BeanUtils.copyProperties(bankCardLogRequestBean, bankCardLogRequest);
        BankCardLogResponse bankCardLogResponse = bankCardManagerService.selectBankCardLogByExample(bankCardLogRequest);
        if (bankCardLogResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(bankCardLogResponse)) {
            return new AdminResult<>(FAIL, bankCardLogResponse.getMessage());
        }
        List<BankCardLogCustomizedVO> bankCardLogCustomizedVO = new ArrayList<BankCardLogCustomizedVO>();
        List<BankCardLogVO> list = bankCardLogResponse.getResultList();
        if(null!=list&&list.size()>0){
            bankCardLogCustomizedVO = CommonUtils.convertBeanList(list,BankCardLogCustomizedVO.class);
        }
        return new AdminResult<ListResult<BankCardLogCustomizedVO>>(ListResult.build(bankCardLogCustomizedVO, bankCardLogResponse.getCount()));
    }

    //@ApiOperation(value = "用户银行卡操作记录导出", notes = "用户银行卡操作记录导出")
    //@PostMapping(value = "/exportbankcardlog")
    /*public void exportBankCardLog(HttpServletRequest request,HttpServletResponse response, @RequestBody BankCardLogRequestBean bankCardLogRequestBean) throws Exception {

        // 表格sheet名称
        String sheetName = "用户银行卡操作记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 封装查询条件
        BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
        BeanUtils.copyProperties(bankCardLogRequestBean, bankCardLogRequest);
        // 银行卡属性
        Map<String, String> bankcardProperty = CacheUtil.getParamNameMap("BANKCARD_PROPERTY");
        //查找全部
        bankCardLogRequest.setLimitFlg(true);
        // 需要输出的结果列表
        BankCardLogResponse bankCardLogResponse =  bankCardManagerService.selectBankCardLogByExample(bankCardLogRequest);
//        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectNewBankCardList(requestBank);
        String[] titles = new String[] { "序号", "用户名", "银行账号", "所属银行", "操作", "银行卡属性", "操作时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (null != bankCardLogResponse) {
            List<BankCardLogVO> bankcardManagerVOList = bankCardLogResponse.getResultList();
            //序号、用户名、当前手机号、姓名、身份证号、银行卡号、绑卡时间
            if (null!= bankcardManagerVOList&& bankcardManagerVOList.size() > 0) {

                int sheetCount = 1;
                int rowNum = 0;

                for (int i = 0; i < bankcardManagerVOList.size(); i++) {
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
                        BankCardLogVO user = bankcardManagerVOList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 序号
                            cell.setCellValue(i + 1);
                        } else if (celLength == 1) {// 用户名
                            cell.setCellValue(user.getUserName());
                        } else if (celLength == 2) {// 银行账号
                            cell.setCellValue(user.getBankCode());
                        } else if (celLength == 3) {// 所属银行
                            cell.setCellValue(user.getBankName());
                        } else if (celLength == 4) {// 操作
                            if(user.getOperationType() == 0){
                                cell.setCellValue("绑定");
                            }else{
                                cell.setCellValue("删除");
                            }
                        } else if (celLength == 5) {
                            // 银行卡属性
                            *//*String cellValue = bankcardProperty.getOrDefault(user.getBankCode(),null);
                            cell.setCellValue(cellValue);*//*
                        } else if (celLength == 6) {// 添加时间
                            cell.setCellValue(user.getCreateTime());
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("==================用户银行卡操作记录导出成功==================");
    }*/

    /**
     * 导出excel
     *
     * @param bankCardLogRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "用户银行卡操作记录导出", notes = "用户银行卡操作记录导出")
    @PostMapping(value = "/exportbankcardlog")
    public void exportToExcelLog(HttpServletRequest request, HttpServletResponse response,@RequestBody BankCardLogRequestBean bankCardLogRequestBean) throws Exception {
        // 封装查询条件
        BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
        BeanUtils.copyProperties(bankCardLogRequestBean, bankCardLogRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "用户银行卡操作记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        bankCardLogRequest.setLimitFlg(false);
        //请求第一页5000条
        bankCardLogRequest.setPageSize(defaultRowMaxCount);
        bankCardLogRequest.setCurrPage(1);
        // 需要输出的结果列表
        BankCardLogResponse bankCardLogResponse =  bankCardManagerService.selectBankCardLogByExample(bankCardLogRequest);
        Integer totalCount = bankCardLogResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMapLog();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterLog();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, bankCardLogResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            bankCardLogRequest.setPageSize(defaultRowMaxCount);
            bankCardLogRequest.setCurrPage(i+1);
            BankCardLogResponse bankCardLogResponse2 =  bankCardManagerService.selectBankCardLogByExample(bankCardLogRequest);
            if (bankCardLogResponse2 != null && bankCardLogResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankCardLogResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMapLog() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("bankCode", "银行账号");
        map.put("bankName", "所属银行");
        map.put("operationType", "操作");
        map.put("createTime", "添加时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapterLog() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter operationTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String operationType = (String) object;
                if(operationType.equalsIgnoreCase("0")){
                    operationType = ("绑定");
                }else{
                    operationType = ("删除");
                }
                return operationType;
            }
        };
        mapAdapter.put("operationType", operationTypeAdapter);
        return mapAdapter;
    }

}
