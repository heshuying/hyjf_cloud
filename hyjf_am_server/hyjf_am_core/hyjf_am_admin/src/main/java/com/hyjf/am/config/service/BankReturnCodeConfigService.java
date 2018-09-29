package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface BankReturnCodeConfigService {

    /**
     *  查询返回码配置列表条数
     * @return
     */
    public Integer selectBankRetcodeListCount(AdminBankRetcodeConfigRequest request);
    /**
     *  查询返回码配置列表
     * @return
     */
    public List<BankReturnCodeConfig> selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest request, int limitStart, int limitEnd);

    /*
     *根据id查询返回码配置详情
     * */
    public BankReturnCodeConfig selectBankRetcodeConfigInfo(Integer userId);


    /**
     * 添加返回码配置
     * @param req
     */
    public Integer insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req) ;

    /**
     * 修改版本配置
     * @param req
     */
    public Integer updateBankReturnCodeConfig( AdminBankRetcodeConfigRequest req) ;

    /**
     *根据主键判断维护中数据是否存在
     */
    public boolean isExistsReturnCode( AdminBankRetcodeConfigRequest req) ;

    /**
     * 根据条件判断该条数据在数据库中是否存在
     * @param req
     */
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest req) ;
}
