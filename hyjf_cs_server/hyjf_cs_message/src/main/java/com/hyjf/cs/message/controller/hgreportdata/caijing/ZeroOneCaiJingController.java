package com.hyjf.cs.message.controller.hgreportdata.caijing;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.service.hgreportdata.caijing.CaiJingPresentationLogService;
import com.hyjf.cs.message.service.hgreportdata.caijing.ZeroOneCaiJingService;
import com.hyjf.cs.message.service.hgreportdata.caijing.impl.ZeroOneCaiJingServiceImpl;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Calendar;

/**
 * 零壹财经数据推送
 * @Author: yinhui
 * @Date: 2019/6/3 10:29
 * @Version 1.0
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/zeroOneCaiJingController")
public class ZeroOneCaiJingController {

    private final Logger logger = LoggerFactory.getLogger(ZeroOneCaiJingController.class);

    @Autowired
    private ZeroOneCaiJingService zeroOneCaiJingService;
    @Autowired
    private CaiJingPresentationLogService presentationLogService;

    private static final String INVESTRECORD = "出借记录";
    private static final String BORROWRECORD = "借款记录";
    private static final String ADVANCEREPAY = "提前还款";

    /**
     * 零壹财经记录报送
     */
    @RequestMapping("/investRecordSub")
    public void investRecordSub(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = GetDate.date_sdf.format(cal.getTime());
        //借款记录报送
        zeroOneCaiJingService.borrowRecordSub(yesterday,yesterday);
        // 出借记录报送
        zeroOneCaiJingService.investRecordSub(yesterday,yesterday);
        // 提前还款报送
        zeroOneCaiJingService.advancedRepay(yesterday,yesterday);
    }

    /**
     * 重新投资记录报送接口
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("investRecord/{startDate}/{endDate}")
    public BooleanResponse investRecord(@PathVariable String startDate, @PathVariable String endDate) {
        BooleanResponse response = new BooleanResponse();
        deleteLog(INVESTRECORD, startDate, endDate);
        zeroOneCaiJingService.investRecordSub(startDate, endDate);
        response.setResultBoolean(true);
        return response;
    }

    /**
     * 重新借款记录报送接口
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("borrowRecord/{startDate}/{endDate}")
    public BooleanResponse borrowRecord(@PathVariable String startDate, @PathVariable String endDate) {
        BooleanResponse response = new BooleanResponse();
        deleteLog(BORROWRECORD, startDate, endDate);
        zeroOneCaiJingService.borrowRecordSub(startDate, endDate);
        response.setResultBoolean(true);
        return response;
    }

    /**
     * 重新提前还款接口报送
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("advancedRepay/{startDate}/{endDate}")
    public BooleanResponse advancedRepay(@PathVariable String startDate, @PathVariable String endDate) {
        BooleanResponse response = new BooleanResponse();
        deleteLog(ADVANCEREPAY, startDate, endDate);
        zeroOneCaiJingService.advancedRepay(startDate, endDate);
        response.setResultBoolean(true);
        return response;
    }

    /**
     * 历史数据记录报送
     */
    @RequestMapping("/historySub")
    public void historySub() {
        Integer time[] = {4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7};
        StringBuilder startDate = null;
        StringBuilder endDate = null;
        try{
            for (int i =0;i<time.length;i++) {
                startDate = new StringBuilder();
                endDate = new StringBuilder();

                if(i > 8){
                    startDate.append("2019-").append(time[i]).append("-01");
                    endDate.append("2019-").append(time[i]).append("-32");
                }else{
                    startDate.append("2018-").append(time[i]).append("-01");
                    endDate.append("2018-").append(time[i]).append("-32");
                }

                //借款记录报送
                zeroOneCaiJingService.borrowRecordSub(startDate.toString(),endDate.toString());
                // 出借记录报送
                zeroOneCaiJingService.investRecordSub(startDate.toString(),endDate.toString());
                // 提前还款报送
                zeroOneCaiJingService.advancedRepay(startDate.toString(),endDate.toString());
            }
        }catch (Exception e){
            logger.error("历史数据记录报送错误 error:", e);
        }


    }

    private void deleteLog(String logType, String startDate, String endDate) {
        CaiJingLogRequest request = new CaiJingLogRequest();
        request.setLogType(logType);
        request.setPresentationTimeStart(startDate);
        request.setPresentationTimeEnd(endDate);
        presentationLogService.deleteLog(request);
    }

    /**
     * 测试零壹财经记录报送
     */
    @RequestMapping("/ceshiInvestRecordSub")
    public void ceshiInvestRecordSub(){
        Calendar cal = Calendar.getInstance();
        String yesterday = GetDate.date_sdf.format(cal.getTime());

        deleteLog(BORROWRECORD, yesterday, yesterday);
        deleteLog(INVESTRECORD, yesterday, yesterday);
        deleteLog(ADVANCEREPAY, yesterday, yesterday);

        //借款记录报送
        zeroOneCaiJingService.borrowRecordSub(yesterday,yesterday);
        // 出借记录报送
        zeroOneCaiJingService.investRecordSub(yesterday,yesterday);
        // 提前还款报送
        zeroOneCaiJingService.advancedRepay(yesterday,yesterday);
    }
}
