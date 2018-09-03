package com.hyjf.am.trade.controller.report;

import com.hyjf.am.response.trade.TenderCityCountResponse;
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
}
