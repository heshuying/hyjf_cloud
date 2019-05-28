
package com.hyjf.am.trade.controller.admin.borrow;

import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhLabelService;
import com.hyjf.am.trade.service.front.borrow.BorrowCommonService;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

	// 机构属性 1-出借机构 0-借款机构
	private final int TENDER_INST_TYPE = 1;

	@Autowired
	private BorrowCommonService borrowCommonService;

	@Autowired
	private InstConfigService instConfigService;

	@Autowired
	private AdminHjhLabelService adminHjhLabelService;

	@Autowired
	BorrowService borrowService;

	@Value("${wjt.instCode}")
	private String wjtInstCode;

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
        bcr.setBorrowStyleList(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectRepayList(),BorrowProjectRepayVO.class));


        // 资产机构
        bcr.setInstList(CommonUtils.convertBeanList(this.borrowCommonService.getInstList(),HjhInstConfigVO.class));

		// add by xiashuqing 20171129 begin
		// 定向发标
		//  modelAndView.addObject("instConfigList", this.instConfigService.getInstConfigByType(TENDER_INST_TYPE));
		bcr.setInstConfigList(CommonUtils.convertBeanList(this.instConfigService.getInstConfigByType(TENDER_INST_TYPE), HjhInstConfigVO.class));
		// add by xiashuqing 20171129 end

		// 借款预编码
        form.setBorrowPreNid(this.borrowCommonService.getBorrowPreNid());

        // 借款编码
        String borrowNid = form.getBorrowNid();
        
        // 是否使用过引擎：
        int engineFlag = 0;
        if (StringUtils.isNotEmpty(borrowNid)) {
        	engineFlag = this.borrowCommonService.isEngineUsed(borrowNid); // 0 未使用引擎 ; 1使用引擎
        }
         if ("BORROW_FIRST".equals(form.getMoveFlag())) {
        	 form.setIsEngineUsed(String.valueOf(engineFlag));
         }
       
        // 机构编号
        form.setInstCode(form.getInstCode() == null ? "10000000" : form.getInstCode());

        // 获取风险测评等级配置
		EvaluationConfig evaluationConfig = this.borrowCommonService.selectEvaluationConfig();
		if(evaluationConfig!=null){
			String investLevel = evaluationConfig.getAa1EvaluationProposal();
			form.setInvestLevel(investLevel);
		}else {
			form.setInvestLevel("保守型");
		}


        if (StringUtils.isNotEmpty(borrowNid)) {
            // 借款编码是否存在
            boolean isExistsRecord = this.borrowCommonService.isExistsRecord(borrowNid, StringUtils.EMPTY);
            if (isExistsRecord) {
                this.borrowCommonService.getBorrow(form);
            } else {
                // 设置标的的出借有效时间
                form.setBorrowValidTime(this.borrowCommonService.getBorrowConfig("BORROW_VALID_TIME"));
                // 设置标的的银行注册时间
                form.setBankRegistDays(this.borrowCommonService.getBorrowConfig("BORROW_REGIST_DAYS"));
            }
        } else {
            // 设置标的的出借有效时间
            form.setBorrowValidTime(this.borrowCommonService.getBorrowConfig("BORROW_VALID_TIME"));
            // 设置标的的银行注册时间
            form.setBankRegistDays(this.borrowCommonService.getBorrowConfig("BORROW_REGIST_DAYS"));
        }

       
        com.hyjf.am.bean.admin.BorrowWithBLOBs bo = this.borrowCommonService.getRecordById(form);
        form.setBorrowIncreaseMoney(bo.getBorrowIncreaseMoney());
        form.setBorrowInterestCoupon(bo.getBorrowInterestCoupon());
        form.setBorrowTasteMoney(bo.getBorrowTasteMoney());
		// add by liushouyi 20180911 start
		// 备案中的标的返回备案flg
		if(null != bo.getStatus() && bo.getStatus() == 0){
			form.setIsRegistFlg("BORROW_REGIST");
			form.setIsEngineUsed(bo.getIsEngineUsed().toString());
		}
		//  add by liushouyi 20180911 end
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
		boolean isExistsRecord = StringUtils.isNotEmpty(borrowNid) && this.borrowCommonService.isExistsRecord(borrowNid, StringUtils.EMPTY);

		// 画面的值放到Bean中
		this.borrowCommonService.setPageListInfo( form, isExistsRecord);
		
        bcr.setBorrowProjectType(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectTypeList(CustomConstants.HZT),BorrowProjectTypeVO.class));
        // 还款方式
        bcr.setBorrowStyleList(CommonUtils.convertBeanList(this.borrowCommonService.borrowProjectRepayList(),BorrowProjectRepayVO.class));

		// 资产机构
		bcr.setInstList(CommonUtils.convertBeanList(this.borrowCommonService.getInstList(),HjhInstConfigVO.class));


		// add by xiashuqing 20171129 begin
		// 定向发标 只获取出借端机构
		bcr.setInstConfigList(CommonUtils.convertBeanList(this.instConfigService.getInstConfigByType(TENDER_INST_TYPE),HjhInstConfigVO.class));
		//	modelAndView.addObject("instConfigList", this.instConfigService.getInstConfigByType(TENDER_INST_TYPE));
		// add by xiashuqing 20171129 end

		/*--------upd by liushouyi HJH3 Start---------------*/
		// 新建标的使用引擎的时候验证是否有匹配的到的标签
		if (isExistsRecord == false) {
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
				// 勾选使用引擎未匹配到标签的无法建标
				if(StringUtils.isNotBlank(form.getIsEngineUsed()) && "1".equals(form.getIsEngineUsed())) {
					// 返回错误信息
					bcr.setRtn(Response.FAIL);
					bcr.setMessage("该标的信息未能匹配到相应的标签，无法使用引擎进计划！");
					return bcr;
				} else {
					// 未勾选使用引擎的散标匹配不到标签赋值为0
                    form.setLabelId(0);
				}
			} else {
                form.setLabelId(label.getId());
            }
		}else if (isExistsRecord){
			// 备案状态的标的修改时重新匹配标签
			Borrow borrow = borrowService.getBorrow(form.getBorrowNid());
			BorrowInfo borrowInfo =borrowService.getBorrowInfoByNid(form.getBorrowNid());
			if (null != borrow && borrow.getStatus()==0) {
				// 备案的页面需要给前台返回flg
				form.setIsRegistFlg("BORROW_REGIST");
				if( StringUtils.isNotBlank(form.getIsEngineUsed()) && "1".equals(form.getIsEngineUsed())) {
					borrow.setBorrowStyle(form.getBorrowStyle());
					// 借款期限长度不能超过3位，且必须为数字
					borrow.setBorrowPeriod(Integer.valueOf(form.getBorrowPeriod()));
					// 不大于30的最多带两位小数点的数字
                    borrow.setBorrowApr(new BigDecimal(form.getBorrowApr()));
					// JSP输入校验：只能是100倍数的数字
                    borrow.setAccount(new BigDecimal(form.getAccount()));
                    borrowInfo.setInstCode(form.getInstCode());
					// 录标页面没有该字段输入
                    borrowInfo.setAssetType(Integer.valueOf(borrowInfo.getAssetType()));
					// 项目类型添加时borrowCd只能填写100以内的数字
                    borrow.setProjectType(Integer.valueOf(form.getProjectType()));
					// 进计划的散标点击提交保存时验证该标的是否有相应的标签
					// 获取标签id
					HjhLabel label = adminHjhLabelService.getBestLabel(borrow,borrowInfo, null);
					if (label == null || label.getId() == null) {
						// 加载错误信息到页面
						bcr.setRtn(Response.FAIL);
						bcr.setMessage("该标的信息未能匹配到相应的标签，无法使用引擎进计划！");
						return bcr;
					}
				}
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
			if(borrowInfo.getBorrowExtraYield()!=null) {
				if (borrowInfo.getBorrowExtraYield().compareTo(BigDecimal.ZERO) > 0) {
					bcr.setRtn(Response.FAIL);
					bcr.setMessage("汇计划底层资产的标的不能设置产品额外加息。");
					return bcr;
				}
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

		// add by liuyang 温金投推送标的不能为新手标 20190527 start
		if(wjtInstCode.equals(form.getPublishInstCode()) && form.getProjectType() == 4){
			bcr.setRtn(Response.ERROR);
			bcr.setMessage("新手标出借终端仅能选择汇盈金服。");
			return bcr;
		}
		// add by liuyang 温金投推送标的不能为新手标 20190527 end
		if (isExistsRecord) {
			this.borrowCommonService.modifyRecord(form,borrowCommonRequest.getAdminUsername(),borrowCommonRequest.getAdminId());

		} else {
			this.borrowCommonService.insertRecord(form,borrowCommonRequest.getAdminUsername(),borrowCommonRequest.getAdminId());
			// 插入borrow的标的判断是否自动备案
            this.borrowCommonService.isAutoRecord(form.getBorrowPreNid());
		}
		// 列表迁移
		//
 
        bcr.setResult(form);
        return bcr;
	}


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
		boolean borrowPreNidFlag = borrowCommonService.isExistsRecord(StringUtils.EMPTY, borrowPreNid);
		return borrowPreNidFlag;
	}

	/**
	 * 获取放款服务费率 & 还款服务费率
	 *
	 * @param borrowCommonRequest
	 * @return
	 */
	@ApiOperation(value = "获取放款服务费率 & 还款服务费率")
	@RequestMapping("/getBorrowServiceScale")
	public BorrowCommonVO getBorrowServiceScale(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
		BorrowCommonVO scale = this.borrowCommonService.getBorrowServiceScale(borrowCommonRequest);
		return scale;
	}

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

	/**
	 * 获取标的投资等级
	 *
	 * @param borrowLevel
	 * @return
	 */
	@ApiOperation(value = "获取标的投资等级")
	@RequestMapping("/getBorrowLevelAction/{borrowLevel}")
	public StringResponse getBorrowLevelAction(@PathVariable String borrowLevel) {
		StringResponse response = new StringResponse();
		String investLevel = "保守型";
		EvaluationConfig evaluationConfig = this.borrowCommonService.selectEvaluationConfig();
		if (evaluationConfig != null) {
			switch (borrowLevel) {
				case "BBB":
					investLevel = evaluationConfig.getBbbEvaluationProposal();
					break;
				case "A":
					investLevel = evaluationConfig.getaEvaluationProposal();
					break;
				case "AA-":
					investLevel = evaluationConfig.getAa0EvaluationProposal();
					break;
				case "AA":
					investLevel = evaluationConfig.getAa1EvaluationProposal();
					break;
				case "AA+":
					investLevel = evaluationConfig.getAa2EvaluationProposal();
					break;
				case "AAA":
					investLevel = evaluationConfig.getAaaEvaluationProposal();
					break;
			}
		}
		response.setResultStr(investLevel);
		return response;
	}
}