package com.hyjf.am.response.admin;

import java.util.List;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.UtmPlatVO;

/**
 * @author lisheng
 * @version AppUtmRegResponse, v0.1 2018/9/21 17:26
 */

public class AppUtmRegResponse extends Response<AppUtmRegVO> {
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
