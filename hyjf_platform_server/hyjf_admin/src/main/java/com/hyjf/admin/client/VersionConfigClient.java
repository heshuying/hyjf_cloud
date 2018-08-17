package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import com.hyjf.am.vo.admin.VersionVO;

/**
 * @author by xiehuili on 2018/7/16.
 */
public interface VersionConfigClient {
    /**
     * 查询版本设置列表
     * @param adminRequest
     * @return
     */
    public AdminVersionResponse versionConfigInit(AdminVersionRequest adminRequest);
    /**
     * 查询详情页面
     * @param adminRequest
     * @return
     */
    public AdminVersionResponse searchVersionConfigInfo(AdminVersionRequest adminRequest);
//    /**
//     * 数据字典
//     * @param code
//     * @return
//     */
//    public List<ParamName> getParamNameList(String code);

    /**
     * 编辑保存版本设置
     * @return
     */
    public AdminVersionResponse saveVersionConfig(AdminVersionRequest req);

    /**
     * 修改版本设置
     * @return
     */
    public AdminVersionResponse updateVersionConfig(AdminVersionRequest req);

    /**
     * 删除保证金配置
     * @return
     */
    public AdminVersionResponse deleteVersionConfig(Integer id);
    /**
     * 校验版本号是否唯一
     * @return
     */
    public VersionVO getVersionByCode(Integer vid, Integer type, String version);
    
    public VersionConfigBeanResponse searchList(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request);

}
