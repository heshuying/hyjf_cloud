/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.ca;

import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.ca.CertificateAuthorityService;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: CertificateAuthorityController, v0.1 2018/7/17 10:05
 */
@Api(value = "法大大CA认证")
@RestController
@RequestMapping("/am-user/certificateauthority")
public class CertificateAuthorityController extends BaseController {

    @Autowired
    private CertificateAuthorityService certificateAuthorityService;

    /**
     * CertificateAuthority更新-法大大CA认证MQ用
     * @auth sunpeikai
     * @param certificateAuthorityVO 更新参数
     * @return
     */
    @ApiOperation(value = "法大大CA认证-更新",notes = "法大大CA认证-更新")
    @PostMapping(value = "/updatecertificateauthority")
    public Integer updateCertificateAuthority(@RequestBody CertificateAuthorityVO certificateAuthorityVO){
        return certificateAuthorityService.updateCertificateAuthority(certificateAuthorityVO);
    }

    /**
     * CertificateAuthority新增-法大大CA认证MQ用
     * @auth sunpeikai
     * @param certificateAuthorityVO 插入参数
     * @return
     */
    @ApiOperation(value = "法大大CA认证-新增数据",notes = "法大大CA认证-新增数据")
    @PostMapping(value = "/insertcertificateauthority")
    public Integer insertCertificateAuthority(@RequestBody CertificateAuthorityVO certificateAuthorityVO){
        return certificateAuthorityService.insertCertificateAuthority(certificateAuthorityVO);
    }
}
