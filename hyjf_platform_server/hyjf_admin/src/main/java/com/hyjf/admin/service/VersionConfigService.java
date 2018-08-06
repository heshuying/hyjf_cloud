package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
public interface VersionConfigService {

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
    /**
     * 数据字典
     * @param code
     * @return
     */
    public  List<ParamNameVO> getParamNameList(String code);

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
}
