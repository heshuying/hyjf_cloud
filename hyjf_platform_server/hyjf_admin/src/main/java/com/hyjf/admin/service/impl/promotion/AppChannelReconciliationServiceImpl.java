package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmConfigClient;
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
    private AmConfigClient amConfigClient;


    @Override
    public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId) {
        return amConfigClient.selectAdminUtmReadPermissions(userId);
    }

}
