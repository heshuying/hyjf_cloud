/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.maintenance;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.maintenance.AdminParamNamesService;
import com.hyjf.am.resquest.admin.AdminParamNameRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminParamNamesController, v0.1 2018/9/6 11:01
 */
@Api(value = "系统中心-数据字典",tags = "系统中心-数据字典")
@RestController
@RequestMapping(value = "/hyjf-admin/maintenance/paramname")
public class AdminParamNamesController extends BaseController {

    @Autowired
    private AdminParamNamesService adminParamNamesService;

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "查询数据字典",notes = "查询数据字典")
    @PostMapping(value = "/searchparamnames")
    public AdminResult<ListResult<ParamNameVO>> searchParamNames(@RequestBody AdminParamNameRequest adminParamNameRequest){
        Integer count = adminParamNamesService.getParamNamesCount(adminParamNameRequest);
        count = (count == null)?0:count;
        List<ParamNameVO> paramNameVOList = adminParamNamesService.searchParamNamesList(adminParamNameRequest);
        return new AdminResult<>(ListResult.build(paramNameVOList,count));
    }

    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "添加数据字典",notes = "添加数据字典")
    @PostMapping(value = "/addparamname")
    public AdminResult addParamName(@RequestBody ParamNameVO paramNameVO){
        boolean isSuccess = adminParamNamesService.insertParamName(paramNameVO);
        if(isSuccess){
            return new AdminResult(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            return new AdminResult(BaseResult.FAIL,BaseResult.FAIL_DESC);
        }

    }

    /**
     * 修改数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "修改数据字典",notes = "修改数据字典")
    @PostMapping(value = "/updateparamname")
    public AdminResult updateParamName(@RequestBody ParamNameVO paramNameVO){
        boolean isSuccess = adminParamNamesService.updateParamName(paramNameVO);
        if(isSuccess){
            return new AdminResult(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            return new AdminResult(BaseResult.FAIL,BaseResult.FAIL_DESC);
        }
    }

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "删除数据字典",notes = "删除数据字典")
    @PostMapping(value = "/deleteparamname")
    public AdminResult deleteParamName(@RequestBody ParamNameVO paramNameVO){
        boolean isSuccess = adminParamNamesService.deleteParamName(paramNameVO);
        if(isSuccess){
            return new AdminResult(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            return new AdminResult(BaseResult.FAIL,BaseResult.FAIL_DESC);
        }
    }

    /**
     * 根据联合主键查询一条数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据联合主键查询一条数据",notes = "根据联合主键查询一条数据")
    @PostMapping(value = "/selectparamname")
    public AdminResult selectParamName(@RequestBody ParamNameVO paramNameVO){
        ParamNameVO result = adminParamNamesService.selectParamName(paramNameVO);
        if(result == null){
            throw new ReturnMessageException(MsgEnum.ERR_DATA_NOT_EXISTS);
        }
        return new AdminResult(result);
    }
}
