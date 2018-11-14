/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.am.vo.config.HolidaysConfigVO;
import com.hyjf.common.paginator.Paginator;

/**
 * @author dangzw
 * @version AdminHolidaysConfigResponse, v0.1 2018/11/13 16:22
 */
public class AdminHolidaysConfigResponse extends Response<HolidaysConfigVO> {

    //分页工具类
    private Paginator paginator;

    //请求表单
    private AdminHolidaysConfigRequest holidaysconfigForm;

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public AdminHolidaysConfigRequest getHolidaysconfigForm() {
        return holidaysconfigForm;
    }

    public void setHolidaysconfigForm(AdminHolidaysConfigRequest holidaysconfigForm) {
        this.holidaysconfigForm = holidaysconfigForm;
    }
}
