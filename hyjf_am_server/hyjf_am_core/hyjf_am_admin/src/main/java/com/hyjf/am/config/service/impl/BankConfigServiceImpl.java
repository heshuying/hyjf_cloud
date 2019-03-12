package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.mapper.auto.*;
import com.hyjf.am.config.dao.mapper.customize.JXBankConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class BankConfigServiceImpl implements BankConfigService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected BankConfigMapper bankConfigMapper;

	@Autowired
	protected BankReturnCodeConfigMapper bankReturnCodeConfigMapper;

	@Autowired
	protected CardBinMapper cardBinMapper;

	@Autowired
	private ParamNameMapper paramNameMapper;

	@Autowired
	private JxBankConfigMapper jxBankConfigMapper;


	@Autowired
	private JXBankConfigCustomizeMapper bankConfigCustomizeMapper;
	/**
	 * 获取银行卡配置信息
	 */
	@Override
	public BankConfig getBankConfigByBankId(Integer bankId) {
		if (bankId == null) {
			return null;
		}
		BankConfigExample example = new BankConfigExample();
		example.createCriteria().andIdEqualTo(bankId);
		List<BankConfig> BankConfigList = bankConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(BankConfigList)) {
			return BankConfigList.get(0);
		}
		return null;
	}

	/**
	 * 根据卡号获取银行卡配置信息
	 */
	@Override
	public BankConfig selectBankConfigByCode(String code) {
		if (code == null) {
			return null;
		}
		BankConfigExample example = new BankConfigExample();
		example.createCriteria().andCodeEqualTo(code);
		List<BankConfig> BankConfigList = bankConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(BankConfigList)) {
			return BankConfigList.get(0);
		}
		return null;
	}


	@Override
	public BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example) {
		List<BankReturnCodeConfig> retCodes = this.bankReturnCodeConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(retCodes)) {
			return retCodes.get(0);
		}
		return null;
	}

	/**
	 * 根据银行卡号获取bankId
	 * @param cardNo
	 * @return
	 */
	@Override
	public String queryBankIdByCardNo(String cardNo) {
		logger.info("-----------start----------------根据银行卡号获取bankId  cardNo  {}   ",cardNo);
		String bankId = null;
		if (cardNo == null || cardNo.length() < 14 || cardNo.length() > 19) {
			return "";
		}
		// 把常用的卡BIN放到最前面
		// 6位卡BIN
		String cardBin_6 = cardNo.substring(0, 6);
		bankId = this.getBankId(6, cardBin_6);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 7位卡BIN
		String cardBin_7 = cardNo.substring(0, 7);
		bankId = this.getBankId(7, cardBin_7);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 8位卡BIN
		String cardBin_8 = cardNo.substring(0, 8);
		bankId = this.getBankId(8, cardBin_8);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 9位卡BIN
		String cardBin_9 = cardNo.substring(0, 9);
		bankId = this.getBankId(9, cardBin_9);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 2位卡BIN
		String cardBin_2 = cardNo.substring(0, 2);
		bankId = this.getBankId(2, cardBin_2);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 3位卡BIN
		String cardBin_3 = cardNo.substring(0, 3);
		bankId = this.getBankId(3, cardBin_3);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 4位卡BIN
		String cardBin_4 = cardNo.substring(0, 4);
		bankId = this.getBankId(4, cardBin_4);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 5位卡BIN
		String cardBin_5 = cardNo.substring(0, 5);
		bankId = this.getBankId(5, cardBin_5);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 10位卡BIN
		String cardBin_10 = cardNo.substring(0, 10);
		bankId = this.getBankId(10, cardBin_10);
		if (StringUtils.isNotBlank(cardBin_10)) {
			return bankId;
		}
		logger.info("------------end---------------根据银行卡号获取bankId  cardNo  {}   ",cardNo);
		return bankId;
	}

	private String getBankId(int cardBinLength, String cardBin) {

		logger.info("------22222222222------start---------------getBankId根据银行卡号获取bankId  cardBinLength  {} ,cardBin{}  ",cardBinLength,cardBin);
		CardBinExample example = new CardBinExample();
		CardBinExample.Criteria cra = example.createCriteria();
		cra.andBinLengthEqualTo(cardBinLength);
		cra.andBinValueEqualTo(cardBin);
		logger.info("---------111111111111111111-------------getBankId根据银行卡号获取bankId  list  {} ");
		List<CardBin> list = this.cardBinMapper.selectByExample(example);
		logger.info("------------list---------------getBankId根据银行卡号获取bankId  list  {} ",JSONObject.toJSONString(list));
		if (list != null && list.size() > 0) {
			return list.get(0).getBankId();
		}
		return null;
	}

	/**
	 * 获取银行列表
	 */
	@Override
	public List<BankConfig> selectBankConfigList(){
		List<BankConfig> banks = bankConfigMapper.selectByExample(new BankConfigExample());
		return banks;
	}

	/**
	 * 获取status=1的银行列表
	 */
	@Override
	public List<BankConfig> getBankConfigListByStatus(BankConfigVO bankConfigVO){
		BankConfigExample example = new BankConfigExample();
		BankConfigExample.Criteria criteria = example.createCriteria();
		// 条件查询
		criteria.andStatusEqualTo(1);
		return bankConfigMapper.selectByExample(example);
	}
	/**
	 * 获取银行列表(快捷支付卡)
	 */
	@Override
	public List<BankConfig> getBankRecordListByQuickPayment(BankConfigVO bankConfigVO){
		BankConfigExample example = new BankConfigExample();
		BankConfigExample.Criteria cra = example.createCriteria();
		cra.andQuickPaymentEqualTo(1);//支持快捷支付
		example.setOrderByClause(" id");
		return bankConfigMapper.selectByExample(example);
	}

	@Override
	public List<ParamName> getParamNameList(String nameClass) {
		ParamNameExample example = new ParamNameExample();
		ParamNameExample.Criteria cra = example.createCriteria();
		cra.andNameClassEqualTo(nameClass);
		cra.andDelFlagEqualTo(Integer.parseInt(CustomConstants.FLAG_NORMAL));
		example.setOrderByClause(" sort ASC ");
		return this.paramNameMapper.selectByExample(example);
	}
	/**
	 * 分页查询银行配置条数
	 */
	@Override
	public int selectBankConfigCount(BankConfigVO banksConfigVO, int limitStart, int limitEnd){
		BankConfigExample example = new BankConfigExample();
		BankConfigExample.Criteria criteria =example.createCriteria();
		if (banksConfigVO.getName()!= null ) {
			criteria.andNameEqualTo(banksConfigVO.getName());
		}
		if(banksConfigVO.getCode() != null){
			criteria.andCodeEqualTo(banksConfigVO.getCode());
		}
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		return bankConfigMapper.countByExample(example);
	}
	/**
	 * 分页查询银行配置
	 */
	@Override
	public List<BankConfig> selectBankConfigListByPage(BankConfigVO banksConfigVO, int limitStart, int limitEnd){
		BankConfigExample example = new BankConfigExample();
		BankConfigExample.Criteria criteria =example.createCriteria();
		if (banksConfigVO.getName()!= null ) {
			criteria.andNameEqualTo(banksConfigVO.getName());
		}
		if(banksConfigVO.getCode() != null){
			criteria.andCodeEqualTo(banksConfigVO.getCode());
		}
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		List<BankConfig> BankConfigList = bankConfigMapper.selectByExample(example);
		return BankConfigList;
	}

	/**
	 * 根据bankName查询银行配置
	 */
	@Override
	public List<BankConfig> selectBankConfigByBankName(BankConfigVO banksConfigVO,int limitStart, int limitEnd){
		BankConfigExample example = new BankConfigExample();
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
//		example.setOrderByClause("sort_id ASC");
		BankConfigExample.Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo(0);

		// 条件查询
		if (StringUtils.isNotBlank(banksConfigVO.getName())) {
			criteria.andNameEqualTo(banksConfigVO.getName());
		}
		if (StringUtils.isNotBlank(banksConfigVO.getCode())) {
			criteria.andCodeEqualTo(banksConfigVO.getCode());
		}
		return bankConfigMapper.selectByExample(example);

	}

	/**
	 * 添加银行配置
	 */
	@Override
	public int insertBankConfig(AdminBankConfigRequest adminBankConfigRequest){
		BankConfig record =new BankConfig();
		BeanUtils.copyProperties(adminBankConfigRequest,record);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
//        record.setLogo("1");
		return bankConfigMapper.insertSelective(record);
	}
	/**
	 * 修改银行配置
	 */
	@Override
	public int updadteBankConfig(AdminBankConfigRequest adminBankConfigRequest){
		BankConfig record =new BankConfig();
		BeanUtils.copyProperties(adminBankConfigRequest,record);
		record.setUpdateTime(new Date());
		return bankConfigMapper.updateByPrimaryKeySelective(record);

	}

	/**
	 * 删除银行配置
	 */
	@Override
	public void deleteBankConfigById(Integer id){
		bankConfigMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据银行code获取银行配置
	 * @auth sunpeikai
	 * @param code 银行code,例如：招商银行,code是CMB
	 * @return
	 */
	@Override
	public List<BankConfig> getBankConfigByCode(String code) {
		BankConfigExample bankConfigExample = new BankConfigExample();
		BankConfigExample.Criteria criteria = bankConfigExample.createCriteria();
		criteria.andCodeEqualTo(code);
		return bankConfigMapper.selectByExample(bankConfigExample);
	}
	/**
	 * 根据bankId查找江西银行的银行卡配置表
	 * @param bankId
	 * @return
	 */
	@Override
	public JxBankConfig getJxBankConfigByBankId(int bankId){
		JxBankConfigExample example = new JxBankConfigExample();
		example.createCriteria().andBankIdEqualTo(bankId);
		List<JxBankConfig> jxBankConfigList = jxBankConfigMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(jxBankConfigList)) {
			return jxBankConfigList.get(0);
		}
		return null;
	}

	@Override
	public List<JxBankConfig> getRechargeQuotaLimit(Integer quickPayment) {
		JxBankConfigExample example = new JxBankConfigExample();
		example.createCriteria().andQuickPaymentEqualTo(quickPayment).andDelFlagEqualTo(0);
		List<JxBankConfig> jxBankConfigList = jxBankConfigMapper.selectByExample(example);
		return jxBankConfigList;
	}

	@Override
	public List<ParamName> getParamName(String other1) {
		ParamNameExample example = new ParamNameExample();
		ParamNameExample.Criteria cra = example.createCriteria();
		cra.andOther1EqualTo(other1);
		return this.paramNameMapper.selectByExample(example);
	}

	/**
	 * 获取银行列表（快捷卡）
	 * @param quickPayment
	 * @return
	 */
	@Override
	public List<JxBankConfig> getBankRecordList(Integer quickPayment) {
		return  bankConfigCustomizeMapper.selectByQuickPayment(quickPayment);
	}

	/**
	 * 根据银行名查找江西银行配置
	 * @param bankName
	 * @return
	 */
	@Override
	public JxBankConfig getBankConfigByBankName(String bankName) {
		if(StringUtils.isNotBlank(bankName)){
			JxBankConfigExample example = new JxBankConfigExample();
			example.createCriteria().andBankNameLike(bankName);
			List<JxBankConfig> jxBankConfigList = jxBankConfigMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(jxBankConfigList)) {
				return jxBankConfigList.get(0);
			}		}

		return null;
	}
}
