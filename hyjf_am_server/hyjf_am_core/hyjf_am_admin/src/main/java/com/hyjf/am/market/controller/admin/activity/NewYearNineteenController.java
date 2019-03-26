package com.hyjf.am.market.controller.admin.activity;

import com.hyjf.am.market.service.NewYearNineteenService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import com.hyjf.am.vo.admin.NewYearActivityVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/25.
 */
@RestController
@RequestMapping("/am-admin/newYearNineteen")
public class NewYearNineteenController {

    @Autowired
    private NewYearNineteenService newYearNineteenService;

    /**
     * 累计年化出借金额 画面初始化
     * @param newYearNineteenRequestBean
     * @return
     */
    @PostMapping("/selectInvestList")
    public NewYearActivityResponse init(@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) {
        NewYearActivityResponse response = new NewYearActivityResponse();
        //请求参数处理
        Map<String, Object> paraMap =beanToMap(newYearNineteenRequestBean);
        Integer count = this.newYearNineteenService.selectInvestCount(paraMap);
        if (count != null && count > 0) {
            response.setTotal(count);
            Paginator paginator = new Paginator(newYearNineteenRequestBean.getCurrPage(), count,newYearNineteenRequestBean.getPageSize() == 0?10:newYearNineteenRequestBean.getPageSize());
            paraMap.put("limitStart",paginator.getOffset());
            paraMap.put("limitEnd",paginator.getLimit());
            List<NewYearActivityVO> recordList = this.newYearNineteenService.selectInvestList(paraMap);
            if(!CollectionUtils.isEmpty(recordList)){
                response.setResultList(recordList);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }


    public Map<String, Object> beanToMap(NewYearNineteenRequestBean form){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(form.getUsername())){
            paraMap.put("username", form.getUsername().trim());
        }
        if(StringUtils.isNotBlank(form.getTruename())){
            paraMap.put("truename", form.getTruename().trim());
        }
        if(form.getStatus() != null){
            paraMap.put("status", form.getStatus());
        }
        if(StringUtils.isNotBlank(form.getCol())){
            paraMap.put("col", form.getCol().trim());
        }
        if(StringUtils.isNotBlank(form.getSort())){
            paraMap.put("sort", form.getSort().trim());
        }
        if(StringUtils.isNotBlank(form.getSortTwo())){
            paraMap.put("sortTwo", form.getSortTwo());
        }
        if(StringUtils.isNotBlank(form.getColTwo())){
            paraMap.put("colTwo", form.getColTwo());
        }
        return paraMap;
    }
}