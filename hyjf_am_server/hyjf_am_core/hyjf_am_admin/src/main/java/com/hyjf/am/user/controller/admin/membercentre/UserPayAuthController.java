/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.membercentre;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.HjhUserAuthLogRequest;
import com.hyjf.am.resquest.user.UserPayAuthRequest;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhCreditTenderService;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.service.admin.membercentre.UserPayAuthService;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserPayListAuthCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-缴费授权
 */

@RestController
@RequestMapping("/am-user/userPayAuth")
public class UserPayAuthController extends BaseController {
    @Autowired
    private UserPayAuthService userPayAuthService;
    @Autowired
    private AdminHjhCreditTenderService adminHjhCreditTenderService;



    /**
     * 根据用户的查询条件查询用户授权列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/userPayAuthList")
    public UserPayAuthResponse selectUserPayAuthList(@RequestBody @Valid UserPayAuthRequest request) {
        logger.info("---selectUserPayAuthList by param---  " + JSONObject.toJSON(request));
        UserPayAuthResponse response = new UserPayAuthResponse();
        Map<String, Object> mapParam = paramSet(request);
        int usesrCount = userPayAuthService.countRecordTotalPay(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), usesrCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), usesrCount);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();
        if (request.isLimitFlg()) {
            limitEnd = 0;
            limitStart = 0;
        }
        response.setCount(usesrCount);
        if (usesrCount > 0) {
            List<AdminUserPayAuthCustomize> adminUserPayAuthCustomizeList = userPayAuthService.selectUserPayAuthList(mapParam, limitStart, limitEnd);
            if (!CollectionUtils.isEmpty(adminUserPayAuthCustomizeList)) {
                List<UserPayListAuthCustomizeVO> userVoList = CommonUtils.convertBeanList(adminUserPayAuthCustomizeList, UserPayListAuthCustomizeVO.class);
                response.setResultList(userVoList);
                response.setRtn(Response.SUCCESS);
                response.setMessage(Response.SUCCESS_MSG);
            }
        }
        return response;
    }

    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(UserPayAuthRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("authTimeStart", userRequest.getAuthTimeStart());
        mapParam.put("authTimeEnd", userRequest.getAuthTimeEnd());
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("authType", userRequest.getAuthType());
        mapParam.put("bankid", userRequest.getBankCode());
        mapParam.put("signTimeStart", userRequest.getSignTimeStart());
        mapParam.put("signTimeEnd", userRequest.getSignTimeEnd());
        /*mapParam.put("limitStart", userRequest.getSignTimeEnd());
        mapParam.put("limitEnd", userRequest.getSignTimeEnd());*/
        return mapParam;
    }

    /**
     * 根据用户id查询用户签约授权信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getHjhUserAuthByUserId/{userId}")
    public HjhUserAuthResponse searchUserDetail(@PathVariable int userId) {
        logger.info("---getHjhUserAuthByUserId by userId---  " + userId);
        HjhUserAuthResponse userManagerDetailResponse = new HjhUserAuthResponse();
        userManagerDetailResponse.setRtn(Response.FAIL);
        HjhUserAuth hjhUserAuth = userPayAuthService.selectHjhUserAuthByUserId(userId);
        if (null != hjhUserAuth) {
            HjhUserAuthVO hjhUserAuthVO = new HjhUserAuthVO();
            BeanUtils.copyProperties(hjhUserAuth, hjhUserAuthVO);
            userManagerDetailResponse.setResult(hjhUserAuthVO);
            userManagerDetailResponse.setRtn(Response.SUCCESS);//代表成功
        }
        return userManagerDetailResponse;
    }

    @RequestMapping("/isDismissPay/{userId}")
    public IntegerResponse isDismissPay(@PathVariable int userId){
        int count = adminHjhCreditTenderService.isDismissPay(userId);
        return new IntegerResponse(count);
    }
    @RequestMapping("/isDismissRePay/{userId}")
    public IntegerResponse isDismissRePay(@PathVariable int userId){
        int count = adminHjhCreditTenderService.isDismissRePay(userId);
        return new IntegerResponse(count);
    }

    /**
     * 缴费授权解约
     * @param userId
     * @return
     */
    @RequestMapping("/updateCancelPayAuth/{userId}")
    public BooleanResponse updateCancelPayAuth(@PathVariable int userId){
        boolean flg = userPayAuthService.updateCancelPayAuth(userId);
        return new BooleanResponse(flg);
    }
    /**
     * 插入授权记录表
     * @param hjhUserAuthLogRequest
     * @return
     */
    @RequestMapping("/insertUserAuthLog2")
    public BooleanResponse insertUserAuthLog2(@RequestBody @Valid HjhUserAuthLogRequest hjhUserAuthLogRequest){
        HjhUserAuthLog hjhUserAuthLog = new HjhUserAuthLog();
        BeanUtils.copyProperties(hjhUserAuthLogRequest,hjhUserAuthLog);
        boolean flg = userPayAuthService.insertUserAuthLog2(hjhUserAuthLog);
        return new BooleanResponse(flg);
    }

    /**
     * 查找还款授权列表
     * @param request
     * @return
     */
    @RequestMapping("/selectRecordListRePay")
    public UserPayAuthResponse selectRecordListRePay(@RequestBody @Valid UserPayAuthRequest request) {
        logger.info("---selectRecordListRePay by param---  " + JSONObject.toJSON(request));
        UserPayAuthResponse response = new UserPayAuthResponse();
        Map<String, Object> mapParam = paramSet(request);
        int usesrCount = userPayAuthService.countRecordTotalRePay(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), usesrCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), usesrCount);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();
        if (request.isLimitFlg()) {
            limitEnd = 0;
            limitStart = 0;
        }
        response.setCount(usesrCount);
        if (usesrCount > 0) {
            List<AdminUserPayAuthCustomize> adminUserPayAuthCustomizeList = userPayAuthService.selectUserRePayAuthList(mapParam, limitStart, limitEnd);
            if (!CollectionUtils.isEmpty(adminUserPayAuthCustomizeList)) {
                List<UserPayListAuthCustomizeVO> userVoList = CommonUtils.convertBeanList(adminUserPayAuthCustomizeList, UserPayListAuthCustomizeVO.class);
                response.setResultList(userVoList);
                response.setRtn(Response.SUCCESS);
                response.setMessage(Response.SUCCESS_MSG);
            }
        }
        return response;
    }

    @RequestMapping("/updateCancelRePayAuth/{userId}")
    public IntegerResponse updateCancelRePayAuth(@PathVariable  int userId){
        HjhUserAuth hjhUserAuth = userPayAuthService.selectHjhUserAuthByUserId(userId);
        hjhUserAuth.setAutoRepayStatus(0);
        hjhUserAuth.setRepayCancelTime(GetDate.date2Str(new Date(),GetDate.yyyyMMdd));
        int intUpd = userPayAuthService.updateCancelRePayAuth(hjhUserAuth);
        return new IntegerResponse(intUpd);
    }
}
