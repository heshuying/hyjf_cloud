package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.UserPortraitRequestBean;
import com.hyjf.admin.beans.vo.UserPortraitCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserPortraitService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */
@Api(value = "会员中心-用户画像接口", tags = "会员中心-用户画像接口")
@RestController
@RequestMapping("/hyjf-admin/userPortrait")
public class UserPortraitController extends BaseController {

    public static final String PERMISSIONS = "userPortrait";

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
        logger.info("---获取用户画像列表 by param---  " + JSONObject.toJSON(userPortraitRequestBean));
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        UserPortraitRequest userPortraitRequest = new UserPortraitRequest();
        BeanUtils.copyProperties(userPortraitRequestBean, userPortraitRequest);
        logger.info("---userPortraitRequest ---  " + JSONObject.toJSON(userPortraitRequest));
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
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for (UserPortraitVO registRecordVO:userPortraitVOList){
                    registRecordVO.setMobile(AsteriskProcessUtil.getDesensitizationValue(registRecordVO.getMobile()));
                }
            }
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
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
     * 导入当前拥有人
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入当前拥有人", notes = "导入当前拥有人")
    @PostMapping(value = "/importCurrentOwner")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_IMPORT)
    public AdminResult importCurrentOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("当前拥有人数据导入开始......");
        StringResponse stringResponse = userPortraitService.importBatch(request);
        if (stringResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(stringResponse)) {
            return new AdminResult<>(FAIL, stringResponse.getMessage());
        }
        logger.info("当前拥有人数据导入结束......");
        return new AdminResult(stringResponse);
    }

    /**
     * 导出功能
     *
     * @param request
     */
    //@ApiOperation(value = "导出用户画像户", notes = "导出用户画像")
    //@PostMapping(value = "/exportLoancover")
    /*public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody UserPortraitRequestBean userPortraitRequestBean) throws Exception {
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

        String[] titles = new String[]{"用户名", "手机号","年龄", "性别", "学历", "职业", "地域", "爱好","账户总资产（元）","账户可用金额（元）","账户待还金额（元）", "账户冻结金额（元）", "资金存留比（%）","客均收益率（%）", "累计收益（元）", "累计年化出借金额（元）", "累计充值金额（元）",
                "累计提取金额（元）", "登录活跃", "客户来源", "最后一次登录至今时长（天）", "最后一次充值至今时长（天）", "最后一次提现至今时长（天）","最后一笔回款时间", "同时出借平台数", "投龄",
                "交易笔数", "当前拥有人", "是否加微信", "出借进程", "客户投诉", "邀约客户数","邀约注册客户数","邀约充值客户数","邀约出借客户数","是否有主单","注册时间"};
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
                        } else if (celLength == 15) {// 累计年化出借金额
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
                        }else if (celLength == 24) {// 同时出借平台数
                            cell.setCellValue(usersPortrait.getInvestPlatform() != null ?usersPortrait.getInvestPlatform().toString(): "");
                        } else if (celLength == 25) {// 投龄
                            cell.setCellValue(usersPortrait.getInvestAge() != null ?usersPortrait.getInvestAge().toString() : "");
                        }else if (celLength == 26) {// 交易笔数
                            cell.setCellValue(usersPortrait.getTradeNumber() != null ?usersPortrait.getTradeNumber().toString() : "");
                        } else if (celLength == 27) {// 当前拥有人
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getCurrentOwner()) ?usersPortrait.getCurrentOwner() : "");
                        } else if (celLength == 28) {// 是否加微信
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getAddWechat())?usersPortrait.getAddWechat() : "");
                        } else if (celLength == 29) {// 出借进程
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getInvestProcess())?usersPortrait.getInvestProcess() : "");
                        } else if (celLength == 30) {// 客户投诉
                            cell.setCellValue(StringUtils.isNoneBlank(usersPortrait.getCustomerComplaint())?usersPortrait.getCustomerComplaint() : "");
                        } else if (celLength == 31) {// 邀约客户数
                            cell.setCellValue(usersPortrait.getInviteCustomer() != null ?usersPortrait.getInviteCustomer().toString() : "");
                        }else if (celLength == 32) {//  "邀约注册客户数"
                            cell.setCellValue(usersPortrait.getInviteRegist() != null ?usersPortrait.getInviteRegist().toString() : "");
                        }else if (celLength == 33) {//  "邀约充值客户数"
                            cell.setCellValue(usersPortrait.getInviteRecharge() != null ?usersPortrait.getInviteRecharge().toString() :"");
                        }else if (celLength == 34) {//  ""邀约出借客户数
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
    }*/

    /**
     * 导出excel
     *
     * @param userPortraitRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出用户画像户", notes = "导出用户画像")
    @PostMapping(value = "/exportLoancover")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportActionLoa(HttpServletRequest request, HttpServletResponse response, @RequestBody UserPortraitRequestBean userPortraitRequestBean) throws Exception {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        // 需要输出的结果列表
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = GetDate.date_sdf.format(cal.getTime());
        String yesterdayBegin = yesterday + " 00:00:00";
        String yesterdayEnd = yesterday + " 23:59:59";
        // 封装查询条件
        UserPortraitRequest userPortraitRequest = new UserPortraitRequest();
        BeanUtils.copyProperties(userPortraitRequestBean,userPortraitRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "用户画像";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        userPortraitRequest.setYesterdayBeginTime(yesterdayBegin);
        userPortraitRequest.setYesterdayEndTime(yesterdayEnd);
        //导出时查找全部数据
        userPortraitRequest.setLimitFlg(true);
        //请求第一页5000条
        userPortraitRequest.setPageSize(defaultRowMaxCount);
        userPortraitRequest.setCurrPage(1);
        // 需要输出的结果列表
        UserPortraitResponse responseUserPortrait = userPortraitService.selectRecordList(userPortraitRequest);
        Integer totalCount = responseUserPortrait.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if(!isShow){
            //如果没有查看脱敏权限,显示加星
            mapValueAdapter = buildValueAdapterAccAdaptertAsterisked();
        }
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            for(UserPortraitVO userPortraitVO : responseUserPortrait.getResultList()){
                // 用户名
                userPortraitVO.setUserName(StringUtils.isNoneBlank(userPortraitVO.getUserName()) ? userPortraitVO.getUserName() : "");
                //手机号
                userPortraitVO.setMobile(StringUtils.isNoneBlank(userPortraitVO.getMobile()) ? userPortraitVO.getMobile() :  "");
                // 性别
                userPortraitVO.setSex(StringUtils.isNoneBlank(userPortraitVO.getSex()) ? userPortraitVO.getSex() :  "");
                // 学历
                userPortraitVO.setEducation(StringUtils.isNoneBlank(userPortraitVO.getEducation()) ? userPortraitVO.getEducation() :  "");
                // 职业
                userPortraitVO.setOccupation(StringUtils.isNoneBlank(userPortraitVO.getOccupation()) ? userPortraitVO.getOccupation() :  "");
                // 地域
                userPortraitVO.setCity(StringUtils.isNoneBlank(userPortraitVO.getCity()) ? userPortraitVO.getCity() :  "");
                // 爱好
                userPortraitVO.setInterest(StringUtils.isNoneBlank(userPortraitVO.getInterest()) ?userPortraitVO.getInterest() :  "");
                // 登录活跃
                userPortraitVO.setLoginActive(StringUtils.isNoneBlank(userPortraitVO.getLoginActive()) ?userPortraitVO.getLoginActive() :  "");
                // 客户来源
                userPortraitVO.setCustomerSource(StringUtils.isNoneBlank(userPortraitVO.getCustomerSource()) ?userPortraitVO.getCustomerSource() :  "");
                // 最后一笔回款时间
                userPortraitVO.setLastRepayTimeS(StringUtils.isNoneBlank(userPortraitVO.getLastRepayTimeS()) ?userPortraitVO.getLastRepayTimeS() : "");
                // 当前拥有人
                userPortraitVO.setCurrentOwner(StringUtils.isNoneBlank(userPortraitVO.getCurrentOwner()) ?userPortraitVO.getCurrentOwner() : "");
                // 是否加微信
                userPortraitVO.setAddWechat(StringUtils.isNoneBlank(userPortraitVO.getAddWechat())?userPortraitVO.getAddWechat() : "");
                // 出借进程
                userPortraitVO.setInvestProcess(StringUtils.isNoneBlank(userPortraitVO.getInvestProcess())?userPortraitVO.getInvestProcess() : "");
                // 客户投诉
                userPortraitVO.setCustomerComplaint(StringUtils.isNoneBlank(userPortraitVO.getCustomerComplaint())?userPortraitVO.getCustomerComplaint() : "");
                // 注册时间
                userPortraitVO.setRegTime(StringUtils.isNoneBlank(userPortraitVO.getRegTime())?userPortraitVO.getRegTime() : "");
            }
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, responseUserPortrait.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            userPortraitRequest.setPageSize(defaultRowMaxCount);
            userPortraitRequest.setCurrPage(i+1);
            UserPortraitResponse responseUserPortrait2 = userPortraitService.selectRecordList(userPortraitRequest);
            if (responseUserPortrait2 != null && responseUserPortrait2.getResultList().size()> 0) {
                for(UserPortraitVO userPortraitVO : responseUserPortrait2.getResultList()){
                    // 用户名
                    userPortraitVO.setUserName(StringUtils.isNoneBlank(userPortraitVO.getUserName()) ? userPortraitVO.getUserName() : "");
                    //手机号
                    userPortraitVO.setMobile(StringUtils.isNoneBlank(userPortraitVO.getMobile()) ? userPortraitVO.getMobile() :  "");
                    // 性别
                    userPortraitVO.setSex(StringUtils.isNoneBlank(userPortraitVO.getSex()) ? userPortraitVO.getSex() :  "");
                    // 学历
                    userPortraitVO.setEducation(StringUtils.isNoneBlank(userPortraitVO.getEducation()) ? userPortraitVO.getEducation() :  "");
                    // 职业
                    userPortraitVO.setOccupation(StringUtils.isNoneBlank(userPortraitVO.getOccupation()) ? userPortraitVO.getOccupation() :  "");
                    // 地域
                    userPortraitVO.setCity(StringUtils.isNoneBlank(userPortraitVO.getCity()) ? userPortraitVO.getCity() :  "");
                    // 爱好
                    userPortraitVO.setInterest(StringUtils.isNoneBlank(userPortraitVO.getInterest()) ?userPortraitVO.getInterest() :  "");
                    // 登录活跃
                    userPortraitVO.setLoginActive(StringUtils.isNoneBlank(userPortraitVO.getLoginActive()) ?userPortraitVO.getLoginActive() :  "");
                    // 客户来源
                    userPortraitVO.setCustomerSource(StringUtils.isNoneBlank(userPortraitVO.getCustomerSource()) ?userPortraitVO.getCustomerSource() :  "");
                    // 最后一笔回款时间
                    userPortraitVO.setLastRepayTimeS(StringUtils.isNoneBlank(userPortraitVO.getLastRepayTimeS()) ?userPortraitVO.getLastRepayTimeS() : "");
                    // 当前拥有人
                    userPortraitVO.setCurrentOwner(StringUtils.isNoneBlank(userPortraitVO.getCurrentOwner()) ?userPortraitVO.getCurrentOwner() : "");
                    // 是否加微信
                    userPortraitVO.setAddWechat(StringUtils.isNoneBlank(userPortraitVO.getAddWechat())?userPortraitVO.getAddWechat() : "");
                    // 出借进程
                    userPortraitVO.setInvestProcess(StringUtils.isNoneBlank(userPortraitVO.getInvestProcess())?userPortraitVO.getInvestProcess() : "");
                    // 客户投诉
                    userPortraitVO.setCustomerComplaint(StringUtils.isNoneBlank(userPortraitVO.getCustomerComplaint())?userPortraitVO.getCustomerComplaint() : "");
                    // 注册时间
                    userPortraitVO.setRegTime(StringUtils.isNoneBlank(userPortraitVO.getRegTime())?userPortraitVO.getRegTime() : "");
                }
                //导出增行
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  responseUserPortrait2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("mobile", "手机号");
        map.put("age", "年龄");
        map.put("sex", "性别");
        map.put("education", "学历");
        map.put("occupation", "职业");
        map.put("city", "地域");
        map.put("interest", "爱好");
        map.put("bankTotal", "账户总资产（元）");
        map.put("bankBalance", "账户可用金额（元）");
        map.put("accountAwait", "账户待还金额（元）");
        map.put("bankFrost", "账户冻结金额（元）");
        map.put("fundRetention", "资金存留比（%）");
        map.put("yield", "客均收益率（%）");
        map.put("interestSum", "累计收益（元）");
        map.put("investSum", "累计年化出借金额（元）");
        map.put("rechargeSum", "累计充值金额（元）");
        map.put("withdrawSum", "累计提取金额（元）");
        map.put("loginActive", "登录活跃");
        map.put("customerSource", "客户来源");
        map.put("lastLoginTime", "最后一次登录至今时长（天）");
        map.put("lastRechargeTime", "最后一次充值至今时长（天）");
        map.put("lastWithdrawTime", "最后一次提现至今时长（天）");
        map.put("lastRepayTimeS", "最后一笔回款时间");
        map.put("investPlatform", "同时出借平台数");
        map.put("investAge", "投龄");
        map.put("tradeNumber", "交易笔数");
        map.put("currentOwner", "当前拥有人");
        map.put("addWechat", "是否加微信");
        map.put("investProcess", "出借进程");
        map.put("customerComplaint", "客户投诉");
        map.put("inviteCustomer", "邀约客户数");
        map.put("inviteRegist", "邀约注册客户数");
        map.put("inviteRecharge", "邀约充值客户数");
        map.put("inviteTender", "邀约出借客户数");
        map.put("attribute", "是否有主单");
        map.put("regTime", "注册时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter ageAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer age = (Integer) object;
                return age != null ? age.toString() :  "";
            }
        };
        mapAdapter.put("age", ageAdapter);
        // 账户总资产（元）
        IValueFormatter bankTotalAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankTotal = (BigDecimal) object;
                return bankTotal != null ? bankTotal.toString() :  "";
            }
        };
        mapAdapter.put("bankTotal", bankTotalAdapter);
        // 账户可用金额（元）
        IValueFormatter bankBalanceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankBalance = (BigDecimal) object;
                return bankBalance != null ? bankBalance.toString() :  "";
            }
        };
        mapAdapter.put("bankBalance", bankBalanceAdapter);
        // "账户待还金额（元）
        IValueFormatter accountAwaitAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal accountAwait = (BigDecimal) object;
                return accountAwait != null ? accountAwait.toString() :  "";
            }
        };
        mapAdapter.put("accountAwait", accountAwaitAdapter);
        // "账户冻结金额（元）",
        IValueFormatter bankFrostAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankFrost = (BigDecimal) object;
                return bankFrost != null ? bankFrost.toString() :  "";
            }
        };
        mapAdapter.put("bankFrost", bankFrostAdapter);
        // "资金存留比（%）"
        IValueFormatter fundRetentionAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal fundRetention = (BigDecimal) object;
                return fundRetention != null ? fundRetention.toString() :  "";
            }
        };
        mapAdapter.put("fundRetention", fundRetentionAdapter);
        // ""客均收益率（%）",
        IValueFormatter yieldAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal yield = (BigDecimal) object;
                return yield != null ? yield.toString() :  "";
            }
        };
        mapAdapter.put("yield", yieldAdapter);
        // 累计收益
        IValueFormatter interestSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal interestSum = (BigDecimal) object;
                return interestSum != null ? interestSum.toString() :  "";
            }
        };
        mapAdapter.put("interestSum", interestSumAdapter);
        // 累计年化出借金额
        IValueFormatter investSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal investSum = (BigDecimal) object;
                return investSum != null ? investSum.toString() :  "";
            }
        };
        mapAdapter.put("investSum", investSumAdapter);
        // 累计充值金额
        IValueFormatter rechargeSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal rechargeSum = (BigDecimal) object;
                return rechargeSum != null ? rechargeSum.toString() :  "";
            }
        };
        mapAdapter.put("rechargeSum", rechargeSumAdapter);
        // 累计提取金额
        IValueFormatter withdrawSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal withdrawSum = (BigDecimal) object;
                return withdrawSum != null ? withdrawSum.toString() :  "";
            }
        };
        mapAdapter.put("withdrawSum", withdrawSumAdapter);
        // 最后一次登录至今时长
        IValueFormatter lastLoginTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer lastLoginTime = (Integer) object;
                return lastLoginTime != null ? lastLoginTime.toString() :  "";
            }
        };
        mapAdapter.put("lastLoginTime", lastLoginTimeAdapter);
        // 最后一次充值至今时长
        IValueFormatter lastRechargeTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer lastRechargeTime = (Integer) object;
                return lastRechargeTime != null ? lastRechargeTime.toString() :  "";
            }
        };
        mapAdapter.put("lastRechargeTime", lastRechargeTimeAdapter);
        // 最后一次提现至今时长
        IValueFormatter lastWithdrawTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer lastWithdrawTime = (Integer) object;
                return lastWithdrawTime != null ? lastWithdrawTime.toString() :  "";
            }
        };
        mapAdapter.put("lastWithdrawTime", lastWithdrawTimeAdapter);
        // 同时出借平台数
        IValueFormatter investPlatformAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer investPlatform = (Integer) object;
                return investPlatform != null ? investPlatform.toString() :  "";
            }
        };
        mapAdapter.put("investPlatform", investPlatformAdapter);
        // 投龄
        IValueFormatter investAgeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer investAge = (Integer) object;
                return investAge != null ? investAge.toString() :  "";
            }
        };
        mapAdapter.put("investAge", investAgeAdapter);
        // 交易笔数
        IValueFormatter tradeNumberAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer tradeNumber = (Integer) object;
                return tradeNumber != null ? tradeNumber.toString() :  "";
            }
        };
        mapAdapter.put("tradeNumber", tradeNumberAdapter);
        // 邀约客户数
        IValueFormatter inviteCustomerAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteCustomer = (Integer) object;
                return inviteCustomer != null ? inviteCustomer.toString() :  "";
            }
        };
        mapAdapter.put("inviteCustomer", inviteCustomerAdapter);
        //  "邀约注册客户数"
        IValueFormatter inviteRegistAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteRegist = (Integer) object;
                return inviteRegist != null ? inviteRegist.toString() :  "";
            }
        };
        mapAdapter.put("inviteRegist", inviteRegistAdapter);
        //  "邀约充值客户数"
        IValueFormatter inviteRechargeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteRecharge = (Integer) object;
                return inviteRecharge != null ? inviteRecharge.toString() :  "";
            }
        };
        mapAdapter.put("inviteRecharge", inviteRechargeAdapter);
        //  ""邀约出借客户数
        IValueFormatter inviteTenderAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteTender = (Integer) object;
                return inviteTender != null ? inviteTender.toString() :  "";
            }
        };
        mapAdapter.put("inviteTender", inviteTenderAdapter);
        //  ""是否有主单"
        IValueFormatter attributeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer attribute = (Integer) object;
                String att = "";
                if(attribute.equals(0)){
                    att="无主单";
                }else if(attribute.equals(1)){
                    att="有主单";
                }else if(attribute.equals(2)){
                    att="线下员工";
                }else if(attribute.equals(3)){
                    att="线上员工";
                }
                return attribute != null ? att :  "";
            }
        };
        mapAdapter.put("attribute", attributeAdapter);
        return mapAdapter;
    }

    private Map<String, IValueFormatter> buildValueAdapterAccAdaptertAsterisked() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter ageAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer age = (Integer) object;
                return age != null ? age.toString() :  "";
            }
        };
        mapAdapter.put("age", ageAdapter);
        // 账户总资产（元）
        IValueFormatter bankTotalAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankTotal = (BigDecimal) object;
                return bankTotal != null ? bankTotal.toString() :  "";
            }
        };
        mapAdapter.put("bankTotal", bankTotalAdapter);
        // 账户可用金额（元）
        IValueFormatter bankBalanceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankBalance = (BigDecimal) object;
                return bankBalance != null ? bankBalance.toString() :  "";
            }
        };
        mapAdapter.put("bankBalance", bankBalanceAdapter);
        // "账户待还金额（元）
        IValueFormatter accountAwaitAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal accountAwait = (BigDecimal) object;
                return accountAwait != null ? accountAwait.toString() :  "";
            }
        };
        mapAdapter.put("accountAwait", accountAwaitAdapter);
        // "账户冻结金额（元）",
        IValueFormatter bankFrostAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankFrost = (BigDecimal) object;
                return bankFrost != null ? bankFrost.toString() :  "";
            }
        };
        mapAdapter.put("bankFrost", bankFrostAdapter);
        // "资金存留比（%）"
        IValueFormatter fundRetentionAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal fundRetention = (BigDecimal) object;
                return fundRetention != null ? fundRetention.toString() :  "";
            }
        };
        mapAdapter.put("fundRetention", fundRetentionAdapter);
        // ""客均收益率（%）",
        IValueFormatter yieldAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal yield = (BigDecimal) object;
                return yield != null ? yield.toString() :  "";
            }
        };
        mapAdapter.put("yield", yieldAdapter);
        // 累计收益
        IValueFormatter interestSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal interestSum = (BigDecimal) object;
                return interestSum != null ? interestSum.toString() :  "";
            }
        };
        mapAdapter.put("interestSum", interestSumAdapter);
        // 累计年化出借金额
        IValueFormatter investSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal investSum = (BigDecimal) object;
                return investSum != null ? investSum.toString() :  "";
            }
        };
        mapAdapter.put("investSum", investSumAdapter);
        // 累计充值金额
        IValueFormatter rechargeSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal rechargeSum = (BigDecimal) object;
                return rechargeSum != null ? rechargeSum.toString() :  "";
            }
        };
        mapAdapter.put("rechargeSum", rechargeSumAdapter);
        // 累计提取金额
        IValueFormatter withdrawSumAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal withdrawSum = (BigDecimal) object;
                return withdrawSum != null ? withdrawSum.toString() :  "";
            }
        };
        mapAdapter.put("withdrawSum", withdrawSumAdapter);
        // 最后一次登录至今时长
        IValueFormatter lastLoginTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer lastLoginTime = (Integer) object;
                return lastLoginTime != null ? lastLoginTime.toString() :  "";
            }
        };
        mapAdapter.put("lastLoginTime", lastLoginTimeAdapter);
        // 最后一次充值至今时长
        IValueFormatter lastRechargeTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer lastRechargeTime = (Integer) object;
                return lastRechargeTime != null ? lastRechargeTime.toString() :  "";
            }
        };
        mapAdapter.put("lastRechargeTime", lastRechargeTimeAdapter);
        // 最后一次提现至今时长
        IValueFormatter lastWithdrawTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer lastWithdrawTime = (Integer) object;
                return lastWithdrawTime != null ? lastWithdrawTime.toString() :  "";
            }
        };
        mapAdapter.put("lastWithdrawTime", lastWithdrawTimeAdapter);
        // 同时出借平台数
        IValueFormatter investPlatformAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer investPlatform = (Integer) object;
                return investPlatform != null ? investPlatform.toString() :  "";
            }
        };
        mapAdapter.put("investPlatform", investPlatformAdapter);
        // 投龄
        IValueFormatter investAgeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer investAge = (Integer) object;
                return investAge != null ? investAge.toString() :  "";
            }
        };
        mapAdapter.put("investAge", investAgeAdapter);
        // 交易笔数
        IValueFormatter tradeNumberAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer tradeNumber = (Integer) object;
                return tradeNumber != null ? tradeNumber.toString() :  "";
            }
        };
        mapAdapter.put("tradeNumber", tradeNumberAdapter);
        // 邀约客户数
        IValueFormatter inviteCustomerAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteCustomer = (Integer) object;
                return inviteCustomer != null ? inviteCustomer.toString() :  "";
            }
        };
        mapAdapter.put("inviteCustomer", inviteCustomerAdapter);
        //  "邀约注册客户数"
        IValueFormatter inviteRegistAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteRegist = (Integer) object;
                return inviteRegist != null ? inviteRegist.toString() :  "";
            }
        };
        mapAdapter.put("inviteRegist", inviteRegistAdapter);
        //  "邀约充值客户数"
        IValueFormatter inviteRechargeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteRecharge = (Integer) object;
                return inviteRecharge != null ? inviteRecharge.toString() :  "";
            }
        };
        mapAdapter.put("inviteRecharge", inviteRechargeAdapter);
        //  ""邀约出借客户数
        IValueFormatter inviteTenderAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer inviteTender = (Integer) object;
                return inviteTender != null ? inviteTender.toString() :  "";
            }
        };
        mapAdapter.put("inviteTender", inviteTenderAdapter);
        //  ""是否有主单"
        IValueFormatter attributeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer attribute = (Integer) object;
                String att = "";
                if(attribute.equals(0)){
                    att="无主单";
                }else if(attribute.equals(1)){
                    att="有主单";
                }else if(attribute.equals(2)){
                    att="线下员工";
                }else if(attribute.equals(3)){
                    att="线上员工";
                }
                return attribute != null ? att :  "";
            }
        };
        mapAdapter.put("attribute", attributeAdapter);
        //
        IValueFormatter mobileAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String mobile = (String) object;
                return AsteriskProcessUtil.getDesensitizationValue(mobile);
            }
        };
        mapAdapter.put("mobile", mobileAdapter);
        return mapAdapter;
    }
}
