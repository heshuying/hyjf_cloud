package com.hyjf.am.market.controller.admin.activity;

import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.market.service.NaMiMarketingService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.am.vo.admin.PerformanceReturnDetailVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketController, v0.1 2018/12/26 15:21
 */
@RestController
@RequestMapping("/am-market/activity")
public class NaMiMarketController {
    @Autowired
    NaMiMarketingService naMiMarketingService;

    @PostMapping("/getNaMiMarketingList")
    public NaMiMarketingResponse selectActivityList(@RequestBody NaMiMarketingRequest request){
        NaMiMarketingResponse response = new NaMiMarketingResponse();
        Map<String,Object> req=new HashMap<String, Object>();
        List<Integer> ids = naMiMarketingService.selectNaMiMarketingCount(req);
        if (!CollectionUtils.isEmpty(ids)) {
            Paginator paginator = new Paginator(request.getCurrPage(), ids.size(),request.getPageSize());
            req.put("limitStart",paginator.getOffset());
            req.put("limitEnd",paginator.getLimit());
            req.put("ids",ids);
            List<NaMiMarketingVO> list = this.naMiMarketingService.selectNaMiMarketingList(req);
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setCount(ids.size());
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }

    /**
     * 查询业绩返现详情列表
     * @param request
     */
    @PostMapping("/performanceInit")
    private NaMiMarketingResponse performanceCreatePage(@RequestBody NaMiMarketingRequest request) {
        NaMiMarketingResponse response = new NaMiMarketingResponse();
        Map<String, Object> paraMap  = beanToMap(request);
        List<NaMiMarketingVO> list = null;
        int count = this.naMiMarketingService.selectNaMiMarketingPerfanceCount(paraMap);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
            paraMap.put("limitStart",paginator.getOffset());
            paraMap.put("limitEnd",paginator.getLimit());
            list = this.naMiMarketingService.selectNaMiMarketingPerfanceList(paraMap);
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setCount(count);
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }

    /**
     * 业绩返现详情
     * @param request
     * @return
     */
    @PostMapping("/performanceInfo")
    public NaMiMarketingResponse performanceInfo(NaMiMarketingRequest request) {
        NaMiMarketingResponse response = new NaMiMarketingResponse();
        Map<String, PerformanceReturnDetailVO> map = response.getMap();
        //id为空，不查询
        if(null != request.getId()&& request.getId().intValue() >0){
            //根据id查询上级推荐人
            List<PerformanceReturnDetail> performanceReturnDetailVOS=this.naMiMarketingService.selectNaMiMarketingPerfanceInfo(request);
            List<PerformanceReturnDetailVO> recordList = CommonUtils.convertBeanList(performanceReturnDetailVOS, PerformanceReturnDetailVO.class);
            if(!CollectionUtils.isEmpty(recordList)){
                BigDecimal returnAmount = recordList.get(0).getReturnAmount();
                if(recordList.size()==1){
                    map.put("A", recordList.get(0));
                    response.setActive(1);
                }else if(recordList.size()==2){
                    map.put("B", recordList.get(0));
                    recordList.get(1).setReturnAmount(returnAmount);
                    map.put("A", recordList.get(1));
                    response.setActive(2);
                } else if(recordList.size()==3){
                    BigDecimal ret=returnAmount.divide(new BigDecimal(2),2,BigDecimal.ROUND_DOWN);
                    map.put("C", recordList.get(0));
                    recordList.get(1).setReturnAmount(ret);
                    map.put("B", recordList.get(1));
                    recordList.get(2).setReturnAmount(ret);
                    map.put("A", recordList.get(2));
                    response.setActive(3);
                }else if(recordList.size()==4){
                    BigDecimal ret=returnAmount.divide(new BigDecimal(3),2,BigDecimal.ROUND_DOWN);
                    map.put("D", recordList.get(0));
                    recordList.get(1).setReturnAmount(ret);
                    map.put("C", recordList.get(1));
                    recordList.get(2).setReturnAmount(ret);
                    map.put("B", recordList.get(2));
                    recordList.get(3).setReturnAmount(ret);
                    map.put("A", recordList.get(3));
                    response.setActive(4);
                }
            }
        }
        return response;
    }

    public Map<String, Object> beanToMap(NaMiMarketingRequest request){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(request.getUsername())){
            paraMap.put("username", request.getUsername().trim());
        }
        if(StringUtils.isNotBlank(request.getTruename())){
            paraMap.put("truename", request.getTruename().trim());
        }
        if(StringUtils.isNotBlank(request.getRefferName())){
            paraMap.put("refferName", request.getRefferName().trim());
        }
        if(StringUtils.isNotBlank(request.getCol())){
            paraMap.put("col", request.getCol().trim());
        }
        if(StringUtils.isNotBlank(request.getSort())){
            paraMap.put("sort", request.getSort().trim());
        }
        if(StringUtils.isNotBlank(request.getProductType())){
            paraMap.put("productType", request.getProductType().trim());
        }
        if(StringUtils.isNotBlank(request.getProductNo())){
            paraMap.put("productNo", request.getProductNo().trim());
        }
        if(StringUtils.isNotBlank(request.getJoinTimeStart())){
            paraMap.put("joinTimeStart", request.getJoinTimeStart().trim()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(request.getJoinTimeEnd())){
            paraMap.put("joinTimeEnd", request.getJoinTimeEnd().trim()+" 23:59:59");
        }
        return paraMap;
    }

}
