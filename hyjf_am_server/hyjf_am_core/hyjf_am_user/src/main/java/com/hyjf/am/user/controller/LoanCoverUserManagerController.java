/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthority;
import com.hyjf.am.user.service.LoanCoverUserManagerService;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-借款盖章用户
 */

@RestController
@RequestMapping("/am-user/loanCoverUser")
public class LoanCoverUserManagerController extends BaseController{
    @Autowired
    private LoanCoverUserManagerService loanCoverUserManagerService;

    /**
     * 根据筛选条件查找(用户管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/loanCoverUserRecord")
    public LoanCoverUserResponse findLoanCoverUserRecord(@RequestBody @Valid LoanCoverUserRequest request) {
        logger.info("---findLoanCoverUserRecord by param---  " + JSONObject.toJSON(request));
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = new Date();
        Date dateEnd = new Date();
        if (StringUtils.isNotBlank(request.getStartCreate()) || StringUtils.isNotBlank(request.getStartCreate())) {
            try {
                dateStart = smp.parse(request.getStartCreate());
                dateEnd = smp.parse(request.getStartCreate());
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }

        LoanCoverUserResponse response = new LoanCoverUserResponse();
        Integer registCount = loanCoverUserManagerService.countLoanSubjectCertificateAuthority(request,dateStart,dateEnd);
        Paginator paginator = new Paginator(request.getCurrPage(), registCount,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), registCount);
        }
        int limitStart = paginator.getOffset();
        int limitEnd =  paginator.getLimit();
        if(request.getLimitFlg()==1){
            limitEnd = 0;
            limitStart = 0;
        }
        List<LoanSubjectCertificateAuthority> registRecordCustomizeList = loanCoverUserManagerService.getRecordList(request,limitStart,limitEnd,dateStart,dateEnd);
        if(registCount>0){
            if (!CollectionUtils.isEmpty(registRecordCustomizeList)) {
                List<LoanCoverUserVO> userVoList = CommonUtils.convertBeanList(registRecordCustomizeList, LoanCoverUserVO.class);
                response.setResultList(userVoList);
                response.setCount(registCount);
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }


    /**
     * 保存记录
     */
    @RequestMapping("/insertLoanCoverUserRecord")
    public int isExistsRecordByIdNo(@RequestBody @Valid LoanCoverUserRequest request){
        int intFlg = loanCoverUserManagerService.insertLoanCoverUserRecord(request);
        return intFlg;
    }
    /**
     * 根据证件号码查找借款主体CA认证记录表
     */
    @RequestMapping("/selectIsExistsRecordByIdNo/{strIdNo}")
    public LoanCoverUserResponse selectIsExistsRecordByIdNo(@PathVariable String strIdNo){
        LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = loanCoverUserManagerService.selectIsExistsRecordByIdNo(strIdNo);
        LoanCoverUserResponse response = new LoanCoverUserResponse();
        String status = Response.FAIL;
        if(null!=loanSubjectCertificateAuthority){
            LoanCoverUserVO loanCoverUserVO = new LoanCoverUserVO();
            BeanUtils.copyProperties(loanSubjectCertificateAuthority, loanCoverUserVO);
            response.setResult(loanCoverUserVO);
            status = Response.SUCCESS;
        }
        //代表成功
        response.setRtn(status);
        return response;

    }
    /**
     * 根据证id查找借款主体CA认证记录表
     */
    @RequestMapping("/selectIsExistsRecordById/{strId}")
    public LoanCoverUserResponse selectIsExistsRecordById(@PathVariable String strId){
        LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = loanCoverUserManagerService.selectIsExistsRecordById(strId);
        LoanCoverUserResponse response = new LoanCoverUserResponse();
        String status = Response.FAIL;
        if(null!=loanSubjectCertificateAuthority){
            LoanCoverUserVO loanCoverUserVO = new LoanCoverUserVO();
            BeanUtils.copyProperties(loanSubjectCertificateAuthority, loanCoverUserVO);
            response.setResult(loanCoverUserVO);
            status = Response.SUCCESS;
        }
        response.setRtn(status);
        return response;
    }
    /**
     * 更新记录
     */
    @RequestMapping("/updateLoanCoverUserRecord")
    public int updateLoanCoverUserRecord(@RequestBody @Valid LoanCoverUserRequest request){
        int updFlg = loanCoverUserManagerService.updateLoanCoverUserRecord(request);
        return updFlg;
    }
 }
