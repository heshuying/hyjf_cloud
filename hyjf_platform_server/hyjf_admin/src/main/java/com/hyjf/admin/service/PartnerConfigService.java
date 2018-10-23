package com.hyjf.admin.service;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.AdminPartnerConfigDetailResponse;
import com.hyjf.am.resquest.admin.AdminPartnerConfigListRequest;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigService, v0.1 2018/7/5.
 */
public interface PartnerConfigService {

    /**
     * 查询合作机构配置列表
     * @param adminRequest
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse partnerConfigInit(AdminPartnerConfigListRequest adminRequest);
    /**
     * 查询合作机构配置详情页面
     * @param adminRequest
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse searchPartnerConfigInfo(AdminPartnerConfigListRequest adminRequest);

    /**
     * 编辑保存合作机构配置
     * @param req
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse savePartnerConfig(AdminPartnerConfigListRequest req);

    /**
     * 修改合作机构配置
     * @param req
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse updatePartnerConfig(AdminPartnerConfigListRequest req);

    /**
     * 删除合作机构配置
     * @param req
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse deletePartnerConfig(AdminPartnerConfigListRequest req);

    /**
     * 合作机构配置资产编号校验
     * @param req
     * @author xiehuili
     * @return
     */
    public IntegerResponse isExistsCheckAction(AdminPartnerConfigListRequest req);
}
