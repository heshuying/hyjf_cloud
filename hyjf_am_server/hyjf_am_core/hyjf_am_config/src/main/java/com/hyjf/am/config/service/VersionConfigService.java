package com.hyjf.am.config.service;
import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/16.
 */
public interface VersionConfigService {


    /**
     *  查询版本配置列表条数
     * @return
     */
    public Integer getVersionConfigCount(AdminVersionRequest request);
    /**
     *  查询版本配置列表
     * @return
     */
    public  List<Version> getVersionConfigListByPage(AdminVersionRequest version, int limitStart, int limitEnd);

    /*
     *根据id查询版本配置
     * */
    public VersionVO getVersionConfigInfoById(Integer userId);


    /**
     * 添加版本配置
     * @param req
     */
    public Integer insertVersionConfig(AdminVersionRequest req) ;

    /**
     * 修改版本配置
     * @param req
     */
    public Integer updateVersionConfig( AdminVersionRequest req) ;

    /**
     * 删除版本配置
     * @param recordList
     */
    public void deleteVersionConfig( List<Integer> id) ;

    /**
     * 校验版本配置当前系统版本号是否唯一
     * @param map
     */
    public VersionVO validationFeild( Map map) ;


    /**
     * 获取最新的版本信息
     * @author zhangyk
     * @date 2018/9/5 11:53
     */
    public Version  getLastestVersion();
}
