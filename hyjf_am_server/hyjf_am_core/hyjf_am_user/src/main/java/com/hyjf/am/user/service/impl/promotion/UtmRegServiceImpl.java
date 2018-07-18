/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.promotion;

import com.hyjf.am.user.dao.mapper.customize.UtmRegCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.service.promotion.UtmRegService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<UtmReg> getUtmRegList(Integer sourceId, String type) {
        return utmRegCustomizeMapper.getUtmRegList(sourceId, type);
    }

    @Override
    public BigDecimal getRegisterAttrCount(List<Integer> list) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return utmRegCustomizeMapper.getRegisterAttrCount(list, dayStart, dayEnd);
    }

    @Override
    public Integer getAccountNumber(List<Integer> list, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return utmRegCustomizeMapper.getAccountNumber(list, dayStart, dayEnd, type);
    }
}
