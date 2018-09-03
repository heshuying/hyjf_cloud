package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;

import java.util.List;

public interface AdminPoundageCustomizeMapper {

    public void updatePoundage(PoundageListRequest entity);

    public Integer getPoundageCount(PoundageListRequest entity);

    public List<AdminPoundageCustomize>  getPoundageList(PoundageListRequest entity);

    public AdminPoundageCustomize getPoundageById(int id);

    public AdminPoundageCustomize getPoundageSum(PoundageListRequest entity);
}
