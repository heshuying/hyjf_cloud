package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.AssociatedRecordListResponse;
import com.hyjf.am.response.app.AppChannelStatisticsDetailResponse;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.*;
import com.hyjf.cs.message.mongo.ic.*;
import com.hyjf.cs.message.service.bank.BankReturnConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping("/cs-message/search")
public class MongoSeachController extends BaseController {
    @Autowired
    private AppChannelStatisticsDetailDao appChannelStatisticsDetailDao;

    @Autowired
    DirectionalTransferAssociatedRecordsDao directionalTransferAssociatedRecordsDao;

    @Autowired
    BankSmsAuthCodeDao bankSmsAuthCodeDao;

    @Autowired
    AccountWebListDao accountWebListDao;

    @Autowired
    private CalculateInvestInterestDao calculateInvestInterestDao;

    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;

    @Autowired
    private HjhPlanCapitalDao hjhPlanCapitalDao;

    @Autowired
    BankReturnConfig bankReturnConfig;

    /**
     * 查询检证日志
     * @param logOrdId
     * @return
     */
    @RequestMapping("/getRetCode/{logOrdId}")
    public String getRetCode(@PathVariable String logOrdId) {
        String result = bankReturnConfig.getRetCode(logOrdId);
        return result;
    }

    /**
     * 根据userId查询渠道投资信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getAppChannelStatisticsDetailByUserId/{userId}")
    public AppChannelStatisticsDetailResponse selectById(@PathVariable int userId) {
        AppChannelStatisticsDetail entity = appChannelStatisticsDetailDao.findByUserId(userId);
        AppChannelStatisticsDetailResponse response = new AppChannelStatisticsDetailResponse();
        if (entity != null) {
            AppChannelStatisticsDetailVO appChannelStatisticsDetailVO = new AppChannelStatisticsDetailVO();
            BeanUtils.copyProperties(entity, appChannelStatisticsDetailVO);
            response.setResult(appChannelStatisticsDetailVO);
        }
        return response;
    }

    /**
     * 查询所有渠道投资信息
     *
     * @return
     */
    @RequestMapping("/getappchannelstatisticsdetail")
    public AppChannelStatisticsDetailResponse selectAppChannelStatistics() {
        List<AppChannelStatisticsDetail> list = appChannelStatisticsDetailDao.find(new Query());
        AppChannelStatisticsDetailResponse response = new AppChannelStatisticsDetailResponse();
        if (!CollectionUtils.isEmpty(list)) {
            List<AppChannelStatisticsDetailVO> voList = CommonUtils.convertBeanList(list, AppChannelStatisticsDetailVO.class);
            response.setResultList(voList);
        }
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
    public AccountWebListResponse queryWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = new AccountWebListResponse();
        int recordTotal = (int) accountWebListDao.queryWebCount(accountWebList);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(accountWebList.getPaginatorPage(), recordTotal);
            List<AccountWebList> recordList = accountWebListDao.queryWebList(accountWebList, paginator.getOffset(), paginator.getLimit());
            if (recordList != null) {
                List<AccountWebListVO> voList = CommonUtils.convertBeanList(recordList, AccountWebListVO.class);
                response.setResultList(voList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 绝对错误的写法,list是使内存瞬间飙升  todo ....
     * @param accountWebList
     * @return
     */
    @RequestMapping(value = "/queryAccountWebList")
    public AccountWebListResponse queryAccountWebList(@RequestBody AccountWebListVO accountWebList) {
        AccountWebListResponse response = new AccountWebListResponse();
        List<AccountWebList> recordList = accountWebListDao.queryAccountWebList(accountWebList);
        int recordTotal = recordList.size();
        if (null != recordList) {
            if (recordTotal > 0) {
                Paginator paginator = new Paginator(accountWebList.getPaginatorPage(), recordTotal);
                int end = 0;
                if(recordTotal<paginator.getOffset()*paginator.getLimit()+paginator.getLimit()){
                    end=recordTotal;
                }else {
                    end = paginator.getOffset()*paginator.getLimit()+paginator.getLimit();
                }
                List<AccountWebList> recordList2 = recordList.subList(paginator.getOffset(), end);
                List<AccountWebListVO> voList = CommonUtils.convertBeanList(recordList2, AccountWebListVO.class);
                response.setResultList(voList);
            }
        }
        response.setRecordTotal(recordTotal);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @RequestMapping(value = "/selectBorrowInvestAccount")
    public String selectBorrowInvestAccount(AccountWebListVO accountWebList){
        int total = accountWebListDao.selectBorrowInvestAccount(accountWebList);
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(total);
    }

    /**
     * 获取累计投资金额
     *
     * @return
     */
	@RequestMapping(value = "/gettotalinvestmentamount")
	public CalculateInvestInterestResponse getTotalInvestmentAmount() {
		CalculateInvestInterestResponse response = new CalculateInvestInterestResponse();
		BigDecimal totalInvestmentAmount = calculateInvestInterestDao.getTotalInvestmentAmount();
		response.setInterestSum(totalInvestmentAmount);
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
    @PostMapping(value = "/getassociatedrecordscount")
    public long getDirectionalTransferCount(@RequestBody AssociatedRecordListRequest request){
        return directionalTransferAssociatedRecordsDao.getDirectionalTransferCount(request);
    }

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedRecords的list
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    @PostMapping("/searchassociatedrecordlist")
    public AssociatedRecordListResponse searchDirectionalTransferList(@RequestBody AssociatedRecordListRequest request){
        AssociatedRecordListResponse response = new AssociatedRecordListResponse();
        Long count = directionalTransferAssociatedRecordsDao.getDirectionalTransferCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count.intValue());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchDirectionalTransferList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<DirectionalTransferAssociatedRecords> directionalTransferAssociatedRecords = directionalTransferAssociatedRecordsDao.searchDirectionalTransferList(request);
        if(!CollectionUtils.isEmpty(directionalTransferAssociatedRecords)){
            List<AssociatedRecordListVo> associatedRecordListVoList = CommonUtils.convertBeanList(directionalTransferAssociatedRecords,AssociatedRecordListVo.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(associatedRecordListVoList);
        }
        return response;
    }
}
