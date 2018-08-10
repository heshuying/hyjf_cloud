package com.hyjf.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.MspApplyService;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
@Service
public class MspApplyServiceImpl implements MspApplyService{
	@Autowired
	private AmUserClient mspApplyClient;
	@Override
	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.getRecordList(mspApplytRequest);
	}
	@Override
	public MspApplytResponse infoAction() {
		return mspApplyClient.infoAction();
	}
	@Override
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.insertAction(mspApplytRequest);
	}
	@Override
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.updateAction(mspApplytRequest);
	}
	@Override
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.deleteRecordAction(mspApplytRequest);
	}
	@Override
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.validateBeforeAction(mspApplytRequest);
	}
	@Override
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.applyInfo(mspApplytRequest);
	}
	@Override
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.shareUser(mspApplytRequest);
	}
	@Override
	public MspApplytResponse download(MspApplytRequest mspApplytRequest) {
		return mspApplyClient.download(mspApplytRequest);
	}
	@Override
	public MspResponse searchAction(MspRequest mspRequest) {
		
		return mspApplyClient.searchAction(mspRequest);
	}
	@Override
	public MspResponse infoAction(MspRequest mspRequest) {
		
		return mspApplyClient.infoAction(mspRequest);
	}
	@Override
	public MspResponse insertAction(MspRequest mspRequest) {
		
		return mspApplyClient.insertAction(mspRequest);
	}
	@Override
	public MspResponse updateAction(MspRequest mspRequest) {
		
		return mspApplyClient.updateAction(mspRequest);
	}
	@Override
	public MspResponse configureNameError(MspRequest mspRequest) {
		
		return mspApplyClient.configureNameError(mspRequest);
	}
	@Override
	public MspResponse deleteAction(MspRequest mspRequest) {
		
		return mspApplyClient.deleteAction(mspRequest);
	}
	@Override
	public MspResponse checkAction(MspRequest mspRequest) {
	
		return mspApplyClient.checkAction(mspRequest);
	}

}
