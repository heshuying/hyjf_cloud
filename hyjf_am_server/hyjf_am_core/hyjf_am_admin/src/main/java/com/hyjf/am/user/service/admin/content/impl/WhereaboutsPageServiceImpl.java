/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.content.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.resquest.admin.WhereaboutsPageRequest;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPageConfig;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPagePicture;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPagePictureExample;
import com.hyjf.am.user.service.admin.content.WhereaboutsPageService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.config.WhereaboutsPageVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;

/**
 * @author tanyy
 * @version WhereaboutsPageServiceImpl, v0.1 2018/7/16 14:34
 */
@Service
public class WhereaboutsPageServiceImpl extends BaseServiceImpl implements WhereaboutsPageService {

	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequest request) {
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		int count = whereaboutsPageConfigCustomizeMapper.countWhereaboutsPage(request);
		response.setCount(count);
		if(count>0){
			Paginator paginator = new Paginator(request.getCurrPage(), count);
			request.setLimitStart(paginator.getOffset());
			request.setLimitEnd(paginator.getLimit());
			List<WhereaboutsPageVo> list = whereaboutsPageConfigCustomizeMapper.selectWhereaboutsPageList(request);
			response.setResultList(list);
		}
		return response;
	}

	@Override
	public void insertAction(WhereaboutsPageRequest form) {

		WhereaboutsPageConfig whereaboutsPageConfig=createWhereaboutsPageConfig(form);
		whereaboutsPageConfigMapper.insertSelective(whereaboutsPageConfig);

		List<WhereaboutsPagePicture> imageJsonList1 = JSONArray.parseArray(form.getImageJson1(), WhereaboutsPagePicture.class);

		for (WhereaboutsPagePicture WhereaboutsPagePicture : imageJsonList1) {
			WhereaboutsPagePicture whereaboutsPagePicture = createWhereaboutsPagePicture(WhereaboutsPagePicture,1,whereaboutsPageConfig.getId());
			whereaboutsPagePictureMapper.insertSelective(whereaboutsPagePicture);
		}

		List<WhereaboutsPagePicture> imageJsonList2 = JSONArray.parseArray(form.getImageJson2(), WhereaboutsPagePicture.class);

		for (WhereaboutsPagePicture WhereaboutsPagePicture : imageJsonList2) {
			WhereaboutsPagePicture whereaboutsPagePicture = createWhereaboutsPagePicture(WhereaboutsPagePicture,2,whereaboutsPageConfig.getId());
			whereaboutsPagePictureMapper.insertSelective(whereaboutsPagePicture);
		}

		List<WhereaboutsPagePicture> imageJsonList3 = JSONArray.parseArray(form.getImageJson3(), WhereaboutsPagePicture.class);

		for (WhereaboutsPagePicture WhereaboutsPagePicture : imageJsonList3) {
			WhereaboutsPagePicture whereaboutsPagePicture = createWhereaboutsPagePicture(WhereaboutsPagePicture,3,whereaboutsPageConfig.getId());
			whereaboutsPagePictureMapper.insertSelective(whereaboutsPagePicture);
		}
	}

	@Override
	public WhereaboutsPageConfig getRecord(Integer id) {
		return  whereaboutsPageConfigMapper.selectByPrimaryKey(id);
	}
	@Override
	public void updateAction(WhereaboutsPageRequest form) {
		WhereaboutsPageConfig whereaboutsPageConfig=createWhereaboutsPageConfig(form);
		whereaboutsPageConfigMapper.updateByPrimaryKeySelective(whereaboutsPageConfig);

		WhereaboutsPagePictureExample example=new WhereaboutsPagePictureExample();
		example.createCriteria().andWhereaboutsIdEqualTo(whereaboutsPageConfig.getId());
		whereaboutsPagePictureMapper.deleteByExample(example);

		List<WhereaboutsPagePicture> imageJsonList1 = JSONArray.parseArray(form.getImageJson1(), WhereaboutsPagePicture.class);

		for (WhereaboutsPagePicture whereaboutsPageImage : imageJsonList1) {
			WhereaboutsPagePicture whereaboutsPagePicture = createWhereaboutsPagePicture(whereaboutsPageImage,1,whereaboutsPageConfig.getId());
			whereaboutsPagePictureMapper.insertSelective(whereaboutsPagePicture);
		}

		List<WhereaboutsPagePicture> imageJsonList2 = JSONArray.parseArray(form.getImageJson2(), WhereaboutsPagePicture.class);

		for (WhereaboutsPagePicture whereaboutsPageImage : imageJsonList2) {
			WhereaboutsPagePicture whereaboutsPagePicture = createWhereaboutsPagePicture(whereaboutsPageImage,2,whereaboutsPageConfig.getId());
			whereaboutsPagePictureMapper.insertSelective(whereaboutsPagePicture);
		}

		List<WhereaboutsPagePicture> imageJsonList3 = JSONArray.parseArray(form.getImageJson3(), WhereaboutsPagePicture.class);

		for (WhereaboutsPagePicture whereaboutsPageImage : imageJsonList3) {
			WhereaboutsPagePicture whereaboutsPagePicture = createWhereaboutsPagePicture(whereaboutsPageImage,3,whereaboutsPageConfig.getId());
			whereaboutsPagePictureMapper.insertSelective(whereaboutsPagePicture);
		}
	}

	@Override
	public void statusAction(WhereaboutsPageRequest request) {
		WhereaboutsPageConfig record = new WhereaboutsPageConfig();
		BeanUtils.copyProperties(request, record);
		record.setStatusOn(request.getStatusOn() == 0? 1 : 0);
		whereaboutsPageConfigMapper.updateByPrimaryKey(record);
	}


	@Override
	public void deleteById(Integer id){
		WhereaboutsPageConfig record = new WhereaboutsPageConfig();
		record.setDelFlag(CustomConstants.FALG_DEL);
		// 操作时间
		record.setUpdateTime(GetDate.getDate());
		whereaboutsPageConfigMapper.updateByPrimaryKey(record);
	}

	private WhereaboutsPagePicture createWhereaboutsPagePicture(WhereaboutsPagePicture whereaboutsPageImage,int pictureType,int whereaboutsId) {
		WhereaboutsPagePicture whereaboutsPagePicture = new WhereaboutsPagePicture();
		whereaboutsPagePicture.setPictureName(whereaboutsPageImage.getPictureName());
		whereaboutsPagePicture.setPictureType(pictureType);
		whereaboutsPagePicture.setWhereaboutsId(whereaboutsId);
		whereaboutsPagePicture.setPictureUrl(
				whereaboutsPageImage.getPictureUrl());
		whereaboutsPagePicture.setSort(whereaboutsPageImage.getSort());
		whereaboutsPagePicture.setDelFlag(0);
		// 操作时间
		whereaboutsPagePicture.setCreateTime(GetDate.getDate());
		return whereaboutsPagePicture;
	}
	private WhereaboutsPageConfig createWhereaboutsPageConfig(WhereaboutsPageRequest form) {
		WhereaboutsPageConfig whereaboutsPageConfig=new WhereaboutsPageConfig();
		if(form.getId()!=null&&form.getId()!=0){
			whereaboutsPageConfig.setId(form.getId());
		}
		whereaboutsPageConfig.setTitle(form.getTitle());
		Utm utm =utmMapper.selectByPrimaryKey(form.getUtmId());
		if(utm!=null&&utm.getUtmId()!=null){
			whereaboutsPageConfig.setUtmId(form.getUtmId());
		}
		if(form.getReferrerName()!=null && !"".equals(form.getReferrerName())){
			UserExample example=new UserExample();
			example.createCriteria().andUsernameEqualTo(form.getReferrerName());
			List<User> list=userMapper.selectByExample(example);
			if(list!=null&&list.size()!=0){
				whereaboutsPageConfig.setReferrer(list.get(0).getUserId());
			}

		}
		whereaboutsPageConfig.setStyle(form.getStyle());
		whereaboutsPageConfig.setTopButton(StringUtils.isNotEmpty(form.getTopButton()) ? form.getTopButton() : "");
		whereaboutsPageConfig.setJumpPath(StringUtils.isNotEmpty(form.getJumpPath()) ? form.getJumpPath() : "");
		whereaboutsPageConfig.setBottomButtonStatus(form.getBottomButtonStatus());
		whereaboutsPageConfig.setBottomButton(StringUtils.isNotEmpty(form.getBottomButton()) ? form.getBottomButton() : "");
		whereaboutsPageConfig.setDownloadPath(form.getDownloadPath());
		whereaboutsPageConfig.setDescribe(form.getDescribe());
		whereaboutsPageConfig.setDelFlag(0);
		whereaboutsPageConfig.setStatusOn(form.getStatusOn());
		// 操作时间
		whereaboutsPageConfig.setCreateTime(GetDate.getDate());
		return whereaboutsPageConfig;
	}
}
