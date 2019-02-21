package com.hyjf.am.trade.service.admin.config.impl;


import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.AdminPartnerConfigListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;
import com.hyjf.am.trade.service.admin.config.PartnerConfigService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/6.
 */
@Service
public class PartnerConfigServiceImpl extends BaseServiceImpl implements PartnerConfigService {

    /*
    * 查询保证金记录总数
    * */
    @Override
    public Integer getInstConfigCount(){
        HjhInstConfigExample example = new HjhInstConfigExample();
        return hjhInstConfigMapper.selectByExample(example).size();
    }
    /*
   * 查询保证金记录条数查询
   * */
    @Override
    public int instConfigInitCount(){
        HjhInstConfigExample example = new HjhInstConfigExample();
        example.createCriteria().andDelFlagEqualTo(0);
        return hjhInstConfigMapper.countByExample(example);
    }
    /*
    * 查询保证金记录查询
    * */
    @Override
    public List<HjhInstConfig> instConfigInitByPage(int limitStart, int limitEnd){
        HjhInstConfigExample example = new HjhInstConfigExample();
        example.createCriteria().andDelFlagEqualTo(0);
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return hjhInstConfigMapper.selectByExample(example);
    }
    /*
     *根据id查询保证金配置
     * */
    @Override
    public HjhInstConfig  getInstConfigRecordById(String userId){
        Integer id = Integer.valueOf(userId);
        return hjhInstConfigMapper.selectByPrimaryKey(id);
    }
    /**
     * 添加保证金配置
     * @param req
     */
    @Override
    public Integer insertInstConfig(AdminPartnerConfigListRequest req, String instCode) {
        HjhInstConfig inc = new HjhInstConfig();
        BeanUtils.copyProperties(req,inc);
        inc.setCreateUser(req.getUserId());
        inc.setUpdateUser(req.getUserId());
        inc.setCreateTime(new Date());
        inc.setUpdateTime(new Date());
        inc.setInstCode(instCode);
        return hjhInstConfigMapper.insertSelective(inc);
    }
    /**
     * 修改保证金配置
     * @param req
     */
    @Override
    public Integer updateInstConfigRecordById( AdminPartnerConfigListRequest req) {
        HjhInstConfig inc = new HjhInstConfig();
        BeanUtils.copyProperties(req,inc);
        inc.setUpdateUser(req.getUserId());
        inc.setUpdateTime(new Date());
        return hjhInstConfigMapper.updateByPrimaryKeySelective(inc);
    }
    /**
     * 删除保证金配置
     * @param req
     */
    @Override
    public void deleteInstConfig( AdminPartnerConfigListRequest req) {
        HjhInstConfig record =null;
        if(StringUtils.isNotBlank(req.getIds())){
            record = this.getInstConfigRecordById(req.getIds());
            record.setDelFlag(1);
            int result = hjhInstConfigMapper.updateByPrimaryKeySelective(record);
//            if(result > 0 && RedisUtils.exists(RedisConstants.CAPITAL_TOPLIMIT_+record.getInstCode())){
//                RedisUtils.del(RedisConstants.CAPITAL_TOPLIMIT_+record.getInstCode());
//            }
        }
    }
    /**
     * 根据机构属性获取机构配置
     * @param instType
     * @return
     */
    @Override
    public List<HjhInstConfig> getInstConfigByType(int instType) {
        HjhInstConfigExample example = new HjhInstConfigExample();

        HjhInstConfigExample.Criteria criteria1 = example.createCriteria();
        criteria1.andInstTypeEqualTo(instType);

        HjhInstConfigExample.Criteria criteria2 = example.createCriteria();
        criteria2.andInstTypeEqualTo(2);

        example.or(criteria2);

        example.setLimitStart(-1);
        example.setLimitEnd(-1);
        return hjhInstConfigMapper.selectByExample(example);
    }

    /**
     * 合作机构配置资产编号校验
     * @param req
     */
    @Override
    public IntegerResponse isExists(AdminPartnerConfigListRequest req){
        IntegerResponse response = new IntegerResponse();
        HjhInstConfigExample example = new HjhInstConfigExample();
        example.createCriteria().andInstCodeEqualTo(req.getInstCode()).andDelFlagEqualTo(0);
        int count = this.hjhInstConfigMapper.countByExample(example);
        if(count >=0){
            response.setResultInt(count);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;

    }

}
