package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.AssociatedRecordListResponse;
import com.hyjf.am.response.admin.BindLogResponse;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVO;
import com.hyjf.am.vo.admin.BindLogVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.*;
import com.hyjf.cs.message.mongo.ic.*;
import com.hyjf.cs.message.service.bank.BankReturnConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.DecimalFormat;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping("/cs-message/search")
public class MongoSeachController extends BaseController {

    @Autowired
    DirectionalTransferAssociatedRecordsDao directionalTransferAssociatedRecordsDao;

    @Autowired
    DirectionalTransferAssociatedLogDao directionalTransferAssociatedLogDao;

    @Autowired
    BankSmsAuthCodeDao bankSmsAuthCodeDao;

    @Autowired
    AccountWebListDao accountWebListDao;

    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;

    @Autowired
    BankReturnConfig bankReturnConfig;

    /**
     * 查询检证日志
     * @param logOrdId
     * @return
     */
    @RequestMapping("/getRetCode/{logOrdId}")
    public StringResponse getRetCode(@PathVariable String logOrdId) {
        StringResponse response = new StringResponse();
        String result = bankReturnConfig.getRetCode(logOrdId);
        response.setResultStr(result);
        return response;
    }



    /**
     * 企业用户是否已绑定用户
     *
     * @param userId
     * @return -1 未绑定，0初始，1成功，2失败
     * @author Michael
     */
    @RequestMapping("/isCompBindUser/{userId}")
    public int isCompBindUser(@PathVariable Integer userId) {
        int result = -1;
        DirectionalTransferAssociatedRecords entity = directionalTransferAssociatedRecordsDao.findByUserId(userId);
        if (entity != null) {
            result = entity.getAssociatedState();
        }
        return result;
    }

    @RequestMapping("/selectBankSmsSeq/{userId}/{txcodeDirectRechargeOnline}")
    public String selectBankSmsSeq(@PathVariable Integer userId, @PathVariable String txcodeDirectRechargeOnline) {
        BankSmsAuthCode smsAuthCode = bankSmsAuthCodeDao.selectBankSmsSeq(userId, txcodeDirectRechargeOnline);
        if (smsAuthCode != null) {
            return smsAuthCode.getSrvAuthCode();
        }
        return null;
    }

    @RequestMapping(value = "/queryWebCount")
	public AccountWebListResponse queryWebList(@RequestBody AccountWebListVO accountWebList) {
		AccountWebListResponse response = new AccountWebListResponse();
		int recordTotal = (int) accountWebListDao.queryWebCount(accountWebList);
		logger.debug("网站明细总条数recordTotal==" + recordTotal);
		if (recordTotal > 0) {
			Paginator paginator = new Paginator(accountWebList.getCurrPage(), recordTotal,
					accountWebList.getPageSize());
			List<AccountWebList> recordList = accountWebListDao.queryWebList(accountWebList, paginator.getOffset(),
					paginator.getLimit());
			if (recordList != null) {
				List<AccountWebListVO> voList = CommonUtils.convertBeanList(recordList, AccountWebListVO.class);
				response.setResultList(voList);
				response.setRecordTotal(recordTotal);
				response.setRtn(Response.SUCCESS);
			}
		}
		return response;
	}

    @RequestMapping(value = "/selectBorrowInvestAccount")
    public String selectBorrowInvestAccount(@RequestBody AccountWebListVO accountWebList){
        double total = accountWebListDao.selectBorrowInvestAccount(accountWebList);
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(total);
    }

    /**
     * 获取累计出借金额
     *
     * @return
     */
	@RequestMapping(value = "/gettotalinvestmentamount")
	public CalculateInvestInterestResponse getTotalInvestmentAmount() {
        CalculateInvestInterestResponse response = new CalculateInvestInterestResponse();
        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
        if (entity != null) {
            response.setInterestSum(entity.getTotalInvestAmount());
        }
		return response;
	}

	/**
	 * 查询运营统计数据
	 * @author zhangyk
	 * @date 2018/7/14 15:57
	 */
	@GetMapping("/getTotalInvestAndInterestEntity")
    public TotalInvestAndInterestResponse getTotalInvestAndInterest(){
	   TotalInvestAndInterestResponse response = new TotalInvestAndInterestResponse();
	   TotalInvestAndInterestEntity entity =  totalInvestAndInterestMongoDao.findOne(new Query());
	   if (entity != null){
	       response.setResult(CommonUtils.convertBean(entity,TotalInvestAndInterestVO.class));
       }
	   return response;

    }
    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedRecords的count
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    @PostMapping(value = "/getAssociatedRecordsCount")
    public AssociatedRecordListResponse getDirectionalTransferCount(@RequestBody AssociatedRecordListRequest request){
        AssociatedRecordListResponse response = new AssociatedRecordListResponse();
        long count = directionalTransferAssociatedRecordsDao.getDirectionalTransferCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedRecords的list
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    @PostMapping("/searchAssociatedRecordList")
    public AssociatedRecordListResponse searchDirectionalTransferList(@RequestBody AssociatedRecordListRequest request){
        AssociatedRecordListResponse response = new AssociatedRecordListResponse();
        Long count = directionalTransferAssociatedRecordsDao.getDirectionalTransferCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count.intValue(),request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchDirectionalTransferList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<DirectionalTransferAssociatedRecords> directionalTransferAssociatedRecords = directionalTransferAssociatedRecordsDao.searchDirectionalTransferList(request);
        if(!CollectionUtils.isEmpty(directionalTransferAssociatedRecords)){
            List<AssociatedRecordListVO> associatedRecordListVoList = CommonUtils.convertBeanList(directionalTransferAssociatedRecords,AssociatedRecordListVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(associatedRecordListVoList);
        }
        return response;
    }

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedLog的count
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    @PostMapping(value = "/getAssociatedLogCount")
    public BindLogResponse getDirectionalTransferLogCount(@RequestBody BindLogListRequest request){
        BindLogResponse response = new BindLogResponse();
        long count = directionalTransferAssociatedLogDao.getDirectionalTransferLogCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedLog的list
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    @PostMapping("/searchAssociatedLogList")
    public BindLogResponse searchDirectionalTransferLogList(@RequestBody BindLogListRequest request){
        BindLogResponse response = new BindLogResponse();
        Long count = directionalTransferAssociatedLogDao.getDirectionalTransferLogCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count.intValue(),request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchDirectionalTransferLogList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<DirectionalTransferAssociatedLog> directionalTransferAssociatedLogList = directionalTransferAssociatedLogDao.searchDirectionalTransferLogList(request);
        if(!CollectionUtils.isEmpty(directionalTransferAssociatedLogList)){
            List<BindLogVO> bindLogVOList = CommonUtils.convertBeanList(directionalTransferAssociatedLogList,BindLogVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(bindLogVOList);
        }
        return response;
    }


}
