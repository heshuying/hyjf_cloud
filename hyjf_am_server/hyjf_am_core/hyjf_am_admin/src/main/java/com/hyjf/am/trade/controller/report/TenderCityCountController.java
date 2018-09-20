package com.hyjf.am.trade.controller.report;

import com.hyjf.am.response.trade.TenderCityCountResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.resquest.trade.TenderCityCountRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.report.TenderCityCountService;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询 投资人按照地域分布
 * @author：yinhui
 * @Date: 2018/9/1  13:29
 */
@RestController
@RequestMapping("/am-admin/tendercitycount")
public class TenderCityCountController extends BaseController {

    @Autowired
    private TenderCityCountService tenderCityCountService;

    /**
     * 按照省份统计投资人的分布
     * @return
     */
    @PostMapping("/gettendercitygroupby")
    public TenderCityCountResponse getAccountByUserId(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        List<TenderCityCountVO> list = tenderCityCountService.getTenderCityGroupBy(request.getCreateTime());
        if (list != null) {
            response.setListTenderCityCountVO(list);
        }
        return response;
    }

    /**
     *  按照性别统计投资人的分布
     * @return
     */
    @PostMapping("/gettendersexgroupby")
    public TenderCityCountResponse getTenderSexGroupBy(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        List<TenderSexCountVO> list = tenderCityCountService.getTenderSexGroupBy(request.getCreateTime());
        if (list != null) {
            response.setListTenderSexCountVO(list);
        }
        return response;
    }

    /**
     *  按照性别统计投资人的分布
     * @return
     */
    @PostMapping("/gettenderagebyrange")
    public TenderCityCountResponse getTenderAgeByRange(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        Integer age = tenderCityCountService.getTenderAgeByRange(request.getCreateTime(),request.getFirstAge(),request.getEndAge());
        response.setAge(age);
        return response;
    }

    /**
     *  按月统计平台的交易总额
     * @return
     */
    @PostMapping("/getAccountByMonth")
    public TenderCityCountResponse getAccountByMonth(@RequestBody TzjDayReportRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        BigDecimal money = tenderCityCountService.getAccountByMonth(request.getStartTime(),request.getEndTime());
        response.setAccountMonth(money);
        return response;
    }

    /**
     *  按月统计交易笔数
     * @return
     */
    @PostMapping("/getTradeCountByMonth")
    public TenderCityCountResponse getTradeCountByMonth(@RequestBody TzjDayReportRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        int count = tenderCityCountService.getTradeCountByMonth(request.getStartTime(),request.getEndTime());
        response.setCount(count);
        return response;
    }

    /**
     *  按照性别统计投资人的分布
     * @return
     */
    @PostMapping("/getLoanNum")
    public TenderCityCountResponse getLoanNum(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        Integer count = tenderCityCountService.getLoanNum(request.getCreateTime());
        response.setCount(count);
        return response;
    }

    /**
     *  获取截至日期的投资金额
     * @return
     */
    @PostMapping("/getInvestLastDate")
    public TenderCityCountResponse getInvestLastDate(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        BigDecimal money = tenderCityCountService.getInvestLastDate(request.getCreateTime());
        response.setAccountMonth(money);
        return response;
    }

    /**
     *  统计投资人总数，截至日期为上个月的最后一天
     * @return
     */
    @PostMapping("/getTenderCount")
    public TenderCityCountResponse getTenderCount(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        Integer count = tenderCityCountService.getTenderCount(request.getCreateTime());
        response.setCount(count);
        return response;
    }

    /**
     *  平均满标时间
     * @return
     */
    @PostMapping("/getFullBillAverageTime")
    public TenderCityCountResponse getFullBillAverageTime(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        float count = tenderCityCountService.getFullBillAverageTime(request.getCreateTime());
        response.setaFloat(count);
        return response;
    }

    /**
     *  统计所有待偿金额，截至日期为上个月的最后一天
     * @return
     */
    @PostMapping("/getRepayTotal")
    public TenderCityCountResponse getRepayTotal(@RequestBody TenderCityCountRequest request) {
        TenderCityCountResponse response = new TenderCityCountResponse();
        //request.getCreateTime 上个月的最后一天
        BigDecimal money = tenderCityCountService.getRepayTotal(request.getCreateTime());
        response.setAccountMonth(money);
        return response;
    }

}
