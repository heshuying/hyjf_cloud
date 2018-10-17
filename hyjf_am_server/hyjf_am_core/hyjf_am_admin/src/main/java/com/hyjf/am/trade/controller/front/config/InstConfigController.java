package com.hyjf.am.trade.controller.front.config;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminInstConfigDetailResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.front.config.InstConfigService;
import com.hyjf.am.vo.admin.HjhInstConfigWrapVo;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.GetCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/am-admin/config/instconfig")
public class InstConfigController {

    @Autowired
    private InstConfigService instConfigService;
    private static Logger logger = LoggerFactory.getLogger(InstConfigController.class);

    /**
     * 分页查询配置中心保证金配置列表
     * @return
     */
    @RequestMapping("/list")
    public AdminInstConfigDetailResponse instConfigInitByPage( @RequestBody AdminInstConfigListRequest adminRequest) {
        logger.info("保证金配置列表..." + JSONObject.toJSON(adminRequest));
        AdminInstConfigDetailResponse  response =new AdminInstConfigDetailResponse();
        List<HjhInstConfigWrapVo> resList = new ArrayList<>();
        //查询保证金配置条数
        List<HjhInstConfig> recordList = this.instConfigService.instConfigInitByPage(-1,-1);
        if (!CollectionUtils.isEmpty(recordList)) {
            response.setRecordTotal(recordList.size());
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), recordList.size(),adminRequest.getPageSize()==0?10:adminRequest.getPageSize());
            //查询记录
            recordList =instConfigService.instConfigInitByPage(paginator.getOffset(), paginator.getLimit());
            for(HjhInstConfig instConfigVO:recordList){
                HjhInstConfigWrapVo recordWrap = new HjhInstConfigWrapVo();
                BeanUtils.copyProperties(instConfigVO, recordWrap);
                //获取发标额度余额
                String capitalAvailable = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode());
                if(StringUtils.isNotEmpty(capitalAvailable)){
                    recordWrap.setCapitalAvailable(capitalAvailable);
                }else{
                    recordWrap.setCapitalAvailable(recordWrap.getCapitalToplimit().toString());
                    RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode(),recordWrap.getCapitalToplimit().toString());
                }
                resList.add(recordWrap);
            }
            response.setResultList(resList);
        }
        return response;
    }

    /**
     * 查询详情页面
     * @return
     */
    @RequestMapping("/info")
    public AdminInstConfigDetailResponse instConfigInfoById(@RequestBody @Valid AdminInstConfigListRequest adminRequest) {
        AdminInstConfigDetailResponse response = new AdminInstConfigDetailResponse();
        if (StringUtils.isNotEmpty(adminRequest.getIds())) {
            HjhInstConfig record = this.instConfigService.getInstConfigRecordById(adminRequest.getIds());
            HjhInstConfigWrapVo recordWrap = new HjhInstConfigWrapVo();
            if(null != record){
                BeanUtils.copyProperties(record, recordWrap);
                //获取发标额度余额
                String capitalAvailable = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode());
                if(StringUtils.isNotEmpty(capitalAvailable)){
                    recordWrap.setCapitalAvailable(capitalAvailable);
                }else{
                    recordWrap.setCapitalAvailable(recordWrap.getCapitalToplimit().toString());
                    RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_+recordWrap.getInstCode(),recordWrap.getCapitalToplimit().toString());
                }
                response.setResult(recordWrap);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 添加保证金配置
     * @param req
     */
    @RequestMapping("/insert")
    public AdminInstConfigListResponse insertInstConfig(@RequestBody AdminInstConfigListRequest req) {
        AdminInstConfigListResponse resp = new AdminInstConfigListResponse();
        String instCode = req.getInstCode();
        int result =this.instConfigService.insertInstConfig(req,instCode);
        if(result > 0 ){
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage("添加失败！");
        return resp;
    }

    /**
     * 修改保证金配置
     * @param req
     */
    @RequestMapping("/update")
    public AdminInstConfigListResponse updateInstConfig(@RequestBody AdminInstConfigListRequest req) {
        AdminInstConfigListResponse resp = new AdminInstConfigListResponse();
        req.setId(Integer.valueOf(req.getIds()));
        int result = instConfigService.updateInstConfigRecordById(req);
        // 更新redis中的可用余额(保证金需求优化删除、只更新机构名称)
        if(result > 0){
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage("修改失败！");
        return  resp;
    }
    /**
     * 删除保证金配置
     * @param req
     */
    @RequestMapping("/delete")
    public AdminInstConfigListResponse deleteInstConfigById(@RequestBody AdminInstConfigListRequest req) {
        AdminInstConfigListResponse resp = new AdminInstConfigListResponse();
        this.instConfigService.deleteInstConfig(req);
        resp.setRtn(Response.SUCCESS);
        return resp;
    }

    /**
     * 并发情况下保证设置一个值
     * @param key
     * @param value
     */
    private void redisAdd(String key,String value){

        Jedis jedis = RedisUtils.getPool().getResource();

        while ("OK".equals(jedis.watch(key))) {
            List<Object> results = null;

            String balance = jedis.get(key);
            BigDecimal bal = new BigDecimal(0);
            if (balance != null) {
                bal =  new BigDecimal(balance);
            }
            BigDecimal val =  new BigDecimal(value);

            Transaction tx = jedis.multi();
            String valbeset = bal.add(val).toString();
            tx.set(key, valbeset);
            results = tx.exec();
            if (results == null || results.isEmpty()) {
                jedis.unwatch();
            } else {
                String ret = (String) results.get(0);
                if (ret != null && "OK".equals(ret)) {
                    // 成功后
                    break;
                } else {
                    jedis.unwatch();
                }
            }
        }
    }

    /**
     * 并发情况下保证设置一个值
     * @param key
     * @param value
     */
    private boolean redisSubstrack(String key,String value){

        Jedis jedis = RedisUtils.getPool().getResource();
        boolean result = false;

        while ("OK".equals(jedis.watch(key))) {
            List<Object> results = null;

            String balance = jedis.get(key);
            BigDecimal bal = new BigDecimal(balance);
            BigDecimal val = new BigDecimal(value);

            if(val.compareTo(bal)>0){
                return false;
            }

            Transaction tx = jedis.multi();
            String valbeset = bal.subtract(val).toString();
            tx.set(key, valbeset);
            results = tx.exec();
            if (results == null || results.isEmpty()) {
                jedis.unwatch();
            } else {
                String ret = (String) results.get(0);
                if (ret != null && "OK".equals(ret)) {
                    // 成功后
                    result = true;
                    break;
                } else {
                    jedis.unwatch();
                }
            }
        }

        return result;
    }

}