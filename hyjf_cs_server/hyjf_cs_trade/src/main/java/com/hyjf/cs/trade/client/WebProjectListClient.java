package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.ProjectListRequest;

public interface WebProjectListClient {


    public ProjectListResponse getHomeProductList(ProjectListRequest request);

}
