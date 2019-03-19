package com.hyjf.admin.service.vip.content;

import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.ScreenConfigVO;

import java.util.List;

/**
 * Created by future on 2019/3/18.
 */
public interface OperService {

    public List<ScreenConfigVO> list(ScreenConfigRequest request);

    int add(ScreenConfigVO screenConfigVO);

    int update(ScreenConfigVO screenConfigVO);
}
