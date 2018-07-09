package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.CertificateAuthorityExceptionBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CertificateAuthorityExceptionService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.MspApplyVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.validator.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * CA认证异常
 *
 * @author dongzeshan
 */
@Api(value = "安融反欺诈")
@RestController
@RequestMapping("/hyjf-admin/certificate")
public class CertificateAuthorityExceptionController extends BaseController {

	private static final String PERMISSIONS = "CAException";
	
    Logger _log = LoggerFactory.getLogger(CertificateAuthorityExceptionController.class);

    @Autowired
    private CertificateAuthorityExceptionService certificateAuthorityExceptionService;

    /**
     * 画面初始化
     *
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/search")
	@ApiOperation(value = "CA认证记录列表", notes = "CA认证记录列表")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CertificateAuthorityVO>> init(@RequestBody CertificateAuthorityExceptionBean certificateAuthorityExceptionBean) {
    	CertificateAuthorityRequest aprlr = new CertificateAuthorityRequest();
		// 可以直接使用
		BeanUtils.copyProperties(certificateAuthorityExceptionBean, aprlr);

		CertificateAuthorityResponse prs = certificateAuthorityExceptionService.getRecordList(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<ListResult<CertificateAuthorityVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal()));
    }


    /**
     * 异常处理更新Action
     *
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/modifyAction")
 	@ApiOperation(value = "CA认证更新", notes = "CA认证更新")
 	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult modifyAction(@RequestBody CertificateAuthorityExceptionBean certificateAuthorityExceptionBean) {

       // 发送CA认证MQ
    	CertificateAuthorityResponse prs = certificateAuthorityExceptionService.updateUserCAMQ(certificateAuthorityExceptionBean.getUserId());
        if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
    }


}
