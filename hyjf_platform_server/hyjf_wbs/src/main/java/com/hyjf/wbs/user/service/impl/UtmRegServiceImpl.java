package com.hyjf.wbs.user.service.impl;

import com.hyjf.wbs.user.dao.model.auto.UtmReg;
import com.hyjf.wbs.user.dao.model.auto.UtmRegExample;
import com.hyjf.wbs.user.service.UtmRegService;
import com.hyjf.wbs.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Auther: wxd
 * @Date: 2019-04-15 17:00
 * @Description:
 */
@Service
public class UtmRegServiceImpl extends BaseServiceImpl implements UtmRegService {

    @Override
    public UtmReg selectUtmInfo(Integer userId,List<Integer> utmId) {

        UtmRegExample example = new UtmRegExample();
        UtmRegExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andUtmIdIn(utmId);
        List<UtmReg> utmRegList = utmRegMapper.selectByExample(example);
        if(utmRegList!= null && utmRegList.size()>0){
            return utmRegList.get(0);
        }
        return null;
    }
}
