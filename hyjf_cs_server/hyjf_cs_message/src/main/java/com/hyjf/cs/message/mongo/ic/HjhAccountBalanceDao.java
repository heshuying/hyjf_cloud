/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.HjhAccountBalance;
import com.hyjf.cs.message.bean.ic.HjhPlanCapital;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceDao, v0.1 2018/8/2 11:14
 */
@Repository
public class HjhAccountBalanceDao extends BaseMongoDao<HjhAccountBalance> {

    @Override
    protected Class<HjhAccountBalance> getEntityClass() {
        return HjhAccountBalance.class;
    }

    public List<HjhAccountBalanceVO> selectHjhAccountBalanceList(HjhAccountBalanceVO hjhAccountBalanceVO) {
        Query query = new Query();
        Criteria criteria = Criteria.where("date").is(hjhAccountBalanceVO.getDate());
        query.addCriteria(criteria);
        return CommonUtils.convertBeanList(this.find(query), HjhAccountBalanceVO.class);
    }

    public boolean updateHjhAccountBalance(HjhAccountBalanceVO hjhAccountBalanceVO) {
        Query query = new Query();
        Criteria criteria = Criteria.where("date").is(hjhAccountBalanceVO.getDate());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("investAccount", hjhAccountBalanceVO.getReinvestAccount())
                .set("creditAccount", hjhAccountBalanceVO.getCreditAccount())
                .set("reinvestAccount", hjhAccountBalanceVO.getCreditAccount())
                .set("addAccount", hjhAccountBalanceVO.getCreditAccount())
                .set("updateTime", GetDate.getDate()).set("delFlg", 0);
        this.update(query, update);
        return true;
    }

    public boolean insertHjhAccountBalance(HjhAccountBalanceVO hjhAccountBalanceVO) {
        HjhAccountBalance hjhAccountBalance = CommonUtils.convertBean(hjhAccountBalanceVO, getEntityClass());
        hjhAccountBalance.setCreateTime(GetDate.getNowTime10());
        hjhAccountBalance.setDelFlg(0);
        this.insert(hjhAccountBalance);
        return true;
    }
}
