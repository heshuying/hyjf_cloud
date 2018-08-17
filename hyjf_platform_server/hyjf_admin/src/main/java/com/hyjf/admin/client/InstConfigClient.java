package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminInstConfigDetailResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigClient, v0.1 2018/7/5.
 */
public interface InstConfigClient {

    /**
     * 查询配置中心保证金配置
     *
     * @return
     */
    public AdminInstConfigDetailResponse instConfigInit(AdminInstConfigListRequest adminRequest);

    /**
     * 查询详情
     * @param adminRequest
     * @return
     */
    public AdminInstConfigDetailResponse searchInstConfigInfo(AdminInstConfigListRequest adminRequest);

    /**
     * 编辑保存保证金配置
     * @return
     */
    public AdminInstConfigListResponse saveInstConfig(AdminInstConfigListRequest req);

    /**
     * 修改保证金配置
     * @return
     */
    public AdminInstConfigListResponse updateInstConfig(AdminInstConfigListRequest req);

    /**
     * 删除保证金配置
     * @return
     */
    public AdminInstConfigListResponse deleteInstConfig(AdminInstConfigListRequest req);
}
