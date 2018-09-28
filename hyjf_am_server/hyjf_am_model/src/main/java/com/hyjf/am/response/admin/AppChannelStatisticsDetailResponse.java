package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailResponse, v0.1 2018/9/21 17:26
 */

public class AppChannelStatisticsDetailResponse extends Response<AppChannelStatisticsDetailVO> {
    private Long count;
    List<UtmPlatVO> appUtm;
    AdminUtmReadPermissionsVO adminUtmReadPermissions;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<UtmPlatVO> getAppUtm() {
        return appUtm;
    }

    public void setAppUtm(List<UtmPlatVO> appUtm) {
        this.appUtm = appUtm;
    }

    public AdminUtmReadPermissionsVO getAdminUtmReadPermissions() {
        return adminUtmReadPermissions;
    }

    public void setAdminUtmReadPermissions(AdminUtmReadPermissionsVO adminUtmReadPermissions) {
        this.adminUtmReadPermissions = adminUtmReadPermissions;
    }
}
