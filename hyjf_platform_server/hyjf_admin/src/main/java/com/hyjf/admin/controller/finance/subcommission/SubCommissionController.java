/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.subcommission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.SubCommissionService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: SubCommissionController, v0.1 2018/7/10 9:28
 */
@Api(value = "资金中心-平台账户分佣",tags = "资金中心-平台账户分佣")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/subcommission")
public class SubCommissionController extends BaseController {

    @Autowired
    private SubCommissionService subCommissionService;

    @Autowired
    private AdminCommonService adminCommonService;

    /**
     * 平台账户分佣
     * @auth sunpeikai
     * @param request 筛选参数
     * @return
     */
    @ApiOperation(value = "平台账户分佣列表查询",notes = "平台账户分佣列表查询")
    @PostMapping(value = "/subcommissionlist")
    public AdminResult<ListResult<SubCommissionVO>> subCommissionList(@RequestBody SubCommissionRequest request){
        Integer count = subCommissionService.getSubCommissionCount(request);
        count = (count == null)?0:count;
        List<SubCommissionVO> subCommissionVOList = subCommissionService.searchSubCommissionList(request);
        logger.debug(JSON.toJSONString(subCommissionVOList));
        return new AdminResult<>(ListResult.build(subCommissionVOList,count));
    }

    /**
     * 获取交易类型和转账状态下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "转账状态下拉框数据",notes = "转账状态下拉框数据")
    @PostMapping(value = "/gettransferstatus")
    public AdminResult<ListResult<DropDownVO>> getSelectorData(){
        List<DropDownVO> status = adminCommonService.getParamNameList("FS_TRANSFER_STATUS");
        return new AdminResult<>(ListResult.build(status,0));
    }

    /**
     * 平台账户分佣导出
     * @auth sunpeikai
     * @param request 筛选参数
     * @return
     */
    @ApiOperation(value = "平台账户分佣导出",notes = "平台账户分佣导出")
    @PostMapping(value = "/subcommissionlistexport")
    public void exportSubCommissionList(HttpServletRequest request, HttpServletResponse response,@RequestBody SubCommissionRequest subCommissionRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "账户分佣明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        subCommissionRequest.setCurrPage(-1);
        Integer totalCount = subCommissionService.getSubCommissionCount(subCommissionRequest);
        List<ParamNameVO> transferStatus = this.subCommissionService.searchParamNameList("FS_TRANSFER_STATUS");

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter(transferStatus);
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            subCommissionRequest.setPageSize(defaultRowMaxCount);
            subCommissionRequest.setCurrPage(i);
            List<SubCommissionVO> subCommissionVOList = subCommissionService.searchSubCommissionList(subCommissionRequest);
            if (subCommissionVOList != null && subCommissionVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  subCommissionVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("orderId", "转账订单号");
        map.put("accountId", "转出电子账户号");
        map.put("account", "转账金额");
        map.put("receiveUserName", "转入用户名");
        map.put("truename", "转入姓名");
        map.put("receiveAccountId", "转入电子账户号");
        map.put("tradeStatus", "转账状态");
        map.put("createTime", "转账时间");
        map.put("createUserName", "操作人");
        map.put("remark", "备注");
        map.put("txDate", "发送日期");
        map.put("txTime", "发送时间");
        map.put("seqNo", "系统跟踪号");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter(List<ParamNameVO> transferStatus) {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();

        IValueFormatter accountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal accountTmp = (BigDecimal) object;
                String account = String.valueOf(accountTmp);
                return StringUtils.isEmpty(account) ? StringUtils.EMPTY : account;
            }
        };

        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer tradeStatus = (Integer) object;
                // 转账状态
                for (int j = 0; j < transferStatus.size(); j++) {
                    if (transferStatus.get(j).getNameCd().equals(String.valueOf(tradeStatus))) {
                        return transferStatus.get(j).getName();
                    }
                }
                return "";
            }
        };
        IValueFormatter createTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date createTime = (Date) object;
                return createTime == null?"":GetDate.dateToString2(createTime);
            }
        };

        IValueFormatter txDateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer txDate = (Integer) object;
                String tmpTxDate = txDate.toString();
                return StringUtils.isEmpty(tmpTxDate) ? StringUtils.EMPTY : tmpTxDate.substring(0, 4) + "-" + tmpTxDate.substring(4, 6) + "-" + tmpTxDate.substring(6, 8);
            }
        };
        IValueFormatter txTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer txTime = (Integer) object;
                String tmpTxTime = String.format("%06d", txTime);
                return StringUtils.isEmpty(tmpTxTime) ? StringUtils.EMPTY : tmpTxTime.substring(0, 2) + ":" + tmpTxTime.substring(2, 4) + ":" + tmpTxTime.substring(4, 6);
            }
        };
        IValueFormatter strAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String str = (String) object;
                return StringUtils.isEmpty(str) ? StringUtils.EMPTY : str;
            }
        };
        mapAdapter.put("orderId", strAdapter);
        mapAdapter.put("accountId", strAdapter);
        mapAdapter.put("account", accountAdapter);
        mapAdapter.put("receiveUserName", strAdapter);
        mapAdapter.put("truename", strAdapter);
        mapAdapter.put("receiveAccountId", strAdapter);
        mapAdapter.put("tradeStatus", statusAdapter);
        mapAdapter.put("createTime", createTimeAdapter);
        mapAdapter.put("createUserName", strAdapter);
        mapAdapter.put("remark", strAdapter);
        mapAdapter.put("txDate", txDateAdapter);
        mapAdapter.put("txTime", txTimeAdapter);
        mapAdapter.put("seqNo", strAdapter);
        return mapAdapter;
    }

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "发起账户分佣所需的detail信息",notes = "发起账户分佣所需的detail信息")
    @PostMapping(value = "/searchdetails")
    public AdminResult searchDetails(HttpServletRequest request){
        Integer userId = Integer.valueOf(getUser(request).getId());
        JSONObject result = subCommissionService.searchDetails(userId);
        return new AdminResult(result);
    }

    /**
     * 发起账户分佣
     * @auth sunpeikai
     * @param request 插入数据参数
     * @return
     */
    @ApiOperation(value = "发起账户分佣",notes = "发起账户分佣")
    @PostMapping(value = "/subcommission")
    public AdminResult subCommission(HttpServletRequest request,@RequestBody SubCommissionRequest subCommissionRequest){
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        JSONObject jsonObject = subCommissionService.subCommission(loginUserId,subCommissionRequest);
        return new AdminResult(jsonObject);
    }
}
