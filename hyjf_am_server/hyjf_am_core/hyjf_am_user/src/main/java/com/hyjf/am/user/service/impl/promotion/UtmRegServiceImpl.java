/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.promotion;

import com.hyjf.am.user.dao.mapper.customize.UtmRegCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.service.promotion.UtmRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version UtmRegServiceImpl, v0.1 2018/7/17 9:15
 */
@Service
public class UtmRegServiceImpl implements UtmRegService {
    @Autowired
    private UtmRegCustomizeMapper utmRegCustomizeMapper;

    @Override
    public List<UtmReg> getUtmRegList(Integer sourceId) {
        return utmRegCustomizeMapper.getUtmRegList(sourceId);
    }
}
