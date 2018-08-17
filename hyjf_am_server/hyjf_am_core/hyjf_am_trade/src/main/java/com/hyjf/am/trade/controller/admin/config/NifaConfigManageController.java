package com.hyjf.am.trade.controller.admin.config;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.admin.NifaContractTemplateAddRequest;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.config.NifaContractTemplateRequest;
import com.hyjf.am.resquest.config.NifaFieldDefinitionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.NifaContractTemplate;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinition;
import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.config.NifaConfigManageService;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
        NifaFieldDefinitionVO nifaFieldDefinitionVO = null;
        if(null!=nifaFieldDefinition) {
            BeanUtils.copyProperties(nifaFieldDefinition, nifaFieldDefinitionVO);
            response.setResult(nifaFieldDefinitionVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 修改互金字段定义
     * @param request
     * @return
     */
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


    /**
     * 添加合同模版约定条款表
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertNifaContractTemplate", method = RequestMethod.POST)
    public Boolean insertNifaContractTemplate(@RequestBody @Valid NifaContractTemplateAddRequest request) {
        logger.info("============添加合同模版约定条款===========参数为"+ JSONObject.toJSONString(request));
        NifaContractTemplate nifaContractTemplate = new NifaContractTemplate();
        BeanUtils.copyProperties(request, nifaContractTemplate);
        return nifaConfigManageService.insertNifaContractTemplate(nifaContractTemplate) > 0 ? true : false;
    }
    /**
     * 修改合同模版约定条款表
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateNifaContractTemplate", method = RequestMethod.POST)
    public Boolean updateNifaContractTemplate(@RequestBody @Valid NifaContractTemplateAddRequest request) {
        logger.info("============修改合同模版约定条款表===========参数为"+ JSONObject.toJSONString(request));
        //根据id查找之前的数据
        NifaContractTemplate nifaFieldDefinition = nifaConfigManageService.selelctNifaContractTemplateById(String.valueOf(request.getId()));
        if(null!=nifaFieldDefinition){
            //将修改的数据赋值到源数据上
            BeanUtils.copyProperties(request,nifaFieldDefinition);
        }
        BeanUtils.copyProperties(request, nifaFieldDefinition);
        return nifaConfigManageService.updateNifaContractTemplate(nifaFieldDefinition) > 0 ? true : false;
    }

    /**
     * 根据id查找合同模版约定条款表
     * @param nifaId
     * @return
     */
    @GetMapping(value = "/selectNifaContractTemplateById/{nifaId}")
    public NifaContractTemplateResponse selectNifaContractTemplateById(@PathVariable String nifaId) {
        logger.info("============根据id查找合同模版约定条款表===========参数为"+ nifaId);
        NifaContractTemplateResponse response = new NifaContractTemplateResponse();
        NifaContractTemplate nifaFieldDefinition = nifaConfigManageService.selelctNifaContractTemplateById(nifaId);
        NifaContractTemplateVO nifaFieldDefinitionVO = null;
        if(null!=nifaFieldDefinition) {
            BeanUtils.copyProperties(nifaFieldDefinition, nifaFieldDefinitionVO);
            response.setResult(nifaFieldDefinitionVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 根据id删除合同模版约定条款表
     * @param nifaId
     * @return
     */
    @GetMapping(value = "/deleteNifaContractTemplateById/{nifaId}")
    public Boolean deleteNifaContractTemplateById(@PathVariable int nifaId) {
        logger.info("============根据id删除合同模版约定条款表===========参数为"+ nifaId);
        return nifaConfigManageService.deleteNifaContractTemplate(nifaId) > 0 ? true : false;
    }
    /**
     * 查找合同模板id
     * @return
     */
    @GetMapping(value = "/selectTempletId")
    public FddTempletResponse selectTempletId(){
        FddTempletResponse response = new FddTempletResponse();
        List<FddTempletCustomize> fddTempletCustomizeList=nifaConfigManageService.selectTempletId();
        List<FddTempletVO> fddTempletVOList = new ArrayList<FddTempletVO>();
        if(null!=fddTempletCustomizeList&&fddTempletCustomizeList.size()>0){
            fddTempletVOList= CommonUtils.convertBeanList(fddTempletCustomizeList,FddTempletVO.class);
            response.setResultList(fddTempletVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 合同模版约定条款表列表显示
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectNifaContractTemplateList", method = RequestMethod.POST)
    public NifaContractTemplateResponse selectNifaContractTemplateList(@RequestBody @Valid NifaContractTemplateRequest request) {
        logger.info("============合同模版约定条款表列表显示===========参数为"+ JSONObject.toJSONString(request));
        NifaContractTemplateResponse response = new NifaContractTemplateResponse();
        int countNifa = nifaConfigManageService.countNifaContractTemplate();
        Paginator paginator = new Paginator(request.getCurrPage(), countNifa,request.getPageSize());
        if(request.getPageSize()==0){
            paginator = new Paginator(request.getCurrPage(), countNifa);
        }
        response.setRecordTotal(countNifa);
        if(countNifa>0){
            List<NifaContractTemplate> listNifa = nifaConfigManageService.selectNifaContractTemplateList(paginator.getOffset(),paginator.getLimit());
            if(CollectionUtils.isNotEmpty(listNifa)){
                List<NifaContractTemplateVO> nifaFieldDefinitionVOList = CommonUtils.convertBeanList(listNifa,NifaContractTemplateVO.class);
                response.setRtn(Response.SUCCESS);
                response.setResultList(nifaFieldDefinitionVOList);
            }
        }
        return response;
    }
}
