package com.hyjf.am.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.user.dao.mapper.auto.MspAbnormalcreditMapper;
import com.hyjf.am.user.dao.mapper.auto.MspAbnormalcreditdetailMapper;
import com.hyjf.am.user.dao.mapper.auto.MspAnliinfosMapper;
import com.hyjf.am.user.dao.mapper.auto.MspApplyMapper;
import com.hyjf.am.user.dao.mapper.auto.MspApplydetailsMapper;
import com.hyjf.am.user.dao.mapper.auto.MspBlackdataMapper;
import com.hyjf.am.user.dao.mapper.auto.MspConfigureMapper;
import com.hyjf.am.user.dao.mapper.auto.MspFqzMapper;
import com.hyjf.am.user.dao.mapper.auto.MspNormalcreditdetailMapper;
import com.hyjf.am.user.dao.mapper.auto.MspQuerydetailMapper;
import com.hyjf.am.user.dao.mapper.auto.MspRegionMapper;
import com.hyjf.am.user.dao.mapper.auto.MspShixininfosMapper;
import com.hyjf.am.user.dao.mapper.auto.MspTitleMapper;
import com.hyjf.am.user.dao.mapper.auto.MspZhixinginfosMapper;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcredit;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditExample;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditdetail;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditdetailExample;
import com.hyjf.am.user.dao.model.auto.MspAnliinfos;
import com.hyjf.am.user.dao.model.auto.MspAnliinfosExample;
import com.hyjf.am.user.dao.model.auto.MspApply;
import com.hyjf.am.user.dao.model.auto.MspApplyExample;
import com.hyjf.am.user.dao.model.auto.MspApplydetails;
import com.hyjf.am.user.dao.model.auto.MspApplydetailsExample;
import com.hyjf.am.user.dao.model.auto.MspBlackdata;
import com.hyjf.am.user.dao.model.auto.MspBlackdataExample;
import com.hyjf.am.user.dao.model.auto.MspConfigure;
import com.hyjf.am.user.dao.model.auto.MspConfigureExample;
import com.hyjf.am.user.dao.model.auto.MspFqz;
import com.hyjf.am.user.dao.model.auto.MspFqzExample;
import com.hyjf.am.user.dao.model.auto.MspNormalcreditdetail;
import com.hyjf.am.user.dao.model.auto.MspNormalcreditdetailExample;
import com.hyjf.am.user.dao.model.auto.MspQuerydetail;
import com.hyjf.am.user.dao.model.auto.MspQuerydetailExample;
import com.hyjf.am.user.dao.model.auto.MspRegion;
import com.hyjf.am.user.dao.model.auto.MspRegionExample;
import com.hyjf.am.user.dao.model.auto.MspShixininfos;
import com.hyjf.am.user.dao.model.auto.MspShixininfosExample;
import com.hyjf.am.user.dao.model.auto.MspTitle;
import com.hyjf.am.user.dao.model.auto.MspTitleExample;
import com.hyjf.am.user.dao.model.auto.MspZhixinginfos;
import com.hyjf.am.user.dao.model.auto.MspZhixinginfosExample;
import com.hyjf.am.user.service.MspApplyService;

@Service
public class MspApplyServiceImpl implements MspApplyService {
	@Autowired
	private MspApplyMapper mspApplyMapper;
	@Autowired
	private MspRegionMapper mspRegionMapper;
	@Autowired
	private MspConfigureMapper mspConfigureMapperAuto;
	@Autowired
	private MspFqzMapper mspFqzMapper;
	@Autowired
	private MspAnliinfosMapper mspAnliInfosMapper;
	@Autowired
	private MspShixininfosMapper mspShixinInfosMapper;
	@Autowired
	private MspZhixinginfosMapper mspZhixinginfosMapper;
	@Autowired
	private MspTitleMapper mspTitleMapper;
	@Autowired
	private MspNormalcreditdetailMapper mspNormalcreditdetailMapper;
	@Autowired
	private MspApplydetailsMapper mspApplyDetailsMapper;
	@Autowired
	private MspQuerydetailMapper mspQueryDetailMapper;
	@Autowired
	private MspBlackdataMapper mspBlackDataMapper;
	@Autowired
	private MspAbnormalcreditdetailMapper mspAbnormalCreditDetailMapper;
	@Autowired
	private MspAbnormalcreditMapper mspAbnormalCreditMapper;

	/**
	 * 获取列表列表
	 * 
	 * @return
	 */
	public List<MspApply> getRecordList(MspApply mspApply, int limitStart, int limitEnd, int createStart,
			int createEnd) {
		MspApplyExample example = new MspApplyExample();
		example.setOrderByClause(" id desc");
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		MspApplyExample.Criteria criteria = example.createCriteria();
		// 条件查询
		if (StringUtils.isNotEmpty(mspApply.getName())) {
			criteria.andNameEqualTo(mspApply.getName());
		}
		if (StringUtils.isNotEmpty(mspApply.getCreateUser())) {
			criteria.andCreateUserEqualTo(mspApply.getCreateUser());
		}
		if (StringUtils.isNotEmpty(mspApply.getIdentityCard())) {
			criteria.andIdentityCardEqualTo(mspApply.getIdentityCard());
		}
		if (createStart != 0 || createEnd != 0) {
			criteria.andCreateTimeBetween(createStart, createEnd);
		}

		return mspApplyMapper.selectByExample(example);
	}

	/**
	 * 获取单个维护
	 * 
	 * @return
	 */
	public MspApply getRecord(Integer record) {
		MspApply FeeConfig = mspApplyMapper.selectByPrimaryKey(record);
		return FeeConfig;
	}

	/**
	 * 根据主键判断维护中数据是否存在
	 * 
	 * @return
	 */
	public boolean isExistsRecord(MspApply record) {
		if (record.getId() == null) {
			return false;
		}
		// BankConfigExample example = new BankConfigExample();
		// BankConfigExample.Criteria cra = example.createCriteria();
		// cra.andIdEqualTo(record.getId());
		// List<BankConfig> bankConfigs = bankConfigMapper.selectByExample(example);
		// if (bankConfigs != null && bankConfigs.size() > 0) {
		// return true;
		// }
		return false;
	}

	/**
	 * 维护插入
	 * 
	 * @param record
	 */
	public void insertRecord(MspApply record) {
		mspApplyMapper.insertSelective(record);
	}

	/**
	 * 维护更新
	 * 
	 * @param record
	 */
	public void updateRecord(MspApply record) {
		record.setUpdateTime(1);
		// record.setLogo("1");
		mspApplyMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param recordList
	 */
	@Override
	public void deleteRecord(List<Integer> recordList) {
		for (Integer id : recordList) {
			mspApplyMapper.deleteByPrimaryKey(id);
		}
	}

	/**
	 * 获取城市列表
	 * 
	 * @return
	 * @author
	 */
	@Override
	public List<MspRegion> getRegionList() {

		return mspRegionMapper.selectByExample(new MspRegionExample());
	}

	@Override
	public List<MspConfigure> getConfigureList() {

		return mspConfigureMapperAuto.selectByExample(new MspConfigureExample());
	}

	@Override
	public MspConfigure getConfigure(int id) {

		return mspConfigureMapperAuto.selectByPrimaryKey(id);
	}

	@Override
	public MspFqz getFqz(String applyId) {

		MspFqzExample me = new MspFqzExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspFqzMapper.selectByExample(me).get(0);
	}

	@Override
	public List<MspAnliinfos> getAnliInfos(String applyId) {
		MspAnliinfosExample me = new MspAnliinfosExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspAnliInfosMapper.selectByExample(me);
	}

	@Override
	public List<MspShixininfos> getShixinInfos(String applyId) {
		MspShixininfosExample me = new MspShixininfosExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspShixinInfosMapper.selectByExample(me);
	}

	@Override
	public List<MspZhixinginfos> getZhixingInfos(String applyId) {
		MspZhixinginfosExample me = new MspZhixinginfosExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspZhixinginfosMapper.selectByExample(me);
	}

	public MspTitle getTitle(String applyId) {
		MspTitleExample me = new MspTitleExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspTitleMapper.selectByExample(me).get(0);
	}

	@Override
	public List<MspNormalcreditdetail> getNormalCreditDetail(String applyId) {
		MspNormalcreditdetailExample me = new MspNormalcreditdetailExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspNormalcreditdetailMapper.selectByExample(me);
	}

	@Override
	public List<MspApplydetails> getApplyDetails(String applyId) {
		MspApplydetailsExample me = new MspApplydetailsExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspApplyDetailsMapper.selectByExample(me);
	}

	@Override
	public List<MspQuerydetail> getQueryDetail(String applyId) {
		MspQuerydetailExample me = new MspQuerydetailExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspQueryDetailMapper.selectByExample(me);
	}

	@Override
	public List<MspBlackdata> getBlackData(String applyId) {
		MspBlackdataExample me = new MspBlackdataExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspBlackDataMapper.selectByExample(me);
	}

	@Override
	public List<MspAbnormalcreditdetail> getAbnormalCreditDetail(String applyId) {
		MspAbnormalcreditdetailExample me = new MspAbnormalcreditdetailExample();
		me.or().andAbcdIdEqualTo(applyId);
		return mspAbnormalCreditDetailMapper.selectByExample(me);
	}

	@Override
	public List<MspAbnormalcredit> getAbnormalCredit(String applyId) {
		MspAbnormalcreditExample me = new MspAbnormalcreditExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspAbnormalCreditMapper.selectByExample(me);
	}

	@Override
	public int countByExample(MspApply mspApply) {
		MspApplyExample example = new MspApplyExample();

		MspApplyExample.Criteria criteria = example.createCriteria();
		// 条件查询
		if (StringUtils.isNotEmpty(mspApply.getName())) {
			criteria.andNameEqualTo(mspApply.getName());
		}
		if (StringUtils.isNotEmpty(mspApply.getCreateUser())) {
			criteria.andCreateUserEqualTo(mspApply.getCreateUser());
		}
		if (StringUtils.isNotEmpty(mspApply.getIdentityCard())) {
			criteria.andIdentityCardEqualTo(mspApply.getIdentityCard());
		}


		return mspApplyMapper.countByExample(example);
	}

}
