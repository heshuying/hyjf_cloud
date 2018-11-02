package com.hyjf.am.user.service.admin.anrong.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.anrong.MspApplyService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    /** 安融返回结果msp 的key */
    public static final String RESULT_KEY_MSP = "msp";
    /** 安融返回结果msp 的errorCode */
    public static final String RESULT_KEY_ERRORCODE = "errorCode";
    /** 安融返回结果msp 的abnormalCreditDetails */
    public static final String RESULT_KEY_ABNORMALCREDITDETAILS = "abnormalCreditDetails";
    /** 安融返回结果msp 的applyDetails */
    public static final String RESULT_KEY_APPLYDETAILS = "applyDetails";
    /** 安融返回结果msp 的blackDatas */
    public static final String RESULT_KEY_BLACKDATAS = "blackDatas";
    /** 安融返回结果msp 的normalCreditDetails */
    public static final String RESULT_KEY_NORMALCREDITDETAILS = "normalCreditDetails";
    /** 安融返回结果msp 的queryDetails */
    public static final String RESULT_KEY_QUERYDETAILS = "queryDetails";
    /** 安融返回结果msp 的title */
    public static final String RESULT_KEY_TITLE = "title";
    /** 安融返回结果msp 的overdues */
    public static final String RESULT_KEY_OVERDUES = "overdues";
    /** 安融返回结果msp 的fqz */
    public static final String RESULT_KEY_FQZ = "fqz";
    /** 安融返回结果msp 的fqzBean */
    public static final String RESULT_KEY_FQZBEAN = "fqzBean";
    // Controller 返回客户端的响应
    /** api返回给客户端的响应反欺诈结果   fqz_success */
    public static final String RESULT_JSON_KEY_FQZ_SUCCESS = "fqz_success";
    /** api返回给客户端的响应反欺诈结果描述   fqz_mess */
    public static final String RESULT_JSON_KEY_FQZ_MESS = "fqz_mess";
    /** api返回给客户端的响应msp结果   msp_success */
    public static final String RESULT_JSON_KEY_MSP_SUCCESS = "msp_success";
    /** api返回给客户端的响应msp结果描述   msp_mess */
    public static final String RESULT_JSON_KEY_MSP_MESS = "msp_mess";
    /** 安融返回结果反欺诈 的validSifa */
    public static final String RESULT_KEY_VALIDSIFA = "validSifa";
    /** 安融返回结果反欺诈 的anliInfos */
    public static final String RESULT_KEY_ANLIINFOS = "anliInfos";
    /** 安融返回结果反欺诈 的shixinInfos */
    public static final String RESULT_KEY_SHIXININFOS = "shixinInfos";
    /** 安融返回结果反欺诈 的zhixingInfos */
    public static final String RESULT_KEY_ZHIXINGINFOS = "zhixingInfos";
    /** 安融返回结果反欺诈 的degreeResult */
    public static final String RESULT_KEY_DEGREERESULT = "degreeResult";
    /** 安融返回结果反欺诈 的cuikuangongshis */
    public static final String RESULT_KEY_CUIKUANGONGSHIS = "cuikuangongshis";
    /** id */
    public static final String STRING_ID = "id";
    /** 共享请求URL /send */
    public static final String URL_ANRONG_SEND = "/send";
    /** TXCODE共享接口 sendMess */
    public static final String TXCODE_SENDMESS = "sendMess";
    /** TXCODE查询接口 queryUser */
    public static final String TXCODE_QUERYUSER = "queryUser";
    
    /** api返回给客户端的响应查询Id reqId */
    public static final String RESULT_JSON_KEY_REQID = "reqId";
    /** api返回给客户端的响应信息 */
    public static final String RESULT_KEY_MESSAGE = "message";
    /** api返回给客户端的响应结果  0000 */
    public static final String RESULT_VALUE_SUCCESS = "0000";
    /** api返回给客户端的json Key success */
    public static final String RESULT_JSON_KEY_SUCCESS = "success";
    /** api返回给客户端的json Key errors */
    public static final String RESULT_KEY_ERRORS = "errors";
    /** 安融请求返回的 Key msg */
    public static final String RESULT_KEY_MSG = "msg";
    /** api返回给客户端的json Key msg */
    public static final String RESULT_JSON_KEY_MSG = "msg";
	 /**
     * 
     * 安融返回结果处理
     * @author sss
     * @param resultJson
     * @param appid 请求ID
     * @return
     */
    @Override
    public void insertResult(JSONObject resultJson,String appid) {
        // msp 报告
        JSONObject msp = resultJson.getJSONObject(RESULT_KEY_MSP);
        if ((!msp.containsKey(RESULT_KEY_ERRORCODE))||msp.get(RESULT_KEY_ERRORCODE)==null) {
            // 有数据
            // 插入 abnormalCreditDetails
            if(validateIsNull(msp,RESULT_KEY_ABNORMALCREDITDETAILS)){
                JSONArray abnormalCreditDetails = msp.getJSONArray(RESULT_KEY_ABNORMALCREDITDETAILS);
                insertCreditDetails(abnormalCreditDetails,appid);
            }
            
            // 插入 applyDetails
            if(validateIsNull(msp,RESULT_KEY_APPLYDETAILS)){
                JSONArray applyDetails = msp.getJSONArray(RESULT_KEY_APPLYDETAILS);
                insertApplyDetails(applyDetails,appid);
            }
            
            // 插入 blackDatas
            if(validateIsNull(msp,RESULT_KEY_BLACKDATAS)){
                JSONArray blackDatas = msp.getJSONArray(RESULT_KEY_BLACKDATAS);
                inserTblackDatas(blackDatas,appid);
            }
            
            // 插入 normalCreditDetails
            if(validateIsNull(msp,RESULT_KEY_NORMALCREDITDETAILS)){
                JSONArray normalCreditDetails = msp.getJSONArray(RESULT_KEY_NORMALCREDITDETAILS);
                insertNormalCreditDetails(normalCreditDetails,appid);
            }
            
            // 插入queryDetails
            if(validateIsNull(msp,RESULT_KEY_QUERYDETAILS)){
                JSONArray queryDetails = msp.getJSONArray(RESULT_KEY_QUERYDETAILS);
                insertQueryDetails(queryDetails,appid);
            }
            
            // 插入titles
            if(validateIsNull(msp,RESULT_KEY_TITLE)){
                JSONObject titles = msp.getJSONObject(RESULT_KEY_TITLE);
                inserTtitles(titles,appid);
            }
        }
        
        // fqz 报告
        JSONObject fqz = resultJson.getJSONObject(RESULT_KEY_FQZ);
        if ((!fqz.containsKey(RESULT_KEY_ERRORCODE))||fqz.get(RESULT_KEY_ERRORCODE)==null) {
            insertFqz(fqz,appid);
        }
    }

    // 插入fqz
    private void insertFqz(JSONObject fqz,String appid) {
        JSONObject fqzBeanJson = fqz.getJSONObject(RESULT_KEY_FQZBEAN);
        MspFqz fqzBean = JSONObject.parseObject(fqzBeanJson.toString(), MspFqz.class);
        fqzBean.setApplyId(appid);
        mspFqzMapper.insert(fqzBean);
        
        if(validateIsNull(fqzBeanJson,RESULT_KEY_DEGREERESULT)){
            JSONObject degreeResult = fqzBeanJson.getJSONObject(RESULT_KEY_DEGREERESULT);
            MspDegreeresult mspDegreeResult = JSONObject.parseObject(degreeResult.toString(), MspDegreeresult.class);
            mspDegreeResult.setApplyId(appid);
            mspDegreeresultMapper.insert(mspDegreeResult);
        }
        
        // 插入 validSifa
        JSONObject validSifa = fqzBeanJson.getJSONObject(RESULT_KEY_VALIDSIFA);
        if(validateIsNull(validSifa,RESULT_KEY_ANLIINFOS)){
            JSONArray anliInfos = validSifa.getJSONArray(RESULT_KEY_ANLIINFOS);
            for (Object item : anliInfos) {
                JSONObject jso = (JSONObject) item;
                // 这里ID跟数据库里面的ID数据类型冲突  所以需要单独处理
                String id = jso.getString(STRING_ID);
                jso.remove(STRING_ID);
                MspAnliinfos anliInfo = JSONObject.parseObject(jso.toString(), MspAnliinfos.class);
                anliInfo.setAnliId(id);
                anliInfo.setApplyId(appid);
                mspAnliinfosMapper.insert(anliInfo);
            }
        }
        if(validateIsNull(validSifa,RESULT_KEY_SHIXININFOS)){
            JSONArray shixinInfos = validSifa.getJSONArray(RESULT_KEY_SHIXININFOS);
            for (Object item : shixinInfos) {
                JSONObject jso = (JSONObject) item;
                MspShixininfos shixinInfo = JSONObject.parseObject(jso.toString(), MspShixininfos.class);
                shixinInfo.setApplyId(appid);
                mspShixininfosMapper.insert(shixinInfo);
            }
        }
        if(validateIsNull(validSifa,RESULT_KEY_ZHIXINGINFOS)){
            JSONArray zhixingInfos = validSifa.getJSONArray(RESULT_KEY_ZHIXINGINFOS);
            for (Object item : zhixingInfos) {
                JSONObject jso = (JSONObject) item;
                MspZhixinginfos zhixingInfo = JSONObject.parseObject(jso.toString(), MspZhixinginfos.class);
                zhixingInfo.setApplyId(appid);
                mspZhixinginfosMapper.insert(zhixingInfo);
            }
        }
    }

    // 插入titles
    private void inserTtitles(JSONObject titles, String appid) {
        MspTitle ad = JSONObject.parseObject(titles.toString(), MspTitle.class);
        ad.setApplyId(appid);
        mspTitleMapper.insert(ad);
    }

    // 插入queryDetails
    private void insertQueryDetails(JSONArray queryDetails, String appid) {
        for (Object item : queryDetails) {
            JSONObject jso = (JSONObject) item;
            MspQuerydetail ad = JSONObject.parseObject(jso.toString(), MspQuerydetail.class);
            ad.setApplyId(appid);
            mspQuerydetailMapper.insert(ad);
        }
    }

    // 插入 normalCreditDetails
    private void insertNormalCreditDetails(JSONArray normalCreditDetails, String appid) {
        for (Object item : normalCreditDetails) {
            JSONObject jso = (JSONObject) item;
            MspNormalcreditdetail ad = JSONObject.parseObject(jso.toString(), MspNormalcreditdetail.class);
            ad.setApplyId(appid);
            mspNormalcreditdetailMapper.insert(ad);
        }
    }

    // 插入 blackDatas
    private void inserTblackDatas(JSONArray blackDatas, String appid) {
        for (Object item : blackDatas) {
            JSONObject jso = (JSONObject) item;
            MspBlackdata ad = JSONObject.parseObject(jso.toString(), MspBlackdata.class);
            ad.setApplyId(appid);
            mspBlackdataMapper.insert(ad);
        }
    }

    // msp 插入 applyDetails
    private void insertApplyDetails(JSONArray applyDetails, String appid) {
        for (Object item : applyDetails) {
            JSONObject jso = (JSONObject) item;
            MspApplydetails ad = JSONObject.parseObject(jso.toString(), MspApplydetails.class);
            ad.setApplyId(appid);
            mspApplydetailsMapper.insert(ad);
        }
    }

    // msp  abnormalCreditDetails
    private void insertCreditDetails(JSONArray abnormalCreditDetails,String appId) {
        // 需要插入两个表
        for (Object item : abnormalCreditDetails) {
            JSONObject jso = (JSONObject) item;
            MspAbnormalcredit abnormalCredit = JSONObject.parseObject(jso.toString(), MspAbnormalcredit.class);
            abnormalCredit.setApplyId(appId);
            abnormalCredit.setId(UUID.randomUUID().toString());
            
            if(jso.containsKey(RESULT_KEY_OVERDUES)){
                for (Object aDetail : jso.getJSONArray(RESULT_KEY_OVERDUES)) {
                    MspAbnormalcreditdetail detail = JSONObject.parseObject(aDetail.toString(), MspAbnormalcreditdetail.class);
                    detail.setAbcdId(abnormalCredit.getId());
                    mspAbnormalcreditdetailMapper.insert(detail);
                }
            }
            mspAbnormalcreditMapper.insert(abnormalCredit);
        }
    }
    
    // 验证是否为空
    private boolean validateIsNull(JSONObject validSifa,String key) {
        if(validSifa.containsKey(key) && validSifa.get(key)!=null && !"null".equals(validSifa.get(key))){
            return true;
        }
        return false;
    }
}
