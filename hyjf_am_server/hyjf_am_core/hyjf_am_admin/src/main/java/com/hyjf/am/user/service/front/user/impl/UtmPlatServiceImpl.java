package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.front.user.UtmPlatService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author：yinhui
 * @Date: 2018/10/23  16:06
 */
@Service
public class UtmPlatServiceImpl extends BaseServiceImpl implements UtmPlatService {

    @Override
    public UtmPlat getUtmPlat(Integer sourceId){

        UtmPlat utmPlat = utmPlatCustomizeMapper.selectUtmPlatBySourceIds(sourceId);
        return utmPlat;
    }
}
