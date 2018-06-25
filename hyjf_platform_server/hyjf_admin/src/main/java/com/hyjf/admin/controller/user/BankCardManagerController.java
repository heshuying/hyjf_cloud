/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.BankCardManagerService;
import com.hyjf.admin.service.BankOpenRecordService;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankcardManagerVO;
import com.hyjf.common.cache.CacheUtil;
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
 * @author nxl
 * @version RegistRecordController, v0.1 2018/6/23 15:16
 */

@Api(value = "銀行卡管理")
@RestController
@RequestMapping("/hyjf-admin/bankcardManager")
public class BankCardManagerController {
    @Autowired
    public BankCardManagerService bankCardManagerService;

    @ApiOperation(value = "銀行卡管理", notes = "銀行卡管理页面初始化")
    /*@RequestMapping(value = "/usersInit", method = {RequestMethod.GET, RequestMethod.POST})*/
    @PostMapping(value = "/bankCardInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = new JSONObject();
        // 银行卡属性
        Map<String, String> bankcardProperty = CacheUtil.getParamNameMap("BANKCARD_PROPERTY");
        // 是否默认
        Map<String, String> bankcardType  = CacheUtil.getParamNameMap("BANKCARD_TYPE");
        List<BanksConfigVO> listBanksConfigVO = bankCardManagerService.selectBankConfigList();
        jsonObject.put("bankcardType", bankcardType);
        jsonObject.put("bankcardProperty", bankcardProperty);
        jsonObject.put("banks", listBanksConfigVO);
        return jsonObject;

    }
    //汇付银行开户銀行卡記錄查询
    @ApiOperation(value = "銀行卡管理", notes = "汇付银行开户銀行卡記錄查询")
    /*@RequestMapping(value = "/bankOpenRecordAccount", method = {RequestMethod.GET, RequestMethod.POST})*/
    @PostMapping(value = "/bankOpenRecordAccount")
    @ResponseBody
    public JSONObject bankOpenRecordAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = null;
        BankCardManagerRequest requestBank =setRequese(map);
        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectBankCardList(requestBank);
        String status="error";
        if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
            jsonObject.put("record",bankcardManagerVOList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    @ApiOperation(value = "銀行卡管理", notes = "江西银行开户銀行卡記錄查询")
/*    @RequestMapping(value = "/bankOpenRecordBankAccount", method = {RequestMethod.GET, RequestMethod.POST})*/
    @PostMapping(value = "/bankOpenRecordBankAccount")
    @ResponseBody
    public JSONObject bankOpenRecordBankAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = null;
        BankCardManagerRequest requestBank =setRequese(map);
        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectNewBankCardList(requestBank);
        String status="error";
        if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
            jsonObject.put("record",bankcardManagerVOList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    private BankCardManagerRequest  setRequese(Map<String,Object> mapParam){
        BankCardManagerRequest requestBank = new BankCardManagerRequest();
        if(null!=mapParam&&mapParam.size()>0){
            if(mapParam.containsKey("userName")){
                requestBank.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("userName")){
                requestBank.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("bank")){
                requestBank.setBank(mapParam.get("bank").toString());
            }
            if(mapParam.containsKey("account")){
                requestBank.setAccount(mapParam.get("account").toString());
            }
            if(mapParam.containsKey("realName")){
                requestBank.setRealName(mapParam.get("realName").toString());
            }
            if(mapParam.containsKey("cardProperty")){
                requestBank.setCardProperty(mapParam.get("cardProperty").toString());
            }
            if(mapParam.containsKey("cardType")){
                requestBank.setCardType(mapParam.get("cardType").toString());
            }
            if(mapParam.containsKey("addTimeStart")){
                requestBank.setAddTimeStart(mapParam.get("addTimeStart").toString());
            }
            if(mapParam.containsKey("addTimeEnd")){
                requestBank.setAddTimeEnd(mapParam.get("addTimeEnd").toString());
            }
            if(mapParam.containsKey("mobile")){
                requestBank.setMobile(mapParam.get("mobile").toString());
            }
            if (mapParam.containsKey("limit")&& StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                requestBank.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return requestBank;
    }

}
