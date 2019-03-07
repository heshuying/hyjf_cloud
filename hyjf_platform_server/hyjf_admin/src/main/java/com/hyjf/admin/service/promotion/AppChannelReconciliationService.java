package com.hyjf.admin.service.promotion;

import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 16:51
 * @Description: AppChannelReconciliationService
 */
public interface AppChannelReconciliationService {

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据用户Id查询渠道账号管理
     * @Date 16:52 2018/7/24
     * @Param userId
     * @return
     */
    AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId);

}
