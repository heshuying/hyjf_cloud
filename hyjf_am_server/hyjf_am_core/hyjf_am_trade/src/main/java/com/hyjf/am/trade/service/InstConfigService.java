package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;

import java.util.List;

/**
 * * @Description 配置中心 保障金配置
 * @author by xiehuili on 2018/7/6.
 */
public interface InstConfigService {

    /*
    * 查询保证金记录总数
    * */
    public Integer getInstConfigCount();
    /*
   * 查询保证金记录总数
   * */
    public List<HjhInstConfig> instConfigInitByPage(int limitStart, int limitEnd);
    /*
      *根据id查询保证金配置
      * */
    public HjhInstConfig getInstConfigRecordById(String userId);

    /**
     * 添加保证金配置
     * @param req
     */
    public Integer insertInstConfig(AdminInstConfigListRequest req, String instCode) ;
    /**
     * 修改保证金配置
     * @param req
     */
    public Integer updateInstConfigRecordById( AdminInstConfigListRequest req) ;
    /**
     * 删除保证金配置
     * @param recordList
     */
    public void deleteInstConfig( AdminInstConfigListRequest recordList) ;

}
