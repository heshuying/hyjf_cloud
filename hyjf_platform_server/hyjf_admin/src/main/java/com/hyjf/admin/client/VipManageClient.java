/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.vo.admin.VipManageVO;

import java.util.List;

/**
 * @author yaoyong
 * @version VipManageClient, v0.1 2018/7/2 16:23
 */
public interface VipManageClient {
    List<VipManageVO> searchList(VipManageRequest vipManageRequest);
}
