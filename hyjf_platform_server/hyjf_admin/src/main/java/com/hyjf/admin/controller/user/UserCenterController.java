/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.*;
import com.hyjf.admin.beans.response.*;
import com.hyjf.admin.beans.vo.*;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */

@Api(value = "会员中心-会员管理接口",tags = "会员中心-会员管理接口")
@RestController
@RequestMapping("/hyjf-admin/usersManager")
public class UserCenterController extends BaseController {

    public static final String PERMISSIONS = "userslist";
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "会员管理页面初始化(下拉列表)", notes = "会员管理页面初始化")
    @PostMapping(value = "/usersInit")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public  AdminResult<UserManagerInitResponseBean>  userManagerInit() {
        UserManagerInitResponseBean userManagerInitResponseBean =this.initUserManaget();
        return new AdminResult<UserManagerInitResponseBean>(userManagerInitResponseBean);

    }

    //会员管理列表查询
    @ApiOperation(value = "会员管理列表查询", notes = "会员管理列表查询")
    @PostMapping(value = "/userslist")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<UserManagerCustomizeVO>> getUserslist(@RequestBody UserManagerRequestBean userManagerRequestBean,HttpServletRequest request) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean,managerRequest);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);
        if(userManagerResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserManagerVO> listUserManagetVO = userManagerResponse.getResultList();
        List<UserManagerCustomizeVO> userManagerCustomizeList = new ArrayList<UserManagerCustomizeVO>();
        if(null!=listUserManagetVO&&listUserManagetVO.size()>0){
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for(UserManagerVO userManagerVO:listUserManagetVO){
                    userManagerVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(userManagerVO.getMobile()));
                }
            }
            userManagerCustomizeList = CommonUtils.convertBeanList(listUserManagetVO, UserManagerCustomizeVO.class);
        }
        if (!Response.isSuccess(userManagerResponse)) {
            return new AdminResult<>(FAIL, userManagerResponse.getMessage());
        }
        return new AdminResult<ListResult<UserManagerCustomizeVO>>(ListResult.build(userManagerCustomizeList,userManagerResponse.getCount())) ;
    }

    //会员详情
    @ApiOperation(value = "会员详情", notes = "会员详情")
    @PostMapping(value = "/getUserdetail")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public  AdminResult<UserDetailInfoResponseBean>  getUserdetail(HttpServletRequest request,@RequestBody String userId) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserDetailInfoResponseBean userDetailInfoResponseBean = new UserDetailInfoResponseBean();
        UserManagerDetailVO userManagerDetailVO = userCenterService.selectUserDetail(userId);
        UserManagerDetailCustomizeVO userManagerDetailCustomizeVO = new UserManagerDetailCustomizeVO();
        if(null!=userManagerDetailVO){
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                userManagerDetailVO.setIdCard(AsteriskProcessUtil.getAsteriskedValue(userManagerDetailVO.getIdCard()));
                userManagerDetailVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(userManagerDetailVO.getMobile()));
                //紧急联系人手机号加密显示
                userManagerDetailVO.setEmPhone(AsteriskProcessUtil.getAsteriskedValue(userManagerDetailVO.getEmPhone()));
            }
            BeanUtils.copyProperties(userManagerDetailVO, userManagerDetailCustomizeVO);
        }
        userDetailInfoResponseBean.setUserManagerDetailVO(userManagerDetailCustomizeVO);
        // vip user 表里没有vip字段

        // 获取测评信息
        UserEvalationResultVO userEvalationResultInfo = userCenterService.getUserEvalationResult(userId);
        UserEvalationResultShowVO userEvalationResultShowVO = new UserEvalationResultShowVO();
        if (null != userEvalationResultInfo && null != userEvalationResultInfo.getCreateTime()) {
            //从user表获取用户测评到期日
            UserVO user = userCenterService.selectUserByUserId(userId);
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = user.getEvaluationExpiredTime().getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = System.currentTimeMillis();
            if (lCreate <= lNow) {
                //已过期需要重新评测2已过期、1有效
                userDetailInfoResponseBean.setIsEvalation("2");
            } else {
                userDetailInfoResponseBean.setIsEvalation("1");
            }
            String strDate = smp.format(userEvalationResultInfo.getCreateTime());
            BeanUtils.copyProperties(userEvalationResultInfo, userEvalationResultShowVO);
            userEvalationResultShowVO.setCreateTime(strDate);
        }
        userDetailInfoResponseBean.setUserEvalationResultInfo(userEvalationResultShowVO);
        //用户开户信息
        UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(userId);
        UserBankOpenAccountCustomizeVO userBankOpenAccountCustomizeVO = new UserBankOpenAccountCustomizeVO();
        if(null!=userBankOpenAccountVO){
            BeanUtils.copyProperties(userBankOpenAccountVO, userBankOpenAccountCustomizeVO);
        }
        userDetailInfoResponseBean.setUserBankOpenAccountVO(userBankOpenAccountCustomizeVO);
        //公司信息
        CompanyInfoVO companyInfo = userCenterService.selectCompanyInfoByUserId(userId);
        CompanyInfoCompanyInfoVO companyInfoCompanyInfoVO = new CompanyInfoCompanyInfoVO();
        if(null!=companyInfo){
            BeanUtils.copyProperties(companyInfo, companyInfoCompanyInfoVO);
        }
        userDetailInfoResponseBean.setEnterpriseInformation(companyInfoCompanyInfoVO);
//        //第三方平台绑定信息
//        BindUserVo bindUserVo = userCenterService.selectBindeUserByUserI(userId);
//        BindUserCustomizeVO bindUserCustomizeVO = new BindUserCustomizeVO();
//        if(null!=bindUserVo){
//            BeanUtils.copyProperties(bindUserVo,bindUserCustomizeVO);
//        }
//        userDetailInfoResponseBean.setBindUserVo(bindUserCustomizeVO);
        //电子签章
        CertificateAuthorityVO certificateAuthorityVO = userCenterService.selectCertificateAuthorityByUserId(userId);
        CertificateAuthorityCustomizeVO certificateAuthorityCustomizeVO = new CertificateAuthorityCustomizeVO();
        if(null!=certificateAuthorityVO){
            BeanUtils.copyProperties(certificateAuthorityVO, certificateAuthorityCustomizeVO);
        }
        userDetailInfoResponseBean.setCertificateAuthorityVO(certificateAuthorityCustomizeVO);
        // 文件服务器
        String fileDomainUrl = systemConfig.getFileDomainUrl();
        userDetailInfoResponseBean.setHostUrl(fileDomainUrl);

        {
            //邀请信息
            UserUtmInfoCustomizeVO userUtmInfo = userCenterService.getUserUtmInfo(Integer.valueOf(userId));

            Map<String, String> information = new HashMap<>();
            // web 着陆页URl 显示用
            String linkUrl = null;
            // 微信着陆页Url 二维码使用
            String linkUrlQr = null;

//            if (userUtmInfo != null) {
//                logger.info("获取用户所在渠道信息:" + userUtmInfo.getSourceId() + ":" + userUtmInfo.getSourceName());
//
//                linkUrl = systemConfig.getWebLandingPageUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfo.getSourceId().toString() + "&utmSource=" + userUtmInfo.getSourceName();
//                linkUrlQr = systemConfig.getWechatLandingPageUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfo.getSourceId().toString() + "&utmSource=" + userUtmInfo.getSourceName();
//
//            }else {
                // 已确认未关联渠道的用户
                linkUrl = systemConfig.getWebLandingPageUrl() + "refferUserId=" + userId;
                linkUrlQr = systemConfig.getWechatLandingPageUrl() + "refferUserId=" + userId;
//            }

            information.put("linkUrl", linkUrl);
            information.put("linkUrlQr", linkUrlQr);
            userDetailInfoResponseBean.setInvitationInformation(information);
        }
        return new AdminResult<UserDetailInfoResponseBean>(userDetailInfoResponseBean);
    }


    @ApiOperation(value = "获取用户编辑初始信息", notes = "获取用户编辑初始信息")
    @PostMapping(value = "/initUserUpdate")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<InitUserUpdResponseBean> initUserUpdate(@RequestBody String userId) {

        InitUserUpdResponseBean initUserUpdResponseBean = new InitUserUpdResponseBean();
        // 根据用户id查询用户详情信息
        UserManagerUpdateVO userManagerUpdateVo = userCenterService.selectUserUpdateInfoByUserId(userId);
        UserManagerUpdateCustomizeVO userManagerUpdateCustomizeVO = new UserManagerUpdateCustomizeVO();
        if(null!=userManagerUpdateVo){
            BeanUtils.copyProperties(userManagerUpdateVo, userManagerUpdateCustomizeVO);
        }
        initUserUpdResponseBean.setUsersUpdateForm(userManagerUpdateCustomizeVO);
        // 加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            userChangeLogRequest.setChangeType(2);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initUserUpdResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitUserUpdResponseBean>(initUserUpdResponseBean);
    }

    @ApiOperation(value = "修改更新用户信息", notes = "修改更新用户信息")
    @PostMapping(value = "/updateUser")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updataUserInfo(HttpServletRequest request, @RequestBody UserManagerUpdateRequestBean requestBean) {
        UserManagerUpdateRequest userRequest = new UserManagerUpdateRequest();
        BeanUtils.copyProperties(requestBean, userRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        userRequest.setLoginUserName(adminSystemVO.getUsername());
        userRequest.setLogingUserId(adminSystemVO.getId());
        //1代表更新成功，0为失败
        int intUpdFlg = userCenterService.updataUserInfo(userRequest);
        // 修改手机号后 发送更新客户信息
        userCenterService.sendCAChangeMQ(userRequest);
        if (intUpdFlg<=0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "获取推荐人信息", notes = "获取推荐人信息")
    @PostMapping(value = "/initModifyre")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFYRE)
    public AdminResult<InitModifyreResponseBean> initModifyre(@RequestBody String userId) {
        InitModifyreResponseBean initModifyreResponseBean = new InitModifyreResponseBean();
        //推荐人信息
        UserRecommendCustomizeVO userRecommendVO = userCenterService.selectUserRecommendByUserId(userId);
        UserRecommendCustomizeShowVO userRecommendCustomizeShowVO = new UserRecommendCustomizeShowVO();
        if(null!=userRecommendVO){
            BeanUtils.copyProperties(userRecommendVO,userRecommendCustomizeShowVO);
        }
        initModifyreResponseBean.setModifyReForm(userRecommendCustomizeShowVO);
        //加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            //修改类型 2用户信息修改  1推荐人修改
            userChangeLogRequest.setChangeType(1);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initModifyreResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitModifyreResponseBean>(initModifyreResponseBean);
    }

    @ApiOperation(value = "修改用户推荐人", notes = "修改用户推荐人")
    @PostMapping(value = "/updateModifyre")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFYRE)
    public AdminResult updateRe(HttpServletRequest request, @RequestBody AdminUserRecommendRequestBean requestBean) {
        AdminUserRecommendRequest adminUserRecommendRequest = new AdminUserRecommendRequest();
        BeanUtils.copyProperties(requestBean, adminUserRecommendRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        if(null!=adminSystemVO){
            //当前登陆用户
            adminUserRecommendRequest.setLoginUserId(adminSystemVO.getId());
            adminUserRecommendRequest.setLoginUserName(adminSystemVO.getUsername());
        }
        int updFlg = userCenterService.updateUserRecommend(adminUserRecommendRequest);
        if(updFlg<=0){
            return new AdminResult<>(FAIL, "修改用户推荐人失败");
        }
        return new AdminResult<>();
    }

    /**
     * 获取用户身份证信息
     *
     * @param userId
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "获取用户身份证信息", notes = "获取用户身份证信息")
    @PostMapping(value = "/searchIdCard")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFYIDCARD)
    public  AdminResult<InitModifyreResponseBean> searchIdCard(@RequestBody String userId) {
        InitModifyreResponseBean initModifyreResponseBean = new InitModifyreResponseBean();
        //根据用户id查询用户详情信息
        UserRecommendCustomizeShowVO userRecommendCustomizeShowVO = new UserRecommendCustomizeShowVO();
        UserRecommendCustomizeVO userRecommendVO = userCenterService.selectUserRecommendByUserId(userId);
        if(null!=userRecommendVO){
            BeanUtils.copyProperties(userRecommendVO,userRecommendCustomizeShowVO);
        }
        initModifyreResponseBean.setModifyReForm(userRecommendCustomizeShowVO);
        //加载修改日志 userChageLog
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            //根据源代码 设置为3
            userChangeLogRequest.setChangeType(3);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initModifyreResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitModifyreResponseBean>(initModifyreResponseBean);
    }

    /**
     * 修改用户身份证
     *
     * @param request
     * @return 进入用户详情页面
     */
    @ApiOperation(value = "修改用户身份证", notes = "修改用户身份证")
    @PostMapping(value = "/modifyIdCard")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFYIDCARD)
    public AdminResult modifyIdCard(HttpServletRequest request, @RequestBody AdminUserRecommendRequestBean requestBean) {
        AdminUserRecommendRequest adminUserRecommendRequest = new AdminUserRecommendRequest();
        BeanUtils.copyProperties(requestBean, adminUserRecommendRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        if(null!=adminSystemVO){
            //当前登陆用户
            adminUserRecommendRequest.setLoginUserId(adminSystemVO.getId());
            adminUserRecommendRequest.setLoginUserName(adminSystemVO.getUsername());
        }
        int updFlg = userCenterService.updateUserIdCard(adminUserRecommendRequest);
        if(updFlg<=0){
            return new AdminResult<>(FAIL, "修改用户身份证失败");
        }
        //修改身份证号后 发送更新客户信息MQ
        userCenterService.sendCAChangeMQ(adminUserRecommendRequest);
        return new AdminResult<>();
    }

    /**
     * 检查手机号码或用户名唯一性
     *
     * @param userId
     * @param userName
     * @return
     */
    @PostMapping(value = "/checkReAction")
    @ResponseBody
    @ApiOperation(value = "校验推荐人", notes = "校验推荐人")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFYRE)
    public AdminResult checkReAction(@RequestParam(value = "userId") String userId,HttpServletRequest request,@RequestParam(value = "userName") String userName) {
        //校验推荐人
        if (Validator.isNotNull(userId)) {
            if (StringUtils.isNotEmpty(userName)) {
                int recomFlag = userCenterService.checkRecommend(userId, userName);
                if (recomFlag == 2) {// 推荐人不能是自己
                    return new AdminResult<>(FAIL, "不能将推荐人设置为自己");
                } else if (recomFlag == 1) {// 推荐人不存在
                    return new AdminResult<>(FAIL, "推荐人不存在");

                }
            }
        }
        return new AdminResult<>();
    }



    /**
     * 检查手机号码或用户名唯一性
     *
     * @return
     */
    @PostMapping(value = "/checkAction")
    @ResponseBody
    @ApiOperation(value = "校验手机号", notes = "校验手机号")
    public AdminResult checkAction(@RequestParam(value = "userId") String userId,@RequestParam(value = "mobile") String mobile) {
        // 检查手机号码唯一性
        if(StringUtils.isNotBlank(userId)){
            int userrIdInt = Integer.parseInt(userId);
            int cnt = userCenterService.countUserByMobile(mobile,userrIdInt);
            if (cnt > 0) {
                return new AdminResult<>(FAIL, "手机号已经存在！");
            }
        }else{
            return new AdminResult<>(FAIL, "用户id不能为空！");
        }
        return new AdminResult<>();
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
//    @ApiOperation(value = "导出会员管理列表", notes = "导出会员管理列表")
//    @PostMapping(value = "/exportusers")
    /*public void exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody UserManagerRequestBean userManagerRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "会员列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "分公司", "分部", "团队", "用户来源", "用户名", "姓名", "性别", "年龄",
                "生日","身份证号", "户籍所在地", "手机号码",  "用户角色", "用户属性", "推荐人", "用户状态","银行开户状态",
                "银行开户时间","汇付开户状态", "注册平台", "注册时间", "注册所在地" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean,managerRequest);
        managerRequest.setLimitFlg(true);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);
        if(null!=userManagerResponse){
            List<UserManagerVO>recordList = userManagerResponse.getResultList();
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
                        UserManagerVO user = recordList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 序号
                            cell.setCellValue(i + 1);
                        } else if (celLength == 1) { // 大区
                            cell.setCellValue(user.getRegionName());
                        } else if (celLength == 2) { // 分公司
                            cell.setCellValue(user.getBranchName());
                        } else if (celLength == 3) { // 团队
                            cell.setCellValue(user.getDepartmentName());
                        } else if (celLength == 4) { // 用户来源
                            cell.setCellValue(user.getInstName());
                        } else if (celLength == 5) {// 用户名
                            cell.setCellValue(user.getUserName());
                        } else if (celLength == 6) {// 姓名
                            cell.setCellValue(user.getRealName());
                        } else if (celLength == 7) {// 性别
                            if ("1".equals(user.getSex())) {
                                cell.setCellValue("男");
                            } else if ("2".equals(user.getSex())) {
                                cell.setCellValue("女");
                            } else {
                                cell.setCellValue("未知");
                            }
                        } else if (celLength == 8) {// 年龄
                            cell.setCellValue(this.getAge(user.getBirthday()));
                        } else if (celLength == 9) {// 生日
                            cell.setCellValue(user.getBirthday());
                        } else if (celLength == 10) {// 身份证号
                            cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getIdcard(),7));
                        } else if (celLength == 11) {// 户籍所在地
                            cell.setCellValue(userCenterService.getAreaByIdCard(user.getIdcard()));
                        } else if (celLength == 12) {// 手机号码
                            cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getMobile(),3));
                        } else if (celLength == 13) {// 用户角色
                            cell.setCellValue(user.getUserRole());
                        } else if (celLength == 14) {// 用户属性
                            cell.setCellValue(user.getUserProperty());
                        } else if (celLength == 15) {// 推荐人
                            cell.setCellValue(user.getRecommendName());
                        }  else if (celLength == 16) {// 用户状态
                            cell.setCellValue(user.getUserStatus());
                        } else if (celLength == 17) {// 银行开户状态
                            cell.setCellValue("1".equals(user.getBankOpenAccount())?"已开户":"未开户");
                        } else if (celLength == 18) {// 银行开户时间
                            cell.setCellValue(user.getBankOpenTime());
                        } else if (celLength == 19) {// 开户状态
                            cell.setCellValue("1".equals(user.getOpenAccount())?"已开户":"未开户");
                        } else if (celLength == 20) {// 注册平台
                            cell.setCellValue(user.getRegistPlat());
                        } else if (celLength == 21) {// 注册时间
                            cell.setCellValue(user.getRegTime());
                        } else if (celLength == 22) {// 注册所在地
                            cell.setCellValue(user.getRegistArea());
                        }
                    }
                }
            }
        }
         // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出excel
     *
     * @param userManagerRequestBean
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出会员管理列表", notes = "导出会员管理列表")
    @PostMapping(value = "/exportusers")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody UserManagerRequestBean userManagerRequestBean) throws Exception {
        // 用户是否具有组织机构查看权限
        String isOrganizationView = userManagerRequestBean.getIsOrganizationView();
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "会员列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean,managerRequest);
        managerRequest.setLimitFlg(false);
        //请求第一页5000条
        managerRequest.setPageSize(defaultRowMaxCount);
        managerRequest.setCurrPage(1);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);

        Integer totalCount = userManagerResponse.getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if(!isShow){
            mapValueAdapter = buildValueAdaptertAsterisked();
        }
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
        	 helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, userManagerResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {

            managerRequest.setPageSize(defaultRowMaxCount);
            managerRequest.setCurrPage(i+1);
            UserManagerResponse userManagerResponse2 = userCenterService.selectUserMemberList(managerRequest);
            if (userManagerResponse2 != null && userManagerResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  userManagerResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap(String isOrganizationView) {
        Map<String, String> map = Maps.newLinkedHashMap();
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("regionName", "分公司");
            map.put("branchName", "分部");
            map.put("departmentName", "团队");
        }
        map.put("instName", "用户来源");
        map.put("userName", "用户名");
        map.put("realName", "姓名");
        map.put("sex", "性别");
        map.put("age", "年龄");
        map.put("birthday", "生日");
        map.put("idcard", "身份证号");
        // 新增户籍所在地
        map.put("areaByIdCard", "户籍所在地");
        map.put("mobile", "手机号码");
        // 会员类型（迁移无字段）删除
        //map.put("vipType", "会员类型");
        map.put("userRole", "用户角色");
        map.put("userProperty", "用户属性");
        map.put("recommendName", "推荐人");
        // 51老用户（迁移无字段）删除
        //map.put("is51", "51老用户");
        map.put("userStatus", "用户状态");
        map.put("bankOpenAccount", "银行开户状态");
        map.put("bankOpenTime", "银行开户时间");
        map.put("openAccount", "汇付开户状态");
        map.put("registPlat", "注册平台");
        map.put("regTime", "注册时间");
        map.put("registArea", "注册所在地");


        return map;
    }
    private Map<String, IValueFormatter> buildValueAdaptertAsterisked() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter sexAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String sex = (String) object;
                if("1".equals(sex)) {
                	return "男";
                }else {
                	return "女";
                }

            }
        };
        IValueFormatter idcardAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String idcard = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(idcard);
            }
        };
        IValueFormatter bankOpenAccounAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
            	String bankOpenAccount = (String) object;
                return "1".equals(bankOpenAccount)?"已开户":"未开户";
            }
        };
        IValueFormatter openAccounAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
            	String openAccount = (String) object;
                return "1".equals(openAccount)?"已开户":"未开户";
            }
        };
        IValueFormatter mobileAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String mobile = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(mobile);
            }
        };
        //户籍所在地
        IValueFormatter areaByIdCard = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String areaByIdCard = (String) object;
                return  userCenterService.getAreaByIdCard(areaByIdCard);
            }
        };
        // 年龄
        IValueFormatter age = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String age = (String) object;
                if (StringUtils.isBlank(age)) {
                    return "";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                String formatDate = sdf.format(date);
                return  String.valueOf(Integer.parseInt(formatDate) - Integer.parseInt(age.substring(0, 4)));
            }
        };
        mapAdapter.put("sex", sexAdapter);
        mapAdapter.put("mobile", mobileAdapter);
        mapAdapter.put("idcard", idcardAdapter);
        mapAdapter.put("bankOpenAccount", bankOpenAccounAdapter);
        mapAdapter.put("openAccount", openAccounAdapter);
        mapAdapter.put("areaByIdCard", areaByIdCard);
        mapAdapter.put("age", age);
        return mapAdapter;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter sexAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String sex = (String) object;
                if("1".equals(sex)) {
                    return "男";
                }else {
                    return "女";
                }

            }
        };
       /* IValueFormatter idcardAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String idcard = (String) object;
                return AsteriskProcessUtil.getAsteriskedValue(idcard,7);
            }
        };*/
        IValueFormatter bankOpenAccounAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String bankOpenAccount = (String) object;
                return "1".equals(bankOpenAccount)?"已开户":"未开户";
            }
        };
        IValueFormatter openAccounAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String openAccount = (String) object;
                return "1".equals(openAccount)?"已开户":"未开户";
            }
        };
        IValueFormatter areaByIdCardAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String areaByIdCard = (String) object;
                return  userCenterService.getAreaByIdCard(areaByIdCard);
            }
        };
        IValueFormatter ageAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String age = (String) object;
                if (StringUtils.isBlank(age)) {
                    return "";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                String formatDate = sdf.format(date);
                return  String.valueOf(Integer.parseInt(formatDate) - Integer.parseInt(age.substring(0, 4)));
            }
        };

        mapAdapter.put("sex", sexAdapter);
       // mapAdapter.put("idcard", idcardAdapter);
        mapAdapter.put("bankOpenAccount", bankOpenAccounAdapter);
        mapAdapter.put("openAccount", openAccounAdapter);
        mapAdapter.put("areaByIdCard", areaByIdCardAdapter);
        mapAdapter.put("age", ageAdapter);
        return mapAdapter;
    }

    public String getAge(String birthday) {
        if (StringUtils.isBlank(birthday)||birthday.contains("--")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatDate = sdf.format(date);
        int age = Integer.parseInt(formatDate) - Integer.parseInt(birthday.substring(0, 4));
        return String.valueOf(age);
    }

    /**
     * 企业用户信息补录
     * @param userId
     * @return
     */
    @PostMapping(value = "/initCompanyInfo")
    @ResponseBody
    @ApiOperation(value = "初始化企业用户信息", notes = "初始化企业用户信息")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_NSERT_CONPANYINFO)
    public  AdminResult<InitCompanyInfoResponseBean> initCompanyInfo(@RequestBody String userId) {
        InitCompanyInfoResponseBean initCompanyInfoResponseBean = new InitCompanyInfoResponseBean();
        if (StringUtils.isNotBlank(userId)) {
            UserVO user = userCenterService.selectUserByUserId(userId);
            UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(userId);
            CompanyInfoVO companyInfo = userCenterService.selectCompanyInfoByUserId(userId);
            CompanyInfoCompanyInfoVO companyInfoCompanyInfoVO = new CompanyInfoCompanyInfoVO();
            UserBankOpenAccountCustomizeVO userBankOpenAccountCustomizeVO = new UserBankOpenAccountCustomizeVO();
            UserCustomizeShowVO userCustomizeShowVO = new UserCustomizeShowVO();
            if(null!=companyInfo){
                BeanUtils.copyProperties(companyInfo,companyInfoCompanyInfoVO);
            }
            if(null!=userBankOpenAccountVO){
                BeanUtils.copyProperties(userBankOpenAccountVO, userBankOpenAccountCustomizeVO);
            }
            if(null!=user){
                BeanUtils.copyProperties(user, userCustomizeShowVO);
            }
            initCompanyInfoResponseBean.setBankOpenAccount(userBankOpenAccountCustomizeVO);
            initCompanyInfoResponseBean.setCompanyInfo(companyInfoCompanyInfoVO);
            initCompanyInfoResponseBean.setUserVO(userCustomizeShowVO);
        }
        return new AdminResult<InitCompanyInfoResponseBean>(initCompanyInfoResponseBean);
    }

    /**
     * 查询企业开户信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/serchCompanyInfo")
    @ApiOperation(value = "查询企业开户信息", notes = "查询企业开户信息")
    public AdminResult<SearchCompanyInfoResponseBean> serchCompanyInfo(@RequestParam(value = "userId") String userId,@RequestParam(value = "accountId") String accountId) {
//        String userId = getUser(request).getId();
        SearchCompanyInfoResponseBean  searchCompanyInfoResponseBean = new  SearchCompanyInfoResponseBean();
        if (StringUtils.isBlank(userId)) {
            return new AdminResult<>(FAIL, "请先选择用户再进行操作!");
        }
        if (StringUtils.isBlank(accountId)) {
            return new AdminResult<>(FAIL, "请输入正确的电子账号!");
        }
        //根据accountid调用接口查找企业信息
        int intUserId = Integer.parseInt(userId);
        CompanyInfoSearchResponseBean companyInfoSearchResponseBean = userCenterService.queryCompanyInfoByAccoutnId(intUserId, accountId);
        CompanyInfoCompanyInfoVO companyInfoCompanyInfoVO = new CompanyInfoCompanyInfoVO();
        if(null!=companyInfoSearchResponseBean&& "00".equals(companyInfoSearchResponseBean.getReturnCode())){
            //代表成功
            CompanyInfoVO infoVO = companyInfoSearchResponseBean.getCompanyInfoVO();
            if(null==infoVO){
                return new AdminResult<>(FAIL, "请输入正确的电子账号!");
            }
            BeanUtils.copyProperties(infoVO,companyInfoCompanyInfoVO);
            //企业信息补录时，企业名称若含有英文括号，自动替换成中文括号再保存 add by nxl start
            if(null!=companyInfoCompanyInfoVO.getName()){
                if(companyInfoCompanyInfoVO.getName().contains("(")){
                    String repNameStart = companyInfoCompanyInfoVO.getName().replace("(","（");
                    companyInfoCompanyInfoVO.setName(repNameStart);
                }
                if(companyInfoCompanyInfoVO.getName().contains(")")){
                    String repNameEnd = companyInfoCompanyInfoVO.getName().replace(")","）");
                    companyInfoCompanyInfoVO.setName(repNameEnd);
                }
            }
            //企业信息补录时，企业名称若含有英文括号，自动替换成中文括号再保存 add by nxl end
            // 后台优化 add by nxl start 根据对公账号查找银行卡信息
            BankCardResponse bankCardResponse = userCenterService.getBankInfoByAccount(infoVO.getAccount(),userId);
            if(null!=bankCardResponse){
                BankCardVO bankCardVO =bankCardResponse.getResult();
                companyInfoCompanyInfoVO.setBankName(bankCardVO.getBank());
                companyInfoCompanyInfoVO.setPayAllianceCode(bankCardVO.getPayAllianceCode());
                companyInfoCompanyInfoVO.setBankId(bankCardVO.getBankId().toString());
            }

            //后台优化 add by nxl end

            searchCompanyInfoResponseBean.setCompany(companyInfoCompanyInfoVO);
            UserVO userVO = userCenterService.selectUserByUserId(userId);
            Integer bankFlag = userVO.getBankOpenAccount();
            searchCompanyInfoResponseBean.setIsOpenAccount(bankFlag);
            return new AdminResult<SearchCompanyInfoResponseBean>(searchCompanyInfoResponseBean);
        }
        if(null!=companyInfoSearchResponseBean&& "99".equals(companyInfoSearchResponseBean.getReturnCode())){
            return new AdminResult<>(FAIL, companyInfoSearchResponseBean.getReturnMsg());
        }
        return new AdminResult<>(FAIL, "请输入正确的电子账号!");
    }

    /**
     * 保存企业开户信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/saveCompanyInfo")
    @ApiOperation(value = "保存企业开户信息", notes = "保存企业开户信息")
    public AdminResult<Response> saveCompanyInfo(@RequestBody CompanyInfoInstRequesetBean companyInfoInstRequesetBean) {
        UpdCompanyRequest updCompanyRequest = new UpdCompanyRequest();
        BeanUtils.copyProperties(companyInfoInstRequesetBean, updCompanyRequest);
        Response response = userCenterService.saveCompanyInfo(updCompanyRequest);
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return new AdminResult<Response>(response);
        }
        return new AdminResult<>(FAIL, response.getMessage());
    }

    public UserManagerInitResponseBean initUserManaget(){
        UserManagerInitResponseBean userManagerInitResponseBean = new UserManagerInitResponseBean();
        // 用户角色
        Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        List<DropDownVO> listUserRoles = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userRoles);
        // 用户属性
        Map<String, String> userPropertys = CacheUtil.getParamNameMap("USER_PROPERTY");
        List<DropDownVO> listUserPropertys = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userPropertys);
        // 开户状态
        Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
        List<DropDownVO> listAccountStatus = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(accountStatus);
        // 用户状态
        Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
        List<DropDownVO> listUserStatus = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userStatus);
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        List<DropDownVO> listRegistPlat = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(registPlat);
        // 用户类型
        Map<String, String> userTypes = CacheUtil.getParamNameMap("USER_TYPE");
        List<DropDownVO> listUserTypes = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userTypes);
        // 借款人类型
        Map<String, String> borrowTypes = CacheUtil.getParamNameMap("BORROWER_TYPE");
        List<DropDownVO> listBorrowTypes = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(borrowTypes);

        List<HjhInstConfigVO> listHjhInstConfig =  userCenterService.selectInstConfigAll();
        List<DropDownVO> dropDownVOList = com.hyjf.admin.utils.ConvertUtils.convertListToDropDown(listHjhInstConfig,"instCode","instName");
        //部门树形列表
        // 部门
        String[] list = new String[] {};
        JSONArray ja = userCenterService.getCrmDepartmentList(list);
        JSONObject ret= new JSONObject();
        if (ja != null) {
            //在部门树中加入 0=部门（其他）,因为前端不能显示id=0,就在后台将0=其他转换为-10086=其他
            JSONObject jo = new JSONObject();
            jo.put("value", "-10086");
            jo.put("title", "其他");
            JSONArray array = new JSONArray();
            jo.put("key", UUID.randomUUID());
            jo.put("children", array);
            ja.add(jo);
            //
            ret.put("data", ja);
            ret.put("status", "000");
            ret.put("statusDesc", "成功");
        }
        userManagerInitResponseBean.setDeptList(ret);
        userManagerInitResponseBean.setUserRoles(listUserRoles);
        userManagerInitResponseBean.setUserPropertys(listUserPropertys);
        userManagerInitResponseBean.setAccountStatus(listAccountStatus);
        userManagerInitResponseBean.setUserStatus(listUserStatus);
        userManagerInitResponseBean.setRegistPlat(listRegistPlat);
        userManagerInitResponseBean.setUserTypes(listUserTypes);
        userManagerInitResponseBean.setBorrowTypes(listBorrowTypes);
        userManagerInitResponseBean.setListHjhInstConfig(dropDownVOList);
        return userManagerInitResponseBean;
    }
    //修改用户信息
    @ResponseBody
    @PostMapping(value = "/initUpdateUserInfos")
    @ApiOperation(value = "初始化用户信息", notes = "初始化用户信息(修改手机号,邮箱,用户角色,银行卡)")
    public AdminResult<InitUserBaseInfoResponseBean> initUpdateUserInfos(@RequestParam(value = "userId") String userId,@RequestParam(value = "updType") String updType) {
        InitUserBaseInfoResponseBean initUserBaseInfoResponseBean = new InitUserBaseInfoResponseBean();
        initUserBaseInfoResponseBean.setUpdType(updType);
        // 根据用户id查询用户详情信息
        UserManagerUpdateVO userManagerUpdateVo = userCenterService.selectUserUpdateInfoByUserId(userId);
        if(null!=userManagerUpdateVo){
            UserManagerUpdateCustomizeVO userManagerUpdateCustomizeVO = new UserManagerUpdateCustomizeVO();
            BeanUtils.copyProperties(userManagerUpdateVo, userManagerUpdateCustomizeVO);
            initUserBaseInfoResponseBean.setUserManagerDetailCustomizeVO(userManagerUpdateCustomizeVO);
        }
        //开户信息
        BankCardVO bankCardVO = userCenterService.getBankCardByUserId(userId);
        if(null!=bankCardVO){
            BankCardCustomizeVO bankCardCustomizeVO = new BankCardCustomizeVO();
            BeanUtils.copyProperties(bankCardVO,bankCardCustomizeVO);
            initUserBaseInfoResponseBean.setBankCardInfo(bankCardCustomizeVO);
        }
        int changeType = 0;
        switch(updType){
            //修改手机号
            case "mobile":
                changeType = 4;
                break;
            //修改邮箱
            case "email":
                changeType = 5;
                break;
            //修改用户角色
            case "userRole":
                changeType =6;
                break;
            //修改银行卡信息
            case "bankCard":
                changeType = 7;
                break;
        }
        //修改日志
        UserChangeLogRequest userChangeLogRequest = new UserChangeLogRequest();
        if(StringUtils.isNotEmpty(userId)){
            int intUserId = Integer.parseInt(userId);
            userChangeLogRequest.setUserId(intUserId);
            //查找日志类型
            userChangeLogRequest.setChangeType(changeType);
            List<UserChangeLogVO> userChangeLogVOList = userCenterService.selectUserChageLog(userChangeLogRequest);
            List<UserChangeLogCustomizeVO> userChangeLogCustomizeVOList = new ArrayList<UserChangeLogCustomizeVO>();
            if(null!=userChangeLogVOList&&userChangeLogVOList.size()>0){
                userChangeLogCustomizeVOList = CommonUtils.convertBeanList(userChangeLogVOList, UserChangeLogCustomizeVO.class);
            }
            initUserBaseInfoResponseBean.setUsersChangeLogForm(userChangeLogCustomizeVOList);
        }
        return new AdminResult<InitUserBaseInfoResponseBean>(initUserBaseInfoResponseBean);
    }

    //查询银联号
    @ResponseBody
    @PostMapping(value = "/searchPayAllianceCode")
    @ApiOperation(value = "查找银行卡信息", notes = "查找银行卡信息")
    public AdminResult<SearchCompanyInfoResponseBean>  searchPayAllianceCode(@RequestBody UserInfosUpdCustomizeRequestBean userInfosUpdCustomizeRequestBean) {
        if(StringUtils.isBlank(userInfosUpdCustomizeRequestBean.getUserId()) || "null".equals(userInfosUpdCustomizeRequestBean.getUserId())){
            return new AdminResult<>(FAIL, "用户id不能为空");
        }
        SearchCompanyInfoResponseBean  searchCompanyInfoResponseBean = new  SearchCompanyInfoResponseBean();
        //根据银行卡号查找银行信息
        BankCardResponse bankCardResponse = userCenterService.getBankInfoByAccount(userInfosUpdCustomizeRequestBean.getCardNo(),userInfosUpdCustomizeRequestBean.getUserId());
        if(null!=bankCardResponse){
            BankCardVO bankCardVO =bankCardResponse.getResult();
            CompanyInfoCompanyInfoVO companyInfoCompanyInfoVO = new CompanyInfoCompanyInfoVO();
            companyInfoCompanyInfoVO.setBankName(bankCardVO.getBank());
            companyInfoCompanyInfoVO.setPayAllianceCode(bankCardVO.getPayAllianceCode());
            companyInfoCompanyInfoVO.setBankId(bankCardVO.getBankId().toString());
            searchCompanyInfoResponseBean.setCompany(companyInfoCompanyInfoVO);
            return new AdminResult<SearchCompanyInfoResponseBean>(searchCompanyInfoResponseBean);
        }

       /* BankCallBean bankCallBean = userCenterService.payAllianceCodeQuery(userInfosUpdCustomizeRequestBean.getCardNo(), Integer.parseInt(userInfosUpdCustomizeRequestBean.getUserId()));
        AdminResult<Response> result = new AdminResult<Response>();
        Response response = new Response();
        logger.info("=======用户修改银行卡,查找银联号返回结果为:"+JSONObject.toJSON(bankCallBean+"========="));
        if (null != bankCallBean && BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankCallBean.getRetCode())) {
            //如果调用银行接口没有返回联行号,则查找本地联行号
            if (StringUtils.isNotBlank(bankCallBean.getPayAllianceCode())) {
                //如果调用银行接口查找到银联号,则进行显示
                response.setResult(bankCallBean.getPayAllianceCode());
                result.setData(response);
                result.setStatus(SUCCESS);
                logger.info("============本地银联号为："+bankCallBean.getPayAllianceCode()+" ============");
                return result;
            }
        } else {
            logger.info("调用银行接口未能查找到银联号,调用本地数据库查找");
            List<JxBankConfigVO> banksConfigList = userCenterService.getBankConfigByBankName(userInfosUpdCustomizeRequestBean.getBank());
            if (CollectionUtils.isNotEmpty(banksConfigList)) {
                JxBankConfigVO banksConfig = banksConfigList.get(0);
                response.setResult(banksConfig.getPayAllianceCode());
                result.setStatus(SUCCESS);
                result.setStatusDesc("未查询到分行联行号已填充总行联行号");
                result.setData(response);
                logger.info("============本地银联号为："+banksConfig.getPayAllianceCode()+" ============");
                return result;
            } else {
                return new AdminResult<>(FAIL, "未查询到联行号");
            }
        }*/
        return new AdminResult<>(FAIL, "未能查找到银行卡信息！");
    }


    @ResponseBody
    @PostMapping(value = "/updateUserBaseInfo")
    @ApiOperation(value = "保存用户基本信息", notes = "保存用户基本信息")
    public AdminResult<Response> updateUserBaseInfo(HttpServletRequest request, @RequestBody UserInfosUpdCustomizeRequestBean userInfosUpdCustomizeRequestBean) {
        AdminSystemVO  adminSystemVO = this.getUser(request);
        UserInfosUpdCustomizeRequest userInfosUpdCustomizeRequest = new UserInfosUpdCustomizeRequest();
        BeanUtils.copyProperties(userInfosUpdCustomizeRequestBean,userInfosUpdCustomizeRequest);
        int instFlg = 0;
        if(null!=adminSystemVO){
            userInfosUpdCustomizeRequest.setLoginUserName(adminSystemVO.getUsername());
            userInfosUpdCustomizeRequest.setLoginUserId(Integer.parseInt(adminSystemVO.getId()));
            if(userInfosUpdCustomizeRequestBean.getUpdFlg().equals("bankCard")){
                instFlg = userCenterService.updateUserBankInfo(userInfosUpdCustomizeRequest);
            }else{
                //修改用户基本信息(电话,邮箱,用户角色)
               instFlg= userCenterService.updateUserBaseInfo(userInfosUpdCustomizeRequest);
            }
            if (instFlg<=0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }else{
                return new AdminResult<>();
            }
        }
        return new AdminResult<>(FAIL, "后台用户未登录");
    }



    /**
     * 同步用户角色 add by jijun 20181014
     */
    @GetMapping(value = "/syncRoleAction/{userId}")
    @ApiOperation(value = "同步用户角色", notes = "同步用户角色")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFYRE)
    public AdminResult syncRoleAction(HttpServletRequest request,@PathVariable String userId){
        if (StringUtils.isBlank(userId)){
            return new AdminResult<>(FAIL, "获取用户userId失败!");
        }

        UserVO user = userCenterService.selectUserByUserId(userId);
        //获取共同参数start
        String bankInstCode = systemConfig.getBANK_INSTCODE();
        String bankCode = systemConfig.getBANK_BANKCODE();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        //获取共同参数end
        BankCallBean selectbean = new BankCallBean();
        //共同参数封装start
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setInstCode(bankInstCode);// 机构代码
        selectbean.setBankCode(bankCode);
        selectbean.setTxDate(txDate);
        selectbean.setTxTime(txTime);
        selectbean.setSeqNo(seqNo);
        //000001手机APP,000002网页,000003微信,000004柜面
        selectbean.setChannel("000002");
        //共同参数封装end

        //非共同参数封装start
        selectbean.setTxCode(BankCallMethodConstant.TXCODE_ACCOUNT_QUERY_BY_MOBILE);
        selectbean.setMobile(user.getMobile());
        //非共同参数封装end

        BankCallBean retBean;
        try {
            retBean= BankCallUtils.callApiBg(selectbean);
        }catch (Exception e){
            logger.error("请求银行接口出错!手机号:"+user.getMobile());
            return new AdminResult<>(FAIL, "请求银行接口出错!");
        }

        if (Validator.isNull(retBean)){
            //获取银行账户信息失败
            return new AdminResult<>(FAIL, "获取银行账户信息失败!");
        }
        //获取userInfo
        UserInfoVO userInfo = userCenterService.selectUserInfoByUserId(userId);
        if (Validator.isNotNull(userInfo)){
            UserManagerUpdateRequest userRequest = new UserManagerUpdateRequest();
            AdminSystemVO adminSystemVO = this.getUser(request);

            userRequest.setUserId(userId);
            userRequest.setLoginUserName(adminSystemVO.getUsername());
            userRequest.setLogingUserId(adminSystemVO.getId());
            userRequest.setUserRole(retBean.getIdentity());
            userRequest.setBorrowerType(userInfo.getBorrowerType());
            userRequest.setStatus(user.getStatus()==null?"":String.valueOf(user.getStatus()));
            userRequest.setMobile(user.getMobile());
            userRequest.setRemark("会员管理-同步用户角色");

            int result=userCenterService.updataUserInfo(userRequest);
            if (result<=0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        }else {
            return new AdminResult<>(FAIL, "查询不到对应的用户信息!");
        }
        return new AdminResult<>(SUCCESS,"同步用户角色成功!");
    }


    /**
     * 用户销户操作
     *
     * @param request
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/cancellationAccountAction/{userId}")
    @ApiOperation(value = "用户销户", notes = "用户销户")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_BANK_CANCELLATION_ACCOUNT)
    public AdminResult cancellationAccountAction(HttpServletRequest request, @PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            return new AdminResult<>(FAIL, "获取用户userId失败!");
        }
        // 根据用户ID查询用户信息
        UserVO userVO = this.userCenterService.selectUserByUserId(userId);
        if (userVO == null) {
            return new AdminResult<>(FAIL, "根据用户ID查询用户信息失败");
        }
        // 根据用户ID查询用户详情信息
        UserInfoVO userInfoVO = this.userCenterService.selectUserInfoByUserId(userId);
        if (userInfoVO == null) {
            return new AdminResult<>(FAIL, "根据用户ID查询用户详情信息失败");
        }
        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        String loginUserName = adminSystemVO.getUsername();
        // 判断用户是否开户
        Integer bankOpenAccount = userVO.getBankOpenAccount();
        // 用户销户VO
        BankCancellationAccountRequest bankCancellationAccountVO = new BankCancellationAccountRequest();
        // 用户已开户的情况
        if (bankOpenAccount == 1) {
//            // 调用银行接口查询用户信息
//            BankCallBean bankCallBean = new BankCallBean(userId,BankCallConstant.TXCODE_ACCOUNT_QUERY_BY_MOBILE,0);
//            bankCallBean.setMobile(userVO.getMobile());
//            BankCallBean callBackBean ;
//            try {
//                callBackBean = BankCallUtils.callApiBg(bankCallBean);
//            } catch (Exception e) {
//                logger.error("请求银行接口出错!手机号:" + userVO.getMobile());
//                return new AdminResult<>(FAIL, "请求银行接口出错!");
//            }
//            if (Validator.isNull(callBackBean) || !BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankCallBean.getRetCode())){
//                //获取银行账户信息失败
//                return new AdminResult<>(FAIL, "获取用户银行账户信息失败!");
//            }
//            // 账户状态
//            // 空-正常
//            // C-止付
//            // Z-注销
//            if (!"Z".equals(bankCallBean.getAcctState())){
//                return new AdminResult<>(FAIL, "用户银行状态未销户,请确认");
//            }
            // 获取用户开户信息
            BankOpenAccountVO bankOpenAccountVO = this.userCenterService.queryBankOpenAccountByUserId(Integer.parseInt(userId));
            // 电子账户号
            bankCancellationAccountVO.setBankAccount(bankOpenAccountVO != null ? bankOpenAccountVO.getAccount() : "");
            // 是否开户
            bankCancellationAccountVO.setBankOpenAccount(1);
            // 用户姓名
            bankCancellationAccountVO.setTruename(userInfoVO.getTruename());
            // 用户身份证号
            bankCancellationAccountVO.setIdcard(userInfoVO.getIdcard());
            // 查询用户银行卡信息
            BankCardVO bankCardVO = this.userCenterService.getBankCardByUserId(userId);
            // 用户银行卡卡号
            bankCancellationAccountVO.setCardNo(bankCardVO == null ? "" : bankCardVO.getCardNo());
            // 注册手机号
            bankCancellationAccountVO.setMobile(userVO.getMobile());
            // 注册时间
            bankCancellationAccountVO.setRegTime(userVO.getRegTime());
            // 删除用户ID
            bankCancellationAccountVO.setUserId(userVO.getUserId());
            // 删除用户名
            bankCancellationAccountVO.setUsername(userVO.getUsername());
            // 操作人用户ID
            bankCancellationAccountVO.setCreateUserId(Integer.parseInt(loginUserId));
            // 操作人用户名
            bankCancellationAccountVO.setCreateUserName(loginUserName);
            // 用户销户操作
            this.userCenterService.cancellationAccountAction(userId, bankOpenAccount);
            // 保存用户销户记录表
            this.userCenterService.saveCancellationAccountRecordAction(bankCancellationAccountVO);
        } else if (bankOpenAccount == 0) {
            // 用户未开户
            // 电子账户号
            bankCancellationAccountVO.setBankAccount("");
            // 是否开户
            bankCancellationAccountVO.setBankOpenAccount(0);
            // 用户姓名
            bankCancellationAccountVO.setTruename("");
            // 用户身份证号
            bankCancellationAccountVO.setIdcard("");
            // 用户银行卡号
            bankCancellationAccountVO.setCardNo("");
            // 注册手机号
            bankCancellationAccountVO.setMobile(userVO.getMobile());
            // 注册时间
            bankCancellationAccountVO.setRegTime(userVO.getRegTime());
            // 删除用户ID
            bankCancellationAccountVO.setUserId(userVO.getUserId());
            // 删除用户名
            bankCancellationAccountVO.setUsername(userVO.getUsername());
            // 操作人用户ID
            bankCancellationAccountVO.setCreateUserId(Integer.parseInt(loginUserId));
            // 操作人用户名
            bankCancellationAccountVO.setCreateUserName(loginUserName);
            // 用户销户操作
            this.userCenterService.cancellationAccountAction(userId, userVO.getBankOpenAccount());
            // 未开户用户销户后,删除用户Account表
            this.userCenterService.deleteUserAccountAction(userId);
            // 保存用户销户记录表
            this.userCenterService.saveCancellationAccountRecordAction(bankCancellationAccountVO);
        }
        return new AdminResult<>(SUCCESS, "用户销户成功!");
    }

    /**
     * 根据所属银行名查找银联号
     * @param bankName
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/selectBankConfigByName",produces = "application/json; charset=utf-8")
    @ApiOperation(value = "根据所属银行名查找银行配置信息", notes = "根据所属银行名查找银行配置信息")
    public  AdminResult<ListResult<JxBankConfigCustomizeVO>> selectBankConfigByName(HttpServletRequest request, @RequestParam String bankName) {
        AdminResult<JxBankConfigCustomizeVO> result = new AdminResult<JxBankConfigCustomizeVO>();
        Response response = new Response();
        List<JxBankConfigVO> bankConfigVOList = userCenterService.getBankConfigByBankName(bankName);
        if(CollectionUtils.isEmpty(bankConfigVOList)) {
            return new AdminResult<>(FAIL, "未查询到配置信息");
        }
        List<JxBankConfigCustomizeVO> jxBankConfigCustomizeVO =CommonUtils.convertBeanList(bankConfigVOList,JxBankConfigCustomizeVO.class);
        return new AdminResult<ListResult<JxBankConfigCustomizeVO>>(ListResult.build(jxBankConfigCustomizeVO,jxBankConfigCustomizeVO.size())) ;
    }

    /**
     * 根据银行卡号查询相应的银行配置信息
     * @param request
     * @param cardNo
     * @return
     */
  /*  @ResponseBody
    @GetMapping(value = "/selectBankConfigByCardNo")
    @ApiOperation(value = "根据银卡号查找配置信息", notes = "根据银卡号查找配置信息")
    public AdminResult<JxBankConfigCustomizeVO> selectBankConfigByCardNo(HttpServletRequest request, @RequestParam String cardNo) {
        AdminResult<JxBankConfigCustomizeVO> result = new AdminResult<JxBankConfigCustomizeVO>();
        JxBankConfigCustomizeVO jxBankConfigVO = userCenterService.getBankIdByCardNo(cardNo);
        if(null==jxBankConfigVO){
            return new AdminResult<>(FAIL, "未查询到所属银行信息");
        }
        return new AdminResult<JxBankConfigCustomizeVO>(jxBankConfigVO);
    }*/

}
