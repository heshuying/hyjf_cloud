package com.hyjf.admin.controller.promotion;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.promotion.UtmService;
import com.hyjf.am.response.admin.promotion.UtmResultResponse;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Api(tags ="推广中心-渠道管理列表")
@RestController
@RequestMapping("/hyjf-admin/promotion/utm")
public class UtmController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UtmController.class);
    /** 查看权限 */
    public static final String PERMISSIONS = "utm";

    @Autowired
    private UtmService utmService;

    @ApiOperation(value = "页面初始化", notes = "渠道列表")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public UtmResultResponse utmListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        UtmResultResponse utmResultResponse = new UtmResultResponse();
        utmResultResponse = utmService.getByPageList(map,Integer.parseInt(getCurrPage(map)),Integer.parseInt(getPageSize(map)));
        return utmResultResponse;
    }

    @ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", notes = "画面迁移(含有id更新，不含有id添加)")
    @PostMapping("/infoaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD,ShiroConstants.PERMISSION_MODIFY})
    public UtmResultResponse info(HttpServletRequest request, HttpServletResponse response, @RequestBody UtmPlatVO utmPlatVO){
        UtmResultResponse adminResult = new UtmResultResponse();
        if (StringUtils.isNotEmpty(utmPlatVO.getId()+"")) {
            UtmPlatVO record = utmService.getDataById(utmPlatVO.getId());
            adminResult.setData(record);
        }
        return adminResult;
    }

    @ApiOperation(value = "添加或修改信息", notes = "添加或修改信息")
    @PostMapping("/insertorupdateaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD,ShiroConstants.PERMISSION_MODIFY})
    public UtmResultResponse insertAction(HttpServletRequest request, HttpServletResponse response, @RequestBody UtmPlatVO utmPlatVO){
        UtmResultResponse adminResult = new UtmResultResponse();
        //根据utmId判断，如存在，则为修改，如不存在，则为新增
        ModelAndView modelAndView = new ModelAndView();
        validatorFieldCheck(modelAndView,utmPlatVO);
        boolean flag = utmService.insertOrUpdateUtmPlat(utmPlatVO);
        if(!flag){
            adminResult.setStatus(AdminResult.FAIL);
            adminResult.setStatusDesc("系统异常，请联系管理员!");
        }
        return adminResult;
    }

    @ApiOperation(value = "删除信息", notes = "删除信息")
    @PostMapping("/deleteaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public UtmResultResponse deleteAction(HttpServletRequest request, HttpServletResponse response, @RequestBody UtmPlatVO utmPlatVO){
        UtmResultResponse adminResult = new UtmResultResponse();
        //根据utmId判断，如存在，则为修改，如不存在，则为新增
        if(StringUtils.isNotEmpty(utmPlatVO.getId()+"")){
            boolean flag = utmService.deleteUtmPlatAction(utmPlatVO);
            if(!flag){
                adminResult.setStatus(AdminResult.FAIL);
                adminResult.setStatusDesc("系统异常，请联系管理员!");
            }
        }else{
            adminResult.setStatus(AdminResult.FAIL);
            adminResult.setStatusDesc("删除异常！");
        }
        return adminResult;
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, UtmPlatVO form) {
        String sourseId = String.valueOf(form.getSourceId());
        CheckUtil.check(StringUtils.isNotBlank(sourseId), MsgEnum.ERR_OBJECT_REQUIRED,"渠道编号");
        CheckUtil.check(sourseId!=null&&sourseId.length()<=10, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"渠道编号");
        //CheckUtil.check(sourseId!=null&&(!GenericValidator.isInt(sourseId) || !NumberUtils.isNumber(sourseId) || Integer.valueOf(sourseId) < 0), MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"渠道编号");
        // 渠道编号
       //boolean flag1 = ValidatorFieldCheckUtil.validateSignlessNum(modelAndView, "sourceId", form.getSourceId()+"", 10, true);
        // 名称
        CheckUtil.check(StringUtils.isNotBlank(form.getSourceName()), MsgEnum.ERR_OBJECT_REQUIRED,"渠道");
        CheckUtil.check(form.getSourceName()!=null&&form.getSourceName().length()<=50, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"渠道");
       // boolean flag2 = ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "sourceName", form.getSourceName(), 50, true);
        // 发标时间
        CheckUtil.check(StringUtils.isNotBlank(String.valueOf(form.getDelFlag())), MsgEnum.ERR_OBJECT_REQUIRED,"状态");
        //ValidatorFieldCheckUtil.validateRequired(modelAndView, "delFlag", form.getDelFlag()+"");
        // 备注说明

        CheckUtil.check(form.getRemark()==null||form.getRemark().length()<=225, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"说明");

        //ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "remark", form.getRemark(), 225, false);

        int record = utmService.sourceNameIsExists(form.getSourceName(), form.getSourceId());
        if (record == 1) {
            CheckUtil.check(false,MsgEnum.NAME_REPEAT,"渠道名称");
            //ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "sourceName", "repeat");
        }
    }

    /**
     * 检查编号唯一性
     * @param request
     * @param response
     * @param form
     * @return
     */
    @ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
    @PostMapping("/checkAction")
    public UtmResultResponse checkAction(HttpServletRequest request, HttpServletResponse response, @RequestBody UtmPlatVO form){
        UtmResultResponse ret = new UtmResultResponse();
        int record = utmService.sourceIdIsExists(form.getSourceId());
        if (record == 1) {
            ret.setStatus(AdminResult.FAIL);
            ret.setStatusDesc(AdminResult.FAIL_DESC);
        }else{
            ret.setStatus(AdminResult.SUCCESS);
            ret.setStatusDesc(AdminResult.SUCCESS_DESC);
        }
        return ret;
    }
}
