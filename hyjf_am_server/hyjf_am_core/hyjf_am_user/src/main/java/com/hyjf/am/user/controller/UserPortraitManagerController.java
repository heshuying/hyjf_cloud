/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.service.LoanCoverUserManagerService;
import com.hyjf.am.user.service.UserPortraitManagerService;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-借款盖章用户
 */

@RestController
@RequestMapping("/am-user/userPortraitManage")
public class UserPortraitManagerController extends BaseController{
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
        logger.info("---findUserPortraitRecord by param---  " + request);
        Map<String, Object> mapParam = paramToMap(request);
        UserPortraitResponse response = new UserPortraitResponse();
        Integer registCount = userPortraitManagerService.countLoanSubjectCertificateAuthority(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), registCount,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), registCount);
        }
        int limitStart = 0;
        int limitEnd = 1000;
        //查询导出数据
        if(request.isLimitFlg()){
            limitStart = paginator.getOffset();
            limitEnd = paginator.getLimit();
        }
        List<UserPortrait> listUserPortrait = userPortraitManagerService.selectRecordList(mapParam,limitStart, limitEnd);
        response.setCount(registCount);
        if(registCount>0){
            if (!CollectionUtils.isEmpty(listUserPortrait)) {
                List<UserPortraitVO> userVoList = CommonUtils.convertBeanList(listUserPortrait, UserPortraitVO.class);
                response.setResultList(userVoList);
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
     * 根据用户id查找用户图像
     */
    @RequestMapping("/selectUserPortraitByUserId/{userId}")
    public UserPortraitResponse userId(@PathVariable String userId){
        if(StringUtils.isNotBlank(userId)){
            int intUserId = Integer.parseInt(userId);
            UserPortrait userPortrait = userPortraitManagerService.selectUsersPortraitByUserId(intUserId);
            UserPortraitResponse response = new UserPortraitResponse();
            String status = Response.FAIL;
            if(null!=userPortrait){
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
    public int updateUserPortraitRecord(@RequestBody @Valid UserPortraitRequest request){
        int updFlg = userPortraitManagerService.updateUserPortrait(request);
        return updFlg;
    }

    private Map<String, Object> paramToMap(UserPortraitRequest request){
        Map<String,Object> mapParam = new HashMap<String,Object>();
        if(null!=request){
            mapParam.put("userName",request.getUserName());
        }
        if(null!=request){
            mapParam.put("yesterdayBeginTime",request.getYesterdayBeginTime());
        }
        if(null!=request){
            mapParam.put("yesterdayEnd",request.getYesterdayEndTime());
        }
        return mapParam;
    }

 }
