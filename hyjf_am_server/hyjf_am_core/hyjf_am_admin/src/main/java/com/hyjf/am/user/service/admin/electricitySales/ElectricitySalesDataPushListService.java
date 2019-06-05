package com.hyjf.am.user.service.admin.electricitySales;

import java.util.List;

import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;

public interface ElectricitySalesDataPushListService {

	Integer getCount(ElectricitySalesDataPushListRequest request);

	List<ElectricitySalesDataPushList> searchList(ElectricitySalesDataPushListRequest request, int i, int j);

	int insertElectricitySalesDataPushList(List<ElectricitySalesDataPushList> request);

}
