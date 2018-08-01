package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.service.ParamNameService;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ParamName> getParamNameList(@PathVariable String code){
        return paramNameService.getParamNameList(code);
    }
    /**
     * @return
     */
    @RequestMapping("/getNameCd/{code}")
    public List<ParamName> getNameCd(@PathVariable String code){
        return paramNameService.getNameCd(code);
    }
    /**
     * （条件）列表查询--其他相关字段
     * @return
     */
    @RequestMapping("/selectProjectTypeParamList")
    public List<ParamNameVO>  selectProjectTypeParamList(){
        return paramNameService.selectProjectTypeParamList();
    }

}
