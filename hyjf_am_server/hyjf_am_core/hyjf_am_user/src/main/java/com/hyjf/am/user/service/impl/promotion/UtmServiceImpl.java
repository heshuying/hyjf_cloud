package com.hyjf.am.user.service.impl.promotion;

import com.hyjf.am.user.dao.mapper.customize.UtmRegCustomizeMapper;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.user.service.promotion.UtmService;
import com.hyjf.am.vo.admin.UtmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 * 渠道管理接口
 */
@Service
public class UtmServiceImpl extends BaseServiceImpl implements UtmService {
    @Autowired
    private UtmRegCustomizeMapper utmRegCustomizeMapper;
    @Override
    public List<UtmVO> getByPageList(Map<String, Object> map) {
        List<UtmVO> list = utmRegCustomizeMapper.getByPageList(map);
        return list;
    }

    @Override
    public Integer getCountByParam(Map<String, Object> map) {
        return utmRegCustomizeMapper.getCountByParam(map);
    }
}
