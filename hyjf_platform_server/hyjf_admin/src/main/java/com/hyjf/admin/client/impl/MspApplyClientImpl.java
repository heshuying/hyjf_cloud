package com.hyjf.admin.client.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.MspApplyClient;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;

@Service
public class MspApplyClientImpl implements MspApplyClient{
    @Autowired
    private RestTemplate restTemplate;

	@Override
	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/init" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse infoAction() {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/infoAction" ,null,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/insertAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/updateAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/deleteRecordAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/validateBeforeAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/applyInfo" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/shareUser" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse download(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapply/download" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspResponse searchAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/searchAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse infoAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/infoAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse insertAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/searchAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse updateAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/updateAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse configureNameError(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/configureNameError" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse deleteAction(MspRequest mspRequest) {
		 MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/deleteAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse checkAction(MspRequest mspRequest) {
		 MspResponse mspResponse = restTemplate
					.postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/checkAction" ,mspRequest,
							MspResponse.class)
					.getBody();
			if (mspResponse != null) {
				return mspResponse;
			}
			return null;
	}


}
