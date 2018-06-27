/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhAccedeMapper;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.service.HjhAccedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeServiceImpl, v0.1 2018/6/25 10:24
 */
@Service
public class HjhAccedeServiceImpl implements HjhAccedeService {

    @Autowired
    HjhAccedeMapper hjhAccedeMapper;

    /**
     * 查询退出中和待进入锁定期的标的
     * @return
     */
    @Override
    public List<HjhAccede> selectWaitQuitHjhList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);//(退出中)准备退出计划
        list.add(2);//(自动投标成功)准备进入锁定期
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
}
