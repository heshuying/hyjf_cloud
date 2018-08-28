package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankInterfaceService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.resquest.admin.BankInterfaceRequest;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Api(tags = "配置中心-银行配置--接口切换")
@RestController
@RequestMapping("/hyjf-admin/config/banksetting/bankinterface")
public class BankInterfaceController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "bankinterface";
    @Autowired
    private BankInterfaceService bankInterfaceService;

    @ApiOperation(value = "查询接口切换", notes = "查询接口切换")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult bankInterfaceInit(@RequestBody BankInterfaceRequest adminRequest) {
        BankInterfaceResponse response=bankInterfaceService.bankInterfaceInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<BankInterfaceVO>>(ListResult.build(response.getResultList(), response.getFlag())) ;
    }

    @ApiOperation(value = "查询配置中心接口切换详情页面", notes = "查询配置中心接口切换详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult bankInterfaceInfo(@RequestBody BankInterfaceRequest adminRequest) {
        BankInterfaceResponse response=null;
        if(adminRequest.getId()!= null){
            response=bankInterfaceService.bankInterfaceInfo(adminRequest);
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<BankInterfaceVO>(response.getResult()) ;
    }


    @ApiOperation(value = "接口切换 禁用", notes = "接口切换 禁用")
    @PostMapping("/useAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult updateUseAction(@RequestBody  BankInterfaceRequest adminRequest, HttpServletRequest request)  {
        //todo 联调时候需要放开
//        AdminSystemVO user = getUser(request);
        BankInterfaceVO bankInterface = new BankInterfaceVO();
        BankInterfaceResponse prs =null;
        if(adminRequest.getId() != null){
            bankInterface.setId(adminRequest.getId());
            if(adminRequest.getIsUsable() == 0){
                bankInterface.setIsUsable(1);
            }else {
                bankInterface.setIsUsable(0);
            }
            bankInterface.setUpdateTime(new Date());
            //todo 联调时候需要放开
//            bankInterface.setUpdateUserName(user.getUsername());
//            bankInterface.setUpdateUserId(Integer.valueOf(user.getId()));
            prs = bankInterfaceService.updateBankIntefaceAction(bankInterface);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "快捷充值限额修改", notes = "快捷充值限额修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankRechargeConfig(@RequestBody  BankInterfaceRequest adminRequest, HttpServletRequest request)  {
        //todo 联调时候需要放开
//        AdminSystemVO user = getUser(request);
        BankInterfaceResponse prs = null;
        BankInterfaceVO bankInterfaceVO = new BankInterfaceVO();
        if(adminRequest.getId() != null) {
            BeanUtils.copyProperties(adminRequest,bankInterfaceVO);
            bankInterfaceVO.setUpdateTime(new Date());
            //todo 联调时候需要放开
//           bankInterface.setUpdateUserName(user.getUsername());
//           bankInterface.setUpdateUserId(Integer.valueOf(user.getId()));
            prs = bankInterfaceService.updateBankIntefaceAction(bankInterfaceVO);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "快捷充值限额删除", notes = "快捷充值限额删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBankInterfaceConfig(@RequestBody BankInterfaceRequest adminRequest)  {
        //todo 联调时候需要放开
//        AdminSystemVO user = getUser(request);
        BankInterfaceResponse  response= null;
        BankInterfaceVO bankInterface = new BankInterfaceVO();
        if(adminRequest.getId() != null){
            bankInterface.setIsDelete(1);
            bankInterface.setId(Integer.valueOf(adminRequest.getId()));
            bankInterface.setUpdateTime(new Date());
//        //todo 联调时候需要放开
//           bankInterface.setUpdateUserName(user.getUsername());
//           bankInterface.setUpdateUserId(Integer.valueOf(user.getId()));
            response=bankInterfaceService.deleteBankInterfaceConfig(bankInterface);
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }


}
