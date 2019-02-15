package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import org.springframework.stereotype.Repository;

/**
 * 运营数据
 */
@Repository
public class TotalInvestAndInterestMongoDao extends BaseMongoDao<TotalInvestAndInterestEntity> {

	@Override
	protected Class<TotalInvestAndInterestEntity> getEntityClass() {
		return TotalInvestAndInterestEntity.class;
	}

}
