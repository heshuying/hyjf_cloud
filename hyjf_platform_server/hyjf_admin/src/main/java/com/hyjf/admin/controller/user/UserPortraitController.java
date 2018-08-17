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
import java.util.Calendar;
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
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody UserPortraitRequestBean userPortraitRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "用户画像";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        //解决IE浏览器导出列表中文乱码问题
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            // win10 ie edge 浏览器 和其他系统的ie
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        }
        // 需要输出的结果列表
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = GetDate.date_sdf.format(cal.getTime());
        String yesterdayBegin = yesterday + " 00:00:00";
        String yesterdayEnd = yesterday + " 23:59:59";
        UserPortraitRequest userPortraitRequest = new UserPortraitRequest();
        BeanUtils.copyProperties(userPortraitRequestBean,userPortraitRequest);
//        userPortraitRequest.setUserName(userName);
        userPortraitRequest.setYesterdayBeginTime(yesterdayBegin);
        userPortraitRequest.setYesterdayEndTime(yesterdayEnd);
        userPortraitRequest.setLimitFlg(true);//导出时查找全部数据
        List<UserPortraitVO> loanCoverUserVOList = new ArrayList<UserPortraitVO>();
        UserPortraitResponse responseUserPortrait = userPortraitService.selectRecordList(userPortraitRequest);
        if (null != responseUserPortrait) {
            loanCoverUserVOList = responseUserPortrait.getResultList();
        }

        String[] titles = new String[]{"用户名", "年龄", "性别", "学历", "职业", "地域", "爱好", "累计收益", "累计年化投资金额", "累计充值金额",
                "累计提取金额", "登录活跃", "客户来源", "最后一次登录至今时长", "最后一次充值至今时长", "最后一次提现至今时长", "同时投资平台数", "投龄",
                "交易笔数", "当前拥有人", "是否加微信", "投资进程", "客户投诉", "邀约客户数"};
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
                            if (usersPortrait.getUserName() != null) {
                                cell.setCellValue(usersPortrait.getUserName());
                            }
                        } else if (celLength == 1) {//年龄
                            if (usersPortrait.getAge() != null) {
                                cell.setCellValue(usersPortrait.getAge());
                            }
                        } else if (celLength == 2) {// 性别
                            if (usersPortrait.getSex() != null) {
                                cell.setCellValue(usersPortrait.getSex());
                            }
                        } else if (celLength == 3) {// 学历
                            if (usersPortrait.getEducation() != null) {
                                cell.setCellValue(usersPortrait.getEducation());
                            }
                        } else if (celLength == 4) {// 职业
                            if (usersPortrait.getOccupation() != null) {
                                cell.setCellValue(usersPortrait.getOccupation());
                            }
                        } else if (celLength == 5) {// 地域
                            if (usersPortrait.getCity() != null) {
                                cell.setCellValue(usersPortrait.getCity());
                            }
                        } else if (celLength == 6) {// 爱好
                            if (usersPortrait.getInterest() != null) {
                                cell.setCellValue(usersPortrait.getInterest());
                            }
                        } else if (celLength == 7) {// 累计收益
                            if (usersPortrait.getInterestSum() != null) {
                                cell.setCellValue(usersPortrait.getInterestSum().doubleValue());
                            }
                        } else if (celLength == 8) {// 累计年化投资金额
                            if (usersPortrait.getInvestSum() != null) {
                                cell.setCellValue(usersPortrait.getInvestSum().doubleValue());
                            }
                        } else if (celLength == 9) {// 累计充值金额
                            if (usersPortrait.getRechargeSum() != null) {
                                cell.setCellValue(usersPortrait.getRechargeSum().doubleValue());
                            }
                        } else if (celLength == 10) {// 累计提取金额
                            if (usersPortrait.getWithdrawSum() != null) {
                                cell.setCellValue(usersPortrait.getWithdrawSum().doubleValue());
                            }
                        } else if (celLength == 11) {// 登录活跃
                            if (usersPortrait.getLoginActive() != null) {
                                cell.setCellValue(usersPortrait.getLoginActive());
                            }
                        } else if (celLength == 12) {// 客户来源
                            if (usersPortrait.getCustomerSource() != null) {
                                cell.setCellValue(usersPortrait.getCustomerSource());
                            }
                        } else if (celLength == 13) {// 最后一次登录至今时长
                            if (usersPortrait.getLastLoginTime() != null) {
                                cell.setCellValue(usersPortrait.getLastLoginTime());
                            }
                        } else if (celLength == 14) {// 最后一次充值至今时长
                            if (usersPortrait.getLastRechargeTime() != null) {
                                cell.setCellValue(usersPortrait.getLastRechargeTime());
                            }
                        } else if (celLength == 15) {// 最后一次提现至今时长
                            if (usersPortrait.getLastWithdrawTime() != null) {
                                cell.setCellValue(usersPortrait.getLastWithdrawTime());
                            }
                        } else if (celLength == 16) {// 同时投资平台数
                            if (usersPortrait.getInvestPlatform() != null) {
                                cell.setCellValue(usersPortrait.getInvestPlatform());
                            }
                        } else if (celLength == 17) {// 投龄
                            if (usersPortrait.getInvestAge() != null) {
                                cell.setCellValue(usersPortrait.getInvestAge());
                            }
                        } else if (celLength == 18) {// 交易笔数
                            if (usersPortrait.getTradeNumber() != null) {
                                cell.setCellValue(usersPortrait.getTradeNumber());
                            }
                        } else if (celLength == 19) {// 当前拥有人
                            if (usersPortrait.getCurrentOwner() != null) {
                                cell.setCellValue(usersPortrait.getCurrentOwner());
                            }
                        } else if (celLength == 20) {// 是否加微信
                            if (usersPortrait.getAddWechat() != null) {
                                cell.setCellValue(usersPortrait.getAddWechat());
                            }
                        } else if (celLength == 21) {// 投资进程
                            if (usersPortrait.getInvestProcess() != null) {
                                cell.setCellValue(usersPortrait.getInvestProcess());
                            }
                        } else if (celLength == 22) {// 客户投诉
                            if (usersPortrait.getCustomerComplaint() != null) {
                                cell.setCellValue(usersPortrait.getCustomerComplaint());
                            }
                        } else if (celLength == 23) {// 邀约客户数
                            if (usersPortrait.getInviteCustomer() != null) {
                                cell.setCellValue(usersPortrait.getInviteCustomer());
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("错误 e :{}", e);
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("=============导出借款盖章用户完成=============");
    }
}
