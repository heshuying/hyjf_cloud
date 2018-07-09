package com.hyjf.am.user.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.service.CertificateAuthorityExceptionService;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;

/**
 * CA认证异常
 *
 * @author dongzeshan
 */
@RestController
@RequestMapping("/am-user/certificate")
public class CertificateAuthorityExceptionController extends BaseController {


    @Autowired
    private CertificateAuthorityExceptionService certificateAuthorityExceptionService;



    /**
     * 画面检索
     *
     * @param request
     * @param form
     * @return
     */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
    public CertificateAuthorityResponse search(HttpServletRequest request, CertificateAuthorityExceptionRequest form) {
		CertificateAuthorityResponse cfar=new CertificateAuthorityResponse();
        // 创建分页
       
        return  this.createPage(form,cfar);
    }

    /**
     * 异常处理更新Action
     *
     * @param request
     * @param form
     * @return
     * @throws MQException 
     * @throws ParseException 
     * @throws NumberFormatException 
     */
	@RequestMapping(value = "/modifyAction", method = RequestMethod.POST)
    public CertificateAuthorityResponse modifyAction( CertificateAuthorityExceptionRequest form) throws NumberFormatException, ParseException, MQException {
		CertificateAuthorityResponse cr=new CertificateAuthorityResponse();
        // 用户ID
        if (Validator.isNull(form.getUserId())) {
        	cr.setRtn(AdminResponse.FAIL);
        	cr.setMessage("用户ID为空");
            return cr;
        }
       // 发送CA认证MQ
        this.certificateAuthorityExceptionService.updateUserCAMQ(Integer.valueOf(form.getUserId()));
        return cr;
    }

    /**
     * 创建分页机能
     *
     * @param request
     * @param modelAndView
     * @param form
     */
    private CertificateAuthorityResponse createPage( CertificateAuthorityExceptionRequest form,CertificateAuthorityResponse cfar) {
        Integer counts = this.certificateAuthorityExceptionService.countCAExceptionList(form);
        if (counts > 0) {
            Paginator paginator = new Paginator(form.getCurrPage(),form.getPageSize(), counts);
            List<CertificateAuthority> recordList = this.certificateAuthorityExceptionService.getCAExceptionList(form, paginator.getOffset(), paginator.getLimit());
            cfar.setResultList(CommonUtils.convertBeanList(recordList, CertificateAuthorityVO.class));
        }
      
        cfar.setRecordTotal(counts);
        return cfar;
    }
}
