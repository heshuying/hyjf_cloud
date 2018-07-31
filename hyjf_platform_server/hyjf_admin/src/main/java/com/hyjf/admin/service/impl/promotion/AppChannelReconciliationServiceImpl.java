package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AdminUtmReadPermissionsClient;
import com.hyjf.admin.service.promotion.AppChannelReconciliationService;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 16:51
 * @Description: AppChannelReconciliationServiceImpl
 */
@Service
public class AppChannelReconciliationServiceImpl implements AppChannelReconciliationService {

    @Resource
    private AdminUtmReadPermissionsClient adminUtmReadPermissionsClient;
    @Override
    public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId) {
        return adminUtmReadPermissionsClient.selectAdminUtmReadPermissions(userId);
    }

    @Override
    public AppChannelReconciliationResponse getReconciliationPage(AppChannelReconciliationRequest form) {
        return adminUtmReadPermissionsClient.getReconciliationPage(form);
    }
}
