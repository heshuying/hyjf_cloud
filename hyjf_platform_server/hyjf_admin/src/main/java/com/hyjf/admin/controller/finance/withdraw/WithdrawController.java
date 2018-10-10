package com.hyjf.admin.controller.finance.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.WithdrawBeanAPIRequest;
import com.hyjf.admin.beans.vo.AdminWithdrawAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.finance.withdraw.WithdrawService;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public String confirmWithdrawAction(HttpServletRequest request,@RequestBody WithdrawBeanAPIRequest form) {
		JSONObject ret = new JSONObject();
		Integer userId = form.getUserId();
		String nid = form.getNid();
		Integer status = form.getStatus();// 0 失败； 1成功

		if (Validator.isNull(userId) || Validator.isNull(nid) || Validator.isNull(status)) {
			ret.put("status", "error");
			ret.put("result", "确认发生错误,请重新操作!");
			logger.error("withdrawConfirm", new Exception("参数不正确[userId=" + userId + "]"));
			return ret.toString();
		}
		// 取出账户信息
		AccountVO account = this.withdrawService.getAccountByUserId(userId);
		if (Validator.isNull(account)) {
			ret.put("status", "error");
			ret.put("result", "确认发生错误,请重新操作!");
			logger.error("withdrawConfirm", new Exception("[userId=" + userId + "]下账户异常！"));
			return ret.toString();
		}
		// 获取充值信息
		AccountWithdrawVO accountwithdraw = this.withdrawService.queryAccountwithdrawByNid(nid, userId);
		if (Validator.isNull(accountwithdraw)) {
			ret.put("status", "error");
			ret.put("result", "确认发生错误,请重新操作!");
			logger.error("withdrawConfirm", new Exception("[nid=" + nid + "]不存在！"));
			return ret.toString();
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
				e.printStackTrace();
			}
		} else {
			// 充值失败,更新充值订单
			try {
				isAccountUpdate = this.withdrawService.updateAccountAfterWithdrawFail(userId, nid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 提现确认成功
		if (isAccountUpdate) {
			ret.put("status", "success");
			ret.put("result", "提现确认操作成功!");
		} else {
			ret.put("status", "error");
			ret.put("result", "提现确认发生错误,请重新操作!");
		}

		return ret.toString();
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
	public void exportWithdrawExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody WithdrawBeanAPIRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "提现列表";

		// 取得数据
		form.setLimitStart(-1);
		form.setLimitEnd(-1);
		// 设置默认查询时间
		if (StringUtils.isEmpty(form.getAddtimeStartSrch())) {
			form.setAddtimeStartSrch(GetDate.getDate("yyyy-MM-dd"));
		}
		if (StringUtils.isEmpty(form.getAddtimeEndSrch())) {
			form.setAddtimeEndSrch(GetDate.getDate("yyyy-MM-dd"));
		}
		List<WithdrawCustomizeVO> recordList = null;
		WithdrawCustomizeResponse recordListResponse=this.withdrawService.getWithdrawRecordList(CommonUtils.convertBean(form, WithdrawBeanRequest.class));
		if (Validator.isNotNull(recordListResponse)){
			recordList=recordListResponse.getResultList();
		}

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

		String[] titles = new String[] { "序号", "用户名", "电子帐号", "资金托管平台", "流水号", "手机号", "用户角色", "用户属性（当前）", "用户所属一级分部（当前）", "用户所属二级分部（当前）", "用户所属团队（当前）", "推荐人用户名（当前）", "推荐人姓名（当前）", "推荐人所属一级分部（当前）",
				"推荐人所属二级分部（当前）", "推荐人所属团队（当前）", "订单号", "提现金额", "手续费", "到账金额", "实际出账金额", "提现银行", "提现方式", "提现银行卡号", "提交时间", "提现平台", "处理状态" ,"发送日期" ,"发送时间" ,"系统跟踪号" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

		if (CollectionUtils.isNotEmpty(recordList)) {

			int sheetCount = 1;
			int rowNum = 0;

			for (int i = 0; i < recordList.size(); i++) {
				rowNum++;
				if (i != 0 && i % 60000 == 0) {
					sheetCount++;
					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}

				// 新建一行
				Row row = sheet.createRow(rowNum);
				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					WithdrawCustomizeVO bean = recordList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 用户名
					else if (celLength == 1) {
						cell.setCellValue(bean.getUsername());
					}
					// 电子帐号
					else if (celLength == 2) {
						cell.setCellValue(bean.getAccountId());
					}
					// 资金托管平台
					else if (celLength == 3) {
						if (bean.getBankFlag() != null) {
							if (bean.getBankFlag() == 1) {
								cell.setCellValue("江西银行");
							} else {
								cell.setCellValue("汇付天下");
							}
						} else {
							cell.setCellValue("汇付天下");
						}

					}
					// 流水号
					else if (celLength == 4) {
						cell.setCellValue(bean.getSeqNo()==null ? "" : String.valueOf(bean.getSeqNo()));
					}

					// 手机号
					else if (celLength == 5) {
						cell.setCellValue(bean.getMobile());
					}
					// 用户角色
					else if (celLength == 6) {
						cell.setCellValue(bean.getRoleid());
					}

					// 用户属性（当前）
					else if (celLength == 7) {
						cell.setCellValue(bean.getUserAttribute());
						if ("0".equals(bean.getUserAttribute())) {
							cell.setCellValue("无主单");
						} else if ("1".equals(bean.getUserAttribute())) {
							cell.setCellValue("有主单");
						} else if ("2".equals(bean.getUserAttribute())) {
							cell.setCellValue("线下员工");
						} else if ("3".equals(bean.getUserAttribute())) {
							cell.setCellValue("线上员工");
						}
					}
					// 用户所属一级分部（当前）
					else if (celLength == 8) {
						cell.setCellValue(bean.getUserRegionName());
					}
					// 用户所属二级分部（当前）
					else if (celLength == 9) {
						cell.setCellValue(bean.getUserBranchName());
					}
					// 用户所属团队（当前）
					else if (celLength == 10) {
						cell.setCellValue(bean.getUserDepartmentName());
					}
					// 推荐人用户名（当前）
					else if (celLength == 11) {
						cell.setCellValue(bean.getReferrerName());
					}
					// 推荐人姓名（当前）
					else if (celLength == 12) {
						cell.setCellValue(bean.getReferrerTrueName());
					}
					// 推荐人所属一级分部（当前）
					else if (celLength == 13) {
						cell.setCellValue(bean.getReferrerRegionName());
					}
					// 推荐人所属二级分部（当前）
					else if (celLength == 14) {
						cell.setCellValue(bean.getReferrerBranchName());
					}
					// 推荐人所属团队（当前）
					else if (celLength == 15) {
						cell.setCellValue(bean.getReferrerDepartmentName());
					}
					// 订单号
					else if (celLength == 16) {
						cell.setCellValue(bean.getOrdid());
					}
					// 提现金额
					else if (celLength == 17) {
						cell.setCellValue(bean.getTotal().toString());
					}
					// 手续费
					else if (celLength == 18) {
						cell.setCellValue(bean.getFee());
					}
					// 到账金额
					else if (celLength == 19) {
						cell.setCellValue(bean.getCredited().toString());
					}
					// 实际出账金额
					else if (celLength == 20) {
						cell.setCellValue(bean.getTotal().toString());
					}
					// 提现银行
					else if (celLength == 21) {
						cell.setCellValue(bean.getBank());
					}
					// 提现方式
					else if (celLength == 22) {
					    if ("0".equals(bean.getWithdrawType()+"")) {
					        cell.setCellValue("主动提现");
                        } else if ("1".equals(bean.getWithdrawType()+"")) {
                            cell.setCellValue("代提现");
                        }
                    }
					// 提现银行卡号
					else if (celLength == 23) {
						cell.setCellValue(bean.getAccount());
					}
					// 提交时间
					else if (celLength == 24) {
						cell.setCellValue(bean.getAddtimeStr());
					}
					// 提现平台
					else if (celLength == 25) {
						cell.setCellValue(bean.getClientStr());
					}
					// 处理状态
					else if (celLength == 26) {
						cell.setCellValue(bean.getStatusStr());
					}
					else if (celLength == 27) {
						cell.setCellValue(bean.getTxDateS());
					} 
					else if (celLength == 28) {
						cell.setCellValue(bean.getTxTimeS());
					} 
					else if (celLength == 29) {
						cell.setCellValue(bean.getSeqNo()==null ? "" : String.valueOf(bean.getSeqNo()));
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(request, response, workbook, titles, fileName);

	}
	// ***********************************************提现画面End****************************************************

}
