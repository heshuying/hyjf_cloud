package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.BankRechargeConfig;
import com.hyjf.am.config.service.BankRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankRechargeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;
import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@RestController
@RequestMapping("/am-config/config/bankrecharge")
public class BankRechargeController  extends BaseConfigController{
    @Autowired
    private BankRechargeService bankRechargeService;

    /**
     *查询快捷充值列表
     * @return
     */
    @RequestMapping("/list")
    public AdminBankRechargeConfigResponse selectBankRechargeByPage(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        logger.info("查询快捷充值列表..." + JSONObject.toJSON(adminRequest));
        AdminBankRechargeConfigResponse  response =new AdminBankRechargeConfigResponse();
        //查询查询快捷充值列表
        List<BankRechargeConfig> recordList =bankRechargeService.selectBankRechargeByPage(adminRequest,-1, -1);
        if (!CollectionUtils.isEmpty(recordList)) {
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(), recordList.size());
            //查询记录
            recordList =bankRechargeService.selectBankRechargeByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<BankRechargeLimitConfigVO> bankRechargeLimitConfigVO = CommonUtils.convertBeanList(recordList, BankRechargeLimitConfigVO.class);
                response.setResultList(bankRechargeLimitConfigVO);
                response.setRecordTotal(recordList.size());
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return null;
    }

    /**
     * 查询快捷充值情页面
     * @return
     */
    @RequestMapping("/info")
    public AdminBankRechargeConfigResponse selectBankRechargeById(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        logger.info("手续费配置详情..." + JSONObject.toJSON(adminRequest));
        AdminBankRechargeConfigResponse  response =new AdminBankRechargeConfigResponse();
        BankRechargeConfig record = this.bankRechargeService.selectBankRechargeById(adminRequest.getId());
        if(null != record){
            BankRechargeLimitConfigVO feeConfigVO = CommonUtils.convertBean(record, BankRechargeLimitConfigVO.class);
            response.setResult(feeConfigVO);
            response.setRtn(Response.SUCCESS);
        }else{
            response.setRtn(Response.FAIL);
        }
        return response;
    }

    /**
     * 添加快捷充值
     * @param adminRequest
     */
    @RequestMapping("/insert")
    public  AdminBankRechargeConfigResponse insertBankRecharge(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        AdminBankRechargeConfigResponse  response =new AdminBankRechargeConfigResponse();
        try{
            int result =this.bankRechargeService.insertBankRecharge(adminRequest);
            if(result > 0 ){
                response.setRtn(Response.SUCCESS);
            }else{
                response.setRtn(Response.FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 修改快捷充值
     * @param adminRequest
     */
    @RequestMapping("/update")
    public AdminBankRechargeConfigResponse updateBankRecharge(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        AdminBankRechargeConfigResponse  response =new AdminBankRechargeConfigResponse();
        try{
            int result =this.bankRechargeService.updateBankRecharge(adminRequest);
            if(result > 0 ){
                response.setRtn(Response.SUCCESS);
            }else{
                response.setRtn(Response.FAIL);
            }
        }catch (Exception e){
            response.setRtn(Response.FAIL);
        }
        return response;
    }

    /**
     * 删除快捷充值
     * @param adminRequest
     */
    @RequestMapping("/delete")
    public AdminBankRechargeConfigResponse deleteBankRecharge(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        AdminBankRechargeConfigResponse  response =new AdminBankRechargeConfigResponse();
        if(adminRequest.getId() != null){
            this.bankRechargeService.deleteBankRecharge(adminRequest.getId());
            response.setRtn(Response.SUCCESS);
        }
        return  response;
    }
    /**
     * 查询快捷充值导出列表
     * @param adminRequest
     */
    @RequestMapping("/exportRecordList")
    public List<BankRechargeLimitConfigVO>  selectExportRecordList(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        List<BankRechargeLimitConfigVO> list=new ArrayList<BankRechargeLimitConfigVO>();
        List<BankRechargeConfig> bankRechargeConfigs = this.bankRechargeService.selectExportRecordList(adminRequest);
        if(!CollectionUtils.isEmpty(bankRechargeConfigs)){
            list = CommonUtils.convertBeanList(bankRechargeConfigs,BankRechargeLimitConfigVO.class);
        }
        return  list;
    }
}
