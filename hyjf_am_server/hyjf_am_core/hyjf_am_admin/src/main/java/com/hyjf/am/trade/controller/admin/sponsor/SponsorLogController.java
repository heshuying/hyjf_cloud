package com.hyjf.am.trade.controller.admin.sponsor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.SponsorLogResponse;
import com.hyjf.am.resquest.trade.SponsorLogRequest;
import com.hyjf.am.trade.dao.model.auto.SponsorLog;
import com.hyjf.am.trade.dao.model.auto.SponsorLogExample;
import com.hyjf.am.trade.dao.model.auto.SponsorLogExample.Criteria;
import com.hyjf.am.trade.service.admin.SponsorLogService;
import com.hyjf.am.vo.trade.SponsorLogVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;



@RestController
@RequestMapping(value = "/am-trade/sponsorLog")
public class SponsorLogController {
    @Autowired
    SponsorLogService sponsorLogService;
	/**
	 * 查询列表
	 * @throws ParseException 
 	 */
    @PostMapping(value = "/sponsorLogList")
    private SponsorLogResponse sponsorLogList(@RequestBody SponsorLogRequest sponsorLogRequest) throws ParseException {
    	
    	SponsorLogExample example=new SponsorLogExample();
    	Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(sponsorLogRequest.getAdminUserName())) {
        	criteria.andUpdateUserNameEqualTo(sponsorLogRequest.getAdminUserName());
        }
        if (StringUtils.isNotEmpty(sponsorLogRequest.getBorrowNid())) {
        	criteria.andBorrowNidEqualTo(sponsorLogRequest.getBorrowNid());
        }
        if (sponsorLogRequest.getStatus()!=null) {
        	criteria.andStatusEqualTo(sponsorLogRequest.getStatus());
        }
        if(StringUtils.isNotEmpty(sponsorLogRequest.getTimeStart())&&StringUtils.isNotEmpty(sponsorLogRequest.getTimeEnd())) {
        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	criteria.andUpdateTimeBetween(simpleDateFormat.parse(GetDate.getDayStart(sponsorLogRequest.getTimeStart())), simpleDateFormat.parse(GetDate.getDayEnd(sponsorLogRequest.getTimeEnd())));
        }
        

		int count=sponsorLogService.countSponsorLog(example);
		if (count > 0) {
			Paginator paginator = new Paginator(sponsorLogRequest.getCurrPage(),count,sponsorLogRequest.getPageSize());
			example.setLimitStart(paginator.getOffset());
			example.setLimitEnd(paginator.getLimit());
			List<SponsorLog> recordList = sponsorLogService.getRecordList(example);
			
			SponsorLogResponse aualr = new SponsorLogResponse();
			if (recordList != null) {
				aualr.setResultList(CommonUtils.convertBeanList(recordList,SponsorLogVO.class));
				aualr.setRecordTotal(count);
				aualr.setRtn(Response.SUCCESS);
			}
			return aualr;
		}
        return new SponsorLogResponse();
    }
    @PostMapping(value = "/deleteSponsorLog")
    private SponsorLogResponse deleteSponsorLog(@RequestBody SponsorLogRequest sponsorLogRequest) {
    	int i=sponsorLogService.deleteSponsorLog(sponsorLogRequest);
    	SponsorLogResponse aualr = new SponsorLogResponse();
    	if(i>0) {
    		aualr.setRtn(Response.SUCCESS);
    	}else {
    		aualr.setRtn(Response.ERROR);
    		aualr.setMessage(Response.ERROR_MSG);
    	}
    	return aualr;
    }
    @PostMapping(value = "/selectSponsorLog")
    private SponsorLogResponse selectSponsorLog(@RequestBody SponsorLogRequest sponsorLogRequest) {

        return new SponsorLogResponse();
    }
    @PostMapping(value = "/insertSponsorLog")
    private SponsorLogResponse insertSponsorLog(@RequestBody SponsorLogRequest sponsorLogRequest) {
    	int i=sponsorLogService.insertSponsorLog(sponsorLogRequest);
    	SponsorLogResponse aualr = new SponsorLogResponse();
    	if(i>0) {
    		aualr.setRtn(Response.SUCCESS);
    	}else {
    		aualr.setRtn(Response.ERROR);
    		aualr.setMessage(Response.ERROR_MSG);
    	}
    	return aualr;
    }
    @PostMapping(value = "/updateSponsorLog")
    private SponsorLogResponse updateSponsorLog(@RequestBody SponsorLogRequest sponsorLogRequest) {
    	int i=sponsorLogService.updateSponsorLog(sponsorLogRequest);
    	SponsorLogResponse aualr = new SponsorLogResponse();
    	if(i>0) {
    		aualr.setRtn(Response.SUCCESS);
    	}else {
    		aualr.setRtn(Response.ERROR);
    		aualr.setMessage(Response.ERROR_MSG);
    	}
    	return aualr;
    }
}
