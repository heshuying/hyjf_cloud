package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;

import java.util.List;

public interface CustomerTaskConfigMapperCustomize {

    List<CustomerTaskConfigVO> selectByExample(CustomerTaskConfigRequest request);
}