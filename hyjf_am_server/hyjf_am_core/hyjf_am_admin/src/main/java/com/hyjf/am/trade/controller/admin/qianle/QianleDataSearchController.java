package com.hyjf.am.trade.controller.admin.qianle;

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version QianleDataSearchController, v0.1 2018/8/24 9:36
 */
@RestController
@RequestMapping("am-admin/qianle")
public class QianleDataSearchController {
    @Autowired
    QianleDataSearchService qianleDataSearchService;

    /**
     * 查询散标列表
     * @param dataSearchRequest
     * @return
     */
    @RequestMapping(value = "/querysanlist")
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
            response.setMoney(findMoneyData(dataSearchRequest));
            response.setResultList(dataSearchCustomizes);
            response.setCount(total);
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
            response.setMoney(findMoneyData(dataSearchRequest));
            response.setResultList(dataSearchCustomizes);
            response.setCount(total);
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
            response.setMoney(findMoneyData(dataSearchRequest));
            response.setResultList(dataSearchCustomizes);
            response.setCount(total);
        }
        return response;
    }

    /**
     * 查询散标金额
     * @return
     */
    @RequestMapping(value = "querySanMoney")
    public DataSearchCustomizeResponse querySanMoney(@RequestBody DataSearchRequest dataSearchRequest) {
        DataSearchCustomizeResponse response = new DataSearchCustomizeResponse();
        Map<String, Object> money = qianleDataSearchService.querySanMoney(dataSearchRequest);
        response.setMoney(money);
        return response;
    }

    /**
     * 查询计划金额
     * @return
     */
    @RequestMapping(value = "queryPlanMoney")
    public DataSearchCustomizeResponse queryPlanMoney(@RequestBody DataSearchRequest dataSearchRequest) {
        DataSearchCustomizeResponse response = new DataSearchCustomizeResponse();
        Map<String, Object> money = qianleDataSearchService.queryPlanMoney(dataSearchRequest);
        response.setMoney(money);
        return response;
    }


    /**
     * 查詢千乐金额数据
     * @return
     */
    public Map<String, Object> findMoneyData(DataSearchRequest dataSearchRequest) {

        String type = dataSearchRequest.getType();
        HashMap<String, Object> req = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> maps = null;
        Map<String, Object> maps2 = null;

        BigDecimal summoney = new BigDecimal(0);
        BigDecimal summoney1 = new BigDecimal(0);
        BigDecimal yearmoney = new BigDecimal(0);
        BigDecimal yearmoney1 = new BigDecimal(0);
        BigDecimal commission = new BigDecimal(0);
        BigDecimal commission1 = new BigDecimal(0);

        if (StringUtils.equals(type,"1")) {
            maps =qianleDataSearchService.querySanMoney(dataSearchRequest);
            maps2 =qianleDataSearchService.queryPlanMoney(dataSearchRequest);
            if (maps != null) {
                summoney = (BigDecimal) (maps.get("summoney"));
                yearmoney = (BigDecimal) maps.get("yearmoney");
                commission = (BigDecimal) maps.get("commission");
            }
            if (maps2 != null) {
                summoney1 = (BigDecimal) maps2.get("summoney");
                yearmoney1 = (BigDecimal) maps2.get("yearmoney");
                commission1 = (BigDecimal) maps2.get("commission");
            }
            BigDecimal sum = summoney.add(summoney1);
            BigDecimal year = yearmoney.add(yearmoney1);
            BigDecimal com = commission.add(commission1);
            res.put("summoney", sum);
            res.put("yearmoney", year);
            res.put("commission", com);

        }else  if (StringUtils.equals(type,"2")) {
            res =qianleDataSearchService.queryPlanMoney(dataSearchRequest);
        }else  if (StringUtils.equals(type,"3")) {
            res =qianleDataSearchService.querySanMoney(dataSearchRequest);
        }
        if (res == null) {
            res=new HashMap<>();
            res.put("summoney", summoney);
            res.put("yearmoney", yearmoney);
            res.put("commission", commission);
        }
        return res;
    }
}
