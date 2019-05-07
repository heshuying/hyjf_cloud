/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.message;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.SmsCountCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author fq
 * @version SmsCountController, v0.1 2018/8/20 16:22
 */
@RestController
@RequestMapping("/am-user/sms_count")
public class SmsCountController extends BaseController {
    @Autowired
    private SmsCountService smsCountService;

    /**
     * 询短信统计，通过分公司显示
     * @param request
     * @return
     */
    @RequestMapping("/query_sms_count_list")
    public SmsCountCustomizeResponse querySmsCountLlist(@RequestBody SmsCountRequest request) {
        SmsCountCustomizeResponse response = new SmsCountCustomizeResponse();
        // 查询总条数
        int count = smsCountService.selectCount(request);
        response.setCount(count);
        if(count > 0){
            //根据需求：2018年12月27日之前的按照5分钱算，之后按4分钱算
            List<SmsCountCustomize> list = smsCountService.querySmsCountLlist(request);
            if (!CollectionUtils.isEmpty(list)) {
                List<SmsCountCustomizeVO> voList = CommonUtils.convertBeanList(list, SmsCountCustomizeVO.class);
                response.setResultList(voList);
            }
            //根据需求：2018年12月27日之前的按照5分钱算，之后按4分钱算
//            String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
////            if (StringUtils.isEmpty(configMoney)) {
////                configMoney = "0.042";//短信单价（0.042元/条）
////            }

            // 查询短信总条数
            //根据需求：2018年12月27日之前的按照5分钱算，之后按4分钱算
            List<SmsCountCustomize> listsmsCount = smsCountService.querySmsCountNumberTotal(request);
            if(listsmsCount.size() > 0){
                SmsCountCustomize vo = listsmsCount.get(0);
                response.setSumCount(vo.getSmsNumber());
                response.setSumMoney(vo.getSmsMoney());
            }
        }
        return response;
    }

    /**
     * 查询导出总条数
     * @param request
     * @return
     */
    @RequestMapping("getsmscountforexport")
    public SmsCountCustomizeResponse getCountForExport (@RequestBody SmsCountRequest request){
        // 查询列表条数
        SmsCountCustomizeResponse response = new SmsCountCustomizeResponse();
        response.setCount(smsCountService.selectCount(request));
        return response;
    }

    /**
     * 查询部门信息
     * @return
     */
    @RequestMapping("/query_department_info")
    public SmsCountCustomizeResponse queryDepartmentInfo() {
        SmsCountCustomizeResponse response = new SmsCountCustomizeResponse();
        List<OADepartmentCustomize> list = smsCountService.queryDepartmentInfo();
        if (!CollectionUtils.isEmpty(list)) {
            List<OADepartmentCustomizeVO> voList = CommonUtils.convertBeanList(list, OADepartmentCustomizeVO.class);
            response.setList(voList);
        }
        return response;
    }

    /**
     * 查询符合条件的用户的号码集合
     * @return
     */
    @RequestMapping("/queryUser")
    public Response queryUser(@RequestBody SmsCodeUserRequest request) {
        Response response = new Response();
        List<String> list = smsCountService.queryUser(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }
}
