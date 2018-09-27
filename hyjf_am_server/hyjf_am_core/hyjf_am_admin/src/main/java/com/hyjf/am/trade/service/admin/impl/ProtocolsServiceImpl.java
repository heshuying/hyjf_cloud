/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import java.util.Date;
import java.util.List;

import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.dao.mapper.auto.FddTempletMapper;
import com.hyjf.am.trade.dao.mapper.customize.FddTempletCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;
import com.hyjf.common.util.GetDate;

import javax.annotation.Resource;

/**
 * @author fuqiang
 * @version ProtocolsServiceImpl, v0.1 2018/7/10 18:15
 */
@Service
public class ProtocolsServiceImpl implements ProtocolsService {
	@Resource
	private FddTempletMapper fddTempletMapper;
	@Resource
	private FddTempletCustomizeMapper fddTempletCustomizeMapper;
    private static final String TEMPLET_ID_REFIX = "HYHT";

	@Override
	public List<FddTempletCustomize> selectFddTempletList(ProtocolsRequest request) {
		FddTempletCustomize fddTemplet = new FddTempletCustomize();
		if (request.getLimitStart() != -1) {
			fddTemplet.setLimitStart(request.getLimitStart());
			fddTemplet.setLimitEnd(request.getLimitEnd());
		}
		return fddTempletCustomizeMapper.selectFddTempletList(fddTemplet);
	}

	@Override
	public void insertAction(ProtocolsRequest request) {
		FddTemplet record = new FddTemplet();
		BeanUtils.copyProperties(request, record);
		int nowTime = GetDate.getNowTime10();
		record.setCertificateTime(nowTime);//认证时间
		// 登陆信息
		record.setCreateTime(new Date());
		fddTempletMapper.insertSelective(record);
	}

	@Override
	public void updateAction(ProtocolsRequest request) {
		FddTemplet record = new FddTemplet();
		BeanUtils.copyProperties(request, record);
		int nowTime = GetDate.getNowTime10();
		record.setCertificateTime(nowTime);//认证时间
		// 登陆信息
		record.setCreateTime(new Date());
		fddTempletMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public String getNewTempletId(Integer protocolType) {
		String templetId = null;
		templetId = fddTempletCustomizeMapper.getMaxTempletId(protocolType);
		if (templetId == null){
			return TEMPLET_ID_REFIX + String.format("%02d",protocolType) + String.format("%04d",1);
		}
		String preFixStr = templetId.substring(0, templetId.length()-4);
		String postSNStr = templetId.substring(templetId.length()-4);
		postSNStr = String.format("%04d",Integer.parseInt(postSNStr) + 1);
		return preFixStr + postSNStr;
	}

    /**
     * 协议管理-画面迁移
     *
     * @param id
     * @return
     */
    @Override
    public FddTempletCustomize getRecordInfoById(Integer id) {
        FddTempletCustomize list = new FddTempletCustomize();
        FddTemplet fddTemplet = new FddTemplet();
        fddTemplet.setId(id);
        FddTemplet fddTempletT = fddTempletMapper.selectByPrimaryKey(id);
        if (fddTemplet != null) {
            list = CommonUtils.convertBean(fddTempletT, FddTempletCustomize.class);
        }
        return list;
    }
}
