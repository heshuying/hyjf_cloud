package com.hyjf.admin.controller.finance.recharge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AccountRechargeRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.finance.recharge.AccountRechargeService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeCustomizeVO;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资金中心 - 充值管理
 * @Author : huanghui
 */
@Api(value = "资金中心->充值管理",tags = "资金中心->充值管理")
@RestController
@RequestMapping("/hyjf-admin/recharge")
public class AccountRechargeController extends BaseController {

    public static final String PERMISSIONS = "recharge";

    @Autowired
    private AccountRechargeService rechargeService;

    /**
     * 资金中心 - 充值管理 检索下拉框
     * @return
     */
    @ApiOperation(value = "充值管理检索下拉框", notes = "充值管理检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        //用户账户充值状态
        List<ParamNameVO> paramList = this.rechargeService.selectParamNameList("RECHARGE_STATUS");
        logger.info(JSON.toJSONString(paramList));
        List<Object> rechargeStatusList = new ArrayList<>();
        for(int i = 0; i<paramList.size(); i++){
            Map<String, Object> rechargeStatusMap = new HashedMap();
            rechargeStatusMap.put("key", paramList.get(i).getNameCd());
            rechargeStatusMap.put("value", paramList.get(i).getName());
            rechargeStatusList.add(rechargeStatusMap);
        }

        // 充值银行列表
        List<JxBankConfigVO> banks = this.rechargeService.getBankcardList();
        logger.info(JSON.toJSONString(paramList));
        List<Object> banksList = new ArrayList<>();
        for(int i = 0; i<banks.size(); i++){
            Map<String, Object> banksMap = new HashedMap();
            banksMap.put("key", banks.get(i).getBankName());
            banksMap.put("value", banks.get(i).getBankName());
            banksList.add(banksMap);
        }

        // 资金托管平台
        List<ParamNameVO> bankType = this.rechargeService.selectParamNameList("BANK_TYPE");
        logger.info(JSON.toJSONString(paramList));
        List<Object> bankTypeList = new ArrayList<>();
        for(int i = 0; i<bankType.size(); i++){
            Map<String, Object> bankTypeMap = new HashedMap();
            bankTypeMap.put("key", bankType.get(i).getNameCd());
            bankTypeMap.put("value", bankType.get(i).getName());
            bankTypeList.add(bankTypeMap);
        }

        //充值类型
        //初始充值类型List
        List<Object> rechargeList = new ArrayList<>();

        //初始充值类型Map
        Map<String, Object> rechargeType = new HashedMap();
        Map<String, Object> rechargeType2 = new HashedMap();
        Map<String, Object> rechargeType3 = new HashedMap();
        Map<String, Object> rechargeType4 = new HashedMap();

        //设置对应的键值对
        rechargeType.put("key", "B2C");
        rechargeType.put("value", "个人网银充值");
        rechargeType2.put("key", "B2B");
        rechargeType2.put("value", "企业网银充值");
        rechargeType3.put("key", "QP");
        rechargeType3.put("value", "快捷充值");
        rechargeType4.put("key", "OFFLINE");
        rechargeType4.put("value", "线下充值");

        //map加入List
        rechargeList.add(rechargeType);
        rechargeList.add(rechargeType2);
        rechargeList.add(rechargeType3);
        rechargeList.add(rechargeType4);

        //充值平台
        //初始化充值平台的list 和 Map
        List<Object> rechargePlatformList = new ArrayList<>();
        Map<String, Object> rechargePlatform = new HashedMap();
        Map<String, Object> rechargePlatform2 = new HashedMap();
        Map<String, Object> rechargePlatform3 = new HashedMap();
        Map<String, Object> rechargePlatform4 = new HashedMap();

        rechargePlatform.put("key", 0);
        rechargePlatform.put("value", "PC");
        rechargePlatform2.put("key", 1);
        rechargePlatform2.put("value", "微信");
        rechargePlatform3.put("key", 2);
        rechargePlatform3.put("value", "Android");
        rechargePlatform4.put("key", 3);
        rechargePlatform4.put("value", "iOS");

        //Map 加入 List
        rechargePlatformList.add(rechargePlatform);
        rechargePlatformList.add(rechargePlatform2);
        rechargePlatformList.add(rechargePlatform3);
        rechargePlatformList.add(rechargePlatform4);

        //获取充值手续费方式
        //初始化获取充值手续费List 和 Map
        List<Object> accessFeeMethodList = new ArrayList<>();
        Map<String, Object> accessFeeMethodMap = new HashedMap();
        Map<String, Object> accessFeeMethodMap2 = new HashedMap();

        accessFeeMethodMap.put("key", 0);
        accessFeeMethodMap.put("value", "向用户收取");
        accessFeeMethodMap2.put("key", 1);
        accessFeeMethodMap2.put("value", "向商户收取");

        //Map 加入 List
        accessFeeMethodList.add(accessFeeMethodMap);
        accessFeeMethodList.add(accessFeeMethodMap2);

        //用户角色
        List<Object> userRoleList = new ArrayList<>();
        Map<String, Object> userRoleMap = new HashedMap();
        Map<String, Object> userRoleMap2 = new HashedMap();
        Map<String, Object> userRoleMap3 = new HashedMap();
        Map<String, Object> userRoleMap4 = new HashedMap();

        //选项加入Map
        userRoleMap.put("key", " ");
        userRoleMap.put("value", "全部");
        userRoleMap2.put("key", 1);
        userRoleMap2.put("value", "出借人");
        userRoleMap3.put("key", 2);
        userRoleMap3.put("value", "借款人");
        userRoleMap4.put("key", 3);
        userRoleMap4.put("value", "担保机构");

        userRoleList.add(userRoleMap);
        userRoleList.add(userRoleMap2);
        userRoleList.add(userRoleMap3);
        userRoleList.add(userRoleMap4);

        // 初始化总的Map
        Map<String, Object> allMap = new HashedMap();
        allMap.put("rechargeStatusList", rechargeStatusList);
        allMap.put("banksList", banksList);
        allMap.put("bankTypeList", bankTypeList);
        allMap.put("rechargeTypeList", rechargeList);
        allMap.put("rechargePlatformList", rechargePlatformList);
        allMap.put("accessFeeMethodList", accessFeeMethodList);
        allMap.put("userRoleList", userRoleList);

        //初始化List 将所有Map放入此List 以达到返回指定格式数据的方式
//        List<Object> allList = new ArrayList<>();
//        allList.add(allMap);

        if (paramList != null && banks != null && bankType != null){
            jsonObject.put("status", BaseResult.SUCCESS);
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
            jsonObject.put("data", allMap);
        }else {
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG);
            jsonObject.put("data", "");
        }
        return jsonObject;
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "充值管理列表", notes = "资金中心->充值管理")
    @PostMapping(value = "/accountRechargeList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AccountRechargeCustomizeVO>> queryRechargeList(HttpServletRequest request, @RequestBody AccountRechargeRequestBean requestBean){

        AccountRechargeRequest copyRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(requestBean, copyRequest);

        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        try {
            for (String string : perm) {
                if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                    isShow = true;
                }
            }
        }catch (NullPointerException e){
            logger.error("Admin - 充值管理未获取到当前用户的权限列表!", e.getMessage());
        }

        //初始化返回List
        List<AccountRechargeCustomizeVO> returnList = new ArrayList<>();
        AccountRechargeCustomizeResponse rechargeResponse = rechargeService.queryRechargeList(copyRequest);

        if (rechargeResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(rechargeResponse)){
            return new AdminResult<>(FAIL, rechargeResponse.getMessage());
        }

        List<AccountRechargeCustomizeVO> accountRechargeCustomizeVO = rechargeResponse.getResultList();

        if (CollectionUtils.isNotEmpty(rechargeResponse.getResultList())){
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for(AccountRechargeCustomizeVO rechargeCustomizeVO:accountRechargeCustomizeVO){
                    rechargeCustomizeVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(rechargeCustomizeVO.getMobile()));
                }
            }
            returnList = CommonUtils.convertBeanList(accountRechargeCustomizeVO, AccountRechargeCustomizeVO.class);
            return new AdminResult<ListResult<AccountRechargeCustomizeVO>>(ListResult.build(returnList, rechargeResponse.getCount()));
        }else {
            return new AdminResult<ListResult<AccountRechargeCustomizeVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "更新充值状态", notes = "资金中心->更新充值状态")
    @PostMapping(value = "/modifyRechargeStatus")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public JSONObject modifyRechargeStatus(@RequestBody AccountRechargeRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();

        //用户ID
        Integer userId = requestBean.getUserId();
        //订单ID
        String nid = requestBean.getNidSearch();

        if (Validator.isNull(userId) || Validator.isNull(nid)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":订单编号或用户ID为空!");
            return jsonObject;
        }

        //更新充值状态
        boolean isAccountUpdate = this.rechargeService.updateRechargeStatus(userId, nid);
        String status= BaseResult.SUCCESS;
        if (isAccountUpdate){
            status = BaseResult.SUCCESS;
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        }else {
            status = Response.FAIL;
            jsonObject.put("statusDesc", Response.FAIL_MSG);
        }
        jsonObject.put("status", status);
        return jsonObject;
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "确认充值(FIX) 操作", notes = "资金中心->确认充值(FIX) 操作")
    @PostMapping(value = "/rechargeFix")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CONFIRM)
    public JSONObject rechargeFix(@RequestBody AccountRechargeRequestBean requestBean){

        AccountRechargeRequest copyRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(requestBean, copyRequest);

        JSONObject jsonObject = new JSONObject();

        //用户ID
        Integer userId = requestBean.getUserId();
        //订单编号
        String nid = requestBean.getNidSearch();
        //订单状态
        String status = requestBean.getStatusSearch();

        if (Validator.isNull(userId)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":用户ID不能为空!");
            return jsonObject;
        }else if (Validator.isNull(nid)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":订单编号不能为空!");
            return jsonObject;
        }else if (Validator.isNull(status)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":订单状态为空!");
            return jsonObject;
        }

        AccountRechargeCustomizeResponse isAccountUpdate = this.rechargeService.updateAccountAfterRecharge(copyRequest);

        String backStatus = null;
        String backMsg = null;
        if (isAccountUpdate != null){
            backStatus = BaseResult.SUCCESS;
            backMsg = BaseResult.SUCCESS_DESC;
        }else {
            backStatus = BaseResult.FAIL;
            backMsg = BaseResult.FAIL_DESC;
        }

        jsonObject.put("status", backStatus);
        jsonObject.put("statusDesc", backMsg);
        return jsonObject;
    }

    @ApiOperation(value = "充值管理数据导出",notes = "资金中心->充值管理数据导出")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountRechargeRequestBean accountRechargeRequestBean) throws Exception {

        // 是否具有组织机构查看权限
        String isOrganizationView = accountRechargeRequestBean.getIsOrganizationView();

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "充值管理详情数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        AccountRechargeRequest accountRechargeRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(accountRechargeRequestBean, accountRechargeRequest);

        //请求第一页5000条
        accountRechargeRequest.setPageSize(defaultRowMaxCount);
        accountRechargeRequest.setCurrPage(1);
        AccountRechargeCustomizeResponse rechargeResponse = rechargeService.queryRechargeList(accountRechargeRequest);

        Integer totalCount = rechargeResponse.getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, rechargeResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {

            accountRechargeRequest.setPageSize(defaultRowMaxCount);
            accountRechargeRequest.setCurrPage(i+1);
            AccountRechargeCustomizeResponse rechargeCustomizeResponse = rechargeService.queryRechargeList(accountRechargeRequest);
            if (rechargeCustomizeResponse != null && rechargeCustomizeResponse.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  rechargeCustomizeResponse.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap(String isOrganizationView) {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("nid", "订单号");
        map.put("username", "用户名");
        map.put("accountId", "电子账号");
        map.put("mobile", "手机号");
        map.put("seqNo", "流水号");
        map.put("isBank", "资金托管平台");
        map.put("roleId", "用户角色");
        map.put("userAttribute", "用户属性（当前）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("userRegionName", "用户所属一级分部（当前）");
            map.put("userBranchName", "用户所属二级分部（当前）");
            map.put("userDepartmentName", "用户所属团队（当前）");
        }
        map.put("referrerName", "推荐人用户名（当前）");
        map.put("referrerTrueName", "推荐人姓名（当前）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("referrerRegionName", "推荐人所属一级分部（当前）");
            map.put("referrerBranchName", "推荐人所属二级分部（当前）");
            map.put("referrerDepartmentName", "推荐人所属团队（当前）");
        }
        map.put("type", "充值渠道");
        map.put("gateType", "充值类型");
        map.put("bankName", "充值银行");
        map.put("cardid", "银行卡号");
        map.put("money", "充值金额");
        map.put("fee", "手续费");
        map.put("balance", "到账金额");
        map.put("statusName", "充值状态");
        map.put("client", "充值平台");
        map.put("createTime", "充值时间");
        map.put("txDate", "发送日期");
        map.put("txTime", "发送时间");
        map.put("bankSeqNo", "系统跟踪号");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter userAttributeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String userAttribute = (String) object;
                if ("0".equals(userAttribute)) {
                    return "无主单";
                } else if ("1".equals(userAttribute)) {
                    return "有主单";
                } else if ("2".equals(userAttribute)) {
                    return "线下员工";
                } else if ("3".equals(userAttribute)) {
                    return "线上员工";
                }
                return "";
            }
        };
        IValueFormatter gateTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                // 充值类型
                String gateType = (String) object;
                if ("B2C".equals(gateType)) {
                    return "个人网银充值";
                } else if ("B2B".equals(gateType)) {
                    return "企业网银充值";
                } else if ("QP".equals(gateType)) {
                    return "快捷充值";
                } else {
                    return gateType;
                }
            }
        };

        IValueFormatter moneyAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal money = (BigDecimal) object;
                return money.toString();
            }
        };
        IValueFormatter feeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal fee = (BigDecimal) object;
                return fee != null ? (fee + "") : (0.00 + "");
            }
        };
        mapAdapter.put("userAttribute", userAttributeAdapter);
        mapAdapter.put("gateType", gateTypeAdapter);
        mapAdapter.put("money", moneyAdapter);
        mapAdapter.put("fee", feeAdapter);
        mapAdapter.put("balance", moneyAdapter);
        return mapAdapter;
    }


}
