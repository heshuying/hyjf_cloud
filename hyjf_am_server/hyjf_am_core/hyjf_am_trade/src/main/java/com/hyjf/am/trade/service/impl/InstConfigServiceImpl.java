package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;
import com.hyjf.am.trade.service.InstConfigService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/6.
 */
@Service
public class InstConfigServiceImpl extends BaseServiceImpl implements InstConfigService {

    /*
    * 查询保证金记录总数
    * */
    @Override
    public Integer getInstConfigCount(){
        HjhInstConfigExample example = new HjhInstConfigExample();
        return hjhInstConfigMapper.selectByExample(example).size();
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
    public HjhInstConfig  getInstConfigRecordById(Map map){
        Integer id=(Integer)map.get("userId");
       return hjhInstConfigMapper.selectByPrimaryKey(id);
    }
    /**
     * 添加保证金配置
     * @param req
     */
    @Override
    public Integer insertInstConfig(AdminInstConfigListRequest req, String instCode) {
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
    public Integer updateInstConfigRecordById( AdminInstConfigListRequest req) {
        HjhInstConfig inc = new HjhInstConfig();
        BeanUtils.copyProperties(req,inc);
        inc.setUpdateUser(req.getUserId());
        inc.setUpdateTime(new Date());
        return hjhInstConfigMapper.updateByPrimaryKeySelective(inc);
    }
    /**
     * 删除保证金配置
     * @param recordList
     */
    @Override
    public void deleteInstConfig( List<Integer> recordList) {
        for (Integer id : recordList) {
            Map<String,Integer> map = new HashMap();
            map.put("userId",id);
            HjhInstConfig record = this.getInstConfigRecordById(map);
            record.setDelFlag(1);
            int result = hjhInstConfigMapper.updateByPrimaryKeySelective(record);
            if(result > 0 && RedisUtils.exists(RedisConstants.CAPITAL_TOPLIMIT_+record.getInstCode())){
                RedisUtils.del(RedisConstants.CAPITAL_TOPLIMIT_+record.getInstCode());
            }
        }

    }

}
