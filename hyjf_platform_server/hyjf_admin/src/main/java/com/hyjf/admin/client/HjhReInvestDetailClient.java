package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;

/**
 * @Author : huanghui
 */
public interface HjhReInvestDetailClient {

    HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest request);
}
