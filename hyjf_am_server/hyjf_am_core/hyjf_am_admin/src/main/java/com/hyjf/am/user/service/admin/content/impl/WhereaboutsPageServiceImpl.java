/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.resquest.admin.WhereaboutsPageRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.content.WhereaboutsPageService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.config.WhereaboutsPageVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize()==0?10:request.getPageSize());
			request.setLimitStart(paginator.getOffset());
			request.setLimitEnd(paginator.getLimit());
			List<WhereaboutsPageVo> list = whereaboutsPageConfigCustomizeMapper.selectWhereaboutsPageList(request);
			response.setResultList(list);
			return response;
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
		whereaboutsPageConfigMapper.updateByPrimaryKey(whereaboutsPageConfig);

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
		//record.setStatusOn(request.getStatusOn() == 0? 1 : 0);
		whereaboutsPageConfigMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public void deleteById(Integer id){
		WhereaboutsPageConfig record = new WhereaboutsPageConfig();
		record.setDelFlag(CustomConstants.FALG_DEL);
		record.setId(id);
		// 操作时间
		record.setUpdateTime(GetDate.getDate());
		whereaboutsPageConfigMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public WhereaboutsPageResponse  getWhereaboutsPageConfigById(WhereaboutsPageRequest form){
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		WhereaboutsPageConfig whereaboutsPageConfig=whereaboutsPageConfigMapper.selectByPrimaryKey(form.getId());
		if(whereaboutsPageConfig!=null&&whereaboutsPageConfig.getId()!=0){
			WhereaboutsPagePictureExample example=new WhereaboutsPagePictureExample();
			example.createCriteria().andWhereaboutsIdEqualTo(form.getId());
			List<WhereaboutsPagePicture> list=whereaboutsPagePictureMapper.selectByExample(example);

			List<BorrowCommonImage> pictureTypeList1=new ArrayList<BorrowCommonImage>();
			List<BorrowCommonImage> pictureTypeList2=new ArrayList<BorrowCommonImage>();
			List<BorrowCommonImage> pictureTypeList3=new ArrayList<BorrowCommonImage>();
			for (WhereaboutsPagePicture whereaboutsPagePicture : list) {
				if(whereaboutsPagePicture.getPictureType()==1){
					pictureTypeList1.add(createBorrowCommonImage(whereaboutsPagePicture,form.getDomain()));
				}
				if(whereaboutsPagePicture.getPictureType()==2){
					pictureTypeList2.add(createBorrowCommonImage(whereaboutsPagePicture,form.getDomain()));
				}
				if(whereaboutsPagePicture.getPictureType()==3){
					pictureTypeList3.add(createBorrowCommonImage(whereaboutsPagePicture,form.getDomain()));
				}
			}

			form.setWhereaboutsPagePictures1(pictureTypeList1);
			form.setWhereaboutsPagePictures2(pictureTypeList2);
			form.setWhereaboutsPagePictures3(pictureTypeList3);

			createWhereaboutsPageBean(form,whereaboutsPageConfig);
		}
		response.setForm(form);
		return response;
	}
	private void createWhereaboutsPageBean(WhereaboutsPageRequest form, WhereaboutsPageConfig whereaboutsPageConfig) {
		form.setTitle(whereaboutsPageConfig.getTitle());
		form.setUtmId(whereaboutsPageConfig.getUtmId());

		if(whereaboutsPageConfig.getReferrer()!=null&&!"".equals(whereaboutsPageConfig.getReferrer())){
			UserExample example=new UserExample();
			example.createCriteria().andUserIdEqualTo(whereaboutsPageConfig.getReferrer());
			List<User> list=userMapper.selectByExample(example);
			if(list!=null&&list.size()!=0){
				form.setReferrerName(list.get(0).getUsername());
			}
		}
		form.setStyle(whereaboutsPageConfig.getStyle());
		form.setTopButton(StringUtils.isNotEmpty(whereaboutsPageConfig.getTopButton()) ? whereaboutsPageConfig.getTopButton() : "");
		form.setJumpPath(StringUtils.isNotEmpty(whereaboutsPageConfig.getJumpPath()) ? whereaboutsPageConfig.getJumpPath() : "");
		form.setBottomButtonStatus(whereaboutsPageConfig.getBottomButtonStatus());
		form.setBottomButton(StringUtils.isNotEmpty(whereaboutsPageConfig.getBottomButton()) ? whereaboutsPageConfig.getBottomButton() : "");
		form.setDownloadPath(whereaboutsPageConfig.getDownloadPath());
		form.setDescribe(whereaboutsPageConfig.getDescribe());
		form.setStatusOn(whereaboutsPageConfig.getStatusOn());
	}
	private BorrowCommonImage createBorrowCommonImage(WhereaboutsPagePicture whereaboutsPagePicture,String fileDomainUrl) {
		BorrowCommonImage whereaboutsPageImage=new BorrowCommonImage();
		whereaboutsPageImage.setImageName(whereaboutsPagePicture.getPictureName());
		whereaboutsPageImage.setImagePath(whereaboutsPagePicture.getPictureUrl());
		whereaboutsPageImage.setImageSort(whereaboutsPagePicture.getSort()+"");
		whereaboutsPageImage.setImageRealName(fileDomainUrl+whereaboutsPagePicture.getPictureUrl());
		whereaboutsPageImage.setImageSrc(fileDomainUrl+whereaboutsPagePicture.getPictureUrl());

		return whereaboutsPageImage;
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

	@Override
	public StringResponse checkUtmId(Integer utmId) {
		StringResponse response = new StringResponse();
		JSONObject ret = new JSONObject();
		if(utmId==null || "".equals(utmId)){
			ret.put("success", "渠道不能为空");
			response.setResult(ret);
			return response;
		}
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher((CharSequence) String.valueOf(utmId));
		if(!matcher.matches()){
			ret.put("success", "请输入正确的渠道格式");
			response.setResult(ret);
			return response;
		}
		Utm utm =utmMapper.selectByPrimaryKey(utmId);
		if(utm!=null&&utm.getUtmId()!=null){
			ret.put("success", "y");
			response.setResult(ret);
			return response;
		}
		ret.put("success", "渠道不存在");
		response.setResult(ret);
		return response;
	}

	@Override
	public StringResponse checkReferrer(String referrer) {
		StringResponse response = new StringResponse();
		JSONObject ret = new JSONObject();
		if(StringUtils.isBlank(referrer)){
			ret.put("success", "推荐人不能为空");
			response.setResult(ret);
			return response;
		}
		UserExample example=new UserExample();
		example.createCriteria().andUsernameEqualTo(referrer);
		List<User> list=userMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			ret.put("success", "y");
			response.setResult(ret);
			return response;
		}
		ret.put("success", "推荐人不存在");
		response.setResult(ret);
		return response;
	}
}
