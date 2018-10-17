/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.maintenance;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.service.ParamNameService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.resquest.admin.AdminParamNameRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: ParamNameController, v0.1 2018/9/6 11:28
 */
@RestController
@RequestMapping(value = "/am-admin/paramname")
public class ParamNameController extends BaseConfigController {

    @Autowired
    private ParamNameService paramNameService;

    /**
     * 查询数据字典总数
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getParamNamesCount")
    public ParamNameResponse getParamNamesCount(@RequestBody AdminParamNameRequest request){
        ParamNameResponse response = new ParamNameResponse();
        int count = paramNameService.getParamNamesCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchParamNamesList")
    public ParamNameResponse searchParamNamesList(@RequestBody AdminParamNameRequest request){
        ParamNameResponse response = new ParamNameResponse();
        Integer count = paramNameService.getParamNamesCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchParamNamesList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<ParamName> paramNameList = paramNameService.searchParamNamesList(request);
        logger.debug(JSON.toJSONString(paramNameList));
        if(!CollectionUtils.isEmpty(paramNameList)){
            List<ParamNameVO> paramNameVOList = CommonUtils.convertBeanList(paramNameList,ParamNameVO.class);
            response.setResultList(paramNameVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 检查数据库是否已存在该数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/isExistsParamName")
    public BooleanResponse isExistsParamName(@RequestBody ParamNameVO paramNameVO){
        BooleanResponse response = new BooleanResponse();
        boolean isExists = paramNameService.isExistsParamName(paramNameVO);
        response.setResultBoolean(isExists);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/insertParamName")
    public ParamNameResponse insertParamName(@RequestBody ParamNameVO paramNameVO){
        ParamNameResponse response = new ParamNameResponse();
        int count = paramNameService.insertParamName(paramNameVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 修改数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/updateParamName")
    public ParamNameResponse updateParamName(@RequestBody ParamNameVO paramNameVO){
        ParamNameResponse response = new ParamNameResponse();
        int count = paramNameService.updateParamName(paramNameVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据联合主键查询数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchParamNameByKey")
    public ParamNameResponse searchParamNameByKey(@RequestBody ParamNameVO paramNameVO){
        ParamNameResponse response = new ParamNameResponse();
        ParamName paramName = paramNameService.searchParamNameByKey(paramNameVO);
        if(paramName != null){
            ParamNameVO exist = CommonUtils.convertBean(paramName,ParamNameVO.class);
            response.setResult(exist);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/deleteParamName")
    public ParamNameResponse deleteParamName(@RequestBody ParamNameVO paramNameVO){
        ParamNameResponse response = new ParamNameResponse();
        int count = paramNameService.deleteParamName(paramNameVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 子账户类型 查询
     * @return
     */
    @RequestMapping("/getParamNameList/{code}")
    public ParamNameResponse getParamNameList(@PathVariable String code){
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNames=paramNameService.getParamNameList(code);
        if(!CollectionUtils.isEmpty(paramNames)){
            List<ParamNameVO> paramName= CommonUtils.convertBeanList(paramNames,ParamNameVO.class);
            response.setResultList(paramName);
            return response;
        }
        return response;
    }
    /**
     * @return
     */
    @RequestMapping("/getNameCd/{code}")
    public ParamNameResponse  getNameCd(@PathVariable String code){
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNames=paramNameService.getNameCd(code);
        if(!CollectionUtils.isEmpty(paramNames)){
            List<ParamNameVO> paramName= CommonUtils.convertBeanList(paramNames,ParamNameVO.class);
            response.setResultList(paramName);
            return response;
        }
        return response;
    }
    /**
     * （条件）列表查询--其他相关字段
     * @return
     */
    @RequestMapping("/selectProjectTypeParamList")
    public ParamNameResponse  selectProjectTypeParamList(){
        ParamNameResponse response = new ParamNameResponse();
        List<ParamNameVO> paramNames=paramNameService.selectProjectTypeParamList();
        if(!CollectionUtils.isEmpty(paramNames)){
            response.setResultList(paramNames);
            return response;
        }
        return response;
    }
}
