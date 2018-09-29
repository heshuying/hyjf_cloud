/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bankaccountmanage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.response.BankAccountManageResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.util.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/28 15:57
 */
@Api(value = "账户管理(银行)", tags = "资金中心-账户管理(银行)")
@RestController
@RequestMapping("/hyjf-admin/bank_account_manage")
public class BankAccountManageController extends BaseController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * 银行账户管理页面
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "账户管理明细", notes = "银行账户管理")
    @PostMapping(value = "/search")
    @ResponseBody
    public AdminResult<BankAccountManageResponseBean> search(@RequestBody BankAccountManageRequest request) {

        BankAccountManageResponseBean bean = new BankAccountManageResponseBean();

        Integer count = bankAccountManageService.selectAccountInfoCount(request);
        count = (count == null) ? 0 : count;
        bean.setTotal(count);
        //分页参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count > 0) {
            List<BankAccountManageCustomizeVO> bankAccountManageCustomizeVO = bankAccountManageService.queryAccountInfos(request);
            if (bankAccountManageCustomizeVO == null || bankAccountManageCustomizeVO.size() == 0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            List<BankAccountManageCustomizeVO> recordList = bankAccountManageCustomizeVO;
            bean.setRecordList(recordList);
        }
        return new AdminResult(bean);
    }

//    /**
//     * 导出资金明细列表
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @ApiOperation(value = "导出资金明细列表", notes = "银行账户管理")
//    @PostMapping("/export_account_detail_excel")
//    public void exportAccountDetailExcel(@RequestBody BankAccountManageRequest request, HttpServletResponse response) {
//        // 表格sheet名称
//        String sheetName = "资金明细";
//        // 取得数据
//        List<BankAccountManageCustomizeVO> recordList = this.bankAccountManageService.queryAccountDetails(request);
//
//        String fileName = null;
//        try {
//            fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            logger.error("转码错误....", e);
//        }
//
//        String[] titles = new String[]{"序号", "明细ID", "用户名", "推荐人", "推荐组", "订单号", "操作类型", "交易类型", "操作金额", "可用余额", "冻结金额", "备注说明", "时间"};
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//
//        // 生成一个表格
//        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//        if (recordList != null && recordList.size() > 0) {
//
//            int sheetCount = 1;
//            int rowNum = 0;
//
//            for (int i = 0; i < recordList.size(); i++) {
//                rowNum++;
//                if (i != 0 && i % 60000 == 0) {
//                    sheetCount++;
//                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//                    rowNum = 1;
//                }
//
//                // 新建一行
//                Row row = sheet.createRow(rowNum);
//                // 循环数据
//                for (int celLength = 0; celLength < titles.length; celLength++) {
//                    BankAccountManageCustomizeVO accountDetailCustomize = recordList.get(i);
//
//                    // 创建相应的单元格
//                    Cell cell = row.createCell(celLength);
//
//                    // 序号
//                    if (celLength == 0) {
//                        cell.setCellValue(i + 1);
//                    }
//
//                    // 明细ID
//                    else if (celLength == 1) {
//                        cell.setCellValue(accountDetailCustomize.getId());
//                    }
//                    // 用户名
//                    else if (celLength == 2) {
//                        cell.setCellValue(accountDetailCustomize.getUsername());
//                    }
//                    // 推荐人
//                    else if (celLength == 3) {
//                        cell.setCellValue(accountDetailCustomize.getReferrerName());
//                    }
//                    // 推荐组
//                    else if (celLength == 4) {
//                        cell.setCellValue(accountDetailCustomize.getReferrerGroup());
//                    }
//                    // 订单号
//                    else if (celLength == 5) {
//                        cell.setCellValue(accountDetailCustomize.getNid());
//                    }
//                    // 操作类型
//                    else if (celLength == 6) {
//                        cell.setCellValue(accountDetailCustomize.getType());
//                    }
//                    // 交易类型
//                    else if (celLength == 7) {
//                        cell.setCellValue(accountDetailCustomize.getTradeType());
//                    }
//                    // 操作金额
//                    else if (celLength == 8) {
//                        cell.setCellValue(accountDetailCustomize.getAmount() + "");
//                    }
//                    // 可用余额
//                    else if (celLength == 9) {
//                        cell.setCellValue(accountDetailCustomize.getBalance() + "");
//                    }
//                    // 冻结金额
//                    else if (celLength == 10) {
//                        cell.setCellValue(accountDetailCustomize.getFrost() + "");
//                    }
//                    // 备注说明
//                    else if (celLength == 11) {
//                        cell.setCellValue(accountDetailCustomize.getRemark());
//                    }
//                    // 时间
//                    else if (celLength == 12) {
//                        cell.setCellValue(accountDetailCustomize.getCreateTime());
//                    }
//                }
//            }
//        }
//        // 导出
//        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//
//    }

    /**
     * 更新
     *
     * @param userIdStr
     * @return
     */
    @ApiOperation(value = "更新账户余额", notes = "银行账户管理")
    @ResponseBody
    @GetMapping("/update_balance_action/{userIdStr}")
    public JSONObject updateBalanceAction(@PathVariable String userIdStr) {

        JSONObject ret = new JSONObject();

        // 用户ID
        Integer userId = GetterUtil.getInteger(userIdStr);
        if (null == userId || 0 ==userId) {
            ret.put("status", FAIL);
            ret.put("statusDesc", "更新发生错误,请重新操作!");
            logger.error("参数不正确[userId=" + userId + "]", BankAccountManageController.class);
            return ret;
        }

        // 取得用户在银行的账户信息
        AccountVO accountVO = bankAccountManageService.getAccountByUserId(userId);
        // 用户未开户时,返回错误信息
        if (accountVO == null || StringUtils.isBlank(accountVO.getAccountId())) {
            ret.put("status", FAIL);
            ret.put("statusDesc", "用户未开户!");
            logger.error("参数不正确[userId=" + userId + "]", BankAccountManageController.class);
            return ret;
        }

        // 账户可用余额
        BigDecimal balance = BigDecimal.ZERO;
        // 账户冻结金额
        BigDecimal frost = BigDecimal.ZERO;
        // 账面余额
        BigDecimal currBalance = BigDecimal.ZERO;

        BankCallBean bean = new BankCallBean();
        // 版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 交易代码
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
        // 交易日期
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        // 交易时间
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        // 交易流水号
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        // 交易渠道
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountVO.getAccountId());
        // 订单号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.valueOf(userId)));
        // 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogUserId(String.valueOf(userId));
        // 平台
        bean.setLogClient(0);
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean == null) {
                logger.error("调用银行接口发生错误", BankAccountManageController.class);
                ret.put("status", FAIL);
                ret.put("statusDesc", "调用银行接口发生错误");
                return ret;
            }
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                // 账户余额
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
                // 账面余额
                currBalance = new BigDecimal(resultBean.getCurrBal().replace(",", ""));
                // 账户冻结金额
                frost = currBalance.subtract(balance);
            } else {
                logger.error("调用银行接口发生错误", BankAccountManageController.class);
                ret.put("status", FAIL);
                ret.put("statusDesc", "调用银行接口发生错误");
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int cnt = 0;

        try {
            // 更新处理
            cnt = this.bankAccountManageService.updateAccount(userId, balance, frost);
        } catch (Exception e) {
            logger.error("余额更新异常！", BankAccountManageController.class, e);
        }

        // 返现成功
        if (cnt > 0) {
            ret.put("status", SUCCESS);
            ret.put("statusDesc", "更新操作成功!");
        } else {
            ret.put("status", FAIL);
            ret.put("statusDesc", "更新发生错误,请重新操作!");
        }

        return ret;
    }

    /**
     * 开始线下充值对账 add by liushouyi
     *
     * @param request
     * @param form
     * @return
     */
    @ApiOperation(value = "开始线下充值对账", notes = "银行账户管理")
    @ResponseBody
    @PostMapping("/start_account_check_action")
    public JSONObject bankAccountCheckAction(HttpServletRequest request, @RequestBody BankAccountManageCustomizeVO form) {
        Integer userId = Integer.valueOf(form.getUserId());
        JSONObject ret = new JSONObject();
        String startTime = form.getStartTime();
        String endTime = form.getEndTime();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            ret.put("statusDesc", "开始时间或结束时间不得为空!");
            ret.put("status", FAIL);
            return ret;
        }
        String result = this.bankAccountManageService.updateAccountCheck(userId, startTime, endTime);

        if ("success".equals(result)) {
            ret.put("statusDesc", "处理成功");
            ret.put("status", SUCCESS);
        } else {
            ret.put("statusDesc", result);
            ret.put("status", FAIL);
        }

        return ret;
    }

     /**
     * 取得部门信息
     *
     * @return
     */
    @ApiOperation(value = "取得部门信息", notes = "银行账户管理")
    @GetMapping(value = "/get_crm_department_list")
    public JSONObject getCrmDepartmentListAction() {
        // 部门
        String[] list = new String[]{};

        JSONArray ja = this.bankAccountManageService.getCrmDepartmentList(list);
        if (ja != null) {

            //在部门树中加入 0=部门（其他）,因为前端不能显示id=0,就在后台将0=其他转换为-10086=其他
            JSONObject jo = new JSONObject();

            jo.put("value", "-10086");
            jo.put("title", "其他");
            JSONArray array = new JSONArray();
            jo.put("key", UUID.randomUUID());
            jo.put("children", array);

            ja.add(jo);
            JSONObject ret= new JSONObject();
            ret.put("data", ja);
            ret.put("status", SUCCESS);
            ret.put("statusDesc", "成功");
            return ret;
        }
        return new JSONObject();
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出资金明细列表", notes = "银行账户管理")
    @PostMapping("/export_account_detail_excel")
	public void exportAccountsExcel(@RequestBody BankAccountManageRequest request, HttpServletResponse response) throws Exception {
		// 表格sheet名称
		String sheetName = "账户数据";

		
//		// 部门
//		if (Validator.isNotNull(request.getCombotreeSrch())) {
//			if (form.getCombotreeSrch().contains(StringPool.COMMA)) {
//				String[] list = form.getCombotreeSrch().split(StringPool.COMMA);
//				form.setCombotreeListSrch(list);
//			} else {
//				form.setCombotreeListSrch(new String[] { form.getCombotreeSrch() });
//			}
//		}
	      List<BankAccountManageCustomizeVO> recordList = this.bankAccountManageService.queryAccountDetails(request);

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

		String[] titles = new String[] { "用户ID", "用户名", "分公司", "分部", "团队", "资产总额", "电子账号", "会员等级", "可用金额", "冻结金额", "银行待收", "银行待还", "银行账户", "用户手机号", "用户属性（当前）", "用户角色", "一级分部（当前）", "二级分部（当前）",
				"三级分部（当前）", "推荐人用户名（当前）", "推荐人姓名（当前）", "推荐人所属一级分部（当前）", "推荐人所属二级分部（当前）", "推荐人所属三级分部（当前）" };

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

		if (recordList != null && recordList.size() > 0) {
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
					BankAccountManageCustomizeVO bean = recordList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					// 用户ID
					if (celLength == 0) {
						cell.setCellValue(bean.getUserId());
					}
					// 用户名
					else if (celLength == 1) {
						cell.setCellValue(bean.getUsername());
					}
					// 分公司
					else if (celLength == 2) {
						cell.setCellValue(bean.getRegionName());
					}
					// 分部
					else if (celLength == 3) {
						cell.setCellValue(bean.getBranchName());
					}
					// 团队
					else if (celLength == 4) {
						cell.setCellValue(bean.getDepartmentName());
					}
					// 资产总额
					else if (celLength == 5) {
						if (bean.getBankTotal() != null) {
							cell.setCellValue(String.valueOf(bean.getBankTotal()));
						} else {
							cell.setCellValue("0.00");
						}
					}
					// 电子账号
					else if (celLength == 6) {
						cell.setCellValue(bean.getAccount() == null ? "" : bean.getAccount());
					}
					// 会员等级
					else if (celLength == 7) {
						cell.setCellValue(bean.getVipName() == null ? "" : bean.getVipName());
					}
					// 可用金额
					else if (celLength == 8) {
						cell.setCellValue(bean.getBankBalance() == null ? "0.00" : bean.getBankBalance().toString());
					}
					// 冻结金额
					else if (celLength == 9) {
						cell.setCellValue(bean.getBankFrost() == null ? "0.00" : bean.getBankFrost().toString());
					}
					// 待收金额
					else if (celLength == 10) {
						cell.setCellValue(bean.getBankAwait() == null ? "0.00" : bean.getBankAwait().toString());
					}
					// 待还金额
					else if (celLength == 11) {
						cell.setCellValue(bean.getBankWaitRepay() == null ? "0.00" : bean.getBankWaitRepay().toString());
					}
					// 银行账户
					else if (celLength == 12) {
						cell.setCellValue((bean.getBankBalanceCash() == null ? "0.00" : bean.getBankBalanceCash().toString()) + "/"
								+ (bean.getBankFrostCash() == null ? "0.00" : bean.getBankFrostCash().toString()));
					}
					// 手机号
					else if (celLength == 13) {
						cell.setCellValue(bean.getMobile());
					}
					// 用户属性（当前）
					else if (celLength == 14) {
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
					// 角色
					else if (celLength == 15) {
						cell.setCellValue(bean.getRoleid());
					}
					// 用户所属一级分部（当前）
					else if (celLength == 16) {
						cell.setCellValue(bean.getUserRegionName());
					}
					// 用户所属二级分部（当前）
					else if (celLength == 17) {
						cell.setCellValue(bean.getUserBranchName());
					}
					// 用户所属三级分部（当前）
					else if (celLength == 18) {
						cell.setCellValue(bean.getUserDepartmentName());
					}
					// 推荐人用户名（当前）
					else if (celLength == 19) {
						cell.setCellValue(bean.getReferrerName());
					}
					// 推荐人姓名（当前）
					else if (celLength == 20) {
						cell.setCellValue(bean.getReferrerTrueName());
					}
					// 推荐人所属一级分部（当前）
					else if (celLength == 21) {
						cell.setCellValue(bean.getReferrerRegionName());
					}
					// 推荐人所属二级分部（当前）
					else if (celLength == 22) {
						cell.setCellValue(bean.getReferrerBranchName());
					}
					// 推荐人所属三级分部（当前）
					else if (celLength == 23) {
						cell.setCellValue(bean.getReferrerDepartmentName());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

}
