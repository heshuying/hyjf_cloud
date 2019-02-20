package com.hyjf.am.user.controller.front.ca;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.service.front.ca.CertificateAuthorityExceptionService;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

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
    @PostMapping("/search")
    public CertificateAuthorityResponse search(HttpServletRequest request, @RequestBody CertificateAuthorityExceptionRequest form) {
        CertificateAuthorityResponse cfar = new CertificateAuthorityResponse();
        // 创建分页

        return this.createNoExceptionPage(form, cfar);
    }


    /**
     * 获取CA认证异常列表
     */
    @PostMapping("/getExceptionRecordList")
    public CertificateAuthorityResponse getExceptionRecordList(@RequestBody CertificateAuthorityExceptionRequest form) {
        CertificateAuthorityResponse cfar = new CertificateAuthorityResponse();
        // 创建分页
        return this.createExceptionPage(form, cfar);
    }

    /**
     * 创建CA认证异常分页机能
     * @param form
     * @param cfar
     * @return
     */
    private CertificateAuthorityResponse createExceptionPage(CertificateAuthorityExceptionRequest form, CertificateAuthorityResponse cfar) {
        Integer counts = this.certificateAuthorityExceptionService.countCAExceptionList(form);
        if (counts > 0) {
            Paginator paginator = new Paginator(form.getCurrPage(), counts, form.getPageSize());
            List<CertificateAuthority> recordList = this.certificateAuthorityExceptionService.getCAExceptionList(form, paginator.getOffset(), paginator.getLimit());
            cfar.setResultList(CommonUtils.convertBeanList(recordList, CertificateAuthorityVO.class));
        }

        cfar.setRecordTotal(counts);
        return cfar;
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
    @GetMapping("/modifyAction/{userId}")
    public CertificateAuthorityResponse modifyAction(@PathVariable String userId) throws NumberFormatException, ParseException, MQException {
        CertificateAuthorityResponse cr = new CertificateAuthorityResponse();
        // 用户ID
        if (Validator.isNull(userId)) {
            cr.setRtn(AdminResponse.FAIL);
            cr.setMessage("用户ID为空");
            return cr;
        }
        // 发送CA认证MQ
        this.certificateAuthorityExceptionService.updateUserCAMQ(Integer.valueOf(userId));
        return cr;
    }

    /**
     * 创建分页机能
     *
     * @param request
     * @param modelAndView
     * @param form
     */
    private CertificateAuthorityResponse createNoExceptionPage(CertificateAuthorityExceptionRequest form, CertificateAuthorityResponse cfar) {
        Integer counts = this.certificateAuthorityExceptionService.countCANOExceptionList(form);
        if (counts > 0) {
            Paginator paginator = new Paginator(form.getCurrPage(),counts,form.getPageSize());
            List<CertificateAuthority> recordList = this.certificateAuthorityExceptionService.getCANOExceptionList(form, paginator.getOffset(), paginator.getLimit());
            cfar.setResultList(CommonUtils.convertBeanList(recordList, CertificateAuthorityVO.class));
        }

        cfar.setRecordTotal(counts);
        return cfar;
    }
}
