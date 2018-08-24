package com.hyjf.am.trade.controller.front.qianle;

import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.trade.service.front.qianle.QianleDataSearchService;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version QianleDataSearchController, v0.1 2018/8/24 9:36
 */
@RestController
@RequestMapping("am-trade/qianle")
public class QianleDataSearchController {
    @Autowired
    QianleDataSearchService qianleDataSearchService;

    /**
     * 查询散标列表
     * @param dataSearchRequest
     * @return
     */
    @RequestMapping(value = "querysanlist")
    public DataSearchCustomizeResponse querySanList(@RequestBody DataSearchRequest dataSearchRequest){
        DataSearchCustomizeResponse response = new DataSearchCustomizeResponse();
        Integer total = qianleDataSearchService.querySanCount(dataSearchRequest);
        if (total > 0) {
            Paginator paginator = new Paginator(dataSearchRequest.getCurrPage(), total, dataSearchRequest.getPageSize());
            List<DataSearchCustomizeVO> dataSearchCustomizes = qianleDataSearchService.querySanList(dataSearchRequest,paginator.getOffset(),paginator.getLimit());
            if (dataSearchCustomizes!=null&&!dataSearchCustomizes.isEmpty()){
                for (DataSearchCustomizeVO map : dataSearchCustomizes) {
                    Integer userId = map.getUserId();
                    Map<String, Object> stringObjectMap = qianleDataSearchService.queryFirstTender(userId);
                    String nid = stringObjectMap.get("nid") + "";
                    if (StringUtils.equals(map.getNid(), nid)) {
                        map.setFirst("是");
                    } else {
                        map.setFirst("否");
                    }
                }
            }
            response.setResultList(dataSearchCustomizes);
        }
        return response;
    }

    /**
     * 查询计划列表
     * @param dataSearchRequest
     * @return
     */
    @RequestMapping(value = "queryPlanList")
    public DataSearchCustomizeResponse queryPlanList(@RequestBody DataSearchRequest dataSearchRequest){
        DataSearchCustomizeResponse response = new DataSearchCustomizeResponse();
        Integer total = qianleDataSearchService.queryPlanCount(dataSearchRequest);
        if (total > 0) {
            Paginator paginator = new Paginator(dataSearchRequest.getCurrPage(), total, dataSearchRequest.getPageSize());
            List<DataSearchCustomizeVO> dataSearchCustomizes = qianleDataSearchService.queryPlanList(dataSearchRequest, paginator.getOffset(), paginator.getLimit());
            if (dataSearchCustomizes!=null&&!dataSearchCustomizes.isEmpty()){
                for (DataSearchCustomizeVO map : dataSearchCustomizes) {
                    Integer userId = map.getUserId();
                    Map<String, Object> stringObjectMap = qianleDataSearchService.queryFirstTender(userId);
                    String nid = stringObjectMap.get("nid") + "";
                    if (StringUtils.equals(map.getNid(), nid)) {
                        map.setFirst("是");
                    } else {
                        map.setFirst("否");
                    }
                }
            }
            response.setResultList(dataSearchCustomizes);
        }
        return response;
    }


    /**
     * 查询全部列表
     * @param dataSearchRequest
     * @return
     */
    @RequestMapping(value = "queryList")
    public DataSearchCustomizeResponse queryList(@RequestBody DataSearchRequest dataSearchRequest){
        DataSearchCustomizeResponse response = new DataSearchCustomizeResponse();
        Integer total = qianleDataSearchService.queryCount(dataSearchRequest);
        if (total > 0) {
            Paginator paginator = new Paginator(dataSearchRequest.getCurrPage(), total, dataSearchRequest.getPageSize());
            List<DataSearchCustomizeVO> dataSearchCustomizes = qianleDataSearchService.queryList(dataSearchRequest, paginator.getOffset(), paginator.getLimit());
            if (dataSearchCustomizes!=null&&!dataSearchCustomizes.isEmpty()){
                for (DataSearchCustomizeVO map : dataSearchCustomizes) {
                    Integer userId = map.getUserId();
                    Map<String, Object> stringObjectMap = qianleDataSearchService.queryFirstTender(userId);
                    String nid = stringObjectMap.get("nid") + "";
                    if (StringUtils.equals(map.getNid(), nid)) {
                        map.setFirst("是");
                    } else {
                        map.setFirst("否");
                    }
                }
            }
            response.setResultList(dataSearchCustomizes);
        }
        return response;
    }

    /**
     * 查询散标金额
     * @return
     */
    @RequestMapping(value = "querySanMoney")
    public Map<String, Object> querySanMoney(@RequestBody DataSearchRequest dataSearchRequest) {
        return qianleDataSearchService.querySanMoney(dataSearchRequest);

    }

    /**
     * 查询计划金额
     * @return
     */
    @RequestMapping(value = "queryPlanMoney")
    public Map<String, Object> queryPlanMoney(@RequestBody DataSearchRequest dataSearchRequest) {
        return  qianleDataSearchService.queryPlanMoney(dataSearchRequest);
    }
}
