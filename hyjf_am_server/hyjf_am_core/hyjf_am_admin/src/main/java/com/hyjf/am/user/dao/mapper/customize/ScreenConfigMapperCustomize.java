package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.ScreenConfigExample;
import com.hyjf.am.vo.user.ScreenConfigVO;

import java.util.List;

public interface ScreenConfigMapperCustomize {

    List<ScreenConfigVO> selectByExample(ScreenConfigExample example);
}