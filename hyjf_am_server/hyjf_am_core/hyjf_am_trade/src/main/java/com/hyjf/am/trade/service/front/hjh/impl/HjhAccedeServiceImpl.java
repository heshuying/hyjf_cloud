/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.dao.model.customize.PlanDetailCustomize;
import com.hyjf.am.trade.service.front.hjh.HjhAccedeService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
     * 判断用户是否有持有中的计划。如果有，则不能解除出借授权和债转授权
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
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public HjhAccede doGetHjhAccedeByAccedeOrderId(String accedeOrderId) {
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
        return this.hjhAccedeMapper.updateByPrimaryKeySelective(hjhAccede);
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
        // 订单状态处于自动出借中和自动投标成功(0,2) 2018年6月27日14:16:05
        criteria.andOrderStatusIn(Arrays.asList(0,2));
        List<HjhAccede> accedeList = hjhAccedeMapper.selectByExample(hjhAccedeExample);
        return accedeList;
    }


    /**
     * 订单出借异常短信预警
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

        /**
         * 计划订单的匹配期计算新规则  : 2019-02-25
         * 1,当前时间前推30天之内的数据.
         * 2,异常状态码个位数为:0,1,2 三种状态的异常数据
         */

        // 当前日期
        Date nowDate = GetDate.getDate();
        // 当前日期格式化为 yyy-MM-dd 格式
        String nowDateStr = GetDate.formatDate(nowDate);

        // 30 天前的日期
        Date thirtyDayAgo = GetDate.getTodayBeforeOrAfter(-29);
        // 30天前的日期格式化为 yyy-MM-dd 格式
        String thirtyDayAgoStr = GetDate.formatDate(thirtyDayAgo);

        Boolean updateStatus = this.batchHjhAccedeCustomizeMapper.updateMatchDatesTwo(thirtyDayAgoStr, nowDateStr) >= 0 ? true : false;
        logger.info("未进入锁定期的计划订单的匹配期:" + GetDate.formatTime2() + ", 状态:" + updateStatus);
        // 获取发生异常且开始计息时间为空的的数据并更新其匹配期 add by huanghui end
        return updateStatus;
    }

    /**
     * 根据用户ID查询用户加入记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<HjhAccede> selectHjhAccedeListByUserId(Integer userId) {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        example.setOrderByClause("id ASC");
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        return list;
    }
}
