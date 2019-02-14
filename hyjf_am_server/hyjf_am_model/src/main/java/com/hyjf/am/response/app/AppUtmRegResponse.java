/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.app;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;

/**
 * @Description app渠道统计
 * @Author sunss
 * @Date 2018/6/23 9:59
 */
public class AppUtmRegResponse extends Response<AppUtmRegVO> {
    private Integer count;
    private List<UtmPlatVO> appUtm;
    private List<Integer> userIdList;
    private AdminUtmReadPermissionsVO adminUtmReadPermissions;
    private List<AppUtmRegVO> openAccountTimeList;
    private List<AppUtmRegVO> hztTenderPriceList;
    private List<AppUtmRegVO> hxfTenderPriceList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
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

    public List<AppUtmRegVO> getOpenAccountTimeList() {
        return openAccountTimeList;
    }

    public void setOpenAccountTimeList(List<AppUtmRegVO> openAccountTimeList) {
        this.openAccountTimeList = openAccountTimeList;
    }

    public List<AppUtmRegVO> getHztTenderPriceList() {
        return hztTenderPriceList;
    }

    public void setHztTenderPriceList(List<AppUtmRegVO> hztTenderPriceList) {
        this.hztTenderPriceList = hztTenderPriceList;
    }

    public List<AppUtmRegVO> getHxfTenderPriceList() {
        return hxfTenderPriceList;
    }

    public void setHxfTenderPriceList(List<AppUtmRegVO> hxfTenderPriceList) {
        this.hxfTenderPriceList = hxfTenderPriceList;
    }
}
