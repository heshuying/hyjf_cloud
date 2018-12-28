package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.ActivityListMapper;
import com.hyjf.am.market.dao.mapper.auto.PerformanceReturnDetailMapper;
import com.hyjf.am.market.dao.mapper.customize.market.NaMiMarketingCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.market.service.NaMiMarketingService;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.common.util.ActivityDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketingServiceImpl, v0.1 2018/12/26 15:45
 */
@Service
public class NaMiMarketingServiceImpl implements NaMiMarketingService {
    @Autowired
    public NaMiMarketingCustomizeMapper naMiMarketingCustomizeMapper;
    @Autowired
    public ActivityListMapper activityListMapper;

    @Autowired
    public PerformanceReturnDetailMapper performanceReturnDetailMapper;

    /**
     * 查询活动是否开始
     * @param activityId
     * @return
     */
    public ActivityList checkActivityIfAvailable(String activityId) {
        if (activityId == null) {
            return null;
        }
        return activityListMapper.selectByPrimaryKey(new Integer(activityId));
    }
    @Override
    public List<Integer> selectNaMiMarketingCount(Map<String, Object> paraMap) {
        //获取活动时间
        ActivityList activityList = checkActivityIfAvailable(ActivityDateUtil.RETURNCASH_ACTIVITY_ID);
        if(activityList==null){
            return null;
        }
        //查询活动期间的合伙人
        List<User> id0 =naMiMarketingCustomizeMapper.selectId0List(activityList);
        if(CollectionUtils.isEmpty(id0)){
            return null;
        }
        paraMap.put("id0",id0);
        paraMap.put("activityList",activityList);
        //查询符合条件的用户id
        List<Integer> ids =new ArrayList<Integer>();
        String refferName= (String)paraMap.get("refferName");
        if(StringUtils.isNotEmpty(refferName)){
            //根据邀请人账户名，查询邀请人id
            int refferId = naMiMarketingCustomizeMapper.selectRefferIdByName(refferName);
            paraMap.put("refferId",refferId);
        }
        //查询活动期间的不是合伙人的好友
        return naMiMarketingCustomizeMapper.selectIdList(paraMap);
    }

    @Override
    public List<NaMiMarketingVO> selectNaMiMarketingList(Map<String, Object> paraMap) {
        return naMiMarketingCustomizeMapper.selectNaMiMarketingList(paraMap);
    }

    @Override
    public Integer selectNaMiMarketingPerfanceCount(Map<String, Object> paraMap) {
        return  naMiMarketingCustomizeMapper.selectNaMiMarketingPerfanceCount(paraMap);
    }
    @Override
    public int selectNaMiMarketingRefferCount(Map<String, Object> paraMap){
        return  naMiMarketingCustomizeMapper.selectNaMiMarketingRefferCount(paraMap);

    }

    @Override
    public List<NaMiMarketingVO> selectNaMiMarketingPerfanceList(Map<String, Object> paraMap) {
        return naMiMarketingCustomizeMapper.selectNaMiMarketingPerfanceList(paraMap);
    }
    @Override
    public List<NaMiMarketingVO> selectNaMiMarketingRefferList(Map<String, Object> paraMap){
        return naMiMarketingCustomizeMapper.selectNaMiMarketingRefferList(paraMap);

    }
    /**
     * 查询邀请人返现统计 条数
     * @param paraMap
     * @return
     */
    @Override
    public int selectNaMiMarketingRefferTotalCount(Map<String, Object> paraMap){
        return naMiMarketingCustomizeMapper.selectNaMiMarketingRefferTotalCount(paraMap);
    }

    /**
     * 查询邀请人返现统计 列表
     * @param paraMap
     * @return
     */
    @Override
    public List<NaMiMarketingVO> selectNaMiMarketingRefferTotalList(Map<String, Object> paraMap){
        return naMiMarketingCustomizeMapper.selectNaMiMarketingRefferTotalList(paraMap);
    }

    /**
     * 查询邀请人返现统计 合计
     * @param paraMap
     * @return
     */
    @Override
    public BigDecimal selectNaMiMarketingRefferTotalAmount(Map<String, Object> paraMap) {
        return naMiMarketingCustomizeMapper.selectNaMiMarketingRefferTotalAmount(paraMap);
    }
    @Override
    public List<PerformanceReturnDetail> selectNaMiMarketingPerfanceInfo(NaMiMarketingRequest request) {
        List<PerformanceReturnDetail> returnDetails= new ArrayList<>();
        ActivityList activityList = checkActivityIfAvailable(ActivityDateUtil.RETURNCASH_ACTIVITY_ID);
        if(activityList==null){
            return null;
        }

        //查询所有合伙人
        List<User> users =naMiMarketingCustomizeMapper.selectId0List(activityList);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        List<String> userName=new ArrayList<>();
        for(User u:users){
            userName.add(u.getUsername());
        }
        //查询当前业绩
        PerformanceReturnDetail detail =  performanceReturnDetailMapper.selectByPrimaryKey(request.getId());

        return selectReturnDetailList(returnDetails,detail, userName);

    }

    /**
     * 迭代查询 所有朋友
     */
    public  List<PerformanceReturnDetail> selectReturnDetailList(List<PerformanceReturnDetail> returnDetails,PerformanceReturnDetail detail,List<String> users){
        //判断是否是合伙人
        if(users.contains(detail.getUserName())){
            returnDetails.add(detail);
            return returnDetails;
        }
        returnDetails.add(detail);
        //根据推荐人，查询上级好友
        PerformanceReturnDetail details=naMiMarketingCustomizeMapper.selectReturnDetail(detail.getRefferName());
        if(details == null){
            return returnDetails;
        }
        return selectReturnDetailList(returnDetails, details,users);
    }
}
