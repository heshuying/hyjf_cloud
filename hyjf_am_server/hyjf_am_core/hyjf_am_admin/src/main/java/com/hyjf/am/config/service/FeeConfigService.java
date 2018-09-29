package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.FeeConfig;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;

import java.util.List;

public interface FeeConfigService {

    List<FeeConfig> getFeeConfigs(String bankCode);
    /**
     *  查询手续费配置列表条数
     * @return
     */
    public Integer getFeeConfigListCount(AdminFeeConfigRequest request,int limitStart,int limitEnd);
    /**
     *  查询手续费配置列表
     * @return
     */
    public  List<FeeConfig> getFeeConfigListByPage(AdminFeeConfigRequest request, int limitStart, int limitEnd);

    /*
     *根据id查询手续费配置详情
     * */
    public FeeConfig getFeeConfigInfoById(Integer userId);


    /**
     * 添加手续费配置
     * @param req
     */
    public Integer insertFeeConfig(AdminFeeConfigRequest req) ;

    /**
     * 修改手续费配置
     * @param req
     */
    public Integer updateFeeConfig( AdminFeeConfigRequest req) ;

    /**
     * 删除手续费配置
     * @param id
     */
    public void deleteFeeConfig( Integer id) ;
}
