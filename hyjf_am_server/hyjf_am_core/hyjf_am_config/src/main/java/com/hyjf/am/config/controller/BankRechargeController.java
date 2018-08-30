package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.BankRechargeConfig;
import com.hyjf.am.config.service.BankRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankRechargeConfigResponse;
import com.hyjf.am.response.config.BankRechargeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;
import com.hyjf.am.vo.config.BankRechargeConfigVo;
import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            int count =recordList.size();
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), recordList.size(),adminRequest.getPageSize()==0? 10:adminRequest.getPageSize());
            //查询记录
            recordList =bankRechargeService.selectBankRechargeByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<BankRechargeLimitConfigVO> bankRechargeLimitConfigVO = CommonUtils.convertBeanList(recordList, BankRechargeLimitConfigVO.class);
                response.setResultList(bankRechargeLimitConfigVO);
                response.setRecordTotal(count);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询的数据为空！");
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
            response.setMessage("查询的数据为空！");
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
                response.setMessage("添加失败！");
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
                response.setMessage("修改失败！");
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
            return response;
        }
        return  null;
    }
    /**
     * 查询快捷充值导出列表
     * @param adminRequest
     */
    @RequestMapping("/exportRecordList")
    public AdminBankRechargeConfigResponse  selectExportRecordList(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        AdminBankRechargeConfigResponse  response= new AdminBankRechargeConfigResponse();
        List<BankRechargeLimitConfigVO> list=new ArrayList<BankRechargeLimitConfigVO>();
        List<BankRechargeConfig> bankRechargeConfigs = this.bankRechargeService.selectExportRecordList(adminRequest);
        if(!CollectionUtils.isEmpty(bankRechargeConfigs)){
            list = CommonUtils.convertBeanList(bankRechargeConfigs,BankRechargeLimitConfigVO.class);
            response.setResultList(list);
            return response;
        }
        return  null;
    }

    /**
     * 根据bankId查询BankRechargeConfig
     * @auth sunpeikai
     * @param bankId
     * @return
     */
    @GetMapping(value = "/getBankRechargeConfigByBankId/{bankId}")
    public BankRechargeConfigResponse getBankRechargeConfigByBankId(@PathVariable Integer bankId){
        BankRechargeConfigResponse response = new BankRechargeConfigResponse();
        List<BankRechargeConfig> bankRechargeConfigs = this.bankRechargeService.getBankRechargeConfigByBankId(bankId);
        if(!CollectionUtils.isEmpty(bankRechargeConfigs)){
            BankRechargeConfig bankRechargeConfig = bankRechargeConfigs.get(0);
            BankRechargeConfigVo bankRechargeConfigVo = CommonUtils.convertBean(bankRechargeConfig,BankRechargeConfigVo.class);
            response.setResult(bankRechargeConfigVo);
            response.setRtn(Response.SUCCESS);
            return response;
        }
        return null;
    }
}
