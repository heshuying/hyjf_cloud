package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.HolidaysConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HolidaysConfigService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Api(value = "配置中心节假日配置",tags ="配置中心节假日配置")
@RestController
@RequestMapping("/hyjf-admin/config/holidaysconfig")
public class HolidaysConfigController  extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "instconfig";
    @Autowired
    private HolidaysConfigService holidaysConfigService;

    @ApiOperation(value = "配置中心节假日配置", notes = "查询配置中心节假日配置")
    @RequestMapping("/init")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initHolidaysConfig(@RequestBody HolidaysConfigRequestBean holidaysConfigRequestBean) {
        AdminHolidaysConfigRequest adminRequest= new AdminHolidaysConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(holidaysConfigRequestBean, adminRequest);
        HolidaysConfigResponse response=holidaysConfigService.initHolidaysConfig(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<HolidaysConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "配置中心节假日配置", notes = "节假日配置详情页面")
    @PostMapping("/infoAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult instConfigInfo(@RequestBody HolidaysConfigRequestBean holidaysConfigRequestBean) {
        HolidaysConfigResponse adminResponse=null;
        AdminHolidaysConfigRequest adminRequest= new AdminHolidaysConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(holidaysConfigRequestBean, adminRequest);
        if (StringUtils.isNotEmpty(adminRequest.getIds())) {
            Integer id = Integer.valueOf(adminRequest.getIds());
            adminResponse = this.holidaysConfigService.getHolidaysConfigById(id);
        }else {
            HolidaysConfigVO record = new HolidaysConfigVO();
            String data=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//            record.setStatrTime(data);
//            record.setEndTime(data);
            adminResponse.setResult(record);
        }
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<HolidaysConfigVO>(adminResponse.getResult()) ;
    }
    @ApiOperation(value = "配置中心节假日配置", notes = "节假日配置添加")
    @PostMapping("/insertAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertHolidaysConfig( @RequestBody HolidaysConfigRequestBean holidaysConfigRequestBean) {
        AdminHolidaysConfigRequest adminRequest= new AdminHolidaysConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(holidaysConfigRequestBean, adminRequest);
        ModelAndView modelAndView = new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(modelAndView, adminRequest) != null) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验失败");
        }
        HolidaysConfigResponse adminResponse = holidaysConfigService.saveHolidaysConfig(adminRequest);
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "配置中心节假日配置", notes = "节假日配置修改")
    @PostMapping("/updateAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult updateHolidaysConfig( @RequestBody HolidaysConfigRequestBean holidaysConfigRequestBean) {
        AdminHolidaysConfigRequest adminRequest= new AdminHolidaysConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(holidaysConfigRequestBean, adminRequest);
        ModelAndView modelAndView = new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(modelAndView, adminRequest) != null) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验失败");
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "id", holidaysConfigRequestBean.getId().toString())) {
            // 失败返回
            return new AdminResult<>(FAIL, "id不能为空");
        }
        HolidaysConfigResponse adminResponse = holidaysConfigService.updateHolidaysConfig(adminRequest);
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<>();
    }

    /**
     * 调用校验表单方法
     *
     * @param modelAndView
     * @param form
     * @return
     */
    private ModelAndView validatorFieldCheck(ModelAndView modelAndView, AdminHolidaysConfigRequest form) {
        // 字段校验(非空判断和长度判断)

        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "eventsName", form.getEventsName())) {
            return modelAndView;
        }
/*        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "year", form.getYear())) {
            return modelAndView;
        }*/
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "statrTime", form.getStatrTime())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "endTime", form.getEndTime())) {
            return modelAndView;
        }
        return null;
    }

}
