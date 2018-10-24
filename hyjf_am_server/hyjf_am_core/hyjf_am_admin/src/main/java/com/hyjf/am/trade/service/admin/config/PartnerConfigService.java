package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.admin.AdminPartnerConfigListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;

import java.util.List;

/**
 * * @Description 配置中心 保障金配置
 * @author by xiehuili on 2018/7/6.
 */
public interface PartnerConfigService {

    /*
    * 查询合作机构配置记录总数
    * */
    public Integer getInstConfigCount();
    /*
   * 查询合作机构配置总数
   * */
    public List<HjhInstConfig> instConfigInitByPage(int limitStart, int limitEnd);
    /*
      *根据id查询合作机构配置
      * */
    public HjhInstConfig getInstConfigRecordById(String userId);

    /**
     * 添加合作机构配置
     * @param req
     */
    public Integer insertInstConfig(AdminPartnerConfigListRequest req, String instCode) ;
    /**
     * 修改合作机构配置
     * @param req
     */
    public Integer updateInstConfigRecordById(AdminPartnerConfigListRequest req) ;
    /**
     * 删除合作机构配置
     * @param recordList
     */
    public void deleteInstConfig(AdminPartnerConfigListRequest recordList) ;
    /**
     * 根据机构属性获取合作机构配置
     * @param instType
     * @return
     */
    List<HjhInstConfig> getInstConfigByType(int instType);

    /**
     * 合作机构配置资产编号校验
     * @param req
     */
    public IntegerResponse isExists(AdminPartnerConfigListRequest req);

}