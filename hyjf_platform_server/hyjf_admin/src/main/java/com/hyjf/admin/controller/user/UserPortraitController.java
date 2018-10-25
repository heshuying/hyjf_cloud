package com.hyjf.admin.controller.user;

import com.hyjf.admin.beans.request.UserPortraitRequestBean;
import com.hyjf.admin.beans.vo.UserPortraitCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserPortraitService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */
@Api(value = "会员中心-用户画像接口", tags = "会员中心-用户画像接口")
@RestController
@RequestMapping("/hyjf-admin/userPortrait")
public class UserPortraitController extends BaseController {

    private static final String PERMISSIONS = "userPortrait";

    @Autowired
    private UserPortraitService userPortraitService;

    private static Logger logger = LoggerFactory.getLogger(UserPortraitController.class);


    /**
     * 列表维护画面初始化
     *
     * @return
     */
    @ApiOperation(value = "获取用户画像列表", notes = "获取用户画像列表")
    @PostMapping(value = "/selectUserPortraitList")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<UserPortraitCustomizeVO>> selectUserPortraitList(HttpServletRequest request, @RequestBody UserPortraitRequestBean userPortraitRequestBean) {
        UserPortraitRequest userPortraitRequest = new UserPortraitRequest();
        BeanUtils.copyProperties(userPortraitRequestBean, userPortraitRequest);
        UserPortraitResponse responseUserPortrait = userPortraitService.selectRecordList(userPortraitRequest);
        if (responseUserPortrait == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(responseUserPortrait)) {
            return new AdminResult<>(FAIL, responseUserPortrait.getMessage());
        }
        List<UserPortraitCustomizeVO> userPortraitCustomizeVOList = new ArrayList<UserPortraitCustomizeVO>();
        List<UserPortraitVO> userPortraitVOList = responseUserPortrait.getResultList();
        if (null != userPortraitVOList && userPortraitVOList.size() > 0) {
            userPortraitCustomizeVOList = CommonUtils.convertBeanList(userPortraitVOList, UserPortraitCustomizeVO.class);
        }
        return new AdminResult<ListResult<UserPortraitCustomizeVO>>(ListResult.build(userPortraitCustomizeVOList, responseUserPortrait.getCount()));

    }


    /**
     * 添加借款盖章用户
     *
     * @return
     */
    @ApiOperation(value = "初始化用户画像修改页面", notes = "初始化用户画像修改页面")
    @PostMapping(value = "/initUserPortraitEdit")
    @ResponseBody
    public AdminResult<UserPortraitCustomizeVO> initUserPortraitEdit(HttpServletRequest request, @RequestBody int userId) {
        UserPortraitVO userPortraitVO = userPortraitService.selectUsersPortraitByUserId(userId);
        UserPortraitCustomizeVO userPortraitCustomizeVO = new UserPortraitCustomizeVO();
        if (null != userPortraitVO) {
            BeanUtils.copyProperties(userPortraitVO, userPortraitCustomizeVO);
            return new AdminResult<UserPortraitCustomizeVO>(userPortraitCustomizeVO);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }

    /**
     * 修改用户画像
     *
     * @return
     */
    @ApiOperation(value = "修改用户画像", notes = "修改用户画像")
    @PostMapping(value = "/updateUserPortrait")
    @ResponseBody
    public AdminResult updateUserPortrait(@RequestBody UserPortraitRequestBean userPortraitRequestBean) {
        if (null==userPortraitRequestBean.getUserId()) {
            return new AdminResult<>(FAIL, "请输入用户id");
        }
        UserPortraitRequest userPortraitRequest = new UserPortraitRequest();
        BeanUtils.copyProperties(userPortraitRequestBean, userPortraitRequest);
        int updFlg = userPortraitService.updateUserPortrait(userPortraitRequest);
        if (updFlg <= 0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 导出功能
     *
     * @param request
     */
    @ApiOperation(value = "导出用户画像户", notes = "导出用户画像")
    @PostMapping(value = "/exportLoancover")
    public void exportAction(HttpServletRequest request, HttpServletResponse response,@RequestBody UserPortraitRequestBean userPortraitRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "用户画像";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        UserPortraitRequest userPortraitRequest = new UserPortraitRequest();
        BeanUtils.copyProperties(userPortraitRequestBean,userPortraitRequest);
        List<UserPortraitVO> loanCoverUserVOList = new ArrayList<UserPortraitVO>();
        UserPortraitResponse responseUserPortrait = userPortraitService.exportRecordList(userPortraitRequest);
        if (null != responseUserPortrait) {
            loanCoverUserVOList = responseUserPortrait.getResultList();
        }

        String[] titles = new String[]{"用户名", "手机号","年龄", "性别", "学历", "职业", "地域", "爱好","账户总资产（元）","账户可用金额（元）","账户待还金额（元）", "账户冻结金额（元）", "资金存留比（%）","客均收益率（%）", "累计收益（元）", "累计年化投资金额（元）", "累计充值金额（元）",
                "累计提取金额（元）", "登录活跃", "客户来源", "最后一次登录至今时长（天）", "最后一次充值至今时长（天）", "最后一次提现至今时长（天）","最后一笔回款时间", "同时投资平台数", "投龄",
                "交易笔数", "当前拥有人", "是否加微信", "投资进程", "客户投诉", "邀约客户数","邀约注册客户数","邀约充值客户数","邀约投资客户数","是否有主单","注册时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (loanCoverUserVOList != null && loanCoverUserVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < loanCoverUserVOList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                try {
                    for (int celLength = 0; celLength < titles.length; celLength++) {
                        UserPortraitVO usersPortrait = loanCoverUserVOList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 用户名
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getUserName()) ? usersPortrait.getUserName() : "");
                        } else if (celLength == 1) {//手机号
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getMobile()) ? usersPortrait.getMobile() :  "");
                        } else if (celLength == 2) {//年龄
                            cell.setCellValue(usersPortrait.getAge() != null ? usersPortrait.getAge().toString() :  "");
                        } else if (celLength == 3) {//  "性别",
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getSex()) ? usersPortrait.getSex() :  "");
                        } else if (celLength == 4) {// 学历
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getEducation()) ? usersPortrait.getEducation() :  "");
                        } else if (celLength == 5) {// 职业" "
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getOccupation()) ? usersPortrait.getOccupation() :  "");
                        } else if (celLength == 6) {// 地域
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getCity()) ? usersPortrait.getCity() :  "");
                        } else if (celLength == 7) {// 爱好
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getInterest()) ?usersPortrait.getInterest() :  "");
                        } else if (celLength == 8) {// 账户总资产（元）
                            cell.setCellValue(usersPortrait.getBankTotal()!=null ?usersPortrait.getBankTotal().toString() : "");
                        } else if (celLength == 9) {// 账户可用金额（元）
                            cell.setCellValue(usersPortrait.getBankBalance()!=null ?usersPortrait.getBankBalance().toString() :  "");
                        }else if (celLength == 10) {// "账户待还金额（元）
                            cell.setCellValue(usersPortrait.getAccountAwait()!=null ?usersPortrait.getAccountAwait().toString() :  "");
                        }else if (celLength == 11) {// "账户冻结金额（元）",
                            cell.setCellValue(usersPortrait.getBankFrost()!=null ?usersPortrait.getBankFrost().toString() :  "");
                        } else if (celLength == 12) {// "资金存留比（%）"
                            cell.setCellValue(usersPortrait.getFundRetention()!=null ?usersPortrait.getFundRetention().toString() :  "");
                        } else if (celLength == 13) {// ""客均收益率（%）",
                            cell.setCellValue(usersPortrait.getYield()!=null ?usersPortrait.getYield().toString() :  "");
                        } else if (celLength == 14) {// 累计收益
                            cell.setCellValue(usersPortrait.getInterestSum()!=null ?usersPortrait.getInterestSum().toString() :  "");
                        } else if (celLength == 15) {// 累计年化投资金额
                            cell.setCellValue(usersPortrait.getInvestSum()!=null ?usersPortrait.getInvestSum().toString() :  "");
                        } else if (celLength == 16) {// 累计充值金额
                            cell.setCellValue(usersPortrait.getRechargeSum()!=null ?usersPortrait.getRechargeSum().toString() :  "");
                        } else if (celLength == 17) {// 累计提取金额
                            cell.setCellValue(usersPortrait.getWithdrawSum()!=null ?usersPortrait.getWithdrawSum().toString() :  "");
                        } else if (celLength == 18) {// 登录活跃"
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getLoginActive()) ?usersPortrait.getLoginActive() :  "");
                        } else if (celLength == 19) {// 客户来源
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getCustomerSource()) ?usersPortrait.getCustomerSource() :  "");
                        } else if (celLength == 20) {// 最后一次登录至今时长
                            cell.setCellValue(usersPortrait.getLastLoginTime() != null ?usersPortrait.getLastLoginTime().toString() :  "");
                        } else if (celLength == 21) {// 最后一次充值至今时长
                            cell.setCellValue(usersPortrait.getLastRechargeTime() != null ?usersPortrait.getLastRechargeTime() .toString(): "");
                        } else if (celLength == 22) {// 最后一次提现至今时长
                            cell.setCellValue(usersPortrait.getLastWithdrawTime() != null ?usersPortrait.getLastWithdrawTime().toString() : "");
                        }else if (celLength == 23) {// 最后一笔回款时间
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getLastRepayTimeS()) ?usersPortrait.getLastRepayTimeS() : "");
                        }else if (celLength == 24) {// 同时投资平台数
                            cell.setCellValue(usersPortrait.getInvestPlatform() != null ?usersPortrait.getInvestPlatform().toString(): "");
                        } else if (celLength == 25) {// 投龄
                            cell.setCellValue(usersPortrait.getInvestAge() != null ?usersPortrait.getInvestAge().toString() : "");
                        }else if (celLength == 26) {// 交易笔数
                            cell.setCellValue(usersPortrait.getTradeNumber() != null ?usersPortrait.getTradeNumber().toString() : "");
                        } else if (celLength == 27) {// 当前拥有人
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getCurrentOwner()) ?usersPortrait.getCurrentOwner() : "");
                        } else if (celLength == 28) {// 是否加微信
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getAddWechat())?usersPortrait.getAddWechat() : "");
                        } else if (celLength == 29) {// 投资进程
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getInvestProcess())?usersPortrait.getInvestProcess() : "");
                        } else if (celLength == 30) {// 客户投诉
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getCustomerComplaint())?usersPortrait.getCustomerComplaint() : "");
                        } else if (celLength == 31) {// 邀约客户数
                            cell.setCellValue(usersPortrait.getInviteCustomer() != null ?usersPortrait.getInviteCustomer().toString() : "");
                        }else if (celLength == 32) {//  "邀约注册客户数"
                            cell.setCellValue(usersPortrait.getInviteRegist() != null ?usersPortrait.getInviteRegist().toString() : "");
                        }else if (celLength == 33) {//  "邀约充值客户数"
                            cell.setCellValue(usersPortrait.getInviteRecharge() != null ?usersPortrait.getInviteRecharge().toString() :"");
                        }else if (celLength == 34) {//  ""邀约投资客户数
                            cell.setCellValue(usersPortrait.getInviteTender() != null ?usersPortrait.getInviteTender().toString() : "");
                        }else if (celLength == 35) {//  ""是否有主单"
                            cell.setCellValue(usersPortrait.getAttribute() != null ?usersPortrait.getAttribute().toString() : "");
                        }else if (celLength == 36) {//  "注册时间"
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getRegTime())?usersPortrait.getRegTime() : "");
                        }
                    }
                } catch (Exception e) {
                    logger.error("错误 e :{}", e);
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("=============导出用户画像完成=============");
    }
}
