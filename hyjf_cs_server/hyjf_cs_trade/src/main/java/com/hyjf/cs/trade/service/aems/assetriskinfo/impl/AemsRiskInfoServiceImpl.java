package com.hyjf.cs.trade.service.aems.assetriskinfo.impl;

import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.bean.AemsInfoBean;
import com.hyjf.cs.trade.service.aems.assetriskinfo.AemsRiskInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AemsRiskInfoServiceImpl extends BaseServiceImpl implements AemsRiskInfoService {

	Logger logger = LoggerFactory.getLogger(AemsRiskInfoServiceImpl.class);
	
	/**
	 * 插入资产表
	 *
	 * @param infobeans
	 * @return
	 */
	@Override
	public void insertRiskInfo(List<AemsInfoBean> infobeans) {
		for (AemsInfoBean infobean : infobeans) {
			HjhPlanAssetVO riskInfo = new HjhPlanAssetVO();
			
			/*int nowTime = GetDate.getNowTime10(); // 当前时间
			riskInfo.setCreateTime(nowTime);
			riskInfo.setUpdateTime(nowTime);
			riskInfo.setCreateUser(1);// 默认系统用户
			riskInfo.setUpdateUserId(1);
			
			riskInfo.setAssetId(infobean.getAssetId());
			riskInfo.setAmazonInfo(infobean.getAmazonInfo());
			riskInfo.setEbayInfo(infobean.getEbayInfo());
			riskInfo.setJingdongInfo(infobean.getJingdongInfo());
			riskInfo.setTaobaoInfo(infobean.getTaobaoInfo());
			riskInfo.setTianmaoInfo(infobean.getTianmaoInfo());

			HjhAssetRiskInfoExample example = new HjhAssetRiskInfoExample();
			HjhAssetRiskInfoExample.Criteria crt =example.createCriteria();
	        crt.andAssetIdEqualTo(riskInfo.getAssetId());
			//已存在的assetid做更新处理
			if (this.hjhAssetRiskInfoMapper.countByExample(example) > 0) {
				if (this.hjhAssetRiskInfoMapper.updateByExampleSelective(riskInfo,example) > 0 ? false : true) {
					logger.error("----------------------商家信息更新失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
				}
			} else {
				if (this.hjhAssetRiskInfoMapper.insertSelective(riskInfo) > 0 ? false : true) {
					logger.error("----------------------商家信息插入失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
				}
			}*/
		}
	}
}
