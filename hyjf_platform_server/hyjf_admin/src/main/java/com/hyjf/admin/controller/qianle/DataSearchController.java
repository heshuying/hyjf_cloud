package com.hyjf.admin.controller.qianle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.DataSearchBean;
import com.hyjf.admin.common.result.WebResult;
import com.hyjf.admin.mq.SmsProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.qianle.DataSearchService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author lisheng
 * @version DataSearchController, v0.1 2018/8/21 9:37
 */
@Api(tags = "web端-千乐数据查询统计")
@RestController
@RequestMapping("/hyjf-web/qianle")
public class DataSearchController {
    @Autowired
    DataSearchService dataSearchService;
    public static final String SMSSENDFORMOBILE = "smsSendForMobile";

    @Autowired
    SmsProducer smsProducer;
    private Logger logger = LoggerFactory.getLogger(DataSearchController.class);

    /**
     * 千乐数据查询
     * @return
     */
    @ApiOperation(value = "千乐数据查询", notes = "千乐数据查询")
    @PostMapping("/querylist")
    public  WebResult<Map<String, Object>> getQianleSanList(@RequestBody DataSearchBean request){
        DataSearchRequest dataSearchRequest = CommonUtils.convertBean(request, DataSearchRequest.class);
        DataSearchCustomizeResponse response = dataSearchService.findDataList(dataSearchRequest);
        HashMap<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(response.getResultList())) {
            result.put("data", response.getResultList());
            result.put("money",response.getMoney());
        }else{
            result.put("data", new String [0]);
            result.put("money",new String [0]);
        }
        WebResult webResult = new WebResult(result);
        Page page = new Page();
        page.setTotal(response.getCount());
        page.setCurrPage(request.getCurrPage());
        page.setPageSize(request.getPageSize());
        webResult.setPage(page);
        return webResult;
    }
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping("/cancle")
    public WebResult cancle(){
        WebResult webResult = new WebResult();
        return webResult;
    }

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    public WebResult login(@RequestBody DataSearchBean request){
        String code = request.getCode();
        String mobile = request.getMobile();
        WebResult webResult = new WebResult();
        if(!dataSearchService.checkMobile(mobile)){
            return new WebResult("111", "手机号校验失败");
        }
        int result = dataSearchService.checkMobileCode(mobile, code);
        if(result>0){
            return webResult;
        }else{
            webResult=new WebResult("111", "登录失败");
            return webResult;
        }

    }

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @PostMapping("/sendsms")
    public WebResult sendsms(@RequestBody DataSearchBean form, HttpServletRequest request) {
        String mobile = form.getMobile();
        WebResult webResult = new WebResult();
        JSONObject jo = new JSONObject();
        if (!dataSearchService.checkMobile(mobile)) {
            webResult = new WebResult("111", "手机号验证失败");
            return webResult;
        }
        SmsConfigVO smsConfig = dataSearchService.initSmsConfig().getResult();
        String ip = GetCilentIP.getIpAddr(request);
        String ipCount = RedisUtils.get(ip + ":MaxIpCount");
        if (StringUtils.isBlank(ipCount)) {
            ipCount = "0";
            RedisUtils.set(ip + ":MaxIpCount", "0");
        }
        if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
            if (Integer.valueOf(ipCount) == smsConfig.getMaxIpCount()) {

                RedisUtils.set(ip + ":MaxIpCount", (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
            }
            webResult = new WebResult("111", "IP访问次数超限");
            return webResult;
        }
        // 判断最大发送数max_phone_count
        String count = RedisUtils.get(mobile + ":MaxPhoneCount");
        if (StringUtils.isBlank(count)) {
            count = "0";
            RedisUtils.set(mobile + ":MaxPhoneCount", "0");
        }
        if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
            if (Integer.valueOf(count) == smsConfig.getMaxPhoneCount()) {
                RedisUtils.set(mobile + ":MaxPhoneCount", (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);
            }
            webResult = new WebResult("111", "手机发送次数超限");
            return webResult;
        }
        // 判断发送间隔时间
        String intervalTime = RedisUtils.get(mobile + ":IntervalTime");
        if (StringUtils.isNotBlank(intervalTime)) {
            webResult = new WebResult("111", "发送时间间隔太短");
            return webResult;
        }
        boolean result = false;
        // 生成验证码
        String checkCode = GetCode.getRandomSMSCode(6);
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", checkCode);
        // 发送短信验证码
        SmsMessage smsMessage = new SmsMessage(null, param, mobile, null, SMSSENDFORMOBILE, null, CustomConstants.PARAM_TPL_ZHUCE, CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
            result = smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
        } catch (MQException e) {
            e.printStackTrace();
        }
        // checkCode过期时间，默认120秒
        RedisUtils.set(mobile + ":MaxValidTime", checkCode, smsConfig.getMaxValidTime() == null ? 120 : smsConfig.getMaxValidTime() * 60);
        // 发送checkCode最大时间间隔，默认60秒
        RedisUtils.set(mobile + ":IntervalTime", mobile, smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());

        // 短信发送成功后处理
        if (result) {
            // 累计IP次数
            String currentMaxIpCount = RedisUtils.get(ip + ":MaxIpCount");
            if (StringUtils.isBlank(currentMaxIpCount)) {
                currentMaxIpCount = "0";
            }
            // 累加手机次数
            String currentMaxPhoneCount = RedisUtils.get(mobile + ":MaxPhoneCount");
            if (StringUtils.isBlank(currentMaxPhoneCount)) {
                currentMaxPhoneCount = "0";
            }
            RedisUtils.set(ip + ":MaxIpCount", (Integer.valueOf(currentMaxIpCount) + 1) + "", 24 * 60 * 60);
            RedisUtils.set(mobile + ":MaxPhoneCount", (Integer.valueOf(currentMaxPhoneCount) + 1) + "", 24 * 60 * 60);
        }

        // 保存短信验证码
        this.dataSearchService.saveSmsCode(checkCode,mobile,"PC");
        jo.put("status", 0);
        String maxValidTime = "60";
        Integer time = smsConfig.getMaxIntervalTime();
        if (time != null) {
            maxValidTime = time + "";
        }
        webResult = new WebResult("000", "短信发送成功");
        return webResult;
    }


    /**
     * 千乐数据导出
     * @return
     */
    @ApiOperation(value = "千乐数据导出", notes = "千乐数据导出")
    @GetMapping("/exportdata")
    public  void exportAction( DataSearchBean request,HttpServletResponse response){
        DataSearchRequest dataSearchRequest = CommonUtils.convertBean(request, DataSearchRequest.class);
        DataSearchCustomizeResponse result = dataSearchService.findDataList(dataSearchRequest);
        // 表格sheet名称
        String sheetName = "数据导出";
        List<DataSearchCustomizeVO> resultList = result.getResultList();
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]
                { "序号", "注册时间", "用户名", "姓名", "手机号", "投资类型",
                        "项目/计划编号", "投资金额", "投资期限","年化金额"
                        ,"佣金7%","推荐人姓名","投资时间"
                };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        //HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i<10000&&i < resultList.size(); i++) {
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
                    DataSearchCustomizeVO record = resultList.get(i);
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    else if (celLength == 1) {
                        cell.setCellValue(record.getAddtimes());//注册日期
                    }
                    else if (celLength == 2) {
                        cell.setCellValue(record.getUsername());//用户名
                    }
                    else if (celLength == 3) {
                        cell.setCellValue(record.getTruename());//姓名
                    }
                    else if (celLength == 4) {
                        cell.setCellValue(record.getMobile());//手机号
                    }
                    else if (celLength == 5) {
                        cell.setCellValue(record.getType());//投资类型
                    }
                    else if (celLength == 6) {
                        cell.setCellValue(record.getPlannid());//项目/计划编号
                    }


                    else if (celLength == 7) {
                        cell.setCellValue(record.getAccount());//投资金额
                    }
                    else if (celLength == 8) {
                        cell.setCellValue(record.getBorrow_period());//投资期限
                    }else if (celLength == 9) {
                        cell.setCellValue(record.getYearAccount());//年化金额
                    }else if (celLength == 10) {
                        cell.setCellValue(record.getMoney());//佣金7%
                    }else if (celLength == 11) {
                        cell.setCellValue(record.getReffername());//推荐人姓名
                    }else if (celLength == 12) {
                        cell.setCellValue(record.getAddtimes());//投资日期
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }


}