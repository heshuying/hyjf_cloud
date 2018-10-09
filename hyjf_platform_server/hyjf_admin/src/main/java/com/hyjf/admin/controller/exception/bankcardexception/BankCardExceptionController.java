/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.bankcardexception;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
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
@Api(value = "异常中心-汇付银行卡异常",tags = "异常中心-汇付银行卡异常")
@RestController
@RequestMapping(value = "/hyjf-admin/exception/bankcardexception")
public class BankCardExceptionController extends BaseController {

    @Autowired
    private BankCardExceptionService bankCardExceptionService;

    private List<BankConfigVO> bankConfigVOList = new ArrayList<>();

    @ApiOperation(value = "银行卡异常列表",notes = "银行卡异常列表")
    @PostMapping(value = "/searchAction")
    public AdminResult<ListResult<AdminBankCardExceptionCustomizeVO>> searchAction(@RequestBody BankCardExceptionRequest request){
        // 数据总数
        Integer count = bankCardExceptionService.getBankCardExceptionCount(request);
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
        return new AdminResult<>(ListResult.build(bankCardExceptionCustomizeVOList,count));
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
        AdminResult adminResult = new AdminResult();
        // 验证
        if (Validator.isNull(request.getUserId())) {
            adminResult.setStatusInfo(FAIL,"空的用户ID");
            return adminResult;
        }
        if (!Validator.isNumber(request.getUserId())) {
            // 无效的用户ID
            adminResult.setStatusInfo(MsgEnum.ERR_OBJECT_INVALID,"用户ID");
            return adminResult;
        }
        // 更新
        String result = bankCardExceptionService.updateAccountBankByUserId(Integer.parseInt(request.getUserId()));
        if (result.equals(ChinaPnrConstant.RESPCODE_SUCCESS)) {
            // 跳转页面用（info里面有）
            adminResult.setStatus(SUCCESS);
        } else {
            adminResult.setStatusInfo(FAIL,result);
            return adminResult;
        }
        return adminResult;
    }

}
