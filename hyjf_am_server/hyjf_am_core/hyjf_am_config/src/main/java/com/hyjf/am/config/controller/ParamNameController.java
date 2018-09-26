package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.service.ParamNameService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@RestController
@RequestMapping("/am-config/accountconfig")
public class ParamNameController extends BaseConfigController {
    @Autowired
    private ParamNameService paramNameService;

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
        response.setRtn(Response.FAIL);
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
        response.setRtn(Response.FAIL);
        return response;
    }

}
