/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.ProtocolsService;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author fuqiang
 * @version ProtocolsServiceImpl, v0.1 2018/7/10 16:54
 */
@Service
public class ProtocolsServiceImpl implements ProtocolsService {
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request) {
		FddTempletCustomizeResponse response = amTradeClient.selectFddTempletList(request);
		if (response != null) {
			List<FddTempletCustomizeVO> voList = response.getResultList();
			if (!CollectionUtils.isEmpty(voList)) {
				for (FddTempletCustomizeVO vo : voList) {
					List<ParamNameVO> typeList = amConfigClient.getParamNameList("PROTOCOL_TYPE");
					if (!CollectionUtils.isEmpty(typeList)) {
						ParamNameVO typeVo = typeList.get(0);
						if (Objects.equals(typeVo.getNameCd(), vo.getProtocolType().toString())) {
							vo.setProtocolTypeName(typeVo.getName());
						}
					}
					List<ParamNameVO> flagList = amConfigClient.getParamNameList("CA_FLAG");
					if (!CollectionUtils.isEmpty(flagList)) {
						ParamNameVO flagVo = flagList.get(0);
						if (Objects.equals(flagVo.getNameCd(), vo.getProtocolType().toString())) {
							vo.setCaFlagName(flagVo.getName());
						}
					}
				}
			}
			response.setResultList(voList);
			return response;
		}
		return null;
	}

	@Override
	public FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean) {
		return amTradeClient.insertAction(requestBean);
	}

	@Override
	public FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean) {
		return amTradeClient.updateAction(requestBean);
	}

	@Override
	public String getNewTempletId(Integer protocolType) {
		return amTradeClient.getNewTempletId(protocolType);
	}

	/**
	 * 协议管理-画面迁移
	 *
	 * @param id
	 * @return
	 */
	@Override
	public FddTempletCustomizeResponse getRecordInfo(Integer id) {
		return amTradeClient.getRecordInfoById(id);
	}

	/**
	 * 协议管理-获取协议类型下拉列表
	 *
	 * @param protocolType
	 * @return
	 */
	@Override
	public List<ParamNameVO> getParamNameList(String protocolType) {
		return amConfigClient.getParamNameList(protocolType);
	}
}
