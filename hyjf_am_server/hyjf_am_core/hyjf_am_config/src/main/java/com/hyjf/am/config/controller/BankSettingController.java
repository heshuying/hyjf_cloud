/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.FeeConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.BankSettingService;
import com.hyjf.am.config.service.FeeConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.response.config.FeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author dangzw
 * @version BankSettingController, v0.1 2018/7/24 23:58
 */
@Api(value = "配置中心银行配置 江西银行", description = "配置中心银行配置 江西银行")
@RestController
@RequestMapping("/am-config/banksetting")
public class BankSettingController {

    @Autowired
    private BankSettingService bankSettingService;

    /**
     *(条件)列表查询
     * @return
     */
    @RequestMapping("/list")
    public AdminBankSettingResponse selectBankSettingListByPage(@RequestBody AdminBankSettingRequest adminRequest) {
        //logger.info("江西银行列表..." + JSONObject.toJSON(adminRequest));
        AdminBankSettingResponse  response =new AdminBankSettingResponse();
        List<JxBankConfig> recordList = this.bankSettingService.getRecordList(new JxBankConfig(), -1, -1);
        if (recordList != null) {
            for(JxBankConfig banksConfig : recordList) {
                // 不支持快捷支付
                if (0 == banksConfig.getQuickPayment()) {
                    banksConfig.setMonthCardQuota(new BigDecimal(0));
                }
            }
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(), recordList.size());
            JxBankConfig bc=new JxBankConfig();
            bc.setBankName(adminRequest.getBankName());
            bc.setPayAllianceCode(adminRequest.getPayAllianceCode());
            recordList = this.bankSettingService.getRecordList(bc, paginator.getOffset(),
                    paginator.getLimit());
           // String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
            if(!CollectionUtils.isEmpty(recordList)){
                List<JxBankConfigVO> jxBankConfigList = CommonUtils.convertBeanList(recordList, JxBankConfigVO.class);
                response.setResultList(jxBankConfigList);
                response.setRecordTotal(recordList.size());
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return null;
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param adminRequest
     * @return
     */
    @RequestMapping("/info")
    public AdminBankSettingResponse bankSettingInfo(@RequestBody AdminBankSettingRequest adminRequest) {
        AdminBankSettingResponse  response =new AdminBankSettingResponse();
        JxBankConfig jxBankConfigList = bankSettingService.bankSettingInfo(adminRequest);
        if(null != jxBankConfigList){
            JxBankConfigVO jxBankConfigVO = CommonUtils.convertBean(jxBankConfigList, JxBankConfigVO.class);
            response.setResult(jxBankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     *(条件)列表查询
     * @return
     */
    @RequestMapping("/searchForInsert")
    public List<JxBankConfigVO> searchForInsert(@RequestBody AdminBankSettingRequest adminRequest) {
        AdminBankSettingResponse  response =new AdminBankSettingResponse();
            JxBankConfig bc=new JxBankConfig();
            bc.setBankName(adminRequest.getBankName());
            List<JxBankConfig> recordList = this.bankSettingService.getRecordList(bc, -1, -1);
            List<JxBankConfigVO> jxBankConfigList = CommonUtils.convertBeanList(recordList, JxBankConfigVO.class);
        return jxBankConfigList;
    }

    /**
     * 添加
     * @param adminRequest
     */
    @RequestMapping("/insert")
    public AdminBankSettingResponse insertBankSetting(@RequestBody AdminBankSettingRequest adminRequest) {
        AdminBankSettingResponse  response =new AdminBankSettingResponse();
        try{
            int result =this.bankSettingService.insertBankSetting(adminRequest);
            if(result > 0 ){
                response.setRtn(Response.SUCCESS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
