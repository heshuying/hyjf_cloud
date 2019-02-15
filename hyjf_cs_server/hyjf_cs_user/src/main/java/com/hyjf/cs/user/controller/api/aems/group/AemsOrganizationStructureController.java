package com.hyjf.cs.user.controller.api.aems.group;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.OrganizationStructureVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AemsOrganizationStructureBean;
import com.hyjf.cs.user.bean.OrganizationStructureRequestBean;
import com.hyjf.cs.user.bean.ResultApiBean;
import com.hyjf.cs.user.service.aems.group.AemsOrganizationStructureService;
import com.hyjf.cs.user.service.group.ApiOrganizationStructureService;
import com.hyjf.cs.user.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @version AemsOrganizationStructureController, v0.1 2018/12/6 19:07
 * @Author: Zha Daojian
 */

@Api(value = "api端-AEMS集团组织机构查询",tags = "api端-AEMS集团组织机构查询")
@RestController
@RequestMapping("/hyjf-api/aems/company")
public class AemsOrganizationStructureController extends BaseController {

    @Autowired
    AemsOrganizationStructureService service;

    /**
     * 集团组织机构查询
     * @author Zha Daojian
     * @date 2018/12/7 15:18
     * @param bean
     * @return ResultApiBean
     **/
    @ApiOperation(value = "集团组织机构查询", notes = "集团组织机构查询")
    @PostMapping(value = "/syncCompanyInfo.do", produces = "application/json; charset=utf-8")
    public ResultApiBean<List<OrganizationStructureVO>> queryInfo(@RequestBody @Valid AemsOrganizationStructureBean bean){
        logger.info("bean:{}", JSONObject.toJSONString(bean));

        // 验签
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(bean, "/aems/company/syncCompanyInfo"), MsgEnum.ERR_SIGN);

        // 返回查询结果
        return new ResultApiBean<List<OrganizationStructureVO>>(service.searchOrganizationList(bean));
    }
}
