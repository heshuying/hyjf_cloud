/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.manager.holidaysconfig;

import com.hyjf.admin.beans.request.HolidaysConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.controller.manager.banksetting.BankSettingController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.holidaysconfig.HolidaysConfigService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminHolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dangzw
 * @version HolidaysConfigController, v0.1 2018/11/13 15:53
 */
@Api(tags = "配置中心-节假日配置")
@RestController
@RequestMapping("/hyjf-admin/manager/holidaysconfig")
public class HolidaysConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HolidaysConfigController.class);

    //权限名称
    private static final String PERMISSIONS = "feeconfig";

    @Autowired
    private HolidaysConfigService holidaysConfigService;

    @ApiOperation(value = "节假日配置-列表查询", notes = "节假日配置-列表查询")
    @PostMapping("/init")
    public AdminResult init(@RequestBody HolidaysConfigRequestBean requestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/manager/holidaysconfig/init");

        AdminHolidaysConfigRequest request = new AdminHolidaysConfigRequest();
        AdminHolidaysConfigResponse response;
        BeanUtils.copyProperties(requestBean, request);

        try {
            // 节假日配置-列表查询
            response = holidaysConfigService.getRecordList(request);
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置组合层数据查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            return new AdminResult<>(FAIL, "Admin配置中心-节假日配置组合层数据查询异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/manager/holidaysconfig/init");
        return new AdminResult(response);
    }

    @ApiOperation(value = "节假日配置-画面迁移(含有id更新，不含有id添加)", notes = "节假日配置-画面迁移(含有id更新，不含有id添加)")
    @PostMapping("/infoAction")
    public AdminResult info(@RequestBody HolidaysConfigRequestBean requestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/manager/holidaysconfig/infoAction");

        AdminHolidaysConfigRequest request = new AdminHolidaysConfigRequest();
        AdminHolidaysConfigResponse response;
        BeanUtils.copyProperties(requestBean, request);

        try {
            // 节假日配置-画面迁移(含有id更新，不含有id添加)
            response = holidaysConfigService.getInfoList(request);
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置-画面迁移(含有id更新，不含有id添加)-组合层数据查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            return new AdminResult<>(FAIL, "Admin配置中心-节假日配置-画面迁移(含有id更新，不含有id添加)-组合层数据查询异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/manager/holidaysconfig/infoAction");
        return new AdminResult(response);
    }

    @ApiOperation(value = "节假日配置-添加活动信息", notes = "节假日配置-添加活动信息")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insert(@RequestBody HolidaysConfigRequestBean requestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/manager/holidaysconfig/insertAction");

        AdminHolidaysConfigRequest request = new AdminHolidaysConfigRequest();
        AdminHolidaysConfigResponse response;
        BeanUtils.copyProperties(requestBean, request);

        try {
            // 调用校验
            if (validatorFieldCheck(new ModelAndView(), request) != null) {
                // 失败返回
                return new AdminResult<>(FAIL, "节假日配置-添加活动信息-字段校验失败");
            }

            // 节假日配置-添加活动信息
            response = holidaysConfigService.insert(request);
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置-添加活动信息-组合层数据查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            return new AdminResult<>(FAIL, "Admin配置中心-节假日配置-添加活动信息-组合层数据查询异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/manager/holidaysconfig/insertAction");
        return new AdminResult(response);
    }

    @ApiOperation(value = "节假日配置-修改活动维护信息", notes = "节假日配置-修改活动维护信息")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult update(@RequestBody HolidaysConfigRequestBean requestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/manager/holidaysconfig/updateAction");

        AdminHolidaysConfigRequest request = new AdminHolidaysConfigRequest();
        AdminHolidaysConfigResponse response;
        BeanUtils.copyProperties(requestBean, request);

        try {
            // 调用校验
            if (validatorFieldCheck(new ModelAndView(), request) != null) {
                // 失败返回
                return new AdminResult<>(FAIL, "节假日配置-修改活动信息-字段校验失败");
            }
            // 根据id更新
            if(request.getId() == null){
                return new AdminResult<>(FAIL, "节假日配置-修改活动信息-id字段必传！");
            }
            if (!ValidatorFieldCheckUtil.validateRequired(new ModelAndView(), "id", request.getId().toString())) {
                return new AdminResult<>(FAIL, "节假日配置-修改活动信息-id字段校验失败");
            }

            // 节假日配置-更新活动信息
            response = holidaysConfigService.update(request);
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置-修改活动维护信息-组合层数据查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            return new AdminResult<>(FAIL, "Admin配置中心-节假日配置-修改活动维护信息-组合层数据查询异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/manager/holidaysconfig/updateAction");
        return new AdminResult(response);
    }

    @ApiOperation(value = "节假日配置-保存之前的非空验证", notes = "节假日配置-保存之前的去重验证")
    @PostMapping("/validateBeforeAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult validateBeforeAction(@RequestBody HolidaysConfigRequestBean requestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/manager/holidaysconfig/validateBeforeAction");

        AdminHolidaysConfigRequest request = new AdminHolidaysConfigRequest();
        BeanUtils.copyProperties(requestBean, request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        ModelAndView modelAndView=new ModelAndView();
        // 字段校验(非空判断和长度判断)
        if(StringUtils.isBlank(request.getEventsName())){
            return new AdminResult<>(FAIL, "节日名称必传！");
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "eventsName", request.getEventsName())) {
            return new AdminResult<>(FAIL, "节日名称不能为空");
        }
        if(StringUtils.isBlank(request.getEventsName())){
            return new AdminResult<>(FAIL, "开始时间必传！");
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "statrTime", request.getStatrTime())) {
            return new AdminResult<>(FAIL, "开始时间不能为空");
        }
        if(StringUtils.isBlank(request.getEventsName())){
            return new AdminResult<>(FAIL, "结束时间必传！");
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "endTime", request.getEndTime())) {
            return new AdminResult<>(FAIL, "结束时间不能为空");
        }
        /*
       if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "year", form.getYear())) {
            resultMap.put("success", false);
            resultMap.put("msg", "年份不能为空");
            return resultMap;
        }
        if (form.getEndTime().indexOf(form.getYear())<0||form.getStatrTime().indexOf(form.getYear())<0) {
            resultMap.put("success", false);
            resultMap.put("msg", "请选择当前年份的日期");
            return resultMap;
        }
        */
        Date endTime = createTime(request.getEndTime());
        Date statrTime = createTime(request.getStatrTime());
        if(endTime.getTime()<statrTime.getTime()){
            return new AdminResult<>(FAIL, "开始时间不能小于结束时间");
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/manager/holidaysconfig/validateBeforeAction");
        return new AdminResult();
    }

    private Date createTime(String dateString) {
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {
            date = sim.parse(dateString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return date;
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
