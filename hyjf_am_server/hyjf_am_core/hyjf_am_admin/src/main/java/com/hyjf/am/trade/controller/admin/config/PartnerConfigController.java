package com.hyjf.am.trade.controller.admin.config;


import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminPartnerConfigDetailResponse;
import com.hyjf.am.resquest.admin.AdminPartnerConfigListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.admin.config.PartnerConfigService;
import com.hyjf.am.vo.admin.HjhInstConfigWrapVo;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.GetCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigController, v0.1 2018/7/5.
 */
@RestController
@RequestMapping("/am-admin/config/partnerconfig")
public class PartnerConfigController {

    @Autowired
    private PartnerConfigService partnerConfigService;
    private static Logger logger = LoggerFactory.getLogger(PartnerConfigController.class);

    /**
     * 分页查询配置中心保证金配置列表
     * @return
     */
    @RequestMapping("/list")
    public AdminPartnerConfigDetailResponse instConfigInitByPage(@RequestBody AdminPartnerConfigListRequest adminRequest) {
        logger.info("合作机构配置列表..." + JSONObject.toJSON(adminRequest));
        AdminPartnerConfigDetailResponse  response =new AdminPartnerConfigDetailResponse();
        List<HjhInstConfigWrapVo> resList = new ArrayList<>();
        //查询合作机构配置条数
         int count = this.partnerConfigService.instConfigInitCount();
        if (count>0) {
            response.setRecordTotal(count);
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), count,adminRequest.getPageSize() == 0? 10 :adminRequest.getPageSize());
            //查询记录
            List<HjhInstConfig> recordList =partnerConfigService.instConfigInitByPage(paginator.getOffset(), paginator.getLimit());
            for(HjhInstConfig instConfigVO:recordList){
                HjhInstConfigWrapVo recordWrap = new HjhInstConfigWrapVo();
                BeanUtils.copyProperties(instConfigVO, recordWrap);
                //获取发标额度余额
//                String capitalAvailable = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode());
//                if(StringUtils.isNotEmpty(capitalAvailable)){
//                    recordWrap.setCapitalAvailable(capitalAvailable);
//                }else{
//                    recordWrap.setCapitalAvailable(recordWrap.getCapitalToplimit().toString());
//                    RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode(),recordWrap.getCapitalToplimit().toString());
//                }
                resList.add(recordWrap);
            }
            response.setResultList(resList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 查询详情页面
     * @return
     */
    @RequestMapping("/info")
    public AdminPartnerConfigDetailResponse instConfigInfoById(@RequestBody @Valid AdminPartnerConfigListRequest adminRequest) {
        AdminPartnerConfigDetailResponse response = new AdminPartnerConfigDetailResponse();
        if (StringUtils.isNotEmpty(adminRequest.getIds())) {
            HjhInstConfig record = this.partnerConfigService.getInstConfigRecordById(adminRequest.getIds());
            HjhInstConfigWrapVo recordWrap = new HjhInstConfigWrapVo();
            if(null != record){
                BeanUtils.copyProperties(record, recordWrap);
                //获取发标额度余额
//                String capitalAvailable = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode());
//                if(StringUtils.isNotEmpty(capitalAvailable)){
//                    recordWrap.setCapitalAvailable(capitalAvailable);
//                }else{
//                    recordWrap.setCapitalAvailable(recordWrap.getCapitalToplimit().toString());
//                    RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode(),recordWrap.getCapitalToplimit().toString());
//                }
                response.setResult(recordWrap);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 添加合作机构配置
     * @param req
     */
    @RequestMapping("/insert")
    public AdminPartnerConfigDetailResponse insertInstConfig(@RequestBody AdminPartnerConfigListRequest req) {
        AdminPartnerConfigDetailResponse resp = new AdminPartnerConfigDetailResponse();
        String instCode = GetCode.generateInstCode(8);
        int result =this.partnerConfigService.insertInstConfig(req,instCode);
        if(result > 0 /*&& req.getCapitalToplimit() != null && !RedisUtils.exists(RedisConstants.CAPITAL_TOPLIMIT_+instCode)*/){
            //RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_ + instCode, req.getCapitalToplimit().toString());
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage("添加失败！");
        return resp;
    }

    /**
     * 修改合作机构配置
     * @param req
     */
    @RequestMapping("/update")
    public AdminPartnerConfigDetailResponse updateInstConfig(@RequestBody AdminPartnerConfigListRequest req) {
        AdminPartnerConfigDetailResponse resp = new AdminPartnerConfigDetailResponse();
        HjhInstConfig instConfig = null;
        if(req.getId() != null ){
            instConfig = this.partnerConfigService.getInstConfigRecordById(String.valueOf(req.getId()));
        }
        int result = partnerConfigService.updateInstConfigRecordById(req);
        // 更新redis中的可用余额
        if(result > 0 /*&& !req.getCapitalToplimit(). equals(instConfig.getCapitalToplimit())*/){
//            if(!RedisUtils.exists(RedisConstants.CAPITAL_TOPLIMIT_+instConfig.getInstCode())){
//                RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_ + instConfig.getInstCode(), req.getCapitalToplimit().toString());
//            }else {
//                if(req.getCapitalToplimit().compareTo(instConfig.getCapitalToplimit()) > 0){
//                    redisAdd(RedisConstants.CAPITAL_TOPLIMIT_ + instConfig.getInstCode(),req.getCapitalToplimit().subtract(instConfig.getCapitalToplimit()).toString());//增加redis相应计划可投金额
//                }else{
//                    redisSubstrack(RedisConstants.CAPITAL_TOPLIMIT_ + instConfig.getInstCode(),instConfig.getCapitalToplimit().subtract(req.getCapitalToplimit()).toString());//减少风险保证金可投金额
//
//                }
//            }
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage("修改失败！");
        return  resp;
    }
    /**
     * 删除合作机构配置
     * @param req
     */
    @RequestMapping("/delete")
    public AdminPartnerConfigDetailResponse deleteInstConfigById(@RequestBody AdminPartnerConfigListRequest req) {
        AdminPartnerConfigDetailResponse resp = new AdminPartnerConfigDetailResponse();
        this.partnerConfigService.deleteInstConfig(req);
        resp.setRtn(Response.SUCCESS);
        return resp;
    }

    /**
     * 合作机构配置资产编号校验
     * @param req
     */
    @RequestMapping("/isExists")
    public IntegerResponse isExists(@RequestBody AdminPartnerConfigListRequest req) {
        return this.partnerConfigService.isExists(req);
    }

}
