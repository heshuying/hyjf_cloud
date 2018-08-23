package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.BankReturnCodeConfigMapper;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.service.BankReturnCodeConfigService;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class BankReturnCodeConfigServiceImpl implements BankReturnCodeConfigService {

        @Autowired
        private BankReturnCodeConfigMapper bankReturnCodeConfigMapper;
    /**
     *  查询返回码配置列表条数
     * @return
     */
    @Override
    public Integer selectBankRetcodeListCount(AdminBankRetcodeConfigRequest request){

        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        BankReturnCodeConfigExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (Validator.isNotNull(request.getTxCode())) {
            criteria.andTxCodeEqualTo(request.getTxCode());
        }
        if (Validator.isNotNull(request.getRetCode())) {
            criteria.andRetCodeEqualTo(request.getRetCode());
        }
        return bankReturnCodeConfigMapper.countByExample(example);
    }
    /**
     *  查询返回码配置列表
     * @return
     */
    @Override
    public List<BankReturnCodeConfig> selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest request, int limitStart, int limitEnd){
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        BankReturnCodeConfigExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (Validator.isNotNull(request.getTxCode())) {
            criteria.andTxCodeLike("%" + request.getTxCode() + "%");
        }
        if (Validator.isNotNull(request.getRetCode())) {
            criteria.andRetCodeLike("%" + request.getRetCode() + "%");
        }
        if (Validator.isNotNull(request.getErrorMsg())) {
            criteria.andErrorMsgLike("%" + request.getErrorMsg() + "%");
        }
        return bankReturnCodeConfigMapper.selectByExample(example);
    }

    /*
     *根据id查询返回码配置详情
     * */
    @Override
    public BankReturnCodeConfig selectBankRetcodeConfigInfo(Integer userId){
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        BankReturnCodeConfigExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(userId);
        List<BankReturnCodeConfig> list = bankReturnCodeConfigMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new BankReturnCodeConfig();
    }


    /**
     * 添加返回码配置
     * @param req
     */
    @Override
    public Integer insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req) {
        BankReturnCodeConfig config= new BankReturnCodeConfig();
        BeanUtils.copyProperties(req,config);
        config.setCreateTime(GetDate.getDate());
        config.setUpdateTime(GetDate.getDate());
        return bankReturnCodeConfigMapper.insertSelective(config);
    }

    /**
     * 修改版本配置
     * @param req
     */
    @Override
    public Integer updateBankReturnCodeConfig( AdminBankRetcodeConfigRequest req) {
        BankReturnCodeConfig config= new BankReturnCodeConfig();
        BeanUtils.copyProperties(req,config);
        config.setUpdateTime(GetDate.getDate());
        return bankReturnCodeConfigMapper.updateByPrimaryKeySelective(config);
    }

    /**
     *根据主键判断维护中数据是否存在
     */
    @Override
    public boolean isExistsReturnCode(AdminBankRetcodeConfigRequest req) {
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        BankReturnCodeConfigExample.Criteria cra = example.createCriteria();
        if (Validator.isNotNull(req.getTxCode()) && Validator.isNotNull(req.getRetCode())) {
            cra.andTxCodeLike("%" + req.getTxCode() + "%");
            cra.andRetCodeLike("%" + req.getRetCode() + "%");
        }
        List<BankReturnCodeConfig> bankReturnCodeConfigList = bankReturnCodeConfigMapper.selectByExample(example);
        if (bankReturnCodeConfigList != null && bankReturnCodeConfigList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据条件判断该条数据在数据库中是否存在
     * @param req
     */
    @Override
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest req) {
        if (Validator.isNotNull(req.getId())) {
            return false;
        }
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        BankReturnCodeConfigExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(req.getId());
        List<BankReturnCodeConfig> bankReturnCodeConfigList = bankReturnCodeConfigMapper.selectByExample(example);
        if (bankReturnCodeConfigList != null && bankReturnCodeConfigList.size() > 0) {
            return true;
        }
        return false;
    }

}
