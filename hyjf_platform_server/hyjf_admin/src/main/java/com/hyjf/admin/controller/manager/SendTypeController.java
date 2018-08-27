package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SendTypeService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowSendTypeResponse;
import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.vo.admin.BorrowSendTypeVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author by xiehuili on 2018/8/1.
 */
@Api(tags = "配置中心-借款项目配置--发标/复审")
@RestController
@RequestMapping("/hyjf-admin/config/sendtype")
public class SendTypeController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "sendtype";

    @Autowired
    private SendTypeService sendTypeService;
    @ApiOperation(value = "查询发标/复审", notes = "查询发标/复审")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult borrowSendInit(@RequestBody BorrowSendTypeRequest adminRequest) {
        BorrowSendTypeResponse resList=sendTypeService.selectBorrowSendList(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());

        }
        return new AdminResult<ListResult<BorrowSendTypeVO>>(ListResult.build(resList.getResultList(), resList.getCount())) ;
    }

    @ApiOperation(value = "查询发标/复审详情", notes = "查询发标/复审详情")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult borrowSendInfo(@RequestBody BorrowSendTypeRequest adminRequest) {
        BorrowSendTypeResponse resList= new BorrowSendTypeResponse();
        if (StringUtils.isNotEmpty(adminRequest.getSendCd())) {
            BorrowSendTypeVO borrowSendTypeVO = sendTypeService.getBorrowSendInfo(adminRequest.getSendCd());
            if(borrowSendTypeVO != null){
                borrowSendTypeVO.setModifyFlag("M");
                resList.setResult(borrowSendTypeVO);
                return new AdminResult<BorrowSendTypeVO>(resList.getResult()) ;
            }
            return new AdminResult<>(FAIL,adminRequest.getSendCd()+"对应的标的不存在");
        }
        return new AdminResult<>(FAIL,adminRequest.getSendCd()+"对应的标的不存在");
    }
    @ApiOperation(value = "添加 发标/复审", notes = "添加 发标/复审")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public BorrowSendTypeResponse insertBorrowSend( @RequestBody BorrowSendTypeRequest adminRequest) {
        BorrowSendTypeResponse response =new BorrowSendTypeResponse();
        // 表单校验(双表校验)
        ModelAndView modelAndView =new ModelAndView();
        // 编号
        boolean sendCdFlag = ValidatorFieldCheckUtil.validateAlphaAndMaxLength(modelAndView, "sendCd", adminRequest.getSendCd(), 50, true);
        if (sendCdFlag) {
            BorrowSendTypeVO borrowSendTypeVO = sendTypeService.getBorrowSendInfo(adminRequest.getSendCd());
            if (borrowSendTypeVO != null && StringUtils.isNotEmpty(borrowSendTypeVO.getSendCd())) {
                ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "sendCd", "repeat");
            }
        }
        // 画面验证
        this.validatorFieldCheck(modelAndView, adminRequest);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
            response.setEnddayMonthList(this.sendTypeService.getParamNameList(CustomConstants.ENDDAY_MONTH));
        }
        // 数据插入
        BorrowSendTypeResponse res =this.sendTypeService.insertBorrowSend(adminRequest);
        if (!Response.isSuccess(res)) {
            response.setRtn(Response.FAIL);
            return response;
        }
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @ApiOperation(value = "修改 发标/复审", notes = "修改 发标/复审")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public BorrowSendTypeResponse updateBorrowSend( @RequestBody BorrowSendTypeRequest adminRequest) {
        BorrowSendTypeResponse response =new BorrowSendTypeResponse();
        // 表单校验(双表校验)
        ModelAndView modelAndView =new ModelAndView();
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "sendCd", adminRequest.getSendCd());
        // 画面验证
        this.validatorFieldCheck(modelAndView, adminRequest);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
            response.setEnddayMonthList(this.sendTypeService.getParamNameList(CustomConstants.ENDDAY_MONTH));
        }
        // 数据修改
        BorrowSendTypeResponse res =this.sendTypeService.updateBorrowSend(adminRequest);
        if (!Response.isSuccess(res)) {
            response.setRtn(Response.FAIL);
            return response;
        }
        response.setRtn(Response.SUCCESS);
        return response;
    }


    @ApiOperation(value = "删除 发标/复审", notes = "删除 发标/复审")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public BorrowSendTypeResponse daleteBorrowSend( @RequestBody BorrowSendTypeRequest adminRequest) {
        // 数据修改
        BorrowSendTypeResponse response = this.sendTypeService.daleteBorrowSend(adminRequest.getSendCd());
        if (Response.isSuccess(response)) {
            response.setRedirectUrl("redirect:/config/sendtype/init" );
            response.setRtn(Response.SUCCESS);
            return response;
        }
        response.setRtn(Response.FAIL);
        return response;
    }
    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, BorrowSendTypeRequest form) {
        // 名称
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "sendName", form.getSendName(), 50, true);
        // 发标时间
        ValidatorFieldCheckUtil.validateSignlessNum(modelAndView, "afterTime", form.getAfterTime(), 4, true);
        // 备注说明
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "remark", form.getRemark(), 50, true);

    }

}
