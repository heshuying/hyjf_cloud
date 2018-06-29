/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthority;
import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.service.LoanCoverUserManagerService;
import com.hyjf.am.user.service.UserPortraitManagerService;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-借款盖章用户
 */

@RestController
@RequestMapping("/am-user/userPortraitManage")
public class UserPortraitManagerController {
    @Autowired
    private LoanCoverUserManagerService loanCoverUserManagerService;
    @Autowired
    private UserPortraitManagerService userPortraitManagerService;

    private static Logger logger = LoggerFactory.getLogger(UserPortraitManagerController.class);

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
        Paginator paginator = new Paginator(request.getPaginatorPage(), registCount,request.getLimit());
        if(request.getLimit() ==0){
            paginator = new Paginator(request.getPaginatorPage(), registCount);
        }
        int limitStart = 0;
        int limitEnd = 1000;
        //查询导出数据
        if(request.getLimitFlg()!=0){
            limitStart = paginator.getOffset();
            limitEnd = paginator.getLimit();
        }
        List<UserPortrait> listUserPortrait = userPortraitManagerService.selectRecordList(mapParam,limitStart, limitEnd);
        if(registCount>0){
            if (!CollectionUtils.isEmpty(listUserPortrait)) {
                List<UserPortraitVO> userVoList = CommonUtils.convertBeanList(listUserPortrait, UserPortraitVO.class);
                response.setResultList(userVoList);
                response.setCount(registCount);
                response.setRtn(Response.SUCCESS);//代表成功
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
            response.setRtn(status);//代表成功
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
