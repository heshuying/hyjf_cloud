package com.hyjf.admin.service.impl.vip.content;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.vip.content.OperService;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by future on 2019/3/18.
 */
@Service
public class OperServiceImpl implements OperService {

    @Autowired
    private AmAdminClient amAdminClient;

    /**
     *大屏运营部数据配置列表查询
     * @param screenConfigVO
     * @return
     */
    @Override
    public List<ScreenConfigVO> list(ScreenConfigRequest request) {
        return amAdminClient.getScreenConfigList(request);
    }

    /**
     *大屏运营部数据配置数据新增
     * @param screenConfigVO
     * @return
     */
    @Override
    public int add(ScreenConfigVO screenConfigVO) {
        return amAdminClient.addScreenConfig(screenConfigVO);
    }

    /**
     *大屏运营部数据配置数据编辑
     * @param screenConfigVO
     * @return
     */
    @Override
    public int update(ScreenConfigVO screenConfigVO) {
        return amAdminClient.updateScreenConfig(screenConfigVO);
    }
}
