package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAssetExample;
import com.hyjf.am.trade.service.admin.AssetListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class AssetListServiceImpl extends BaseServiceImpl implements AssetListService{


	@Override
	public List<AssetListCustomizeVO> findAssetList(Map<String, Object> mapParam,int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
		List<AssetListCustomizeVO> list = assetListServiceCustomizeMapper.queryAssetList(mapParam);
		if(!CollectionUtils.isEmpty(list)){
	        Map<String, String> assetStatusMap = CacheUtil.getParamNameMap("ASSET_STATUS");
	        Map<String, String> assetApplyStatusMap = CacheUtil.getParamNameMap("ASSET_APPLY_STATUS");
	        Map<String, String> accountStatusMap = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
			Map<String, String> userTypeMap = CacheUtil.getParamNameMap("USER_TYPE");
			for(AssetListCustomizeVO assetListCustomizeVO : list){
				assetListCustomizeVO.setStatus(assetStatusMap.getOrDefault(assetListCustomizeVO.getStatus(),null));
				assetListCustomizeVO.setVerifyStatus(assetApplyStatusMap.getOrDefault(assetListCustomizeVO.getVerifyStatus(),null));
				assetListCustomizeVO.setBankOpenAccount(accountStatusMap.getOrDefault(assetListCustomizeVO.getBankOpenAccount(),null));
				assetListCustomizeVO.setUserType(userTypeMap.getOrDefault(assetListCustomizeVO.getUserType(),null));
			}
		}
		return list;
	}

	@Override
	public AssetDetailCustomizeVO findDetailById(Map<String, Object> mapParam) {
		return assetListServiceCustomizeMapper.selectAssetDetail(mapParam);
	}

	@Override
	public Integer getRecordCount(AssetListRequest request) {
		return assetListServiceCustomizeMapper.countAssetList(request);
	}

	@Override
	public BigDecimal getSumAccount(AssetListRequest request) {
		return assetListServiceCustomizeMapper.getSumAccount(request);
	}

	@Override
	public boolean updateCashDepositeStatus(String assetId, String menuHide) {
	    HjhPlanAsset hjhPlanAsset = new HjhPlanAsset();
        if(StringUtils.equals("1",menuHide)){
            hjhPlanAsset.setStatus(0);//补交
        }else{
            hjhPlanAsset.setStatus(15);//流标
        }
          HjhPlanAssetExample hjhPlanAssetExample = new HjhPlanAssetExample();
          HjhPlanAssetExample.Criteria criteria = hjhPlanAssetExample.createCriteria();
          criteria.andAssetIdEqualTo(assetId);
          int i = hjhPlanAssetMapper.updateByExampleSelective(hjhPlanAsset, hjhPlanAssetExample);
          if(i > 0&&StringUtils.equals("1",menuHide)) {
            List<HjhPlanAsset> hjhPlanAssets = hjhPlanAssetMapper.selectByExample(hjhPlanAssetExample);
            HjhPlanAsset asset = hjhPlanAssets.get(0);
            String redisKey = "borrowsend:" + asset.getInstCode()+asset.getAssetId();
              if(RedisUtils.exists(redisKey)){
                  RedisUtils.del(redisKey);
              }
           //sendToMQ(assetId,asset.getInstCode());//  AutoIssueRecoverTask 自动录标修复
        }
		return false;
	}


	/**
	 * 获取保证金不足列表
	 * @param mapParam
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	@Override
	public List<AssetListCustomizeVO> findBZJBZList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
		// 封装查询条件
		if (limitStart == 0 || limitStart > 0) {
			mapParam.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			mapParam.put("limitEnd", limitEnd);
		}
		List<AssetListCustomizeVO> list = assetListServiceCustomizeMapper.findBZJBZList(mapParam);
		return list;
	}

	/**
	 * 查询保证金不足总记录数
	 * @param mapParam
	 * @return
	 */
	@Override
	public Integer getBZJBZCount(Map<String, Object> mapParam) {
		return assetListServiceCustomizeMapper.getBZJBZCount(mapParam);
	}


	@Override
	public List<AssetListCustomizeVO> findAssetListWithoutPage(Map<String, Object> mapParam, int limitStart, int limitEnd) {
		// 封装查询条件
		if (limitStart == 0 || limitStart > 0) {
			mapParam.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			mapParam.put("limitEnd", limitEnd);
		}
		List<AssetListCustomizeVO> list = assetListServiceCustomizeMapper.queryAssetList(mapParam);
		if(!CollectionUtils.isEmpty(list)){
	        Map<String, String> assetStatusMap = CacheUtil.getParamNameMap("ASSET_STATUS");
	        Map<String, String> assetApplyStatusMap = CacheUtil.getParamNameMap("ASSET_APPLY_STATUS");
	        Map<String, String> accountStatusMap = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
			Map<String, String> userTypeMap = CacheUtil.getParamNameMap("USER_TYPE");
			for(AssetListCustomizeVO assetListCustomizeVO : list){
				assetListCustomizeVO.setStatus(assetStatusMap.getOrDefault(assetListCustomizeVO.getStatus(),null));
				assetListCustomizeVO.setVerifyStatus(assetApplyStatusMap.getOrDefault(assetListCustomizeVO.getVerifyStatus(),null));
				assetListCustomizeVO.setBankOpenAccount(accountStatusMap.getOrDefault(assetListCustomizeVO.getBankOpenAccount(),null));
				assetListCustomizeVO.setUserType(userTypeMap.getOrDefault(assetListCustomizeVO.getUserType(),null));
			}
		}
		return list;
	}
}
