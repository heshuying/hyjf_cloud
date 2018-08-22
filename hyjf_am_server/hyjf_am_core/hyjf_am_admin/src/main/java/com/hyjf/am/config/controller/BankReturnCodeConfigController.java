package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.service.BankReturnCodeConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/17.
 */
@RestController
@RequestMapping("/am-config/config/bankretcodeconfig")
public class BankReturnCodeConfigController extends BaseConfigController{


    @Autowired
    private BankReturnCodeConfigService bankReturnCodeConfigService;


    /**
     * 分页查询配置中心返回码配置列表
     * @return
     */
    @RequestMapping("/list")
    public BankReturnCodeConfigResponse selectBankRetcodeListByPage(@RequestBody AdminBankRetcodeConfigRequest adminRequest) {
        logger.info("返回码配置列表..." + JSONObject.toJSON(adminRequest));
        BankReturnCodeConfigResponse  response =new BankReturnCodeConfigResponse();
        //查询返回码配置列表条数
        int recordTotal = this.bankReturnCodeConfigService.selectBankRetcodeListCount(adminRequest);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(), recordTotal);
            //查询记录
            List<BankReturnCodeConfig> recordList =bankReturnCodeConfigService.selectBankRetcodeListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<BankReturnCodeConfigVO> hicv = CommonUtils.convertBeanList(recordList, BankReturnCodeConfigVO.class);
                response.setResultList(hicv);
//                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return null;
    }
    /**
     * 查询详情页面
     * @return
     */
    @RequestMapping("/info")
    public BankReturnCodeConfigResponse selectBankRetcodeConfigInfo(@RequestBody AdminBankRetcodeConfigRequest adminRequest) {
        logger.info("返回码配置详情..." + JSONObject.toJSON(adminRequest));
        BankReturnCodeConfigResponse  response =new BankReturnCodeConfigResponse();
        BankReturnCodeConfig record = this.bankReturnCodeConfigService.selectBankRetcodeConfigInfo(adminRequest.getId());
        if(null != record){
            BankReturnCodeConfigVO vo = new BankReturnCodeConfigVO();
            BeanUtils.copyProperties(record,vo);
            response.setResult(vo);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    /**
     * 添加返回码配置
     * @param req
     */
    @RequestMapping("/insert")
    public BankReturnCodeConfigResponse insertBankReturnCodeConfig(@RequestBody  AdminBankRetcodeConfigRequest req) {
        BankReturnCodeConfigResponse resp = new BankReturnCodeConfigResponse();
        try{
            int result =this.bankReturnCodeConfigService.insertBankReturnCodeConfig(req);
            if(result > 0 ){
                //分页查询
//                resp = versionConfigInitByPage(req);
                resp.setRtn(Response.SUCCESS);
            }
        }catch (Exception e){
            resp.setRtn(Response.FAIL);
        }
        return resp;
    }
    /**
     * 修改版本配置
     * @param req
     */
    @RequestMapping("/update")
    public BankReturnCodeConfigResponse updateBankReturnCodeConfig( @RequestBody AdminBankRetcodeConfigRequest req) {
        BankReturnCodeConfigResponse resp = new BankReturnCodeConfigResponse();
        try{
            int result =this.bankReturnCodeConfigService.updateBankReturnCodeConfig(req);
            if(result > 0 ){
                //分页查询
//                resp = versionConfigInitByPage(req);
                resp.setRtn(Response.SUCCESS);
            }
        }catch (Exception e){
            resp.setRtn(Response.FAIL);
        }
        return resp;
    }
    /**
     * 根据主键判断维护中数据是否存在
     *
     * @return
     */
    @RequestMapping("/isExistsReturnCode")
    public boolean isExistsReturnCode(@RequestBody  AdminBankRetcodeConfigRequest record){
        return bankReturnCodeConfigService.isExistsReturnCode(record);
    }
    /**
     * 根据条件判断该条数据在数据库中是否存在
     * @param adminRequest
     * @return
     */
    @RequestMapping("/isExistsRecord")
    public boolean isExistsRecord(@RequestBody AdminBankRetcodeConfigRequest adminRequest){
        return bankReturnCodeConfigService.isExistsRecord(adminRequest);
    }





}
