/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;

/**
 * @author yaoyong
 * @version VipManageClient, v0.1 2018/7/2 16:23
 */
public interface VipManageClient {
    VipManageResponse searchList(VipManageRequest vipManageRequest);

    VipDetailListResponse searchDetailList(VipDetailListRequest detailListRequest);

    VipUpdateGradeListResponse searchUpdateGradeList(VipUpdateGradeListRequest vgl);
}
