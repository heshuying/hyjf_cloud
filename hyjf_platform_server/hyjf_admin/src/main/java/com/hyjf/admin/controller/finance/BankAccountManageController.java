/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/28 15:57
 */
@Api(value = "账户管理(银行)")
@RestController
@RequestMapping("/hyjf-admin/bankAccountManage")
public class BankAccountManageController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * 画面初始化
     *
     * @param request
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "资金中心", notes = "银行账户管理")
    @PostMapping(value = "/init")
    @ResponseBody
    public JSONObject init(HttpServletRequest request, BankAccountManageRequest form) {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    /**
     * 列表查询(初始无参/查询带参 共用)
     *
     * @param request
     * @return 进入银行账户管理页面
     */
    @ApiOperation(value = "资金中心", notes = "银行账户管理")
    @PostMapping(value = "/selectBankAccountManage")
    @ResponseBody
    public JSONObject selectBankAccountManage(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        BankAccountManageRequest bankAccountManageRequest = setRequese(map);
        Integer count = this.bankAccountManageService.queryAccountCount(bankAccountManageRequest);
        if (count > 0) {
            List<BankAccountManageCustomizeVO> assetList = bankAccountManageService.queryAccountInfos(bankAccountManageRequest);
            String status = Response.FAIL;
            if (null != assetList && assetList.size() > 0) {
                jsonObject.put("record", assetList);
                status = Response.SUCCESS;
            }
            jsonObject.put("status", status);
        }
        return jsonObject;
    }

    /**
     * 拼装查询参数
     * @param mapParam
     * @return
     */
    private BankAccountManageRequest setRequese(Map<String, Object> mapParam) {
        BankAccountManageRequest bankAccountManageRequest = new BankAccountManageRequest();
        if (null != mapParam && mapParam.size() > 0) {
            // 用户名
            if (mapParam.containsKey("userNameSrch")) {
                bankAccountManageRequest.setUserNameSrch(mapParam.get("userNameSrch").toString());
            }
            // 部门
            if (mapParam.containsKey("combotreeSrch")) {
                bankAccountManageRequest.setCombotreeSrch(mapParam.get("combotreeSrch").toString());
            }
            // 部门
//            if (mapParam.containsKey("combotreeListSrch")) {
//                bankAccountManageRequest.setCombotreeListSrch(mapParam.get("combotreeListSrch").toString());
//            }
            // 产品类型
            if (mapParam.containsKey("accountSrch")) {
                bankAccountManageRequest.setAccountSrch(mapParam.get("accountSrch").toString());
            }
            // 产品类型
            if (mapParam.containsKey("vipSrch")) {
                bankAccountManageRequest.setVipSrch(mapParam.get("vipSrch").toString());
            }
            if (mapParam.containsKey("limitStart") && StringUtils.isNotBlank(mapParam.get("limitStart").toString())) {
                bankAccountManageRequest.setLimitStart(Integer.parseInt(mapParam.get("limitStart").toString()));
            }
            if (mapParam.containsKey("limlimitEndit") && StringUtils.isNotBlank(mapParam.get("limitEnd").toString())) {
                bankAccountManageRequest.setLimitEnd(Integer.parseInt(mapParam.get("limitEnd").toString()));
            }
        }
        return bankAccountManageRequest;
    }

}
