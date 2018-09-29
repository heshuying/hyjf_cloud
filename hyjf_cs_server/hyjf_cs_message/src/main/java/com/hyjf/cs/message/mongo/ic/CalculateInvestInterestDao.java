package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.CalculateInvestInterest;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 获取累计投资金额
     *
     * @return
     */
    public BigDecimal getTotalInvestmentAmount() {
        List<CalculateInvestInterest> list = mongoTemplate.findAll(CalculateInvestInterest.class);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0).getInterestSum();
        }
        return null;
    }
}
