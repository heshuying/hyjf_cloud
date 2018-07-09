package com.hyjf.admin.client;


import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;

public interface MspApplyClient {
	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest);
	public MspApplytResponse infoAction();
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest);
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest);
	public MspApplytResponse download(MspApplytRequest mspApplytRequest);
	
	
	
	
	public MspResponse searchAction(MspRequest mspRequest);
	public MspResponse infoAction(MspRequest mspRequest);
	public MspResponse insertAction(MspRequest mspRequest);
	public MspResponse updateAction(MspRequest mspRequest);
	public MspResponse configureNameError(MspRequest mspRequest);
	public MspResponse deleteAction(MspRequest mspRequest);
	public MspResponse checkAction(MspRequest mspRequest);

}
