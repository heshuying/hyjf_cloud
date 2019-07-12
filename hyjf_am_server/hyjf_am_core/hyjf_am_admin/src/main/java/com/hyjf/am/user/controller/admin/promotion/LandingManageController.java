package com.hyjf.am.user.controller.admin.promotion;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.user.LandingManagerRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.TemplateConfig;
import com.hyjf.am.user.service.admin.promotion.LandingManagerService;
import com.hyjf.am.vo.user.TemplateConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author walter.nxl
 * @version LandingManageController, v0.1 2019/6/18 11:17
 */
@RestController
@RequestMapping("/am-user/landing")
public class LandingManageController extends BaseController {

    @Autowired
    private LandingManagerService landingManagerService;


    /**
     * 获取着陆页列表
     *
     * @param request
     * @return
     */
    @PostMapping("/selectTempConfigList")
    public TemplateConfigResponse selectTempConfigList(@RequestBody @Valid LandingManagerRequest request) {
        logger.info("---selectTempConfigList by param---  " + JSONObject.toJSON(request));
        TemplateConfigResponse response = landingManagerService.selectTempList(request);
        return response;
    }

    /**
     * 根据id查找着陆页配置
     *
     * @param id
     * @return
     */
    @GetMapping("/selectTemplateById/{id}")
    public TemplateConfigResponse selectTemplateById(@PathVariable Integer id) {
        TemplateConfigResponse response = new TemplateConfigResponse();
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        TemplateConfig config = landingManagerService.selectTemplateById(id);
        if (null != config) {
            TemplateConfigVO templateConfigVO = new TemplateConfigVO();
            BeanUtils.copyProperties(config, templateConfigVO);
            response.setResult(templateConfigVO);
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return response;
    }

    /**
     * 保存着陆页模板配置
     *
     * @param request
     * @return
     */
    @PostMapping("/insertTemplate")
    public IntegerResponse insertTemplate(@RequestBody @Valid LandingManagerRequest request) {
        logger.info("---insertTemplate by param---  " + JSONObject.toJSON(request));
        TemplateConfig templateConfig = new TemplateConfig();
        BeanUtils.copyProperties(request, templateConfig);
        templateConfig.setCreateTime(new Date());
        templateConfig.setUpdateTime(new Date());
        templateConfig.setCreateUserId(Integer.parseInt(request.getLoginUserId()));
        templateConfig.setUpdateUserId(Integer.parseInt(request.getLoginUserId()));
        int insertFlg = landingManagerService.insertTemplate(templateConfig);
        return new IntegerResponse(insertFlg);
    }

    /**
     * 修改着陆页模板配置
     *
     * @param request
     * @return
     */
    @PostMapping("/updateOrInsertTemplate")
    public IntegerResponse updateOrInsertTemplate(@RequestBody @Valid LandingManagerRequest request) {
        logger.info("---updateTemplate by param---  " + JSONObject.toJSON(request));
        TemplateConfig templateConfig = new TemplateConfig();
        int intFlg =0;
        BeanUtils.copyProperties(request, templateConfig);
        templateConfig.setUpdateTime(new Date());
        templateConfig.setUpdateUserId(Integer.parseInt(request.getLoginUserId()));
        if (request.getId() != null) {
            intFlg = landingManagerService.updateTemplate(templateConfig);
        } else {
            templateConfig.setCreateTime(new Date());
            templateConfig.setCreateUserId(Integer.parseInt(request.getLoginUserId()));
            intFlg = landingManagerService.insertTemplate(templateConfig);
        }
        return new IntegerResponse(intFlg);
    }

    @GetMapping("/deleteTemplate/{id}")
    public TemplateConfigResponse deleteTemplate(@PathVariable Integer id) {
        logger.info("---deleteTemplate by param---  " + JSONObject.toJSON(id));
        TemplateConfigResponse response = new TemplateConfigResponse();
        int count=landingManagerService.deleteTemplateCount(id);
        if(count>0) {
        	response.setRtn(UtmResponse.FAIL);
        	response.setMessage("该模板已关联渠道，暂不可删除");
        	return response;
        }
        try {
            int intDelete = landingManagerService.deleteTemplate(id);
            response.setRtn(UtmResponse.SUCCESS);
        } catch (Exception e) {
            response.setRtn(UtmResponse.FAIL);
            response.setMessage("删除失败");
        }
        return response;
    }
}
