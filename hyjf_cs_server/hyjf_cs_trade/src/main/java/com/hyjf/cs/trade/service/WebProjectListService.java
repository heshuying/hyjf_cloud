package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.util.Result;

public interface WebProjectListService {

    public Result getHomeProductList(ProjectListRequest request);
}
