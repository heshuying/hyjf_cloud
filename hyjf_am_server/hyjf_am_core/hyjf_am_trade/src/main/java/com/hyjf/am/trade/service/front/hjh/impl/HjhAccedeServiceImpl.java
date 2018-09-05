/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhAccedeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhAccedeCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.dao.model.customize.PlanDetailCustomize;
import com.hyjf.am.trade.service.front.hjh.HjhAccedeService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeServiceImpl, v0.1 2018/6/25 10:24
 */
@Service
public class HjhAccedeServiceImpl extends BaseServiceImpl implements HjhAccedeService {

    /**
     * 查询退出中和待进入锁定期的标的
     * @return
     */
    @Override
    public List<HjhAccede> selectWaitQuitHjhList() {
        ArrayList<Integer> list = new ArrayList<>();
        //(退出中)准备退出计划
        list.add(5);
        //(自动投标成功)准备进入锁定期
        list.add(2);
        HjhAccedeExample accedeExample = new HjhAccedeExample();
        accedeExample.createCriteria().andOrderStatusIn(list);
        List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(accedeExample);
        if (accedeList != null && accedeList.size() > 0) {
            return accedeList;
        }
        return null;
    }
    /**
     * 判断用户是否有持有中的计划。如果有，则不能解除投资授权和债转授权
     * @param userId
     * @return
     */
    @Override
	public boolean canCancelAuth(int userId) {
		HjhAccedeExample example = new HjhAccedeExample();
		HjhAccedeExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andOrderStatusNotEqualTo(7);
		List<HjhAccede> list = hjhAccedeMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return false;
		}
		return true;
    }


    /**
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public HjhAccede getHjhAccedeByAccedeOrderId(String accedeOrderId) {
        HjhAccedeExample example = new HjhAccedeExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> hjhAccedes = this.hjhAccedeMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(hjhAccedes)){
            return hjhAccedes.get(0);
        }
        return null;
    }

    /**
     * 计算加入总数
     * @author zhangyk
     * @date 2018/6/27 19:10
     */
    @Override
    public int countAccede(String planNid) {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria criteria = example.createCriteria();
        criteria.andPlanNidEqualTo(planNid);
        int count = hjhAccedeMapper.countByExample(example);
        return count;
    }

    @Override
    public List<PlanDetailCustomize> getPlanDetail(String planNid) {
        return null;
    }

    @Override
    public int updateHjhAccedeByPrimaryKey(HjhAccede hjhAccede) {
        return this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede);
    }

    /**
     * 查询汇计划匹配期大于2天，短信预警
     * @author zhangyk
     * @date 2018/8/15 14:14
     */
    @Override
    public List<HjhAccede> getPlanMatchPeriodList() {
        // 计划订单进入匹配期时间超过2天给工作人员发送预警短信
        HjhAccedeExample hjhAccedeExample = new HjhAccedeExample();
        HjhAccedeExample.Criteria criteria = hjhAccedeExample.createCriteria();
        criteria.andMatchDatesGreaterThanOrEqualTo(2);
        // 订单状态处于自动投资中和自动投资成功(0,2) 2018年6月27日14:16:05
        criteria.andOrderStatusIn(Arrays.asList(0,2));
        List<HjhAccede> accedeList = hjhAccedeMapper.selectByExample(hjhAccedeExample);
        return accedeList;
    }


    /**
     * 订单投资异常短信预警
     * @author zhangyk
     * @date 2018/8/15 16:26
     */
    @Override
    public List<HjhAccede> getPlanOrderInvestExceptionList() {
        HjhAccedeExample hjhAccedeExample = new HjhAccedeExample();
        hjhAccedeExample.createCriteria().andOrderStatusGreaterThanOrEqualTo(80);
        List<HjhAccede> accedeList = hjhAccedeMapper.selectByExample(hjhAccedeExample);
        return accedeList;
    }

    /**
     * 更新未进入锁定期的计划订单的匹配期hjhaccede
     * @return
     */
    @Override
    public boolean updateMatchDays() {
        return this.batchHjhAccedeCustomizeMapper.updateMatchDates() >= 0 ? true : false;
    }
}
