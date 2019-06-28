package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.jinchuang.TotalInvestAndInterestEntity;
import com.hyjf.data.mongo.BaseMongoDao;
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
