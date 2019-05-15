/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.membercentre;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.RegistRecordCustomize;
import com.hyjf.am.user.service.admin.membercentre.RegistRecordManagerService;
import com.hyjf.am.vo.user.RegistRecordVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-会员管理
 */

@RestController
@RequestMapping("/am-user/registRecord")
public class RegistRecordManagerController extends BaseController {
    @Autowired
    private RegistRecordManagerService registRecordService;

    /**
     * 根据筛选条件查找(用户管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/registRecordList")
    public RegistRecordResponse findRegistRecordList(@RequestBody @Valid RegistRcordRequest request) {
        logger.info("---findRegistRecordList by param---  " + JSONObject.toJSON(request));
        Map<String, Object> mapParam = paramSet(request);
        RegistRecordResponse response = new RegistRecordResponse();
        Integer registCount = registRecordService.countRecordTotal(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), registCount,request.getPageSize());
        if(request.getPageSize() ==0){
            //参数没有显示条数的时候，默认显示i0条
            paginator = new Paginator(request.getCurrPage(), registCount);
        }
        int intStart = paginator.getOffset();
        int intEnd = paginator.getLimit();
        if(request.isLimitFlg()){
            intStart = 0;
            intEnd = 0;
        }
        response.setCount(registCount);
        if(registCount>0){
            List<RegistRecordCustomize> registRecordCustomizeList = registRecordService.selectRegistList(mapParam,intStart,intEnd);
            if (!CollectionUtils.isEmpty(registRecordCustomizeList)) {
                List<RegistRecordVO> userVoList = CommonUtils.convertBeanList(registRecordCustomizeList, RegistRecordVO.class);
                response.setResultList(userVoList);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 根据筛选条件查找(用户渠道修改列表详细信息)
     *
     * @param request
     * @return
     */
    @RequestMapping("/registRecordOne")
    public RegistRecordResponse findRegistRecordOne(@RequestBody @Valid RegistRcordRequest request) {
        logger.info("---findRegistRecordOne by param---  " + JSONObject.toJSON(request));
        RegistRecordResponse response = new RegistRecordResponse();
        RegistRecordCustomize registRecordCustomize = registRecordService.selectRegistOne(Integer.valueOf(request.getUserId()));
        if (registRecordCustomize!=null) {
            RegistRecordVO userVoList = CommonUtils.convertBean(registRecordCustomize, RegistRecordVO.class);
            response.setResult(userVoList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 获取列表总数
     *
     * @param request
     * @return
     */
    @RequestMapping("/countRecordTotal")
    public UserManagerResponse countRecordTotal(@RequestBody @Valid RegistRcordRequest request) {
        logger.info("---countUserList by param---  " + JSONObject.toJSON(request));
        UserManagerResponse response = new UserManagerResponse();
        Map<String, Object> mapParam = paramSet(request);
        int usesrCount = registRecordService.countRecordTotal(mapParam);
        response.setCount(usesrCount);
        return response;
    }
    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(RegistRcordRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        String startDate="";
        String endtDate="";
        if(StringUtils.isNotBlank(userRequest.getRegTimeStart())){
            startDate = userRequest.getRegTimeStart()+" 00:00:00";
        }
        if(StringUtils.isNotBlank(userRequest.getRegTimeStart())){
            endtDate = userRequest.getRegTimeEnd()+" 23:59:59";
        }
        mapParam.put("regTimeStart",startDate );
        mapParam.put("regTimeEnd", endtDate);
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("mobile", userRequest.getMobile());
        mapParam.put("recommendName", userRequest.getRecommendName());
        mapParam.put("registPlat",userRequest.getRegistPlat());
        mapParam.put("userId",userRequest.getUserId());
        mapParam.put("sourceId",userRequest.getSourceId());
        mapParam.put("sourceName",userRequest.getSourceName());
        return mapParam;
    }

 }
