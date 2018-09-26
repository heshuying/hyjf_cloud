package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SendTypeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowSendTypeResponse;
import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.vo.admin.BorrowSendTypeVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AdminResult insertBorrowSend( @RequestBody BorrowSendTypeRequest adminRequest) {
        AdminResult result= new AdminResult();
        BorrowSendTypeResponse response =new BorrowSendTypeResponse();
        // 表单校验(双表校验)
        // 编号
        boolean sendCdFlag = StringUtils.isNotBlank(adminRequest.getSendCd())&&adminRequest.getSendCd().length()<=50;
        if (!sendCdFlag) {
            return new AdminResult<>(Response.FAIL,"sendCd不能为空且长度小于或等于50！");
        }
        //校验sendCd是否存在
        BorrowSendTypeVO borrowSendTypeVO = sendTypeService.getBorrowSendInfo(adminRequest.getSendCd());
        if (borrowSendTypeVO != null && StringUtils.isNotEmpty(borrowSendTypeVO.getSendCd())) {
            response.setRtn(Response.FAIL);
            response.setMessage("sendCd 重复了！");
            result.setStatus(BaseResult.FAIL);
            result.setStatusDesc("sendCd 重复了！");
            result.setData(response);
            return result ;
        }
        // 画面验证
        String message =this.validatorFieldCheck(adminRequest);
        if (StringUtils.isNotBlank(message)) {
            response.setEnddayMonthList(this.sendTypeService.getParamNameList(CustomConstants.ENDDAY_MONTH));
        }
        // 数据插入
        BorrowSendTypeResponse res =this.sendTypeService.insertBorrowSend(adminRequest);
        if(res==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(res)) {
            return new AdminResult<>(FAIL, res.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "修改 发标/复审", notes = "修改 发标/复审")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult updateBorrowSend( @RequestBody BorrowSendTypeRequest adminRequest) {
        BorrowSendTypeResponse response =new BorrowSendTypeResponse();
        // 表单校验(双表校验)
        if(StringUtils.isBlank(adminRequest.getSendCd())){
            response.setRtn(Response.FAIL);
            response.setMessage("sendCd 不能为空！");
            return new AdminResult<BorrowSendTypeResponse>(response);
        }
        // 画面验证
        String message =this.validatorFieldCheck(adminRequest);
        if (StringUtils.isNotBlank(message)) {
            response.setEnddayMonthList(this.sendTypeService.getParamNameList(CustomConstants.ENDDAY_MONTH));
        }
        // 数据修改
        BorrowSendTypeResponse res =this.sendTypeService.updateBorrowSend(adminRequest);
        if(res==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(res)) {
            return new AdminResult<>(FAIL, res.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "删除 发标/复审", notes = "删除 发标/复审")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult daleteBorrowSend( @RequestBody BorrowSendTypeRequest adminRequest) {
        // 数据修改
        BorrowSendTypeResponse response = this.sendTypeService.daleteBorrowSend(adminRequest.getSendCd());
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "校验发标/复审", notes = "添加 发标/复审")
    @PostMapping("/checkAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult checkAction( @RequestBody BorrowSendTypeRequest adminRequest) {
        AdminResult result= new AdminResult();
        BorrowSendTypeResponse response =new BorrowSendTypeResponse();
        // 表单校验(双表校验)
        // 编号
        boolean sendCdFlag = StringUtils.isNotBlank(adminRequest.getSendCd())&&adminRequest.getSendCd().length()<=50;
        if (!sendCdFlag) {
            return new AdminResult<>(Response.FAIL,"sendCd不能为空且长度小于或等于50！");
        }
        //校验sendCd是否存在
        BorrowSendTypeVO borrowSendTypeVO = sendTypeService.getBorrowSendInfo(adminRequest.getSendCd());
        if (borrowSendTypeVO != null && StringUtils.isNotEmpty(borrowSendTypeVO.getSendCd())) {
            response.setRtn(Response.FAIL);
            response.setMessage("sendCd 重复了！");
            result.setStatus(BaseResult.FAIL);
            result.setStatusDesc("sendCd 重复了！");
            result.setData(response);
            return result ;
        }
        return result;
    }
    /**
     * 画面校验
     *
     * @param form
     */
    private String validatorFieldCheck(BorrowSendTypeRequest form) {
        // 名称
        if(StringUtils.isBlank(form.getSendName())||(StringUtils.isNotBlank(form.getSendName())&&form.getSendName().length()>50)){
            return "sendName 不能为空且长度不能超过50！";
        }
        // 发标时间
        if(StringUtils.isBlank(form.getAfterTime())||(StringUtils.isNotBlank(form.getAfterTime())&&form.getAfterTime().length()>4)){
            String value=form.getAfterTime();
            if(!GenericValidator.isInt(value) || !NumberUtils.isNumber(value) || Integer.valueOf(value) < 0){
                return "afterTime 不能为空且长度小于4位的正整数！";
            }
            return "afterTime 不能为空且长度不能超过4！";
        }
        // 备注说明
        if(StringUtils.isBlank(form.getRemark())||(StringUtils.isNotBlank(form.getRemark())&&form.getRemark().length()>50)){
            return "remark 不能为空且长度不能超过4！";
        }
        return "";
    }

}
