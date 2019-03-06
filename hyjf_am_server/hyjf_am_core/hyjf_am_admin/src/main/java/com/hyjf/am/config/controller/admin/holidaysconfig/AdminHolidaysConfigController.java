/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.holidaysconfig;

import com.hyjf.am.config.controller.admin.bank.BankSettingController;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.service.AdminHolidaysConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminHolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.am.vo.config.HolidaysConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dangzw
 * @version AdminHolidaysConfigController, v0.1 2018/11/13 16:40
 */

@RestController
@RequestMapping("/am-config/adminHolidaysConfig")
public class AdminHolidaysConfigController {

    private static final Logger logger = LoggerFactory.getLogger(AdminHolidaysConfigController.class);

    @Autowired
    private AdminHolidaysConfigService adminHolidaysConfig;

    /**
     * 节假日配置-列表查询
     * @param request
     * @return
     */
    @PostMapping("/getRecordList")
    public AdminHolidaysConfigResponse init(@RequestBody @Valid AdminHolidaysConfigRequest request) {
        logger.info(BankSettingController.class.toString(), "startLog -- AM-ADMIN/am-config/adminHolidaysConfig/getRecordList");

        AdminHolidaysConfigResponse response = new AdminHolidaysConfigResponse();
        try {
            // 节假日配置-列表查询
            Integer totalCount = this.adminHolidaysConfig.getTotalCount();
            if (totalCount != null) {
                Paginator paginator = new Paginator(request.getCurrPage(), totalCount, request.getPageSize() == 0 ? 10 : request.getPageSize());
                List<HolidaysConfig> recordList = this.adminHolidaysConfig.getRecordList(new HolidaysConfig(), paginator.getOffset(),
                        paginator.getLimit());
                if(CollectionUtils.isNotEmpty(recordList)){
                    List<HolidaysConfigVO> recordListVO = CommonUtils.convertBeanList(recordList, HolidaysConfigVO.class);
                    response.setResultList(recordListVO);
                }
                response.setPaginator(paginator);
                response.setHolidaysconfigForm(request);
            }
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置数据原子层查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            response.setRtn(Response.FAIL);
            response.setMessage("Admin配置中心-节假日配置数据原子层查询异常！");
            return response;
        }

        logger.info(BankSettingController.class.toString(), "endLog -- AM-ADMIN/am-config/adminHolidaysConfig/getRecordList");
        return response;
    }

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @PostMapping("/getInfoList")
    public AdminHolidaysConfigResponse info(@RequestBody @Valid AdminHolidaysConfigRequest request) {
        logger.info(BankSettingController.class.toString(), "startLog -- AM-ADMIN/am-config/adminHolidaysConfig/getInfoList");

        AdminHolidaysConfigResponse response = new AdminHolidaysConfigResponse();
        try {
            if (StringUtils.isNotEmpty(request.getIds())) {
                Integer id = Integer.valueOf(request.getIds());
                HolidaysConfig record = adminHolidaysConfig.getRecord(id);
                if(record != null){
                    HolidaysConfigVO recordVO = CommonUtils.convertBean(record, HolidaysConfigVO.class);
                    response.setResult(recordVO);
                }
            }else {
                HolidaysConfig record = new HolidaysConfig();
                String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                record.setStatrTime(data);
                record.setEndTime(data);
                if(record != null){
                    HolidaysConfigVO recordVO = CommonUtils.convertBean(record, HolidaysConfigVO.class);
                    response.setResult(recordVO);
                }
            }
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置画面迁移(含有id更新，不含有id添加)数据原子层查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            response.setRtn(Response.FAIL);
            response.setMessage("Admin配置中心-节假日配置画面迁移(含有id更新，不含有id添加)数据原子层查询异常！");
            return response;
        }

        logger.info(BankSettingController.class.toString(), "endLog -- AM-ADMIN/am-config/adminHolidaysConfig/getInfoList");
        return response;
    }

    /**
     * 节假日配置-添加活动信息
     * @param request
     * @return
     */
    @PostMapping("/insertHolidays")
    public AdminHolidaysConfigResponse insertHolidays(@RequestBody @Valid AdminHolidaysConfigRequest request) {
        logger.info(BankSettingController.class.toString(), "startLog -- AM-ADMIN/am-config/adminHolidaysConfig/insertHolidays");

        int result = 0;
        AdminHolidaysConfigResponse response = new AdminHolidaysConfigResponse();

        try {
            //添加活动信息
            result = adminHolidaysConfig.insertHolidays(request);
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置-添加活动信息-数据原子层查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            response.setRtn(Response.FAIL);
            response.setMessage("Admin配置中心-节假日配置-添加活动信息-数据原子层查询异常！");
            return response;
        }

        if(result <= 0 ){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }

        logger.info(BankSettingController.class.toString(), "endLog -- AM-ADMIN/am-config/adminHolidaysConfig/insertHolidays");
        return response;
    }

    /**
     * 节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    @PostMapping("/updateHolidays")
    public AdminHolidaysConfigResponse updateHolidays(@RequestBody @Valid AdminHolidaysConfigRequest request) {
        logger.info(BankSettingController.class.toString(), "startLog -- AM-ADMIN/am-config/adminHolidaysConfig/updateHolidays");

        int result = 0;
        AdminHolidaysConfigResponse response = new AdminHolidaysConfigResponse();
        try {
            //修改活动维护信息
            result = adminHolidaysConfig.updateHolidays(request);
        } catch (Exception e) {
            logger.info("Admin配置中心-节假日配置-修改活动维护信息-数据原子层查询异常！requestParam:{}", request.toString());
            logger.error(e.getMessage());
            response.setRtn(Response.FAIL);
            response.setMessage("Admin配置中心-节假日配置-修改活动维护信息-数据原子层查询异常！");
            return response;
        }

        if(result <= 0 ){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }

        logger.info(BankSettingController.class.toString(), "endLog -- AM-ADMIN/am-config/adminHolidaysConfig/updateHolidays");
        return response;
    }


}
