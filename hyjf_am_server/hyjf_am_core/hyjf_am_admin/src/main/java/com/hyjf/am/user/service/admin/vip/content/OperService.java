package com.hyjf.am.user.service.admin.vip.content;

import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.vo.user.ScreenConfigVO;

import java.util.List;

/**
 * Created by future on 2019/3/18.
 */
public interface OperService {

    List<ScreenConfig> list(ScreenConfigRequest request);

    int insert(ScreenConfigVO screenConfigVO);

    int update(ScreenConfigVO screenConfigVO);
}
