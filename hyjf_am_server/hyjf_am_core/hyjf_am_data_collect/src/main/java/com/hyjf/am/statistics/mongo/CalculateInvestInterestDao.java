package com.hyjf.am.statistics.mongo;

import com.hyjf.am.statistics.bean.AccountWebList;
import com.hyjf.am.statistics.bean.CalculateInvestInterest;
import org.springframework.stereotype.Repository;

/**
 * @Description 网站收益  运营数据
 * @Author sunss
 * @Date 2018/7/7 15:43
 */
@Repository
public class CalculateInvestInterestDao extends BaseMongoDao<CalculateInvestInterest> {

    @Override
    protected Class<CalculateInvestInterest> getEntityClass() {
        return CalculateInvestInterest.class;
    }
}
