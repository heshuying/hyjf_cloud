package com.hyjf.am.statistics.mongo;

import com.hyjf.am.statistics.bean.TotalInvestAndInterestEntity;
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
