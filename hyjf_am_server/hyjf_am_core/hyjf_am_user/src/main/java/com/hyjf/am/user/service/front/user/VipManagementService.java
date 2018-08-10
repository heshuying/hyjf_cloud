/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.customize.VipDetailListCustomize;
import com.hyjf.am.user.dao.model.customize.VipManageListCustomize;
import com.hyjf.am.user.dao.model.customize.VipUpdateGradeListCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;
import java.util.Map; /**
 * @author yaoyong
 * @version VipManagementService, v0.1 2018/7/2 17:48
 */
public interface VipManagementService extends BaseService {
    int countRecord(Map<String, Object> mapParam);

    List<VipManageListCustomize> selectUserList(Map<String, Object> mapParam, int offset, int limit);

    List<VipDetailListCustomize> searchDetailList(Map<String, Object> mapParam, int offset, int limit);

    int countDetailRecord(Map<String, Object> mapParam);

    int countUpdateGradeList(Map<String, Object> mapParam);

    List<VipUpdateGradeListCustomize> searchUpdaeGradeList(Map<String, Object> mapParam, int offset, int limit);
}
