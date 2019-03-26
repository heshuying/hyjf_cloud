package com.hyjf.am.market.controller.admin.activity;

import com.hyjf.am.market.service.NewYearNineteenService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.response.admin.NewYearActivityRewardResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import com.hyjf.am.vo.admin.NewYearActivityRewardVO;
import com.hyjf.am.vo.admin.NewYearActivityVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
     *
     * @param newYearNineteenRequestBean
     * @return
     */
    @PostMapping("/selectInvestList")
    public NewYearActivityResponse init(@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) {
        NewYearActivityResponse response = new NewYearActivityResponse();
        //请求参数处理
        Map<String, Object> paraMap = beanToMap(newYearNineteenRequestBean);
        Integer count = this.newYearNineteenService.selectInvestCount(paraMap);
        if (count != null && count > 0) {
            response.setTotal(count);
            Paginator paginator = new Paginator(newYearNineteenRequestBean.getCurrPage(), count, newYearNineteenRequestBean.getPageSize() == 0 ? 10 : newYearNineteenRequestBean.getPageSize());
            paraMap.put("limitStart", paginator.getOffset());
            paraMap.put("limitEnd", paginator.getLimit());
            List<NewYearActivityVO> recordList = this.newYearNineteenService.selectInvestList(paraMap);
            if (!CollectionUtils.isEmpty(recordList)) {
                response.setResultList(recordList);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }


    /**
     * 获取奖励明细列表
     *
     * @param requestBean
     * @return
     */
    @RequestMapping("/getAwardList")
    public NewYearActivityRewardResponse getAwardList(@RequestBody @Valid NewYearNineteenRequestBean requestBean) {
        NewYearActivityRewardResponse response = new NewYearActivityRewardResponse();
        Map<String, Object> paraMap = beanToMap(requestBean);
        int count = newYearNineteenService.selectAwardCount(paraMap);
        if (count > 0) {
            response.setTotal(count);
            Paginator paginator = new Paginator(requestBean.getCurrPage(), count, requestBean.getPageSize());
            List<NewYearActivityRewardVO> rewardList = newYearNineteenService.selectAwardList(paraMap, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(rewardList)) {
                response.setResultList(rewardList);
            }
        }
        return response;
    }

    /**
     * 获取奖励明细详情
     * @param request
     * @return
     */
    @RequestMapping("/getAwardInfo")
    public NewYearActivityRewardResponse getAwardInfo(@RequestBody @Valid NewYearNineteenRequestBean request) {
        NewYearActivityRewardResponse response = new NewYearActivityRewardResponse();
        NewYearActivityRewardVO activityRewardVO = newYearNineteenService.selectAwardInfo(request.getId());
        if (activityRewardVO != null) {
            response.setResult(activityRewardVO);
        }
        return response;
    }

    /**
     * 修改奖励发放状态
     * @param request
     * @return
     */
    @RequestMapping("/updateStatus")
    public BooleanResponse updateStatus(@RequestBody @Valid NewYearNineteenRequestBean request) {
        BooleanResponse response = new BooleanResponse();
        NewYearActivityRewardVO activityRewardVO = new NewYearActivityRewardVO();
        activityRewardVO.setStatus(request.getStatus());
        activityRewardVO.setId(request.getId());
        boolean result = newYearNineteenService.updateAwardStatus(activityRewardVO);
        response.setResultBoolean(result);
        return response;
    }

    private Map<String, Object> beanToMap(NewYearNineteenRequestBean requestBean) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(requestBean.getUsername())) {
            paraMap.put("username", requestBean.getUsername().trim());
        }
        if (StringUtils.isNotBlank(requestBean.getTruename())) {
            paraMap.put("truename", requestBean.getTruename().trim());
        }
        if (requestBean.getStatus() != null) {
            paraMap.put("status", requestBean.getStatus());
        }
        if (StringUtils.isNotBlank(requestBean.getCol())) {
            paraMap.put("col", requestBean.getCol().trim());
        }
        if (StringUtils.isNotBlank(requestBean.getSort())) {
            paraMap.put("sort", requestBean.getSort().trim());
        }
        if (StringUtils.isNotBlank(requestBean.getSortTwo())) {
            paraMap.put("sortTwo", requestBean.getSortTwo());
        }
        if (StringUtils.isNotBlank(requestBean.getColTwo())) {
            paraMap.put("colTwo", requestBean.getColTwo());
        }
        return paraMap;
    }
}