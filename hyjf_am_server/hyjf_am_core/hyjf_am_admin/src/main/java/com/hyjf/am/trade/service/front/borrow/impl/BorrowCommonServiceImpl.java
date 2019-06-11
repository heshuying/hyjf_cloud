package com.hyjf.am.trade.service.front.borrow.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.trade.bean.BorrowCommonFile;
import com.hyjf.am.trade.bean.BorrowCommonFileData;
import com.hyjf.am.trade.bean.BorrowWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample.Criteria;
import com.hyjf.am.trade.service.front.borrow.BorrowCommonService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

@Service
public class BorrowCommonServiceImpl extends BaseServiceImpl implements BorrowCommonService {

	Logger _log = LoggerFactory.getLogger(BorrowCommonServiceImpl.class);
	/**借款操作日志 操作类型*/
	public static final String BORROW_LOG_UPDATE = "修改";
	public static final String BORROW_LOG_ADD = "新增";
	public static final String BORROW_LOG_DEL = "删除";
    @Resource
	private CommonProducer commonProducer;

	@Value("${file.domain.url}")
    private String url; 
	@Value("${file.physical.path}")
	private String physical;
	@Value("${file.upload.real.path}")
	private String real;

	@Value("${wjt.instCode}")
	private String wjtInstCode;
	/**
	 * 汇消费的项目类型编号
	 */
	public static int PROJECT_TYPE_HXF = 8;
	public static JedisPool poolNew = RedisUtils.getPool();

	/**
	 * 根据主键判断权限维护中数据是否存在
	 * @param borrowNid
	 * @param borrowPreNid
	 * @return
	 */
	@Override
	public boolean isExistsRecord(String borrowNid, String borrowPreNid) {
		if (StringUtils.isNotEmpty(borrowNid) || StringUtils.isNotEmpty(borrowPreNid)) {
			BorrowInfoExample borrowExample = new BorrowInfoExample();
			 Criteria borrowCra = borrowExample.createCriteria();
			if (StringUtils.isNotEmpty(borrowNid)) {
				borrowCra.andBorrowNidEqualTo(borrowNid);
			}
			if (StringUtils.isNotEmpty(borrowPreNid)) {
				borrowCra.andBorrowPreNidEqualTo(borrowPreNid);
			}
			List<BorrowInfo> borrowList = this.borrowInfoMapper.selectByExample(borrowExample);
			if (borrowList != null && borrowList.size() > 0) {
				return true;
			}
			String redisBorrowPreNid = "";
			if(borrowPreNid.length()==11){
				 redisBorrowPreNid = RedisUtils.get("xjdBorrowPreNid");
			}else{
				 redisBorrowPreNid = RedisUtils.get("borrowPreNid");	
			}
				
			if (redisBorrowPreNid != null && redisBorrowPreNid.length() != 0) {
				//董泽杉优化如果手动输入的预编号和redis里长度不同则pass
				if(borrowPreNid.length()==redisBorrowPreNid.length()) {
					if (Long.valueOf(redisBorrowPreNid) >= Long.valueOf(borrowPreNid)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 权限维护插入
	 * @param borrowBean
	 * @throws Exception
	 */
	@Override
	public synchronized void insertRecord(BorrowCommonBean borrowBean,String adminUsername,int adminId) throws Exception {
		// 项目类型
		String projectType = borrowBean.getProjectType().toString();
		String beforeFix = this.getBorrowProjectClass(projectType);
		String borrowPreNid = borrowBean.getBorrowPreNid();
		List<BorrowCommonNameAccountVO> borrowCommonNameAccountList = borrowBean.getBorrowCommonNameAccountList();
		String isChaibiao = borrowBean.getIsChaibiao();
		// 借款标题 & 借款金额
		if (!"yes".equals(isChaibiao)) {
			borrowCommonNameAccountList = new ArrayList<BorrowCommonNameAccountVO>();
			BorrowCommonNameAccountVO borrowCommonNameAccount = new BorrowCommonNameAccountVO();
			borrowCommonNameAccount.setNames(borrowBean.getName());
			borrowCommonNameAccount.setAccounts(borrowBean.getAccount());
			borrowCommonNameAccountList.add(borrowCommonNameAccount);
		}
		if (borrowCommonNameAccountList != null && borrowCommonNameAccountList.size() > 0) {
			// 遍历相应的拆标数据
			for (int i = 0; i < borrowCommonNameAccountList.size(); i++) {
				BorrowCommonNameAccountVO borrowCommonNameAccount = borrowCommonNameAccountList.get(i);
				// String name = borrowCommonNameAccount.getNames();
				String account = borrowCommonNameAccount.getAccounts();
				if (StringUtils.isNotEmpty(account)) {
					// 操作redis
					String borrowPreNidNew = "";
					String borrowNid = "";

					Jedis jedis = poolNew.getResource();
					try {
						while ("OK".equals(jedis.watch("borrowPreNid"))) {
							List<Object> result = null;
							Transaction tx = jedis.multi();
							borrowPreNidNew = RedisUtils.get("borrowPreNid");
							if (StringUtils.isBlank(borrowPreNidNew)) {
								tx.set("borrowPreNid", borrowPreNid);
								borrowPreNidNew = borrowPreNid;
								result = tx.exec();
							} else if (borrowPreNidNew != null) {
								if(Long.parseLong(borrowPreNid)>Long.parseLong(borrowPreNidNew)){
									borrowPreNidNew = (String.valueOf(borrowPreNid));
								}else{
									borrowPreNidNew = (String.valueOf(Long.valueOf(borrowPreNidNew) + 1));
								}
								tx.set("borrowPreNid", borrowPreNidNew);
								result = tx.exec();
							}
							if (result == null || result.isEmpty()) {
								jedis.unwatch();
							} else {
								String ret = (String) result.get(0);
								if (ret != null && "OK".equals(ret)) {
									if (i == 0) {
										borrowPreNid = borrowPreNidNew;
									}
									borrowNid = beforeFix + borrowPreNidNew;
									break;
								} else {
									jedis.unwatch();
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						jedis.close();
					}
						

					// 插入huiyingdai_borrow
					BorrowWithBLOBs borrow = new BorrowWithBLOBs();
					// 借款表插入
					this.insertBorrowCommonData(borrowBean, borrow, borrowPreNid, borrowPreNidNew, borrowNid, account,adminUsername);
					// 删写redis的定时发标时间
					changeOntimeOfRedis(borrow);
					//添加修改日志
                    BorrowLog borrowLog = new BorrowLog();
                    borrowLog.setBorrowNid(borrowNid);
//                    String statusNameString = getBorrowStatusName(borrowBean.getStatus());
//                    borrowLog.setBorrowStatus(statusNameString);
                    borrowLog.setBorrowStatusCd(StringUtil.isBlank(borrowBean.getStatus())?0:Integer.valueOf(borrowBean.getStatus()));
                    borrowLog.setType(BORROW_LOG_ADD);
                    borrowLog.setCreateTime(new Date());
                    borrowLog.setCreateUserId(adminId);
                    borrowLog.setCreateUserName(adminUsername);

                    borrowLogMapper.insert(borrowLog);

					// 发送MQ 更改借款人机构编号
					if (wjtInstCode.equals(borrow.getPublishInstCode())){
						try {
							this.sendBorrowUserMQ(borrow.getUserId());
						}catch (Exception e){
							logger.error("发送修改标的借款人机构编号MQ失败...");
						}
					}
				}
			}
		}
	}

	/**
	 * @param borrow
	 */
		
	private void changeOntimeOfRedis(BorrowWithBLOBs borrow) {
		if (borrow.getVerifyStatus() == 3){
			//定时发标 写定时发标时间 redis 有效期10天 
			RedisUtils.set(CustomConstants.REDIS_KEY_ONTIME+CustomConstants.COLON+borrow.getBorrowNid(), String.valueOf(borrow.getOntime()), 864000);
		} else{
			//非定时发标 删redis
			RedisUtils.del(CustomConstants.REDIS_KEY_ONTIME+CustomConstants.COLON+borrow.getBorrowNid());
		}
	}

	/**
	 * 借款表插入
	 * 
	 * @param borrowBean
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertBorrowCommonData(BorrowCommonBean borrowBean, BorrowWithBLOBs borrow, String borrowPreNid, String newBorrowPreNid, String borrowNid,String account,String adminUsername)
			throws Exception {
		// 插入时间
		Date systemNowDate = GetDate.getDate();

		// 借款用户
		RUserExample example = new RUserExample();
		RUserExample.Criteria cra = example.createCriteria();
		cra.andUsernameEqualTo(borrowBean.getUsername().trim());

		List<RUser> userList = this.rUserMapper.selectByExample(example);
		if(StringUtils.isNotEmpty(borrowBean.getAssetAttributes())) {
			//插入资产属性
			borrow.setAssetAttributes(Integer.valueOf(borrowBean.getAssetAttributes()));
		}

		borrow.setUserId(userList.get(0).getUserId());
		// 借款人用户名
		borrow.setBorrowUserName(borrowBean.getUsername().trim());


//		cra1.andBankOpenAccountEqualTo(1);
//		cra1.andStatusEqualTo(0);
		if(borrowBean.getEntrustedFlg().equals("1")) {
			// 受托支付新增
			RUserExample example1 = new RUserExample();
			RUserExample.Criteria cra1 = example1.createCriteria();
			cra1.andUsernameEqualTo(borrowBean.getEntrustedUsername().trim());
			List<RUser> userList1 = this.rUserMapper.selectByExample(example1);
			borrow.setEntrustedFlg(Integer.valueOf(borrowBean.getEntrustedFlg()));
			if("1".equals(borrowBean.getEntrustedFlg())){
				borrow.setEntrustedUserName(borrowBean.getEntrustedUsername().trim());
				borrow.setEntrustedUserId(userList1.get(0).getUserId());
			}
		}
		borrow.setRemark(borrowBean.getRemark());
		// 项目申请人
//		borrow.setApplicant(borrowBean.getApplicant());
		borrow.setCreateUserName(adminUsername);
		borrow.setRemark(borrowBean.getRemark());
		// 担保机构用户名
		String repayOrgName =borrowBean.getRepayOrgName();
		// 担保机构用户名不为空的情况
		if (StringUtils.isNotEmpty(repayOrgName)) {
			// 根据担保机构用户名检索担保机构用户ID
			RUserExample usersExample = new RUserExample();
			RUserExample.Criteria userCri = usersExample.createCriteria();
			userCri.andUsernameEqualTo(repayOrgName);
		//	userCri.andBankOpenAccountEqualTo(1);// 汇付已开户
			List<RUser> ulist = this.rUserMapper.selectByExample(usersExample);
			// 如果用户名不存在，返回错误信息。
			if (ulist == null || ulist.size() == 0) {
				throw new Exception("获取担保机构失败,担保机构名称:" + repayOrgName);
			}
			Integer userId = ulist.get(0).getUserId();
			borrow.setRepayOrgUserId(userId);
			borrow.setIsRepayOrgFlag(1);
			// 担保机构用户名
			borrow.setRepayOrgName(repayOrgName);
		} else {
			borrow.setRepayOrgUserId(0);
			borrow.setIsRepayOrgFlag(0);
		}
		borrow.setBorrowIncreaseMoney(borrowBean.getBorrowIncreaseMoney());
		borrow.setBorrowInterestCoupon(borrowBean.getBorrowInterestCoupon());
		borrow.setBorrowTasteMoney(borrowBean.getBorrowTasteMoney());
		// 项目编号
		borrowBean.setBorrowNid(borrowNid);
		// 借款标题
		borrow.setName(borrowBean.getProjectName());
		// 资产机构编号
		borrow.setInstCode(borrowBean.getInstCode());
		// 产品类型
		borrow.setAssetType(borrowBean.getProjectType());
		// 根据资产机构编号,产品类型检索产品名称
		String assetTypeName = this.getAssetTypeName(borrowBean.getInstCode(), borrowBean.getProjectType());
		// 产品名称
		borrow.setAssetTypeName(assetTypeName == null ? "" : assetTypeName);
		// 状态
		borrow.setStatus(0);
		//项目标题
		borrow.setProjectName(borrowBean.getProjectName());
		// 新标（20170612改版后都为新标）
		borrow.setIsNew(1);
		
		// 借款方式
		// 车辆抵押:2 房产抵押:1
		if (StringUtils.equals("2", borrowBean.getTypeCar())) {
			borrow.setType(borrowBean.getTypeCar());
		}

		if (StringUtils.equals("1", borrowBean.getTypeHouse())) {
			borrow.setType(borrowBean.getTypeHouse());
		}

		if (StringUtils.equals("2", borrowBean.getTypeCar()) && StringUtils.equals("1", borrowBean.getTypeHouse())) {
			borrow.setType("3");
		}

		if (StringUtils.isEmpty(borrow.getType())) {
			borrow.setType("0");
		}
		borrow.setCreateTime(new Date());
		// 添加IP
		borrow.setAddIp(GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest()));
		// 借款总金额
		borrow.setAccount(new BigDecimal(account));
		borrow.setBorrowAccountWait(new BigDecimal(account));
		// 财务状况
		if (StringUtils.isEmpty(borrowBean.getAccountContents())) {
			borrow.setAccountContents(StringUtils.EMPTY);
		} else {
			borrow.setAccountContents(borrowBean.getAccountContents());
		}
		// 是否可以进行借款
		borrow.setBorrowStatus(0);
		// 满表审核状态
		borrow.setBorrowFullStatus(0);
		// 项目编号
		borrow.setBorrowNid(borrowNid);
		// 借款预编码
		borrow.setBorrowPreNid(borrowPreNid);
		/******* 修改预编号生成规则 pcc ********/
		// 新借款预编码
		borrow.setBorrowPreNidNew(newBorrowPreNid);
		/******* 修改预编号生成规则 pcc ********/
		// 已经募集的金额
		borrow.setBorrowAccountYes(BigDecimal.ZERO);
		// 借款用途
		borrow.setBorrowUse(borrowBean.getBorrowUse());
		// 还款方式
		borrow.setBorrowStyle(borrowBean.getBorrowStyle());
		// 借款期限
		borrow.setBorrowPeriod(Integer.valueOf(borrowBean.getBorrowPeriod().trim()));
		// 借款利率
		borrow.setBorrowApr(new BigDecimal(borrowBean.getBorrowApr().trim()));
		// 项目描述
		borrow.setBorrowContents(borrowBean.getBorrowContents());
		// 新增协议期限字段
		if (StringUtils.isNotEmpty(borrowBean.getContractPeriod())) {
			borrow.setContractPeriod(Integer.parseInt(borrowBean.getContractPeriod()));
		}
		if (StringUtils.isNotEmpty(borrowBean.getBorrowLevel())) {
			borrow.setBorrowLevel(borrowBean.getBorrowLevel());
		}
		if (StringUtils.isNotEmpty(borrowBean.getInvestLevel())){
			borrow.setInvestLevel(borrowBean.getInvestLevel());
		}
		// ----------风险缓释金添加-------
		// 资产编号
		borrow.setBorrowAssetNumber(borrowBean.getBorrowAssetNumber());
		// 项目来源
		borrow.setBorrowProjectSource(borrowBean.getBorrowProjectSource());
		// 起息时间
		borrow.setBorrowInterestTime(borrowBean.getBorrowInterestTime());
		// 到期时间
		borrow.setBorrowDueTime(borrowBean.getBorrowDueTime());
		// 保障方式
		borrow.setBorrowSafeguardWay(borrowBean.getBorrowSafeguardWay());
		// 收益说明
		borrow.setBorrowIncomeDescription(borrowBean.getBorrowIncomeDescription());
		// 发行人
		borrow.setBorrowPublisher(borrowBean.getBorrowPublisher());

		// ----------风险缓释金添加 end-------

		/**************网站改版添加 ******************/
		//融资用途
		borrow.setFinancePurpose(borrowBean.getFinancePurpose());
		//月薪收入
		borrow.setMonthlyIncome(borrowBean.getMonthlyIncome());
		//还款来源
		borrow.setPayment(borrowBean.getPayment());
		//第一还款来源
		borrow.setFirstPayment(borrowBean.getFirstPayment());
		//第二还款来源
		borrow.setSecondPayment(borrowBean.getSecondPayment());
		//费用说明
		borrow.setCostIntrodution(borrowBean.getCostIntrodution());
		//财务状况
		borrow.setFianceCondition(borrowBean.getFianceCondition());
		/**************网站改版添加end ******************/

		// 借款有效时间
		if (StringUtils.isNotEmpty(borrowBean.getBorrowValidTime())) {
			borrow.setBorrowValidTime(Integer.valueOf(borrowBean.getBorrowValidTime()));
		} else {
			borrow.setBorrowValidTime(0);
		}
		borrow.setRegistStatus(0);// 银行备案状态
		// 银行备案时间
		if (StringUtils.isNotEmpty(borrowBean.getBankRegistDays())) {
			borrow.setBankRegistDays(Integer.valueOf(borrowBean.getBankRegistDays()));
		} else {
			borrow.setBankRegistDays(0);
		}
		borrow.setRepayStatus(0);// 标的还款状态
		// 银行募集开始时间
		String rasieStartDate = GetOrderIdUtils.getOrderDate();
		borrow.setBankRaiseStartDate(rasieStartDate);
		// 银行募集结束时间
		String raiseEndDate = this.getBankRaiseEndDate(rasieStartDate, borrow.getBankRegistDays(), borrow.getBorrowValidTime());
		borrow.setBankRaiseEndDate(raiseEndDate);
		// 银行用借款期限
		if (borrow.getBorrowStyle().equals(CustomConstants.BORROW_STYLE_ENDDAY)) {
			borrow.setBankBorrowDays(borrow.getBorrowPeriod());
		}
		/** 月标直接写死每月30天，银行不校验.计算过于麻烦 */
		else {
			borrow.setBankBorrowDays(borrow.getBorrowPeriod() * 30);
		}
		// 借款到期时间
		borrow.setBorrowEndTime("");
		borrow.setBorrowUpfiles("");// 插入时不用的字段
		// 最低投标金额
		if (StringUtils.isNotEmpty(borrowBean.getTenderAccountMin())) {
			borrow.setTenderAccountMin(Integer.valueOf(borrowBean.getTenderAccountMin()));
		} else {
			borrow.setTenderAccountMin(0);
		}

		// 最高投标金额
		if (StringUtils.isNotEmpty(borrowBean.getTenderAccountMax())) {
			borrow.setTenderAccountMax(Integer.valueOf(borrowBean.getTenderAccountMax()));
		} else {
			borrow.setTenderAccountMax(0);
		}
		// 投标次数
		borrow.setTenderTimes(0);
		// 应还款总额
		borrow.setRepayAccountAll(BigDecimal.ZERO);
		// 总还款利息
		borrow.setRepayAccountInterest(BigDecimal.ZERO);
		// 总还款本金
		borrow.setRepayAccountCapital(BigDecimal.ZERO);
		// 已还款总额
		borrow.setRepayAccountYes(BigDecimal.ZERO);
		// 已还款利息
		borrow.setRepayAccountInterestYes(BigDecimal.ZERO);
		// 已还款本金
		borrow.setRepayAccountCapitalYes(BigDecimal.ZERO);
		// 未还款总额
		borrow.setRepayAccountWait(BigDecimal.ZERO);
		// 未还款利息
		borrow.setRepayAccountInterestWait(BigDecimal.ZERO);
		// 未还款本金
		borrow.setRepayAccountCapitalWait(BigDecimal.ZERO);
		// 最后还款时间
		borrow.setRepayLastTime(0);// 插入时不用的字段
		borrow.setRepayNextTime(0);// 插入时不用的字段
		borrow.setRepayFullStatus(0);// 插入时不用的字段
		borrow.setRepayFeeNormal(BigDecimal.ZERO); // 插入时不用的字段
		borrow.setDiyaContents(""); // 插入时不用的字段
		borrow.setBorrowPawnDescription(""); // 插入时不用的字段
		// 初审核人
		borrow.setVerifyUserid("0");
		// 正式发标时间
		borrow.setVerifyTime(0);
		// 初审通过时间
		borrow.setVerifyOverTime(0);
		// 初审核备注
		borrow.setVerifyRemark("");
		borrow.setVerifyContents(""); // 插入时不用的字段 审核备注
		borrow.setVerifyStatus(0); // 插入时不用的字段
		// 复审核人
		borrow.setReverifyUserid("0");
		// 复审核时间
		borrow.setReverifyTime(0);
		// 复审核备注
		borrow.setReverifyRemark("");
		borrow.setReverifyStatus(0); // 插入时不用的字段
		borrow.setReverifyContents(""); // 插入时不用的字段 审核复审标注
		borrow.setUpfilesId(""); // 插入时不用的字段 发标上传图片
		borrow.setBorrowRunningUse(""); // 插入时不用的字段 资金运转-用途
		borrow.setBorrowRunningSoruce(""); // 插入时不用的字段 资金运转-来源
		// 担保机构 风险控制措施-机构
		borrow.setBorrowMeasuresInstit(borrowBean.getBorrowMeasuresInstit());
		// 机构介绍
		borrow.setBorrowCompanyInstruction(borrowBean.getBorrowCompanyInstruction());
		// 操作流程
		borrow.setBorrowOperatingProcess(borrowBean.getBorrowOperatingProcess());
		// 抵押物信息 风险控制措施-抵押物
		borrow.setBorrowMeasuresMort(borrowBean.getBorrowMeasuresMort());
		// 本息保障 险控制措施-措施
		borrow.setBorrowMeasuresMea(borrowBean.getBorrowMeasuresMea());
		borrow.setBorrowAnalysisPolicy(""); // 插入时不用的字段 政策及市场分析-政策支持
		borrow.setBorrowAnalysisMarket(""); // 插入时不用的字段 政策及市场分析-市场分析
		borrow.setBorrowCompany(""); // 插入时不用的字段 企业背景
		borrow.setBorrowCompanyScope(""); // 插入时不用的字段 企业信息-营业范围
		borrow.setBorrowCompanyBusiness(""); // 插入时不用的字段 企业信息-经营状况
		// 项目资料
		borrow.setFiles(this.getUploadImage(borrowBean, "", borrowNid));
		// 项目类型
		borrow.setProjectType( borrowBean.getProjectType());

		//new addede by LIBIN
		//手动发标(进计划的标)需要插入 逾期利率 + 逾期免息天数
		if(StringUtils.isNotEmpty(borrowBean.getIsEngineUsed())){
			// 要进计划的标的(手动)
			if("1".equalsIgnoreCase(borrowBean.getIsEngineUsed())){
				borrow.setLateInterestRate(this.getLateInterestRate(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod()))); //逾期利率(汇计划用)late_interest_rate
				borrow.setLateFreeDays(Integer.valueOf(this.getLateFreeDays(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod())))); // 逾期免息天数(汇计划用)late_free_days
			}
		}

		if (PROJECT_TYPE_HXF!=borrowBean.getProjectType()) {
			// 放款服务费
			String borrowServiceScale = this.getBorrowServiceScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(),borrowBean.getInstCode(), Integer.valueOf(borrowBean.getBorrowPeriod()));
			borrow.setServiceFeeRate(borrowServiceScale);
            // 还款服务费率
            borrow.setManageFeeRate(this.getBorrowManagerScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod())));
            // 收益差率
            borrow.setDifferentialRate(this.getBorrowReturnScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod())));
        } else if (PROJECT_TYPE_HXF==borrowBean.getProjectType()) {
			// 放款服务费
			borrow.setServiceFeeRate("0.00");
			JSONObject jsonObject = new JSONObject();
			jsonObject = this.getBorrowManagerScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), Integer.valueOf(borrowBean.getBorrowPeriod()), jsonObject);
			if (StringUtils.isNotEmpty(jsonObject.getString("borrowManagerScale"))) {
				// 还款服务费率(上限)
				borrow.setManageFeeRate(jsonObject.getString("borrowManagerScale"));
			}
			if (StringUtils.isNotEmpty(jsonObject.getString("borrowManagerScaleEnd"))) {
				// 还款服务费率(下限)
				borrow.setBorrowManagerScaleEnd(jsonObject.getString("borrowManagerScaleEnd"));
			}
		}
		// 可出借平台_PC
		if (StringUtils.isEmpty(borrowBean.getCanTransactionPc())) {
			borrow.setCanTransactionPc("0");
		} else {
			borrow.setCanTransactionPc(borrowBean.getCanTransactionPc());
		}

		// 可出借平台_微网站
		if (StringUtils.isEmpty(borrowBean.getCanTransactionWei())) {
			borrow.setCanTransactionWei("0");
		} else {
			borrow.setCanTransactionWei(borrowBean.getCanTransactionWei());
		}

		// 可出借平台_IOS
		if (StringUtils.isEmpty(borrowBean.getCanTransactionIos())) {
			borrow.setCanTransactionIos("0");
		} else {
			borrow.setCanTransactionIos(borrowBean.getCanTransactionIos());
		}

		// 可出借平台_Android
		if (StringUtils.isEmpty(borrowBean.getCanTransactionAndroid())) {
			borrow.setCanTransactionAndroid("0");
		} else {
			borrow.setCanTransactionAndroid(borrowBean.getCanTransactionAndroid());
		}

		// 运营标签
		if (StringUtils.isEmpty(borrowBean.getOperationLabel())) {
			borrow.setOperationLabel("0");
		} else {
			borrow.setOperationLabel(borrowBean.getOperationLabel());
		}
		// 企业还是个人
		borrow.setCompanyOrPersonal(Integer.valueOf(borrowBean.getCompanyOrPersonal()));
		// 定时发标
		borrow.setOntime(0);
		// 汇资管的内容设置
		this.setHZGInfo(borrowBean, borrow);
		// 更新时间
		borrow.setUpdatetime(systemNowDate);
		// new added 插入是否调用引擎字段 default 0
		borrow.setIsEngineUsed(Integer.valueOf(borrowBean.getIsEngineUsed()));

		if("0".equalsIgnoreCase(borrowBean.getIsEngineUsed())){//如果不使用引擎(非计划)，则web端显示
			//是否展示(隐藏测试标用:0:显示,1:不显示)
			borrow.setIsShow(0);
		} else {
			borrow.setIsShow(1);
		}
		// 定向发标平台
		borrow.setPublishInstCode(borrowBean.getPublishInstCode());

		borrow.setIsMonth(borrowBean.getIsMonth());
		// 新建标的有标签的先打上标签
		borrow.setLabelId(borrowBean.getLabelId());
		BorrowInfoWithBLOBs borrowinfo=new BorrowInfoWithBLOBs();
		// 产品加息 add by liuyang 20180730 start
		// 根据项目编号查询项目类型配置
		BorrowProjectType borrowProjectType = this.getBrrowProjectTpyeByProjectType(String.valueOf(borrowBean.getProjectType()));
		BeanUtils.copyProperties(borrow, borrowinfo);
		if (borrowProjectType.getIncreaseInterestFlag() == 1 && !StringUtils.isEmpty(borrowBean.getBorrowExtraYield()) && new BigDecimal(borrowBean.getBorrowExtraYield()).compareTo(BigDecimal.ZERO) > 0) {
			// 是否加息
			borrow.setIncreaseInterestFlag(1);
			borrowinfo.setIncreaseInterestFlag(1);
			// 产品加息率
			borrowinfo.setBorrowExtraYield(new BigDecimal(borrowBean.getBorrowExtraYield()));
		} else {
			// 是否加息
			borrowinfo.setIncreaseInterestFlag(0);
			// 产品加息率
			if (!StringUtils.isEmpty(borrowBean.getBorrowExtraYield())) {
				borrowinfo.setBorrowExtraYield(new BigDecimal(borrowBean.getBorrowExtraYield()));
			} else {
				borrowinfo.setBorrowExtraYield(new BigDecimal(0));
			}
		}
		// 产品加息 add by liuyang 20180730 end
		boolean isBorrowInsertFlag = this.borrowMapper.insertSelective(borrow) >0 ? true:false;
		logger.info("准备插入borrow表,标的编号:[" + borrowNid + "].");
		if (isBorrowInsertFlag) {
			logger.info("borrow表插入数据成功,标的编号:[" + borrowNid + "].");
		}else{
			logger.info("borrow表插入数据失败~~~~~~,标的编号:[" + borrowNid + "].");
		}
		borrowinfo.setEntrustedFlg(Integer.valueOf(borrowBean.getEntrustedFlg()));
		if("1".equals(borrowBean.getEntrustedFlg())){
			borrowinfo.setEntrustedUserName(borrowBean.getEntrustedUsername().trim());
			borrowinfo.setEntrustedUserId(this.getRUser(borrowBean.getEntrustedUsername().trim()).getUserId());
		} else {
			borrowinfo.setEntrustedUserName("");
			borrowinfo.setEntrustedUserId(0);
		}
		borrowinfo.setTrusteePayTime(0);
		logger.info("准备插入borrow_info表,标的编号:[" + borrowNid + "].");
		boolean isBorrowInfoInsertFlag = this.borrowInfoMapper.insertSelective(borrowinfo) > 0 ? true : false;

		if (isBorrowInfoInsertFlag) {
			logger.info("borrow_info表插入数据成功,标的编号:[" + borrowNid + "].");
		}else{
			logger.info("borrow_info表插入失败~~~~!!,标的编号:[" + borrowNid + "].");
		}
		// 个人信息(信批新增字段)
		this.insertBorrowManinfo(borrowNid, borrowBean, borrow);
		// 公司信息(信批新增字段)
		this.insertBorrowUsers(borrowNid, borrowBean, borrow);
		// 车辆信息
		this.insertBorrowCarinfo(borrowNid, borrowBean, borrow);
		// 房产信息
		this.insertBorrowHouses(borrowNid, borrowBean, borrow);
		// 认证信息
		this.insertBorrowCompanyAuthen(borrowNid, borrowBean, borrow);
	}

	private String getBankRaiseEndDate(String rasieStartDate, Integer bankRegistDays, Integer borrowValidTime) {
		Integer raiseStartdate = GetDate.strYYYYMMDD3Timestamp3(rasieStartDate);
		int raistEndDate = raiseStartdate + bankRegistDays * 60 * 60 * 24 + borrowValidTime * 60 * 60 * 24;
		return GetDate.getDateMyTimeInMillisYYYYMMDD(raistEndDate);
	}

	/**
	 * 更新
	 *
	 * @param borrowBean
	 * @param adminUsername
	 * @param adminId
	 * @throws Exception
	 */
	@Override
	public void modifyRecord(BorrowCommonBean borrowBean,String adminUsername,int adminId) throws Exception {
		String borrowNid = borrowBean.getBorrowNid();
		if (StringUtils.isNotEmpty(borrowNid)) {
			BorrowInfoExample borrowExample = new BorrowInfoExample();
			BorrowInfoExample.Criteria borrowCra = borrowExample.createCriteria();
			borrowCra.andBorrowNidEqualTo(borrowNid);

			BorrowInfo borrowList = this.getBorrowInfoByNid(borrowNid);
			if (borrowList != null ) {

				borrowExample = new BorrowInfoExample();
				borrowCra = borrowExample.createCriteria();
				borrowCra.andBorrowNidEqualTo(borrowList.getBorrowNid());
				//添加修改日志
				BorrowLog borrowLog = new BorrowLog();
				List<BorrowInfoWithBLOBs> borrowAllList = this.borrowInfoMapper.selectByExampleWithBLOBs(borrowExample);
				if (borrowAllList != null && borrowAllList.size() > 0) {
					for (BorrowInfoWithBLOBs borrow : borrowAllList) {
						BorrowWithBLOBs bwb=new BorrowWithBLOBs();
						 BeanUtils.copyProperties(borrow,bwb);
						 BeanUtils.copyProperties(this.getBorrow(borrowNid),bwb);
						 borrowLog.setBorrowStatus(bwb.getStatus().toString());
						 bwb.setInfoId(borrow.getId());
						// 借款表更新(此更新中有关于散标进计划的redis判断)
						this.updateBorrowCommonData(borrowBean, bwb, borrowNid,adminUsername,adminId,borrow.getId());

						if (borrowBean.getVerifyStatus() != null && StringUtils.isNotEmpty(borrowBean.getVerifyStatus())) {
							if(Integer.valueOf(borrowBean.getVerifyStatus()) == 4) {
								if (bwb.getIsEngineUsed().equals(1)) {
									//成功后到关联计划队列 RabbitMQConstants.ROUTINGKEY_BORROW_ISSUE
									try {
										JSONObject params = new JSONObject();
										params.put("borrowNid", borrow.getBorrowNid());
										// modify by yangchangwei
										// 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
										commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
														MQConstant.AUTO_ASSOCIATE_PLAN_ADMIN_INSERT_TAG, borrow.getBorrowNid(), params),
												2);
									} catch (MQException e) {
										logger.error("发送【关联计划队列】MQ失败...");
									}
								}
								// 发送消息队列到合规上报数据
								// 2.common借款详情展示页面提交发标
								JSONObject params = new JSONObject();
								params.put("borrowNid", borrow.getBorrowNid());
								params.put("userId", borrow.getUserId());
								commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTING_TAG, UUID.randomUUID().toString(), params),
										MQConstant.HG_REPORT_DELAY_LEVEL);


								// add by liuyang 20190507 wbs系统标的信息推送MQ start
								// 如果是散标,则往wbs系统推送标的
								if (StringUtil.isBlank(bwb.getPlanNid())) {
									try {
										Borrow nowBorrow = this.getBorrow(borrow.getBorrowNid());
										// 判断标的当前状态是否是投资中的状态
										if (nowBorrow != null && nowBorrow.getStatus() == 2 && StringUtils.isEmpty(nowBorrow.getPlanNid()) && bwb.getIsEngineUsed()== 0 ) {
											logger.info("WBS系统标的信息推送MQ:标的号:[" + borrow.getBorrowNid() + "].");
											sendWbsBorrowInfo(borrow.getBorrowNid(), "2", 0);
										}
									} catch (Exception e) {
										logger.error("wbs系统标的信息推送MQ失败,标的编号:[" + borrow.getBorrowNid() + "].");
									}
								}
								// add by liuyang 20190507 wbs系统标的信息推送MQ end
							}
						}

						borrowLog.setBorrowNid(borrowNid);
						Map<String, String> map = CacheUtil.getParamNameMap("BORROW_STATUS");
						//String statusNameString = getBorrowStatusName(borrowBean.getStatus());
						borrowLog.setBorrowStatusCd(StringUtil.isBlank(borrowLog.getBorrowStatus())?0:Integer.valueOf(borrowLog.getBorrowStatus()));
						borrowLog.setBorrowStatus(map.get(borrowLog.getBorrowStatus()));
						borrowLog.setType(BORROW_LOG_UPDATE);
						borrowLog.setCreateTime(new Date());
						borrowLog.setCreateUserId(adminId);
						borrowLog.setCreateUserName(adminUsername);

			            borrowLogMapper.insert(borrowLog);
						// 删写redis的定时发标时间
						changeOntimeOfRedis(bwb);
					}
				}
			}

		}
	}

	/**
     * 推送消息到MQ
     */
//    public void sendToMQ(BorrowCommonBean borrowBean,String routingKey){
		// 加入到消息队列
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("mqMsgId", GetCode.getRandomCode(10));
//        params.put("borrowNid", borrowBean.getBorrowNid());
//        params.put("instCode", borrowBean.getInstCode());
//        //RabbitMQConstants.EXCHANGES_COUPON
//        rabbitTemplate.convertAndSend("hyjf-direct-exchange", routingKey, JSONObject.toJSONString(params));
//	}

	/**
	 * 借款表更新
	 * 
	 * @param borrowBean
	 * @param borrow
	 * @throws Exception
	 */
	@Override
	public void updateBorrowCommonData(BorrowCommonBean borrowBean, BorrowWithBLOBs borrow, String borrowMainNid,String adminUsername,int adminId,int infoId) throws Exception {
		// 插入时间
		int systemNowDateLong = GetDate.getNowTime10();
		Date systemNowDate = GetDate.getDate(systemNowDateLong);
		// 项目编号
		String borrowNid = borrow.getBorrowNid();
		borrow.setRemark(borrowBean.getRemark());
		int status = borrow.getStatus();
		int registStatus = borrow.getRegistStatus();
		if (borrowMainNid.equals(borrowNid)) {
			borrow.setBorrowIncreaseMoney(borrowBean.getBorrowIncreaseMoney());
			borrow.setBorrowInterestCoupon(borrowBean.getBorrowInterestCoupon() != null ? borrowBean.getBorrowInterestCoupon() : 0);
			borrow.setBorrowTasteMoney(borrowBean.getBorrowTasteMoney() != null ? borrowBean.getBorrowTasteMoney() : 0);

		}
		// 用户名(只要是备案中且不是备案成功都可以更表)
		if (status == 0 && registStatus != 2 && borrowMainNid.equals(borrowNid)) {
			// 借款用户
//			UsersExample example = new UsersExample();
//			UsersExample.Criteria cra = example.createCriteria();
//			cra.andUsernameEqualTo(borrowBean.getUsername());
//			cra.andBankOpenAccountEqualTo(1);
//			cra.andStatusEqualTo(0);
//			List<Users> userList = this.usersMapper.selectByExample(example);
			borrow.setUserId(this.getRUser(borrowBean.getUsername()).getUserId());
			// 借款人用户名
			borrow.setBorrowUserName(borrowBean.getUsername());

			// 受托支付新增
//			UsersExample example1 = new UsersExample();
//			UsersExample.Criteria cra1 = example1.createCriteria();
//			cra1.andUsernameEqualTo(borrowBean.getEntrustedUsername().trim());
//			cra1.andBankOpenAccountEqualTo(1);
//			cra1.andStatusEqualTo(0);
//			List<Users> userList1 = this.usersMapper.selectByExample(example1);
			borrow.setEntrustedFlg(Integer.valueOf(borrowBean.getEntrustedFlg()));
			if("1".equals(borrowBean.getEntrustedFlg())){
				borrow.setEntrustedUserName(borrowBean.getEntrustedUsername().trim());
				borrow.setEntrustedUserId(this.getRUser(borrowBean.getEntrustedUsername().trim()).getUserId());
			} else {
				borrow.setEntrustedUserName("");
				borrow.setEntrustedUserId(0);
			}

			// 项目申请人
//			borrow.setApplicant(borrowBean.getApplicant());
			//项目标题
			borrow.setProjectName(borrowBean.getProjectName());
			// 担保机构用户名
			// borrow.setRepayOrgName(borrowBean.getRepayOrgName());
			// 担保机构用户名不为空的情况
			if (StringUtils.isNotEmpty(borrowBean.getRepayOrgName())) {
				// 根据担保机构用户名检索担保机构用户ID
//				UsersExample usersExample = new UsersExample();
//				UsersExample.Criteria userCri = usersExample.createCriteria();
//				userCri.andUsernameEqualTo(borrowBean.getRepayOrgName());
//				userCri.andBankOpenAccountEqualTo(1);// 汇付已开户
				 RUser ulist = this.getRUser(borrowBean.getRepayOrgName());
				// 如果用户名不存在，返回错误信息。
				if (ulist == null ) {
					throw new Exception("获取担保机构失败,担保机构名称:" + borrowBean.getRepayOrgName());
				}
				Integer userId = ulist.getUserId();
				borrow.setRepayOrgUserId(userId);
				borrow.setIsRepayOrgFlag(1);
				// 担保机构用户名
				borrow.setRepayOrgName(borrowBean.getRepayOrgName());
			}
		}

		// 借款标题
		if (borrowMainNid.equals(borrowNid)) {
			borrow.setName(borrowBean.getProjectName());
		}
		// 借款方式
		// 车辆抵押:2 房产抵押:1
		if (StringUtils.equals("2", borrowBean.getTypeCar())) {
			borrow.setType(borrowBean.getTypeCar());
		}

		if (StringUtils.equals("1", borrowBean.getTypeHouse())) {
			borrow.setType(borrowBean.getTypeHouse());
		}

		if (StringUtils.equals("2", borrowBean.getTypeCar()) && StringUtils.equals("1", borrowBean.getTypeHouse())) {
			borrow.setType("3");
		}

		if (StringUtils.isEmpty(borrow.getType())) {
			borrow.setType("0");
		}

		if (status == 0 && borrowMainNid.equals(borrowNid)) {
			// 冻结额度 初审通过不能修改
//			borrow.setAmountAccount(new BigDecimal(borrowBean.getAccount()));
			// 借款总金额 初审通过不能修改
			borrow.setAccount(new BigDecimal(borrowBean.getAccount()));
		}

//		// 车辆抵押:2 房产抵押:1
//		if (StringUtils.equals("2", borrowBean.getTypeCar())) {
//			borrow.setType(borrowBean.getTypeCar());
//		}
//
//		if (StringUtils.equals("1", borrowBean.getTypeHouse())) {
//			borrow.setType(borrowBean.getTypeHouse());
//		}
//
//		if (StringUtils.equals("2", borrowBean.getTypeCar()) && StringUtils.equals("1", borrowBean.getTypeHouse())) {
//			borrow.setType("3");
//		}

		// 财务状况
		if (StringUtils.isEmpty(borrowBean.getAccountContents())) {
			borrow.setAccountContents(StringUtils.EMPTY);
		} else {
			borrow.setAccountContents(borrowBean.getAccountContents());
		}

		// 借款用途
		borrow.setBorrowUse(borrowBean.getBorrowUse());
		if (status == 0 && borrowMainNid.equals(borrowNid)) {
			// 还款方式 初审通过不能修改
			borrow.setBorrowStyle(borrowBean.getBorrowStyle());
			// 借款期限 初审通过不能修改
			borrow.setBorrowPeriod(Integer.valueOf(borrowBean.getBorrowPeriod()));
			// 借款利率 初审通过不能修改
			borrow.setBorrowApr(new BigDecimal(borrowBean.getBorrowApr()));
			// 产品加息收益率 初审通过不能修改
			borrow.setBorrowExtraYield(new BigDecimal(StringUtils.isNotEmpty(borrowBean.getBorrowExtraYield()) ? borrowBean.getBorrowExtraYield() : "0"));

		}

		// 项目描述
		if (StringUtils.isEmpty(borrowBean.getBorrowContents())) {
			borrow.setBorrowContents(StringUtils.EMPTY);
		} else {
			borrow.setBorrowContents(borrowBean.getBorrowContents());
		}

		if (borrowMainNid.equals(borrowNid)) {
			
			if(StringUtils.isNotEmpty(borrowBean.getAssetAttributes())) {
				//插入资产属性
				borrow.setAssetAttributes(Integer.valueOf(borrowBean.getAssetAttributes()));
			}

			// 新增协议期限字段
			borrow.setBorrowLevel(borrowBean.getBorrowLevel());
			// 标的风险测评投资等级
			borrow.setInvestLevel(borrowBean.getInvestLevel());
			// 新增协议期限字段
			if (StringUtils.isNotEmpty(borrowBean.getContractPeriod())) {
				borrow.setContractPeriod(Integer.parseInt(borrowBean.getContractPeriod()));
			}
			// ------风险缓释金添加--------
			// 资产编号
			if (StringUtils.isEmpty(borrowBean.getBorrowAssetNumber())) {
				borrow.setBorrowAssetNumber(StringUtils.EMPTY);
			} else {
				borrow.setBorrowAssetNumber(borrowBean.getBorrowAssetNumber());
			}
			// 项目来源
			if (StringUtils.isEmpty(borrowBean.getBorrowProjectSource())) {
				borrow.setBorrowProjectSource(StringUtils.EMPTY);
			} else {
				borrow.setBorrowProjectSource(borrowBean.getBorrowProjectSource());
			}
			// 起息时间
			if (StringUtils.isEmpty(borrowBean.getBorrowInterestTime())) {
				borrow.setBorrowInterestTime(StringUtils.EMPTY);
			} else {
				borrow.setBorrowInterestTime(borrowBean.getBorrowInterestTime());
			}
			// 到期时间
			if (StringUtils.isEmpty(borrowBean.getBorrowDueTime())) {
				borrow.setBorrowDueTime(StringUtils.EMPTY);
			} else {
				borrow.setBorrowDueTime(borrowBean.getBorrowDueTime());
			}
			// 保障方式
			if (StringUtils.isEmpty(borrowBean.getBorrowSafeguardWay())) {
				borrow.setBorrowSafeguardWay(StringUtils.EMPTY);
			} else {
				borrow.setBorrowSafeguardWay(borrowBean.getBorrowSafeguardWay());
			}
			// 收益说明
			if (StringUtils.isEmpty(borrowBean.getBorrowIncomeDescription())) {
				borrow.setBorrowIncomeDescription(StringUtils.EMPTY);
			} else {
				borrow.setBorrowIncomeDescription(borrowBean.getBorrowIncomeDescription());
			}
			// 发行人
			if (StringUtils.isEmpty(borrowBean.getBorrowPublisher())) {
				borrow.setBorrowPublisher(StringUtils.EMPTY);
			} else {
				borrow.setBorrowPublisher(borrowBean.getBorrowPublisher());
			}
			// ------风险缓释金添加end--------
			// ------网站改版添加--------
			// 融资用途
			if (StringUtils.isEmpty(borrowBean.getFinancePurpose())) {
				borrow.setFinancePurpose(StringUtils.EMPTY);
			} else {
				borrow.setFinancePurpose(borrowBean.getFinancePurpose());
			}
			// 月薪收入
			if (StringUtils.isEmpty(borrowBean.getMonthlyIncome())) {
				borrow.setMonthlyIncome(StringUtils.EMPTY);
			} else {
				borrow.setMonthlyIncome(borrowBean.getMonthlyIncome());
			}
			// 还款来源
			if (StringUtils.isEmpty(borrowBean.getPayment())) {
				borrow.setPayment(StringUtils.EMPTY);
			} else {
				borrow.setPayment(borrowBean.getPayment());
			}
			// 第一还款来源
			if (StringUtils.isEmpty(borrowBean.getFirstPayment())) {
				borrow.setFirstPayment(StringUtils.EMPTY);
			} else {
				borrow.setFirstPayment(borrowBean.getFirstPayment());
			}
			// 第二还款来源
			if (StringUtils.isEmpty(borrowBean.getSecondPayment())) {
				borrow.setSecondPayment(StringUtils.EMPTY);
			} else {
				borrow.setSecondPayment(borrowBean.getSecondPayment());
			}
			// 费用说明
			if (StringUtils.isEmpty(borrowBean.getCostIntrodution())) {
				borrow.setCostIntrodution(StringUtils.EMPTY);
			} else {
				borrow.setCostIntrodution(borrowBean.getCostIntrodution());
			}
			// 财务状况
			if (StringUtils.isEmpty(borrowBean.getFianceCondition())) {
				borrow.setFianceCondition(StringUtils.EMPTY);
			} else {
				borrow.setFianceCondition(borrowBean.getFianceCondition());
			}
			// ------网站改版添加--------
			
		}

		if (status == 0 && registStatus != 2 && borrowMainNid.equals(borrowNid)) {
			// 借款有效时间 初审通过不能修改
			if (StringUtils.isNotEmpty(borrowBean.getBorrowValidTime())) {
				borrow.setBorrowValidTime(Integer.valueOf(borrowBean.getBorrowValidTime()));
			} else {
				borrow.setBorrowValidTime(0);
			}
			// 银行备案时间
			if (StringUtils.isNotEmpty(borrowBean.getBankRegistDays())) {
				borrow.setBankRegistDays(Integer.valueOf(borrowBean.getBankRegistDays()));
			} else {
				borrow.setBankRegistDays(0);
			}
		}

		if (borrowMainNid.equals(borrowNid)) {
			// 最低投标金额
			if (StringUtils.isNotEmpty(borrowBean.getTenderAccountMin())) {
				borrow.setTenderAccountMin(Integer.valueOf(borrowBean.getTenderAccountMin()));
			} else {
				borrow.setTenderAccountMin(0);
			}

			// 最高投标金额
			if (StringUtils.isNotEmpty(borrowBean.getTenderAccountMax())) {
				borrow.setTenderAccountMax(Integer.valueOf(borrowBean.getTenderAccountMax()));
			} else {
				borrow.setTenderAccountMax(0);
			}
		}

		// 借款初审列表迁移 风控初审：
		if ("BORROW_FIRST".equals(borrowBean.getMoveFlag()) && borrowMainNid.equals(borrowNid)) {
//			AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
			int time = Integer.valueOf(String.valueOf(systemNowDateLong));

			// 剩余的金额
			borrow.setBorrowAccountWait(borrow.getAccount());
//		borrow.setBorrowAccountWaitAppoint(borrow.getAccount());
			// 初审人员ID
			borrow.setVerifyUserid(String.valueOf(adminId));
			// 初审人员用户名
			borrow.setVerifyUserName(adminUsername);
			// 初审备注
			borrow.setVerifyRemark(borrowBean.getVerifyRemark());
			// 初审通过时间
			borrow.setVerifyOverTime(time);
			//是否调用标的分配规则引擎
			borrow.setIsEngineUsed(Integer.valueOf(borrowBean.getIsEngineUsed()));

			//new addede by LIBIN
			//手动发标(进计划的标)需要插入 逾期利率 + 逾期免息天数
			_log.info("初审标的"+borrowBean.getBorrowNid()+"的isEngineUsed为"+borrowBean.getIsEngineUsed());
			if(StringUtils.isNotEmpty(borrowBean.getIsEngineUsed())){
				// 要进计划的标的(手动)
				if("1".equalsIgnoreCase(borrowBean.getIsEngineUsed())){
					// upd by liushouyi HJH3
					if ("10000000".equals(borrowBean.getInstCode())) {
						borrow.setLateInterestRate(this.getLateInterestRate(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod()))); //逾期利率(汇计划用)late_interest_rate
						borrow.setLateFreeDays(Integer.valueOf(this.getLateFreeDays(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod())))); // 逾期免息天数(汇计划用)late_free_days
						borrow.setIsShow(1);
					} else {
						BorrowFinmanNewCharge borrowFinmanNewCharge = this.getBorrowFinmanNewCharge(borrowBean);
						if (null != borrowFinmanNewCharge) {
							borrow.setLateInterestRate(borrowFinmanNewCharge.getLateInterest()); //逾期利率(汇计划用)late_interest_rate
							borrow.setLateFreeDays(borrowFinmanNewCharge.getLateFreeDays()); // 逾期免息天数(汇计划用)late_free_days
							borrow.setIsShow(1);
						}
					}
				}else {//如果不使用引擎(非计划)，则web端显示
					borrow.setIsShow(0);//是否展示(隐藏测试标用:0:显示,1:不显示)
					borrow.setLateInterestRate(null);
					borrow.setLateFreeDays(null);
				}
			}
		
			// 当发标状态为立即发标时插入系统时间
			if (borrowBean.getVerifyStatus() != null && StringUtils.isNotEmpty(borrowBean.getVerifyStatus())) {
				// 发标方式为”暂不发标 2“或者”定时发标 3“时，项目状态变为”待发布“
				if (Integer.valueOf(borrowBean.getVerifyStatus()) == 2 || Integer.valueOf(borrowBean.getVerifyStatus()) == 3) {
					// 定时发标
					if (Integer.valueOf(borrowBean.getVerifyStatus()) == 3) {
						borrow.setOntime(GetDate.strYYYYMMDDHHMMSS2Timestamp(borrowBean.getOntime()));// 发标时间
						// add by liuyang 20190415 wbs标的信息推送 start
						// 立即发标时,设置牛投邦状态为:1 预热中
						try {
							Borrow nowBorrow = this.getBorrow(borrow.getBorrowNid());
							// 判断标的当前状态是否是投资中的状态
							if (nowBorrow != null && StringUtils.isBlank(borrow.getPlanNid()) && borrow.getIsEngineUsed() == 0) {
								logger.info("WBS系统标的信息推送MQ:标的号:[" + borrow.getBorrowNid() + "].");
								sendWbsBorrowInfo(borrow.getBorrowNid(), "1", 0);
							}
						} catch (Exception e) {
							logger.error("WBS系统标的信息推送MQ失败,[" + e + "].");
						}
						// add by liuyang 20190415 wbs标的信息推送 end
					} else if (Integer.valueOf(borrowBean.getVerifyStatus()) == 4) {
						borrow.setOntime(0);// 发标时间
					}
	//				borrow.setBookingStatus(0);
	//				borrow.setBorrowAccountScaleAppoint(BigDecimal.ZERO);
					borrow.setStatus(1);// 状态
					borrow.setVerifyStatus(Integer.valueOf(borrowBean.getVerifyStatus()));// 初审状态
				}
				// 发标方式为”立即发标4“时，项目状态变为”出借中
				else if (Integer.valueOf(borrowBean.getVerifyStatus()) == 4) {
					// 借款到期时间
					borrow.setBorrowEndTime(String.valueOf(time + borrow.getBorrowValidTime() * 86400));
					// 是否可以进行借款
					borrow.setBorrowStatus(1);
					// 初审时间
					borrow.setVerifyTime(GetDate.getNowTime10());
					// 发标的状态
					borrow.setVerifyStatus(Integer.valueOf(borrowBean.getVerifyStatus()));
					// 状态
					borrow.setStatus(2);

					// 根据此标的是否跑引擎来操作redis: 0:未使用引擎 , 1：使用引擎
					if(borrow.getIsEngineUsed().equals(0)){
						// borrowNid，借款的borrowNid,account借款总额
						RedisUtils.set(RedisConstants.BORROW_NID+borrowNid, borrow.getAccount().toString());
					}
					if (!CustomConstants.INST_CODE_HYJF.equals(borrowBean.getInstCode())) {
						// 三方资产更新资产表状态
						HjhPlanAsset hjhPlanAssetNew = this.selectHjhPlanAssetByBorrowNid(borrowNid);
						// 受托支付，更新为待授权
						//7 出借中
						hjhPlanAssetNew.setStatus(7);
						//获取当前时间
						hjhPlanAssetNew.setUpdateTime(new Date());
						hjhPlanAssetNew.setUpdateUserId(1);
						this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetNew);
					}
				}
			}
		}

		// 担保机构 风险控制措施-机构
		if (StringUtils.isEmpty(borrowBean.getBorrowMeasuresInstit())) {
			borrow.setBorrowMeasuresInstit(StringUtils.EMPTY);
		} else {
			borrow.setBorrowMeasuresInstit(borrowBean.getBorrowMeasuresInstit());
		}

		// 抵押物信息 风险控制措施-抵押物
		if (StringUtils.isEmpty(borrowBean.getBorrowMeasuresMort())) {
			borrow.setBorrowMeasuresMort(StringUtils.EMPTY);
		} else {
			borrow.setBorrowMeasuresMort(borrowBean.getBorrowMeasuresMort());
		}

		// 本息保障 险控制措施-措施
		if (StringUtils.isEmpty(borrowBean.getBorrowMeasuresMea())) {
			borrow.setBorrowMeasuresMea(StringUtils.EMPTY);
		} else {
			borrow.setBorrowMeasuresMea(borrowBean.getBorrowMeasuresMea());
		}

		// 机构介绍
		if (StringUtils.isEmpty(borrowBean.getBorrowCompanyInstruction())) {
			borrow.setBorrowCompanyInstruction(StringUtils.EMPTY);
		} else {
			borrow.setBorrowCompanyInstruction(borrowBean.getBorrowCompanyInstruction());
		}

		// 操作流程
		if (StringUtils.isEmpty(borrowBean.getBorrowOperatingProcess())) {
			borrow.setBorrowOperatingProcess(StringUtils.EMPTY);
		} else {
			borrow.setBorrowOperatingProcess(borrowBean.getBorrowOperatingProcess());
		}

		String files = borrow.getFiles();
		// 项目资料
		borrow.setFiles(this.getUploadImage(borrowBean, files, borrow.getBorrowNid()));
		// 担保方式
//		borrow.setGuaranteeType(0);
		if (status == 0 && borrowMainNid.equals(borrowNid)) {
			// 项目类型 初审通过不能修改
			borrow.setProjectType(borrowBean.getProjectType());
		}

		if (borrowMainNid.equals(borrowNid)) {
			// 可出借平台_PC
			if (StringUtils.isEmpty(borrowBean.getCanTransactionPc())) {
				borrow.setCanTransactionPc("0");
			} else {
				borrow.setCanTransactionPc(borrowBean.getCanTransactionPc());
			}

			// 可出借平台_微网站
			if (StringUtils.isEmpty(borrowBean.getCanTransactionWei())) {
				borrow.setCanTransactionWei("0");
			} else {
				borrow.setCanTransactionWei(borrowBean.getCanTransactionWei());
			}

			// 可出借平台_IOS
			if (StringUtils.isEmpty(borrowBean.getCanTransactionIos())) {
				borrow.setCanTransactionIos("0");
			} else {
				borrow.setCanTransactionIos(borrowBean.getCanTransactionIos());
			}

			// 可出借平台_Android
			if (StringUtils.isEmpty(borrowBean.getCanTransactionAndroid())) {
				borrow.setCanTransactionAndroid("0");
			} else {
				borrow.setCanTransactionAndroid(borrowBean.getCanTransactionAndroid());
			}

			// 运营标签
			if (StringUtils.isEmpty(borrowBean.getOperationLabel())) {
				borrow.setOperationLabel("0");
			} else {
				borrow.setOperationLabel(borrowBean.getOperationLabel());
			}
		}
		// 企业还是个人
		borrow.setCompanyOrPersonal(borrowBean.getCompanyOrPersonal());

		if (status == 0 && borrowMainNid.equals(borrowNid)) {
			if (PROJECT_TYPE_HXF!=borrowBean.getProjectType()) {
                // 放款服务费
                String borrowServiceScale = this.getBorrowServiceScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.valueOf(borrowBean.getBorrowPeriod()));
                borrow.setServiceFeeRate(borrowServiceScale);
                // 还款服务费率
                borrow.setManageFeeRate(this.getBorrowManagerScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod())));
                // 收益差率
                borrow.setDifferentialRate(this.getBorrowReturnScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod())));
            } else if (PROJECT_TYPE_HXF==borrowBean.getProjectType()) {
				// 放款服务费
				borrow.setServiceFeeRate("0.00");
				JSONObject jsonObject = new JSONObject();
				jsonObject = this.getBorrowManagerScale(borrowBean.getProjectType(), borrowBean.getBorrowStyle(), Integer.valueOf(borrowBean.getBorrowPeriod()), jsonObject);
				if (StringUtils.isNotEmpty(jsonObject.getString("borrowManagerScale"))) {
					// 还款服务费率(上限)
					borrow.setManageFeeRate(jsonObject.getString("borrowManagerScale"));
				}
				if (StringUtils.isNotEmpty(jsonObject.getString("borrowManagerScaleEnd"))) {
					// 还款服务费率(下限)
					borrow.setBorrowManagerScaleEnd(jsonObject.getString("borrowManagerScaleEnd"));
				}
			}
		}

		// 汇资管的内容设置
		this.setHZGInfo(borrowBean, borrow);
		// 更新时间
		borrow.setUpdatetime(systemNowDate);

		// 定向发标平台
		borrow.setPublishInstCode(borrowBean.getPublishInstCode());
		// 备案中的标的重新打标签
		/*-------------upd by liushouyi HJH3 Start-----------------*/
		if ("BORROW_REGIST".equals(borrowBean.getIsRegistFlg())) {
			borrow.setIsEngineUsed(0);
			if(StringUtils.isNotBlank(borrowBean.getIsEngineUsed()) && "1".equals(borrowBean.getIsEngineUsed())) {
				// 页面传值不为空并勾选使用引擎的情况
				borrow.setIsEngineUsed(1);
			}

			String labelName = "--";
			HjhLabel label = getLabelId(borrow, null);
			if (label == null || label.getId() == null) {
				borrow.setLabelId(0);
				_log.info(borrow.getBorrowNid() + " 该散标没有匹配到标签");
			} else {
				// 临时存着
				borrow.setLabelId(label.getId());
				labelName = label.getLabelName();
			}
			// 三方资产的标的更新资产表的标签
			if (!"10000000".equals(borrowBean.getInstCode())) {
				try {
					HjhPlanAssetExample example = new HjhPlanAssetExample();
					example.createCriteria().andBorrowNidEqualTo(borrowNid);
					List<HjhPlanAsset> hjhPlanAssetList = this.hjhPlanAssetMapper.selectByExample(example);
					if (null != hjhPlanAssetList && hjhPlanAssetList.size() > 0) {
						HjhPlanAsset hjhPlanAsset = hjhPlanAssetList.get(0);
						hjhPlanAsset.setLabelId(borrow.getLabelId());
						hjhPlanAsset.setLabelName(labelName);
						this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAsset);
					}
				} catch (Exception e) {
					_log.info(borrow.getBorrowNid() + " 三方资产标的标签更新失败");
					logger.error(e.getMessage());
				}
			}
		}
		/*-------------upd by liushouyi HJH3 End-----------------*/
		this.borrowMapper.updateByPrimaryKeySelective(borrow);
		//应急中心二期，散标发标时，报送数据 start
		if(borrow.getVerifyStatus()==4){
			//已发标
			try {
				JSONObject params = new JSONObject();
				params.put("planNid", borrow.getBorrowNid());
                params.put("isPlan","0");
				commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.BORROW_MODIFY_TAG, UUID.randomUUID().toString(), params),
						MQConstant.HG_REPORT_DELAY_LEVEL);
			} catch (Exception e) {
				logger.error("散标发标时，应急中心上报失败！borrowNid : " + borrow.getBorrowNid() ,e);
			}
		}
		//应急中心二期，散标发标时，报送数据 end
		borrow.setId(borrow.getInfoId());
		BorrowInfoWithBLOBs record=new BorrowInfoWithBLOBs();
		BeanUtils.copyProperties(borrow,record);
		 record.setId(infoId);
		this.borrowInfoMapper.updateByPrimaryKeyWithBLOBs(record);
		// 个人信息
		this.insertBorrowManinfo(borrowNid, borrowBean, borrow);
		// 公司信息
		this.insertBorrowUsers(borrowNid, borrowBean, borrow);
		// 车辆信息
		this.insertBorrowCarinfo(borrowNid, borrowBean, borrow);
		// 房产信息
		this.insertBorrowHouses(borrowNid, borrowBean, borrow);
		// 认证信息
		this.insertBorrowCompanyAuthen(borrowNid, borrowBean, borrow);
		// 发送MQ 更改借款人机构编号
		if (wjtInstCode.equals(borrow.getPublishInstCode())){
			try {
				this.sendBorrowUserMQ(borrow.getUserId());
			}catch (Exception e){
				logger.error("发送修改标的借款人机构编号MQ失败...");
			}
		}
	}

	/**
	 * 三方推送资产获取相关费率
	 *
	 * @param borrowBean
	 * @return
	 */
	private BorrowFinmanNewCharge getBorrowFinmanNewCharge(BorrowCommonBean borrowBean){
		// 获取管理费率，服务费率，自动发标费率
		Borrow borrow = this.getBorrow(borrowBean.getBorrowNid());
		BorrowInfo borrowInfo =this.getBorrowInfoByNid(borrowBean.getBorrowNid());
		HjhAssetBorrowtype hjhAssetBorrowType = this.selectAssetBorrowType(borrowBean.getBorrowNid());
		String projectCd = hjhAssetBorrowType.getBorrowCd()+"";
		String borrowClass = this.getBorrowProjectClass(projectCd);
		String queryBorrowStyle = null;
		// 费率配置表有点尴尬，还款方式只区分了天和月
		if ("endday".equals(borrow.getBorrowStyle())) {//天标
			queryBorrowStyle  = "endday";

		}else {
			queryBorrowStyle = "month";
		}

		BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
		BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
		cra.andProjectTypeEqualTo(borrowClass);
		cra.andInstCodeEqualTo(borrowInfo.getInstCode());
		cra.andAssetTypeEqualTo(hjhAssetBorrowType.getAssetType());
		cra.andManChargeTimeTypeEqualTo(queryBorrowStyle);
		cra.andManChargeTimeEqualTo(borrow.getBorrowPeriod());
		cra.andStatusEqualTo(0);

		List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	private HjhLabel getLabelId(BorrowWithBLOBs borrow, HjhPlanAsset hjhPlanAsset) {
		HjhLabel resultLabel = null;
		HjhLabelExample example = new HjhLabelExample();
		HjhLabelExample.Criteria cra = example.createCriteria();
		cra.andDelFlagEqualTo(0);
		cra.andLabelStateEqualTo(1);
		cra.andBorrowStyleEqualTo(borrow.getBorrowStyle());
		cra.andIsCreditEqualTo(0); // 原始标
		cra.andIsLateEqualTo(0); // 是否逾期
		example.setOrderByClause(" update_time desc ");
		List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
		if (list != null && list.size() <= 0) {
			logger.info(borrow.getBorrowStyle()+" 该原始标还款方式 没有一个标签");
			return resultLabel;
		}
		// continue过滤输入了但是不匹配的标签，如果找到就是第一个
		for (HjhLabel hjhLabel : list) {
			if(hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0 && hjhLabel.getLabelTermStart()!=null
					&& hjhLabel.getLabelTermStart().intValue()>0){
				if(borrow.getBorrowPeriod() >= hjhLabel.getLabelTermStart() && borrow.getBorrowPeriod() <= hjhLabel.getLabelTermEnd()){
				}else{
					continue;
				}
			}else if ((hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0) ||
					(hjhLabel.getLabelTermStart()!=null && hjhLabel.getLabelTermStart().intValue()>0)) {
				if(borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermStart()) || borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermEnd())){
				}else{
					continue;
				}
			}else{
				continue;
			}
			// 标的实际利率
			if(hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0 &&
					hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0){
				if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())>=0 && borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())<=0){
				}else{
					continue;
				}
			}else if (hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0) {
				if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())==0 ){
				}else{
					continue;
				}

			}else if (hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0 ) {
				if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())==0){
				}else {
					continue;
				}
			}
			// 标的实际支付金额
			if(hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0 &&
					hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0){
				if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())>=0 && borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())<=0){
				}else{
					continue;
				}
			}else if (hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0) {
				if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())==0 ){
				}else{
					continue;
				}
			}else if (hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0 ) {
				if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())==0){
				}else{
					continue;
				}
			}
			// 资产来源
			if(org.apache.commons.lang.StringUtils.isNotBlank(hjhLabel.getInstCode())){
				if(hjhLabel.getInstCode().equals(borrow.getInstCode())){
				}else{
					continue;
				}
			}
			// 产品类型
			if(hjhLabel.getAssetType() != null && hjhLabel.getAssetType().intValue() >= 0){
				if(hjhLabel.getAssetType().equals(borrow.getAssetType())){
				}else{
					continue;
				}
			}
			// 项目类型
			if(hjhLabel.getProjectType() != null && hjhLabel.getProjectType().intValue() >= 0){
				if(hjhLabel.getProjectType().equals(borrow.getProjectType())){
				}else{
					continue;
				}
			}
			// 推送时间节点
			if(hjhPlanAsset != null && hjhPlanAsset.getRecieveTime() != null && hjhPlanAsset.getRecieveTime().intValue() > 0){
				Date reciveDate = GetDate.getDate(hjhPlanAsset.getRecieveTime());

				if(hjhLabel.getPushTimeStart() != null && hjhLabel.getPushTimeEnd()!=null){
					if(reciveDate.getTime() >= hjhLabel.getPushTimeStart().getTime() &&
							reciveDate.getTime() <= hjhLabel.getPushTimeEnd().getTime()){
					}else{
						continue;
					}
				}else if (hjhLabel.getPushTimeStart() != null) {
					if(reciveDate.getTime() == hjhLabel.getPushTimeStart().getTime() ){
					}else{
						continue;
					}
				}else if (hjhLabel.getPushTimeEnd()!=null) {
					if(reciveDate.getTime() == hjhLabel.getPushTimeEnd().getTime() ){
					}else{
						continue;
					}
				}
			}
			// 如果找到返回最近的一个
			return hjhLabel;
		}
		return resultLabel;
	}

	/**
	 * 汇资管的内容设置
	 * 
	 * @param borrowBean
	 * @param borrow
	 */
	private void setHZGInfo(BorrowCommonBean borrowBean, BorrowWithBLOBs borrow) {

		// 售价预估
		if (StringUtils.isEmpty(borrowBean.getDisposalPriceEstimate())) {
			borrow.setDisposalPriceEstimate(StringUtils.EMPTY);
		} else {
			borrow.setDisposalPriceEstimate(borrowBean.getDisposalPriceEstimate());
		}
		// 处置周期
		if (StringUtils.isEmpty(borrowBean.getDisposalPeriod())) {
			borrow.setDisposalPeriod(StringUtils.EMPTY);
		} else {
			borrow.setDisposalPeriod(borrowBean.getDisposalPeriod());
		}
		// 处置渠道
		if (StringUtils.isEmpty(borrowBean.getDisposalChannel())) {
			borrow.setDisposalChannel(StringUtils.EMPTY);
		} else {
			borrow.setDisposalChannel(borrowBean.getDisposalChannel());
		}
		// 处置结果预案
		if (StringUtils.isEmpty(borrowBean.getDisposalResult())) {
			borrow.setDisposalResult(StringUtils.EMPTY);
		} else {
			borrow.setDisposalResult(borrowBean.getDisposalResult());
		}
		// 备注说明
		if (StringUtils.isEmpty(borrowBean.getDisposalNote())) {
			borrow.setDisposalNote(StringUtils.EMPTY);
		} else {
			borrow.setDisposalNote(borrowBean.getDisposalNote());
		}

		// 项目名称
		if (StringUtils.isEmpty(borrowBean.getDisposalProjectName())) {
			borrow.setDisposalProjectName(StringUtils.EMPTY);
		} else {
			borrow.setDisposalProjectName(borrowBean.getDisposalProjectName());
		}
		// 项目类型
		if (StringUtils.isEmpty(borrowBean.getDisposalProjectType())) {
			borrow.setDisposalProjectType(StringUtils.EMPTY);
		} else {
			borrow.setDisposalProjectType(borrowBean.getDisposalProjectType());
		}
		// 所在地区
		if (StringUtils.isEmpty(borrowBean.getDisposalArea())) {
			borrow.setDisposalArea(StringUtils.EMPTY);
		} else {
			borrow.setDisposalArea(borrowBean.getDisposalArea());
		}
		// 预估价值
		if (StringUtils.isEmpty(borrowBean.getDisposalPredictiveValue())) {
			borrow.setDisposalPredictiveValue(StringUtils.EMPTY);
		} else {
			borrow.setDisposalPredictiveValue(borrowBean.getDisposalPredictiveValue());
		}
		// 权属类别
		if (StringUtils.isEmpty(borrowBean.getDisposalOwnershipCategory())) {
			borrow.setDisposalOwnershipCategory(StringUtils.EMPTY);
		} else {
			borrow.setDisposalOwnershipCategory(borrowBean.getDisposalOwnershipCategory());
		}
		// 资产成因
		if (StringUtils.isEmpty(borrowBean.getDisposalAssetOrigin())) {
			borrow.setDisposalAssetOrigin(StringUtils.EMPTY);
		} else {
			borrow.setDisposalAssetOrigin(borrowBean.getDisposalAssetOrigin());
		}
		// 附件信息
		if (StringUtils.isEmpty(borrowBean.getDisposalAttachmentInfo())) {
			borrow.setDisposalAttachmentInfo(StringUtils.EMPTY);
		} else {
			borrow.setDisposalAttachmentInfo(borrowBean.getDisposalAttachmentInfo());
		}
	}

	/**
	 * 车辆信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	@Override
	public int insertBorrowCarinfo(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow) {
		BorrowCarinfoExample borrowCarinfoExample = new BorrowCarinfoExample();
		BorrowCarinfoExample.Criteria borrowCarinfoCra = borrowCarinfoExample.createCriteria();
		borrowCarinfoCra.andBorrowNidEqualTo(borrowNid);
		this.borrowCarinfoMapper.deleteByExample(borrowCarinfoExample);

		if (StringUtils.equals("2", borrowBean.getTypeCar())) {
			List<BorrowCommonCarVO> borrowCommonCarList = borrowBean.getBorrowCarinfoList();
			if (borrowCommonCarList != null && borrowCommonCarList.size() > 0) {
				for (BorrowCommonCarVO borrowCommonCar : borrowCommonCarList) {
					BorrowCarinfo borrowCarinfo = new BorrowCarinfo();
					// 品牌
					if (StringUtils.isNotEmpty(borrowCommonCar.getBrand())) {
						borrowCarinfo.setBrand(borrowCommonCar.getBrand());
					} else {
						borrowCarinfo.setBrand(StringUtils.EMPTY);
					}

					// 型号
					if (StringUtils.isNotEmpty(borrowCommonCar.getModel())) {
						borrowCarinfo.setModel(borrowCommonCar.getModel());
					} else {
						borrowCarinfo.setModel(StringUtils.EMPTY);
					}

					// 车系
					if (StringUtils.isNotEmpty(borrowCommonCar.getCarseries())) {
						borrowCarinfo.setCarseries(borrowCommonCar.getCarseries());
					} else {
						borrowCarinfo.setCarseries(StringUtils.EMPTY);
					}

					// 颜色
					if (StringUtils.isNotEmpty(borrowCommonCar.getColor())) {
						borrowCarinfo.setColor(borrowCommonCar.getColor());
					} else {
						borrowCarinfo.setColor(StringUtils.EMPTY);
					}

					// 出厂年份
					if (StringUtils.isNotEmpty(borrowCommonCar.getYear())) {
						borrowCarinfo.setYear(borrowCommonCar.getYear());
					} else {
						borrowCarinfo.setYear(StringUtils.EMPTY);
					}

					// 产地
					if (StringUtils.isNotEmpty(borrowCommonCar.getPlace())) {
						borrowCarinfo.setPlace(borrowCommonCar.getPlace());
					} else {
						borrowCarinfo.setPlace(StringUtils.EMPTY);
					}

					// 购买日期
					if (StringUtils.isNotEmpty(borrowCommonCar.getBuytime())) {
						borrowCarinfo.setBuytime(Integer.valueOf(String.valueOf(GetDate.str2Timestamp(borrowCommonCar.getBuytime()).getTime() / 1000)));
					} else {
						borrowCarinfo.setBuytime(0);
					}
					// 1有保险2无保险
					if (StringUtils.isNotEmpty(borrowCommonCar.getIsSafe())) {
						borrowCarinfo.setIsSafe(Integer.valueOf(borrowCommonCar.getIsSafe()));
					} else {
						borrowCarinfo.setIsSafe(0);
					}

					// 购买价
					if (StringUtils.isNotEmpty(borrowCommonCar.getPrice())) {
						borrowCarinfo.setPrice(new BigDecimal(borrowCommonCar.getPrice()));
					} else {
						borrowCarinfo.setPrice(BigDecimal.ZERO);
					}
					// 评估价
					if (StringUtils.isNotEmpty(borrowCommonCar.getToprice())) {
						borrowCarinfo.setToprice(new BigDecimal(borrowCommonCar.getToprice()));
					} else {
						borrowCarinfo.setToprice(BigDecimal.ZERO);
					}

					// 车牌号
					if (StringUtils.isNotEmpty(borrowCommonCar.getNumber())) {
						borrowCarinfo.setNumber(borrowCommonCar.getNumber());
					} else {
						borrowCarinfo.setNumber(StringUtils.EMPTY);
					}
					// 车辆登记地
					if (StringUtils.isNotEmpty(borrowCommonCar.getRegistration())) {
						borrowCarinfo.setRegistration(borrowCommonCar.getRegistration());
					} else {
						borrowCarinfo.setRegistration(StringUtils.EMPTY);
					}
					// 车架号
					if (StringUtils.isNotEmpty(borrowCommonCar.getVin())) {
						borrowCarinfo.setVin(borrowCommonCar.getVin());
					} else {
						borrowCarinfo.setVin(StringUtils.EMPTY);
					}
					
					borrowCarinfo.setBorrowNid(borrowNid);
					borrowCarinfo.setBorrowPreNid(borrow.getBorrowPreNid());

					this.borrowCarinfoMapper.insertSelective(borrowCarinfo);
				}
			}
		}

		return 0;
	}

	/**
	 * 个人信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	@Override
	public int insertBorrowManinfo(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow) {

		BorrowManinfoExample borrowManinfoExample = new BorrowManinfoExample();
		BorrowManinfoExample.Criteria borrowManinfoCra = borrowManinfoExample.createCriteria();
		borrowManinfoCra.andBorrowNidEqualTo(borrowNid);
		this.borrowManinfoMapper.deleteByExample(borrowManinfoExample);

		if (2== borrowBean.getCompanyOrPersonal()) {

			// 个人信息
			if (StringUtils.isNotEmpty(borrowBean.getManname()) || StringUtils.isNotEmpty(borrowBean.getOld()) || StringUtils.isNotEmpty(borrowBean.getIndustry())
					|| StringUtils.isNotEmpty(borrowBean.getLocation_p()) || StringUtils.isNotEmpty(borrowBean.getLocation_c()) || StringUtils.isNotEmpty(borrowBean.getBusiness())
					|| StringUtils.isNotEmpty(borrowBean.getWtime()) || StringUtils.isNotEmpty(borrowBean.getUserCredit())) {

				BorrowManinfo borrowManinfo = new BorrowManinfo();
				borrowManinfo.setBorrowNid(borrowNid);
				borrowManinfo.setBorrowPreNid(borrow.getBorrowPreNid());
				// 姓名
				if (StringUtils.isNotEmpty(borrowBean.getManname())) {
					borrowManinfo.setName(borrowBean.getManname());
				} else {
					borrowManinfo.setName(StringUtils.EMPTY);
				}
				// 性别
				if (StringUtils.isNotEmpty(borrowBean.getSex())) {
					borrowManinfo.setSex(Integer.valueOf(borrowBean.getSex()));
				} else {
					borrowManinfo.setSex(0);
				}
				// 年龄
				if (StringUtils.isNotEmpty(borrowBean.getOld())) {
					borrowManinfo.setOld(Integer.valueOf(borrowBean.getOld()));
				} else {
					borrowManinfo.setOld(0);
				}
				// 婚姻
				if (StringUtils.isNotEmpty(borrowBean.getMerry())) {
					borrowManinfo.setMerry(Integer.valueOf(borrowBean.getMerry()));
				} else {
					borrowManinfo.setMerry(0);
				}
				// 岗位职业
				if (StringUtils.isNotEmpty(borrowBean.getPosition())) {
					borrowManinfo.setPosition(borrowBean.getPosition());
				}
				// 省
				if (StringUtils.isNotEmpty(borrowBean.getLocation_p())) {
					borrowManinfo.setPro(borrowBean.getLocation_p());
				} else {
					borrowManinfo.setPro(StringUtils.EMPTY);
				}
				// 市
				if (StringUtils.isNotEmpty(borrowBean.getLocation_c())) {
					borrowManinfo.setCity(borrowBean.getLocation_c());
				} else {
					borrowManinfo.setCity(StringUtils.EMPTY);
				}

				// 公司规模
				borrowManinfo.setSize(StringUtils.EMPTY);

				// 公司月营业额
				borrowManinfo.setBusiness(BigDecimal.ZERO);

				// 行业
				if (StringUtils.isNotEmpty(borrowBean.getIndustry())) {
					borrowManinfo.setIndustry(borrowBean.getIndustry());
				} else {
					borrowManinfo.setIndustry(StringUtils.EMPTY);
				}

				// 现单位工作时间
				if (StringUtils.isNotEmpty(borrowBean.getWtime())) {
					borrowManinfo.setWtime(borrowBean.getWtime());
				} else {
					borrowManinfo.setWtime(StringUtils.EMPTY);
				}

				// 授信额度
				if (StringUtils.isNotEmpty(borrowBean.getUserCredit())) {
					borrowManinfo.setCredit(Integer.valueOf((borrowBean.getUserCredit())));
				} else {
					borrowManinfo.setCredit(0);
				}
				//身份证号
				if(StringUtils.isNotEmpty(borrowBean.getCardNo())){
					borrowManinfo.setCardNo(borrowBean.getCardNo());
				}else{
					borrowManinfo.setCardNo(StringUtils.EMPTY);
				}
				//户籍地
				if(StringUtils.isNotEmpty(borrowBean.getDomicile())){
					borrowManinfo.setDomicile(borrowBean.getDomicile());
				}else{
					borrowManinfo.setDomicile(StringUtils.EMPTY);
				}
				//在平台逾期次数
				if(StringUtils.isNotEmpty(borrowBean.getOverdueTimes())){
					borrowManinfo.setOverdueTimes(borrowBean.getOverdueTimes().trim());
				}else{
					borrowManinfo.setOverdueTimes(StringUtils.EMPTY);
				}
				//在平台逾期金额
				if(StringUtils.isNotEmpty(borrowBean.getOverdueAmount())){
					borrowManinfo.setOverdueAmount(borrowBean.getOverdueAmount().trim());
				}else{
					borrowManinfo.setOverdueAmount(StringUtils.EMPTY);
				}
				//涉诉情况
				if(StringUtils.isNotEmpty(borrowBean.getLitigation())){
					borrowManinfo.setLitigation(borrowBean.getLitigation());
				}else{
					borrowManinfo.setLitigation(StringUtils.EMPTY);
				}
				
				/** * 信批需求(个人)新增 */
				// 个人年收入
				if(StringUtils.isNotEmpty(borrowBean.getAnnualIncome())){
					borrowManinfo.setAnnualIncome(borrowBean.getAnnualIncome());
				}else{
					borrowManinfo.setAnnualIncome("10万以内");
				}
				// 征信报告逾期情况
				if(StringUtils.isNotEmpty(borrowBean.getOverdueReport())){
					borrowManinfo.setOverdueReport(borrowBean.getOverdueReport());
				}else{
					borrowManinfo.setOverdueReport("暂无数据");
				}
				// 重大负债状况
				if(StringUtils.isNotEmpty(borrowBean.getDebtSituation())){
					borrowManinfo.setDebtSituation(borrowBean.getDebtSituation());
				}else{
					borrowManinfo.setDebtSituation("无");
				}
				// 其他平台借款情况
				if(StringUtils.isNotEmpty(borrowBean.getOtherBorrowed())){
					borrowManinfo.setOtherBorrowed(borrowBean.getOtherBorrowed());
				}else{
					borrowManinfo.setOtherBorrowed("暂无数据");
				}
				//个贷审核信息 身份证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsCard())){
					borrowManinfo.setIsCard(Integer.valueOf(borrowBean.getIsCard()));
				}else{
					borrowManinfo.setIsCard(0);
				}
				//个贷审核信息 收入状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsIncome())){
					borrowManinfo.setIsIncome(Integer.valueOf(borrowBean.getIsIncome()));
				}else{
					borrowManinfo.setIsIncome(0);
				}
				//个贷审核信息 信用状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsCredit())){
					borrowManinfo.setIsCredit(Integer.valueOf(borrowBean.getIsCredit()));
				}else{
					borrowManinfo.setIsCredit(0);
				}
				//个贷审核信息 资产状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsAsset())){
					borrowManinfo.setIsAsset(Integer.valueOf(borrowBean.getIsAsset()));
				}else{
					borrowManinfo.setIsAsset(0);
				}
				//个贷审核信息 车辆状况0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsVehicle())){
					borrowManinfo.setIsVehicle(Integer.valueOf(borrowBean.getIsVehicle()));
				}else{
					borrowManinfo.setIsVehicle(0);
				}
				//个贷审核信息 行驶证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsDrivingLicense())){
					borrowManinfo.setIsDrivingLicense(Integer.valueOf(borrowBean.getIsDrivingLicense()));
				}else{
					borrowManinfo.setIsDrivingLicense(0);
				}
				//个贷审核信息 车辆登记证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsVehicleRegistration())){
					borrowManinfo.setIsVehicleRegistration(Integer.valueOf(borrowBean.getIsVehicleRegistration()));
				}else{
					borrowManinfo.setIsVehicleRegistration(0);
				}
				//个贷审核信息 车辆登记证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsVehicleRegistration())){
					borrowManinfo.setIsVehicleRegistration(Integer.valueOf(borrowBean.getIsVehicleRegistration()));
				}else{
					borrowManinfo.setIsVehicleRegistration(0);
				}
				//个贷审核信息 婚姻状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsMerry())){
					borrowManinfo.setIsMerry(Integer.valueOf(borrowBean.getIsMerry()));
				}else{
					borrowManinfo.setIsMerry(0);
				}
				//个贷审核信息 工作状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsWork())){
					borrowManinfo.setIsWork(Integer.valueOf(borrowBean.getIsWork()));
				}else{
					borrowManinfo.setIsWork(0);
				}
				//个贷审核信息 户口本 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsAccountBook())){
					borrowManinfo.setIsAccountBook(Integer.valueOf(borrowBean.getIsAccountBook()));
				}else{
					borrowManinfo.setIsAccountBook(0);
				}
				
				/** * 信批需求(个人)新增 */
				//借款资金运用情况
				if(StringUtils.isNotEmpty(borrowBean.getIsFunds())){
					borrowManinfo.setIsFunds(borrowBean.getIsFunds());
				}else{
					borrowManinfo.setIsFunds("正常");
				}
				//借款人经营状况及财务状况
				if(StringUtils.isNotEmpty(borrowBean.getIsManaged())){
					borrowManinfo.setIsManaged(borrowBean.getIsManaged());
				}else{
					borrowManinfo.setIsManaged("正常");
				}
				//借款人还款能力变化情况
				if(StringUtils.isNotEmpty(borrowBean.getIsAbility())){
					borrowManinfo.setIsAbility(borrowBean.getIsAbility());
				}else{
					borrowManinfo.setIsAbility("正常");
				}
				//借款人逾期情况
				if(StringUtils.isNotEmpty(borrowBean.getIsOverdue())){
					borrowManinfo.setIsOverdue(borrowBean.getIsOverdue());
				}else{
					borrowManinfo.setIsOverdue("暂无");
				}
				//借款人涉诉情况
				if(StringUtils.isNotEmpty(borrowBean.getIsComplaint()) ){
					borrowManinfo.setIsComplaint(borrowBean.getIsComplaint());
				}else{
					borrowManinfo.setIsComplaint("暂无");
				}
				//借款人受行政处罚情况
				if(StringUtils.isNotEmpty(borrowBean.getIsPunished())){
					borrowManinfo.setIsPunished(borrowBean.getIsPunished());
				}else{
					borrowManinfo.setIsPunished("暂无");
				}
				borrowManinfo.setAddress(borrowBean.getAddress());
				this.borrowManinfoMapper.insertSelective(borrowManinfo);
			} else {
			    BorrowManinfo borrowManinfo = new BorrowManinfo();
			    borrowManinfo.setBorrowNid(borrowNid);
                borrowManinfo.setBorrowPreNid(borrow.getBorrowPreNid());
                //个贷审核信息 身份证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsCard())){
					borrowManinfo.setIsCard(Integer.valueOf(borrowBean.getIsCard()));
				}else{
					borrowManinfo.setIsCard(0);
				}
				//个贷审核信息 收入状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsIncome())){
					borrowManinfo.setIsIncome(Integer.valueOf(borrowBean.getIsIncome()));
				}else{
					borrowManinfo.setIsIncome(0);
				}
				//个贷审核信息 信用状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsCredit())){
					borrowManinfo.setIsCredit(Integer.valueOf(borrowBean.getIsCredit()));
				}else{
					borrowManinfo.setIsCredit(0);
				}
				//个贷审核信息 资产状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsAsset())){
					borrowManinfo.setIsAsset(Integer.valueOf(borrowBean.getIsAsset()));
				}else{
					borrowManinfo.setIsAsset(0);
				}
				//个贷审核信息 车辆状况0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsVehicle())){
					borrowManinfo.setIsVehicle(Integer.valueOf(borrowBean.getIsVehicle()));
				}else{
					borrowManinfo.setIsVehicle(0);
				}
				//个贷审核信息 行驶证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsDrivingLicense())){
					borrowManinfo.setIsDrivingLicense(Integer.valueOf(borrowBean.getIsDrivingLicense()));
				}else{
					borrowManinfo.setIsDrivingLicense(0);
				}
				//个贷审核信息 车辆登记证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsVehicleRegistration())){
					borrowManinfo.setIsVehicleRegistration(Integer.valueOf(borrowBean.getIsVehicleRegistration()));
				}else{
					borrowManinfo.setIsVehicleRegistration(0);
				}
				//个贷审核信息 车辆登记证 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsVehicleRegistration())){
					borrowManinfo.setIsVehicleRegistration(Integer.valueOf(borrowBean.getIsVehicleRegistration()));
				}else{
					borrowManinfo.setIsVehicleRegistration(0);
				}
				//个贷审核信息 婚姻状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsMerry())){
					borrowManinfo.setIsMerry(Integer.valueOf(borrowBean.getIsMerry()));
				}else{
					borrowManinfo.setIsMerry(0);
				}
				//个贷审核信息 工作状况 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsWork())){
					borrowManinfo.setIsWork(Integer.valueOf(borrowBean.getIsWork()));
				}else{
					borrowManinfo.setIsWork(0);
				}
				//个贷审核信息 户口本 0未审核 1已审核
				if(StringUtils.isNotEmpty(borrowBean.getIsAccountBook())){
					borrowManinfo.setIsAccountBook(Integer.valueOf(borrowBean.getIsAccountBook()));
				}else{
					borrowManinfo.setIsAccountBook(0);
				}

				/** * 信批需求(个人)新增 */
				//借款资金运用情况
				if(borrowBean.getIsFunds() != null ){
					borrowManinfo.setIsFunds(borrowBean.getIsFunds());
				}
				//借款人经营状况及财务状况
				if(borrowBean.getIsManaged() != null ){
					borrowManinfo.setIsManaged(borrowBean.getIsManaged());
				}
				//借款人还款能力变化情况
				if(borrowBean.getIsAbility() != null ){
					borrowManinfo.setIsAbility(borrowBean.getIsAbility());
				}
				//借款人逾期情况
				if(borrowBean.getIsOverdue() != null ){
					borrowManinfo.setIsOverdue(borrowBean.getIsOverdue());
				}
				//借款人涉诉情况
				if(borrowBean.getIsComplaint() != null ){
					borrowManinfo.setIsComplaint(borrowBean.getIsComplaint());
				}
				//借款人受行政处罚情况
				if(borrowBean.getIsPunished() != null ){
					borrowManinfo.setIsPunished(borrowBean.getIsPunished());
				}
				borrowManinfo.setAddress(borrowBean.getAddress());
			    this.borrowManinfoMapper.insertSelective(borrowManinfo);
			}
		} else {
			if (StringUtils.isNotEmpty(borrowBean.getSize())) {
				BorrowManinfo borrowManinfo = new BorrowManinfo();

				borrowManinfo.setBorrowNid(borrowNid);
				borrowManinfo.setBorrowPreNid(borrow.getBorrowPreNid());

				// 公司规模
				if (StringUtils.isNotEmpty(borrowBean.getSize())) {
					borrowManinfo.setSize(borrowBean.getSize());
				} else {
					borrowManinfo.setSize(StringUtils.EMPTY);
				}

				// 公司月营业额
				if (StringUtils.isNotEmpty(borrowBean.getBusiness())) {
					borrowManinfo.setBusiness(new BigDecimal(borrowBean.getBusiness()));
				} else {
					borrowManinfo.setBusiness(BigDecimal.ZERO);
				}
				this.borrowManinfoMapper.insertSelective(borrowManinfo);
			}
		}

		return 0;
	}

	/**
	 * 公司信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	@Override
	public int insertBorrowUsers(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow) {
		BorrowUserExample borrowUsersExample = new BorrowUserExample();
		BorrowUserExample.Criteria borrowUsersCra = borrowUsersExample.createCriteria();
		borrowUsersCra.andBorrowNidEqualTo(borrowNid);
		this.borrowUserMapper.deleteByExample(borrowUsersExample);

		if (1==borrowBean.getCompanyOrPersonal()) {
			// 公司信息
			BorrowUser borrowUsers = new BorrowUser();

			borrowUsers.setBorrowNid(borrowNid);
//			borrowUsers.setBorrowPreNid(borrow.getBorrowPreNid());

			if (StringUtils.isNotEmpty(borrowBean.getComName())) {
				borrowUsers.setUsername(borrowBean.getComName());
			} else {
				borrowUsers.setUsername(StringUtils.EMPTY);
			}

//			if (StringUtils.isNotEmpty(borrowBean.getComLocationProvince())) {
//				borrowUsers.setProvince(borrowBean.getComLocationProvince());
//			} else {
//				borrowUsers.setProvince(StringUtils.EMPTY);
//			}
//
			if (StringUtils.isNotEmpty(borrowBean.getComLocationCity())) {
				borrowUsers.setCity(borrowBean.getComLocationCity());
			} else {
				borrowUsers.setCity(StringUtils.EMPTY);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComLocationArea())) {
				borrowUsers.setArea(borrowBean.getComLocationArea());
			} else {
				borrowUsers.setArea(StringUtils.EMPTY);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComRegCaptial())) {
				borrowUsers.setRegCaptial(borrowBean.getComRegCaptial());
			} else {
				borrowUsers.setRegCaptial(StringUtils.EMPTY);
			}
			if (StringUtils.isNotEmpty(borrowBean.getCurrencyName())) {
                borrowUsers.setCurrencyName(borrowBean.getCurrencyName());
            } else {
                borrowUsers.setCurrencyName("元");
            }

			if (StringUtils.isNotEmpty(borrowBean.getComUserIndustry())) {
				borrowUsers.setIndustry(borrowBean.getComUserIndustry());
			} else {
				borrowUsers.setIndustry(StringUtils.EMPTY);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComLitigation())) {
				borrowUsers.setLitigation(borrowBean.getComLitigation());
			} else {
				borrowUsers.setLitigation(StringUtils.EMPTY);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComCreReport())) {
				borrowUsers.setCreReport(borrowBean.getComCreReport());
			} else {
				borrowUsers.setCreReport(StringUtils.EMPTY);
			}

			/** * 信批需求(企业)新增字段 */
			// 征信报告逾期情况:暂未提供；无；已处理
			if (StringUtils.isNotEmpty(borrowBean.getComOverdueReport())) {
				borrowUsers.setOverdueReport(borrowBean.getComOverdueReport());
			} else {
				borrowUsers.setOverdueReport("暂无数据");
			}
			// 重大负债状况
			if (StringUtils.isNotEmpty(borrowBean.getComDebtSituation())) {
				borrowUsers.setDebtSituation(borrowBean.getComDebtSituation());
			} else {
				borrowUsers.setDebtSituation("无");
			}
			// 其他平台借款情况
			if (StringUtils.isNotEmpty(borrowBean.getComOtherBorrowed())) {
				borrowUsers.setOtherBorrowed(borrowBean.getComOtherBorrowed());
			} else {
				borrowUsers.setOtherBorrowed("暂无数据");
			}
			
			if (StringUtils.isNotEmpty(borrowBean.getComCredit())) {
				borrowUsers.setCredit(Integer.valueOf(borrowBean.getComCredit()));
			} else {
				borrowUsers.setCredit(0);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComStaff())) {
				borrowUsers.setStaff(Integer.valueOf(borrowBean.getComStaff()));
			} else {
				borrowUsers.setStaff(0);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComOtherInfo())) {
				borrowUsers.setOtherInfo(borrowBean.getComOtherInfo());
			} else {
				borrowUsers.setOtherInfo(StringUtils.EMPTY);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComRegTime())) {
				borrowUsers.setComRegTime(borrowBean.getComRegTime());
			} else {
				borrowUsers.setComRegTime(StringUtils.EMPTY);
			}
			//统一社会信用代码
			if (StringUtils.isNotEmpty(borrowBean.getComSocialCreditCode())) {
				borrowUsers.setSocialCreditCode(borrowBean.getComSocialCreditCode());
			} else {
				borrowUsers.setSocialCreditCode(StringUtils.EMPTY);
			}
			//法人
			if (StringUtils.isNotEmpty(borrowBean.getComLegalPerson())) {
				borrowUsers.setLegalPerson(borrowBean.getComLegalPerson());
			} else {
				borrowUsers.setLegalPerson(StringUtils.EMPTY);
			}
			//注册号
			if (StringUtils.isNotEmpty(borrowBean.getComRegistCode())) {
				borrowUsers.setRegistCode(borrowBean.getComRegistCode());
			} else {
				borrowUsers.setRegistCode(StringUtils.EMPTY);
			}
			//主营业务
			if (StringUtils.isNotEmpty(borrowBean.getComMainBusiness())) {
				borrowUsers.setMainBusiness(borrowBean.getComMainBusiness());
			} else {
				borrowUsers.setMainBusiness(StringUtils.EMPTY);
			}
			//在平台逾期次数
			if (StringUtils.isNotEmpty(borrowBean.getComOverdueTimes())) {
				borrowUsers.setOverdueTimes(borrowBean.getComOverdueTimes());
			} else {
				borrowUsers.setOverdueTimes(StringUtils.EMPTY);
			}
			//在平台逾期金额
			if (StringUtils.isNotEmpty(borrowBean.getComOverdueAmount())) {
				borrowUsers.setOverdueAmount(borrowBean.getComOverdueAmount());
			} else {
				borrowUsers.setOverdueAmount(StringUtils.EMPTY);
			}
			//企贷审核信息  0未审核 1已审核
			if (StringUtils.isNotEmpty(borrowBean.getComIsCertificate())) {
				borrowUsers.setIsCertificate(Integer.valueOf(borrowBean.getComIsCertificate()));
			} else {
				borrowUsers.setIsCertificate(0);
			}

			if (StringUtils.isNotEmpty(borrowBean.getComIsOperation())) {
				borrowUsers.setIsOperation(Integer.valueOf(borrowBean.getComIsOperation()));
			} else {
				borrowUsers.setIsOperation(0);
			}
			if (StringUtils.isNotEmpty(borrowBean.getComIsFinance())) {
				borrowUsers.setIsFinance(Integer.valueOf(borrowBean.getComIsFinance()));
			} else {
				borrowUsers.setIsFinance(0);
			}
			if (StringUtils.isNotEmpty(borrowBean.getComIsEnterpriseCreidt())) {
				borrowUsers.setIsEnterpriseCreidt(Integer.valueOf(borrowBean.getComIsEnterpriseCreidt()));
			} else {
				borrowUsers.setIsEnterpriseCreidt(0);
			}
			if (StringUtils.isNotEmpty(borrowBean.getComIsLegalPerson())) {
				borrowUsers.setIsLegalPerson(Integer.valueOf(borrowBean.getComIsLegalPerson()));
			} else {
				borrowUsers.setIsLegalPerson(0);
			}
			if (StringUtils.isNotEmpty(borrowBean.getComIsAsset())) {
				borrowUsers.setIsAsset(Integer.valueOf(borrowBean.getComIsAsset()));
			} else {
				borrowUsers.setIsAsset(0);
			}
			if (StringUtils.isNotEmpty(borrowBean.getComIsPurchaseContract())) {
				borrowUsers.setIsPurchaseContract(Integer.valueOf(borrowBean.getComIsPurchaseContract()));
			} else {
				borrowUsers.setIsPurchaseContract(0);
			}
			if (StringUtils.isNotEmpty(borrowBean.getComIsSupplyContract())) {
				borrowUsers.setIsSupplyContract(borrowBean.getComIsSupplyContract());
			} else {
				borrowUsers.setIsSupplyContract("");
			}
			
			// 借款资金运用情况 0不正常,1正常
			if(StringUtils.isNotEmpty(borrowBean.getComIsFunds())){
				borrowUsers.setIsFunds(borrowBean.getComIsFunds());
			} else {
				borrowUsers.setIsFunds("正常");
			}
			// 借款人经营状况及财务状况 0不正常,1正常
			if(StringUtils.isNotEmpty(borrowBean.getComIsManaged())){
				borrowUsers.setIsManaged(borrowBean.getComIsManaged());
			}  else {
				borrowUsers.setIsManaged("正常");
			}
			// 借款人还款能力变化情况：0不正常,1正常
			if(StringUtils.isNotEmpty(borrowBean.getComIsAbility())){
				borrowUsers.setIsAbility(borrowBean.getComIsAbility());
			} else {
				borrowUsers.setIsAbility("正常");
			}
			// 借款人逾期情况：0暂无,1有
			if(StringUtils.isNotEmpty(borrowBean.getComIsOverdue())){
				borrowUsers.setIsOverdue(borrowBean.getComIsOverdue());
			} else {
				borrowUsers.setIsOverdue("暂无");
			}
			// 借款人涉诉情况：0暂无,1有
			if(StringUtils.isNotEmpty(borrowBean.getComIsComplaint())){
				borrowUsers.setIsComplaint(borrowBean.getComIsComplaint());
			} else {
				borrowUsers.setIsComplaint("暂无");
			}
			// 借款人受行政处罚情况：0暂无,1有
			if(StringUtils.isNotEmpty(borrowBean.getComIsPunished())){
				borrowUsers.setIsPunished(borrowBean.getComIsPunished());
			} else {
				borrowUsers.setIsPunished("暂无");
			}
			//20180705 add by NXL 添加企业组织机构代码和企业注册地 start
			borrowUsers.setCorporateCode(borrowBean.getCorporateCode());
			borrowUsers.setRegistrationAddress(borrowBean.getRegistrationAddress());
			//20180705 add by NXL 添加企业组织机构代码和企业注册地 end
			this.borrowUserMapper.insertSelective(borrowUsers);
		}
		return 0;
	}

	/**
	 * 房产信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	@Override
	public int insertBorrowHouses(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow) {
		if (StringUtils.equals("1", borrowBean.getTypeHouse())) {
			BorrowHousesExample borrowHousesExample = new BorrowHousesExample();
			BorrowHousesExample.Criteria borrowHousesCra = borrowHousesExample.createCriteria();
			borrowHousesCra.andBorrowNidEqualTo(borrowNid);
			this.borrowHousesMapper.deleteByExample(borrowHousesExample);
			List<BorrowHousesVO> borrowHousesList = borrowBean.getBorrowHousesList();
			if (borrowHousesList != null && borrowHousesList.size() > 0) {
				for (BorrowHousesVO borrowHouses : borrowHousesList) {
					borrowHouses.setBorrowNid(borrowNid);
					borrowHouses.setBorrowPreNid(borrow.getBorrowPreNid());
					BorrowHouses bh=new BorrowHouses();
					BeanUtils.copyProperties(borrowHouses,bh);
					this.borrowHousesMapper.insertSelective(bh);
				}
			}
		}
		return 0;
	}

	/**
	 * 认证信息
	 * 
	 * @param borrowNid
	 * @param borrowBean
	 * @param borrow
	 * @return
	 */
	@Override
	public int insertBorrowCompanyAuthen(String borrowNid, BorrowCommonBean borrowBean, BorrowWithBLOBs borrow) {
		BorrowCompanyAuthenExample borrowCompanyAuthenExample = new BorrowCompanyAuthenExample();
		BorrowCompanyAuthenExample.Criteria borrowCompanyAuthenCra = borrowCompanyAuthenExample.createCriteria();
		borrowCompanyAuthenCra.andBorrowNidEqualTo(borrowNid);
		this.borrowCompanyAuthenMapper.deleteByExample(borrowCompanyAuthenExample);
		List<BorrowCommonCompanyAuthenVO> borrowCommonCompanyAuthenList = borrowBean.getBorrowCommonCompanyAuthenList();
		if (borrowCommonCompanyAuthenList != null && borrowCommonCompanyAuthenList.size() > 0) {
			for (BorrowCommonCompanyAuthenVO borrowCommonCompanyAuthen : borrowCommonCompanyAuthenList) {
				BorrowCompanyAuthen borrowCompanyAuthen = new BorrowCompanyAuthen();
				borrowCompanyAuthen.setAuthenName(borrowCommonCompanyAuthen.getAuthenName());
				borrowCompanyAuthen.setAuthenSortKey(Integer.valueOf(borrowCommonCompanyAuthen.getAuthenSortKey()));
				borrowCompanyAuthen.setAuthenTime(borrowCommonCompanyAuthen.getAuthenTime());
				borrowCompanyAuthen.setBorrowNid(borrowNid);
				borrowCompanyAuthen.setBorrowPreNid(borrow.getBorrowPreNid());
				this.borrowCompanyAuthenMapper.insertSelective(borrowCompanyAuthen);
			}
		}
		return 0;
	}

	/**
	 * 项目类型
	 * 
	 * @return
	 * @author Administrator
	 */
	@Override
	public List<BorrowProjectType> borrowProjectTypeList(String projectTypeCd) {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		BorrowProjectTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
		if (StringUtils.isNotEmpty(projectTypeCd)) {
			cra.andBorrowProjectTypeEqualTo(projectTypeCd);
		}
		// 不查询融通宝相关
		cra.andBorrowNameNotEqualTo(CustomConstants.RTB);
		return this.borrowProjectTypeMapper.selectByExample(example);
	}

	/**
	 * 项目类型
	 * 
	 * @return
	 * @author Administrator
	 */
	@Override
	public String getBorrowProjectClass(String borrowCd) {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		BorrowProjectTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
		cra.andBorrowCdEqualTo(Integer.valueOf(borrowCd));
		// 不查询融通宝相关
		cra.andBorrowNameNotEqualTo(CustomConstants.RTB);
		List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getBorrowClass();
		}
		return "";
	}

	/**
	 * 还款方式
	 * 
	 * @param nid
	 * @return
	 * @author Administrator
	 */
	@Override
	public List<BorrowStyle> borrowStyleList(String nid) {
		BorrowStyleExample example = new BorrowStyleExample();
		BorrowStyleExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
		if (StringUtils.isNotEmpty(nid)) {
			cra.andNidEqualTo(nid);
		}
		return this.borrowStyleMapper.selectByExample(example);
	}

	/**
	 * 还款方式 关联 项目类型
	 * 
	 * @return
	 */
	@Override
	public List<BorrowProjectRepay> borrowProjectRepayList() {

//		HashMap<String, BorrowProjectRepay> map = new HashMap<String, BorrowProjectRepay>();
		BorrowProjectRepayExample example = new BorrowProjectRepayExample();
		BorrowProjectRepayExample.Criteria cra = example.createCriteria();
		cra.andDelFlagEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
		List<BorrowProjectRepay> borrowProjectRepayList = this.borrowProjectRepayMapper.selectByExample(example);
//		if (borrowProjectRepayList != null && borrowProjectRepayList.size() > 0) {
//			for (BorrowProjectRepay borrowProjectRepay : borrowProjectRepayList) {
//				if (map.containsKey(borrowProjectRepay.getRepayMethod())) {
//					BorrowProjectRepay mapRecord = map.get(borrowProjectRepay.getRepayMethod());
//					String borrowClass = borrowProjectRepay.getBorrowClass();
//				//	String optionAttr = mapRecord.getBorrowClass() + "data-" + borrowClass + "='" + borrowClass + "' ";
//					mapRecord.setBorrowClass(borrowClass);
//					map.put(borrowProjectRepay.getRepayMethod(), mapRecord);
//				} else {
//					String borrowClass = borrowProjectRepay.getBorrowClass();
//				//	String optionAttr = "data-" + borrowClass + "='" + borrowClass + "' ";
//					borrowProjectRepay.setBorrowClass(borrowClass);
//					map.put(borrowProjectRepay.getRepayMethod(), borrowProjectRepay);
//				}
//			}
//		}
//		borrowProjectRepayList = new ArrayList<BorrowProjectRepay>();
//		Iterator<Entry<String, BorrowProjectRepay>> iter = map.entrySet().iterator();
//		while (iter.hasNext()) {
//			Entry<String, BorrowProjectRepay> entry = iter.next();
//			borrowProjectRepayList.add(entry.getValue());
//		}

		return borrowProjectRepayList;
	}


	/**
	 *
	 * 获取借款信息
	 * @param borrowBean
	 * @return
	 */
	@Override
	public BorrowCommonBean getBorrow(BorrowCommonBean borrowBean) {
		// 借款信息数据获取
		BorrowWithBLOBs borrowWithBLOBs = this.getBorrowWithBLOBs(borrowBean.getBorrowNid());
		if (borrowWithBLOBs != null) {
			// 借款信息数据放置
			this.getBorrowCommonFiled(borrowBean, borrowWithBLOBs);
			if (borrowBean.getCompanyOrPersonal()==0) {
				if (StringUtils.isNotEmpty(borrowBean.getComName())) {
					borrowBean.setCompanyOrPersonal(1);
				} else if (StringUtils.isNotEmpty(borrowBean.getManname())) {
					borrowBean.setCompanyOrPersonal(2);
				}
			}
		}
		return borrowBean;

	}

	/**
	 * 借款信息数据获取
	 * 
	 * @param borrowBean
	 * @param borrowWithBLOBs
	 * @author Administrator
	 */
	@Override
	public void getBorrowCommonFiled(BorrowCommonBean borrowBean, BorrowWithBLOBs borrowWithBLOBs) {
		NumberFormat numberFormat = new DecimalFormat("####");
		borrowBean.setRemark(this.getValue(String.valueOf(borrowWithBLOBs.getRemark())));
		// 借款预编码（1411001意为2014年11月份第1单借款标，后借款标分期编号HDD141100101）
		borrowBean.setBorrowPreNid(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowPreNid())));
		// 借款状态
		borrowBean.setStatus(this.getValue(String.valueOf(borrowWithBLOBs.getStatus())));
		// 借款编码
		borrowBean.setBorrowNid(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowNid())));
		// 借款标题
		borrowBean.setName(this.getValue(borrowWithBLOBs.getName()));
		// 项目标题
		borrowBean.setProjectName(this.getValue(borrowWithBLOBs.getProjectName()));
		// 借款金额
		borrowBean.setAccount(this.getValue(numberFormat.format(borrowWithBLOBs.getAccount())));
		// 借款用户
		RUser users = this.rUserMapper.selectByPrimaryKey(borrowWithBLOBs.getUserId());
		if (users != null) {
			borrowBean.setUsername(this.getValue(users.getUsername()));
		}
		// 项目申请人
		borrowBean.setApplicant(borrowWithBLOBs.getApplicant());
		// 担保机构用户名
		borrowBean.setRepayOrgName(borrowWithBLOBs.getRepayOrgName());
		// 年利率
		borrowBean.setBorrowApr(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowApr())));
		// 借款期限
		borrowBean.setBorrowPeriod(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowPeriod())));
		// 还款方式
		borrowBean.setBorrowStyle(this.getValue(borrowWithBLOBs.getBorrowStyle()));
		// 借款用途
		borrowBean.setBorrowUse(this.getValue(borrowWithBLOBs.getBorrowUse()));
		// 担保方式
	//	borrowBean.setGuaranteeType(this.getValue(String.valueOf(borrowWithBLOBs.getGuaranteeType())));
		// 项目类型
		borrowBean.setProjectType((borrowWithBLOBs.getProjectType()));
		// 资产类型
		borrowBean.setInstCode(this.getValue(String.valueOf(borrowWithBLOBs.getInstCode())));
		// 借款方式
		borrowBean.setType(this.getValue(String.valueOf(borrowWithBLOBs.getType())));
		// 借款方式
		borrowBean.setBorrowLevel(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowLevel())));

		borrowBean.setInvestLevel(this.getValue(String.valueOf(borrowWithBLOBs.getInvestLevel())));

		if ("BORROW_FIRST".equals(borrowBean.getMoveFlag())) {
			// 立即发标 20171101修改为"借款初审内，每个标的点开默认暂不发标"--查道健
			borrowBean.setVerifyStatus("2");
		}
		//资产属性
		if(borrowWithBLOBs.getAssetAttributes()==null) {
			borrowBean.setAssetAttributes("");
		}else {
			borrowBean.setAssetAttributes(borrowWithBLOBs.getAssetAttributes().toString());
		}
		
		// 担保方式
		// 担保机构
		borrowBean.setBorrowMeasuresInstit(this.getValue(borrowWithBLOBs.getBorrowMeasuresInstit()));
		// 抵押物信息
		borrowBean.setBorrowMeasuresMort(this.getValue(borrowWithBLOBs.getBorrowMeasuresMort()));
		// 本息保障
		borrowBean.setBorrowMeasuresMea(this.getValue(borrowWithBLOBs.getBorrowMeasuresMea()));
		// 机构介绍
		borrowBean.setBorrowCompanyInstruction(this.getValue(borrowWithBLOBs.getBorrowCompanyInstruction()));
		// 操作流程
		borrowBean.setBorrowOperatingProcess(this.getValue(borrowWithBLOBs.getBorrowOperatingProcess()));
		// 财务状况
		borrowBean.setAccountContents(this.getValue(borrowWithBLOBs.getAccountContents()));
		// 项目描述
		borrowBean.setBorrowContents(this.getValue(borrowWithBLOBs.getBorrowContents()));
		// 资产编号
		borrowBean.setBorrowAssetNumber(this.getValue(borrowWithBLOBs.getBorrowAssetNumber()));
		// 新增协议期限字段
		// 协议期限
		if(borrowWithBLOBs.getContractPeriod()!=null) {
			borrowBean.setContractPeriod(String.valueOf(borrowWithBLOBs.getContractPeriod()));
		}
		// 项目来源
		borrowBean.setBorrowProjectSource(this.getValue(borrowWithBLOBs.getBorrowProjectSource()));
		// 起息时间
		borrowBean.setBorrowInterestTime(this.getValue(borrowWithBLOBs.getBorrowInterestTime()));
		// 到期时间
		borrowBean.setBorrowDueTime(this.getValue(borrowWithBLOBs.getBorrowDueTime()));
		// 保障方式
		borrowBean.setBorrowSafeguardWay(this.getValue(borrowWithBLOBs.getBorrowSafeguardWay()));
		// 收益说明
		borrowBean.setBorrowIncomeDescription(this.getValue(borrowWithBLOBs.getBorrowIncomeDescription()));
		// 发行人
		borrowBean.setBorrowPublisher(this.getValue(borrowWithBLOBs.getBorrowPublisher()));
		//融资用途
		borrowBean.setFinancePurpose(this.getValue(borrowWithBLOBs.getFinancePurpose()));
		//月薪收入
		borrowBean.setMonthlyIncome(this.getValue(borrowWithBLOBs.getMonthlyIncome()));
		//还款来源 
		borrowBean.setPayment(this.getValue(borrowWithBLOBs.getPayment()));
		//第一还款来源 
		borrowBean.setFirstPayment(this.getValue(borrowWithBLOBs.getFirstPayment()));
		//第二还款来源
		borrowBean.setSecondPayment(this.getValue(borrowWithBLOBs.getSecondPayment()));
		//费用说明
		borrowBean.setCostIntrodution(this.getValue(borrowWithBLOBs.getCostIntrodution()));
		//财务状况
		borrowBean.setFianceCondition(this.getValue(borrowWithBLOBs.getFianceCondition()));
		
		// 产品加息收益率
		borrowBean.setBorrowExtraYield(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowExtraYield())));
		// 最低投标金额
		if (borrowWithBLOBs.getTenderAccountMin() == null || borrowWithBLOBs.getTenderAccountMin() == 0) {
			borrowBean.setTenderAccountMin(StringUtils.EMPTY);
		} else {
			borrowBean.setTenderAccountMin(this.getValue(numberFormat.format(borrowWithBLOBs.getTenderAccountMin())));
		}

		// 最高投标金额
		if (borrowWithBLOBs.getTenderAccountMax() == null || borrowWithBLOBs.getTenderAccountMax() == 0) {
			borrowBean.setTenderAccountMax(StringUtils.EMPTY);
		} else {
			borrowBean.setTenderAccountMax(this.getValue(numberFormat.format(borrowWithBLOBs.getTenderAccountMax())));
		}

		// 有效时间
		if (borrowWithBLOBs.getBorrowValidTime() == null || borrowWithBLOBs.getBorrowValidTime() == 0) {
			borrowBean.setBorrowValidTime(StringUtils.EMPTY);
		} else {
			borrowBean.setBorrowValidTime(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowValidTime())));
		}
		borrowBean.setBankRegistDays(borrowWithBLOBs.getBankRegistDays().toString());
		// 放款服务费
		borrowBean.setBorrowServiceScale(this.getValue(borrowWithBLOBs.getServiceFeeRate()));
		// 还款服务费率
		borrowBean.setBorrowManagerScale(this.getValue(borrowWithBLOBs.getManageFeeRate()));
		// 还款服务费率(上限)
		borrowBean.setBorrowManagerScaleEnd(this.getValue(borrowWithBLOBs.getBorrowManagerScaleEnd()));
		// 可出借平台_PC
		borrowBean.setCanTransactionPc(this.getValue(borrowWithBLOBs.getCanTransactionPc()));
		// 可出借平台_微网站
		borrowBean.setCanTransactionWei(this.getValue(borrowWithBLOBs.getCanTransactionWei()));
		// 可出借平台_IOS
		borrowBean.setCanTransactionIos(this.getValue(borrowWithBLOBs.getCanTransactionIos()));
		// 可出借平台_Android
		borrowBean.setCanTransactionAndroid(this.getValue(borrowWithBLOBs.getCanTransactionAndroid()));
		// 运营标签
		borrowBean.setOperationLabel(this.getValue(borrowWithBLOBs.getOperationLabel()));
		// 公司个人区分
		borrowBean.setCompanyOrPersonal(borrowWithBLOBs.getCompanyOrPersonal());
		// 定向标
		borrowBean.setPublishInstCode(this.getValue(borrowWithBLOBs.getPublishInstCode()));
		// 项目资料
		if (StringUtils.isNotEmpty(borrowWithBLOBs.getFiles())) {
			List<BorrowCommonFile> borrowCommonFileList = JSONArray.parseArray(borrowWithBLOBs.getFiles(), BorrowCommonFile.class);
			if (borrowCommonFileList != null && borrowCommonFileList.size() > 0) {
				// domain URL
				String fileDomainUrl = UploadFileUtils.getDoPath(url);

				List<BorrowCommonImageVO> borrowCommonImageList = new ArrayList<BorrowCommonImageVO>();

				for (BorrowCommonFile borrowCommonFile : borrowCommonFileList) {
					List<BorrowCommonFileData> fileDataList = borrowCommonFile.getData();
					if (fileDataList != null && fileDataList.size() > 0) {
						int i = 0;
						for (BorrowCommonFileData borrowCommonFileData : fileDataList) {
							BorrowCommonImageVO borrowCommonImage = new BorrowCommonImageVO();
							borrowCommonImage.setImageName(borrowCommonFileData.getName());
							if (StringUtils.isNotEmpty(borrowCommonFileData.getFileRealName())) {
								borrowCommonImage.setImageRealName(borrowCommonFileData.getFileRealName());
							} else {
								borrowCommonImage.setImageRealName(borrowCommonFileData.getFilename());
							}
							if (StringUtils.isEmpty(borrowCommonFileData.getImageSort().trim())) {
								borrowCommonImage.setImageSort(String.valueOf(i));
							} else {
								borrowCommonImage.setImageSort(borrowCommonFileData.getImageSort().trim());
							}

							borrowCommonImage.setImagePath(borrowCommonFileData.getFileurl());
							borrowCommonImage.setImageSrc(fileDomainUrl + borrowCommonFileData.getFileurl());
							borrowCommonImageList.add(borrowCommonImage);
							i++;
						}
					}
				}

				Collections.sort(borrowCommonImageList, new Comparator<BorrowCommonImageVO>() {
					@Override
					public int compare(BorrowCommonImageVO o1, BorrowCommonImageVO o2) {
						if (o1 != null && o2 != null) {
							Integer sort1 = Integer.valueOf(o1.getImageSort().trim());
							Integer sort2 = Integer.valueOf(o2.getImageSort().trim());
							return sort1.compareTo(sort2);
						}
						return 0;
					}

				});
				borrowBean.setBorrowCommonImageList(borrowCommonImageList);
			}
		}

		// 售价预估
		borrowBean.setDisposalPriceEstimate(this.getValue(borrowWithBLOBs.getDisposalPriceEstimate()));
		// 处置周期
		borrowBean.setDisposalPeriod(this.getValue(borrowWithBLOBs.getDisposalPeriod()));
		// 处置渠道
		borrowBean.setDisposalChannel(this.getValue(borrowWithBLOBs.getDisposalChannel()));
		// 处置结果预案
		borrowBean.setDisposalResult(this.getValue(borrowWithBLOBs.getDisposalResult()));
		// 备注说明
		borrowBean.setDisposalNote(this.getValue(borrowWithBLOBs.getDisposalNote()));

		// 项目名称
		borrowBean.setDisposalProjectName(this.getValue(borrowWithBLOBs.getDisposalProjectName()));
		// 项目类型
		borrowBean.setDisposalProjectType(this.getValue(borrowWithBLOBs.getDisposalProjectType()));
		// 所在地区
		borrowBean.setDisposalArea(this.getValue(borrowWithBLOBs.getDisposalArea()));
		// 预估价值
		borrowBean.setDisposalPredictiveValue(this.getValue(borrowWithBLOBs.getDisposalPredictiveValue()));
		// 权属类别
		borrowBean.setDisposalOwnershipCategory(this.getValue(borrowWithBLOBs.getDisposalOwnershipCategory()));
		// 资产成因
		borrowBean.setDisposalAssetOrigin(this.getValue(borrowWithBLOBs.getDisposalAssetOrigin()));
		// 附件信息
		borrowBean.setDisposalAttachmentInfo(this.getValue(borrowWithBLOBs.getDisposalAttachmentInfo()));

		// 借款人信息数据获取
		this.getBorrowManinfo(borrowBean);
		// 车辆 房屋 认证信息 数据获取
		this.getBorrowCarinfo(borrowBean);
		// 用户信息数据获取
		this.getBorrowUsers(borrowBean);
	}

	/**
	 * 借款人信息数据获取
	 * 
	 * @param borrowBean
	 * @return
	 * @author Administrator
	 */

	@Override
	public void getBorrowManinfo(BorrowCommonBean borrowBean) {
		BorrowManinfoExample example = new BorrowManinfoExample();
		BorrowManinfoExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowManinfo> borrowManinfoList = this.borrowManinfoMapper.selectByExample(example);

		if (borrowManinfoList != null && borrowManinfoList.size() > 0) {
			for (BorrowManinfo record : borrowManinfoList) {
				// 借款人姓名
				if (StringUtils.isNotEmpty(record.getName())) {
					borrowBean.setManname(this.getValue(record.getName()));
				} else {
					borrowBean.setManname(StringUtils.EMPTY);
				}

				// 借款人性别
				if (record.getSex() == null || record.getSex() == 0) {
					borrowBean.setSex(StringUtils.EMPTY);
				} else {
					borrowBean.setSex(this.getValue(String.valueOf(record.getSex())));
				}
				// 借款人年龄
				if (record.getOld() == null || record.getOld() == 0) {
					borrowBean.setOld(StringUtils.EMPTY);
				} else {
					borrowBean.setOld(this.getValue(String.valueOf(record.getOld())));
				}
				// 岗位职业
				if (StringUtils.isNotEmpty(record.getPosition())) {
					borrowBean.setPosition(record.getPosition());
				}

				// 借款人婚姻状况
				if (record.getMerry() == null || record.getMerry() == 0) {
					borrowBean.setMerry(StringUtils.EMPTY);
				} else {
					borrowBean.setMerry(this.getValue(String.valueOf(record.getMerry())));
				}

				// 借款人省
				if (StringUtils.isNotEmpty(record.getPro())) {
					borrowBean.setLocation_p(this.getValue(record.getPro()));
				} else {
					borrowBean.setLocation_p(StringUtils.EMPTY);
				}

				// 借款人市
				if (StringUtils.isNotEmpty(record.getCity())) {
					borrowBean.setLocation_c(this.getValue(record.getCity()));
				} else {
					borrowBean.setLocation_c(StringUtils.EMPTY);
				}

				// 借款人行业
				if (StringUtils.isNotEmpty(record.getIndustry())) {
					borrowBean.setIndustry(this.getValue(record.getIndustry()));
				} else {
					borrowBean.setIndustry(StringUtils.EMPTY);
				}

				// 借款人公司规模
				if (StringUtils.isNotEmpty(record.getSize())) {
					borrowBean.setSize(this.getValue(record.getSize()));
				} else {
					borrowBean.setSize(StringUtils.EMPTY);
				}

				// 借款人公司月营业额
				if (record.getBusiness() == null || BigDecimal.ZERO.equals(record.getBusiness())) {
					borrowBean.setBusiness(StringUtils.EMPTY);
				} else {
					borrowBean.setBusiness(this.getValue(String.valueOf(record.getBusiness())));
				}

				// 现单位工作时间
				if (StringUtils.isNotEmpty(record.getWtime())) {
					borrowBean.setWtime(this.getValue(record.getWtime()));
				} else {
					borrowBean.setWtime(StringUtils.EMPTY);
				}

				// 授信额度
				if (record.getCredit() != null && !Integer.valueOf(0).equals(record.getCredit())) {
					borrowBean.setUserCredit(String.valueOf((record.getCredit())));
				} else {
					borrowBean.setUserCredit(StringUtils.EMPTY);
				}
				// 身份证号
				if (StringUtils.isNotEmpty(record.getCardNo())) {
					borrowBean.setCardNo(record.getCardNo());
				} else {
					borrowBean.setCardNo(StringUtils.EMPTY);
				}
				// 户籍地
				if (StringUtils.isNotEmpty(record.getDomicile())) {
					borrowBean.setDomicile(record.getDomicile());
				} else {
					borrowBean.setDomicile(StringUtils.EMPTY);
				}
				// 在平台逾期次数
				if (StringUtils.isNotEmpty(record.getOverdueTimes())) {
					borrowBean.setOverdueTimes(record.getOverdueTimes());
				} else {
					borrowBean.setOverdueTimes(StringUtils.EMPTY);
				}
				// 在平台逾期金额
				if (StringUtils.isNotEmpty(record.getOverdueAmount())) {
					borrowBean.setOverdueAmount(record.getOverdueAmount());
				} else {
					borrowBean.setOverdueAmount(StringUtils.EMPTY);
				}
				// 涉诉情况
				if (StringUtils.isNotEmpty(record.getLitigation())) {
					borrowBean.setLitigation(record.getLitigation());
				} else {
					borrowBean.setLitigation(StringUtils.EMPTY);
				}
				// 个贷审核信息 身份证 0未审核 1已审核
				if (record.getIsCard() != null) {
					borrowBean.setIsCard(String.valueOf(record.getIsCard()));
				} else {
					borrowBean.setIsCard(StringUtils.EMPTY);
				}
				// 个贷审核信息 收入状况 0未审核 1已审核
				if (record.getIsIncome() != null) {
					borrowBean.setIsIncome(String.valueOf(record.getIsIncome()));
				} else {
					borrowBean.setIsIncome(StringUtils.EMPTY);
				}
				// 个贷审核信息 信用状况  0未审核 1已审核
				if (record.getIsCredit() != null) {
					borrowBean.setIsCredit(String.valueOf(record.getIsCredit()));
				} else {
					borrowBean.setIsCredit(StringUtils.EMPTY);
				}
				// 个贷审核信息 资产状况  0未审核 1已审核
				if (record.getIsAsset() != null) {
					borrowBean.setIsAsset(String.valueOf(record.getIsAsset()));
				} else {
					borrowBean.setIsAsset(StringUtils.EMPTY);
				}
				// 个贷审核信息 车辆状况 0未审核 1已审核
				if (record.getIsVehicle() != null) {
					borrowBean.setIsVehicle(String.valueOf(record.getIsVehicle()));
				} else {
					borrowBean.setIsVehicle(StringUtils.EMPTY);
				}
				// 个贷审核信息 行驶证 0未审核 1已审核
				if (record.getIsDrivingLicense() != null) {
					borrowBean.setIsDrivingLicense(String.valueOf(record.getIsDrivingLicense()));
				} else {
					borrowBean.setIsDrivingLicense(StringUtils.EMPTY);
				}
				// 个贷审核信息 车辆登记证 0未审核 1已审核
				if (record.getIsVehicleRegistration() != null) {
					borrowBean.setIsVehicleRegistration(String.valueOf(record.getIsVehicleRegistration()));
				} else {
					borrowBean.setIsVehicleRegistration(StringUtils.EMPTY);
				}
				//	个贷审核信息 婚姻状况 0未审核 1已审核
				if (record.getIsMerry() != null) {
					borrowBean.setIsMerry(String.valueOf(record.getIsMerry()));
				} else {
					borrowBean.setIsMerry(StringUtils.EMPTY);
				}
				//	个贷审核信息 工作状况 0未审核 1已审核
				if (record.getIsWork() != null) {
					borrowBean.setIsWork(String.valueOf(record.getIsWork()));
				} else {
					borrowBean.setIsWork(StringUtils.EMPTY);
				}
				//	个贷审核信息 户口本 0未审核 1已审核
				if (record.getIsAccountBook() != null) {
					borrowBean.setIsAccountBook(String.valueOf(record.getIsAccountBook()));
				} else {
					borrowBean.setIsAccountBook(StringUtils.EMPTY);
				}
				/** 信批需求新增(个人) start */
				// 个人年收入:10万以内；10万以上
				if (StringUtils.isNotEmpty(record.getAnnualIncome())) {
					borrowBean.setAnnualIncome(record.getAnnualIncome());
				} else {
					borrowBean.setAnnualIncome(StringUtils.EMPTY);
				}
				// 征信报告逾期情况:暂未提供；无；已处理
				if (StringUtils.isNotEmpty(record.getOverdueReport())) {
					borrowBean.setOverdueReport(record.getOverdueReport());
				} else {
					borrowBean.setOverdueReport(StringUtils.EMPTY);
				}
				// 重大负债状况:无
				if (StringUtils.isNotEmpty(record.getDebtSituation())) {
					borrowBean.setDebtSituation(record.getDebtSituation());
				} else {
					borrowBean.setDebtSituation(StringUtils.EMPTY);
				}
				// 其他平台借款情况:无
				if (StringUtils.isNotEmpty(record.getOtherBorrowed())) {
					borrowBean.setOtherBorrowed(record.getOtherBorrowed());
				} else {
					borrowBean.setOtherBorrowed(StringUtils.EMPTY);
				}
				// 借款资金运用情况：0不正常,1正常
				if (StringUtils.isNotEmpty(record.getIsFunds())) {
					borrowBean.setIsFunds(record.getIsFunds());
				} else {
					borrowBean.setIsFunds(StringUtils.EMPTY);
				}
				// 借款人经营状况及财务状况：0不正常,1正常
				if (StringUtils.isNotEmpty(record.getIsManaged())) {
					borrowBean.setIsManaged(record.getIsManaged());
				} else {
					borrowBean.setIsManaged(StringUtils.EMPTY);
				}
				// 借款人还款能力变化情况：0不正常,1正常
				if (StringUtils.isNotEmpty(record.getIsAbility())) {
					borrowBean.setIsAbility(record.getIsAbility());
				} else {
					borrowBean.setIsAbility(StringUtils.EMPTY);
				}
				// 借款人逾期情况：0暂无,1有
				if (StringUtils.isNotEmpty(record.getIsOverdue())) {
					borrowBean.setIsOverdue(record.getIsOverdue());
				} else {
					borrowBean.setIsOverdue(StringUtils.EMPTY);
				}
				// 借款人涉诉情况：0暂无,1有
				if (StringUtils.isNotEmpty(record.getIsComplaint())) {
					borrowBean.setIsComplaint(record.getIsComplaint());
				} else {
					borrowBean.setIsComplaint(StringUtils.EMPTY);
				}
				// 借款人受行政处罚情况：0暂无,1有
				if (StringUtils.isNotEmpty(record.getIsPunished())) {
					borrowBean.setIsPunished(record.getIsPunished());
				} else {
					borrowBean.setIsPunished(StringUtils.EMPTY);
				}
				/** 信批需求新增(个人) end */
				if (StringUtils.isNotEmpty(record.getAddress())) {
					borrowBean.setAddress(this.getValue(record.getAddress()));
				} else {
					borrowBean.setAddress(StringUtils.EMPTY);
				}
			}
		}
	}

	/**
	 * 车辆信息数据获取
	 * 
	 * @param borrowBean
	 * @return
	 * @author Administrator
	 */

	@Override
	public void getBorrowCarinfo(BorrowCommonBean borrowBean) {

		NumberFormat numberFormat = new DecimalFormat("####");

		BorrowCarinfoExample example = new BorrowCarinfoExample();
		BorrowCarinfoExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowCarinfo> borrowCarinfoList = this.borrowCarinfoMapper.selectByExample(example);

		List<BorrowCommonCarVO> borrowCarList = new ArrayList<BorrowCommonCarVO>();
		if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
			for (BorrowCarinfo record : borrowCarinfoList) {
				BorrowCommonCarVO borrowCar = new BorrowCommonCarVO();
				// 车辆信息 品牌
				borrowCar.setBrand(this.getValue(record.getBrand()));
				// 车辆信息 型号
				borrowCar.setModel(this.getValue(record.getModel()));
				// 车辆信息 车系
				borrowCar.setCarseries(this.getValue(record.getCarseries()));
				// 车辆信息 颜色
				borrowCar.setColor(this.getValue(record.getColor()));
				// 车辆信息 出厂年份
				borrowCar.setYear(this.getValue(record.getYear()));
				// 车辆信息 产地
				borrowCar.setPlace(this.getValue(record.getPlace()));
				// 车辆信息 购买日期
				if (record.getBuytime() != null && record.getBuytime() != 0) {
					borrowCar.setBuytime(GetDate.formatDate(Long.valueOf(record.getBuytime()) * 1000));
				} else {
					borrowCar.setBuytime(StringUtils.EMPTY);
				}
				// 车辆信息 1有保险2无保险
				if (record.getIsSafe() != null && record.getIsSafe() != 0) {
					borrowCar.setIsSafe(this.getValue(String.valueOf(record.getIsSafe())));
				} else {
					borrowCar.setIsSafe(StringUtils.EMPTY);
				}
				// 车辆信息 购买价
				if (record.getPrice() != null) {
					borrowCar.setPrice(this.getValue(numberFormat.format(record.getPrice())));
				} else {
					borrowCar.setPrice(StringUtils.EMPTY);
				}

				// 车辆信息 评估价
				if (record.getToprice() != null) {
					borrowCar.setToprice(this.getValue(numberFormat.format(record.getToprice())));
				} else {
					borrowCar.setToprice(StringUtils.EMPTY);
				}
				
				// 车辆信息 车牌号
				if (record.getNumber() != null) {
					borrowCar.setNumber(this.getValue(record.getNumber()));
				} else {
					borrowCar.setNumber(StringUtils.EMPTY);
				}
				// 车辆信息 车辆登记地
				if (record.getRegistration() != null) {
					borrowCar.setRegistration(this.getValue(record.getRegistration()));
				} else {
					borrowCar.setRegistration(StringUtils.EMPTY);
				}
				// 车辆信息 车架号
				if (record.getVin() != null) {
					borrowCar.setVin(this.getValue(record.getVin()));
				} else {
					borrowCar.setVin(StringUtils.EMPTY);
				}

				borrowCarList.add(borrowCar);
			}
		}

		borrowBean.setBorrowCarinfoList(borrowCarList);

		BorrowHousesExample borrowHousesExample = new BorrowHousesExample();
		BorrowHousesExample.Criteria borrowHousesCra = borrowHousesExample.createCriteria();
		borrowHousesCra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowHouses> borrowHousesList = this.borrowHousesMapper.selectByExample(borrowHousesExample);
		if (borrowHousesList != null && borrowHousesList.size() > 0) {
			borrowBean.setBorrowHousesList(CommonUtils.convertBeanList(borrowHousesList,BorrowHousesVO.class));
		}

		BorrowCompanyAuthenExample borrowCompanyAuthenExample = new BorrowCompanyAuthenExample();
		BorrowCompanyAuthenExample.Criteria borrowCompanyAuthenCra = borrowCompanyAuthenExample.createCriteria();
		borrowCompanyAuthenCra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		borrowCompanyAuthenExample.setOrderByClause(" authen_sort_key ASC ");
		List<BorrowCompanyAuthen> borrowCompanyAuthenList = this.borrowCompanyAuthenMapper.selectByExample(borrowCompanyAuthenExample);
		List<BorrowCommonCompanyAuthenVO> borrowCommonCompanyAuthenList = new ArrayList<BorrowCommonCompanyAuthenVO>();
		if (borrowCompanyAuthenList != null && borrowCompanyAuthenList.size() > 0) {
			borrowBean.setBorrowCompanyAuthenList(CommonUtils.convertBeanList(borrowCompanyAuthenList,BorrowCompanyAuthenVO.class));
			for (BorrowCompanyAuthen borrowCompanyAuthen : borrowCompanyAuthenList) {
				BorrowCommonCompanyAuthenVO borrowCommonCompanyAuthen = new BorrowCommonCompanyAuthenVO();
				borrowCommonCompanyAuthen.setAuthenName(this.getValue(borrowCompanyAuthen.getAuthenName()));
				borrowCommonCompanyAuthen.setAuthenTime(this.getValue(borrowCompanyAuthen.getAuthenTime()));
				borrowCommonCompanyAuthen.setAuthenSortKey(this.getValue(String.valueOf(borrowCompanyAuthen.getAuthenSortKey())));
				borrowCommonCompanyAuthenList.add(borrowCommonCompanyAuthen);
				borrowBean.setBorrowCommonCompanyAuthenList(borrowCommonCompanyAuthenList);
			}
		}
	}

	/**
	 * 用户信息数据获取
	 * 
	 * @param borrowBean
	 * @return
	 * @author Administrator
	 */

	@Override
	public void getBorrowUsers(BorrowCommonBean borrowBean) {
		BorrowUserExample example = new BorrowUserExample();
		BorrowUserExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowUser> borrowUsersList = this.borrowUserMapper.selectByExampleWithBLOBs(example);

		if (borrowUsersList != null && borrowUsersList.size() > 0) {
			for (BorrowUser record : borrowUsersList) {
				// 用户信息 用户名称
				borrowBean.setComName(this.getValue(record.getUsername()));
				// 用户信息 所在地区 省
			//	borrowBean.setComLocationProvince(this.getValue(record.getProvince()));
				// 用户信息 所在地区 市
				borrowBean.setComLocationCity(this.getValue(record.getCity()));
				// 用户信息 所在地区 区
				borrowBean.setComLocationArea(this.getValue(record.getArea()));
				// 用户信息 注册资本
				borrowBean.setComRegCaptial(this.getValue(record.getRegCaptial()));
				// 用户信息 注册资本--货币类型
                borrowBean.setCurrencyName(this.getValue(record.getCurrencyName()));
				// 用户信息 所属行业
				borrowBean.setComUserIndustry(this.getValue(record.getIndustry()));
				// 用户信息 涉诉情况
				borrowBean.setComLitigation(this.getValue(record.getLitigation()));
				// 用户信息 征信记录
				borrowBean.setComCreReport(this.getValue(record.getCreReport()));
				// 用户信息 授信额度
				if (record.getCredit() != null && !Integer.valueOf(0).equals(record.getCredit())) {
					borrowBean.setComCredit(this.getValue(String.valueOf(record.getCredit())));
				} else {
					borrowBean.setComCredit(StringUtils.EMPTY);
				}
				// 用户信息 员工人数
				if (record.getStaff() != null && !Integer.valueOf(0).equals(record.getStaff())) {
					borrowBean.setComStaff(this.getValue(String.valueOf(record.getStaff())));
				} else {
					borrowBean.setComStaff(StringUtils.EMPTY);
				}
				// 用户信息 企业注册时间
				borrowBean.setComRegTime(this.getValue(record.getComRegTime()));
				// 用户信息 其他资料
				borrowBean.setComOtherInfo(this.getValue(record.getOtherInfo()));
				// 统一社会信用代码
				borrowBean.setComSocialCreditCode(this.getValue(record.getSocialCreditCode()));
				//注册号
				borrowBean.setComRegistCode(this.getValue(record.getRegistCode()));
				//法人
				borrowBean.setComLegalPerson(this.getValue(record.getLegalPerson()));
				//主营业务
				borrowBean.setComMainBusiness(this.getValue(record.getMainBusiness()));
				//在平台逾期次数
				borrowBean.setComOverdueTimes(this.getValue(record.getOverdueTimes()));
				//在平台逾期金额
				borrowBean.setComOverdueAmount(this.getValue(record.getOverdueAmount()));
				//企贷审核信息 企业证件 0未审核 1已审核
				if(record.getIsCertificate() != null){
					borrowBean.setComIsCertificate(String.valueOf(record.getIsCertificate()));
				}else{
					borrowBean.setComIsCertificate(StringUtils.EMPTY);
				}
				//企贷审核信息 经营状况 0未审核 1已审核
				if(record.getIsOperation() != null){
					borrowBean.setComIsOperation(String.valueOf(record.getIsOperation()));
				}else{
					borrowBean.setComIsOperation(StringUtils.EMPTY);
				}
				//企贷审核信息 财务状况 0未审核 1已审核
				if(record.getIsFinance() != null){
					borrowBean.setComIsFinance(String.valueOf(record.getIsFinance()));
				}else{
					borrowBean.setComIsFinance(StringUtils.EMPTY);
				}
				//企贷审核信息 企业信用 0未审核 1已审核
				if(record.getIsEnterpriseCreidt() != null){
					borrowBean.setComIsEnterpriseCreidt(String.valueOf(record.getIsEnterpriseCreidt()));
				}else{
					borrowBean.setComIsEnterpriseCreidt(StringUtils.EMPTY);
				}
				//企贷审核信息 法人信息 0未审核 1已审核
				if(record.getIsLegalPerson() != null){
					borrowBean.setComIsLegalPerson(String.valueOf(record.getIsLegalPerson()));
				}else{
					borrowBean.setComIsLegalPerson(StringUtils.EMPTY);
				}
				//企贷审核信息 法人信息 0未审核 1已审核
				if(record.getIsLegalPerson() != null){
					borrowBean.setComIsLegalPerson(String.valueOf(record.getIsLegalPerson()));
				}else{
					borrowBean.setComIsLegalPerson(StringUtils.EMPTY);
				}
				//企贷审核信息 资产状况 0未审核 1已审核
				if(record.getIsAsset() != null){
					borrowBean.setComIsAsset(String.valueOf(record.getIsAsset()));
				}else{
					borrowBean.setComIsAsset(StringUtils.EMPTY);
				}
				//企贷审核信息 购销合同 0未审核 1已审核
				if(record.getIsPurchaseContract() != null){
					borrowBean.setComIsPurchaseContract(String.valueOf(record.getIsPurchaseContract()));
				}else{
					borrowBean.setComIsPurchaseContract(StringUtils.EMPTY);
				}
				//企贷审核信息 供销合同 0未审核 1已审核
				if(record.getIsSupplyContract() != null){
					borrowBean.setComIsSupplyContract(String.valueOf(record.getIsSupplyContract()));
				}else{
					borrowBean.setComIsSupplyContract(StringUtils.EMPTY);
				}
				/** 信批需求新增(企业) start */
				// 征信报告逾期情况:暂未提供；无；已处理
				if(record.getOverdueReport() != null){
					borrowBean.setComOverdueReport(record.getOverdueReport());
				}else{
					borrowBean.setComOverdueReport(StringUtils.EMPTY);
				}
				// 重大负债状况:无
				if(record.getDebtSituation() != null){
					borrowBean.setComDebtSituation(record.getDebtSituation());
				}else{
					borrowBean.setComDebtSituation(StringUtils.EMPTY);
				}
				// 其他平台借款情况:无
				if(record.getOtherBorrowed() != null){
					borrowBean.setComOtherBorrowed(record.getOtherBorrowed());
				}else{
					borrowBean.setComOtherBorrowed(StringUtils.EMPTY);
				}
				// 借款资金运用情况：0不正常,1正常
				if(record.getIsFunds() != null){
					borrowBean.setComIsFunds(record.getIsFunds());
				}else{
					borrowBean.setComIsFunds(StringUtils.EMPTY);
				}
				// 借款人经营状况及财务状况：0不正常,1正常
				if(record.getIsManaged() != null){
					borrowBean.setComIsManaged(record.getIsManaged());
				}else{
					borrowBean.setComIsManaged(StringUtils.EMPTY);
				}
				// 借款人还款能力变化情况：0不正常,1正常
				if(record.getIsAbility() != null){
					borrowBean.setComIsAbility(record.getIsAbility());
				}else{
					borrowBean.setComIsAbility(StringUtils.EMPTY);
				}
				// 借款人逾期情况：0暂无,1有
				if(record.getIsOverdue() != null){
					borrowBean.setComIsOverdue(record.getIsOverdue());
				}else{
					borrowBean.setComIsOverdue(StringUtils.EMPTY);
				}
				// 借款人涉诉情况：0暂无,1有
				if(record.getIsComplaint() != null){
					borrowBean.setComIsComplaint(record.getIsComplaint());
				}else{
					borrowBean.setComIsComplaint(StringUtils.EMPTY);
				}
				// 借款人受行政处罚情况：0暂无,1有
				if(record.getIsPunished() != null){
					borrowBean.setComIsPunished(record.getIsPunished());
				}else{
					borrowBean.setComIsPunished(StringUtils.EMPTY);
				}
				/** 信批需求新增(企业) end */
				/** add by nxl 添加企业注册地址和企业组织机构代码 Start */
				borrowBean.setCorporateCode(this.getValue(record.getCorporateCode()));
				borrowBean.setRegistrationAddress(this.getValue(record.getRegistrationAddress()));
				/** add by nxl 添加企业注册地址和企业组织机构代码 end */
			}
		}
	}

	private String getValue(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return value;
		}
		return "";
	}

	/**
	 * 借款用户必须是已开户的用户名
	 * 
	 * @param userId
	 * @return
	 * @author Administrator
	 */
	@Override
	public int isExistsUser(String userId) {
		if (StringUtils.isNotEmpty(userId)) {

			 RUser user = this.getRUser(userId);
			 
			if (user == null ) {
				// 借款人用户名不存在。
				return 1;
			}

			
			 Account openAccount = this.getAccount(user.getUserId());
			if (Validator.isNull(openAccount)) {
				// 借款人用户名必须已在银行开户
				return 2;
			}
			if(openAccount.getAccountId().isEmpty()) {
				return 2;
			}
			if(user.getRoleId()!=2) {
				return 4;
			}
//
//			if (users.getStatus() != 0) {
//				// 借款人用户名已经被禁用
//				return 3;
//			}

//			// 1 ：出借人 2：借款人
//			UsersInfoExample usersInfoExample = new UsersInfoExample();
//			UsersInfoExample.Criteria usersInfoCra = usersInfoExample.createCriteria();
//			usersInfoCra.andUserIdEqualTo(users.getUserId());
//			usersInfoCra.andRoleIdEqualTo(2);
//			List<UsersInfo> userInfoList = this.usersInfoMapper.selectByExample(usersInfoExample);
//
//			if (userInfoList == null || userInfoList.size() == 0) {
//				// 借款人用户名必须是借款人账户
//				return 4;
//			}
			return 0;
		}
		return 1;
	}

//	/**
//	 * 验证发标金额是否合法
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public String isAccountLegal(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//		message = message.replace("{label}", "借款人用户名");
//
//		String param = request.getParameter("param");
//		if (StringUtils.isEmpty(param)) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		// 发标金额
//		BigDecimal account = new BigDecimal(param);
//		BigDecimal increaseMoney = new BigDecimal("100");
//		if (account.compareTo(increaseMoney) < 0) {
//			// 发标金额应大于递增金额
//			message = ValidatorFieldCheckUtil.getErrorMessage("borrow.acount.legal", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		// 不是100的整数倍
//		if (account.divideAndRemainder(increaseMoney)[1].compareTo(BigDecimal.ZERO) != 0) {
//			// 发标金额应大于递增金额
//			message = ValidatorFieldCheckUtil.getErrorMessage("borrow.acount.multiple", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//		return ret.toJSONString();
//	}

//	/**
//	 * 验证发标金额是否合法
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public String isBorrowPeriodCheck(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//		message = message.replace("{label}", "借款期限");
//
//		String param = request.getParameter("param");
//		if (StringUtils.isEmpty(param)) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		if (param.equals("0")) {
//			// 发标金额应大于递增金额
//			message = ValidatorFieldCheckUtil.getErrorMessage("borrow.borrowPeriod.legal", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//		return ret.toJSONString();
//	}

//	/**
//	 * 用户是否存在
//	 * 
//	 * @param username
//	 * @return
//	 */
//	@Override
//	public String isExistsUser(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//		message = message.replace("{label}", "借款金额");
//
//		String param = request.getParameter("param");
//		if (StringUtils.isEmpty(param)) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//
//		int usersFlag = this.isExistsUser(param);
//		if (usersFlag == 1) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.exists", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 2) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.account");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 3) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.use");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 4) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.role");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//
//		return ret.toJSONString();
//	}

	/**
	 * 项目申请人是否存在
	 * @param request
	 * @return
	 */
//	@Override
//	public String isExistsApplicant(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//		message = message.replace("{label}", "项目申请人");
//
//		String param = request.getParameter("param");// ajaxurl中定义的参数名，固定。
//		if (StringUtils.isEmpty(param)) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//
//		int applicantFlag = this.isExistsApplicant(param);
//		if (applicantFlag == 0) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, "项目申请人不存在！");
//			return ret.toString();
//		}
//
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//
//		return ret.toJSONString();
//	}

//	/**
//	 * 验证项目申请人是否存在
//	 */
//	public int isExistsApplicant(String applicant) {
//		if (StringUtils.isNotEmpty(applicant)) {
//			ConfigApplicantExample example = new ConfigApplicantExample();
//			ConfigApplicantExample.Criteria cra = example.createCriteria();
//			cra.andApplicantEqualTo(applicant);
//			List<ConfigApplicant> applicants = this.configApplicantMapper.selectByExample(example);
//
//			if (applicants == null || applicants.size() == 0) {
//				// 项目申请人不存在。
//				return 0;
//			}
//			return 1;
//		}
//		return 0;
//	}

	/**
	 * 担保机构是否存在
	 * @param request
	 * @return
	 */
//	@Override
//	public String isRepayOrgUser(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//		message = message.replace("{label}", "担保机构用户名");
//		String param = request.getParameter("param");// ajaxurl中定义的参数名，固定。
//		if (StringUtils.isEmpty(param)) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//
//		UsersExample example = new UsersExample();
//		UsersExample.Criteria cri = example.createCriteria();
//		cri.andUsernameEqualTo(param);
//		cri.andBankOpenAccountEqualTo(1);// 汇付已开户
//		List<Users> ulist = this.usersMapper.selectByExample(example);
//		// 如果用户名不存在，返回错误信息。
//		if (ulist == null || ulist.size() == 0) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, "请填写用户角色为担保机构的已开户用户！");
//			return ret.toString();
//		}
//		Integer userId = ulist.get(0).getUserId();
//		UsersInfoExample uexample = new UsersInfoExample();
//		UsersInfoExample.Criteria ucri = uexample.createCriteria();
//		ucri.andUserIdEqualTo(userId);
//		ucri.andRoleIdEqualTo(3); // 担保机构
//		List<UsersInfo> userinfos = this.usersInfoMapper.selectByExample(uexample);
//		// 如果用户名不存在，返回错误信息。
//		if (userinfos == null || userinfos.size() == 0) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, "请填写用户角色为担保机构的已开户用户！！");
//			return ret.toString();
//		}
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//		return ret.toJSONString();
//	}

	/**
	 * 借款预编码是否存在
	 * @param request
	 * @return
	 */
//	@Override
//	public String isExistsBorrowPreNidRecord(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("repeat", "");
//		message = message.replace("{label}", "借款预编码");
//		String param = request.getParameter("param");
//		boolean borrowPreNidFlag = this.isExistsRecord(StringUtils.EMPTY, param);
//		if (borrowPreNidFlag) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//		return ret.toJSONString();
//	}

	/**
	 * 借款预编码
	 * 
	 * @return
	 */
	@Override
	public String getBorrowPreNid() {
		String yyyymm = GetDate.getServerDateTime(13, new Date());
		String mmdd = yyyymm.substring(2);
		String borrowPreNid = this.borrowCustomizeMapper.getBorrowPreNid(mmdd);
		if (StringUtils.isEmpty(borrowPreNid)) {
			RedisUtils.set("borrowPreNid", mmdd + "0000");
			return mmdd + "0001";
		}
		if (borrowPreNid.length() == 7) {
			return mmdd + "0001";
		}
		return String.valueOf(Long.valueOf(borrowPreNid) + 1);
	}

	/**
	 * 获取借款预编号是否存在
	 * @param borrowPreNid
	 * @return
	 */
	@Override
	public int isExistsBorrowPreNid(String borrowPreNid) {
		BorrowInfoExample borrowExample = new BorrowInfoExample();
		BorrowInfoExample.Criteria borrowCra = borrowExample.createCriteria();
		borrowCra.andBorrowPreNidEqualTo(borrowPreNid);
		List<BorrowInfo> borrowList = this.borrowInfoMapper.selectByExample(borrowExample);

		if (borrowList != null && borrowList.size() > 0) {
			return 1;
		}

		return 0;
	}

	/**
	 * 根据借款标号查询标的详情
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowWithBLOBs getBorrowWithBLOBs(String borrowNid) {
		if (StringUtils.isEmpty(borrowNid)) {
			return null;
		}
		BorrowWithBLOBs bwb=new BorrowWithBLOBs();
		BeanUtils.copyProperties(this.getBorrowInfoByNid(borrowNid),bwb);
		BeanUtils.copyProperties(this.getBorrow(borrowNid),bwb);
//		BorrowExample example = new BorrowExample();
//		BorrowExample.Criteria cra = example.createCriteria();
//		cra.andBorrowNidEqualTo(borrowNid);
//
//		List<BorrowWithBLOBs> borrowList = this.borrowMapper.selectByExampleWithBLOBs(example);
//		if (borrowList != null && borrowList.size() > 0) {
//			return borrowList.get(0);
//		}

		return bwb;
	}

    /**
     * 获取放款服务费率
     * @param projectType
     * @param borrowStyle
     * @param instCode
     * @param borrowPeriod
     * @return
     */
	@Override
	public String getBorrowServiceScale(Integer projectType, String borrowStyle, String instCode, Integer borrowPeriod) {
		String serviceRate = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("borrowStyle", borrowStyle);
		params.put("borrowPeriod", borrowPeriod);
		params.put("instCode", instCode);
		params.put("assetType", projectType);
		params.put("projectType", projectType);
		serviceRate = borrowFullCustomizeMapper.selectServiceRateByParams(params);
		return serviceRate;
	}

	/**
	 * 合作机构
	 * @return
	 */
//	@Override
//	public List<Links> getLinks() {
//		LinksExample example = new LinksExample();
//		LinksExample.Criteria cra = example.createCriteria();
//		cra.andTypeEqualTo(2);
//		cra.andPartnerTypeEqualTo(7);
//
//		List<Links> links = linksMapper.selectByExample(example);
//
//		if (links != null && links.size() > 0) {
//			return links;
//		}
//		return null;
//	}


	/**
	 * 获取还款服务费
	 * @param projectType
	 * @param borrowStyle
	 * @param instCode
	 * @param borrowPeriod
	 * @return
	 */
    @Override
    public String getBorrowManagerScale(Integer projectType, String borrowStyle, String instCode,Integer borrowPeriod) {
        String manChargeRate = "";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("borrowStyle",borrowStyle);
        params.put("borrowPeriod",borrowPeriod);
        params.put("instCode",instCode);
        params.put("assetType",projectType);
        params.put("projectType",projectType);
        manChargeRate = borrowFullCustomizeMapper.selectManChargeRateByParams(params);
        return manChargeRate;
    }


    /**
     * 收益差率
     * @param projectType
     * @param borrowStyle
     * @param instCode
     * @param borrowPeriod
     * @return
     */
    @Override
	public String getBorrowReturnScale(Integer projectType, String borrowStyle, String instCode, Integer borrowPeriod) {
		String returnRate = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("borrowStyle", borrowStyle);
		params.put("borrowPeriod", borrowPeriod);
		params.put("instCode", instCode);
		params.put("assetType", projectType);
		params.put("projectType", projectType);
		returnRate = borrowFullCustomizeMapper.selectReturnRateByParams(params);
		return returnRate;
	}

	/**
	 * 还款服务费率(最低，最高)
	 * @param projectType
	 * @param borrowStyle
	 * @param borrowPeriod
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject getBorrowManagerScale(Integer projectType, String borrowStyle, Integer borrowPeriod, JSONObject jsonObject) {
		BorrowFinhxfmanChargeExample example = new BorrowFinhxfmanChargeExample();
		BorrowFinhxfmanChargeExample.Criteria cra = example.createCriteria();
		if ("endday".equals(borrowStyle)) {
			cra.andChargeTimeTypeEqualTo("endday");
		} else {
			cra.andChargeTimeEqualTo(borrowPeriod);
			cra.andChargeTimeTypeNotEqualTo("endday");
		}
		cra.andStatusEqualTo(0);

		List<BorrowFinhxfmanCharge> borrowFinhxfmanChargeList = borrowFinhxfmanChargeMapper.selectByExample(example);
		if (borrowFinhxfmanChargeList != null && borrowFinhxfmanChargeList.size() > 0) {
			jsonObject.put("borrowManagerScale", borrowFinhxfmanChargeList.get(0).getManChargePer());
			jsonObject.put("borrowManagerScaleEnd", borrowFinhxfmanChargeList.get(0).getManChargePerEnd());
		} else {
			jsonObject.put("borrowManagerScale", "");
			jsonObject.put("borrowManagerScaleEnd", "");
		}

		return jsonObject;
	}

	/**
	 * 上传图片的信息
	 * 
	 * @param borrowBean
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getUploadImage(BorrowCommonBean borrowBean, String files, String borrowNid) throws Exception {

		HashMap<String, String> fileMap = new HashMap<String, String>();
		// 项目资料
		if (StringUtils.isNotEmpty(files)) {
			List<BorrowCommonFile> borrowCommonFileList = JSONArray.parseArray(files, BorrowCommonFile.class);
			if (borrowCommonFileList != null && borrowCommonFileList.size() > 0) {
				for (BorrowCommonFile borrowCommonFile : borrowCommonFileList) {
					List<BorrowCommonFileData> fileDataList = borrowCommonFile.getData();
					if (fileDataList != null && fileDataList.size() > 0) {
						for (BorrowCommonFileData borrowCommonFileData : fileDataList) {
							if (StringUtils.isEmpty(borrowCommonFileData.getFileRealName())) {
								fileMap.put(borrowCommonFileData.getName(), borrowCommonFileData.getFileurl());
							} else {
								fileMap.put(borrowCommonFileData.getFileRealName(), borrowCommonFileData.getFileurl());
							}
						}
					}
				}
			}
		}

		List<BorrowCommonImageVO> borrowCommonImageList = borrowBean.getBorrowCommonImageList();
		if (borrowCommonImageList != null && borrowCommonImageList.size() > 0) {
			// 保存的物理路径
			String filePhysicalPath = UploadFileUtils.getDoPath(physical);
			// 正式保存的路径
			String fileUploadRealPath = UploadFileUtils.getDoPath(real) + UploadFileUtils.getDoPath(borrowNid);

			List<BorrowCommonFile> fileList = new ArrayList<BorrowCommonFile>();
			BorrowCommonFile borrowCommonFile = new BorrowCommonFile();
			List<BorrowCommonFileData> fileDataList = new ArrayList<BorrowCommonFileData>();
			for (BorrowCommonImageVO borrowCommonImage : borrowCommonImageList) {
				BorrowCommonFileData borrowCommonFileData = new BorrowCommonFileData();
				if(!borrowCommonImage.getImagePath().isEmpty()) {
					
					
					// 图片顺序
					borrowCommonFileData.setImageSort(borrowCommonImage.getImageSort().trim());
					// 图片名称
					borrowCommonFileData.setName(borrowCommonImage.getImageName());
					// 图片真实名称
					borrowCommonFileData.setFileRealName(borrowCommonImage.getImageRealName());
					// 图片真实名称
					borrowCommonFileData.setFilename(borrowCommonImage.getImageRealName());
	
					if (fileMap == null || fileMap.isEmpty()) {
						// 图片路径
						String fileName = UploadFileUtils.upload4CopyFile(filePhysicalPath + borrowCommonImage.getImagePath(), filePhysicalPath + fileUploadRealPath);
						borrowCommonFileData.setFileurl(fileUploadRealPath + fileName);
						// 垃圾文件删除
						UploadFileUtils.removeFile4Dir(borrowCommonImage.getImagePath());
					} else {
						// 该文件是否已经存在-不存在
						if (!(fileMap.containsKey(borrowCommonImage.getImageRealName()) || fileMap.containsKey(borrowCommonImage.getImageName()))) {
							// 图片路径
							String fileName = UploadFileUtils.upload4CopyFile(filePhysicalPath + borrowCommonImage.getImagePath(), filePhysicalPath + fileUploadRealPath);
							borrowCommonFileData.setFileurl(fileUploadRealPath + fileName);
							// 垃圾文件删除
							UploadFileUtils.removeFile4Dir(borrowCommonImage.getImagePath());
						} else {
							// 图片顺序
							borrowCommonFileData.setImageSort(borrowCommonImage.getImageSort().trim());
							// 图片名称
							borrowCommonFileData.setName(borrowCommonImage.getImageName());
							// 图片真实名称
							borrowCommonFileData.setFileRealName(borrowCommonImage.getImageRealName());
							// 图片真实名称
							borrowCommonFileData.setFilename(borrowCommonImage.getImageRealName());
							// 图片路径
							borrowCommonFileData.setFileurl(borrowCommonImage.getImagePath());
							// 文件已经存在
							fileMap.remove(borrowCommonImage.getImageRealName());
						}
					}
					fileDataList.add(borrowCommonFileData);
				}
			}

			// 垃圾文件删除
			if (fileMap != null && !fileMap.isEmpty()) {
				Iterator<Entry<String, String>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					UploadFileUtils.removeFile4Dir(fileUploadRealPath + entry.getValue());
				}
			}

			if (fileDataList != null && fileDataList.size() > 0) {
				borrowCommonFile.setName("");
				borrowCommonFile.setData(fileDataList);
				fileList.add(borrowCommonFile);
				return JSONObject.toJSONString(fileList, false);
			}
		}
		return "";
	}

	/**
	 * 信息验证
	 * 
	 * @param mav
	 * @param request
	 */
//	@Override
//	public void validatorFieldCheck(ModelAndView mav, BorrowCommonBean borrowBean, boolean isExistsRecord, String HztOrHxf) {
//
//		// 项目类型
//		ValidatorFieldCheckUtil.validateRequired(mav, "projectType", borrowBean.getProjectType());
//
//		// 借款用户
//		int usersFlag = this.isExistsUser(borrowBean.getUsername());
//		if (usersFlag == 1) {
//			ValidatorFieldCheckUtil.validateSpecialError(mav, "username", "username.not.exists");
//		} else if (usersFlag == 2) {
//			ValidatorFieldCheckUtil.validateSpecialError(mav, "username", "username.not.account");
//		} else if (usersFlag == 3) {
//			ValidatorFieldCheckUtil.validateSpecialError(mav, "username", "username.not.use");
//		} else if (usersFlag == 4) {
//			ValidatorFieldCheckUtil.validateSpecialError(mav, "username", "username.not.role");
//		}
//
//		if(borrowBean.getEntrustedFlg().equals("1")){
//			// 受托用户
//			int entrustedUsersFlg = this.isEntrustedExistsUserCheck(borrowBean.getInstCode(),borrowBean.getEntrustedUsername());
//			if (entrustedUsersFlg == 1) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "entrustedUsername", "username.not.exist");
//			} else if (entrustedUsersFlg == 2) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "entrustedUsername", "username.not.accounts");
//			} else if (entrustedUsersFlg == 3) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "entrustedUsername", "username.not.uses");
//			} else if (entrustedUsersFlg == 4) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "entrustedUsername", "username.not.in");
//			} else if (entrustedUsersFlg == 5) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "entrustedUsername", "username.not.relevant");
//			}
//		}
//
//		// 借款预编号
//		if (!isExistsRecord) {
//			String borrowPreNid = borrowBean.getBorrowPreNid();
//			boolean borrowPreNidFlag = ValidatorFieldCheckUtil.validateMaxLength(mav, "borrowPreNid", borrowBean.getBorrowPreNid(), 12, true);
//			if (borrowPreNidFlag) {
//				borrowPreNidFlag = this.isExistsRecord(StringUtils.EMPTY, borrowPreNid);
//				if (borrowPreNidFlag) {
//					ValidatorFieldCheckUtil.validateSpecialError(mav, "borrowPreNid", "repeat");
//				}
//			}
//		}
//
//		String isChaibiao = borrowBean.getIsChaibiao();
//		// 借款标题 & 借款金额
//		if (isExistsRecord || !"yes".equals(isChaibiao)) {
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "name", borrowBean.getName(), 60, true);
//			ValidatorFieldCheckUtil.validateSignlessNum(mav, "account", borrowBean.getAccount(), 10, true);
//		} else if ("yes".equals(isChaibiao)) {
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "name", borrowBean.getName(), 60, true);
//			boolean accountFlag = ValidatorFieldCheckUtil.validateSignlessNum(mav, "account", borrowBean.getAccount(), 10, true);
//			List<BorrowCommonNameAccount> borrowCommonNameAccountList = borrowBean.getBorrowCommonNameAccountList();
//			boolean accountFlag2 = true;
//			BigDecimal amountAll = BigDecimal.ZERO;
//			BigDecimal amount = BigDecimal.ZERO;
//			if (accountFlag) {
//				amount = new BigDecimal(borrowBean.getAccount());
//				if ((BigDecimal.ZERO).equals(amount)) {
//					ValidatorFieldCheckUtil.validateSpecialError(mav, "account-error", "account.not.zero");
//					accountFlag = false;
//				}
//			}
//			for (int i = 0; i < borrowCommonNameAccountList.size(); i++) {
//				BorrowCommonNameAccount borrowCommonNameAccount = borrowCommonNameAccountList.get(i);
//				ValidatorFieldCheckUtil.validateMaxLength(mav, "names" + i, borrowCommonNameAccount.getNames(), 60, true);
//				boolean accountFlag3 = ValidatorFieldCheckUtil.validateSignlessNum(mav, "accounts" + i, borrowCommonNameAccount.getAccounts(), 10, true);
//				if (accountFlag3) {
//					amountAll = amountAll.add(new BigDecimal(borrowCommonNameAccount.getAccounts()));
//				} else {
//					accountFlag2 = false;
//				}
//			}
//
//			if (accountFlag && accountFlag2 && !amountAll.equals(amount)) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "account-error", "not.equals.account");
//			}
//		}
//
//		// 还款方式
//		boolean borrowStyleFlag = ValidatorFieldCheckUtil.validateRequired(mav, "borrowStyle", borrowBean.getBorrowStyle());
//
//		String borrowApr = borrowBean.getBorrowApr();
//		BigDecimal borrowAprDec = new BigDecimal(borrowApr);
//		if (borrowAprDec.compareTo(BigDecimal.ZERO) <= 0) {
//			ValidatorFieldCheckUtil.validateSpecialError(mav, "borrowApr", "borrowapr.not.zero");
//		}
//		// 出借利率
//		ValidatorFieldCheckUtil.validateSignlessNumLength(mav, "borrowApr", borrowBean.getBorrowApr(), 2, 2, true);
//
//		// 借款期限
//		boolean borrowPeriodFlag = ValidatorFieldCheckUtil.validateSignlessNum(mav, "borrowPeriod", borrowBean.getBorrowPeriod(), 3, true);
//
//		// 售价预估
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalPriceEstimate", borrowBean.getDisposalPriceEstimate(), 50, false);
//		// 处置周期
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalPeriod", borrowBean.getDisposalPeriod(), 50, false);
//		// 处置渠道
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalChannel", borrowBean.getDisposalChannel(), 50, false);
//		// 处置结果预案
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalResult", borrowBean.getDisposalResult(), 2000, false);
//		// 备注说明
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalNote", borrowBean.getDisposalNote(), 2000, false);
//
//		// 项目名称
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalProjectName", borrowBean.getDisposalProjectName(), 100, false);
//		// 项目类型
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalProjectType", borrowBean.getDisposalProjectType(), 100, false);
//		// 所在地区
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalArea", borrowBean.getDisposalArea(), 200, false);
//		// 预估价值
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalPredictiveValue", borrowBean.getDisposalPredictiveValue(), 20, false);
//		// 权属类别
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalOwnershipCategory", borrowBean.getDisposalOwnershipCategory(), 20, false);
//		// 资产成因
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalAssetOrigin", borrowBean.getDisposalAssetOrigin(), 20, false);
//		// 附件信息
//		ValidatorFieldCheckUtil.validateMaxLength(mav, "disposalAttachmentInfo", borrowBean.getDisposalAttachmentInfo(), 2000, false);
//
//		// 初审
//		if ("BORROW_FIRST".equals(borrowBean.getMoveFlag())) {
//			// 初审意见
//			ValidatorFieldCheckUtil.validateRequired(mav, "verify", borrowBean.getVerify());
//			// 发标方式
//			boolean verifyStatusFlag = ValidatorFieldCheckUtil.validateRequired(mav, "verifyStatus", borrowBean.getVerifyStatus());
//			if (verifyStatusFlag) {
//				// 定时发标
//				if (StringUtils.equals(borrowBean.getVerifyStatus(), "1")) {
//					verifyStatusFlag = ValidatorFieldCheckUtil.validateDateYYYMMDDHH24MI(mav, "ontime", borrowBean.getOntime(), true);
//					if (verifyStatusFlag) {
//						String systeDate = GetDate.getServerDateTime(14, new Date());
//						if (systeDate.compareTo(borrowBean.getOntime()) >= 0) {
//							ValidatorFieldCheckUtil.validateSpecialError(mav, "ontime", "ontimeltsystemdate");
//						}
//						if (StringUtils.isNotEmpty(borrowBean.getBookingBeginTime()) || StringUtils.isNotEmpty(borrowBean.getBookingEndTime())) {
//							// 如果开始预约和截止预约时间中有一个时间有值,则进行以下验证
//							// 截止预约时间必填
//							verifyStatusFlag = ValidatorFieldCheckUtil.validateDateYYYMMDDHH24MI(mav, "bookingEndTime", borrowBean.getBookingEndTime(), true);
//							// 截止预约时间必须小于定时发标时间
//							if (verifyStatusFlag && borrowBean.getBookingEndTime().compareTo(borrowBean.getOntime()) >= 0) {
//								ValidatorFieldCheckUtil.validateSpecialError(mav, "bookingEndTime", "bookingEndTimeltontime");
//							}
//							// 开始预约时间必填
//							verifyStatusFlag = ValidatorFieldCheckUtil.validateDateYYYMMDDHH24MI(mav, "bookingBeginTime", borrowBean.getBookingBeginTime(), true);
//							// 开始预约时间必须小于截止预约时间
//							if (verifyStatusFlag && borrowBean.getBookingBeginTime().compareTo(borrowBean.getBookingEndTime()) >= 0) {
//								ValidatorFieldCheckUtil.validateSpecialError(mav, "bookingEndTime", "bookingBeginTimeltbookingEndTime");
//							}
//							// 当前时间必须小于开始预约时间
//							if (systeDate.compareTo(borrowBean.getBookingBeginTime()) >= 0) {
//								ValidatorFieldCheckUtil.validateSpecialError(mav, "bookingBeginTime", "bookingBeginTimeltsystemdate");
//							}
//						}
//					}
//				}
//			}
//			// 复审意见
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "verifyRemark", borrowBean.getVerifyRemark(), 255, true);
//		}
//
//		// 个人信息(信批需求新增字段属于非必须入力，无需校验(已合产品确认))
//		if (StringUtils.equals("2", borrowBean.getCompanyOrPersonal())) {
//			// 借款人信息 姓名
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "manname", borrowBean.getManname(), 50, true);
//			// 借款人信息 年龄
//			ValidatorFieldCheckUtil.validateSignlessNum(mav, "old", borrowBean.getOld(), 3, false);
//			// 借款人信息 行业
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "industry", borrowBean.getIndustry(), 50, false);
//			// 借款人信息 授信额度
//			ValidatorFieldCheckUtil.validateSignlessNum(mav, "userCredit", borrowBean.getUserCredit(), 10, false);
//			// 身份证号
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "cardNo", borrowBean.getCardNo(), 18, true);
//			// 个人借款
//			boolean isPersonCAFlag = this.isPersonCAFlag(borrowBean.getManname(),borrowBean.getCardNo());
//			if(!isPersonCAFlag){
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "manname", "certificate.authority.idno.not.exist");
//			}
//		}
//
//		// 企业名称(信批需求新增字段属于非必须入力，无需校验(已合产品确认))
//		if (StringUtils.equals("1", borrowBean.getCompanyOrPersonal())) {
//			// 企业名称
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "comName", borrowBean.getComName(), 50, true);
//			// 注册时间
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "comRegTime", borrowBean.getComRegTime(), 30, false);
//			// 涉诉情况
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "comLitigation", borrowBean.getComLitigation(), 100, false);
//			// 注册资本
//			ValidatorFieldCheckUtil.validateDecimal(mav, "comRegCaptial", borrowBean.getComRegCaptial(), 13, false);
//			// 授信额度
//			ValidatorFieldCheckUtil.validateSignlessNum(mav, "comCredit", borrowBean.getComCredit(), 10, false);
//			// 征信记录
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "comCreReport", borrowBean.getComCreReport(), 100, false);
//			// 所属行业
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "comUserIndustry", borrowBean.getComUserIndustry(), 100, false);
//			// 社会统一信用代码
//			ValidatorFieldCheckUtil.validateMaxLength(mav, "comSocialCreditCode", borrowBean.getComSocialCreditCode(), 30, true);
//
//			// 根据企业名称,社会统一代码查询企业用户是否做CA认证
//			boolean isCaFlag = this.companyCAFlag(borrowBean.getComName(), borrowBean.getComSocialCreditCode());
//			if(!isCaFlag){
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "comSocialCreditCode", "certificate.authority.idno.not.exist");
//			}
//		}
//
//		if (StringUtils.equals("2", borrowBean.getTypeCar())) {
//			List<BorrowCommonCar> borrowCarinfoList = borrowBean.getBorrowCarinfoList();
//			if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
//				for (int i = 0; i < borrowCarinfoList.size(); i++) {
//					BorrowCommonCar borrowCarinfo = borrowCarinfoList.get(i);
//					// 车辆信息 品牌
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "brand" + i, borrowCarinfo.getBrand(), 40, true);
//					// 车辆信息 型号
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "model" + i, borrowCarinfo.getModel(), 50, false);
//					// 车辆信息 车系
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "carseries" + i, borrowCarinfo.getCarseries(), 50, false);
//					// 车辆信息 颜色
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "color" + i, borrowCarinfo.getColor(), 16, false);
//					// 车辆信息 出厂年份
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "year" + i, borrowCarinfo.getYear(), 12, false);
//					// 车辆信息 产地
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "place" + i, borrowCarinfo.getPlace(), 60, false);
//					// 车辆信息 购买日期
//					ValidatorFieldCheckUtil.validateDate(mav, "buytime" + i, borrowCarinfo.getBuytime(), false);
//					// 车辆信息 购买价
//					ValidatorFieldCheckUtil.validateSignlessNum(mav, "price" + i, borrowCarinfo.getPrice(), 13, false);
//					// del by liuyang 20170714 start
//					//// 车辆信息 是否有保险
//					//ValidatorFieldCheckUtil.validateRequired(mav, "isSafe" + i, borrowCarinfo.getIsSafe());
//					// del by liuyang 20170714 end
//					// 车辆信息 评估价
//					ValidatorFieldCheckUtil.validateSignlessNum(mav, "toprice" + i, borrowCarinfo.getToprice(), 13, true);
//				}
//			}
//		}
//
//		if (StringUtils.equals("1", borrowBean.getTypeHouse())) {
//			List<BorrowHouses> borrowHousesList = borrowBean.getBorrowHousesList();
//			if (borrowHousesList != null && borrowHousesList.size() > 0) {
//				for (int i = 0; i < borrowHousesList.size(); i++) {
//					BorrowHouses record = borrowHousesList.get(i);
//					// 房产类型
//					ValidatorFieldCheckUtil.validateRequired(mav, "housesType" + i, record.getHousesType());
//					// 房产位置
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "housesLocation" + i, record.getHousesLocation(), 255, false);
//					// 建筑面积
//					ValidatorFieldCheckUtil.validateSignlessNumLength(mav, "housesArea" + i, record.getHousesArea(), 7, 2, true);
//					// modify by liuyang 20170714 start
//					// 市值
//					// ValidatorFieldCheckUtil.validateSignlessNum(mav, "housesPrice" + i, record.getHousesPrice(), 20, true);
//					ValidatorFieldCheckUtil.validateSignlessNum(mav, "housesPrice" + i, record.getHousesPrice(), 20, false);
//					// modify by liuyang 20170714 end
//					// 评估价值（元）
//					ValidatorFieldCheckUtil.validateSignlessNum(mav, "housesToprice" + i, record.getHousesToprice(), 20, false);
//					// 资产所属
//					ValidatorFieldCheckUtil.validateMaxLength(mav, "housesBelong" + i, record.getHousesBelong(), 20, false);
//				}
//			}
//		}
//
//		List<BorrowCommonCompanyAuthen> borrowCommonCompanyAuthenList = borrowBean.getBorrowCommonCompanyAuthenList();
//		if (borrowCommonCompanyAuthenList != null && borrowCommonCompanyAuthenList.size() > 0) {
//			for (int i = 0; i < borrowCommonCompanyAuthenList.size(); i++) {
//				BorrowCommonCompanyAuthen record = borrowCommonCompanyAuthenList.get(i);
//				// 展示顺序
//				ValidatorFieldCheckUtil.validateSignlessNum(mav, "authenSortKey" + i, record.getAuthenSortKey(), 2, true);
//				// 认证项目名称
//				ValidatorFieldCheckUtil.validateMaxLength(mav, "authenName" + i, record.getAuthenName(), 255, true);
//				// 认证时间
//				ValidatorFieldCheckUtil.validateDate(mav, "authenTime" + i, record.getAuthenTime(), true);
//			}
//		}
//
//		List<BorrowCommonImage> borrowCommonImageList = borrowBean.getBorrowCommonImageList();
//		if (borrowCommonImageList != null && borrowCommonImageList.size() > 0) {
//			for (int i = 0; i < borrowCommonImageList.size(); i++) {
//				BorrowCommonImage record = borrowCommonImageList.get(i);
//				// 展示顺序
//				ValidatorFieldCheckUtil.validateMaxLength(mav, "imageSort" + i, record.getImageSort().trim(), 2, true);
//				// 资料名称
//				boolean imageNameFlag = ValidatorFieldCheckUtil.validateMaxLength(mav, "imageName" + i, record.getImageName(), 50, true);
//				// 图片路径
//				boolean imagePathFlag = ValidatorFieldCheckUtil.validateMaxLength(mav, "imagePath" + i, record.getImagePath(), 100, true);
//
//				if (imageNameFlag && imagePathFlag) {
//					String imagePath = record.getImagePath();
//					String imageRealName = record.getImageRealName();
//					if (!imagePath.contains(imageRealName)) {
//						ValidatorFieldCheckUtil.validateSpecialError(mav, "imageSrc" + i, "image.not.exists");
//					}
//				}
//			}
//		}
//
//		if (CustomConstants.HZT.equals(HztOrHxf)) {
//
//			// 放款服务费(放款时收)
//			if (borrowPeriodFlag && borrowStyleFlag) {
//				String borrowStyle = borrowBean.getBorrowStyle();
//				List<BorrowStyle> borrowStyleList = this.borrowStyleList(borrowStyle);
//				if (borrowStyleList != null && borrowStyleList.size() > 0) {
//                    String borrowServiceScale = this.getBorrowServiceScale(borrowBean.getProjectType(), borrowStyle, borrowBean.getInstCode(),Integer.valueOf(borrowBean.getBorrowPeriod()));
//                    if (StringUtils.isEmpty(borrowServiceScale)) {
//						ValidatorFieldCheckUtil.validateSpecialError(mav, "borrowServiceScale", "notget.borrowservicescale");
//					} else {
//						borrowBean.setBorrowServiceScale(borrowServiceScale);
//					}
//				}
//			}
//
//			// 还款服务费率
//			if (borrowStyleFlag) {
//				String borrowStyle = borrowBean.getBorrowStyle();
//				List<BorrowStyle> borrowStyleList = this.borrowStyleList(borrowStyle);
//				if (borrowStyleList != null && borrowStyleList.size() > 0) {
//                    String borrowManagerScale = this.getBorrowManagerScale(borrowBean.getProjectType(), borrowStyle, borrowBean.getInstCode(), Integer.parseInt(borrowBean.getBorrowPeriod()));
//                    if (StringUtils.isEmpty(borrowManagerScale)) {
//						ValidatorFieldCheckUtil.validateSpecialError(mav, "borrowManagerScale", "notget.borrowmanagerscale");
//					} else {
//						borrowBean.setBorrowManagerScale(borrowManagerScale);
//					}
//				}
//			}
//		} else if (CustomConstants.HXF.equals(HztOrHxf)) {
//			// 还款服务费率
//			if (borrowStyleFlag) {
//				String borrowStyle = borrowBean.getBorrowStyle();
//				List<BorrowStyle> borrowStyleList = this.borrowStyleList(borrowStyle);
//				if (borrowStyleList != null && borrowStyleList.size() > 0) {
//					JSONObject jsonObject = new JSONObject();
//					jsonObject = this.getBorrowManagerScale(borrowBean.getProjectType(), borrowStyle, Integer.valueOf(borrowBean.getBorrowPeriod()), jsonObject);
//					if (StringUtils.isEmpty(jsonObject.getString("borrowManagerScale"))) {
//						ValidatorFieldCheckUtil.validateSpecialError(mav, "borrowManagerScale", "notget.borrowmanagerscale.start");
//					}
//					if (StringUtils.isEmpty(jsonObject.getString("borrowManagerScaleEnd"))) {
//						ValidatorFieldCheckUtil.validateSpecialError(mav, "borrowManagerScaleEnd", "notget.borrowmanagerscale.end");
//					}
//				}
//			}
//		}
//
//		// 最低投标金额
//		boolean tenderAccountMinFlag = ValidatorFieldCheckUtil.validateSignlessNum(mav, "tenderAccountMin", borrowBean.getTenderAccountMin(), 10, false);
//		// 最高投标金额
//		boolean tenderAccountMaxFlag = ValidatorFieldCheckUtil.validateSignlessNum(mav, "tenderAccountMax", borrowBean.getTenderAccountMax(), 10, false);
//
//		if (tenderAccountMinFlag && tenderAccountMaxFlag && StringUtils.isNotEmpty(borrowBean.getTenderAccountMin()) && StringUtils.isNotEmpty(borrowBean.getTenderAccountMax())) {
//			BigDecimal tenderAccountMinb = new BigDecimal(borrowBean.getTenderAccountMin());
//			BigDecimal tenderAccountMaxb = new BigDecimal(borrowBean.getTenderAccountMax());
//			if (tenderAccountMaxb.compareTo(tenderAccountMinb) <= 0) {
//				ValidatorFieldCheckUtil.validateSpecialError(mav, "tenderAccount", "tenderaccount");
//			}
//		}
//		// 有效时间
//		ValidatorFieldCheckUtil.validateSignlessNum(mav, "borrowValidTime", borrowBean.getBorrowValidTime(), 2, false);
//	}

	/**
	 * 画面的值放到Bean中
	 * 
	 * @param isExistsRecord
	 * @param form
	 */
	@Override
	public void setPageListInfo( BorrowCommonBean form, boolean isExistsRecord) {

		List<BorrowCommonNameAccountVO> borrowCommonNameAccountList = new ArrayList<BorrowCommonNameAccountVO>();
		String isChaibiao = form.getIsChaibiao();
		// 借款标题 & 借款金额
		if (!isExistsRecord && "yes".equals(isChaibiao)) {
			borrowCommonNameAccountList = form.getBorrowCommonNameAccountList();
					//JSONArray.parseArray(form.getBorrowNameJson(), BorrowCommonNameAccountVO.class);
		}
		form.setBorrowCommonNameAccountList(borrowCommonNameAccountList);
		// 车辆信息
		if (StringUtils.equals("2", form.getTypeCar())) {
			//List<BorrowCommonCarVO> borrowCarinfoList = JSONArray.parseArray(form.getBorrowCarJson(), BorrowCommonCarVO.class);
			form.setBorrowCarinfoList(form.getBorrowCarinfoList());
		}
		// 房产信息
		if (StringUtils.equals("1", form.getTypeHouse())) {
			//List<BorrowHousesVO> borrowHousesList = JSONArray.parseArray(form.getBorrowHousesJson(), BorrowHousesVO.class);
			form.setBorrowHousesList(form.getBorrowHousesList());
		}
		// 认证信息
//		List<BorrowCommonCompanyAuthenVO> borrowCommonCompanyAuthenList = JSONArray.parseArray(form.getBorrowAuthenJson(), BorrowCommonCompanyAuthenVO.class);
//		form.setBorrowCommonCompanyAuthenList(borrowCommonCompanyAuthenList);
		// 项目资料
		List<BorrowCommonImageVO> borrowCommonImageList =form.getBorrowCommonImageList();
		String fileDomainUrl = UploadFileUtils.getDoPath(url);
		if(borrowCommonImageList!=null) {
			for (BorrowCommonImageVO borrowCommonImage : borrowCommonImageList) {
				borrowCommonImage.setImageSrc(fileDomainUrl + borrowCommonImage.getImagePath());
			}
		}

		form.setBorrowCommonImageList(borrowCommonImageList);
	}

	/**
	 * 获取放款服务费率 & 还款服务费率 & 收益差率
	 * 
	 * @param borrowCommonRequest
	 * @return
	 */
	@Override
	public BorrowCommonVO getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest) {

		Integer projectType=borrowCommonRequest.getProjectType();
		String borrowStyle=borrowCommonRequest.getBorrowStyle();
		String instCode=borrowCommonRequest.getInstCode();
		String borrowPeriod=borrowCommonRequest.getBorrowPeriod();
		BorrowCommonVO bean=new BorrowCommonVO();
        // 获取放款服务费率
        String borrowServiceScale = this.getBorrowServiceScale(projectType, borrowStyle, instCode, Integer.valueOf(borrowPeriod));
        bean.setBorrowServiceScale(StringUtils.isEmpty(borrowServiceScale) ? "--" : borrowServiceScale);
        // 还款服务费率
        String borrowManagerScale = this.getBorrowManagerScale(projectType, borrowStyle, instCode, Integer.valueOf(borrowPeriod));
        bean.setBorrowManagerScale(StringUtils.isEmpty(borrowManagerScale) ? "--" : borrowManagerScale);

        // 收益差率
        String borrowReturnScale = this.getBorrowReturnScale(projectType, borrowStyle, instCode, Integer.valueOf(borrowPeriod));
        bean.setBorrowReturnScale(StringUtils.isEmpty(borrowReturnScale) ? "--" : borrowReturnScale);
		return bean;
	}

//	/**
//	 * 资料上传
//	 * 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
//		String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
//		String filePhysicalPath = UploadFileUtils.getDoPath(PropUtils.getSystem("file.physical.path"));
//		String fileUploadTempPath = UploadFileUtils.getDoPath(PropUtils.getSystem("file.upload.temp.path"));
//
//		String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
//
//		File logoSaveFile = new File(logoRealPathDir);
//		if (!logoSaveFile.exists()) {
//			logoSaveFile.mkdirs();
//		}
//
//		BorrowCommonImage fileMeta = null;
//		LinkedList<BorrowCommonImage> files = new LinkedList<BorrowCommonImage>();
//
//		Iterator<String> itr = multipartRequest.getFileNames();
//		MultipartFile multipartFile = null;
//
//		while (itr.hasNext()) {
//			multipartFile = multipartRequest.getFile(itr.next());
//			String fileRealName = String.valueOf(new Date().getTime());
//			String originalFilename = multipartFile.getOriginalFilename();
//			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
//
//			// 文件大小
//			String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
//
//			fileMeta = new BorrowCommonImage();
//			int index = originalFilename.lastIndexOf(".");
//			if (index != -1) {
//				fileMeta.setImageName(originalFilename.substring(0, index));
//			} else {
//				fileMeta.setImageName(originalFilename);
//			}
//
//			fileMeta.setImageRealName(fileRealName);
//			fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
//			fileMeta.setImageType(multipartFile.getContentType());
//			fileMeta.setErrorMessage(errorMessage);
//			// 获取文件路径
//			fileMeta.setImagePath(fileUploadTempPath + fileRealName);
//			fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
//			files.add(fileMeta);
//		}
//		return JSONObject.toJSONString(files, true);
//	}

//	/**
//	 * 导出功能
//	 * 
//	 * @param request
//	 * @param modelAndView
//	 * @param form
//	 */
//	@Override
//	public void downloadCar(HttpServletRequest request, HttpServletResponse response, BorrowBean form) throws Exception {
//		// 表格sheet名称
//		String sheetName = "抵押车辆模板";
//
//		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//		String[] titles = new String[] { "车辆品牌", "型号", "车系", "颜色", "出厂年份", "产地", "购买日期(例：2016-01-04)", "购买价格（元）", "是否有保险(否|有)", "评估价（元）","车牌号","车辆登记地","车架号" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);
//
//		CellStyle style = workbook.createCellStyle();
//		DataFormat format = workbook.createDataFormat();
//		style.setDataFormat(format.getFormat("@"));
//		for (int i = 0; i < 10; i++) {
//			sheet.setDefaultColumnStyle(i, style);
//		}
//
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}

//	/**
//	 * 资料上传
//	 * 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public String uploadCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
//
//		Iterator<String> itr = multipartRequest.getFileNames();
//		MultipartFile multipartFile = null;
//
//		List<BorrowCommonCar> borrowCarinfoList = new ArrayList<BorrowCommonCar>();
//
//		while (itr.hasNext()) {
//			multipartFile = multipartRequest.getFile(itr.next());
//			String fileRealName = String.valueOf(new Date().getTime());
//			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
//
//			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());
//
//			if (hssfWorkbook != null) {
//				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
//					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//
//					// 循环行Row
//					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//						if (rowNum == 0) {
//							continue;
//						}
//						BorrowCommonCar borrowCarinfo = new BorrowCommonCar();
//						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//						if (hssfRow == null
//								|| (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null && hssfRow.getCell(2) == null && hssfRow.getCell(3) == null && hssfRow.getCell(4) == null
//										&& hssfRow.getCell(5) == null && hssfRow.getCell(6) == null && hssfRow.getCell(7) == null && hssfRow.getCell(8) == null && hssfRow.getCell(9) == null)) {
//							continue;
//						}
//						
//						if (!(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(1))) 
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(2)))
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(3))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(4)))
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(5))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(6)))
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(7))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(8)))
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(9))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(10)))
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(11))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(12))))) {
//							// 车辆品牌
//							borrowCarinfo.setBrand(this.getValue(hssfRow.getCell(0)));
//							// 型号
//							borrowCarinfo.setModel(this.getValue(hssfRow.getCell(1)));
//							// 车系
//							borrowCarinfo.setCarseries(this.getValue(hssfRow.getCell(2)));
//							// 颜色
//							borrowCarinfo.setColor(this.getValue(hssfRow.getCell(3)));
//							// 出厂年份
//							borrowCarinfo.setYear(this.getValue(hssfRow.getCell(4)));
//							// 产地
//							borrowCarinfo.setPlace(this.getValue(hssfRow.getCell(5)));
//							// 购买日期
//							borrowCarinfo.setBuytime(this.getValue(hssfRow.getCell(6)));
//							// 购买价格（元）
//							borrowCarinfo.setPrice(this.getValue(hssfRow.getCell(7)));
//							// 是否有保险
//							borrowCarinfo.setIsSafe(this.getValue(hssfRow.getCell(8)));
//							// 评估价（元）
//							borrowCarinfo.setToprice(this.getValue(hssfRow.getCell(9)));
//							// 车牌号
//							borrowCarinfo.setNumber(this.getValue(hssfRow.getCell(10)));
//							// 车辆登记地
//							borrowCarinfo.setRegistration(this.getValue(hssfRow.getCell(11)));
//							// 车架号
//							borrowCarinfo.setVin(this.getValue(hssfRow.getCell(12)));
//							
//							borrowCarinfoList.add(borrowCarinfo);
//						}
//					}
//				}
//			}
//		}
//		return JSONObject.toJSONString(borrowCarinfoList, true);
//	}

//	/**
//	 * 导出功能
//	 * 
//	 * @param request
//	 * @param modelAndView
//	 * @param form
//	 */
//	@Override
//	public void downloadHouse(HttpServletRequest request, HttpServletResponse response, BorrowBean form) throws Exception {
//		// 表格sheet名称
//		String sheetName = "抵押房产模板";
//
//		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//		String[] titles = new String[] { "房产类型", "房产位置", "建筑面积", "市值", "资产数量","评估价值（元）","资产所属" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);
//
//		CellStyle style = workbook.createCellStyle();
//		DataFormat format = workbook.createDataFormat();
//		style.setDataFormat(format.getFormat("@"));
//		for (int i = 0; i < 5; i++) {
//			sheet.setDefaultColumnStyle(i, style);
//		}
//
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}

//	/**
//	 * 资料上传
//	 * 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public String uploadHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
//
//		Iterator<String> itr = multipartRequest.getFileNames();
//		MultipartFile multipartFile = null;
//
//		List<BorrowHouses> recordList = new ArrayList<BorrowHouses>();
//
//		while (itr.hasNext()) {
//			multipartFile = multipartRequest.getFile(itr.next());
//			String fileRealName = String.valueOf(new Date().getTime());
//			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
//
//			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());
//
//			if (hssfWorkbook != null) {
//				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
//					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//
//					// 循环行Row
//					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//						if (rowNum == 0) {
//							continue;
//						}
//						BorrowHouses record = new BorrowHouses();
//						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null && hssfRow.getCell(2) == null && hssfRow.getCell(3) == null && hssfRow.getCell(4) == null)) {
//							continue;
//						}
//						if (!(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(1))) 
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(2))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(3))) 
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(4))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(5))) 
//								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(6))))) {
//							// 房产类型
//							record.setHousesType(this.getValue(hssfRow.getCell(0)));
//							// 房产位置
//							record.setHousesLocation(this.getValue(hssfRow.getCell(1)));
//							// 建筑面积
//							record.setHousesArea(this.getValue(hssfRow.getCell(2)));
//							// 市值
//							record.setHousesPrice(this.getValue(hssfRow.getCell(3)));
//							// 资产数量
//							if(StringUtils.isNotEmpty(this.getValue(hssfRow.getCell(4)))){
//								record.setHousesCnt(Integer.valueOf(this.getValue(hssfRow.getCell(4))));
//							}else{
//								record.setHousesCnt(1);
//							}
//							// 抵押价值（元）
//							record.setHousesToprice(this.getValue(hssfRow.getCell(5)));
//							// 资产所属
//							record.setHousesBelong(this.getValue(hssfRow.getCell(6)));
//							recordList.add(record);
//						}
//					}
//				}
//			}
//		}
//		return JSONObject.toJSONString(recordList, true);
//	}

//	/**
//	 * 导出功能
//	 * 
//	 * @param request
//	 * @param modelAndView
//	 * @param form
//	 */
//	@Override
//	public void downloadAuthen(HttpServletRequest request, HttpServletResponse response, BorrowBean form) throws Exception {
//		// 表格sheet名称
//		String sheetName = "认证信息模板";
//
//		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//		String[] titles = new String[] { "展示顺序", "认证项目名称", "认证时间(例：2016-01-04)" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);
//
//		CellStyle style = workbook.createCellStyle();
//		DataFormat format = workbook.createDataFormat();
//		style.setDataFormat(format.getFormat("@"));
//		for (int i = 0; i < 3; i++) {
//			sheet.setDefaultColumnStyle(i, style);
//		}
//
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}
//
//	/**
//	 * 资料上传
//	 * 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public String uploadAuthen(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
//
//		Iterator<String> itr = multipartRequest.getFileNames();
//		MultipartFile multipartFile = null;
//
//		List<BorrowCommonCompanyAuthen> recordList = new ArrayList<BorrowCommonCompanyAuthen>();
//
//		while (itr.hasNext()) {
//			multipartFile = multipartRequest.getFile(itr.next());
//			String fileRealName = String.valueOf(new Date().getTime());
//			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
//
//			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());
//
//			if (hssfWorkbook != null) {
//				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
//					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//
//					// 循环行Row
//					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//						if (rowNum == 0) {
//							continue;
//						}
//						BorrowCommonCompanyAuthen record = new BorrowCommonCompanyAuthen();
//						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null && hssfRow.getCell(2) == null)) {
//							continue;
//						}
//						if (!(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(1))) && StringUtils
//								.isEmpty(this.getValue(hssfRow.getCell(2))))) {
//							// 展示顺序
//							record.setAuthenSortKey(this.getValue(hssfRow.getCell(0)));
//							// 认证项目名称
//							record.setAuthenName(this.getValue(hssfRow.getCell(1)));
//							// 认证时间
//							record.setAuthenTime(this.getValue(hssfRow.getCell(2)));
//
//							recordList.add(record);
//						}
//
//					}
//				}
//			}
//		}
//		return JSONObject.toJSONString(recordList, true);
//	}



	/**
	 * 获取对应借款对象
	 * 
	 * @param borrowBean
	 * @throws Exception
	 */
	@Override
	public com.hyjf.am.bean.admin.BorrowWithBLOBs getRecordById(BorrowCommonBean borrowBean) {
		String borrowNid = borrowBean.getBorrowNid();
		if (StringUtils.isNotEmpty(borrowNid)) {
			com.hyjf.am.bean.admin.BorrowWithBLOBs bwb=new com.hyjf.am.bean.admin.BorrowWithBLOBs();
			BeanUtils.copyProperties(this.getBorrowInfoByNid(borrowNid),bwb);
			BeanUtils.copyProperties(this.getBorrow(borrowNid),bwb);
			return  bwb;
		}
		
		return new com.hyjf.am.bean.admin.BorrowWithBLOBs();
	}
	
	
	/**
	 * 借款内容填充
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public String contentFill(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
//
//		Iterator<String> itr = multipartRequest.getFileNames();
//		MultipartFile multipartFile = null;
//
//		//返回结果
//		Map<String,String> resultMap = new HashMap<String,String>();
//
//		while (itr.hasNext()) {
//			multipartFile = multipartRequest.getFile(itr.next());
//			String fileRealName = String.valueOf(new Date().getTime());
//			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
//
//			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());
//
//			if (hssfWorkbook != null) {
//				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
//					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//
//					// 循环行Row
//					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//
//						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null)) {
//							continue;
//						}
//						if(StringUtils.isEmpty(this.getValue(hssfRow.getCell(1)))){
//							continue;
//						}
//						if(rowNum == 0){//借款人用户名
//							resultMap.put("username", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 1){//项目申请人
//							resultMap.put("applicant", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 2){//担保机构用户名
//							resultMap.put("repayOrgName", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 3){//项目标题
//							resultMap.put("projectName", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 4){//借款标题
//							resultMap.put("jkName", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 5){//借款金额
//							resultMap.put("account", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 6){//出借利率
//							resultMap.put("borrowApr", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 7){//融资用途
//							resultMap.put("financePurpose", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 8){//月薪收入
//							resultMap.put("monthlyIncome", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 9){//还款来源
//							resultMap.put("payment", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 10){//第一还款来源
//							resultMap.put("firstPayment", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 11){//第二还款来源
//							resultMap.put("secondPayment", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 12){//费用说明
//							resultMap.put("costIntrodution", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 13){//项目信息
//							resultMap.put("borrowContents", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 14){//财务状况
//							resultMap.put("fianceCondition", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 15){//车辆品牌
//							resultMap.put("brand", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 16){//型号
//							resultMap.put("model", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 17){//产地
//							resultMap.put("place", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 18){//购买价格
//							resultMap.put("price", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 19){//评估价（元）
//							resultMap.put("toprice", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 20){//车牌号
//							resultMap.put("number", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 21){//房产类型
//							resultMap.put("housesType", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 22){//建筑面积
//							resultMap.put("housesArea", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 23){//资产数量
//							resultMap.put("housesCnt", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 24){//评估价值（元）
//							resultMap.put("housesToprice", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 25){//资产所属
//							resultMap.put("housesBelong", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 26){//借款类型
//							resultMap.put("companyOrPersonal", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 27){//融资主体
//							resultMap.put("comName", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 28){//法人
//							resultMap.put("comLegalPerson", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 29){//注册地区
//							resultMap.put("comLocationCity", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 30){//主营业务
//							resultMap.put("comMainBusiness", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 31){//在平台逾期次数
//							resultMap.put("comOverdueTimes", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 32){//在平台逾期金额
//							resultMap.put("comOverdueAmount", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 33){//注册时间
//							resultMap.put("comRegTime", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 34){//统一社会信用代码
//							resultMap.put("comSocialCreditCode", this.getValue(hssfRow.getCell(1)));
//						}
//						
//						else if(rowNum == 35){//注册号
//							String a = this.getValue(hssfRow.getCell(1));
//							if(StringUtils.isNotEmpty(a)){
//								Integer b = Integer.parseInt(a.split("\\.")[0]);
//								resultMap.put("comRegistCode", b.toString());
//							}	
//						}
//						
//						else if(rowNum == 36){//注册资本
//							resultMap.put("comRegCaptial", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 37){//所属行业
//							resultMap.put("comUserIndustry", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 38){//涉诉情况
//							resultMap.put("comLitigation", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 39){//姓名
//							resultMap.put("manname", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 40){//身份证号
//							resultMap.put("cardNo", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 41){//年龄
//							resultMap.put("old", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 42){//岗位职业
//							resultMap.put("position", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 43){//性别
//							resultMap.put("sex", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 44){//婚姻状况
//							resultMap.put("merry", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 45){//工作城市
//							resultMap.put("location_c", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 46){//户籍地
//							resultMap.put("domicile", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 47){//在平台逾期次数
//							resultMap.put("overdueTimes", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 48){//在平台逾期金额
//							resultMap.put("overdueAmount", this.getValue(hssfRow.getCell(1)));
//						}else if(rowNum == 49){//涉诉情况
//							resultMap.put("litigation", this.getValue(hssfRow.getCell(1)));
//						}
//						// 信批需求新增
//						else if(rowNum == 50){//年收入
//							resultMap.put("annualIncome", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 51){//征信报告逾期情况
//							resultMap.put("overdueReport", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 52){//重大负债状况
//							resultMap.put("debtSituation", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 53){//其他平台借款情况
//							resultMap.put("otherBorrowed", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 54){//(个人)借款资金运用情况
//							resultMap.put("isFunds", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 55){//(个人)借款人经营状况及财务状况
//							resultMap.put("isManaged", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 56){//(个人)借款人还款能力变化情况
//							resultMap.put("isAbility", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 57){//(个人)借款人逾期情况
//							resultMap.put("isOverdue", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 58){//(个人)借款人涉诉情况
//							resultMap.put("isComplaint", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 59){//(个人)借款人受行政处罚情况
//							resultMap.put("isPunished", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 60){//(企业)征信报告逾期情况
//							resultMap.put("comOverdueReport", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 61){//(企业)重大负债状况
//							resultMap.put("comDebtSituation", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 62){//(企业)其他平台借款情况
//							resultMap.put("comOtherBorrowed", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 63){//(企业)借款资金运用情况
//							resultMap.put("comIsFunds", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 64){//(企业)借款人经营状况及财务状况
//							resultMap.put("comIsManaged", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 65){//(企业)借款人还款能力变化情况
//							resultMap.put("comIsAbility", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 66){//(企业)借款人逾期情况
//							resultMap.put("comIsOverdue", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 67){//(企业)借款人涉诉情况
//							resultMap.put("comIsComplaint", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 68){//(企业)借款人受行政处罚情况
//							resultMap.put("comIsPunished", this.getValue(hssfRow.getCell(1)));
//						}
//						/** 原企业勾选内容改上传 start */
//						else if(rowNum == 69){//(企业)企业证件
//							resultMap.put("comIsCertificate", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 70){//(企业)经营状况
//							resultMap.put("comIsOperation", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 71){//(企业)财务状况
//							resultMap.put("comIsFinance", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 72){//(企业)企业信用
//							resultMap.put("comIsEnterpriseCreidt", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 73){//(企业)法人信息
//							resultMap.put("comIsLegalPerson", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 74){//(企业)资产状况
//							resultMap.put("comIsAsset", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 75){//(企业)购销合同
//							resultMap.put("comIsPurchaseContract", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 76){//(企业)购销合同
//							resultMap.put("comIsSupplyContract", this.getValue(hssfRow.getCell(1)));
//						}
//						/** 原企业勾选内容改上传 end */
//						/** 原个人勾选内容改上传 start */
//						else if(rowNum == 77){//(个人)身份证
//							resultMap.put("isCard", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 78){//(个人)收入状况
//							resultMap.put("isIncome", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 79){//(个人)信用状况
//							resultMap.put("isCredit", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 80){//(个人)资产状况
//							resultMap.put("isAsset", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 81){//(个人)车辆状况
//							resultMap.put("isVehicle", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 82){//(个人)行驶证
//							resultMap.put("isDrivingLicense", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 83){//(个人)车辆登记证
//							resultMap.put("isVehicleRegistration", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 84){//(个人)婚姻状况
//							resultMap.put("isMerry", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 85){//(个人)工作状况
//							resultMap.put("isWork", this.getValue(hssfRow.getCell(1)));
//						}
//						else if(rowNum == 86){//(个人)户口本
//							resultMap.put("isAccountBook", this.getValue(hssfRow.getCell(1)));
//						}	
//						/** 原个人勾选内容改上传 end */
//					}
//				}
//			}
//		}
//		return JSONObject.toJSONString(resultMap, true);
//	}

//	
//	/**
//	 * 下载内容填充模板
//	 * 
//	 * @param request
//	 * @param modelAndView
//	 * @param form
//	 */
//	public void downloadContentFill(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		// 表格sheet名称
//		String sheetName = "借款内容填充模板";
//
//		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//		String[] titles = new String[] { "" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);
//
//		int rowNum = 0;
//		for (int i = 0; i < 87; i++) {
//			// 新建一行
//			Row row = sheet.createRow(rowNum);
//
//			// 循环数据
//			for (int celLength = 0; celLength < titles.length; celLength++) {
//				// 创建相应的单元格
//				Cell cell = row.createCell(celLength);
//				if(rowNum == 0){//借款人用户名
//					if (celLength == 0) {
//						cell.setCellValue("借款方用户名");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 1){//项目申请人
//					if (celLength == 0) {
//						cell.setCellValue("项目申请人");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 2){//担保机构用户名
//					if (celLength == 0) {
//						cell.setCellValue("担保机构用户名");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 3){//项目标题
//					if (celLength == 0) {
//						cell.setCellValue("项目标题");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 4){//借款标题
//					if (celLength == 0) {
//						cell.setCellValue("借款标题");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 5){//借款金额
//					if (celLength == 0) {
//						cell.setCellValue("借款金额");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 6){//出借利率
//					if (celLength == 0) {
//						cell.setCellValue("出借利率");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 7){//融资用途
//					if (celLength == 0) {
//						cell.setCellValue("融资用途");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 8){//月薪收入
//					if (celLength == 0) {
//						cell.setCellValue("月薪收入");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 9){//还款来源
//					if (celLength == 0) {
//						cell.setCellValue("还款来源");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 10){//第一还款来源
//					if (celLength == 0) {
//						cell.setCellValue("第一还款来源");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 11){//第二还款来源
//					if (celLength == 0) {
//						cell.setCellValue("第二还款来源");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 12){//费用说明
//					if (celLength == 0) {
//						cell.setCellValue("费用说明");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 13){//项目信息
//					if (celLength == 0) {
//						cell.setCellValue("项目信息");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 14){//财务状况
//					if (celLength == 0) {
//						cell.setCellValue("财务状况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 15){//车辆品牌
//					if (celLength == 0) {
//						cell.setCellValue("车辆品牌");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 16){//型号
//					if (celLength == 0) {
//						cell.setCellValue("型号");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 17){//产地
//					if (celLength == 0) {
//						cell.setCellValue("产地");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 18){//购买价格
//					if (celLength == 0) {
//						cell.setCellValue("购买价格");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 19){//评估价（元）
//					if (celLength == 0) {
//						cell.setCellValue("评估价（元）");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 20){//车牌号
//					if (celLength == 0) {
//						cell.setCellValue("车牌号");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 21){//房产类型
//					if (celLength == 0) {
//						cell.setCellValue("房产类型(住宅|房产|土地|工业用房|工业用地)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 22){//建筑面积
//					if (celLength == 0) {
//						cell.setCellValue("建筑面积");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 23){//资产数量
//					if (celLength == 0) {
//						cell.setCellValue("资产数量");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 24){//评估价值（元）
//					if (celLength == 0) {
//						cell.setCellValue("评估价值（元）");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 25){//资产所属
//					if (celLength == 0) {
//						cell.setCellValue("资产所属");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 26){//借款类型
//					if (celLength == 0) {
//						cell.setCellValue("借款类型（个人|企业）");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 27){//融资主体
//					if (celLength == 0) {
//						cell.setCellValue("融资主体");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 28){//法人
//					if (celLength == 0) {
//						cell.setCellValue("法人");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 29){//注册地区
//					if (celLength == 0) {
//						cell.setCellValue("注册地区");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 30){//主营业务
//					if (celLength == 0) {
//						cell.setCellValue("主营业务");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 31){//在平台逾期次数
//					if (celLength == 0) {
//						cell.setCellValue("在平台逾期次数");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 32){//在平台逾期金额
//					if (celLength == 0) {
//						cell.setCellValue("在平台逾期金额");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 33){//注册时间
//					if (celLength == 0) {
//						cell.setCellValue("注册时间");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 34){//统一社会信用代码
//					if (celLength == 0) {
//						cell.setCellValue("统一社会信用代码");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 35){//注册号
//					if (celLength == 0) {
//						cell.setCellValue("注册号");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 36){//注册资本
//					if (celLength == 0) {
//						cell.setCellValue("注册资本");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 37){//所属行业
//					if (celLength == 0) {
//						cell.setCellValue("所属行业");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 38){//涉诉情况
//					if (celLength == 0) {
//						cell.setCellValue("涉诉情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 39){//姓名
//					if (celLength == 0) {
//						cell.setCellValue("姓名");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 40){//身份证号
//					if (celLength == 0) {
//						cell.setCellValue("身份证号");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 41){//年龄
//					if (celLength == 0) {
//						cell.setCellValue("年龄");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 42){//岗位职业
//					if (celLength == 0) {
//						cell.setCellValue("岗位职业");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 43){//性别
//					if (celLength == 0) {
//						cell.setCellValue("性别(男|女)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 44){//婚姻状况
//					if (celLength == 0) {
//						cell.setCellValue("婚姻状况(已婚|未婚)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 45){//工作城市
//					if (celLength == 0) {
//						cell.setCellValue("工作城市");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 46){//户籍地
//					if (celLength == 0) {
//						cell.setCellValue("户籍地");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 47){//在平台逾期次数
//					if (celLength == 0) {
//						cell.setCellValue("在平台逾期次数");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 48){//在平台逾期金额
//					if (celLength == 0) {
//						cell.setCellValue("在平台逾期金额");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}else if(rowNum == 49){//涉诉情况
//					if (celLength == 0) {
//						cell.setCellValue("涉诉情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				// 信批需求新增 start
//				else if(rowNum == 50){//年收入
//					if (celLength == 0) {
//						cell.setCellValue("(个人)年收入");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 51){//征信报告逾期情况：暂未提供；无；已处理
//					if (celLength == 0) {
//						cell.setCellValue("(个人)征信报告逾期情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 52){//重大负债状况：无
//					if (celLength == 0) {
//						cell.setCellValue("(个人)重大负债状况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 53){//其他平台借款情况：无；暂未提供
//					if (celLength == 0) {
//						cell.setCellValue("(个人)其他平台借款情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 54){//借款资金运用情况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)借款资金运用情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 55){//借款人经营状况及财务状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)借款方经营状况及财务状况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 56){//借款人还款能力变化情况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)借款方还款能力变化情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 57){//借款人逾期情况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)借款方逾期情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 58){//借款人涉诉情况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)借款方涉诉情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 59){//借款人受行政处罚情况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)借款方受行政处罚情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 60){//征信报告逾期情况：暂未提供；无；已处理
//					if (celLength == 0) {
//						cell.setCellValue("(企业)征信报告逾期情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 61){//重大负债状况：无
//					if (celLength == 0) {
//						cell.setCellValue("(企业)重大负债状况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 62){//其他平台借款情况：无；暂未提供
//					if (celLength == 0) {
//						cell.setCellValue("(企业)其他平台借款情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 63){//借款资金运用情况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)借款资金运用情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 64){//借款人经营状况及财务状况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)借款方经营状况及财务状况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 65){//借款人还款能力变化情况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)借款方还款能力变化情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 66){//借款人逾期情况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)借款方逾期情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 67){//借款人涉诉情况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)借款方涉诉情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				
//				else if(rowNum == 68){//借款人受行政处罚情况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)借款方受行政处罚情况");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				/**---------企业勾选--------- */
//				else if(rowNum == 69){//企业证件
//					if (celLength == 0) {
//						cell.setCellValue("(企业)证件(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 70){//经营状况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)经营状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 71){//财务状况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)财务状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 72){//企业信用
//					if (celLength == 0) {
//						cell.setCellValue("(企业)企业信用(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 73){//法人信息
//					if (celLength == 0) {
//						cell.setCellValue("(企业)法人信息(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 74){//资产状况
//					if (celLength == 0) {
//						cell.setCellValue("(企业)资产状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 75){//购销合同
//					if (celLength == 0) {
//						cell.setCellValue("(企业)购销合同(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 76){//供销合同
//					if (celLength == 0) {
//						cell.setCellValue("(企业)供销合同(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				/**---------企业勾选--------- */
//				/**---------个人勾选--------- */
//				else if(rowNum == 77){//身份证
//					if (celLength == 0) {
//						cell.setCellValue("(个人)身份证(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 78){//收入状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)收入状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 79){//信用状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)信用状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 80){//资产状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)资产状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 81){//车辆状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)车辆状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 82){//行驶证
//					if (celLength == 0) {
//						cell.setCellValue("(个人)行驶证(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 83){//车辆登记证
//					if (celLength == 0) {
//						cell.setCellValue("(个人)车辆登记证(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 84){//婚姻状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)婚姻状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 85){//工作状况
//					if (celLength == 0) {
//						cell.setCellValue("(个人)工作状况(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//				else if(rowNum == 86){//户口本
//					if (celLength == 0) {
//						cell.setCellValue("(个人)户口本(0未审核 1已审核)");
//					}else if (celLength == 1) {
//						cell.setCellValue("");
//					}
//				}
//			}
//			//行++
//			rowNum++;
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}

	@Override
	public String getXJDBorrowPreNid() {
		String yyyymm = GetDate.getServerDateTime(13, new Date());
		String mmdd = yyyymm.substring(2);
		String borrowPreNid = this.borrowCustomizeMapper.getXJDBorrowPreNid(mmdd);
		if (StringUtils.isEmpty(borrowPreNid)) {
			return mmdd + "0000001";
		}
		if (borrowPreNid.length() == 14) {
			return mmdd + "0000001";
		}
		return String.valueOf(Long.valueOf(borrowPreNid) + 1);
	}

	/**
	 * 获取机构资产列表
	 * @return
	 */
	@Override
	public List<HjhInstConfig> getInstList() {
		HjhInstConfigExample example = new HjhInstConfigExample();
//		HjhInstConfigExample.Criteria cra = example.createCriteria();
//		cra.andDelFlgEqualTo(0);
		return this.hjhInstConfigMapper.selectByExample(example);
	}

	/**
	 * 根据机构资产编号,产品类型查询产品名称
	 *
	 * @param instCode
	 * @param productType
	 * @return
	 */
	private String getAssetTypeName(String instCode, Integer productType) {
		String productName = null;
		HjhAssetTypeExample example = new HjhAssetTypeExample();
		HjhAssetTypeExample.Criteria cra = example.createCriteria();
		cra.andInstCodeEqualTo(instCode);
		cra.andAssetTypeEqualTo(productType);
		List<HjhAssetType> list = this.hjhAssetTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			HjhAssetType hjhAssetType = list.get(0);
			productName = hjhAssetType.getAssetTypeName();
		}
		return productName;
	}

//	/**
//	 * 受托用户是否存在
//	 *
//	 * @param username
//	 * @return
//	 */
//	@Override
//	public String isEntrustedExistsUser(HttpServletRequest request) {
//		JSONObject ret = new JSONObject();
//		String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//		message = message.replace("{label}", "借款金额");
//
//		String param = request.getParameter("param");
//		if (StringUtils.isEmpty(param)) {
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		int usersFlag = this.isEntrustedExistsUser(param);
//		if (usersFlag == 1) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.exist", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 2) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.accounts");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 3) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.uses");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 4) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.not.in");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		} else if (usersFlag == 6) {
//			message = ValidatorFieldCheckUtil.getErrorMessage("username.is.disable");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//
//		return ret.toJSONString();
//	}

	/**
	 * 校验受托用户名
	 *
	 * @param userName
	 * @return
	 * @author Administrator
	 */
	@Override
	public int isEntrustedExistsUser(String userName) {
		if (StringUtils.isNotEmpty(userName)) {
//			UserExample example = new UserExample();
//			UserExample.Criteria cra = example.createCriteria();
//			cra.andUsernameEqualTo(userName);
//			List<User> userList = this.userMapper.selectByExample(example);
//			UserExample example = new UsersExample();
//			UsersExample.Criteria cra = example.createCriteria();
//			cra.andUsernameEqualTo(userName);
//			 RUser user = this.getRUser(userName);
//			if (user == null ) {
//				// 借款人用户名不存在。
//				return 1;
//			}
//			 Account openAccount = this.getAccount(user.getUserId());
//			if (Validator.isNull(openAccount)) {
//				// 借款人用户名必须已在银行开户
//				return 2;
//			}
//			if (users.getStatus() != 0) {
//				// 借款人用户名已经被禁用
//				return 3;
//			}
			//TO DO 添加一个是否存在于配置表的校验，返回  4
			StzhWhiteListExample example1 = new StzhWhiteListExample();
			StzhWhiteListExample.Criteria cra1 = example1.createCriteria();
			// 受托用户名需要存在于白名单的受托用户中
			cra1.andStUserNameEqualTo(userName);
//			cra1.andDelFlgEqualTo(0);
			List<StzhWhiteList> whiteList = this.sTZHWhiteListMapper.selectByExample(example1);
			if (whiteList == null || whiteList.size() == 0) {
				// 借款人用户名不存在。
				return 4;
			}
			if(whiteList.get(0).getState() == 0){//状态 1启用  0禁用
				return 6;
			}
		}
		return 0;
	}

	/**
	 * 根据原始标的号拉取标的信息判断是否发送自动备案MQ消息队列
	 *
	 * @param borrowPreNid
	 */
	@Override
	public void isAutoRecord(String borrowPreNid) {
		// 根据借款预编号获取标的编号（拆标的可获取N个borrowNid）
		BorrowInfoExample example = new BorrowInfoExample();
		BorrowInfoExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowPreNidEqualTo(borrowPreNid);

		List<BorrowInfo> list = borrowInfoMapper.selectByExample(example);
		// 未拉取到数据返回
		if (CollectionUtils.isEmpty(list)) {
			logger.error("判断是否自动备案发送MQ拉取标的信息失败！");
			return;
		}

		// 根据borrowNid判断该标的组是否需要自动备案
		HjhAssetBorrowtype hjhAssetBorrowType = this.selectAssetBorrowType(list.get(0).getBorrowNid());
		if (null != hjhAssetBorrowType && null != hjhAssetBorrowType.getAutoRecord() && hjhAssetBorrowType.getAutoRecord() == 1) {
			// 遍历borrowNid
			for (BorrowInfo borrowInfo : list) {
                logger.info(borrowInfo.getBorrowNid()+" 发送自动备案消息到MQ ");
                try {
                    JSONObject params = new JSONObject();
                    params.put("borrowNid", borrowInfo.getBorrowNid());
                    params.put("instCode", borrowInfo.getInstCode());
					commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_RECORD_TOPIC,
							MQConstant.AUTO_BORROW_RECORD_ADMIN_TAG, borrowInfo.getBorrowNid(), params));
                } catch (MQException e) {
                    logger.error("发送【自动备案消息到MQ】MQ失败...");
                }
				logger.info("标的编号：" + borrowInfo.getBorrowNid()+ " 已发送到自动备案消息队列！");
			}
		}
	}

	/**
	 * 获取风险测评配置
	 *
	 * @return
	 */
	@Override
	public EvaluationConfig selectEvaluationConfig() {
		EvaluationConfigExample example = new EvaluationConfigExample();
		EvaluationConfigExample.Criteria cra = example.createCriteria();
		List<EvaluationConfig> list = this.evaluationConfigMapper.selectByExample(example);
		if (list != null && list.size() >0 ){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 校验受托用户名
	 *
	 * @param instCode
	 * @param userName
	 * @return
	 * @author Administrator
	 */
	public int isEntrustedExistsUserCheck(String instCode,String userName) {
		if (StringUtils.isNotEmpty(userName)) {
//			UsersExample example = new UsersExample();
//			UsersExample.Criteria cra = example.createCriteria();
//			cra.andUsernameEqualTo(userName);
//			List<Users> userList = this.usersMapper.selectByExample(example);
			 RUser user = this.getRUser(userName);
			if (user == null ) {
				// 借款人用户名不存在。
				return 1;
			}
//			Users users = userList.get(0);
			 Account openAccount = this.getAccount(user.getUserId());
			if (Validator.isNull(openAccount)) {
				// 借款人用户名必须已在银行开户
				return 2;
			}
//			if (users.getStatus() != 0) {
//				// 借款人用户名已经被禁用
//				return 3;
//			}
			//TO DO 添加一个是否存在于配置表的校验，返回  4
			StzhWhiteListExample example1 = new StzhWhiteListExample();
			StzhWhiteListExample.Criteria cra1 = example1.createCriteria();
			// 受托用户名需要存在于白名单的受托用户中
			cra1.andStUserNameEqualTo(userName);
			cra1.andStateEqualTo(1);
//			cra1.andDelFlgEqualTo(0);
			List<StzhWhiteList> whiteList = this.sTZHWhiteListMapper.selectByExample(example1);
			if (whiteList == null || whiteList.size() == 0) {
				// 借款人用户名不存在。
				return 4;
			}
			if(!whiteList.get(0).getInstCode().equals(instCode)){
				return 5;
			}
		}
		return 0;
	}

	@Override
	public Integer isEngineUsed(String borrowNid) {
		BorrowExample borrowExample = new BorrowExample();
		BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
		if (StringUtils.isNotEmpty(borrowNid)) {
			borrowCra.andBorrowNidEqualTo(borrowNid);
		}
		borrowCra.andIsEngineUsedEqualTo(1);//0:否 , 1：是
		List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);
		if (borrowList != null && borrowList.size() > 0) {
			return borrowList.size();//要么检出一条数据 返回 1，要么没有检出 返回 0
		}
		return 0;
	}

//	/**
//     * 根据项目状态获取项目状态名称
//     *
//     * @param instCode
//     * @param productType
//     * @return
//     */
//    private String getBorrowStatusName(String borrowStauts) {
//        String StatusName = null;
//     // 项目状态
//        ParamNameExample example = new ParamNameExample();
//        ParamNameExample.Criteria cra = example.createCriteria();
//        cra.andNameClassEqualTo(CustomConstants.BORROW_STATUS);
//        cra.andDelFlagEqualTo(CustomConstants.FLAG_NORMAL);
//        example.setOrderByClause(" sort ASC ");
//       
//        List<ParamName> borrowStatusList = this.paramNameMapper.selectByExample(example);
//        if (borrowStatusList != null && borrowStatusList.size() > 0) {
//            for (ParamName paramName : borrowStatusList) {
//                if(borrowStauts.equals(paramName.getNameCd())){
//                    StatusName = paramName.getName();
//                    break;
//                }
//            }
//
//        }
//        return StatusName;
//    }
//    
   
	/**
	 * 获取逾期利率(汇计划用)
	 * @param projectType
	 * @param borrowStyle
	 * @param instCode
	 * @param borrowPeriod
	 * @return
	 */
    public String getLateInterestRate(Integer projectType, String borrowStyle, String instCode,Integer borrowPeriod) {
        String lateInterestRate = "";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("borrowStyle",borrowStyle);
        params.put("borrowPeriod",borrowPeriod);
        params.put("instCode",instCode);
        params.put("assetType",projectType);
        params.put("projectType",projectType);
        lateInterestRate = borrowFullCustomizeMapper.selectLateInterestRateByParams(params);
        return lateInterestRate;
    }
    
	/**
	 * 逾期免息天数(汇计划用)
	 * @param projectType
	 * @param borrowStyle
	 * @param instCode
	 * @param borrowPeriod
	 * @return
	 */
    public String getLateFreeDays(Integer projectType, String borrowStyle, String instCode,Integer borrowPeriod) {
        String lateFreeDays = "";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("borrowStyle",borrowStyle);
        params.put("borrowPeriod",borrowPeriod);
        params.put("instCode",instCode);
        params.put("assetType",projectType);
        params.put("projectType",projectType);
        lateFreeDays = borrowFullCustomizeMapper.selectLateFreeDaysByParams(params);
        return lateFreeDays;
    }


	/**
	 * 根据借款主体,社会统一信用代码查询用户是否做过CA认证
	 * @param comName
	 * @param name
	 * @return
	 */
//	@Override
//	public String isBorrowUserCACheck(String comName,String name) {
//		JSONObject ret = new JSONObject();
//		// 借款主体为空
//		if(StringUtils.isBlank(comName)){
//			String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//			if ("comName".equals(name)) {
//				message = message.replace("{label}", "借款主体");
//			}else if ("manname".equals(name)){
//				message = message.replace("{label}", "姓名");
//			}
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = null;
//		LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
//		LoanSubjectCertificateAuthorityExample.Criteria cra = example.createCriteria();
//		cra.andNameEqualTo(comName);
//		List<LoanSubjectCertificateAuthority> loanSubjectlist = this.loanSubjectCertificateAuthorityMapper.selectByExample(example);
//		if (loanSubjectlist != null && loanSubjectlist.size()>0){
//			loanSubjectCertificateAuthority  = loanSubjectlist.get(0);
//		}
//		CertificateAuthorityExample caExample = new CertificateAuthorityExample();
//		CertificateAuthorityExample.Criteria caCra = caExample.createCriteria();
//		caCra.andTrueNameEqualTo(comName);
//		List<CertificateAuthority> caList = this.certificateAuthorityMapper.selectByExample(caExample);
//		CertificateAuthority certificateAuthority = null;
//		if(caList!=null && caList.size() >0 ){
//			certificateAuthority = caList.get(0);
//		}
//		if (certificateAuthority == null && loanSubjectCertificateAuthority == null){
//			String message = ValidatorFieldCheckUtil.getErrorMessage("user.not.certificate.authority", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//		return ret.toJSONString();
//	}

	/**
	 * 根据社会统一信用代码或身份证号查询用户是否做过CA认证
	 * @param idNo
	 * @param name
	 * @return
	 */
//	@Override
//	public String isCAIdNoCheck(String idNo, String name) {
//		JSONObject ret = new JSONObject();
//		if(StringUtils.isBlank(idNo)){
//			String message = ValidatorFieldCheckUtil.getErrorMessage("required", "");
//			if ("comSocialCreditCode".equals(name)) {
//				message = message.replace("{label}", "社会统一信用代码");
//			}else if ("cardNo".equals(name)){
//				message = message.replace("{label}", "身份证号");
//			}
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
//		LoanSubjectCertificateAuthorityExample.Criteria cra = example.createCriteria();
//		cra.andIdNoEqualTo(idNo);
//		List<LoanSubjectCertificateAuthority> loanSubjectlist = this.loanSubjectCertificateAuthorityMapper.selectByExample(example);
//
//		CertificateAuthorityExample caExample = new CertificateAuthorityExample();
//		CertificateAuthorityExample.Criteria caCra = caExample.createCriteria();
//		caCra.andIdNoEqualTo(idNo);
//		List<CertificateAuthority> caList = this.certificateAuthorityMapper.selectByExample(caExample);
//
//		LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = null;
//		if(loanSubjectlist!=null && loanSubjectlist.size()>0){
//			loanSubjectCertificateAuthority = loanSubjectlist.get(0);
//		}
//		CertificateAuthority certificateAuthority = null;
//		if (caList != null && caList.size()>0){
//			certificateAuthority = caList.get(0);
//		}
//		if (loanSubjectCertificateAuthority == null && certificateAuthority == null ){
//			String message = ValidatorFieldCheckUtil.getErrorMessage("certificate.authority.idno.not.exist", "");
//			ret.put(AdminDefine.JSON_VALID_INFO_KEY, message);
//			return ret.toString();
//		}
//		ret.put(AdminDefine.JSON_VALID_STATUS_KEY, AdminDefine.JSON_VALID_STATUS_OK);
//		return ret.toJSONString();
//	}

	/**
	 * 根据企业名称,社会统一信用代码查询用户是否做过CA认证
	 * @param 
	 * @param 
	 * @return
	 */
//	private boolean companyCAFlag(String comName, String comSocialCreditCode) {
//		// 借款主体CA认证记录表
//		LoanSubjectCertificateAuthority loanSubjectCA = null;
//		LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
//		LoanSubjectCertificateAuthorityExample.Criteria cra = example.createCriteria();
//		cra.andIdNoEqualTo(comSocialCreditCode);
//		cra.andIdTypeEqualTo(1);
//		cra.andNameEqualTo(comName);
//		List<LoanSubjectCertificateAuthority> list = this.loanSubjectCertificateAuthorityMapper.selectByExample(example);
//		if (list != null && list.size() > 0) {
//			loanSubjectCA = list.get(0);
//		}
//		// 用户CA记录表
//		CertificateAuthorityExample caExample = new CertificateAuthorityExample();
//		CertificateAuthorityExample.Criteria ca = caExample.createCriteria();
//		ca.andIdNoEqualTo(comSocialCreditCode);
//		ca.andTrueNameEqualTo(comName);
//		ca.andIdTypeEqualTo(1);
//
//		CertificateAuthority certificateAuthority = null;
//		List<CertificateAuthority> caList = this.certificateAuthorityMapper.selectByExample(caExample);
//		if (caList != null && caList.size() > 0) {
//			certificateAuthority = caList.get(0);
//		}
//		// 借款主体和CA认证记录不为空
//		if (loanSubjectCA != null && certificateAuthority != null) {
//			if (StringUtils.isBlank(loanSubjectCA.getCustomerId()) || StringUtils.isBlank(certificateAuthority.getCustomerId())) {
//				_log.info("用户未做CA认证");
//				return false;
//			}
//			// 客户编号不一致
//			if (!loanSubjectCA.getCustomerId().equals(certificateAuthority.getCustomerId())) {
//				_log.info("用户CA认证客户编号不一致.");
//				return false;
//			}
//			return true;
//		}
//		// 如果一张表有数据
//		if (loanSubjectCA != null) {
//			if (StringUtils.isBlank(loanSubjectCA.getCustomerId())) {
//				_log.info("用户未做CA认证");
//				return false;
//			}
//			return true;
//		}
//		// 如果CA记录表有数据
//		if (certificateAuthority != null) {
//			if (StringUtils.isBlank(certificateAuthority.getCustomerId())) {
//				_log.info("用户未做CA认证");
//				return false;
//			}
//			return true;
//		}
//		return false;
//	}

//	/**
//	 * 根据姓名,身份证号查询用户是否做过CA认证
//	 * @param manname
//	 * @param cardNo
//	 * @return
//	 */
//	private boolean isPersonCAFlag(String manname, String cardNo) {
//
//		LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = null;
//		LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
//		LoanSubjectCertificateAuthorityExample.Criteria cra = example.createCriteria();
//		cra.andIdTypeEqualTo(0);
//		cra.andIdNoEqualTo(cardNo);
//		cra.andNameEqualTo(manname);
//		List<LoanSubjectCertificateAuthority> list = this.loanSubjectCertificateAuthorityMapper.selectByExample(example);
//		if (list != null && list.size() > 0 ){
//			loanSubjectCertificateAuthority = list.get(0);
//		}
//
//		CertificateAuthority certificateAuthority = null;
//
//		CertificateAuthorityExample certificateAuthorityExample = new CertificateAuthorityExample();
//		CertificateAuthorityExample.Criteria ca = certificateAuthorityExample.createCriteria();
//		ca.andTrueNameEqualTo(manname);
//		ca.andIdTypeEqualTo(0);
//		ca.andIdNoEqualTo(cardNo);
//		List<CertificateAuthority> resultList = this.certificateAuthorityMapper.selectByExample(certificateAuthorityExample);
//
//		if (resultList !=null && resultList.size()>0){
//			certificateAuthority = resultList.get(0);
//		}
//
//		// 借款主体和CA认证记录不为空
//		if (loanSubjectCertificateAuthority != null && certificateAuthority != null) {
//			if (StringUtils.isBlank(loanSubjectCertificateAuthority.getCustomerId()) || StringUtils.isBlank(certificateAuthority.getCustomerId())) {
//				_log.info("用户未做CA认证");
//				return false;
//			}
//			// 客户编号不一致
//			if (!loanSubjectCertificateAuthority.getCustomerId().equals(certificateAuthority.getCustomerId())) {
//				_log.info("用户CA认证客户编号不一致.");
//				return false;
//			}
//			return true;
//		}
//		// 如果一张表有数据
//		if (loanSubjectCertificateAuthority != null) {
//			if (StringUtils.isBlank(loanSubjectCertificateAuthority.getCustomerId())) {
//				_log.info("用户未做CA认证");
//				return false;
//			}
//			return true;
//		}
//		// 如果CA记录表有数据
//		if (certificateAuthority != null) {
//			if (StringUtils.isBlank(certificateAuthority.getCustomerId())) {
//				_log.info("用户未做CA认证");
//				return false;
//			}
//			return true;
//		}
//		return false;
//	}
	@Override
	public List<HjhInstConfig> hjhInstConfigList(String instCode) {
		HjhInstConfigExample example = new HjhInstConfigExample();
		HjhInstConfigExample.Criteria cra = example.createCriteria();
//		cra.andDelFlgEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
		if (StringUtils.isNotEmpty(instCode)) {
			cra.andInstCodeEqualTo(instCode);
		}
		return this.hjhInstConfigMapper.selectByExample(example);
	}
	/**
	 * 获取系统配置
	 *
	 * @return
	 */
	@Override
    public String getBorrowConfig(String configCd) {
		BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
		return borrowConfig.getConfigValue();
	}
	/**
	 * 根据资金来源取得产品类型
	 * @param instCode
	 * @return
	 * @author LiuBin
	 */
	@Override
	public List<HjhAssetType> hjhAssetTypeList(String instCode) {
		HjhAssetTypeExample example = new HjhAssetTypeExample();
		HjhAssetTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(0);
		if (StringUtils.isEmpty(instCode)) {
			return null;
		}
		cra.andInstCodeEqualTo(instCode);
		return this.hjhAssetTypeMapper.selectByExample(example);
	}


	/**
	 * 根据项目类型查询项目配置信息
	 *
	 * @param projectType
	 * @return
	 */
	private BorrowProjectType getBrrowProjectTpyeByProjectType(String projectType) {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		BorrowProjectTypeExample.Criteria cra = example.createCriteria();
		cra.andBorrowCdEqualTo(Integer.valueOf(projectType));
		List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	   /**
     * 根据项目编号获取该机构的审核配置
     *
     * @param borrowNid
     * @return
     */
    public HjhAssetBorrowtype selectAssetBorrowType(String borrowNid) {
        BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        example.createCriteria().andInstCodeEqualTo(borrowInfo.getInstCode()).andAssetTypeEqualTo(borrowInfo.getAssetType());
        List<HjhAssetBorrowtype> list = this.hjhAssetBorrowtypeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

	/**
	 * wbs标的信息推送MQ
	 *
	 * @param borrowNid
	 * @param productStatus
	 * @param productType
	 */
	private void sendWbsBorrowInfo(String borrowNid, String productStatus, Integer productType) throws MQException {
		JSONObject params = new JSONObject();
		// 产品编号
		params.put("productNo", borrowNid);
		// 产品状态
		params.put("productStatus", productStatus);
		// 产品类型 0 散标类, 1 计划类
		params.put("productType", productType);
		commonProducer.messageSend(new MessageContent(MQConstant.WBS_BORROW_INFO_TOPIC, MQConstant.WBS_BORROW_INFO_TAG, UUID.randomUUID().toString(), params));
	}


	/**
	 *
	 * 如果是温金投定向发标标的的话,发送更新借款人机构编号的MQ
	 *
	 * @param borrowUserId
	 */
	private void sendBorrowUserMQ(Integer borrowUserId) throws MQException {
		JSONObject params = new JSONObject();
		params.put("userId", borrowUserId);
		// 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
		commonProducer.messageSendDelay(new MessageContent(MQConstant.WJT_BORROW_USER_MODIFY_TOPIC, MQConstant.WJT_BORROW_USER_MODIFY_GROUP, params), 2);
	}
}