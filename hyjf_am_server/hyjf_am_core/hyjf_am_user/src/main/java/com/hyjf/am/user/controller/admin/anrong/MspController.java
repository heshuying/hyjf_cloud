package com.hyjf.am.user.controller.admin.anrong;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.MspConfigureExample;
import com.hyjf.am.user.dao.model.customize.MspConfigureCustomize;
import com.hyjf.am.user.service.admin.anrong.MspApplyService;
import com.hyjf.am.user.service.admin.anrong.MspService;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.am.vo.user.MspRegionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**安融反欺诈查询配置表
 * 
 * @author
 *
 */
@RestController
@RequestMapping("/am-user/mspapplyconfigure")
public class MspController extends BaseController {

	@Autowired
	private MspService mspService;
	@Autowired
	private MspApplyService mspApplyService;

	/**
	 * 列表维护画面初始化
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/init")
	public MspResponse init(@RequestBody  MspRequest form) {

		// 创建分页

		return this.createPage(form);
	}

	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/searchAction")
	public MspResponse search(@RequestBody  MspRequest form) {
		return this.createPage(form);
	}

	/**
	 * 文章列表维护分页机能 页面初始化
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	private MspResponse createPage(@RequestBody MspRequest form) {
		// 封装查询条件
		Map<String, Object> conditionMap = setCondition(form);
		Integer count = this.mspService.getRecordCount(conditionMap);
		MspResponse mr=new MspResponse();
		if (count != null && count > 0) {
			Paginator paginator = new Paginator(form.getCurrPage(),form.getPageSize(),count);
			List<MspConfigureCustomize> recordList = this.mspService.getRecordList(conditionMap, paginator.getOffset(), paginator.getLimit());
			mr.setRecordTotal(count);
			mr.setResultList( CommonUtils.convertBeanList(recordList,MspConfigureVO.class));

		}
		return mr;
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/infoAction")
	public MspResponse info( @RequestBody MspRequest form) {
		MspResponse mr=new MspResponse();
		if (form.getId()!=null) {
			MspConfigureCustomize record = mspService.getRecord(form.getId().toString());
			
			MspConfigureVO mcv=new MspConfigureVO();
			BeanUtils.copyProperties(record,mcv);
			mr.setResult(mcv);
			mr.setRegionList(CommonUtils.convertBeanList(this.mspApplyService.getRegionList(),MspRegionVO.class));

		}
//		form.setUnredeemedMoney(new BigDecimal(0));
//		form.setLoanTimeLimit(1); 
		mr.setRegionList(CommonUtils.convertBeanList(this.mspApplyService.getRegionList(),MspRegionVO.class));
		return mr;
	}

	/**
	 * 添加信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/insertAction")
	public MspResponse insertAction(@RequestBody MspRequest form) {
		MspResponse mr=new MspResponse();

		if (form.getId()!=null) {
			MspConfigureCustomize record = mspService.getRecord(form.getId().toString());
			if (record != null) {
				mr.setRtn(AdminResponse.FAIL);
				mr.setMessage("Id重复");
				return mr;
			}
		}
		MspConfigureCustomize mspConfigure =new MspConfigureCustomize();
		mspConfigure.setConfigureName(form.getConfigureName());
		//验证标的名称是否重复
		int record = mspService.sourceNameIsExists(mspConfigure);
		if (record == 1) {
			mr.setRtn(AdminResponse.FAIL);
			mr.setMessage("标的名称是否重复");
			return mr;
		}
		
		MspConfigureCustomize mc=new MspConfigureCustomize();
		BeanUtils.copyProperties(form,mc);

		// 数据插入
		this.mspService.insertRecord(mc);

		return mr;
	}

	/**
	 * 修改维护信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/updateAction")
	public MspResponse updateAction( @RequestBody MspRequest form) {
		
		MspConfigureCustomize mc=new MspConfigureCustomize();
		BeanUtils.copyProperties(form,mc);

		// 更新
		this.mspService.updateRecord(mc);

		return new MspResponse();
	}
	/**
     * ajax用户按照手机号和身份证号查询开户掉单校验
     * 
     * @param request
     * 
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/configureNameError")
    public MspResponse configureNameError(@RequestBody MspRequest form) {
    
    	MspResponse mr=new MspResponse();
    	MspConfigureCustomize mspConfigure =new MspConfigureCustomize();
		mspConfigure.setConfigureName(form.getConfigureName());
    	int record = mspService.sourceNameIsExists(mspConfigure);
    	if (record>0) {
    		mr.setRtn(AdminResponse.FAIL);
			mr.setMessage("标的名不允许重复！");
			return mr;
        }else{
        	return mr;
        }
    }
    	

	//判断合同金额是否大于100
	public boolean bigDecimalMix(BigDecimal bigDecimal){
		BigDecimal b = new BigDecimal(100);
		if (bigDecimal.compareTo(b)==-1) {
			return false;
		}
		return true;
		
	}

	/**
	 * 删除安融查询数据
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/deleteAction")
	public MspResponse deleteRecordAction(@RequestBody MspRequest form) {

		this.mspService.deleteRecord(form.getId().toString());

		return new MspResponse();
//		return "redirect:" + MspDefine.REQUEST_MAPPING + "/" + MspDefine.INIT;
	}
	/**
	 * 封装查询条件
	 * 
	 * @param form
	 * @return
	 */
	private Map<String, Object> setCondition(MspRequest form) {
		//封装查询条件
		MspConfigureExample example = new MspConfigureExample();
		String serviceTypeSrch = StringUtils.isNotEmpty(form.getServiceTypeSrch()) ? form.getServiceTypeSrch() : null;
		String sourceTypeSrch = StringUtils.isNotEmpty(form.getSourceTypeSrch()) ? form.getSourceTypeSrch() : null;
		String loanTypeSrch = StringUtils.isNotEmpty(form.getLoanTypeSrch()) ? form.getLoanTypeSrch() : null;
		String loanMoney = StringUtils.isNotEmpty(form.getLoanMoneys()) ? form.getLoanMoneys() : null;
		String creditAddress = StringUtils.isNotEmpty(form.getCreditAddress()) ? form.getCreditAddress() : null;
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("serviceTypeSrch", serviceTypeSrch);
		conditionMap.put("sourceTypeSrch", sourceTypeSrch);
		conditionMap.put("loanTypeSrch", loanTypeSrch);
		conditionMap.put("loanMoney", loanMoney);
		conditionMap.put("creditAddress", creditAddress);
		return conditionMap;
	}
	/**
	 * 检查编号唯一性
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkAction")
	public MspResponse checkAction(@RequestBody MspRequest form) {
		MspResponse mr=new MspResponse();
		int record = mspService.sourceIdIsExists(form.getSourceId());
		if (record == 1) {
    		mr.setRtn(AdminResponse.FAIL);
			mr.setMessage("ID重复！");
			return mr;
		}
		// 没有错误时,返回y
		return mr;
	}

}
