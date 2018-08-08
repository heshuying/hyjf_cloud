package com.hyjf.am.trade.controller.admin.borrow;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.BorrowCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowBeanRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.front.borrow.BorrowCommonService;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @author dongzeshan
 * @date 2018/07/09 17:00
 * @version V1.0  
 */
@Api(value = "借款列表")
@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowAdminController  {

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private BorrowCommonService borrowCommonService;

	/**
	 * 画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "查询借款列表")
	@RequestMapping("/selectBorrowStyleList")
	public BorrowCustomizeResponse init(@RequestBody @Valid BorrowBeanRequest form) {
		
		BorrowCustomizeResponse mcr=new BorrowCustomizeResponse();
		// 资金来源
		List<HjhInstConfig> hjhInstConfigList = this.borrowCommonService.hjhInstConfigList("");
		mcr.setHjhInstConfig(CommonUtils.convertBeanList(hjhInstConfigList, HjhInstConfigVO.class));
		// 项目类型
		List<BorrowProjectType> borrowProjectTypeList = this.borrowCommonService.borrowProjectTypeList("");
		mcr.setBorrowProjectType(CommonUtils.convertBeanList(borrowProjectTypeList, BorrowProjectTypeVO.class));
		// 还款方式
		List<BorrowStyle> borrowStyleList = this.borrowCommonService.borrowStyleList("");
		mcr.setBorrowStyle(CommonUtils.convertBeanList(borrowStyleList, BorrowStyleVO.class));
		// 项目状态TODO 需要从配置库
		//List<ParamName> borrowStatusList = this.borrowCommonService.getParamNameList(CustomConstants.BORROW_STATUS);

		BorrowCommonCustomizeVO corrowCommonCustomize = new BorrowCommonCustomizeVO();
		// 借款编码
		corrowCommonCustomize.setBorrowNidSrch(form.getBorrowNidSrch());
		// 借款标题
		/* DEL BY LIUSHOUYI 合规检查 START
		corrowCommonCustomize.setBorrowNameSrch(form.getBorrowNameSrch());
		DEL BY LIUSHOUYI 合规检查 END*/
		// 借 款 人
		corrowCommonCustomize.setUsernameSrch(form.getUsernameSrch());
		// 项目状态
		corrowCommonCustomize.setStatusSrch(form.getStatusSrch());
		// 项目类型
		corrowCommonCustomize.setProjectTypeSrch(form.getProjectTypeSrch());
		// 还款方式
		corrowCommonCustomize.setBorrowStyleSrch(form.getBorrowStyleSrch());
		// 放款时间
		corrowCommonCustomize.setRecoverTimeStartSrch(form.getRecoverTimeStartSrch());
		// 放款时间
		corrowCommonCustomize.setRecoverTimeEndSrch(form.getRecoverTimeEndSrch());
		// 添加时间
		corrowCommonCustomize.setTimeStartSrch(form.getTimeStartSrch());
		// 添加时间
		corrowCommonCustomize.setTimeEndSrch(form.getTimeEndSrch());
		// 标签名称 new added
		corrowCommonCustomize.setLabelNameSrch(form.getLabelNameSrch());
		corrowCommonCustomize.setSort(form.getSort());
		corrowCommonCustomize.setCol(form.getCol());
		corrowCommonCustomize.setBorrowPeriod(form.getBorrowPeriod());
		// 计划编号
		corrowCommonCustomize.setPlanNidSrch(form.getPlanNidSrch());
		// 资金来源编号
		corrowCommonCustomize.setInstCodeSrch(form.getInstCodeSrch());

		Long count = this.borrowService.countBorrow(corrowCommonCustomize);
		mcr.setRecordTotal(count.intValue());
		if (count != null && count > 0) {
			Paginator paginator = new Paginator(form.getCurrPage(),form.getPageSize(), Integer.valueOf(String.valueOf(count)));
			corrowCommonCustomize.setLimitStart(paginator.getOffset());
			corrowCommonCustomize.setLimitEnd(paginator.getLimit());
			List<BorrowCustomizeVO> recordList = this.borrowService.selectBorrowList(corrowCommonCustomize);
			mcr.setResultList(recordList);
		}
		return mcr;
	}

	

}
