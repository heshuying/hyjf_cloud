package com.hyjf.am.user.service.admin.anrong.impl;

import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.anrong.MspApplyService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MspApplyServiceImpl extends BaseServiceImpl implements MspApplyService {

	/**
	 * 获取列表列表
	 * 
	 * @return
	 */
	@Override
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
	@Override
    public MspApply getRecord(Integer record) {
		MspApply FeeConfig = mspApplyMapper.selectByPrimaryKey(record);
		return FeeConfig;
	}

	/**
	 * 根据主键判断维护中数据是否存在
	 * 
	 * @return
	 */
	@Override
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
	@Override
    public void insertRecord(MspApply record) {
		mspApplyMapper.insertSelective(record);
	}

	/**
	 * 维护更新
	 * 
	 * @param record
	 */
	@Override
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
		return mspAnliinfosMapper.selectByExample(me);
	}

	@Override
	public List<MspShixininfos> getShixinInfos(String applyId) {
		MspShixininfosExample me = new MspShixininfosExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspShixininfosMapper.selectByExample(me);
	}

	@Override
	public List<MspZhixinginfos> getZhixingInfos(String applyId) {
		MspZhixinginfosExample me = new MspZhixinginfosExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspZhixinginfosMapper.selectByExample(me);
	}

	@Override
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
		return mspApplydetailsMapper.selectByExample(me);
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
		return mspAbnormalcreditdetailMapper.selectByExample(me);
	}

	@Override
	public List<MspAbnormalcredit> getAbnormalCredit(String applyId) {
		MspAbnormalcreditExample me = new MspAbnormalcreditExample();
		me.or().andApplyIdEqualTo(applyId);
		return mspAbnormalcreditMapper.selectByExample(me);
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
