package com.hyjf.admin.controller.finance.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.WithdrawBeanAPIRequest;
import com.hyjf.admin.beans.vo.AdminWithdrawAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.finance.withdraw.WithdrawService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WithdrawCustomizeResponse;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.vo.admin.finance.withdraw.WithdrawCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author jijun
 * @date 20180719
 */
@Api(value = "资金中心-提现管理",tags = "资金中心-提现管理")
@RestController
@RequestMapping("/hyjf-admin/finance/withdraw")
public class WithdrawController extends BaseController {

	@Autowired
	private WithdrawService withdrawService;

	private static final String PERMISSIONS = "withdrawlist";
	/**
	 * 返现管理画面初始化
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "提现管理页面载入", notes = "提现管理页面载入")
	@PostMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<AdminWithdrawAPIVO>> init(@RequestBody WithdrawBeanAPIRequest request) {

		WithdrawCustomizeResponse response =withdrawService.getWithdrawRecordList(CommonUtils.convertBean(request, WithdrawBeanRequest.class));
		if (response==null){
			return new AdminResult<>(FAIL, FAIL_DESC);
		}else if(!Response.isSuccess(response)){
			return new AdminResult<>(FAIL, response.getMessage());
		}

		List<AdminWithdrawAPIVO> apiList = new ArrayList<AdminWithdrawAPIVO>();

		if(CollectionUtils.isNotEmpty(response.getResultList())){
			apiList=CommonUtils.convertBeanList(response.getResultList(), AdminWithdrawAPIVO.class);
			return new AdminResult<ListResult<AdminWithdrawAPIVO>>(ListResult.build(apiList, response.getCount()));
		}else {
			return new AdminResult<ListResult<AdminWithdrawAPIVO>>(ListResult.build(apiList,0));

		}
	}

	/**
	 * 提现确认
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "提现确认", notes = "提现确认")
	@PostMapping("/withdrawConfirm")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CONFIRM)
	public AdminResult confirmWithdrawAction(HttpServletRequest request,@RequestBody WithdrawBeanAPIRequest form) {
		JSONObject ret = new JSONObject();
		Integer userId = form.getUserId();
		String nid = form.getNid();
		Integer status = form.getStatus();// 0 失败； 1成功

		if (Validator.isNull(userId) || Validator.isNull(nid) || Validator.isNull(status)) {
			logger.error("withdrawConfirm", new Exception("参数不正确[userId=" + userId + ",nid="+nid+",status="+status+"]"));
			return new AdminResult<>(FAIL, "确认发生错误,请重新操作!");
		}
		// 取出账户信息
		AccountVO account = this.withdrawService.getAccountByUserId(userId);
		if (Validator.isNull(account)) {
			logger.error("withdrawConfirm", new Exception("[userId=" + userId + "]下账户异常！"));
			return new AdminResult<>(FAIL, "确认发生错误,请重新操作!");
		}
		// 获取充值信息
		AccountWithdrawVO accountwithdraw = this.withdrawService.queryAccountwithdrawByNid(nid, userId);
		if (Validator.isNull(accountwithdraw)) {
			logger.error("withdrawConfirm", new Exception("[nid=" + nid + "]不存在！"));
			return new AdminResult<>(FAIL,"确认发生错误,请重新操作!");
		}
		// 设置IP地址
		String ip = CustomUtil.getIpAddr(request);
		// 登陆用户名
		String loginUser = getUser(request).getUsername();

		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", loginUser);
		param.put("ip", ip);
		param.put("userId",String.valueOf(userId));
		param.put("nid",nid);
		// 确认充值 ; 0表示充值失败
		boolean isAccountUpdate = false;
		if (status == 1) {
			try {
				isAccountUpdate = this.withdrawService.updateAccountAfterWithdraw(param);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			// 充值失败,更新充值订单
			try {
				isAccountUpdate = this.withdrawService.updateAccountAfterWithdrawFail(userId, nid);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		// 提现确认成功
		if (isAccountUpdate) {
			return new AdminResult<>(SUCCESS,"提现确认操作成功!");
		} else {
			return new AdminResult<>(FAIL,"提现确认发生错误,请重新操作!");
		}
	}

	/**
	 * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "导出EXCEL", notes = "导出EXCEL")
	@PostMapping("/exportWithdrawExcel")
	//@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportWithdrawExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody WithdrawBeanAPIRequest form) throws Exception {

		// 是否具有组织机构查看权限
		String isOrganizationView = form.getIsOrganizationView();

		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "提现列表";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

		WithdrawBeanRequest withdrawBeanRequest = new WithdrawBeanRequest();
		BeanUtils.copyProperties(form, withdrawBeanRequest);

		withdrawBeanRequest.setCurrPage(1);
		withdrawBeanRequest.setPageSize(defaultRowMaxCount);
		WithdrawCustomizeResponse recordListResponse = withdrawService.getWithdrawRecordList(withdrawBeanRequest);
		Integer totalCount = recordListResponse.getCount();

		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		if (totalCount == 0) {
			String sheetNameTmp = sheetName + "_第1页";
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}
		for (int i = 1; i <= sheetCount; i++) {
			withdrawBeanRequest.setPageSize(defaultRowMaxCount);
			withdrawBeanRequest.setCurrPage(i);
			WithdrawCustomizeResponse withdrawCustomizeResponse = withdrawService.getWithdrawRecordList(withdrawBeanRequest);
			List<WithdrawCustomizeVO> record = withdrawCustomizeResponse.getResultList();
			if (record != null && record.size()> 0) {
				String sheetNameTmp = sheetName + "_第" + i + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  record);
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap(String isOrganizationView) {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("username", "用户名");
		map.put("accountId", "电子帐号");
		map.put("bankFlag", "资金托管平台");
		map.put("bankSeqNo", "流水号");
		map.put("mobile", "手机号");
		map.put("roleid", "用户角色");
		map.put("userAttribute", "用户属性（当前）");
		if (StringUtils.isNotBlank(isOrganizationView)) {
			map.put("userRegionName", "用户所属一级分部（当前）");
			map.put("userBranchName", "用户所属二级分部（当前）");
			map.put("userDepartmentName", "用户所属团队（当前）");
		}
		map.put("referrerName", "推荐人用户名（当前）");
		map.put("referrerTrueName", "推荐人姓名（当前）");
		if (StringUtils.isNotBlank(isOrganizationView)) {
			map.put("referrerRegionName", "推荐人所属一级分部（当前）");
			map.put("referrerBranchName", "推荐人所属二级分部（当前）");
			map.put("referrerDepartmentName", "推荐人所属团队（当前）");
		}
		map.put("ordid", "订单号");
		map.put("total", "提现金额");
		map.put("fee", "手续费");
		map.put("credited", "到账金额");
		map.put("actualTotal", "实际出账金额");
		map.put("bank", "提现银行");
		map.put("withdrawType", "提现方式");
		map.put("account", "提现银行卡号");
		map.put("addtimeStr", "提交时间");
		map.put("clientStr", "提现平台");
		map.put("statusStr", "处理状态");
		map.put("txDateS", "发送日期");
		map.put("txTimeS", "发送时间");
		map.put("seqNo", "系统跟踪号");
		return map;
	}
	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter bankFlagAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer bankFlag = (Integer) object;
				if (bankFlag != null) {
					if (bankFlag == 1) {
						return "江西银行";
					} else {
						return "汇付天下";
					}
				} else {
					return "汇付天下";
				}
			}
		};
		IValueFormatter strAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String str = (String) object;
				return StringUtils.isNotBlank(str) ? String.valueOf(str) : "";
			}
		};
		IValueFormatter userAttributeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String userAttribute = (String) object;
				if ("0".equals(userAttribute)) {
					return "无主单";
				} else if ("1".equals(userAttribute)) {
					return "有主单";
				} else if ("2".equals(userAttribute)) {
					return "线下员工";
				} else if ("3".equals(userAttribute)) {
					return "线上员工";
				}
				return "";
			}
		};
		IValueFormatter bigDecimalToStrAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal bigDecimal = (BigDecimal) object;
				return bigDecimal.toString();
			}
		};

		IValueFormatter withdrawAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer withdrawType = (Integer) object;
				if ("0".equals(withdrawType+"")) {
					return "主动提现";
				} else if ("1".equals(withdrawType+"")) {
					return "代提现";
				}
				return "";
			}
		};
		mapAdapter.put("bankFlag", bankFlagAdapter);
		mapAdapter.put("bankSeqNo", strAdapter);
		mapAdapter.put("userAttribute", userAttributeAdapter);
		mapAdapter.put("total", bigDecimalToStrAdapter);
		mapAdapter.put("credited", bigDecimalToStrAdapter);
		mapAdapter.put("actualTotal", bigDecimalToStrAdapter);
		mapAdapter.put("withdrawType", withdrawAdapter);
		return mapAdapter;
	}



}
