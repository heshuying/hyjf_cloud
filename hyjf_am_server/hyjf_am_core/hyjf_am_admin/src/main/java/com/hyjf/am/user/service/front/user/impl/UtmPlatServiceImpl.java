package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.front.user.UtmPlatService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Integer> getUsersInfoList(){

        List<Integer> utmPlat = utmPlatCustomizeMapper.selectUsersInfo();
        return utmPlat;
    }

    @Override
    public List<Integer> getUsersList(String sourceIdSrch){

        List<Integer> utmPlat = utmPlatCustomizeMapper.selectUsers(sourceIdSrch);
        return utmPlat;
    }
}
