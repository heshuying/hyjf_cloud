package com.hyjf.am.statistics.controller;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.trade.AppChannelStatisticsDetailResponse;
import com.hyjf.am.statistics.bean.AccountWebList;
import com.hyjf.am.statistics.bean.AppChannelStatisticsDetail;
import com.hyjf.am.statistics.bean.BankSmsAuthCode;
import com.hyjf.am.statistics.bean.DirectionalTransferAssociatedRecords;
import com.hyjf.am.statistics.mongo.AccountWebListDao;
import com.hyjf.am.statistics.mongo.AppChannelStatisticsDetailDao;
import com.hyjf.am.statistics.mongo.BankSmsAuthCodeDao;
import com.hyjf.am.statistics.mongo.DirectionalTransferAssociatedRecordsDao;
import com.hyjf.am.vo.statistics.AccountWebListVO;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/am-statistics/search")
public class MongoSeachController {

    private static Logger logger = LoggerFactory.getLogger(MongoSeachController.class);

    @Autowired
    private AppChannelStatisticsDetailDao appChannelStatisticsDetailDao;

    @Autowired
    DirectionalTransferAssociatedRecordsDao directionalTransferAssociatedRecordsDao;

    @Autowired
    BankSmsAuthCodeDao bankSmsAuthCodeDao;

    @Autowired
    AccountWebListDao accountWebListDao;

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

    @RequestMapping(value = "/queryAccountWebList")
    public AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = new AccountWebListResponse();
        List<AccountWebList> recordList = accountWebListDao.queryAccountWebList(accountWebList);
        if (null != recordList) {
            int recordTotal = recordList.size();
            if (recordTotal > 0) {
                Paginator paginator = new Paginator(accountWebList.getPaginatorPage(), recordTotal);
                List<AccountWebList> recordList2 = recordList.subList(paginator.getOffset(), paginator.getLimit());
                List<AccountWebListVO> voList = CommonUtils.convertBeanList(recordList2, AccountWebListVO.class);
                response.setResultList(voList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    @RequestMapping(value = "/selectBorrowInvestAccount")
    public String selectBorrowInvestAccount(AccountWebListVO accountWebList){
        int total = accountWebListDao.selectBorrowInvestAccount(accountWebList);
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(total);
    }
}
