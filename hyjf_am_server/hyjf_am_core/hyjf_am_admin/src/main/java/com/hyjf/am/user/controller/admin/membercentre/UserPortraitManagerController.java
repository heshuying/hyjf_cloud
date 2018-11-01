/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.membercentre;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UserPortraitScoreResponse;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.admin.UserPortraitScoreRequest;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.dao.model.customize.UserPortraitScoreCustomize;
import com.hyjf.am.user.service.admin.membercentre.LoanCoverUserManagerService;
import com.hyjf.am.user.service.admin.membercentre.UserPortraitManagerService;
import com.hyjf.am.vo.admin.UserPortraitScoreCustomizeVO;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 * 后台会员中心-借款盖章用户
 */

@RestController
@RequestMapping("/am-user/userPortraitManage")
public class UserPortraitManagerController extends BaseController {
    @Autowired
    private LoanCoverUserManagerService loanCoverUserManagerService;
    @Autowired
    private UserPortraitManagerService userPortraitManagerService;

    /**
     * 根据筛选条件查找(用户管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/findUserPortraitRecord")
    public UserPortraitResponse findUserPortraitRecord(@RequestBody @Valid UserPortraitRequest request) {
        logger.info("---findUserPortraitRecord by param---  " + JSONObject.toJSON(request));
        Map<String, Object> mapParam = paramToMap(request);
        UserPortraitResponse response = new UserPortraitResponse();
        Integer registCount = userPortraitManagerService.countLoanSubjectCertificateAuthority(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), registCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), registCount);
        }
        int limitStart = paginator.getOffset();
        int limitEnd =  paginator.getLimit();
        if(request.isLimitFlg()) {
            limitEnd = 0;
            limitStart = 0;
        }
        response.setCount(registCount);
        if (registCount > 0) {
            List<UserPortraitVO> listUserPortrait = userPortraitManagerService.selectRecordList(mapParam, limitStart, limitEnd);
            if (!CollectionUtils.isEmpty(listUserPortrait)) {
                response.setResultList(listUserPortrait);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }


    /**
     * 保存记录
     */
    @RequestMapping("/insertLoanCoverUserRecord")
    public int isExistsRecordByIdNo(@RequestBody @Valid LoanCoverUserRequest request) {
        int intFlg = loanCoverUserManagerService.insertLoanCoverUserRecord(request);
        return intFlg;
    }

    /**
     * 根据用户id查找用户图像
     */
    @RequestMapping("/selectUserPortraitByUserId/{userId}")
    public UserPortraitResponse userId(@PathVariable String userId) {
        logger.info("---selectUserPortraitByUserId by userId---  " + userId);
        if (StringUtils.isNotBlank(userId)) {
            int intUserId = Integer.parseInt(userId);
            UserPortrait userPortrait = userPortraitManagerService.selectUsersPortraitByUserId(intUserId);
            UserPortraitResponse response = new UserPortraitResponse();
            String status = Response.FAIL;
            if (null != userPortrait) {
                UserPortraitVO loanCoverUserVO = new UserPortraitVO();
                BeanUtils.copyProperties(userPortrait, loanCoverUserVO);
                response.setResult(loanCoverUserVO);
                status = Response.SUCCESS;
            }
            response.setRtn(status);
            return response;
        }
        return null;
    }

    /**
     * 更新记录
     */
    @RequestMapping("/updateUserPortraitRecord")
    public IntegerResponse updateUserPortraitRecord(@RequestBody @Valid UserPortraitRequest request) {
        int updFlg = userPortraitManagerService.updateUserPortrait(request);
        return new IntegerResponse(updFlg);
    }

    private Map<String, Object> paramToMap(UserPortraitRequest request) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        if (null != request) {
            mapParam.put("userName", request.getUserName());
        }
        if (null != request) {
            mapParam.put("yesterdayBeginTime", request.getYesterdayBeginTime());
        }
        if (null != request) {
            mapParam.put("yesterdayEnd", request.getYesterdayEndTime());
        }
        String mobile= StringUtils.isNoneBlank(request.getMobile())?request.getMobile():null;
        String sex=StringUtils.isNoneBlank(request.getSex())?request.getSex():null;
        Integer ageStart=request.getAgeStart()!= null ?request.getAgeStart():null;
        Integer ageEnd=request.getAgeEnd()!= null ?request.getAgeEnd():null;
        BigDecimal bankTotalStart=request.getBankTotalStart()!= null?request.getBankTotalStart():null;
        BigDecimal bankTotalEnd = request.getBankTotalEnd() != null?request.getBankTotalEnd():null;
        BigDecimal interestSumStart=request.getInterestSumStart() != null? request.getInterestSumStart():null;
        BigDecimal interestSumEnd =request.getInterestSumEnd() != null?request.getInterestSumEnd():null;
        Integer tradeNumberStart=request.getTradeNumberStart() != null?request.getTradeNumberStart():null;
        Integer tradeNumberEnd=request.getTradeNumberEnd() != null?request.getTradeNumberEnd():null;
        String currentOwner = StringUtils.isNotBlank(request.getCurrentOwner())?request.getCurrentOwner():null;
        Integer attribute=request.getAttribute() != null?request.getAttribute():null;
        String investProcess=StringUtils.isNoneBlank(request.getInvestProcess())?request.getInvestProcess():null;
        String regTimeStart=StringUtils.isNoneBlank(request.getRegTimeStart())?request.getRegTimeStart()+" 00:00:00":null;
        String regTimeEnd=StringUtils.isNoneBlank(request.getRegTimeEnd())?request.getRegTimeEnd()+" 23:59:59":null;

        mapParam.put("mobile", mobile);
        mapParam.put("sex", sex);
        mapParam.put("ageStart", ageStart);
        mapParam.put("ageEnd", ageEnd);
        mapParam.put("bankTotalStart", bankTotalStart);
        mapParam.put("bankTotalEnd", bankTotalEnd);
        mapParam.put("interestSumStart", interestSumStart);
        mapParam.put("interestSumEnd", interestSumEnd);
        mapParam.put("tradeNumberStart", tradeNumberStart);
        mapParam.put("tradeNumberEnd", tradeNumberEnd);
        mapParam.put("currentOwner", currentOwner);
        mapParam.put("attribute", attribute);
        mapParam.put("investProcess", investProcess);
        mapParam.put("regTimeStart", regTimeStart);
        mapParam.put("regTimeEnd", regTimeEnd);
        return mapParam;
    }


    /**
     * 获取用户画像评分列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectUserPortraitScoreRecordList")
    public UserPortraitScoreResponse selectUserPortraitScoreRecordList(@RequestBody @Valid UserPortraitScoreRequest request) {
        UserPortraitScoreResponse response = new UserPortraitScoreResponse();
        Map<String, Object> userPortrait = new HashMap<>();
//        if (null != request) {
            userPortrait.put("userName", request.getUserName());
//        }
        int count = userPortraitManagerService.countLoanSubjectCertificateAuthority(userPortrait);
        Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), count);
        }
        List<UserPortraitScoreCustomize> recordList = userPortraitManagerService.selectUserPortraitScoreList(userPortrait,request, paginator.getOffset(), paginator.getLimit());
        response.setCount(count);
        if (count > 0) {
            if (!CollectionUtils.isEmpty(recordList)) {
                List<UserPortraitScoreCustomizeVO> customizeVOList = CommonUtils.convertBeanList(recordList, UserPortraitScoreCustomizeVO.class);
                response.setResultList(customizeVOList);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 导出根据筛选条件查找(用户管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/exportRecordList")
    public UserPortraitResponse exportRecordList(@RequestBody @Valid UserPortraitRequest request) {
        logger.info("---用户画像导出 param---  " + request);
        Map<String, Object> mapParam = paramToMap(request);
        UserPortraitResponse response = new UserPortraitResponse();
        Integer registCount = userPortraitManagerService.countLoanSubjectCertificateAuthority(mapParam);
        if (registCount > 0) {
            response.setCount(registCount);
            List<UserPortraitVO> listUserPortrait = userPortraitManagerService.selectRecordList(mapParam,-1,-1);
            if (!CollectionUtils.isEmpty(listUserPortrait)) {
                response.setResultList(listUserPortrait);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
