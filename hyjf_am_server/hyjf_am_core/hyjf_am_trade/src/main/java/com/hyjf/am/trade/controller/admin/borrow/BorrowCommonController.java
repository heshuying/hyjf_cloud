package com.hyjf.am.trade.controller.admin.borrow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.response.Response;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhLabelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.trade.bean.BorrowWithBLOBs;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.borrow.BorrowCommonService;
import com.hyjf.am.trade.service.front.config.InstConfigService;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonNameAccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author GOGTZ-Z
 * @version V1.0  
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @date 2015/07/09 17:00
 */
@Api(value = "借款增加")
@RestController
@RequestMapping("/am-trade/borrowcommon")
public class BorrowCommonController extends BaseController {

	// 机构属性 1-投资机构 0-借款机构
	private final int TENDER_INST_TYPE = 1;

	@Autowired
	private BorrowCommonService borrowCommonService;

	@Autowired
	private InstConfigService instConfigService;

	@Autowired
	private AdminHjhLabelService adminHjhLabelService;

	/**
     * 迁移到详细画面
     *
     * @param borrowCommonRequest
     * @return
     */
	@ApiOperation(value = "查询信息")
	@RequestMapping("/infoAction")
    public BorrowCommonResponse moveToInfoAction(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
		BorrowCommonResponse bcr=new BorrowCommonResponse();
	      BorrowCommonBean form=new BorrowCommonBean();
	      BeanUtils.copyProperties(borrowCommonRequest, form);
        // 项目类型
        bcr.setBorrowProjectType(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectTypeList(CustomConstants.HZT),BorrowProjectTypeVO.class));


        // 还款方式
  //      List<BorrowProjectRepay> borrowStyleList = this.borrowCommonService.borrowProjectRepayList();
        bcr.setBorrowStyleList(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectRepayList(),BorrowProjectRepayVO.class));
//        //TODO 房屋类型 放在admin层取
//        modelAndView.addObject("housesTypeList", this.borrowCommonService.getParamNameList(CustomConstants.HOUSES_TYPE));
//
//        // 公司规模
//        modelAndView.addObject("companySizeList", this.borrowCommonService.getParamNameList(CustomConstants.COMPANY_SIZE));
//
//        // 借款人评级
//        modelAndView.addObject("levelList", this.borrowCommonService.getParamNameList(CustomConstants.BORROW_LEVEL));

        // 资产机构
//        modelAndView.addObject("instList", this.borrowCommonService.getInstList());
        bcr.setInstList(CommonUtils.convertBeanList(this.borrowCommonService.getInstList(),HjhInstConfigVO.class));
        //TODO 合作机构 放在admin层数
       // modelAndView.addObject("links", this.borrowCommonService.getLinks());
        //货币种类
//        List<ParamName> list = this.couponBackMoneyHztService.getParamNameList(CustomConstants.CURRENCY_STATUS);
//        modelAndView.addObject("currencyList", list);
        // add by xiashuqing 20171129 begin
        //货币种类
       // List<ParamName> list = this.borrowCommonService.getParamNameList(CustomConstants.CURRENCY_STATUS);
        //定向发标
      //  modelAndView.addObject("instConfigList", this.instConfigService.getInstConfigByType(TENDER_INST_TYPE));
        bcr.setInstConfigList(CommonUtils.convertBeanList(this.instConfigService.getInstConfigByType(TENDER_INST_TYPE),HjhInstConfigVO.class));
        // add by xiashuqing 20171129 end


		// 借款预编码
        form.setBorrowPreNid(this.borrowCommonService.getBorrowPreNid());

        // 借款编码
        String borrowNid = form.getBorrowNid();
        
        // 是否使用过引擎：
        int engineFlag = 0;
        if (StringUtils.isNotEmpty(borrowNid)) {
        	engineFlag = this.borrowCommonService.isEngineUsed(borrowNid); // 0 未使用引擎 ; 1使用引擎
        //	modelAndView.addObject("engineFlag", engineFlag);
        }
         if ("BORROW_FIRST".equals(form.getMoveFlag())) {
        	 form.setIsEngineUsed(String.valueOf(engineFlag));
         }
       
        // 机构编号
        form.setInstCode(form.getInstCode() == null ? "10000000" : form.getInstCode());

        if (StringUtils.isNotEmpty(borrowNid)) {
            // 借款编码是否存在
            boolean isExistsRecord = this.borrowCommonService.isExistsRecord(borrowNid, StringUtils.EMPTY);
            if (isExistsRecord) {
                this.borrowCommonService.getBorrow(form);
            } else {
                // 设置标的的投资有效时间
                form.setBorrowValidTime(this.borrowCommonService.getBorrowConfig("BORROW_VALID_TIME"));
                // 设置标的的银行注册时间
                form.setBankRegistDays(this.borrowCommonService.getBorrowConfig("BORROW_REGIST_DAYS"));
            }
        } else {
            // 设置标的的投资有效时间
            form.setBorrowValidTime(this.borrowCommonService.getBorrowConfig("BORROW_VALID_TIME"));
            // 设置标的的银行注册时间
            form.setBankRegistDays(this.borrowCommonService.getBorrowConfig("BORROW_REGIST_DAYS"));
        }

       
        com.hyjf.am.bean.admin.BorrowWithBLOBs bo = this.borrowCommonService.getRecordById(form);
        form.setBorrowIncreaseMoney(bo.getBorrowIncreaseMoney());
        form.setBorrowInterestCoupon(bo.getBorrowInterestCoupon());
        form.setBorrowTasteMoney(bo.getBorrowTasteMoney());
        //EntrustedFlg() DB 默认为0
        if(bo.getEntrustedFlg()!= null){
        	form.setEntrustedFlg(bo.getEntrustedFlg().toString());
        }
        // EntrustedUserName为空时，不向form传值
        if(bo.getEntrustedUserName()!= null){
        	form.setEntrustedUsername(bo.getEntrustedUserName());
        }

        bcr.setResult(form);
        return bcr;
    }

	/**
	 * 添加信息
	 *
	 * @param borrowCommonRequest
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "插入")
	@RequestMapping("/insertAction")
	public synchronized BorrowCommonResponse insertAction(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) throws Exception {
	      BorrowCommonBean form=new BorrowCommonBean();
	      BeanUtils.copyProperties(borrowCommonRequest, form);
	      BorrowCommonResponse bcr=new BorrowCommonResponse();
		// 借款编码
		String borrowNid = form.getBorrowNid();

		// 借款编码是否存在
		boolean isExistsRecord = StringUtils.isNotEmpty(borrowNid)
				&& this.borrowCommonService.isExistsRecord(borrowNid, StringUtils.EMPTY);

		// 画面的值放到Bean中
		this.borrowCommonService.setPageListInfo( form, isExistsRecord);
		
        bcr.setBorrowProjectType(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectTypeList(CustomConstants.HZT),BorrowProjectTypeVO.class));
        // 还款方式
        bcr.setBorrowStyleList(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectRepayList(),BorrowProjectRepayVO.class));
		// TODO 货币种类 配置表
		// List<ParamName> list =
		// this.borrowCommonService.getParamNameList(CustomConstants.CURRENCY_STATUS);
		//TODO 暂时屏蔽 画面验证(信批需求新增字段无需校验)
		//this.borrowCommonService.validatorFieldCheck( form, isExistsRecord, CustomConstants.HZT);
		/*
		 * HttpSession session = request.getSession(); String sessionToken
		 * =String.valueOf(session.getAttribute(TokenInterceptor.RESUBMIT_TOKEN));//
		 * 生成的令牌 String pageToken = form.getPageToken();//页面令牌
		 */

//		if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)/*
//																	 * || sessionToken == null || pageToken == null ||
//																	 * !sessionToken.equals(pageToken)
//																	 */) {

			// 项目类型
//			List<BorrowProjectType> borrowProjectTypeList = this.borrowCommonService
//					.borrowProjectTypeList(CustomConstants.HZT);
//			modelAndView.addObject("borrowProjectTypeList", borrowProjectTypeList);
//
//			// 还款方式
//			List<BorrowProjectRepay> borrowStyleList = this.borrowCommonService.borrowProjectRepayList();
//			modelAndView.addObject("borrowStyleList", borrowStyleList);

//TODO放在配置里			// 房屋类型
//			modelAndView.addObject("housesTypeList",
//					this.borrowCommonService.getParamNameList(CustomConstants.HOUSES_TYPE));
//
//			// 公司规模
//			modelAndView.addObject("companySizeList",
//					this.borrowCommonService.getParamNameList(CustomConstants.COMPANY_SIZE));
//
//			// 借款人validatorFieldCheck评级
//			modelAndView.addObject("levelList",
//					this.borrowCommonService.getParamNameList(CustomConstants.BORROW_LEVEL));
//			// 合作机构
//			modelAndView.addObject("links", this.borrowCommonService.getLinks());
			// 资产机构
	        bcr.setInstList(CommonUtils.convertBeanList(this.borrowCommonService.getInstList(),HjhInstConfigVO.class));
			//modelAndView.addObject("instList", this.borrowCommonService.getInstList());


			// add by xiashuqing 20171129 begin
			// 定向发标 只获取投资端机构
	        bcr.setInstConfigList(CommonUtils.convertBeanList(this.instConfigService.getInstConfigByType(TENDER_INST_TYPE),HjhInstConfigVO.class));
		//	modelAndView.addObject("instConfigList", this.instConfigService.getInstConfigByType(TENDER_INST_TYPE));
			// add by xiashuqing 20171129 end
//
//			modelAndView.addObject(BorrowCommonDefine.BORROW_FORM, form);
//			return modelAndView;
		//}

		/*--------upd by liushouyi HJH3 Start---------------*/
		// 新建标的使用引擎的时候验证是否有匹配的到的标签
		if (isExistsRecord == false && StringUtils.isNotBlank(form.getIsEngineUsed()) && "1".equals(form.getIsEngineUsed())) {
			Borrow borrow = new Borrow();
			BorrowInfo borrowInfo = new BorrowInfo();

			borrow.setBorrowStyle(form.getBorrowStyle());
			// 借款期限长度不能超过3位，且必须为数字
			borrow.setBorrowPeriod(Integer.valueOf(form.getBorrowPeriod()));
			// 不大于30的最多带两位小数点的数字
			borrow.setBorrowApr(new BigDecimal(form.getBorrowApr()));
			// JSP输入校验：只能是100倍数的数字
			borrow.setAccount(new BigDecimal(form.getAccount()));
			// 录标页面没有该字段输入
			borrowInfo.setAssetType(Integer.valueOf(form.getProjectType()));
			// 资产来源
			borrowInfo.setInstCode(form.getInstCode());
			// 项目类型添加时borrowCd只能填写100以内的数字
			borrow.setProjectType(Integer.valueOf(form.getProjectType()));
			// 进计划的散标点击提交保存时验证该标的是否有相应的标签
			// 获取标签id
			HjhLabel label = adminHjhLabelService.getBestLabel(borrow,borrowInfo, null);
			if(label == null || label.getId() == null){
				// 返回错误信息
				bcr.setRtn(Response.FAIL);
				bcr.setMessage("该标的信息未能匹配到相应的标签，无法使用引擎进计划！");
				return bcr;
			}
		}

		// 初审的时候未打上标签的不允许再进计划
		if ("BORROW_FIRST".equals(form.getMoveFlag()) && StringUtils.isNotBlank(form.getIsEngineUsed()) && "1".equals(form.getIsEngineUsed())) {
			Borrow borrow = this.borrowCommonService.getBorrow(form.getBorrowNid());
			if (null != borrow.getLabelId() && borrow.getLabelId().intValue() == 0) {
				// 返回错误信息
				bcr.setRtn(Response.FAIL);
				bcr.setMessage("该标的录标时未获取到标签，初审发标时不可再使用引擎进计划！");
				return bcr;
			}

			// 标的进计划 产品额外加息不能>0 add by liuyang 20180729
			BorrowInfo borrowInfo = this.borrowCommonService.getBorrowInfoByNid(form.getBorrowNid());
			if (borrowInfo.getBorrowExtraYield().compareTo(BigDecimal.ZERO) > 0) {
				bcr.setRtn(Response.FAIL);
				bcr.setMessage("汇计划底层资产的标的不能设置产品额外加息。");
				return bcr;
			}
			// 标的进计划 产品额外加息不能>0 add by liuyang 20180729
		}
		if ("BORROW_LIST".equals(form.getMoveFlag())) {
			boolean isEngineUsed = false;
			Borrow borrow = this.borrowCommonService.getBorrow(form.getBorrowNid());
			if (StringUtils.isBlank(form.getIsEngineUsed())) {
				if (borrow != null && borrow.getIsEngineUsed() == 1) {
					isEngineUsed = true;
				}
			} else if ("1".equals(form.getIsEngineUsed())) {
				isEngineUsed = true;
			}
			if (isEngineUsed) {
				// 标的进计划 产品额外加息不能>0 add by cwyang 20180729
				if (StringUtils.isNotBlank(form.getBorrowExtraYield())) {
					BigDecimal extrayield = new BigDecimal(form.getBorrowExtraYield());
					if (extrayield.compareTo(BigDecimal.ZERO) > 0) {
						bcr.setRtn(Response.FAIL);
						bcr.setMessage("汇计划底层资产的标的不能设置产品额外加息。");
						return bcr;
					}
				}
			}
		}
		if (isExistsRecord) {
			List<BorrowCommonNameAccountVO> borrowCommonNameAccountList = new ArrayList<BorrowCommonNameAccountVO>();
			BorrowCommonNameAccountVO borrowCommonNameAccount = new BorrowCommonNameAccountVO();
			borrowCommonNameAccount.setNames(form.getName());
			borrowCommonNameAccount.setAccounts(form.getAccount());
			borrowCommonNameAccountList.add(borrowCommonNameAccount);
			form.setBorrowCommonNameAccountList(borrowCommonNameAccountList);
		}

		if (isExistsRecord) {
			this.borrowCommonService.modifyRecord(form,borrowCommonRequest.getAdminUsername(),borrowCommonRequest.getAdminId());

		} else {
			this.borrowCommonService.insertRecord(form,borrowCommonRequest.getAdminUsername(),borrowCommonRequest.getAdminId());
			// 插入borrow的标的判断是否自动备案
            this.borrowCommonService.isAutoRecord(form.getBorrowPreNid());
		}
		// 列表迁移
 
        bcr.setResult(form);
        return bcr;
	}

	/**
	 * 返回列表
	 *
	 * @param request
	 * @param form
	 * @return
	 */
//	@RequestMapping(value = BorrowCommonDefine.BACK_ACTION, method = RequestMethod.POST)
//	public ModelAndView backAction(HttpServletRequest request, RedirectAttributes attr, BorrowCommonBean form) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.BACK_ACTION);
//		ModelAndView modelAndView = new ModelAndView("redirect:/manager/borrow/borrow/init");
//		// 列表迁移
//		modelAndView = this.backActionModelAndView(request, modelAndView, attr, form);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.BACK_ACTION);
//		return modelAndView;
//	}

//	/**
//	 * 返回列表
//	 *
//	 * @param request
//	 * @param form
//	 * @return
//	 */
//	public ModelAndView backActionModelAndView(HttpServletRequest request, ModelAndView modelAndView,
//			RedirectAttributes attr, BorrowCommonBean form) {
//		// 全部借款列表迁移
//		String backparm = "";
//		if (!StringUtils.isEmpty(form.getPageUrl()) && form.getPageUrl().split("\\?").length > 1) {
//			backparm = "?" + form.getPageUrl().split("\\?")[1];
//		}
//
//		if ("BORROW_LIST".equals(form.getMoveFlag())) {
//
//			BorrowBean borrowBean = new BorrowBean();
//			// 借款编码
//			borrowBean.setBorrowNidSrch(form.getBorrowNidSrch());
//			// 借款标题
//			borrowBean.setBorrowNameSrch(form.getBorrowNameSrch());
//			// 借 款 人
//			borrowBean.setUsernameSrch(form.getUsernameSrch());
//			// 项目状态
//			borrowBean.setStatusSrch(form.getStatusSrch());
//			// 项目类型
//			borrowBean.setProjectTypeSrch(form.getProjectTypeSrch());
//			// 还款方式
//			borrowBean.setBorrowStyleSrch(form.getBorrowStyleSrch());
//			// 添加时间
//			borrowBean.setTimeStartSrch(form.getTimeStartSrch());
//			// 添加时间
//			borrowBean.setTimeEndSrch(form.getTimeEndSrch());
//			attr.addFlashAttribute("borrowBean", borrowBean);
//			modelAndView = new ModelAndView("redirect:/manager/borrow/borrow/init" + backparm);
//			// 借款初审列表迁移
//		} else if ("BORROW_FIRST".equals(form.getMoveFlag())) {
//			BorrowFirstBean borrowFirstBean = new BorrowFirstBean();
//			// 借款编码
//			borrowFirstBean.setBorrowNidSrch(form.getBorrowNidSrch());
//			// 借款标题
//			borrowFirstBean.setBorrowNameSrch(form.getBorrowNameSrch());
//			// 是否交保证金
//			borrowFirstBean.setIsBailSrch(form.getIsBailSrch());
//			// 借 款 人
//			borrowFirstBean.setUsernameSrch(form.getUsernameSrch());
//			// 添加时间
//			borrowFirstBean.setTimeStartSrch(form.getTimeStartSrch());
//			// 添加时间
//			borrowFirstBean.setTimeEndSrch(form.getTimeEndSrch());
//			attr.addFlashAttribute("borrowfirstForm", borrowFirstBean);
//			modelAndView = new ModelAndView("redirect:/manager/borrow/borrowfirst/init" + backparm);
//		}
//		return modelAndView;
//	}

	/**
	 * 用户是否存在
	 *
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "检查用户Id")
	@RequestMapping("/isExistsUser/{userId}")
	public int isExistsUser(@PathVariable  String userId) {

		return this.borrowCommonService.isExistsUser(userId);
	}

//	/**TODO 放在配置
//	 * 项目申请人是否存在
//	 *
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.ISEXISTSAPPLICANT_ACTION, method = RequestMethod.POST)
//	public String isExistsApplicant(HttpServletRequest request) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSAPPLICANT_ACTION);
//		String message = this.borrowCommonService.isExistsApplicant(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSAPPLICANT_ACTION);
//		return message;
//	}

//	/**TODO放在admin
//	 * 项目申请人是否存在
//	 *
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.IS_ACCOUNT_LEGAL_ACTION, method = RequestMethod.POST)
//	public String isAccountLegal(HttpServletRequest request) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_ACCOUNT_LEGAL_ACTION);
//		String message = this.borrowCommonService.isAccountLegal(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_ACCOUNT_LEGAL_ACTION);
//		return message;
//	}

//	/**TODO放在前置
//	 * 判断借款期限是否为0
//	 *
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.IS_BORROW_PERIOD_CHECK_ACTION, method = RequestMethod.POST)
//	public String isBorrowPeriodCheck(HttpServletRequest request) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_BORROW_PERIOD_CHECK_ACTION);
//		String message = this.borrowCommonService.isBorrowPeriodCheck(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_BORROW_PERIOD_CHECK_ACTION);
//		return message;
//	}

//	/**TODO放在用户里
//	 * 垫付机构用户名是否存在
//	 *
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.ISREPAYORGUSER_ACTION, method = RequestMethod.POST)
//	public String isRepayOrgUser(HttpServletRequest request) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISREPAYORGUSER_ACTION);
//		String message = this.borrowCommonService.isRepayOrgUser(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISREPAYORGUSER_ACTION);
//		return message;
//	}

	/**
	 * 获取最新的借款预编码
	 *
	 * @return
	 */
	@ApiOperation(value = " 获取最新的借款预编码")
	@RequestMapping("/getBorrowPreNid")
	public String getBorrowPreNid() {
		String borrowPreNid = this.borrowCommonService.getBorrowPreNid();
		return borrowPreNid;
	}

	/**
	 * 获取现金贷的借款预编号
	 *
	 * @return
	 */
	@ApiOperation(value = " 获取现金贷的借款预编号")
	@RequestMapping("/getXJDBorrowPreNid")
	public String getXJDBorrowPreNid() {
		String borrowPreNid = this.borrowCommonService.getXJDBorrowPreNid();
		return borrowPreNid;
	}

	/**
	 * 借款预编码是否存在
	 *
	 * @param borrowPreNid
	 * @return
	 */
	@ApiOperation(value = " 借款预编码是否存在")
	@RequestMapping("/isExistsBorrowPreNidRecord/{borrowPreNid}")
	public boolean isExistsBorrowPreNidRecord(@PathVariable  String borrowPreNid) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSBORROWPRENIDRECORD);
//		String message = this.borrowCommonService.isExistsBorrowPreNidRecord(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSBORROWPRENIDRECORD);
		boolean borrowPreNidFlag = borrowCommonService.isExistsRecord(StringUtils.EMPTY, borrowPreNid);
		return borrowPreNidFlag;
	}

	/**
	 * 获取融资服务费率 & 账户管理费率
	 *
	 * @param borrowCommonRequest
	 * @return
	 */
	@ApiOperation(value = "获取融资服务费率 & 账户管理费率")
	@RequestMapping("/getBorrowServiceScale")
	public BorrowCommonVO getBorrowServiceScale(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
		BorrowCommonVO scale = this.borrowCommonService.getBorrowServiceScale(borrowCommonRequest);
		return scale;
	}

//	/**
//	 * 资料上传
//	 *
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = BorrowCommonDefine.UPLOAD_FILE, method = RequestMethod.POST)
//	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_FILE);
//		String files = this.borrowCommonService.uploadFile(request, response);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_FILE);
//		return files;
//	}

//	/**
//	 * 导出功能
//	 * 
//	 * @param request
//	 * @param response
//	 * @param form
//	 * @throws Exception
//	 */
//	@RequestMapping(value = BorrowCommonDefine.DOWNLOAD_CAR_ACTION, method = RequestMethod.POST)
//	public void downloadCarAction(HttpServletRequest request, HttpServletResponse response, BorrowBean form)
//			throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_CAR_ACTION);
//		this.borrowCommonService.downloadCar(request, response, form);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_CAR_ACTION);
//	}

//	/**
//	 * 资料上传
//	 *
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.UPLOAD_CAR_ACTION, method = RequestMethod.POST)
//	public String uploadCarAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_CAR_ACTION);
//		String result = this.borrowCommonService.uploadCar(request, response);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_CAR_ACTION);
//		return result;
//	}

//	/**
//	 * 导出功能
//	 *
//	 * @param request
//	 * @param response
//	 * @param form
//	 * @throws Exception
//	 */
//	@RequestMapping(value = BorrowCommonDefine.DOWNLOAD_HOUSE_ACTION, method = RequestMethod.POST)
//	public void downloadHouseAction(HttpServletRequest request, HttpServletResponse response, BorrowBean form)
//			throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_HOUSE_ACTION);
//		this.borrowCommonService.downloadHouse(request, response, form);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_HOUSE_ACTION);
//	}

//	/**
//	 * 资料上传
//	 *
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.UPLOAD_HOUSE_ACTION, method = RequestMethod.POST)
//	public String uploadHouseAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_HOUSE_ACTION);
//		String result = this.borrowCommonService.uploadHouse(request, response);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_HOUSE_ACTION);
//		return result;
//	}

//	/**
//	 * 导出功能
//	 *
//	 * @param request
//	 * @param response
//	 * @param form
//	 * @throws Exception
//	 */
//	@RequestMapping(value = BorrowCommonDefine.DOWNLOAD_AUTHEN_ACTION, method = RequestMethod.POST)
//	public void downloadAuthenAction(HttpServletRequest request, HttpServletResponse response, BorrowBean form)
//			throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_AUTHEN_ACTION);
//		this.borrowCommonService.downloadAuthen(request, response, form);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_AUTHEN_ACTION);
//	}

//	/**
//	 * 资料上传
//	 *
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.UPLOAD_AUTHEN_ACTION, method = RequestMethod.POST)
//	public String uploadAuthenAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_AUTHEN_ACTION);
//		String result = this.borrowCommonService.uploadAuthen(request, response);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.UPLOAD_AUTHEN_ACTION);
//		return result;
//	}

//	/**
//	 * 借款内容填充
//	 *
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.CONTENT_FILL_ACTION, method = RequestMethod.POST)
//	public String contentFillAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.CONTENT_FILL_ACTION);
//		String result = this.borrowCommonService.contentFill(request, response);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.CONTENT_FILL_ACTION);
//		return result;
//	}

//	/**
//	 * 下载借款内容模板
//	 *
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(value = BorrowCommonDefine.DOWNLOAD_CONTENT_ACTION, method = RequestMethod.POST)
//	public void downloadContentFillAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_CONTENT_ACTION);
//		this.borrowCommonService.downloadContentFill(request, response);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.DOWNLOAD_CONTENT_ACTION);
//	}

	/**
	 * 根据资产编号查询该资产下面的产品类型
	 *
	 * @param instCode
	 * @return
	 */
	@ApiOperation(value = " 根据资产编号查询该资产下面的产品类型")
	@RequestMapping("/getProductTypeAction/{instCode}")
	public BorrowCommonResponse getProductTypeAction(@PathVariable  String instCode) {
		BorrowCommonResponse bcr=new BorrowCommonResponse();
		bcr.setHjhAssetTypeList(CommonUtils.convertBeanList(this.borrowCommonService.hjhAssetTypeList(instCode),HjhAssetTypeVO.class));
		return bcr;
	}

	/**
	 * 受托用户是否存在
	 *
	 * @param userName
	 * @return
	 */
	@ApiOperation(value = " 受托用户是否存在")
	@RequestMapping("/isEntrustedExistsUser/{userName}")
	public int isEntrustedExistsUser(@PathVariable  String userName) {
		return this.borrowCommonService.isEntrustedExistsUser(userName);
	}

//	/**
//	 * 借款主体CA认证check
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.IS_BORROWUSER_CA_CHECK_ACTION, method = RequestMethod.POST)
//	public String isBorrowUserCACheck(HttpServletRequest request) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_BORROWUSER_CA_CHECK_ACTION);
//		// 借款主体
//		String value = request.getParameter("param");
//		String name = request.getParameter("name");
//		String ret = this.borrowCommonService.isBorrowUserCACheck(value, name);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_BORROWUSER_CA_CHECK_ACTION);
//		return ret;
//	}
//
//	/**
//	 * 社会信用代码或身份证号CA认证check
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = BorrowCommonDefine.IS_CA_IDNO_CHECK_ACTION, method = RequestMethod.POST)
//	public String isCAIdNoCheck(HttpServletRequest request) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_CA_IDNO_CHECK_ACTION);
//		// 借款主体
//		String value = request.getParameter("param");
//		String name = request.getParameter("name");
//		String ret = this.borrowCommonService.isCAIdNoCheck(value, name);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.IS_CA_IDNO_CHECK_ACTION);
//		return ret;
//	}
}