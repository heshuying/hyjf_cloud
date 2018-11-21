package com.hyjf.admin.controller.productcenter.borrow.applyagreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ApplyAgreementService;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.resquest.admin.DownloadAgreementRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version ApplyAgreementController, v0.1 2018/8/9 16:36
 * @Author: Zha Daojian
 */

@Api(value = "Admin端产品中心-汇转让-垫付协议管理",tags="Admin端产品中心-汇转让-垫付协议管理")
@RestController
@RequestMapping("/hyjf-admin/applyagreement")
public class ApplyAgreementController  extends BaseController {

    @Autowired
    private ApplyAgreementService applyAgreementService;


    @ApiOperation(value = "垫付协议申请列表", notes = "垫付协议申请列表")
    @PostMapping("/getList")
    public AdminResult  getApplyAgreementList(@RequestBody ApplyAgreementRequest request){
        AdminResult result = applyAgreementService.getApplyAgreementList(request);
        return result;
    }

    @ApiOperation(value = "垫付协议申请明细列表页", notes = "垫付协议申请明细列表页")
    @PostMapping("/addListDetail")
    public AdminResult getAddApplyAgreementListDetail(@RequestBody BorrowRepayAgreementRequest request){
        AdminResult result =  applyAgreementService.getAddApplyAgreementListDetail(request);
        return result;
    }

    @ApiOperation(value = "批量生成垫付债转协议", notes = "批量生成垫付债转协议")
    @PostMapping("/generateContract")
    public AdminResult generateContract(HttpServletRequest request, @RequestBody BorrowRepayAgreementRequest borrowRepayAgreementRequest){
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        AdminResult result =  applyAgreementService.generateContract(borrowRepayAgreementRequest,currUser);
        return result;
    }
    @ApiOperation(value = "垫付债转协议PDF文件签署", notes = "垫付债转协议PDF文件签署")
    @PostMapping("/pdfSignAction")
    public AdminResult pdfSignAction(HttpServletRequest request, @RequestBody BorrowRepayAgreementRequest borrowRepayAgreementRequest){
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        AdminResult result =  applyAgreementService.generateContract(borrowRepayAgreementRequest,currUser);
        return result;
    }
    @ApiOperation(value = "下載垫付机构协议", notes = "下載垫付机构协议")
    @PostMapping("/downloadAction")
    public void downloadAction(@RequestBody DownloadAgreementRequest requestBean,HttpServletResponse response){
        logger.info("------------------------------下載垫付机构协议requestBean:"+JSONObject.toJSON(requestBean));
        applyAgreementService.downloadAction(requestBean,response);
    }
}
