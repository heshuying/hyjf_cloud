package com.hyjf.admin.service;

import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;

public interface ElectricitySalesDataPushListService {

	ElectricitySalesDataPushListResponse searchList(ElectricitySalesDataPushListRequest request);

	ElectricitySalesDataPushListResponse insertElectricitySalesDataPushList(ElectricitySalesDataPushListRequest request);

}
