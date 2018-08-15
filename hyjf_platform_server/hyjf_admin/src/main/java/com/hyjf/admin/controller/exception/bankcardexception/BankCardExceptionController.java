/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.bankcardexception;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankCardExceptionService;
import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionController, v0.1 2018/8/14 9:56
 */
@Api(value = "江西银行卡异常",tags = "江西银行卡异常")
@RestController
@RequestMapping(value = "/hyjf-admin/exception/bankcardexception")
public class BankCardExceptionController extends BaseController {

    @Autowired
    private BankCardExceptionService bankCardExceptionService;

    private List<BankConfigVO> bankConfigVOList = new ArrayList<>();

    @ApiOperation(value = "银行卡异常列表",notes = "银行卡异常列表")
    @PostMapping(value = "/searchAction")
    public AdminResult searchAction(@RequestBody BankCardExceptionRequest request){
        Map<String,Object> map = new HashMap<>();
        // 数据总数
        Integer count = bankCardExceptionService.getBankCardExceptionCount(request);
        map.put("count",count);
        // 异常列表list
        List<AdminBankCardExceptionCustomizeVO> bankCardExceptionCustomizeVOList = bankCardExceptionService.searchBankCardExceptionList(request);
        // 如果银行卡配置信息为空，重新获取
        if(bankConfigVOList.size() == 0){
            bankConfigVOList = bankCardExceptionService.searchBankConfig();
        }
        // 设置银行卡Name。原因：原来的name设置是在自定义sql中写的，跨库了。所以拿到这里处理
        for(AdminBankCardExceptionCustomizeVO bankCardExceptionCustomizeVO:bankCardExceptionCustomizeVOList){
            for(BankConfigVO bankConfigVO:bankConfigVOList){
                String bankCode = bankCardExceptionCustomizeVO.getBankcode();
                String code = bankConfigVO.getCode();
                // 如果列表对象的bankcode 不为空就补全银行name，否则break
                if(bankCode != null){
                    // 如果配置表的code不为空且bankCode=code就补全银行name，否则continue
                    if(code != null && bankCode.equals(code)){
                        bankCardExceptionCustomizeVO.setBank(bankConfigVO.getName());
                        break;
                    }
                }else{
                    break;
                }

            }
        }
        map.put("bankCardExceptionCustomizeVOList",bankCardExceptionCustomizeVOList);
        return new AdminResult(map);
    }

    @ApiOperation(value = "银行卡配置下拉框",notes = "银行卡配置下拉框")
    @GetMapping(value = "/searchbankconfig")
    public AdminResult searchBankConfig(){
        List<Map<String,String>> resultList = new ArrayList<>();
        this.bankConfigVOList = bankCardExceptionService.searchBankConfig();
        for(BankConfigVO bankConfigVO:bankConfigVOList){
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("key",bankConfigVO.getCode());
            resultMap.put("value",bankConfigVO.getName());
            resultList.add(resultMap);
        }
        return new AdminResult(resultList);
    }

    @ApiOperation(value = "更新银行卡，参数就一个userId",notes = "更新银行卡，参数就一个userId")
    @PostMapping(value = "/updateBankCardExceptionAction")
    public AdminResult updateBankCardExceptionAction(@RequestBody BankCardExceptionRequest request){
        // 验证
/*        if (Validator.isNull(request.getUserId())) {
            modelAndView.getModel().put(ContentArticleDefine.SUCCESS, false);
            modelAndView.getModel().put(ContentArticleDefine.JSON_VALID_INFO_KEY, "空的用户ID");
            return new AdminResult(MsgEnum);
        }
        if (!Validator.isNumber(request.getUserId())) {
            modelAndView.getModel().put(ContentArticleDefine.SUCCESS, false);
            modelAndView.getModel().put(ContentArticleDefine.JSON_VALID_INFO_KEY, "无效的用户ID");
        }
        // 更新
        String result = this.bankCardService.updateAccountBankByUserId(Integer.parseInt(form.getUserId()));
        if (result.equals(ChinaPnrConstant.RESPCODE_SUCCESS)) {
            // 跳转页面用（info里面有）
            modelAndView.getModel().put(ContentArticleDefine.SUCCESS, true);
        } else {
            modelAndView.getModel().put(ContentArticleDefine.SUCCESS, false);
            modelAndView.getModel().put(ContentArticleDefine.JSON_VALID_INFO_KEY, result);
        }*/
        return new AdminResult();
    }

}
