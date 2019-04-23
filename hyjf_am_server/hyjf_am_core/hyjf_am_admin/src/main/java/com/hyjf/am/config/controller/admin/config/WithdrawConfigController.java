package com.hyjf.am.config.controller.admin.config;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfig;
import com.hyjf.am.config.service.config.WithdrawConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.config.WithdrawRuleConfigResponse;
import com.hyjf.am.response.admin.config.WithdrawTimeConfigResponse;
import com.hyjf.am.resquest.admin.config.WithdrawRuleConfigRequest;
import com.hyjf.am.resquest.admin.config.WithdrawTimeConfigRequest;
import com.hyjf.am.vo.admin.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.admin.config.WithdrawTimeConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提现配置
 * @author jun
 * @version WithdrawConfigController, v0.1 2019/4/19 14:12
 */
@RestController
@RequestMapping("/am-config/configCenter/withdrawConfig")
public class WithdrawConfigController extends BaseConfigController {

    @Autowired
    private WithdrawConfigService withdrawConfigService;

    /**
     * 获取提现规则配置列表
     * @return
     */
    @PostMapping("/getWithdrawRuleConfigList")
    public WithdrawRuleConfigResponse getWithdrawRuleConfigList(@RequestBody WithdrawRuleConfigRequest request) {
        WithdrawRuleConfigResponse response = new WithdrawRuleConfigResponse();
        int count = withdrawConfigService.getWithdrawRuleConfigCount();
        if (count==0) {
            return response;
        }
        if(request.getCurrPage()>0) {
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize() ==0?10:request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<WithdrawRuleConfig> recordList=withdrawConfigService.getWithdrawRuleConfigList(request);
        if(CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList, WithdrawRuleConfigVO.class));
            response.setCount(count);
            response.setRtn(Response.SUCCESS);
        }

        return response;
    }

    /**
     * 获取提现时间配置列表
     * @return
     */
    @PostMapping("/getWithdrawTimeConfigList")
    public WithdrawTimeConfigResponse getWithdrawTimeConfigList(@RequestBody WithdrawTimeConfigRequest request) {
        WithdrawTimeConfigResponse response = new WithdrawTimeConfigResponse();
        int count = withdrawConfigService.getWithdrawTimeConfigCount();
        if (count==0){
            return response;
        }
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize() ==0?10:request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        List<WithdrawTimeConfig> results=withdrawConfigService.getWithdrawTimeConfigList(request);
        if (CollectionUtils.isNotEmpty(results)){
            response.setResultList(CommonUtils.convertBeanList(results,WithdrawTimeConfigVO.class));
            response.setCount(count);
            response.setRtn(Response.SUCCESS);
        }
        return response;

    }


    /**
     * 提现规则配置详情
     * @param id
     * @return
     */
    @GetMapping("/getWithdrawRuleConfigById/{id}")
    public WithdrawRuleConfigResponse getWithdrawRuleConfigById(@PathVariable Integer id){
        WithdrawRuleConfigResponse response = new WithdrawRuleConfigResponse();
        WithdrawRuleConfig withdrawRuleConfig = withdrawConfigService.getWithdrawRuleConfigById(id);
        if (withdrawRuleConfig!=null){
            response.setResult(CommonUtils.convertBean(withdrawRuleConfig,WithdrawRuleConfigVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    /**
     * 修改提现规则配置
     * @param form
     * @return
     */
    @PostMapping("/updateWithdrawRuleConfig")
    public Integer updateWithdrawRuleConfig(@RequestBody WithdrawRuleConfigVO form){
        return withdrawConfigService.updateWithdrawRuleConfig(CommonUtils.convertBean(form,WithdrawRuleConfig.class));
    }

    /**
     * 保存提现时间配置
     */
    @PostMapping("/saveWithdrawTimeConfig")
    public Integer saveWithdrawTimeConfig(@RequestBody WithdrawTimeConfigVO form){
        return withdrawConfigService.saveWithdrawTimeConfig(CommonUtils.convertBean(form,WithdrawTimeConfig.class));
    }


    /**
     * 提现时间配置详情
     * @param form
     * @return
     */
    @GetMapping("/getWithdrawTimeConfigById/{id}")
    public WithdrawTimeConfigResponse getWithdrawTimeConfigById(@PathVariable Integer id){
        WithdrawTimeConfigResponse response = new WithdrawTimeConfigResponse();
        WithdrawTimeConfig withdrawTimeConfig = withdrawConfigService.getWithdrawTimeConfigById(id);
        if (withdrawTimeConfig != null){
            response.setResult(CommonUtils.convertBean(withdrawTimeConfig,WithdrawTimeConfigVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

}
