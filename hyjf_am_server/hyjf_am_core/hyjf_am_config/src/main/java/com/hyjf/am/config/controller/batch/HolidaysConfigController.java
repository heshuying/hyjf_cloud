/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.batch;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yaoy
 * @version HolidaysConfigController, v0.1 2018/6/22 10:10
 */
@RestController
@RequestMapping("/am-config/holidaysConfig")
public class HolidaysConfigController extends BaseConfigController {

    @Autowired
    private HolidaysConfigService holidaysConfigService;

    @GetMapping("/selectHolidaysConfig/{orderByClause}")
    public HolidaysConfigResponse selectHolidaysConfig(@PathVariable String orderByClause) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        List<HolidaysConfig> holidaysConfigList = holidaysConfigService.selectHolidaysConfig(orderByClause);
        if (!CollectionUtils.isEmpty(holidaysConfigList)) {
            List<HolidaysConfigVO> holidaysConfigVOList = CommonUtils.convertBeanList(holidaysConfigList,HolidaysConfigVO.class);
            response.setResultList(holidaysConfigVOList);
        }
        return response;
    }

    /**
     * 分页查询节假日配置
     * @param adminRequest
     * @return
     */
    @RequestMapping ("/list")
    public HolidaysConfigResponse selectHolidaysConfigListByPage(@RequestBody AdminHolidaysConfigRequest adminRequest) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        List<HolidaysConfig> holidaysConfigList = holidaysConfigService.selectHolidaysConfigListByPage(new HolidaysConfig(), -1, -1);
        if (!CollectionUtils.isEmpty(holidaysConfigList)) {
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(), holidaysConfigList.size());
            holidaysConfigList = this.holidaysConfigService.selectHolidaysConfigListByPage(new HolidaysConfig(), paginator.getOffset(),
                    paginator.getLimit());
            List<HolidaysConfigVO> holidaysConfigVOList = CommonUtils.convertBeanList(holidaysConfigList,HolidaysConfigVO.class);
            response.setResultList(holidaysConfigVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 查询节假日配置详情页面
     * @param id
     * @return
     */
    @RequestMapping ("/info/{id}")
    public HolidaysConfigResponse selectHolidaysConfigInfo(@PathVariable Integer id) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        HolidaysConfig holidaysConfig = holidaysConfigService.selectHolidaysConfigInfo(id);
        if (null != holidaysConfig) {
            HolidaysConfigVO holidaysConfigVO = CommonUtils.convertBean(holidaysConfig,HolidaysConfigVO.class);
            response.setResult(holidaysConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 添加节假日配置详情页面
     * @param req
     * @return
     */
    @RequestMapping ("/insert")
    public HolidaysConfigResponse insertHolidaysConfigInfo(@RequestBody AdminHolidaysConfigRequest req) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        int result =holidaysConfigService.insertHolidaysConfigInfo(req);
        if(result > 0){
            response.setRtn(Response.SUCCESS);
        }
        response.setRtn(Response.FAIL);
        return response;
    }
    /**
     * 修改节假日配置详情页面
     * @param req
     * @return
     */
    @RequestMapping ("/update")
    public HolidaysConfigResponse updateHolidaysConfigInfo(@RequestBody AdminHolidaysConfigRequest req) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        int result =holidaysConfigService.updateHolidaysConfigInfo(req);
        if(result > 0){
            response.setRtn(Response.SUCCESS);
        }
        response.setRtn(Response.FAIL);
        return response;
    }

}
