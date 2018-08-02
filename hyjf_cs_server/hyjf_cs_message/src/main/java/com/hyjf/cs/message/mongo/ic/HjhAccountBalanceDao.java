/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.BankSmsAuthCode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceDao, v0.1 2018/8/2 11:14
 */
public class HjhAccountBalanceDao extends BaseMongoDao<HjhAccountBalanceVO>{

    @Override
    protected Class<HjhAccountBalanceVO> getEntityClass() {
            return HjhAccountBalanceVO.class;
    }

    public List<HjhAccountBalanceVO> selectHjhAccountBalanceList(HjhAccountBalanceVO hjhAccountBalance) {
        Query query = new Query();
        Criteria criteria = Criteria.where("date").is(hjhAccountBalance.getDate());
        query.addCriteria(criteria);
        return this.find(query);
    }

    public boolean updateHjhAccountBalance(HjhAccountBalanceVO hjhAccountBalance) {
        Query query = new Query();
        Criteria criteria = Criteria.where("date").is(hjhAccountBalance.getDate());

        Update update = new Update();
        update.inc("updateTime", GetDate.getNowTime10()).set("delFlg", 0);

        this.update(query, update);
        return true;
    }

    public boolean insertHjhAccountBalance(HjhAccountBalanceVO hjhAccountBalance) {
        hjhAccountBalance.setCreateTime(GetDate.getNowTime10());
        hjhAccountBalance.setDelFlg(0);
        this.insert(hjhAccountBalance);
        return true;
    }
}
