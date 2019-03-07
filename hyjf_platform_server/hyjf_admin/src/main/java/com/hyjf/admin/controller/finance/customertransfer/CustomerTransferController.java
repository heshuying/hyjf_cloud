/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.customertransfer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import static com.hyjf.admin.common.util.ShiroConstants.*;

/**
 * @author: sunpeikai
 * @version: CustomerTransferController, v0.1 2018/7/5 18:00
 */
@Api(value = "资金中心-转账管理-用户转账",tags = "资金中心-转账管理-用户转账")
@Deprecated
@RestController
@RequestMapping(value = "/hyjf-admin/finance/customertransfer")
public class CustomerTransferController extends BaseController {

    @Autowired
    private CustomerTransferService customerTransferService;
    @Autowired
    private AdminCommonService adminCommonService;
    private static final String PERMISSIONS = "transferlist";

    /**
     * 用户转账-查询转账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "用户转账-查询转账列表",notes = "用户转账-查询转账列表")
    @PostMapping(value = "/transferlist")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<UserTransferVO>> transferList(@RequestBody CustomerTransferListRequest request){
        Integer count = customerTransferService.getTransferCount(request);
        count = (count == null)?0:count;
        List<UserTransferVO> userTransferVOList = customerTransferService.searchUserTransferList(request);
        return new AdminResult<>(ListResult.build(userTransferVOList,count));
    }

    /**
     * 获取交易类型和转账状态下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "转账状态下拉框数据",notes = "转账状态下拉框数据")
    @PostMapping(value = "/getselectordata")
    public AdminResult getSelectorData(){
        Map<String,List> data = new HashMap<>();
        List<DropDownVO> status = adminCommonService.getParamNameList("TRANSFER_STATUS");
        List<DropDownVO> type = adminCommonService.getParamNameList("TRANSFER_TYPE");
        data.put("statusData",status);
        data.put("typeData",type);
        return new AdminResult(data);
    }

    /**
     * 用户转账-导出转账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "用户转账-导出转账列表",notes = "用户转账-导出转账列表")
    @PostMapping(value = "/transferlistexport")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_EXPORT)
    public void exportTransferList(HttpServletRequest httpRequest, HttpServletResponse response, @RequestBody CustomerTransferListRequest request) throws UnsupportedEncodingException {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "转账记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        request.setCurrPage(1);
        request.setPageSize(defaultRowMaxCount);

        Integer count = customerTransferService.getTransferCount(request);
        if (count == null || count <= 0  ){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = count;
            // 需要输出的结果列表
            List<UserTransferVO> userTransferVOList = customerTransferService.searchUserTransferList(request);
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, userTransferVOList);
        }

        for (int i = 1; i < sheetCount; i++) {
            request.setCurrPage(i+1);
            List<UserTransferVO> userTransferVOList2 = customerTransferService.searchUserTransferList(request);
            if (!CollectionUtils.isEmpty(userTransferVOList2)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  userTransferVOList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(httpRequest, response, fileName, workbook);
    }


    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("orderId", "订单号");
        map.put("transferType", "交易类型");
        map.put("outUserName", "转出账户");
        map.put("updateTime", "转入账户");// 导出时是静态字符，此处用updateTime占位
        map.put("transferAmount", "转账金额");
        map.put("reconciliationId", "对账标识");
        map.put("status", "转账状态");
        map.put("transferUrl", "转账链接");
        map.put("createUserName","操作员");
        map.put("createTime", "操作时间");
        map.put("transferTime", "转账时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter transferTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                // 交易类型
                Integer transferType = (Integer) object;
                List<ParamNameVO> transferTypes = customerTransferService.searchParamNameList("TRANSFER_TYPE");
                for (int j = 0; j < transferTypes.size(); j++) {
                    if (transferTypes.get(j).getNameCd().equals(String.valueOf(transferType))) {
                        return transferTypes.get(j).getName();
                    }
                }
                return "";
            }
        };
        IValueFormatter transferAmountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal transferAmount = (BigDecimal) object;
                return String.valueOf(transferAmount);
            }
        };
        IValueFormatter reconciliationIdAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String reconciliationId = (String) object;
                return StringUtils.isNotBlank(reconciliationId) ? reconciliationId : "";
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer status = (Integer) object;
                // 转账状态
                List<ParamNameVO> transferStatus = customerTransferService.searchParamNameList("TRANSFER_STATUS");
                for(int j=0;j<transferStatus.size();j++){
                    if(transferStatus.get(j).getNameCd().equals(String.valueOf(status))){
                        transferStatus.get(j).getName();
                    }
                }
                return "";
            }
        };

        IValueFormatter createTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date createTime = (Date) object;
                return createTime != null ? GetDate.date2Str(createTime, GetDate.datetimeFormat) : "";
            }
        };

        IValueFormatter transferTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date transferTime = (Date) object;
                return transferTime != null ? GetDate.date2Str(transferTime, GetDate.datetimeFormat) : "";
            }
        };

        IValueFormatter updateTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return "平台";
            }
        };

        mapAdapter.put("transferType",transferTypeAdapter);
        mapAdapter.put("transferAmount",transferAmountAdapter);
        mapAdapter.put("reconciliationId",reconciliationIdAdapter);
        mapAdapter.put("status",statusAdapter);
        mapAdapter.put("createTime",createTimeAdapter);
        mapAdapter.put("updateTime",updateTimeAdapter);
        mapAdapter.put("transferTime",transferTimeAdapter);
        return mapAdapter;
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
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_ADD)
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
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_ADD)
    public AdminResult addTransfer(HttpServletRequest request, @RequestBody CustomerTransferRequest customerTransferRequest){
        //Integer userId = Integer.valueOf(getUser(request).getId());
        customerTransferService.checkCustomerTransferParam(customerTransferRequest);
        //AdminSystemVO adminSystemVO = customerTransferService.getAdminSystemByUserId(userId);
        AdminSystemVO adminSystemVO = getUser(request);
        customerTransferRequest.setCreateUserId(Integer.valueOf(adminSystemVO.getId()));
        customerTransferRequest.setCreateUserName(adminSystemVO.getUsername());
        logger.debug("request:[{}]", JSON.toJSONString(customerTransferRequest));
        boolean success = customerTransferService.insertTransfer(customerTransferRequest);
        if(success){
            logger.info("success");
            return new AdminResult(SUCCESS,SUCCESS_DESC);
        }
        logger.error("fail");
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
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_TRANSFER_SEND_EMAIL)
    public AdminResult transferSendMail(@RequestBody Map map){
        Integer transferId = (Integer) map.get("transferId");
        customerTransferService.transferSendMail(transferId);
        return new AdminResult(SUCCESS,SUCCESS_DESC);
    }


}
