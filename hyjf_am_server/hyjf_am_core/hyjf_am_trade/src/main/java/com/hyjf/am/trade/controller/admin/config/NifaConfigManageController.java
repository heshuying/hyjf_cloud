package com.hyjf.am.trade.controller.admin.config;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.user.NifaFieldDefinitionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinition;
import com.hyjf.am.trade.service.admin.config.NifaConfigManageService;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nxl
 * @version NifaConfigManageController, v0.1 2018/8/15 17:38
 */
@RestController
@RequestMapping("/am-trade/nifaConfig")
public class NifaConfigManageController extends BaseController {
    @Autowired
    private NifaConfigManageService nifaConfigManageService;
    /**
     * 互金字段列表显示
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectFieldDefinitionList", method = RequestMethod.POST)
    public NifaFieldDefinitionResponse selectFieldDefinitionList(@RequestBody @Valid NifaFieldDefinitionRequest request) {
        logger.info("============互金字段列表显示===========参数为"+ JSONObject.toJSONString(request));
        NifaFieldDefinitionResponse response = new NifaFieldDefinitionResponse();
        int countNifa = nifaConfigManageService.countNifaFieldDefinition();
        Paginator paginator = new Paginator(request.getCurrPage(), countNifa,request.getPageSize());
        if(request.getPageSize()==0){
            paginator = new Paginator(request.getCurrPage(), countNifa);
        }
        response.setRecordTotal(countNifa);
        if(countNifa>0){
            List<NifaFieldDefinition> listNifa = nifaConfigManageService.selectNifaFieldDefinition(paginator.getOffset(),paginator.getLimit());
            if(CollectionUtils.isNotEmpty(listNifa)){
                List<NifaFieldDefinitionVO> nifaFieldDefinitionVOList = CommonUtils.convertBeanList(listNifa,NifaFieldDefinitionVO.class);
                response.setRtn(Response.SUCCESS);
                response.setResultList(nifaFieldDefinitionVOList);
            }
        }
        return response;
    }

    /**
     * 添加互金字段定义
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertNifaFieldDefinition", method = RequestMethod.POST)
    public Boolean insertNifaFieldDefinition(@RequestBody @Valid NifaFieldDefinitionAddRequest request) {
        logger.info("============添加互金字段定义===========参数为"+ JSONObject.toJSONString(request));
        NifaFieldDefinition nifaFieldDefinition = new NifaFieldDefinition();
        BeanUtils.copyProperties(request, nifaFieldDefinition);
        return nifaConfigManageService.insertNifaFieldDefinition(nifaFieldDefinition) > 0 ? true : false;
    }

    /**
     * 根据id查找互金定义
     * @param nifaId
     * @return
     */
    @GetMapping(value = "/selectFieldDefinitionById/{nifaId}")
    public NifaFieldDefinitionResponse selectFieldDefinitionById(@PathVariable String nifaId) {
        logger.info("============根据id查找互金定义===========参数为"+ nifaId);
        NifaFieldDefinitionResponse response = new NifaFieldDefinitionResponse();
        NifaFieldDefinition nifaFieldDefinition = nifaConfigManageService.selectFieldDefinitionById(nifaId);
        NifaFieldDefinitionVO nifaFieldDefinitionVO = new NifaFieldDefinitionVO();
        if(null!=nifaFieldDefinition) {
            BeanUtils.copyProperties(nifaFieldDefinition, nifaFieldDefinitionVO);
            response.setResult(nifaFieldDefinitionVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @RequestMapping(value = "/updateNifaFieldDefinition", method = RequestMethod.POST)
    public Boolean updateNifaFieldDefinition(@RequestBody @Valid NifaFieldDefinitionAddRequest request) {
        logger.info("============修改互金字段定义===========参数为"+ JSONObject.toJSONString(request));
        //根据id查找之前的数据
        NifaFieldDefinition nifaFieldDefinition = nifaConfigManageService.selectFieldDefinitionById(String.valueOf(request.getId()));
        if(null!=nifaFieldDefinition){
            //将修改的数据赋值到源数据上
            BeanUtils.copyProperties(request,nifaFieldDefinition);
        }
        BeanUtils.copyProperties(request, nifaFieldDefinition);
        return nifaConfigManageService.updateNifaFieldDefinition(nifaFieldDefinition) > 0 ? true : false;
    }

}
